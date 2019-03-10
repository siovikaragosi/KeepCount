
package com.keepcount.dao.pos ;

import java.math.BigDecimal ;
import java.sql.CallableStatement ;
import java.sql.Connection ;
import java.sql.ResultSet ;
import java.util.ArrayList ;
import java.util.List ;

import com.keepcount.dao.dbutils.DBUtils ;
import com.keepcount.model.pos.EmailsPhoneNumbersAndNamesOfBusinesses ;

public class EmailsPhoneNumbersAndNamesOfBusinessesDAO {

	private static List < String > listOfAllProceduresCreated ;

	public static List < String > getListOfAllProceduresCreated() {
		return listOfAllProceduresCreated ;
	}

	public static void setListOfAllProceduresCreated( List < String > listOfAllProceduresCreated ) {
		EmailsPhoneNumbersAndNamesOfBusinessesDAO.listOfAllProceduresCreated = listOfAllProceduresCreated ;
	}

	private static List < String > listOfAllProceduresAlreadyCreated() throws Exception {
		setListOfAllProceduresCreated( DBUtils.listOfAllProceduresAlreadyCreated() ) ;
		return getListOfAllProceduresCreated() ;
	}

	private static String procName = "get_all_emails_of_a_biz" ;
	private static String tableName = "emails_of_a_business" ;
	private static String whereCond = "" ;
	private static String inParams = "" ;
	private static String wildCardValues = "" ;
	private static String asteriskOrCols = "*" ;

	private static int get_all_emails_of_a_biz_Existence ;

	public static int getGet_all_emails_of_a_biz_Existence() {
		return get_all_emails_of_a_biz_Existence ;
	}

	public static void setGet_all_emails_of_a_biz_Existence( int get_all_emails_of_a_biz_Existence ) {
		EmailsPhoneNumbersAndNamesOfBusinessesDAO.get_all_emails_of_a_biz_Existence = get_all_emails_of_a_biz_Existence ;
	}

	/*
	 * There is a debate of whether to use a Map or List. A Map was first used but later on a List has been preferred since a Map will only allow a single KEY to hold only
	 * and only a single value. This limits the number of businesses that have to appear in case the user has got a couple of businesses in the system
	 */

	// public static Map < BigDecimal , EmailsPhoneNumbersAndNamesOfBusinesses > findAllEmailsPhoneNumbersAndNamesOfBusinessesToBeUsedAnyWhereNecessary() throws Exception {
	// // String procName, String businessId, String tableName, String whereCondition
	//
	// String businessId = "" ;
	//
	// String procedureName = procName.concat( businessId ) ;
	//
	// if ( getListOfAllProceduresCreated() == null ) {
	// listOfAllProceduresAlreadyCreated() ;
	// }
	//
	// if ( !getListOfAllProceduresCreated().contains( procedureName ) ) {
	// DBUtils.createSelectionProc( inParams , procName , businessId , asteriskOrCols , tableName , whereCond , getGet_all_emails_of_a_biz_Existence() ) ;
	// setGet_all_emails_of_a_biz_Existence( 1 ) ;
	// } else {
	// setGet_all_emails_of_a_biz_Existence( 1 ) ;
	// }
	//
	// Map < BigDecimal , EmailsPhoneNumbersAndNamesOfBusinesses > emailsPhoneNumbersAndNamesOfBusinessesMap = new LinkedHashMap <>() ;
	// CallableStatement cs = null ;
	// ResultSet rs = null ;
	// Connection connection = DBUtils.getConn() ;
	// try {
	// cs = connection.prepareCall( DBUtils.invokeSelection( procName , businessId , wildCardValues ) ) ;
	// rs = cs.executeQuery() ;
	// while ( rs.next() ) {
	// BigDecimal id = rs.getBigDecimal( "id" ) ;
	// String email = rs.getString( "email" ) ;
	// String password = rs.getString( "password" ) ;
	// String businessName = rs.getString( "business_name" ) ;
	// String userName = rs.getString( "user_name" ) ;
	// BigDecimal idOfTheBusiness = rs.getBigDecimal( "id_biz_type" ) ;
	// String phoneNumber = rs.getString( "phone_number" ) ;
	// EmailsPhoneNumbersAndNamesOfBusinesses emailsPhoneNumbersAndNamesOfBusinesses = new EmailsPhoneNumbersAndNamesOfBusinesses( id , email , password , businessName ,
	// userName , idOfTheBusiness , phoneNumber ) ;
	// emailsPhoneNumbersAndNamesOfBusinessesMap.put( idOfTheBusiness , emailsPhoneNumbersAndNamesOfBusinesses ) ;
	// }
	// } catch ( Exception e ) {
	// e.printStackTrace() ;
	// } finally {
	// DBUtils.closeConnections( rs , cs , null , connection ) ;
	// }
	// return emailsPhoneNumbersAndNamesOfBusinessesMap ;
	// }

