package com.wangzh.weixin.pa.model.enums.menu;

/**
 * 按钮类型
 */
public enum ButtonType {

    click(1),
    view(2),
    /**
     * 扫描推事件
     */
    scancode_push(3),
    /**
     * 扫码带提示
     */
    scancode_waitmsg(4),
    /**
     * 系统拍照
     */
    pic_sysphoto(5),
    /**
     * 拍照或者相册发图
     */
    pic_photo_or_album(6),
    /**
     * 微信相册发图
     */
    pic_weixin(7),
    /**
     * 地理位置
     */
    location_select(8),
    /**
     * 多媒体
     */
    media_id(9),
    view_limited(10);
    private int code;

    ButtonType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
