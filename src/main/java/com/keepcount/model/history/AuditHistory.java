package com.keepcount.model.history;

import java.math.BigDecimal;

public class AuditHistory {

	private BigDecimal id;
	private String date;
	private String time;
	private String action;
	private String user;

	public AuditHistory(BigDecimal id, String date, String time, String action, String user) {
		super();
		this.id = id;
		this.date = date;
		this.time = time;
		this.action = action;
		this.user = user;
	}

	public AuditHistory(String date, String time, String action, String user) {
		super();
		this.date = date;
		this.time = time;
		this.action = action;
		this.user = user;
	}

	public AuditHistory(String user) {
		super();
		this.user = user;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "AuditHistory [id=" + id + ", date=" + date + ", time=" + time + ", action=" + action + ", user=" + user
				+ "]";
	}

	
}
