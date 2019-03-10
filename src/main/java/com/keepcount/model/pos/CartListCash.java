package com.keepcount.model.pos;

import java.math.BigDecimal;

public class CartListCash {

	private BigDecimal id;
	private String item;
	private BigDecimal itemId;
	private BigDecimal itemQuantity;
	private String itemQuantityStr;
	private BigDecimal unitCost;
	private String unitCostStr;
	private BigDecimal costOfItem;
	private String costOfItemStr;
	private BigDecimal totalCost;
	private String totalCostStr;
	private BigDecimal amountPaidIn;
	private String amountPaidInStr;
	private BigDecimal change;
	private String changeStr;

	public CartListCash() {

		// TODO Auto-generated constructor stub
	}

	public CartListCash(BigDecimal id, String item, BigDecimal itemId, BigDecimal itemQuantity, String itemQuantityStr, BigDecimal unitCost, String unitCostStr, BigDecimal costOfItem, String costOfItemStr,
											BigDecimal totalCost, String totalCostStr, BigDecimal amountPaidIn, String amountPaidInStr, BigDecimal change, String changeStr) {

		super();
		this.id = id;
		this.item = item;
		this.itemId = itemId;
		this.itemQuantity = itemQuantity;
		this.itemQuantityStr = itemQuantityStr;
		this.unitCost = unitCost;
		this.unitCostStr = unitCostStr;
		this.costOfItem = costOfItem;
		this.costOfItemStr = costOfItemStr;
		this.totalCost = totalCost;
		this.totalCostStr = totalCostStr;
		this.amountPaidIn = amountPaidIn;
		this.amountPaidInStr = amountPaidInStr;
		this.change = change;
		this.changeStr = changeStr;
	}

	public CartListCash(String item, BigDecimal itemId, BigDecimal itemQuantity, String itemQuantityStr, BigDecimal unitCost, String unitCostStr, BigDecimal costOfItem, String costOfItemStr,
											BigDecimal totalCost, String totalCostStr, BigDecimal amountPaidIn, String amountPaidInStr, BigDecimal change, String changeStr) {

		super();
		this.item = item;
		this.itemId = itemId;
		this.itemQuantity = itemQuantity;
		this.itemQuantityStr = itemQuantityStr;
		this.unitCost = unitCost;
		this.unitCostStr = unitCostStr;
		this.costOfItem = costOfItem;
		this.costOfItemStr = costOfItemStr;
		this.totalCost = totalCost;
		this.totalCostStr = totalCostStr;
		this.amountPaidIn = amountPaidIn;
		this.amountPaidInStr = amountPaidInStr;
		this.change = change;
		this.changeStr = changeStr;
	}

	public CartListCash(String item, BigDecimal itemId, BigDecimal itemQuantity, BigDecimal unitCost, BigDecimal costOfItem, BigDecimal totalCost, BigDecimal amountPaidIn, BigDecimal change) {

		super();
		this.item = item;
		this.itemId = itemId;
		this.itemQuantity = itemQuantity;
		this.unitCost = unitCost;
		this.costOfItem = costOfItem;
		this.totalCost = totalCost;
		this.amountPaidIn = amountPaidIn;
		this.change = change;
	}

	public BigDecimal getId() {

		return id;
	}

	public void setId(BigDecimal id) {

		this.id = id;
	}

	public String getItem() {

		return item;
	}

	public void setItem(String item) {

		this.item = item;
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

	public BigDecimal getUnitCost() {

		return unitCost;
	}

	public void setUnitCost(BigDecimal unitCost) {

		this.unitCost = unitCost;
	}

	public String getUnitCostStr() {

		return unitCostStr;
	}

	public void setUnitCostStr(String unitCostStr) {

		this.unitCostStr = unitCostStr;
	}

	public BigDecimal getCostOfItem() {

		return costOfItem;
	}

	public void setCostOfItem(BigDecimal costOfItem) {

		this.costOfItem = costOfItem;
	}

	public String getCostOfItemStr() {

		return costOfItemStr;
	}

	public void setCostOfItemStr(String costOfItemStr) {

		this.costOfItemStr = costOfItemStr;
	}

	public BigDecimal getTotalCost() {

		return totalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {

		this.totalCost = totalCost;
	}

	public String getTotalCostStr() {

		return totalCostStr;
	}

	public void setTotalCostStr(String totalCostStr) {

		this.totalCostStr = totalCostStr;
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

	public BigDecimal getChange() {

		return change;
	}

	public void setChange(BigDecimal change) {

		this.change = change;
	}

	public String getChangeStr() {

		return changeStr;
	}

	public void setChangeStr(String changeStr) {

		this.changeStr = changeStr;
	}

	@Override
	public String toString() {

		return "CartListCash [id=" + id + ", item=" + item + ", itemId=" + itemId + ", itemQuantity=" + itemQuantity + ", itemQuantityStr=" + itemQuantityStr + ", unitCost=" + unitCost + ", unitCostStr="
												+ unitCostStr + ", costOfItem=" + costOfItem + ", costOfItemStr=" + costOfItemStr + ", totalCost=" + totalCost + ", totalCostStr=" + totalCostStr
												+ ", amountPaidIn=" + amountPaidIn + ", amountPaidInStr=" + amountPaidInStr + ", change=" + change + ", changeStr=" + changeStr + "]";
	}

}
