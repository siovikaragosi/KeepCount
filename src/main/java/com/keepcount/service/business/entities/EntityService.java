package com.keepcount.service.business.entities;

import org.springframework.stereotype.Service;

import com.keepcount.dao.entity.EntityHibernation;

@Service
public class EntityService {

	private EntityHibernation entityHibernation = EntityHibernation.getEntityHibernationInstance();

	public String getEmailOfTheUser() {
		return entityHibernation.getEmailOfTheUser();
	}

	public String getNameOfTheBusiness() {
		return entityHibernation.getNameOfTheBusiness();
	}

}
