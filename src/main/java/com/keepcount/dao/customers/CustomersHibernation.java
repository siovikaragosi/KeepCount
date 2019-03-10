package com.keepcount.dao.customers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.keepcount.model.customers.Customers;

public class CustomersHibernation {

	private static List<Customers> customers;
	private static int tableCreated = 0;

	private static int checkTableCreated(String businessId) {
		int result = 0;
		try {
			result = CustomersDAO.checkTableCustomersExistence(businessId);
			setTableCreated(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static int createNewCustomer(Customers customer, String businessId) {
		int result = 0;

		try {
			if (getTableCreated() == 0) {
				checkTableCreated(businessId);
				result = CustomersDAO.createNewCustomer(customer, businessId);
			} else if (getTableCreated() == 1) {
				result = CustomersDAO.createNewCustomer(customer, businessId);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
		String bizEm = "b5alimahmoudraage@gmail.com";
		CustomersHibernation.createNewCustomer(new Customers("1", "2", "3", "4"), bizEm);
		System.out.println(findAllCustomersDirect(bizEm));
	}

	public static int updateCustomer(String businessId, Customers customer, BigDecimal id) {
		int result = 0;
		try {
			result = CustomersDAO.updateCustomer(businessId, customer, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static int deleteCustomer(String businessId, BigDecimal id) {
		int result = 0;
		try {
			result = CustomersDAO.deleteCustomer(businessId, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static List<Customers> findAllCustomersDirect(String businessId) {
		List<Customers> customerss = new ArrayList<>();
		if (getTableCreated() == 0) {
			checkTableCreated(businessId);
		} else {
			try {
				customerss = CustomersDAO.findAllCustomers(businessId);
				setCustomers(customerss);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return customerss;
	}

	public static List<Customers> findAllCustomersNotDirect() {
		return getCustomers();
	}

	public static List<Customers> getCustomers() {
		return customers;
	}

	public static void setCustomers(List<Customers> customers) {
		CustomersHibernation.customers = customers;
	}

	public static int getTableCreated() {
		return tableCreated;
	}

	public static void setTableCreated(int tableCreated) {
		CustomersHibernation.tableCreated = tableCreated;
	}

}
