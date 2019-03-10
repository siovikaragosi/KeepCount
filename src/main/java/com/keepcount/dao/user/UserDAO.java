
package com.keepcount.dao.user ;

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
import com.keepcount.model.user.User ;

public class UserDAO {

	private static String nameOfTheTableUsers( String businessId ) {
		return "`users_".concat( businessId ).concat( "`" ) ;

	}

	private static String createTableUsers( String businessId ) {
		return "CREATE TABLE ".concat( nameOfTheTableUsers( businessId ) ).concat( "(id bigint primary key auto_increment," ).concat( "user_first_name varchar(255)," )
				.concat( "user_last_name varchar(255)," ).concat( "user_phone_number varchar(255)," ).concat( "user_email varchar(255)," ).concat( "user_nin varchar(255)," )
				.concat( "user_photo longblob" ).concat( ",user_image_base64 longtext)" ) ;
	}

	private static String createNewUserWithin( String businessId ) {
		return "INSERT INTO ".concat( nameOfTheTableUsers( businessId ) ).concat( " (user_first_name" ).concat( ",user_last_name" ).concat( ",user_phone_number" )
				.concat( ",user_email" ).concat( ",user_nin" ).concat( ",user_photo) " ).concat( " VALUES(?,?,?,?,?,?)" ) ;
	}

	private static String findAllUsersPerBusinessWithin( String businessId ) {
		return "SELECT * FROM ".concat( nameOfTheTableUsers( businessId ) ) ;
	}

	private static String findAllUserPerBusinessByEmailWithin( String businessId ) {
		return "SELECT * FROM ".concat( nameOfTheTableUsers( businessId ) ).concat( " WHERE email=?" ) ;

	}

	private static String updateUerPertableWithin( String businessId ) {
		return "UPDATE ".concat( nameOfTheTableUsers( businessId ) ).concat( "SET user_first_name=?," ).concat( "user_last_name=?," ).concat( "user_phone_number=?," )
				.concat( "user_email=?," ).concat( "user_nin=?," ).concat( "user_photo=?" ).concat( " WHERE id=?" ) ;

	}

	private static String deleteUserPerBusinessWithin( String businessId ) {
		return "DELETE FROM ".concat( nameOfTheTableUsers( businessId ) ).concat( "WHERE id=?" ) ;
	}

