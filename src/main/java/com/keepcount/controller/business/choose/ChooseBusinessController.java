package com.keepcount.controller.business.choose;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.keepcount.controller.business.create.CreateBusinessController;
import com.keepcount.model.business.choose.ChooseBusiness;
import com.keepcount.model.business.create.EmailsOfABusinessLogin;
import com.keepcount.service.business.choose.ChooseBusinessService;

@Controller
public class ChooseBusinessController {

	private static String emailOfThisBusiness;
	private static String nameOfTheBusiness;
	private static String emailLoggedIn;
	private static BigDecimal idOfBusiness;

	@Autowired
	private ChooseBusinessService chooseBusinessService;

	@RequestMapping(value = "/business/chooseBusiness/{email}", method = RequestMethod.GET)
	public String showChooseBusinessPage(Model model, @PathVariable(name = "email") String email,
			EmailsOfABusinessLogin email2) {
		CreateBusinessController.setEmail();
		email = CreateBusinessController.getEmailOfTheUserLoggedIn();

		// System.out.println("Email of the logger in: ".concat(email));

		EmailsOfABusinessLogin emailsOfABusiness = new EmailsOfABusinessLogin();

		model.addAttribute("emailsOfABusinessForm", emailsOfABusiness);

		List<EmailsOfABusinessLogin> list = chooseBusinessService.findBusinessesOfThisEmail(email);
		model.addAttribute("businessNames", list);

		email2.setEmail(email);

		model.addAttribute("email", email2.getEmail());

		return "keepCount/business/choose/choose-business";

	}

	@ResponseBody
	@RequestMapping(value = "/business/chooseBusiness/{businessName}/{emailOfUser}", method = RequestMethod.POST)
	public String continueAfterChoosingBusiness(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(name = "businessName") String businessName,
			@PathVariable(name = "emailOfUser") String emailOfUser, ChooseBusiness choose) {
		int result = 0;

		CreateBusinessController.setEmail();
		emailOfUser = CreateBusinessController.getEmailOfTheUserLoggedIn();

		result = chooseBusinessService.chooseABusinessAndContinue(emailOfUser, businessName, choose);

		setEmailLoggedIn(emailOfUser);
		setNameOfTheBusiness(businessName);
		setEmailOfThisBusiness(chooseBusinessService.getTheEmailOfThisBusiness(businessName));

		BigDecimal id = chooseBusinessService.findIdOfBusinessByEmailLoggedInAndBusinessName(getEmailLoggedIn(),
				getNameOfTheBusiness());

		setIdOfBusiness(id);

		System.out.println("id: " + getIdOfBusiness());

		// EmailAndBusiness emailAndBusiness =
		// EmailAndBusiness.getEmailAndBusinessInstance();

		// emailAndBusiness.setEmailAndBusinessName(ChooseBusinessController.getNameOfTheBusiness()
		// .concat(ChooseBusinessController.getEmailOfThisBusiness()));

		// System.out.println("biz name from chooe biz: " +
		// emailAndBusiness.getEmailAndBusinessName());

		if (result == 1) {
			return "redirect:/keepCount/business/entities/{businessName}/{email}";
		} else {
			return "redirect:/keepCount/business/chooseBusiness/{emailOfUser}";
		}

	}

	public static BigDecimal getIdOfBusiness() {
		return idOfBusiness;
	}

	public static void setIdOfBusiness(BigDecimal idOfBusiness) {
		ChooseBusinessController.idOfBusiness = idOfBusiness;
	}

	public static String getEmailOfThisBusiness() {
		return emailOfThisBusiness;
	}

	public static void setEmailOfThisBusiness(String emailOfThisBusiness) {
		ChooseBusinessController.emailOfThisBusiness = emailOfThisBusiness;
	}

	public static String getNameOfTheBusiness() {
		return nameOfTheBusiness;
	}

	public static void setNameOfTheBusiness(String nameOfTheBusiness) {
		ChooseBusinessController.nameOfTheBusiness = nameOfTheBusiness;
	}

	public static String getEmailLoggedIn() {
		return emailLoggedIn;
	}

	public static void setEmailLoggedIn(String emailLoggedIn) {
		ChooseBusinessController.emailLoggedIn = emailLoggedIn;
	}

}
