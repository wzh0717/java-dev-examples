package com.wangzh.app.weixin.pa.service;

import java.util.Map;

/**
 * @Description: access token
 * @Auther:wangzh
 * @Date: 2019/03/24 17:55
 */
public interface WxMpAccessService {

    /**
     * 获取access token
     *
     * @param url    链接
     * @param params
     * @return
     */
    String getAccessToken(String url, Map<String, Object> params) throws Exception;
}
