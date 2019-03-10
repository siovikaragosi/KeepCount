
package com.keepcount.service.pos ;

import java.math.BigDecimal ;
import java.util.ArrayList ;
import java.util.LinkedHashMap ;
import java.util.List ;
import java.util.Map ;

import org.springframework.stereotype.Service ;

import com.keepcount.model.business.entities.EmailsOfABusinessWithAllAttributes ;
import com.keepcount.model.errorsuccess.ErrorSuccess ;
import com.keepcount.model.pos.CartListCash ;

@Service
public class CartListCashService {

	private Map < BigDecimal , CartListCash > map ;
	private CartListCash amountPaidIn ;

	public CartListCash getAmountPaidIn() {

		return amountPaidIn ;
	}

	public void setAmountPaidIn( CartListCash amountPaidIn ) {

		this.amountPaidIn = amountPaidIn ;
	}

	public Map < BigDecimal , CartListCash > getMap() {

		return map ;
	}

	public void setMap( Map < BigDecimal , CartListCash > map ) {

		this.map = map ;
	}

	public Map < BigDecimal , CartListCash > getTotal( Map < BigDecimal , CartListCash > map ) {

		BigDecimal subTotal = BigDecimal.ZERO ;
		for ( Map.Entry < BigDecimal , CartListCash > cart : map.entrySet() ) {
			BigDecimal eachCostOfItem = cart.getValue().getCostOfItem() ;
			subTotal = subTotal.add( eachCostOfItem ) ;
		}

		CartListCash cash = new CartListCash() ;
		cash.setTotalCost( subTotal ) ;

		Map < BigDecimal , CartListCash > total = new LinkedHashMap <>() ;

		total.put( BigDecimal.ZERO , cash ) ;

		return total ;
	}

	public Map < BigDecimal , CartListCash > getChange( BigDecimal total ) {

		Map < BigDecimal , CartListCash > change = new LinkedHashMap <>() ;

		CartListCash changeSingle = new CartListCash() ;

		BigDecimal changeObtained = this.getAmountPaidIn().getAmountPaidIn().subtract( total ) ;

		changeSingle.setChange( changeObtained ) ;

		change.put( BigDecimal.ZERO , changeSingle ) ;

		return change ;
	}

	public CartListCash addingAnItemToCart( CartListCash cartListCash ) {

		BigDecimal itemId = cartListCash.getItemId() ;
		String item = cartListCash.getItem() ;

		BigDecimal qtyToBeAdded = cartListCash.getItemQuantity() ;
		BigDecimal unitCost = cartListCash.getUnitCost() ;

		CartListCash cash = new CartListCash() ;

		// if the list contains the item being added
		Map < BigDecimal , CartListCash > mapped = this.getMap() ;

		if ( mapped != null ) {
			if ( mapped.keySet().contains( itemId ) ) {
				cash = mapped.get( itemId ) ;

				cash.setItemId( itemId ) ;
				cash.setItem( item ) ;

				BigDecimal newQty = cash.getItemQuantity().add( qtyToBeAdded ) ;
				cash.setItemQuantity( newQty ) ;
				cash.setItemQuantityStr( cash.getItemQuantityStr() ) ;

				cash.setUnitCost( unitCost ) ;
				cash.setUnitCostStr( cartListCash.getUnitCostStr() ) ;

				cash.setCostOfItem( cartListCash.getUnitCost().multiply( cash.getItemQuantity() ) ) ;
				cash.setCostOfItemStr( cash.getCostOfItemStr() ) ;

				getTotal( mapped ) ;

			} else {
				// cash = cartListCash;

				cash.setItemId( itemId ) ;
				cash.setItem( item ) ;

				cash.setItemQuantity( cartListCash.getItemQuantity() ) ;
				cash.setItemQuantityStr( cartListCash.getItemQuantityStr() ) ;

				cash.setUnitCost( cartListCash.getUnitCost() ) ;
				cash.setUnitCostStr( cartListCash.getUnitCostStr() ) ;

				cash.setCostOfItem( cartListCash.getUnitCost().multiply( cartListCash.getItemQuantity() ) ) ;
				cash.setCostOfItemStr( cartListCash.getCostOfItemStr() ) ;

				mapped.put( itemId , cash ) ;

				getTotal( mapped ) ;

			}
		} // if the list contains the cart list being added END

		else {

			cash.setItemId( itemId ) ;
			cash.setItem( item ) ;

			cash.setItemQuantity( cartListCash.getItemQuantity() ) ;
			cash.setItemQuantityStr( cartListCash.getItemQuantityStr() ) ;

			cash.setUnitCost( cartListCash.getUnitCost() ) ;
			cash.setUnitCostStr( cartListCash.getUnitCostStr() ) ;

			cash.setCostOfItem( cartListCash.getUnitCost().multiply( cartListCash.getItemQuantity() ) ) ;
			cash.setCostOfItemStr( cartListCash.getCostOfItemStr() ) ;

			Map < BigDecimal , CartListCash > map2 = new LinkedHashMap <>() ;

			map2.put( itemId , cash ) ;
			this.setMap( map2 ) ;

		}

		return cash ;
	}

