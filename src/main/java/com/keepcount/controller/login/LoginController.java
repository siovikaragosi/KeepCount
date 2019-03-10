
package com.keepcount.controller.login ;

import java.util.ArrayList ;
import java.util.LinkedHashMap ;
import java.util.List ;
import java.util.Map ;

import javax.servlet.http.HttpServletRequest ;
import javax.servlet.http.HttpServletResponse ;

import org.springframework.beans.factory.annotation.Autowired ;
import org.springframework.security.crypto.bcrypt.BCrypt ;
import org.springframework.stereotype.Controller ;
import org.springframework.web.bind.annotation.RequestBody ;
import org.springframework.web.bind.annotation.RequestMapping ;
import org.springframework.web.bind.annotation.RequestMethod ;
import org.springframework.web.bind.annotation.RequestParam ;
import org.springframework.web.bind.annotation.ResponseBody ;

import com.google.gson.Gson ;
import com.keepcount.controller.signup.EmailValidator ;
import com.keepcount.model.signup.SignUp ;
import com.keepcount.service.login.LoginService ;

@Controller
public class LoginController {

	private static String emailHeld ;
	private String emailForMatch ;
	private String passwordForMatch ;
	private SignUp signUpForMatch ;

	public static String getEmailHeld() {

		return emailHeld ;
	}

	public static void setEmailHeld( String emailHeld ) {

		LoginController.emailHeld = emailHeld ;
	}

	public SignUp getSignUpForMatch() {

		return signUpForMatch ;
	}

	public void setSignUpForMatch( SignUp signUpForMatch ) {

		this.signUpForMatch = signUpForMatch ;
	}

	public String getEmailForMatch() {

		return emailForMatch ;
	}

	public void setEmailForMatch( String emailForMatch ) {

		this.emailForMatch = emailForMatch ;
	}

	public String getPasswordForMatch() {

		return passwordForMatch ;
	}

	public void setPasswordForMatch( String passwordForMatch ) {

		this.passwordForMatch = passwordForMatch ;
	}

	@Autowired
	private LoginService loginService ;

	/*
	 * @GetMapping ( value = "/login/login" ) public String showLoginPage( Login
	 * login ) {
	 * 
	 * return "keepCount/login/login";
	 * 
	 * }
	 */

	private List < SignUp > signUps ;

	public List < SignUp > getSignUps() {

		return signUps ;
	}

	public void setSignUps( List < SignUp > signUps ) {

		this.signUps = signUps ;
	}

	@RequestMapping( value = "/api/login/findAllSignUps" , method = RequestMethod.GET , produces = "application/json" )
	@ResponseBody
	public String findAllEmailsSignedUp() {

		List < SignUp > list = loginService.findAllSignUps() ;

		this.setSignUps( list ) ;

		System.out.println( "list: " + list ) ;

		this.setSignUps( list ) ;

		return new Gson().toJson( list ) ;

	}

	/*
	 * 
	 * 
	 * This methods helps to check whether the email specified by the user already
	 * exists when the client tries to sign it up
	 * 
	 * 
	 */

	@RequestMapping( value = "/api/login/checkEmailExistence" , method = RequestMethod.POST )
	@ResponseBody
	public String checkEmailExistence( @RequestBody SignUp signUp ) {

		System.out.println( "signs2: " + this.getSignUps() ) ;

		List < SignUp > message = new ArrayList <>() ;

		List < String > list = new ArrayList <>() ;

		for ( SignUp up : loginService.findAllSignUps() ) {
			String email = up.getEmail() ;
			list.add( email ) ;
		}

		SignUp signUpMessage = new SignUp() ;

		String email = signUp.getEmail() ;
		System.out.println( "login email: " + email ) ;

		if ( list.contains( email ) ) {

			this.setEmailForMatch( signUp.getEmail() ) ;
			System.out.println( "contains" ) ;
			signUpMessage.setEmail( "good to continue to choose business" ) ;

		} else {
			System.out.println( "contains not" ) ;
			signUpMessage.setEmail( "email does not exist" ) ;

		}

		message.add( signUpMessage ) ;

		this.setEmailExistenceMessage( message ) ;

		return new Gson().toJson( message ) ;
	}

