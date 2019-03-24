package com.wangzh.weixin.pa.model.menu;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 子菜单
 * @Auther:wangzh
 * @Date: 2019/03/24 17:11
 */

public class SubButtonsDTO implements Serializable {

    private static final long serialVersionUID = 8326335964601185614L;

    @JSONField(name = "list")
    private List<ButtonDTO> subButtons = new ArrayList<>();

    public List<ButtonDTO> getSubButtons() {
        return subButtons;
    }

    public void setSubButtons(List<ButtonDTO> subButtons) {
        this.subButtons = subButtons;
    }
}
