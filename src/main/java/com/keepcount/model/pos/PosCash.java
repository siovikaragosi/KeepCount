
package com.keepcount.model.pos;

import java.math.BigDecimal;
import java.math.BigInteger;

public class PosCash {

	private BigDecimal id;
	/* 1 */ private String dateServer;
	/* 2 */ private String dateClient;
	/* 3 */ private BigDecimal itemId;
	/* 4 */ private BigDecimal itemQuantity;
	/* 5 == 4 */ private String itemQuantityStr;
	/* 6 */ private BigDecimal itemUnitCost;
	/* 7 == 6 */ private String itemUnitCostStr;
	/* 8 */ private BigDecimal itemTotalCost;
	/* 9 == 7 */ private String itemTotalCostStr;
	/* 10 */ private BigDecimal totalSalesCost;
	/* 11 == 8 */ private String totalSalesCostStr;
	/* 12 */ private BigDecimal amountPaidIn;
	/* 13 == 9 */ private String amountPaidInStr;
	/* 14 */ private BigDecimal changeOrBalance;
	/* 15 == 10 */ private String changeOrBalanceStr;
	/* 16 == 11 */ private String customerEmail;
	/* 17 == 12 */ private String customerPhone;
	/* 18 == 13 */ private String businessName;
	/* 19 == 14 */ private BigDecimal transactionId;

	public static void main( String[] args ) {

		for (int i = 0; i < 10; i++) {
			// BigInteger num = (Math.random() * 1_000_000_000_000) + 1;

			int num = (int) ((Math.random() * 1_000_00));

			int num2 = (int) ((Math.random() * 1_000_000_000));

			String numFirst = String.valueOf(num);
			int numFirstLen = numFirst.length();

			if ( numFirstLen < 5 ) {

			}

			System.out.println("first: " + num);
			System.out.println("second: " + num2);
			System.out.println();
		}

	}

	public PosCash() {

	}

	public PosCash(BigDecimal id, String dateServer, String dateClient, BigDecimal itemId, BigDecimal itemQuantity, String itemQuantityStr, BigDecimal itemUnitCost, String itemUnitCostStr,
											BigDecimal itemTotalCost, String itemTotalCostStr, BigDecimal totalSalesCost, String totalSalesCostStr, BigDecimal amountPaidIn, String amountPaidInStr,
											BigDecimal changeOrBalance, String changeOrBalanceStr, String customerEmail, String customerPhone, String businessName, BigDecimal transactionId) {

		super();
		this.id = id;
		this.dateServer = dateServer;
		this.dateClient = dateClient;
		this.itemId = itemId;
		this.itemQuantity = itemQuantity;
		this.itemQuantityStr = itemQuantityStr;
		this.itemUnitCost = itemUnitCost;
		this.itemUnitCostStr = itemUnitCostStr;
		this.itemTotalCost = itemTotalCost;
		this.itemTotalCostStr = itemTotalCostStr;
		this.totalSalesCost = totalSalesCost;
		this.totalSalesCostStr = totalSalesCostStr;
		this.amountPaidIn = amountPaidIn;
		this.amountPaidInStr = amountPaidInStr;
		this.changeOrBalance = changeOrBalance;
		this.changeOrBalanceStr = changeOrBalanceStr;
		this.customerEmail = customerEmail;
		this.customerPhone = customerPhone;
		this.businessName = businessName;
		this.transactionId = transactionId;
	}

	public BigDecimal getId() {

		return id;
	}

	public void setId( BigDecimal id ) {

		this.id = id;
	}

	public String getDateServer() {

		return dateServer;
	}

	public void setDateServer( String dateServer ) {

		this.dateServer = dateServer;
	}

	public String getDateClient() {

		return dateClient;
	}

	public void setDateClient( String dateClient ) {

		this.dateClient = dateClient;
	}

	public BigDecimal getItemId() {

		return itemId;
	}

	public void setItemId( BigDecimal itemId ) {

		this.itemId = itemId;
	}

	public BigDecimal getItemQuantity() {

		return itemQuantity;
	}

	public void setItemQuantity( BigDecimal itemQuantity ) {

		this.itemQuantity = itemQuantity;
	}

