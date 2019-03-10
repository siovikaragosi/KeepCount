
package com.keepcount.controller.pricing ;

import java.math.BigDecimal ;
import java.util.ArrayList ;
import java.util.Collections ;
import java.util.HashMap ;
import java.util.List ;
import java.util.Map ;
import java.util.Set ;

import javax.servlet.http.HttpServletRequest ;
import javax.servlet.http.HttpServletResponse ;

import org.springframework.beans.factory.annotation.Autowired ;
import org.springframework.stereotype.Controller ;
import org.springframework.ui.Model ;
import org.springframework.web.bind.annotation.PathVariable ;
import org.springframework.web.bind.annotation.RequestBody ;
import org.springframework.web.bind.annotation.RequestMapping ;
import org.springframework.web.bind.annotation.RequestMethod ;
import org.springframework.web.bind.annotation.RequestParam ;
import org.springframework.web.bind.annotation.ResponseBody ;

import com.google.gson.Gson ;
import com.keepcount.controller.business.choose.ChooseBusinessController ;
import com.keepcount.model.business.create.EmailsOfABusinessLogin ;
import com.keepcount.model.item.Item ;
import com.keepcount.model.pricing.Pricing ;
import com.keepcount.service.pricing.PricingService ;

@Controller
public class PricingController {

	private int changeMade = 0 ;
	private int priceChecked = 0 ;
	private String englishNumberOK ;
	private String gremanhNumberOK ;
	private String frenchNumberOK ;
	private String numberFormatOkGeneral ;

	private List < BigDecimal > sortedItemIDs ;
	private String busId ;

	/* If this value = 1, then a success message is shown to the client */
	private int insertionComplete = 0 ;

	private List < Item > itemsToFindID ;
	private static HashMap < BigDecimal , Item > idKeyItemValueMap ;

	@Autowired
	private PricingService pricingService ;

	/* This displays the web page */
	@RequestMapping( value = "/business/pricing/{businessName}/{emailOfUser}" )
	public String showPricingPage( Model model , @PathVariable( name = "businessName" ) String businessName , @PathVariable( name = "emailOfUser" ) String emailOfUser ) {

		EmailsOfABusinessLogin emailsOfABusiness = new EmailsOfABusinessLogin() ;
		emailOfUser = ChooseBusinessController.getEmailLoggedIn() ;
		businessName = ChooseBusinessController.getNameOfTheBusiness() ;
		model.addAttribute( "emailOfUser" , emailOfUser ) ;
		model.addAttribute( "businessName" , businessName ) ;
		model.addAttribute( "emailsOfABusinessForm" , emailsOfABusiness ) ;
		setChangeMade( 1 ) ;
		return "/keepCount/business/pricing/pricing" ;
	}

	@RequestMapping( value = "/api/business/pricing/{businessName}/{emailOfUser}/{id}/delete" , method = RequestMethod.DELETE )
	@ResponseBody
	public String deleteFromPrices( HttpServletRequest request , HttpServletResponse response , @PathVariable( name = "businessName" ) String businessName ,
			@PathVariable( name = "emailOfUser" ) String emailOfUser , @PathVariable( name = "id" ) BigDecimal id ) {

		// new ChooseBusinessController();
		emailOfUser = ChooseBusinessController.getEmailLoggedIn() ;
		// new ChooseBusinessController();
		businessName = ChooseBusinessController.getNameOfTheBusiness() ;
		// new ChooseBusinessController();
		String businessId = ChooseBusinessController.getIdOfBusiness().toString() ;

		System.out.println( "id: " + id ) ;

		int result = pricingService.deletePricing( businessId , id ) ;

		if ( result >= 1 ) {
			System.out.println( "deleted successfully" ) ;
			setChangeMade( 1 ) ;
		} else {
			System.out.println( "something went wrong" ) ;
		}
		return "/api/business/purchases/{businessName}/{emailOfUser}/{id}/delete" ;
	}

