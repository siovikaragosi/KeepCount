
package com.keepcount.service.business.journal ;

import java.util.List ;

import com.keepcount.model.business.journal.GeneralJournal ;

public class GeneralJournalHelperToPickFromOtherClasses {

	private List < GeneralJournal > generalJournalsToBeTakenToTheGenJournalController ;

	public List < GeneralJournal > getGeneralJournalsToBeTakenToTheGenJournalController() {
		return generalJournalsToBeTakenToTheGenJournalController ;
	}

	public void setGeneralJournalsToBeTakenToTheGenJournalController( List < GeneralJournal > generalJournalsToBeTakenToTheGenJournalController ) {
		this.generalJournalsToBeTakenToTheGenJournalController = generalJournalsToBeTakenToTheGenJournalController ;
	}

}
