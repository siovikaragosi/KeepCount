
package com.keepcount.dao.purchases ;

import java.math.BigDecimal ;
import java.util.ArrayList ;
import java.util.List ;

import com.keepcount.dao.dbutils.DBUtils ;
import com.keepcount.model.purchases.PurchasesTwo ;

public class PurchasesTwoDAO {

	private static int procExisst = 0 ;

	public static int getProcExisst() {
		return procExisst ;
	}

	public static void setProcExisst( int procExisst ) {
		PurchasesTwoDAO.procExisst = procExisst ;
	}

	private static String tableName = "purchases2_" ;

	private static String createTableStr = "(id bigint primary key auto_increment"

			.concat( ",date_server datetime default current_timestamp" ).concat( ",date_client varchar(255)" )

			.concat( ",item_id bigint not null" ).concat( ",item_quantity decimal(50,5)" ).concat( ",unit_cost decimal(50,5)" ).concat( ",credit boolean" )

			.concat( ",discount_rec decimal(50,5)" ).concat( ",amount_cleared decimal(50,5)" ).concat( ",total_cost_manual decimal(50,5)" )

			.concat( ",total_cost_auto decimal(50,5)" ).concat( ",balance decimal(50,5)" ).concat( ",supplier_id bigint" ).concat( ",purchaser_dealer_id bigint" )

			.concat( ",narration longtext" )

			.concat( ",transaction_id bigint" )

			.concat( ")" ) ;

	public static int checkPurchasesTableExistence( String businessId ) {
		int result = 0 ;
		try {
			result = DBUtils.checkTableExistence( tableName , createTableStr , businessId ) ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return result ;
	}

	public static int newPurchases( PurchasesTwo purchasesTwo , String businessId ) {

		checkPurchasesTableExistence( businessId ) ;

		int result = 0 ;
		try {
			result = PurchasesTwoDAOInsertion.newPurchase( purchasesTwo , businessId ) ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return result ;
	}

	public static int updatePurchases( PurchasesTwo purchasesTwo , String businessId , BigDecimal id ) {
		int result = 0 ;
		try {
			result = PurchasesTwoDAOUpdate.updatePurchasesTwo( purchasesTwo , businessId , id ) ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return result ;
	}

	public static int deleteFromPurchases( String businessId , BigDecimal id , BigDecimal itemId , BigDecimal transactionId ) {
		int result = 0 ;
		try {
			result = PurchasesTwoDAODelete.deletePurchasesTwo( businessId , id , itemId , transactionId ) ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return result ;
	}

	public static List < PurchasesTwo > findAllPurchasesTwo( String businessId , String languageOfNumberFormat ) {
		checkPurchasesTableExistence( businessId ) ;
		List < PurchasesTwo > list = new ArrayList <>() ;
		try {
			list = PurchasesTwoDAORetreive.findAllPurchasesTwo( businessId , languageOfNumberFormat ) ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return list ;
	}

}
