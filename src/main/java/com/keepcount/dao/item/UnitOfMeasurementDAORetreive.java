
package com.keepcount.dao.item ;

import java.math.BigDecimal ;
import java.sql.CallableStatement ;
import java.sql.Connection ;
import java.sql.ResultSet ;
import java.util.ArrayList ;
import java.util.List ;

import com.keepcount.dao.dbutils.DBUtils ;
import com.keepcount.model.item.UnitOfMeasurement ;

public class UnitOfMeasurementDAORetreive {

	private static List < String > listOfAllProceduresCreated ;

	public static List < String > getListOfAllProceduresCreated() {
		return listOfAllProceduresCreated ;
	}

	public static void setListOfAllProceduresCreated( List < String > listOfAllProceduresCreated ) {
		UnitOfMeasurementDAORetreive.listOfAllProceduresCreated = listOfAllProceduresCreated ;
	}

	private static List < String > listOfAllProceduresAlreadyCreated() throws Exception {
		setListOfAllProceduresCreated( DBUtils.listOfAllProceduresAlreadyCreated() ) ;
		return getListOfAllProceduresCreated() ;
	}

	private static String procName = "get_all_measurement_units_" ;
	private static String tableName = "measurement_units_" ;
	private static String whereCond = "" ;
	private static String inParams = "" ;
	private static String wildCardValues = "" ;
	private static String asteriskOrCols = "*" ;

	private static int get_all_measurement_units_Existence ;

	public static int getGet_all_measurement_units_Existence() {
		return get_all_measurement_units_Existence ;
	}

	public static void setGet_all_measurement_units_Existence( int get_all_measurement_units_Existence ) {
		UnitOfMeasurementDAORetreive.get_all_measurement_units_Existence = get_all_measurement_units_Existence ;
	}

	public static List < UnitOfMeasurement > findAllUnitsOfMeasurement( String businessId ) throws Exception {
		// String procName, String businessId, String tableName, String whereCondition

		String procedureName = procName.concat( businessId ) ;

		if ( getListOfAllProceduresCreated() == null ) {
			listOfAllProceduresAlreadyCreated() ;
		}

		if ( !getListOfAllProceduresCreated().contains( procedureName ) ) {
			DBUtils.createSelectionProc( inParams , procName , businessId , asteriskOrCols , tableName , whereCond , getGet_all_measurement_units_Existence() ) ;

			setGet_all_measurement_units_Existence( 1 ) ;
		} else {
			setGet_all_measurement_units_Existence( 1 ) ;
		}

		// DBUtils.createSelectionProc( inParams , procName , businessId ,
		// asteriskOrCols , tableName , whereCond ) ;

		List < UnitOfMeasurement > list = new ArrayList <>() ;
		CallableStatement cs = null ;
		ResultSet rs = null ;
		Connection connection = DBUtils.getConn() ;
		try {
			cs = connection.prepareCall( DBUtils.invokeSelection( procName , businessId , wildCardValues ) ) ;
			rs = cs.executeQuery() ;
			while ( rs.next() ) {
				BigDecimal id = rs.getBigDecimal( "id" ) ;
				BigDecimal itemId = rs.getBigDecimal( "item_id" ) ;
				String unit = rs.getString( "unit_of_measurement" ) ;
				UnitOfMeasurement measurement = new UnitOfMeasurement( id , itemId , unit ) ;
				list.add( measurement ) ;
			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( rs , cs , null , connection ) ;
		}
		return list ;
	}

	public static void main( String [ ] args ) throws Exception {
		// UnitOfMeasurementDAORetreive.
		findAllUnitsOfMeasurement( "16" ) ;
	}

}
