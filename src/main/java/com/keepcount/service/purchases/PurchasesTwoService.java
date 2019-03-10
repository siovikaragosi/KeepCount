
package com.keepcount.service.purchases ;

import java.math.BigDecimal ;
import java.util.List ;

import org.springframework.stereotype.Service ;

import com.keepcount.dao.item.ItemHibernation ;
import com.keepcount.dao.purchases.PurchasesTwoHibernation ;
import com.keepcount.dao.suppliers.SuppliersHibernation ;
import com.keepcount.dao.transaction.id.TransactionIDNumberHibernation ;
import com.keepcount.model.item.Item ;
import com.keepcount.model.purchases.PurchasesTwo ;
import com.keepcount.model.suppliers.Suppliers ;

@Service
public class PurchasesTwoService {

	public int newPurchases( PurchasesTwo purchasesTwo , String businessId ) {
		return PurchasesTwoHibernation.newPurchases( purchasesTwo , businessId ) ;
	}

	private BigDecimal getTheRequiredTransactionId() {
		return TransactionIDNumberHibernation.getTheRequiredTransactionId() ;
	}

	public BigDecimal getTheHibernatedRequiredTransactionId() {
		if ( TransactionIDNumberHibernation.getTheHibernatedRequiredTransactionId() == BigDecimal.ZERO ) {
			getTheRequiredTransactionId() ;
		}

		return TransactionIDNumberHibernation.getTheHibernatedRequiredTransactionId() ;
	}

	public List < Item > findAllItemsWithoutRefreshing( String businessId ) {
		return ItemHibernation.findAllItemsWithoutRefreshing( businessId ) ;
	}

	public List < Item > findAllItemsWithRefreshing( String businessId ) {
		return ItemHibernation.findAllitemsWithRefreshing( businessId ) ;
	}

	public List < Suppliers > findAllSupplierDirectly( String businessId ) {
		return SuppliersHibernation.findAllSuppliersDirect( businessId ) ;
	}

	public List < Suppliers > findAllSuppliersNotDirectly( String businessId ) {
		return SuppliersHibernation.findAllSuppliersNotDirectly( businessId ) ;
	}

}
