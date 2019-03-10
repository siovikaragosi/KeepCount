package com.keepcount.model.business.choose;

import java.math.BigDecimal;

public class ChooseBusiness {

	private BigDecimal id;
	private String businessName;

	public ChooseBusiness() {

	}

	public ChooseBusiness(BigDecimal id, String businessName) {
		super();
		this.id = id;
		this.businessName = businessName;
	}

	public ChooseBusiness(String businessName) {
		super();
		this.businessName = businessName;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	@Override
	public String toString() {
		return "ChooseBusiness [id=" + id + ", businessName=" + businessName + "]";
	}

}
