package com.keepcount.model.login;

import java.math.BigDecimal;

public class EmailsOfABusiness {

	private BigDecimal id;
	private String email;
	private String password;
	private String businessName;

	public EmailsOfABusiness() {

	}

	public EmailsOfABusiness(BigDecimal id, String email, String password, String businessName) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.businessName = businessName;
	}

	public EmailsOfABusiness(String email, String password, String businessName) {
		super();
		this.email = email;
		this.password = password;
		this.businessName = businessName;
	}

	public EmailsOfABusiness(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public EmailsOfABusiness(String email) {
		super();
		this.email = email;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	@Override
	public String toString() {
		return "EmailsOfABusiness [id=" + id + ", email=" + email + ", password=" + password + ", businessName="
				+ businessName + "]";
	}

}
