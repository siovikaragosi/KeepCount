
package com.keepcount.dao.purchases ;

import java.math.BigDecimal ;
import java.sql.CallableStatement ;
import java.sql.Connection ;
import java.sql.PreparedStatement ;
import java.sql.ResultSet ;
import java.text.NumberFormat ;
import java.util.ArrayList ;
import java.util.List ;
import java.util.Locale ;

import com.keepcount.dao.dbutils.DBUtils ;
import com.keepcount.model.purchases.Purchases ;

public class PurchasesDAORetrieval {

	private static int procExisst = 0 ;

	public static int getProcExisst() {
		return procExisst ;
	}

	public static void setProcExisst( int procExisst ) {
		PurchasesDAORetrieval.procExisst = procExisst ;
	}

	private static void checkProcExitence( String businessId ) throws Exception {
		String sql = "SELECT ROUTINE_NAME FROM INFORMATION_SCHEMA.ROUTINES WHERE ROUTINE_TYPE= \"PROCEDURE\"  AND ROUTINE_SCHEMA=\"kc\"" ;

		List < String > list = new ArrayList <>() ;
		PreparedStatement st = null ;
		ResultSet rs = null ;
		Connection connection = DBUtils.getConn() ;

		try {
			st = connection.prepareStatement( sql ) ;
			rs = st.executeQuery() ;
			while ( rs.next() ) {
				String proc = rs.getString( "ROUTINE_NAME" ) ;
				list.add( proc ) ;
			}

			if ( list.contains( "get_all_purchases_".concat( businessId ) ) ) {
				setProcExisst( 1 ) ;
			} else {
				setProcExisst( 0 ) ;
			}

		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( rs , null , st , connection ) ;
		}

	}

	public static void main( String [ ] args ) throws Exception {
		checkProcExitence( "16" ) ;
	}

	public static String create_within_findAllPurchases( String businessId ) {
		return "CREATE PROCEDURE get_all_purchases_".concat( businessId ).concat( "()" ).concat( " BEGIN " ).concat( "SELECT * FROM purchases_".concat( businessId ) ).concat( ";" )
				.concat( " END" ) ;
	}

	public static void createProcedureToFindAllPurchases( String businessId ) throws Exception {
		checkProcExitence( businessId ) ;

		PreparedStatement ps = null ;
		Connection connection = DBUtils.getConn() ;
		try {

			ps = connection.prepareStatement( create_within_findAllPurchases( businessId ) ) ;

			if ( businessId != null ) {

				if ( getProcExisst() == 0 ) {
					ps.executeUpdate() ;
				}

			}

		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( null , null , ps , connection ) ;
		}
	}

	/**
	 * @author luqman
	 * 
	 *         These are countries with their thousand separators and decimal places
	 *
	 *         ***************************************************************************************************************************************
	 *         * 1 - Canada (English and French) =========== 4 294 967 295,000 ***
	 *         ***************************************************************************************************************************************
	 *
	 **
	 *         ***************************************************************************************************************************************
	 *         * 2 - Danish ================================ 4 294 967 295,000 ***
	 *         ***************************************************************************************************************************************
	 *
	 *
	 **
	 *         ***************************************************************************************************************************************
	 *         * 3 - Finnish =============================== 4 294 967 295,000 ***
	 *         ***************************************************************************************************************************************
	 *
	 **
	 *         ***************************************************************************************************************************************
	 *         * 4 - French ================================ 4 294 967 295,000 ***
	 *         ***************************************************************************************************************************************
	 *
	 **
	 *         ***************************************************************************************************************************************
	 *         * 5 - German ================================ 4.294.967.295,000 ***
	 *         ***************************************************************************************************************************************
	 *
	 **
	 *         ***************************************************************************************************************************************
	 *         * 6 - Italian =============================== 4.294.967.295,000 ***
	 *         ***************************************************************************************************************************************
	 *
	 **
	 *         ***************************************************************************************************************************************
	 *         * 7 - Norwegian ============================= 4.294.967.295,000 ***
	 *         ***************************************************************************************************************************************
	 *
	 **
	 *         ***************************************************************************************************************************************
	 *         * 8 - Spanish =============================== 4.294.967.295,000 ***
	 *         ***************************************************************************************************************************************
	 *
	 **
	 *         ***************************************************************************************************************************************
	 *         * 9 - Swedish =============================== 4 294 967 295,000 ***
	 *         ***************************************************************************************************************************************
	 *
	 **
	 *         ***************************************************************************************************************************************
	 *         * 10 - GB-English =========================== 4,294,967,295.00 ***
	 *         ***************************************************************************************************************************************
	 *
	 **
	 *         ***************************************************************************************************************************************
	 *         * 11 - US-English =========================== 4,294,967,295.00 ***
	 *         ***************************************************************************************************************************************
	 **
	 *
	 *         ***************************************************************************************************************************************
	 *         * 11 - Thai ================================= 4,294,967,295.00 ***
	 *         ***************************************************************************************************************************************
	 *
	 */

