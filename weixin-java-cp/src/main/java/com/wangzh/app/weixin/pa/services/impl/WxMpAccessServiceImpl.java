package com.wangzh.app.weixin.pa.services.impl;

//import com.wangzh.app.commons.http.HttpClient;
import com.wangzh.app.weixin.pa.services.WxMpAccessService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description: access token
 * @Auther:wangzh
 * @Date: 2019/03/24 17:55
 */
@Service
public class WxMpAccessServiceImpl implements WxMpAccessService {
    /**
     * 获取access token
     *
     * @param url    链接
     * @param params 请求参数
     * @return
     */
    @Override
    public String getAccessToken(String url, Map<String, Object> params) throws Exception {
        // return HttpClient.httpGet(url, params);
        return null;
    }
}
