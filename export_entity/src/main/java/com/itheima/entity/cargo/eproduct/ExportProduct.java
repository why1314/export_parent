package com.itheima.entity.cargo.eproduct;

import java.io.Serializable;


public class ExportProduct implements Serializable {
    
    private String id;
    private String exportId;
    private String factoryId;
    private String factoryName;
    private String productNo;
    private String packingUnit;
    private Integer cnumber;
    private Integer boxNum;
    private Double grossWeight;
    private Double netWeight;
    private Double sizeLength;
    private Double sizeWidth;
    private Double sizeHeight;
    private Double exPrice;
    private Double price;
    private Double tax;
    private Integer orderNo;
    private String companyId;
    private String companyName;

    
    public String getId() {
        return id;
    }

    
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    
    public String getExportId() {
        return exportId;
    }

    
    public void setExportId(String exportId) {
        this.exportId = exportId == null ? null : exportId.trim();
    }

    
    public String getFactoryId() {
        return factoryId;
    }

    
    public void setFactoryId(String factoryId) {
        this.factoryId = factoryId == null ? null : factoryId.trim();
    }

    
    public String getFactoryName() {
        return factoryName;
    }

    
    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName == null ? null : factoryName.trim();
    }

    
    public String getProductNo() {
        return productNo;
    }

    
    public void setProductNo(String productNo) {
        this.productNo = productNo == null ? null : productNo.trim();
    }

    
    public String getPackingUnit() {
        return packingUnit;
    }

    
    public void setPackingUnit(String packingUnit) {
        this.packingUnit = packingUnit == null ? null : packingUnit.trim();
    }

    
    public Integer getCnumber() {
        return cnumber;
    }

    
    public void setCnumber(Integer cnumber) {
        this.cnumber = cnumber;
    }

    
    public Integer getBoxNum() {
        return boxNum;
    }

    
    public void setBoxNum(Integer boxNum) {
        this.boxNum = boxNum;
    }

    
    public Double getGrossWeight() {
        return grossWeight;
    }

    
    public void setGrossWeight(Double grossWeight) {
        this.grossWeight = grossWeight;
    }

    
    public Double getNetWeight() {
        return netWeight;
    }

    
    public void setNetWeight(Double netWeight) {
        this.netWeight = netWeight;
    }

    
    public Double getSizeLength() {
        return sizeLength;
    }

    
    public void setSizeLength(Double sizeLength) {
        this.sizeLength = sizeLength;
    }

    
    public Double getSizeWidth() {
        return sizeWidth;
    }

    
    public void setSizeWidth(Double sizeWidth) {
        this.sizeWidth = sizeWidth;
    }

    
    public Double getSizeHeight() {
        return sizeHeight;
    }

    
    public void setSizeHeight(Double sizeHeight) {
        this.sizeHeight = sizeHeight;
    }

    
    public Double getExPrice() {
        return exPrice;
    }

    
    public void setExPrice(Double exPrice) {
        this.exPrice = exPrice;
    }

    
    public Double getPrice() {
        return price;
    }

    
    public void setPrice(Double price) {
        this.price = price;
    }

    
    public Double getTax() {
        return tax;
    }

    
    public void setTax(Double tax) {
        this.tax = tax;
    }

    
    public Integer getOrderNo() {
        return orderNo;
    }

    
    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    
    public String getCompanyId() {
        return companyId;
    }

    
    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    
    public String getCompanyName() {
        return companyName;
    }

    
    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }
}