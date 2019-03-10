
package com.keepcount.dao.login ;

import java.math.BigDecimal ;
import java.sql.CallableStatement ;
import java.sql.Connection ;
import java.sql.PreparedStatement ;
import java.sql.ResultSet ;
import java.sql.Timestamp ;
import java.time.LocalDateTime ;
import java.util.ArrayList ;
import java.util.LinkedHashMap ;
import java.util.List ;
import java.util.Map ;

import org.springframework.security.crypto.bcrypt.BCrypt ;

import com.keepcount.dao.dbutils.DBUtils ;
import com.keepcount.dao.dbutils.DBUtilsTypes ;
import com.keepcount.model.login.Login ;
import com.keepcount.model.signup.SignUp ;

public class LoginDAO {

	private static List < String > listOfAllProceduresCreated ;

	public static List < String > getListOfAllProceduresCreated() {
		return listOfAllProceduresCreated ;
	}

	public static void setListOfAllProceduresCreated( List < String > listOfAllProceduresCreated ) {
		LoginDAO.listOfAllProceduresCreated = listOfAllProceduresCreated ;
	}

	private static List < String > listOfAllProceduresAlreadyCreated() throws Exception {
		setListOfAllProceduresCreated( DBUtils.listOfAllProceduresAlreadyCreated() ) ;
		return getListOfAllProceduresCreated() ;
	}

	private static String procName = "insert_into_login_kc" ;
	private static String tableName = "login_kc" ;

	private static Map < String , DBUtilsTypes > findMappingForInsertionParameters() {

		Map < String , DBUtilsTypes > map = new LinkedHashMap <>() ;

		map.put( "email" , new DBUtilsTypes( "email" , "varchar(255)" ) ) ;
		map.put( "password" , new DBUtilsTypes( "password" , "varchar(255)" ) ) ;
		map.put( "time_in" , new DBUtilsTypes( "time_in" , "varchar(255)" ) ) ;
		map.put( "time_out" , new DBUtilsTypes( "time_out" , "varchar(255)" ) ) ;

		return map ;

	}

	private static final String FIND_ALL_LOGINS = "SELECT * FROM login_kc" ;

	private static final String FIND_ALL_LOGINS_BY_EMAIL = "SELECT * FROM login_kc WHERE email=?" ;

	private static final String FIND_PASSWORD_BY_EMAIL = "SELECT * FROM emails_kc WHERE email=?" ;

	private static int insert_into_login_kc ;

	public static int getInsert_into_login_kc() {
		return insert_into_login_kc ;
	}

	public static void setInsert_into_login_kc( int insert_into_login_kc ) {
		LoginDAO.insert_into_login_kc = insert_into_login_kc ;
	}

	public static int login( Login log ) throws Exception {

		String procedureName = procName.concat( "" ) ;

		if ( getListOfAllProceduresCreated() == null ) {
			listOfAllProceduresAlreadyCreated() ;
		}

		if ( !getListOfAllProceduresCreated().contains( procedureName ) ) {
			DBUtils.createInsertionProc( procName , "" , tableName , findMappingForInsertionParameters() , getInsert_into_login_kc() ) ;
			setInsert_into_login_kc( 1 ) ;
		} else {
			setInsert_into_login_kc( 1 ) ;
		}

		// DBUtils.createInsertionProc( procName , "" , tableName ,
		// findMappingForInsertionParameters() ) ;

		int result = 0 ;

		LocalDateTime dateTime = LocalDateTime.now() ;
		Timestamp timestamp = Timestamp.valueOf( dateTime ) ;
		@SuppressWarnings( "deprecation" )
		String timeIn = timestamp.getHours() + ":" + timestamp.getMinutes() ;
		String timeOut = null ;

		Connection connection = DBUtils.getConn() ;
		connection.setAutoCommit( false ) ;

		CallableStatement cs = null ;

		try {

			cs = connection.prepareCall( DBUtils.invocationOfInsertAPI( findMappingForInsertionParameters() , procName , "" ) ) ;

			cs.setString( 1 , log.getEmail() ) ;
			cs.setString( 2 , log.getPassword() ) ;
			cs.setString( 3 , timeIn ) ;
			cs.setString( 4 , timeOut ) ;

			result = cs.executeUpdate() ;

			result = cs.executeUpdate() ;
			System.out.println( "res dao ins: " + result ) ;

			if ( result == 1 ) {
				connection.commit() ;
			} else {
				connection.rollback() ;
			}

		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			connection.close() ;
			cs.close() ;
		}
		return result ;
	}

