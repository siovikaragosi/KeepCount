
package com.keepcount.dao.business.create ;

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
import com.keepcount.model.business.create.CreateBusiness ;
import com.keepcount.model.business.create.EmailsOfABusinessLogin ;

public class CreateBusinessDAO {

	private static List < String > listOfAllProceduresCreated ;

	public static List < String > getListOfAllProceduresCreated() {
		return listOfAllProceduresCreated ;
	}

	public static void setListOfAllProceduresCreated( List < String > listOfAllProceduresCreated ) {
		CreateBusinessDAO.listOfAllProceduresCreated = listOfAllProceduresCreated ;
	}

	private static List < String > listOfAllProceduresAlreadyCreated() throws Exception {
		setListOfAllProceduresCreated( DBUtils.listOfAllProceduresAlreadyCreated() ) ;
		return getListOfAllProceduresCreated() ;
	}

	// private static final String FIND_BUSINESSES_TYPES_BY_EMAIL = "SELECT * FROM
	// business_type WHERE email=?";
	//
	// private static final String CREATE_BUSINESS_TYPE = "INSERT INTO
	// business_type(email, business_type, business_name) VALUES(?,?,?)";

	// private static final String UPDATE_BUSINESS_TYPE = "UPDATE business_type SET
	// business_type=?, business_name=? WHERE EMAIL=? AND id=?";

	// private static final String ADD_NEW_BUSINESS_EMAIL = "INSERT INTO
	// emails_of_a_business(email, password, business_name, user_name, id_biz_type)
	// VALUES(?,?,?,?,?) ";

	private static Map < String , DBUtilsTypes > findMappingForInsertionParameters() {

		Map < String , DBUtilsTypes > map = new LinkedHashMap <>() ;

		map.put( "email" , new DBUtilsTypes( "email" , "varchar(255)" ) ) ;
		map.put( "password" , new DBUtilsTypes( "password" , "varchar(255)" ) ) ;
		map.put( "business_name" , new DBUtilsTypes( "business_name" , "varchar(255)" ) ) ;
		map.put( "user_name" , new DBUtilsTypes( "user_name" , "varchar(255)" ) ) ;
		map.put( "id_biz_type" , new DBUtilsTypes( "id_biz_type" , "bigint" ) ) ;
		map.put( "phone_number" , new DBUtilsTypes( "phone_number" , "varchar(255)" ) ) ;

		return map ;

	}

	private static int create_new_email_for_a_businessExistence ;

	public static int getCreate_new_email_for_a_businessExistence() {
		return create_new_email_for_a_businessExistence ;
	}

	public static void setCreate_new_email_for_a_businessExistence( int create_new_email_for_a_businessExistence ) {
		CreateBusinessDAO.create_new_email_for_a_businessExistence = create_new_email_for_a_businessExistence ;
	}

	public static int addNewEmailForABusiness( EmailsOfABusinessLogin emails ) throws Exception {

		String procName = "create_new_email_for_a_business" ;
		String tableName = "emails_of_a_business" ;
		String businessId = "" ;

		String procedureName = procName.concat( businessId ) ;

		if ( getListOfAllProceduresCreated() == null ) {
			listOfAllProceduresAlreadyCreated() ;
		}

		if ( !getListOfAllProceduresCreated().contains( procedureName ) ) {
			DBUtils.createInsertionProc( procName , "" , tableName , findMappingForInsertionParameters() , getCreate_new_email_for_a_businessExistence() ) ;
			setCreate_new_email_for_a_businessExistence( 1 ) ;
		} else {
			setCreate_new_email_for_a_businessExistence( 1 ) ;
		}

		// DBUtils.createInsertionProc( procName , "" , tableName ,
		// findMappingForInsertionParameters() ) ;

		Connection connection = DBUtils.getConn() ;
		connection.setAutoCommit( false ) ;

		CallableStatement cs = null ;

		int result = 0 ;
		try {

			// Map< String, DBUtilsTypes > procParamsForInsert , String procName , String
			// businessId

			cs = connection.prepareCall( DBUtils.invocationOfInsertAPI( findMappingForInsertionParameters() , procName , businessId ) ) ;

			cs.setString( 1 , emails.getEmail() ) ;
			cs.setString( 2 , BCrypt.hashpw( emails.getPassword() , BCrypt.gensalt() ) ) ;
			cs.setString( 3 , emails.getBusinessName() ) ;
			cs.setString( 4 , emails.getUserName() ) ;
			cs.setBigDecimal( 5 , emails.getIdBizType() ) ;
			cs.setString( 6 , emails.getPhoneNumber() ) ;
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
			// DBUtils.getConn().close();
		}
		return result ;
	}

	private static int emails_of_a_businessExistence ;

