package com.keepcount.controller.business.choose;

public class DetailsOfLogIn {

	private String nameOfTheBusiness;
	private String emailOfThebusiness;
	private String emailOfTheUser;

	public DetailsOfLogIn() {
		// TODO Auto-generated constructor stub
	}

	public DetailsOfLogIn(String nameOfTheBusiness, String emailOfThebusiness, String emailOfTheUser) {
		super();
		this.nameOfTheBusiness = nameOfTheBusiness;
		this.emailOfThebusiness = emailOfThebusiness;
		this.emailOfTheUser = emailOfTheUser;
	}

	public String getNameOfTheBusiness() {
		return nameOfTheBusiness;
	}

	public void setNameOfTheBusiness(String nameOfTheBusiness) {
		this.nameOfTheBusiness = nameOfTheBusiness;
	}

	public String getEmailOfThebusiness() {
		return emailOfThebusiness;
	}

	public void setEmailOfThebusiness(String emailOfThebusiness) {
		this.emailOfThebusiness = emailOfThebusiness;
	}

	public String getEmailOfTheUser() {
		return emailOfTheUser;
	}

	public void setEmailOfTheUser(String emailOfTheUser) {
		this.emailOfTheUser = emailOfTheUser;
	}

	@Override
	public String toString() {
		return "DetailsOfLogIn [nameOfTheBusiness=" + nameOfTheBusiness + ", emailOfThebusiness=" + emailOfThebusiness
				+ ", emailOfTheUser=" + emailOfTheUser + "]";
	}

}