	public static List < EmailsPhoneNumbersAndNamesOfBusinesses > findAllEmailsPhoneNumbersAndNamesOfBusinessesInListFormatToBeUsedAnyWhereNecessary() throws Exception {
		// String procName, String businessId, String tableName, String whereCondition

		String businessId = "" ;

		String procedureName = procName.concat( businessId ) ;

		if ( getListOfAllProceduresCreated() == null ) {
			listOfAllProceduresAlreadyCreated() ;
		}

		if ( !getListOfAllProceduresCreated().contains( procedureName ) ) {
			DBUtils.createSelectionProc( inParams , procName , businessId , asteriskOrCols , tableName , whereCond , getGet_all_emails_of_a_biz_Existence() ) ;
			setGet_all_emails_of_a_biz_Existence( 1 ) ;
		} else {
			setGet_all_emails_of_a_biz_Existence( 1 ) ;
		}

		List < EmailsPhoneNumbersAndNamesOfBusinesses > emailsPhoneNumbersAndNamesOfBusinesses = new ArrayList <>() ;

		CallableStatement cs = null ;
		ResultSet rs = null ;
		Connection connection = DBUtils.getConn() ;
		try {
			cs = connection.prepareCall( DBUtils.invokeSelection( procName , businessId , wildCardValues ) ) ;
			rs = cs.executeQuery() ;
			while ( rs.next() ) {
				BigDecimal id = rs.getBigDecimal( "id" ) ;
				String email = rs.getString( "email" ) ;
				String password = rs.getString( "password" ) ;
				String businessName = rs.getString( "business_name" ) ;
				String userName = rs.getString( "user_name" ) ;
				BigDecimal idOfTheBusiness = rs.getBigDecimal( "id_biz_type" ) ;
				String phoneNumber = rs.getString( "phone_number" ) ;
				EmailsPhoneNumbersAndNamesOfBusinesses emailsPhoneNumbersAndNamesOfBusinesses1 = new EmailsPhoneNumbersAndNamesOfBusinesses( id , email , password , businessName ,
						userName , idOfTheBusiness , phoneNumber ) ;
				emailsPhoneNumbersAndNamesOfBusinesses.add( emailsPhoneNumbersAndNamesOfBusinesses1 ) ;
			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( rs , cs , null , connection ) ;
		}
		return emailsPhoneNumbersAndNamesOfBusinesses ;
	}

	public static void main( String [ ] args ) throws Exception {
		// Map < BigDecimal , EmailsPhoneNumbersAndNamesOfBusinesses > map = findAllEmailsPhoneNumbersAndNamesOfBusinessesToBeUsedAnyWhereNecessary() ;
		List < EmailsPhoneNumbersAndNamesOfBusinesses > list = findAllEmailsPhoneNumbersAndNamesOfBusinessesInListFormatToBeUsedAnyWhereNecessary() ;
		System.out.println( "list: " + list ) ;
	}

}
