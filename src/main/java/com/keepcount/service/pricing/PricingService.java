
package com.keepcount.service.pricing ;

import java.math.BigDecimal ;
import java.util.ArrayList ;
import java.util.HashMap ;
import java.util.List ;

import org.springframework.stereotype.Service ;

import com.keepcount.dao.errorsuccess.ErrorSuccessHibernation ;
import com.keepcount.dao.pricing.PricingHibernation ;
import com.keepcount.dao.pricing.PricingNumberFormatHibernation ;
import com.keepcount.dao.pricing.PricingNumberVerificationMessageHibernation ;
import com.keepcount.model.errorsuccess.ErrorSuccess ;
import com.keepcount.model.item.Item ;
import com.keepcount.model.pricing.Pricing ;
import com.keepcount.model.pricing.PricingNumberFormat ;
import com.keepcount.model.pricing.PricingNumberVerificationMessage ;

@Service
public class PricingService {

	public int newPricing( String businessId , Pricing pricing ) {
		return PricingHibernation.newPricing( businessId , pricing ) ;
	}

	public int updatePricing( String businessId , Pricing pricing , BigDecimal id ) {
		return PricingHibernation.updatePricing( businessId , pricing , id ) ;
	}

	public int deletePricing( String businessId , BigDecimal id ) {
		return PricingHibernation.deletePricing( businessId , id ) ;
	}

	public List < Pricing > findAllPricingsDirect( String businessId , String numberFormat ) {
		return PricingHibernation.findAllPricingDirect( businessId , numberFormat ) ;
	}

	public List < Pricing > findAllPrcingNotDirect( String businessId ) {
		return PricingHibernation.findAllPricingNotDirect( businessId ) ;
	}

	public List < Item > findAllItemsWithoutRefreshing() {
		return PricingHibernation.findAllItemsWithoutRefreshing() ;
	}

	public List < Item > findAllItemsWithRefreshing( String businessId ) {
		return new PricingHibernation().findAllitemsWithRefreshing( businessId ) ;
	}

	public List < PricingNumberFormat > getPurchasesNumberFormat() {
		return PricingNumberFormatHibernation.getPricingNumberFortas() ;
	}

	public List < Item > listOfAllItemsWhosePricesAreNotYetSet() {
		return PricingHibernation.listOfAllItemsWhosePricesAreNotYetSet() ;
	}

	public List < ErrorSuccess > getSuccessMessage( String message ) {
		return ErrorSuccessHibernation.getSuccessMessage( message ) ;
	}

	public List < ErrorSuccess > findErrorSuccessMessageWithin() {
		List < ErrorSuccess > list = new ArrayList <>() ;
		ErrorSuccess errorSuccess = new ErrorSuccess() ;
		errorSuccess.setMessage( "Success" ) ;

		list.add( errorSuccess ) ;

		return list ;
	}

	public List < PricingNumberVerificationMessage > verificationMessage( String message ) {
		return PricingNumberVerificationMessageHibernation.verificationMessage( message ) ;
	}

	public HashMap < String , Item > findAllItemsToHelpFindItemID_Direct( String businessId ) {
		return PricingHibernation.findAllItemsToHelpFindItemID_Direct( businessId ) ;
	}

	public HashMap < String , Item > findAllItemsToHelpFindItemID_Not_Direct() {
		return PricingHibernation.findAllItemsToHelpFindItemID_Not_Direct() ;
	}

	public List < Item > getListOfItemsToExtractNameForEditing( List < Item > itemsToExtractIDsFrom , BigDecimal idSearchable , HashMap < BigDecimal , Item > hashMap ,
			List < BigDecimal > IDs ) {
		return PricingHibernation.getListOfItemsToExtractNameForEditing( itemsToExtractIDsFrom , idSearchable , hashMap , IDs ) ;
	}

	public HashMap < BigDecimal , Item > findItemNameBasingOnIDMappingDirect( String businessId ) {
		return PricingHibernation.findItemNameBasingOnIDMappingDirect( businessId ) ;
	}

	public HashMap < BigDecimal , Item > findItemNameBasingOnIDMappingNotDirect() {
		return PricingHibernation.findItemNameBasingOnIDMappingNotDirect() ;
	}

	public List < Item > getItemWhoseIDisRequiredForEditing() {
		return PricingHibernation.getItemWhoseIDisRequiredForEditing() ;
	}

}
