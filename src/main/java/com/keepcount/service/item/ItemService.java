
package com.keepcount.service.item ;

import java.math.BigDecimal ;
import java.util.List ;

import org.springframework.stereotype.Service ;

import com.keepcount.dao.item.ItemHibernation ;
import com.keepcount.model.item.Item ;

@Service
public class ItemService {

	public int createNewItem( Item item , String businessId ) {
		return ItemHibernation.createNewItem( item , businessId ) ;
	}

	public int updateItem( BigDecimal id , String businessId , Item item ) {
		return ItemHibernation.updateitem( id , businessId , item ) ;
	}

	public int deleteItem( BigDecimal id , String businessId ) {
		return ItemHibernation.deleteItem( id , businessId ) ;
	}

	public List < Item > findAllItemsWithoutRefreshing( String businessId ) {
		return ItemHibernation.findAllItemsWithoutRefreshing( businessId ) ;
	}

	public List < Item > findAllItemsWithRefreshing( String businessId ) {
		return ItemHibernation.findAllitemsWithRefreshing( businessId ) ;
	}

}
