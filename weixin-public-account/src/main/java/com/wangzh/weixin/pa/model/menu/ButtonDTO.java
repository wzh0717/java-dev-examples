package com.wangzh.weixin.pa.model.menu;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * @Description:
 * @Auther:wangzh
 * @Date: 2019/03/24 16:50
 */

public class ButtonDTO implements Serializable {
    private static final long serialVersionUID = -7498640547222647897L;
    /**
     * 菜单类型
     */
    private String type;
    /**
     * 菜单名称
     */
    @JSONField(name = "name")
    private String name;
    private String key;
    private String url;
    private String value;
    private String mediaID;
    private String text;
    private String voice;
    private String img;
    @JSONField(name = "sub_button")
    private SubButtonsDTO subButtons;

    @JSONField(name = "news_info")
    private NewsInfoDTO newsInfo;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMediaID() {
        return mediaID;
    }

    public void setMediaID(String mediaID) {
        this.mediaID = mediaID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public SubButtonsDTO getSubButtons() {
        return subButtons;
    }

    public void setSubButtons(SubButtonsDTO subButtons) {
        this.subButtons = subButtons;
    }

    public NewsInfoDTO getNewsInfo() {
        return newsInfo;
    }

    public void setNewsInfo(NewsInfoDTO newsInfo) {
        this.newsInfo = newsInfo;
    }
}