	/*
	 * This method creates a new price for an item specified by the client. The price is as well
	 * specified by the client
	 */
	@RequestMapping( value = "/api/business/pricing/{businessName}/{emailOfUser}/newPricing" , method = RequestMethod.POST )
	@ResponseBody
	public String createNewItemPricing( HttpServletRequest request , HttpServletResponse response , @PathVariable( name = "businessName" ) String businessName ,
			@PathVariable( name = "emailOfUser" ) String emailOfUser , @RequestBody Pricing pricing , @RequestParam( "numberFormat" ) String numberFormat ) {

		emailOfUser = ChooseBusinessController.getEmailLoggedIn() ;
		businessName = ChooseBusinessController.getNameOfTheBusiness() ;
		String businessId = ChooseBusinessController.getIdOfBusiness().toString() ;

		// String subAlone = getPricingItemSubCategory(pricing);
		String subAlone = removeSpaceFirstCharacterItemSubCategory( pricing.getItemSubCategory() ) ;
		pricing.setItemSubCategory( subAlone ) ;
		System.out.println( "alone: " + subAlone ) ;

		String priceStr = pricing.getPriceStr() ;

		String idStr = getItemIDBasingOnSubCategory( pricing.getItemSubCategory() , businessId ) ;
		BigDecimal id = new BigDecimal( idStr ) ;
		pricing.setItemId( id ) ;

		BigDecimal priceInBigDecimal = priceInBigDecimal( numberFormat , priceStr ) ;
		pricing.setPrice( priceInBigDecimal ) ;

		System.out.println( "pricing cont: " + pricing ) ;
		// int result = pricingService.newPricing(businessId, pricing);

		int result = pricingService.newPricing( businessId , pricing ) ;

		setInsertionComplete( result ) ;

		return "/api/business/pricing/{businessName}/{emailOfUser}" ;
	}

	@RequestMapping( value = "/api/business/pricing/{businessName}/{emailOfUser}/updatePricing" , method = RequestMethod.PUT )
	@ResponseBody
	public String updateItemPricing( HttpServletRequest request , HttpServletResponse response , @PathVariable( name = "businessName" ) String businessName ,
			@PathVariable( name = "emailOfUser" ) String emailOfUser , @RequestBody Pricing pricing , @RequestParam( "id" ) BigDecimal id ,
			@RequestParam( "numberFormat" ) String numberFormat ) {

		emailOfUser = ChooseBusinessController.getEmailLoggedIn() ;
		businessName = ChooseBusinessController.getNameOfTheBusiness() ;
		String businessId = ChooseBusinessController.getIdOfBusiness().toString() ;

		// String subAlone = getPricingItemSubCategory(pricing);
		String subAlone = removeSpaceFirstCharacterItemSubCategory( pricing.getItemSubCategory() ) ;
		pricing.setItemSubCategory( subAlone ) ;
		System.out.println( "alone: " + subAlone ) ;

		String priceStr = pricing.getPriceStr() ;

		String idStr = getItemIDBasingOnSubCategory( pricing.getItemSubCategory() , businessId ) ;
		BigDecimal iD = new BigDecimal( idStr ) ;
		pricing.setItemId( iD ) ;
		pricing.setId( id ) ;

		BigDecimal priceInBigDecimal = priceInBigDecimal( numberFormat , priceStr ) ;
		pricing.setPrice( priceInBigDecimal ) ;

		System.out.println( "pricing cont: " + pricing ) ;

		int result = pricingService.updatePricing( businessId , pricing , id ) ;
		setInsertionComplete( result ) ;

		return "/api/business/pricing/{businessName}/{emailOfUser}/updatePricing" ;
	}

	/*
	 * Message after trying to save Price. This sends a 1 or 0 to the client javascript i.e pricing.js,
	 * which sets the required message to be displayed basing on the returned binary value
	 */
	@RequestMapping( value = "/api/business/pricing/insertionComplete" , method = RequestMethod.GET , produces = "application/json" )
	@ResponseBody
	public String insertionResponse() {

		String message = String.valueOf( getInsertionComplete() ) ;
		return new Gson().toJson( pricingService.getSuccessMessage( message ) ) ;
	}

