
package com.keepcount.service.purchases ;

import java.math.BigDecimal ;

import org.springframework.stereotype.Service ;

import com.keepcount.dao.dbutils.NumberFormatting ;

@Service
public class PurchasesTwoNumberVerificationService {

	public boolean verifyNumberFormatOfTheQuantityOfTheItem( String quantityStr , String languageFormat ) {
		return NumberFormatting.compareUserNumberToFormattedOne( quantityStr , languageFormat ) ;
	}

	private BigDecimal parseEnglishFormattedNumber( String userFormattedEnglishNumber ) {
		return NumberFormatting.parseEnglishFormattedNumber( userFormattedEnglishNumber ) ;
	}

	private BigDecimal parseGermanFormattedNumber( String userFormattedGermanNumber ) {
		return NumberFormatting.parseGermanFormattedNumber( userFormattedGermanNumber ) ;
	}

	private BigDecimal parseFrenchFormattedNumber( String userFormattedFrenchNumber , boolean resultFromCompariosnOfFrenchFormatAndUserFormat ) {
		return NumberFormatting.parseFrenchFormattedNumber( userFormattedFrenchNumber , resultFromCompariosnOfFrenchFormatAndUserFormat ) ;
	}

	public BigDecimal parseTheUserFormattedNumber( String userFormattedNumber , String languageFormat , boolean result ) {

		BigDecimal parsedNumber = BigDecimal.ZERO ;

		if ( languageFormat.equalsIgnoreCase( "english" ) ) {

			if ( result == true ) {
				parsedNumber = this.parseEnglishFormattedNumber( userFormattedNumber ) ;
			} else {
				parsedNumber = BigDecimal.ZERO ;
			}

		}

		else if ( languageFormat.equalsIgnoreCase( "german" ) ) {

			if ( result == true ) {
				parsedNumber = this.parseGermanFormattedNumber( userFormattedNumber ) ;
			} else {
				parsedNumber = BigDecimal.ZERO ;
			}

		}

		else if ( languageFormat.equalsIgnoreCase( "french" ) ) {

			if ( result == true ) {
				parsedNumber = this.parseFrenchFormattedNumber( userFormattedNumber , result ) ;
			} else {
				parsedNumber = BigDecimal.ZERO ;
			}

		}

		return parsedNumber ;
	}

}