	/**
	 * @author luqman
	 *
	 *         CURRENCY (INTERNATIONAL MONETARY CONVENTION).
	 ** 
	 *         ***************************************************************************************************************************************
	 *         * ****** LOCALE ******||****** CURRENCY ******||****** EXAMPLE ******
	 *         ***************************************************************************************************************************************
	 *         ***************************************************************************************************************************************
	 *         * Canadian (English) || Dollar ($) || $1 234.56
	 *         ***************************************************************************************************************************************
	 **
	 *         ***************************************************************************************************************************************
	 *         * Canadian (French) || Dollar ($) || 1 234.56$
	 *         ***************************************************************************************************************************************
	 **
	 *         ***************************************************************************************************************************************
	 *         * Danish || Kroner (kr) || kr 1.234,56
	 *         ***************************************************************************************************************************************
	 **
	 *         ***************************************************************************************************************************************
	 *         * Finnish || Markka (mk) || 1.234,56 mk
	 *         ***************************************************************************************************************************************
	 * 
	 *         ***************************************************************************************************************************************
	 *         * French || Franc (F) || 1 234,56 F
	 *         ***************************************************************************************************************************************
	 **
	 *         ***************************************************************************************************************************************
	 *         * German || Deutche Mark (DM) || DM 1 234.56
	 *         ***************************************************************************************************************************************
	 **
	 *         ***************************************************************************************************************************************
	 *         * Italian || Lira (L) || L1.234,56
	 *         ***************************************************************************************************************************************
	 **
	 *         ***************************************************************************************************************************************
	 *         * Japanese || Yen () || 1,234.56 Yen
	 *         ***************************************************************************************************************************************
	 * 
	 *         ***************************************************************************************************************************************
	 *         * Norwegian || Krone (kr) || kr 1.234,56
	 *         ***************************************************************************************************************************************
	 * 
	 *         ***************************************************************************************************************************************
	 *         * Spanish || Peseta (Pts) || 1.234,56Pts
	 *         ***************************************************************************************************************************************
	 * 
	 *         ***************************************************************************************************************************************
	 *         * Swedish || Krona (kr) || 1.234,56 kr
	 *         ***************************************************************************************************************************************
	 * 
	 *         ***************************************************************************************************************************************
	 *         * GB-English || Pound || 31,234.56 pounds
	 *         ***************************************************************************************************************************************
	 * 
	 *         ***************************************************************************************************************************************
	 *         * US-English || Dollars ($) || $1,234.56
	 *         ***************************************************************************************************************************************
	 * 
	 *         ***************************************************************************************************************************************
	 *         * Thai || Baht || 1,234.56 Baht
	 *         ***************************************************************************************************************************************
	 * 
	 **
	 **
	 **
	 **
	 **
	 **
	 **
	 *
	 **
	 */

