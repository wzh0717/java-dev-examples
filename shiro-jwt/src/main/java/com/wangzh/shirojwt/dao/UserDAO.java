package com.wangzh.shirojwt.dao;

import com.wangzh.shirojwt.model.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @Description:
 * @Auther:wangzh
 * @Date: 2019/05/29 17:24
 */
@Mapper
public interface UserDAO {

    /**
     * getUserInfo
     * @param params
     * @return
     */
    SysUserEntity getUserInfo(Map<String,Object> params);
}
