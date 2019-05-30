package com.wangzh.shirojwt.service.ipml;

import com.wangzh.shirojwt.dao.UserDAO;
import com.wangzh.shirojwt.model.entity.SysPermissionEntity;
import com.wangzh.shirojwt.model.entity.SysRoleEntity;
import com.wangzh.shirojwt.model.entity.SysUserEntity;
import com.wangzh.shirojwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Auther:wangzh
 * @Date: 2019/05/29 17:21
 */
@Service
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
//        Map<String, Object> params = new HashMap<>();
//        params.put("userName", userName);
//        return userDAO.getUserInfo(params);
        SysUserEntity userEntity = new SysUserEntity();
        userEntity.setUserID(1L);
        userEntity.setUserName("admin");
        userEntity.setSalt("4fcd");
        userEntity.setPassword("5a54e24376dbdf6c80e10f484bcb1eef");
        return userEntity;
    }

    /**
     * getUserAllByName
     *
     * @param userName
     * @return
     */
    @Override
    public SysUserEntity getUserAllByName(String userName) {
        SysUserEntity userEntity = new SysUserEntity();
        SysRoleEntity roleEntity = new SysRoleEntity();
        roleEntity.setRoleID(1L);
        roleEntity.setRoleName("管理员");

        SysPermissionEntity permissionEntity = new SysPermissionEntity();
        permissionEntity.setPermID(1L);
        permissionEntity.setPermName("权限1");
        List<SysPermissionEntity> permissionList = new ArrayList<>();
        permissionList.add(permissionEntity);
        roleEntity.setPermissions(permissionList);

        List<SysRoleEntity> roleList = new ArrayList<>();
        roleList.add(roleEntity);
        userEntity.setRoles(roleList);
        userEntity.setUserID(1L);
        userEntity.setUserName("admin");
        userEntity.setSalt("4fcd");
        userEntity.setPassword("5a54e24376dbdf6c80e10f484bcb1eef");
        return userEntity;
    }
}
