
package com.keepcount.dao.dbutils ;

import java.math.BigDecimal ;
import java.text.NumberFormat ;
import java.text.ParseException ;
import java.util.ArrayList ;
import java.util.List ;
import java.util.Locale ;

public class NumberFormatting {

	private static BigDecimal parsedValueIntoBigDecimal ;

	public static BigDecimal getParsedValueIntoBigDecimal() {
		return parsedValueIntoBigDecimal ;
	}

	public static String formattedBigDecimalIntoString( BigDecimal bigDecimalNumber , String languageOfNumberFormat ) {
		NumberFormat formatIntoEnglish = NumberFormat.getNumberInstance( Locale.ENGLISH ) ;
		NumberFormat formatIntoGerman = NumberFormat.getNumberInstance( Locale.GERMAN ) ;
		NumberFormat formatIntoFrench = NumberFormat.getNumberInstance( Locale.FRENCH ) ;

		String formattedNumber = null ;

		if ( languageOfNumberFormat.equalsIgnoreCase( "english" ) ) {
			formattedNumber = formatIntoEnglish.format( bigDecimalNumber ) ;
		} else if ( languageOfNumberFormat.equalsIgnoreCase( "german" ) ) {
			formattedNumber = formatIntoGerman.format( bigDecimalNumber ) ;
		} else if ( languageOfNumberFormat.equalsIgnoreCase( "french" ) ) {
			formattedNumber = formatIntoFrench.format( bigDecimalNumber ) ;
		}

		return formattedNumber ;
	}

	public static boolean compareUserNumberToFormattedOne( String userNumber , String languageOfFormatting ) {

		boolean tested = false ;

		try {

			if ( languageOfFormatting.equalsIgnoreCase( "english" ) ) {

				if ( userNumber.contains( "," ) ) {
					tested = comparisonWithEnglishFormat( userNumber ) ;

				} else {
					tested = testingForTheUnPunctuatedNumber( userNumber , "english" ) ;
				}

			}

			else if ( languageOfFormatting.equalsIgnoreCase( "german" ) ) {

				if ( userNumber.contains( "." ) ) {
					tested = comparisonWithGermanFormat( userNumber ) ;
				} else {
					tested = testingForTheUnPunctuatedNumber( userNumber , "german" ) ;
				}

			}

			else if ( languageOfFormatting.equalsIgnoreCase( "french" ) ) {

				if ( userNumber.contains( " " ) ) {
					tested = comparisonWithFrenchFormat( userNumber ) ;
				} else {
					tested = testingForTheUnPunctuatedNumber( userNumber , "french" ) ;
				}

			}

		} catch ( Exception e ) {
			e.printStackTrace() ;
		}

		return tested ;
	}

	/*
	 * Comparing the number formatting using English language format
	 */
	private static boolean comparisonWithEnglishFormat( String userFormattedNumber ) {

		NumberFormat nfEnglish = NumberFormat.getInstance( Locale.ENGLISH ) ;
		Number unPunctuatedNumberFromTheUser = null ;

		String formatted = null ;

		try {
			unPunctuatedNumberFromTheUser = nfEnglish.parse( userFormattedNumber ) ;

			/*
			 * Formatting the unpunctuated number for comparison
			 */

			formatted = nfEnglish.format( unPunctuatedNumberFromTheUser ) ;

		} catch ( Exception e ) {
			e.printStackTrace() ;
		}

		/*
		 * Comparing the two formatted numbers
		 */

		if ( userFormattedNumber.equals( formatted ) ) {
			return true ;
		} else {
			return false ;
		}

	}

	/* Parse English formatted number entered by the user */
	public static BigDecimal parseEnglishFormattedNumber( String englishFormattedNumber ) {
		NumberFormat nf = NumberFormat.getInstance( Locale.ENGLISH ) ;

		Number number = null ;
		try {
			number = nf.parse( englishFormattedNumber ) ;
		} catch ( ParseException e ) {
			e.printStackTrace() ;
		}

		return BigDecimal.valueOf( number.doubleValue() ) ;

	}

	/*
	 * Comparing the number formatting using German language format
	 */

