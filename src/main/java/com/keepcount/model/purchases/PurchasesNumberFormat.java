package com.keepcount.model.purchases;

public class PurchasesNumberFormat {

	private String numberFormatName;

	public PurchasesNumberFormat() {

	}

	public PurchasesNumberFormat(String numberFormatName) {
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
		return "PurchasesNumberFormat [numberFormatName=" + numberFormatName + "]";
	}

}
