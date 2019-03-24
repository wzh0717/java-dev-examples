package com.wangzh.weixin.pa.model.result;

import java.io.Serializable;

/**
 * @Description: 微信 AccessToken
 * @Auther:wangzh
 * @Date: 2019/03/24 15:01
 */

public class AccessTokenDTO implements Serializable {
    private static final long serialVersionUID = -6754410569097877478L;
    /**
     * 凭证
     */
    private String accessToken;
    /**
     * 凭证有效时间，默认：7200秒
     */
    private int expiresIn = -1;


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
