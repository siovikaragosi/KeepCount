
package com.keepcount.controller.pos ;

import java.util.ArrayList ;
import java.util.List ;

import com.keepcount.dao.pos.EmailsPhoneNumbersAndNamesOfBusinessesDAO ;
import com.keepcount.model.pos.EmailsPhoneNumbersAndNamesOfBusinesses ;

public class EmailsPhoneNumbersAndNamesOfBusinessesHibernation {

	private static List < EmailsPhoneNumbersAndNamesOfBusinesses > emailsPhoneNumbersAndNamesOfBusinessesFromDBStraightHibernated ;

	public static List < EmailsPhoneNumbersAndNamesOfBusinesses > getEmailsPhoneNumbersAndNamesOfBusinessesFromDBStraightHibernated() {
		return emailsPhoneNumbersAndNamesOfBusinessesFromDBStraightHibernated ;
	}

	public static void setEmailsPhoneNumbersAndNamesOfBusinessesFromDBStraightHibernated(
			List < EmailsPhoneNumbersAndNamesOfBusinesses > emailsPhoneNumbersAndNamesOfBusinessesFromDBStraightHibernated ) {
		EmailsPhoneNumbersAndNamesOfBusinessesHibernation.emailsPhoneNumbersAndNamesOfBusinessesFromDBStraightHibernated = emailsPhoneNumbersAndNamesOfBusinessesFromDBStraightHibernated ;
	}

	/* Checking whether the email or the telephone number of the client is contained in the system */
	public static String checkingForTheExistenceOfTheSpecifiedCustomerEmailOrTelephoneNumber( String emailOrTelephoneNumberOfTheCustomer ) {
		if ( ( findAllEmailsPhoneNumbersAndNamesOfBusinessesBasingOnEmailOfTheCustomer( emailOrTelephoneNumberOfTheCustomer ) != null )
				|| ( findAllEmailsPhoneNumbersAndNamesOfBusinessesBasingOnTelephoneNumberOfTheCustomer( emailOrTelephoneNumberOfTheCustomer ) != null ) ) {
			return "email Or telephone number of the customer exists" ;
		} else {
			return "email Or telephone number of the customer does not exists" ;
		}
	}

	public static List < EmailsPhoneNumbersAndNamesOfBusinesses > messageAfterCheckingExistenceOfTheEmailOrTelephoneNumberOfTheCustomer(
			List < EmailsPhoneNumbersAndNamesOfBusinesses > resultOfCheck , String emailOrTelephoneNumberOfTheCustomer ) {
		List < EmailsPhoneNumbersAndNamesOfBusinesses > message = new ArrayList <>() ;
		EmailsPhoneNumbersAndNamesOfBusinesses emailsPhoneNumbersAndNamesOfBusinessesForMessageOfItsExistence = new EmailsPhoneNumbersAndNamesOfBusinesses() ;
		if ( resultOfCheck != null ) {
			emailsPhoneNumbersAndNamesOfBusinessesForMessageOfItsExistence
					.setEmail( checkingForTheExistenceOfTheSpecifiedCustomerEmailOrTelephoneNumber( emailOrTelephoneNumberOfTheCustomer ) ) ;
			message.add( emailsPhoneNumbersAndNamesOfBusinessesForMessageOfItsExistence ) ;
		} else {
			emailsPhoneNumbersAndNamesOfBusinessesForMessageOfItsExistence
					.setEmail( checkingForTheExistenceOfTheSpecifiedCustomerEmailOrTelephoneNumber( emailOrTelephoneNumberOfTheCustomer ) ) ;
			message.add( emailsPhoneNumbersAndNamesOfBusinessesForMessageOfItsExistence ) ;
		}
		return message ;
	}

