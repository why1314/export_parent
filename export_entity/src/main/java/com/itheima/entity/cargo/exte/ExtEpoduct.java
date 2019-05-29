package com.itheima.entity.cargo.exte;

import java.io.Serializable;
import java.math.BigDecimal;

public class ExtEpoduct implements Serializable {
    
    private String id;

    
    private String exportProductId;

    
    private String exportId;

    
    private String factoryId;

    
    private String productNo;

    
    private String productImage;

    
    private String productDesc;

    
    private Integer cnumber;

    
    private String packingUnit;

    
    private Double price;

    
    private Double amount;

    
    private Integer orderNo;

    
    private String companyId;

    
    private String companyName;

    
    private String productRequest;

    
    public String getId() {
        return id;
    }

    
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    
    public String getExportProductId() {
        return exportProductId;
    }

    
    public void setExportProductId(String exportProductId) {
        this.exportProductId = exportProductId == null ? null : exportProductId.trim();
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

    
    public String getProductNo() {
        return productNo;
    }

    
    public void setProductNo(String productNo) {
        this.productNo = productNo == null ? null : productNo.trim();
    }

    
    public String getProductImage() {
        return productImage;
    }

    
    public void setProductImage(String productImage) {
        this.productImage = productImage == null ? null : productImage.trim();
    }

    
    public String getProductDesc() {
        return productDesc;
    }

    
    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc == null ? null : productDesc.trim();
    }

    
    public Integer getCnumber() {
        return cnumber;
    }

    
    public void setCnumber(Integer cnumber) {
        this.cnumber = cnumber;
    }

    
    public String getPackingUnit() {
        return packingUnit;
    }

    
    public void setPackingUnit(String packingUnit) {
        this.packingUnit = packingUnit == null ? null : packingUnit.trim();
    }

    
    public Double getPrice() {
        return price;
    }

    
    public void setPrice(Double price) {
        this.price = price;
    }

    
    public Double getAmount() {
        return amount;
    }

    
    public void setAmount(Double amount) {
        this.amount = amount;
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

    
    public String getProductRequest() {
        return productRequest;
    }

    
    public void setProductRequest(String productRequest) {
        this.productRequest = productRequest == null ? null : productRequest.trim();
    }
}