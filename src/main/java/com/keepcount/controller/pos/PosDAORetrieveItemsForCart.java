package com.keepcount.controller.pos;

import java.util.ArrayList;
import java.util.List;

import com.keepcount.dao.pricing.PricingDAORetrieval;
import com.keepcount.model.pricing.Pricing;

public class PosDAORetrieveItemsForCart {

	public static List<Pricing> findAllPricings(String businessId, String numberFormat) {
		List<Pricing> pricings = new ArrayList<>();
		try {
			pricings = PricingDAORetrieval.findAllPricings(businessId, numberFormat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pricings;
	}

}
