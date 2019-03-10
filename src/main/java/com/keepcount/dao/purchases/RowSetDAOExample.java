
package com.keepcount.dao.purchases ;

import java.sql.Connection ;
import java.util.ArrayList ;
import java.util.List ;

import javax.sql.rowset.CachedRowSet ;
import javax.sql.rowset.RowSetFactory ;
import javax.sql.rowset.RowSetProvider ;
import javax.sql.rowset.spi.SyncProviderException ;

import com.keepcount.dao.dbutils.DBUtils ;

public class RowSetDAOExample {

	// id
	// name
	// course

	public static void main( String [ ] args ) throws Exception {
		retrieve() ;
		insert() ;
		retrieve() ;
	}

	private static void retrieve() throws Exception {
		Connection con = DBUtils.getConn() ;
		System.out.println( "before auto set to false" ) ;

		con.setAutoCommit( false ) ;
		System.out.println( "after auto set to false" ) ;

		RowSetFactory rowSetFactory = RowSetProvider.newFactory() ;

		CachedRowSet cachedRowSet = rowSetFactory.createCachedRowSet() ;

		cachedRowSet.setUrl( DBUtils.getDB_URL() ) ;
		cachedRowSet.setUsername( DBUtils.getUSER() ) ;
		cachedRowSet.setPassword( DBUtils.getPASSWORD() ) ;

		cachedRowSet.setCommand( "select * from rowsettest" ) ;
		cachedRowSet.execute() ;
		con.commit() ;

		List < RowSetModelExample > list = new ArrayList <>() ;

		while ( cachedRowSet.next() ) {
			// BigDecimal id = cachedRowSet.getBigDecimal( "id" ) ;
			String name = cachedRowSet.getString( "name" ) ;
			String course = cachedRowSet.getString( "course" ) ;

			RowSetModelExample example = new RowSetModelExample( null , name , course ) ;
			list.add( example ) ;
		}

		System.out.println( "rs: " + list ) ;

		cachedRowSet.close() ;

		DBUtils.closeConnections( null , null , null , con ) ;

	}

	private static int insert() throws Exception {
		int result = 0 ;
		// insert into rowset_test(name, course) values('Comboni', 'BITC');

		Connection con = DBUtils.getConn() ;
		con.setAutoCommit( false ) ;

		RowSetFactory rowSetFactory = RowSetProvider.newFactory() ;
		CachedRowSet cachedRowSet = rowSetFactory.createCachedRowSet() ;

		cachedRowSet.setUrl( DBUtils.getDB_URL() ) ;
		cachedRowSet.setUsername( DBUtils.getUSER() ) ;
		cachedRowSet.setPassword( DBUtils.getPASSWORD() ) ;

		System.out.println( "cursor name: " + cachedRowSet.getCommand() ) ;

		try {
			cachedRowSet.setTableName( "rowsettest" ) ;
			cachedRowSet.moveToInsertRow() ;
			cachedRowSet.updateString( "name" , "Akuayo" ) ;
			cachedRowSet.updateString( "course" , "BITC" ) ;
			cachedRowSet.insertRow() ;
			cachedRowSet.moveToCurrentRow();
			cachedRowSet.acceptChanges(con) ;
			con.commit() ;

			System.out.println( "inserting..." ) ;

		} catch ( SyncProviderException e ) {
			// TODO: handle exception
		}

		return result ;
	}

}
