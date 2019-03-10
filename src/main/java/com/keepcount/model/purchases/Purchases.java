package com.keepcount.model.purchases;

import java.math.BigDecimal;

public class Purchases {

	private BigDecimal id;
	private String dateServer;/* 1 */
	private String dateClient;/* 2 */
	private String item;/* 3 */
	private String itemSubCategory;/* 4 */
	private BigDecimal itemQuantity;/* 5 */
	private String itemQuantityStr;
	private BigDecimal itemPricePerUnit;/* 6 */
	private String itemPricePerUnitStr;
	private BigDecimal itemTotalManualCost;/* 7 */
	private String itemTotalManualCostStr;
	private BigDecimal itemTotalAutomaticCost;/* 8 */
	private String itemTotalAutomaticCostStr;
	private boolean credit;/* 9 */
	private String clearedBy;/* 10 */
	private BigDecimal amountPaid;/* 11 */
	private String amountPaidStr;
	private BigDecimal installmentPaid;/* 12 */
	private String installmentPaidStr;
	private BigDecimal installmentBalance;/* 13 */
	private String installmentBalanceStr;
	private String paymentMethod;/* 14 */
	private String supplierName;/* 15 */

	public Purchases() {

	}

	public Purchases(String item, String itemSubCategory, BigDecimal itemQuantity, String itemQuantityStr,
			BigDecimal itemPricePerUnit, String itemPricePerUnitStr, BigDecimal itemTotalManualCost,
			String itemTotalManualCostStr, BigDecimal itemTotalAutomaticCost, String itemTotalAutomaticCostStr,
			boolean credit, String clearedBy, BigDecimal amountPaid, String amountPaidStr, BigDecimal installmentPaid,
			String installmentPaidStr, BigDecimal installmentBalance, String installmentBalanceStr,
			String paymentMethod, String supplierName) {
		super();
		this.item = item;
		this.itemSubCategory = itemSubCategory;
		this.itemQuantity = itemQuantity;
		this.itemQuantityStr = itemQuantityStr;
		this.itemPricePerUnit = itemPricePerUnit;
		this.itemPricePerUnitStr = itemPricePerUnitStr;
		this.itemTotalManualCost = itemTotalManualCost;
		this.itemTotalManualCostStr = itemTotalManualCostStr;
		this.itemTotalAutomaticCost = itemTotalAutomaticCost;
		this.itemTotalAutomaticCostStr = itemTotalAutomaticCostStr;
		this.credit = credit;
		this.clearedBy = clearedBy;
		this.amountPaid = amountPaid;
		this.amountPaidStr = amountPaidStr;
		this.installmentPaid = installmentPaid;
		this.installmentPaidStr = installmentPaidStr;
		this.installmentBalance = installmentBalance;
		this.installmentBalanceStr = installmentBalanceStr;
		this.paymentMethod = paymentMethod;
		this.supplierName = supplierName;
	}

	public Purchases(BigDecimal id, String dateServer, String dateClient, String item, String itemSubCategory,
			BigDecimal itemQuantity, String itemQuantityStr, BigDecimal itemPricePerUnit, String itemPricePerUnitStr,
			BigDecimal itemTotalManualCost, String itemTotalManualCostStr, BigDecimal itemTotalAutomaticCost,
			String itemTotalAutomaticCostStr, boolean credit, String clearedBy, BigDecimal amountPaid,
			String amountPaidStr, BigDecimal installmentPaid, String installmentPaidStr, BigDecimal installmentBalance,
			String installmentBalanceStr, String paymentMethod, String supplierName) {
		super();
		this.id = id;
		this.dateServer = dateServer;
		this.dateClient = dateClient;
		this.item = item;
		this.itemSubCategory = itemSubCategory;
		this.itemQuantity = itemQuantity;
		this.itemQuantityStr = itemQuantityStr;
		this.itemPricePerUnit = itemPricePerUnit;
		this.itemPricePerUnitStr = itemPricePerUnitStr;
		this.itemTotalManualCost = itemTotalManualCost;
		this.itemTotalManualCostStr = itemTotalManualCostStr;
		this.itemTotalAutomaticCost = itemTotalAutomaticCost;
		this.itemTotalAutomaticCostStr = itemTotalAutomaticCostStr;
		this.credit = credit;
		this.clearedBy = clearedBy;
		this.amountPaid = amountPaid;
		this.amountPaidStr = amountPaidStr;
		this.installmentPaid = installmentPaid;
		this.installmentPaidStr = installmentPaidStr;
		this.installmentBalance = installmentBalance;
		this.installmentBalanceStr = installmentBalanceStr;
		this.paymentMethod = paymentMethod;
		this.supplierName = supplierName;
	}

