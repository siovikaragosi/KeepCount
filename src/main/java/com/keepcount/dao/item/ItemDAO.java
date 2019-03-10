
package com.keepcount.dao.item ;

import java.io.ByteArrayInputStream ;
import java.io.InputStream ;
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

public class ItemDAO {

	private static String nameOfTheTableItems( String businessId ) {
		return "`items_".concat( businessId ).concat( "`" ) ;

	}

	private static String createTableItems( String businessId ) {
		return "CREATE TABLE ".concat( nameOfTheTableItems( businessId ) )

				.concat( "(id bigint primary key auto_increment" )
				// 1
				.concat( ",item_name varchar(255)" )
				// 2
				.concat( ",item_category varchar(255)" )
				// 3
				.concat( ",item_sub_category varchar(255) unique" )
				// 4
				.concat( ",item_description longtext" )
				// 5
				.concat( ",item_sample_photo_one longblob" )
				// 6
				.concat( ",item_sample_photo_two longblob" )
				// 7
				.concat( ",item_sample_photo_three longblob)" ) ;
	}

	private static String createNewItemWithin( String businessId ) {
		return "INSERT INTO ".concat( nameOfTheTableItems( businessId ) ).
		// 1
				concat( " (item_name" )
				// 2
				.concat( ",item_category" )
				// 3
				.concat( ",item_sub_category" )
				// 4
				.concat( ",item_description" )
				// 5
				.concat( ",item_sample_photo_one" )
				// 6
				.concat( ",item_sample_photo_two" )
				// 7
				.concat( ",item_sample_photo_three)" )
				// VALUES (PLCAEHOLDERS)
				.concat( " VALUES(?,?,?,?,?,?,?)" ) ;
	}

	private static String findAllItemsPerBusinessWithin( String businessId ) {
		return "SELECT * FROM ".concat( nameOfTheTableItems( businessId ) ) ;
	}

	private static String updateItemPertableWithin( String businessId ) {
		return "UPDATE ".concat( nameOfTheTableItems( businessId ) )
				// 1
				.concat( "SET item_name=?" )
				// 2
				.concat( ",item_category=?" )
				// 3
				.concat( ",item_sub_category=?" )
				// 4
				.concat( ",item_description=?" )
				// 5
				.concat( ",item_sample_photo_one=?" )
				// 6
				.concat( ",item_sample_photo_two=?" )
				// 7
				.concat( ",item_sample_photo_three=?" )
				// 8
				.concat( " WHERE id=?" ) ;

	}

	private static String deleteItemPerBusinessWithin( String businessId ) {
		return "DELETE FROM ".concat( nameOfTheTableItems( businessId ) ).concat( "WHERE id=?" ) ;
	}

