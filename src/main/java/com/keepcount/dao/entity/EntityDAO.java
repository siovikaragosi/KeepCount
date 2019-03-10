package com.keepcount.dao.entity;

import com.keepcount.controller.business.choose.ChooseBusinessController;

public class EntityDAO {

	private static EntityDAO entityDAOInstance = null;

	private ChooseBusinessController chooseBusinessController;

	public String getEmailOfTheUser() {
		chooseBusinessController = new ChooseBusinessController();
		return chooseBusinessController.getEmailLoggedIn();
	}

	public String getNameOfTheBusiness() {
		chooseBusinessController = new ChooseBusinessController();
		return chooseBusinessController.getNameOfTheBusiness();
	}

	public static EntityDAO getEntityDAOInstance() {
		if (entityDAOInstance == null) {
			entityDAOInstance = new EntityDAO();
		}
		return entityDAOInstance;
	}

}
