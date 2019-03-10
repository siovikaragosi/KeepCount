
package com.keepcount.dao.purchases ;

import java.math.BigDecimal ;
import java.sql.CallableStatement ;
import java.sql.Connection ;
import java.util.List ;

import com.keepcount.dao.dbutils.DBUtils ;

public class PurchasesTwoDAODelete {

	private static List < String > listOfAllProceduresCreated ;

	public static List < String > getListOfAllProceduresCreated() {
		return listOfAllProceduresCreated ;
	}

	public static void setListOfAllProceduresCreated( List < String > listOfAllProceduresCreated ) {
		PurchasesTwoDAODelete.listOfAllProceduresCreated = listOfAllProceduresCreated ;
	}

	private static List < String > listOfAllProceduresAlreadyCreated() throws Exception {
		setListOfAllProceduresCreated( DBUtils.listOfAllProceduresAlreadyCreated() ) ;
		return getListOfAllProceduresCreated() ;
	}

	private static String procName = "delete_from_purchases_" ;
	private static String tableName = "purchases_" ;
	private static String inParams = "in in_id bigint, in in_item_id bigint, in in_transaction_id decimal" ;
	// private static String whereCondition = " WHERE id=in_id" ;

	private static String whereCondition() {
		return " WHERE id = in_id AND item_id = in_item_id AND transaction_id = in_transaction_id" ;
	}

	// private static List < String > whereList() {
	// List < String > list = new ArrayList <>() ;
	// // list.add(" WHERE ");
	// list.add( "id = in_id" ) ;
	// list.add( "item_id = in_item_id" ) ;
	// list.add( "transaction_id = in_transaction_id" ) ;
	// return list ;
	// }

	private static int deleteFromPurchasesTwo ;

	public static int getDeleteFromPurchasesTwo() {
		return deleteFromPurchasesTwo ;
	}

	public static void setDeleteFromPurchasesTwo( int deleteFromPurchasesTwo ) {
		PurchasesTwoDAODelete.deleteFromPurchasesTwo = deleteFromPurchasesTwo ;
	}

	public static int deletePurchasesTwo( String businessId , BigDecimal id , BigDecimal itemId , BigDecimal transactionId ) throws Exception {

		String procedureName = procName.concat( businessId ) ;

		if ( getListOfAllProceduresCreated() == null ) {
			listOfAllProceduresAlreadyCreated() ;
		}

		if ( !getListOfAllProceduresCreated().contains( procedureName ) ) {
			DBUtils.createDeletionProc( procName , businessId , tableName , inParams , whereCondition() , getDeleteFromPurchasesTwo() ) ;
			setDeleteFromPurchasesTwo( 1 ) ;
		} else {
			setDeleteFromPurchasesTwo( 1 ) ;
		}

		int result = 0 ;
		CallableStatement cs = null ;
		Connection connection = DBUtils.getConn() ;
		connection.setAutoCommit( false ) ;
		try {
			cs = connection.prepareCall( "{CALL ".concat( procName ).concat( businessId ).concat( "(?,?,?)" ).concat( "}" ) ) ;
			cs.setBigDecimal( 1 , id ) ;
			cs.setBigDecimal( 2 , itemId ) ;
			cs.setBigDecimal( 3 , transactionId ) ;

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
