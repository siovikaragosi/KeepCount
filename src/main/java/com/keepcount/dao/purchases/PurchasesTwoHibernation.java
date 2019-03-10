
package com.keepcount.dao.purchases ;

import java.util.ArrayList ;
import java.util.List ;

import com.keepcount.dao.dbutils.NumberFormatting ;
import com.keepcount.model.purchases.PurchasesTwo ;

public class PurchasesTwoHibernation {

	public static int newPurchases( PurchasesTwo purchasesTwo , String businessId ) {
		int result = 0 ;
		result = PurchasesTwoDAO.newPurchases( purchasesTwo , businessId ) ;
		return result ;
	}

	public static List < PurchasesTwo > findAllPurchasesTwoUponRefreshing( String businessId , String numberFormat , String languageOfNumberFormat ) {

		List < PurchasesTwo > purchasesTwos = new ArrayList <>() ;

		purchasesTwos = PurchasesTwoDAO.findAllPurchasesTwo( businessId , languageOfNumberFormat ) ;

		List < PurchasesTwo > purchasesTwosToBeFinallyReturned = new ArrayList <>() ;

		for ( PurchasesTwo twos : purchasesTwos ) {

			String itemQuantityStr = NumberFormatting.formattedBigDecimalIntoString( twos.getItemQuantity() , languageOfNumberFormat ) ;
			twos.setItemQuantityStr( itemQuantityStr ) ;

			String unitCostStr = NumberFormatting.formattedBigDecimalIntoString( twos.getUnitCost() , languageOfNumberFormat ) ;
			twos.setUnitCostStr( unitCostStr ) ;

			String discountReceivedStr = NumberFormatting.formattedBigDecimalIntoString( twos.getDiscountReceived() , languageOfNumberFormat ) ;
			twos.setDicountReceivedStr( discountReceivedStr ) ;

			String amountClearedStr = NumberFormatting.formattedBigDecimalIntoString( twos.getAmountCleared() , languageOfNumberFormat ) ;
			twos.setAmountClearedStr( amountClearedStr ) ;

			String totalCostManualStr = NumberFormatting.formattedBigDecimalIntoString( twos.getTotalCostManual() , languageOfNumberFormat ) ;
			twos.setTotalCostManualStr( totalCostManualStr ) ;

			String totalCostAutoStr = NumberFormatting.formattedBigDecimalIntoString( twos.getTotalCostAuto() , languageOfNumberFormat ) ;
			twos.setTotalCostAutoStr( totalCostAutoStr ) ;

			String balanceStr = NumberFormatting.formattedBigDecimalIntoString( twos.getBalanceYetToBePaid() , languageOfNumberFormat ) ;
			twos.setBalanceYetToBePaidStr( balanceStr ) ;

			purchasesTwosToBeFinallyReturned.add( twos ) ;

		}

		return purchasesTwosToBeFinallyReturned ;
	}

}
