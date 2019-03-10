
package com.keepcount.service.business.journal ;

import java.util.List ;

import org.springframework.stereotype.Service ;

import com.keepcount.dao.business.journal.GeneralJournalHibernation ;
import com.keepcount.model.business.journal.GeneralJournal ;

@Service
public class GeneralJournalService {

	public int [ ] newJournal( List < GeneralJournal > generalJournalsToEnhanceBatch , String businessId ) {
		return GeneralJournalHibernation.newGeneralJournal( generalJournalsToEnhanceBatch , businessId ) ;
	}

}
