
package com.keepcount.dao.business.journal ;

import java.math.BigDecimal ;
import java.sql.CallableStatement ;
import java.sql.Connection ;
import java.util.ArrayList ;
import java.util.LinkedHashMap ;
import java.util.List ;
import java.util.Map ;

import com.keepcount.dao.dbutils.DBUtils ;
import com.keepcount.dao.dbutils.DBUtilsTypes ;
import com.keepcount.model.business.journal.GeneralJournal ;

public class GeneralJournalDAOUpdate {
	private static List < String > listOfAllProceduresCreated ;

	public static List < String > getListOfAllProceduresCreated() {
		return listOfAllProceduresCreated ;
	}

	public static void setListOfAllProceduresCreated( List < String > listOfAllProceduresCreated ) {
		GeneralJournalDAOUpdate.listOfAllProceduresCreated = listOfAllProceduresCreated ;
	}

	private static List < String > listOfAllProceduresAlreadyCreated() throws Exception {
		setListOfAllProceduresCreated( DBUtils.listOfAllProceduresAlreadyCreated() ) ;
		return getListOfAllProceduresCreated() ;
	}

	private static String procName = "update_general_journal_" ;
	private static String tableName = "journal_" ;

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
		map.put( "transaction_id" , new DBUtilsTypes( "transaction_id" , "bigint" ) ) ;

		return map ;

	}

	private static int procExisst = 0 ;

	public static int getProcExisst() {
		return procExisst ;
	}

	public static void setProcExisst( int procExisst ) {
		GeneralJournalDAOUpdate.procExisst = procExisst ;
	}

	private static int updateGeneralJournal ;

	public static int getUpdateGeneralJournal() {
		return updateGeneralJournal ;
	}

	public static void setUpdateGeneralJournal( int updateGeneralJournal ) {
		GeneralJournalDAOUpdate.updateGeneralJournal = updateGeneralJournal ;
	}

	public static int updateGeneralJournal( GeneralJournal generalJournal , String businessId , BigDecimal id , BigDecimal transactionId ) throws Exception {

		String procedureName = procName.concat( businessId ) ;

		if ( getListOfAllProceduresCreated() == null ) {
			listOfAllProceduresAlreadyCreated() ;
		}

		if ( !getListOfAllProceduresCreated().contains( procedureName ) ) {
			DBUtils.createUpdatingProc( procName , businessId , tableName , findMappingForUpdateParameters() , whereCondition() , whereList() , getUpdateGeneralJournal() ) ;
			setUpdateGeneralJournal( 1 ) ;
		} else {
			setUpdateGeneralJournal( 1 ) ;
		}

		int result = 0 ;
		CallableStatement cs = null ;
		Connection connection = DBUtils.getConn() ;
		connection.setAutoCommit( false ) ;
		try {
			cs = connection.prepareCall( DBUtils.invocationOfUpdatetAPI( findMappingForUpdateParameters() , procName , businessId ) ) ;
			// System.out.println(invocationOfUpdatetAPI(map, tableName, procName));
			cs.setBigDecimal( 1 , id ) ;
			cs.setBigDecimal( 2 , generalJournal.getItemId() ) ;
			cs.setBigDecimal( 3 , transactionId ) ;

			if ( businessId != null ) {
				result = cs.executeUpdate() ;
				if ( result == 1 ) {
					connection.commit() ;
					System.out.println( "general journal update successful" ) ;
				} else {
					connection.rollback() ;
					System.out.println( "general journal update failed" ) ;
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
