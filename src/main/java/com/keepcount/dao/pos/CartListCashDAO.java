package com.keepcount.dao.pos;

import java.math.BigDecimal;
import java.util.Map;

import com.keepcount.model.pos.CartListCash;

public class CartListCashDAO {

	public static CartListCash addAnItemToItsOwnRowForFirstTime(CartListCash cartListCash, BigDecimal itemId, BigDecimal quantity, BigDecimal price) {

		CartListCash cash = new CartListCash();
		BigDecimal cost = quantity.multiply(price);

		cash.setItemId(itemId);
		cash.setItemQuantity(quantity);
		cash.setUnitCost(price);
		cash.setCostOfItem(cost);

		return cash;
	}

	public static CartListCash addingAnItemToCart(CartListCash cartListCash) {

		BigDecimal itemId = cartListCash.getItemId();
		String item = cartListCash.getItem();

		BigDecimal qtyToBeAdded = cartListCash.getItemQuantity();
		BigDecimal unitCost = cartListCash.getUnitCost();
		BigDecimal costOfItem = cartListCash.getCostOfItem();

		CartListCash cash = new CartListCash();

		// if the list contains the item being added
		if (populatePOSCashToBeSoldOff() != null) {
			if (populatePOSCashToBeSoldOff().keySet().contains(itemId)) {
				cash = populatePOSCashToBeSoldOff().get(itemId);
				cash.setItem(item);
				cash.getItemQuantity().add(qtyToBeAdded);
				cash.setUnitCost(unitCost);
				cash.getCostOfItem().add(costOfItem);
			}
			else {
				cash = cartListCash;
				populatePOSCashToBeSoldOff().put(itemId, cash);
			}
		} // if the list contains the cart list being added END
		else {
			populatePOSCashToBeSoldOff().put(itemId, cash);
		}

		return cash;
	}

	public static Map<BigDecimal, CartListCash> populatePOSCashToBeSoldOff() {

		return null;
	}

}
