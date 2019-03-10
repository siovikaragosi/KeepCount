
package com.keepcount.service.pos ;

import java.math.BigDecimal ;
import java.util.LinkedHashMap ;
import java.util.Map ;

import com.keepcount.dao.business.entities.EmailsOfABusinessWithAllAttributesDAO ;
import com.keepcount.model.business.entities.EmailsOfABusinessWithAllAttributes ;

public class EmailsOfABusinessWithAllAttributesHibernation {

	public static Map < BigDecimal , EmailsOfABusinessWithAllAttributes > findAllEmailsOfABusinessWithAllAttributesBasingOnEmailOfAMember ;

	public static Map < BigDecimal , EmailsOfABusinessWithAllAttributes > getFindAllEmailsOfABusinessWithAllAttributesBasingOnEmailOfAMember() {
		return findAllEmailsOfABusinessWithAllAttributesBasingOnEmailOfAMember ;
	}

	public static void setFindAllEmailsOfABusinessWithAllAttributesBasingOnEmailOfAMember(
			Map < BigDecimal , EmailsOfABusinessWithAllAttributes > findAllEmailsOfABusinessWithAllAttributesBasingOnEmailOfAMember ) {
		EmailsOfABusinessWithAllAttributesHibernation.findAllEmailsOfABusinessWithAllAttributesBasingOnEmailOfAMember = findAllEmailsOfABusinessWithAllAttributesBasingOnEmailOfAMember ;
	}

	public static Map < BigDecimal , EmailsOfABusinessWithAllAttributes > findAllEmailsOfABusinessWithAllAttributes() {
		Map < BigDecimal , EmailsOfABusinessWithAllAttributes > map = new LinkedHashMap <>() ;

		try {
			if ( getFindAllEmailsOfABusinessWithAllAttributesBasingOnEmailOfAMember() != null ) {
				map = getFindAllEmailsOfABusinessWithAllAttributesBasingOnEmailOfAMember() ;
			} else {
				map = EmailsOfABusinessWithAllAttributesDAO.findAllEmailsOfABusinessWithAllAttributes() ;
				setFindAllEmailsOfABusinessWithAllAttributesBasingOnEmailOfAMember( map ) ;
			}

		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return map ;
	}

	public static Map < BigDecimal , EmailsOfABusinessWithAllAttributes > allBusinessesAndRelatedIDsBasingOnEmailOfTheCustomer( String email ) {

		Map < BigDecimal , EmailsOfABusinessWithAllAttributes > all = EmailsOfABusinessWithAllAttributesHibernation.findAllEmailsOfABusinessWithAllAttributes() ;

		Map < BigDecimal , EmailsOfABusinessWithAllAttributes > businessessWithThisEmail = new LinkedHashMap <>() ;
		for ( Map.Entry < BigDecimal , EmailsOfABusinessWithAllAttributes > each : all.entrySet() ) {
			if ( each.getValue().getEmailOfABusinessMember().equals( email ) ) {
				businessessWithThisEmail.put( each.getKey() , each.getValue() ) ;
			}
		}
		return businessessWithThisEmail ;
	}

	public static void main( String [ ] args ) {
		System.out.println( "all: " + allBusinessesAndRelatedIDsBasingOnEmailOfTheCustomer( "alimahmoudraage@gmail.com" ) ) ;
	}

}
