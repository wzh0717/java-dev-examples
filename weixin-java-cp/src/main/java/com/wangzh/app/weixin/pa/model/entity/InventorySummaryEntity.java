package com.wangzh.app.weixin.pa.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 * @Auther:wangzh
 * @Date: 2019/06/15 16:15
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InventorySummaryEntity implements Serializable {
    private static final long serialVersionUID = 5537500032339776014L;
    /**
     * PK
     */
    private String summaryID;
    /**
     * 数据来源
     */
    private String billSource;
    /**
     * 科室
     */
    private String storageDeptID;
    /**
     * 货位号
     */
    private String storageSectionNo;
    /**
     * 库存批次
     */
    private double storageSerialID;
    /**
     * 药品编码
     */
    private String drugID;
    /**
     * 药品名称
     */
    private String drugName;
    /**
     * 规格型号
     */
    private String drugSpecs;
    /**
     * 药品类型
     */
    private String drugType;
    /**
     * 药品性质
     */
    private String drugQuality;

    /**
     * 最小包装单位
     */
    private String drugBulkUnit;
    /**
     * 包装单位
     */
    private String drugPackageUnit;
    /**
     * 包装含量
     */
    private Integer drugPackageQTY;
    /**
     * 效期
     */
    private Date drugValidDate;
    /**
     * 药库零售价
     */
    private double drugRetailPrice;
    /**
     * 药库进货价
     */
    private double drugPurchasePrice;

    /**
     * 药房零售价
     */
    private double storageRetailPrice;
    /**
     * 药房进货价
     */
    private double storagePurchasePrice;
    /**
     * 生产商
     */
    private String supplyProducerID;
    /**
     * 供应商
     */
    private String supplyCompanyID;
    /**
     * 批准文号
     */
    private String supplyBatchNO;
    /**
     * 库存数
     */
    private double storageQTY;
    /**
     * 盘点数
     */
    private double inventoryQTY;
    /**
     * 盈亏数
     */
    private double lossQTY;
    /**
     * 盈亏金额
     */
    private double lossAmountMoney;
    private String createdID;
    private Date createdTime;
    private String remark;

    /**
     * 药品类型
     */
    private String drugTypeName;

    /**
     * 零售金额
     */
    private double storageRetailMoney;

    /**
     * 进货金额
     */
    private double storagePurchaseMoney;

    /**
     * 进销差额
     */
    private double storageDiversityMoney;

    public String getSummaryID() {
        return summaryID;
    }

    public void setSummaryID(String summaryID) {
        this.summaryID = summaryID;
    }

    public String getBillSource() {
        return billSource;
    }

    public void setBillSource(String billSource) {
        this.billSource = billSource;
    }

    public String getStorageDeptID() {
        return storageDeptID;
    }

    public void setStorageDeptID(String storageDeptID) {
        this.storageDeptID = storageDeptID;
    }

    public String getStorageSectionNo() {
        return storageSectionNo;
    }

    public void setStorageSectionNo(String storageSectionNo) {
        this.storageSectionNo = storageSectionNo;
    }

    public double getStorageSerialID() {
        return storageSerialID;
    }

    public void setStorageSerialID(double storageSerialID) {
        this.storageSerialID = storageSerialID;
    }

    public String getDrugID() {
        return drugID;
    }

    public void setDrugID(String drugID) {
        this.drugID = drugID;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDrugSpecs() {
        return drugSpecs;
    }

    public void setDrugSpecs(String drugSpecs) {
        this.drugSpecs = drugSpecs;
    }

    public String getDrugType() {
        return drugType;
    }

    public void setDrugType(String drugType) {
        this.drugType = drugType;
    }

    public String getDrugQuality() {
        return drugQuality;
    }

    public void setDrugQuality(String drugQuality) {
        this.drugQuality = drugQuality;
    }

    public String getDrugBulkUnit() {
        return drugBulkUnit;
    }

    public void setDrugBulkUnit(String drugBulkUnit) {
        this.drugBulkUnit = drugBulkUnit;
    }

    public String getDrugPackageUnit() {
        return drugPackageUnit;
    }

    public void setDrugPackageUnit(String drugPackageUnit) {
        this.drugPackageUnit = drugPackageUnit;
    }

    public Integer getDrugPackageQTY() {
        return drugPackageQTY;
    }

    public void setDrugPackageQTY(Integer drugPackageQTY) {
        this.drugPackageQTY = drugPackageQTY;
    }

    public Date getDrugValidDate() {
        return drugValidDate;
    }

    public void setDrugValidDate(Date drugValidDate) {
        this.drugValidDate = drugValidDate;
    }

    public double getDrugRetailPrice() {
        return drugRetailPrice;
    }

    public void setDrugRetailPrice(double drugRetailPrice) {
        this.drugRetailPrice = drugRetailPrice;
    }

    public double getDrugPurchasePrice() {
        return drugPurchasePrice;
    }

    public void setDrugPurchasePrice(double drugPurchasePrice) {
        this.drugPurchasePrice = drugPurchasePrice;
    }

    public double getStorageRetailPrice() {
        return storageRetailPrice;
    }

    public void setStorageRetailPrice(double storageRetailPrice) {
        this.storageRetailPrice = storageRetailPrice;
    }

    public double getStoragePurchasePrice() {
        return storagePurchasePrice;
    }

    public void setStoragePurchasePrice(double storagePurchasePrice) {
        this.storagePurchasePrice = storagePurchasePrice;
    }

    public String getSupplyProducerID() {
        return supplyProducerID;
    }

    public void setSupplyProducerID(String supplyProducerID) {
        this.supplyProducerID = supplyProducerID;
    }

    public String getSupplyCompanyID() {
        return supplyCompanyID;
    }

    public void setSupplyCompanyID(String supplyCompanyID) {
        this.supplyCompanyID = supplyCompanyID;
    }

    public String getSupplyBatchNO() {
        return supplyBatchNO;
    }

    public void setSupplyBatchNO(String supplyBatchNO) {
        this.supplyBatchNO = supplyBatchNO;
    }

    public double getStorageQTY() {
        return storageQTY;
    }

    public void setStorageQTY(double storageQTY) {
        this.storageQTY = storageQTY;
    }

    public double getInventoryQTY() {
        return inventoryQTY;
    }

    public void setInventoryQTY(double inventoryQTY) {
        this.inventoryQTY = inventoryQTY;
    }

    public double getLossQTY() {
        return lossQTY;
    }

    public void setLossQTY(double lossQTY) {
        this.lossQTY = lossQTY;
    }

    public double getLossAmountMoney() {
        return lossAmountMoney;
    }

    public void setLossAmountMoney(double lossAmountMoney) {
        this.lossAmountMoney = lossAmountMoney;
    }

    public String getCreatedID() {
        return createdID;
    }

    public void setCreatedID(String createdID) {
        this.createdID = createdID;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDrugTypeName() {
        return drugTypeName;
    }

    public void setDrugTypeName(String drugTypeName) {
        this.drugTypeName = drugTypeName;
    }

    public double getStorageRetailMoney() {
        return storageRetailMoney;
    }

    public void setStorageRetailMoney(double storageRetailMoney) {
        this.storageRetailMoney = storageRetailMoney;
    }

    public double getStoragePurchaseMoney() {
        return storagePurchaseMoney;
    }

    public void setStoragePurchaseMoney(double storagePurchaseMoney) {
        this.storagePurchaseMoney = storagePurchaseMoney;
    }

    public double getStorageDiversityMoney() {
        return storageDiversityMoney;
    }

    public void setStorageDiversityMoney(double storageDiversityMoney) {
        this.storageDiversityMoney = storageDiversityMoney;
    }
}
