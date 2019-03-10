
package com.keepcount.service.numberformat ;

import java.util.List ;

import org.springframework.stereotype.Service ;

import com.keepcount.dao.pricing.PricingNumberFormatHibernation ;
import com.keepcount.model.pricing.PricingNumberFormat ;

@Service
public class NumberFormatService {

	public List < PricingNumberFormat > getPurchasesNumberFormat() {
		return PricingNumberFormatHibernation.getPricingNumberFortas() ;
	}

}
