package com.wangzh.app.weixin.pa.model.result;

import java.io.Serializable;

/**
 * @Description: 卡卷API签名
 * @Auther:wangzh
 * @Date: 2019/03/24 16:13
 */

public class CardApiSignatureDTO implements Serializable {
    private static final long serialVersionUID = -3903704342555130964L;
    private String appId;

    private String cardId;

    private String cardType;

    private String locationId;

    private String code;

    private String openId;

    private Long timestamp;

    private String nonceStr;

    private String signature;


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