	public Purchases(BigDecimal id, String dateServer, String dateClient, String item, String itemSubCategory,
			BigDecimal itemQuantity, BigDecimal itemPricePerUnit, BigDecimal itemTotalManualCost,
			BigDecimal itemTotalAutomaticCost, boolean credit, String clearedBy, BigDecimal amountPaid,
			BigDecimal installmentPaid, BigDecimal installmentBalance, String paymentMethod, String supplierName) {
		super();
		this.id = id;
		this.dateServer = dateServer;
		this.dateClient = dateClient;
		this.item = item;
		this.itemSubCategory = itemSubCategory;
		this.itemQuantity = itemQuantity;
		this.itemPricePerUnit = itemPricePerUnit;
		this.itemTotalManualCost = itemTotalManualCost;
		this.itemTotalAutomaticCost = itemTotalAutomaticCost;
		this.credit = credit;
		this.clearedBy = clearedBy;
		this.amountPaid = amountPaid;
		this.installmentPaid = installmentPaid;
		this.installmentBalance = installmentBalance;
		this.paymentMethod = paymentMethod;
		this.supplierName = supplierName;
	}

	public Purchases(String dateServer, String dateClient, String item, String itemSubCategory, BigDecimal itemQuantity,
			BigDecimal itemPricePerUnit, BigDecimal itemTotalManualCost, BigDecimal itemTotalAutomaticCost,
			boolean credit, String clearedBy, BigDecimal amountPaid, BigDecimal installmentPaid,
			BigDecimal installmentBalance, String paymentMethod, String supplierName) {
		super();
		this.dateServer = dateServer;
		this.dateClient = dateClient;
		this.item = item;
		this.itemSubCategory = itemSubCategory;
		this.itemQuantity = itemQuantity;
		this.itemPricePerUnit = itemPricePerUnit;
		this.itemTotalManualCost = itemTotalManualCost;
		this.itemTotalAutomaticCost = itemTotalAutomaticCost;
		this.credit = credit;
		this.clearedBy = clearedBy;
		this.amountPaid = amountPaid;
		this.installmentPaid = installmentPaid;
		this.installmentBalance = installmentBalance;
		this.paymentMethod = paymentMethod;
		this.supplierName = supplierName;
	}

