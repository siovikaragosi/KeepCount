
package com.keepcount.service.login;

import java.util.List;

import org.springframework.stereotype.Service;

import com.keepcount.dao.login.LoginHibernation;
import com.keepcount.dao.login.LoginTestDAO;
import com.keepcount.model.login.Login;
import com.keepcount.model.signup.SignUp;

@Service
public class LoginService {

	public SignUp getSignedUpEmailMatch ( String sqlQuery , String emailMatch ) {

		SignUp signUp = null;
		try {
			signUp = LoginTestDAO.getEmailSignedUpMatch( sqlQuery , emailMatch );
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
		return signUp;
	}

	public List< SignUp > findAllSignUps () {

		return LoginHibernation.findAllSignUps();
	}

	public SignUp getTheUserSignUpDetails ( String email , String password ) {

		SignUp up = new SignUp();
		up.setEmail( email );
		up.setPassword( password );
		return up;

	}

	public String getEmailToCheckIfTheProvidedCorrespondentExists ( String emailSpecified ) {

		return LoginHibernation.getEmailToCheckIfTheProvidedCorrespondentExists( emailSpecified );
	}

	public String checkPassword ( String emailSpecified ) {

		return LoginHibernation.checkPassword( emailSpecified );
	}

	public int login ( Login login ) {

		int result = 0;

		result = LoginHibernation.login( login );

		return result;
	}

	public Login toGetLoginValue ( Login login ) {

		return login;
	}

	public List< Login > findAllLogins () {

		return LoginHibernation.findAllLogins();
	}

	public List< Login > findLoginByEmail ( String email ) {

		return LoginHibernation.findLoginByEmail( email );
	}

	public String findLoginPasswordByEmail ( String em ) {

		return LoginHibernation.findLoginPasswordByEmail( em );
	}

}
