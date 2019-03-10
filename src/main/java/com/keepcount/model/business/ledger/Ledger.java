
package com.keepcount.model.business.ledger ;

import java.math.BigDecimal ;

public class Ledger {

	private BigDecimal id ;
	private String dateServer ;
	private String dateClient ;
	private String ledgerName ;
	private String drParticular ;
	private String crParticular ;

	private BigDecimal drAmount ;
	private String drAmountStr ;
	private BigDecimal crAmount ;
	private String crAmountStr ;

	private BigDecimal itemId ;
	private BigDecimal transactionId ;

	public Ledger() {

	}

	public Ledger( BigDecimal id , String dateServer , String dateClient , String ledgerName , String drParticular , String crParticular , BigDecimal drAmount ,
			String drAmountStr , BigDecimal crAmount , String crAmountStr , BigDecimal itemId , BigDecimal transactionId ) {
		super() ;
		this.id = id ;
		this.dateServer = dateServer ;
		this.dateClient = dateClient ;
		this.ledgerName = ledgerName ;
		this.drParticular = drParticular ;
		this.crParticular = crParticular ;
		this.drAmount = drAmount ;
		this.drAmountStr = drAmountStr ;
		this.crAmount = crAmount ;
		this.crAmountStr = crAmountStr ;
		this.itemId = itemId ;
		this.transactionId = transactionId ;
	}

	public BigDecimal getId() {
		return id ;
	}

	public void setId( BigDecimal id ) {
		this.id = id ;
	}

	public String getDateServer() {
		return dateServer ;
	}

	public void setDateServer( String dateServer ) {
		this.dateServer = dateServer ;
	}

	public String getDateClient() {
		return dateClient ;
	}

	public void setDateClient( String dateClient ) {
		this.dateClient = dateClient ;
	}

	public String getLedgerName() {
		return ledgerName ;
	}

	public void setLedgerName( String ledgerName ) {
		this.ledgerName = ledgerName ;
	}

	public String getDrParticular() {
		return drParticular ;
	}

	public void setDrParticular( String drParticular ) {
		this.drParticular = drParticular ;
	}

	public String getCrParticular() {
		return crParticular ;
	}

	public void setCrParticular( String crParticular ) {
		this.crParticular = crParticular ;
	}

	public BigDecimal getDrAmount() {
		return drAmount ;
	}

	public void setDrAmount( BigDecimal drAmount ) {
		this.drAmount = drAmount ;
	}

	public String getDrAmountStr() {
		return drAmountStr ;
	}

	public void setDrAmountStr( String drAmountStr ) {
		this.drAmountStr = drAmountStr ;
	}

	public BigDecimal getCrAmount() {
		return crAmount ;
	}

	public void setCrAmount( BigDecimal crAmount ) {
		this.crAmount = crAmount ;
	}

	public String getCrAmountStr() {
		return crAmountStr ;
	}

	public void setCrAmountStr( String crAmountStr ) {
		this.crAmountStr = crAmountStr ;
	}

	public BigDecimal getItemId() {
		return itemId ;
	}

	public void setItemId( BigDecimal itemId ) {
		this.itemId = itemId ;
	}

	public BigDecimal getTransactionId() {
		return transactionId ;
	}

	public void setTransactionId( BigDecimal transactionId ) {
		this.transactionId = transactionId ;
	}

	@Override
	public String toString() {
		return "Ledger [id=" + id + ", dateServer=" + dateServer + ", dateClient=" + dateClient + ", ledgerName=" + ledgerName + ", drParticular=" + drParticular
				+ ", crParticular=" + crParticular + ", drAmount=" + drAmount + ", drAmountStr=" + drAmountStr + ", crAmount=" + crAmount + ", crAmountStr=" + crAmountStr
				+ ", itemId=" + itemId + ", transactionId=" + transactionId + "]" ;
	}

}
