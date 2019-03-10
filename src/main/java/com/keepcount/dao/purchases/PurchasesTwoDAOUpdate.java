
package com.keepcount.dao.purchases ;

import java.math.BigDecimal ;
import java.sql.CallableStatement ;
import java.sql.Connection ;
import java.util.ArrayList ;
import java.util.LinkedHashMap ;
import java.util.List ;
import java.util.Map ;

import com.keepcount.dao.dbutils.DBUtils ;
import com.keepcount.dao.dbutils.DBUtilsTypes ;
import com.keepcount.model.purchases.PurchasesTwo ;

public class PurchasesTwoDAOUpdate {

	private static List < String > listOfAllProceduresCreated ;

	public static List < String > getListOfAllProceduresCreated() {
		return listOfAllProceduresCreated ;
	}

	public static void setListOfAllProceduresCreated( List < String > listOfAllProceduresCreated ) {
		PurchasesTwoDAOUpdate.listOfAllProceduresCreated = listOfAllProceduresCreated ;
	}

	private static List < String > listOfAllProceduresAlreadyCreated() throws Exception {
		setListOfAllProceduresCreated( DBUtils.listOfAllProceduresAlreadyCreated() ) ;
		return getListOfAllProceduresCreated() ;
	}

	private static String procName = "update_purchases_" ;
	private static String tableName = "purchases_" ;

	private static String whereCondition() {
		return " WHERE id = in_id AND item_id = in_item_id AND transaction_id = in_transaction_id" ;
	}

	private static List < String > whereList() {
		List < String > list = new ArrayList <>() ;
		// list.add(" WHERE ");
		list.add( "id = in_id" ) ;
		list.add( "item_id = in_item_id" ) ;
		list.add( "transaction_id = in_transaction_id" ) ;
		return list ;
	}

	private static Map < String , DBUtilsTypes > findMappingForUpdateParameters() {

		Map < String , DBUtilsTypes > map = new LinkedHashMap <>() ;

		map.put( "id" , new DBUtilsTypes( "id" , "bigint" ) ) ;
		map.put( "item_id" , new DBUtilsTypes( "item_id" , "bigint" ) ) ;
		map.put( "item_quantity" , new DBUtilsTypes( "item_quantity" , "decimal(50,5)" ) ) ;
		map.put( "unit_cost" , new DBUtilsTypes( "unit_cost" , "decimal(50,5)" ) ) ;
		map.put( "credit" , new DBUtilsTypes( "credit" , "boolean" ) ) ;
		map.put( "discount_rec" , new DBUtilsTypes( "discount_rec" , "decimal(50,5)" ) ) ;
		map.put( "amount_cleared" , new DBUtilsTypes( "amount_cleared" , "decimal(50,5)" ) ) ;
		map.put( "total_cost_manual" , new DBUtilsTypes( "total_cost_manual" , "decimal(50,5)" ) ) ;
		map.put( "total_cost_auto" , new DBUtilsTypes( "total_cost_auto" , "decimal(50,5)" ) ) ;
		map.put( "balance" , new DBUtilsTypes( "balance" , "decimal(50,5)" ) ) ;
		map.put( "supplier_id" , new DBUtilsTypes( "supplier_id" , "bigint" ) ) ;
		map.put( "purchaser_dealer_id" , new DBUtilsTypes( "purchaser_dealer_id" , "bigint" ) ) ;
		map.put( "narration" , new DBUtilsTypes( "narration" , "longtext" ) ) ;
		map.put( "transaction_id" , new DBUtilsTypes( "transaction_id" , "decimal" ) ) ;

		return map ;

	}

	private static int procExisst = 0 ;

	public static int getProcExisst() {
		return procExisst ;
	}

	public static void setProcExisst( int procExisst ) {
		PurchasesTwoDAOUpdate.procExisst = procExisst ;
	}

	private static int updatePurchasesTwo ;

	public static int getUpdatePurchasesTwo() {
		return updatePurchasesTwo ;
	}

	public static void setUpdatePurchasesTwo( int updatePurchasesTwo ) {
		PurchasesTwoDAOUpdate.updatePurchasesTwo = updatePurchasesTwo ;
	}

	public static int updatePurchasesTwo( PurchasesTwo purchasesTwo , String businessId , BigDecimal id ) throws Exception {
		// procParamsForUpdate, tableName, procName, businessId, whereStrOnly,
		// wheresList
		// String procName, String businessId, String tableName, Map<String,
		// DBUtilsTypes> procParamsForUpdate, String whereStrOnly, List<String>
		// wheresList

		String procedureName = procName.concat( businessId ) ;

		if ( getListOfAllProceduresCreated() == null ) {
			listOfAllProceduresAlreadyCreated() ;
		}

		if ( !getListOfAllProceduresCreated().contains( procedureName ) ) {
			DBUtils.createUpdatingProc( procName , businessId , tableName , findMappingForUpdateParameters() , whereCondition() , whereList() , getUpdatePurchasesTwo() ) ;
			setUpdatePurchasesTwo( 1 ) ;
		} else {
			setUpdatePurchasesTwo( 1 ) ;
		}

		int result = 0 ;
		CallableStatement cs = null ;
		Connection connection = DBUtils.getConn() ;
		connection.setAutoCommit( false ) ;
		try {
			cs = connection.prepareCall( DBUtils.invocationOfUpdatetAPI( findMappingForUpdateParameters() , procName , businessId ) ) ;
			// System.out.println(invocationOfUpdatetAPI(map, tableName, procName));
			cs.setBigDecimal( 1 , id ) ;
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
				if ( result == 1 ) {
					connection.commit() ;
					System.out.println( "update successful" ) ;
				} else {
					connection.rollback() ;
					System.out.println( "update failed" ) ;
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
