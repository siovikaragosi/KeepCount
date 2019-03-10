
package com.keepcount.model.pos ;

import java.math.BigDecimal ;

public class EmailsPhoneNumbersAndNamesOfBusinesses {

	private BigDecimal id ;
	private String email ;
	private String password ;
	private String businessName ;
	private String userName ;
	private BigDecimal idOfTheBusiness ;
	private String phoneNumber ;

	public EmailsPhoneNumbersAndNamesOfBusinesses() {

	}

	public EmailsPhoneNumbersAndNamesOfBusinesses( BigDecimal id , String email , String password , String businessName , String userName , BigDecimal idOfTheBusiness ,
			String phoneNumber ) {
		super() ;
		this.id = id ;
		this.email = email ;
		this.password = password ;
		this.businessName = businessName ;
		this.userName = userName ;
		this.idOfTheBusiness = idOfTheBusiness ;
		this.phoneNumber = phoneNumber ;
	}

	public BigDecimal getId() {
		return id ;
	}

	public void setId( BigDecimal id ) {
		this.id = id ;
	}

	public String getEmail() {
		return email ;
	}

	public void setEmail( String email ) {
		this.email = email ;
	}

	public String getPassword() {
		return password ;
	}

	public void setPassword( String password ) {
		this.password = password ;
	}

	public String getBusinessName() {
		return businessName ;
	}

	public void setBusinessName( String businessName ) {
		this.businessName = businessName ;
	}

	public String getUserName() {
		return userName ;
	}

	public void setUserName( String userName ) {
		this.userName = userName ;
	}

	public BigDecimal getIdOfTheBusiness() {
		return idOfTheBusiness ;
	}

	public void setIdOfTheBusiness( BigDecimal idOfTheBusiness ) {
		this.idOfTheBusiness = idOfTheBusiness ;
	}

	public String getPhoneNumber() {
		return phoneNumber ;
	}

	public void setPhoneNumber( String phoneNumber ) {
		this.phoneNumber = phoneNumber ;
	}

	@Override
	public String toString() {
		return "EmailsPhoneNumbersAndNamesOfBusinesses [id=" + id + ", email=" + email + ", password=" + password + ", businessName=" + businessName + ", userName=" + userName
				+ ", idOfTheBusiness=" + idOfTheBusiness + ", phoneNumber=" + phoneNumber + "]" ;
	}

}
