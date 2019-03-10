
package com.keepcount.dao.pos ;

import java.math.BigDecimal ;
import java.util.ArrayList ;
import java.util.LinkedHashMap ;
import java.util.List ;
import java.util.Map ;
import java.util.Map.Entry ;

import com.keepcount.dao.item.ItemHibernation ;
import com.keepcount.dao.item.UnitOfMeasurementHibernation ;
import com.keepcount.dao.pricing.PricingHibernation ;
import com.keepcount.model.item.Item ;
import com.keepcount.model.item.UnitOfMeasurement ;
import com.keepcount.model.pos.AddToCart ;
import com.keepcount.model.pricing.Pricing ;

public class CartDAORetreival {

	private static Map < UnitOfMeasurement , Item > mapForAddingToCart ;

	private static List < BigDecimal > itemIDsAddToCart ;
	private static List < String > itemNamesAddToCart ;
	private static List < String > itemSubCategoriesAddToCart ;
	private static List < BigDecimal > pricesAddToCart ;
	private static List < String > unitsOfMeasurementAddToCart ;

	private static Map < UnitOfMeasurement , Item > findCartForChoosingToAddToCart_Not_Direct( String businessId ) {

		// if (getMapForAddingToCart() == null) {
		// System.out.println("Direct cart");
		setMapForAddingToCart( findCartForChoosingToAddToCart_Direct( businessId ) ) ;
		return findCartForChoosingToAddToCart_Direct( businessId ) ;

		// } else {
		// System.out.println("Not Direct cart");
		// return getMapForAddingToCart();
		// }

	}

	private static Map < BigDecimal , Item > findAllItemsAndTheirRespectiveIDs( String businessId ) {
		Map < BigDecimal , Item > itemsAndTheirRespectiveIDsMapped = new LinkedHashMap <>() ;
		List < Item > itemListOrdinary = ItemHibernation.findAllitemsWithRefreshing( businessId ) ;
		for ( Item item : itemListOrdinary ) {
			itemsAndTheirRespectiveIDsMapped.put( item.getId() , item ) ;
		}
		return itemsAndTheirRespectiveIDsMapped ;
	}

	private static Map < BigDecimal , UnitOfMeasurement > findAllItemsUnitsOfMeasurementAndTheirMapped( String businessId ) {
		Map < BigDecimal , UnitOfMeasurement > map = new LinkedHashMap <>() ;
		List < UnitOfMeasurement > measurements = UnitOfMeasurementHibernation.findUnitsOfMeasurement_Direct( businessId ) ;
		for ( UnitOfMeasurement measurement : measurements ) {
			map.put( measurement.getItemId() , measurement ) ;
		}
		return map ;
	}

	private static Map < BigDecimal , Pricing > findAllItemsPricingsAndTheirRespectiveIDsMapped( String businessId ) {
		Map < BigDecimal , Pricing > map = new LinkedHashMap <>() ;
		List < Pricing > pricings = PricingHibernation.findAllPricingNotDirect( businessId ) ;
		if ( pricings == null ) {
			pricings = PricingHibernation.findAllPricingDirect( businessId , "English" ) ;
		}
		for ( Pricing pricing : pricings ) {
			map.put( pricing.getItemId() , pricing ) ;
		}
		return map ;
	}

