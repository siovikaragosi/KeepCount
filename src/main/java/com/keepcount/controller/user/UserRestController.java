package com.keepcount.controller.user;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keepcount.controller.business.choose.ChooseBusinessController;
import com.keepcount.model.business.create.EmailsOfABusinessLogin;
import com.keepcount.model.user.User;
import com.keepcount.service.user.UserService;

@RestController
public class UserRestController {

//	private UserService userService;
//
//	@RequestMapping(value = "/api/business/{businessName}/{emailOfUser}")
//	public List<User> findAllUsersOfABusiness(Model model, @PathVariable(name = "businessName") String businessName,
//			@PathVariable(name = "emailOfUser") String emailOfUser) {
//		EmailsOfABusinessLogin emailsOfABusiness = new EmailsOfABusinessLogin();
//		emailOfUser = ChooseBusinessController.getEmailLoggedIn();
//		businessName = ChooseBusinessController.getNameOfTheBusiness();
//
//		model.addAttribute("emailsOfABusinessForm", emailsOfABusiness);
//		model.addAttribute("emailOfUser", emailOfUser);
//		model.addAttribute("businessName", businessName);
//
//		return userService.findAllUserPerBusiness();
//	}

}
