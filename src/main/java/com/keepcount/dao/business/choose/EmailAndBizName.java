package com.keepcount.dao.business.choose;

import java.math.BigDecimal;

public class EmailAndBizName {

	private BigDecimal id;
	private String email;
	private String bizName;

	public EmailAndBizName() {

	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public EmailAndBizName(BigDecimal id, String email, String bizName) {
		super();
		this.id = id;
		this.email = email;
		this.bizName = bizName;
	}

	public EmailAndBizName(String email, String bizName) {
		this.email = email;
		this.bizName = bizName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBizName() {
		return bizName;
	}

	public void setBizName(String bizName) {
		this.bizName = bizName;
	}

	@Override
	public String toString() {
		return "EmailAndBizName [id=" + id + ", email=" + email + ", bizName=" + bizName + "]";
	}

}
