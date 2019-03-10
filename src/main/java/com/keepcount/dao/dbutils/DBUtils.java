
package com.keepcount.dao.dbutils ;

import java.sql.CallableStatement ;
import java.sql.Connection ;
import java.sql.DatabaseMetaData ;
import java.sql.DriverManager ;
import java.sql.PreparedStatement ;
import java.sql.ResultSet ;
import java.util.ArrayList ;
import java.util.LinkedHashMap ;
import java.util.List ;
import java.util.Map ;

public class DBUtils {

	private static int procExisst = 0 ;

	public static int getProcExisst() {

		return procExisst ;
	}

	public static void setProcExisst( int procExisst ) {

		DBUtils.procExisst = procExisst ;
	}

	// private static Connection connection = null ;
	private static String DRIVER_NAME = "com.mysql.jdbc.Driver" ;
	private static String DB_URL = "jdbc:mysql://localhost/kc" ;
	private static String PASSWORD = "mysql" ;
	private static String USER = "root" ;

	// private static PreparedStatement pst = null ;
	// private static CallableStatement cs = null ;
	// private static ResultSet rs = null ;

	public static Connection getConn() throws Exception {
		Connection connection = null ;
		try {

			Class.forName( DRIVER_NAME ) ;

			connection = DriverManager.getConnection( DB_URL , USER , PASSWORD ) ;
			System.out.println( "connection got..." ) ;

		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return connection ;
	}

	public static String getDB_URL() {
		return DB_URL ;
	}

	public static void setDB_URL( String dB_URL ) {
		DB_URL = dB_URL ;
	}

	public static String getPASSWORD() {
		return PASSWORD ;
	}

	public static void setPASSWORD( String pASSWORD ) {
		PASSWORD = pASSWORD ;
	}

	public static String getUSER() {
		return USER ;
	}

	public static void setUSER( String uSER ) {
		USER = uSER ;
	}

	private static int tableExists = 0 ;

	public static int getTableExists() {

		return tableExists ;
	}

	public static void setTableExists( int tableExists ) {

		DBUtils.tableExists = tableExists ;
	}

	public static int checkTableExistence( String tableName , String sqlTableDecription , String businessId ) throws Exception {

		int result = 0 ;

		Connection connectionToCheckTableExistence = DBUtils.getConn() ;
		ResultSet rsToCheckTableExistence = null ;
		PreparedStatement stToCheckTableExistence = null ;

		if ( getTableExists() == 0 ) {

			DatabaseMetaData data = connectionToCheckTableExistence.getMetaData() ;
			rsToCheckTableExistence = data.getTables( null , null , "".concat( tableName ).concat( businessId ) , null ) ;
			connectionToCheckTableExistence.setAutoCommit( false ) ;
			try {

				String sql = "CREATE TABLE ".concat( tableName ).concat( businessId ).concat( " " ).concat( sqlTableDecription ) ;

				if ( !rsToCheckTableExistence.next() ) {

					if ( businessId != null ) {

						stToCheckTableExistence = connectionToCheckTableExistence.prepareStatement( sql ) ;
						result = stToCheckTableExistence.executeUpdate() ;

						if ( result == 1 ) {
							connectionToCheckTableExistence.commit() ;
							System.out.println( tableName.concat( businessId ) + " created successfully" ) ;
						} else {
							connectionToCheckTableExistence.rollback() ;
							System.out.println( tableName.concat( businessId ) + " not created" ) ;
						}

					}

				}

			} catch ( Exception e ) {
				e.printStackTrace() ;
			} finally {
				DBUtils.closeConnections( rsToCheckTableExistence , null , stToCheckTableExistence , connectionToCheckTableExistence ) ;
			}

		}
		return result ;
	}

	public static List < String > listOfAllProceduresAlreadyCreated() throws Exception {

		List < String > allProceduresAlreadyCreated = new ArrayList <>() ;
		PreparedStatement preparedStatementToFindAllProceduresAlreadyCreated = null ;
		ResultSet resultSetToFindAllProceduresAlreadyCreated = null ;
		Connection connectionToFindAllProceduresAlreadyCreated = DBUtils.getConn() ;
		String sql = "SELECT ROUTINE_NAME FROM INFORMATION_SCHEMA.ROUTINES WHERE ROUTINE_TYPE= \"PROCEDURE\"  AND ROUTINE_SCHEMA=\"kc\"" ;

		try {
			preparedStatementToFindAllProceduresAlreadyCreated = connectionToFindAllProceduresAlreadyCreated.prepareStatement( sql ) ;
			resultSetToFindAllProceduresAlreadyCreated = preparedStatementToFindAllProceduresAlreadyCreated.executeQuery() ;
			while ( resultSetToFindAllProceduresAlreadyCreated.next() ) {
				String procedure = resultSetToFindAllProceduresAlreadyCreated.getString( "ROUTINE_NAME" ) ;
				allProceduresAlreadyCreated.add( procedure ) ;
			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( resultSetToFindAllProceduresAlreadyCreated , null , preparedStatementToFindAllProceduresAlreadyCreated ,
					connectionToFindAllProceduresAlreadyCreated ) ;
		}

		return allProceduresAlreadyCreated ;
	}

	private static int checkProcExitence( String procName , String businessId , int procedureExistence ) throws Exception {

		int result = 0 ;
		String sql = "SELECT ROUTINE_NAME FROM INFORMATION_SCHEMA.ROUTINES WHERE ROUTINE_TYPE= \"PROCEDURE\"  AND ROUTINE_SCHEMA=\"kc\"" ;

		List < String > listOfAllProcs = new ArrayList <>() ;
		PreparedStatement stToFindAllProcs = null ;

		ResultSet rsConatiningAllProcs = null ;

		Connection connectionToAllProcs = DBUtils.getConn() ;

		try {

			System.out.println( "ex: " + procedureExistence ) ;

			if ( procedureExistence == 0 ) {

				stToFindAllProcs = connectionToAllProcs.prepareStatement( sql ) ;
				rsConatiningAllProcs = stToFindAllProcs.executeQuery() ;

				while ( rsConatiningAllProcs.next() ) {
					String proc = rsConatiningAllProcs.getString( "ROUTINE_NAME" ) ;
					listOfAllProcs.add( proc ) ;
				}

				if ( listOfAllProcs.contains( "".concat( procName ).concat( businessId ) ) ) {
					result = 1 ;
					System.out.println( "proc exists" ) ;
					procedureExistence = 1 ;
				} else {
					result = 0 ;
					System.out.println( "proc does not exist" ) ;
				}

			}

		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( rsConatiningAllProcs , null , stToFindAllProcs , connectionToAllProcs ) ;
		}
		return result ;
	}

	public static int createInsertionProc( String procName , String businessId , String tableName , Map < String , DBUtilsTypes > procParamsForInsert , int procedureExistence )
			throws Exception {

		checkProcExitence( procName , businessId , procedureExistence ) ;

		int result = 0 ;

		String proc = insertionProcAPI( procParamsForInsert , tableName , procName , businessId ) ;

		System.out.println( "proc: " + proc ) ;

		PreparedStatement statementToCreateInsertionProcedureAPI = null ;
		Connection connectionToCreateInsertionProcedureAPI = DBUtils.getConn() ;
		connectionToCreateInsertionProcedureAPI.setAutoCommit( false ) ;

		try {

			if ( procedureExistence == 0 ) {

				statementToCreateInsertionProcedureAPI = connectionToCreateInsertionProcedureAPI.prepareStatement( proc ) ;

				if ( businessId != null ) {
					result = statementToCreateInsertionProcedureAPI.executeUpdate() ;
					System.out.println( "proc result: " + result ) ;
					if ( result == 1 ) {
						connectionToCreateInsertionProcedureAPI.commit() ;
						System.out.println( "insertion proc created successfully" ) ;
					} else {
						connectionToCreateInsertionProcedureAPI.rollback() ;
						System.out.println( "insertion proc failed to create" ) ;
					}
				}

			}

		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( null , null , statementToCreateInsertionProcedureAPI , connectionToCreateInsertionProcedureAPI ) ;
		}
		return result ;
	}

	public static int createUpdatingProc( String procName , String businessId , String tableName , Map < String , DBUtilsTypes > procParamsForUpdate , String whereStrOnly ,
			List < String > wheresList , int procedureExistence ) throws Exception {

		checkProcExitence( procName , businessId , procedureExistence ) ;

		int result = 0 ;

		String proc2 = updatingProcAPI( procParamsForUpdate , tableName , procName , businessId , whereStrOnly , wheresList ) ;
		// Map<String, DBUtilsTypes> procParamsForInsert, String tableName, String
		// procName, String businessId, String whereConditions, List<String> wheres

		System.out.println( "proc update: " + proc2 ) ;

		PreparedStatement preparedStatementToCreateUpdateProcedureAPI = null ;
		Connection connectionToCreateUpdateProcedureAPI = DBUtils.getConn() ;
		connectionToCreateUpdateProcedureAPI.setAutoCommit( false ) ;

		try {

			if ( procedureExistence == 0 ) {

				preparedStatementToCreateUpdateProcedureAPI = connectionToCreateUpdateProcedureAPI.prepareStatement( proc2 ) ;

				if ( businessId != null ) {
					result = preparedStatementToCreateUpdateProcedureAPI.executeUpdate() ;
					System.out.println( "proc result: " + result ) ;
					if ( result == 1 ) {
						connectionToCreateUpdateProcedureAPI.commit() ;
					} else {
						connectionToCreateUpdateProcedureAPI.rollback() ;
					}
				}

			}

		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( null , null , preparedStatementToCreateUpdateProcedureAPI , connectionToCreateUpdateProcedureAPI ) ;
		}
		return result ;
	}

	public static int createDeletionProc( String procName , String businessId , String tableName , String inParams , String whereCondition , int procedureExistence )
			throws Exception {

		checkProcExitence( procName , businessId , procedureExistence ) ;

		int result = 0 ;

		String proc2 = deleteProcAPI( procName , businessId , inParams , tableName , whereCondition ) ;
		System.out.println( "proc delete: " + proc2 ) ;

		PreparedStatement preparedStatementToCreateDeleteProcedureAPI = null ;
		Connection connectionToCreateDeleteProcedureAPI = DBUtils.getConn() ;
		connectionToCreateDeleteProcedureAPI.setAutoCommit( false ) ;

		try {

			if ( procedureExistence == 0 ) {

				preparedStatementToCreateDeleteProcedureAPI = connectionToCreateDeleteProcedureAPI.prepareStatement( proc2 ) ;

				if ( businessId != null ) {
					result = preparedStatementToCreateDeleteProcedureAPI.executeUpdate() ;
					System.out.println( "proc result: " + result ) ;
					if ( result == 1 ) {
						connectionToCreateDeleteProcedureAPI.commit() ;
					} else {
						connectionToCreateDeleteProcedureAPI.rollback() ;
					}
				}

			}

		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( null , null , preparedStatementToCreateDeleteProcedureAPI , connectionToCreateDeleteProcedureAPI ) ;
		}
		return result ;
	}

	public static int createSelectionProc( String inParams , String procName , String businessId , String asteriskOrCols , String tableName , String whereCondition ,
			int procedureExistence ) throws Exception {

		checkProcExitence( procName , businessId , procedureExistence ) ;

		int result = 0 ;

		String proc2 = selectProcAPI( inParams , procName , businessId , asteriskOrCols , tableName , whereCondition ) ;
		System.out.println( "proc select: " + proc2 ) ;

		PreparedStatement preparedStatementToCreateSelectProcedureAPI = null ;
		Connection connectionToCreateSelectProcedureAPI = DBUtils.getConn() ;
		connectionToCreateSelectProcedureAPI.setAutoCommit( false ) ;

		try {

			if ( procedureExistence == 0 ) {

				preparedStatementToCreateSelectProcedureAPI = connectionToCreateSelectProcedureAPI.prepareStatement( proc2 ) ;

				if ( businessId != null ) {
					result = preparedStatementToCreateSelectProcedureAPI.executeUpdate() ;
					System.out.println( "proc result: " + result ) ;
					if ( result == 1 ) {
						connectionToCreateSelectProcedureAPI.commit() ;
						System.out.println( "selection proc created" ) ;
					} else {
						connectionToCreateSelectProcedureAPI.rollback() ;
						System.out.println( "selection proc not created" ) ;
					}
				}

			}

		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( null , null , preparedStatementToCreateSelectProcedureAPI , connectionToCreateSelectProcedureAPI ) ;
		}
		return result ;
	}

	private static String insertionProcAPI( Map < String , DBUtilsTypes > procParamsForInsert , String tableName , String procName , String businessId ) {

		Map < String , DBUtilsTypes > list = new LinkedHashMap <>() ;

		list = procParamsForInsert ;

		String proc = "CREATE PROCEDURE ".concat( procName ).concat( businessId ) ;

		String insertsParams = "".concat( "(" ) ;

		for ( String cols : list.keySet() ) {
			DBUtilsTypes colName = list.get( cols ) ;

			insertsParams = insertsParams.concat( "in " ).concat( colName.getTypeName() ).concat( " " ).concat( colName.getType() ).concat( ", " ) ;

		}

		String insertsParams2 = insertsParams.concat( ")" ) ;
		String insertsParams3 = insertsParams2 ;

		String rep = ", )" ;

		if ( insertsParams2.contains( rep ) ) {
			insertsParams3 = insertsParams2.replace( rep , ")" ) ;
		}

		String insertsParams5 = " BEGIN INSERT INTO ".concat( tableName ).concat( businessId ).concat( "(" ) ;

		for ( String cols : list.keySet() ) {
			DBUtilsTypes colName = list.get( cols ) ;

			insertsParams5 = insertsParams5.concat( colName.getTypeName() ).concat( ", " ) ;

		}

		String insertsParams6 = insertsParams5.concat( ")" ) ;

		String insertsParams7 = "" ;

		if ( insertsParams6.contains( ", )" ) ) {
			insertsParams7 = insertsParams6.replace( ", )" , "" ).concat( ")" ) ;
		}

		String values = " VALUES(" ;
		for ( String cols : list.keySet() ) {
			DBUtilsTypes colName = list.get( cols ) ;

			values = values.concat( colName.getTypeName() ).concat( " " ).concat( "," ) ;

		}

		String values2 = values.concat( ")" ).concat( ";" ).concat( " END" ) ;

		String values3 = "" ;
		if ( values2.contains( " ,)" ) ) {
			values3 = values2.replace( " ,)" , ")" ) ;
		}

		String insertsParams8 = proc.concat( " \n" ).concat( insertsParams3 ).concat( " \n" ).concat( insertsParams7 ).concat( " \n" ).concat( values3 ) ;

		return insertsParams8 ;

	}

	public static String invocationOfInsertAPI( Map < String , DBUtilsTypes > procParamsForInsert , String procName , String businessId ) {

		String callableStatement1 = "{CALL ".concat( procName ).concat( businessId ).concat( "(" ) ;

		System.out.println( procParamsForInsert.size() + " required values for this insertion" ) ;

		for ( int i = 0 ; i < procParamsForInsert.size() ; i++ ) {

			callableStatement1 = callableStatement1.concat( "?" ).concat( ", " ) ;

		}

		String callableStatement2 = callableStatement1.concat( ")" ) ;

		String callableStatement3 = "" ;

		if ( callableStatement2.contains( ", )" ) ) {
			callableStatement3 = callableStatement2.replace( ", )" , ")" ).concat( "}" ) ;
		}

		return callableStatement3 ;

	}

	private static String updatingProcAPI( Map < String , DBUtilsTypes > procParamsForInsert , String tableName , String procName , String businessId , String whereConditions ,
			List < String > wheres ) {

		String proc = "CREATE PROCEDURE \n".concat( procName ).concat( businessId ) ;

		String insertsParams = "".concat( proc ).concat( "(" ) ;

		for ( String cols : procParamsForInsert.keySet() ) {
			DBUtilsTypes colName = procParamsForInsert.get( cols ) ;

			insertsParams = insertsParams.concat( "in in_" ).concat( colName.getTypeName() ).concat( " " ).concat( colName.getType() ).concat( ", " ) ;

		}

		String insertsParams2 = insertsParams.concat( ")" ) ;

		String insertsParams3 = insertsParams2 ;

		String rep = ", )" ;

		if ( insertsParams2.contains( rep ) ) {
			insertsParams3 = insertsParams2.replace( rep , ")" ) ;
		}

		String insertsParams5 = insertsParams3.concat( "\n" ).concat( "  BEGIN UPDATE " ).concat( tableName ).concat( businessId ).concat( " SET " ).concat( "" ) ;

		List < String > list = new ArrayList <>() ;

		for ( String cols : procParamsForInsert.keySet() ) {
			DBUtilsTypes colName = procParamsForInsert.get( cols ) ;
			// insertsParams5 = insertsParams5.concat(colName.getTypeName()).concat(" =
			// in_").concat(colName.getTypeName()).concat(", ");
			list.add( "".concat( colName.getTypeName() ).concat( " = in_" ).concat( colName.getTypeName() ) ) ;

		}

		list.removeAll( wheres ) ;

		String listStr = String.valueOf( list ) ;

		String listStr2 = listStr.replace( "[" , "" ) ;

		int index = listStr2.length() ;
		char charLast = listStr2.charAt( index - 1 ) ;
		String listStr3 = listStr2.replace( String.valueOf( charLast ) , "" ) ;

		insertsParams5 = insertsParams5.concat( listStr3 ) ;
		String insertsParams6 = insertsParams5.concat( "\n" ).concat( " " ).concat( whereConditions ).concat( "" ).concat( ";" ).concat( " END" ) ;
		System.out.println() ;

		return insertsParams6 ;

	}

	public static String invocationOfUpdatetAPI( Map < String , DBUtilsTypes > procParamsForInsert , String procName , String businessId ) {

		String callableStatement1 = "{CALL ".concat( procName ).concat( businessId ).concat( "(" ) ;

		System.out.println( procParamsForInsert.size() + " required values for this updating" ) ;

		for ( int i = 0 ; i < procParamsForInsert.size() ; i++ ) {

			callableStatement1 = callableStatement1.concat( "?" ).concat( ", " ) ;

		}

		String callableStatement2 = callableStatement1.concat( ")" ) ;

		String callableStatement3 = "" ;

		if ( callableStatement2.contains( ", )" ) ) {
			callableStatement3 = callableStatement2.replace( ", )" , ")" ).concat( "}" ) ;
		}

		System.out.println( "call: " + callableStatement3 ) ;

		return callableStatement3 ;

	}

	private static String deleteProcAPI( String procName , String businessId , String inParams , String tableName , String whereConditon ) {

		// e.g of inParams
		// "create procedure delete_from_call_tb(in id int) " where (in id int) is the
		// in param

		String proc = "CREATE PROCEDURE \n".concat( procName ).concat( businessId ).concat( "(" ).concat( inParams ).concat( ")" ).concat( " " ).concat( " BEGIN DELETE FROM " )
				.concat( tableName )

				.concat( businessId ).concat( " " ).concat( whereConditon ).concat( ";" ).concat( " END" ) ;

		System.out.println( "deletion proc: " + proc ) ;

		return proc ;

	}

	public static String invocationOfDeletionAPI( String procName , String businessId , String tableName , String inParams , String whereCondition ) {

		return DBUtils.deleteProcAPI( procName , businessId , inParams , tableName , whereCondition ) ;

	}

	private static String selectProcAPI( String inParams , String procName , String businessId , String asteriskOrcols , String tableName , String whereCondition ) {

		String proc = "CREATE PROCEDURE \n".concat( procName ).concat( businessId ).concat( "(" ).concat( inParams ).concat( ")" ).concat( "\n" )

				.concat( " BEGIN SELECT " ).concat( asteriskOrcols ).concat( " FROM " ).concat( tableName ).concat( businessId ).concat( " " ).concat( whereCondition )
				.concat( ";" ).concat( " END" ) ;

		System.out.println( "selection proc: " + proc ) ;

		return proc ;

	}

	public static String invokeSelection( String procName , String businessId , String wildCardValues ) {

		String call = "{CALL ".concat( procName ).concat( businessId ).concat( "(" ).concat( wildCardValues ).concat( ")" ).concat( "}" ) ;

		return call ;

	}

	public static List < String > InsertionsProcParamsAPI() {

		List < String > list = new ArrayList <>() ;

		return list ;

	}

	public static String addInsertProcParamsWithTheirDataTypes( String anInsertionParam ) {

		DBUtils.InsertionsProcParamsAPI().add( anInsertionParam ) ;

		return anInsertionParam ;

	}

	public static void main( String [ ] args ) {

		Map < String , DBUtilsTypes > map = new LinkedHashMap <>() ;

		// DBUtilsTypes types = new DBUtilsTypes() ;

		map.put( "id" , new DBUtilsTypes( "id" , "bigint primary key auto_increment" ) ) ;
		map.put( "item" , new DBUtilsTypes( "item" , "varchar(255)" ) ) ;
		map.put( "item_name" , new DBUtilsTypes( "item_name" , "varchar(255)" ) ) ;
		// map.put("item_cat", new DBUtilsTypes("item_cat", "varchar(255)"));
		// map.put("item_sub_cat", new DBUtilsTypes("item_sub_cat", "varchar(255)"));
		// map.put("item_description", new DBUtilsTypes("item_description",
		// "varchar(255)"));
		// map.put("item_test", new DBUtilsTypes("item_test", "varchar(255)"));

		// System.out.println(map.size());

		String tableName = "table" ;
		String procName = "procName" ;

		Map < String , DBUtilsTypes > mapCond = new LinkedHashMap <>() ;
		mapCond.put( "item" , new DBUtilsTypes( "item" , "varchar(255)" ) ) ;
		mapCond.put( "item_name" , new DBUtilsTypes( "item_name" , "varchar(255)" ) ) ;

		String where = "id = in_id" ;
		String w = "item = in_item" ;

		String whereCond = " WHERE id = in_id" ;

		List < String > list = new ArrayList <>() ;
		list.add( w ) ;
		list.add( where ) ;

		System.out.println( updatingProcAPI( map , tableName , procName , "16" , whereCond , list ) ) ;
		// map.put("id", "bigint primary key auto_increment");
		// map.put("item_name", "varchar(255)");
		// map.put("item_name1", "varchar(255)");
		// map.put("item_name2", "varchar(255)");
		// map.put("item_name3", "varchar(255)");
		// map.put("item_name4", "varchar(255)");
		// map.put("item_name5", "varchar(255)");

		// System.out.println(insertionProcAPI(map, tableName, procName));

		// invocationOfInsertAPI(map, tableName, procName);

		// Map<String, DBUtilsTypes> procParamsForInsert, String procName, String
		// businessId
		System.out.println( invocationOfUpdatetAPI( map , tableName , procName ) ) ;

	}

	public static void closeConnections( ResultSet rs , CallableStatement cs , PreparedStatement st , Connection connection ) throws Exception {

		if ( rs != null ) {
			rs.close() ;
		}

		if ( cs != null ) {
			cs.close() ;
		}

		if ( st != null ) {
			st.close() ;
		}

		if ( connection != null ) {
			connection.close() ;
		}

	}

}