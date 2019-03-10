package com.keepcount.model.suppliers;

import java.math.BigDecimal;

public class Suppliers {

	private BigDecimal id;
	private String supplierName;
	private String supplierPhoneNumber;
	private String supplierEmail;
	private String supplierLocation;
	private String supplierWebsite;

	public Suppliers() {

	}

	public Suppliers(BigDecimal id, String supplierName, String supplierPhoneNumber, String supplierEmail,
			String supplierLocation, String supplierWebsite) {
		super();
		this.id = id;
		this.supplierName = supplierName;
		this.supplierPhoneNumber = supplierPhoneNumber;
		this.supplierEmail = supplierEmail;
		this.supplierLocation = supplierLocation;
		this.supplierWebsite = supplierWebsite;
	}

	public Suppliers(String supplierName, String supplierPhoneNumber, String supplierEmail, String supplierLocation,
			String supplierWebsite) {
		super();
		this.supplierName = supplierName;
		this.supplierPhoneNumber = supplierPhoneNumber;
		this.supplierEmail = supplierEmail;
		this.supplierLocation = supplierLocation;
		this.supplierWebsite = supplierWebsite;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierPhoneNumber() {
		return supplierPhoneNumber;
	}

	public void setSupplierPhoneNumber(String supplierPhoneNumber) {
		this.supplierPhoneNumber = supplierPhoneNumber;
	}

	public String getSupplierEmail() {
		return supplierEmail;
	}

	public void setSupplierEmail(String supplierEmail) {
		this.supplierEmail = supplierEmail;
	}

	public String getSupplierLocation() {
		return supplierLocation;
	}

	public void setSupplierLocation(String supplierLocation) {
		this.supplierLocation = supplierLocation;
	}

	public String getSupplierWebsite() {
		return supplierWebsite;
	}

	public void setSupplierWebsite(String supplierWebsite) {
		this.supplierWebsite = supplierWebsite;
	}

	@Override
	public String toString() {
		return "Suppliers [id=" + id + ", supplierName=" + supplierName + ", supplierPhoneNumber=" + supplierPhoneNumber
				+ ", supplierEmail=" + supplierEmail + ", supplierLocation=" + supplierLocation + ", supplierWebsite="
				+ supplierWebsite + "]";
	}

}