	public Purchases(String dateClient, String item, String itemSubCategory, BigDecimal itemQuantity,
			BigDecimal itemPricePerUnit, BigDecimal itemTotalManualCost, BigDecimal itemTotalAutomaticCost,
			boolean credit, String clearedBy, BigDecimal amountPaid, BigDecimal installmentPaid,
			BigDecimal installmentBalance, String paymentMethod, String supplierName) {
		super();
		this.dateClient = dateClient;
		this.item = item;
		this.itemSubCategory = itemSubCategory;
		this.itemQuantity = itemQuantity;
		this.itemPricePerUnit = itemPricePerUnit;
		this.itemTotalManualCost = itemTotalManualCost;
		this.itemTotalAutomaticCost = itemTotalAutomaticCost;
		this.credit = credit;
		this.clearedBy = clearedBy;
		this.amountPaid = amountPaid;
		this.installmentPaid = installmentPaid;
		this.installmentBalance = installmentBalance;
		this.paymentMethod = paymentMethod;
		this.supplierName = supplierName;
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

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getItemSubCategory() {
		return itemSubCategory;
	}

	public void setItemSubCategory(String itemSubCategory) {
		this.itemSubCategory = itemSubCategory;
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

	public BigDecimal getItemPricePerUnit() {
		return itemPricePerUnit;
	}

	public void setItemPricePerUnit(BigDecimal itemPricePerUnit) {
		this.itemPricePerUnit = itemPricePerUnit;
	}

	public String getItemPricePerUnitStr() {
		return itemPricePerUnitStr;
	}

	public void setItemPricePerUnitStr(String itemPricePerUnitStr) {
		this.itemPricePerUnitStr = itemPricePerUnitStr;
	}

	public BigDecimal getItemTotalManualCost() {
		return itemTotalManualCost;
	}

	public void setItemTotalManualCost(BigDecimal itemTotalManualCost) {
		this.itemTotalManualCost = itemTotalManualCost;
	}

	public String getItemTotalManualCostStr() {
		return itemTotalManualCostStr;
	}

	public void setItemTotalManualCostStr(String itemTotalManualCostStr) {
		this.itemTotalManualCostStr = itemTotalManualCostStr;
	}

	public BigDecimal getItemTotalAutomaticCost() {
		return itemTotalAutomaticCost;
	}

	public void setItemTotalAutomaticCost(BigDecimal itemTotalAutomaticCost) {
		this.itemTotalAutomaticCost = itemTotalAutomaticCost;
	}

	public String getItemTotalAutomaticCostStr() {
		return itemTotalAutomaticCostStr;
	}

	public void setItemTotalAutomaticCostStr(String itemTotalAutomaticCostStr) {
		this.itemTotalAutomaticCostStr = itemTotalAutomaticCostStr;
	}

	public boolean isCredit() {
		return credit;
	}

	public void setCredit(boolean credit) {
		this.credit = credit;
	}

	public String getClearedBy() {
		return clearedBy;
	}

	public void setClearedBy(String clearedBy) {
		this.clearedBy = clearedBy;
	}

	public BigDecimal getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(BigDecimal amountPaid) {
		this.amountPaid = amountPaid;
	}

	public String getAmountPaidStr() {
		return amountPaidStr;
	}

	public void setAmountPaidStr(String amountPaidStr) {
		this.amountPaidStr = amountPaidStr;
	}

	public BigDecimal getInstallmentPaid() {
		return installmentPaid;
	}

	public void setInstallmentPaid(BigDecimal installmentPaid) {
		this.installmentPaid = installmentPaid;
	}

	public String getInstallmentPaidStr() {
		return installmentPaidStr;
	}

	public void setInstallmentPaidStr(String installmentPaidStr) {
		this.installmentPaidStr = installmentPaidStr;
	}

	public BigDecimal getInstallmentBalance() {
		return installmentBalance;
	}

	public void setInstallmentBalance(BigDecimal installmentBalance) {
		this.installmentBalance = installmentBalance;
	}

	public String getInstallmentBalanceStr() {
		return installmentBalanceStr;
	}

	public void setInstallmentBalanceStr(String installmentBalanceStr) {
		this.installmentBalanceStr = installmentBalanceStr;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	@Override
	public String toString() {
		return "Purchases [id=" + id + ", dateServer=" + dateServer + ", dateClient=" + dateClient + ", item=" + item
				+ ", itemSubCategory=" + itemSubCategory + ", itemQuantity=" + itemQuantity + ", itemQuantityStr="
				+ itemQuantityStr + ", itemPricePerUnit=" + itemPricePerUnit + ", itemPricePerUnitStr="
				+ itemPricePerUnitStr + ", itemTotalManualCost=" + itemTotalManualCost + ", itemTotalManualCostStr="
				+ itemTotalManualCostStr + ", itemTotalAutomaticCost=" + itemTotalAutomaticCost
				+ ", itemTotalAutomaticCostStr=" + itemTotalAutomaticCostStr + ", credit=" + credit + ", clearedBy="
				+ clearedBy + ", amountPaid=" + amountPaid + ", amountPaidStr=" + amountPaidStr + ", installmentPaid="
				+ installmentPaid + ", installmentPaidStr=" + installmentPaidStr + ", installmentBalance="
				+ installmentBalance + ", installmentBalanceStr=" + installmentBalanceStr + ", paymentMethod="
				+ paymentMethod + ", supplierName=" + supplierName + "]";
	}

}