	public static int checkUserTableExistence( String businessId ) throws Exception {
		int result = 0 ;

		DatabaseMetaData data = DBUtils.getConn().getMetaData() ;
		ResultSet rs = data.getTables( null , null , nameOfTheTableUsers( businessId ) , null ) ;
		PreparedStatement st = null ;
		Connection connection = DBUtils.getConn() ;
		try {
			if ( !rs.next() ) {
				st = connection.prepareStatement( createTableUsers( businessId ) ) ;
				result = st.executeUpdate() ;
			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( rs , null , st , connection ) ;
		}
		return result ;
	}

	public static int createNewUser( User user , String businessId ) throws Exception {
		checkUserTableExistence( businessId ) ;
		int result = 0 ;
		PreparedStatement st = null ;
		Connection connection = DBUtils.getConn() ;
		try {

			st = connection.prepareStatement( createNewUserWithin( businessId ) ) ;

			String imagePath = user.getImagePathBase64() ;

			if ( imagePath != null ) {
				st.setString( 1 , user.getUserFirstName() ) ;
				st.setString( 2 , user.getUserLastName() ) ;
				st.setString( 3 , user.getUserPhoneNumber() ) ;
				st.setString( 4 , user.getUserEmail() ) ;
				st.setString( 5 , user.getUserNIN() ) ;
				String splitted = imagePath.split( "," ) [ 1 ] ;

				byte [ ] imageByteArray = Base64.getDecoder().decode( splitted ) ;
				InputStream inputStream = new ByteArrayInputStream( imageByteArray ) ;
				st.setBinaryStream( 6 , inputStream ) ;
			} else {
				st.setString( 1 , user.getUserFirstName() ) ;
				st.setString( 2 , user.getUserLastName() ) ;
				st.setString( 3 , user.getUserPhoneNumber() ) ;
				st.setString( 4 , user.getUserEmail() ) ;
				st.setString( 5 , user.getUserNIN() ) ;
			}

			result = st.executeUpdate() ;

		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( null , null , st , connection ) ;
		}
		return result ;
	}

	public static int updateUser( BigDecimal id , String businessId , User user ) throws Exception {
		checkUserTableExistence( businessId ) ;
		int result = 0 ;
		PreparedStatement st = null ;
		Connection connection = DBUtils.getConn() ;
		// User user = new User();
		try {

			st = connection.prepareStatement( updateUerPertableWithin( businessId ) ) ;
			st.setString( 1 , user.getUserFirstName() ) ;
			st.setString( 2 , user.getUserLastName() ) ;
			st.setString( 3 , user.getUserPhoneNumber() ) ;
			st.setString( 4 , user.getUserEmail() ) ;
			st.setString( 5 , user.getUserNIN() ) ;

			String splitted = user.getImagePathBase64().split( "," ) [ 1 ] ;
			byte [ ] imageByteArray = Base64.getDecoder().decode( splitted ) ;
			InputStream inputStream = new ByteArrayInputStream( imageByteArray ) ;

			st.setBinaryStream( 6 , inputStream ) ;

			st.setBigDecimal( 7 , id ) ;

			result = st.executeUpdate() ;

		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( null , null , st , connection ) ;
		}
		return result ;
	}

	public static int deleteUserByIdAndEmail( BigDecimal id , String businessId ) throws Exception {
		int result = 0 ;
		PreparedStatement st = null ;
		Connection connection = DBUtils.getConn() ;
		try {
			st = connection.prepareStatement( deleteUserPerBusinessWithin( businessId ) ) ;
			st.setBigDecimal( 1 , id ) ;
			result = st.executeUpdate() ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( null , null , st , connection ) ;
		}
		return result ;
	}

	public static User findUserById( BigDecimal id , String businessId ) throws Exception {
		User user = new User() ;
		PreparedStatement st = null ;
		ResultSet rs = null ;
		Connection connection = DBUtils.getConn() ;
		try {
			st = connection.prepareStatement( "SELECT * FROM " + nameOfTheTableUsers( businessId ) + " WHERE id=?" ) ;
			st.setBigDecimal( 1 , id ) ;
			rs = st.executeQuery() ;
			while ( rs.next() ) {
				BigDecimal iD = rs.getBigDecimal( "id" ) ;
				String userFirstName = rs.getString( "user_first_name" ) ;
				String userLastName = rs.getString( "user_last_name" ) ;
				String userPhoneNumber = rs.getString( "user_phone_number" ) ;
				String userEmail = rs.getString( "user_email" ) ;
				String userNIN = rs.getString( "user_nin" ) ;

				Blob blob = rs.getBlob( "user_photo" ) ;

				byte [ ] bytes = new byte [ ( int ) blob.length() ] ;
				bytes = blob.getBytes( 1 , ( int ) blob.length() ) ;

				String image = Base64.getEncoder().encodeToString( bytes ) ;
				// String pre = "data:image/jpeg;base64,";
				String pre = "data:image/png;base64," ;
				String imagePathBase64 = pre.concat( image ) ;

				User user1 = new User( iD , userFirstName , userLastName , userPhoneNumber , userEmail , userNIN , imagePathBase64 ) ;

				user = user1 ;

			}

		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( rs , null , st , connection ) ;
		}
		return user ;
	}

	public static void main( String [ ] args ) throws Exception {

		// System.out.println(findAllUsersPerBusiness());
	}

	public static List < User > findAllUsersPerBusiness( String businessId ) throws Exception {
		List < User > users = new ArrayList <>() ;
		PreparedStatement st = null ;
		ResultSet rs = null ;
		Connection connection = DBUtils.getConn() ;
		try {

			// System.out.println("Email and biz name from dao: " +
			// chooseBusinessController.getNameOfTheBusiness()
			// .concat(chooseBusinessController.getEmailOfThisBusiness()));

			if ( nameOfTheTableUsers( businessId ) == null ) {
				System.out.println( "name of table is null" ) ;
			}

			st = connection.prepareStatement( findAllUsersPerBusinessWithin( businessId ) ) ;
			rs = st.executeQuery() ;
			while ( rs.next() ) {
				BigDecimal id = rs.getBigDecimal( "id" ) ;
				String userFirstName = rs.getString( "user_first_name" ) ;
				String userLastName = rs.getString( "user_last_name" ) ;
				String userPhoneNumber = rs.getString( "user_phone_number" ) ;
				String userEmail = rs.getString( "user_email" ) ;
				String userNIN = rs.getString( "user_nin" ) ;
				Blob blob = rs.getBlob( "user_photo" ) ;

				byte [ ] bytes = new byte [ ( int ) blob.length() ] ;
				bytes = blob.getBytes( 1 , ( int ) blob.length() ) ;

				String image = Base64.getEncoder().encodeToString( bytes ) ;
				// String pre = "data:image/jpeg;base64,";
				String pre = "data:image/png;base64," ;
				String imagePathBase64 = pre.concat( image ) ;

				User user = new User( id , userFirstName , userLastName , userPhoneNumber , userEmail , userNIN , imagePathBase64 ) ;
				users.add( user ) ;

				// System.out.println("dao: " + users);
				// User user = new User(id, userFirstName, userLastName, userPhoneNumber,
				// userEmail, userNIN);
				// users.add(user);
			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( rs , null , st , connection ) ;
		}
		return users ;
	}

	public static List < User > findAUserPerBusinessByID( String email , String businessId ) throws Exception {
		List < User > users = new ArrayList <>() ;
		PreparedStatement st = null ;
		ResultSet rs = null ;
		Connection connection = DBUtils.getConn() ;
		try {
			st = connection.prepareStatement( findAllUserPerBusinessByEmailWithin( businessId ) ) ;
			st.setString( 1 , email ) ;
			rs = st.executeQuery() ;
			while ( rs.next() ) {
				BigDecimal id = rs.getBigDecimal( "id" ) ;
				String userFirstName = rs.getString( "user_first_name" ) ;
				String userLastName = rs.getString( "user_last_name" ) ;
				String userPhoneNumber = rs.getString( "user_phone_number" ) ;
				String userEmail = rs.getString( "user_email" ) ;
				String userNIN = rs.getString( "user_nin" ) ;
				// User user = new User(id, userFirstName, userLastName, userPhoneNumber,
				// userEmail, userNIN);
				// users.add(user);
			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( rs , null , st , connection ) ;
		}
		return users ;
	}

}
