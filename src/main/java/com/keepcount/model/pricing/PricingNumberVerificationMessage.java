package com.keepcount.model.pricing;

public class PricingNumberVerificationMessage {

	private String numberCorrectnessInfo;

	public PricingNumberVerificationMessage() {

	}

	public PricingNumberVerificationMessage(String numberCorrectnessInfo) {
		super();
		this.numberCorrectnessInfo = numberCorrectnessInfo;
	}

	public String getNumberCorrectnessInfo() {
		return numberCorrectnessInfo;
	}

	public void setNumberCorrectnessInfo(String numberCorrectnessInfo) {
		this.numberCorrectnessInfo = numberCorrectnessInfo;
	}

	@Override
	public String toString() {
		return "PricingNumberVerification [numberCorrectnessInfo=" + numberCorrectnessInfo + "]";
	}

}
