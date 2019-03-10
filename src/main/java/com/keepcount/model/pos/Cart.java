package com.keepcount.model.pos;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.keepcount.model.item.Item;
import com.keepcount.model.item.UnitOfMeasurement;
import com.keepcount.model.pricing.Pricing;

public class Cart {

	private BigDecimal id;
	private BigDecimal itemId;
	private Item item;
	private Pricing pricing;
	private UnitOfMeasurement unitOfMeasurement;

	private List<BigDecimal> itemIDs;
	private List<Item> items;
	private List<Pricing> pricings;
	private List<UnitOfMeasurement> unitOfMeasurements;

	private Map<BigDecimal, Item> mapItems;
	private Map<BigDecimal, Pricing> mapPricings;
	private Map<BigDecimal, UnitOfMeasurement> mapUnitOfMeasurements;
	

	public Cart() {

	}

	public Cart(BigDecimal id, BigDecimal itemId, Item item, Pricing pricing, UnitOfMeasurement unitOfMeasurement) {
		super();
		this.id = id;
		this.itemId = itemId;
		this.item = item;
		this.pricing = pricing;
		this.unitOfMeasurement = unitOfMeasurement;
	}

	public Cart(BigDecimal itemId, Item item, Pricing pricing, UnitOfMeasurement unitOfMeasurement) {
		super();
		this.itemId = itemId;
		this.item = item;
		this.pricing = pricing;
		this.unitOfMeasurement = unitOfMeasurement;
	}

	public Cart(Item item, Pricing pricing, UnitOfMeasurement unitOfMeasurement) {
		super();
		this.item = item;
		this.pricing = pricing;
		this.unitOfMeasurement = unitOfMeasurement;
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

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Pricing getPricing() {
		return pricing;
	}

	public void setPricing(Pricing pricing) {
		this.pricing = pricing;
	}

	public UnitOfMeasurement getUnitOfMeasurement() {
		return unitOfMeasurement;
	}

	public void setUnitOfMeasurement(UnitOfMeasurement unitOfMeasurement) {
		this.unitOfMeasurement = unitOfMeasurement;
	}

	public List<BigDecimal> getItemIDs() {
		return itemIDs;
	}

	public void setItemIDs(List<BigDecimal> itemIDs) {
		this.itemIDs = itemIDs;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public List<Pricing> getPricings() {
		return pricings;
	}

	public void setPricings(List<Pricing> pricings) {
		this.pricings = pricings;
	}

	public List<UnitOfMeasurement> getUnitOfMeasurements() {
		return unitOfMeasurements;
	}

	public void setUnitOfMeasurements(List<UnitOfMeasurement> unitOfMeasurements) {
		this.unitOfMeasurements = unitOfMeasurements;
	}

	public Map<BigDecimal, Item> getMapItems() {
		return mapItems;
	}

	public void setMapItems(Map<BigDecimal, Item> mapItems) {
		this.mapItems = mapItems;
	}

	public Map<BigDecimal, Pricing> getMapPricings() {
		return mapPricings;
	}

	public void setMapPricings(Map<BigDecimal, Pricing> mapPricings) {
		this.mapPricings = mapPricings;
	}

	public Map<BigDecimal, UnitOfMeasurement> getMapUnitOfMeasurements() {
		return mapUnitOfMeasurements;
	}

	public void setMapUnitOfMeasurements(Map<BigDecimal, UnitOfMeasurement> mapUnitOfMeasurements) {
		this.mapUnitOfMeasurements = mapUnitOfMeasurements;
	}

	@Override
	public String toString() {
		return "Cart [id=" + id + ", itemId=" + itemId + ", item=" + item + ", pricing=" + pricing + ", unitOfMeasurement=" + unitOfMeasurement + "]";
	}

}
