
package com.keepcount.model.purchases ;

import java.math.BigDecimal ;

public class PurchasesTwo {

	private BigDecimal id ;
	private String dateServer ;
	private String dateClient ;
	private BigDecimal itemId ;
	private BigDecimal itemQuantity ;

	private String itemQuantityStr ;
	private BigDecimal unitCost ;
	private String unitCostStr ;
	private boolean credit ;
	private BigDecimal discountReceived ;

	private String dicountReceivedStr ;
	private BigDecimal amountCleared ;
	private String amountClearedStr ;
	private BigDecimal totalCostManual ;
	private String totalCostManualStr ;

	private BigDecimal totalCostAuto ;
	private String totalCostAutoStr ;
	private BigDecimal balanceYetToBePaid ;
	private String balanceYetToBePaidStr ;
	private BigDecimal supplierId ;

	private String supplierName ;
	private String narration ;
	private BigDecimal transactionId ;

	public PurchasesTwo() {
		// TODO Auto-generated constructor stub
	}

	public PurchasesTwo( BigDecimal id , String dateServer , String dateClient , BigDecimal itemId , BigDecimal itemQuantity , String itemQuantityStr , BigDecimal unitCost ,
			String unitCostStr , boolean credit , BigDecimal discountReceived , String dicountReceivedStr , BigDecimal amountCleared , String amountClearedStr ,
			BigDecimal totalCostManual , String totalCostManualStr , BigDecimal totalCostAuto , String totalCostAutoStr , BigDecimal balanceYetToBePaid ,
			String balanceYetToBePaidStr , BigDecimal supplierId , String supplierName , String narration , BigDecimal transactionId ) {
		super() ;
		this.id = id ;
		this.dateServer = dateServer ;
		this.dateClient = dateClient ;
		this.itemId = itemId ;
		this.itemQuantity = itemQuantity ;
		this.itemQuantityStr = itemQuantityStr ;
		this.unitCost = unitCost ;
		this.unitCostStr = unitCostStr ;
		this.credit = credit ;
		this.discountReceived = discountReceived ;
		this.dicountReceivedStr = dicountReceivedStr ;
		this.amountCleared = amountCleared ;
		this.amountClearedStr = amountClearedStr ;
		this.totalCostManual = totalCostManual ;
		this.totalCostManualStr = totalCostManualStr ;
		this.totalCostAuto = totalCostAuto ;
		this.totalCostAutoStr = totalCostAutoStr ;
		this.balanceYetToBePaid = balanceYetToBePaid ;
		this.balanceYetToBePaidStr = balanceYetToBePaidStr ;
		this.supplierId = supplierId ;
		this.supplierName = supplierName ;
		this.narration = narration ;
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

	public BigDecimal getItemId() {
		return itemId ;
	}

	public void setItemId( BigDecimal itemId ) {
		this.itemId = itemId ;
	}

	public BigDecimal getItemQuantity() {
		return itemQuantity ;
	}

	public void setItemQuantity( BigDecimal itemQuantity ) {
		this.itemQuantity = itemQuantity ;
	}

	public String getItemQuantityStr() {
		return itemQuantityStr ;
	}

	public void setItemQuantityStr( String itemQuantityStr ) {
		this.itemQuantityStr = itemQuantityStr ;
	}

	public BigDecimal getUnitCost() {
		return unitCost ;
	}

	public void setUnitCost( BigDecimal unitCost ) {
		this.unitCost = unitCost ;
	}

	public String getUnitCostStr() {
		return unitCostStr ;
	}

	public void setUnitCostStr( String unitCostStr ) {
		this.unitCostStr = unitCostStr ;
	}

	public boolean isCredit() {
		return credit ;
	}

	public void setCredit( boolean credit ) {
		this.credit = credit ;
	}

	public BigDecimal getDiscountReceived() {
		return discountReceived ;
	}

	public void setDiscountReceived( BigDecimal discountReceived ) {
		this.discountReceived = discountReceived ;
	}

	public String getDicountReceivedStr() {
		return dicountReceivedStr ;
	}

	public void setDicountReceivedStr( String dicountReceivedStr ) {
		this.dicountReceivedStr = dicountReceivedStr ;
	}

	public BigDecimal getAmountCleared() {
		return amountCleared ;
	}

	public void setAmountCleared( BigDecimal amountCleared ) {
		this.amountCleared = amountCleared ;
	}

	public String getAmountClearedStr() {
		return amountClearedStr ;
	}

	public void setAmountClearedStr( String amountClearedStr ) {
		this.amountClearedStr = amountClearedStr ;
	}

	public BigDecimal getTotalCostManual() {
		return totalCostManual ;
	}

	public void setTotalCostManual( BigDecimal totalCostManual ) {
		this.totalCostManual = totalCostManual ;
	}

	public String getTotalCostManualStr() {
		return totalCostManualStr ;
	}

	public void setTotalCostManualStr( String totalCostManualStr ) {
		this.totalCostManualStr = totalCostManualStr ;
	}

	public BigDecimal getTotalCostAuto() {
		return totalCostAuto ;
	}

	public void setTotalCostAuto( BigDecimal totalCostAuto ) {
		this.totalCostAuto = totalCostAuto ;
	}

	public String getTotalCostAutoStr() {
		return totalCostAutoStr ;
	}

	public void setTotalCostAutoStr( String totalCostAutoStr ) {
		this.totalCostAutoStr = totalCostAutoStr ;
	}

	public BigDecimal getBalanceYetToBePaid() {
		return balanceYetToBePaid ;
	}

	public void setBalanceYetToBePaid( BigDecimal balanceYetToBePaid ) {
		this.balanceYetToBePaid = balanceYetToBePaid ;
	}

	public String getBalanceYetToBePaidStr() {
		return balanceYetToBePaidStr ;
	}

	public void setBalanceYetToBePaidStr( String balanceYetToBePaidStr ) {
		this.balanceYetToBePaidStr = balanceYetToBePaidStr ;
	}

	public BigDecimal getSupplierId() {
		return supplierId ;
	}

	public void setSupplierId( BigDecimal supplierId ) {
		this.supplierId = supplierId ;
	}

	public String getSupplierName() {
		return supplierName ;
	}

	public void setSupplierName( String supplierName ) {
		this.supplierName = supplierName ;
	}

	public String getNarration() {
		return narration ;
	}

	public void setNarration( String narration ) {
		this.narration = narration ;
	}

	public BigDecimal getTransactionId() {
		return transactionId ;
	}

	public void setTransactionId( BigDecimal transactionId ) {
		this.transactionId = transactionId ;
	}

	@Override
	public String toString() {
		return "PurchasesTwo [id=" + id + ", dateServer=" + dateServer + ", dateClient=" + dateClient + ", itemId=" + itemId + ", itemQuantity=" + itemQuantity
				+ ", itemQuantityStr=" + itemQuantityStr + ", unitCost=" + unitCost + ", unitCostStr=" + unitCostStr + ", credit=" + credit + ", discountReceived="
				+ discountReceived + ", dicountReceivedStr=" + dicountReceivedStr + ", amountCleared=" + amountCleared + ", amountClearedStr=" + amountClearedStr
				+ ", totalCostManual=" + totalCostManual + ", totalCostManualStr=" + totalCostManualStr + ", totalCostAuto=" + totalCostAuto + ", totalCostAutoStr="
				+ totalCostAutoStr + ", balanceYetToBePaid=" + balanceYetToBePaid + ", balanceYetToBePaidStr=" + balanceYetToBePaidStr + ", supplierId=" + supplierId
				+ ", supplierName=" + supplierName + ", narration=" + narration + ", transactionId=" + transactionId + "]" ;
	}

}
