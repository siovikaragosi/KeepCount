
package com.keepcount.controller.pos;

import com.keepcount.dao.dbutils.DBUtils;

public class PosCashDAO {

	private static int procExisst = 0;

	public static int getProcExisst() {

		return procExisst;
	}

	public static void setProcExisst( int procExisst ) {

		PosCashDAO.procExisst = procExisst;
	}

	private static String tableName = "poscash_";

	private static String createTableStr = "(id bigint primary key auto_increment".concat(",date_server datetime not null default current_timestamp").concat(",date_client varchar(255) not null")
											.concat(",item_id bigint not null").concat(",item_quantity decimal(50,5)").concat(",item_unit_cost decimal(50,5)").concat(",item_sub_total decimal(50,5)")
											.concat(",total_sales decimal(50,5)").concat(",amount_paid_in decimal(50,5)").concat(",change decimal(50,5)").concat(",customer_email varchar(255)")
											.concat(",customer_phone varchar(255)").concat(",cust_business_name longtext").concat(",transaction_id bigint");

	public static int checkPosCashTableExistence( String businessId ) {

		int result = 0;
		try {
			result = DBUtils.checkTableExistence(tableName, createTableStr, businessId);
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
		return result;
	}

}
