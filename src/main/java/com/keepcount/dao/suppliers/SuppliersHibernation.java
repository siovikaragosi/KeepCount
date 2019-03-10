package com.keepcount.dao.suppliers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.keepcount.model.suppliers.Suppliers;

public class SuppliersHibernation {

	private static List<Suppliers> allSuppliers;

	private static int tableCreated = 0;

	private static int checkTableExistence(String businessId) {
		int result = 0;

		if (getTableCreated() == 0) {
			try {
				result = SuppliersDAO.checkSuppliersTableExistence(businessId);
				setTableCreated(1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public static int createANewSupplier(Suppliers suppliers, String businessId) {
		int result = 0;
		try {
			if (getTableCreated() == 1) {
				result = SuppliersDAO.createANewSupplier(suppliers, businessId);
			} else {
				checkTableExistence(businessId);
				result = SuppliersDAO.createANewSupplier(suppliers, businessId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static int deleteSupplier(BigDecimal id, String businessId) {
		int result = 0;
		try {
			if (getTableCreated() == 1) {
				result = SuppliersDAO.deleteSupplier(id, businessId);
			} else {
				checkTableExistence(businessId);
				result = SuppliersDAO.deleteSupplier(id, businessId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static int updateSupplier(BigDecimal id, String businessId, Suppliers supplier) {
		int result = 0;
		try {
			if (getTableCreated() == 1) {
				result = SuppliersDAO.updateSupplier(id, businessId, supplier);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static List<Suppliers> findAllSuppliersDirect(String businessId) {

		checkTableExistence(businessId);

		List<Suppliers> s = new ArrayList<>();
		try {
			s = SuppliersDAO.findAllSuppliers(businessId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		setAllSuppliers(s);

		return s;
	}

	public static List<Suppliers> findAllSuppliersNotDirectly(String businessId) {
		if (getAllSuppliers() == null) {
			findAllSuppliersDirect(businessId);
		}
		checkTableExistence(businessId);
		return getAllSuppliers();
	}

	public static List<Suppliers> getAllSuppliers() {
		return allSuppliers;
	}

	public static void setAllSuppliers(List<Suppliers> allSuppliers) {
		SuppliersHibernation.allSuppliers = allSuppliers;
	}

	public static int getTableCreated() {
		return tableCreated;
	}

	public static void setTableCreated(int tableCreated) {
		SuppliersHibernation.tableCreated = tableCreated;
	}

}
