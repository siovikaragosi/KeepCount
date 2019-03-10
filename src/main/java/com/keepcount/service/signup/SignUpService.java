
package com.keepcount.service.signup;

import java.util.List;

import org.springframework.stereotype.Service;

import com.keepcount.dao.signup.SignUpHibernation;
import com.keepcount.model.signup.SignUp;

@Service
public class SignUpService {

	public int signUpNewUser( SignUp signUp ) {

		int result = 0;

		result = SignUpHibernation.signUpNewUser( signUp );

		return result;
	}

	public List< SignUp > findAllSignUps() {

		return SignUpHibernation.findAllSignUps();

	}

	/*
		*//**
			 * @author luqman Holding already hibernated emails
			 */
	/*
	 * private List<EmailsSignedUp> emailsSignedUpsAlreadyHibernated;
	 * 
	 *//**
		 * @author luqman Holding already hibernated sign ups
		 *//*
			 * private List<SignUp> signedUpsAlreadyHibernated;
			 * 
			 * public int signUpNewUser(SignUp signUp) {
			 * 
			 * 
			 * BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); signUp.setPassword(encoder.encode(signUp.getPassword()));
			 * 
			 * signUpHibernation = new SignUpHibernation("");
			 * 
			 * int result = 0;
			 * 
			 * List<EmailsSignedUp> signedUps = this.getEmailsSignedUpsAlreadyHibernated();
			 * 
			 * if (signedUps != null) { result = signUpHibernation.signUpNewUser(signUp); } else { setEmailsSignedUpsAlreadyHibernated(new SignUpHibernation().getEmailsSignedUps()); result =
			 * signUpHibernation.signUpNewUser(signUp); }
			 * 
			 * return result;
			 * 
			 * }
			 * 
			 * public List<EmailsSignedUp> findAllEmailsSignedUp() { List<EmailsSignedUp> emailsSignedUps = new ArrayList<>(); List<EmailsSignedUp> signedUpsAlready =
			 * this.getEmailsSignedUpsAlreadyHibernated(); if (signedUpsAlready != null) { emailsSignedUps = this.getEmailsSignedUpsAlreadyHibernated(); System.out.println("see signeds 1" +
			 * emailsSignedUps); } else { emailsSignedUps = new SignUpHibernation().getEmailsSignedUps(); setEmailsSignedUpsAlreadyHibernated(emailsSignedUps); System.out.println("see signeds 2" +
			 * emailsSignedUps); }
			 * 
			 * return emailsSignedUps; }
			 * 
			 * public List<SignUp> findAllSignUps() {
			 * 
			 * signUpHibernation = new SignUpHibernation("");
			 * 
			 * List<SignUp> signUps = signUpHibernation.getSignUpsHibernated();
			 * 
			 * if (signUps == null) { signUps = new SignUpHibernation().getSignUpsHibernated(); }
			 * 
			 * return signUps; }
			 * 
			 * public List<SignUp> getSignedUpsAlreadyHibernated() { return signedUpsAlreadyHibernated; }
			 * 
			 * public void setSignedUpsAlreadyHibernated(List<SignUp> signedUpsAlreadyHibernated) { this.signedUpsAlreadyHibernated = signedUpsAlreadyHibernated; }
			 * 
			 * public List<EmailsSignedUp> getEmailsSignedUpsAlreadyHibernated() { return emailsSignedUpsAlreadyHibernated; }
			 * 
			 * public void setEmailsSignedUpsAlreadyHibernated(List<EmailsSignedUp> emailsSignedUpsAlreadyHibernated) { this.emailsSignedUpsAlreadyHibernated = emailsSignedUpsAlreadyHibernated; }
			 */
}
