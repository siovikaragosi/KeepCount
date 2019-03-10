
package com.keepcount.dao.pricing ;

import java.math.BigDecimal ;
import java.sql.CallableStatement ;
import java.sql.Connection ;
import java.util.ArrayList ;
import java.util.LinkedHashMap ;
import java.util.List ;
import java.util.Map ;

import com.keepcount.dao.dbutils.DBUtils ;
import com.keepcount.dao.dbutils.DBUtilsTypes ;
import com.keepcount.model.pricing.Pricing ;

public class PricingDAOUpdate {

	private static List < String > listOfAllProceduresCreated ;

	public static List < String > getListOfAllProceduresCreated() {
		return listOfAllProceduresCreated ;
	}

	public static void setListOfAllProceduresCreated( List < String > listOfAllProceduresCreated ) {
		PricingDAOUpdate.listOfAllProceduresCreated = listOfAllProceduresCreated ;
	}

	private static List < String > listOfAllProceduresAlreadyCreated() throws Exception {
		setListOfAllProceduresCreated( DBUtils.listOfAllProceduresAlreadyCreated() ) ;
		return getListOfAllProceduresCreated() ;
	}

	private static String procName = "update_pricing_" ;
	private static String tableName = "pricing_" ;

	private static String whereCondition() {
		return " WHERE id = in_id" ;
	}

	private static List < String > whereList() {
		List < String > list = new ArrayList <>() ;
		// list.add(" WHERE ");
		list.add( "id = in_id" ) ;
		return list ;
	}

	private static Map < String , DBUtilsTypes > findMappingForUpdateParameters() {

		Map < String , DBUtilsTypes > map = new LinkedHashMap <>() ;

		map.put( "id" , new DBUtilsTypes( "id" , "bigint" ) ) ;
		map.put( "price" , new DBUtilsTypes( "price" , "decimal(50,5)" ) ) ;

		return map ;

	}

	private static int update_pricing_Existence ;

	public static int getUpdate_pricing_Existence() {
		return update_pricing_Existence ;
	}

	public static void setUpdate_pricing_Existence( int update_pricing_Existence ) {
		PricingDAOUpdate.update_pricing_Existence = update_pricing_Existence ;
	}

	public static int updatePricing( String businessId , Pricing pricing , BigDecimal id ) throws Exception {

		String procedureName = procName.concat( businessId ) ;

		if ( getListOfAllProceduresCreated() == null ) {
			listOfAllProceduresAlreadyCreated() ;
		}

		if ( !getListOfAllProceduresCreated().contains( procedureName ) ) {
			DBUtils.createUpdatingProc( procName , businessId , tableName , findMappingForUpdateParameters() , whereCondition() , whereList() , getUpdate_pricing_Existence() ) ;
			setUpdate_pricing_Existence( 1 ) ;
		} else {
			setUpdate_pricing_Existence( 1 ) ;
		}

		int result = 0 ;
		CallableStatement cs = null ;
		Connection connection = DBUtils.getConn() ;
		connection.setAutoCommit( false ) ;
		try {
			cs = connection.prepareCall( DBUtils.invocationOfUpdatetAPI( findMappingForUpdateParameters() , procName , businessId ) ) ;
			cs.setBigDecimal( 1 , id ) ;
			cs.setBigDecimal( 3 , pricing.getPrice() ) ;

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
