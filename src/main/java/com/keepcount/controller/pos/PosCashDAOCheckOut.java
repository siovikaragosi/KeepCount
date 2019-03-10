
package com.keepcount.controller.pos ;

import java.math.BigDecimal ;
import java.sql.CallableStatement ;
import java.sql.Connection ;
import java.sql.Timestamp ;
import java.time.LocalDateTime ;
import java.util.ArrayList ;
import java.util.LinkedHashMap ;
import java.util.List ;
import java.util.Map ;

import com.keepcount.dao.dbutils.DBUtils ;
import com.keepcount.dao.dbutils.DBUtilsTypes ;
import com.keepcount.dao.pricing.PricingDAOUpdate ;
import com.keepcount.model.pos.CartListCash ;
import com.keepcount.model.pos.PosCash ;

public class PosCashDAOCheckOut {

	private static List < String > listOfAllProceduresCreated ;

	public static List < String > getListOfAllProceduresCreated() {
		return listOfAllProceduresCreated ;
	}

	public static void setListOfAllProceduresCreated( List < String > listOfAllProceduresCreated ) {
		PosCashDAOCheckOut.listOfAllProceduresCreated = listOfAllProceduresCreated ;
	}

	private static List < String > listOfAllProceduresAlreadyCreated() throws Exception {
		setListOfAllProceduresCreated( DBUtils.listOfAllProceduresAlreadyCreated() ) ;
		return getListOfAllProceduresCreated() ;
	}

	private static String procName = "insert_into_poscash_" ;
	private static String tableName = "poscash_" ;

	private static Map < String , DBUtilsTypes > findMappingForInsertionParameters() {

		Map < String , DBUtilsTypes > map = new LinkedHashMap <>() ;

		/* 1 */ map.put( "date_client" , new DBUtilsTypes( "date_client" , "varchar(255)" ) ) ;
		/* 2 */ map.put( "item_id" , new DBUtilsTypes( "item_id" , "bigint" ) ) ;
		/* 3 */ map.put( "item_quantity" , new DBUtilsTypes( "item_quantity" , "decimal(50,5)" ) ) ;
		/* 4 */ map.put( "item_unit_cost" , new DBUtilsTypes( "item_unit_cost" , "decimal(50,5)" ) ) ;
		/* 5 */ map.put( "item_sub_total" , new DBUtilsTypes( "item_sub_total" , "decimal(50,5)" ) ) ;
		/* 6 */ map.put( "total_sales" , new DBUtilsTypes( "total_sales" , "decimal(50,5)" ) ) ;
		/* 7 */ map.put( "amount_paid_in" , new DBUtilsTypes( "amount_paid_in" , "decimal(50,5)" ) ) ;
		/* 8 */ map.put( "change" , new DBUtilsTypes( "change" , "decimal(50,5)" ) ) ;
		/* 9 */ map.put( "customer_email" , new DBUtilsTypes( "customer_email" , "varchar(255)" ) ) ;
		/* 10 */ map.put( "customer_phone" , new DBUtilsTypes( "customer_phone" , "varchar(255)" ) ) ;
		/* 11 */ map.put( "cust_business_name" , new DBUtilsTypes( "cust_business_name" , "longtext" ) ) ;
		/* 12 */ map.put( "transaction_id" , new DBUtilsTypes( "transaction_id" , "bigint" ) ) ;

		return map ;

	}

	private static int procExisst = 0 ;

	public static int getProcExisst() {

		return procExisst ;

	}

	public static void setProcExisst( int procExisst ) {

		PosCashDAOCheckOut.procExisst = procExisst ;
	}

	public static void main( String [ ] args ) {

		for ( int i = 1 ; i < 11 ; i++ ) {

			// System.out.println( "one: " + transactionId() );

			System.out.println( i + ":" + transactionId( transId() ) ) ;
			System.out.println() ;
		}

	}

	private static BigDecimal transId() {

		return new BigDecimal( ( int ) ( Math.random() * 1_000_000_000 * 10 ) ) ;

	}

	private static List < BigDecimal > transactionsAlreadyExisting() {

		List < BigDecimal > bigs = new ArrayList <>() ;
		bigs.add( new BigDecimal( 2147483647 ) ) ;
		return bigs ;

	}

