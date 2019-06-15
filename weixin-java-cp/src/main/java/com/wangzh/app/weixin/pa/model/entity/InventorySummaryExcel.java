package com.wangzh.app.weixin.pa.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @Auther:wangzh
 * @Date: 2019/06/15 16:18
 */

public class InventorySummaryExcel implements Serializable {
    private String drugType;
    private String typeName;
    private List<InventorySummaryEntity> list;
    /**
     * 合计金额
     */
    private double summaryMoney;

    public String getDrugType() {
        return drugType;
    }

    public void setDrugType(String drugType) {
        this.drugType = drugType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<InventorySummaryEntity> getList() {
        return list;
    }

    public void setList(List<InventorySummaryEntity> list) {
        this.list = list;
    }

    public double getSummaryMoney() {
        return summaryMoney;
    }

    public void setSummaryMoney(double summaryMoney) {
        this.summaryMoney = summaryMoney;
    }
}
