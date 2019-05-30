package com.wangzh.shirojwt.model;

import java.io.Serializable;

/**
 * @Description:
 * @Auther:wangzh
 * @Date: 2019/05/29 17:11
 */

public class LoginModel implements Serializable {

    private String userName;
    private String password;

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
}
