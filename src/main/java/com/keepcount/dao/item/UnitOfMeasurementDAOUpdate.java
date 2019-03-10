
package com.keepcount.dao.item ;

import java.math.BigDecimal ;
import java.sql.CallableStatement ;
import java.sql.Connection ;
import java.util.ArrayList ;
import java.util.LinkedHashMap ;
import java.util.List ;
import java.util.Map ;

import com.keepcount.dao.dbutils.DBUtils ;
import com.keepcount.dao.dbutils.DBUtilsTypes ;
import com.keepcount.model.item.UnitOfMeasurement ;

public class UnitOfMeasurementDAOUpdate {

	private static List < String > listOfAllProceduresCreated ;

	public static List < String > getListOfAllProceduresCreated() {
		return listOfAllProceduresCreated ;
	}

	public static void setListOfAllProceduresCreated( List < String > listOfAllProceduresCreated ) {
		UnitOfMeasurementDAOUpdate.listOfAllProceduresCreated = listOfAllProceduresCreated ;
	}

	private static List < String > listOfAllProceduresAlreadyCreated() throws Exception {
		setListOfAllProceduresCreated( DBUtils.listOfAllProceduresAlreadyCreated() ) ;
		return getListOfAllProceduresCreated() ;
	}

	private static String procName = "update_measurement_units_" ;
	private static String tableName = "measurement_units_" ;

	private static String whereCondition() {
		return " WHERE id = in_id AND item_id = in_item_id" ;
	}

	private static List < String > whereList() {
		List < String > list = new ArrayList <>() ;
		// list.add(" WHERE ");
		list.add( "id = in_id" ) ;
		list.add( "item_id = in_item_id" ) ;
		return list ;
	}

	private static Map < String , DBUtilsTypes > findMappingForUpdateParameters() {

		Map < String , DBUtilsTypes > map = new LinkedHashMap <>() ;

		map.put( "id" , new DBUtilsTypes( "id" , "bigint" ) ) ;
		map.put( "unit_of_measurement" , new DBUtilsTypes( "unit_of_measurement" , "varchar(255)" ) ) ;
		map.put( "item_id" , new DBUtilsTypes( "item_id" , "varchar(255)" ) ) ;

		return map ;

	}

	private static int procExisst = 0 ;

	public static int getProcExisst() {
		return procExisst ;
	}

	public static void setProcExisst( int procExisst ) {
		UnitOfMeasurementDAOUpdate.procExisst = procExisst ;
	}

	private static int update_measurement_units_ ;

	public static int getUpdate_measurement_units_() {
		return update_measurement_units_ ;
	}

	public static void setUpdate_measurement_units_( int update_measurement_units_ ) {
		UnitOfMeasurementDAOUpdate.update_measurement_units_ = update_measurement_units_ ;
	}

	public static int updateUnitOfMeasurement( UnitOfMeasurement measurement , String businessId , BigDecimal id ) throws Exception {
		String procedureName = procName.concat( businessId ) ;
		if ( getListOfAllProceduresCreated() == null ) {
			listOfAllProceduresAlreadyCreated() ;
		}
		if ( !getListOfAllProceduresCreated().contains( procedureName ) ) {
			DBUtils.createUpdatingProc( procName , businessId , tableName , findMappingForUpdateParameters() , whereCondition() , whereList() , getUpdate_measurement_units_() ) ;
			setUpdate_measurement_units_( 1 ) ;
		} else {
			setUpdate_measurement_units_( 1 ) ;
		}

		int result = 0 ;
		CallableStatement cs = null ;
		Connection connection = DBUtils.getConn() ;
		connection.setAutoCommit( false ) ;
		try {
			cs = connection.prepareCall( DBUtils.invocationOfUpdatetAPI( findMappingForUpdateParameters() , procName , businessId ) ) ;
			cs.setBigDecimal( 1 , id ) ;
			cs.setString( 2 , measurement.getUnitOfMeasurement() ) ;
			cs.setBigDecimal( 3 , measurement.getItemId() ) ;

			if ( businessId != null ) {
				result = cs.executeUpdate() ;
				if ( result == 1 ) {
					connection.commit() ;
					System.out.println( "update successful" ) ;
				} else {
					connection.rollback() ;
					System.out.println( "update failed" ) ;
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
