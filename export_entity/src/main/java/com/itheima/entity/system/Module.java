package com.itheima.entity.system;

import java.io.Serializable;
import java.util.Objects;

/**
 * 详情
 *
 * @author wz
 * @date 2019-05-08-15:28
 */
public class Module implements Serializable {

    private String	id;
    private String	parentId;
    private String	parentName;
    private String	name;
    private Double	layerNum;
    private Double	isLeaf;
    private String	ico;
    private String	cpermission;
    private String	curl;
    private Double	ctype;
    private Double	state;
    private String	belong;
    private String	cwhich;
    private Double	quoteNum;
    private String	remark;
    private Double	orderNo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Module module = (Module) o;
        return Objects.equals(id, module.id) &&
                Objects.equals(parentId, module.parentId) &&
                Objects.equals(parentName, module.parentName) &&
                Objects.equals(name, module.name) &&
                Objects.equals(layerNum, module.layerNum) &&
                Objects.equals(isLeaf, module.isLeaf) &&
                Objects.equals(ico, module.ico) &&
                Objects.equals(cpermission, module.cpermission) &&
                Objects.equals(curl, module.curl) &&
                Objects.equals(ctype, module.ctype) &&
                Objects.equals(state, module.state) &&
                Objects.equals(belong, module.belong) &&
                Objects.equals(cwhich, module.cwhich) &&
                Objects.equals(quoteNum, module.quoteNum) &&
                Objects.equals(remark, module.remark) &&
                Objects.equals(orderNo, module.orderNo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, parentId, parentName, name, layerNum, isLeaf, ico, cpermission, curl, ctype, state, belong, cwhich, quoteNum, remark, orderNo);
    }

    @Override
    public String toString() {
        return "Module{" +
                "id='" + id + '\'' +
                ", parentId='" + parentId + '\'' +
                ", parentName='" + parentName + '\'' +
                ", name='" + name + '\'' +
                ", layerNum=" + layerNum +
                ", isLeaf=" + isLeaf +
                ", ico='" + ico + '\'' +
                ", cpermission='" + cpermission + '\'' +
                ", curl='" + curl + '\'' +
                ", ctype=" + ctype +
                ", state=" + state +
                ", belong='" + belong + '\'' +
                ", cwhich='" + cwhich + '\'' +
                ", quoteNum=" + quoteNum +
                ", remark='" + remark + '\'' +
                ", orderNo=" + orderNo +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLayerNum() {
        return layerNum;
    }

    public void setLayerNum(Double layerNum) {
        this.layerNum = layerNum;
    }

    public Double getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Double isLeaf) {
        this.isLeaf = isLeaf;
    }

    public String getIco() {
        return ico;
    }

    public void setIco(String ico) {
        this.ico = ico;
    }

    public String getCpermission() {
        return cpermission;
    }

    public void setCpermission(String cpermission) {
        this.cpermission = cpermission;
    }

    public String getCurl() {
        return curl;
    }

    public void setCurl(String curl) {
        this.curl = curl;
    }

    public Double getCtype() {
        return ctype;
    }

    public void setCtype(Double ctype) {
        this.ctype = ctype;
    }

    public Double getState() {
        return state;
    }

    public void setState(Double state) {
        this.state = state;
    }

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }

    public String getCwhich() {
        return cwhich;
    }

    public void setCwhich(String cwhich) {
        this.cwhich = cwhich;
    }

    public Double getQuoteNum() {
        return quoteNum;
    }

    public void setQuoteNum(Double quoteNum) {
        this.quoteNum = quoteNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Double orderNo) {
        this.orderNo = orderNo;
    }
}
