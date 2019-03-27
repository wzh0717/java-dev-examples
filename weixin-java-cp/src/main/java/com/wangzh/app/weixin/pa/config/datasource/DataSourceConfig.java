package com.wangzh.app.weixin.pa.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.wangzh.app.weixin.pa.constant.DataSourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @CreatedDate:2019-03-26 16:34
 * @Author:wangzh
 */
@Configuration
public class DataSourceConfig {
    private Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);


    /***************************写************************/
    @Value("${datasource.master.write.url}")
    private String url;

    @Value("${datasource.master.write.userName}")
    private String userName;

    @Value("${datasource.master.write.password}")
    private String password;

    @Value("${datasource.master.write.driverClassName}")
    private String driverClassName;

    @Value("${datasource.master.write.validationQuery}")
    private String validationQuery;

    @Bean(name = "writeDataSource")
    public DataSource writeDataSource() {
        return setDataSource(url, userName, password, driverClassName, validationQuery);
    }

    /***************************读************************/
    @Value("${datasource.master.read.url}")
    private String readUrl;

    @Value("${datasource.master.read.userName}")
    private String readUserName;

    @Value("${datasource.master.read.password}")
    private String readPassword;

    @Value("${datasource.master.read.driverClassName}")
    private String readDriverClassName;

    @Value("${datasource.master.read.validationQuery}")
    private String readValidationQuery;

    @Bean(name = "readDataSource")
    public DataSource readDataSource() {
        return setDataSource(readUrl, readUserName, readPassword, readDriverClassName, readValidationQuery);
    }

    /**
     * @param url             连接地址
     * @param userName
     * @param password
     * @param driverClassName
     * @param validationQuery
     * @return
     */
    private DataSource setDataSource(String url, String userName, String password, String driverClassName, String validationQuery) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(userName);
        druidDataSource.setPassword(password);
        druidDataSource.setDriverClassName(driverClassName);
        druidDataSource.setValidationQuery(validationQuery);
        //druid数据源
        setDruidOptions(druidDataSource);
        return druidDataSource;
    }

    /***************************druid************************/
    @Value("${datasource.druid.initialSize}")
    private int initialSize;

    @Value("${datasource.druid.minIdle}")
    private int minIdle;

    @Value("${datasource.druid.maxActive}")
    private int maxActive;

    @Value("${datasource.druid.maxWait}")
    private int maxWait;

    @Value("${datasource.druid.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${datasource.druid.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${datasource.druid.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${datasource.druid.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${datasource.druid.testOnReturn}")
    private boolean testOnReturn;

    @Value("${datasource.druid.poolPreparedStatements}")
    private boolean poolPreparedStatements;

    @Value("${datasource.druid.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;

    @Value("${datasource.druid.filters}")
    private String filters;

    @Value("{datasource.druid.connectionProperties}")
    private String connectionProperties;

    /**
     * druid数据源的属性
     *
     * @param datasource
     */
    private void setDruidOptions(DruidDataSource datasource) {
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setPoolPreparedStatements(poolPreparedStatements);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        try {
            datasource.setFilters(filters);
        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.error("druid configuration initialization filter Exception", ex);
        }
        datasource.setConnectionProperties(connectionProperties);
    }

    @Bean(name = "dynamicDataSource")
    @Primary
    public DynamicDataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        DataSource readDs = readDataSource();
        DataSource writeDs = writeDataSource();
        //默认数据源
        dynamicDataSource.setDefaultTargetDataSource(writeDs);

        //设置多数据源
        Map<Object, Object> dsParams = new HashMap<>();
        dsParams.put(DataSourceType.DATASOURCE_READ_KEY.getKey(), readDs);
        dsParams.put(DataSourceType.DATASOURCE_WRITE_KEY.getKey(), writeDs);
        dynamicDataSource.setTargetDataSources(dsParams);

        return dynamicDataSource;
    }

    @Bean(name = "platformTransactionManager")
    public PlatformTransactionManager platformTransactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }

    @Bean(name = "druidServlet")
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        //白名单
        servletRegistrationBean.addInitParameter("allow", "");
        return servletRegistrationBean;
    }

    @Bean(name = "filterRegistrationBean")
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        filterRegistrationBean.addInitParameter("profileEnable", "true");
        filterRegistrationBean.addInitParameter("principalCookieName", "USER_COOKIE");
        filterRegistrationBean.addInitParameter("principalSessionName", "USER_SESSION");
        filterRegistrationBean.addInitParameter("DruidWebStatFilter", "/*");
        return filterRegistrationBean;
    }
}