	/*
	 * private static String getPricingItemSubCategory(Pricing pricing) { String itemName =
	 * pricing.getItemSubCategory(); if (itemName.contains(" -- (")) { int placeOfFirstHyphen =
	 * pricing.getItemSubCategory().indexOf(" -- ("); String subCat =
	 * itemName.substring(placeOfFirstHyphen); String subCat2 = subCat.replace("(", "").replace("--",
	 * "").replace(")", "");
	 * 
	 * // // char firstChar = subCat2.charAt(0); // String subCat3 = null; // String subCat4 = null; //
	 * // String space = subCat2.substring(0, 1); // // String spaceFirst = null; // while
	 * (String.valueOf(firstChar).equals(" ")) { // // subCat3 =
	 * subCat2.replace(String.valueOf(firstChar), ""); // subCat4 = subCat3; // firstChar =
	 * subCat2.charAt(0); // // spaceFirst = subCat2.replace(space, ""); // // space =
	 * spaceFirst.substring(0, 1); // // subCategory = spaceFirst; //
	 * pricing.setItemSubCategory(subCat4); // } } return pricing.getItemSubCategory();
	 * 
	 * }
	 * 
	 */

	/*
	 * This method gets the text obtained from the chosen item-name item-sub-cat drop down. It then
	 * sieves out only the sub category of the item
	 */

	private static String removeSpaceFirstCharacterItemSubCategory( String subCat ) {

		char firstChar = subCat.charAt( 0 ) ;
		if ( !String.valueOf( firstChar ).equals( " " ) ) {

			int firstBracePos = subCat.indexOf( "-- (" ) ;
			int lastBracePos = subCat.indexOf( ")" ) ;

			String n = subCat.substring( firstBracePos , lastBracePos ) ;

			String nn = n.replace( "-- (" , "" ) ;

			return nn ;
		} else {

			int firstBracePos = subCat.indexOf( "-- (" ) ;
			int lastBracePos = subCat.indexOf( ")" ) ;

			String n = subCat.substring( firstBracePos , lastBracePos ) ;

			List < String > list = convertStringToListChararcters( n ) ;

			String firstCharacter = list.get( 0 ) ;

			for ( int i = 0 ; i < list.size() ; i++ ) {
				if ( firstCharacter.equals( " " ) ) {
					list.remove( firstCharacter ) ;
				}
			}

			List < String > list2 = new ArrayList <>() ;
			list2 = list ;

			String finalString = "" ;
			for ( int i = 0 ; i < list2.size() ; i++ ) {

				finalString = finalString.concat( list2.get( i ) ) ;

			}
			String nn = finalString.replace( "-- (" , "" ) ;
			return nn ;
		}

	}

	private static List < String > convertStringToListChararcters( String toListCharacters ) {

		List < String > list = new ArrayList <>() ;

		for ( int i = 0 ; i < toListCharacters.length() ; i++ ) {
			// char eachChar = toListCharacters
			char eachChar = toListCharacters.charAt( i ) ;
			list.add( String.valueOf( eachChar ) ) ;
		}

		return list ;
	}