	public static List < Purchases > findAllPurchases( String businessId , String numberFormat ) throws Exception {
		createProcedureToFindAllPurchases( businessId ) ;
		System.out.println( "reached.." ) ;
		List < Purchases > purchasesS = new ArrayList <>() ;
		List < Purchases > purchasesSEnglish = new ArrayList <>() ;
		List < Purchases > purchasesSGerman = new ArrayList <>() ;
		List < Purchases > purchasesSFrench = new ArrayList <>() ;
		List < Purchases > purchasesReturned = new ArrayList <>() ;
		ResultSet rs = null ;
		CallableStatement cs = null ;
		Connection connection = DBUtils.getConn() ;
		try {
			cs = connection.prepareCall( "{CALL get_all_purchases_".concat( businessId ).concat( "}" ) ) ;
			rs = cs.executeQuery() ;

			while ( rs.next() ) {
				BigDecimal id = rs.getBigDecimal( "id" ) ;
				String dateServer = rs.getString( "date_server" ) ;/* 1 */
				String dateClient = rs.getString( "date_client" ) ;/* 2 */
				String item = rs.getString( "item" ) ;/* 3 */
				String itemSubCategory = rs.getString( "item_sub_category" ) ;/* 4 */
				BigDecimal itemQuantity = rs.getBigDecimal( "item_quantity" ) ;/* 5 */
				BigDecimal itemPricePerUnit = rs.getBigDecimal( "item_price_per_unit" ) ;/* 6 */
				BigDecimal itemTotalManualCost = rs.getBigDecimal( "item_total_manual_cost" ) ;/* 7 */
				BigDecimal itemTotalAutomaticCost = rs.getBigDecimal( "item_total_automatic_cost" ) ;/* 8 */
				boolean credit = rs.getBoolean( "credit" ) ;/* 9 */
				String clearedBy = rs.getString( "cleared_by" ) ;/* 10 */
				BigDecimal amountPaid = rs.getBigDecimal( "amount_paid" ) ;/* 11 */
				BigDecimal installmentPaid = rs.getBigDecimal( "installment_paid" ) ;/* 12 */
				BigDecimal installmentBalance = rs.getBigDecimal( "installment_balance" ) ;/* 13 */
				String paymentMethod = rs.getString( "payment_method" ) ;/* 14 */
				String supplierName = rs.getString( "supplier_name" ) ;/* 15 */

				Purchases purchases = new Purchases( id , dateServer , dateClient , item , itemSubCategory , itemQuantity , itemPricePerUnit , itemTotalManualCost ,
						itemTotalAutomaticCost , credit , clearedBy , amountPaid , installmentPaid , installmentBalance , paymentMethod , supplierName ) ;

				purchasesS.add( purchases ) ;

			}

			if ( numberFormat.equals( "English" ) ) {
				purchasesSEnglish = formattedPurchasesEnglish( purchasesS ) ;
				purchasesReturned = purchasesSEnglish ;
			} else if ( numberFormat.equals( "German" ) ) {
				purchasesSGerman = formattedPurchasesGerman( purchasesS ) ;
				purchasesReturned = purchasesSGerman ;
			} else if ( numberFormat.equals( "French" ) ) {
				purchasesSFrench = formattedPurchasesFrench( purchasesS ) ;
				purchasesReturned = purchasesSFrench ;
			}

		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( rs , cs , null , connection ) ;
		}

		System.out.println( "Purch returned dao: " + purchasesReturned ) ;

		return purchasesReturned ;
	}

