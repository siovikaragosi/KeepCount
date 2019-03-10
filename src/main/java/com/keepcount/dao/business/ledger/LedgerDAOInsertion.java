
package com.keepcount.dao.business.ledger ;

import java.math.BigDecimal ;
import java.sql.CallableStatement ;
import java.sql.Connection ;
import java.util.LinkedHashMap ;
import java.util.List ;
import java.util.Map ;

import com.keepcount.dao.dbutils.DBUtils ;
import com.keepcount.dao.dbutils.DBUtilsTypes ;
import com.keepcount.model.business.ledger.Ledger ;

public class LedgerDAOInsertion {

	private static List < String > listOfAllProceduresCreated ;

	public static List < String > getListOfAllProceduresCreated() {
		return listOfAllProceduresCreated ;
	}

	public static void setListOfAllProceduresCreated( List < String > listOfAllProceduresCreated ) {
		LedgerDAOInsertion.listOfAllProceduresCreated = listOfAllProceduresCreated ;
	}

	private static List < String > listOfAllProceduresAlreadyCreated() throws Exception {
		setListOfAllProceduresCreated( DBUtils.listOfAllProceduresAlreadyCreated() ) ;
		return getListOfAllProceduresCreated() ;
	}

	private static String procName = "insert_into_ledger_" ;
	private static String tableName = "ledgers_" ;

	/*
	 * 
	 * 
	 * insertion parameters for the purchases table
	 * 
	 */
	private static Map < String , DBUtilsTypes > findMappingForInsertionParameters() {

		Map < String , DBUtilsTypes > map = new LinkedHashMap <>() ;

		map.put( "date_client" , new DBUtilsTypes( "date_client" , "varchar(255)" ) ) ;// 1
		map.put( "ledger_name" , new DBUtilsTypes( "ledger_name" , "varchar(255)" ) ) ;// 1
		map.put( "particulars_dr" , new DBUtilsTypes( "particulars_dr" , "varchar(255)" ) ) ;// 2
		map.put( "particulars_cr" , new DBUtilsTypes( "particulars_cr" , "varchar(255)" ) ) ;// 3
		map.put( "dr_amount" , new DBUtilsTypes( "dr_amount" , "decimal(50,5)" ) ) ;// 6
		map.put( "cr_amount" , new DBUtilsTypes( "cr_amount" , "decimal(50,5)" ) ) ;// 7
		map.put( "item_id" , new DBUtilsTypes( "item_id" , "bigint" ) ) ;// 8
		map.put( "transaction_id" , new DBUtilsTypes( "transaction_id" , "bigint" ) ) ;// 9

		return map ;

	}

	private static int procExisst = 0 ;

	public static int getProcExisst() {

		return procExisst ;
	}

	public static void setProcExisst( int procExisst ) {
		LedgerDAOInsertion.procExisst = procExisst ;
	}

	private static int insertIntoLedgerExistence ;

	public static int getInsertIntoLedgerExistence() {
		return insertIntoLedgerExistence ;
	}

	public static void setInsertIntoLedgerExistence( int insertIntoLedgerExistence ) {
		LedgerDAOInsertion.insertIntoLedgerExistence = insertIntoLedgerExistence ;
	}

	public static int [ ] newLedger( List < Ledger > ledgersToEnhanceBatch , String businessId ) throws Exception {

		Ledger ledger = new Ledger() ;

		String procedureName = procName.concat( businessId ) ;

		if ( getListOfAllProceduresCreated() == null ) {
			listOfAllProceduresAlreadyCreated() ;
		}

		if ( !getListOfAllProceduresCreated().contains( procedureName ) ) {
			DBUtils.createInsertionProc( procName , businessId , tableName , findMappingForInsertionParameters() , getInsertIntoLedgerExistence() ) ;
			setInsertIntoLedgerExistence( 1 ) ;
		} else {
			setInsertIntoLedgerExistence( 1 ) ;
		}

		int [ ] result = null ;
		CallableStatement cs = null ;
		Connection connection = DBUtils.getConn() ;
		connection.setAutoCommit( false ) ;

		try {

			cs = connection.prepareCall( DBUtils.invocationOfInsertAPI( findMappingForInsertionParameters() , procName , businessId ) ) ;

			cs.clearBatch() ;

			for ( Ledger eachLedger : ledgersToEnhanceBatch ) {

				ledger = eachLedger ;

				cs.setString( 1 , ledger.getDateClient() ) ;
				cs.setString( 2 , ledger.getLedgerName() ) ;
				cs.setString( 3 , ledger.getDrParticular() ) ;// dr (it is in the debit side of the ledger)
				cs.setString( 4 , ledger.getCrParticular() ) ;// cr (it is in the credit side of the ledger)
				cs.setBigDecimal( 5 , ledger.getDrAmount() ) ;
				cs.setBigDecimal( 6 , ledger.getCrAmount() ) ;
				cs.setBigDecimal( 7 , ledger.getItemId() ) ;
				cs.setBigDecimal( 8 , ledger.getTransactionId() ) ;

				cs.addBatch() ;

			}

			for ( Ledger led : ledgersToEnhanceBatch ) {
				System.out.println( "ledger: " + led ) ;
			}

			if ( businessId != null ) {

				// result = cs.executeBatch() ;

				System.out.println( "inserting ledger result: " + result ) ;

//				if ( result.length == ledgersToEnhanceBatch.size() ) {
//					connection.commit() ;
//				}
//
//				else {
//					connection.rollback() ;
//					System.out.println( "inserting ledger result rolledback: " + result.toString() ) ;
//				}
			}

		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( null , cs , null , connection ) ;
		}
		return result ;
	}

}
