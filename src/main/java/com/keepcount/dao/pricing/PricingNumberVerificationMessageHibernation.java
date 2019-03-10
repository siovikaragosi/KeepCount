package com.keepcount.dao.pricing;

import java.util.List;

import com.keepcount.model.pricing.PricingNumberVerificationMessage;
import com.keepcount.model.pricing.PricingNumberVerificationMessageDAO;

public class PricingNumberVerificationMessageHibernation {

	public static List<PricingNumberVerificationMessage> verificationMessage(String message) {
		return PricingNumberVerificationMessageDAO.verificationMessage(message);
	}

}
