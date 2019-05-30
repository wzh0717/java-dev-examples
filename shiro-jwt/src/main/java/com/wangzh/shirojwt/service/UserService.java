package com.wangzh.shirojwt.service;

import com.wangzh.shirojwt.model.entity.SysUserEntity;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Auther:wangzh
 * @Date: 2019/05/29 17:18
 */

public interface UserService {

    /**
     * get userName
     * @param userName
     * @return
     */
    SysUserEntity getUserByName(String userName);

    /**
     * getUserAllByName
     * @param userName
     * @return
     */
    SysUserEntity getUserAllByName(String userName);
}