	/*
	 * private static String getPurchasesItemSubCategory(Purchases purchases) { String itemName =
	 * purchases.getItem(); String subCategory = null; if (itemName.contains(" -- (")) { int
	 * placeOfFirstHyphen = purchases.getItem().indexOf(" -- ("); String subCat =
	 * itemName.substring(placeOfFirstHyphen); String subCat2 = subCat.replace("(", "").replace("--",
	 * "").replace(")", ""); String space = subCat2.substring(0, 1); String spaceFirst = null; while
	 * (space.equals(" ")) { spaceFirst = subCat2.replace(space, ""); space = spaceFirst.substring(0,
	 * 1); subCategory = spaceFirst; purchases.setItemSubCategory(subCategory); } } return
	 * purchases.getItemSubCategory(); }
	 * 
	 */
	/*
	 * This method checks whether the specified price number matches the chosen number format language.
	 * It calls a verification method from a different class PricingInsertionVerifiction. This method
	 * uses a Map to attach the specified language number format to the corresponding price number the
	 * client has entered. It returns a message which the client side Javascript then uses to display a
	 * message to the client (user). A message is shown to the client only if the price number is
	 * invalid.
	 */
	@RequestMapping( value = "/api/business/pricing/{businessName}/{emailOfUser}/checkPriceCorrectness" , method = RequestMethod.POST )
	@ResponseBody
	public String apiToCheckPriceCorrectness( HttpServletRequest request , HttpServletResponse response , @PathVariable( name = "businessName" ) String businessName ,
			@PathVariable( name = "emailOfUser" ) String emailOfUser , @RequestBody Pricing pricing , @RequestParam( "numberFormat" ) String numberFormat ) {

		String priceStr = pricing.getPriceStr() ;

		Map < String , String > map = PricingInsertionVerification.verifyPrice( numberFormat , priceStr ) ;

		System.out.println( map ) ;
		System.out.println( "values:" ) ;
		System.out.println( map.values() ) ;
		System.out.println( "keys:" ) ;
		System.out.println( map.keySet() ) ;

		Set < String > key = map.keySet() ;
		Object setOkOrNotObject = key.iterator().next() ;
		String setOkOrNot = setOkOrNotObject.toString() ;

		setNumberFormatOkGeneral( numberFormat ) ;

		if ( numberFormat.equalsIgnoreCase( "English" ) ) {
			if ( key.size() <= 1 ) {
				setEnglishNumberOK( setOkOrNot ) ;
			}
		} else if ( numberFormat.equalsIgnoreCase( "German" ) ) {
			if ( key.size() <= 1 ) {
				setGremanhNumberOK( setOkOrNot ) ;
			}
		} else if ( numberFormat.equalsIgnoreCase( "French" ) ) {
			if ( key.size() <= 1 ) {
				setFrenchNumberOK( setOkOrNot ) ;
			}
		}

		return "/api/business/pricing/{businessName}/{emailOfUser}/checkPriceCorrectness" ;
	}

