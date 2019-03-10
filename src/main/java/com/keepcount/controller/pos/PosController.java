
package com.keepcount.controller.pos ;

import java.math.BigDecimal ;
import java.text.NumberFormat ;
import java.util.ArrayList ;
import java.util.LinkedHashMap ;
import java.util.List ;
import java.util.Locale ;
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
import com.keepcount.controller.pricing.PricingInsertionVerification ;
import com.keepcount.model.business.create.EmailsOfABusinessLogin ;
import com.keepcount.model.errorsuccess.ErrorSuccess ;
import com.keepcount.model.pos.AddToCart ;
import com.keepcount.model.pos.CartListCash ;
import com.keepcount.model.pos.EmailsPhoneNumbersAndNamesOfBusinesses ;
import com.keepcount.model.pricing.Pricing ;
import com.keepcount.service.pos.CartListCashService ;
import com.keepcount.service.pos.CartRetreivalService ;
import com.keepcount.service.pos.EmailsPhoneNumbersAndNamesOfBusinessesServiceForPOSCash ;
import com.keepcount.service.pos.PosService ;

@Controller
public class PosController {

	private static int changeMade = 0 ;

	@Autowired
	private PosService posService ;
	@Autowired
	private CartRetreivalService cartRetreivalService ;
	@Autowired
	private CartListCashService cartListCashService ;

	@Autowired
	private EmailsPhoneNumbersAndNamesOfBusinessesServiceForPOSCash emailsPhoneNumbersAndNamesOfBusinessesServiceForPOSCash ;

	@RequestMapping( value = "/business/pos/{businessName}/{emailOfUser}" )
	public String showPosPage( Model model , @PathVariable( name = "businessName" ) String businessName , @PathVariable( name = "emailOfUser" ) String emailOfUser ) {

		EmailsOfABusinessLogin emailsOfABusiness = new EmailsOfABusinessLogin() ;
		emailOfUser = ChooseBusinessController.getEmailLoggedIn() ;
		businessName = ChooseBusinessController.getNameOfTheBusiness() ;
		model.addAttribute( "emailOfUser" , emailOfUser ) ;
		model.addAttribute( "businessName" , businessName ) ;
		model.addAttribute( "emailsOfABusinessForm" , emailsOfABusiness ) ;
		setChangeMade( 1 ) ;
		return "/keepCount/business/pos/pos" ;
	}

	/* This method populates the items table */
	@RequestMapping( value = "/api/business/pos/{businessName}/{emailOfUser}/allItems" , method = RequestMethod.GET , produces = "application/json" )
	@ResponseBody
	public String findAllItemPrices( Model model , @PathVariable( name = "businessName" ) String businessName , @PathVariable( name = "emailOfUser" ) String emailOfUser
	// ,@RequestParam("numberFormat") String numberFormat
	) {

		EmailsOfABusinessLogin emailsOfABusiness = new EmailsOfABusinessLogin() ;
		emailOfUser = ChooseBusinessController.getEmailLoggedIn() ;
		businessName = ChooseBusinessController.getNameOfTheBusiness() ;
		model.addAttribute( "emailsOfABusinessForm" , emailsOfABusiness ) ;
		model.addAttribute( "emailOfUser" , emailOfUser ) ;
		model.addAttribute( "businessName" , businessName ) ;
		String businessId = ChooseBusinessController.getIdOfBusiness().toString() ;

		String allPrices = null ;
		List < Pricing > pricings = new ArrayList <>() ;

		if ( getChangeMade() == 1 ) {
			pricings = posService.findAllItemsForCart_Direct( businessId , "English" ) ;
			allPrices = new Gson().toJson( pricings ) ;
		} else {
			pricings = posService.findAllItemsForCart_Not_Direct() ;
			allPrices = new Gson().toJson( pricings ) ;
		}

		return allPrices ;
	}

	/* This method populates the items table */
	@RequestMapping( value = "/api/business/pos/{businessName}/{emailOfUser}/allAddToCarts" , method = RequestMethod.GET , produces = "application/json" )
	@ResponseBody
	public String findAllAddToCarts( Model model , @PathVariable( name = "businessName" ) String businessName , @PathVariable( name = "emailOfUser" ) String emailOfUser
	// ,@RequestParam("numberFormat") String numberFormat
	) {

		EmailsOfABusinessLogin emailsOfABusiness = new EmailsOfABusinessLogin() ;
		emailOfUser = ChooseBusinessController.getEmailLoggedIn() ;
		businessName = ChooseBusinessController.getNameOfTheBusiness() ;
		model.addAttribute( "emailsOfABusinessForm" , emailsOfABusiness ) ;
		model.addAttribute( "emailOfUser" , emailOfUser ) ;
		model.addAttribute( "businessName" , businessName ) ;
		String businessId = ChooseBusinessController.getIdOfBusiness().toString() ;

		// setBusId(businessId);

		List < AddToCart > addToCarts = cartRetreivalService.getAddToCartList( businessId ) ;

		return new Gson().toJson( addToCarts ) ;
	}

