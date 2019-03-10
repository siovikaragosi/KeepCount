package com.keepcount.model.item;

import java.math.BigDecimal;

public class UnitOfMeasurement {

	private BigDecimal id;
	private BigDecimal itemId;
	private String unitOfMeasurement;

	public UnitOfMeasurement() {

	}

	public UnitOfMeasurement(BigDecimal itemId, String unitOfMeasurement) {
		super();
		this.itemId = itemId;
		this.unitOfMeasurement = unitOfMeasurement;
	}

	public UnitOfMeasurement(BigDecimal id, BigDecimal itemId, String unitOfMeasurement) {
		super();
		this.id = id;
		this.itemId = itemId;
		this.unitOfMeasurement = unitOfMeasurement;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public String getUnitOfMeasurement() {
		return unitOfMeasurement;
	}

	public void setUnitOfMeasurement(String unitOfMeasurement) {
		this.unitOfMeasurement = unitOfMeasurement;
	}

	@Override
	public String toString() {
		return "UnitOfMeasurement [id=" + id + ", itemId=" + itemId + ", unitOfMeasurement=" + unitOfMeasurement + "]";
	}

}