	/*
	 * This method returns a BigDecimal value of the String price number obtained from the user. It also
	 * bases on the number format language to format the provided price String value and convert it into
	 * a BigDecimal value
	 */
	private BigDecimal priceInBigDecimal( String numberFormat , String priceStr ) {

		BigDecimal priceBig = BigDecimal.ZERO ;
		String priceFormattedFinal = null ;

		if ( numberFormat.equals( "English" ) ) {

			if ( priceStr.contains( "," ) ) {
				String priceFormatted = priceStr.replaceAll( "," , "" ) ;
				priceFormattedFinal = priceFormatted ;
				priceBig = new BigDecimal( priceFormattedFinal ) ;
			}

			else {
				priceBig = new BigDecimal( priceStr ) ;
			}
		}

		if ( numberFormat.equals( "German" ) ) {

			List < String > priceChars = new ArrayList <>() ;

			if ( priceStr.contains( "." ) && priceStr.contains( "," ) ) {

				for ( int i = 0 ; i < priceStr.length() ; i++ ) {
					char eachCharacter = priceStr.charAt( i ) ;
					String eachChar = String.valueOf( eachCharacter ) ;
					priceChars.add( eachChar ) ;
				}

				System.out.println( "Chars: " + priceChars ) ;

				String germanSeparator = "." ;
				String germanPoint = "," ;

				for ( int i = 0 ; i <= priceChars.size() ; i++ ) {
					if ( priceChars.contains( germanSeparator ) ) {
						priceChars.remove( germanSeparator ) ;
					}

				}

				if ( priceStr.contains( germanPoint ) ) {
					int pointPos = priceChars.indexOf( germanPoint ) ;
					priceChars.set( pointPos , "." ) ;
				}

				String v = priceChars.toString() ;
				String priceToFinallyChangeToBigDecimal = v.replaceAll( "," , "" ).replaceAll( " " , "" ) ;

				char charFirst = priceToFinallyChangeToBigDecimal.charAt( 0 ) ;

				String priceToFinallyChangeToBigDecimal2 = priceToFinallyChangeToBigDecimal.replace( String.valueOf( charFirst ) , "" ) ;

				int lastCharPos = priceToFinallyChangeToBigDecimal2.length() ;
				char charLast = priceToFinallyChangeToBigDecimal2.charAt( lastCharPos - 1 ) ;

				String priceToFinallyChangeToBigDecimal3 = priceToFinallyChangeToBigDecimal2.replace( String.valueOf( charLast ) , "" ) ;

				priceFormattedFinal = priceToFinallyChangeToBigDecimal3 ;
				priceBig = new BigDecimal( priceFormattedFinal ) ;
			}

			else if ( priceStr.contains( "," ) && !priceStr.contains( "." ) ) {
				String priceFormatted = priceStr.replaceAll( "," , "." ) ;
				priceFormattedFinal = priceFormatted ;
				priceBig = new BigDecimal( priceFormattedFinal ) ;
			}

			else if ( !priceStr.contains( "," ) && priceStr.contains( "." ) ) {

				for ( int i = 0 ; i < priceStr.length() ; i++ ) {
					char eachCharacter = priceStr.charAt( i ) ;
					String eachChar = String.valueOf( eachCharacter ) ;
					priceChars.add( eachChar ) ;
				}

				String germanSeparator = "." ;

				for ( int i = 0 ; i <= priceChars.size() ; i++ ) {
					if ( priceChars.contains( germanSeparator ) ) {
						priceChars.remove( germanSeparator ) ;
					}

				}

				String v = priceChars.toString() ;
				String priceToFinallyChangeToBigDecimal = v.replaceAll( "," , "" ).replaceAll( " " , "" ) ;

				char charFirst = priceToFinallyChangeToBigDecimal.charAt( 0 ) ;

				String priceToFinallyChangeToBigDecimal2 = priceToFinallyChangeToBigDecimal.replace( String.valueOf( charFirst ) , "" ) ;

				int lastCharPos = priceToFinallyChangeToBigDecimal2.length() ;
				char charLast = priceToFinallyChangeToBigDecimal2.charAt( lastCharPos - 1 ) ;

				String priceToFinallyChangeToBigDecimal3 = priceToFinallyChangeToBigDecimal2.replace( String.valueOf( charLast ) , "" ) ;

				priceFormattedFinal = priceToFinallyChangeToBigDecimal3 ;
				priceBig = new BigDecimal( priceFormattedFinal ) ;

			}

			else {
				priceBig = new BigDecimal( priceStr ) ;
				System.out.println( "last price decision: " + priceBig ) ;
			}

		}

		if ( numberFormat.equals( "French" ) ) {
			System.out.println( "priceStr french " + priceStr ) ;

			if ( priceStr.contains( " " ) && priceStr.contains( "," ) ) {
				String priceFormatted = priceStr.replaceAll( " " , "" ).replaceAll( "," , "." ) ;
				System.out.println( "rep1: " + priceFormatted ) ;
				priceFormattedFinal = priceFormatted ;
				priceBig = new BigDecimal( priceFormattedFinal ) ;
			}

			else if ( priceStr.contains( " " ) && !priceStr.contains( "," ) ) {
				String priceFormatted = priceStr.replaceAll( " " , "" ) ;
				priceFormattedFinal = priceFormatted ;
				priceBig = new BigDecimal( priceFormattedFinal ) ;
			}

			else if ( priceStr.contains( "," ) && !priceStr.contains( " " ) ) {
				String priceFormatted = priceStr.replaceAll( "," , "." ) ;
				priceFormattedFinal = priceFormatted ;
				priceBig = new BigDecimal( priceFormattedFinal ) ;
			}

			else {
				priceBig = new BigDecimal( priceStr ) ;
			}

		}

		return priceBig ;
	}

