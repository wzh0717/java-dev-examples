package com.wangzh.app.weixin.pa.config.datasource;

import com.wangzh.app.weixin.pa.constant.DataSourceType;

import java.lang.annotation.*;

/**
 * @Description: 自定义注解TargetDataSource
 * @CreatedDate:2019-03-26 17:15
 * @Author:wangzh
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TargetDataSource {
    DataSourceType value() default DataSourceType.DATASOURCE_WRITE_KEY;
}
