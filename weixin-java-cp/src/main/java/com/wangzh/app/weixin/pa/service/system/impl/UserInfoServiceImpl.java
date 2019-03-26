package com.wangzh.app.weixin.pa.service.system.impl;

import com.wangzh.app.weixin.pa.dao.system.UserInfoDAO;
import com.wangzh.app.weixin.pa.model.entity.UserInfoEntity;
import com.wangzh.app.weixin.pa.service.impl.AbstractService;
import com.wangzh.app.weixin.pa.service.system.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @CreatedDate:2019-03-26 10:23
 * @Author:wangzh
 */
@Service
public class UserInfoServiceImpl extends AbstractService implements UserInfoService {

    @Autowired
    private UserInfoDAO userInfoDAO;

    /**
     * 主键获取用户
     *
     * @param userID
     * @return
     */
    @Override
    public UserInfoEntity getUserInfoByID(Integer userID) {
        Map<String, Object> params = new HashMap<>();
        params.put("userID", userID);
        return userInfoDAO.getUserInfo(params);
    }
}
