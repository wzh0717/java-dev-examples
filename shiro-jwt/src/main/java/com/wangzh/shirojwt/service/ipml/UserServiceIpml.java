package com.wangzh.shirojwt.service.ipml;

import com.wangzh.shirojwt.dao.UserDAO;
import com.wangzh.shirojwt.model.entity.SysUserEntity;
import com.wangzh.shirojwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Auther:wangzh
 * @Date: 2019/05/29 17:21
 */

public class UserServiceIpml implements UserService {

    @Autowired
    private UserDAO userDAO;

    /**
     * get userName
     *
     * @param userName
     * @return
     */
    @Override
    public SysUserEntity getUserByName(String userName) {
        Map<String, Object> params = new HashMap<>();
        params.put("userName", userName);
        return userDAO.getUserInfo(params);
    }
}
