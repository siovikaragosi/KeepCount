
package com.keepcount.dao.item ;

import java.math.BigDecimal ;
import java.sql.CallableStatement ;
import java.sql.Connection ;
import java.util.List ;

import com.keepcount.dao.dbutils.DBUtils ;

public class UnitOfMeasurementDAODeletion {

	private static List < String > listOfAllProceduresCreated ;

	public static List < String > getListOfAllProceduresCreated() {
		return listOfAllProceduresCreated ;
	}

	public static void setListOfAllProceduresCreated( List < String > listOfAllProceduresCreated ) {
		UnitOfMeasurementDAODeletion.listOfAllProceduresCreated = listOfAllProceduresCreated ;
	}

	private static List < String > listOfAllProceduresAlreadyCreated() throws Exception {
		setListOfAllProceduresCreated( DBUtils.listOfAllProceduresAlreadyCreated() ) ;
		return getListOfAllProceduresCreated() ;
	}

	private static String procName = "delete_from_measurement_units_" ;
	private static String tableName = "measurement_units_" ;
	private static String inParams = "in in_id bigint" ;
	private static String whereCondition = " WHERE id=in_id" ;

	private static int delete_from_measurement_units_Existence ;

	public static int getDelete_from_measurement_units_Existence() {
		return delete_from_measurement_units_Existence ;
	}

	public static void setDelete_from_measurement_units_Existence( int delete_from_measurement_units_Existence ) {
		UnitOfMeasurementDAODeletion.delete_from_measurement_units_Existence = delete_from_measurement_units_Existence ;
	}

	public static int deleteFromMeasurementUnits( String businessId , BigDecimal id ) throws Exception {

		String procedureName = procName.concat( businessId ) ;

		if ( getListOfAllProceduresCreated() == null ) {
			listOfAllProceduresAlreadyCreated() ;
		}

		if ( !getListOfAllProceduresCreated().contains( procedureName ) ) {
			DBUtils.createDeletionProc( procName , businessId , tableName , inParams , whereCondition , getDelete_from_measurement_units_Existence() ) ;
			setDelete_from_measurement_units_Existence( 1 ) ;
		} else {
			setDelete_from_measurement_units_Existence( 1 ) ;
		}

		// DBUtils.createDeletionProc( procName , businessId , tableName , inParams ,
		// whereCondition ) ;

		int result = 0 ;
		CallableStatement cs = null ;
		Connection connection = DBUtils.getConn() ;
		connection.setAutoCommit( false ) ;
		try {
			cs = connection.prepareCall( "{CALL delete_from_measurement_units_".concat( businessId ).concat( "(?)" ).concat( "}" ) ) ;
			cs.setBigDecimal( 1 , id ) ;

			// System.out.println("ms: " + measurement);

			if ( businessId != null ) {
				result = cs.executeUpdate() ;
				System.out.println( "res dao ins: " + result ) ;
				if ( result == 1 ) {
					connection.commit() ;
				} else {
					connection.rollback() ;
				}
			}

		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( null , cs , null , connection ) ;
		}
		return result ;
	}

}
