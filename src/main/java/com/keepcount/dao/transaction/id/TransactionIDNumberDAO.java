
package com.keepcount.dao.transaction.id ;

import java.math.BigDecimal ;
import java.sql.CallableStatement ;
import java.sql.Connection ;
import java.sql.PreparedStatement ;
import java.sql.ResultSet ;
import java.util.LinkedHashMap ;
import java.util.List ;
import java.util.Map ;

import com.keepcount.dao.dbutils.DBUtils ;
import com.keepcount.dao.dbutils.DBUtilsTypes ;
import com.keepcount.model.transaction.id.TransactionIDNumber ;
import com.mysql.jdbc.Statement ;

public class TransactionIDNumberDAO {

	private static List < String > listOfAllProceduresCreated ;

	public static List < String > getListOfAllProceduresCreated() {
		return listOfAllProceduresCreated ;
	}

	public static void setListOfAllProceduresCreated( List < String > listOfAllProceduresCreated ) {
		TransactionIDNumberDAO.listOfAllProceduresCreated = listOfAllProceduresCreated ;
	}

	private static List < String > listOfAllProceduresAlreadyCreated() throws Exception {
		setListOfAllProceduresCreated( DBUtils.listOfAllProceduresAlreadyCreated() ) ;
		return getListOfAllProceduresCreated() ;
	}

	private static int procExisst = 0 ;

	public static int getProcExisst() {
		return procExisst ;
	}

	public static void setProcExisst( int procExisst ) {
		TransactionIDNumberDAO.procExisst = procExisst ;
	}

	private static String createTableStr = "(id bigint primary key auto_increment".concat( ", trans_id bigint not null)" ) ;

	private static int checkTableExistence() {
		int result = 0 ;

		String tableName = "transaction_id" ;
		String businessId = "" ;

		System.out.println( "checking..." ) ;

		try {

			if ( TransactionIDNumberDAO.getProcExisst() == 0 ) {
				result = DBUtils.checkTableExistence( tableName , createTableStr , businessId ) ;
				TransactionIDNumberDAO.setProcExisst( 1 ) ;
			}

		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return result ;
	}

	private static Map < String , DBUtilsTypes > findMappingForInsertionParameters() {
		Map < String , DBUtilsTypes > map = new LinkedHashMap <>() ;
		map.put( "trans_id" , new DBUtilsTypes( "trans_id" , "bigint unique" ) ) ;
		return map ;
	}

	private static int insert_into_transaction_id_existence ;

	public static int getInsert_into_transaction_id_existence() {
		return insert_into_transaction_id_existence ;
	}

	public static void setInsert_into_transaction_id_existence( int insert_into_transaction_id_existence ) {
		TransactionIDNumberDAO.insert_into_transaction_id_existence = insert_into_transaction_id_existence ;
	}

	private static BigDecimal currentTransactionIdNeeded ;

	public static BigDecimal getCurrentTransactionIdNeeded() {
		return currentTransactionIdNeeded ;
	}

	public static void setCurrentTransactionIdNeeded( BigDecimal currentTransactionIdNeeded ) {
		TransactionIDNumberDAO.currentTransactionIdNeeded = currentTransactionIdNeeded ;
	}

	public static int newTransactionId() throws Exception {
		TransactionIDNumber transactionIDNumber = new TransactionIDNumber() ;
		String procName = "into_transaction_id" ;
		String businessId = "" ;
		String tableName = "transaction_id" ;
		String procedureName = procName.concat( businessId ) ;
		checkTableExistence() ;
		// if ( getListOfAllProceduresCreated() == null ) {
		// listOfAllProceduresAlreadyCreated() ;
		// System.out.println( listOfAllProceduresAlreadyCreated() ) ;
		// }
		// if ( !getListOfAllProceduresCreated().contains( procedureName ) ) {
		// DBUtils.createInsertionProc( procName , businessId , tableName ,
		// TransactionIDNumberDAO.findMappingForInsertionParameters() ,
		// getInsert_into_transaction_id_existence() ) ;
		// setInsert_into_transaction_id_existence( 1 ) ;
		// } else {
		// setInsert_into_transaction_id_existence( 1 ) ;
		// }
		int result = 0 ;

		PreparedStatement ps = null ;

		Connection connection = DBUtils.getConn() ;
		connection.setAutoCommit( false ) ;
		try {

			connection.setAutoCommit( false ) ;

			BigDecimal lastTransactionId = getLastTransactionId() ;
			transactionIDNumber.setTransactionId( lastTransactionId.add( BigDecimal.ONE ) ) ;

			setTheRequiredTransactionId( transactionIDNumber.getTransactionId() ) ;

			/*
			 * This transaction ID is to be passed or invoked by other classes or operations that will need it
			 * as their desired transaction ID
			 */
			TransactionIDNumberDAO.setCurrentTransactionIdNeeded( transactionIDNumber.getTransactionId() ) ;

			ps = connection.prepareStatement( "insert into transaction_id (trans_id) values(?)" , PreparedStatement.RETURN_GENERATED_KEYS ) ;
			ps.setBigDecimal( 1 , transactionIDNumber.getTransactionId() ) ;
			result = ps.executeUpdate() ;

			// result = cs.executeUpdate() ;
			if ( result == 1 ) {
				connection.commit() ;

				ResultSet rs = ps.getGeneratedKeys() ;

				if ( rs.next() ) {

					BigDecimal key = rs.getBigDecimal( 1 ) ;

					setTheRequiredTransactionId( key ) ;

					System.out.println( "key: " + key ) ;

				}

			} else {
				connection.rollback() ;
			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
			connection.rollback() ;
		} finally {
			DBUtils.closeConnections( null , null , ps , connection ) ;
		}
		return result ;
	}

	private static BigDecimal getLastTransactionId() throws Exception {
		String sql = "select trans_id from transaction_id where id=( select max(id) from transaction_id )" ;
		PreparedStatement preparedStatementToGetLastTransactionId = null ;
		ResultSet resultSetToGetLastTransactionId = null ;
		Connection connectionToGetLastTransactionId = DBUtils.getConn() ;
		BigDecimal lastTransactionId = BigDecimal.ZERO ;
		try {
			preparedStatementToGetLastTransactionId = connectionToGetLastTransactionId.prepareStatement( sql ) ;
			resultSetToGetLastTransactionId = preparedStatementToGetLastTransactionId.executeQuery() ;
			if ( resultSetToGetLastTransactionId.next() ) {
				BigDecimal big = resultSetToGetLastTransactionId.getBigDecimal( "trans_id" ) ;
				if ( big == null ) {
					lastTransactionId = BigDecimal.ZERO ;
				} else {
					lastTransactionId = big ;
				}
			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return lastTransactionId ;
	}

	private static BigDecimal theRequiredTransactionId = BigDecimal.ZERO ;

	public static BigDecimal getTheRequiredTransactionId() {
		return theRequiredTransactionId ;
	}

	public static void setTheRequiredTransactionId( BigDecimal theRequiredTransactionId ) {
		TransactionIDNumberDAO.theRequiredTransactionId = theRequiredTransactionId ;
	}

	public static BigDecimal getTheRequiredTransactionIdForAllCurrentTransactions() {
		return getTheRequiredTransactionId() ;
	}

	public static void main( String [ ] args ) throws Exception {
		// System.out.println( getLastTransactionId() ) ;
		// newTransactionId() ;
	}

}
