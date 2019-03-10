package com.keepcount.dao.purchases;

import java.util.List;

import com.keepcount.model.purchases.PurchasesNumberFormat;

public class PurchasesNumberFormatHibernation {

	public static List<PurchasesNumberFormat> getPurchasesNumberFortas() {
		return PurchasesNumberFormatDAO.getAllNumberFormats();
	}

}
