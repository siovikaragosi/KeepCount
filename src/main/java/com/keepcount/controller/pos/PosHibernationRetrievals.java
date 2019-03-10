package com.keepcount.controller.pos;

import java.util.ArrayList;
import java.util.List;

import com.keepcount.model.pricing.Pricing;

public class PosHibernationRetrievals {

	private static List<Pricing> itemsForCart;

	public static List<Pricing> findAllItemsForCart_Direct(String businessId, String numberFormat) {
		List<Pricing> list = new ArrayList<>();
		list = PosDAORetrieveItemsForCart.findAllPricings(businessId, numberFormat);
		setItemsForCart(list);
		return list;
	}

	public static List<Pricing> findAllItemsForCart_Not_Direct() {
		return getItemsForCart();
	}

	public static List<Pricing> getItemsForCart() {
		return itemsForCart;
	}

	public static void setItemsForCart(List<Pricing> itemsForCart) {
		PosHibernationRetrievals.itemsForCart = itemsForCart;
	}

}
