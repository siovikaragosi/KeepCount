
package com.keepcount.dao.business.journal ;

import java.math.BigDecimal ;
import java.util.ArrayList ;
import java.util.List ;

import com.keepcount.dao.dbutils.DBUtils ;
import com.keepcount.model.business.journal.GeneralJournal ;

public class GeneralJournalDAO {

	private static int procExisst = 0 ;

	public static int getProcExisst() {
		return procExisst ;
	}

	public static void setProcExisst( int procExisst ) {
		GeneralJournalDAO.procExisst = procExisst ;
	}

	private static String tableName = "journal_" ;

	private static String createTableStr = "("
			// 1
			.concat( "id bigint primary key auto_increment" )
			// 2
			.concat( ",date_server datetime default current_timestamp not null" )
			// 3
			.concat( ",date_client varchar(255)" )
			// 4
			.concat( ",particulars_dr varchar(255)" )
			// 5
			.concat( ",particulars_cr varchar(255)" )
			// 6
			.concat( ",narration longtext" )
			// 7
			.concat( ",reference_number varchar(255)" )
			// 8
			.concat( ",dr_amount decimal(50,5)" )
			// 9
			.concat( ",cr_amount decimal(50,5)" )
			// 10
			.concat( ",item_id bigint" )
			// 11
			.concat( ",transaction_id decimal)" ) ;

	public static int checkGeneralJournalTableExistence( String businessId ) {
		int result = 0 ;
		try {
			result = DBUtils.checkTableExistence( tableName , createTableStr , businessId ) ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return result ;
	}

	public static int [ ] newJournalEntry( List < GeneralJournal > generalJournalsToEnhanceBatch , String businessId ) {

		checkGeneralJournalTableExistence( businessId ) ;

		int result[] = null ;
		try {
			result = GeneralJournalDAOInsertion.newJournalEntry( generalJournalsToEnhanceBatch , businessId ) ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return result ;
	}

	public static int updateJournalEntry( GeneralJournal journal , String businessId , BigDecimal id , BigDecimal transactionId ) {
		int result = 0 ;
		try {
			result = GeneralJournalDAOUpdate.updateGeneralJournal( journal , businessId , id , transactionId ) ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return result ;
	}

	public static int deleteJournalEntry( String businessId , BigDecimal id , BigDecimal itemId , BigDecimal transactionId ) {
		int result = 0 ;
		try {
			result = GeneralJournalDAODelete.deletePurchasesTwo( businessId , id , itemId , transactionId ) ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return result ;
	}

	public static List < GeneralJournal > findAllGeneralJournal( String businessId ) {
		List < GeneralJournal > journals = new ArrayList <>() ;
		try {
			journals = GeneralJournalDAORetrieve.findAllGeneralJournal( businessId ) ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return journals ;
	}

}