	public static Map < BigDecimal , AddToCart > findAllItemsToFinallyBeAddedToCartListForChoosingByTheSellerOrBuyer( String businessId ) {

		Map < BigDecimal , AddToCart > map = new LinkedHashMap <>() ;

		// here, the itemId's have to be matched and tested. Then and only then will the final list of items to be added to cart can be shown

		/* First off, add all items from the item list to the AddToCart by specify the names of the fields */
		// private BigDecimal id;
		// private BigDecimal itemId;
		// private String item;
		// private String itemSubCategory;
		// private BigDecimal itemQuantity;
		// private String itemQuantityStr;
		// private BigDecimal price;
		// private String priceStr;
		// private String unitOfMeasurement;

		// putting only IDs of all items from the entire item list
		Map < BigDecimal , Item > itemsToGetNamesOnly = new LinkedHashMap <>() ;
		for ( Entry < BigDecimal , Item > item : findAllItemsAndTheirRespectiveIDs( businessId ).entrySet() ) {

			itemsToGetNamesOnly.put( item.getValue().getId() , item.getValue() ) ;

			AddToCart addToCart = new AddToCart() ;
			BigDecimal itemId = item.getValue().getId() ;
			String itemSubCategory = item.getValue().getItemSubCategory() ;

			addToCart.setItemId( itemId ) ;
			addToCart.setItemSubCategory( itemSubCategory ) ;
			map.put( item.getKey() , addToCart ) ;
		}

		for ( Entry < BigDecimal , Pricing > pricing : findAllItemsPricingsAndTheirRespectiveIDsMapped( businessId ).entrySet() ) {
			BigDecimal itemPrice = pricing.getValue().getPrice() ;
			BigDecimal itemId = pricing.getValue().getItemId() ;
			if ( map.get( itemId ) != null ) {
				map.get( itemId ).setPrice( itemPrice ) ;
			}
		}

		Map < BigDecimal , AddToCart > mappedCart = new LinkedHashMap <>() ;

		for ( Entry < BigDecimal , UnitOfMeasurement > unitOfMeasurement : findAllItemsUnitsOfMeasurementAndTheirMapped( businessId ).entrySet() ) {
			BigDecimal itemId = unitOfMeasurement.getValue().getItemId() ;
			String unitOfMeasurementOfTheParticularItem = unitOfMeasurement.getValue().getUnitOfMeasurement() ;
			if ( ( map.get( itemId ) != null ) && ( map.get( itemId ).getPrice() != null ) ) {

				map.get( itemId ).setItem( itemsToGetNamesOnly.get( itemId ).getItemName() ) ; // setting the name of the AddToCart

				map.get( itemId ).setUnitOfMeasurement( unitOfMeasurementOfTheParticularItem ) ;
				mappedCart.put( itemId , map.get( itemId ) ) ;
			} else if ( ( map.get( itemId ) != null ) && ( map.get( itemId ).getPrice() == null ) ) {
				map.remove( itemId ) ;
				System.out.println( "rem" ) ;
			}

		}

		for ( Entry < BigDecimal , AddToCart > cartList : mappedCart.entrySet() ) {
			System.out.println( cartList.getValue().getItemId() + "--" + cartList.getValue().getPrice() + "--" + cartList.getValue().getUnitOfMeasurement() ) ;
		}
		for ( Entry < BigDecimal , AddToCart > cartList : mappedCart.entrySet() ) {

		}

		return mappedCart ;
	}

	public static void main( String [ ] args ) {
		System.out.println( "Prices: " + findAllItemsToFinallyBeAddedToCartListForChoosingByTheSellerOrBuyer( "16" ).keySet() ) ;
		// System.out.println( "Item IDs raw: " + findAllItemsAndTheirRespectiveIDs( "16" ).keySet() ) ;
		// System.out.println( "Unit IDs raw: " + findAllItemsUnitsOfMeasurementAndTheirMapped( "16" ).keySet() ) ;
		// System.out.println( "Price IDs raw: " + findAllItemsPricingsAndTheirRespectiveIDsMapped( "16" ).keySet() ) ;
	}

