
package com.keepcount.dao.pos ;

import java.math.BigDecimal ;
import java.util.ArrayList ;
import java.util.List ;
import java.util.Map ;
import java.util.Map.Entry ;

import com.keepcount.model.pos.AddToCart ;

public class CartHibernationRetrieval {

	private static List < AddToCart > addToCarts ;

	public static List < AddToCart > getAddToCartList( String businessId ) {

		if ( getAddToCarts() == null ) {

			Map < BigDecimal , AddToCart > toCart = CartDAORetreival.findAllItemsToFinallyBeAddedToCartListForChoosingByTheSellerOrBuyer( businessId ) ;

			List < AddToCart > cartList = new ArrayList <>() ;

			for ( Entry < BigDecimal , AddToCart > cart : toCart.entrySet() ) {
				AddToCart addToCart = new AddToCart() ;
				addToCart = cart.getValue() ;
				cartList.add( addToCart ) ;
				System.out.println( "names...: " + cart.getValue().getItem() ) ;
			}

			setAddToCarts( cartList ) ;

			System.out.println( "Direct add to cart " ) ;
			System.out.println( "...Dir: " + getAddToCarts() ) ;
			return getAddToCarts() ;
		} else {
			return getAddToCarts() ;
		}
		// System.out.println("Indirect add to cart ");
		// System.out.println("...Indir: " + getAddToCarts());
		// return getAddToCarts();

	}

	public static List < AddToCart > getAddToCarts() {
		return addToCarts ;
	}

	public static void setAddToCarts( List < AddToCart > addToCarts ) {
		CartHibernationRetrieval.addToCarts = addToCarts ;
	}

}
