package com.keepcount.model.entity;

import java.math.BigDecimal;

public class Entity {

	private BigDecimal id;
	private String entityName;

	public Entity() {

	}

	public Entity(BigDecimal id, String entityName) {
		super();
		this.id = id;
		this.entityName = entityName;
	}

	public Entity(String entityName) {
		super();
		this.entityName = entityName;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	@Override
	public String toString() {
		return "Entity [id=" + id + ", entityName=" + entityName + "]";
	}

}
