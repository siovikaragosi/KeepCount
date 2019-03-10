package com.keepcount.model.pos;

import java.math.BigDecimal;

public class PosCredit {

	private BigDecimal id;
	/* 1 */
	private String dateServer;
	/* 2 */
	private String dateClient;
	/* 3 */
	private String itemName;
	/* 4 */
	private BigDecimal itemId;
	/* 5 */
	private BigDecimal itemQuantity;
	private String itemQuantityStr;
	/* 6 */
	private BigDecimal itemUnitCost;
	private String itemUnitCostStr;
	/* 7 */
	private BigDecimal itemTotalCost;
	private String itemTotalCostStr;
	/* 8 */
	private BigDecimal totalSalesCost;
	private String totalSalesCostStr;
	/* 9 */
	private BigDecimal creditInstallmentPaid;
	private String creditInstallmentPaidStr;
	/* 10 */
	private BigDecimal creditInstallmentBalanceToBePaid;
	private String creditInstallmentBalanceToBePaidStr;
	/* 11 */
	private BigDecimal amountPaidIn;
	private String amountPaidInStr;
	/* 12 */
	private BigDecimal changeOrBalance;
	private String changeOrBalanceStr;
	/* 13 */
	private String customerEmail;
	/* 14 */
	private String customerPhone;
	/* 15 */
	private String businessName;

	public PosCredit() {

	}

	public PosCredit(BigDecimal id, String dateServer, String dateClient, String itemName, BigDecimal itemId, BigDecimal itemQuantity, String itemQuantityStr, BigDecimal itemUnitCost,
											String itemUnitCostStr, BigDecimal itemTotalCost, String itemTotalCostStr, BigDecimal totalSalesCost, String totalSalesCostStr, BigDecimal creditInstallmentPaid,
											String creditInstallmentPaidStr, BigDecimal creditInstallmentBalanceToBePaid, String creditInstallmentBalanceToBePaidStr, BigDecimal amountPaidIn,
											String amountPaidInStr, BigDecimal changeOrBalance, String changeOrBalanceStr, String customerEmail, String customerPhone, String businessName) {
		super();
		this.id = id;
		this.dateServer = dateServer;
		this.dateClient = dateClient;
		this.itemName = itemName;
		this.itemId = itemId;
		this.itemQuantity = itemQuantity;
		this.itemQuantityStr = itemQuantityStr;
		this.itemUnitCost = itemUnitCost;
		this.itemUnitCostStr = itemUnitCostStr;
		this.itemTotalCost = itemTotalCost;
		this.itemTotalCostStr = itemTotalCostStr;
		this.totalSalesCost = totalSalesCost;
		this.totalSalesCostStr = totalSalesCostStr;
		this.creditInstallmentPaid = creditInstallmentPaid;
		this.creditInstallmentPaidStr = creditInstallmentPaidStr;
		this.creditInstallmentBalanceToBePaid = creditInstallmentBalanceToBePaid;
		this.creditInstallmentBalanceToBePaidStr = creditInstallmentBalanceToBePaidStr;
		this.amountPaidIn = amountPaidIn;
		this.amountPaidInStr = amountPaidInStr;
		this.changeOrBalance = changeOrBalance;
		this.changeOrBalanceStr = changeOrBalanceStr;
		this.customerEmail = customerEmail;
		this.customerPhone = customerPhone;
		this.businessName = businessName;
	}