	public String getItemQuantityStr() {

		return itemQuantityStr;
	}

	public void setItemQuantityStr( String itemQuantityStr ) {

		this.itemQuantityStr = itemQuantityStr;
	}

	public BigDecimal getItemUnitCost() {

		return itemUnitCost;
	}

	public void setItemUnitCost( BigDecimal itemUnitCost ) {

		this.itemUnitCost = itemUnitCost;
	}

	public String getItemUnitCostStr() {

		return itemUnitCostStr;
	}

	public void setItemUnitCostStr( String itemUnitCostStr ) {

		this.itemUnitCostStr = itemUnitCostStr;
	}

	public BigDecimal getItemTotalCost() {

		return itemTotalCost;
	}

	public void setItemTotalCost( BigDecimal itemTotalCost ) {

		this.itemTotalCost = itemTotalCost;
	}

	public String getItemTotalCostStr() {

		return itemTotalCostStr;
	}

	public void setItemTotalCostStr( String itemTotalCostStr ) {

		this.itemTotalCostStr = itemTotalCostStr;
	}

	public BigDecimal getTotalSalesCost() {

		return totalSalesCost;
	}

	public void setTotalSalesCost( BigDecimal totalSalesCost ) {

		this.totalSalesCost = totalSalesCost;
	}

	public String getTotalSalesCostStr() {

		return totalSalesCostStr;
	}

	public void setTotalSalesCostStr( String totalSalesCostStr ) {

		this.totalSalesCostStr = totalSalesCostStr;
	}

	public BigDecimal getAmountPaidIn() {

		return amountPaidIn;
	}

	public void setAmountPaidIn( BigDecimal amountPaidIn ) {

		this.amountPaidIn = amountPaidIn;
	}

	public String getAmountPaidInStr() {

		return amountPaidInStr;
	}

	public void setAmountPaidInStr( String amountPaidInStr ) {

		this.amountPaidInStr = amountPaidInStr;
	}

	public BigDecimal getChangeOrBalance() {

		return changeOrBalance;
	}

	public void setChangeOrBalance( BigDecimal changeOrBalance ) {

		this.changeOrBalance = changeOrBalance;
	}

	public String getChangeOrBalanceStr() {

		return changeOrBalanceStr;
	}

	public void setChangeOrBalanceStr( String changeOrBalanceStr ) {

		this.changeOrBalanceStr = changeOrBalanceStr;
	}

	public String getCustomerEmail() {

		return customerEmail;
	}

	public void setCustomerEmail( String customerEmail ) {

		this.customerEmail = customerEmail;
	}

	public String getCustomerPhone() {

		return customerPhone;
	}

	public void setCustomerPhone( String customerPhone ) {

		this.customerPhone = customerPhone;
	}

	public String getBusinessName() {

		return businessName;
	}

	public void setBusinessName( String businessName ) {

		this.businessName = businessName;
	}

	public BigDecimal getTransactionId() {

		return transactionId;
	}

	public void setTransactionId( BigDecimal transactionId ) {

		this.transactionId = transactionId;
	}

	@Override
	public String toString() {

		return "PosCash [id=" + id + ", dateServer=" + dateServer + ", dateClient=" + dateClient + ", itemId=" + itemId + ", itemQuantity=" + itemQuantity + ", itemQuantityStr=" + itemQuantityStr
												+ ", itemUnitCost=" + itemUnitCost + ", itemUnitCostStr=" + itemUnitCostStr + ", itemTotalCost=" + itemTotalCost + ", itemTotalCostStr=" + itemTotalCostStr
												+ ", totalSalesCost=" + totalSalesCost + ", totalSalesCostStr=" + totalSalesCostStr + ", amountPaidIn=" + amountPaidIn + ", amountPaidInStr="
												+ amountPaidInStr + ", changeOrBalance=" + changeOrBalance + ", changeOrBalanceStr=" + changeOrBalanceStr + ", customerEmail=" + customerEmail
												+ ", customerPhone=" + customerPhone + ", businessName=" + businessName + ", transactionId=" + transactionId + "]";
	}

}
