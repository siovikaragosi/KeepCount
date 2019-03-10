package com.keepcount.model.user;

import java.math.BigDecimal;
import java.util.Arrays;

public class UserDisplay {
	private BigDecimal id;
	private String userFirstName;
	private String userLastName;
	private String userPhoneNumber;
	private String userEmail;
	private String userNIN;
	private String imagePathBase64;
	private byte[] userPhoto;
	// private FileInputStream

	public UserDisplay() {

	}

	public UserDisplay(BigDecimal id, String userFirstName, String userLastName, String userPhoneNumber,
			String userEmail, String userNIN, String imagePathBase64, byte[] userPhoto) {
		super();
		this.id = id;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userPhoneNumber = userPhoneNumber;
		this.userEmail = userEmail;
		this.userNIN = userNIN;
		this.imagePathBase64 = imagePathBase64;
		this.userPhoto = userPhoto;
	}

	public UserDisplay(String userFirstName, String userLastName, String userPhoneNumber, String userEmail,
			String userNIN, String imagePathBase64, byte[] userPhoto) {
		super();
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userPhoneNumber = userPhoneNumber;
		this.userEmail = userEmail;
		this.userNIN = userNIN;
		this.imagePathBase64 = imagePathBase64;
		this.userPhoto = userPhoto;
	}

	public UserDisplay(BigDecimal id, String userFirstName, String userLastName, String userPhoneNumber,
			String userEmail, String userNIN, String imagePathBase64) {
		super();
		this.id = id;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userPhoneNumber = userPhoneNumber;
		this.userEmail = userEmail;
		this.userNIN = userNIN;
		this.imagePathBase64 = imagePathBase64;
	}

	public UserDisplay(String userFirstName, String userLastName, String userPhoneNumber, String userEmail,
			String userNIN, String imagePathBase64) {
		super();
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userPhoneNumber = userPhoneNumber;
		this.userEmail = userEmail;
		this.userNIN = userNIN;
		this.imagePathBase64 = imagePathBase64;
	}

	public UserDisplay(String userFirstName

			, String userLastName, String userPhoneNumber, String userEmail, String userNIN) {
		super();
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
		this.userPhoneNumber = userPhoneNumber;
		this.userEmail = userEmail;
		this.userNIN = userNIN;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getUserPhoneNumber() {
		return userPhoneNumber;
	}

	public void setUserPhoneNumber(String userPhoneNumber) {
		this.userPhoneNumber = userPhoneNumber;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserNIN() {
		return userNIN;
	}

	public void setUserNIN(String userNIN) {
		this.userNIN = userNIN;
	}

	public String getImagePathBase64() {
		return imagePathBase64;
	}

	public void setFileName(String imagePathBase64) {
		this.imagePathBase64 = imagePathBase64;
	}

	public byte[] getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(byte[] userPhoto) {
		this.userPhoto = userPhoto;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userFirstName=" + userFirstName + ", userLastName=" + userLastName
				+ ", userPhoneNumber=" + userPhoneNumber + ", userEmail=" + userEmail + ", userNIN=" + userNIN
				+ ", imagePathBase64=" + imagePathBase64 + ", userPhoto=" + Arrays.toString(userPhoto) + "]";
	}

}
