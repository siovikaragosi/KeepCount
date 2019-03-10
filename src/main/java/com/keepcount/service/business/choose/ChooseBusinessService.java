package com.keepcount.service.business.choose;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.keepcount.dao.business.choose.ChooseBusinessHibernation;
import com.keepcount.model.business.choose.ChooseBusiness;
import com.keepcount.model.business.create.EmailsOfABusinessLogin;

@Service
public class ChooseBusinessService {

	private ChooseBusinessHibernation chooseBusinessHibernation;

	public List<EmailsOfABusinessLogin> findBusinessesOfThisEmail(String email) {
		chooseBusinessHibernation = new ChooseBusinessHibernation();
		System.out.println(
				"all biz per email choose service: " + chooseBusinessHibernation.findAllBusinessesOfThisEmail(email));
		return chooseBusinessHibernation.findAllBusinessesOfThisEmail(email);
	}

	public BigDecimal findIdOfBusinessByEmailLoggedInAndBusinessName(String emailToFindBizId,
			String businessNameToFindBizId) {
		return ChooseBusinessHibernation.findIdOfBusinessByEmailLoggedInAndBusinessName(emailToFindBizId,
				businessNameToFindBizId);
	}

	public int chooseABusinessAndContinue(String emailLoggedIn, String businessNameChosen, ChooseBusiness choose) {
		int result = 0;
		chooseBusinessHibernation = new ChooseBusinessHibernation();
		ChooseBusinessHibernation.chooseABusinessAndContinue(emailLoggedIn, businessNameChosen, choose);
		return result;
	}

	public String getTheEmailOfThisBusiness(String businessNameChosen) {
		return ChooseBusinessHibernation.emailOfTheBusiness(businessNameChosen);
	}

}
