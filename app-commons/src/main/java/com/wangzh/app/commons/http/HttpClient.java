package com.wangzh.app.commons.http;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 基于apache HttpClient HTTP辅助类
 * @Auther:wangzh
 * @Date: 2019/03/23 21:22
 */
public class HttpClient {

    public static final ContentType TEXT_PLAIN = ContentType.create("text/plain", StandardCharsets.UTF_8);
    /**
     * 最大连接数
     */
    private static final int MAX_TOTAL = 200;
    private static final int Max_PRE_ROUTE = 50;
    private static final int MAX_ROUTE = 50;
    /**
     * 请求超时时间
     */
    private static final int CONNECT_TIMEOUT = 3000;
    /**
     * 等待超时时间
     */
    private static final int SOCKET_TIMEOUT = 2000;
    private static final long IDLE_TIMEOUT = 5000;
    private static final long MONITOR_VAL = 3000;
    /**
     * Http 连接池
     */
    private static PoolingHttpClientConnectionManager connectionManager;
    /**
     * HttpClient
     */
    private static CloseableHttpClient httpClient;
    private static ScheduledExecutorService monitorExecutor;
    private final static Object syncLock = new Object();


    /**
     * 创建 HttpClient 实例
     *
     * @param path     证书全路径
     * @param password 证书密码
     * @return
     */
    public static CloseableHttpClient createHttpClient(String path, String password) throws Exception {
        // 初始化连接池，可用于请求HTTP/HTTPS（信任所有证书）
        connectionManager = new PoolingHttpClientConnectionManager(getRegistry());
        // 连接池最大连接数
        connectionManager.setMaxTotal(MAX_TOTAL);
        // 每个路由最大连接数，默认值是50
        connectionManager.setDefaultMaxPerRoute(Max_PRE_ROUTE);
        //Keep-Alive
        ConnectionKeepAliveStrategy keepAliveStrategy = new ConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                HeaderElementIterator it = new BasicHeaderElementIterator
                        (response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase
                            ("timeout")) {
                        return Long.parseLong(value) * 1000;
                    }
                }
                return 60 * 1000;//如果没有约定，则默认定义时长为60s
            }
        };
        //请求失败重试
        HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {
            /**
             * 失败重试
             * @param e
             * @param executionCount 重试次数
             * @param context
             * @return
             */
            @Override
            public boolean retryRequest(IOException e, int executionCount, HttpContext context) {
                if (executionCount > 3) {
                    System.out.println("retry has more than 3 time, give up request");
                    return false;
                }
                if (e instanceof NoHttpResponseException) {
                    //服务器没有响应,可能是服务器断开了连接,应该重试
                    System.out.println("receive no response from server, retry");
                    return true;
                }
                if (e instanceof SSLHandshakeException) {
                    // SSL握手异常
                    System.out.println("SSL hand shake exception");
                    return false;
                }
                if (e instanceof InterruptedIOException) {
                    //超时
                    System.out.println("InterruptedIOException");
                    return false;
                }
                if (e instanceof UnknownHostException) {
                    // 服务器不可达
                    System.out.println("server host unknown");
                    return false;
                }
                if (e instanceof ConnectTimeoutException) {
                    // 连接超时
                    System.out.println("Connection Time out");
                    return false;
                }
                if (e instanceof SSLException) {
                    System.out.println("SSLException");
                    return false;
                }

                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                if (!(request instanceof HttpEntityEnclosingRequest)) {
                    //如果请求不是关闭连接的请求
                    return true;
                }
                return false;
            }
        };
        HttpClientBuilder clientBuilder = HttpClients.custom().setConnectionManager(connectionManager)
                .setKeepAliveStrategy(keepAliveStrategy)
                .setDefaultRequestConfig(setRequestConfig())
                .setRetryHandler(retryHandler);

        //https
        if (StringUtils.isNotBlank(path) && StringUtils.isNotBlank(password))
            //获取HTTPS SSL证书
            return clientBuilder.setSSLSocketFactory(getSSLFactory(path, password)).build();
        return clientBuilder.build();
    }

    /**
     * 获取 HttpClient 单例
     *
     * @param path     证书全路径
     * @param password 证书密码
     * @return
     */
    public static CloseableHttpClient getHttpClient(String path, String password) throws Exception {
        if (null == httpClient) {
            synchronized (syncLock) {
                if (null == httpClient) {
                    httpClient = createHttpClient(path, password);
                    //开启监控线程,对异常和空闲线程进行关闭
                    monitorExecutor = Executors.newScheduledThreadPool(1);
                    monitorExecutor.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            //关闭异常连接
                            connectionManager.closeExpiredConnections();
                            //关闭5s空闲的连接
                            connectionManager.closeIdleConnections(IDLE_TIMEOUT, TimeUnit.MILLISECONDS);
                            System.out.println("close expired and idle for over 5s connection");
                        }
                    }, MONITOR_VAL, MONITOR_VAL, TimeUnit.MILLISECONDS);
                }
            }
        }
        return httpClient;
    }


    /**
     * 发送 HTTP GET请求
     * 不带请求参数和请求头
     *
     * @param url 地址
     * @return
     * @throws Exception
     */
    public static String httpGet(String url) throws Exception {
        HttpGet httpGet = new HttpGet(url);

        return doHttp(httpGet);
    }

    /**
     * 发送 HTTP GET请求
     * 带请求参数，不带请求头
     *
     * @param url    地址
     * @param params 参数
     * @return
     * @throws Exception
     */
    public static String httpGet(String url, Map<String, Object> params) throws Exception {
        // 转换请求参数
        List<NameValuePair> pairs = covertParams2NVPS(params);

        // 装载请求地址和参数
        URIBuilder ub = new URIBuilder();
        ub.setPath(url);
        ub.setParameters(pairs);

        HttpGet httpGet = new HttpGet(ub.build());

        return doHttp(httpGet);
    }

    /**
     * 发送 HTTP GET请求
     * 带请求参数和请求头
     *
     * @param url     地址
     * @param headers 请求头
     * @param params  参数
     * @return
     * @throws Exception
     */
    public static String httpGet(String url, Map<String, Object> headers, Map<String, Object> params) throws Exception {
        // 转换请求参数
        List<NameValuePair> pairs = covertParams2NVPS(params);

        // 装载请求地址和参数
        URIBuilder ub = new URIBuilder();
        ub.setPath(url);
        ub.setParameters(pairs);

        HttpGet httpGet = new HttpGet(ub.build());
        // 设置请求头
        for (Map.Entry<String, Object> param : headers.entrySet())
            httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));

        return doHttp(httpGet);
    }


    /**
     * 发送 HTTP POST请求
     * 不带请求参数和请求头
     *
     * @param url 地址
     * @return
     * @throws Exception
     */
    public static String httpPost(String url) throws Exception {
        HttpPost httpPost = new HttpPost(url);

        return doHttp(httpPost);
    }

    /**
     * 发送 HTTP POST请求
     * 带请求参数，不带请求头
     *
     * @param url    地址
     * @param params 参数
     * @return
     * @throws Exception
     */
    public static String httpPost(String url, Map<String, Object> params) throws Exception {
        // 转换请求参数
        List<NameValuePair> pairs = covertParams2NVPS(params);

        HttpPost httpPost = new HttpPost(url);
        // 设置请求参数
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, StandardCharsets.UTF_8.name()));

        return doHttp(httpPost);
    }

    /**
     * 发送 HTTP POST请求
     * 带请求参数和请求头
     *
     * @param url     地址
     * @param headers 请求头
     * @param params  参数
     * @return
     * @throws Exception
     */
    public static String httpPost(String url, Map<String, Object> headers, Map<String, Object> params) throws Exception {
        // 转换请求参数
        List<NameValuePair> pairs = covertParams2NVPS(params);

        HttpPost httpPost = new HttpPost(url);
        // 设置请求参数
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, StandardCharsets.UTF_8.name()));
        // 设置请求头
        for (Map.Entry<String, Object> param : headers.entrySet())
            httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));

        return doHttp(httpPost);
    }

    /**
     * 发送HTTP POST/FORM请求
     * 模拟表单文件上传，默认表单名称为“media”，无附加表单
     *
     * @param url      请求地址
     * @param pathName 模拟表单文件全路径名
     * @return Http POST/FORM 请求结果
     * @throws Exception IO流异常
     */
    public static String httpPostForm(String url, String pathName) throws Exception {
        return httpPostForm(url, "media", pathName, null);
    }

    /**
     * 发送HTTP POST/FORM请求
     * <p>模拟表单文件上传，默认表单名称为“media”，无附加表单</p>
     *
     * @param url  请求地址
     * @param file 模拟表单文件
     * @return Http POST/FORM 请求结果
     * @throws Exception IO流异常
     */
    public static String httpPostForm(String url, File file) throws Exception {
        return httpPostForm(url, "media", file, null);
    }

    /**
     * 发送 HTTP POST/FORM请求
     * <p>模拟表单文件上传</p>
     *
     * @param url      请求地址
     * @param name     模拟表单名称
     * @param pathName 模拟表单文件路径
     * @param map      文件上传表单之外的附加表单，新增永久视频素材API需要使用该字段
     * @return Http POST/FORM 请求结果
     * @throws Exception IO流异常
     */
    public static String httpPostForm(String url, String name, String pathName, Map<String, Object> map) throws Exception {
        File file = new File(pathName);
        // 检查文件名是否合法，以及文件是否存在
        if (!file.isFile() || !file.exists())
            throw new Exception("HttpClient Post/Form模拟表单请求的文件名不合法或文件不存在");

        return httpPostForm(url, name, file, map);
    }


    /**
     * 发送 HTTP POST/FORM请求
     * 模拟表单文件上传
     *
     * @param url  请求地址
     * @param name 模拟表单名称
     * @param file 模拟表单文件
     * @param map  文件上传表单之外的附加表单，新增永久视频素材API需要使用该字段
     * @return Http POST/FORM 请求结果
     * @throws Exception IO流异常
     */
    public static String httpPostForm(String url, String name, File file, Map<String, Object> map) throws Exception {
        // 初始化请求链接
        HttpPost httppost = new HttpPost(url);

        // 组装模拟表单
        FileBody body = new FileBody(file);
        // 组装HTTP表单请求参数
        MultipartEntityBuilder builder = MultipartEntityBuilder.create().addPart(name, body);

        // 附加表单
        if (MapUtils.isNotEmpty(map)) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() == null)
                    continue;
                builder.addTextBody(entry.getKey(), entry.getValue().toString(), TEXT_PLAIN);
            }
        }

        // 构建HttpEntity
        HttpEntity entity = builder.build();
        // 设置请求参数
        httppost.setEntity(entity);

        return doHttp(httppost);
    }

    /**
     * 发送 HTTP POST请求，参数格式JSON
     * 请求参数是JSON格式，数据编码是UTF-8
     *
     * @param url
     * @param param
     * @return
     * @throws Exception
     */
    public static String httpPostJson(String url, String param) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        // 设置请求头
        httpPost.addHeader("Content-Type", "application/json; charset=UTF-8");
        // 设置请求参数
        httpPost.setEntity(new StringEntity(param, StandardCharsets.UTF_8.name()));

        return doHttp(httpPost);
    }

    /**
     * 发送 HTTP POST请求，参数格式XML
     * 请求参数是XML格式，数据编码是UTF-8
     *
     * @param url
     * @param param
     * @return
     * @throws Exception
     */
    public static String httpPostXml(String url, String param) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        // 设置请求头
        httpPost.addHeader("Content-Type", "application/xml; charset=UTF-8");
        // 设置请求参数
        httpPost.setEntity(new StringEntity(param, StandardCharsets.UTF_8.name()));

        return doHttp(httpPost);
    }

    /**
     * 发送 HTTPS POST请求
     * 使用指定的证书文件及密码，不带请求头信息
     *
     * @param url
     * @param param
     * @param path
     * @param password
     * @return
     * @throws Exception
     * @throws Exception
     */
    public static String httpsPost(String url, String param, String path, String password) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        // 设置请求参数
        httpPost.setEntity(new StringEntity(param, StandardCharsets.UTF_8.name()));

        return doHttps(httpPost, path, password);
    }

    /**
     * 发送 HTTPS POST请求
     * 使用指定的证书文件及密码，请求头为“application/xml;charset=UTF-8”
     *
     * @param url
     * @param param
     * @param path
     * @param password
     * @return
     * @throws Exception
     * @throws Exception
     */
    public static String httpsPostXml(String url, String param, String path, String password) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        // 设置请求头
        httpPost.addHeader("Content-Type", "application/xml; charset=UTF-8");
        // 设置请求参数
        httpPost.setEntity(new StringEntity(param, StandardCharsets.UTF_8.name()));

        return doHttps(httpPost, path, password);
    }

    /**
     * 将Map键值对拼接成QueryString字符串，UTF-8编码
     *
     * @param params
     * @return
     * @throws Exception
     */
    public static String getQueryString(Map<String, Object> params) throws Exception {
        return URLEncodedUtils.format(covertParams2NVPS(params), StandardCharsets.UTF_8.name());
    }

    /**
     * 将JavaBean属性拼接成QueryString字符串，UTF-8编码
     *
     * @param bean
     * @return
     * @throws Exception
     */
    public static String getQueryString(Object bean) throws Exception {
        // 将JavaBean转换为Map
        Map<String, Object> map = PropertyUtils.describe(bean);

        // 移除Map中键为“class”和值为空的项

        map = Maps.filterEntries(map, new Predicate<Map.Entry<String, Object>>() {
            public boolean apply(Map.Entry<String, Object> input) {
                // 返回false表示排除该项
                return !(input.getKey().equals("class") || input.getValue() == null);
            }
        });

        return URLEncodedUtils.format(covertParams2NVPS(map), StandardCharsets.UTF_8.name());
    }

    /**
     * 将表单字符串转换为JavaBean
     *
     * @param queryStr 表单字符串
     * @param clazz    {@link Class}待转换的JavaBean
     * @return
     * @throws Exception
     */
    public static <T> T parseNVPS2Bean(String queryStr, Class<T> clazz) throws Exception {
        // 将“表单字符串”形式的字符串解析成NameValuePair集合
        List<NameValuePair> list = URLEncodedUtils.parse(queryStr, StandardCharsets.UTF_8);
        Map<String, String> rsMap = new HashMap<String, String>();

        // 将NameValuePair集合中的参数装载到Map中
        for (NameValuePair nvp : list)
            rsMap.put(nvp.getName(), nvp.getValue());

        // 实例化JavaBean对象
        T t = clazz.newInstance();
        // 将Map键值对装载到JavaBean中
        BeanUtils.populate(t, rsMap);

        return t;
    }

    /**
     * 转换请求参数，将Map键值对拼接成QueryString字符串
     *
     * @param params
     * @return
     */
    public static String covertParams2QueryString(Map<String, Object> params) {
        List<NameValuePair> pairs = covertParams2NVPS(params);

        return URLEncodedUtils.format(pairs, StandardCharsets.UTF_8.name());
    }

    /**
     * 转换请求参数
     *
     * @param params
     * @return
     */
    public static List<NameValuePair> covertParams2NVPS(Map<String, Object> params) {
        List<NameValuePair> pairs = new ArrayList<>();

        for (Map.Entry<String, Object> param : params.entrySet())
            pairs.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));

        return pairs;
    }

    /**
     * 关闭连接池
     */
    public static void closeConnectionPool() {
        try {
            httpClient.close();
            connectionManager.close();
            monitorExecutor.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送 HTTP 请求
     *
     * @param request
     * @return
     * @throws Exception
     */
    private static String doHttp(HttpRequestBase request) throws Exception {
        // 通过连接池获取连接对象
        CloseableHttpClient httpClient = getHttpClient(null, null);

        return doRequest(httpClient, request);
    }

    /**
     * 发送 HTTPS 请求
     * 使用指定的证书文件及密码
     *
     * @param request
     * @param path
     * @param password
     * @return
     * @throws Exception
     * @throws Exception
     */
    private static String doHttps(HttpRequestBase request, String path, String password) throws Exception {
        // 通过连接池获取连接对象
        CloseableHttpClient httpClient = getHttpClient(path, password);

        return doRequest(httpClient, request);
    }


    /**
     * 处理Http/Https请求，并返回请求结果
     * 注：默认请求编码方式 UTF-8
     *
     * @param httpClient
     * @param request
     * @return
     * @throws Exception
     */
    private static String doRequest(CloseableHttpClient httpClient, HttpRequestBase request) throws Exception {
        String result = null;
        CloseableHttpResponse response = null;

        try {
            // 获取请求结果
            response = httpClient.execute(request);
            // 解析请求结果
            HttpEntity entity = response.getEntity();
            // 转换结果
            result = EntityUtils.toString(entity, StandardCharsets.UTF_8.name());
            // 关闭IO流
            EntityUtils.consume(entity);
        } finally {
            if (null != response)
                response.close();
        }

        return result;
    }


    /**
     * 获取 HTTPClient注册器
     *
     * @param:
     * @return:
     * @auther:wangzh
     * @date: 2019-03-23 21:23
     */
    private static Registry<ConnectionSocketFactory> getRegistry() {
        Registry<ConnectionSocketFactory> registry = null;

        try {
            registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", new PlainConnectionSocketFactory())
                    .register("https", getSSLFactory()).build();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return registry;
    }

    /**
     * 获取HTTPS SSL连接工厂
     * 使用指定的证书文件及密码
     *
     * @param path     证书全路径
     * @param password 证书密码
     * @return
     * @throws Exception
     * @throws Exception
     */
    private static SSLConnectionSocketFactory getSSLFactory(String path, String password) throws Exception {

        // 初始化证书，指定证书类型为“PKCS12”
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        // 读取指定路径的证书
        FileInputStream input = new FileInputStream(new File(path));

        try {
            // 装载读取到的证书，并指定证书密码
            keyStore.load(input, password.toCharArray());
        } finally {
            input.close();
        }

        // 获取HTTPS SSL证书连接上下文
        SSLContext sslContext = SSLContexts.custom().loadKeyMaterial(keyStore, password.toCharArray()).build();

        // 获取HTTPS连接工厂，指定TSL版本
        SSLConnectionSocketFactory sslCsf = new SSLConnectionSocketFactory(sslContext, new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2"}, null, SSLConnectionSocketFactory.getDefaultHostnameVerifier());

        return sslCsf;
    }

    /**
     * 获取HTTPS SSL连接工厂
     * 跳过证书校验，即信任所有证书
     *
     * @return
     * @throws Exception
     */
    private static SSLConnectionSocketFactory getSSLFactory() throws Exception {
        // 设置HTTPS SSL证书信息，跳过证书校验，即信任所有证书请求HTTPS
        SSLContextBuilder sslBuilder = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                return true;
            }
        });

        // 获取HTTPS SSL证书连接上下文
        SSLContext sslContext = sslBuilder.build();

        // 获取HTTPS连接工厂
        SSLConnectionSocketFactory sslCsf = new SSLConnectionSocketFactory(sslContext, new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE);

        return sslCsf;
    }


    /**
     * Http 请求设置
     *
     */
    private static RequestConfig setRequestConfig() {
        RequestConfig config = RequestConfig.custom()
                .setConnectionRequestTimeout(CONNECT_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT)
                .build();
        return config;
    }

}
