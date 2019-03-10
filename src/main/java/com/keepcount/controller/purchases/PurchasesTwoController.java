
package com.keepcount.controller.purchases ;

import java.math.BigDecimal ;
import java.sql.Timestamp ;
import java.time.LocalDateTime ;
import java.util.ArrayList ;
import java.util.List ;

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
import com.keepcount.controller.business.journal.GeneralJournalController ;
import com.keepcount.controller.business.ledger.LedgerController ;
import com.keepcount.dao.business.journal.GeneralJournalHibernationValues ;
import com.keepcount.model.business.create.EmailsOfABusinessLogin ;
import com.keepcount.model.business.journal.GeneralJournal ;
import com.keepcount.model.business.ledger.Ledger ;
import com.keepcount.model.purchases.PurchasesTwo ;
import com.keepcount.service.purchases.PurchasesTwoCalculationsService ;
import com.keepcount.service.purchases.PurchasesTwoNumberVerificationService ;
import com.keepcount.service.purchases.PurchasesTwoService ;

@Controller
public class PurchasesTwoController {

	@Autowired
	private PurchasesTwoService purchasesTwoService ;
	@Autowired
	private PurchasesTwoNumberVerificationService purchasesTwoNumberVerificationService ;
	@Autowired
	private PurchasesTwoCalculationsService purchasesTwoCalculationsService ;

	private int changeMade = 0 ;

	public int getChangeMade() {
		return changeMade ;
	}

	public void setChangeMade( int changeMade ) {
		this.changeMade = changeMade ;
	}

	@RequestMapping( value = "/business/purchasesTwo/{businessName}/{emailOfUser}" )
	public String showPurchasesPage( Model model , @PathVariable( name = "businessName" ) String businessName , @PathVariable( name = "emailOfUser" ) String emailOfUser ) {
		EmailsOfABusinessLogin emailsOfABusiness = new EmailsOfABusinessLogin() ;
		emailOfUser = ChooseBusinessController.getEmailLoggedIn() ;
		businessName = ChooseBusinessController.getNameOfTheBusiness() ;
		model.addAttribute( "emailOfUser" , emailOfUser ) ;
		model.addAttribute( "businessName" , businessName ) ;
		model.addAttribute( "emailsOfABusinessForm" , emailsOfABusiness ) ;

		System.out.println( "em and bizName at purch2: " + emailOfUser + " : " + businessName ) ;

		setChangeMade( 1 ) ;
		setChangeMade( 0 ) ;
		return "/keepCount/business/purchase/purchases-two" ;
	}

	/*
	 * This API
	 * 
	 * "/api/business/purchasesTwo/{businessName}/{emailOfUser}/allItems"
	 * 
	 * returns all items and sends them to the purchases-two.html <select> tag
	 * 
	 * to give a drop down of all the items so as to help the purchaser choose the item they want to
	 * purchase
	 * 
	 */
	@RequestMapping( value = "/api/business/purchasesTwo/{businessName}/{emailOfUser}/allItems" , method = RequestMethod.GET , produces = "application/json" )
	@ResponseBody
	public String findAllItems( Model model , @PathVariable( name = "businessName" ) String businessName , @PathVariable( name = "emailOfUser" ) String emailOfUser ) {

		EmailsOfABusinessLogin emailsOfABusiness = new EmailsOfABusinessLogin() ;
		emailOfUser = ChooseBusinessController.getEmailLoggedIn() ;
		businessName = ChooseBusinessController.getNameOfTheBusiness() ;
		// String emailOfTheBusiness =
		// ChooseBusinessController.getEmailOfThisBusiness();

		model.addAttribute( "emailsOfABusinessForm" , emailsOfABusiness ) ;
		model.addAttribute( "emailOfUser" , emailOfUser ) ;
		model.addAttribute( "businessName" , businessName ) ;

		String businessId = ChooseBusinessController.getIdOfBusiness().toString() ;

		String allItems = null ;

		if ( getChangeMade() != 1 ) {
			allItems = new Gson().toJson( purchasesTwoService.findAllItemsWithoutRefreshing( businessId ) ) ;
			return allItems ;
		} else {
			allItems = new Gson().toJson( purchasesTwoService.findAllItemsWithRefreshing( businessId ) ) ;
			return allItems ;
		}
	}

