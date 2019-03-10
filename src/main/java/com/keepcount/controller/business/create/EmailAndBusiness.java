package com.keepcount.controller.business.create;

public class EmailAndBusiness {

	private static String emailAndBusinessName;

	private static EmailAndBusiness emailAndBusinessInstance = null;

	public static EmailAndBusiness getEmailAndBusinessInstance() {
		if (emailAndBusinessInstance == null) {
			emailAndBusinessInstance = new EmailAndBusiness();
		}
		return emailAndBusinessInstance;
	}

	public EmailAndBusiness() {

	}

	public EmailAndBusiness(String emailAndBusinessName) {
		super();
		EmailAndBusiness.emailAndBusinessName = emailAndBusinessName;
	}

	public String getEmailAndBusinessName() {
		return EmailAndBusiness.emailAndBusinessName;
	}

	public void setEmailAndBusinessName(String emailAndBusinessName) {
		EmailAndBusiness.emailAndBusinessName = emailAndBusinessName;
	}

	@Override
	public String toString() {
		return "EmailAndBusiness [emailAndBusinessName=" + emailAndBusinessName + "]";
	}

}
