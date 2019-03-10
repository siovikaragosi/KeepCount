
package com.keepcount.dao.purchases ;

import java.sql.CallableStatement ;
import java.sql.Connection ;
import java.util.LinkedHashMap ;
import java.util.List ;
import java.util.Map ;

import com.keepcount.dao.dbutils.DBUtils ;
import com.keepcount.dao.dbutils.DBUtilsTypes ;
import com.keepcount.model.purchases.PurchasesTwo ;

public class PurchasesTwoDAOInsertion {

	private static List < String > listOfAllProceduresCreated ;

	public static List < String > getListOfAllProceduresCreated() {
		return listOfAllProceduresCreated ;
	}

	public static void setListOfAllProceduresCreated( List < String > listOfAllProceduresCreated ) {
		PurchasesTwoDAOInsertion.listOfAllProceduresCreated = listOfAllProceduresCreated ;
	}

	private static List < String > listOfAllProceduresAlreadyCreated() throws Exception {
		setListOfAllProceduresCreated( DBUtils.listOfAllProceduresAlreadyCreated() ) ;
		return getListOfAllProceduresCreated() ;
	}

	private static String procName = "insert__into_purchases_" ;
	private static String tableName = "purchases2_" ;

	/*
	 * 
	 * 
	 * insertion parameters for the purchases table
	 * 
	 */
	private static Map < String , DBUtilsTypes > findMappingForInsertionParameters() {

		Map < String , DBUtilsTypes > map = new LinkedHashMap <>() ;

		map.put( "date_client" , new DBUtilsTypes( "date_client" , "varchar(255)" ) ) ;// 1
		map.put( "item_id" , new DBUtilsTypes( "item_id" , "bigint" ) ) ;// 2
		map.put( "item_quantity" , new DBUtilsTypes( "item_quantity" , "decimal(50,5)" ) ) ;// 3

		map.put( "unit_cost" , new DBUtilsTypes( "unit_cost" , "decimal(50,5)" ) ) ;// 4
		map.put( "credit" , new DBUtilsTypes( "credit" , "boolean" ) ) ;// 5
		map.put( "discount_rec" , new DBUtilsTypes( "discount_rec" , "decimal(50,5)" ) ) ;// 6

		map.put( "amount_cleared" , new DBUtilsTypes( "amount_cleared" , "decimal(50,5)" ) ) ;// 7
		map.put( "total_cost_manual" , new DBUtilsTypes( "total_cost_manual" , "decimal(50,5)" ) ) ;// 8
		map.put( "total_cost_auto" , new DBUtilsTypes( "total_cost_auto" , "decimal(50,5)" ) ) ;// 9

		map.put( "balance" , new DBUtilsTypes( "balance" , "decimal(50,5)" ) ) ;// 10
		map.put( "supplier_id" , new DBUtilsTypes( "supplier_id" , "bigint" ) ) ;
		// map.put( "purchaser_dealer_id" , new DBUtilsTypes( "purchaser_dealer_id" , "bigint" ) ) ;// 11

		map.put( "narration" , new DBUtilsTypes( "narration" , "longtext" ) ) ;// 12
		map.put( "transaction_id" , new DBUtilsTypes( "transaction_id" , "bigint" ) ) ;// 13

		return map ;

	}

	private static int procExisst = 0 ;

	public static int getProcExisst() {

		return procExisst ;
	}

	public static void setProcExisst( int procExisst ) {
		PurchasesTwoDAOInsertion.procExisst = procExisst ;
	}

	private static int insertIntoPurchasesExistence ;

	public static int getInsertIntoPurchasesExistence() {
		return insertIntoPurchasesExistence ;
	}

	public static void setInsertIntoPurchasesExistence( int insertIntoPurchasesExistence ) {
		PurchasesTwoDAOInsertion.insertIntoPurchasesExistence = insertIntoPurchasesExistence ;
	}

	public static int newPurchase( PurchasesTwo purchasesTwo , String businessId ) throws Exception {

		String procedureName = procName.concat( businessId ) ;

		if ( getListOfAllProceduresCreated() == null ) {
			listOfAllProceduresAlreadyCreated() ;
		}

		if ( !getListOfAllProceduresCreated().contains( procedureName ) ) {
			DBUtils.createInsertionProc( procName , businessId , tableName , findMappingForInsertionParameters() , getInsertIntoPurchasesExistence() ) ;
			setInsertIntoPurchasesExistence( 1 ) ;
		} else {
			setInsertIntoPurchasesExistence( 1 ) ;
		}

		int result = 0 ;
		CallableStatement cs = null ;
		Connection connection = DBUtils.getConn() ;
		connection.setAutoCommit( false ) ;
		try {

			cs = connection.prepareCall( DBUtils.invocationOfInsertAPI( findMappingForInsertionParameters() , procName , businessId ) ) ;
			cs.setString( 1 , purchasesTwo.getDateClient() ) ;
			cs.setBigDecimal( 2 , purchasesTwo.getItemId() ) ;
			cs.setBigDecimal( 3 , purchasesTwo.getItemQuantity() ) ;
			cs.setBigDecimal( 4 , purchasesTwo.getUnitCost() ) ;
			cs.setBoolean( 5 , purchasesTwo.isCredit() ) ;
			cs.setBigDecimal( 6 , purchasesTwo.getDiscountReceived() ) ;
			cs.setBigDecimal( 7 , purchasesTwo.getAmountCleared() ) ;
			cs.setBigDecimal( 8 , purchasesTwo.getTotalCostManual() ) ;
			cs.setBigDecimal( 9 , purchasesTwo.getTotalCostAuto() ) ;
			cs.setBigDecimal( 10 , purchasesTwo.getBalanceYetToBePaid() ) ;
			cs.setBigDecimal( 11 , purchasesTwo.getSupplierId() ) ;
			cs.setString( 12 , purchasesTwo.getNarration() ) ;
			cs.setBigDecimal( 13 , purchasesTwo.getTransactionId() ) ;

			if ( businessId != null ) {
				result = cs.executeUpdate() ;
				System.out.println( "res dao ins: " + result ) ;
				if ( result == 1 ) {
					connection.commit() ;
				} else {
					connection.rollback() ;
				}
			}

		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( null , cs , null , connection ) ;
		}
		return result ;
	}

}