	/*
	 * This method checks for the final success of a given price number that must have been formatted
	 * correctly. It then allows for the triggering or not triggering of the client side function to
	 * save price from the client side
	 */

	@RequestMapping( value = "/api/business/pricing/successResponse" , method = RequestMethod.GET , produces = "application/json" )
	@ResponseBody
	public String insertionResponsePricingOK() {

		String message = null ;

		System.out.println( getEnglishNumberOK() ) ;

		if ( getNumberFormatOkGeneral().equalsIgnoreCase( "English" ) ) {

			if ( getEnglishNumberOK().equalsIgnoreCase( "OK" ) ) {
				message = "The number is ok in English format" ;
			} else if ( getEnglishNumberOK().equalsIgnoreCase( "Not OK" ) ) {
				message = "The number is NOT ok in English format" ;
			}

		} else if ( getNumberFormatOkGeneral().equalsIgnoreCase( "German" ) ) {

			if ( getGremanhNumberOK().equalsIgnoreCase( "OK" ) ) {
				message = "The number is ok in German format" ;
			} else if ( getGremanhNumberOK().equalsIgnoreCase( "Not OK" ) ) {
				message = "The number is NOT ok in German format" ;
			}

		} else if ( getNumberFormatOkGeneral().equalsIgnoreCase( "French" ) ) {

			if ( getFrenchNumberOK().equalsIgnoreCase( "OK" ) ) {
				message = "The number is ok in French format" ;
			} else if ( getFrenchNumberOK().equalsIgnoreCase( "Not OK" ) ) {
				message = "The number is NOT ok in French format" ;
			}

		}

		return new Gson().toJson( pricingService.getSuccessMessage( message ) ) ;
	}

	/*
	 * This method is not yet used for any triggering in the client side but it has a corresponding
	 * function in the client side
	 */
	@RequestMapping( value = "/api/business/pricing/errorResponse" , method = RequestMethod.GET , produces = "application/json" )
	@ResponseBody
	public String insertionResponsePricingNotOK() {

		String message = "Please check your number properly." ;
		return new Gson().toJson( pricingService.getSuccessMessage( message ) ) ;
	}

	/*
	 * This method has not been used in the client side yet and it does not have a corresponding
	 * function in the client side
	 */
	@RequestMapping( value = "/api/business/pricing/notSuccessfulResponse" , method = RequestMethod.GET , produces = "application/json" )
	@ResponseBody
	public String insertionResponsePricingSomethingWentWrong() {

		String message = "Not Successful." ;
		return new Gson().toJson( message ) ;
	}

	/* This method populates the drop down list of the language formats */
	@RequestMapping( value = "/api/business/pricing/numberFormats" , method = RequestMethod.GET , produces = "application/json" )
	@ResponseBody
	public String findNumberFormats() {

		return new Gson().toJson( pricingService.getPurchasesNumberFormat() ) ;
	}

	/* This method populates the drop down list of the items */
	@RequestMapping( value = "/api/business/pricing/items/{businessName}/{emailOfUser}/allItems" , method = RequestMethod.GET , produces = "application/json" )
	@ResponseBody
	public String findAllItems( Model model , @PathVariable( name = "businessName" ) String businessName , @PathVariable( name = "emailOfUser" ) String emailOfUser ) {

		EmailsOfABusinessLogin emailsOfABusiness = new EmailsOfABusinessLogin() ;
		emailOfUser = ChooseBusinessController.getEmailLoggedIn() ;
		businessName = ChooseBusinessController.getNameOfTheBusiness() ;
		model.addAttribute( "emailsOfABusinessForm" , emailsOfABusiness ) ;
		model.addAttribute( "emailOfUser" , emailOfUser ) ;
		model.addAttribute( "businessName" , businessName ) ;
		String businessId = ChooseBusinessController.getIdOfBusiness().toString() ;

		System.out.println( "price bizID....: " + businessId ) ;

		String allItems = null ;
		if ( getChangeMade() == 0 ) {
			setItemsToFindID( pricingService.findAllItemsWithoutRefreshing() ) ;
			allItems = new Gson().toJson( pricingService.findAllItemsWithoutRefreshing() ) ;
		} else {
			setItemsToFindID( pricingService.findAllItemsWithRefreshing( businessId ) ) ;
			allItems = new Gson().toJson( getItemsToFindID() ) ;
		}

		return new Gson().toJson( pricingService.listOfAllItemsWhosePricesAreNotYetSet() ) ;
	}

