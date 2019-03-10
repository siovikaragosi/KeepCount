
package com.keepcount.dao.item ;

import java.sql.CallableStatement ;
import java.sql.Connection ;
import java.util.LinkedHashMap ;
import java.util.List ;
import java.util.Map ;

import com.keepcount.dao.dbutils.DBUtils ;
import com.keepcount.dao.dbutils.DBUtilsTypes ;
import com.keepcount.model.item.UnitOfMeasurement ;

public class UnitOfMeasurementDAOInsertion {

	private static List < String > listOfAllProceduresCreated ;

	public static List < String > getListOfAllProceduresCreated() {
		return listOfAllProceduresCreated ;
	}

	public static void setListOfAllProceduresCreated( List < String > listOfAllProceduresCreated ) {
		UnitOfMeasurementDAOInsertion.listOfAllProceduresCreated = listOfAllProceduresCreated ;
	}

	private static List < String > listOfAllProceduresAlreadyCreated() throws Exception {
		setListOfAllProceduresCreated( DBUtils.listOfAllProceduresAlreadyCreated() ) ;
		return getListOfAllProceduresCreated() ;
	}

	private static String procName = "insert_into_measurement_units_" ;
	private static String tableName = "measurement_units_" ;

	private static Map < String , DBUtilsTypes > findMappingForInsertionParameters() {

		Map < String , DBUtilsTypes > map = new LinkedHashMap <>() ;

		map.put( "item_id" , new DBUtilsTypes( "item_id" , "varchar(255)" ) ) ;
		map.put( "unit_of_measurement" , new DBUtilsTypes( "unit_of_measurement" , "varchar(255)" ) ) ;

		return map ;

	}

	private static int procExisst = 0 ;

	public static int getProcExisst() {

		return procExisst ;
	}

	public static void setProcExisst( int procExisst ) {

		UnitOfMeasurementDAOInsertion.procExisst = procExisst ;
	}

	private static int insert_into_measurement_units_Existence ;

	public static int getInsert_into_measurement_units_Existence() {
		return insert_into_measurement_units_Existence ;
	}

	public static void setInsert_into_measurement_units_Existence( int insert_into_measurement_units_Existence ) {
		UnitOfMeasurementDAOInsertion.insert_into_measurement_units_Existence = insert_into_measurement_units_Existence ;
	}

	public static int newUnitOfMeasurement( UnitOfMeasurement measurement , String businessId ) throws Exception {

		String procedureName = procName.concat( businessId ) ;

		if ( getListOfAllProceduresCreated() == null ) {
			listOfAllProceduresAlreadyCreated() ;
		}

		if ( !getListOfAllProceduresCreated().contains( procedureName ) ) {
			DBUtils.createInsertionProc( procName , businessId , tableName , findMappingForInsertionParameters() , getInsert_into_measurement_units_Existence() ) ;
			setInsert_into_measurement_units_Existence( 1 ) ;
		} else {
			setInsert_into_measurement_units_Existence( 1 ) ;
		}

		// DBUtils.createInsertionProc( procName , businessId , tableName ,
		// findMappingForInsertionParameters() ) ;

		int result = 0 ;
		CallableStatement cs = null ;
		Connection connection = DBUtils.getConn() ;
		connection.setAutoCommit( false ) ;
		try {
			cs = connection.prepareCall( DBUtils.invocationOfInsertAPI( findMappingForInsertionParameters() , procName , businessId ) ) ;
			cs.setBigDecimal( 1 , measurement.getItemId() ) ;
			cs.setString( 2 , measurement.getUnitOfMeasurement() ) ;

			System.out.println( "ms: " + measurement ) ;

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
