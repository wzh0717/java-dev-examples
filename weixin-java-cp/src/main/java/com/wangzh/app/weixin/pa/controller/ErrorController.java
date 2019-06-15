package com.wangzh.app.weixin.pa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description:
 * @Auther:wangzh
 * @Date: 2019/06/15 19:03
 */

@Controller
public class ErrorController {
    @RequestMapping("/400")
    public String page400() {
        return "/400";
    }

    @RequestMapping("/404")
    public String page404() {
        return "/404";
    }

    @RequestMapping("/500")
    public String page500() {
        return "/500";
    }
}
