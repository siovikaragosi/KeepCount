
package com.keepcount.dao.pricing ;

import java.math.BigDecimal ;
import java.sql.Blob ;
import java.sql.CallableStatement ;
import java.sql.Connection ;
import java.sql.PreparedStatement ;
import java.sql.ResultSet ;
import java.util.ArrayList ;
import java.util.Base64 ;
import java.util.Collections ;
import java.util.HashMap ;
import java.util.List ;

import com.keepcount.dao.dbutils.DBUtils ;
import com.keepcount.model.item.Item ;
import com.keepcount.model.pricing.Pricing ;

public class PricingDAO {

	private static List < String > listOfAllProceduresCreated ;

	public static List < String > getListOfAllProceduresCreated() {
		return listOfAllProceduresCreated ;
	}

	public static void setListOfAllProceduresCreated( List < String > listOfAllProceduresCreated ) {
		PricingDAO.listOfAllProceduresCreated = listOfAllProceduresCreated ;
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
		PricingDAO.procExisst = procExisst ;
	}

	private static String createTableStr = "(id bigint primary key auto_increment".concat( ",date_server datetime not null default current_timestamp" )
			.concat( ",date_client varchar(255) not null" ).concat( ",price decimal(50,5) not null" ).concat( ",item_id bigint unique not null" )
			.concat( ",item_sub_category varchar(255) unique not null)" ) ;

