package com.wangzh.app.weixin.pa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description:
 * @Auther:wangzh
 * @Date: 2019/06/15 17:48
 */

@Controller
public class WebController {

    @RequestMapping("/index")
    public String webIndex() {
        return "index";
    }
}
