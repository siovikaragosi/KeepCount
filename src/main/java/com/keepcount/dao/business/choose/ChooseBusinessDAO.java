
package com.keepcount.dao.business.choose ;

import java.math.BigDecimal ;
import java.sql.Connection ;
import java.sql.DatabaseMetaData ;
import java.sql.PreparedStatement ;
import java.sql.ResultSet ;
import java.sql.Timestamp ;
import java.time.LocalDateTime ;
import java.util.ArrayList ;
import java.util.List ;

import org.springframework.security.crypto.bcrypt.BCrypt ;

import com.keepcount.dao.dbutils.DBUtils ;
import com.keepcount.model.business.choose.ChooseBusiness ;
import com.keepcount.model.business.create.EmailsOfABusinessLogin ;

public class ChooseBusinessDAO {

	// private static final String FIND_THE_BUSINESS_NAME = "SELECT business_name
	// FROM emails_of_a_business WHERE email=?";

	private static String emailOfTheBusiness ;
	private static String emailLoggedIn ;

	private static final String FIND_THE_BUSINESS_EMAIL_FROM_BUSINESS_TYPE = "SELECT * FROM business_type WHERE business_name=?" ;

	private static final String FIND_THE_BUSINESS_NAME_FROM_BUSINESS_TYPE = "SELECT * FROM business_type WHERE email=?" ;

	private static final String FIND_THE_BUSINESS_EMAIL_AND_NAME_FOR_COMPARISON_FROM_EMAILS_OF_A_BUSINESS = "SELECT * FROM emails_of_a_business WHERE email=? AND business_name=?" ;;

	private static String TABLE_NAME = null ;

