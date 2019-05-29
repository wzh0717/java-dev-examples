package com.wangzh.shirojwt.model.entity;

import java.io.Serializable;

/**
 * @Description:
 * @Auther:wangzh
 * @Date: 2019/05/29 16:58
 */

public class SysRoleEntity implements Serializable {
    private static final long serialVersionUID = 2290838943214753638L;
    private long roleID;
    private String roleName;

    public long getRoleID() {
        return roleID;
    }

    public void setRoleID(long roleID) {
        this.roleID = roleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "SysRoleEntity{" +
                "roleID=" + roleID +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