	private static List < Purchases > formattedPurchasesEnglish( List < Purchases > list ) {

		List < Purchases > purchasesReturned = new ArrayList <>() ;

		NumberFormat format = NumberFormat.getNumberInstance( Locale.ENGLISH ) ;

		for ( Purchases purchases : list ) {
			BigDecimal id = purchases.getId() ;
			String dateServer = purchases.getDateServer() ;
			String dateClient = purchases.getDateClient() ;
			String item = purchases.getItem() ;
			String subCat = purchases.getItemSubCategory() ;
			boolean credit = purchases.isCredit() ;
			String clearedBy = purchases.getClearedBy() ;
			String paymentMethod = purchases.getPaymentMethod() ;
			String supplierName = purchases.getSupplierName() ;

			String quantityFormatted = format.format( purchases.getItemQuantity() ) ;
			BigDecimal quantityOriginal = purchases.getItemQuantity() ;

			String itemPricePerUnitFormatted = format.format( purchases.getItemPricePerUnit() ) ;
			BigDecimal itemPriceUnitOriginal = purchases.getItemPricePerUnit() ;

			String itemTotalManualCostFormatted = format.format( purchases.getItemTotalManualCost() ) ;
			BigDecimal itemTotalManualOriginal = purchases.getItemTotalManualCost() ;

			String itemTotalAutoCostFormatted = format.format( purchases.getItemTotalAutomaticCost() ) ;
			BigDecimal itemTotalAutoOriginal = purchases.getItemTotalAutomaticCost() ;

			String amountPaidFormatted = format.format( purchases.getAmountPaid() ) ;
			BigDecimal amountPaidOriginal = purchases.getAmountPaid() ;

			String installmentPaidFormatted = format.format( purchases.getInstallmentPaid() ) ;
			BigDecimal installmentPaidOriginal = purchases.getInstallmentPaid() ;

			String installmentBalanceFormatted = format.format( purchases.getInstallmentBalance() ) ;
			BigDecimal installmentBalanceOriginal = purchases.getInstallmentBalance() ;

			Purchases purchases2 = new Purchases( id , dateServer , dateClient , item , subCat , quantityOriginal , quantityFormatted , itemPriceUnitOriginal ,
					itemPricePerUnitFormatted , itemTotalManualOriginal , itemTotalManualCostFormatted , itemTotalAutoOriginal , itemTotalAutoCostFormatted , credit , clearedBy ,
					amountPaidOriginal , amountPaidFormatted , installmentPaidOriginal , installmentPaidFormatted , installmentBalanceOriginal , installmentBalanceFormatted ,
					paymentMethod , supplierName ) ;

			purchasesReturned.add( purchases2 ) ;

		}

		return purchasesReturned ;
	}

	private static List < Purchases > formattedPurchasesGerman( List < Purchases > list ) {

		List < Purchases > purchasesReturned = new ArrayList <>() ;

		NumberFormat format = NumberFormat.getNumberInstance( Locale.GERMAN ) ;

		for ( Purchases purchases : list ) {
			BigDecimal id = purchases.getId() ;
			String dateServer = purchases.getDateServer() ;
			String dateClient = purchases.getDateClient() ;
			String item = purchases.getItem() ;
			String subCat = purchases.getItemSubCategory() ;
			boolean credit = purchases.isCredit() ;
			String clearedBy = purchases.getClearedBy() ;
			String paymentMethod = purchases.getPaymentMethod() ;
			String supplierName = purchases.getSupplierName() ;

			String quantityFormatted = format.format( purchases.getItemQuantity() ) ;
			BigDecimal quantityOriginal = purchases.getItemQuantity() ;

			String itemPricePerUnitFormatted = format.format( purchases.getItemPricePerUnit() ) ;
			BigDecimal itemPriceUnitOriginal = purchases.getItemPricePerUnit() ;

			String itemTotalManualCostFormatted = format.format( purchases.getItemTotalManualCost() ) ;
			BigDecimal itemTotalManualOriginal = purchases.getItemTotalManualCost() ;

			String itemTotalAutoCostFormatted = format.format( purchases.getItemTotalAutomaticCost() ) ;
			BigDecimal itemTotalAutoOriginal = purchases.getItemTotalAutomaticCost() ;

			String amountPaidFormatted = format.format( purchases.getAmountPaid() ) ;
			BigDecimal amountPaidOriginal = purchases.getAmountPaid() ;

			String installmentPaidFormatted = format.format( purchases.getInstallmentPaid() ) ;
			BigDecimal installmentPaidOriginal = purchases.getInstallmentPaid() ;

			String installmentBalanceFormatted = format.format( purchases.getInstallmentBalance() ) ;
			BigDecimal installmentBalanceOriginal = purchases.getInstallmentBalance() ;

			Purchases purchases2 = new Purchases( id , dateServer , dateClient , item , subCat , quantityOriginal , quantityFormatted , itemPriceUnitOriginal ,
					itemPricePerUnitFormatted , itemTotalManualOriginal , itemTotalManualCostFormatted , itemTotalAutoOriginal , itemTotalAutoCostFormatted , credit , clearedBy ,
					amountPaidOriginal , amountPaidFormatted , installmentPaidOriginal , installmentPaidFormatted , installmentBalanceOriginal , installmentBalanceFormatted ,
					paymentMethod , supplierName ) ;

			purchasesReturned.add( purchases2 ) ;

		}
		return purchasesReturned ;
	}

