
package com.keepcount.dao.pricing ;

import java.sql.CallableStatement ;
import java.sql.Connection ;
import java.sql.Timestamp ;
import java.time.LocalDateTime ;
import java.util.LinkedHashMap ;
import java.util.List ;
import java.util.Map ;

import com.keepcount.dao.dbutils.DBUtils ;
import com.keepcount.dao.dbutils.DBUtilsTypes ;
import com.keepcount.model.pricing.Pricing ;

public class PricingDAOInsertion {

	private static List < String > listOfAllProceduresCreated ;

	public static List < String > getListOfAllProceduresCreated() {
		return listOfAllProceduresCreated ;
	}

	public static void setListOfAllProceduresCreated( List < String > listOfAllProceduresCreated ) {
		PricingDAOInsertion.listOfAllProceduresCreated = listOfAllProceduresCreated ;
	}

	private static List < String > listOfAllProceduresAlreadyCreated() throws Exception {
		setListOfAllProceduresCreated( DBUtils.listOfAllProceduresAlreadyCreated() ) ;
		return getListOfAllProceduresCreated() ;
	}

	private static String procName = "insert_into_pricing_" ;
	private static String tableName = "pricing_" ;

	private static Map < String , DBUtilsTypes > findMappingForInsertionParameters() {

		Map < String , DBUtilsTypes > map = new LinkedHashMap <>() ;

		map.put( "date_client" , new DBUtilsTypes( "date_client" , "varchar(255)" ) ) ;
		map.put( "price" , new DBUtilsTypes( "price" , "decimal(50,5)" ) ) ;
		map.put( "item_id" , new DBUtilsTypes( "item_id" , "bigint" ) ) ;
		map.put( "item_sub_category" , new DBUtilsTypes( "item_sub_category" , "varchar(255)" ) ) ;

		return map ;

	}

	private static int insert_into_pricing_Existence ;

	public static int getInsert_into_pricing_Existence() {
		return insert_into_pricing_Existence ;
	}

	public static void setInsert_into_pricing_Existence( int insert_into_pricing_Existence ) {
		PricingDAOInsertion.insert_into_pricing_Existence = insert_into_pricing_Existence ;
	}

	public static int newPricing( String businessId , Pricing pricing ) throws Exception {

		String procedureName = procName.concat( businessId ) ;

		if ( getListOfAllProceduresCreated() == null ) {
			listOfAllProceduresAlreadyCreated() ;
		}

		if ( !getListOfAllProceduresCreated().contains( procedureName ) ) {
			DBUtils.createInsertionProc( procName , businessId , tableName , PricingDAOInsertion.findMappingForInsertionParameters() , getInsert_into_pricing_Existence() ) ;
			setInsert_into_pricing_Existence( 1 ) ;
		} else {
			setInsert_into_pricing_Existence( 1 ) ;
		}

		int result = 0 ;
		CallableStatement cs = null ;
		Connection connection = DBUtils.getConn() ;
		connection.setAutoCommit( false ) ;

		try {
			connection.setAutoCommit( false ) ;

			cs = connection.prepareCall( DBUtils.invocationOfInsertAPI( PricingDAOInsertion.findMappingForInsertionParameters() , procName , businessId ) ) ;
			Timestamp timestamp = Timestamp.valueOf( LocalDateTime.now() ) ;
			pricing.setDateClient( timestamp.toString() ) ;
			// 1
			cs.setString( 1 , pricing.getDateClient() ) ;
			// 2
			cs.setBigDecimal( 2 , pricing.getPrice() ) ;
			// 3
			cs.setBigDecimal( 3 , pricing.getItemId() ) ;
			// 4
			cs.setString( 4 , pricing.getItemSubCategory() ) ;
			result = cs.executeUpdate() ;

			if ( result == 1 ) {
				connection.commit() ;
			} else {
				connection.rollback() ;
			}

		} catch ( Exception e ) {
			e.printStackTrace() ;
			connection.rollback() ;
		} finally {
			DBUtils.closeConnections( null , cs , null , connection ) ;
		}
		return result ;
	}

}