	private static int checkPricingTableExistence( String businessId ) {
		int result = 0 ;

		String tableName = "pricing_" ;

		try {

			if ( PricingDAO.getProcExisst() == 0 ) {
				result = DBUtils.checkTableExistence( tableName , createTableStr , businessId ) ;
			}

		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return result ;
	}

	public static int newPrice( String businessId , Pricing pricing ) {

		PricingDAO.checkPricingTableExistence( businessId ) ;

		int result = 0 ;
		try {
			result = PricingDAOInsertion.newPricing( businessId , pricing ) ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return result ;
	}

	public static int updatePricing( String businessId , Pricing pricing , BigDecimal id ) {

		int result = 0 ;
		try {
			result = PricingDAOUpdate.updatePricing( businessId , pricing , id ) ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return result ;
	}

	public static int deletePricing( String businessId , BigDecimal id ) {

		int result = 0 ;
		try {
			result = PricingDAODeletion.deleteFromPricing( businessId , id ) ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return result ;
	}

	public static List < Pricing > findAllPricings( String businessId , String numberFormat ) {

		PricingDAO.checkPricingTableExistence( businessId ) ;

		List < Pricing > pricings = new ArrayList <>() ;
		try {
			pricings = PricingDAORetrieval.findAllPricings( businessId , numberFormat ) ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return pricings ;
	}

	public static void main( String [ ] args ) throws Exception {

		System.out.println( findAllItemssPerBusiness( "16" ) ) ;

	}

	private static int get_all_items_per_biz_Existence ;

	public static int getGet_all_items_per_biz_Existence() {
		return get_all_items_per_biz_Existence ;
	}

	public static void setGet_all_items_per_biz_Existence( int get_all_items_per_biz_Existence ) {
		PricingDAO.get_all_items_per_biz_Existence = get_all_items_per_biz_Existence ;
	}

	public static List < Item > findAllItemssPerBusiness( String businessId ) throws Exception {

		String procName = "get_all_items_per_biz_" ;
		String tableName = "items_" ;
		String whereCond = "" ;
		String inParams = "" ;
		String wildCardValues = "" ;
		String asteriskOrCols = "*" ;

		String procedureName = procName.concat( businessId ) ;

		if ( getListOfAllProceduresCreated() == null ) {
			listOfAllProceduresAlreadyCreated() ;
		}

		if ( !getListOfAllProceduresCreated().contains( procedureName ) ) {
			DBUtils.createSelectionProc( inParams , procName , businessId , asteriskOrCols , tableName , whereCond , getGet_all_items_per_biz_Existence() ) ;
			setGet_all_items_per_biz_Existence( 1 ) ;
		} else {
			setGet_all_items_per_biz_Existence( 1 ) ;
		}

		List < Item > items = new ArrayList <>() ;

		CallableStatement cs = null ;
		ResultSet rs = null ;
		Connection connection = DBUtils.getConn() ;
		try {

			cs = connection.prepareCall( DBUtils.invokeSelection( procName , businessId , wildCardValues ) ) ;

			rs = cs.executeQuery() ;

			while ( rs.next() ) {
				BigDecimal id = rs.getBigDecimal( "id" ) ;
				String itemName = rs.getString( "item_name" ) ;

				String itemCategory = rs.getString( "item_category" ) ;
				String itemSubCategory = rs.getString( "item_sub_category" ) ;
				String itemDescription = rs.getString( "item_description" ) ;
				Blob itemSamplePhotoOneBlob = rs.getBlob( "item_sample_photo_one" ) ;
				Blob itemSamplePhotoTwoBlob = rs.getBlob( "item_sample_photo_two" ) ;
				Blob itemSamplePhotoThreeBlob = rs.getBlob( "item_sample_photo_three" ) ;

				// processing the first sample photo
				String itemSamplePhotoOne = null ;
				if ( itemSamplePhotoOneBlob != null ) {
					byte [ ] bytesOne = new byte [ ( int ) itemSamplePhotoOneBlob.length() ] ;
					bytesOne = itemSamplePhotoOneBlob.getBytes( 1 , ( int ) itemSamplePhotoOneBlob.length() ) ;
					String itemSamplePhotoOneToBeEncoded = Base64.getEncoder().encodeToString( bytesOne ) ;
					String preOne = "data:image/png;base64," ;
					String itemSamplePhotoOneBase64 = preOne.concat( itemSamplePhotoOneToBeEncoded ) ;
					itemSamplePhotoOne = itemSamplePhotoOneBase64 ;
					// System.out.println(itemSamplePhotoOne);
				} else {
					itemSamplePhotoOne = null ;
				}

				// processing the second sample photo
				String itemSamplePhotoTwo = null ;
				if ( itemSamplePhotoTwoBlob != null ) {
					byte [ ] bytesTwo = new byte [ ( int ) itemSamplePhotoTwoBlob.length() ] ;
					bytesTwo = itemSamplePhotoTwoBlob.getBytes( 1 , ( int ) itemSamplePhotoTwoBlob.length() ) ;
					String itemSamplePhotoTwoToBeEncoded = Base64.getEncoder().encodeToString( bytesTwo ) ;
					String preTwo = "data:image/png;base64," ;
					String itemSamplePhotoTwoBase64 = preTwo.concat( itemSamplePhotoTwoToBeEncoded ) ;
					itemSamplePhotoTwo = itemSamplePhotoTwoBase64 ;
				} else {
					itemSamplePhotoTwo = null ;
				}

				// processing the third sample photo
				String itemSamplePhotoThree = null ;
				if ( itemSamplePhotoThreeBlob != null ) {
					byte [ ] bytesThree = new byte [ ( int ) itemSamplePhotoThreeBlob.length() ] ;
					bytesThree = itemSamplePhotoThreeBlob.getBytes( 1 , ( int ) itemSamplePhotoThreeBlob.length() ) ;
					String itemSamplePhotoThreeToBeEncoded = Base64.getEncoder().encodeToString( bytesThree ) ;
					String preThree = "data:image/png;base64," ;
					String itemSamplePhotoThreeBase64 = preThree.concat( itemSamplePhotoThreeToBeEncoded ) ;
					itemSamplePhotoThree = itemSamplePhotoThreeBase64 ;
				} else {
					itemSamplePhotoThree = null ;
				}
				System.out.println( "name: " + itemName.concat( " ID: " ).concat( id.toString() ) ) ;

				Item item = new Item( id , itemName , itemCategory , itemSubCategory , itemDescription , itemSamplePhotoOne , itemSamplePhotoTwo , itemSamplePhotoThree ) ;
				items.add( item ) ;

			}

		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( rs , cs , null , connection ) ;
		}

		return items ;
	}

	private static int items_with_no_price_Existence ;

	public static int getItems_with_no_price_Existence() {
		return items_with_no_price_Existence ;
	}

	public static void setItems_with_no_price_( int items_with_no_price_Existence ) {
		PricingDAO.items_with_no_price_Existence = items_with_no_price_Existence ;
	}

	public static List < Item > findAllItemssPerBusinessWhosePricesAreNotYetSet( String businessId ) throws Exception {

		String procName = "items_with_no_price_" ;
		String tableName = "items_" ;
		String whereCond = "where " ;
		String inParams = "" ;
		String wildCardValues = "" ;
		String asteriskOrCols = "*" ;

		String procedureName = procName.concat( businessId ) ;

		if ( getListOfAllProceduresCreated() == null ) {
			listOfAllProceduresAlreadyCreated() ;
		}

		if ( !getListOfAllProceduresCreated().contains( procedureName ) ) {
			DBUtils.createSelectionProc( inParams , procName , businessId , asteriskOrCols , tableName , whereCond , getItems_with_no_price_Existence() ) ;
			setItems_with_no_price_( 1 ) ;
		} else {
			setItems_with_no_price_( 1 ) ;
		}

		List < Item > items = new ArrayList <>() ;

		CallableStatement cs = null ;
		ResultSet rs = null ;
		Connection connection = DBUtils.getConn() ;
		try {

			cs = connection.prepareCall( DBUtils.invokeSelection( procName , businessId , wildCardValues ) ) ;

			rs = cs.executeQuery() ;

			while ( rs.next() ) {
				BigDecimal id = rs.getBigDecimal( "id" ) ;
				String itemName = rs.getString( "item_name" ) ;

				String itemCategory = rs.getString( "item_category" ) ;
				String itemSubCategory = rs.getString( "item_sub_category" ) ;
				String itemDescription = rs.getString( "item_description" ) ;
				Blob itemSamplePhotoOneBlob = rs.getBlob( "item_sample_photo_one" ) ;
				Blob itemSamplePhotoTwoBlob = rs.getBlob( "item_sample_photo_two" ) ;
				Blob itemSamplePhotoThreeBlob = rs.getBlob( "item_sample_photo_three" ) ;

				// processing the first sample photo
				String itemSamplePhotoOne = null ;
				if ( itemSamplePhotoOneBlob != null ) {
					byte [ ] bytesOne = new byte [ ( int ) itemSamplePhotoOneBlob.length() ] ;
					bytesOne = itemSamplePhotoOneBlob.getBytes( 1 , ( int ) itemSamplePhotoOneBlob.length() ) ;
					String itemSamplePhotoOneToBeEncoded = Base64.getEncoder().encodeToString( bytesOne ) ;
					String preOne = "data:image/png;base64," ;
					String itemSamplePhotoOneBase64 = preOne.concat( itemSamplePhotoOneToBeEncoded ) ;
					itemSamplePhotoOne = itemSamplePhotoOneBase64 ;
					// System.out.println(itemSamplePhotoOne);
				} else {
					itemSamplePhotoOne = null ;
				}

				// processing the second sample photo
				String itemSamplePhotoTwo = null ;
				if ( itemSamplePhotoTwoBlob != null ) {
					byte [ ] bytesTwo = new byte [ ( int ) itemSamplePhotoTwoBlob.length() ] ;
					bytesTwo = itemSamplePhotoTwoBlob.getBytes( 1 , ( int ) itemSamplePhotoTwoBlob.length() ) ;
					String itemSamplePhotoTwoToBeEncoded = Base64.getEncoder().encodeToString( bytesTwo ) ;
					String preTwo = "data:image/png;base64," ;
					String itemSamplePhotoTwoBase64 = preTwo.concat( itemSamplePhotoTwoToBeEncoded ) ;
					itemSamplePhotoTwo = itemSamplePhotoTwoBase64 ;
				} else {
					itemSamplePhotoTwo = null ;
				}

				// processing the third sample photo
				String itemSamplePhotoThree = null ;
				if ( itemSamplePhotoThreeBlob != null ) {
					byte [ ] bytesThree = new byte [ ( int ) itemSamplePhotoThreeBlob.length() ] ;
					bytesThree = itemSamplePhotoThreeBlob.getBytes( 1 , ( int ) itemSamplePhotoThreeBlob.length() ) ;
					String itemSamplePhotoThreeToBeEncoded = Base64.getEncoder().encodeToString( bytesThree ) ;
					String preThree = "data:image/png;base64," ;
					String itemSamplePhotoThreeBase64 = preThree.concat( itemSamplePhotoThreeToBeEncoded ) ;
					itemSamplePhotoThree = itemSamplePhotoThreeBase64 ;
				} else {
					itemSamplePhotoThree = null ;
				}
				System.out.println( "name: " + itemName ) ;

				Item item = new Item( id , itemName , itemCategory , itemSubCategory , itemDescription , itemSamplePhotoOne , itemSamplePhotoTwo , itemSamplePhotoThree ) ;
				items.add( item ) ;

			}

		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( rs , cs , null , connection ) ;
		}

		return items ;
	}

	// public static List < Item > findAllItemssPerBusiness( String businessId )
	// throws Exception {
	//
	// String procName = "get_all_items_per_biz_" ;
	// String tableName = "items_" ;
	// String whereCond = "" ;
	// String inParams = "" ;
	// String wildCardValues = "" ;
	// String asteriskOrCols = "*" ;
	//
	// int i = DBUtils.createSelectionProc( inParams , procName , businessId ,
	// asteriskOrCols , tableName , whereCond ) ;
	//
	// System.out.println( "i:....:" + i ) ;
	// System.out.println( "ID: " + businessId ) ;
	//
	// List < Item > items = new ArrayList <>() ;
	//
	// CallableStatement cs = null ;
	// ResultSet rs = null ;
	// Connection connection = DBUtils.getConn() ;
	// try {
	//
	// cs = connection.prepareCall( DBUtils.invokeSelection( procName , businessId ,
	// wildCardValues ) ) ;
	// rs = cs.executeQuery() ;
	//
	// while ( rs.next() ) {
	// BigDecimal id = rs.getBigDecimal( "id" ) ;
	// String itemName = rs.getString( "item_name" ) ;
	//
	// String itemCategory = rs.getString( "item_category" ) ;
	// String itemSubCategory = rs.getString( "item_sub_category" ) ;
	// String itemDescription = rs.getString( "item_description" ) ;
	// Blob itemSamplePhotoOneBlob = rs.getBlob( "item_sample_photo_one" ) ;
	// Blob itemSamplePhotoTwoBlob = rs.getBlob( "item_sample_photo_two" ) ;
	// Blob itemSamplePhotoThreeBlob = rs.getBlob( "item_sample_photo_three" ) ;
	//
	// // processing the first sample photo
	// String itemSamplePhotoOne = null ;
	// if ( itemSamplePhotoOneBlob != null ) {
	// byte [ ] bytesOne = new byte [ ( int ) itemSamplePhotoOneBlob.length() ] ;
	// bytesOne = itemSamplePhotoOneBlob.getBytes( 1 , ( int )
	// itemSamplePhotoOneBlob.length() ) ;
	// String itemSamplePhotoOneToBeEncoded = Base64.getEncoder().encodeToString(
	// bytesOne ) ;
	// String preOne = "data:image/png;base64," ;
	// String itemSamplePhotoOneBase64 = preOne.concat(
	// itemSamplePhotoOneToBeEncoded ) ;
	// itemSamplePhotoOne = itemSamplePhotoOneBase64 ;
	// // System.out.println(itemSamplePhotoOne);
	// } else {
	// itemSamplePhotoOne = null ;
	// }
	//
	// // processing the second sample photo
	// String itemSamplePhotoTwo = null ;
	// if ( itemSamplePhotoTwoBlob != null ) {
	// byte [ ] bytesTwo = new byte [ ( int ) itemSamplePhotoTwoBlob.length() ] ;
	// bytesTwo = itemSamplePhotoTwoBlob.getBytes( 1 , ( int )
	// itemSamplePhotoTwoBlob.length() ) ;
	// String itemSamplePhotoTwoToBeEncoded = Base64.getEncoder().encodeToString(
	// bytesTwo ) ;
	// String preTwo = "data:image/png;base64," ;
	// String itemSamplePhotoTwoBase64 = preTwo.concat(
	// itemSamplePhotoTwoToBeEncoded ) ;
	// itemSamplePhotoTwo = itemSamplePhotoTwoBase64 ;
	// } else {
	// itemSamplePhotoTwo = null ;
	// }
	//
	// // processing the third sample photo
	// String itemSamplePhotoThree = null ;
	// if ( itemSamplePhotoThreeBlob != null ) {
	// byte [ ] bytesThree = new byte [ ( int ) itemSamplePhotoThreeBlob.length() ]
	// ;
	// bytesThree = itemSamplePhotoThreeBlob.getBytes( 1 , ( int )
	// itemSamplePhotoThreeBlob.length() ) ;
	// String itemSamplePhotoThreeToBeEncoded = Base64.getEncoder().encodeToString(
	// bytesThree ) ;
	// String preThree = "data:image/png;base64," ;
	// String itemSamplePhotoThreeBase64 = preThree.concat(
	// itemSamplePhotoThreeToBeEncoded ) ;
	// itemSamplePhotoThree = itemSamplePhotoThreeBase64 ;
	// } else {
	// itemSamplePhotoThree = null ;
	// }
	//
	// System.out.println( "name: " + itemName ) ;
	//
	// Item item = new Item( id , itemName , itemCategory , itemSubCategory ,
	// itemDescription , itemSamplePhotoOne , itemSamplePhotoTwo ,
	// itemSamplePhotoThree ) ;
	// items.add( item ) ;
	//
	// }
	// } catch ( Exception e ) {
	// e.printStackTrace() ;
	// } finally {
	// DBUtils.closeConnections( rs , cs , null , connection ) ;
	// }
	// return items ;
	// }

	private static int get_all_itemsubs_n_ids_Existence ;

	public static int getGet_all_itemsubs_n_ids_Existence() {
		return get_all_itemsubs_n_ids_Existence ;
	}

	public static void setGet_all_itemsubs_n_ids_Existence( int get_all_itemsubs_n_ids_ ) {
		PricingDAO.get_all_itemsubs_n_ids_Existence = get_all_itemsubs_n_ids_ ;
	}

	public static HashMap < String , Item > findAllItemSubCategorisAndIDs( String businessId ) throws Exception {

		HashMap < String , Item > items = new HashMap <>() ;
		String procName = "get_all_itemsubs_n_ids_" ;
		String tableName = "items_" ;
		String whereCond = "" ;
		String inParams = "" ;
		String wildCardValues = "" ;
		String asteriskOrCols = "*" ;

		String procedureName = procName.concat( businessId ) ;

		if ( getListOfAllProceduresCreated() == null ) {
			listOfAllProceduresAlreadyCreated() ;
		}

		if ( !getListOfAllProceduresCreated().contains( procedureName ) ) {
			DBUtils.createSelectionProc( inParams , procName , businessId , asteriskOrCols , tableName , whereCond , getGet_all_itemsubs_n_ids_Existence() ) ;
			setGet_all_itemsubs_n_ids_Existence( 1 ) ;
		} else {
			setGet_all_itemsubs_n_ids_Existence( 1 ) ;
		}

		CallableStatement cs = null ;
		ResultSet rs = null ;
		Connection connection = DBUtils.getConn() ;

		try {

			cs = connection.prepareCall( DBUtils.invokeSelection( procName , businessId , wildCardValues ) ) ;
			rs = cs.executeQuery() ;

			while ( rs.next() ) {
				BigDecimal id = rs.getBigDecimal( "id" ) ;
				String itemName = rs.getString( "item_name" ) ;

				String itemCategory = rs.getString( "item_category" ) ;
				String itemSubCategory = rs.getString( "item_sub_category" ) ;
				String itemDescription = rs.getString( "item_description" ) ;
				Blob itemSamplePhotoOneBlob = rs.getBlob( "item_sample_photo_one" ) ;
				Blob itemSamplePhotoTwoBlob = rs.getBlob( "item_sample_photo_two" ) ;
				Blob itemSamplePhotoThreeBlob = rs.getBlob( "item_sample_photo_three" ) ;

				// processing the first sample photo
				String itemSamplePhotoOne = null ;
				if ( itemSamplePhotoOneBlob != null ) {
					byte [ ] bytesOne = new byte [ ( int ) itemSamplePhotoOneBlob.length() ] ;
					bytesOne = itemSamplePhotoOneBlob.getBytes( 1 , ( int ) itemSamplePhotoOneBlob.length() ) ;
					String itemSamplePhotoOneToBeEncoded = Base64.getEncoder().encodeToString( bytesOne ) ;
					String preOne = "data:image/png;base64," ;
					String itemSamplePhotoOneBase64 = preOne.concat( itemSamplePhotoOneToBeEncoded ) ;
					itemSamplePhotoOne = itemSamplePhotoOneBase64 ;
					// System.out.println(itemSamplePhotoOne);
				} else {
					itemSamplePhotoOne = null ;
				}

				// processing the second sample photo
				String itemSamplePhotoTwo = null ;
				if ( itemSamplePhotoTwoBlob != null ) {
					byte [ ] bytesTwo = new byte [ ( int ) itemSamplePhotoTwoBlob.length() ] ;
					bytesTwo = itemSamplePhotoTwoBlob.getBytes( 1 , ( int ) itemSamplePhotoTwoBlob.length() ) ;
					String itemSamplePhotoTwoToBeEncoded = Base64.getEncoder().encodeToString( bytesTwo ) ;
					String preTwo = "data:image/png;base64," ;
					String itemSamplePhotoTwoBase64 = preTwo.concat( itemSamplePhotoTwoToBeEncoded ) ;
					itemSamplePhotoTwo = itemSamplePhotoTwoBase64 ;
				} else {
					itemSamplePhotoTwo = null ;
				}

				// processing the third sample photo
				String itemSamplePhotoThree = null ;
				if ( itemSamplePhotoThreeBlob != null ) {
					byte [ ] bytesThree = new byte [ ( int ) itemSamplePhotoThreeBlob.length() ] ;
					bytesThree = itemSamplePhotoThreeBlob.getBytes( 1 , ( int ) itemSamplePhotoThreeBlob.length() ) ;
					String itemSamplePhotoThreeToBeEncoded = Base64.getEncoder().encodeToString( bytesThree ) ;
					String preThree = "data:image/png;base64," ;
					String itemSamplePhotoThreeBase64 = preThree.concat( itemSamplePhotoThreeToBeEncoded ) ;
					itemSamplePhotoThree = itemSamplePhotoThreeBase64 ;
				} else {
					itemSamplePhotoThree = null ;
				}

				Item item = new Item( id , itemName , itemCategory , itemSubCategory , itemDescription , itemSamplePhotoOne , itemSamplePhotoTwo , itemSamplePhotoThree ) ;
				items.put( itemSubCategory , item ) ;

			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( rs , cs , null , connection ) ;
		}
		return items ;
	}

	public static HashMap < BigDecimal , Item > findItemNameBasingOnIDMapping( String businessId ) throws Exception {

		HashMap < BigDecimal , Item > map = new HashMap <>() ;

		PreparedStatement st = null ;
		ResultSet rs = null ;

		Connection connection = DBUtils.getConn() ;

		try {

			st = connection.prepareStatement( "select * from items_".concat( businessId ).concat( " " ).concat( "" ) ) ;
			// st.setBigDecimal(1, idOfItem);
			rs = st.executeQuery() ;
			while ( rs.next() ) {
				BigDecimal id = rs.getBigDecimal( "id" ) ;
				String itemName = rs.getString( "item_name" ) ;

				String itemCategory = rs.getString( "item_category" ) ;
				String itemSubCategory = rs.getString( "item_sub_category" ) ;
				String itemDescription = rs.getString( "item_description" ) ;
				Blob itemSamplePhotoOneBlob = rs.getBlob( "item_sample_photo_one" ) ;
				Blob itemSamplePhotoTwoBlob = rs.getBlob( "item_sample_photo_two" ) ;
				Blob itemSamplePhotoThreeBlob = rs.getBlob( "item_sample_photo_three" ) ;

				// processing the first sample photo
				String itemSamplePhotoOne = null ;
				if ( itemSamplePhotoOneBlob != null ) {
					byte [ ] bytesOne = new byte [ ( int ) itemSamplePhotoOneBlob.length() ] ;
					bytesOne = itemSamplePhotoOneBlob.getBytes( 1 , ( int ) itemSamplePhotoOneBlob.length() ) ;
					String itemSamplePhotoOneToBeEncoded = Base64.getEncoder().encodeToString( bytesOne ) ;
					String preOne = "data:image/png;base64," ;
					String itemSamplePhotoOneBase64 = preOne.concat( itemSamplePhotoOneToBeEncoded ) ;
					itemSamplePhotoOne = itemSamplePhotoOneBase64 ;
					// System.out.println(itemSamplePhotoOne);
				} else {
					itemSamplePhotoOne = null ;
				}

				// processing the second sample photo
				String itemSamplePhotoTwo = null ;
				if ( itemSamplePhotoTwoBlob != null ) {
					byte [ ] bytesTwo = new byte [ ( int ) itemSamplePhotoTwoBlob.length() ] ;
					bytesTwo = itemSamplePhotoTwoBlob.getBytes( 1 , ( int ) itemSamplePhotoTwoBlob.length() ) ;
					String itemSamplePhotoTwoToBeEncoded = Base64.getEncoder().encodeToString( bytesTwo ) ;
					String preTwo = "data:image/png;base64," ;
					String itemSamplePhotoTwoBase64 = preTwo.concat( itemSamplePhotoTwoToBeEncoded ) ;
					itemSamplePhotoTwo = itemSamplePhotoTwoBase64 ;
				} else {
					itemSamplePhotoTwo = null ;
				}

				// processing the third sample photo
				String itemSamplePhotoThree = null ;
				if ( itemSamplePhotoThreeBlob != null ) {
					byte [ ] bytesThree = new byte [ ( int ) itemSamplePhotoThreeBlob.length() ] ;
					bytesThree = itemSamplePhotoThreeBlob.getBytes( 1 , ( int ) itemSamplePhotoThreeBlob.length() ) ;
					String itemSamplePhotoThreeToBeEncoded = Base64.getEncoder().encodeToString( bytesThree ) ;
					String preThree = "data:image/png;base64," ;
					String itemSamplePhotoThreeBase64 = preThree.concat( itemSamplePhotoThreeToBeEncoded ) ;
					itemSamplePhotoThree = itemSamplePhotoThreeBase64 ;
				} else {
					itemSamplePhotoThree = null ;
				}

				Item item = new Item( id , itemName , itemCategory , itemSubCategory , itemDescription , itemSamplePhotoOne , itemSamplePhotoTwo , itemSamplePhotoThree ) ;
				map.put( id , item ) ;

			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( rs , null , st , connection ) ;
		}
		return map ;
	}

	/* STEPS:: */
	/* FIRST OF, GET THE ID FROM THE CLIENT */
	/* USE THIS ID TO GET THE INDEX OF ITSELF IN A SORTED ID LIST */
	/* THEN USE THIS INDEX TO GET THE VERY SAME ID FROM THE ID LIST */

	/* THEN USE THIS ID GOT TO GET THE INDEX OF THE ITEM WHOSE NAME IS REQUIRED */
	// OR OR OR
	/*
	 * THEN USE THIS ID TO GET THE REQUIRED ITEM NAME FROM A MAP OF KEY ID AND VALUE
	 * ITEM
	 */

	private static BigDecimal getIDByElementIndex( Number index , List < BigDecimal > idList ) {

		BigDecimal id = idList.get( ( int ) index ) ;
		return id ;
	}

	private static List < Item > itemWhoseIDisRequiredForEditing ;

	public static List < Item > getItemWhoseIDisRequiredForEditing() {

		return itemWhoseIDisRequiredForEditing ;
	}

	public static void setItemWhoseIDisRequiredForEditing( List < Item > itemWhoseIDisRequiredForEditing ) {

		PricingDAO.itemWhoseIDisRequiredForEditing = itemWhoseIDisRequiredForEditing ;
	}

	/* Getting the index of the ID */
	/* This method has covered most of the steps comment above */
	public static List < Item > getListOfItemsToExtractNameForEditing( List < Item > itemsToExtractIDsFrom , BigDecimal idSearchable , HashMap < BigDecimal , Item > hashMap ,
			List < BigDecimal > IDs ) throws Exception {

		Item item = new Item() ;
		List < Item > itemsToBeReturned = new ArrayList <>() ;

		// List<BigDecimal> IDs = allItemIDs_Sorted(itemsToExtractIDsFrom);

		Collections.sort( IDs ) ;
		System.out.println( "IDs: " + IDs ) ;
		System.out.println( "Id serch: " + idSearchable ) ;

		Number indexOfTheId = Collections.binarySearch( IDs , idSearchable ) ;
		System.out.println( "Index: " + indexOfTheId ) ;

		// for (Item item2 : itemsToExtractIDsFrom) {
		// System.out.println("name:" + item2.getItemName());
		// }

		System.out.println( "Keys: " + hashMap.keySet() ) ;

		System.out.println() ;

		BigDecimal idOfItemSpecifiedByClient = getIDByElementIndex( indexOfTheId , IDs ) ;
		System.out.println( "ID spec: " + idOfItemSpecifiedByClient ) ;

		HashMap < BigDecimal , Item > hashMap2 = hashMap ;

		item = hashMap2.get( idOfItemSpecifiedByClient ) ;

		System.out.println( "name of item: " + item.getItemName() ) ;

		itemsToBeReturned.add( item ) ;

		setItemWhoseIDisRequiredForEditing( itemsToBeReturned ) ;

		return itemsToBeReturned ;
	}

}
