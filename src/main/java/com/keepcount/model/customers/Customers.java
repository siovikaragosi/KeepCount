package com.keepcount.model.customers;

import java.math.BigDecimal;

public class Customers {

	private BigDecimal id;
	private String customerName;
	private String customerPhoneNumber;
	private String customerEmail;
	private String customerLocation;

	public Customers() {

	}

	public Customers(BigDecimal id, String customerName, String customerPhoneNumber, String customerEmail,
			String customerLocation) {
		super();
		this.id = id;
		this.customerName = customerName;
		this.customerPhoneNumber = customerPhoneNumber;
		this.customerEmail = customerEmail;
		this.customerLocation = customerLocation;
	}

	public Customers(String customerName, String customerPhoneNumber, String customerEmail, String customerLocation) {
		super();
		this.customerName = customerName;
		this.customerPhoneNumber = customerPhoneNumber;
		this.customerEmail = customerEmail;
		this.customerLocation = customerLocation;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPhoneNumber() {
		return customerPhoneNumber;
	}

	public void setCustomerPhoneNumber(String customerPhoneNumber) {
		this.customerPhoneNumber = customerPhoneNumber;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerLocation() {
		return customerLocation;
	}

	public void setCustomerLocation(String customerLocation) {
		this.customerLocation = customerLocation;
	}

	@Override
	public String toString() {
		return "Customers [id=" + id + ", customerName=" + customerName + ", customerPhoneNumber=" + customerPhoneNumber
				+ ", customerEmail=" + customerEmail + ", customerLocation=" + customerLocation + "]";
	}

}
