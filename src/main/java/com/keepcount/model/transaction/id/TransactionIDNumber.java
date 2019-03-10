
package com.keepcount.model.transaction.id ;

import java.math.BigDecimal ;

public class TransactionIDNumber {

	private BigDecimal id ;
	private BigDecimal transactionId ;

	public TransactionIDNumber() {
		// TODO Auto-generated constructor stub
	}

	public TransactionIDNumber( BigDecimal id , BigDecimal transactionId ) {
		super() ;
		this.id = id ;
		this.transactionId = transactionId ;
	}

	public BigDecimal getId() {
		return id ;
	}

	public void setId( BigDecimal id ) {
		this.id = id ;
	}

	public BigDecimal getTransactionId() {
		return transactionId ;
	}

	public void setTransactionId( BigDecimal transactionId ) {
		this.transactionId = transactionId ;
	}

	@Override
	public String toString() {
		return "TransactionIDNumber [id=" + id + ", transactionId=" + transactionId + "]" ;
	}

}
