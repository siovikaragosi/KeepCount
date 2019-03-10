
package com.keepcount.controller.login ;

import java.math.BigDecimal ;
import java.sql.Connection ;
import java.sql.PreparedStatement ;
import java.sql.ResultSet ;
import java.util.ArrayList ;
import java.util.List ;

import com.keepcount.dao.dbutils.DBUtils ;
import com.keepcount.model.login.EmailsOfABusiness ;

public class EmailsOfABusinessLoginDAO {

	private static final String FIND_ALL_BUSINESS_OF_THIS_EMAIL = "SELECT * FROM emails_of_a_business WHERE email=`?`" ;

	public static List < EmailsOfABusiness > findAllBusinessOfThisEmail( String email ) throws Exception {
		List < EmailsOfABusiness > list = new ArrayList <>() ;
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
				EmailsOfABusiness business = new EmailsOfABusiness( id , emaiL , password , businessName ) ;
				list.add( business ) ;
			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( rs , null , st , connection ) ;
		}
		return list ;
	}

}
