package com.wangzh.shirojwt.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @Auther:wangzh
 * @Date: 2019/05/29 16:57
 */

public class SysUserEntity implements Serializable {

    private static final long serialVersionUID = 8758660522862269381L;
    /**
     * Key
     */
    private Long userID;
    private String userName;
    private String password;
    /**
     * 加密掩码
     */
    private String salt;

    private List<SysRoleEntity> roles;

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }


    public List<SysRoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRoleEntity> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "SysUserEntity{" +
                "userID=" + userID +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }
}
