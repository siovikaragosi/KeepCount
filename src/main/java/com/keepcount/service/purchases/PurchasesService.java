package com.keepcount.service.purchases;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.keepcount.dao.purchases.PurchasesHibernation;
import com.keepcount.dao.purchases.PurchasesNumberFormatHibernation;
import com.keepcount.model.item.Item;
import com.keepcount.model.purchases.Purchases;
import com.keepcount.model.purchases.PurchasesNumberFormat;
import com.keepcount.model.suppliers.Suppliers;

@Service
public class PurchasesService {

	public List<PurchasesNumberFormat> getPurchasesNumberFormat() {
		return PurchasesNumberFormatHibernation.getPurchasesNumberFortas();
	}

	public int newPurchase(String businessId, Purchases purchase) {
		return PurchasesHibernation.newPurchase(businessId, purchase);
	}

	public int updatePurchase(String businessId, Purchases purchases, BigDecimal id) {
		return PurchasesHibernation.updatePurchases(businessId, purchases, id);
	}

	public int deleteFromPurchases(String businessId, BigDecimal id) {
		return PurchasesHibernation.deleteFromPurchases(businessId, id);
	}

	public List<Purchases> findAllPurchasesDirect(String businessId, String numberFormat) {
		return PurchasesHibernation.findAllPurchasesDirect(businessId, numberFormat);
	}

	public List<Purchases> findAllPurchasesNotDirect(String businessId, String numberFormat) {
		return PurchasesHibernation.findAllPurchasesNotDirect(businessId, numberFormat);
	}

	public List<Item> findAllItemsWithoutRefreshing() {
		return PurchasesHibernation.findAllItemsWithoutRefreshing();
	}

	public List<Item> findAllItemsWithRefreshing(String businessId) {
		return PurchasesHibernation.findAllitemsWithRefreshing(businessId);
	}

	public List<Suppliers> findAllSuppliersNotDirectly(String businessId) {
		return PurchasesHibernation.findAllSuppliersNotDirectly(businessId);
	}

	public List<Suppliers> findAllSuppliersDirect(String businessId) {
		return PurchasesHibernation.findAllSuppliersDirect(businessId);
	}

}
