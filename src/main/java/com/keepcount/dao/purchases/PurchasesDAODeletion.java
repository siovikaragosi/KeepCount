
package com.keepcount.dao.purchases ;

import java.math.BigDecimal ;
import java.sql.CallableStatement ;
import java.sql.Connection ;
import java.sql.PreparedStatement ;

import com.keepcount.dao.dbutils.DBUtils ;

public class PurchasesDAODeletion {

	private static String nameOfTheTablePurchasesWithinDAO( String businessId ) {
		return "`purchases_".concat( businessId ).concat( "`" ) ;
	}

	private static String nameOfTheDeletionProcedureCreated( String businessId ) {
		return "delete_from_".concat( nameOfTheTablePurchasesWithinDAO( businessId ).replaceAll( "`" , "" ) ) ;
	}

	private static String creationStringProviderForDeletion( String businessId ) {
		String storedProc = "create procedure ".concat( nameOfTheDeletionProcedureCreated( businessId ) ).concat( "(" ).concat( "in id_in bigint" ).concat( ")" ).concat( " " )
				.concat( "begin " ).concat( "delete from " ).concat( nameOfTheTablePurchasesWithinDAO( businessId ) ).concat( " where id=id_in" ).concat( ";" ).concat( " end" ) ;
		return storedProc ;
	}

	public static void createProcedureToDeletionPurchases( String businessId ) throws Exception {
		PreparedStatement ps = null ;
		String dbName = businessId ;
		Connection connection = DBUtils.getConn() ;
		try {
			ps = connection.prepareStatement( creationStringProviderForDeletion( businessId ) ) ;
			if ( dbName != null ) {
				ps.executeUpdate() ;
				System.out.println( "insertion proc created" ) ;
			} else {
				System.out.println( "database null, insertion proc not created" ) ;
			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( null , null , ps , connection ) ;
		}
	}

	public static int deleteFromPurchases( String businessId , BigDecimal id ) throws Exception {
		createProcedureToDeletionPurchases( businessId ) ;
		int result = 0 ;
		CallableStatement ct = null ;
		Connection connection = DBUtils.getConn() ;
		try {
			connection.setAutoCommit( false ) ;
			ct = connection.prepareCall( "".concat( "{call delete_from_purchases_" ).concat( ( businessId ) ).concat( "(?)" ).concat( "}" ) ) ;
			ct.setBigDecimal( 1 , id ) ;
			result = ct.executeUpdate() ;
			if ( result > 1 ) {
				connection.rollback() ;
			} else {
				connection.commit() ;
			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( null , ct , null , connection ) ;
		}
		return result ;
	}

}
