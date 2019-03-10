package com.keepcount.controller.login;

public class LoginMetaHolder {

	private String metaEmail;
	private String metaBusiness;

	private String metaCombined;

	public LoginMetaHolder() {

	}

	public LoginMetaHolder(String metaEmail, String metaBusiness, String metaCombined) {
		super();
		this.metaEmail = metaEmail;
		this.metaBusiness = metaBusiness;
		this.metaCombined = metaCombined;
	}

	public LoginMetaHolder(String metaEmail, String metaBusiness) {
		super();
		this.metaEmail = metaEmail;
		this.metaBusiness = metaBusiness;
	}

	public LoginMetaHolder(String metaCombined) {
		super();
		this.metaCombined = metaCombined;
	}

	public String getMetaEmail() {
		return metaEmail;
	}

	public void setMetaEmail(String metaEmail) {
		this.metaEmail = metaEmail;
	}

	public String getMetaBusiness() {
		return metaBusiness;
	}

	public void setMetaBusiness(String metaBusiness) {
		this.metaBusiness = metaBusiness;
	}

	public String getMetaCombined() {
		metaCombined = this.getMetaEmail().concat(this.getMetaBusiness());
		return metaCombined;
	}

	@Override
	public String toString() {
		return "LoginMetaHolder [metaEmail=" + metaEmail + ", metaBusiness=" + metaBusiness + ", metaCombined="
				+ metaCombined + "]";
	}

}
