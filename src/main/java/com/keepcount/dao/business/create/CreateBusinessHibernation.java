package com.keepcount.dao.business.create;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.keepcount.model.business.create.CreateBusiness;
import com.keepcount.model.business.create.EmailsOfABusinessLogin;

public class CreateBusinessHibernation {

	private List<CreateBusiness> businessesCreated;

	public CreateBusinessHibernation() {

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

	public static BigDecimal findIdOfBusinessByEmail(String emailToFindBizId) {
		BigDecimal id = BigDecimal.ZERO;
		try {
			id = CreateBusinessDAO.findIdOfBusinessByEmail(emailToFindBizId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public int createNewBusiness(CreateBusiness business) {
		int result = 0;
		try {
			result = CreateBusinessDAO.createBusiness(business);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public int updateBusiness(String email, BigDecimal id) {
		int result = 0;
		try {
			result = CreateBusinessDAO.updateBusiness(email, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private void findAllBusinessesCreatedByEmailFromDirect(String email) {
		List<CreateBusiness> businesses = new ArrayList<>();
		try {
			businesses = CreateBusinessDAO.findAllBusinessesByEmail(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setBusinessesCreated(businesses);
	}

	public List<CreateBusiness> findAllBusinessesByEmail(String email) {
		List<CreateBusiness> businesses = new ArrayList<>();
		businesses = this.getBusinessesCreated();

		if (businesses == null) {
			this.findAllBusinessesCreatedByEmailFromDirect(email);
			businesses = this.getBusinessesCreated();
		}
		return businesses;
	}

	public void setBusinessesCreated(List<CreateBusiness> businessesCreated) {
		this.businessesCreated = businessesCreated;
	}

	public List<CreateBusiness> getBusinessesCreated() {
		return businessesCreated;
	}

}
