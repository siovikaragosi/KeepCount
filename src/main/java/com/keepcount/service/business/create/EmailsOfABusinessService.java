package com.keepcount.service.business.create;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.keepcount.dao.business.create.EmailsOfABusinessHibernation;
import com.keepcount.model.business.create.EmailsOfABusinessLogin;

@Service
public class EmailsOfABusinessService {

	public int addNewEmailForABusiness(EmailsOfABusinessLogin business) {
		int result = 0;
		result = EmailsOfABusinessHibernation.addNewEmailForABusiness(business);
		return result;
	}

	public int updateEmailOfABusiness(BigDecimal id) {
		int result = 0;
		result = EmailsOfABusinessHibernation.updateEmailOfABusiness(id);
		return result;
	}

	public int deleteEmailFromABusiness(String email, BigDecimal id, String businessName) {
		int result = 0;
		result = EmailsOfABusinessHibernation.deleteEmailFromABusiness(email, id, businessName);
		return result;
	}

	public List<EmailsOfABusinessLogin> findAllBusinessesOfThisEmail(String emails) {
		return EmailsOfABusinessHibernation.findAllBusinessesOfThisEmail(emails);
	}

	// public static void main(String[] args) {
	// List<EmailsOfABusinessLogin> logins = new ArrayList<>();
	// logins = findAllBusinessesOfThisEmail("alimahmoudraage@gmail.com");
	// System.out.println(logins);
	// }

	// public List<String> findAllBusinessNames() {
	// return EmailsOfABusinessHibernation.getBusinessesOfThisEmail();
	// }

}