	private static int get_all_signups ;

	public static int getGet_all_signups() {
		return get_all_signups ;
	}

	public static void setGet_all_signups( int get_all_signups ) {
		LoginDAO.get_all_signups = get_all_signups ;
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

	private static int get_email_from_emails_kc ;

	public static int getGet_email_from_emails_kc() {
		return get_email_from_emails_kc ;
	}

	public static void setGet_email_from_emails_kc( int get_email_from_emails_kc ) {
		LoginDAO.get_email_from_emails_kc = get_email_from_emails_kc ;
	}

	public static String getEmailToCheckIfTheProvidedCorrespondentExists( String emailSpecified ) throws Exception {

		String tableName = "emails_kc" ;
		String procName = "get_email_from_emails_kc" ;
		String whereCond = "WHERE email=email" ;
		String inParams = "in email varchar(255)" ;
		String wildCardValues = "?" ;
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

		// DBUtils.createSelectionProc( inParams , procName , "" , asteriskOrCols ,
		// tableName , whereCond ) ;

		String emailProvided = null ;
		ResultSet rs = null ;

		CallableStatement cs = null ;

		Connection connection = DBUtils.getConn() ;

		connection.setAutoCommit( false ) ;

		try {

			cs = connection.prepareCall( DBUtils.invokeSelection( procName , businessId , wildCardValues ) ) ;

			cs.setString( 1 , emailSpecified.trim() ) ;
			rs = cs.executeQuery() ;

			while ( rs.next() ) {
				String emailFound = rs.getString( "email" ) ;
				String password = rs.getString( "password" ) ;
				Login login = new Login( emailFound , password ) ;
				emailProvided = login.getEmail() ;
			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			rs.close() ;
			cs.close() ;
			connection.close() ;
			DBUtils.getConn().close() ;
		}
		return emailProvided ;
	}

	public static String checkPassword( String emailSpecified ) throws Exception {

		String emailProvided = null ;
		PreparedStatement st = null ;
		ResultSet rs = null ;
		Connection connection = DBUtils.getConn() ;
		try {
			st = connection.prepareStatement( "SELECT * FROM emails_kc WHERE email=?" ) ;
			st.setString( 1 , emailSpecified.trim() ) ;
			rs = st.executeQuery() ;
			while ( rs.next() ) {
				String emailFound = rs.getString( "email" ) ;
				String password = rs.getString( "password" ) ;
				Login login = new Login( emailFound , password ) ;
				emailProvided = login.getPassword() ;
			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( rs , null , st , connection ) ;
		}
		return emailProvided ;
	}

	public static List < Login > findAllLogins() throws Exception {

		List < Login > logins = new ArrayList <>() ;
		PreparedStatement st = null ;
		ResultSet rs = null ;
		Connection connection = DBUtils.getConn() ;
		Login login = null ;

		try {
			st = connection.prepareStatement( LoginDAO.FIND_ALL_LOGINS ) ;
			rs = st.executeQuery() ;
			while ( rs.next() ) {
				BigDecimal id = rs.getBigDecimal( "id" ) ;
				String email = rs.getString( "email" ) ;
				String password = rs.getString( "password" ) ;
				String date = rs.getString( "date" ) ;
				String timeIn = rs.getString( "time_in" ) ;
				String timeOut = rs.getString( "time_out" ) ;

				login = new Login( id , email , password , date , timeIn , timeOut ) ;
				logins.add( login ) ;
			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( rs , null , st , connection ) ;
		}

		return logins ;
	}

	public static List < Login > findLoginByEmail( String em ) throws Exception {

		List < Login > logins = new ArrayList <>() ;
		PreparedStatement st = null ;
		ResultSet rs = null ;
		Connection connection = DBUtils.getConn() ;
		Login login = null ;

		try {
			st = connection.prepareStatement( LoginDAO.FIND_ALL_LOGINS_BY_EMAIL ) ;
			st.setString( 1 , em ) ;
			rs = st.executeQuery() ;
			while ( rs.next() ) {
				BigDecimal id = rs.getBigDecimal( "id" ) ;
				String email = rs.getString( "email" ) ;
				String password = rs.getString( "password" ) ;
				String date = rs.getString( "date" ) ;
				String timeIn = rs.getString( "time_in" ) ;
				String timeOut = rs.getString( "time_out" ) ;

				login = new Login( id , email , password , date , timeIn , timeOut ) ;
				logins.add( login ) ;
			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( rs , null , st , connection ) ;
		}

		return logins ;
	}

	public static String findLoginPasswordByEmail( String em ) throws Exception {

		String password = null ;
		PreparedStatement st = null ;
		ResultSet rs = null ;
		Connection connection = DBUtils.getConn() ;
		try {
			st = connection.prepareStatement( LoginDAO.FIND_PASSWORD_BY_EMAIL ) ;
			st.setString( 1 , em ) ;
			rs = st.executeQuery() ;
			while ( rs.next() ) {
				String pass = rs.getString( "password" ) ;
				password = pass ;
				// System.out.println(password);
			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( rs , null , st , connection ) ;
		}

		return password ;
	}

	private static String checkPass( String plainPassword , String hashedPassword ) {

		if ( BCrypt.checkpw( plainPassword , hashedPassword ) )

			return "true" ;
		else

			return "false" ;

	}

	// public static ArrayList< SignUp > getAllSignedUpEmails() throws Exception {
	//
	// ArrayList< SignUp > signUps = new ArrayList<>();
	//
	// PreparedStatement pst = null;
	// ResultSet rs = null;
	//
	// try {
	//
	// pst = DBUtils.getConn().prepareStatement( LoginDAO.GET_ALL_EMAILS );
	// rs = pst.executeQuery();
	//
	// while ( rs.next() ) {
	// BigDecimal id = rs.getBigDecimal( "id" );
	// String email = rs.getString( "email" );
	// String password = rs.getString( "password" );
	// SignUp signUp = new SignUp( email , password );
	// signUps.add( signUp );
	// }
	//
	// }
	// catch ( Exception e ) {
	// e.printStackTrace();
	// }
	// finally {
	// DBUtils.closeAll();
	// }
	// return signUps;
	// }

	// public static SignUp getEmailSignedUpMatch( String sqlQuery , String
	// emailMatch ) throws Exception {
	//
	// SignUp signUps = null;
	//
	// PreparedStatement pst = null;
	// ResultSet rs = null;
	//
	// try {
	// // "SELECT * FROM emails_kc WHERE email=?"
	// pst = DBUtils.getConn().prepareStatement( sqlQuery );
	// pst.setString( 1 , emailMatch );
	// rs = pst.executeQuery();
	//
	// while ( rs.next() ) {
	// BigDecimal id = rs.getBigDecimal( "id" );
	// String email = rs.getString( "email" );
	// String password = rs.getString( "password" );
	// SignUp signUp = new SignUp( email , password );
	// signUps = signUp;
	// }
	//
	// }
	// catch ( Exception e ) {
	// e.printStackTrace();
	// }
	// finally {
	// DBUtils.closeAll();
	// }
	// return signUps;
	// }

	public static void main( String [ ] args ) throws Exception {

		String hash = LoginDAO.findLoginPasswordByEmail( "alimahmoudraage@gmail.com" ) ;

		// System.out.println(getEmailSignedUpMatch("alimahmoudraage@gmail.com"));

	}

}
