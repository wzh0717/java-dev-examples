package com.wangzh.app.weixin.pa.dao.system;

import com.wangzh.app.weixin.pa.model.entity.UserInfoEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

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

    /**
     * 获取用户信息
     *
     * @param params 参数
     * @return
     */
    UserInfoEntity getUserInfo(Map<String, Object> params);
}
