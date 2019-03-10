
package com.keepcount.service.business.ledger ;

import java.util.List ;

import org.springframework.stereotype.Service ;

import com.keepcount.dao.business.ledger.LedgerHibernation ;
import com.keepcount.model.business.ledger.Ledger ;

@Service
public class LedgerService {

	public int [ ] newLedger( List < Ledger > ledgersToEnhanceBatch , String businessId ) {
		return LedgerHibernation.newLedger( ledgersToEnhanceBatch , businessId ) ;
	}

}