	private static boolean comparisonWithGermanFormat( String userFormattedNumber ) {

		NumberFormat nfGerman = NumberFormat.getInstance( Locale.GERMAN ) ;
		Number unPunctuatedNumberFromTheUser = null ;

		String formatted = null ;

		try {
			unPunctuatedNumberFromTheUser = nfGerman.parse( userFormattedNumber ) ;

			/*
			 * Formatting the unpunctuated number for comparison
			 */

			formatted = nfGerman.format( unPunctuatedNumberFromTheUser ) ;

		} catch ( Exception e ) {
			e.printStackTrace() ;
		}

		/*
		 * Comparing the two formatted numbers
		 */

		if ( userFormattedNumber.equals( formatted ) ) {
			return true ;
		} else {
			return false ;
		}

	}

	/* Parse German formatted number entered by the user */
	public static BigDecimal parseGermanFormattedNumber( String germanFormattedNumber ) {
		NumberFormat nf = NumberFormat.getInstance( Locale.GERMAN ) ;

		Number number = null ;
		try {
			number = nf.parse( germanFormattedNumber ) ;
		} catch ( ParseException e ) {
			e.printStackTrace() ;
		}

		return BigDecimal.valueOf( number.doubleValue() ) ;

	}

	/*
	 * Comparing the number formatting using French language format
	 * 
	 * Since the French parser fails to read the spaces and instead discards the numbers after the
	 * thousand separator, the spaces will be replace with commas. The English formatter will the be
	 * used to format this instead but the return will consider it French as long as the final outcome
	 * is correct
	 */

	private static boolean comparisonWithFrenchFormat( String userFormattedNumber ) {

		// String userNumberWithoutAnyPuctuationsStr = userFormattedNumber ;

		// Number numberWithoutAnyPuctuationsStr = null ;

		System.out.println( "fr number = " + userFormattedNumber ) ;

		NumberFormat nfEnglishHelper = NumberFormat.getInstance( Locale.ENGLISH ) ;
		Number unPunctuatedNumberFromTheUser = null ;

		/*
		 * getting an English format of the passed French parameter
		 */
		String userFormattedNumberEnglishHelper = null ;

		if ( userFormattedNumber.contains( " " ) ) {
			userFormattedNumberEnglishHelper = userFormattedNumber.replace( " " , "comma" ) ;
			// System.out.println( "at first replcement: " + userFormattedNumberEnglishHelper ) ;
		} else {
			userFormattedNumberEnglishHelper = userFormattedNumber ;
		}

		if ( userFormattedNumberEnglishHelper.contains( "," ) ) {
			String engForm = userFormattedNumberEnglishHelper ;
			userFormattedNumberEnglishHelper = engForm.replace( "," , "." ) ;
			// System.out.println( "at second replcement: " + userFormattedNumberEnglishHelper ) ;
		}

		if ( userFormattedNumberEnglishHelper.contains( "comma" ) ) {
			String engForm = userFormattedNumberEnglishHelper ;
			userFormattedNumberEnglishHelper = engForm.replace( "comma" , "," ) ;
			// System.out.println( "at third replcement: " + userFormattedNumberEnglishHelper ) ;
		}

		String formatted = null ;
		String englishToFrench = null ;
		String finalToFrench = null ;
		// System.out.println() ;
		// System.out.println( "just before: " + userFormattedNumberEnglishHelper ) ;

		try {
			unPunctuatedNumberFromTheUser = nfEnglishHelper.parse( userFormattedNumberEnglishHelper ) ;

			/*
			 * Formatting the unpunctuated number for comparison
			 */
			formatted = nfEnglishHelper.format( unPunctuatedNumberFromTheUser ) ;

			/*
			 * Comparing the two formatted numbers
			 */

			// System.out.println( "formatted: " + formatted ) ;

			/*************************************************************************************/
			/* Changing the English formatted number to French and comparing with the user input */

			if ( formatted.contains( "," ) ) {
				englishToFrench = formatted.replace( "," , " " ) ;
				// System.out.println() ;
				// System.out.println( ", replaced: " + englishToFrench ) ;
			} else {
				englishToFrench = formatted ;
			}

			String removePoint = englishToFrench ;
			// System.out.println() ;
			// System.out.println( "rem: just: " + removePoint ) ;
			// System.out.println() ;
			if ( removePoint.contains( "." ) ) {
				// System.out.println() ;
				// System.out.println( "rem: " + removePoint ) ;

				String engToFre = removePoint.replace( "." , "," ) ;
				finalToFrench = engToFre ;
				// System.out.println() ;
				// System.out.println( ". replaced: " + finalToFrench ) ;
			} else {
				finalToFrench = removePoint ;
			}
			System.out.println() ;
			System.out.println( "fff: " + finalToFrench ) ;

			/* This comes in in case there is totally no punctuation provided by the use in the number */
			// numberWithoutAnyPuctuationsStr = NumberFormat.getNumberInstance().parse(
			// userNumberWithoutAnyPuctuationsStr ) ;

		} catch ( Exception e ) {
			e.printStackTrace() ;
		}

		if ( userFormattedNumber.equals( finalToFrench ) ) {
			return true ;
		} else {
			return false ;
		}

	}

