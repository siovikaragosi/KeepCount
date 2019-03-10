package com.keepcount.model.pricing;

import java.math.BigDecimal;

public class Pricing {

	private BigDecimal id;
	private String dateServer;
	private String dateClient;
	private BigDecimal price;
	private String priceStr;
	private BigDecimal itemId;
	private String itemSubCategory;

	public Pricing() {

	}

	public Pricing(String priceStr) {
		this.priceStr = priceStr;
	}

	public Pricing(BigDecimal price) {
		this.price = price;
	}

	public Pricing(BigDecimal price, String priceStr, BigDecimal itemId, String itemSubCategory) {
		super();
		this.price = price;
		this.priceStr = priceStr;
		this.itemId = itemId;
		this.itemSubCategory = itemSubCategory;
	}

	public Pricing(BigDecimal id, String dateServer, String dateClient, BigDecimal price, String priceStr, BigDecimal itemId, String itemSubCategory) {
		super();
		this.id = id;
		this.dateServer = dateServer;
		this.dateClient = dateClient;
		this.price = price;
		this.priceStr = priceStr;
		this.itemId = itemId;
		this.itemSubCategory = itemSubCategory;
	}

	public Pricing(String dateClient, BigDecimal price, String priceStr, BigDecimal itemId, String itemSubCategory) {
		super();
		this.dateClient = dateClient;
		this.price = price;
		this.priceStr = priceStr;
		this.itemId = itemId;
		this.itemSubCategory = itemSubCategory;
	}

	public Pricing(BigDecimal id, String dateServer, String dateClient, BigDecimal price, BigDecimal itemId, String itemSubCategory) {
		super();
		this.id = id;
		this.dateServer = dateServer;
		this.dateClient = dateClient;
		this.price = price;
		this.itemId = itemId;
		this.itemSubCategory = itemSubCategory;
	}

	public String getPriceStr() {
		return priceStr;
	}

	public void setPriceStr(String priceStr) {
		this.priceStr = priceStr;
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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getItemId() {
		return itemId;
	}

	public void setItemId(BigDecimal itemId) {
		this.itemId = itemId;
	}

	public String getItemSubCategory() {
		return itemSubCategory;
	}

	public void setItemSubCategory(String itemSubCategory) {
		this.itemSubCategory = itemSubCategory;
	}

	@Override
	public String toString() {
		return "Pricing [id=" + id + ", dateServer=" + dateServer + ", dateClient=" + dateClient + ", price=" + price + ", itemId=" + itemId + ", itemSubCategory=" + itemSubCategory + "]";
	}

}