	/* This method is used to get item ID (BigDecimal) */
	private String getItemIDBasingOnSubCategory( String subCat , String businessId ) {

		Item item = null ;
		String id = null ;
		HashMap < String , Item > hashMap = pricingService.findAllItemsToHelpFindItemID_Direct( businessId ) ;
		// HashMap<String, Item> hashMap1 =
		// pricingService.findAllItemsToHelpFindItemID_Not_Direct();
		// if (hashMap1 != null) {
		// hashMap = hashMap1;
		// } else {
		// hashMap = pricingService.findAllItemsToHelpFindItemID_Direct(businessId);
		// }
		System.out.println( "subCat: " + subCat ) ;

		if ( hashMap.containsKey( subCat ) ) {
			item = hashMap.get( subCat ) ;
			// System.out.println("item: " + item);
			id = item.getId().toString() ;
			System.out.println( "id subcat: " + id ) ;
		}
		// System.out.println("Map: " + hashMap);
		System.out.println( "ID: " + id ) ;
		return id ;
	}

	/* This method populates the prices table */
	@RequestMapping( value = "/api/business/pricing/{businessName}/{emailOfUser}/allPrices" , method = RequestMethod.GET , produces = "application/json" )
	@ResponseBody
	public String findAllItemPrices( Model model , @PathVariable( name = "businessName" ) String businessName , @PathVariable( name = "emailOfUser" ) String emailOfUser ,
			@RequestParam( "numberFormat" ) String numberFormat ) {

		EmailsOfABusinessLogin emailsOfABusiness = new EmailsOfABusinessLogin() ;
		emailOfUser = ChooseBusinessController.getEmailLoggedIn() ;
		businessName = ChooseBusinessController.getNameOfTheBusiness() ;
		model.addAttribute( "emailsOfABusinessForm" , emailsOfABusiness ) ;
		model.addAttribute( "emailOfUser" , emailOfUser ) ;
		model.addAttribute( "businessName" , businessName ) ;
		String businessId = ChooseBusinessController.getIdOfBusiness().toString() ;

		setBusId( businessId ) ;

		String allPrices = null ;

		List < Pricing > pricings = new ArrayList <>() ;

		if ( getChangeMade() == 0 ) {
			pricings = pricingService.findAllPrcingNotDirect( businessId ) ;
			allPrices = new Gson().toJson( pricings ) ;
		} else {
			pricings = pricingService.findAllPricingsDirect( businessId , numberFormat ) ;
			allPrices = new Gson().toJson( pricings ) ;
		}

		return allPrices ;
	}

	/* finding the name of the item for editing */
	@RequestMapping( value = "/api/business/pricing/getNameOfItemForEditing" , method = RequestMethod.GET , produces = "application/json" )
	@ResponseBody
	public String findNameOfItemForEditing() {

		return new Gson().toJson( pricingService.getItemWhoseIDisRequiredForEditing() ) ;
	}

	private List < BigDecimal > allItemIDs_Sorted() {

		List < BigDecimal > IDs = new ArrayList <>() ;
		for ( Item eachItem : getItemsToFindID() ) {
			IDs.add( eachItem.getId() ) ;
		}
		Collections.sort( IDs ) ;
		return IDs ;
	}

