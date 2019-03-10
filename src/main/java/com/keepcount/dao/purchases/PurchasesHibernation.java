package com.keepcount.dao.purchases;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.keepcount.dao.suppliers.SuppliersDAO;
import com.keepcount.model.item.Item;
import com.keepcount.model.purchases.Purchases;
import com.keepcount.model.suppliers.Suppliers;

public class PurchasesHibernation {

	private static List<Purchases> purchases;
	private static List<Item> allItems;
	private static List<Suppliers> allSuppliers;

	private static int tableCreated = 0;

	private static int checkTableExistence(String businessId) {
		int result = 0;
		try {
			if (getTableCreated() == 0) {
				PurchasesDAO.checkPurchasesTableExistence(businessId);
				setTableCreated(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	public static int newPurchase(String businessId, Purchases purchase) {
		int result = 0;
		checkTableExistence(businessId);
		try {
			result = PurchasesDAO.newPurchase(businessId, purchase);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static int deleteFromPurchases(String businessId, BigDecimal id) {
		int result = 0;
		try {
			result = PurchasesDAODeletion.deleteFromPurchases(businessId, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static int updatePurchases(String businessId, Purchases purchases, BigDecimal id) {
		int result = 0;
		try {
			result = PurchasesDAOUpdate.updatePurchase(businessId, purchases, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static List<Purchases> findAllPurchasesDirect(String businessId, String numberFormat) {
		checkTableExistence(businessId);
		List<Purchases> list = new ArrayList<>();
		try {
			list = PurchasesDAO.findAllPurchases(businessId, numberFormat);
			setPurchases(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	private static void findAllItemsWithin(String businessId) {
		List<Item> items = new ArrayList<>();
		try {
			items = PurchasesDAO.findAllItemssPerBusiness(businessId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAllItems(items);
	}

	public static List<Item> findAllitemsWithRefreshing(String businessId) {
		findAllItemsWithin(businessId);
		System.out.println("refreshed items");
		return getAllItems();
	}

	public static List<Item> findAllItemsWithoutRefreshing() {
		System.out.println(" not refreshed items");
		return getAllItems();
	}

	public static List<Purchases> findAllPurchasesNotDirect(String businessId, String numberFormat) {
		return getPurchases();
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

	public static List<Purchases> getPurchases() {
		return purchases;
	}

	public static void setPurchases(List<Purchases> purchases) {
		PurchasesHibernation.purchases = purchases;
	}

	public static int getTableCreated() {
		return tableCreated;
	}

	public static void setTableCreated(int tableCreated) {
		PurchasesHibernation.tableCreated = tableCreated;
	}

	public static List<Item> getAllItems() {
		return allItems;
	}

	public static void setAllItems(List<Item> allItems) {
		PurchasesHibernation.allItems = allItems;
	}

	public static List<Suppliers> getAllSuppliers() {
		return allSuppliers;
	}

	public static void setAllSuppliers(List<Suppliers> allSuppliers) {
		PurchasesHibernation.allSuppliers = allSuppliers;
	}

}
