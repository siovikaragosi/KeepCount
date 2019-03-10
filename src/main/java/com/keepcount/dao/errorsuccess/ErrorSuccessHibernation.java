package com.keepcount.dao.errorsuccess;

import java.util.List;

import com.keepcount.model.errorsuccess.ErrorSuccess;

public class ErrorSuccessHibernation {

	public static List<ErrorSuccess> getSuccessMessage(String message) {
		return ErrorSuccessDAO.getAllErrorSuccessMessages(message);
	}

}
