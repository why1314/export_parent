package com.itheima.entity.cargo.export;

import com.itheima.entity.cargo.eproduct.ExportProduct;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Export implements Serializable {

    private String id;
    private Date inputDate;
    private String contractIds;
    private String customerContract;
    private String lcno;
    private String consignee;
    private String marks;
    private String shipmentPort;
    private String destinationPort;
    private String transportMode;
    private String priceCondition;
    private String remark;
    private Integer boxNums;
    private Double grossWeights;
    private Double measurements;
    private Integer state;
    private Integer proNum;
    private Integer extNum;
    private String createBy;
    private String createDept;
    private Date createTime;
    private String companyId;
    private String companyName;
    private List<ExportProduct> exportProducts;

    public List<ExportProduct> getExportProducts() {
        return exportProducts;
    }

    public void setExportProducts(List<ExportProduct> exportProducts) {
        this.exportProducts = exportProducts;
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }


    public Date getInputDate() {
        return inputDate;
    }


    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }


    public String getContractIds() {
        return contractIds;
    }


    public void setContractIds(String contractIds) {
        this.contractIds = contractIds == null ? null : contractIds.trim();
    }


    public String getCustomerContract() {
        return customerContract;
    }


    public void setCustomerContract(String customerContract) {
        this.customerContract = customerContract == null ? null : customerContract.trim();
    }


    public String getLcno() {
        return lcno;
    }


    public void setLcno(String lcno) {
        this.lcno = lcno == null ? null : lcno.trim();
    }


    public String getConsignee() {
        return consignee;
    }


    public void setConsignee(String consignee) {
        this.consignee = consignee == null ? null : consignee.trim();
    }


    public String getMarks() {
        return marks;
    }


    public void setMarks(String marks) {
        this.marks = marks == null ? null : marks.trim();
    }


    public String getShipmentPort() {
        return shipmentPort;
    }


    public void setShipmentPort(String shipmentPort) {
        this.shipmentPort = shipmentPort == null ? null : shipmentPort.trim();
    }


    public String getDestinationPort() {
        return destinationPort;
    }


    public void setDestinationPort(String destinationPort) {
        this.destinationPort = destinationPort == null ? null : destinationPort.trim();
    }


    public String getTransportMode() {
        return transportMode;
    }


    public void setTransportMode(String transportMode) {
        this.transportMode = transportMode == null ? null : transportMode.trim();
    }


    public String getPriceCondition() {
        return priceCondition;
    }


    public void setPriceCondition(String priceCondition) {
        this.priceCondition = priceCondition == null ? null : priceCondition.trim();
    }


    public String getRemark() {
        return remark;
    }


    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }


    public Integer getBoxNums() {
        return boxNums;
    }


    public void setBoxNums(Integer boxNums) {
        this.boxNums = boxNums;
    }


    public Double getGrossWeights() {
        return grossWeights;
    }


    public void setGrossWeights(Double grossWeights) {
        this.grossWeights = grossWeights;
    }


    public Double getMeasurements() {
        return measurements;
    }


    public void setMeasurements(Double measurements) {
        this.measurements = measurements;
    }


    public Integer getState() {
        return state;
    }


    public void setState(Integer state) {
        this.state = state;
    }


    public Integer getProNum() {
        return proNum;
    }


    public void setProNum(Integer proNum) {
        this.proNum = proNum;
    }


    public Integer getExtNum() {
        return extNum;
    }


    public void setExtNum(Integer extNum) {
        this.extNum = extNum;
    }


    public String getCreateBy() {
        return createBy;
    }


    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }


    public String getCreateDept() {
        return createDept;
    }


    public void setCreateDept(String createDept) {
        this.createDept = createDept == null ? null : createDept.trim();
    }


    public Date getCreateTime() {
        return createTime;
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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