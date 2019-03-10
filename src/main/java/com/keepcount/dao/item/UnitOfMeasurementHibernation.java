package com.keepcount.dao.item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.keepcount.model.item.UnitOfMeasurement;

public class UnitOfMeasurementHibernation {

	private static List<UnitOfMeasurement> unitOfMeasurements;

	public static int newUnit(UnitOfMeasurement measurement, String businessId) {
		return UnitOfMeasurementDAO.newUnitOfMeasurement(measurement, businessId);
	}

	public static int updateUnit(UnitOfMeasurement measurement, String businessId, BigDecimal id) {
		return UnitOfMeasurementDAO.updateUnitOfMeasurement(measurement, businessId, id);
	}

	public static int deleteUnit(String businessId, BigDecimal id) {
		return UnitOfMeasurementDAO.deleteFromMeasurementUnits(businessId, id);
	}

	private static List<UnitOfMeasurement> findAllUnitsOfMeasurementWith(String businessId) {
		List<UnitOfMeasurement> list = new ArrayList<>();
		list = UnitOfMeasurementDAO.findAllUnitsOfMeasurement(businessId);
		setUnitOfMeasurements(list);
		return list;
	}

	public static List<UnitOfMeasurement> findUnitsOfMeasurement_Direct(String businessId) {
		return findAllUnitsOfMeasurementWith(businessId);
	}

	public static List<UnitOfMeasurement> findUnitsOfMeasurement_Not_Direct() {
		return getUnitOfMeasurements();
	}

	public static List<UnitOfMeasurement> getUnitOfMeasurements() {
		return unitOfMeasurements;
	}

	public static void setUnitOfMeasurements(List<UnitOfMeasurement> unitOfMeasurements) {
		UnitOfMeasurementHibernation.unitOfMeasurements = unitOfMeasurements;
	}

}