	public CartListCash subtractingAnItemFromCart( CartListCash cartListCash ) {

		BigDecimal itemId = cartListCash.getItemId() ;
		// String item = cartListCash.getItem();
		BigDecimal unitCost = cartListCash.getUnitCost() ;
		// CartListCash cash = new CartListCash();

		// if the list contains the item being added
		Map < BigDecimal , CartListCash > mapped = this.getMap() ;
		CartListCash cash2 = new CartListCash() ;

		if ( cartListCash.getItemQuantity().doubleValue() >= 1 ) {
			cash2 = mapped.get( itemId ) ;
			BigDecimal newQty = cash2.getItemQuantity().subtract( BigDecimal.ONE ) ;
			cash2.setItemQuantity( newQty ) ;

			cash2.setUnitCost( unitCost ) ;
			cash2.setUnitCostStr( cartListCash.getUnitCostStr() ) ;

			cash2.setCostOfItem( cash2.getItemQuantity().multiply( unitCost ) ) ;

			BigDecimal newCostOfItem = unitCost.multiply( cash2.getItemQuantity() ) ;

			cash2.setCostOfItem( newCostOfItem ) ;
			cash2.setCostOfItemStr( cash2.getCostOfItemStr() ) ;

			mapped.replace( itemId , cash2 ) ;

		} else if ( cartListCash.getItemQuantity().doubleValue() <= 1 ) {
			mapped.remove( itemId ) ;
		}

		return cash2 ;
	}

	public CartListCash increaseAnItemInCart( CartListCash cartListCash ) {

		BigDecimal itemId = cartListCash.getItemId() ;
		// String item = cartListCash.getItem();
		BigDecimal unitCost = cartListCash.getUnitCost() ;

		// if the list contains the item being added
		Map < BigDecimal , CartListCash > mapped = this.getMap() ;
		CartListCash cash2 = new CartListCash() ;

		cash2 = mapped.get( itemId ) ;
		BigDecimal newQty = cash2.getItemQuantity().add( BigDecimal.ONE ) ;
		cash2.setItemQuantity( newQty ) ;

		cash2.setUnitCost( unitCost ) ;
		cash2.setUnitCostStr( cartListCash.getUnitCostStr() ) ;

		cash2.setCostOfItem( cash2.getItemQuantity().multiply( unitCost ) ) ;

		BigDecimal newCostOfItem = unitCost.multiply( cash2.getItemQuantity() ) ;

		cash2.setCostOfItem( newCostOfItem ) ;
		cash2.setCostOfItemStr( cash2.getCostOfItemStr() ) ;

		mapped.replace( itemId , cash2 ) ;

		return cash2 ;
	}

	public CartListCash removeAnItemFromCart( CartListCash cartListCash ) {

		BigDecimal itemId = cartListCash.getItemId() ;

		CartListCash cash2 = new CartListCash() ;

		cash2 = cartListCash ;

		Map < BigDecimal , CartListCash > mapped = this.getMap() ;

		mapped.remove( itemId ) ;

		return cash2 ;
	}

	public Map < BigDecimal , CartListCash > mapFromCartListToCheckOut() {

		return this.getMap() ;
	}

	// give it a key of -1
	public Map < BigDecimal , CartListCash > mapFromAmountPaidInToCheckOut() {

		Map < BigDecimal , CartListCash > map = new LinkedHashMap <>() ;
		map.put( new BigDecimal( -1 ) , this.getAmountPaidIn() ) ;
		return map ;
	}

	public Map < BigDecimal , CartListCash > mapFromTotalToCheckOut() {

		return this.getTotal( this.getMap() ) ;

	}

	// give it a key of -2
	public Map < BigDecimal , CartListCash > mapFromChangeCheckOut() {

		Map < BigDecimal , CartListCash > map = new LinkedHashMap <>() ;
		BigDecimal total = this.getTotal( this.getMap() ).get( BigDecimal.ZERO ).getTotalCost() ;
		BigDecimal amountPaidIn = this.mapFromAmountPaidInToCheckOut().get( new BigDecimal( -1 ) ).getAmountPaidIn() ;
		CartListCash cart = new CartListCash() ;
		BigDecimal change = amountPaidIn.subtract( total ) ;
		cart.setChange( change ) ;
		map.put( new BigDecimal( -2 ) , cart ) ;
		return map ;

	}

	public Map < BigDecimal , CartListCash > checkOut( Map < BigDecimal , CartListCash > mapFromCartList , Map < BigDecimal , CartListCash > mapFromAmountPaidIn ,
			Map < BigDecimal , CartListCash > mapFromTotal , Map < BigDecimal , CartListCash > mapFromChange ) {

		Map < BigDecimal , CartListCash > map = new LinkedHashMap <>() ;

		return map ;

	}

	public List < ErrorSuccess > verifyChange() {

		ErrorSuccess errorSuccess = new ErrorSuccess() ;

		BigDecimal change = this.mapFromChangeCheckOut().get( new BigDecimal( -2 ) ).getChange() ;
		
		if ( change.doubleValue() < 0 ) {
			errorSuccess.setMessage( "Amount paid is less, transaction will not complete successfully" ) ;
		} else {
			errorSuccess.setMessage( "successful" ) ;
		}
		List < ErrorSuccess > errorSuccesses = new ArrayList <>() ;
		errorSuccesses.add( errorSuccess ) ;

		return errorSuccesses ;

	}

	public Map < BigDecimal , CartListCash > populatePOSCashToBeSoldOff() {

		return getMap() ;

	}

	public Map < BigDecimal , EmailsOfABusinessWithAllAttributes > emailsOfABusinessWithAllAttributesBasingOnEmailOfTheCustomer( String email ) {
		return EmailsOfABusinessWithAllAttributesHibernation.allBusinessesAndRelatedIDsBasingOnEmailOfTheCustomer( email ) ;
	}

}