	private static Map < UnitOfMeasurement , Item > findCartForChoosingToAddToCart_Direct( String businessId ) {

		Map < UnitOfMeasurement , Item > map = new LinkedHashMap <>() ;

		List < UnitOfMeasurement > allUnitsOfMeasurement = UnitOfMeasurementHibernation.findUnitsOfMeasurement_Direct( businessId ) ;
		List < Item > allItems = ItemHibernation.findAllitemsWithRefreshing( businessId ) ;
		List < BigDecimal > itemIDsFromMeasurementUnits = new ArrayList <>() ;

		Map < BigDecimal , Item > mappedIDsToItems = new LinkedHashMap <>() ;

		for ( Item item : allItems ) {
			BigDecimal id = item.getId() ;
			mappedIDsToItems.put( id , item ) ;
		}

		Map < BigDecimal , UnitOfMeasurement > mapUnitOfMeasurements = new LinkedHashMap <>() ;

		// getting item IDs that exist in both
		for ( UnitOfMeasurement unit : allUnitsOfMeasurement ) {
			BigDecimal itemId = unit.getItemId() ;
			itemIDsFromMeasurementUnits.add( itemId ) ;

			mapUnitOfMeasurements.put( unit.getItemId() , unit ) ;

		}

		// getting IDs from units of measurement that match the IDs in Items and restricting the items to only the matched IDs
		Map < BigDecimal , Item > mappedIDsToItemsFromUnitsOfMeasurement = new LinkedHashMap <>() ;

		for ( BigDecimal bigDecimal : itemIDsFromMeasurementUnits ) {
			Item item = mappedIDsToItems.get( bigDecimal ) ;
			mappedIDsToItemsFromUnitsOfMeasurement.put( bigDecimal , item ) ;
		}

		List < Pricing > allPricings = PricingHibernation.findAllPricingDirect( businessId , "" ) ;

		Map < BigDecimal , Pricing > mappedIdsToPricings = new LinkedHashMap <>() ;
		for ( Pricing pricing : allPricings ) {
			mappedIdsToPricings.put( pricing.getItemId() , pricing ) ;
		}

		// getting IDs from the items that have whose IDs have been obtained from the units of measurement
		Map < BigDecimal , Pricing > mappedIDsToPricingsFromUnitsOfMeasurement = new LinkedHashMap <>() ;

		for ( Map.Entry < BigDecimal , Item > entry : mappedIDsToItemsFromUnitsOfMeasurement.entrySet() ) {
			Pricing pricing = mappedIdsToPricings.get( entry.getKey() ) ;
			mappedIDsToPricingsFromUnitsOfMeasurement.put( entry.getKey() , pricing ) ;
		}

		List < AddToCart > addToCarts = new ArrayList <>() ;

		AddToCart addToCart = new AddToCart() ;

		List < BigDecimal > addTocart_ItemIDs = new ArrayList <>() ;
		List < String > addTocart_Items = new ArrayList <>() ;
		List < String > addTocart_ItemSubCategorys = new ArrayList <>() ;
		List < BigDecimal > addTocart_Prices = new ArrayList <>() ;
		List < String > addTocart_Units = new ArrayList <>() ;

		if ( mappedIDsToItemsFromUnitsOfMeasurement.isEmpty() ) {
			System.out.println( "list is empty........................." ) ;
		} else {
			System.out.println( "list not empty........................" ) ;
		}

		for ( Map.Entry < BigDecimal , Item > entry : mappedIDsToItemsFromUnitsOfMeasurement.entrySet() ) {

			// System.out.println( "Name: " + entry.getValue().getItemName() ) ;

			System.out.println( "entry......: " + entry.getValue().getItemName() ) ;

			BigDecimal itemId = entry.getKey() ;
			String item = entry.getValue().getItemName() ;
			String itemSubCat = entry.getValue().getItemSubCategory() ;

			addToCart.setItemId( itemId ) ;
			addToCart.setItem( item ) ;
			addToCart.setItemSubCategory( itemSubCat ) ;

			addToCarts.add( addToCart ) ;

			addTocart_ItemIDs.add( itemId ) ;
			addTocart_Items.add( item ) ;
			addTocart_ItemSubCategorys.add( itemSubCat ) ;

		}

		setItemIDsAddToCart( addTocart_ItemIDs ) ;
		setItemNamesAddToCart( addTocart_Items ) ;
		setItemSubCategoriesAddToCart( addTocart_ItemSubCategorys ) ;

		for ( Map.Entry < BigDecimal , Pricing > entry : mappedIDsToPricingsFromUnitsOfMeasurement.entrySet() ) {

			BigDecimal price = entry.getValue().getPrice() ;

			addTocart_Prices.add( price ) ;

			Pricing pricing = mappedIdsToPricings.get( entry.getKey() ) ;
			mappedIDsToPricingsFromUnitsOfMeasurement.put( entry.getKey() , pricing ) ;

		}

		for ( Map.Entry < BigDecimal , UnitOfMeasurement > entry : mapUnitOfMeasurements.entrySet() ) {
			String unit = entry.getValue().getUnitOfMeasurement() ;
			addTocart_Units.add( unit ) ;
		}

		setPricesAddToCart( addTocart_Prices ) ;
		setUnitsOfMeasurementAddToCart( addTocart_Units ) ;

		setMapForAddingToCart( map ) ;

		return map ;
	}

