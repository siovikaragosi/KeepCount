
package com.keepcount.controller.login;

import java.math.BigDecimal;

public class MosqwyHandShakeAttendance {

	public static void main ( String [ ] args ) {

		System.out.println( findHandshakesWhenGivenNumberOfPeople( new BigDecimal( 0 ) ) );

	}

	/* n=numberOfPeople, */

	private static BigDecimal findHandshakesWhenGivenNumberOfPeople ( BigDecimal numberOfHandShakes ) {

		BigDecimal y8 = numberOfHandShakes.multiply( new BigDecimal( 8 ) );
		BigDecimal y8PlusOne = y8.add( BigDecimal.ONE );
		double squareRoot = Math.sqrt( y8PlusOne.doubleValue() );

		BigDecimal onePlusSquareRootOverTwo = ( BigDecimal.ONE.add( new BigDecimal( squareRoot ) ) ).divide( new BigDecimal( 2 ) );

		BigDecimal nOfpeople = onePlusSquareRootOverTwo;

		return nOfpeople;
	}

}
