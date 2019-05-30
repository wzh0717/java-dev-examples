package com.wangzh.shirojwt.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Description:
 * @Auther:wangzh
 * @Date: 2019/05/30 10:30
 */

public class JWTToken implements AuthenticationToken {

    /**
     * 密钥
     */
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
