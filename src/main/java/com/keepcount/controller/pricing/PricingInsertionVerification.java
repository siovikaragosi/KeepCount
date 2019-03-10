package com.keepcount.controller.pricing;

import java.util.HashMap;
import java.util.Map;

public class PricingInsertionVerification {

	public static Map<String, String> verifyPrice(String numberFormat, String number) {
		Map<String, String> map = new HashMap<>();

		if (numberFormat.equals("English")) {
			map = PricingInsertionVerificationEnglsih.ifEnglish(number);
		} else if (numberFormat.equals("German")) {
			map = PricingInsertionVerificationGerman.ifGerman(number);
		} else if (numberFormat.equals("French")) {
			map = PricingInsertionVerificationFrench.ifFrench(number);
		}
		return map;
	}

}
