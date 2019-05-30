package com.wangzh.shirojwt.controller;

import com.alibaba.fastjson.JSONObject;
import com.wangzh.app.commons.result.R;
import com.wangzh.shirojwt.model.LoginModel;
import com.wangzh.shirojwt.model.entity.SysUserEntity;
import com.wangzh.shirojwt.service.UserService;
import com.wangzh.shirojwt.shiro.JWTUtils;
import com.wangzh.shirojwt.shiro.ShiroUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Auther:wangzh
 * @Date: 2019/05/29 15:02
 */

@RestController
@RequestMapping("/user")
public class UserController extends AbstractController {

    @Autowired
    private UserService userService;

    /**
     * 注册
     *
     * @param model
     * @return
     */
    @PostMapping(value = "/register")
    public R register(@RequestBody LoginModel model) {
        SysUserEntity userEntity = new SysUserEntity();
        //掩码
        userEntity.setSalt(ShiroUtils.getRandomSalt(2));
        //MD5
        userEntity.setPassword(ShiroUtils.md5(model.getPassword(), model.getUserName().concat(userEntity.getSalt())));
        return R.ok("success");
    }

    /**
     * 用户登录
     *
     * @param model
     * @return
     */
    @PostMapping(value = "/login")
    public R login(@RequestBody LoginModel model) {
        logger.info("[login] 参数：{}", JSONObject.toJSONString(model));
        if (null == model) return R.error(-1, "参数为空");
        String userName = model.getUserName();
        String password = model.getPassword();
        if (StringUtils.isBlank(userName) || StringUtils.isBlank(password))
            return R.error(-2, "用户名或密码为空");

        SysUserEntity userEntity = userService.getUserByName(userName);
        if (null == userEntity) return R.error(-3, "用户不存在");
        //掩码
        String salt = userEntity.getSalt();
        //MD5
        String encodedPassword = ShiroUtils.md5(password, userName.concat(salt));
        if (!encodedPassword.equalsIgnoreCase(userEntity.getPassword()))
            return R.error(-4, "登录密码错误");
        String signStr = JWTUtils.signature(model.getUserName(), encodedPassword);
        return R.ok("success").put("token", signStr);
    }

    /**
     * 用户信息
     *
     * @param userID
     * @return
     */
    @GetMapping(value = "/info")
    @RequiresAuthentication
    public R getUserInfo(@RequestParam(name = "userID") Integer userID) {
        logger.info("[getUserInfo] 参数：userID ->{}", userID);
        return R.ok("success").put("data", "测试token");
    }
}
