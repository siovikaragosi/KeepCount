package com.keepcount.service.suppliers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.keepcount.dao.suppliers.SuppliersHibernation;
import com.keepcount.model.suppliers.Suppliers;

@Service
public class SuppliersService {

	public int createNewSupplier(Suppliers s, String businessId) {
		return SuppliersHibernation.createANewSupplier(s, businessId);
	}

	public int deleteSupplier(BigDecimal id, String businessId) {
		return SuppliersHibernation.deleteSupplier(id, businessId);
	}

	public int updateSupplier(BigDecimal id, String businessId, Suppliers supplier) {
		return SuppliersHibernation.updateSupplier(id, businessId, supplier);
	}

	public List<Suppliers> findAllSupplierDirectly(String businessId) {
		return SuppliersHibernation.findAllSuppliersDirect(businessId);
	}

	public List<Suppliers> findAllSuppliersNotDirectly(String businessId) {
		return SuppliersHibernation.findAllSuppliersNotDirectly(businessId);
	}

}
