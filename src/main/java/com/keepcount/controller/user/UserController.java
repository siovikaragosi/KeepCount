package com.keepcount.controller.user;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.keepcount.controller.business.choose.ChooseBusinessController;
import com.keepcount.model.business.create.EmailsOfABusinessLogin;
import com.keepcount.model.user.User;
import com.keepcount.service.user.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	private static int insertions = 0;

	@RequestMapping(value = "/business/users/{businessName}/{emailOfUser}")
	public String showUsersPage(Model model, @PathVariable(name = "businessName") String businessName, @PathVariable(name = "emailOfUser") String emailOfUser) {

		// EmailsOfABusinessLogin emailsOfABusiness = new EmailsOfABusinessLogin();
		//
		// model.addAttribute("emailsOfABusinessForm", emailsOfABusiness);
		//
		// emailOfUser = new ChooseBusinessController().getEmailLoggedIn();
		// businessName = new ChooseBusinessController().getNameOfTheBusiness();
		//
		// emailsOfABusiness.setBusinessName(businessName);
		// emailsOfABusiness.setEmail(emailOfUser);
		//
		// System.out.println("emails of a business: " + emailsOfABusiness);
		//
		// model.addAttribute("emailOfUser", emailOfUser);
		// model.addAttribute("businessName", businessName);

		EmailsOfABusinessLogin emailsOfABusiness = new EmailsOfABusinessLogin();
		emailOfUser = ChooseBusinessController.getEmailLoggedIn();
		businessName = ChooseBusinessController.getNameOfTheBusiness();

		model.addAttribute("emailsOfABusinessForm", emailsOfABusiness);
		model.addAttribute("emailOfUser", emailOfUser);
		model.addAttribute("businessName", businessName);

		return "keepCount/business/user/create-user";
	}

	@RequestMapping(value = "/api/business/{businessName}/{emailOfUser}/{id}", method = RequestMethod.GET, produces = "application/json")
	public String findUserById(HttpServletRequest request, HttpServletResponse response,

											@PathVariable(name = "businessName") String businessName,

											@PathVariable(name = "emailOfUser") String emailOfUser, @PathVariable(name = "id") String id) {

		emailOfUser = ChooseBusinessController.getEmailLoggedIn();
		businessName = ChooseBusinessController.getNameOfTheBusiness();
		String emailOfTheBusiness = ChooseBusinessController.getEmailOfThisBusiness();
		// String businessId = businessName.concat(emailOfTheBusiness);
		String businessId = ChooseBusinessController.getIdOfBusiness().toString();

		String pickedUser = new Gson().toJson(userService.findUserById(new BigDecimal(id), businessId));

		String pickedConversion = pickedUser;

		System.out.println("picked user: " + pickedConversion);

		return pickedConversion;
	}

	@RequestMapping(value = "/api/business/{businessName}/{emailOfUser}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String findAllUsersOfABusiness(Model model, @PathVariable(name = "businessName") String businessName, @PathVariable(name = "emailOfUser") String emailOfUser) {

		EmailsOfABusinessLogin emailsOfABusiness = new EmailsOfABusinessLogin();
		emailOfUser = ChooseBusinessController.getEmailLoggedIn();
		businessName = ChooseBusinessController.getNameOfTheBusiness();
		String emailOfTheBusiness = ChooseBusinessController.getEmailOfThisBusiness();

		model.addAttribute("emailsOfABusinessForm", emailsOfABusiness);
		model.addAttribute("emailOfUser", emailOfUser);
		model.addAttribute("businessName", businessName);

		// EmailAndBusiness emailAndBusiness =
		// EmailAndBusiness.getEmailAndBusinessInstance();
		//
		// System.out.println("Email and biz name from find users: " +
		// emailAndBusiness.getEmailAndBusinessName());

		// String businessId = businessName.concat(emailOfTheBusiness);
		String businessId = ChooseBusinessController.getIdOfBusiness().toString();

		if (getInsertions() == 0) {

			System.out.println("users loaded from main");
			String list = new Gson().toJson(userService.findAllUsersPerBusinessUponRefreshing(businessId));
			// System.out.println("cont: " + list);

			setInsertions(1);

			return list;
		} else if (getInsertions() == 1) {
			System.out.println("users loaded from main");
			String list = new Gson().toJson(userService.findAllUsersPerBusinessUponRefreshing(businessId));
			// System.out.println("cont: " + list);

			setInsertions(2);

			return list;
		} else {
			System.out.println("users loaded from hibernation");
			String list = new Gson().toJson(userService.findAllUserPerBusiness(businessId));
			// System.out.println("cont: " + list);

			// setInsertions();

			return list;
		}

	}

	@RequestMapping(value = "/api/business/{businessName}/{emailOfUser}", method = RequestMethod.POST)

	@ResponseBody
	public String createNewUser(HttpServletRequest request, HttpServletResponse response,

											@PathVariable(name = "businessName") String businessName,

											@PathVariable(name = "emailOfUser") String emailOfUser, @RequestBody User user) {

		int result = 0;

		emailOfUser = ChooseBusinessController.getEmailLoggedIn();
		businessName = ChooseBusinessController.getNameOfTheBusiness();
		String emailOfThebusiness = ChooseBusinessController.getEmailOfThisBusiness();
		// String businessId = businessName.concat(emailOfThebusiness);
		String businessId = ChooseBusinessController.getIdOfBusiness().toString();

		result = userService.createNewUser(user, businessId);

		System.out.println("first name: " + user);

		System.out.println("result: " + result);

		System.out.println("emailOfUser: " + emailOfUser);
		System.out.println("businessName: " + businessName);

		if (result == 1) {
			setInsertions(1);
			return "/api/business/{businessName}/{emailOfUser}";
		} // setInsertions(0);
		return "/api/business/{businessName}/{emailOfUser}";
	}

	@RequestMapping(value = "/api/business/{businessName}/{emailOfUser}/{id}/update", method = RequestMethod.PUT)
	@ResponseBody
	public String updateUser(HttpServletRequest request, HttpServletResponse response, @PathVariable(name = "businessName") String businessName, @PathVariable(name = "emailOfUser") String emailOfUser,
											@PathVariable(name = "id") String id, @RequestBody User user) {

		emailOfUser = ChooseBusinessController.getEmailLoggedIn();
		businessName = ChooseBusinessController.getNameOfTheBusiness();
		// String emailOfThebusiness =
		// ChooseBusinessController.getEmailOfThisBusiness();
		// String businessId = businessName.concat(emailOfThebusiness);

		// System.out.println("ID same: " + user.getId());
		String businessId = ChooseBusinessController.getIdOfBusiness().toString();

		userService.updateUser(new BigDecimal(id), businessId, user);

		setInsertions(0);
		return "/api/business/{businessName}/{emailOfUser}/{id}";
	}

	@RequestMapping(value = "/api/business/{businessName}/{emailOfUser}/{id}/delete", method = RequestMethod.DELETE)
	@ResponseBody
	public String deleteUser(HttpServletRequest request, HttpServletResponse response,

											@PathVariable(name = "businessName") String businessName,

											@PathVariable(name = "emailOfUser") String emailOfUser, @PathVariable(name = "id") BigDecimal id) {

		emailOfUser = ChooseBusinessController.getEmailLoggedIn();
		businessName = ChooseBusinessController.getNameOfTheBusiness();
		// String emailOfThebusiness =
		// ChooseBusinessController.getEmailOfThisBusiness();

		// String businessId = businessName.concat(emailOfThebusiness);
		String businessId = ChooseBusinessController.getIdOfBusiness().toString();

		int result = userService.deleteUser(id, businessId);

		if (result == 1) {
			System.out.println("deleted successfully");
			setInsertions(1);

		} else {
			System.out.println("something went wrong");
		}

		// setInsertions(0);
		return "/api/business/{businessName}/{emailOfUser}/{id}";
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public static int getInsertions() {
		return insertions;
	}

	public static void setInsertions(int insertions) {
		UserController.insertions = insertions;
	}

	// @RequestMapping(value = "/api/business/users/{businessName}/{emailOfUser}",
	// method = RequestMethod.POST)
	// @ResponseStatus(HttpStatus.OK)
	// @ResponseBody
	// public String createNewUser(HttpServletRequest request, HttpServletResponse
	// response,
	// @PathVariable(name = "businessName") String businessName,
	// @PathVariable(name = "emailOfUser") String emailOfUser, User user) {
	//
	// int result = 0;
	//
	// emailOfUser = ChooseBusinessController.getEmailLoggedIn();
	// businessName = ChooseBusinessController.getNameOfTheBusiness();
	//
	// result = userService.createNewUser(user);
	//
	// System.out.println("first name: " + user);
	//
	// System.out.println("result: " + result);
	//
	// System.out.println("emailOfUser: " + emailOfUser);
	// System.out.println("businessName: " + businessName);
	//
	// // return new Gson().toJson(user);
	//
	// if (result == 1) {
	// return "/business/users/{businessName}/{emailOfUser}";
	// }
	//
	// return "/business/users/{businessName}/{emailOfUser}";
	// }

}
