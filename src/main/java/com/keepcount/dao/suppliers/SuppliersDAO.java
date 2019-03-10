
package com.keepcount.dao.suppliers ;

import java.math.BigDecimal ;
import java.sql.Connection ;
import java.sql.DatabaseMetaData ;
import java.sql.PreparedStatement ;
import java.sql.ResultSet ;
import java.util.ArrayList ;
import java.util.List ;

import com.keepcount.dao.dbutils.DBUtils ;
import com.keepcount.model.suppliers.Suppliers ;

public class SuppliersDAO {

	private static String nameOfTheTableSuppliersWithinDAO( String businessId ) {
		return "`suppliers_".concat( businessId ).concat( "`" ) ;
	}

	private static String createTableSupplierWithinDAO( String businessId ) {
		return "CREATE TABLE ".concat( nameOfTheTableSuppliersWithinDAO( businessId ) ).concat( "(id bigint primary key auto_increment" ).concat( ",supplier_name varchar(255)" )
				.concat( ",supplier_phone_number varchar(255) unique" ).concat( ",supplier_email varchar(255) unique" ).concat( ",supplier_location varchar(255)" )
				.concat( ",supplier_website varchar(255))" ) ;
	}

	public static int checkSuppliersTableExistence( String businessId ) throws Exception {

		DatabaseMetaData data = DBUtils.getConn().getMetaData() ;
		ResultSet rs = data.getTables( null , null , nameOfTheTableSuppliersWithinDAO( businessId ) , null ) ;
		PreparedStatement st = null ;
		int result = 0 ;
		if ( !rs.next() ) {
			st = DBUtils.getConn().prepareStatement( createTableSupplierWithinDAO( businessId ) ) ;
			result = st.executeUpdate() ;
		}
		return result ;
	}

	private static String createANewSupplierWithinDAO( String businessId ) {
		return "INSERT INTO ".concat( nameOfTheTableSuppliersWithinDAO( businessId ) )

				.concat( "(supplier_name" ).concat( ",supplier_phone_number" ).concat( ",supplier_email" ).concat( ",supplier_location" ).concat( ",supplier_website)" )
				.concat( " VALUES(?,?,?,?,?)" ) ;
	}

	public static int createANewSupplier( Suppliers suppliers , String businessId ) throws Exception {
		int result = 0 ;
		PreparedStatement st = null ;
		try {

			st = DBUtils.getConn().prepareStatement( createANewSupplierWithinDAO( businessId ) ) ;

			st.setString( 1 , suppliers.getSupplierName() ) ;
			st.setString( 2 , suppliers.getSupplierPhoneNumber() ) ;
			st.setString( 3 , suppliers.getSupplierEmail() ) ;
			st.setString( 4 , suppliers.getSupplierLocation() ) ;
			st.setString( 5 , suppliers.getSupplierWebsite() ) ;

			result = st.executeUpdate() ;

		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return result ;
	}

	public static int deleteSupplier( BigDecimal id , String businessId ) throws Exception {
		int result = 0 ;

		PreparedStatement st = null ;
		Connection connection = DBUtils.getConn() ;
		try {
			st = connection.prepareStatement( "DELETE FROM ".concat( nameOfTheTableSuppliersWithinDAO( businessId ).concat( " WHERE id=?" ) ) ) ;
			st.setBigDecimal( 1 , id ) ;
			result = st.executeUpdate() ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( null , null , st , connection ) ;
		}
		return result ;
	}

	private static String updateSupplierPertableWithin( String businessId ) {
		return "UPDATE ".concat( nameOfTheTableSuppliersWithinDAO( businessId ) ).concat( "SET supplier_name=?," ).concat( "supplier_phone_number=?," )
				.concat( "supplier_email=?," ).concat( "supplier_location=?," ).concat( "supplier_website=?" ).concat( " WHERE id=?" ) ;

	}

	public static int updateSupplier( BigDecimal id , String businessId , Suppliers supplier ) throws Exception {
		checkSuppliersTableExistence( businessId ) ;
		int result = 0 ;
		PreparedStatement st = null ;
		Connection connection = DBUtils.getConn() ;
		// User user = new User();
		try {

			st = connection.prepareStatement( updateSupplierPertableWithin( businessId ) ) ;
			st.setString( 1 , supplier.getSupplierName() ) ;
			st.setString( 2 , supplier.getSupplierPhoneNumber() ) ;
			st.setString( 3 , supplier.getSupplierEmail() ) ;
			st.setString( 4 , supplier.getSupplierLocation() ) ;
			st.setString( 5 , supplier.getSupplierWebsite() ) ;
			st.setBigDecimal( 6 , id ) ;

			result = st.executeUpdate() ;

		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( null , null , st , connection ) ;
		}
		return result ;
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
