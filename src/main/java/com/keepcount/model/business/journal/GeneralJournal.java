
package com.keepcount.model.business.journal ;

import java.math.BigDecimal ;

public class GeneralJournal {

	private BigDecimal id ;
	private String dateServer ;
	private String dateClient ;
	private String particularDr ;
	private String particularCr ;
	private String narration ;
	private String referenceNumber ;
	private BigDecimal drAmount ;
	private String drAmountStr ;
	private BigDecimal crAmount ;
	private String cdAmountStr ;
	private BigDecimal itemId ;
	private BigDecimal transactionId ;

	public GeneralJournal() {

	}

	public GeneralJournal( BigDecimal id , String dateServer , String dateClient , String particularDr , String particularCr , String narration , String referenceNumber ,
			BigDecimal drAmount , String drAmountStr , BigDecimal crAmount , String cdAmountStr , BigDecimal itemId , BigDecimal transactionId ) {
		super() ;
		this.id = id ;
		this.dateServer = dateServer ;
		this.dateClient = dateClient ;
		this.particularDr = particularDr ;
		this.particularCr = particularCr ;
		this.narration = narration ;
		this.referenceNumber = referenceNumber ;
		this.drAmount = drAmount ;
		this.drAmountStr = drAmountStr ;
		this.crAmount = crAmount ;
		this.cdAmountStr = cdAmountStr ;
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

	public String getParticularDr() {
		return particularDr ;
	}

	public void setParticularDr( String particularDr ) {
		this.particularDr = particularDr ;
	}

	public String getParticularCr() {
		return particularCr ;
	}

	public void setParticularCr( String particularCr ) {
		this.particularCr = particularCr ;
	}

	public String getNarration() {
		return narration ;
	}

	public void setNarration( String narration ) {
		this.narration = narration ;
	}

	public String getReferenceNumber() {
		return referenceNumber ;
	}

	public void setReferenceNumber( String referenceNumber ) {
		this.referenceNumber = referenceNumber ;
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

	public String getCdAmountStr() {
		return cdAmountStr ;
	}

	public void setCdAmountStr( String cdAmountStr ) {
		this.cdAmountStr = cdAmountStr ;
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
		return "GeneralJournal [id=" + id + ", dateServer=" + dateServer + ", dateClient=" + dateClient + ", particularDr=" + particularDr + ", particularCr=" + particularCr
				+ ", narration=" + narration + ", referenceNumber=" + referenceNumber + ", drAmount=" + drAmount + ", drAmountStr=" + drAmountStr + ", crAmount=" + crAmount
				+ ", cdAmountStr=" + cdAmountStr + ", itemId=" + itemId + ", transactionId=" + transactionId + "]" ;
	}

}