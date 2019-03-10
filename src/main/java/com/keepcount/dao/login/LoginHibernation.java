
package com.keepcount.dao.login;

import java.util.ArrayList;
import java.util.List;

import com.keepcount.dao.signup.SignUpDAO;
import com.keepcount.dao.signup.SignUpHibernation;
import com.keepcount.model.login.Login;
import com.keepcount.model.signup.SignUp;

public class LoginHibernation {

	private static List< Login > logins;
	private static List< Login > loginsByEmail;

	private static List< SignUp > signUpsHibernatedForLoginVerification;

	public LoginHibernation () {

	}

	public static String findLoginPasswordByEmail ( String email ) {

		String em = null;
		try {
			em = LoginDAO.findLoginPasswordByEmail( email );
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
		return em;
	}

	public static String getEmailToCheckIfTheProvidedCorrespondentExists ( String emailSpecified ) {

		String email = null;
		try {
			email = LoginDAO.getEmailToCheckIfTheProvidedCorrespondentExists( emailSpecified );
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
		return email;
	}

	public static String checkPassword ( String emailSpecified ) {

		String email = null;
		try {
			email = LoginDAO.checkPassword( emailSpecified );
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
		return email;
	}

	public static int login ( Login login ) {

		int result = 0;

		try {
			result = LoginDAO.login( login );
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}

		return result;
	}

	public static List< SignUp > findAllSignUpsForLoginVerification () {

		List< SignUp > signUps = new ArrayList<>();
		signUps = getSignUpsHibernatedForLoginVerification();

		if ( signUps == null ) {
			findAllSignUpsForLoginVerification();
			signUps = getSignUpsHibernatedForLoginVerification();
		}

		return signUps;

	}

	public static List< SignUp > findAllSignUps () {

		List< SignUp > ups = new ArrayList<>();

		try {

			if ( getSignUpsHibernated() != null ) {

				ups = getSignUpsHibernated();
				
				System.out.println( "emails_kc not refreshed...." );
				
			}
			else {

				ups = LoginDAO.findAllSignUps();
				
				System.out.println( "emails_kc refreshed...." );
				
			}

		}
		catch ( Exception e ) {
			e.printStackTrace();
		}

		setSignUpsHibernated( ups );

		return ups;

	}

	private static List< SignUp > signUpsHibernated;

	public static List< SignUp > getSignUpsHibernated () {

		return signUpsHibernated;
	}

	public static void setSignUpsHibernated ( List< SignUp > signUpsHibernated ) {

		LoginHibernation.signUpsHibernated = signUpsHibernated;
	}

	private static void findAllLoginsWithin () {

		List< Login > logins = new ArrayList<>();
		try {
			logins = LoginDAO.findAllLogins();
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
		setLogins( logins );
	}

	private static void findLoginsByEmailWithin ( String email ) {

		List< Login > logins = new ArrayList<>();
		try {
			logins = LoginDAO.findLoginByEmail( email );
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
		setLoginsByEmail( logins );
	}

	public static List< Login > findAllLogins () {

		List< Login > logins = new ArrayList<>();
		logins = getLogins();

		if ( logins == null ) {
			findAllLoginsWithin();
			logins = getLogins();
			return logins;
		}
		else {
			return logins;
		}

	}

	public static List< Login > findLoginByEmail ( String email ) {

		List< Login > logins = new ArrayList<>();
		logins = getLogins();

		if ( logins == null ) {
			findLoginsByEmailWithin( email );
			logins = getLoginsByEmail();
			return logins;
		}
		else {
			return logins;
		}

	}

	public static List< Login > getLogins () {

		return logins;
	}

	public static void setLogins ( List< Login > logins ) {

		LoginHibernation.logins = logins;
	}

	public static List< Login > getLoginsByEmail () {

		return loginsByEmail;
	}

	public static void setLoginsByEmail ( List< Login > loginsByEmail ) {

		LoginHibernation.loginsByEmail = loginsByEmail;
	}

	public static List< SignUp > getSignUpsHibernatedForLoginVerification () {

		return signUpsHibernatedForLoginVerification;
	}

	public static void setSignUpsHibernatedForLoginVerification ( List< SignUp > signUpsHibernatedForLoginVerification ) {

		LoginHibernation.signUpsHibernatedForLoginVerification = signUpsHibernatedForLoginVerification;
	}

}
