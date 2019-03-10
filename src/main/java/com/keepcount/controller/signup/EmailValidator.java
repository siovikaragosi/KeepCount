
package com.keepcount.controller.signup ;

import java.util.ArrayList ;
import java.util.List ;
import java.util.regex.Matcher ;
import java.util.regex.Pattern ;

public class EmailValidator {

	private static Pattern pattern ;
	private static Matcher matcher ;

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$" ;

	public EmailValidator() {

		pattern = Pattern.compile( EMAIL_PATTERN ) ;

	}

	public boolean validate( String hex ) {

		matcher = pattern.matcher( hex ) ;

		return matcher.matches() ;

	}

	// private static List< String > emailsToTestValidation() {
	//
	// List< String > list = new ArrayList<>();
	//
	// list.add( "journaldev@yahoo.com" );
	// list.add( "journaldev-100@yahoo.com" );
	// list.add( "journaldev.100@yahoo.com" );
	// list.add( "journaldev111@journaldev.com" );
	// list.add( "journaldev-100@journaldev.net" );
	// list.add( "journaldev.100@journaldev.com.au" );
	// list.add( "journaldev@1.com" );
	// list.add( "journaldev@gmail.com.com" );
	// list.add( "journaldev+100@gmail.com" );
	// list.add( "journaldev-100@yahoo-test.com" );
	// list.add( "journaldev_100@yahoo-test.ABC.CoM" );
	// list.add( "journaldev" );
	// list.add( "journaldev@.com.my" );
	// list.add( "journaldev123@gmail.a" );
	// list.add( "journaldev123@.com" );
	// list.add( "journaldev123@.com.com" );
	// list.add( ".journaldev@journaldev.com" );
	// list.add( "journaldev()*@gmail.com" );
	// list.add( "journaldev@%*.com" );
	// list.add( "journaldev..2002@gmail.com" );
	// list.add( "journaldev.@gmail.com" );
	// list.add( "journaldev@journaldev@gmail.com" );
	// list.add( "journaldev@gmail.com.1a" );
	// list.add( "mkyong@.com.my" );
	//
	// return list;
	//
	// }

	public static void main( String [ ] args ) {

		String email = "alimahmoudraage@gmail.com" ;

		EmailValidator validator = new EmailValidator() ;

		boolean test = validator.validate( email ) ;

		// boolean test = validator.validate( email );
		System.out.println( "first: " + test ) ;

		// for ( int i = 0 ; i < emailsToTestValidation().size() ; i++ ) {
		// String em = emailsToTestValidation().get( i );
		//
		// boolean test2 = new EmailValidator().validate( email );
		//
		// System.out.println( "em: " + em + " --> " + test2 );
		// }

		System.out.println() ;

		// System.out.println( "email is valid: " + email + " " + test );

	}

}
