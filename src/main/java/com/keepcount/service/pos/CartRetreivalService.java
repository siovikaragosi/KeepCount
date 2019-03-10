package com.keepcount.service.pos;

import java.util.List;

import org.springframework.stereotype.Service;

import com.keepcount.dao.pos.CartHibernationRetrieval;
import com.keepcount.model.pos.AddToCart;

@Service
public class CartRetreivalService {

	public List<AddToCart> getAddToCartList(String businessId) {

		return CartHibernationRetrieval.getAddToCartList(businessId);

	}

}
