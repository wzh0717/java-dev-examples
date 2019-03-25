package com.wangzh.app.weixin.pa.model.entity;

import java.io.Serializable;

/**
 * @Description:
 * @CreatedDate:2019-03-25 15:54
 * @Author:wangzh
 */
public class UserInfoEntity implements Serializable {
    private static final long serialVersionUID = 6503681633017457051L;
    private Integer userID;
    private String userName;
    private String userPwd;

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
}
