package com.keepcount.dao.item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.keepcount.dao.dbutils.DBUtils;
import com.keepcount.model.item.UnitOfMeasurement;

public class UnitOfMeasurementDAO {

	private static int procExisst = 0;

	public static int getProcExisst() {
		return procExisst;
	}

	public static void setProcExisst(int procExisst) {
		UnitOfMeasurementDAO.procExisst = procExisst;
	}

	private static String tableName = "measurement_units_";

	private static String createTableStr = "(id bigint primary key auto_increment".concat(",item_id bigint not null").concat(",unit_of_measurement varchar(255))");

	public static int checkMeasurementUnitTableExistence(String businessId) {
		int result = 0;
		try {
			result = DBUtils.checkTableExistence(tableName, createTableStr, businessId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static int newUnitOfMeasurement(UnitOfMeasurement measurement, String businessId) {

		checkMeasurementUnitTableExistence(businessId);

		int result = 0;
		try {
			result = UnitOfMeasurementDAOInsertion.newUnitOfMeasurement(measurement, businessId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static int updateUnitOfMeasurement(UnitOfMeasurement measurement, String businessId, BigDecimal id) {
		int result = 0;
		try {
			result = UnitOfMeasurementDAOUpdate.updateUnitOfMeasurement(measurement, businessId, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static int deleteFromMeasurementUnits(String businessId, BigDecimal id) {
		int result = 0;
		try {
			result = UnitOfMeasurementDAODeletion.deleteFromMeasurementUnits(businessId, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static List<UnitOfMeasurement> findAllUnitsOfMeasurement(String businessId) {
		checkMeasurementUnitTableExistence(businessId);
		List<UnitOfMeasurement> list = new ArrayList<>();
		try {
			list = UnitOfMeasurementDAORetreive.findAllUnitsOfMeasurement(businessId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public static void main(String[] args) throws Exception {
		UnitOfMeasurement measurement = new UnitOfMeasurement(BigDecimal.ONE, "kg");

		UnitOfMeasurementDAO.newUnitOfMeasurement(measurement, "16");

		// UnitOfMeasurement measurement = new UnitOfMeasurement();
		// measurement.setId(BigDecimal.ONE);
		// measurement.setUnitOfMeasurement("mg");
		// measurement.setItemId(BigDecimal.ONE);

		// UnitOfMeasurementDAO.updateUnitOfMeasurement(measurement, "16", measurement.getId());

		BigDecimal id = BigDecimal.ONE;

		System.out.println(UnitOfMeasurementDAO.findAllUnitsOfMeasurement("16"));

		// UnitOfMeasurementDAO.deleteFromMeasurementUnits("16", id);

	}

}
