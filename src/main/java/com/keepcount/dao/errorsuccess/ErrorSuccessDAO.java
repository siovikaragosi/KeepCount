package com.keepcount.dao.errorsuccess;

import java.util.ArrayList;
import java.util.List;

import com.keepcount.model.errorsuccess.ErrorSuccess;

public class ErrorSuccessDAO {

	public static List<ErrorSuccess> getAllErrorSuccessMessages(String message) {
		List<String> numberFormats = new ArrayList<>();

		// numberFormats.add("Success Message");
		// numberFormats.add("German");
		// numberFormats.add("French");
		// numberFormats.add("Canadian (French)");
		// numberFormats.add("Italian");

		List<ErrorSuccess> purchasesNumberFormats = new ArrayList<>();

		purchasesNumberFormats.add(getANumberFormat(message));
		// purchasesNumberFormats.add(getANumberFormat("German"));
		// purchasesNumberFormats.add(getANumberFormat("French"));
		// purchasesNumberFormats.add(getANumberFormat("Canadian (French)"));
		// purchasesNumberFormats.add(getANumberFormat("Italian"));

		return purchasesNumberFormats;
	}

	private static ErrorSuccess getANumberFormat(String msg) {
		ErrorSuccess purchasesNumberFormat = new ErrorSuccess(msg);
		return purchasesNumberFormat;
	}

}