	/*
	 *
	 * 
	 * This method returns a message of whether this email specified for sign up
	 * exists or not
	 * 
	 * 
	 */
	@RequestMapping( value = "/api/login/returnEmailExistenceMessage" , method = RequestMethod.GET , produces = "application/json" )
	@ResponseBody
	public String returnEmailExistenceMessage() {

		// System.out.println( "signs.....: " + this.getSignUps() );
		System.out.println( "nsg: " + this.getEmailExistenceMessage().get( 0 ).getEmail() ) ;

		return new Gson().toJson( this.getEmailExistenceMessage() ) ;
	}

	@RequestMapping( value = "/api/login/emailCheck" )
	@ResponseBody
	public String emailCheck( @RequestParam( name = "emailProvided" ) String emailProvided ) {

		String emailProvd = loginService.getEmailToCheckIfTheProvidedCorrespondentExists( emailProvided ) ;
		// System.out.println("ems login: " + emailProvd);

		String finalValue = null ;

		if ( emailProvd != null ) {
			finalValue = emailProvd ;
			// setEmailHeld( finalValue );
		} else {
			finalValue = "" ;
		}

		return new Gson().toJson( finalValue ) ;

	}

	List < SignUp > emailExistenceMessage ;

	public List < SignUp > getEmailExistenceMessage() {

		return emailExistenceMessage ;
	}

	public void setEmailExistenceMessage( List < SignUp > emailExistenceMessage ) {

		this.emailExistenceMessage = emailExistenceMessage ;
	}

	@RequestMapping( value = "/api/login/validateEmail" , method = RequestMethod.POST )
	@ResponseBody
	public String validateEmail( @RequestBody SignUp signUp , HttpServletRequest request , HttpServletResponse responser ) {

		String email = signUp.getEmail() ;

		this.setEmailForMatch( email ) ;

		EmailValidator validator = new EmailValidator() ;
		boolean result = validator.validate( email ) ;

		System.out.println( "validation: " + result ) ;

		if ( result == false ) {

			List < SignUp > message = new ArrayList <>() ;

			SignUp up = new SignUp() ;

			up.setEmail( "Please re-check your email." ) ;

			message.add( up ) ;

			this.setEmailExistenceMessage( message ) ;

		} else if ( result == true ) {

			List < SignUp > message = new ArrayList <>() ;

			SignUp up = new SignUp() ;

			up.setEmail( "Email validated." ) ;

			message.add( up ) ;

			this.setEmailExistenceMessage( message ) ;

		}

		return "/api/login/validateEmail" ;
	}

	// after signing up
	@RequestMapping( value = "/api/login/returnEmailValidationMessage" , method = RequestMethod.GET , produces = "application/json" )
	@ResponseBody
	public String returnEmailValidationMessage() {
		System.out.println( "em...: " + this.getEmailExistenceMessage() ) ;
		return new Gson().toJson( this.getEmailExistenceMessage() ) ;
	}

