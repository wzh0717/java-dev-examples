package com.wangzh.weixin.pa.model.menu;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 菜单
 * @Auther:wangzh
 * @Date: 2019/03/24 17:15
 */

public class MenuInfoDTO implements Serializable {

    private static final long serialVersionUID = -3956700550580667270L;
    @JSONField(name = "button")
    private List<ButtonDTO> buttons;

    public List<ButtonDTO> getButtons() {
        return buttons;
    }

    public void setButtons(List<ButtonDTO> buttons) {
        this.buttons = buttons;
    }
}
