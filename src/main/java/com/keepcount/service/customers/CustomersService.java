package com.keepcount.service.customers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.keepcount.dao.customers.CustomersHibernation;
import com.keepcount.model.customers.Customers;

@Service
public class CustomersService {

	public int createNewCustomer(Customers customer, String businessId) {
		return CustomersHibernation.createNewCustomer(customer, businessId);
	}

	public int updateCustomer(String businessId, Customers customer, BigDecimal id) {
		return CustomersHibernation.updateCustomer(businessId, customer, id);
	}

	public int deleteCustomer(String businessId, BigDecimal id) {
		return CustomersHibernation.deleteCustomer(businessId, id);
	}

	public List<Customers> findAllCustomersDirect(String businessId) {
		return CustomersHibernation.findAllCustomersDirect(businessId);
	}

	public List<Customers> findAllCustomersNotDirect() {
		return CustomersHibernation.findAllCustomersNotDirect();
	}

}
