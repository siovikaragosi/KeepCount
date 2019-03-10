
package com.keepcount.dao.pricing ;

import java.util.ArrayList ;
import java.util.List ;

import com.keepcount.model.pricing.PricingNumberFormat ;

public class PricingNumberFormatDAO {
	public static List < PricingNumberFormat > getAllNumberFormats() {
		List < String > numberFormats = new ArrayList <>() ;

		numberFormats.add( "English" ) ;
		numberFormats.add( "German" ) ;
		numberFormats.add( "French" ) ;
		numberFormats.add( "Canadian (French)" ) ;
		numberFormats.add( "Italian" ) ;

		List < PricingNumberFormat > pricingNumberFormats = new ArrayList <>() ;

		pricingNumberFormats.add( getANumberFormat( "English" ) ) ;
		pricingNumberFormats.add( getANumberFormat( "German" ) ) ;
		pricingNumberFormats.add( getANumberFormat( "French" ) ) ;
		// pricingNumberFormats.add(getANumberFormat("Canadian (French)"));
		// pricingNumberFormats.add(getANumberFormat("Italian"));

		return pricingNumberFormats ;
	}

	private static PricingNumberFormat getANumberFormat( String numberFormat ) {
		PricingNumberFormat pricingNumberFormat = new PricingNumberFormat( numberFormat ) ;
		return pricingNumberFormat ;
	}

}
