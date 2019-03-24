package com.wangzh.app.weixin.pa.model.result;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 微信服务器IP地址
 * @Auther:wangzh
 * @Date: 2019/03/24 16:06
 */

public class CallbackIPDTO implements Serializable {
    private static final long serialVersionUID = -1089331606529329222L;

    /**
     * 微信服务器IP地址集合
     */
    private List<String> ipList;


    public List<String> getIpList() {
        return ipList;
    }

    public void setIpList(List<String> ipList) {
        this.ipList = ipList;
    }
}