	private static List < Purchases > formattedPurchasesFrench( List < Purchases > list ) {

		List < Purchases > purchasesReturned = new ArrayList <>() ;

		NumberFormat format = NumberFormat.getNumberInstance( Locale.FRENCH ) ;

		for ( Purchases purchases : list ) {
			BigDecimal id = purchases.getId() ;
			String dateServer = purchases.getDateServer() ;
			String dateClient = purchases.getDateClient() ;
			String item = purchases.getItem() ;
			String subCat = purchases.getItemSubCategory() ;
			boolean credit = purchases.isCredit() ;
			String clearedBy = purchases.getClearedBy() ;
			String paymentMethod = purchases.getPaymentMethod() ;
			String supplierName = purchases.getSupplierName() ;

			String quantityFormatted = format.format( purchases.getItemQuantity() ) ;
			BigDecimal quantityOriginal = purchases.getItemQuantity() ;

			String itemPricePerUnitFormatted = format.format( purchases.getItemPricePerUnit() ) ;
			BigDecimal itemPriceUnitOriginal = purchases.getItemPricePerUnit() ;

			String itemTotalManualCostFormatted = format.format( purchases.getItemTotalManualCost() ) ;
			BigDecimal itemTotalManualOriginal = purchases.getItemTotalManualCost() ;

			String itemTotalAutoCostFormatted = format.format( purchases.getItemTotalAutomaticCost() ) ;
			BigDecimal itemTotalAutoOriginal = purchases.getItemTotalAutomaticCost() ;

			String amountPaidFormatted = format.format( purchases.getAmountPaid() ) ;
			BigDecimal amountPaidOriginal = purchases.getAmountPaid() ;

			String installmentPaidFormatted = format.format( purchases.getInstallmentPaid() ) ;
			BigDecimal installmentPaidOriginal = purchases.getInstallmentPaid() ;

			String installmentBalanceFormatted = format.format( purchases.getInstallmentBalance() ) ;
			BigDecimal installmentBalanceOriginal = purchases.getInstallmentBalance() ;

			Purchases purchases2 = new Purchases( id , dateServer , dateClient , item , subCat , quantityOriginal , quantityFormatted , itemPriceUnitOriginal ,
					itemPricePerUnitFormatted , itemTotalManualOriginal , itemTotalManualCostFormatted , itemTotalAutoOriginal , itemTotalAutoCostFormatted , credit , clearedBy ,
					amountPaidOriginal , amountPaidFormatted , installmentPaidOriginal , installmentPaidFormatted , installmentBalanceOriginal , installmentBalanceFormatted ,
					paymentMethod , supplierName ) ;

			purchasesReturned.add( purchases2 ) ;

		}

		return purchasesReturned ;
	}

