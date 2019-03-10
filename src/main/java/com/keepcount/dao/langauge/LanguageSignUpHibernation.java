
package com.keepcount.dao.langauge ;

import java.util.LinkedHashMap ;
import java.util.Map ;

import com.keepcount.model.langauge.LanguageModel ;

public class LanguageSignUpHibernation {

	private static Map < String , LanguageModel > currentLanguage ;

	public static Map < String , LanguageModel > getCurrentLanguage() {
		return currentLanguage ;
	}

	public static void setCurrentLanguage( Map < String , LanguageModel > currentLanguage ) {
		LanguageSignUpHibernation.currentLanguage = currentLanguage ;
	}

	private static Map < String , LanguageModel > findAllLanguageWords_DirectFromFiles( String language , String country , String areaOfConcern ) {
		Map < String , LanguageModel > map = new LinkedHashMap <>() ;
		map = LanguageSignUpDAO.findAllLanguageWords( language , country , areaOfConcern ) ;
		setCurrentLanguage( map ) ;
		return map ;
	}

	public static Map < String , LanguageModel > findAllLanguageWords( String language , String country , String areaOfConcern ) {
		if ( getCurrentLanguage() != null ) {
			return getCurrentLanguage() ;
		} else {
			return findAllLanguageWords_DirectFromFiles( language , country , areaOfConcern ) ;
		}
	}

	public static Map < String , LanguageModel > changeLanguage( String language , String country , String areaOfConcern ) {
		findAllLanguageWords_DirectFromFiles( language , country , areaOfConcern ) ;
		return getCurrentLanguage() ;
	}

}
