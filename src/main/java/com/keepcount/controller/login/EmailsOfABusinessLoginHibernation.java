package com.keepcount.controller.login;

import java.util.ArrayList;
import java.util.List;

import com.keepcount.model.login.EmailsOfABusiness;

public class EmailsOfABusinessLoginHibernation {

	private static List<EmailsOfABusiness> emailsOfABusinessHibernated;

	public EmailsOfABusinessLoginHibernation() {

	}

	private static void findAllEmailsOfAbusiness(String email) {
		List<EmailsOfABusiness> list = new ArrayList<>();
		try {
			list = EmailsOfABusinessLoginDAO.findAllBusinessOfThisEmail(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setEmailsOfABusinessHibernated(list);
	}

	public static List<EmailsOfABusiness> findAllEmailsOfABusiness(String email) {
		findAllEmailsOfAbusiness(email);
		return getEmailsOfABusinessHibernated();
	}

	public static List<EmailsOfABusiness> getEmailsOfABusinessHibernated() {
		return emailsOfABusinessHibernated;
	}

	public static void setEmailsOfABusinessHibernated(List<EmailsOfABusiness> emailsOfABusinessHibernated) {
		EmailsOfABusinessLoginHibernation.emailsOfABusinessHibernated = emailsOfABusinessHibernated;
	}

}
