package com.keepcount.controller.customers;

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
import com.keepcount.model.customers.Customers;
import com.keepcount.service.customers.CustomersService;

@Controller
public class CustomersController {

	private int changeMade = 0;

	@Autowired
	private CustomersService customersService;

	@RequestMapping(value = "/business/customers/{businessName}/{emailOfUser}")
	public String showCustomersPage(Model model, @PathVariable(name = "businessName") String businessName,
			@PathVariable(name = "emailOfUser") String emailOfUser) {

		EmailsOfABusinessLogin emailsOfABusiness = new EmailsOfABusinessLogin();
		emailOfUser = ChooseBusinessController.getEmailLoggedIn();
		businessName = ChooseBusinessController.getNameOfTheBusiness();

		model.addAttribute("emailsOfABusinessForm", emailsOfABusiness);
		model.addAttribute("emailOfUser", emailOfUser);
		model.addAttribute("businessName", businessName);

		// setChangeMade(1);

		return "/keepCount/business/customer/customers";
	}

	@RequestMapping(value = "/api/business/customers/{businessName}/{emailOfUser}", method = RequestMethod.POST)
	@ResponseBody
	public String createNewCustomer(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(name = "businessName") String businessName,
			@PathVariable(name = "emailOfUser") String emailOfUser, @RequestBody Customers customer) {

		emailOfUser = ChooseBusinessController.getEmailLoggedIn();
		businessName = ChooseBusinessController.getNameOfTheBusiness();
		// String emailOfThebusiness =
		// ChooseBusinessController.getEmailOfThisBusiness();
		String businessId = ChooseBusinessController.getIdOfBusiness().toString();

		int result = customersService.createNewCustomer(customer, businessId);
		// System.out.println("cust result: " + result);
		if (result == 1) {
			setChangeMade(1);
		} else {
			setChangeMade(0);
		}

		return "/api/business/customers/{businessName}/{emailOfUser}";
	}

	@RequestMapping(value = "/api/business/customers/{businessName}/{emailOfUser}/allCustomers", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String findAllCustomers(Model model, @PathVariable(name = "businessName") String businessName,
			@PathVariable(name = "emailOfUser") String emailOfUser) {

		EmailsOfABusinessLogin emailsOfABusiness = new EmailsOfABusinessLogin();
		emailOfUser = ChooseBusinessController.getEmailLoggedIn();
		businessName = ChooseBusinessController.getNameOfTheBusiness();
		// String emailOfTheBusiness =
		// ChooseBusinessController.getEmailOfThisBusiness();
		String businessId = ChooseBusinessController.getIdOfBusiness().toString();

		model.addAttribute("emailsOfABusinessForm", emailsOfABusiness);
		model.addAttribute("emailOfUser", emailOfUser);
		model.addAttribute("businessName", businessName);

		// String businessId = businessName.concat(emailOfTheBusiness);

		if (getChangeMade() != 1) {
			// System.out.println("cust: not 1");
			return new Gson().toJson(customersService.findAllCustomersNotDirect());

		} else {
			// System.out.println("cust: 1");
			return new Gson().toJson(customersService.findAllCustomersDirect(businessId));

		}

	}

	@RequestMapping(value = "/api/business/customers/{businessName}/{emailOfUser}/{id}/delete", method = RequestMethod.DELETE)
	@ResponseBody
	public String deleteUser(HttpServletRequest request, HttpServletResponse response,

			@PathVariable(name = "businessName") String businessName,

			@PathVariable(name = "emailOfUser") String emailOfUser, @PathVariable(name = "id") BigDecimal id) {

		new ChooseBusinessController();
		emailOfUser = ChooseBusinessController.getEmailLoggedIn();
		new ChooseBusinessController();
		businessName = ChooseBusinessController.getNameOfTheBusiness();
		new ChooseBusinessController();
		// String emailOfThebusiness =
		// ChooseBusinessController.getEmailOfThisBusiness();

		// String businessId = businessName.concat(emailOfThebusiness);
		String businessId = ChooseBusinessController.getIdOfBusiness().toString();

		int result = customersService.deleteCustomer(businessId, id);

		if (result == 1) {
			System.out.println("deleted successfully");
			setChangeMade(1);
		} else {
			System.out.println("something went wrong");
		}

		// setInsertions(0);
		return "/api/business/customers/{businessName}/{emailOfUser}/{id}/delete";
	}

	@RequestMapping(value = "/api/business/customers/{businessName}/{emailOfUser}/{id}/update", method = RequestMethod.PUT)
	@ResponseBody
	public String updateUser(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(name = "businessName") String businessName,
			@PathVariable(name = "emailOfUser") String emailOfUser, @PathVariable(name = "id") BigDecimal id,
			@RequestBody Customers customer) {

		emailOfUser = ChooseBusinessController.getEmailLoggedIn();
		businessName = ChooseBusinessController.getNameOfTheBusiness();
		String emailOfThebusiness = ChooseBusinessController.getEmailOfThisBusiness();
		// String businessId = businessName.concat(emailOfThebusiness);

		// System.out.println("ID same: " + customer.getId());
		String businessId = ChooseBusinessController.getIdOfBusiness().toString();

		customersService.updateCustomer(businessId, customer, id);

		setChangeMade(1);

		return "/api/business/customers/{businessName}/{emailOfUser}/{id}";
	}

	public int getChangeMade() {
		return changeMade;
	}

	public void setChangeMade(int changeMade) {
		this.changeMade = changeMade;
	}

}