	public static int getEmails_of_a_businessExistence() {
		return emails_of_a_businessExistence ;
	}

	public static void setEmails_of_a_businessExistence( int emails_of_a_businessExistence ) {
		CreateBusinessDAO.emails_of_a_businessExistence = emails_of_a_businessExistence ;
	}

	public static BigDecimal findIdOfBusinessByEmail( String emailToFindBizId ) throws Exception {

		String tableName = "emails_of_a_business" ;
		String procName = "get_biz_id_by_email" ;
		String whereCond = "WHERE email=email" ;
		String inParams = "in email varchar(255)" ;
		String wildCardValues = "?" ;
		String businessId = "" ;
		String asteriskOrCols = "id_biz_type" ;

		String procedureName = procName.concat( businessId ) ;

		if ( getListOfAllProceduresCreated() == null ) {
			listOfAllProceduresAlreadyCreated() ;
		}

		if ( !getListOfAllProceduresCreated().contains( procedureName ) ) {
			DBUtils.createSelectionProc( inParams , procName , "" , asteriskOrCols , tableName , whereCond , getEmails_of_a_businessExistence() ) ;
			setEmails_of_a_businessExistence( 1 ) ;
		} else {
			setEmails_of_a_businessExistence( 1 ) ;
		}

		// DBUtils.createSelectionProc( inParams , procName , "" , asteriskOrCols ,
		// tableName , whereCond ) ;

		ResultSet rs = null ;

		CallableStatement cs = null ;

		Connection connection = DBUtils.getConn() ;

		connection.setAutoCommit( false ) ;

		BigDecimal id = BigDecimal.ZERO ;
		try {
			cs = connection.prepareCall( DBUtils.invokeSelection( procName , businessId , wildCardValues ) ) ;
			cs.setString( 1 , emailToFindBizId ) ;
			rs = cs.executeQuery() ;
			if ( rs.next() ) {
				id = rs.getBigDecimal( "id_biz_type" ) ;
			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			rs.close() ;
			connection.close() ;
			// DBUtils.getConn().close();
		}
		return id ;
	}

	private static int create_new_businessExistence ;

	public static int getCreate_new_businessExistence() {
		return create_new_businessExistence ;
	}

	public static void setCreate_new_businessExistence( int create_new_businessExistence ) {
		CreateBusinessDAO.create_new_businessExistence = create_new_businessExistence ;
	}

	public static int createBusiness( CreateBusiness biz ) throws Exception {

		Map < String , DBUtilsTypes > map = new LinkedHashMap <>() ;

		map.put( "email" , new DBUtilsTypes( "email" , "varchar(255)" ) ) ;
		map.put( "business_type" , new DBUtilsTypes( "business_type" , "varchar(255)" ) ) ;
		map.put( "business_name" , new DBUtilsTypes( "business_name" , "varchar(255)" ) ) ;

		String procName = "create_new_business" ;
		String tableName = "business_type" ;
		String businessId = "" ;
		String procedureName = procName.concat( businessId ) ;

		if ( getListOfAllProceduresCreated() == null ) {
			listOfAllProceduresAlreadyCreated() ;
		}

		if ( !getListOfAllProceduresCreated().contains( procedureName ) ) {
			DBUtils.createInsertionProc( procName , businessId , tableName , map , getCreate_new_businessExistence() ) ;
			setCreate_new_businessExistence( 1 ) ;
		} else {
			setCreate_new_businessExistence( 1 ) ;
		}
		// DBUtils.createInsertionProc( procName , businessId , tableName , map ) ;

		Connection connection = DBUtils.getConn() ;
		connection.setAutoCommit( false ) ;

		CallableStatement cs = null ;

		int result = 0 ;
		try {

			cs = connection.prepareCall( DBUtils.invocationOfInsertAPI( map , procName , businessId ) ) ;
			cs.setString( 1 , biz.getEmail() ) ;
			cs.setString( 2 , biz.getType() ) ;
			cs.setString( 3 , biz.getBusinessName() ) ;
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
			// DBUtils.getConn().close();
		}
		return result ;
	}

	private static Map < String , DBUtilsTypes > findMappingForUpdateParameters() {

		Map < String , DBUtilsTypes > map = new LinkedHashMap <>() ;

		map.put( "business_type" , new DBUtilsTypes( "business_type" , "varchar(255)" ) ) ;
		map.put( "business_name" , new DBUtilsTypes( "business_name" , "bigint" ) ) ;
		map.put( "phone_number" , new DBUtilsTypes( "phone_number" , "varchar(255)" ) ) ;
		// map.put( "item_id" , new DBUtilsTypes( "item_id" , "varchar(255)" ) );

		return map ;

	}

	private static int update_business_type ;

	public static int getUpdate_business_type() {
		return update_business_type ;
	}

	public static void setUpdate_business_type( int update_business_type ) {
		CreateBusinessDAO.update_business_type = update_business_type ;
	}

	public static int updateBusiness( String email , BigDecimal id ) throws Exception {

		String procName = "update_business_type" ;
		String tableName = "business_type" ;
		String whereCond = " WHERE email = in_email AND id=in_id" ;
		String businessId = "" ;

		List < String > whereList = new ArrayList <>() ;
		whereList.add( "email = in_email" ) ;
		whereList.add( "id = in_id" ) ;

		String procedureName = procName.concat( businessId ) ;

		if ( getListOfAllProceduresCreated() == null ) {
			listOfAllProceduresAlreadyCreated() ;
		}

		if ( !getListOfAllProceduresCreated().contains( procedureName ) ) {
			DBUtils.createUpdatingProc( procName , businessId , tableName , findMappingForUpdateParameters() , whereCond , whereList , getUpdate_business_type() ) ;
			setUpdate_business_type( 1 ) ;
		} else {
			setUpdate_business_type( 1 ) ;
		}

		// DBUtils.createUpdatingProc( procName , businessId , tableName ,
		// findMappingForUpdateParameters() , whereCond , whereList ) ;

		int result = 0 ;
		CallableStatement cs = null ;
		Connection connection = DBUtils.getConn() ;
		connection.setAutoCommit( false ) ;
		try {
			cs = connection.prepareCall( DBUtils.invocationOfUpdatetAPI( findMappingForUpdateParameters() , procName , businessId ) ) ;
			// String UPDATE_BUSINESS_TYPE = "UPDATE business_type SET business_type=?,
			// business_name=? WHERE EMAIL=? AND id=?";

			// st = DBUtils.getConn().prepareStatement( UPDATE_BUSINESS_TYPE );
			cs.setString( 1 , email ) ;
			cs.setBigDecimal( 2 , id ) ;
			result = cs.executeUpdate() ;
			if ( result == 1 ) {
				connection.commit() ;
				System.out.println( "update successful" ) ;
			} else {
				connection.rollback() ;
				System.out.println( "update failed" ) ;
			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			cs.close() ;
			connection.close() ;
			// DBUtils.getConn().close();
		}
		return result ;
	}

	private static int find_all_biz_by_email ;

	public static int getFind_all_biz_by_email() {
		return find_all_biz_by_email ;
	}

	public static void setFind_all_biz_by_email( int find_all_biz_by_email ) {
		CreateBusinessDAO.find_all_biz_by_email = find_all_biz_by_email ;
	}

	public static List < CreateBusiness > findAllBusinessesByEmail( String emaiL ) throws Exception {

		List < CreateBusiness > businesses = new ArrayList <>() ;
		String tableName = "business_type" ;
		String procName = "find_all_biz_by_email" ;
		String whereCond = "WHERE email=email" ;
		String inParams = "in email varchar(255)" ;
		String wildCardValues = "?" ;
		String businessId = "" ;
		String asteriskOrCols = "*" ;

		String procedureName = procName.concat( businessId ) ;

		if ( getListOfAllProceduresCreated() == null ) {
			listOfAllProceduresAlreadyCreated() ;
		}

		if ( !getListOfAllProceduresCreated().contains( procedureName ) ) {
			DBUtils.createSelectionProc( inParams , procName , "" , asteriskOrCols , tableName , whereCond , getFind_all_biz_by_email() ) ;
			setFind_all_biz_by_email( 1 ) ;
		} else {
			setFind_all_biz_by_email( 1 ) ;
		}

		// DBUtils.createSelectionProc( inParams , procName , "" , asteriskOrCols ,
		// tableName , whereCond ) ;

		ResultSet rs = null ;

		CallableStatement cs = null ;

		Connection connection = DBUtils.getConn() ;

		connection.setAutoCommit( false ) ;

		try {

			// private static final String FIND_BUSINESSES_TYPES_BY_EMAIL = "SELECT * FROM
			// business_type WHERE email=?";

			cs = connection.prepareCall( DBUtils.invokeSelection( procName , businessId , wildCardValues ) ) ;

			cs.setString( 1 , emaiL ) ;
			rs = cs.executeQuery() ;
			while ( rs.next() ) {
				BigDecimal id = rs.getBigDecimal( "id" ) ;
				String email = rs.getString( "email" ) ;
				String businessType = rs.getString( "business_type" ) ;
				String businessName = rs.getString( "business_name" ) ;
				CreateBusiness business = new CreateBusiness( id , email , businessType , businessName ) ;
				businesses.add( business ) ;
			}

			connection.commit() ;
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			rs.close() ;
			cs.close() ;
			connection.close() ;
			// DBUtils.getConn().close();
		}
		return businesses ;
	}

}