	/*
	 * This API
	 * 
	 * "/api/business/purchasesTwo/{businessName}/{emailOfUser}/allSuppliers"
	 * 
	 * returns all suppliers and sends them to the purchases-two.html <select> tag
	 * 
	 * to give a drop down of all the suppliers so as to help the purchaser choose the supplier they
	 * 
	 * are purchasing from
	 * 
	 */

	@RequestMapping( value = "/api/business/purchasesTwo/{businessName}/{emailOfUser}/allSuppliers" , method = RequestMethod.GET , produces = "application/json" )
	@ResponseBody
	public String findAllSuppliers( Model model , @PathVariable( name = "businessName" ) String businessName , @PathVariable( name = "emailOfUser" ) String emailOfUser ) {

		EmailsOfABusinessLogin emailsOfABusiness = new EmailsOfABusinessLogin() ;
		emailOfUser = ChooseBusinessController.getEmailLoggedIn() ;
		businessName = ChooseBusinessController.getNameOfTheBusiness() ;
		String businessId = ChooseBusinessController.getIdOfBusiness().toString() ;

		model.addAttribute( "emailsOfABusinessForm" , emailsOfABusiness ) ;
		model.addAttribute( "emailOfUser" , emailOfUser ) ;
		model.addAttribute( "businessName" , businessName ) ;

		if ( getChangeMade() != 1 ) {
			return new Gson().toJson( purchasesTwoService.findAllSuppliersNotDirectly( businessId ) ) ;
		} else {
			return new Gson().toJson( purchasesTwoService.findAllSupplierDirectly( businessId ) ) ;
		}

	}

	private boolean itemQuantityNumberFormatTestResult ;
	private boolean unitCostNumberFormatTestResult ;
	private boolean discountReceivedNumberFormatTestResult ;
	private boolean amountPaidNumberFormatTestResult ;
	private boolean totalToBePaidNumberFormatTestResult ;

	public boolean isItemQuantityNumberFormatTestResult() {
		return itemQuantityNumberFormatTestResult ;
	}

	public void setItemQuantityNumberFormatTestResult( boolean itemQuantityNumberFormatTestResult ) {
		this.itemQuantityNumberFormatTestResult = itemQuantityNumberFormatTestResult ;
	}

	public boolean isUnitCostNumberFormatTestResult() {
		return unitCostNumberFormatTestResult ;
	}

	public void setUnitCostNumberFormatTestResult( boolean unitCostNumberFormatTestResult ) {
		this.unitCostNumberFormatTestResult = unitCostNumberFormatTestResult ;
	}

	public boolean isDiscountReceivedNumberFormatTestResult() {
		return discountReceivedNumberFormatTestResult ;
	}

	public void setDiscountReceivedNumberFormatTestResult( boolean discountReceivedNumberFormatTestResult ) {
		this.discountReceivedNumberFormatTestResult = discountReceivedNumberFormatTestResult ;
	}

	public boolean isAmountPaidNumberFormatTestResult() {
		return amountPaidNumberFormatTestResult ;
	}

	public void setAmountPaidNumberFormatTestResult( boolean amountPaidNumberFormatTestResult ) {
		this.amountPaidNumberFormatTestResult = amountPaidNumberFormatTestResult ;
	}

	public boolean isTotalToBePaidNumberFormatTestResult() {
		return totalToBePaidNumberFormatTestResult ;
	}

	public void setTotalToBePaidNumberFormatTestResult( boolean totalToBePaidNumberFormatTestResult ) {
		this.totalToBePaidNumberFormatTestResult = totalToBePaidNumberFormatTestResult ;
	}

	private String numberFormatLanguage ;

	public String getNumberFormatLanguage() {
		return numberFormatLanguage ;
	}

	public void setNumberFormatLanguage( String numberFormatLanguage ) {
		this.numberFormatLanguage = numberFormatLanguage ;
	}

