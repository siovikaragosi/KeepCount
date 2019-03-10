
package com.keepcount.dao.customers ;

import java.math.BigDecimal ;
import java.sql.Connection ;
import java.sql.DatabaseMetaData ;
import java.sql.PreparedStatement ;
import java.sql.ResultSet ;
import java.util.ArrayList ;
import java.util.List ;

import com.keepcount.dao.dbutils.DBUtils ;
import com.keepcount.model.customers.Customers ;

public class CustomersDAO {

	private static String nameOfTheTableCustomersWithinDAO( String businessId ) {
		return "`customers_".concat( businessId ).concat( "`" ) ;
	}

	public static int checkTableCustomersExistence( String businessId ) throws Exception {
		DatabaseMetaData data = DBUtils.getConn().getMetaData() ;
		ResultSet rs = data.getTables( null , null , nameOfTheTableCustomersWithinDAO( businessId ) , null ) ;
		PreparedStatement st = null ;
		Connection connection = DBUtils.getConn() ;
		int result = 0 ;
		try {
			if ( !rs.next() ) {
				st = connection.prepareStatement( createTableCustomers( businessId ) ) ;
				result = st.executeUpdate() ;
			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( rs , null , st , connection ) ;
		}
		return result ;
	}

	// private BigDecimal id;
	// private String customerName;
	// private String customerPhoneNumber;
	// private String customerEmail;
	// private String customerLocation;

	private static String createTableCustomers( String businessId ) throws Exception {
		return "CREATE TABLE ".concat( nameOfTheTableCustomersWithinDAO( businessId ).concat( " (id bigint primary key auto_increment" )

				// 1
				.concat( ",customer_name varchar(255)" )
				// 2
				.concat( ",customer_phone_number varchar(255) unique" )
				// 3
				.concat( ",customer_email varchar(255) unique" )
				// 4
				.concat( ",customer_location varchar(255)" )
				// close
				.concat( ")" )

		) ;
	}

	private static String createNewCustomerWithin( String businessId ) throws Exception {
		return "INSERT INTO ".concat( nameOfTheTableCustomersWithinDAO( businessId )

				.concat( "(" )
				// 1
				.concat( "customer_name" )
				// 2
				.concat( ",customer_phone_number" )
				// 3
				.concat( ",customer_email" )
				// 4
				.concat( ",customer_location" )

				.concat( ")" )

				// VALUES
				.concat( " VALUES(?,?,?,?)" )

		) ;
	}

	public static int createNewCustomer( Customers customers , String businessId ) throws Exception {
		int result = 0 ;
		PreparedStatement st = null ;
		Connection connection = DBUtils.getConn() ;
		try {
			st = DBUtils.getConn().prepareStatement( createNewCustomerWithin( businessId ) ) ;
			st.setString( 1 , customers.getCustomerName() ) ;
			st.setString( 2 , customers.getCustomerPhoneNumber() ) ;
			st.setString( 3 , customers.getCustomerEmail() ) ;
			st.setString( 4 , customers.getCustomerLocation() ) ;
			result = st.executeUpdate() ;
			System.out.println( "result cust dao: " + result ) ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( null , null , st , connection ) ;
		}
		return result ;
	}

	private static String updateCustomerWithin( String businessId ) throws Exception {
		return "UPDATE ".concat( nameOfTheTableCustomersWithinDAO( businessId ).concat( " SET " )
				// 1
				.concat( "customer_name=?" )
				// 2
				.concat( ",customer_phone_number=?" )
				// 3
				.concat( ",customer_email=?" )
				// 4
				.concat( ",customer_location=?" )
				// 5
				.concat( " WHERE id=?" )

		) ;
	}

	public static int deleteCustomer( String businessId , BigDecimal id ) throws Exception {
		int result = 0 ;
		PreparedStatement st = null ;
		Connection connection = DBUtils.getConn() ;
		try {
			st = connection.prepareStatement( "DELETE FROM ".concat( nameOfTheTableCustomersWithinDAO( businessId ).concat( "WHERE id=?" ) ) ) ;
			st.setBigDecimal( 1 , id ) ;
			result = st.executeUpdate() ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( null , null , st , connection ) ;
		}
		return result ;
	}

	public static int updateCustomer( String businessId , Customers customers , BigDecimal id ) throws Exception {
		int result = 0 ;
		PreparedStatement st = null ;
		Connection connection = DBUtils.getConn() ;
		try {
			st = connection.prepareStatement( updateCustomerWithin( businessId ) ) ;
			st.setString( 1 , customers.getCustomerName() ) ;
			st.setString( 2 , customers.getCustomerPhoneNumber() ) ;
			st.setString( 3 , customers.getCustomerEmail() ) ;
			st.setString( 4 , customers.getCustomerLocation() ) ;
			st.setBigDecimal( 5 , id ) ;
			result = st.executeUpdate() ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( null , null , st , connection ) ;
		}
		return result ;
	}

	public static List < Customers > findAllCustomers( String businessId ) throws Exception {
		List < Customers > customerss = new ArrayList <>() ;
		PreparedStatement st = null ;
		ResultSet rs = null ;
		Connection connection = DBUtils.getConn() ;
		try {
			st = connection.prepareStatement( "SELECT * FROM ".concat( nameOfTheTableCustomersWithinDAO( businessId ) ) ) ;
			rs = st.executeQuery() ;
			while ( rs.next() ) {

				BigDecimal id = rs.getBigDecimal( "id" ) ;
				String customerName = rs.getString( "customer_name" ) ;
				String customerPhoneNumber = rs.getString( "customer_phone_number" ) ;
				String customerEmail = rs.getString( "customer_email" ) ;
				String customerLocation = rs.getString( "customer_location" ) ;
				Customers customers = new Customers( id , customerName , customerPhoneNumber , customerEmail , customerLocation ) ;
				customerss.add( customers ) ;
			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( rs , null , st , connection ) ;
		}
		return customerss ;
	}

}
