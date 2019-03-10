package com.keepcount.model.signup;

import java.util.ArrayList;
import java.util.List;

public class EmailsSignedUp {

	private String email;

	public EmailsSignedUp() {

	}

	public List<EmailsSignedUp> addEmailSignedUp(List<String> list) {

		EmailsSignedUp up2 = new EmailsSignedUp();

		List<EmailsSignedUp> list2 = new ArrayList<>();

		for (double i = 0; i < list.size(); i++) {
			String s = list.get((int) i);
			up2 = new EmailsSignedUp(s);
			list2.add(up2);
		}

		// System.out.println(list2);

		return list2;

	}

	// public List<EmailsSignedUp> addEmailSignedUp(List<SignUp> list) {
	//
	// EmailsSignedUp up = new EmailsSignedUp();
	// EmailsSignedUp up2 = new EmailsSignedUp();
	//
	// List<EmailsSignedUp> list2 = new ArrayList<>();
	//
	// for (SignUp object : list) {
	// email = object.getEmail();
	// // up.setEmail(email);
	// up2 = new EmailsSignedUp(email);
	// list2.add(up2);
	// }
	//
	// System.out.println(list2);
	//
	// return list2;
	//
	// }

	public EmailsSignedUp(String email) {
		super();
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "EmailsSignedUp [email=" + email + "]";
	}

}