	/*
	 * This tests for the number format of the quantity of the item entered
	 * 
	 */
	@RequestMapping( value = "/api/business/purchasesTwo/{businessName}/{emailOfUser}/checkItemQuantityCorrectness" , method = RequestMethod.POST )
	@ResponseBody
	public String apiToCheckItemQuantityCorrectness( HttpServletRequest request , HttpServletResponse response , @PathVariable( name = "businessName" ) String businessName ,
			@PathVariable( name = "emailOfUser" ) String emailOfUser , @RequestBody PurchasesTwo purhcasesTwo , @RequestParam( "numberFormat" ) String numberFormat ) {

		this.setNumberFormatLanguage( numberFormat ) ;

		String itemQuantityStr = purhcasesTwo.getItemQuantityStr() ;

		boolean testItemQuantityFormat = purchasesTwoNumberVerificationService.verifyNumberFormatOfTheQuantityOfTheItem( itemQuantityStr , numberFormat ) ;
		System.out.println( "qty: " + testItemQuantityFormat ) ;

		BigDecimal itemQuantityBigDecimal = purchasesTwoNumberVerificationService.parseTheUserFormattedNumber( itemQuantityStr , numberFormat , testItemQuantityFormat ) ;
		purchasesTwoCalculationsService.setQuantityOfTheItemToBePurchased( itemQuantityBigDecimal ) ;

		return "/api/business/purchasesTwo/{businessName}/{emailOfUser}/checkItemQuantityCorrectness" ;
	}

	/*
	 * This tests for the number format of the unit cost entered
	 */
	@RequestMapping( value = "/api/business/purchasesTwo/{businessName}/{emailOfUser}/checkUnitCostCorrectness" , method = RequestMethod.POST )
	@ResponseBody
	public String apiToCheckUnitCostCorrectness( HttpServletRequest request , HttpServletResponse response , @PathVariable( name = "businessName" ) String businessName ,
			@PathVariable( name = "emailOfUser" ) String emailOfUser , @RequestBody PurchasesTwo purhcasesTwo , @RequestParam( "numberFormat" ) String numberFormat ) {

		this.setNumberFormatLanguage( numberFormat ) ;

		String unitCostStr = purhcasesTwo.getUnitCostStr() ;

		boolean testUnitCostFormat = purchasesTwoNumberVerificationService.verifyNumberFormatOfTheQuantityOfTheItem( unitCostStr , numberFormat ) ;

		BigDecimal unitCostOfTheItem = purchasesTwoNumberVerificationService.parseTheUserFormattedNumber( unitCostStr , numberFormat , testUnitCostFormat ) ;
		purchasesTwoCalculationsService.setUnitCost( unitCostOfTheItem ) ;

		System.out.println( "unit cost: " + testUnitCostFormat ) ;

		return "/api/business/purchasesTwo/{businessName}/{emailOfUser}/checkUnitCostCorrectness" ;
	}

	/*
	 * This tests for the number format of the amount paid entered
	 */
	@RequestMapping( value = "/api/business/purchasesTwo/{businessName}/{emailOfUser}/checkAmountPaidCorrectness" , method = RequestMethod.POST )
	@ResponseBody
	public String apiToCheckAmountPaidCorrectness( HttpServletRequest request , HttpServletResponse response , @PathVariable( name = "businessName" ) String businessName ,
			@PathVariable( name = "emailOfUser" ) String emailOfUser , @RequestBody PurchasesTwo purhcasesTwo , @RequestParam( "numberFormat" ) String numberFormat ) {

		this.setNumberFormatLanguage( numberFormat ) ;

		String amountPaidStr = purhcasesTwo.getAmountClearedStr() ;

		boolean testAmountPaidFormat = purchasesTwoNumberVerificationService.verifyNumberFormatOfTheQuantityOfTheItem( amountPaidStr , numberFormat ) ;

		BigDecimal amountPaid = purchasesTwoNumberVerificationService.parseTheUserFormattedNumber( amountPaidStr , numberFormat , testAmountPaidFormat ) ;

		purchasesTwoCalculationsService.setAmountPaid( amountPaid ) ;

		System.out.println( "amount paid: " + testAmountPaidFormat ) ;

		return "/api/business/purchasesTwo/{businessName}/{emailOfUser}/checkAmountPaidCorrectness" ;
	}

	/*
	 * This API returns the total to be paid (auto)
	 * 
	 */