	public static int checkItemsTableExistence( String businessId ) throws Exception {
		int result = 0 ;

		DatabaseMetaData data = DBUtils.getConn().getMetaData() ;
		ResultSet rs = data.getTables( null , null , nameOfTheTableItems( businessId ) , null ) ;
		PreparedStatement st = null ;
		Connection connection = DBUtils.getConn() ;
		try {
			if ( !rs.next() ) {
				st = connection.prepareStatement( createTableItems( businessId ) ) ;
				result = st.executeUpdate() ;
			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( rs , null , st , connection ) ;
		}
		return result ;
	}

	public static int createNewItem( Item item , String businessId ) throws Exception {
		checkItemsTableExistence( businessId ) ;
		int result = 0 ;
		PreparedStatement st = null ;
		Connection connection = DBUtils.getConn() ;
		try {

			st = connection.prepareStatement( createNewItemWithin( businessId ) ) ;

			st.setString( 1 , item.getItemName() ) ;
			st.setString( 2 , item.getItemCategory() ) ;
			st.setString( 3 , item.getItemSubCategory() ) ;
			st.setString( 4 , item.getItemDescription() ) ;

			String splitteditemSamplePhotoOne = item.getItemSamplePhotoOne() ;
			if ( splitteditemSamplePhotoOne != null ) {
				splitteditemSamplePhotoOne = item.getItemSamplePhotoOne().split( "," ) [ 1 ] ;
				byte [ ] byteArrayItemSamplePhotoOne = Base64.getDecoder().decode( splitteditemSamplePhotoOne ) ;
				InputStream inputStreamItemSamplePhotoOne = new ByteArrayInputStream( byteArrayItemSamplePhotoOne ) ;
				st.setBinaryStream( 5 , inputStreamItemSamplePhotoOne ) ;
			} else {
				st.setBinaryStream( 5 , null ) ;
			}

			String splitteditemSamplePhotoTwo = item.getItemSamplePhotoTwo() ;
			if ( splitteditemSamplePhotoTwo != null ) {
				splitteditemSamplePhotoTwo = item.getItemSamplePhotoTwo().split( "," ) [ 1 ] ;
				byte [ ] byteArrayItemSamplePhotoTwo = Base64.getDecoder().decode( splitteditemSamplePhotoTwo ) ;
				InputStream inputStreamItemSamplePhotoTwo = new ByteArrayInputStream( byteArrayItemSamplePhotoTwo ) ;
				st.setBinaryStream( 6 , inputStreamItemSamplePhotoTwo ) ;
			} else {
				st.setBinaryStream( 6 , null ) ;
			}

			String splitteditemSamplePhotoThree = item.getItemSamplePhotoThree() ;
			if ( splitteditemSamplePhotoThree != null ) {
				splitteditemSamplePhotoThree = item.getItemSamplePhotoThree().split( "," ) [ 1 ] ;
				byte [ ] byteArrayItemSamplePhotoThree = Base64.getDecoder().decode( splitteditemSamplePhotoThree ) ;
				InputStream inputStreamItemSamplePhotoThree = new ByteArrayInputStream( byteArrayItemSamplePhotoThree ) ;
				st.setBinaryStream( 7 , inputStreamItemSamplePhotoThree ) ;
			} else {
				st.setBinaryStream( 7 , null ) ;
			}

			result = st.executeUpdate() ;

		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( null , null , st , connection ) ;
		}
		return result ;
	}

	public static int updateItem( BigDecimal id , String businessId , Item item ) throws Exception {
		checkItemsTableExistence( businessId ) ;
		int result = 0 ;
		PreparedStatement st = null ;
		Connection connection = DBUtils.getConn() ;
		try {

			st = connection.prepareStatement( updateItemPertableWithin( businessId ) ) ;
			st.setString( 1 , item.getItemName() ) ;
			st.setString( 2 , item.getItemCategory() ) ;
			st.setString( 3 , item.getItemSubCategory() ) ;
			st.setString( 4 , item.getItemDescription() ) ;

			String splitteditemSamplePhotoOne = item.getItemSamplePhotoOne() ;
			if ( splitteditemSamplePhotoOne != null ) {
				splitteditemSamplePhotoOne = item.getItemSamplePhotoOne().split( "," ) [ 1 ] ;
				byte [ ] byteArrayItemSamplePhotoOne = Base64.getDecoder().decode( splitteditemSamplePhotoOne ) ;
				InputStream inputStreamItemSamplePhotoOne = new ByteArrayInputStream( byteArrayItemSamplePhotoOne ) ;
				st.setBinaryStream( 5 , inputStreamItemSamplePhotoOne ) ;
			} else {
				st.setBinaryStream( 5 , null ) ;
			}

			String splitteditemSamplePhotoTwo = item.getItemSamplePhotoTwo() ;
			if ( splitteditemSamplePhotoTwo != null ) {
				splitteditemSamplePhotoTwo = item.getItemSamplePhotoTwo().split( "," ) [ 1 ] ;
				byte [ ] byteArrayItemSamplePhotoTwo = Base64.getDecoder().decode( splitteditemSamplePhotoTwo ) ;
				InputStream inputStreamItemSamplePhotoTwo = new ByteArrayInputStream( byteArrayItemSamplePhotoTwo ) ;
				st.setBinaryStream( 6 , inputStreamItemSamplePhotoTwo ) ;
			} else {
				st.setBinaryStream( 6 , null ) ;
			}

			String splitteditemSamplePhotoThree = item.getItemSamplePhotoThree() ;
			if ( splitteditemSamplePhotoThree != null ) {
				splitteditemSamplePhotoThree = item.getItemSamplePhotoThree().split( "," ) [ 1 ] ;
				byte [ ] byteArrayItemSamplePhotoThree = Base64.getDecoder().decode( splitteditemSamplePhotoThree ) ;
				InputStream inputStreamItemSamplePhotoThree = new ByteArrayInputStream( byteArrayItemSamplePhotoThree ) ;
				st.setBinaryStream( 7 , inputStreamItemSamplePhotoThree ) ;
			} else {
				st.setBinaryStream( 7 , null ) ;
			}

			st.setBigDecimal( 8 , id ) ;

			result = st.executeUpdate() ;

		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( null , null , st , connection ) ;
		}
		return result ;
	}

	public static int deleteItemById( BigDecimal id , String businessId ) throws Exception {
		int result = 0 ;
		PreparedStatement st = null ;
		Connection connection = DBUtils.getConn() ;
		try {
			st = connection.prepareStatement( deleteItemPerBusinessWithin( businessId ) ) ;
			st.setBigDecimal( 1 , id ) ;
			result = st.executeUpdate() ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( null , null , st , connection ) ;
		}
		return result ;
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

}
