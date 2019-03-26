package com.wangzh.app.weixin.pa;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import java.security.Security;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan("com.wangzh.app.*.**.**")
public class WeixinJavaCpApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        //BC模式
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
        SpringApplication.run(WeixinJavaCpApplication.class, args);
        System.out.println("[weixin application] main...");
    }

    /**
     * 创建一个SpringApplicationBuilder交付给spring boot框架来完成初始化运行配置
     *
     * @param builder
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WeixinJavaCpApplication.class);
    }

}
