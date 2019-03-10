
package com.keepcount.model.signup;

import java.math.BigDecimal;

public class SignUp {

	private BigDecimal id;

	private String email;

	private String password;

	private String date;

	public SignUp() {

	}

	public SignUp( BigDecimal id , String email , String password , String date ) {

		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.date = date;
	}

	public BigDecimal getId() {

		return id;
	}

	public void setId( BigDecimal id ) {

		this.id = id;
	}

	public String getEmail() {

		return email;
	}

	public void setEmail( String email ) {

		this.email = email;
	}

	public String getPassword() {

		return password;
	}

	public void setPassword( String password ) {

		this.password = password;
	}

	public String getDate() {

		return date;
	}

	public void setDate( String date ) {

		this.date = date;
	}

	@Override
	public String toString() {

		return "SignUp [id=" + id + ", email=" + email + ", password=" + password + ", date=" + date + "]";
	}

}
