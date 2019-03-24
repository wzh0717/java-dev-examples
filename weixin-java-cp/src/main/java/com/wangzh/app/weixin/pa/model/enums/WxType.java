package com.wangzh.app.weixin.pa.model.enums;

/**
 * 微信类型
 */
public enum WxType {
    /**
     * 企业微信
     */
    CP(1),
    /**
     * 微信公众号
     */
    MP(2),
    /**
     * 微信小程序
     */
    MiniApp(3),
    /**
     * 微信开放平台
     */
    Open(4),
    /**
     * 微信支付
     */
    Pay(5);

    private int code;

    WxType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
