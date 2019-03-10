
package com.keepcount.dao.signup ;

import java.math.BigDecimal ;
import java.sql.CallableStatement ;
import java.sql.Connection ;
import java.sql.ResultSet ;
import java.util.ArrayList ;
import java.util.LinkedHashMap ;
import java.util.List ;
import java.util.Map ;

import org.springframework.security.crypto.bcrypt.BCrypt ;

import com.keepcount.dao.dbutils.DBUtils ;
import com.keepcount.dao.dbutils.DBUtilsTypes ;
import com.keepcount.dao.login.LoginDAO ;
import com.keepcount.model.signup.SignUp ;

public class SignUpDAO {

	private static List < String > listOfAllProceduresCreated ;

	public static List < String > getListOfAllProceduresCreated() {
		return listOfAllProceduresCreated ;
	}

	public static void setListOfAllProceduresCreated( List < String > listOfAllProceduresCreated ) {
		SignUpDAO.listOfAllProceduresCreated = listOfAllProceduresCreated ;
	}

	private static List < String > listOfAllProceduresAlreadyCreated() throws Exception {
		setListOfAllProceduresCreated( DBUtils.listOfAllProceduresAlreadyCreated() ) ;
		return getListOfAllProceduresCreated() ;
	}

	private static String procName = "insert_into_emails_kc" ;
	private static String tableName = "emails_kc" ;

	private static int get_all_signups ;

	public static int getGet_all_signups() {
		return get_all_signups ;
	}

	public static void setGet_all_signups( int get_all_signups ) {
		SignUpDAO.get_all_signups = get_all_signups ;
	}

	public static List < SignUp > findAllSignUps() throws Exception {

		List < SignUp > signUps = new ArrayList <>() ;

		String tableName = "emails_kc" ;
		String procName = "get_all_signups" ;
		String whereCond = "" ;
		String inParams = "" ;
		String wildCardValues = "" ;
		String businessId = "" ;
		String asteriskOrCols = "*" ;

		String procedureName = procName.concat( "" ) ;

		if ( getListOfAllProceduresCreated() == null ) {
			listOfAllProceduresAlreadyCreated() ;
		}

		if ( !getListOfAllProceduresCreated().contains( procedureName ) ) {
			DBUtils.createSelectionProc( inParams , procName , businessId , asteriskOrCols , tableName , whereCond , getGet_all_signups() ) ;
			setGet_all_signups( 1 ) ;
		} else {
			setGet_all_signups( 1 ) ;
		}

		// DBUtils.createSelectionProc( inParams , procName , businessId ,
		// asteriskOrCols , tableName , whereCond ) ;

		ResultSet rs = null ;

		CallableStatement cs = null ;

		Connection connection = DBUtils.getConn() ;

		connection.setAutoCommit( false ) ;

		try {

			cs = connection.prepareCall( DBUtils.invokeSelection( procName , businessId , wildCardValues ) ) ;

			rs = cs.executeQuery() ;

			while ( rs.next() ) {

				BigDecimal id = rs.getBigDecimal( "id" ) ;
				String email = rs.getString( "email" ) ;
				String password = rs.getString( "password" ) ;
				String date = rs.getString( "date_signed_up" ) ;
				SignUp signUp = new SignUp( id , email , password , date ) ;
				signUps.add( signUp ) ;

			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			rs.close() ;
			cs.close() ;
			connection.close() ;
			DBUtils.getConn().close() ;
		}
		return signUps ;
	}

	private static int get_email_by_id ;

	public static int getGet_email_by_id() {
		return get_email_by_id ;
	}

	public static void setGet_email_by_id( int get_email_by_id ) {
		SignUpDAO.get_email_by_id = get_email_by_id ;
	}

	public static SignUp getById( BigDecimal id ) throws Exception {

		String tableName = "emails_kc" ;
		String procName = "get_email_by_id" ;
		String whereCond = "WHERE id=?" ;
		String inParams = "in id bigint" ;
		String wildCardValues = "?" ;
		String businessId = "" ;
		String asteriskOrCols = "*" ;

		String procedureName = procName.concat( "" ) ;

		if ( getListOfAllProceduresCreated() == null ) {
			listOfAllProceduresAlreadyCreated() ;
		}

		if ( !getListOfAllProceduresCreated().contains( procedureName ) ) {
			DBUtils.createSelectionProc( inParams , procName , businessId , asteriskOrCols , tableName , whereCond , getGet_email_by_id() ) ;
			setGet_email_by_id( 1 ) ;
		} else {
			setGet_email_by_id( 1 ) ;
		}

		// DBUtils.createSelectionProc( inParams , procName , businessId ,
		// asteriskOrCols , tableName , whereCond ) ;

		SignUp signUps = new SignUp() ;

		ResultSet rs = null ;

		CallableStatement cs = null ;

		Connection connection = DBUtils.getConn() ;

		connection.setAutoCommit( false ) ;

		try {
			cs = connection.prepareCall( DBUtils.invokeSelection( procName , businessId , wildCardValues ) ) ;
			cs.setBigDecimal( 1 , id ) ;
			rs = cs.executeQuery() ;

			if ( rs.next() ) {
				BigDecimal iD = rs.getBigDecimal( "id" ) ;
				String email = rs.getString( "email" ) ;
				String password = rs.getString( "password" ) ;
				SignUp signUp = null ;
				// new SignUp(iD, email, password);

				signUps = signUp ;
			}

		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( rs , cs , null , connection ) ;
		}
		return signUps ;
	}

	private static String hashPassword( String plainTextPassword ) {

		return BCrypt.hashpw( plainTextPassword , BCrypt.gensalt() ) ;

	}

	public static void main( String [ ] args ) {

		String plainText = "123456" ;

		String hashed = hashPassword( plainText ) ;

		System.out.println( hashed ) ;

		String pass = "$2a$10$WpNKlOc1YcI09lLlpoj/GOTRQN2HdYoItVmc5BojR397vzsMjhvrm" ;

		boolean t = BCrypt.checkpw( plainText , pass ) ;
		System.out.println( "t: " + t ) ;

	}

	private static Map < String , DBUtilsTypes > findMappingForInsertionParameters() {

		Map < String , DBUtilsTypes > map = new LinkedHashMap <>() ;

		map.put( "email" , new DBUtilsTypes( "email" , "varchar(255)" ) ) ;
		map.put( "password" , new DBUtilsTypes( "password" , "varchar(255)" ) ) ;

		return map ;

	}

	public static int signUpNewUser( SignUp up ) throws Exception {

		System.out.println( "signup in dao: " + up.getEmail() + "...." + up.getPassword() ) ;

		Connection connection = DBUtils.getConn() ;
		connection.setAutoCommit( false ) ;

		CallableStatement cs = null ;

		int result = 0 ;
		try {

			String hashedPassword = hashPassword( up.getPassword() ) ;

			System.out.println( "signup: " + up.getPassword() ) ;

			cs = connection.prepareCall( DBUtils.invocationOfInsertAPI( findMappingForInsertionParameters() , procName , "" ) ) ;
			cs.setString( 1 , up.getEmail() ) ;
			cs.setString( 2 , hashedPassword ) ;
			result = cs.executeUpdate() ;

			if ( result == 1 ) {
				connection.commit() ;
			} else {
				connection.rollback() ;
			}

		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			cs.close() ;
			connection.close() ;
		}

		return result ;
	}

}
