package com.wangzh.weixin.pa.model.result;

import java.io.Serializable;

/**
 * @Description:JS API 签名
 * @Auther:wangzh
 * @Date: 2019/03/24 16:15
 */

public class JsApiSignatureDTO implements Serializable {

    private static final long serialVersionUID = 5893443640749005279L;
    private String appId;

    private String nonceStr;

    private long timestamp;

    private String url;

    private String signature;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
