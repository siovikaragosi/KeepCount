
package com.keepcount.dao.business.journal ;

import java.sql.CallableStatement ;
import java.sql.Connection ;
import java.util.LinkedHashMap ;
import java.util.List ;
import java.util.Map ;

import com.keepcount.dao.dbutils.DBUtils ;
import com.keepcount.dao.dbutils.DBUtilsTypes ;
import com.keepcount.model.business.journal.GeneralJournal ;

public class GeneralJournalDAOInsertion {
	private static List < String > listOfAllProceduresCreated ;

	public static List < String > getListOfAllProceduresCreated() {
		return listOfAllProceduresCreated ;
	}

	public static void setListOfAllProceduresCreated( List < String > listOfAllProceduresCreated ) {
		GeneralJournalDAOInsertion.listOfAllProceduresCreated = listOfAllProceduresCreated ;
	}

	private static List < String > listOfAllProceduresAlreadyCreated() throws Exception {
		setListOfAllProceduresCreated( DBUtils.listOfAllProceduresAlreadyCreated() ) ;
		return getListOfAllProceduresCreated() ;
	}

	private static String procName = "insert_into_journal_" ;
	private static String tableName = "journal_" ;

	private static Map < String , DBUtilsTypes > findMappingForInsertionParameters() {

		Map < String , DBUtilsTypes > map = new LinkedHashMap <>() ;

		map.put( "date_client" , new DBUtilsTypes( "date_client" , "varchar(255)" ) ) ;// 3
		map.put( "particluars_dr" , new DBUtilsTypes( "particulars_dr" , "varchar(255)" ) ) ;// 4
		map.put( "particulars_cr" , new DBUtilsTypes( "particulars_cr" , "varchar(255)" ) ) ;// 5
		map.put( "narration" , new DBUtilsTypes( "narration" , "longtext" ) ) ;// 6
		map.put( "reference_number" , new DBUtilsTypes( "reference_number" , "varchar(255)" ) ) ;// 7
		map.put( "dr_amount" , new DBUtilsTypes( "dr_amount" , "decimal(50,5)" ) ) ;// 8
		map.put( "cr_amount" , new DBUtilsTypes( "cr_amount" , "decimal(50,5)" ) ) ;// 9
		map.put( "item_id" , new DBUtilsTypes( "item_id" , "bigint" ) ) ;// 10
		map.put( "transaction_id" , new DBUtilsTypes( "transaction_id" , "decimal" ) ) ;// 11
		return map ;

	}

	private static int procExisst = 0 ;

	public static int getProcExisst() {

		return procExisst ;
	}

	public static void setProcExisst( int procExisst ) {

		GeneralJournalDAOInsertion.procExisst = procExisst ;
	}

	private static int insert_into_journal_existence ;

	public static int getInsert_into_measurement_units_Existence() {
		return insert_into_journal_existence ;
	}

	public static void setInsert_into_journal_existence( int insert_into_journal_Existence ) {
		GeneralJournalDAOInsertion.insert_into_journal_existence = insert_into_journal_Existence ;
	}

	public static int [ ] newJournalEntry( List < GeneralJournal > generalJournalsToEnhanceBatch , String businessId ) throws Exception {

		String procedureName = procName.concat( businessId ) ;

		if ( getListOfAllProceduresCreated() == null ) {
			listOfAllProceduresAlreadyCreated() ;
		}

		if ( !getListOfAllProceduresCreated().contains( procedureName ) ) {
			DBUtils.createInsertionProc( procName , businessId , tableName , findMappingForInsertionParameters() , getInsert_into_measurement_units_Existence() ) ;
			setInsert_into_journal_existence( 1 ) ;
		} else {
			setInsert_into_journal_existence( 1 ) ;
		}

		int [ ] result = null ;

		CallableStatement cs = null ;
		Connection connection = DBUtils.getConn() ;
		connection.setAutoCommit( false ) ;

		GeneralJournal journal = new GeneralJournal() ;

		try {

			cs = connection.prepareCall( DBUtils.invocationOfInsertAPI( findMappingForInsertionParameters() , procName , businessId ) ) ;

			cs.clearBatch() ;

			for ( GeneralJournal eachGeneralJournal : generalJournalsToEnhanceBatch ) {

				journal = eachGeneralJournal ;

				cs.setString( 1 , journal.getDateClient() ) ;
				cs.setString( 2 , journal.getParticularDr() ) ;
				cs.setString( 3 , journal.getParticularCr() ) ;
				cs.setString( 4 , journal.getNarration() ) ;
				cs.setString( 5 , journal.getReferenceNumber() ) ;
				cs.setBigDecimal( 6 , journal.getDrAmount() ) ;
				cs.setBigDecimal( 7 , journal.getCrAmount() ) ;
				cs.setBigDecimal( 8 , journal.getItemId() ) ;
				cs.setBigDecimal( 9 , journal.getTransactionId() ) ;

				cs.addBatch() ;

			}

			if ( businessId != null ) {

				result = cs.executeBatch() ;

				System.out.println( "inserting general journal result: " + result ) ;

				if ( result.length == generalJournalsToEnhanceBatch.size() ) {
					connection.commit() ;
				}

				else {
					connection.rollback() ;
					System.out.println( "inserting general journal result rolledback: " + result.toString() ) ;
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
