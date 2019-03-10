
package com.keepcount.dao.business.entities ;

import java.math.BigDecimal ;
import java.sql.CallableStatement ;
import java.sql.Connection ;
import java.sql.ResultSet ;
import java.util.LinkedHashMap ;
import java.util.List ;
import java.util.Map ;

import com.keepcount.dao.dbutils.DBUtils ;
import com.keepcount.model.business.entities.EmailsOfABusinessWithAllAttributes ;

public class EmailsOfABusinessWithAllAttributesDAO {

	public static void main( String [ ] args ) throws Exception {

		String businessId = "" ;
		String email = "alimahmoudraage@gmail.com" ;

		// System.out.println(
		// findAllEmailsOfABusinessWithAllAttributesBasingOnEmailOfAMember( businessId ,
		// email ) ) ;

		System.out.println( findAllEmailsOfABusinessWithAllAttributes() ) ;

	}

	private static List < String > listOfAllProceduresCreated ;

	public static List < String > getListOfAllProceduresCreated() {
		return listOfAllProceduresCreated ;
	}

	public static void setListOfAllProceduresCreated( List < String > listOfAllProceduresCreated ) {
		EmailsOfABusinessWithAllAttributesDAO.listOfAllProceduresCreated = listOfAllProceduresCreated ;
	}

	private static List < String > listOfAllProceduresAlreadyCreated() throws Exception {
		setListOfAllProceduresCreated( DBUtils.listOfAllProceduresAlreadyCreated() ) ;
		return getListOfAllProceduresCreated() ;
	}

	private static int get_all_emails_of_a_biz ;

	public static int getGet_all_emails_of_a_biz() {
		return get_all_emails_of_a_biz ;
	}

	public static void setGet_all_emails_of_a_biz( int get_all_emails_of_a_biz ) {
		EmailsOfABusinessWithAllAttributesDAO.get_all_emails_of_a_biz = get_all_emails_of_a_biz ;
	}

	public static Map < BigDecimal , EmailsOfABusinessWithAllAttributes > findAllEmailsOfABusinessWithAllAttributes() throws Exception {

		String procName = "get_all_emails_of_a_biz" ;
		String tableName = "emails_of_a_business" ;
		String whereCond = "" ;
		String inParams = "" ;
		String wildCardValues = "" ;
		String asteriskOrCols = "*" ;
		String businessId = "" ;

		String procedureName = procName.concat( businessId ) ;

		if ( getListOfAllProceduresCreated() == null ) {
			listOfAllProceduresAlreadyCreated() ;
		}

		if ( !getListOfAllProceduresCreated().contains( procedureName ) ) {
			DBUtils.createSelectionProc( inParams , procName , businessId , asteriskOrCols , tableName , whereCond , getGet_all_emails_of_a_biz() ) ;
			setGet_all_emails_of_a_biz( 1 ) ;
		} else {
			setGet_all_emails_of_a_biz( 1 ) ;
		}

		// DBUtils.createSelectionProc( inParams , procName , businessId ,
		// asteriskOrCols , tableName , whereCond ) ;

		Map < BigDecimal , EmailsOfABusinessWithAllAttributes > list = new LinkedHashMap <>() ;
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
				BigDecimal idOfBusiness = rs.getBigDecimal( "id_biz_type" ) ;
				String phoneNumber = rs.getString( "phone_number" ) ;

				EmailsOfABusinessWithAllAttributes aBusinessWithAllAttributes = new EmailsOfABusinessWithAllAttributes( id , email , password , businessName , userName ,
						idOfBusiness , phoneNumber ) ;
				list.put( idOfBusiness , aBusinessWithAllAttributes ) ;
			}

