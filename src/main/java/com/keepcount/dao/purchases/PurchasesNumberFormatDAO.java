package com.keepcount.dao.purchases;

import java.util.ArrayList;
import java.util.List;

import com.keepcount.model.purchases.PurchasesNumberFormat;

public class PurchasesNumberFormatDAO {

	public static List<PurchasesNumberFormat> getAllNumberFormats() {
		List<String> numberFormats = new ArrayList<>();

		numberFormats.add("English");
		numberFormats.add("German");
		numberFormats.add("French");
		numberFormats.add("Canadian (French)");
		numberFormats.add("Italian");

		List<PurchasesNumberFormat> purchasesNumberFormats = new ArrayList<>();

		purchasesNumberFormats.add(getANumberFormat("English"));
		purchasesNumberFormats.add(getANumberFormat("German"));
		purchasesNumberFormats.add(getANumberFormat("French"));
		// purchasesNumberFormats.add(getANumberFormat("Canadian (French)"));
		// purchasesNumberFormats.add(getANumberFormat("Italian"));

		return purchasesNumberFormats;
	}

	private static PurchasesNumberFormat getANumberFormat(String numberFormat) {
		PurchasesNumberFormat purchasesNumberFormat = new PurchasesNumberFormat(numberFormat);
		return purchasesNumberFormat;
	}

}
