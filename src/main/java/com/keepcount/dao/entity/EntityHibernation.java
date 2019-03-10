package com.keepcount.dao.entity;

public class EntityHibernation {

	private static EntityHibernation entityHibernationInstance = null;

	private EntityDAO entityDAO = EntityDAO.getEntityDAOInstance();

	public static EntityHibernation getEntityHibernationInstance() {
		if (entityHibernationInstance == null) {
			entityHibernationInstance = new EntityHibernation();
		}
		return entityHibernationInstance;
	}

	public String getEmailOfTheUser() {
		return entityDAO.getEmailOfTheUser();
	}

	public String getNameOfTheBusiness() {
		return entityDAO.getNameOfTheBusiness();
	}

}
