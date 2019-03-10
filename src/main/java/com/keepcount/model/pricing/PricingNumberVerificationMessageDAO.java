package com.keepcount.model.pricing;

import java.util.ArrayList;
import java.util.List;

public class PricingNumberVerificationMessageDAO {

	public static List<PricingNumberVerificationMessage> verificationMessage(String message) {
		PricingNumberVerificationMessage message2 = new PricingNumberVerificationMessage();
		message2.setNumberCorrectnessInfo(message);
		List<PricingNumberVerificationMessage> messages = new ArrayList<>();
		messages.add(message2);
		return messages;
	}

}
