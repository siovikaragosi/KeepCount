package com.keepcount.model.business.create;

import java.math.BigDecimal;

public class BusinessType {

	private BigDecimal id;
	private String bizType;

	public BusinessType() {

	}

	public BusinessType(String bizType) {
		this.bizType = bizType;
	}

	public BusinessType(BigDecimal id, String bizType) {
		this.id = id;
		this.bizType = bizType;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	@Override
	public String toString() {
		return "BusinessType [id=" + id + ", bizType=" + bizType + "]";
	}

}
