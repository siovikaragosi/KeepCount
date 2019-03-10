
package com.keepcount.service.pos ;

import java.util.ArrayList ;
import java.util.List ;

import org.springframework.stereotype.Service ;

import com.keepcount.controller.pos.EmailsPhoneNumbersAndNamesOfBusinessesHibernation ;
import com.keepcount.model.pos.EmailsPhoneNumbersAndNamesOfBusinesses ;
@Service
public class EmailsPhoneNumbersAndNamesOfBusinessesServiceForPOSCash {

	public List < EmailsPhoneNumbersAndNamesOfBusinesses > findAllEmailsPhoneNumbersAndNamesOfBusinessesBasingOnEmailOfTheCustomer( String emailOfTheCustomer ) {
		return EmailsPhoneNumbersAndNamesOfBusinessesHibernation.findAllEmailsPhoneNumbersAndNamesOfBusinessesBasingOnEmailOfTheCustomer( emailOfTheCustomer ) ;
	}

	public List < EmailsPhoneNumbersAndNamesOfBusinesses > findAllEmailsPhoneNumbersAndNamesOfBusinessesBasingOnTelephoneNumberOfTheCustomer(
			String telephoneNumberOfTheCustomer ) {
		return EmailsPhoneNumbersAndNamesOfBusinessesHibernation.findAllEmailsPhoneNumbersAndNamesOfBusinessesBasingOnTelephoneNumberOfTheCustomer( telephoneNumberOfTheCustomer ) ;
	}

	public List < EmailsPhoneNumbersAndNamesOfBusinesses > checkIfCustomerEntryIsTelOrEmail( String emailOrTelephoneNumber ) {
		List < EmailsPhoneNumbersAndNamesOfBusinesses > messageIfEmailOrTelephoneNumber = new ArrayList <>() ;
		EmailsPhoneNumbersAndNamesOfBusinesses businesses = new EmailsPhoneNumbersAndNamesOfBusinesses() ;
		businesses.setEmail( emailOrTelephoneNumber ) ;
		messageIfEmailOrTelephoneNumber.add( businesses ) ;
		return messageIfEmailOrTelephoneNumber ;
	}

}
