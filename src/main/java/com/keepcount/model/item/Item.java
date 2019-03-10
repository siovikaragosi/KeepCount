package com.keepcount.model.item;

import java.math.BigDecimal;

public class Item {

	private BigDecimal id;
	private String itemName;
	private String itemCategory;
	private String itemSubCategory;
	private String itemDescription;
	private String itemSamplePhotoOne;
	private String itemSamplePhotoTwo;
	private String itemSamplePhotoThree;

	public Item() {

	}

	public Item(BigDecimal id) {
		this.id = id;
	}

	public Item(String itemName, String itemCategory, String itemSubCategory, String itemDescription, String itemSamplePhotoOne, String itemSamplePhotoTwo, String itemSamplePhotoThree) {
		super();
		this.itemName = itemName;
		this.itemCategory = itemCategory;
		this.itemSubCategory = itemSubCategory;
		this.itemDescription = itemDescription;
		this.itemSamplePhotoOne = itemSamplePhotoOne;
		this.itemSamplePhotoTwo = itemSamplePhotoTwo;
		this.itemSamplePhotoThree = itemSamplePhotoThree;
	}

	public Item(BigDecimal id, String itemName, String itemCategory, String itemSubCategory, String itemDescription, String itemSamplePhotoOne, String itemSamplePhotoTwo, String itemSamplePhotoThree) {
		super();
		this.id = id;
		this.itemName = itemName;
		this.itemCategory = itemCategory;
		this.itemSubCategory = itemSubCategory;
		this.itemDescription = itemDescription;
		this.itemSamplePhotoOne = itemSamplePhotoOne;
		this.itemSamplePhotoTwo = itemSamplePhotoTwo;
		this.itemSamplePhotoThree = itemSamplePhotoThree;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	public String getItemSubCategory() {
		return itemSubCategory;
	}

	public void setItemSubCategory(String itemSubCategory) {
		this.itemSubCategory = itemSubCategory;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public String getItemSamplePhotoOne() {
		return itemSamplePhotoOne;
	}

	public void setItemSamplePhotoOne(String itemSamplePhotoOne) {
		this.itemSamplePhotoOne = itemSamplePhotoOne;
	}

	public String getItemSamplePhotoTwo() {
		return itemSamplePhotoTwo;
	}

	public void setItemSamplePhotoTwo(String itemSamplePhotoTwo) {
		this.itemSamplePhotoTwo = itemSamplePhotoTwo;
	}

	public String getItemSamplePhotoThree() {
		return itemSamplePhotoThree;
	}

	public void setItemSamplePhotoThree(String itemSamplePhotoThree) {
		this.itemSamplePhotoThree = itemSamplePhotoThree;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", itemName=" + itemName + ", itemCategory=" + itemCategory + ", itemSubCategory=" + itemSubCategory + ", itemDescription=" + itemDescription + ", itemSamplePhotoOne="
												+ itemSamplePhotoOne + ", itemSamplePhotoTwo=" + itemSamplePhotoTwo + ", itemSamplePhotoThree=" + itemSamplePhotoThree + "]";
	}

}
