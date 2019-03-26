package com.wangzh.app.weixin.pa.constant;

/**
 * @Description: 数据源类型
 * @CreatedDate:2019-03-26 15:30
 * @Author:wangzh
 */
public enum DataSourceType {
    /**
     * 读
     */
    DATASOURCE_READ_KEY("database.master.read"),
    /**
     * 写
     */
    DATASOURCE_WRITE_KEY("database.master.write");

    DataSourceType(String key) {
        this.key = key;
    }

    private String key;

    public String getKey() {
        return key;
    }
}
