
package com.keepcount.dao.signup;

import java.util.ArrayList;
import java.util.List;

import com.keepcount.model.signup.EmailsSignedUp;
import com.keepcount.model.signup.SignUp;

public class SignUpHibernation {

	private static List< SignUp > signUpsHibernated;
	private List< String > emails;
	private List< String > passwords;
	private List< EmailsSignedUp > emailsSignedUps;

	public SignUpHibernation () {

	}

	public SignUpHibernation ( String nothing ) {

	}

	public static int signUpNewUser ( SignUp signUp ) {

		int result = 0;

		try {
			result = SignUpDAO.signUpNewUser( signUp );
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}

		return result;

	}

	public static List< SignUp > findAllSignUps () {

		List< SignUp > ups = new ArrayList<>();

		try {

			if ( getSignUpsHibernated() != null ) {

				ups = getSignUpsHibernated();

			}

			ups = SignUpDAO.findAllSignUps();

		}
		catch ( Exception e ) {
			e.printStackTrace();
		}

		setSignUpsHibernated( ups );

		return ups;

	}

	public static List< SignUp > getSignUpsHibernated () {

		return signUpsHibernated;
	}

	public static void setSignUpsHibernated ( List< SignUp > signUpsHibernated ) {

		SignUpHibernation.signUpsHibernated = signUpsHibernated;
	}

	public List< String > getEmails () {

		return emails;
	}

	public void setEmails ( List< String > emails ) {

		this.emails = emails;
	}

	public List< String > getPasswords () {

		return passwords;
	}

	public void setPasswords ( List< String > passwords ) {

		this.passwords = passwords;
	}

	public List< EmailsSignedUp > getEmailsSignedUps () {

		return emailsSignedUps;
	}

	public void setEmailsSignedUps ( List< EmailsSignedUp > emailsSignedUps ) {

		this.emailsSignedUps = emailsSignedUps;
	}

}
