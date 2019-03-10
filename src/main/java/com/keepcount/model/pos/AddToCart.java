package com.keepcount.model.pos;

import java.math.BigDecimal;

public class AddToCart {

	private BigDecimal id;
	private BigDecimal itemId;
	private String item;
	private String itemSubCategory;
	private BigDecimal itemQuantity;
	private String itemQuantityStr;
	private BigDecimal price;
	private String priceStr;
	private String unitOfMeasurement;

	public AddToCart() {

		// TODO Auto-generated constructor stub
	}

	public AddToCart(BigDecimal id, BigDecimal itemId, String item, String itemSubCategory, BigDecimal itemQuantity, String itemQuantityStr, BigDecimal price, String priceStr, String unitOfMeasurement) {

		super();
		this.id = id;
		this.itemId = itemId;
		this.item = item;
		this.itemSubCategory = itemSubCategory;
		this.itemQuantity = itemQuantity;
		this.itemQuantityStr = itemQuantityStr;
		this.price = price;
		this.priceStr = priceStr;
		this.unitOfMeasurement = unitOfMeasurement;
	}

	public AddToCart(BigDecimal itemId, String item, String itemSubCategory, BigDecimal itemQuantity, String itemQuantityStr, BigDecimal price, String priceStr, String unitOfMeasurement) {

		super();
		this.itemId = itemId;
		this.item = item;
		this.itemSubCategory = itemSubCategory;
		this.itemQuantity = itemQuantity;
		this.itemQuantityStr = itemQuantityStr;
		this.price = price;
		this.priceStr = priceStr;
		this.unitOfMeasurement = unitOfMeasurement;
	}

	public AddToCart(BigDecimal itemId, String item, String itemSubCategory, BigDecimal price, String unitOfMeasurement) {

		super();
		this.itemId = itemId;
		this.item = item;
		this.itemSubCategory = itemSubCategory;
		this.price = price;
		this.unitOfMeasurement = unitOfMeasurement;
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

	public BigDecimal getId() {

		return id;
	}

	public void setId(BigDecimal id) {

		this.id = id;
	}

	public BigDecimal getItemId() {

		return itemId;
	}

	public void setItemId(BigDecimal itemId) {

		this.itemId = itemId;
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

	public BigDecimal getPrice() {

		return price;
	}

	public void setPrice(BigDecimal price) {

		this.price = price;
	}

	public String getPriceStr() {

		return priceStr;
	}

	public void setPriceStr(String priceStr) {

		this.priceStr = priceStr;
	}

	public String getUnitOfMeasurement() {

		return unitOfMeasurement;
	}

	public void setUnitOfMeasurement(String unitOfMeasurement) {

		this.unitOfMeasurement = unitOfMeasurement;
	}

	@Override
	public String toString() {

		return "AddToCart [id=" + id + ", itemId=" + itemId + ", item=" + item + ", itemSubCategory=" + itemSubCategory + ", itemQuantity=" + itemQuantity + ", itemQuantityStr=" + itemQuantityStr
												+ ", price=" + price + ", priceStr=" + priceStr + ", unitOfMeasurement=" + unitOfMeasurement + "]";
	}

}