	/*
	 * Concatenate the businessId of this business first the the customer's businessId second
	 */
	private static BigDecimal transactionId( BigDecimal transId ) {

		if ( transId.toString().length() == 10 && !transactionsAlreadyExisting().contains( transId ) ) {
			return transId ;
		}

		else {

			return transactionId( transId() ) ;

		}
	}

	private static int insert_into_poscash_Existence ;

	public static int getInsert_into_poscash_Existence() {
		return insert_into_poscash_Existence ;
	}

	public static void setInsert_into_poscash_Existence( int insert_into_poscash_Existence ) {
		PosCashDAOCheckOut.insert_into_poscash_Existence = insert_into_poscash_Existence ;
	}

	public static int [ ] checkOut( Map < BigDecimal , CartListCash > mapCartListCashCheckOut , Map < BigDecimal , CartListCash > mapTotalCheckOut ,
			Map < BigDecimal , CartListCash > mapAmountPainInCheckOut , Map < BigDecimal , CartListCash > mapChangeCheckOut , String businessId ) throws Exception {

		String procedureName = procName.concat( businessId ) ;

		if ( getListOfAllProceduresCreated() == null ) {
			listOfAllProceduresAlreadyCreated() ;
		}

		if ( !getListOfAllProceduresCreated().contains( procedureName ) ) {
			DBUtils.createInsertionProc( PosCashDAOCheckOut.procName , businessId , PosCashDAOCheckOut.tableName , PosCashDAOCheckOut.findMappingForInsertionParameters() ,
					getInsert_into_poscash_Existence() ) ;
			setInsert_into_poscash_Existence( 1 ) ;
		} else {
			setInsert_into_poscash_Existence( 1 ) ;
		}

		// DBUtils.createInsertionProc( PosCashDAOCheckOut.procName , businessId ,
		// PosCashDAOCheckOut.tableName ,
		// PosCashDAOCheckOut.findMappingForInsertionParameters() ) ;

		int [ ] result = { } ;
		CallableStatement cs = null ;
		Connection connection = DBUtils.getConn() ;
		connection.setAutoCommit( false ) ;
		try {

			cs = connection.prepareCall( DBUtils.invocationOfInsertAPI( PosCashDAOCheckOut.findMappingForInsertionParameters() , PosCashDAOCheckOut.procName , businessId ) ) ;

			PosCash posCash = new PosCash() ;
			Timestamp timestamp = Timestamp.valueOf( LocalDateTime.now() ) ;
			posCash.setDateClient( timestamp.toString() ) ;

			BigDecimal transactionIdBig = transactionId( transId() ) ;

			String transactionIdStr = String.valueOf( transactionIdBig ).concat( businessId ) ;

			posCash.setTransactionId( new BigDecimal( transactionIdStr ) ) ;

			for ( Map.Entry < BigDecimal , CartListCash > cash : mapCartListCashCheckOut.entrySet() ) {

				BigDecimal itemId = cash.getKey() ;

				cs.setString( 1 , posCash.getDateClient() ) ;
				cs.setBigDecimal( 2 , itemId ) ;
				cs.setBigDecimal( 3 , cash.getValue().getItemQuantity() ) ;
				cs.setBigDecimal( 4 , cash.getValue().getUnitCost() ) ;
				cs.setBigDecimal( 5 , cash.getValue().getCostOfItem() ) ;
				cs.setBigDecimal( 6 , mapTotalCheckOut.get( BigDecimal.ZERO ).getTotalCost() ) ;
				cs.setBigDecimal( 7 , mapAmountPainInCheckOut.get( new BigDecimal( -1 ) ).getAmountPaidIn() ) ;
				cs.setBigDecimal( 8 , mapChangeCheckOut.get( new BigDecimal( -2 ) ).getChange() ) ;
				cs.setString( 9 , posCash.getCustomerEmail() ) ;
				cs.setString( 10 , posCash.getCustomerPhone() ) ;
				cs.setString( 11 , posCash.getBusinessName() ) ;
				cs.setBigDecimal( 12 , posCash.getTransactionId() ) ;

				cs.addBatch() ;

			}

			if ( businessId != null ) {
				result = cs.executeBatch() ;
				System.out.println( "res dao ins: " + result ) ;
				if ( result.length == mapCartListCashCheckOut.size() ) {
					connection.commit() ;
				} else {
					connection.rollback() ;
				}
			}

		}

		catch ( Exception e ) {
			e.printStackTrace() ;
		}

		finally {
			DBUtils.closeConnections( null , cs , null , connection ) ;
		}

		return result ;

	}

}
