package com.wangzh.weixin.pa.model.enums;

public enum TicketType {
    /**
     * jsApi
     */
    JSAPI("jsapi"),
    /**
     * sdk
     */
    SDK("2"),
    /**
     * 微信卡券
     */
    WX_CARD("wx_card");
    /**
     * type代码
     */
    private String code;

    TicketType(String code) {
        this.code = code;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
