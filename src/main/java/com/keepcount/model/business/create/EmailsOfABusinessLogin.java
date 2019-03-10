
package com.keepcount.model.business.create;

import java.math.BigDecimal;

public class EmailsOfABusinessLogin {

	private BigDecimal id;
	private String email;
	private String password;
	private String businessName;
	private String userName;
	private BigDecimal idBizType;
	private String phoneNumber;

	public EmailsOfABusinessLogin() {

	}

	public EmailsOfABusinessLogin( BigDecimal id , String email , String password , String businessName , String userName , BigDecimal idBizType ) {

		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.businessName = businessName;
		this.userName = userName;
		this.idBizType = idBizType;
	}

	public EmailsOfABusinessLogin( BigDecimal id , String email , String password , String businessName , String userName ) {

		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.businessName = businessName;
		this.userName = userName;
	}

	public EmailsOfABusinessLogin( String email , String password , String businessName , String userName ) {

		super();
		this.email = email;
		this.password = password;
		this.businessName = businessName;
		this.userName = userName;
	}

	public EmailsOfABusinessLogin( BigDecimal id , String email , String password , String businessName , String userName , BigDecimal idBizType , String phoneNumber ) {

		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.businessName = businessName;
		this.userName = userName;
		this.idBizType = idBizType;
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber() {

		return phoneNumber;
	}

	public void setPhoneNumber( String phoneNumber ) {

		this.phoneNumber = phoneNumber;
	}

	public BigDecimal getIdBizType() {

		return idBizType;
	}

	public void setIdBizType( BigDecimal idBizType ) {

		this.idBizType = idBizType;
	}

	public BigDecimal getId() {

		return id;
	}

	public void setId( BigDecimal id ) {

		this.id = id;
	}

	public String getEmail() {

		return email;
	}

	public void setEmail( String email ) {

		this.email = email;
	}

	public String getPassword() {

		return password;
	}

	public void setPassword( String password ) {

		this.password = password;
	}

	public String getBusinessName() {

		return businessName;
	}

	public void setBusinessName( String businessName ) {

		this.businessName = businessName;
	}

	public String getUserName() {

		return userName;
	}

	public void setUserName( String userName ) {

		this.userName = userName;
	}

	@Override
	public String toString() {

		return "EmailsOfABusinessLogin [id=" + id + ", email=" + email + ", password=" + password + ", businessName=" + businessName + ", userName=" + userName + ", idBizType=" + idBizType
												+ ", phoneNumber=" + phoneNumber + "]";
	}

}