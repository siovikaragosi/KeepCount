
package com.keepcount.dao.purchases ;

import java.sql.CallableStatement ;
import java.sql.Connection ;
import java.sql.PreparedStatement ;
import java.sql.Timestamp ;
import java.time.LocalDateTime ;

import com.keepcount.dao.dbutils.DBUtils ;
import com.keepcount.model.purchases.Purchases ;

public class PurchasesDAOInsertion {

	private static String nameOfTheTablePurchasesWithinDAO( String businessId ) {
		return "`purchases_".concat( businessId ).concat( "`" ) ;
	}

	private static String nameOfTheInsertionProcedureCreated( String businessId ) {
		return "insert_into_".concat( nameOfTheTablePurchasesWithinDAO( businessId ).replaceAll( "`" , "" ) ) ;
	}

	private static String name_InsertIntoPurchases( String businessId ) {
		String insertionName = nameOfTheInsertionProcedureCreated( businessId ) ;
		String StoredProcedure = "create procedure ".concat( insertionName )
				// opening brace for insertion IN parameters
				.concat( "(" )
				// 1
				.concat( "in date_client varchar(255)" )
				// 2
				.concat( ",in item varchar(255)" )
				// 3
				.concat( ",in item_sub_category varchar(255)" )
				// 4
				.concat( ",in item_quantity decimal(50,5)" )
				// 5
				.concat( ",in item_price_per_unit decimal(50,5)" )
				// 6
				.concat( ",in item_total_manual_cost decimal(50,5)" )
				// 7
				.concat( ",in item_total_automatic_cost decimal(50,5)" )
				// 8
				.concat( ",in credit tinyint" )
				// 9
				.concat( ",in cleared_by varchar(255)" )
				// 10
				.concat( ",amount_paid decimal(50,5)" )
				// 11
				.concat( ",in installment_paid decimal(50,5)" )
				// 12
				.concat( ",in installment_balance decimal(50,5)" )
				// 13
				.concat( ",in payment_method longtext" )
				// 14
				.concat( ",in supplier_name varchar(255)" )

				// closing brace for insertion IN parameters

				.concat( ")" )
				// End of parameters

				.concat( " BEGIN " )

				.concat( "INSERT INTO ".concat( nameOfTheTablePurchasesWithinDAO( businessId ) ) )

				.concat( " ( " )

				// 1
				.concat( "date_client" )
				// 2
				.concat( ",item" )
				// 3
				.concat( ",item_sub_category" )
				// 4
				.concat( ",item_quantity" )
				// 5
				.concat( ",item_price_per_unit" )
				// 6
				.concat( ",item_total_manual_cost" )
				// 7
				.concat( ",item_total_automatic_cost" )
				// 8
				.concat( ",credit" )
				// 9
				.concat( ",cleared_by" )
				// 10
				.concat( ",amount_paid" )
				// 11
				.concat( ",installment_paid" )
				// 12
				.concat( ",installment_balance" )
				// 13
				.concat( ",payment_method" )
				// 14
				.concat( ",supplier_name" )

				.concat( " ) " )

				// VALUES + OPENING BRACE
				.concat( "VALUES(" )

				// 1
				.concat( "date_client" )
				// 2
				.concat( ",item" )
				// 3
				.concat( ",item_sub_category" )
				// 4
				.concat( ",item_quantity" )
				// 5
				.concat( ",item_price_per_unit" )
				// 6
				.concat( ",item_total_manual_cost" )
				// 7
				.concat( ",item_total_automatic_cost" )
				// 8
				.concat( ",credit" )
				// 9
				.concat( ",cleared_by" )
				// 10
				.concat( ",amount_paid" )
				// 11
				.concat( ",installment_paid" )
				// 12
				.concat( ",installment_balance" )
				// 13
				.concat( ",payment_method" )
				// 14
				.concat( ",supplier_name" )

				// THE CLOSING BRACE OF VALUES
				.concat( ")" )

				.concat( ";" )

				.concat( " END" ) ;
		return StoredProcedure ;
	}

	private static void createProcedureToInsertIntoPurchases( String businessId ) throws Exception {
		PreparedStatement ps = null ;
		String dbName = businessId ;
		Connection connection = DBUtils.getConn() ;
		try {
			ps = connection.prepareStatement( name_InsertIntoPurchases( businessId ) ) ;
			if ( dbName != null ) {
				ps.executeUpdate() ;
				System.out.println( "insertion proc created" ) ;
			} else {
				System.out.println( "database null, insertion proc not created" ) ;
			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( null , null , ps , connection ) ;
		}
	}

	public static int newPurchase( String businessId , Purchases purchases ) throws Exception {

		createProcedureToInsertIntoPurchases( businessId ) ;

		Connection connection = DBUtils.getConn() ;
		CallableStatement cs = null ;
		int result = 0 ;

		try {

			cs = connection.prepareCall( "{CALL ".concat( nameOfTheInsertionProcedureCreated( businessId ) )

					.concat( "(" )
					// FIRST THREE (1-3)
					.concat( "?,?,?," )
					// SECOND THREE (4-6)
					.concat( "?,?,?," )
					// THIRD THREE (7-9)
					.concat( "?,?,?," )
					// FOURTH THREE (10-14)
					.concat( "?,?,?,?,?" ).concat( ")" )

					.concat( "}" ) ) ;

			Timestamp timestamp = Timestamp.valueOf( LocalDateTime.now() ) ;
			purchases.setDateClient( timestamp.toString() ) ;

			// 1
			cs.setString( 1 , purchases.getDateClient() ) ;
			// 2
			cs.setString( 2 , purchases.getItem() ) ;
			// 3
			cs.setString( 3 , purchases.getItemSubCategory() ) ;
			// 4
			cs.setBigDecimal( 4 , purchases.getItemQuantity() ) ;
			// 5
			cs.setBigDecimal( 5 , purchases.getItemPricePerUnit() ) ;
			// 6
			cs.setBigDecimal( 6 , purchases.getItemTotalManualCost() ) ;
			// 7
			cs.setBigDecimal( 7 , purchases.getItemTotalAutomaticCost() ) ;
			// 8
			cs.setBoolean( 8 , purchases.isCredit() ) ;
			// 9
			cs.setString( 9 , purchases.getClearedBy() ) ;
			// 10
			cs.setBigDecimal( 10 , purchases.getAmountPaid() ) ;
			// 11
			cs.setBigDecimal( 11 , purchases.getInstallmentPaid() ) ;
			// 12
			cs.setBigDecimal( 12 , purchases.getInstallmentBalance() ) ;
			// 13
			cs.setString( 13 , purchases.getPaymentMethod() ) ;
			// 14
			cs.setString( 14 , purchases.getSupplierName() ) ;

			System.out.println( purchases ) ;

			result = cs.executeUpdate() ;

		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( null , cs , null , connection ) ;
		}
		return result ;
	}

}
