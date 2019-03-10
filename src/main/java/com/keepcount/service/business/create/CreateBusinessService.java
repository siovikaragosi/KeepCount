package com.keepcount.service.business.create;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.keepcount.dao.business.create.CreateBusinessHibernation;
import com.keepcount.model.business.create.CreateBusiness;
import com.keepcount.model.business.create.EmailsOfABusinessLogin;

@Service
public class CreateBusinessService {

	private CreateBusinessHibernation createBusinessHibernation;

	public BigDecimal findIdOfBusinessByEmail(String emailToFindBizId) {
		return CreateBusinessHibernation.findIdOfBusinessByEmail(emailToFindBizId);
	}

	public int addNewEmailForABusiness(EmailsOfABusinessLogin emailsOfABusiness) {
		int result = 0;
		result = CreateBusinessHibernation.addNewEmailForABusiness(emailsOfABusiness);
		return result;
	}

	public CreateBusinessService() {
		createBusinessHibernation = new CreateBusinessHibernation();
	}

	public int createNewBusiness(CreateBusiness business) {
		int result = 0;
		result = createBusinessHibernation.createNewBusiness(business);
		return result;
	}

	public int updateBusiness(String email, BigDecimal id) {
		int result = 0;
		result = createBusinessHibernation.updateBusiness(email, id);
		return result;
	}

	public List<CreateBusiness> findAllBusinessesCreatedByEmail(String email) {
		List<CreateBusiness> businesses = new ArrayList<>();
		businesses = createBusinessHibernation.findAllBusinessesByEmail(email);
		return businesses;
	}

}
