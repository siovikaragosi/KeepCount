
package com.keepcount.dao.business.create ;

import java.math.BigDecimal ;
import java.sql.Connection ;
import java.sql.PreparedStatement ;
import java.sql.ResultSet ;
import java.util.ArrayList ;
import java.util.List ;

import org.springframework.security.crypto.bcrypt.BCrypt ;

import com.keepcount.dao.dbutils.DBUtils ;
import com.keepcount.model.business.create.EmailsOfABusinessLogin ;

public class EmailsOfABusinessDAO {

	private static final String ADD_NEW_BUSINESS_EMAIL = "INSERT INTO emails_of_a_business(email, password, business_name, user_name) VALUES(?,?,?,?) " ;

	private static final String FIND_ALL_BUSINESS_OF_THIS_EMAIL = "SELECT * FROM emails_of_a_business WHERE email=?" ;

	private static final String DELETE_EMAIL_OF_A_BUSINESS = "DELETE FROM emails_of_a_business WHERE email=? AND id=? AND business_name=?" ;

	private static final String UPDATE_EMAIL_OF_A_BUSINESS = "UPDATE emails_of_a_business SET email=?, password=?, user_name=? WHERE id=?" ;

	public static int addNewEmailForABusiness( EmailsOfABusinessLogin emails ) throws Exception {
		int result = 0 ;
		PreparedStatement st = null ;
		Connection connection = DBUtils.getConn() ;
		try {
			st = connection.prepareStatement( ADD_NEW_BUSINESS_EMAIL ) ;
			st.setString( 1 , emails.getEmail() ) ;
			st.setString( 2 , BCrypt.hashpw( emails.getPassword() , BCrypt.gensalt() ) ) ;
			st.setString( 3 , emails.getBusinessName() ) ;
			st.setString( 4 , emails.getUserName() ) ;
			result = st.executeUpdate() ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( null , null , st , connection ) ;
		}
		return result ;
	}

	public static int updateEmailOfABusiness( BigDecimal id ) throws Exception {
		int result = 0 ;
		PreparedStatement st = null ;
		Connection connection = DBUtils.getConn() ;
		try {
			st = connection.prepareStatement( UPDATE_EMAIL_OF_A_BUSINESS ) ;
			st.setBigDecimal( 1 , id ) ;
			result = st.executeUpdate() ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( null , null , st , connection ) ;
		}
		return result ;
	}

	public static int deleteEmailFromABusiness( String email , BigDecimal id , String businessName ) throws Exception {
		int result = 0 ;
		PreparedStatement st = null ;
		Connection connection = DBUtils.getConn() ;
		try {
			st = connection.prepareStatement( DELETE_EMAIL_OF_A_BUSINESS ) ;
			st.setString( 1 , email ) ;
			st.setBigDecimal( 2 , id ) ;
			st.setString( 3 , businessName ) ;
			result = st.executeUpdate() ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( null , null , st , connection ) ;
		}
		return result ;
	}

	public static List < EmailsOfABusinessLogin > findAllBusinessesOfThisEmail( String email ) throws Exception {
		List < EmailsOfABusinessLogin > businesses = new ArrayList <>() ;
		PreparedStatement st = null ;
		ResultSet rs = null ;
		Connection connection = DBUtils.getConn() ;
		try {
			st = connection.prepareStatement( FIND_ALL_BUSINESS_OF_THIS_EMAIL ) ;
			st.setString( 1 , email ) ;
			rs = st.executeQuery() ;
			while ( rs.next() ) {
				BigDecimal id = rs.getBigDecimal( "id" ) ;
				String emaiL = rs.getString( "email" ) ;
				String password = rs.getString( "password" ) ;
				String businessName = rs.getString( "business_name" ) ;
				String userName = rs.getString( "user_name" ) ;
				EmailsOfABusinessLogin business = new EmailsOfABusinessLogin( id , emaiL , password , businessName , userName ) ;
				businesses.add( business ) ;
			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( rs , null , st , connection ) ;
		}
		return businesses ;
	}

	public static void main( String [ ] args ) throws Exception {
		List < EmailsOfABusinessLogin > list = new ArrayList <>() ;
		list = findAllBusinessesOfThisEmail( "alimahmoudraage@gmail.com" ) ;
		System.out.println( list ) ;
	}
}
