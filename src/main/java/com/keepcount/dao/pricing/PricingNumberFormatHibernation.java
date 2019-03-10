
package com.keepcount.dao.pricing ;

import java.util.List ;

import com.keepcount.model.pricing.PricingNumberFormat ;

public class PricingNumberFormatHibernation {

	public static List < PricingNumberFormat > getPricingNumberFortas() {
		return PricingNumberFormatDAO.getAllNumberFormats() ;
	}

}
