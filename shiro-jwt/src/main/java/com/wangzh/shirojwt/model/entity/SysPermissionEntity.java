package com.wangzh.shirojwt.model.entity;

import java.io.Serializable;

/**
 * @Description:
 * @Auther:wangzh
 * @Date: 2019/05/30 11:03
 */

public class SysPermissionEntity implements Serializable {
    private static final long serialVersionUID = 1193196979294233936L;
    private long permID;
    private String permName;
    private String description;

    public long getPermID() {
        return permID;
    }

    public void setPermID(long permID) {
        this.permID = permID;
    }

    public String getPermName() {
        return permName;
    }

    public void setPermName(String permName) {
        this.permName = permName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "SysPermissionEntity{" +
                "permID=" + permID +
                ", permName='" + permName + '\'' +
                '}';
    }
}