	public static BigDecimal parseFrenchFormattedNumber( String userFormattedNumber , boolean resultFromCompariosnOfFrenchFormatAndUserFormat ) {

		System.out.println( "fr number = " + userFormattedNumber ) ;

		NumberFormat nfEnglishHelper = NumberFormat.getInstance( Locale.ENGLISH ) ;
		Number unPunctuatedNumberFromTheUser = null ;

		/*
		 * getting an English format of the passed French parameter
		 */
		String userFormattedNumberEnglishHelper = null ;

		if ( userFormattedNumber.contains( " " ) ) {
			userFormattedNumberEnglishHelper = userFormattedNumber.replace( " " , "comma" ) ;
		} else {
			userFormattedNumberEnglishHelper = userFormattedNumber ;
		}

		if ( userFormattedNumberEnglishHelper.contains( "," ) ) {
			String engForm = userFormattedNumberEnglishHelper ;
			userFormattedNumberEnglishHelper = engForm.replace( "," , "." ) ;
		}

		if ( userFormattedNumberEnglishHelper.contains( "comma" ) ) {
			String engForm = userFormattedNumberEnglishHelper ;
			userFormattedNumberEnglishHelper = engForm.replace( "comma" , "," ) ;
		}

		String formatted = null ;
		String englishToFrench = null ;
		String finalToFrench = null ;

		try {

			unPunctuatedNumberFromTheUser = nfEnglishHelper.parse( userFormattedNumberEnglishHelper ) ;

			/*
			 * Formatting the unpunctuated number for comparison
			 */
			formatted = nfEnglishHelper.format( unPunctuatedNumberFromTheUser ) ;

			/*
			 * Comparing the two formatted numbers
			 */

			/*************************************************************************************/
			/* Changing the English formatted number to French and comparing with the user input */

			if ( formatted.contains( "," ) ) {
				englishToFrench = formatted.replace( "," , " " ) ;
			} else {
				englishToFrench = formatted ;
			}

			String removePoint = englishToFrench ;
			if ( removePoint.contains( "." ) ) {

				String engToFre = removePoint.replace( "." , "," ) ;
				finalToFrench = engToFre ;
			} else {
				finalToFrench = removePoint ;
			}
			System.out.println() ;
			System.out.println( "fff: " + finalToFrench ) ;

		} catch ( Exception e ) {
			e.printStackTrace() ;
		}

		if ( resultFromCompariosnOfFrenchFormatAndUserFormat == true ) {
			System.out.println( "resultFromCompariosnOfFrenchFormatAndUserFormat: " + resultFromCompariosnOfFrenchFormatAndUserFormat ) ;
			Number number = null ;
			try {
				number = nfEnglishHelper.parse( formatted ) ;
			} catch ( ParseException e ) {
				// TODO Auto-generated catch block
				e.printStackTrace() ;
			}

			return BigDecimal.valueOf( number.doubleValue() ) ;

		} else {
			return BigDecimal.ZERO ;
		}

	}

