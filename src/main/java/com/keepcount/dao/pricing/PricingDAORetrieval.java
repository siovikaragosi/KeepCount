
package com.keepcount.dao.pricing ;

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
import com.keepcount.model.pricing.Pricing ;

public class PricingDAORetrieval {

	private static List < String > listOfAllProceduresCreated ;

	public static List < String > getListOfAllProceduresCreated() {
		return listOfAllProceduresCreated ;
	}

	public static void setListOfAllProceduresCreated( List < String > listOfAllProceduresCreated ) {
		PricingDAORetrieval.listOfAllProceduresCreated = listOfAllProceduresCreated ;
	}

	private static List < String > listOfAllProceduresAlreadyCreated() throws Exception {
		setListOfAllProceduresCreated( DBUtils.listOfAllProceduresAlreadyCreated() ) ;
		return getListOfAllProceduresCreated() ;
	}

	private static int procExisst = 0 ;

	public static int getProcExisst() {
		return procExisst ;
	}

	public static void setProcExisst( int procExisst ) {
		PricingDAORetrieval.procExisst = procExisst ;
	}

	private static String create_within_findAllPricings( String businessId ) {
		return "CREATE PROCEDURE get_all_pricings_".concat( ( businessId ) ).concat( "()" ).concat( " BEGIN " ).concat( "SELECT * FROM pricing_".concat( ( businessId ) ) )
				.concat( ";" ).concat( " END" ) ;
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

			if ( list.contains( "get_all_pricings_".concat( businessId ) ) ) {
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

	private static int get_all_pricings_Exisntence ;

	public static int getGet_all_pricings_Exisntence() {
		return get_all_pricings_Exisntence ;
	}

	public static void setGet_all_pricings_Exisntence( int get_all_pricings_Exisntence ) {
		PricingDAORetrieval.get_all_pricings_Exisntence = get_all_pricings_Exisntence ;
	}

	public static List < Pricing > findAllPricings( String businessId , String numberFormat ) throws Exception {
		// String procName, String businessId, String tableName, String whereCondition

		String procName = "get_all_pricings_" ;
		String tableName = "pricing_" ;
		String whereCond = "" ;
		String inParams = "" ;
		String wildCardValues = "" ;
		String asteriskOrCols = "*" ;

		String procedureName = procName.concat( businessId ) ;

		if ( getListOfAllProceduresCreated() == null ) {
			listOfAllProceduresAlreadyCreated() ;
		}

		if ( !getListOfAllProceduresCreated().contains( procedureName ) ) {
			DBUtils.createSelectionProc( inParams , procName , businessId , asteriskOrCols , tableName , whereCond , getGet_all_pricings_Exisntence() ) ;
			setGet_all_pricings_Exisntence( 1 ) ;
		} else {
			setGet_all_pricings_Exisntence( 1 ) ;
		}

		List < Pricing > pricings = new ArrayList <>() ;
		List < Pricing > pricingsReturned = new ArrayList <>() ;
		List < Pricing > pricingsReturnedEnglish = new ArrayList <>() ;
		List < Pricing > pricingsReturnedGerman = new ArrayList <>() ;
		List < Pricing > pricingsReturnedFrench = new ArrayList <>() ;
		// List < Pricing > pricingsReturnedNull = new ArrayList <>() ;

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
				BigDecimal price = rs.getBigDecimal( "price" ) ;
				String itemSubCategory = rs.getString( "item_sub_category" ) ;
				BigDecimal itemId = rs.getBigDecimal( "item_id" ) ;
				Pricing pricing = new Pricing( id , dateServer , dateClient , price , itemId , itemSubCategory ) ;
				System.out.println( "Pricing ret: " + pricing ) ;
				pricings.add( pricing ) ;
			}
			if ( numberFormat.equals( "English" ) ) {
				pricingsReturnedEnglish = formattedPricingEnglish( pricings ) ;
				pricingsReturned = pricingsReturnedEnglish ;
			} else if ( numberFormat.equals( "German" ) ) {
				pricingsReturnedGerman = formattedPricingGerman( pricings ) ;
				pricingsReturned = pricingsReturnedGerman ;
			} else if ( numberFormat.equals( "French" ) ) {
				pricingsReturnedFrench = formattedPricingFrench( pricings ) ;
				pricingsReturned = pricingsReturnedFrench ;
			} else {
				pricingsReturned = pricings ;
			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( rs , cs , null , connection ) ;
		}

		System.out.println( "pricing dao ret: " + pricingsReturned ) ;

		return pricingsReturned ;
	}

	// public static List < Pricing > findAllPricings( String businessId , String
	// numberFormat ) throws Exception {
	// List < Pricing > pricings = new ArrayList <>() ;
	// List < Pricing > pricingsReturned = new ArrayList <>() ;
	// List < Pricing > pricingsReturnedEnglish = new ArrayList <>() ;
	// List < Pricing > pricingsReturnedGerman = new ArrayList <>() ;
	// List < Pricing > pricingsReturnedFrench = new ArrayList <>() ;
	// List < Pricing > pricingsReturnedNull = new ArrayList <>() ;
	//
	// createProcedureToFindAllPricings( businessId ) ;
	//
	// CallableStatement cs = null ;
	// ResultSet rs = null ;
	// try {
	// cs = DBUtils.getConn().prepareCall( "{CALL get_all_pricings_".concat(
	// businessId ).concat( "}" ) ) ;
	// rs = cs.executeQuery() ;
	// while ( rs.next() ) {
	// BigDecimal id = rs.getBigDecimal( "id" ) ;
	// String dateServer = rs.getString( "date_server" ) ;
	// String dateClient = rs.getString( "date_client" ) ;
	// BigDecimal price = rs.getBigDecimal( "price" ) ;
	// String itemSubCategory = rs.getString( "item_sub_category" ) ;
	// BigDecimal itemId = rs.getBigDecimal( "item_id" ) ;
	// Pricing pricing = new Pricing( id , dateServer , dateClient , price , itemId
	// , itemSubCategory ) ;
	// System.out.println( "Pricing ret: " + pricing ) ;
	// pricings.add( pricing ) ;
	// }
	// if ( numberFormat.equals( "English" ) ) {
	// pricingsReturnedEnglish = formattedPricingEnglish( pricings ) ;
	// pricingsReturned = pricingsReturnedEnglish ;
	// } else if ( numberFormat.equals( "German" ) ) {
	// pricingsReturnedGerman = formattedPricingGerman( pricings ) ;
	// pricingsReturned = pricingsReturnedGerman ;
	// } else if ( numberFormat.equals( "French" ) ) {
	// pricingsReturnedFrench = formattedPricingFrench( pricings ) ;
	// pricingsReturned = pricingsReturnedFrench ;
	// } else {
	// pricingsReturned = pricings ;
	// }
	// } catch ( Exception e ) {
	// e.printStackTrace() ;
	// } finally {
	// DBUtils.closeAll() ;
	// }
	//
	// System.out.println( "pricing dao ret: " + pricingsReturned ) ;
	//
	// return pricingsReturned ;
	// }

	private static List < Pricing > formattedPricingEnglish( List < Pricing > list ) {
		List < Pricing > pricingsReturned = new ArrayList <>() ;
		NumberFormat format = NumberFormat.getNumberInstance( Locale.ENGLISH ) ;
		for ( Pricing pricing : list ) {
			BigDecimal id = pricing.getId() ;
			String dateServer = pricing.getDateServer() ;
			String dateClient = pricing.getDateClient() ;
			BigDecimal itemId = pricing.getItemId() ;
			String itemSubCategory = pricing.getItemSubCategory() ;
			String priceStr = format.format( pricing.getPrice() ) ;
			BigDecimal priceOriginal = pricing.getPrice() ;
			Pricing pricing2 = new Pricing( id , dateServer , dateClient , priceOriginal , priceStr , itemId , itemSubCategory ) ;
			pricingsReturned.add( pricing2 ) ;
		}
		return pricingsReturned ;
	}

	private static List < Pricing > formattedPricingGerman( List < Pricing > list ) {
		List < Pricing > pricingsReturned = new ArrayList <>() ;
		NumberFormat format = NumberFormat.getNumberInstance( Locale.GERMAN ) ;
		for ( Pricing pricing : list ) {
			BigDecimal id = pricing.getId() ;
			String dateServer = pricing.getDateServer() ;
			String dateClient = pricing.getDateClient() ;
			BigDecimal itemId = pricing.getItemId() ;
			String itemSubCategory = pricing.getItemSubCategory() ;
			String priceStr = format.format( pricing.getPrice() ) ;
			BigDecimal priceOriginal = pricing.getPrice() ;
			Pricing pricing2 = new Pricing( id , dateServer , dateClient , priceOriginal , priceStr , itemId , itemSubCategory ) ;
			pricingsReturned.add( pricing2 ) ;
		}
		return pricingsReturned ;
	}

	private static List < Pricing > formattedPricingFrench( List < Pricing > list ) {
		List < Pricing > pricingsReturned = new ArrayList <>() ;
		NumberFormat format = NumberFormat.getNumberInstance( Locale.FRENCH ) ;
		for ( Pricing pricing : list ) {
			BigDecimal id = pricing.getId() ;
			String dateServer = pricing.getDateServer() ;
			String dateClient = pricing.getDateClient() ;
			BigDecimal itemId = pricing.getItemId() ;
			String itemSubCategory = pricing.getItemSubCategory() ;
			String priceStr = format.format( pricing.getPrice() ) ;
			BigDecimal priceOriginal = pricing.getPrice() ;
			Pricing pricing2 = new Pricing( id , dateServer , dateClient , priceOriginal , priceStr , itemId , itemSubCategory ) ;
			pricingsReturned.add( pricing2 ) ;
		}
		return pricingsReturned ;
	}

}
