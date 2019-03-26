package com.wangzh.app.weixin.pa.config.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


/**
 * @Description:
 * @CreatedDate:2019-03-26 16:24
 * @Author:wangzh
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 数据源路由，此方法用于产生要选取的数据源逻辑名称
     *
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return JdbcContextHolder.getDataSource();
    }
}