			for ( Map.Entry < BigDecimal , EmailsOfABusinessWithAllAttributes > it : list.entrySet() ) {
				String bizName = it.getValue().getBusinessNameAMemberBelongsTo() ;
				System.out.println( "biz: " + bizName ) ;
				BigDecimal bizId = it.getValue().getIdOfThisBusiness() ;
				System.out.println( "id of biz: " + bizId ) ;

				System.out.println() ;
			}

		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return list ;
	}

	private static int get_all_emails_of_a_biz_base_on_email ;

	public static int getGet_all_emails_of_a_biz_base_on_email() {
		return get_all_emails_of_a_biz_base_on_email ;
	}

	public static void setGet_all_emails_of_a_biz_base_on_email( int get_all_emails_of_a_biz_base_on_email ) {
		EmailsOfABusinessWithAllAttributesDAO.get_all_emails_of_a_biz_base_on_email = get_all_emails_of_a_biz_base_on_email ;
	}

	public static Map < BigDecimal , EmailsOfABusinessWithAllAttributes > findAllEmailsOfABusinessWithAllAttributesBasingOnEmailOfAMember( String businessId , String emailOfUser )
			throws Exception {

		String procName = "get_all_emails_of_a_biz_base_on_email" ;
		String tableName = "emails_of_a_business" ;
		String whereCond = "WHERE email=in_email" ;
		String inParams = "in_email varchar(255)" ;
		String wildCardValues = "?" ;
		String asteriskOrCols = "*" ;

		String procedureName = procName.concat( businessId ) ;

		if ( getListOfAllProceduresCreated() == null ) {
			listOfAllProceduresAlreadyCreated() ;
		}

		if ( !getListOfAllProceduresCreated().contains( procedureName ) ) {
			DBUtils.createSelectionProc( inParams , procName , businessId , asteriskOrCols , tableName , whereCond , getGet_all_emails_of_a_biz_base_on_email() ) ;
			setGet_all_emails_of_a_biz_base_on_email( 1 ) ;
		} else {
			setGet_all_emails_of_a_biz_base_on_email( 1 ) ;
		}

		// DBUtils.createSelectionProc( inParams , procName , businessId ,
		// asteriskOrCols , tableName , whereCond ) ;

		Map < BigDecimal , EmailsOfABusinessWithAllAttributes > list = new LinkedHashMap <>() ;
		CallableStatement cs = null ;
		ResultSet rs = null ;
		Connection connection = DBUtils.getConn() ;
		try {

			cs = connection.prepareCall( DBUtils.invokeSelection( procName , businessId , wildCardValues ) ) ;
			cs.setString( 1 , emailOfUser ) ;
			rs = cs.executeQuery() ;
			while ( rs.next() ) {
				BigDecimal id = rs.getBigDecimal( "id" ) ;
				String email = rs.getString( "email" ) ;
				String password = rs.getString( "password" ) ;
				String businessName = rs.getString( "business_name" ) ;
				String userName = rs.getString( "user_name" ) ;
				BigDecimal idOfBusiness = rs.getBigDecimal( "id_biz_type" ) ;
				String phoneNumber = rs.getString( "phone_number" ) ;

				EmailsOfABusinessWithAllAttributes aBusinessWithAllAttributes = new EmailsOfABusinessWithAllAttributes( id , email , password , businessName , userName ,
						idOfBusiness , phoneNumber ) ;
				list.put( idOfBusiness , aBusinessWithAllAttributes ) ;
			}

			for ( Map.Entry < BigDecimal , EmailsOfABusinessWithAllAttributes > it : list.entrySet() ) {
				String bizName = it.getValue().getBusinessNameAMemberBelongsTo() ;
				System.out.println( "biz: " + bizName ) ;
				BigDecimal bizId = it.getValue().getIdOfThisBusiness() ;
				System.out.println( "id of biz: " + bizId ) ;

				System.out.println() ;
			}

		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return list ;
	}

	private static int get_all_emails_of_a_biz_base_on_phone ;

	public static int getGet_all_emails_of_a_biz_base_on_phone() {
		return get_all_emails_of_a_biz_base_on_phone ;
	}

	public static void setGet_all_emails_of_a_biz_base_on_phone( int get_all_emails_of_a_biz_base_on_phone ) {
		EmailsOfABusinessWithAllAttributesDAO.get_all_emails_of_a_biz_base_on_phone = get_all_emails_of_a_biz_base_on_phone ;
	}

	public static Map < BigDecimal , EmailsOfABusinessWithAllAttributes > findAllEmailsOfABusinessWithAllAttributesBasingOnPhoneNumberOfAMember( String businessId ,
			String phoneOfUser ) throws Exception {

		String procName = "get_all_emails_of_a_biz_base_on_phone" ;
		String tableName = "emails_of_a_business" ;
		String whereCond = "WHERE phone_number=in_phone_number" ;
		String inParams = "in_phone_number varchar(255)" ;
		String wildCardValues = "?" ;
		String asteriskOrCols = "*" ;

		String procedureName = procName.concat( businessId ) ;

		if ( getListOfAllProceduresCreated() == null ) {
			listOfAllProceduresAlreadyCreated() ;
		}

		if ( !getListOfAllProceduresCreated().contains( procedureName ) ) {
			DBUtils.createSelectionProc( inParams , procName , businessId , asteriskOrCols , tableName , whereCond , getGet_all_emails_of_a_biz_base_on_phone() ) ;
			setGet_all_emails_of_a_biz_base_on_phone( 1 ) ;
		} else {
			setGet_all_emails_of_a_biz_base_on_phone( 1 ) ;
		}

		// DBUtils.createSelectionProc( inParams , procName , businessId ,
		// asteriskOrCols , tableName , whereCond ) ;

		Map < BigDecimal , EmailsOfABusinessWithAllAttributes > list = new LinkedHashMap <>() ;
		CallableStatement cs = null ;
		ResultSet rs = null ;
		Connection connection = DBUtils.getConn() ;
		try {

			cs = connection.prepareCall( DBUtils.invokeSelection( procName , businessId , wildCardValues ) ) ;
			cs.setString( 1 , phoneOfUser ) ;
			rs = cs.executeQuery() ;
			while ( rs.next() ) {
				BigDecimal id = rs.getBigDecimal( "id" ) ;
				String email = rs.getString( "email" ) ;
				String password = rs.getString( "password" ) ;
				String businessName = rs.getString( "business_name" ) ;
				String userName = rs.getString( "user_name" ) ;
				BigDecimal idOfBusiness = rs.getBigDecimal( "id_biz_type" ) ;
				String phoneNumber = rs.getString( "phone_number" ) ;

				EmailsOfABusinessWithAllAttributes aBusinessWithAllAttributes = new EmailsOfABusinessWithAllAttributes( id , email , password , businessName , userName ,
						idOfBusiness , phoneNumber ) ;
				list.put( idOfBusiness , aBusinessWithAllAttributes ) ;
			}

			for ( Map.Entry < BigDecimal , EmailsOfABusinessWithAllAttributes > it : list.entrySet() ) {
				String bizName = it.getValue().getBusinessNameAMemberBelongsTo() ;
				System.out.println( "biz: " + bizName ) ;
			}

		} catch ( Exception e ) {
			e.printStackTrace() ;
		}
		return list ;
	}

}
