package com.keepcount.controller.business.create;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.keepcount.controller.login.LoginController;
import com.keepcount.model.business.create.EmailsOfABusinessLogin;
import com.keepcount.service.business.create.EmailsOfABusinessService;

@Controller
public class EmailsOfABusinessController {

	@Autowired
	private EmailsOfABusinessService emailsOfABusinessService;

	private static String emailHeld;

	@RequestMapping(value = "keepCount/business/chooseBusiness/{email}")
	public String showChooseBusinessPage(Model model, @PathVariable(name = "email") String email) {
		// email = LoginController.getEmailHeld();

		setEmailHeld(LoginController.getEmailHeld());

		System.out.println("Email from Choose: ".concat(email));

		EmailsOfABusinessLogin emailsOfABusiness = new EmailsOfABusinessLogin();

		model.addAttribute("emailsOfABusinessForm", emailsOfABusiness);

		List<EmailsOfABusinessLogin> list = emailsOfABusinessService.findAllBusinessesOfThisEmail(getEmailHeld());
		model.addAttribute("businessNames", list);

		return "keepCount/business/choose/choose-business";
	}

	public static String getEmailHeld() {
		return emailHeld;
	}

	public static void setEmailHeld(String emailHeld) {
		EmailsOfABusinessController.emailHeld = emailHeld;
	}

}