	private static Purchases formattedPurchasesCanada_French( Purchases purchases ) {

		NumberFormat format = NumberFormat.getNumberInstance( Locale.CANADA_FRENCH ) ;

		BigDecimal id = purchases.getId() ;
		String dateServer = purchases.getDateServer() ;
		String dateClient = purchases.getDateClient() ;
		String item = purchases.getItem() ;
		String subCat = purchases.getItemSubCategory() ;
		boolean credit = purchases.isCredit() ;
		String clearedBy = purchases.getClearedBy() ;
		String paymentMethod = purchases.getPaymentMethod() ;
		String supplierName = purchases.getSupplierName() ;

		String quantityFormatted = format.format( purchases.getItemQuantity() ) ;
		BigDecimal quantityOriginal = purchases.getItemQuantity() ;

		String itemPricePerUnitFormatted = format.format( purchases.getItemPricePerUnit() ) ;
		BigDecimal itemPriceUnitOriginal = purchases.getItemPricePerUnit() ;

		String itemTotalManualCostFormatted = format.format( purchases.getItemTotalManualCost() ) ;
		BigDecimal itemTotalManualOriginal = purchases.getItemTotalManualCost() ;

		String itemTotalAutoCostFormatted = format.format( purchases.getItemTotalAutomaticCost() ) ;
		BigDecimal itemTotalAutoOriginal = purchases.getItemTotalAutomaticCost() ;

		String amountPaidFormatted = format.format( purchases.getAmountPaid() ) ;
		BigDecimal amountPaidOriginal = purchases.getAmountPaid() ;

		String installmentPaidFormatted = format.format( purchases.getInstallmentPaid() ) ;
		BigDecimal installmentPaidOriginal = purchases.getInstallmentPaid() ;

		String installmentBalanceFormatted = format.format( purchases.getInstallmentBalance() ) ;
		BigDecimal installmentBalanceOriginal = purchases.getInstallmentBalance() ;

		Purchases purchasesReturned = new Purchases( id , dateServer , dateClient , item , subCat , quantityOriginal , quantityFormatted , itemPriceUnitOriginal ,
				itemPricePerUnitFormatted , itemTotalManualOriginal , itemTotalManualCostFormatted , itemTotalAutoOriginal , itemTotalAutoCostFormatted , credit , clearedBy ,
				amountPaidOriginal , amountPaidFormatted , installmentPaidOriginal , installmentPaidFormatted , installmentBalanceOriginal , installmentBalanceFormatted ,
				paymentMethod , supplierName ) ;

		return purchasesReturned ;
	}

	private static Purchases formattedPurchasesItalian( Purchases purchases ) {

		NumberFormat format = NumberFormat.getNumberInstance( Locale.ITALIAN ) ;

		BigDecimal id = purchases.getId() ;
		String dateServer = purchases.getDateServer() ;
		String dateClient = purchases.getDateClient() ;
		String item = purchases.getItem() ;
		String subCat = purchases.getItemSubCategory() ;
		boolean credit = purchases.isCredit() ;
		String clearedBy = purchases.getClearedBy() ;
		String paymentMethod = purchases.getPaymentMethod() ;
		String supplierName = purchases.getSupplierName() ;

		String quantityFormatted = format.format( purchases.getItemQuantity() ) ;
		BigDecimal quantityOriginal = purchases.getItemQuantity() ;

		String itemPricePerUnitFormatted = format.format( purchases.getItemPricePerUnit() ) ;
		BigDecimal itemPriceUnitOriginal = purchases.getItemPricePerUnit() ;

		String itemTotalManualCostFormatted = format.format( purchases.getItemTotalManualCost() ) ;
		BigDecimal itemTotalManualOriginal = purchases.getItemTotalManualCost() ;

		String itemTotalAutoCostFormatted = format.format( purchases.getItemTotalAutomaticCost() ) ;
		BigDecimal itemTotalAutoOriginal = purchases.getItemTotalAutomaticCost() ;

		String amountPaidFormatted = format.format( purchases.getAmountPaid() ) ;
		BigDecimal amountPaidOriginal = purchases.getAmountPaid() ;

		String installmentPaidFormatted = format.format( purchases.getInstallmentPaid() ) ;
		BigDecimal installmentPaidOriginal = purchases.getInstallmentPaid() ;

		String installmentBalanceFormatted = format.format( purchases.getInstallmentBalance() ) ;
		BigDecimal installmentBalanceOriginal = purchases.getInstallmentBalance() ;

		Purchases purchasesReturned = new Purchases( id , dateServer , dateClient , item , subCat , quantityOriginal , quantityFormatted , itemPriceUnitOriginal ,
				itemPricePerUnitFormatted , itemTotalManualOriginal , itemTotalManualCostFormatted , itemTotalAutoOriginal , itemTotalAutoCostFormatted , credit , clearedBy ,
				amountPaidOriginal , amountPaidFormatted , installmentPaidOriginal , installmentPaidFormatted , installmentBalanceOriginal , installmentBalanceFormatted ,
				paymentMethod , supplierName ) ;

		return purchasesReturned ;
	}

}
