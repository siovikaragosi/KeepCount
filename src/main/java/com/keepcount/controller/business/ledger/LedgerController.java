
package com.keepcount.controller.business.ledger ;

import java.util.List ;

import org.springframework.beans.factory.annotation.Autowired ;
import org.springframework.stereotype.Controller ;

import com.keepcount.model.business.ledger.Ledger ;
import com.keepcount.service.business.ledger.LedgerService ;

@Controller
public class LedgerController {

	@Autowired
	private LedgerService ledgerService ;

	public static int [ ] newLegder( List < Ledger > ledgersToEnhanceBatch , String businessId ) {
		LedgerService service = new LedgerService() ;
		return service.newLedger( ledgersToEnhanceBatch , businessId ) ;
	}

}
