package com.keepcount.service.login;

import java.util.List;

import org.springframework.stereotype.Service;

import com.keepcount.controller.login.EmailsOfABusinessLoginHibernation;
import com.keepcount.model.login.EmailsOfABusiness;

@Service
public class EmailsOfABusinessLoginService {

	public List<EmailsOfABusiness> findAllEmailsOfABusinessByEmail(String email) {
		return EmailsOfABusinessLoginHibernation.findAllEmailsOfABusiness(email);
	}

}