	@RequestMapping( value = "/api/login/checkPasswordExistence" , method = RequestMethod.POST )
	@ResponseBody
	public String checkPasswordExistence( @RequestBody SignUp signUp , HttpServletRequest request , HttpServletResponse responser ) {

		String passwordPlainText = signUp.getPassword() ;

		System.out.println( "password: " + passwordPlainText ) ;

		String passwordHashed = hashPassword( passwordPlainText ) ;

		SignUp upToGiveAMatch = new SignUp() ;
		upToGiveAMatch.setEmail( this.getEmailForMatch() ) ;
		upToGiveAMatch.setPassword( passwordHashed ) ;

		System.out.println( "Up to give a match: " + upToGiveAMatch ) ;

		System.out.println( "password hashed: " + passwordHashed ) ;

		List < String > allPassword = new ArrayList <>() ;

		List < String > allMatchingPasswords = new ArrayList <>() ;

		for ( SignUp up : loginService.findAllSignUps() ) {

			String pass = up.getPassword() ;
			String email = up.getEmail() ;

			allPassword.add( pass ) ;

			SignUp newUp = new SignUp() ;

			newUp.setEmail( email ) ;
			newUp.setPassword( pass ) ;

			// System.out.println( "passwords...: " + pass );

		}

		Map < String , SignUp > signUpsToGiveFinalMatch = new LinkedHashMap <>() ;

		for ( int i = 0 ; i < allPassword.size() ; i++ ) {

			String aPasswordToBeMatched = allPassword.get( i ) ;

			String matching = checkPass( passwordPlainText , aPasswordToBeMatched ) ;

			if ( matching.equals( "matched" ) ) {

				allMatchingPasswords.add( aPasswordToBeMatched ) ;

				SignUp up = new SignUp() ;

				up.setEmail( this.getEmailForMatch() ) ;
				this.setPasswordForMatch( aPasswordToBeMatched ) ;
				up.setPassword( this.getPasswordForMatch() ) ;

				signUpsToGiveFinalMatch.put( this.getEmailForMatch() , up ) ;

			}

		}

		System.out.println( "mapped: " + signUpsToGiveFinalMatch ) ;

		/*
		 * Checking if there is a matching email and password for the passed credentials
		 */

		SignUp theMatched = signUpsToGiveFinalMatch.get( upToGiveAMatch.getEmail() ) ;

		System.out.println( "The matched: " + theMatched ) ;

		if ( ( theMatched != null ) && ( theMatched.getEmail().equals( this.getEmailForMatch() ) )
				&& ( this.checkPass( passwordPlainText , theMatched.getPassword() ).equals( "matched" ) ) ) {

			List < SignUp > message = new ArrayList <>() ;

			SignUp up = new SignUp() ;

			up.setEmail( "Password exists" ) ;

			message.add( up ) ;

			System.out.println( "finally matched: ..........." ) ;

			LoginController.setEmailHeld( theMatched.getEmail() ) ;

			this.setEmailExistenceMessage( message ) ;

		} else {

			List < SignUp > message = new ArrayList <>() ;

			SignUp up = new SignUp() ;

			up.setEmail( "Password does not exist" ) ;

			message.add( up ) ;

			System.out.println( "failed to match: ..........." ) ;
			this.setEmailExistenceMessage( message ) ;
		}

		return "/api/login/checkPasswordExistence" ;
	}

	private String checkPass( String plainPassword , String hashedPassword ) {

		if ( BCrypt.checkpw( plainPassword , hashedPassword ) )

			return "matched" ;

		else

			return "notMatched" ;

	}

	private String hashPassword( String plainTextPassword ) {

		return BCrypt.hashpw( plainTextPassword , BCrypt.gensalt() ) ;

	}

	@RequestMapping( value = "/api/login/passwordCheck" )
	@ResponseBody
	public String checkPassword( @RequestParam( name = "emailProvided" ) String emailProvided ) {

		String emailProvd = loginService.checkPassword( emailProvided ) ;
		// System.out.println("ems login: " + emailProvd);

		String finalValue = null ;

		if ( emailProvd != null ) {
			finalValue = emailProvd ;
		} else {
			finalValue = "" ;
		}

		return new Gson().toJson( finalValue ) ;

	}

	@RequestMapping( value = "/api/login/passwordTextToHash" )
	@ResponseBody
	public String convertPassTextToHash( @RequestParam( "passwordProvided" ) String passwordProvided , @RequestParam( "hashedPasswordProvided" ) String hashedPasswordProvided ) {

		String finalValue = checkPass( passwordProvided , hashedPasswordProvided ) ;

		return new Gson().toJson( finalValue ) ;

	}

	@RequestMapping( value = "/api/login/emailMatch" )
	@ResponseBody
	public String getSignedUpEmailMatch( @RequestParam( "emailProvided" ) String emailProvided ) {

		String sqlQuery = "SELECT * FROM emails_kc WHERE email=?" ;
		SignUp emailProvd = loginService.getSignedUpEmailMatch( sqlQuery , emailProvided ) ;

		return new Gson().toJson( emailProvd ) ;

	}

}
