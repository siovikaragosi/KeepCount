
package com.keepcount.service.purchases ;

import java.math.BigDecimal ;
import java.util.ArrayList ;
import java.util.List ;

import org.springframework.stereotype.Service ;

import com.keepcount.dao.dbutils.NumberFormatting ;
import com.keepcount.model.purchases.PurchasesTwo ;

@Service
public class PurchasesTwoCalculationsService {

	private BigDecimal quantityOfTheItemToBePurchased = BigDecimal.ZERO ;

	private BigDecimal unitCost = BigDecimal.ZERO ;

	private BigDecimal amountPaid = BigDecimal.ZERO ;

	public BigDecimal getAmountPaid() {
		return amountPaid ;
	}

	public void setAmountPaid( BigDecimal amountPaid ) {
		this.amountPaid = amountPaid ;
	}

	public BigDecimal getTotalToBePaidAuto() {
		return this.getUnitCost().multiply( this.getQuantityOfTheItemToBePurchased() ) ;
	}

	public List < PurchasesTwo > getBigDecimalTotalToBePaidInListFormatForJson( String languageFormat ) {

		List < PurchasesTwo > twos = new ArrayList <>() ;

		PurchasesTwo two = new PurchasesTwo() ;

		two.setTotalCostAuto( this.getTotalToBePaidAuto() ) ;

		String totalCostAutoStr = NumberFormatting.formatNumberToAGivenLanguageStyle( two.getTotalCostAuto() , languageFormat ) ;
		two.setTotalCostAutoStr( totalCostAutoStr ) ;

		twos.add( two ) ;

		return twos ;

	}

	public BigDecimal getQuantityOfTheItemToBePurchased() {
		return quantityOfTheItemToBePurchased ;
	}

	public void setQuantityOfTheItemToBePurchased( BigDecimal quantityOfTheItemToBePurchased ) {
		this.quantityOfTheItemToBePurchased = quantityOfTheItemToBePurchased ;
	}

	public BigDecimal getUnitCost() {
		return unitCost ;
	}

	public void setUnitCost( BigDecimal unitCost ) {
		this.unitCost = unitCost ;
	}

}
