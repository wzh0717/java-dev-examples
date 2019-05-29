package com.wangzh.app.weixin.pa.model.entity;

import java.io.Serializable;

/**
 * @Description: 辅助类
 * @Auther:wangzh
 * @Date: 2019/05/23 14:34
 */

public class SystemInfoEntity implements Serializable {
    private static final long serialVersionUID = 2842760415393989279L;
    private String departmentID;
    private String departmentName;

    public String getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public String toString() {
        return "SystemInfoEntity{" +
                "departmentID='" + departmentID + '\'' +
                ", departmentName='" + departmentName + '\'' +
                '}';
    }
}
