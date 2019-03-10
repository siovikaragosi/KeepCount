
package com.keepcount.dao.purchases ;

import java.math.BigDecimal ;
import java.sql.CallableStatement ;
import java.sql.Connection ;
import java.sql.ResultSet ;
import java.util.ArrayList ;
import java.util.List ;

import com.keepcount.dao.dbutils.DBUtils ;
import com.keepcount.dao.dbutils.NumberFormatting ;
import com.keepcount.model.item.UnitOfMeasurement ;
import com.keepcount.model.purchases.PurchasesTwo ;

public class PurchasesTwoDAORetreive {

	private static List < String > listOfAllProceduresCreated ;

	public static List < String > getListOfAllProceduresCreated() {
		return listOfAllProceduresCreated ;
	}

	public static void setListOfAllProceduresCreated( List < String > listOfAllProceduresCreated ) {
		PurchasesTwoDAORetreive.listOfAllProceduresCreated = listOfAllProceduresCreated ;
	}

	private static List < String > listOfAllProceduresAlreadyCreated() throws Exception {
		setListOfAllProceduresCreated( DBUtils.listOfAllProceduresAlreadyCreated() ) ;
		return getListOfAllProceduresCreated() ;
	}

	private static String procName = "get_purchases_2_" ;
	private static String tableName = "purchases_" ;
	private static String whereCond = "" ;
	private static String inParams = "" ;
	private static String wildCardValues = "" ;
	private static String asteriskOrCols = "*" ;

	private static int purchasesTwoExistence ;

	public static int getPurchasesTwoExistence() {
		return purchasesTwoExistence ;
	}

	public static void setPurchasesTwoExistence( int purchasesTwoExistence ) {
		PurchasesTwoDAORetreive.purchasesTwoExistence = purchasesTwoExistence ;
	}

	public static List < PurchasesTwo > findAllPurchasesTwo( String businessId , String languageOfNumberFormat ) throws Exception {
		// String procName, String businessId, String tableName, String whereCondition

		String procedureName = procName.concat( businessId ) ;

		if ( getListOfAllProceduresCreated() == null ) {
			listOfAllProceduresAlreadyCreated() ;
		}

		if ( !getListOfAllProceduresCreated().contains( procedureName ) ) {
			DBUtils.createSelectionProc( inParams , procName , businessId , asteriskOrCols , tableName , whereCond , getPurchasesTwoExistence() ) ;

			setPurchasesTwoExistence( 1 ) ;
		} else {
			setPurchasesTwoExistence( 1 ) ;
		}

		List < PurchasesTwo > list = new ArrayList <>() ;
		CallableStatement cs = null ;
		ResultSet rs = null ;
		Connection connection = DBUtils.getConn() ;
		try {
			cs = connection.prepareCall( DBUtils.invokeSelection( procName , businessId , wildCardValues ) ) ;
			rs = cs.executeQuery() ;
			while ( rs.next() ) {

				BigDecimal id = rs.getBigDecimal( "id" ) ;
				String dateServer = rs.getString( "date_server" ) ;
				String dateClient = rs.getString( "date_client" ) ;
				BigDecimal itemId = rs.getBigDecimal( "item_id" ) ;
				BigDecimal itemQuantity = rs.getBigDecimal( "item_quantity" ) ;
				String itemQuantityStr = NumberFormatting.formattedBigDecimalIntoString( itemQuantity , languageOfNumberFormat ) ;
				BigDecimal unitCost = rs.getBigDecimal( "unit_cost" ) ;
				String unitCostStr = NumberFormatting.formattedBigDecimalIntoString( unitCost , languageOfNumberFormat ) ;
				boolean isCredit = rs.getBoolean( "credit" ) ;
				BigDecimal discountReceived = rs.getBigDecimal( "discount_rec" ) ;
				String discountReceivedStr = NumberFormatting.formattedBigDecimalIntoString( discountReceived , languageOfNumberFormat ) ;
				BigDecimal amountCleared = rs.getBigDecimal( "amount_cleared" ) ;
				String amountClearedStr = NumberFormatting.formattedBigDecimalIntoString( amountCleared , languageOfNumberFormat ) ;
				BigDecimal totalCostManual = rs.getBigDecimal( "total_cost_manual" ) ;
				String totalCostManualStr = NumberFormatting.formattedBigDecimalIntoString( totalCostManual , languageOfNumberFormat ) ;
				BigDecimal totalCostAuto = rs.getBigDecimal( "total_cost_auto" ) ;
				String totalCostAutoStr = NumberFormatting.formattedBigDecimalIntoString( totalCostAuto , languageOfNumberFormat ) ;
				BigDecimal balance = rs.getBigDecimal( "balance" ) ;
				String balanceStr = NumberFormatting.formattedBigDecimalIntoString( balance , languageOfNumberFormat ) ;
				BigDecimal supplierId = rs.getBigDecimal( "supplier_id" ) ;
				String narration = rs.getString( "narration" ) ;
				BigDecimal transactionId = rs.getBigDecimal( "transaction_id" ) ;

				PurchasesTwo purchasesTwo = new PurchasesTwo( id , dateServer , dateClient , itemId , itemQuantity , itemQuantityStr , unitCost , unitCostStr , isCredit ,
						discountReceived , discountReceivedStr , amountCleared , amountClearedStr , totalCostManual , totalCostManualStr , totalCostAuto , totalCostAutoStr ,
						balance , balanceStr , supplierId , null , narration , transactionId ) ;

				list.add( purchasesTwo ) ;

			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( rs , cs , null , connection ) ;
		}
		return list ;
	}

	public static void main( String [ ] args ) throws Exception {
		// UnitOfMeasurementDAORetreive.
		// findAllUnitsOfMeasurement( "16" ) ;
	}

}