	public PosCredit(BigDecimal id, String dateServer, String dateClient, String itemName, BigDecimal itemId, BigDecimal itemQuantity, BigDecimal itemUnitCost, BigDecimal itemTotalCost,
											BigDecimal totalSalesCost, BigDecimal creditInstallmentPaid, BigDecimal creditInstallmentBalanceToBePaid, BigDecimal amountPaidIn, BigDecimal changeOrBalance,
											String customerEmail, String customerPhone, String businessName) {
		super();
		this.id = id;
		this.dateServer = dateServer;
		this.dateClient = dateClient;
		this.itemName = itemName;
		this.itemId = itemId;
		this.itemQuantity = itemQuantity;
		this.itemUnitCost = itemUnitCost;
		this.itemTotalCost = itemTotalCost;
		this.totalSalesCost = totalSalesCost;
		this.creditInstallmentPaid = creditInstallmentPaid;
		this.creditInstallmentBalanceToBePaid = creditInstallmentBalanceToBePaid;
		this.amountPaidIn = amountPaidIn;
		this.changeOrBalance = changeOrBalance;
		this.customerEmail = customerEmail;
		this.customerPhone = customerPhone;
		this.businessName = businessName;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getDateServer() {
		return dateServer;
	}

	public void setDateServer(String dateServer) {
		this.dateServer = dateServer;
	}

	public String getDateClient() {
		return dateClient;
	}

	public void setDateClient(String dateClient) {
		this.dateClient = dateClient;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public BigDecimal getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(BigDecimal itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public String getItemQuantityStr() {
		return itemQuantityStr;
	}

	public void setItemQuantityStr(String itemQuantityStr) {
		this.itemQuantityStr = itemQuantityStr;
	}

	public BigDecimal getItemUnitCost() {
		return itemUnitCost;
	}

	public void setItemUnitCost(BigDecimal itemUnitCost) {
		this.itemUnitCost = itemUnitCost;
	}

	public String getItemUnitCostStr() {
		return itemUnitCostStr;
	}

	public void setItemUnitCostStr(String itemUnitCostStr) {
		this.itemUnitCostStr = itemUnitCostStr;
	}

	public BigDecimal getItemTotalCost() {
		return itemTotalCost;
	}

	public void setItemTotalCost(BigDecimal itemTotalCost) {
		this.itemTotalCost = itemTotalCost;
	}

	public String getItemTotalCostStr() {
		return itemTotalCostStr;
	}

	public void setItemTotalCostStr(String itemTotalCostStr) {
		this.itemTotalCostStr = itemTotalCostStr;
	}

	public BigDecimal getTotalSalesCost() {
		return totalSalesCost;
	}

	public void setTotalSalesCost(BigDecimal totalSalesCost) {
		this.totalSalesCost = totalSalesCost;
	}

	public String getTotalSalesCostStr() {
		return totalSalesCostStr;
	}

	public void setTotalSalesCostStr(String totalSalesCostStr) {
		this.totalSalesCostStr = totalSalesCostStr;
	}

	public BigDecimal getCreditInstallmentPaid() {
		return creditInstallmentPaid;
	}

	public void setCreditInstallmentPaid(BigDecimal creditInstallmentPaid) {
		this.creditInstallmentPaid = creditInstallmentPaid;
	}

	public String getCreditInstallmentPaidStr() {
		return creditInstallmentPaidStr;
	}

	public void setCreditInstallmentPaidStr(String creditInstallmentPaidStr) {
		this.creditInstallmentPaidStr = creditInstallmentPaidStr;
	}

	public BigDecimal getCreditInstallmentBalanceToBePaid() {
		return creditInstallmentBalanceToBePaid;
	}

	public void setCreditInstallmentBalanceToBePaid(BigDecimal creditInstallmentBalanceToBePaid) {
		this.creditInstallmentBalanceToBePaid = creditInstallmentBalanceToBePaid;
	}

	public String getCreditInstallmentBalanceToBePaidStr() {
		return creditInstallmentBalanceToBePaidStr;
	}

	public void setCreditInstallmentBalanceToBePaidStr(String creditInstallmentBalanceToBePaidStr) {
		this.creditInstallmentBalanceToBePaidStr = creditInstallmentBalanceToBePaidStr;
	}

	public BigDecimal getAmountPaidIn() {
		return amountPaidIn;
	}

	public void setAmountPaidIn(BigDecimal amountPaidIn) {
		this.amountPaidIn = amountPaidIn;
	}

	public String getAmountPaidInStr() {
		return amountPaidInStr;
	}

	public void setAmountPaidInStr(String amountPaidInStr) {
		this.amountPaidInStr = amountPaidInStr;
	}

	public BigDecimal getChangeOrBalance() {
		return changeOrBalance;
	}

	public void setChangeOrBalance(BigDecimal changeOrBalance) {
		this.changeOrBalance = changeOrBalance;
	}

	public String getChangeOrBalanceStr() {
		return changeOrBalanceStr;
	}

	public void setChangeOrBalanceStr(String changeOrBalanceStr) {
		this.changeOrBalanceStr = changeOrBalanceStr;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	@Override
	public String toString() {
		return "PosCredit [id=" + id + ", dateServer=" + dateServer + ", dateClient=" + dateClient + ", itemName=" + itemName + ", itemId=" + itemId + ", itemQuantity=" + itemQuantity
												+ ", itemQuantityStr=" + itemQuantityStr + ", itemUnitCost=" + itemUnitCost + ", itemUnitCostStr=" + itemUnitCostStr + ", itemTotalCost=" + itemTotalCost
												+ ", itemTotalCostStr=" + itemTotalCostStr + ", totalSalesCost=" + totalSalesCost + ", totalSalesCostStr=" + totalSalesCostStr + ", creditInstallmentPaid="
												+ creditInstallmentPaid + ", creditInstallmentPaidStr=" + creditInstallmentPaidStr + ", creditInstallmentBalanceToBePaid=" + creditInstallmentBalanceToBePaid
												+ ", creditInstallmentBalanceToBePaidStr=" + creditInstallmentBalanceToBePaidStr + ", amountPaidIn=" + amountPaidIn + ", amountPaidInStr=" + amountPaidInStr
												+ ", changeOrBalance=" + changeOrBalance + ", changeOrBalanceStr=" + changeOrBalanceStr + ", customerEmail=" + customerEmail + ", customerPhone="
												+ customerPhone + ", businessName=" + businessName + "]";
	}

}
