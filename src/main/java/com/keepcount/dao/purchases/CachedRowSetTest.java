
package com.keepcount.dao.purchases ;

import java.sql.Connection ;
import java.sql.DriverManager ;
import java.sql.SQLException ;

import javax.sql.rowset.CachedRowSet ;

import com.sun.rowset.CachedRowSetImpl ;

public class CachedRowSetTest {
	public static void main( String [ ] args ) {
		String databaseUrl = "jdbc:mysql://localhost:8888/ebookshop" ;
		String username = "myuser" ;
		String password = "xxxx" ;
		Connection conn = null ;
		CachedRowSet rowSet = null ;

		try {
			conn = DriverManager.getConnection( databaseUrl , username , password ) ;
			conn.setAutoCommit( false ) ; // Need to disable auto-commit for CachedRowSet

			rowSet = new CachedRowSetImpl() ;
			rowSet.setUrl( databaseUrl ) ;
			rowSet.setUsername( username ) ;
			rowSet.setPassword( password ) ;
			rowSet.setCommand( "SELECT * FROM books" ) ;
			int [ ] keys = { 1 } ; // Set column 1 as the key column in the RowSet
			rowSet.setKeyColumns( keys ) ;
			rowSet.execute( conn ) ; // execute on the Connection

			// RowSet is scrollable and updatable
			// Update a row
			rowSet.first() ;
			System.out.println( "-- Update a row --" ) ;
			System.out.println( rowSet.getRow() + ": " + +rowSet.getInt( "id" ) + ", " + rowSet.getString( "title" ) + ", " + rowSet.getString( "author" ) + ", "
					+ rowSet.getDouble( "price" ) + ", " + rowSet.getInt( "qty" ) ) ;
			rowSet.updateDouble( "price" , 99.99 ) ; // update cells
			rowSet.updateInt( "qty" , 99 ) ;
			rowSet.updateRow() ; // update the row in the data source
			System.out.println( rowSet.getRow() + ": " + +rowSet.getInt( "id" ) + ", " + rowSet.getString( "title" ) + ", " + rowSet.getString( "author" ) + ", "
					+ rowSet.getDouble( "price" ) + ", " + rowSet.getInt( "qty" ) ) ;

			// A updatable ResultSet has a special row that serves as a staging area
			// for building a row to be inserted.
			rowSet.moveToInsertRow() ;
			rowSet.updateInt( 1 , 8909 ) ; // Use column number
			rowSet.updateString( 2 , "Even More Programming" ) ;
			rowSet.updateString( 3 , "Kumar" ) ;
			rowSet.updateDouble( 4 , 77.88 ) ;
			rowSet.updateInt( 5 , 77 ) ;
			rowSet.insertRow() ;
			// need to move away from insert row before apply changes
			rowSet.moveToCurrentRow() ;

			// Reconnect to data source to apply change in the RowSet.
			rowSet.acceptChanges( conn ) ; // On non-autocommit Connection
		} catch ( SQLException ex ) {
			ex.printStackTrace() ;
		} finally {
			try {
				rowSet.close() ;
				conn.close() ;
			} catch ( SQLException ex ) {
				ex.printStackTrace() ;
			}
		}
	}
}
