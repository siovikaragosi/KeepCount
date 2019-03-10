
package com.keepcount.dao.business.ledger ;

import java.sql.Timestamp ;
import java.time.LocalDateTime ;
import java.util.List ;

import com.keepcount.model.business.ledger.Ledger ;

public class LedgerHibernation {

	public static int [ ] newLedger( List < Ledger > ledgersToEnhanceBatch , String businessId ) {
		return LedgerDAO.newLedger( ledgersToEnhanceBatch , businessId ) ;
	}

	public static void main( String [ ] args ) {
		Timestamp timestamp = Timestamp.valueOf( LocalDateTime.now() ) ;
		System.out.println( timestamp ) ;
	}
}
