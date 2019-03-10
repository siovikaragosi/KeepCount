package com.keepcount.service.item;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.keepcount.dao.item.UnitOfMeasurementHibernation;
import com.keepcount.model.item.UnitOfMeasurement;

@Service
public class UnitOfMeasurementService {

	public int newUnit(UnitOfMeasurement measurement, String businessId) {
		return UnitOfMeasurementHibernation.newUnit(measurement, businessId);
	}

	public int updateUnit(UnitOfMeasurement measurement, String businessId, BigDecimal id) {
		return UnitOfMeasurementHibernation.updateUnit(measurement, businessId, id);
	}

	public int deleteUnit(String businessId, BigDecimal id) {
		return UnitOfMeasurementHibernation.deleteUnit(businessId, id);
	}

	public List<UnitOfMeasurement> findAllUnitsOfMeasurement_Direct(String businessId) {
		return UnitOfMeasurementHibernation.findUnitsOfMeasurement_Direct(businessId);
	}

	public List<UnitOfMeasurement> findAllUnitsOfMeasurement_Not_Direct() {
		return UnitOfMeasurementHibernation.findUnitsOfMeasurement_Not_Direct();
	}

}
