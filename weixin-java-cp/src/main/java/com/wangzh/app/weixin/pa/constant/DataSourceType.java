package com.wangzh.app.weixin.pa.constant;

/**
 * @Description: 数据源类型
 * @CreatedDate:2019-03-26 15:30
 * @Author:wangzh
 */
public enum DataSourceType {
    Mysql("mysql"),
    Oracle("oracle"),
    SqlServer("SqlServer");

    DataSourceType(String name) {
        this.name = name;
    }

    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