	private static boolean testingForTheUnPunctuatedNumber( String unPunctuatedUserNumber , String languageFormat ) {

		List < Character > listOfCharactersInTheUnPunctuatedNumber = new ArrayList <>() ;

		try {
			for ( int i = 0 ; i < unPunctuatedUserNumber.length() ; i++ ) {
				char c = unPunctuatedUserNumber.charAt( i ) ;
				listOfCharactersInTheUnPunctuatedNumber.add( Character.valueOf( c ) ) ;
			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		}

		boolean tested = false ;
		// System.out.println( "list: " + listOfCharactersInTheUnPunctuatedNumber ) ;
		for ( int i = 0 ; i < listOfCharactersInTheUnPunctuatedNumber.size() ; i++ ) {
			char c = listOfCharactersInTheUnPunctuatedNumber.get( i ) ;
			if ( languageFormat.equalsIgnoreCase( "english" ) ) {
				System.out.println( "Eng" ) ;
				if ( !Character.isDigit( c ) && !String.valueOf( c ).equals( "." ) ) {
					System.out.println( "the char: " + c ) ;
					tested = false ;
					break ;
				} else {
					System.out.println( "the char: " + c ) ;
					tested = true ;
					continue ;
				}
			}

			else if ( languageFormat.equalsIgnoreCase( "german" ) ) {
				System.out.println( "Ger" ) ;
				if ( !Character.isDigit( c ) && !String.valueOf( c ).equals( "," ) ) {
					System.out.println( "the char: " + c ) ;
					tested = false ;
					break ;
				} else {
					System.out.println( "the char: " + c ) ;
					tested = true ;
					continue ;
				}
			}

			else if ( languageFormat.equalsIgnoreCase( "french" ) ) {
				System.out.println( "Fr" ) ;
				if ( !Character.isDigit( c ) && !String.valueOf( c ).equals( "," ) ) {
					System.out.println( "the char: " + c ) ;
					tested = false ;
					break ;
				} else {
					System.out.println( "the char: " + c ) ;
					tested = true ;
					continue ;
				}
			}
		}

		return tested ;

	}

	/*
	 * 
	 * Formatting a number to a particular language format
	 * 
	 */

	public static String formatNumberToAGivenLanguageStyle( BigDecimal numberToBeFormatted , String languageOfFormat ) {

		String numberFinallyFormatted = null ;

		if ( languageOfFormat.equalsIgnoreCase( "english" ) ) {
			NumberFormat nf = NumberFormat.getInstance( Locale.ENGLISH ) ;
			numberFinallyFormatted = nf.format( numberToBeFormatted ) ;
		}

		else if ( languageOfFormat.equalsIgnoreCase( "german" ) ) {
			NumberFormat nf = NumberFormat.getInstance( Locale.GERMAN ) ;
			numberFinallyFormatted = nf.format( numberToBeFormatted ) ;
		}

		else if ( languageOfFormat.equalsIgnoreCase( "french" ) ) {
			NumberFormat nf = NumberFormat.getInstance( Locale.FRENCH ) ;
			numberFinallyFormatted = nf.format( numberToBeFormatted ) ;
		}

		return numberFinallyFormatted ;
	}

	public static void main( String [ ] args ) throws ParseException {

		System.out.println( compareUserNumberToFormattedOne( "4" , "french" ) ) ;

		// System.out.println( testingForTheUnPunctuatedNumber( "4" , "french" ) ) ;

		// System.out.println( formatNumberToAGivenLanguageStyle( "5000" , "english" ) ) ;

		NumberFormat nf = NumberFormat.getInstance( Locale.ENGLISH ) ;
		String n = nf.format( BigDecimal.valueOf( 5000 ) ) ;
		// System.out.println( n ) ;

		//
		//
		// System.out.println( "Eng: " + comparisonWithEnglishFormat( "10" ) ) ;
		//
		// System.out.println( "Ger: " + comparisonWithGermanFormat( "10.000.000" ) ) ;
		//
		// System.out.println( "Fre: " + comparisonWithFrenchFormat( "100 010 041,45" ) ) ;
		//
		// String numberStr = "50 001,12" ;
		// // System.out.println( testingForTheUnPunctuatedNumber( "5000.45" , "english" ) ) ;
		//
		// System.out.println( "final: " + compareUserNumberToFormattedOne( numberStr , "french" ) ) ;
		//
		// NumberFormat nf = NumberFormat.getInstance( Locale.FRENCH ) ;
		//
		// Number n = nf.parse( numberStr ) ;
		// System.out.println( "n: " + n ) ;
		//
		// boolean test = comparisonWithFrenchFormat( numberStr ) ;
		//
		// // System.out.println( "parse french: " + parseFrenchFormattedNumber( numberStr , test ) ) ;

	}

}
