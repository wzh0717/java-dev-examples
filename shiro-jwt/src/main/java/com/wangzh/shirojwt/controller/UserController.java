package com.wangzh.shirojwt.controller;

import com.wangzh.app.commons.result.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Auther:wangzh
 * @Date: 2019/05/29 15:02
 */

@RestController
@RequestMapping("/user")
public class UserController extends AbstractController {

    @GetMapping(value = "/info")
    public R getUserInfo(@RequestParam(name = "userID") Integer userID) {
        logger.info("[getUserInfo] 参数：userID ->{}", userID);
        return R.ok("success");
    }
}
