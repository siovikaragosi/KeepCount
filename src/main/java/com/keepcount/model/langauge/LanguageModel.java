
package com.keepcount.model.langauge ;

public class LanguageModel {

	private String languageKey ;
	private String languageValue ;

	public LanguageModel() {

	}

	public LanguageModel( String languageKey , String languageValue ) {
		super() ;
		this.languageKey = languageKey ;
		this.languageValue = languageValue ;
	}

	public String getLanguageKey() {
		return languageKey ;
	}

	public void setLanguageKey( String languageKey ) {
		this.languageKey = languageKey ;
	}

	public String getLanguageValue() {
		return languageValue ;
	}

	public void setLanguageValue( String languageValue ) {
		this.languageValue = languageValue ;
	}

	@Override
	public String toString() {
		return "LanguageModel [languageKey=" + languageKey + ", languageValue=" + languageValue + "]" ;
	}

}
