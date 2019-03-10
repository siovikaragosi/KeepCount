
package com.keepcount.dao.purchases ;

import java.math.BigDecimal ;
import java.sql.CallableStatement ;
import java.sql.Connection ;
import java.sql.DatabaseMetaData ;
import java.sql.PreparedStatement ;
import java.sql.ResultSet ;

import com.keepcount.dao.dbutils.DBUtils ;

public class CallableStmt {

	public static void main( String [ ] args ) throws Exception {
		// createInsertionCall();
		// getProc();

		/* insertion is working fine */
		// insert();

		/* works */
		// createRetrievalCall();
		// retrieveName();

		/* Works */
		// createUpdateCall();
		// update();

		// createDeleteCall();
		// delete();
		retrieveName() ;

	}

	private static String retrieveName() throws Exception {

		String nameReturned = null ;
		CallableStatement cs = null ;
		ResultSet rs = null ;
		try {
			cs = DBUtils.getConn().prepareCall( "{call retrieve_from_call_tb}" ) ;
			// cs.registerOutParameter(1, java.sql.Types.VARCHAR);
			rs = cs.executeQuery() ;
			while ( rs.next() ) {
				String name = rs.getString( "name" ) ;
				nameReturned = name ;
			}
			System.out.println( nameReturned ) ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return nameReturned ;
	}

	private static int insert() throws Exception {

		int result = 0 ;
		CallableStatement st = null ;
		try {
			st = DBUtils.getConn().prepareCall( "{call insert_into_call_tb(?)}" ) ;
			st.setString( 1 , "test name" ) ;
			result = st.executeUpdate() ;
			System.out.println( result ) ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return result ;
	}

	private static String insertionCall() throws Exception {

		String storedProc = "create procedure insert_into_call_tb(in name varchar(255)) "

				.concat( "begin " )

				.concat( "insert into call_tb(name) values(name)" )

				.concat( ";" )

				.concat( " end" ) ;

		return storedProc ;
	}

	private static int createInsertionCall() throws Exception {

		int result = 0 ;
		PreparedStatement st = null ;
		try {
			st = DBUtils.getConn().prepareStatement( insertionCall() ) ;
			result = st.executeUpdate() ;
			System.out.println( result ) ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return result ;
	}

	private static void getProc() throws Exception {

		DatabaseMetaData data = DBUtils.getConn().getMetaData() ;
		System.out.println( data.getProcedures( null , null , "insert_into_call_tb" ) ) ;
		PreparedStatement st = null ;
		ResultSet rs = data.getProcedures( null , null , "insert_into_call_tb" ) ;

		try {
			if ( !rs.next() ) {
				System.out.println( "no proc" ) ;
			}
			System.out.println( rs.toString() ) ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
	}

	private static int createRetrievalCall() throws Exception {

		int result = 0 ;
		PreparedStatement st = null ;
		try {
			st = DBUtils.getConn().prepareStatement( retrievalCall() ) ;
			result = st.executeUpdate() ;
			System.out.println( result ) ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return result ;
	}

	private static String retrievalCall() throws Exception {

		String storedProc = "create procedure retrieve_from_call_tb()"

				.concat( "begin " )

				.concat( "select * from call_tb;" )

				.concat( " end" ) ;

		return storedProc ;
	}

	private static int createUpdateCall() throws Exception {

		int result = 0 ;
		PreparedStatement st = null ;
		try {
			st = DBUtils.getConn().prepareStatement( updateCall() ) ;
			result = st.executeUpdate() ;
			System.out.println( result ) ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return result ;
	}

	private static int update() throws Exception {

		int result = 0 ;
		CallableStatement st = null ;
		try {
			st = DBUtils.getConn().prepareCall( "{call update_call_tb(?,?)}" ) ;
			// st.setInt(1, 1);
			st.setBigDecimal( 1 , BigDecimal.ONE ) ;
			st.setString( 2 , "name2" ) ;
			result = st.executeUpdate() ;
			System.out.println( result ) ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return result ;
	}

	private static String updateCall() throws Exception {

		String storedProc = "create procedure update_call_tb(in id int,in nameNew varchar(255)) "

				.concat( "begin " )

				.concat( "update call_tb set name=nameNew where id=id;" )

				.concat( " end" )

		;
		return storedProc ;
	}

	private static int createDeleteCall() throws Exception {

		int result = 0 ;
		PreparedStatement st = null ;
		try {
			st = DBUtils.getConn().prepareStatement( deleteCall() ) ;
			result = st.executeUpdate() ;
			System.out.println( result ) ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return result ;
	}

	private static int delete() throws Exception {

		int result = 0 ;
		CallableStatement st = null ;
		try {
			st = DBUtils.getConn().prepareCall( "{call delete_from_call_tb(?)}" ) ;
			st.setBigDecimal( 1 , BigDecimal.ONE ) ;
			result = st.executeUpdate() ;
			System.out.println( result ) ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return result ;
	}

	private static String deleteCall() throws Exception {

		String storedProc = "create procedure delete_from_call_tb(in id int) "

				.concat( "begin " )

				.concat( "delete from call_tb where id=id;" )

				.concat( " end" )

		;
		return storedProc ;
	}

	private static int createTable() throws Exception {

		int result = 0 ;
		DatabaseMetaData data = DBUtils.getConn().getMetaData() ;

		ResultSet rs = data.getTables( null , null , "call_tb" , null ) ;
		PreparedStatement st = null ;
		Connection connection = DBUtils.getConn() ;
		try {
			if ( !rs.next() ) {
				st = connection.prepareStatement( "create table call_tb(id int primary key auto_increment, name varchar(255))" ) ;
				result = st.executeUpdate() ;
			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( rs , null , st , connection ) ;
		}
		return result ;
	}

}
