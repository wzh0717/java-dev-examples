package com.wangzh.weixin.pa.model.result;

import java.io.Serializable;

/**
 * @Description:
 * @Auther:wangzh
 * @Date: 2019/03/24 16:27
 */

public class MediaUploadDTO implements Serializable {

    private static final long serialVersionUID = -7180554443143722790L;
    private String url;
    private String type;
    private String mediaId;
    private String thumbMediaId;
    private long createdAt;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
