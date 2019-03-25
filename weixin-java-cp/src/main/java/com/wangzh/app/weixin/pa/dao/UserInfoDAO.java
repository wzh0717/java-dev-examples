package com.wangzh.app.weixin.pa.dao;

import com.wangzh.app.weixin.pa.model.entity.UserInfoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description:
 * @CreatedDate:2019-03-25 15:52
 * @Author:wangzh
 */
@Mapper
public interface UserInfoDAO {
    /**
     * 用户新增
     *
     * @param entity
     * @return
     */
    int addUser(UserInfoEntity entity);
}
