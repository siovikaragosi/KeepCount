
package com.keepcount.dao.pricing ;

import java.math.BigDecimal ;
import java.util.ArrayList ;
import java.util.HashMap ;
import java.util.LinkedHashMap ;
import java.util.List ;
import java.util.Map ;

import com.keepcount.model.item.Item ;
import com.keepcount.model.pricing.Pricing ;

public class PricingHibernation {

	private static List < Item > allItems ;
	private static List < Pricing > pricings ;

	private static HashMap < String , Item > itemsToHelpFindItemID ;
	private static HashMap < BigDecimal , Item > idKeyItemValueMap ;

	public static int newPricing( String businessId , Pricing pricing ) {
		int result = 0 ;
		result = PricingDAO.newPrice( businessId , pricing ) ;
		return result ;
	}

	public static int updatePricing( String businessId , Pricing pricing , BigDecimal id ) {
		int result = 0 ;
		result = PricingDAO.updatePricing( businessId , pricing , id ) ;
		return result ;
	}

	public static int deletePricing( String businessId , BigDecimal id ) {
		int result = 0 ;
		result = PricingDAO.deletePricing( businessId , id ) ;
		return result ;
	}

	public static List < Pricing > findAllPricingDirect( String businessId , String numberFormat ) {
		List < Pricing > pricings = new ArrayList <>() ;
		pricings = PricingDAO.findAllPricings( businessId , numberFormat ) ;
		setPricings( pricings ) ;
		return getPricings() ;
	}

	public static List < Pricing > findAllPricingNotDirect( String businessId ) {
		return getPricings() ;
	}

	private void findAllItemsWithin( String businessId ) {
		List < Item > items = new ArrayList <>() ;
		try {
			items = PricingDAO.findAllItemssPerBusiness( businessId ) ;
			setAllItems( items ) ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		}

	}

	public List < Item > findAllitemsWithRefreshing( String businessId ) {
		findAllItemsWithin( businessId ) ;
		return getAllItems() ;
	}

	public static List < Item > findAllItemsWithoutRefreshing() {
		System.out.println( " not refreshed items" ) ;
		return getAllItems() ;
	}

	public static HashMap < String , Item > findAllItemsToHelpFindItemID_Direct( String businessId ) {
		HashMap < String , Item > hashMap = new HashMap <>() ;
		try {
			hashMap = PricingDAO.findAllItemSubCategorisAndIDs( businessId ) ;
			setItemsToHelpFindItemID( hashMap ) ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return hashMap ;
	}

	/* STEPS:: */
	/* FIRST OF, GET THE ID FROM THE CLIENT */
	/* USE THIS ID TO GET THE INDEX OF ITSELF IN A SORTED ID LIST */
	/* THEN USE THIS INDEX TO GET THE VERY SAME ID FROM THE ID LIST */

	/* THEN USE THIS ID GOT TO GET THE INDEX OF THE ITEM WHOSE NAME IS REQUIRED */
	// OR OR OR
	/*
	 * THEN USE THIS ID TO GET THE REQUIRED ITEM NAME FROM A MAP OF KEY ID AND VALUE
	 * ITEM
	 */

	public static HashMap < String , Item > findAllItemsToHelpFindItemID_Not_Direct() {
		return getItemsToHelpFindItemID() ;
	}

	public static HashMap < BigDecimal , Item > findItemNameBasingOnIDMappingDirect( String businessId ) {
		HashMap < BigDecimal , Item > hashMap = new HashMap <>() ;
		try {
			hashMap = PricingDAO.findItemNameBasingOnIDMapping( businessId ) ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		setIdKeyItemValueMap( hashMap ) ;
		return hashMap ;
	}

	public static HashMap < BigDecimal , Item > findItemNameBasingOnIDMappingNotDirect() {
		return getIdKeyItemValueMap() ;
	}

	public static List < Item > getItemWhoseIDisRequiredForEditing() {
		return PricingDAO.getItemWhoseIDisRequiredForEditing() ;
	}

	public static List < Item > getListOfItemsToExtractNameForEditing( List < Item > itemsToExtractIDsFrom , BigDecimal idSearchable , HashMap < BigDecimal , Item > hashMap ,
			List < BigDecimal > IDs ) {
		List < Item > items = new ArrayList <>() ;
		try {
			items = PricingDAO.getListOfItemsToExtractNameForEditing( itemsToExtractIDsFrom , idSearchable , hashMap , IDs ) ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return items ;
	}

	public static List < Item > getAllItems() {
		return allItems ;
	}

	public static void setAllItems( List < Item > allItems ) {
		PricingHibernation.allItems = allItems ;
	}

	public static List < Pricing > getPricings() {
		return pricings ;
	}

	public static void setPricings( List < Pricing > pricings ) {
		PricingHibernation.pricings = pricings ;
	}

	public static HashMap < String , Item > getItemsToHelpFindItemID() {
		return itemsToHelpFindItemID ;
	}

	public static void setItemsToHelpFindItemID( HashMap < String , Item > itemsToHelpFindItemID ) {
		PricingHibernation.itemsToHelpFindItemID = itemsToHelpFindItemID ;
	}

	public static HashMap < BigDecimal , Item > getIdKeyItemValueMap() {
		return idKeyItemValueMap ;
	}

	public static void setIdKeyItemValueMap( HashMap < BigDecimal , Item > idKeyItemValueMap ) {
		PricingHibernation.idKeyItemValueMap = idKeyItemValueMap ;
	}

	public static List < Item > listOfAllItemsWhosePricesAreNotYetSet() {

		Map < BigDecimal , Pricing > itemIdToPricingMap = new LinkedHashMap <>() ;
		List < BigDecimal > itemIDsInPricingTable = new ArrayList <>() ;
		for ( Pricing pricing : PricingHibernation.getPricings() ) {
			BigDecimal anItemIdInAPriceRow = pricing.getItemId() ;
			itemIdToPricingMap.put( anItemIdInAPriceRow , pricing ) ;
			itemIDsInPricingTable.add( anItemIdInAPriceRow ) ;
		}

		System.out.println( "Item IDs In Pricings: " + itemIDsInPricingTable ) ;

		Map < BigDecimal , Item > itemIdMappedToItem = new LinkedHashMap <>() ;
		List < BigDecimal > itemIDsInItemsTable = new ArrayList <>() ;
		for ( Item item : PricingHibernation.findAllItemsWithoutRefreshing() ) {
			BigDecimal anItemIdInItemRow = item.getId() ;
			itemIdMappedToItem.put( anItemIdInItemRow , item ) ;
			itemIDsInItemsTable.add( anItemIdInItemRow ) ;
		}
		System.out.println( "Item IDs In item: " + itemIDsInItemsTable ) ;
		itemIDsInItemsTable.removeAll( itemIDsInPricingTable ) ;
		System.out.println( "ididididid:  " + itemIDsInItemsTable ) ;

		List < Item > itemsWhosePricesAreNotYetSet = new ArrayList <>() ;

		for ( BigDecimal idOfItemWhosePriceIsNotYetSet : itemIDsInItemsTable ) {
			BigDecimal id = idOfItemWhosePriceIsNotYetSet ;
			Item item = itemIdMappedToItem.get( id ) ;
			itemsWhosePricesAreNotYetSet.add( item ) ;
		}

		return itemsWhosePricesAreNotYetSet ;
	}

}