	@RequestMapping( value = "/api/business/purchasesTwo/{businessName}/{emailOfUser}/totalToBePaidAuto" , method = RequestMethod.GET , produces = "application/json" )
	@ResponseBody
	public String returnTheTotalToBePaidAuto( @PathVariable( name = "businessName" ) String businessName , @PathVariable( name = "emailOfUser" ) String emailOfUser ) {

		return new Gson().toJson( purchasesTwoCalculationsService.getBigDecimalTotalToBePaidInListFormatForJson( this.getNumberFormatLanguage() ) ) ;

	}

	/*
	 * This API returns the total to be paid (auto)
	 * 
	 */

	@RequestMapping( value = "/api/business/purchasesTwo/{businessName}/{emailOfUser}/getAmountPaid" , method = RequestMethod.GET , produces = "application/json" )
	@ResponseBody
	public String returnTheAmountPaid( @PathVariable( name = "businessName" ) String businessName , @PathVariable( name = "emailOfUser" ) String emailOfUser ) {

		return new Gson().toJson( purchasesTwoCalculationsService.getAmountPaid() ) ;

	}

	/*
	 * 
	 * This method finally saves the purchases two records
	 * 
	 * 
	 */

	@RequestMapping( value = "/api/business/purchasesTwo/{businessName}/{emailOfUser}/savePurchasesTwo" , method = RequestMethod.POST )
	@ResponseBody
	public String apiToSavePurchasesTwo( HttpServletRequest request , HttpServletResponse response , @PathVariable( name = "businessName" ) String businessName ,
			@PathVariable( name = "emailOfUser" ) String emailOfUser , @RequestBody PurchasesTwo purchasesTwo ) {

		String businessId = ChooseBusinessController.getIdOfBusiness().toString() ;

		purchasesTwo.setItemQuantity( purchasesTwoCalculationsService.getQuantityOfTheItemToBePurchased() ) ;
		purchasesTwo.setAmountCleared( purchasesTwoCalculationsService.getAmountPaid() ) ;
		purchasesTwo.setTotalCostAuto( purchasesTwoCalculationsService.getTotalToBePaidAuto() ) ;
		purchasesTwo.setUnitCost( purchasesTwoCalculationsService.getUnitCost() ) ;

		Timestamp timestamp = Timestamp.valueOf( LocalDateTime.now() ) ;

		String dateClient = null ;

		if ( purchasesTwo.getDateClient() == null || purchasesTwo.getDateClient() == "" ) {
			dateClient = timestamp.toString() ;
			System.out.println( "date client 1: " + dateClient ) ;
			purchasesTwo.setDateClient( dateClient );
		} else {
			dateClient = purchasesTwo.getDateClient() ;
			System.out.println( "date client 2: " + dateClient ) ;
			purchasesTwo.setDateClient( dateClient );
		}

		System.out.println( "date client: " + dateClient ) ;

		if ( purchasesTwo.getDiscountReceived() == null ) {
			purchasesTwo.setDiscountReceived( BigDecimal.ZERO ) ;
		}

		if ( purchasesTwo.getTotalCostManual() == null ) {
			purchasesTwo.setTotalCostManual( BigDecimal.ZERO ) ;
		}

		purchasesTwo.setBalanceYetToBePaid( purchasesTwo.getTotalCostAuto().subtract( purchasesTwo.getAmountCleared() ) ) ;

		purchasesTwo.setTransactionId( purchasesTwoService.getTheHibernatedRequiredTransactionId() ) ;

		BigDecimal itemId = purchasesTwo.getItemId() ;

		int result = 0 ;

		result = purchasesTwoService.newPurchases( purchasesTwo , businessId ) ;

		List < GeneralJournal > generalJournalToEnhanceBatch = new ArrayList <>() ;
		List < Ledger > ledgersToEnhanceBatch = new ArrayList <>() ;

		/*
		 * constants
		 * 
		 */
		String narration = null ;
		BigDecimal transactionId = purchasesTwo.getTransactionId() ;

		/*
		 * Some times the credit purchase is 100% while at times it is partly paid off.
		 * 
		 * Putting this into consideration, if there is part payment for the goods purchased then these
		 * follow
		 * 
		 * 
		 */

		if ( purchasesTwo.isCredit() == true ) {

			if ( purchasesTwo.getNarration() == null || purchasesTwo.getNarration() == "" ) {
				narration = "being credit purchase of ".concat( purchasesTwo.getItemId().toString() ) ;
			} else {
				narration = purchasesTwo.getNarration() ;
			}

			/*
			 * if the credit purchases is 100%. i.e without any initial installment paid
			 */

			// beginning 100% credit
			if ( purchasesTwo.getAmountCleared().equals( BigDecimal.ZERO ) ) {

				/*
				 * these values go to the general journal
				 */

				GeneralJournalHibernationValues generalJournalHibernationValuesHundredPercentCredit = new GeneralJournalHibernationValues() ;

				generalJournalHibernationValuesHundredPercentCredit.setDateClient( dateClient ) ;
				generalJournalHibernationValuesHundredPercentCredit.setParticularCr( purchasesTwo.getSupplierId().toString().concat( " a/c" ) ) ;
				generalJournalHibernationValuesHundredPercentCredit.setParticularDr( "purchases a/c" ) ;

				generalJournalHibernationValuesHundredPercentCredit.setCrAmount( purchasesTwo.getTotalCostAuto() ) ;
				generalJournalHibernationValuesHundredPercentCredit.setDrAmount( purchasesTwo.getTotalCostAuto() ) ;

				generalJournalHibernationValuesHundredPercentCredit.setNarration( narration ) ;

				generalJournalHibernationValuesHundredPercentCredit.setItemId( itemId ) ;
				generalJournalHibernationValuesHundredPercentCredit.setTransactionId( transactionId ) ;

				generalJournalToEnhanceBatch.add( generalJournalHibernationValuesHundredPercentCredit ) ;

				/*
				 * these values are transfered from the general journal to the LEDGERS
				 */

				// ledger of the purchase ---- or the purchases a/c
				/*
				 * ======ledger of the purchase ---- or the purchases a/c
				 * 
				 * ---------- the name of the ledger is purchases a/c
				 * 
				 * ---------- the date remains the same
				 * 
				 * ---------- the ledger name makes the name of the account
				 * 
				 * ---------- the DR PARTICULAR is the name of the purchases a/c got from the general journal
				 * 
				 * ---------- the DR AMOUNT is the DR AMOUNT of the general journal
				 * 
				 * ---------- the CR PARTICULAR remains NULL or EMPTY
				 * 
				 * ---------- the CR AMOUNT remains 0
				 */
				Ledger ledgerPurchases = new Ledger() ;
				ledgerPurchases.setDateClient( dateClient ) ;
				ledgerPurchases.setLedgerName( generalJournalHibernationValuesHundredPercentCredit.getParticularDr() ) ;// gets the name of the purchases a/c
				ledgerPurchases.setDrParticular( generalJournalHibernationValuesHundredPercentCredit.getParticularCr() ) ;
				ledgerPurchases.setCrParticular( null ) ;
				ledgerPurchases.setDrAmount( generalJournalHibernationValuesHundredPercentCredit.getDrAmount() ) ;
				ledgerPurchases.setCrAmount( BigDecimal.ZERO ) ;
				ledgerPurchases.setItemId( itemId ) ;
				ledgerPurchases.setTransactionId( transactionId ) ;

				ledgersToEnhanceBatch.add( ledgerPurchases ) ;

				// ledger of the supplier ---- or the supplier a/c
				/*
				 * ======ledger of the supplier ---- or the supplier a/c
				 * 
				 * ---------- the name of the ledger is name of the SUPPLIER
				 * 
				 * ---------- here the supplier ID is used as the LEDGER/ACCOUNT name
				 * 
				 * ---------- the date remains the same
				 * 
				 * ---------- the ledger name makes the name of the account
				 * 
				 * ---------- the DR PARTICULAR remains null or EMPTY
				 * 
				 * ---------- the DR AMOUNT remains 0
				 * 
				 * ---------- the CR PARTICULAR is the name of the purchases a/c got from the general journal
				 * 
				 * ---------- the CR AMOUNT is the whole amount got from DR AMOUNT of purchases a/c
				 */
				Ledger ledgerSupplier = new Ledger() ;
				ledgerSupplier.setDateClient( dateClient ) ;
				ledgerSupplier.setLedgerName( generalJournalHibernationValuesHundredPercentCredit.getParticularCr() ) ;// gets the name supplier a/c
				ledgerSupplier.setCrParticular( generalJournalHibernationValuesHundredPercentCredit.getParticularDr() ) ;
				ledgerSupplier.setDrParticular( null ) ;

				// getting how much balance is to be paid to the supplier
				BigDecimal balanceToBePaidToSupplier = purchasesTwo.getTotalCostAuto().subtract( purchasesTwo.getAmountCleared() ) ;

				ledgerPurchases.setDrAmount( BigDecimal.ZERO ) ;
				ledgerPurchases.setCrAmount( balanceToBePaidToSupplier ) ;
				ledgerSupplier.setDrAmount( BigDecimal.ZERO ) ;
				ledgerSupplier.setCrAmount( balanceToBePaidToSupplier ) ;
				ledgerSupplier.setItemId( itemId ) ;
				ledgerSupplier.setTransactionId( transactionId ) ;

				ledgersToEnhanceBatch.add( ledgerSupplier ) ;

			} // ending 100% credit

			/*
			 * if the credit purchase is not 100%. i.e some initial installment was paid
			 */
			else if ( purchasesTwo.getAmountCleared().doubleValue() > BigDecimal.ZERO.doubleValue() ) {

				/*
				 * debit purchase with the whole amount (both paid amount and the balance to be paid), and credit
				 * the supplier with the balance to be paid
				 */

				// ******** this is when an installment is paid for the purchase of the good /commodity *****//

				// this is to record the purchase as a DEBIT and the supplier as a CREDIT (only the balance to be
				// paid is credited
				GeneralJournalHibernationValues generalJournalHibernationValuesLessThanHundredPercentCredit_withAnInstallmentPaid_creditingSupplier = new GeneralJournalHibernationValues() ;
				generalJournalHibernationValuesLessThanHundredPercentCredit_withAnInstallmentPaid_creditingSupplier.setDateClient( dateClient ) ;
				generalJournalHibernationValuesLessThanHundredPercentCredit_withAnInstallmentPaid_creditingSupplier
						.setParticularCr( "".concat( purchasesTwo.getSupplierId().toString() ).concat( " a/c" ) ) ;
				generalJournalHibernationValuesLessThanHundredPercentCredit_withAnInstallmentPaid_creditingSupplier.setParticularDr( "purchases a/c" ) ;

				generalJournalHibernationValuesLessThanHundredPercentCredit_withAnInstallmentPaid_creditingSupplier.setNarration( narration ) ;

				generalJournalHibernationValuesLessThanHundredPercentCredit_withAnInstallmentPaid_creditingSupplier.setDrAmount( purchasesTwo.getTotalCostAuto() ) ;

				generalJournalHibernationValuesLessThanHundredPercentCredit_withAnInstallmentPaid_creditingSupplier.setCrAmount( purchasesTwo.getBalanceYetToBePaid() ) ;

				generalJournalHibernationValuesLessThanHundredPercentCredit_withAnInstallmentPaid_creditingSupplier.setTransactionId( transactionId ) ;
				generalJournalHibernationValuesLessThanHundredPercentCredit_withAnInstallmentPaid_creditingSupplier.setItemId( itemId ) ;

				generalJournalToEnhanceBatch.add( generalJournalHibernationValuesLessThanHundredPercentCredit_withAnInstallmentPaid_creditingSupplier ) ;

				// this is to record the cash only as a CREDIT since the supplier and the purchase A/Cs have already
				// been recorded.
				// this takes the paid value as the amount to be recorded.
				// In this case, the opposite value will be set to zero
				GeneralJournalHibernationValues generalJournalHibernationValuesLessThanHundredPercentCredit_withAnInstallmentPaid_creditingCash = new GeneralJournalHibernationValues() ;
				generalJournalHibernationValuesLessThanHundredPercentCredit_withAnInstallmentPaid_creditingCash.setParticularCr( "cash a/c" ) ;
				generalJournalHibernationValuesLessThanHundredPercentCredit_withAnInstallmentPaid_creditingCash.setParticularDr( null ) ;
				generalJournalHibernationValuesLessThanHundredPercentCredit_withAnInstallmentPaid_creditingCash.setDateClient( dateClient ) ;
				generalJournalHibernationValuesLessThanHundredPercentCredit_withAnInstallmentPaid_creditingCash.setNarration( narration ) ;
				generalJournalHibernationValuesLessThanHundredPercentCredit_withAnInstallmentPaid_creditingCash.setDrAmount( BigDecimal.ZERO ) ;
				generalJournalHibernationValuesLessThanHundredPercentCredit_withAnInstallmentPaid_creditingCash.setCrAmount( purchasesTwo.getAmountCleared() ) ;
				generalJournalHibernationValuesLessThanHundredPercentCredit_withAnInstallmentPaid_creditingCash.setTransactionId( transactionId ) ;
				generalJournalHibernationValuesLessThanHundredPercentCredit_withAnInstallmentPaid_creditingCash.setItemId( itemId ) ;

				generalJournalToEnhanceBatch.add( generalJournalHibernationValuesLessThanHundredPercentCredit_withAnInstallmentPaid_creditingCash ) ;

				/*
				 * 
				 * WHEN AN INSTALLMENT IS PAID, CASH LEDGER (A/C) IS AFFECTED AND THE SUPPLIER A/C IS AFFECTED
				 * 
				 * the amount paid affects the cash a/c
				 * 
				 * the balance to be paid affects the supplier a/c
				 * 
				 * 
				 */

				/*
				 * these values are transfered from the general journal to the LEDGERS
				 */

				// ledger of the cash ---- or the cash a/c
				/*
				 * ======ledger of the cash ---- or the cash a/c
				 * 
				 * ---------- the name of the ledger is name of the CASH A/C
				 * 
				 * ---------- the date remains the same
				 * 
				 * ---------- the ledger name makes the name of the account
				 * 
				 * ---------- the DR PARTICULAR remains null or EMPTY
				 * 
				 * ---------- the DR AMOUNT remains 0
				 * 
				 * ---------- the CR PARTICULAR is the name of the purchases a/c got from the general journal
				 * 
				 * ---------- the CR AMOUNT is the paid amount by the supplier
				 */
				Ledger ledgerCash = new Ledger() ;
				ledgerCash.setDateClient( dateClient ) ;
				ledgerCash.setLedgerName( "cash a/c" ) ;
				ledgerCash.setCrParticular(

						generalJournalHibernationValuesLessThanHundredPercentCredit_withAnInstallmentPaid_creditingCash.getParticularDr() ) ;

				ledgerCash.setDrParticular( null ) ;

				ledgerCash.setDrAmount( BigDecimal.ZERO ) ;
				ledgerCash.setCrAmount( purchasesTwo.getAmountCleared() ) ;
				ledgerCash.setItemId( itemId ) ;
				ledgerCash.setTransactionId( transactionId ) ;

				ledgersToEnhanceBatch.add( ledgerCash ) ;

				// ledger of the supplier ---- or the supplier a/c
				/*
				 * ======ledger of the supplier ---- or the supplier a/c
				 * 
				 * ---------- the name of the ledger is name of the SUPPLIER A/C which is the ID of the supplier
				 * 
				 * ---------- the date remains the same
				 * 
				 * ---------- the ledger name makes the name of the account
				 * 
				 * ---------- the DR PARTICULAR remains null or EMPTY
				 * 
				 * ---------- the DR AMOUNT remains 0
				 * 
				 * ---------- the CR PARTICULAR is the name of the purchases a/c got from the general journal
				 * 
				 * ---------- the CR AMOUNT is the balance left to be paid to the supplier
				 */

				// getting how much balance is to be paid to the supplier
				BigDecimal balanceToBePaidToSupplier = purchasesTwo.getTotalCostAuto().subtract( purchasesTwo.getAmountCleared() ) ;

				Ledger ledgerSupplier = new Ledger() ;
				ledgerSupplier.setDateClient( dateClient ) ;
				ledgerSupplier.setLedgerName(

						generalJournalHibernationValuesLessThanHundredPercentCredit_withAnInstallmentPaid_creditingSupplier.getParticularCr() ) ;

				ledgerSupplier.setCrParticular(

						generalJournalHibernationValuesLessThanHundredPercentCredit_withAnInstallmentPaid_creditingCash.getParticularDr() ) ;

				ledgerSupplier.setDrParticular( null ) ;

				ledgerSupplier.setDrAmount( BigDecimal.ZERO ) ;
				ledgerSupplier.setCrAmount( balanceToBePaidToSupplier ) ;
				ledgerSupplier.setItemId( itemId ) ;
				ledgerSupplier.setTransactionId( transactionId ) ;

				ledgersToEnhanceBatch.add( ledgerSupplier ) ;

			}

		} /* if credit is true ends here */

		/***********************************************************************************************************************************************/
		/*************************************
		 * working out the cash purchase result
		 ***************************************/
		/***********************************************************************************************************************************************/
		else {

			if ( purchasesTwo.getNarration() == null || purchasesTwo.getNarration() == "" ) {
				narration = "being cash purchase of ".concat( itemId.toString() ) ;
			} else {
				narration = purchasesTwo.getNarration() ;
			}

			/*
			 * when the purchase is made on cash, then the following has to be followed
			 */

			GeneralJournalHibernationValues generalJournalHibernationValuesForCashPurchaseMade = new GeneralJournalHibernationValues() ;
			generalJournalHibernationValuesForCashPurchaseMade.setDateClient( dateClient ) ;
			generalJournalHibernationValuesForCashPurchaseMade.setParticularDr( "purchases a/c" ) ;
			generalJournalHibernationValuesForCashPurchaseMade.setParticularCr( "cash  a/c" ) ;
			generalJournalHibernationValuesForCashPurchaseMade.setNarration( narration ) ;
			generalJournalHibernationValuesForCashPurchaseMade.setCrAmount( purchasesTwo.getAmountCleared() ) ;
			generalJournalHibernationValuesForCashPurchaseMade.setDrAmount( purchasesTwo.getAmountCleared() ) ;
			generalJournalHibernationValuesForCashPurchaseMade.setItemId( itemId ) ;
			generalJournalHibernationValuesForCashPurchaseMade.setTransactionId( transactionId ) ;

			generalJournalToEnhanceBatch.add( generalJournalHibernationValuesForCashPurchaseMade ) ;

			// ledger of the cash ---- or the cash a/c
			/*
			 * ======ledger of the cash ---- or the cash a/c
			 * 
			 * ---------- the name of the ledger is name of the CASH A/C
			 * 
			 * ---------- the date remains the same
			 * 
			 * ---------- the ledger name makes the name of the account
			 * 
			 * ---------- the DR PARTICULAR remains null or EMPTY
			 * 
			 * ---------- the DR AMOUNT remains 0
			 * 
			 * ---------- the CR PARTICULAR is the name of the purchases a/c got from the general journal
			 * 
			 * ---------- the CR AMOUNT is the paid amount by the supplier
			 */
			Ledger ledgerCash = new Ledger() ;
			ledgerCash.setDateClient( dateClient ) ;
			ledgerCash.setLedgerName( "cash a/c" ) ;
			ledgerCash.setCrParticular( "purchases a/c" ) ;

			ledgerCash.setDrParticular( null ) ;

			ledgerCash.setDrAmount( BigDecimal.ZERO ) ;
			ledgerCash.setCrAmount( purchasesTwo.getAmountCleared() ) ;
			ledgerCash.setItemId( itemId ) ;
			ledgerCash.setTransactionId( transactionId ) ;

			ledgersToEnhanceBatch.add( ledgerCash ) ;
		}

		System.out.println( "purc---: " + purchasesTwo ) ;

		if ( result == 1 ) {
			setChangeMade( 1 ) ;

		}

		/*
		 * this will take the list of general journal entries to the general journal controller
		 */
		// GeneralJournalHelperToPickFromOtherClasses generalJournalHelperToPickFromOtherClasses = new
		// GeneralJournalHelperToPickFromOtherClasses() ;
		// generalJournalHelperToPickFromOtherClasses.setGeneralJournalsToBeTakenToTheGenJournalController(
		// generalJournalToEnhanceBatch ) ;

		/*
		 * using a non URL method to save the general journal list
		 */

		GeneralJournalController.anotherPostMethodToEvadeTriggeringFromTheWeb( generalJournalToEnhanceBatch , businessId ) ;
		LedgerController.newLegder( ledgersToEnhanceBatch , businessId ) ;

		return "/api/business/purchasesTwo/{businessName}/{emailOfUser}/savePurchasesTwo" ;
	}

}