package com.itheima.vo;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name="products")
public class ExportProductResult implements Serializable {
	private String exportProductId; //商品id
	private Double tax;            //税
	
	public String getExportProductId() {
		return exportProductId;
	}
	public void setExportProductId(String exportProductId) {
		this.exportProductId = exportProductId;
	}
	public Double getTax() {
		return tax;
	}
	public void setTax(Double tax) {
		this.tax = tax;
	}
	
	
}
