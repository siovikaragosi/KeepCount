
package com.keepcount.controller.business.create;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.keepcount.controller.login.LoginController;
import com.keepcount.dao.select.CountryDAO;
import com.keepcount.model.business.create.CreateBusiness;
import com.keepcount.model.business.create.EmailsOfABusinessLogin;
import com.keepcount.model.select.Country;
import com.keepcount.model.select.PersonForm;
import com.keepcount.service.business.create.CreateBusinessService;

@Controller
public class CreateBusinessController {

	private static String businessName;
	private static String businessEmail;

	private static String emailOfTheUserLoggedIn;

	private static BigDecimal idOfBusiness;

	@Autowired
	private CreateBusinessService createBusinessService;

	public static void setEmail() {

		setEmailOfTheUserLoggedIn( LoginController.getEmailHeld() );
		System.out.println( "email test: " + getEmailOfTheUserLoggedIn() );
	}

	@Autowired
	private CountryDAO countryDAO;

	@RequestMapping ( value = "/business/createBusiness" )
	public String showCreateBusinessPage( Model model ) {

		setEmailOfTheUserLoggedIn( LoginController.getEmailHeld() );
		System.out.println( "email test: " + getEmailOfTheUserLoggedIn() );

		PersonForm form = new PersonForm();
		model.addAttribute( "personForm" , form );

		List< Country > list = countryDAO.getCountries();
		model.addAttribute( "countries" , list );

		return "keepCount/business/create/create-business";
	}

	@RequestMapping ( value = "/api/business/emailOfUser/createBusiness" , method = RequestMethod.POST , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE )
	@ResponseBody
	public String createNewBusiness( Model model , @ModelAttribute CreateBusiness business ) {

		int result = 0;

		model.addAttribute( "businessName" , business.getBusinessName() );
		model.addAttribute( "type" , business.getType() );

		business.setEmail( LoginController.getEmailHeld() );

		result = createBusinessService.createNewBusiness( business );

		if ( result == 1 ) {

			CreateBusinessController.setBusinessName( business.getBusinessName() );
			CreateBusinessController.setBusinessEmail( business.getEmail() );
			setEmailOfTheUserLoggedIn( business.getEmail() );

			EmailsOfABusinessLogin emailsOfABusiness = new EmailsOfABusinessLogin( business.getEmail() , null , business.getBusinessName() , null );

			BigDecimal id = createBusinessService.findIdOfBusinessByEmail( business.getEmail() );

			emailsOfABusiness.setIdBizType( id );

			setIdOfBusiness( id );

			createBusinessService.addNewEmailForABusiness( emailsOfABusiness );

			// System.out.println(business.getBusinessName() + "\n" + business.getEmail() +
			// "\n" + business.getType());

			// CreateBusinessController.getBusinessEmail()
			// .concat(CreateBusinessController.getBusinessName());

			return "keepCount/business/user/create-user";
		}
		else {
			return "keepCount/business/create/create-business";
		}

	}

	public static String getBusinessName() {

		return businessName;
	}

	public static void setBusinessName( String businessName ) {

		CreateBusinessController.businessName = businessName;
	}

	public static String getBusinessEmail() {

		return businessEmail;
	}

	public static void setBusinessEmail( String businessEmail ) {

		CreateBusinessController.businessEmail = businessEmail;
	}

	public static String getEmailOfTheUserLoggedIn() {

		return emailOfTheUserLoggedIn;
	}

	public static void setEmailOfTheUserLoggedIn( String emailOfTheUserLoggedIn ) {

		CreateBusinessController.emailOfTheUserLoggedIn = emailOfTheUserLoggedIn;
	}

	public static BigDecimal getIdOfBusiness() {

		return idOfBusiness;
	}

	public static void setIdOfBusiness( BigDecimal idOfBusiness ) {

		CreateBusinessController.idOfBusiness = idOfBusiness;
	}

}