	private static List < AddToCart > getAddToCartList( String businessId ) {

		findCartForChoosingToAddToCart_Not_Direct( businessId ) ;

		List < AddToCart > addToCarts = new ArrayList <>() ;

		AddToCart addToCart = new AddToCart() ;

		while ( getItemIDsAddToCart().iterator().hasNext() ) {

			for ( int i = 0 ; i < getItemIDsAddToCart().size() ; i++ ) {

				BigDecimal id2 = getItemIDsAddToCart().iterator().next() ;

				String itemName = getItemNamesAddToCart().iterator().next() ;

				String itemSubCat = getItemSubCategoriesAddToCart().iterator().next() ;

				BigDecimal price = getPricesAddToCart().iterator().next() ;

				String unit = getUnitsOfMeasurementAddToCart().iterator().next() ;

				addToCart = new AddToCart( id2 , itemName , itemSubCat , price , unit ) ;
				addToCarts.add( addToCart ) ;

				getUnitsOfMeasurementAddToCart().remove( unit ) ;
				getPricesAddToCart().remove( price ) ;
				getItemSubCategoriesAddToCart().remove( itemSubCat ) ;
				getItemNamesAddToCart().remove( itemName ) ;
				getItemIDsAddToCart().remove( id2 ) ;

			}
		}

		System.out.println( "for adding to cart" + addToCarts ) ;
		return addToCarts ;
	}

	public static Map < UnitOfMeasurement , Item > getMapForAddingToCart() {
		return mapForAddingToCart ;
	}

	public static void setMapForAddingToCart( Map < UnitOfMeasurement , Item > mapForAddingToCart ) {
		CartDAORetreival.mapForAddingToCart = mapForAddingToCart ;
	}

	public static List < BigDecimal > getItemIDsAddToCart() {
		return itemIDsAddToCart ;
	}

	public static void setItemIDsAddToCart( List < BigDecimal > itemIDsAddToCart ) {
		CartDAORetreival.itemIDsAddToCart = itemIDsAddToCart ;
	}

	public static List < String > getItemNamesAddToCart() {
		return itemNamesAddToCart ;
	}

	public static void setItemNamesAddToCart( List < String > itemNamesAddToCart ) {
		CartDAORetreival.itemNamesAddToCart = itemNamesAddToCart ;
	}

	public static List < String > getItemSubCategoriesAddToCart() {
		return itemSubCategoriesAddToCart ;
	}

	public static void setItemSubCategoriesAddToCart( List < String > itemSubCategoriesAddToCart ) {
		CartDAORetreival.itemSubCategoriesAddToCart = itemSubCategoriesAddToCart ;
	}

	public static List < BigDecimal > getPricesAddToCart() {
		return pricesAddToCart ;
	}

	public static void setPricesAddToCart( List < BigDecimal > pricesAddToCart ) {
		CartDAORetreival.pricesAddToCart = pricesAddToCart ;
	}

	public static List < String > getUnitsOfMeasurementAddToCart() {
		return unitsOfMeasurementAddToCart ;
	}

	public static void setUnitsOfMeasurementAddToCart( List < String > unitsOfMeasurementAddToCart ) {
		CartDAORetreival.unitsOfMeasurementAddToCart = unitsOfMeasurementAddToCart ;
	}

}
