package com.keepcount.dao.business.choose;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.keepcount.model.business.choose.ChooseBusiness;
import com.keepcount.model.business.create.EmailsOfABusinessLogin;

public class ChooseBusinessHibernation {

	private static final String businessNameToFindBizId = null;
	private List<EmailsOfABusinessLogin> emailsOfABusiness;

	public ChooseBusinessHibernation() {

	}

	// public static String getEmailOfThisBusiness() {
	// return ChooseBusinessDAO.getEmailOfTheBusiness();
	// }

	public static String emailOfTheBusiness(String businessNameChosen) {

		String emailOfThebusiness = null;
		try {
			emailOfThebusiness = ChooseBusinessDAO.getTheEmailOfTheBusiness(businessNameChosen);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return emailOfThebusiness;
	}

	public static BigDecimal findIdOfBusinessByEmailLoggedInAndBusinessName(String emailToFindBizId,
			String businessNameToFindBizId) {
		BigDecimal id = BigDecimal.ZERO;
		try {
			id = ChooseBusinessDAO.findIdOfBusinessByEmailLoggedInAndBusinessName(emailToFindBizId,
					businessNameToFindBizId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public static int chooseABusinessAndContinue(String emailLoggedIn, String businessNameChosen,
			ChooseBusiness choose) {
		int result = 0;
		try {
			result = ChooseBusinessDAO.chooseABusinessAndContinue(emailLoggedIn, businessNameChosen, choose);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private void findAllBusinessesOfEmailFromDirect(String email) {
		List<EmailsOfABusinessLogin> businesses = new ArrayList<>();
		try {
			businesses = ChooseBusinessDAO.findAllBusinessesOfThisEmail(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setEmailsOfABusiness(businesses);
	}

	public List<EmailsOfABusinessLogin> findAllBusinessesOfThisEmail(String email) {
		List<EmailsOfABusinessLogin> businesses = new ArrayList<>();
		businesses = this.getEmailsOfABusiness();

		if (businesses == null) {
			this.findAllBusinessesOfEmailFromDirect(email);
			businesses = this.getEmailsOfABusiness();
		}

		System.out.println("all biz per email choose hib: " + businesses);
		return businesses;
	}

	public List<EmailsOfABusinessLogin> getEmailsOfABusiness() {
		return emailsOfABusiness;
	}

	public void setEmailsOfABusiness(List<EmailsOfABusinessLogin> emailsOfABusiness) {
		this.emailsOfABusiness = emailsOfABusiness;
	}

}