	private static String numberFormatOkGeneral ;
	private static String englishNumberOK ;
	private static String germanNumberOK ;
	private static String frenchNumberOK ;

	public static String getNumberFormatOkGeneral() {

		return numberFormatOkGeneral ;
	}

	public static void setNumberFormatOkGeneral( String numberFormatOkGeneral ) {

		PosController.numberFormatOkGeneral = numberFormatOkGeneral ;
	}

	public static String getEnglishNumberOK() {

		return englishNumberOK ;
	}

	public static void setEnglishNumberOK( String englishNumberOK ) {

		PosController.englishNumberOK = englishNumberOK ;
	}

	public static String getGermanNumberOK() {

		return germanNumberOK ;
	}

	public static void setGermanNumberOK( String germanNumberOK ) {

		PosController.germanNumberOK = germanNumberOK ;
	}

	public static String getFrenchNumberOK() {

		return frenchNumberOK ;
	}

	public static void setFrenchNumberOK( String frenchNumberOK ) {

		PosController.frenchNumberOK = frenchNumberOK ;
	}

	@RequestMapping( value = "/api/business/posCash/{businessName}/{emailOfUser}/checkQuantityCorrectness" , method = RequestMethod.POST )
	@ResponseBody
	public String apiToCheckPriceCorrectness( HttpServletRequest request , HttpServletResponse response , @PathVariable( name = "businessName" ) String businessName ,
			@PathVariable( name = "emailOfUser" ) String emailOfUser , @RequestBody AddToCart addToCart , @RequestParam( "numberFormat" ) String numberFormat ) {

		String quantityStr = addToCart.getItemQuantityStr() ;

		Map < String , String > map = PricingInsertionVerification.verifyPrice( numberFormat , quantityStr ) ;

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
				setGermanNumberOK( setOkOrNot ) ;
			}
		} else if ( numberFormat.equalsIgnoreCase( "French" ) ) {
			if ( key.size() <= 1 ) {
				setFrenchNumberOK( setOkOrNot ) ;
			}
		}

		return "/api/business/pricing/{businessName}/{emailOfUser}/checkPriceCorrectness" ;
	}

	@RequestMapping( value = "/api/business/posCash/{businessName}/{emailOfUser}/checkAmountPaidInCorrectness" , method = RequestMethod.POST )
	@ResponseBody
	public String apiToCheckAmountPaidInCorrectness( HttpServletRequest request , HttpServletResponse response , @PathVariable( name = "businessName" ) String businessName ,
			@PathVariable( name = "emailOfUser" ) String emailOfUser , @RequestBody CartListCash cartListCash , @RequestParam( "numberFormat" ) String numberFormat ) {

		String quantityStr = cartListCash.getAmountPaidInStr() ;
		System.out.println( "am: " + quantityStr ) ;
		System.out.println( "frm: " + numberFormat ) ;

		Map < String , String > map = PricingInsertionVerification.verifyPrice( numberFormat , quantityStr ) ;

		System.out.println( map ) ;
		System.out.println( "values:" ) ;
		System.out.println( map.values() ) ;
		System.out.println( "keys:" ) ;
		System.out.println( map.keySet() ) ;

		Set < String > key = map.keySet() ;

		if ( !key.isEmpty() ) {

			Object setOkOrNotObject = key.iterator().next() ;
			String setOkOrNot = setOkOrNotObject.toString() ;

			setNumberFormatOkGeneral( numberFormat ) ;

			if ( numberFormat.equalsIgnoreCase( "English" ) ) {
				if ( key.size() <= 1 ) {
					setEnglishNumberOK( setOkOrNot ) ;
				}
			} else if ( numberFormat.equalsIgnoreCase( "German" ) ) {
				if ( key.size() <= 1 ) {
					setGermanNumberOK( setOkOrNot ) ;
				}
			} else if ( numberFormat.equalsIgnoreCase( "French" ) ) {
				if ( key.size() <= 1 ) {
					setFrenchNumberOK( setOkOrNot ) ;
				}
			}

		}

		return "/api/business/pricing/{businessName}/{emailOfUser}/checkAmountPaidInCorrectness" ;
	}

	@RequestMapping( value = "/api/business/pos/successResponse" , method = RequestMethod.GET , produces = "application/json" )
	@ResponseBody
	public String insertionResponsePricingOK() {

		String message = null ;

		System.out.println( getEnglishNumberOK() ) ;

		if ( getNumberFormatOkGeneral() != null ) {

			if ( getNumberFormatOkGeneral().equalsIgnoreCase( "English" ) ) {

				if ( getEnglishNumberOK().equalsIgnoreCase( "OK" ) ) {
					message = "The number is ok in English format" ;
				} else if ( getEnglishNumberOK().equalsIgnoreCase( "Not OK" ) ) {
					message = "The number is NOT ok in English format" ;
				}

			} else if ( getNumberFormatOkGeneral().equalsIgnoreCase( "German" ) ) {

				if ( getGermanNumberOK().equalsIgnoreCase( "OK" ) ) {
					message = "The number is ok in German format" ;
				} else if ( getGermanNumberOK().equalsIgnoreCase( "Not OK" ) ) {
					message = "The number is NOT ok in German format" ;
				}

			} else if ( getNumberFormatOkGeneral().equalsIgnoreCase( "French" ) ) {

				if ( getFrenchNumberOK().equalsIgnoreCase( "OK" ) ) {
					message = "The number is ok in French format" ;
				} else if ( getFrenchNumberOK().equalsIgnoreCase( "Not OK" ) ) {
					message = "The number is NOT ok in French format" ;
				}

			}

		}

		return new Gson().toJson( posService.getSuccessMessage( message ) ) ;
	}

	// Adding an item to cart
	@RequestMapping( value = "/api/business/posCash/addItemToCart" , method = RequestMethod.POST )
	@ResponseBody
	public String addingAnItemToCart( @RequestBody CartListCash cartListCash , @RequestParam( "numberFormat" ) String numberFormat ) {

		String qtyStr = cartListCash.getItemQuantityStr() ;
		BigDecimal quantityInBigDecimal = getNumberInBigDecimal( numberFormat , qtyStr ) ;
		cartListCash.setItemQuantity( quantityInBigDecimal ) ;

		String unitCostStr = cartListCash.getUnitCostStr() ;
		BigDecimal unitCostInBigDecimal = getNumberInBigDecimal( numberFormat , unitCostStr ) ;
		cartListCash.setUnitCost( unitCostInBigDecimal ) ;

		cartListCashService.addingAnItemToCart( cartListCash ) ;

		return "/api/business/posCash/addItemToCart" ;
	}

	// reducing an item from cart
	@RequestMapping( value = "/api/business/posCash/subtractItemFromCart" , method = RequestMethod.POST )
	@ResponseBody
	public String reduceAnItemFromCart( @RequestBody CartListCash cartListCash , @RequestParam( "numberFormat" ) String numberFormat ) {

		BigDecimal itemId = cartListCash.getItemId() ;

		Map < BigDecimal , CartListCash > map = cartListCashService.getMap() ;
		CartListCash cartListCash2 = map.get( itemId ) ;

		cartListCashService.subtractingAnItemFromCart( cartListCash2 ) ;

		return "/api/business/posCash/subtractItemFromCart" ;
	}

	// increasing an item from cart
	@RequestMapping( value = "/api/business/posCash/increaseItemInCart" , method = RequestMethod.POST )
	@ResponseBody
	public String increaseAnItemInCart( @RequestBody CartListCash cartListCash , @RequestParam( "numberFormat" ) String numberFormat ) {

		BigDecimal itemId = cartListCash.getItemId() ;

		Map < BigDecimal , CartListCash > map = cartListCashService.getMap() ;
		CartListCash cartListCash2 = map.get( itemId ) ;

		cartListCashService.increaseAnItemInCart( cartListCash2 ) ;

		return "/api/business/posCash/increasetemInCart" ;
	}

	// reducing an item from cart
	@RequestMapping( value = "/api/business/posCash/removeItemFromCart" , method = RequestMethod.POST )
	@ResponseBody
	public String removeAnItemFromCart( @RequestBody CartListCash cartListCash , @RequestParam( "numberFormat" ) String numberFormat ) {

		BigDecimal itemId = cartListCash.getItemId() ;

		Map < BigDecimal , CartListCash > map = cartListCashService.getMap() ;
		CartListCash cartListCash2 = map.get( itemId ) ;

		cartListCashService.removeAnItemFromCart( cartListCash2 ) ;

		return "/api/business/posCash/removeItemFromCart" ;
	}

	/* This method populates the items table */
	@RequestMapping( value = "/api/business/posCash/cartList" , method = RequestMethod.GET , produces = "application/json" )
	@ResponseBody
	public String populateCartList( @RequestParam( "numberFormat" ) String numberFormat ) {

		Map < BigDecimal , CartListCash > map = cartListCashService.getMap() ;

		return new Gson().toJson( this.numberFormattingInCartList( map , numberFormat ) ) ;

	}

	private BigDecimal total ;

	public BigDecimal getTotal() {

		return total ;
	}

	public void setTotal( BigDecimal total ) {

		this.total = total ;
	}

	/* This method returns total */
	@RequestMapping( value = "/api/business/posCash/total" , method = RequestMethod.GET , produces = "application/json" )
	@ResponseBody
	public String getTotal( @RequestParam( "numberFormat" ) String numberFormat ) {

		Map < BigDecimal , CartListCash > map = cartListCashService.getMap() ;
		Map < BigDecimal , CartListCash > total = new LinkedHashMap <>() ;
		if ( map != null ) {
			total = cartListCashService.getTotal( map ) ;

			setTotal( total.get( BigDecimal.ZERO ).getTotalCost() ) ;
		}

		System.out.println( "map: " + total ) ;

		return new Gson().toJson( this.numberFormattingInCartListTotal( total , numberFormat ) ) ;

	}

	// getting amount paid from the field
	@RequestMapping( value = "/api/business/posCash/getAmountPaidFromClient" , method = RequestMethod.POST )
	@ResponseBody
	public String getAmountPaidFromClient( @RequestBody CartListCash cartListCash , @RequestParam( "numberFormat" ) String numberFormat ) {

		System.out.println( "amountStr: " + cartListCash.getAmountPaidInStr() ) ;

		System.out.println( "lang: " + numberFormat ) ;

		String amountPaidInStr = cartListCash.getAmountPaidInStr() ;
		BigDecimal amountPaidIn = BigDecimal.ZERO ;

		if ( amountPaidInStr != null ) {
			amountPaidIn = getNumberInBigDecimal( numberFormat , amountPaidInStr ) ;
		}

		cartListCash.setAmountPaidIn( amountPaidIn ) ;

		Map < BigDecimal , CartListCash > map = new LinkedHashMap <>() ;

		this.numberFormattingInCartListAmountPaidInAndChange( map , numberFormat ) ;

		cartListCashService.setAmountPaidIn( cartListCash ) ;

		System.out.println( "amount paid: " + amountPaidIn ) ;

		return "/api/business/posCash/getAmountPaidFromClient" ;
	}

	/* This method returns change */
	@RequestMapping( value = "/api/business/posCash/returnChangeToClient" , method = RequestMethod.GET , produces = "application/json" )
	@ResponseBody
	public String getChange( @RequestParam( "numberFormat" ) String numberFormat ) {

		Map < BigDecimal , CartListCash > change = cartListCashService.getChange( this.getTotal() ) ;

		if ( this.getTotal() == null ) {
			Map < BigDecimal , CartListCash > changeZero = cartListCashService.getChange( BigDecimal.ZERO ) ;
			return new Gson().toJson( this.numberFormattingInCartListAmountPaidInAndChange( changeZero , numberFormat ) ) ;
		}

		System.out.println( "totototot: " + getTotal() ) ;

		System.out.println( "change: " + change ) ;

		return new Gson().toJson( this.numberFormattingInCartListAmountPaidInAndChange( change , numberFormat ) ) ;

	}

	// verify change
	@RequestMapping( value = "/api/business/posCash/verifyChange" , method = RequestMethod.GET , produces = "application/json" )
	@ResponseBody
	public String verifyChange() {

		List < ErrorSuccess > verify = cartListCashService.verifyChange() ;
		System.out.println( "change verification: " + verify ) ;

		return new Gson().toJson( verify ) ;

	}

	private List < EmailsPhoneNumbersAndNamesOfBusinesses > customerEntryCheckResult ;

	public List < EmailsPhoneNumbersAndNamesOfBusinesses > getCustomerEntryCheckResult() {
		return customerEntryCheckResult ;
	}

	public void setCustomerEntryCheckResult( List < EmailsPhoneNumbersAndNamesOfBusinesses > customerEntryCheckResult ) {
		this.customerEntryCheckResult = customerEntryCheckResult ;
	}

	private String emailOrTelephoneNumberOfTheCustomer ;

	public String getEmailOrTelephoneNumberOfTheCustomer() {
		return emailOrTelephoneNumberOfTheCustomer ;
	}

	public void setEmailOrTelephoneNumberOfTheCustomer( String emailOrTelephoneNumberOfTheCustomer ) {
		this.emailOrTelephoneNumberOfTheCustomer = emailOrTelephoneNumberOfTheCustomer ;
	}

	@RequestMapping( value = "/api/business/posCash/checkIfCustomerEntryIsTelOrEmail" , method = RequestMethod.POST )
	@ResponseBody
	public String getEmailOrTelephoneOfTheCustomerForChecking( @RequestBody EmailsPhoneNumbersAndNamesOfBusinesses emailsPhoneNumbersAndNamesOfBusinesses ) {

		List < EmailsPhoneNumbersAndNamesOfBusinesses > listToReturnTheJSONForWhetherItIsEmailOrTelephoneNumber = new ArrayList <>() ;

		String emailOrTelephonNumberOfTheCustomer = emailsPhoneNumbersAndNamesOfBusinesses.getEmail() ;

		this.setEmailOrTelephoneNumberOfTheCustomer( emailOrTelephonNumberOfTheCustomer ) ;

		// emailsPhoneNumbersAndNamesOfBusinesses.setEmail( emailOrTelephonNumberOfTheCustomer ) ;

		System.out.println( "em:....: " + emailOrTelephonNumberOfTheCustomer ) ;

		if ( emailOrTelephonNumberOfTheCustomer.contains( "@" ) || emailOrTelephonNumberOfTheCustomer.contains( "." ) ) {
			listToReturnTheJSONForWhetherItIsEmailOrTelephoneNumber = emailsPhoneNumbersAndNamesOfBusinessesServiceForPOSCash
					.checkIfCustomerEntryIsTelOrEmail( "it is an email from the customer" ) ;
			this.setCustomerEntryCheckResult( listToReturnTheJSONForWhetherItIsEmailOrTelephoneNumber ) ;
		} else {
			listToReturnTheJSONForWhetherItIsEmailOrTelephoneNumber = emailsPhoneNumbersAndNamesOfBusinessesServiceForPOSCash
					.checkIfCustomerEntryIsTelOrEmail( "it is a telephone number from the customer" ) ;
			this.setCustomerEntryCheckResult( listToReturnTheJSONForWhetherItIsEmailOrTelephoneNumber ) ;
		}

		return "/api/business/posCash/checkIfCustomerEntryIsTelOrEmail" ;
	}

	@RequestMapping( value = "/api/business/posCash/returnCheckIfCustomerEntryIsTelOrEmail" , method = RequestMethod.GET , produces = "application/json" )
	@ResponseBody
	public String returnCheckIfCustomerEntryIsTelOrEmail() {

		return new Gson().toJson( this.getCustomerEntryCheckResult() ) ;
	}

	@RequestMapping( value = "/api/business/posCash/returnNamesOfBusinessesToTheUserBasingOnEntryOfCustomerEmailOrTelephone" , method = RequestMethod.GET , produces = "application/json" )
	@ResponseBody
	public String returnNamesOfBusinessesToTheUserBasingOnEntryOfCustomerEmailOrTelephone() {
		List < EmailsPhoneNumbersAndNamesOfBusinesses > emailsPhoneNumbersAndNamesOfBusinessesToBeReturnedAsJsonObject = new ArrayList <>() ;

		EmailsPhoneNumbersAndNamesOfBusinesses customerEntry = this.getCustomerEntryCheckResult().get( 0 ) ;

		if ( customerEntry.getEmail().equals( "it is an email from the customer" ) ) {
			emailsPhoneNumbersAndNamesOfBusinessesToBeReturnedAsJsonObject = emailsPhoneNumbersAndNamesOfBusinessesServiceForPOSCash
					.findAllEmailsPhoneNumbersAndNamesOfBusinessesBasingOnEmailOfTheCustomer( this.getEmailOrTelephoneNumberOfTheCustomer() ) ;
		} else {
			emailsPhoneNumbersAndNamesOfBusinessesToBeReturnedAsJsonObject = emailsPhoneNumbersAndNamesOfBusinessesServiceForPOSCash
					.findAllEmailsPhoneNumbersAndNamesOfBusinessesBasingOnTelephoneNumberOfTheCustomer( this.getEmailOrTelephoneNumberOfTheCustomer() ) ;
		}

		return new Gson().toJson( emailsPhoneNumbersAndNamesOfBusinessesToBeReturnedAsJsonObject ) ;
	}

	private Map < BigDecimal , CartListCash > numberFormattingInCartList( Map < BigDecimal , CartListCash > map , String numberFormatLang ) {

		NumberFormat formatEnglish = NumberFormat.getNumberInstance( Locale.ENGLISH ) ;
		NumberFormat formatGerman = NumberFormat.getNumberInstance( Locale.GERMAN ) ;
		NumberFormat formatFrench = NumberFormat.getNumberInstance( Locale.FRENCH ) ;

		Map < BigDecimal , CartListCash > map2 = new LinkedHashMap <>() ;

		if ( numberFormatLang.equalsIgnoreCase( "English" ) ) {
			for ( Map.Entry < BigDecimal , CartListCash > cash : map.entrySet() ) {

				BigDecimal id = cash.getValue().getId() ;
				String item = cash.getValue().getItem() ;

				BigDecimal itemId = cash.getValue().getItemId() ;

				BigDecimal itemQty = cash.getValue().getItemQuantity() ;
				String itemQtyStr = formatEnglish.format( itemQty ) ;

				BigDecimal unitCost = cash.getValue().getUnitCost() ;
				String unitCostStr = formatEnglish.format( unitCost ) ;

				BigDecimal costOfItem = cash.getValue().getCostOfItem() ;
				String costOfItemStr = formatEnglish.format( costOfItem ) ;

				BigDecimal totalCost = cash.getValue().getTotalCost() ;
				String totalCostStr = null ;
				if ( totalCost != null ) {
					totalCostStr = formatEnglish.format( totalCost ) ;
				}

				BigDecimal amountPaidIn = cash.getValue().getAmountPaidIn() ;
				String amountPaidInStr = null ;
				if ( amountPaidIn != null ) {
					amountPaidInStr = formatEnglish.format( amountPaidIn ) ;
				}

				BigDecimal change = cash.getValue().getChange() ;
				String changeStr = null ;
				if ( amountPaidInStr != null ) {
					changeStr = formatEnglish.format( change ) ;
				}

				CartListCash listCash = new CartListCash( id , item , itemId , itemQty , itemQtyStr , unitCost , unitCostStr , costOfItem , costOfItemStr , totalCost ,
						totalCostStr , amountPaidIn , amountPaidInStr , change , changeStr ) ;

				map2.put( itemId , listCash ) ;

			}
		} else if ( numberFormatLang.equalsIgnoreCase( "German" ) ) {
			for ( Map.Entry < BigDecimal , CartListCash > cash : map.entrySet() ) {

				BigDecimal id = cash.getValue().getId() ;
				String item = cash.getValue().getItem() ;

				BigDecimal itemId = cash.getValue().getItemId() ;

				BigDecimal itemQty = cash.getValue().getItemQuantity() ;
				String itemQtyStr = formatGerman.format( itemQty ) ;

				BigDecimal unitCost = cash.getValue().getUnitCost() ;
				String unitCostStr = formatGerman.format( unitCost ) ;

				BigDecimal costOfItem = cash.getValue().getCostOfItem() ;
				String costOfItemStr = formatGerman.format( costOfItem ) ;

				BigDecimal totalCost = cash.getValue().getTotalCost() ;
				String totalCostStr = null ;
				if ( totalCost != null ) {
					totalCostStr = formatGerman.format( totalCost ) ;
				}

				BigDecimal amountPaidIn = cash.getValue().getAmountPaidIn() ;
				String amountPaidInStr = null ;
				if ( amountPaidIn != null ) {
					amountPaidInStr = formatGerman.format( amountPaidIn ) ;
				}

				BigDecimal change = cash.getValue().getChange() ;
				String changeStr = null ;
				if ( amountPaidInStr != null ) {
					changeStr = formatGerman.format( change ) ;
				}
				CartListCash listCash = new CartListCash( id , item , itemId , itemQty , itemQtyStr , unitCost , unitCostStr , costOfItem , costOfItemStr , totalCost ,
						totalCostStr , amountPaidIn , amountPaidInStr , change , changeStr ) ;

				map2.put( itemId , listCash ) ;

			}

		} else if ( numberFormatLang.equalsIgnoreCase( "French" ) ) {
			for ( Map.Entry < BigDecimal , CartListCash > cash : map.entrySet() ) {

				BigDecimal id = cash.getValue().getId() ;
				String item = cash.getValue().getItem() ;

				BigDecimal itemId = cash.getValue().getItemId() ;

				BigDecimal itemQty = cash.getValue().getItemQuantity() ;
				String itemQtyStr = formatFrench.format( itemQty ) ;

				BigDecimal unitCost = cash.getValue().getUnitCost() ;
				String unitCostStr = formatFrench.format( unitCost ) ;

				BigDecimal costOfItem = cash.getValue().getCostOfItem() ;
				String costOfItemStr = formatFrench.format( costOfItem ) ;

				BigDecimal totalCost = cash.getValue().getTotalCost() ;
				String totalCostStr = null ;
				if ( totalCost != null ) {
					totalCostStr = formatFrench.format( totalCost ) ;
				}

				BigDecimal amountPaidIn = cash.getValue().getAmountPaidIn() ;
				String amountPaidInStr = null ;
				if ( amountPaidIn != null ) {
					amountPaidInStr = formatFrench.format( amountPaidIn ) ;
				}

				BigDecimal change = cash.getValue().getChange() ;
				String changeStr = null ;
				if ( amountPaidInStr != null ) {
					changeStr = formatFrench.format( change ) ;
				}

				CartListCash listCash = new CartListCash( id , item , itemId , itemQty , itemQtyStr , unitCost , unitCostStr , costOfItem , costOfItemStr , totalCost ,
						totalCostStr , amountPaidIn , amountPaidInStr , change , changeStr ) ;

				map2.put( itemId , listCash ) ;

			}

		}

		return map2 ;
	}

	// formatting total
	private Map < BigDecimal , CartListCash > numberFormattingInCartListTotal( Map < BigDecimal , CartListCash > map , String numberFormatLang ) {

		NumberFormat formatEnglish = NumberFormat.getNumberInstance( Locale.ENGLISH ) ;
		NumberFormat formatGerman = NumberFormat.getNumberInstance( Locale.GERMAN ) ;
		NumberFormat formatFrench = NumberFormat.getNumberInstance( Locale.FRENCH ) ;

		Map < BigDecimal , CartListCash > map2 = new LinkedHashMap <>() ;

		if ( numberFormatLang.equalsIgnoreCase( "English" ) ) {
			for ( Map.Entry < BigDecimal , CartListCash > cash : map.entrySet() ) {

				BigDecimal totalCost = cash.getValue().getTotalCost() ;
				String totalCostStr = null ;
				if ( totalCost != null ) {
					totalCostStr = formatEnglish.format( totalCost ) ;
				}

				BigDecimal amountPaidIn = cash.getValue().getAmountPaidIn() ;
				String amountPaidInStr = null ;
				if ( amountPaidIn != null ) {
					amountPaidInStr = formatEnglish.format( amountPaidIn ) ;
				}

				BigDecimal change = cash.getValue().getChange() ;
				String changeStr = null ;
				if ( amountPaidInStr != null ) {
					changeStr = formatEnglish.format( change ) ;
				}

				CartListCash listCash = new CartListCash( BigDecimal.ZERO , null , BigDecimal.ZERO , BigDecimal.ZERO , null , BigDecimal.ZERO , null , BigDecimal.ZERO , null ,
						totalCost , totalCostStr , amountPaidIn , amountPaidInStr , change , changeStr ) ;

				map2.put( BigDecimal.ZERO , listCash ) ;

			}
		} else if ( numberFormatLang.equalsIgnoreCase( "German" ) ) {
			for ( Map.Entry < BigDecimal , CartListCash > cash : map.entrySet() ) {

				BigDecimal totalCost = cash.getValue().getTotalCost() ;
				String totalCostStr = null ;
				if ( totalCost != null ) {
					totalCostStr = formatGerman.format( totalCost ) ;
				}

				BigDecimal amountPaidIn = cash.getValue().getAmountPaidIn() ;
				String amountPaidInStr = null ;
				if ( amountPaidIn != null ) {
					amountPaidInStr = formatGerman.format( amountPaidIn ) ;
				}

				BigDecimal change = cash.getValue().getChange() ;
				String changeStr = null ;
				if ( amountPaidInStr != null ) {
					changeStr = formatGerman.format( change ) ;
				}

				CartListCash listCash = new CartListCash( BigDecimal.ZERO , null , BigDecimal.ZERO , BigDecimal.ZERO , null , BigDecimal.ZERO , null , BigDecimal.ZERO , null ,
						totalCost , totalCostStr , amountPaidIn , amountPaidInStr , change , changeStr ) ;

				map2.put( BigDecimal.ZERO , listCash ) ;

			}

		} else if ( numberFormatLang.equalsIgnoreCase( "French" ) ) {
			for ( Map.Entry < BigDecimal , CartListCash > cash : map.entrySet() ) {

				BigDecimal totalCost = cash.getValue().getTotalCost() ;
				String totalCostStr = null ;
				if ( totalCost != null ) {
					totalCostStr = formatFrench.format( totalCost ) ;
				}

				BigDecimal amountPaidIn = cash.getValue().getAmountPaidIn() ;
				String amountPaidInStr = null ;
				if ( amountPaidIn != null ) {
					amountPaidInStr = formatFrench.format( amountPaidIn ) ;
				}

				BigDecimal change = cash.getValue().getChange() ;
				String changeStr = null ;
				if ( amountPaidInStr != null ) {
					changeStr = formatFrench.format( change ) ;
				}

				CartListCash listCash = new CartListCash( BigDecimal.ZERO , null , BigDecimal.ZERO , BigDecimal.ZERO , null , BigDecimal.ZERO , null , BigDecimal.ZERO , null ,
						totalCost , totalCostStr , amountPaidIn , amountPaidInStr , change , changeStr ) ;

				map2.put( BigDecimal.ZERO , listCash ) ;

			}

		}

		return map2 ;
	}

	private Map < BigDecimal , CartListCash > numberFormattingInCartListAmountPaidInAndChange( Map < BigDecimal , CartListCash > map , String numberFormatLang ) {

		NumberFormat formatEnglish = NumberFormat.getNumberInstance( Locale.ENGLISH ) ;
		NumberFormat formatGerman = NumberFormat.getNumberInstance( Locale.GERMAN ) ;
		NumberFormat formatFrench = NumberFormat.getNumberInstance( Locale.FRENCH ) ;

		Map < BigDecimal , CartListCash > map2 = new LinkedHashMap <>() ;

		System.out.println( "mmmmmmmmmmmmmmm: " + map ) ;

		if ( numberFormatLang != null ) {

			if ( map != null ) {

				if ( numberFormatLang.equalsIgnoreCase( "English" ) ) {
					for ( Map.Entry < BigDecimal , CartListCash > cash : map.entrySet() ) {

						BigDecimal amountPaidIn = cash.getValue().getAmountPaidIn() ;
						String amountPaidInStr = null ;
						if ( amountPaidIn != null ) {
							amountPaidInStr = formatEnglish.format( amountPaidIn ) ;
						}

						BigDecimal change = cash.getValue().getChange() ;
						String changeStr = null ;
						changeStr = formatEnglish.format( change ) ;

						CartListCash listCash = new CartListCash( BigDecimal.ZERO , null , BigDecimal.ZERO , BigDecimal.ZERO , null , BigDecimal.ZERO , null , BigDecimal.ZERO ,
								null , BigDecimal.ZERO , null , amountPaidIn , amountPaidInStr , change , changeStr ) ;

						map2.put( BigDecimal.ZERO , listCash ) ;

					}
				} else if ( numberFormatLang.equalsIgnoreCase( "German" ) ) {
					for ( Map.Entry < BigDecimal , CartListCash > cash : map.entrySet() ) {

						BigDecimal amountPaidIn = cash.getValue().getAmountPaidIn() ;
						String amountPaidInStr = null ;
						if ( amountPaidIn != null ) {
							amountPaidInStr = formatGerman.format( amountPaidIn ) ;
						}

						BigDecimal change = cash.getValue().getChange() ;
						String changeStr = null ;
						changeStr = formatGerman.format( change ) ;

						CartListCash listCash = new CartListCash( BigDecimal.ZERO , null , BigDecimal.ZERO , BigDecimal.ZERO , null , BigDecimal.ZERO , null , BigDecimal.ZERO ,
								null , BigDecimal.ZERO , null , amountPaidIn , amountPaidInStr , change , changeStr ) ;

						map2.put( BigDecimal.ZERO , listCash ) ;

					}

				} else if ( numberFormatLang.equalsIgnoreCase( "French" ) ) {
					for ( Map.Entry < BigDecimal , CartListCash > cash : map.entrySet() ) {

						BigDecimal amountPaidIn = cash.getValue().getAmountPaidIn() ;
						String amountPaidInStr = null ;
						if ( amountPaidIn != null ) {
							amountPaidInStr = formatFrench.format( amountPaidIn ) ;
						}

						BigDecimal change = cash.getValue().getChange() ;
						String changeStr = null ;
						changeStr = formatFrench.format( change ) ;

						CartListCash listCash = new CartListCash( BigDecimal.ZERO , null , BigDecimal.ZERO , BigDecimal.ZERO , null , BigDecimal.ZERO , null , BigDecimal.ZERO ,
								null , BigDecimal.ZERO , null , amountPaidIn , amountPaidInStr , change , changeStr ) ;

						map2.put( BigDecimal.ZERO , listCash ) ;

					}
				}

				return map2 ;

			}

		}

		return null ;

	}

	/*
	 * This method returns a BigDecimal value of the String price number obtained from the user. It also bases on the number format language to format the provided price
	 * String value and convert it into a BigDecimal value
	 */
	private BigDecimal getNumberInBigDecimal( String numberFormat , String priceStr ) {

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

	public static int getChangeMade() {

		return changeMade ;
	}

	public static void setChangeMade( int changeMade ) {

		PosController.changeMade = changeMade ;
	}

}