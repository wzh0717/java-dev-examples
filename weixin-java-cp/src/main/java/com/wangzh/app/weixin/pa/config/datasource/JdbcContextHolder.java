package com.wangzh.app.weixin.pa.config.datasource;


/**
 * @Description:
 * @CreatedDate:2019-03-26 16:20
 * @Author:wangzh
 */
public class JdbcContextHolder {
    /**
     * 本地线程共享对象（保证在同一线程下切换后不要被其他线程修改）
     */
    private final static ThreadLocal<String> local = new ThreadLocal<>();

    public static void setDataSource(String name) {
        local.set(name);
    }

    public static String getDataSource() {
        return local.get();
    }

    public static void removeDataSource() {
        local.remove();
    }
}
