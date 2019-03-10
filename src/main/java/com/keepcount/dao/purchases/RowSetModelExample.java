
package com.keepcount.dao.purchases ;

import java.math.BigDecimal ;

public class RowSetModelExample {

	private BigDecimal id ;
	private String name ;
	private String course ;

	public RowSetModelExample() {
		// TODO Auto-generated constructor stub
	}

	public RowSetModelExample( BigDecimal id , String name , String course ) {
		super() ;
		this.id = id ;
		this.name = name ;
		this.course = course ;
	}

	public BigDecimal getId() {
		return id ;
	}

	public void setId( BigDecimal id ) {
		this.id = id ;
	}

	public String getName() {
		return name ;
	}

	public void setName( String name ) {
		this.name = name ;
	}

	public String getCourse() {
		return course ;
	}

	public void setCourse( String course ) {
		this.course = course ;
	}

	@Override
	public String toString() {
		return "RowSetModelExample [id=" + id + ", name=" + name + ", course=" + course + "]" ;
	}

}
