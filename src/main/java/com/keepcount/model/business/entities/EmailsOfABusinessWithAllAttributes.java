
package com.keepcount.model.business.entities ;

import java.math.BigDecimal ;

public class EmailsOfABusinessWithAllAttributes {

	private BigDecimal id ;
	/* 1 */private String emailOfABusinessMember ;
	/* 2 */private String passwordOfABusinessMember ;
	/* 3 */private String businessNameAMemberBelongsTo ;
	/* 4 */private String userNameOfABusinessMember ;
	/* 5 */private BigDecimal idOfThisBusiness ;
	/* 6 */private String phoneNumberOfABusinessMember ;

	public EmailsOfABusinessWithAllAttributes() {

		// TODO Auto-generated constructor stub
	}

	public EmailsOfABusinessWithAllAttributes( BigDecimal id , String emailOfABusinessMember , String passwordOfABusinessMember , String businessNameAMemberBelongsTo ,
			String userNameOfABusinessMember , BigDecimal idOfThisBusiness , String phoneNumberOfABusinessMember ) {

		super() ;
		this.id = id ;
		this.emailOfABusinessMember = emailOfABusinessMember ;
		this.passwordOfABusinessMember = passwordOfABusinessMember ;
		this.businessNameAMemberBelongsTo = businessNameAMemberBelongsTo ;
		this.userNameOfABusinessMember = userNameOfABusinessMember ;
		this.idOfThisBusiness = idOfThisBusiness ;
		this.phoneNumberOfABusinessMember = phoneNumberOfABusinessMember ;
	}

	public BigDecimal getId() {

		return id ;
	}

	public void setId( BigDecimal id ) {

		this.id = id ;
	}

	public String getEmailOfABusinessMember() {

		return emailOfABusinessMember ;
	}

	public void setEmailOfABusinessMember( String emailOfABusinessMember ) {

		this.emailOfABusinessMember = emailOfABusinessMember ;
	}

	public String getPasswordOfABusinessMember() {

		return passwordOfABusinessMember ;
	}

	public void setPasswordOfABusinessMember( String passwordOfABusinessMember ) {

		this.passwordOfABusinessMember = passwordOfABusinessMember ;
	}

	public String getBusinessNameAMemberBelongsTo() {

		return businessNameAMemberBelongsTo ;
	}

	public void setBusinessNameAMemberBelongsTo( String businessNameAMemberBelongsTo ) {

		this.businessNameAMemberBelongsTo = businessNameAMemberBelongsTo ;
	}

	public String getUserNameOfABusinessMember() {

		return userNameOfABusinessMember ;
	}

	public void setUserNameOfABusinessMember( String userNameOfABusinessMember ) {

		this.userNameOfABusinessMember = userNameOfABusinessMember ;
	}

	public BigDecimal getIdOfThisBusiness() {

		return idOfThisBusiness ;
	}

	public void setIdOfThisBusiness( BigDecimal idOfThisBusiness ) {

		this.idOfThisBusiness = idOfThisBusiness ;
	}

	public String getPhoneNumberOfABusinessMember() {

		return phoneNumberOfABusinessMember ;
	}

	public void setPhoneNumberOfABusinessMember( String phoneNumberOfABusinessMember ) {

		this.phoneNumberOfABusinessMember = phoneNumberOfABusinessMember ;
	}

	@Override
	public String toString() {

		return "EmailsOfABusinessWithAllAttributes [id=" + id + ", emailOfABusinessMember=" + emailOfABusinessMember + ", passwordOfABusinessMember=" + passwordOfABusinessMember
				+ ", businessNameAMemberBelongsTo=" + businessNameAMemberBelongsTo + ", userNameOfABusinessMember=" + userNameOfABusinessMember + ", idOfThisBusiness=" + idOfThisBusiness
				+ ", phoneNumberOfABusinessMember=" + phoneNumberOfABusinessMember + "]" ;
	}

}
