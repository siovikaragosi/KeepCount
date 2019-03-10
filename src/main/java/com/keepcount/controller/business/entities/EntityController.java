package com.keepcount.controller.business.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.keepcount.service.business.entities.EntityService;

@Controller
public class EntityController {

	@Autowired
	private EntityService entityService;

	@RequestMapping(value = "keepCount/business/entities/{businessName}/{emailOfUser}")
	// @RequestMapping(value = "keepCount/business/entities")
	public String showEntitiesPage(@PathVariable(name = "businessName") String businessName,
			@PathVariable(name = "emailOfUser") String emailOfUser) {

		emailOfUser = entityService.getEmailOfTheUser();
		businessName = entityService.getNameOfTheBusiness();

		// emailOfUser = "alimahmoudraage@gmail.com";
		// businessName = "b5";

		return "keepCount/business/entities/entities";
	}

}
