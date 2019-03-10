package com.keepcount.model.login;

import java.math.BigDecimal;

public class Login {

	private BigDecimal id;
	private String email;
	private String password;
	private String date;
	private String timeIn;
	private String timeOut;

	public Login() {

	}

	public Login(BigDecimal id, String email, String password, String date, String timeIn, String timeOut) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.date = date;
		this.timeIn = timeIn;
		this.timeOut = timeOut;
	}

	public Login(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTimeIn() {
		return timeIn;
	}

	public void setTimeIn(String timeIn) {
		this.timeIn = timeIn;
	}

	public String getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}

	@Override
	public String toString() {
		return "Login [id=" + id + ", email=" + email + ", password=" + password + ", date=" + date + ", timeIn="
				+ timeIn + ", timeOut=" + timeOut + "]";
	}

}
