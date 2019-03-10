
package com.keepcount.dao.purchases ;

import java.math.BigDecimal ;
import java.sql.CallableStatement ;
import java.sql.Connection ;
import java.sql.PreparedStatement ;

import com.keepcount.dao.dbutils.DBUtils ;
import com.keepcount.model.purchases.Purchases ;

public class PurchasesDAOUpdate {

	private static String name_UpdatePurchases( String businessId ) {
		String StoredProcedure = "create procedure update_purchases_".concat( businessId )
				// opening brace for insertion IN parameters
				.concat( "(" )
				// 1
				.concat( "in item_in varchar(255)" )
				// 2
				.concat( ",in item_sub_category_in varchar(255)" )
				// 3
				.concat( ",in item_quantity_in decimal(50,5)" )
				// 4
				.concat( ",in item_price_per_unit_in decimal(50,5)" )
				// 5
				.concat( ",in item_total_manual_cost_in decimal(50,5)" )
				// 6
				.concat( ",in item_total_automatic_cost_in decimal(50,5)" )
				// 7
				.concat( ",in credit_in tinyint" )
				// 8
				.concat( ",in cleared_by_in varchar(255)" )
				// 9
				.concat( ",amount_paid_in decimal(50,5)" )
				// 10
				.concat( ",in installment_paid_in decimal(50,5)" )
				// 11
				.concat( ",in installment_balance_in decimal(50,5)" )
				// 12
				.concat( ",in payment_method_in longtext" )
				// 13
				.concat( ",in supplier_name_in varchar(255)" )
				// 14
				.concat( ",in id_in bigint" )

				// closing brace for insertion IN parameters

				.concat( ")" )
				// End of parameters

				.concat( " BEGIN " )

				.concat( "update purchases_".concat( businessId ) )

				.concat( " set " )

				// 1
				.concat( "item = item_in" )
				// 2
				.concat( ",item_sub_category = item_sub_category_in" )
				// 3
				.concat( ",item_quantity = item_quantity_in" )
				// 4
				.concat( ",item_price_per_unit = item_price_per_unit_in" )
				// 5
				.concat( ",item_total_manual_cost = item_total_manual_cost_in" )
				// 6
				.concat( ",item_total_automatic_cost = item_total_automatic_cost_in" )
				// 7
				.concat( ",credit = credit_in" )
				// 8
				.concat( ",cleared_by = cleared_by_in" )
				// 9
				.concat( ",amount_paid = amount_paid_in" )
				// 10
				.concat( ",installment_paid = installment_paid_in" )
				// 11
				.concat( ",installment_balance = installment_balance_in" )
				// 12
				.concat( ",payment_method = payment_method_in" )
				// 13
				.concat( ",supplier_name = supplier_name_in" )
				// 14
				.concat( " where id = id_in" )

				.concat( ";" )

				.concat( " END" ) ;
		return StoredProcedure ;
	}

	private static void createProcedureToInsertIntoPurchases( String businessId ) throws Exception {
		PreparedStatement ps = null ;
		String dbName = businessId ;
		Connection connection = DBUtils.getConn() ;
		try {
			ps = connection.prepareStatement( name_UpdatePurchases( businessId ) ) ;
			if ( dbName != null ) {
				ps.executeUpdate() ;
				System.out.println( "update proc created" ) ;
			} else {
				System.out.println( "database null, update proc not created" ) ;
			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			DBUtils.closeConnections( null , null , ps , connection ) ;
		}
	}

	public static int updatePurchase( String businessId , Purchases purchases , BigDecimal id ) throws Exception {

		createProcedureToInsertIntoPurchases( businessId ) ;

		CallableStatement cs = null ;
		int result = 0 ;
		Connection connection = DBUtils.getConn() ;
		connection.setAutoCommit( false ) ;
		try {
			cs = connection.prepareCall( "{CALL ".concat( "update_purchases_" ).concat( businessId ).concat( "(" )
					// FIRST THREE (1-3)
					.concat( "?,?,?," )
					// SECOND THREE (4-6)
					.concat( "?,?,?," )
					// THIRD THREE (7-9)
					.concat( "?,?,?," )
					// FOURTH THREE (10-14)
					.concat( "?,?,?,?,?" ).concat( ")" )

					.concat( "}" ) ) ;

			// Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
			// purchases.setDateClient(timestamp.toString());

			// 1
			cs.setString( 1 , purchases.getItem() ) ;
			// 2
			cs.setString( 2 , purchases.getItemSubCategory() ) ;
			// 3
			cs.setBigDecimal( 3 , purchases.getItemQuantity() ) ;
			// 4
			cs.setBigDecimal( 4 , purchases.getItemPricePerUnit() ) ;
			// 5
			cs.setBigDecimal( 5 , purchases.getItemTotalManualCost() ) ;
			// 6
			cs.setBigDecimal( 6 , purchases.getItemTotalAutomaticCost() ) ;
			// 7
			cs.setBoolean( 7 , purchases.isCredit() ) ;
			// 8
			cs.setString( 8 , purchases.getClearedBy() ) ;
			// 9
			cs.setBigDecimal( 9 , purchases.getAmountPaid() ) ;
			// 10
			cs.setBigDecimal( 10 , purchases.getInstallmentPaid() ) ;
			// 11
			cs.setBigDecimal( 11 , purchases.getInstallmentBalance() ) ;
			// 12
			cs.setString( 12 , purchases.getPaymentMethod() ) ;
			// 13
			cs.setString( 13 , purchases.getSupplierName() ) ;
			// 14
			cs.setBigDecimal( 14 , id ) ;

			System.out.println( purchases ) ;

			result = cs.executeUpdate() ;

			if ( result != 1 ) {
				connection.rollback() ;
			} else {
				connection.commit() ;
			}

		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			connection.close() ;
			DBUtils.closeConnections( null , cs , null , connection ) ;
		}
		return result ;
	}

}
