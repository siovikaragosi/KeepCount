package com.keepcount.dao.business.create;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.keepcount.model.business.create.EmailsOfABusinessLogin;

public class EmailsOfABusinessHibernation {

	// private static List<EmailsOfABusinessLogin> emailsOfABusinessHibernated;
	// private static List<String> businessesOfThisEmail;

	public EmailsOfABusinessHibernation() {

	}

	public static int addNewEmailForABusiness(EmailsOfABusinessLogin emails) {
		int result = 0;
		try {
			result = EmailsOfABusinessDAO.addNewEmailForABusiness(emails);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static int updateEmailOfABusiness(BigDecimal id) {
		int result = 0;
		try {
			result = EmailsOfABusinessDAO.updateEmailOfABusiness(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static int deleteEmailFromABusiness(String email, BigDecimal id, String businessName) {
		int result = 0;
		try {
			result = EmailsOfABusinessDAO.deleteEmailFromABusiness(email, id, businessName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// public static void main(String[] args) {
	// List<EmailsOfABusinessLogin> logins = new ArrayList<>();
	// logins = findAllBusinessesOfThisEmail("findAllBusinessesOfThisEmail");
	// System.out.println(logins);
	// }

	public static List<EmailsOfABusinessLogin> findAllBusinessesOfThisEmail(String email) {
		List<EmailsOfABusinessLogin> businesses = new ArrayList<>();
		try {
			businesses = EmailsOfABusinessDAO.findAllBusinessesOfThisEmail(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// List<String> businessNames = getBusinessesOfThisEmail();
		// List<String> list = new ArrayList<>();
		// try {
		// if (businesses == null) {
		// businesses = EmailsOfABusinessDAO.findAllBusinessesOfThisEmail(email);
		// setEmailsOfABusinessHibernated(businesses);
		// if (businessNames == null) {
		// for (EmailsOfABusinessLogin emailsOfABusiness : businesses) {
		// String business = emailsOfABusiness.getBusinessName();
		// list.add(business);
		// }
		// setBusinessesOfThisEmail(list);
		// }
		//
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		return businesses;
	}

	// public static List<EmailsOfABusinessLogin> getEmailsOfABusinessHibernated() {
	// return emailsOfABusinessHibernated;
	// }
	//
	// public static void
	// setEmailsOfABusinessHibernated(List<EmailsOfABusinessLogin>
	// emailsOfABusinessHibernated) {
	// EmailsOfABusinessHibernation.emailsOfABusinessHibernated =
	// emailsOfABusinessHibernated;
	// }
	//
	// public static List<String> getBusinessesOfThisEmail() {
	// return businessesOfThisEmail;
	// }
	//
	// public static void setBusinessesOfThisEmail(List<String>
	// businessesOfThisEmail) {
	// EmailsOfABusinessHibernation.businessesOfThisEmail = businessesOfThisEmail;
	// }

}
