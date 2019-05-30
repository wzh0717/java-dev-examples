package com.wangzh.shirojwt.model.entity;


import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @Auther:wangzh
 * @Date: 2019/05/29 16:58
 */

public class SysRoleEntity implements Serializable {
    private static final long serialVersionUID = 2290838943214753638L;
    private long roleID;
    private String roleName;

    private List<SysPermissionEntity> permissions;

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


    public List<SysPermissionEntity> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<SysPermissionEntity> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "SysRoleEntity{" +
                "roleID=" + roleID +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
