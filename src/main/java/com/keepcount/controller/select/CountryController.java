package com.keepcount.controller.select;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.keepcount.dao.select.CountryDAO;
import com.keepcount.model.select.Country;
import com.keepcount.model.select.PersonForm;

@Controller
public class CountryController {

	@Autowired
	private CountryDAO countryDAO;

	@RequestMapping(value = "keepCount/select", method = RequestMethod.GET)
	public String selectOptionExample1Page(Model model) {

		PersonForm form = new PersonForm();
		model.addAttribute("personForm", form);

		List<Country> list = countryDAO.getCountries();
		model.addAttribute("countries", list);

		return "keepCount/select";
	}

}