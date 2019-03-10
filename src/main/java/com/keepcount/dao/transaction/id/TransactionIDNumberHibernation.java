
package com.keepcount.dao.transaction.id ;

import java.math.BigDecimal ;

public class TransactionIDNumberHibernation {

	private static int recordNewTransactionId() {

		int result = 0 ;

		try {
			result = TransactionIDNumberDAO.newTransactionId() ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return result ;
	}

	public static BigDecimal getTheRequiredTransactionId() {
		recordNewTransactionId() ;
		BigDecimal transId = TransactionIDNumberDAO.getTheRequiredTransactionId() ;
		setTheHibernatedRequiredTransactionId( transId ) ;
		return transId ;
	}

	private static BigDecimal theHibernatedRequiredTransactionId = BigDecimal.ZERO;

	public static BigDecimal getTheHibernatedRequiredTransactionId() {
		return theHibernatedRequiredTransactionId ;
	}

	public static void setTheHibernatedRequiredTransactionId( BigDecimal theHibernatedRequiredTransactionId ) {
		TransactionIDNumberHibernation.theHibernatedRequiredTransactionId = theHibernatedRequiredTransactionId ;
	}

}
