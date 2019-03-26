package com.wangzh.app.weixin.pa.service.system;

import com.wangzh.app.weixin.pa.model.entity.UserInfoEntity;

/**
 * @Description:
 * @CreatedDate:2019-03-26 10:23
 * @Author:wangzh
 */
public interface UserInfoService {

    /**
     * 主键获取用户
     *
     * @param userID
     * @return
     */
    UserInfoEntity getUserInfoByID(Integer userID);
}
