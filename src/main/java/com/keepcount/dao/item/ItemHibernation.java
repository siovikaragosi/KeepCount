
package com.keepcount.dao.item ;

import java.math.BigDecimal ;
import java.util.ArrayList ;
import java.util.List ;

import com.keepcount.model.item.Item ;

public class ItemHibernation {

	private static List < Item > allItems ;
	private static int createItemTable = 0 ;

	private static int checkTableExistence( String businessId ) {
		int result = 0 ;
		if ( getCreateItemTable() == 0 ) {
			try {
				result = ItemDAO.checkItemsTableExistence( businessId ) ;
				setCreateItemTable( 1 ) ;
				System.out.println( "checking table items" ) ;
			} catch ( Exception e ) {
				e.printStackTrace() ;
			}
		}

		return result ;
	}

	private static int createNewItemWithin( Item item , String businessId ) {
		checkTableExistence( businessId ) ;
		int result = 0 ;
		try {
			result = ItemDAO.createNewItem( item , businessId ) ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return result ;
	}

	public static int createNewItem( Item item , String businessId ) {
		int result = 0 ;
		result = createNewItemWithin( item , businessId ) ;
		return result ;
	}

	private static int updateItemWithin( BigDecimal id , String businessId , Item item ) {
		checkTableExistence( businessId ) ;
		int result = 0 ;
		try {
			result = ItemDAO.updateItem( id , businessId , item ) ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return result ;
	}

	public static int updateitem( BigDecimal id , String businessId , Item item ) {
		int result = 0 ;
		result = updateItemWithin( id , businessId , item ) ;
		return result ;
	}

	private static int deleteItemWithin( BigDecimal id , String businessId ) {
		checkTableExistence( businessId ) ;
		int result = 0 ;
		try {
			result = ItemDAO.deleteItemById( id , businessId ) ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return result ;
	}

	public static int deleteItem( BigDecimal id , String businessId ) {
		return deleteItemWithin( id , businessId ) ;
	}

	private static void findAllItemsWithin( String businessId ) {
		checkTableExistence( businessId ) ;
		List < Item > items = new ArrayList <>() ;
		try {
			items = ItemDAO.findAllItemssPerBusiness( businessId ) ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		setAllItems( items ) ;
	}

	public static List < Item > findAllitemsWithRefreshing( String businessId ) {
		findAllItemsWithin( businessId ) ;
		System.out.println( "refreshed items" ) ;
		return getAllItems() ;
	}

	public static List < Item > findAllItemsWithoutRefreshing( String businessId ) {
		System.out.println( " not refreshed items" ) ;
		if ( getAllItems() == null ) {
			findAllItemsWithin( businessId ) ;
			return getAllItems() ;
		} else {
			return getAllItems() ;
		}

	}

	public static List < Item > getAllItems() {
		return allItems ;
	}

	public static void setAllItems( List < Item > allItems ) {
		ItemHibernation.allItems = allItems ;
	}

	public static int getCreateItemTable() {
		return createItemTable ;
	}

	public static void setCreateItemTable( int createItemTable ) {
		ItemHibernation.createItemTable = createItemTable ;
	}

}
