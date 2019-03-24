package com.wangzh.weixin.pa.model.menu;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * @Description: 图文消息
 * @Auther:wangzh
 * @Date: 2019/03/24 17:01
 */

public class NewsInButtonDTO implements Serializable {
    private static final long serialVersionUID = 8540055298732361616L;
    /**
     * 图文消息标题
     */
    private String title;
    /**
     * 摘要
     */
    private String digest;
    /**
     * 作者
     */
    private String author;

    /**
     * 显示封面
     */
    @JSONField(name = "show_cover")
    private Integer showCover;
    /**
     * 封面图片的URL
     */
    @JSONField(name = "cover_url")
    private String coverUrl;
    /**
     * 正文的URL
     */
    @JSONField(name = "content_url")
    private String contentUrl;
    /**
     * 原文的URL，若置空则无查看原文入口
     */
    @JSONField(name = "source_url")
    private String sourceUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getShowCover() {
        return showCover;
    }

    public void setShowCover(Integer showCover) {
        this.showCover = showCover;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }
}
