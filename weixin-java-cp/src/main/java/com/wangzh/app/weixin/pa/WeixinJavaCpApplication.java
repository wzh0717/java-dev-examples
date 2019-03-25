package com.wangzh.app.weixin.pa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WeixinJavaCpApplication {
    public static void main(String[] args) {
        System.out.println("[main application] start...");
        SpringApplication.run(WeixinJavaCpApplication.class, args);
    }

}
