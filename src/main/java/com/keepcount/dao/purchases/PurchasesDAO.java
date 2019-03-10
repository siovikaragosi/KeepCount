
package com.keepcount.dao.purchases ;

import java.math.BigDecimal ;
import java.sql.Blob ;
import java.sql.Connection ;
import java.sql.DatabaseMetaData ;
import java.sql.PreparedStatement ;
import java.sql.ResultSet ;
import java.util.ArrayList ;
import java.util.Base64 ;
import java.util.List ;

import com.keepcount.dao.dbutils.DBUtils ;
import com.keepcount.model.item.Item ;
import com.keepcount.model.purchases.Purchases ;
import com.keepcount.model.suppliers.Suppliers ;

public class PurchasesDAO {

	private static String nameOfTheTablePurchasesWithinDAO( String businessId ) {
		return "`purchases_".concat( businessId ).concat( "`" ) ;
	}

	public static int checkTableCustomersExistence( String businessId ) throws Exception {
		DatabaseMetaData data = DBUtils.getConn().getMetaData() ;
		ResultSet rs = data.getTables( null , null , nameOfTheTablePurchasesWithinDAO( businessId ) , null ) ;
		PreparedStatement st = null ;
		int result = 0 ;
		try {
			if ( !rs.next() ) {
				st = DBUtils.getConn().prepareStatement( createTablePurchases( businessId ) ) ;
				result = st.executeUpdate() ;
			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			rs.close() ;
			st.close() ;
			DBUtils.getConn().close() ;
		}
		return result ;
	}

	private static String createTablePurchases( String businessId ) {

		return "CREATE TABLE ".concat( nameOfTheTablePurchasesWithinDAO( businessId )

				.concat( "(" )

				.concat( "id bigint primary key auto_increment" )
				// 1
				.concat( ",date_server datetime default current_timestamp" )
				// 2
				.concat( ",date_client varchar(255)" )
				// 3
				.concat( ",item varchar(255)" )
				// 4
				.concat( ",item_sub_category varchar(255)" )
				// 5
				.concat( ",item_quantity decimal(50,5)" )
				// 6
				.concat( ",item_price_per_unit decimal(50,5)" )
				// 7
				.concat( ",item_total_manual_cost decimal(50,5)" )
				// 8
				.concat( ",item_total_automatic_cost decimal(50,5)" )
				// 9
				.concat( ",credit tinyint" )
				// 10
				.concat( ",cleared_by varchar(255)" )
				// 11
				.concat( ",amount_paid decimal(50,5)" )
				// 12
				.concat( ",installment_paid decimal(50,5)" )
				// 13
				.concat( ",installment_balance decimal(50,5)" )
				// 14
				.concat( ",payment_method longtext" )
				// 15
				.concat( ",supplier_name varchar(255)" )

				.concat( ")" ) ) ;
	}

	public static int checkPurchasesTableExistence( String businessId ) throws Exception {
		int result = 0 ;
		DatabaseMetaData data = DBUtils.getConn().getMetaData() ;
		ResultSet rs = data.getTables( null , null , nameOfTheTablePurchasesWithinDAO( businessId ) , null ) ;
		PreparedStatement st = null ;
		Connection connection = DBUtils.getConn() ;
		try {
			if ( !rs.next() ) {
				st = connection.prepareStatement( createTablePurchases( businessId ) ) ;
				result = st.executeUpdate() ;
			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( rs , null , st , connection ) ;
		}
		return result ;
	}

	public static int newPurchase( String businessId , Purchases purchases ) throws Exception {
		// System.out.println(purchases);
		return PurchasesDAOInsertion.newPurchase( businessId , purchases ) ;
	}

	public static List < Purchases > findAllPurchases( String businessId , String numberFormat ) throws Exception {
		return PurchasesDAORetrieval.findAllPurchases( businessId , numberFormat ) ;
	}

	private static String nameOfTheTableItems( String businessId ) {
		return "`items_".concat( businessId ).concat( "`" ) ;

	}

	private static String findAllItemsPerBusinessWithin( String businessId ) {
		return "SELECT * FROM ".concat( nameOfTheTableItems( businessId ) ) ;
	}

	public static List < Item > findAllItemssPerBusiness( String businessId ) throws Exception {
		List < Item > items = new ArrayList <>() ;
		PreparedStatement st = null ;
		ResultSet rs = null ;
		Connection connection = DBUtils.getConn() ;

		try {

			if ( nameOfTheTableItems( businessId ) == null ) {
				System.out.println( "name of table is null" ) ;
			}

			st = connection.prepareStatement( findAllItemsPerBusinessWithin( businessId ) ) ;
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
				items.add( item ) ;

			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( rs , null , st , connection ) ;
		}
		return items ;
	}

	private static String nameOfTheTableSuppliersWithinDAO( String businessId ) {
		return "`suppliers_".concat( businessId ).concat( "`" ) ;
	}

	public static List < Suppliers > findAllSuppliers( String businessId ) throws Exception {
		List < Suppliers > suppliers = new ArrayList <>() ;
		PreparedStatement st = null ;
		ResultSet rs = null ;
		Connection connection = DBUtils.getConn() ;
		try {
			st = connection.prepareStatement( "SELECT * FROM " + nameOfTheTableSuppliersWithinDAO( businessId ) ) ;
			rs = st.executeQuery() ;
			while ( rs.next() ) {
				BigDecimal id = rs.getBigDecimal( "id" ) ;
				String supplierName = rs.getString( "supplier_name" ) ;
				String supplierPhoneNumber = rs.getString( "supplier_phone_number" ) ;
				String supplierEmail = rs.getString( "supplier_email" ) ;
				String supplierLocation = rs.getString( "supplier_location" ) ;
				String supplierWebsite = rs.getString( "supplier_website" ) ;

				Suppliers s = new Suppliers( id , supplierName , supplierPhoneNumber , supplierEmail , supplierLocation , supplierWebsite ) ;
				suppliers.add( s ) ;

			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( rs , null , st , connection ) ;
		}
		return suppliers ;
	}

}
