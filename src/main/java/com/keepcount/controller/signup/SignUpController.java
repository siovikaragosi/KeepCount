
package com.keepcount.controller.signup ;

import java.util.ArrayList ;
import java.util.List ;

import javax.servlet.http.HttpServletRequest ;
import javax.servlet.http.HttpServletResponse ;

import org.springframework.beans.factory.annotation.Autowired ;
import org.springframework.mail.SimpleMailMessage ;
import org.springframework.mail.javamail.JavaMailSender ;
import org.springframework.stereotype.Controller ;
import org.springframework.web.bind.annotation.GetMapping ;
import org.springframework.web.bind.annotation.RequestBody ;
import org.springframework.web.bind.annotation.RequestMapping ;
import org.springframework.web.bind.annotation.RequestMethod ;
import org.springframework.web.bind.annotation.ResponseBody ;

import com.google.gson.Gson ;
import com.keepcount.model.signup.SignUp ;
import com.keepcount.service.langauge.LanguageService ;
import com.keepcount.service.signup.SignUpService ;

@Controller
public class SignUpController {

	@Autowired
	private SignUpService signUpService ;

	@Autowired
	private JavaMailSender javaMailSender ;
	@Autowired
	private LanguageService languageService ;

	@GetMapping( value = "/signupLogin" )
	public String showSignUpPage() {

		return "keepCount/signUp/sign-up" ;
		// return "keepCount/business/blank-page";
	}

	@RequestMapping( value = "/language/default" )
	public String defaultDefualtAtSignUpLogin() {

		String areaOfConcern = "signup" ;
		return new Gson().toJson( languageService.findAllLanguageWords( "en" , "US" , areaOfConcern ) ) ;
	}

	private void send( String to , String subject , String body ) {

		SimpleMailMessage message = new SimpleMailMessage() ;

		try {

			message.setSubject( subject ) ;
			message.setText( body ) ;
			message.setTo( to ) ;
			// message.setFrom(mail.getFrom());
			javaMailSender.send( message ) ;

		} catch ( Exception e ) {
			e.printStackTrace() ;
		}

	}

	@RequestMapping( value = "/api/signup/validateEmail" , method = RequestMethod.POST )
	@ResponseBody
	public String validateEmail( @RequestBody SignUp signUp , HttpServletRequest request , HttpServletResponse responser ) {

		String email = signUp.getEmail() ;

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

		return "/api/signup/validateEmail" ;
	}

	/*
	 * @RequestBody was removed then it worked
	 */

	@RequestMapping( value = "/api/signup" , method = RequestMethod.POST )
	@ResponseBody
	public String signUpNewEmail( @RequestBody SignUp signUp , HttpServletRequest request , HttpServletResponse response ) {

		int result = 0 ;

		System.out.println( "......................sign up email: " + signUp.getEmail() + "\nsign up password: " + signUp.getPassword() ) ;

		result = signUpService.signUpNewUser( signUp ) ;

		System.out.println( "result: " + result ) ;

		if ( result == 1 ) {

			this.send( signUp.getEmail() , "Welcome to KeepCount" , "Please click on the link below to login and start off\n "

					+ "http://localhost:8080/keepCount/signUp/signup" ) ;

			List < SignUp > message = new ArrayList <>() ;

			SignUp up = new SignUp() ;

			up.setEmail( "Sign up complete, please login to get started" ) ;

			message.add( up ) ;

			this.setEmailExistenceMessage( message ) ;

			return "keepCount/signUp/sign-up" ;

		}

		else {

			List < SignUp > message = new ArrayList <>() ;

			SignUp up = new SignUp() ;

			up.setEmail( "Sign up failed. Try again" ) ;

			message.add( up ) ;

			this.setEmailExistenceMessage( message ) ;

			return "keepCount/signUp/sign-up" ;

		}

	}

	@RequestMapping( value = "/api/returnEmailValidationMessage" , method = RequestMethod.GET , produces = "application/json" )
	@ResponseBody
	public String returnSignUpMessage() {

		System.out.println( "return message: " + this.getEmailExistenceMessage() ) ;

		return new Gson().toJson( this.getEmailExistenceMessage() ) ;
	}

	// after signing up
	@RequestMapping( value = "/api/returnSignUpMessage" , method = RequestMethod.GET , produces = "application/json" )
	@ResponseBody
	public String returnEmailValidationMessage() {

		return new Gson().toJson( this.getEmailExistenceMessage() ) ;
	}

	private List < SignUp > signUps ;

	public List < SignUp > getSignUps() {

		return signUps ;
	}

	public void setSignUps( List < SignUp > signUps ) {

		this.signUps = signUps ;
	}

	/* Getting all sign ups */
	@RequestMapping( value = "/api/findAllSignUps" , method = RequestMethod.GET , produces = "application/json" )
	@ResponseBody
	public String findAllEmailsSignedUp() {

		List < SignUp > list = signUpService.findAllSignUps() ;

		System.out.println( "list: " + list ) ;

		this.setSignUps( list ) ;

		return new Gson().toJson( list ) ;

	}

	List < SignUp > emailExistenceMessage ;

	public List < SignUp > getEmailExistenceMessage() {

		return emailExistenceMessage ;
	}

	public void setEmailExistenceMessage( List < SignUp > emailExistenceMessage ) {

		this.emailExistenceMessage = emailExistenceMessage ;
	}

	/*
	 * 
	 * 
	 * This methods helps to check whether the email specified by the user already exists when the client tries to sign it up
	 * 
	 * 
	 */
	@RequestMapping( value = "/api/checkEmailExistence" , method = RequestMethod.POST )
	@ResponseBody
	public String checkEmailExistence( @RequestBody SignUp signUp ) {

		System.out.println( "signs2: " + this.getSignUps() ) ;

		List < SignUp > message = new ArrayList <>() ;

		List < String > list = new ArrayList <>() ;

		List < SignUp > ups = this.getSignUps() ;

		for ( SignUp up : signUpService.findAllSignUps() ) {
			String email = up.getEmail() ;
			System.out.println( "emssss...: " + email ) ;
			list.add( email ) ;
		}

		System.out.println( "upssss: " + ups ) ;
		System.out.println( "Emails: " + list ) ;

		SignUp signUpMessage = new SignUp() ;

		if ( list.contains( signUp.getEmail() ) ) {
			signUpMessage.setEmail( "This email is already signed up" ) ;
		} else {
			signUpMessage.setEmail( "good to go" ) ;
		}

		System.out.println( "msg2.." + signUpMessage.getEmail() ) ;

		message.add( signUpMessage ) ;

		System.out.println( "mess: " + message ) ;

		this.setEmailExistenceMessage( message ) ;

		System.out.println( "get msg: " + this.getEmailExistenceMessage() ) ;

		return new Gson().toJson( message ) ;
	}

	/*
	 *
	 * 
	 * This method returns a message of whether this email specified for sign up exists or not
	 * 
	 * 
	 */
	@RequestMapping( value = "/api/returnEmailExistenceMessage" , method = RequestMethod.GET , produces = "application/json" )
	@ResponseBody
	public String returnEmailExistenceMessage() {

		System.out.println( "signs.....: " + this.getSignUps() ) ;
		System.out.println( "nsg: " + this.getEmailExistenceMessage().get( 0 ).getEmail() ) ;

		return new Gson().toJson( this.getEmailExistenceMessage() ) ;
	}

}
