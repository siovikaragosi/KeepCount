
package com.keepcount.dao.business.journal ;

import java.math.BigDecimal ;
import java.sql.CallableStatement ;
import java.sql.Connection ;
import java.sql.ResultSet ;
import java.util.ArrayList ;
import java.util.List ;

import com.keepcount.dao.dbutils.DBUtils ;
import com.keepcount.model.business.journal.GeneralJournal ;

public class GeneralJournalDAORetrieve {
	private static List < String > listOfAllProceduresCreated ;

	public static List < String > getListOfAllProceduresCreated() {
		return listOfAllProceduresCreated ;
	}

	public static void setListOfAllProceduresCreated( List < String > listOfAllProceduresCreated ) {
		GeneralJournalDAORetrieve.listOfAllProceduresCreated = listOfAllProceduresCreated ;
	}

	private static List < String > listOfAllProceduresAlreadyCreated() throws Exception {
		setListOfAllProceduresCreated( DBUtils.listOfAllProceduresAlreadyCreated() ) ;
		return getListOfAllProceduresCreated() ;
	}

	private static String procName = "get_general_journal_" ;
	private static String tableName = "journal_" ;
	private static String whereCond = "" ;
	private static String inParams = "" ;
	private static String wildCardValues = "" ;
	private static String asteriskOrCols = "*" ;

	private static int generalJournal_Existence ;

	public static int getGeneralJournal_Existence() {
		return generalJournal_Existence ;
	}

	public static void setGeneralJournal_Existence( int generalJournal_Existence ) {
		GeneralJournalDAORetrieve.generalJournal_Existence = generalJournal_Existence ;
	}

	public static List < GeneralJournal > findAllGeneralJournal( String businessId ) throws Exception {
		// String procName, String businessId, String tableName, String whereCondition

		String procedureName = procName.concat( businessId ) ;

		if ( getListOfAllProceduresCreated() == null ) {
			listOfAllProceduresAlreadyCreated() ;
		}

		if ( !getListOfAllProceduresCreated().contains( procedureName ) ) {
			DBUtils.createSelectionProc( inParams , procName , businessId , asteriskOrCols , tableName , whereCond , getGeneralJournal_Existence() ) ;
			setGeneralJournal_Existence( 1 ) ;
		} else {
			setGeneralJournal_Existence( 1 ) ;
		}

		// DBUtils.createSelectionProc( inParams , procName , businessId ,
		// asteriskOrCols , tableName , whereCond ) ;

		List < GeneralJournal > list = new ArrayList <>() ;
		CallableStatement cs = null ;
		ResultSet rs = null ;
		Connection connection = DBUtils.getConn() ;
		try {
			cs = connection.prepareCall( DBUtils.invokeSelection( procName , businessId , wildCardValues ) ) ;
			rs = cs.executeQuery() ;
			while ( rs.next() ) {

				BigDecimal id = rs.getBigDecimal( "id" ) ;
				String dateServer = rs.getString( "date_server" ) ;
				String dateClient = rs.getString( "date_client" ) ;
				String particularDr = rs.getString( "particular_dr" ) ;
				String particularCr = rs.getString( "particular_cr" ) ;
				String narration = rs.getString( "narration" ) ;
				String referenceNumber = rs.getString( "reference_number" ) ;
				BigDecimal drAmount = rs.getBigDecimal( "dr_amount" ) ;
				BigDecimal crAmount = rs.getBigDecimal( "cr_amount" ) ;
				BigDecimal itemId = rs.getBigDecimal( "item_id" ) ;
				BigDecimal transactionId = rs.getBigDecimal( "transaction_id" ) ;

				/*
				 * The two null values in the constructed object of the General Journal are for drAmountStr and crAmountStr
				 * 
				 */

				GeneralJournal journal = new GeneralJournal( id , dateServer , dateClient , particularDr , particularCr , narration , referenceNumber , drAmount , null , crAmount ,
						null , itemId , transactionId ) ;
				list.add( journal ) ;
			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( rs , cs , null , connection ) ;
		}
		return list ;
	}

}
