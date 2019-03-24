package com.wangzh.weixin.pa.model.menu;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 图文消息
 * @Auther:wangzh
 * @Date: 2019/03/24 16:51
 */

public class NewsInfoDTO implements Serializable {

    private static final long serialVersionUID = 789504588350564702L;
    @JSONField(name = "list")
    private List<NewsInButtonDTO> list = new ArrayList<>();


    public List<NewsInButtonDTO> getList() {
        return list;
    }

    public void setList(List<NewsInButtonDTO> list) {
        this.list = list;
    }
}
