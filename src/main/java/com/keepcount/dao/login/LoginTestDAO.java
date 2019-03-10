
package com.keepcount.dao.login ;

import java.math.BigDecimal ;
import java.sql.Connection ;
import java.sql.PreparedStatement ;
import java.sql.ResultSet ;

import com.keepcount.dao.dbutils.DBUtils ;
import com.keepcount.model.signup.SignUp ;

public class LoginTestDAO {

	public static SignUp getEmailSignedUpMatch( String sqlQuery , String emailMatch ) throws Exception {

		SignUp signUps = null ;
		PreparedStatement pst = null ;
		ResultSet rs = null ;
		Connection connection = DBUtils.getConn() ;
		try {
			// "SELECT * FROM emails_kc WHERE email=?"
			pst = connection.prepareStatement( sqlQuery ) ;
			pst.setString( 1 , emailMatch ) ;
			rs = pst.executeQuery() ;
			while ( rs.next() ) {
				// BigDecimal id = rs.getBigDecimal("id");
				String email = rs.getString( "email" ) ;
				String password = rs.getString( "password" ) ;
				SignUp signUp = null ;

				// new SignUp(email, password);
				signUps = signUp ;
			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( rs , null , pst , connection ) ;
		}
		return signUps ;
	}
}