	// private static BigDecimal getIDByElementIndex(Number index, List<BigDecimal>
	// idList) {
	// BigDecimal id = idList.get((int) index);
	// return id;
	// }

	@RequestMapping( value = "/api/business/pricing/sendIdSearchableForEditing" , method = RequestMethod.POST )
	@ResponseBody
	public String sendIdSearchableForEditing( @RequestParam( "idSearchable" ) BigDecimal idSearchable ) {

		List < Item > items = new ArrayList <>() ;

		if ( getIdKeyItemValueMap() == null ) {
			setIdKeyItemValueMap( pricingService.findItemNameBasingOnIDMappingDirect( getBusId() ) ) ;
		}

		System.out.println( "idSearchable: " + idSearchable ) ;

		System.out.println( "Keys cont: " + getIdKeyItemValueMap().keySet() ) ;
		System.out.println( "all IDs sorted: " + allItemIDs_Sorted() ) ;

		items = pricingService.getListOfItemsToExtractNameForEditing( getItemsToFindID() , idSearchable , getIdKeyItemValueMap() , allItemIDs_Sorted() ) ;
		// itemName = new Gson().toJson(items);
		// System.out.println("it ns: " + itemName);

		return "/api/business/pricing/sendIdSearchableForEditing" ;
	}

	public static void main( String [ ] args ) {

		String strValue = " item -- (sub cat of item)" ;
		String tt = removeSpaceFirstCharacterItemSubCategory( strValue ) ;
		System.out.println( "tt: " + tt ) ;
		System.out.println( "str value: " + strValue ) ;

	}

	public int getChangeMade() {

		return changeMade ;
	}

	public void setChangeMade( int changeMade ) {

		this.changeMade = changeMade ;
	}

	public int getPriceChecked() {

		return priceChecked ;
	}

	public void setPriceChecked( int priceChecked ) {

		this.priceChecked = priceChecked ;
	}

	public String getEnglishNumberOK() {

		return englishNumberOK ;
	}

	public void setEnglishNumberOK( String englishNumberOK ) {

		this.englishNumberOK = englishNumberOK ;
	}

	public String getGremanhNumberOK() {

		return gremanhNumberOK ;
	}

	public void setGremanhNumberOK( String gremanhNumberOK ) {

		this.gremanhNumberOK = gremanhNumberOK ;
	}

	public String getFrenchNumberOK() {

		return frenchNumberOK ;
	}

	public void setFrenchNumberOK( String frenchNumberOK ) {

		this.frenchNumberOK = frenchNumberOK ;
	}

	public String getNumberFormatOkGeneral() {

		return numberFormatOkGeneral ;
	}

	public void setNumberFormatOkGeneral( String numberFormatOkGeneral ) {

		this.numberFormatOkGeneral = numberFormatOkGeneral ;
	}

	public List < Item > getItemsToFindID() {

		return itemsToFindID ;
	}

	public void setItemsToFindID( List < Item > itemsToFindID ) {

		this.itemsToFindID = itemsToFindID ;
	}

	public int getInsertionComplete() {

		return insertionComplete ;
	}

	public void setInsertionComplete( int insertionComplete ) {

		this.insertionComplete = insertionComplete ;
	}

	public List < BigDecimal > getSortedItemIDs() {

		return sortedItemIDs ;
	}

	public void setSortedItemIDs( List < BigDecimal > sortedItemIDs ) {

		this.sortedItemIDs = sortedItemIDs ;
	}

	public static HashMap < BigDecimal , Item > getIdKeyItemValueMap() {

		return idKeyItemValueMap ;
	}

	public static void setIdKeyItemValueMap( HashMap < BigDecimal , Item > idKeyItemValueMap ) {

		PricingController.idKeyItemValueMap = idKeyItemValueMap ;
	}

	public String getBusId() {

		return busId ;
	}

	public void setBusId( String busId ) {

		this.busId = busId ;
	}

}
