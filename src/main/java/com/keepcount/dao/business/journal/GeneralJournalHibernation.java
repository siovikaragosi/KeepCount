
package com.keepcount.dao.business.journal ;

import java.math.BigDecimal ;
import java.util.ArrayList ;
import java.util.List ;

import com.keepcount.model.business.journal.GeneralJournal ;

public class GeneralJournalHibernation {

	private static List < GeneralJournal > allJournals ;

	public static List < GeneralJournal > getAllJournals() {
		return allJournals ;
	}

	public static void setAllJournals( List < GeneralJournal > allJournals ) {
		GeneralJournalHibernation.allJournals = allJournals ;
	}

	public static int [ ] newGeneralJournal( List < GeneralJournal > generalJournalsToEnhanceBatch , String businessId ) {
		return GeneralJournalDAO.newJournalEntry( generalJournalsToEnhanceBatch , businessId ) ;
	}

	public static int updateGeneralJournal( GeneralJournal journal , String businessId , BigDecimal id , BigDecimal transactionId ) {
		return GeneralJournalDAO.updateJournalEntry( journal , businessId , id , transactionId ) ;
	}

	public static int deleteGeneralJournal( String businessId , BigDecimal id , BigDecimal itemId , BigDecimal transactionId ) {
		return GeneralJournalDAO.deleteJournalEntry( businessId , id , itemId , transactionId ) ;
	}

	private static List < GeneralJournal > findAllGeneralJournalsWithin( String businessId ) {
		List < GeneralJournal > journals = new ArrayList <>() ;
		journals = GeneralJournalDAO.findAllGeneralJournal( businessId ) ;
		setAllJournals( journals ) ;
		return journals ;
	}

	public static List < GeneralJournal > findAllGeneralJournalsWithRefreshing( String businessId ) {
		return findAllGeneralJournalsWithin( businessId ) ;
	}

	public static List < GeneralJournal > findAllGeneralJournalsWithoutRefreshing( String businessId ) {
		if ( getAllJournals() == null ) {
			return findAllGeneralJournalsWithin( businessId ) ;
		} else {
			return getAllJournals() ;
		}
	}

	/*
	 * 
	 * 
	 * 
	 * */

}
