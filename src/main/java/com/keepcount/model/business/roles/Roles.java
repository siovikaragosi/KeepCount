package com.keepcount.model.business.roles;

import java.math.BigDecimal;
import java.util.List;

public class Roles {

	private BigDecimal id;
	private String email;
	private List<String> priviledges;

	public Roles() {

	}

	public Roles(BigDecimal id, String email, List<String> priviledges) {
		super();
		this.id = id;
		this.email = email;
		this.priviledges = priviledges;
	}

	public Roles(String email, List<String> priviledges) {
		super();
		this.email = email;
		this.priviledges = priviledges;
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

	public List<String> getPriviledges() {
		return priviledges;
	}

	public void setPriviledges(List<String> priviledges) {
		this.priviledges = priviledges;
	}

	@Override
	public String toString() {
		return "Roles [id=" + id + ", email=" + email + ", priviledges=" + priviledges + "]";
	}

}