	public static BigDecimal findIdOfBusinessByEmailLoggedInAndBusinessName( String emailToFindBizId , String businessNameToFindBizId ) throws Exception {

		BigDecimal id = BigDecimal.ZERO ;
		PreparedStatement st = null ;
		ResultSet rs = null ;

		Connection connection = DBUtils.getConn() ;

		try {
			st = connection.prepareStatement( "select id_biz_type from emails_of_a_business where email = ? and business_name=?" ) ;
			st.setString( 1 , emailToFindBizId ) ;
			st.setString( 2 , businessNameToFindBizId ) ;
			// st.executeQuery();
			rs = st.executeQuery() ;
			if ( rs.next() ) {
				id = rs.getBigDecimal( "id_biz_type" ) ;
			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( rs , null , st , connection ) ;
		}
		return id ;
	}

	private static int checkLoginAndContinueTableExistence( String tableNameBusinessName , String tableNameEmail ) throws Exception {

		int result = 0 ;

		String LOGIN_AND_CONTINUE_TABLE_NAME = "`login_".concat( tableNameBusinessName ).concat( tableNameEmail ).concat( "`" ) ;

		Connection connection = DBUtils.getConn() ;

		DatabaseMetaData data = connection.getMetaData() ;
		ResultSet rs = data.getTables( null , null , LOGIN_AND_CONTINUE_TABLE_NAME , null ) ;
		PreparedStatement st = null ;
		try {
			if ( !rs.next() ) {
				st = connection.prepareStatement( "CREATE TABLE ".concat( LOGIN_AND_CONTINUE_TABLE_NAME ).concat( "(id bigint primary key auto_increment," )
						.concat( "email varchar(255)," ).concat( "business_name varchar(255)," ).concat( "password varchar(255)," )
						.concat( "date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," ).concat( "time_in TIME," ).concat( "time_out TIME)" ) ) ;

				result = st.executeUpdate() ;
			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( rs , null , st , connection ) ;
		}
		return result ;
	}

	private static String theEmailOfThisBusiness( String loggedInEmail , String businessNameChosen ) throws Exception {

		List < EmailAndBizName > andBizNames = findAllFromEmailsOfABusinessBasingOnBizName( businessNameChosen ) ;

		EmailAndBizName fromEmailsOfABusiness = findAllEmailsAndBizNamesFromEmailsOfABusiness( loggedInEmail , businessNameChosen ) ;

		EmailAndBizName fromBusinessTypeToGetBizEmail = findEmailOfFromBusinessType( businessNameChosen ) ;

		EmailAndBizName emailAndBizName = new EmailAndBizName( loggedInEmail , businessNameChosen ) ;

		String emailOfTheBusiness = null ;

		// System.out.println(andBizNames);
		// System.out.println("biz: " + emailAndBizName);
		// System.out.println("from biz: " + fromEmailsOfABusiness);
		// System.out.println("biz email: " + fromBusinessTypeToGetBizEmail.getEmail());

		if ( ( andBizNames.stream().findFirst().get().getBizName().equals( emailAndBizName.getBizName() )
				&& andBizNames.stream().findFirst().get().getEmail().equals( emailAndBizName.getEmail() ) )
				&& ( andBizNames.stream().findFirst().get().getBizName().equals( fromBusinessTypeToGetBizEmail.getBizName() )
						&& andBizNames.stream().findAny().get().getEmail().equals( fromBusinessTypeToGetBizEmail.getEmail() ) ) ) {
			return emailOfTheBusiness ;
		}

		return emailOfTheBusiness ;
	}

	public static int chooseABusinessAndContinue( String loggedInEmail , String businessNameChosen , ChooseBusiness choose ) throws Exception {

		int result = 0 ;
		PreparedStatement st = null ;

		List < EmailAndBizName > andBizNames = findAllFromEmailsOfABusinessBasingOnBizName( businessNameChosen ) ;

		EmailAndBizName fromEmailsOfABusiness = findAllEmailsAndBizNamesFromEmailsOfABusiness( loggedInEmail , businessNameChosen ) ;

		EmailAndBizName fromBusinessTypeToGetBizEmail = findEmailOfFromBusinessType( businessNameChosen ) ;

		EmailAndBizName emailAndBizName = new EmailAndBizName( loggedInEmail , businessNameChosen ) ;

		String emailOfTheBusiness = null ;

		Connection connection = DBUtils.getConn() ;

		try {

			System.out.println( "+id: " + andBizNames ) ;
			System.out.println( "biz: " + emailAndBizName ) ;
			System.out.println( "from biz: " + fromEmailsOfABusiness ) ;
			System.out.println( "biz email: " + fromBusinessTypeToGetBizEmail.getEmail() ) ;

			if ( ( andBizNames.stream().findFirst().get().getBizName().equals( emailAndBizName.getBizName() )
					&& andBizNames.stream().findFirst().get().getEmail().equals( emailAndBizName.getEmail() ) )
					&& ( andBizNames.stream().findFirst().get().getBizName().equals( fromBusinessTypeToGetBizEmail.getBizName() )
							&& andBizNames.stream().findAny().get().getEmail().equals( fromBusinessTypeToGetBizEmail.getEmail() ) ) ) {

				setEmailOfTheBusiness( emailOfTheBusiness ) ;

				emailOfTheBusiness = fromBusinessTypeToGetBizEmail.getEmail() ;

				System.out.println( emailOfTheBusiness ) ;

				checkLoginAndContinueTableExistence( businessNameChosen , emailOfTheBusiness ) ;

				TABLE_NAME = "`login_".concat( businessNameChosen ).concat( emailOfTheBusiness ).concat( "`" ) ;

				st = connection.prepareStatement( "INSERT INTO ".concat( TABLE_NAME ).concat( "" ).concat( "(business_name," ).concat( "email," ).concat( "time_in) " )
						.concat( " " ).concat( "VALUES(?,?,?)" ) ) ;
				LocalDateTime localDateTime = LocalDateTime.now() ;

				Timestamp timestamp = Timestamp.valueOf( localDateTime ) ;

				@SuppressWarnings( "deprecation" )
				int hours = timestamp.getHours() ;
				@SuppressWarnings( "deprecation" )
				int minutes = timestamp.getMinutes() ;
				@SuppressWarnings( "deprecation" )
				int seconds = timestamp.getSeconds() ;

				String time = hours + ":" + minutes + ":" + seconds ;

				// login = new Login(id, email, password, date, timeIn, timeOut);

				st.setString( 1 , choose.getBusinessName() ) ;
				st.setString( 2 , loggedInEmail ) ;
				st.setString( 3 , time ) ;

				result = st.executeUpdate() ;
			} else {
				System.out.println( false ) ;
			}

		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( null , null , st , connection ) ;
		}
		return result ;
	}

	public static EmailAndBizName findAllEmailsAndBizNamesFromEmailsOfABusiness( String email , String bizName ) throws Exception {

		EmailAndBizName andBizName = new EmailAndBizName() ;
		// dao.new EmailAndBizName();
		PreparedStatement st = null ;
		ResultSet rs = null ;
		Connection connection = DBUtils.getConn() ;
		try {
			st = connection.prepareStatement( FIND_THE_BUSINESS_EMAIL_AND_NAME_FOR_COMPARISON_FROM_EMAILS_OF_A_BUSINESS ) ;
			st.setString( 1 , email ) ;
			st.setString( 2 , bizName ) ;
			rs = st.executeQuery() ;
			while ( rs.next() ) {
				String emaiL = rs.getString( "email" ) ;
				String businessName = rs.getString( "business_name" ) ;
				EmailAndBizName andBizName2 = new EmailAndBizName( emaiL , businessName ) ;
				andBizName = andBizName2 ;
			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( rs , null , st , connection ) ;
		}
		return andBizName ;
	}

	private static List < EmailAndBizName > findAllFromEmailsOfABusinessBasingOnBizName( String bizName ) throws Exception {

		List < EmailAndBizName > andBizName = new ArrayList <>() ;
		PreparedStatement st = null ;
		ResultSet rs = null ;

		Connection connection = DBUtils.getConn() ;
		try {
			st = connection.prepareStatement( "SELECT * FROM business_type WHERE business_name=?" ) ;
			st.setString( 1 , bizName ) ;
			rs = st.executeQuery() ;
			while ( rs.next() ) {
				BigDecimal id = rs.getBigDecimal( "id" ) ;
				String emaiL = rs.getString( "email" ) ;
				String businessName = rs.getString( "business_name" ) ;
				EmailAndBizName andBizName2 = new EmailAndBizName( id , emaiL , businessName ) ;
				andBizName.add( andBizName2 ) ;
			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( rs , null , st , connection ) ;
		}
		return andBizName ;
	}

	public static BigDecimal findBusinessIdBasingOnEmail() throws Exception {

		BigDecimal id = BigDecimal.ZERO ;
		PreparedStatement st = null ;
		ResultSet rs = null ;

		Connection connection = DBUtils.getConn() ;

		try {
			st = connection.prepareStatement( "select id from businessType" ) ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( rs , null , st , connection ) ;
		}
		return id ;
	}

	public static void main( String [ ] args ) throws Exception {

		String email = "alimahmoudraage@gmail.com" ;
		String businessName = "b5" ;

		// System.out.println(theEmailOfThisBusiness(email, businessName));
		// chooseABusinessAndContinue(email, businessName);
		System.out.println( findEmailOfFromBusinessType( businessName ) ) ;
		System.out.println( getTheEmailOfTheBusiness( businessName ) ) ;
		System.out.println( findAllFromEmailsOfABusinessBasingOnBizName( businessName ) ) ;

	}

	public static String getTheEmailOfTheBusiness( String bizName ) throws Exception {

		String emailOfBusinessGot = null ;
		EmailAndBizName emailOfTheBiz = findEmailOfFromBusinessType( bizName ) ;

		List < EmailAndBizName > list1 = findAllFromEmailsOfABusinessBasingOnBizName( bizName ) ;
		List < String > strings = new ArrayList <>() ;
		for ( EmailAndBizName emailAndBizName : list1 ) {
			String string = emailAndBizName.getEmail() ;
			strings.add( string ) ;
		}

		if ( strings.contains( emailOfTheBiz.getEmail() ) ) {
			emailOfBusinessGot = emailOfTheBiz.getEmail() ;
		}

		return emailOfBusinessGot ;
	}

	public static EmailAndBizName findEmailOfFromBusinessType( String bizName ) throws Exception {

		EmailAndBizName andBizName = new EmailAndBizName() ;
		PreparedStatement st = null ;
		ResultSet rs = null ;
		Connection connection = DBUtils.getConn() ;
		try {
			st = connection.prepareStatement( FIND_THE_BUSINESS_EMAIL_FROM_BUSINESS_TYPE ) ;
			st.setString( 1 , bizName ) ;
			rs = st.executeQuery() ;
			if ( rs.next() ) {
				String emaiL = rs.getString( "email" ) ;
				String businessName = rs.getString( "business_name" ) ;
				EmailAndBizName andBizName2 = new EmailAndBizName( emaiL , businessName ) ;
				andBizName = andBizName2 ;
			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( rs , null , st , connection ) ;
		}
		return andBizName ;
	}

	public static String getEmailLoggedIn() {

		return emailLoggedIn ;
	}

	public static void setEmailLoggedIn( String emailLoggedIn ) {

		ChooseBusinessDAO.emailLoggedIn = emailLoggedIn ;
	}

	public static String getEmailOfTheBusiness() {

		return emailOfTheBusiness ;
	}

	public static void setEmailOfTheBusiness( String emailOfTheBusiness ) {

		ChooseBusinessDAO.emailOfTheBusiness = emailOfTheBusiness ;
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private static final String ADD_NEW_BUSINESS_EMAIL = "INSERT INTO emails_of_a_business(email, password, business_name, user_name) VALUES(?,?,?,?) " ;

	private static final String FIND_ALL_BUSINESS_OF_THIS_EMAIL = "SELECT * FROM emails_of_a_business WHERE email=?" ;

	private static final String DELETE_EMAIL_OF_A_BUSINESS = "DELETE FROM emails_of_a_business WHERE email=? AND id=? AND business_name=?" ;

	private static final String UPDATE_EMAIL_OF_A_BUSINESS = "UPDATE emails_of_a_business SET email=?, password=?, user_name=? WHERE id=?" ;

	public static int addNewEmailForABusiness( EmailsOfABusinessLogin emails ) throws Exception {

		int result = 0 ;
		PreparedStatement st = null ;
		Connection connection = DBUtils.getConn() ;
		try {
			st = connection.prepareStatement( ADD_NEW_BUSINESS_EMAIL ) ;
			st.setString( 1 , emails.getEmail() ) ;
			st.setString( 2 , BCrypt.hashpw( emails.getPassword() , BCrypt.gensalt() ) ) ;
			st.setString( 3 , emails.getBusinessName() ) ;
			st.setString( 4 , emails.getUserName() ) ;
			result = st.executeUpdate() ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( null , null , st , connection ) ;
		}
		return result ;
	}

	public static int updateEmailOfABusiness( BigDecimal id ) throws Exception {

		int result = 0 ;
		PreparedStatement st = null ;
		Connection connection = DBUtils.getConn() ;
		try {
			st = connection.prepareStatement( UPDATE_EMAIL_OF_A_BUSINESS ) ;
			st.setBigDecimal( 1 , id ) ;
			result = st.executeUpdate() ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( null , null , st , connection ) ;
		}
		return result ;
	}

	public static int deleteEmailFromABusiness( String email , BigDecimal id , String businessName ) throws Exception {

		int result = 0 ;
		PreparedStatement st = null ;
		Connection connection = DBUtils.getConn() ;
		try {
			st = connection.prepareStatement( DELETE_EMAIL_OF_A_BUSINESS ) ;
			st.setString( 1 , email ) ;
			st.setBigDecimal( 2 , id ) ;
			st.setString( 3 , businessName ) ;
			result = st.executeUpdate() ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( null , null , st , connection ) ;
		}
		return result ;
	}

	public static List < EmailsOfABusinessLogin > findAllBusinessesOfThisEmail( String email ) throws Exception {

		List < EmailsOfABusinessLogin > businesses = new ArrayList <>() ;
		PreparedStatement st = null ;
		ResultSet rs = null ;
		Connection connection = DBUtils.getConn() ;
		try {
			st = connection.prepareStatement( FIND_ALL_BUSINESS_OF_THIS_EMAIL ) ;
			st.setString( 1 , email ) ;
			rs = st.executeQuery() ;
			while ( rs.next() ) {
				BigDecimal id = rs.getBigDecimal( "id" ) ;
				String emaiL = rs.getString( "email" ) ;
				String password = rs.getString( "password" ) ;
				String businessName = rs.getString( "business_name" ) ;
				String userName = rs.getString( "user_name" ) ;
				EmailsOfABusinessLogin business = new EmailsOfABusinessLogin( id , emaiL , password , businessName , userName ) ;
				businesses.add( business ) ;
			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( rs , null , st , connection ) ;
		}
		return businesses ;
	}

}
