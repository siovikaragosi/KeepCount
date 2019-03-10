package com.keepcount.model.pricing;

public class PricingNumberFormat {

	private String numberFormatName;

	public PricingNumberFormat() {

	}

	public PricingNumberFormat(String numberFormatName) {
		super();
		this.numberFormatName = numberFormatName;
	}

	public String getNumberFormatName() {
		return numberFormatName;
	}

	public void setNumberFormatName(String numberFormatName) {
		this.numberFormatName = numberFormatName;
	}

	@Override
	public String toString() {
		return "PricingNumberFormat [numberFormatName=" + numberFormatName + "]";
	}

}
