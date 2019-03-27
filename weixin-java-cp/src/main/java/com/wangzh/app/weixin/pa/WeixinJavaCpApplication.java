package com.wangzh.app.weixin.pa;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.Security;

@SpringBootApplication
public class WeixinJavaCpApplication {
    public static void main(String[] args) {
        //BC模式
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
        SpringApplication.run(WeixinJavaCpApplication.class, args);
        System.out.println("[weixin application] main...");
    }

}