	/*
	 * This retrieves from the database, all the EmailsPhoneNumbersAndNamesOfBusinesses. It then set the list to the
	 * setEmailsPhoneNumbersAndNamesOfBusinessesFromDBStraightHibernated
	 */
	public static List < EmailsPhoneNumbersAndNamesOfBusinesses > findAllEmailsPhoneNumbersAndNamesOfBusinesses() {
		List < EmailsPhoneNumbersAndNamesOfBusinesses > emailsPhoneNumbersAndNamesOfBusinessesFromDB = new ArrayList <>() ;
		try {
			emailsPhoneNumbersAndNamesOfBusinessesFromDB = EmailsPhoneNumbersAndNamesOfBusinessesDAO
					.findAllEmailsPhoneNumbersAndNamesOfBusinessesInListFormatToBeUsedAnyWhereNecessary() ;
			setEmailsPhoneNumbersAndNamesOfBusinessesFromDBStraightHibernated( emailsPhoneNumbersAndNamesOfBusinessesFromDB ) ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return emailsPhoneNumbersAndNamesOfBusinessesFromDB ;
	}

	public static List < EmailsPhoneNumbersAndNamesOfBusinesses > findAllEmailsPhoneNumbersAndNamesOfBusinessesBasingOnEmailOfTheCustomer( String emailOfTheCustomer ) {
		List < EmailsPhoneNumbersAndNamesOfBusinesses > emailsPhoneNumbersAndNamesOfBusinessesBasingOnEmailOfTheCustomer = new ArrayList <>() ;
		if ( getEmailsPhoneNumbersAndNamesOfBusinessesFromDBStraightHibernated() == null ) {
			findAllEmailsPhoneNumbersAndNamesOfBusinesses() ;
		}
		for ( EmailsPhoneNumbersAndNamesOfBusinesses emailsPhoneNumbersAndNamesOfBusinesses : getEmailsPhoneNumbersAndNamesOfBusinessesFromDBStraightHibernated() ) {
			String emailToBeTestedIfItMatchesCustomerEmail = emailsPhoneNumbersAndNamesOfBusinesses.getEmail() ;
			if ( emailToBeTestedIfItMatchesCustomerEmail.equalsIgnoreCase( emailOfTheCustomer ) ) {
				emailsPhoneNumbersAndNamesOfBusinessesBasingOnEmailOfTheCustomer.add( emailsPhoneNumbersAndNamesOfBusinesses ) ;
			}
		}
		return emailsPhoneNumbersAndNamesOfBusinessesBasingOnEmailOfTheCustomer ;
	}

	public static List < EmailsPhoneNumbersAndNamesOfBusinesses > findAllEmailsPhoneNumbersAndNamesOfBusinessesBasingOnTelephoneNumberOfTheCustomer(
			String telephoneNumberOfTheCustomer ) {
		List < EmailsPhoneNumbersAndNamesOfBusinesses > emailsPhoneNumbersAndNamesOfBusinessesBasingOnTelephoneOfTheCustomer = new ArrayList <>() ;
		if ( getEmailsPhoneNumbersAndNamesOfBusinessesFromDBStraightHibernated() == null ) {
			findAllEmailsPhoneNumbersAndNamesOfBusinesses() ;
		}
		for ( EmailsPhoneNumbersAndNamesOfBusinesses emailsPhoneNumbersAndNamesOfBusinesses : getEmailsPhoneNumbersAndNamesOfBusinessesFromDBStraightHibernated() ) {
			String emailToBeTestedIfItMatchesCustomerTelephoneNumber = emailsPhoneNumbersAndNamesOfBusinesses.getEmail() ;
			if ( emailToBeTestedIfItMatchesCustomerTelephoneNumber.equalsIgnoreCase( telephoneNumberOfTheCustomer ) ) {
				emailsPhoneNumbersAndNamesOfBusinessesBasingOnTelephoneOfTheCustomer.add( emailsPhoneNumbersAndNamesOfBusinesses ) ;
			}
		}
		return emailsPhoneNumbersAndNamesOfBusinessesBasingOnTelephoneOfTheCustomer ;
	}

	public static void main( String [ ] args ) {
		System.out.println( findAllEmailsPhoneNumbersAndNamesOfBusinessesBasingOnEmailOfTheCustomer( "alimahmoudraage@gmail.com" ) ) ;
	}

}
