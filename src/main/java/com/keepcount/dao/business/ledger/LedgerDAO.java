
package com.keepcount.dao.business.ledger ;

import java.util.List ;

import com.keepcount.dao.dbutils.DBUtils ;
import com.keepcount.model.business.ledger.Ledger ;

public class LedgerDAO {

	private static int procExisst = 0 ;

	public static int getProcExisst() {
		return procExisst ;
	}

	public static void setProcExisst( int procExisst ) {
		LedgerDAO.procExisst = procExisst ;
	}

	private static String tableName = "ledger_" ;

	private static String createTableStr = "("

			// 1
			.concat( "id bigint primary key auto_increment" )
			// 2
			.concat( ",date_server datetime default current_timestamp not null" )
			// 3
			.concat( ",date_client varchar(255)" )
			// 4
			.concat( ",ledger_name varchar(255)" )
			// 5
			.concat( ",particulars_dr varchar(255)" )
			// 6
			.concat( ",particulars_cr varchar(255)" )
			// 7
			.concat( ",dr_amount decimal(50,5)" )
			// 8
			.concat( ",cr_amount decimal(50,5)" )
			// 9
			.concat( ",item_id bigint" )
			// 10
			.concat( ",transaction_id decimal)" ) ;

	public static int checkLedgerTableExistence( String businessId ) {
		int result = 0 ;
		try {
			result = DBUtils.checkTableExistence( tableName , createTableStr , businessId ) ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return result ;
	}

	public static int [ ] newLedger( List < Ledger > ledgersToEnhanceBatch , String businessId ) {

		checkLedgerTableExistence( businessId ) ;

		int [ ] result = null ;
		try {
			result = LedgerDAOInsertion.newLedger( ledgersToEnhanceBatch , businessId ) ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return result ;
	}

}
