package com.keepcount.dao.purchases;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.keepcount.dao.dbutils.DBUtils;
import com.keepcount.model.purchases.Purchases;

public interface PurchasesInsertionDAOHelperInterface {

	public static String nameOfTheTablePurchasesWithinDAO(String users_businessName_email) {
		return "`purchases_".concat(users_businessName_email).concat("`");
	}

	public static String nameOfTheInsertionProcedureCreated(String users_businessName_email) {
		return "insert_into_".concat(nameOfTheTablePurchasesWithinDAO(users_businessName_email));
	}

	public static String name_InsertIntoPurchases(String user_businessName_email) {
		return "CREATE PROCEDURE ".concat(nameOfTheInsertionProcedureCreated(user_businessName_email))
				// opening brace for insertion IN parameters
				.concat("(")
				// 1
				.concat("in date_client varchar(255)")
				// 2
				.concat(",in item varchar(255)")
				// 3
				.concat(",in item_sub_category varchar(255)")
				// 4
				.concat(",in item_quantity decimal(50,10)")
				// 5
				.concat(",in item_price_per_unit decimal(50,10)")
				// 6
				.concat(",in item_total_manual_cost decimal(50,10)")
				// 7
				.concat(",in item_total_automatic_cost decimal(50,10)")
				// 8
				.concat(",in credit tinyint")
				// 9
				.concat(",in cleared_by varchar(255)")
				// 10
				.concat(",in installment_paid decimal(50,10)")
				// 11
				.concat(",in installment_balance decimal(50,10)")
				// 12
				.concat(",in payment_method longtext")
				// 13
				.concat(",in supplier_name varchar(255)")

				// closing brace for insertion IN parameters

				.concat(")")
				// End of parameters

				.concat("BEGIN ")

				.concat("INSERT INTO ".concat(nameOfTheTablePurchasesWithinDAO(user_businessName_email)))

				// 1
				.concat("date_client")
				// 2
				.concat(",item")
				// 3
				.concat(",item_sub_category")
				// 4
				.concat(",item_quantity")
				// 5
				.concat(",item_price_per_unit")
				// 6
				.concat(",item_total_manual_cost")
				// 7
				.concat(",item_total_automatic_cost")
				// 8
				.concat(",credit")
				// 9
				.concat(",cleared_by")
				// 10
				.concat(",installment_paid")
				// 11
				.concat(",installment_balance")
				// 12
				.concat("payment_method")
				// 13
				.concat(",supplier_name")

				.concat(" ) ")

				// VALUES + OPENING BRACE
				.concat("VALUES(")

				// FIRST THREE (1-3)
				.concat("?,?,?,")
				// SECOND THREE (4-6)
				.concat("?,?,?,")
				// THIRD THREE (7-9)
				.concat("?,?,?,")
				// FOURTH FOUR (10-13)
				.concat("?,?,?,?")

				// THE CLOSING BRACE OF VALUES
				.concat(")")

				.concat(";")

				.concat(" END");

	}

	public static void createProcedureToInsertIntoPurchases(String user_businessName_email) throws Exception {
		PreparedStatement ps = null;
		try {
			ps = DBUtils.getConn().prepareStatement(name_InsertIntoPurchases(user_businessName_email));
			if (user_businessName_email != null) {
				ps.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}

	public static int newPurchase(String users_businessName_email, Purchases purchases) throws Exception {
		CallableStatement cs = null;
		int result = 0;

		try {

			cs = DBUtils.getConn()
					.prepareCall("{CALL ".concat(nameOfTheInsertionProcedureCreated(users_businessName_email))

							.concat("(")
							// FIRST THREE (1-3)
							.concat("?,?,?,")
							// SECOND THREE (4-6)
							.concat("?,?,?,")
							// THIRD THREE (7-9)
							.concat("?,?,?,")
							// FOURTH FOUR (10-13)
							.concat("?,?,?,?")

							.concat(")")

							.concat("}"));

			Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
			purchases.setDateClient(timestamp.toString());

			// 1
			cs.setString(1, purchases.getDateClient());
			// 2
			cs.setString(2, purchases.getItem());
			// 3
			cs.setString(3, purchases.getItemSubCategory());
			// 4
			cs.setBigDecimal(4, purchases.getItemQuantity());
			// 5
			cs.setBigDecimal(5, purchases.getItemPricePerUnit());
			// 6
			cs.setBigDecimal(6, purchases.getItemTotalManualCost());
			// 7
			cs.setBigDecimal(7, purchases.getItemTotalAutomaticCost());
			// 8
			cs.setBoolean(8, purchases.isCredit());
			// 9
			cs.setString(9, purchases.getClearedBy());
			// 10
			cs.setBigDecimal(10, purchases.getInstallmentPaid());
			// 11
			cs.setBigDecimal(11, purchases.getInstallmentBalance());
			// 12
			cs.setString(12, purchases.getPaymentMethod());
			// 13
			cs.setString(12, purchases.getSupplierName());

			result = cs.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

}