package com.wangzh.app.weixin.pa.model.enums;

/**
 * AI开放接口里的语言类型，目前只支持两种：中文和英文
 */
public enum AiLangType {
    /**
     * 中文 汉语.
     */
    zh_CN("zh_CN"),
    /**
     * 英文 英语.
     */
    en_US("en_US");

    private String code;

    AiLangType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
