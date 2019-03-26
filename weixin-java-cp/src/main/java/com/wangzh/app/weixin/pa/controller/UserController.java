package com.wangzh.app.weixin.pa.controller;

import com.wangzh.app.commons.result.R;
import com.wangzh.app.weixin.pa.model.entity.UserInfoEntity;
import com.wangzh.app.weixin.pa.service.system.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @CreatedDate:2019-03-26 10:15
 * @Author:wangzh
 */
@RestController
@RequestMapping("/user")
public class UserController extends AbstractController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 获取用户基础信息
     *
     * @param userID
     * @return
     */
    @GetMapping(value = "/info", produces = "application/json;charset=UTF-8")
    public R getUserInfo(@RequestParam(name = "userID") Integer userID) {
        logger.info("[getUserInfo] 参数：userID ->{}", userID);
        UserInfoEntity userInfo = userInfoService.getUserInfoByID(userID);
        return R.ok("success").put("data", userInfo);
    }
}
