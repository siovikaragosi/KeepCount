package com.keepcount.model.business.create;

import java.math.BigDecimal;

public class CreateBusiness {

	private BigDecimal id;
	private String email;
	private String type;
	private String businessName;

	public CreateBusiness() {

	}

	public CreateBusiness(BigDecimal id, String email, String type, String businessName) {
		this.id = id;
		this.email = email;
		this.type = type;
		this.businessName = businessName;
	}

	public CreateBusiness(String email, String type, String businessName) {
		super();
		this.email = email;
		this.type = type;
		this.businessName = businessName;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	@Override
	public String toString() {
		return "CreateBusiness [id=" + id + ", email=" + email + ", type=" + type + ", businessName=" + businessName
				+ "]";
	}

}
