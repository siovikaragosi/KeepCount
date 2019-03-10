
package com.keepcount.service.langauge ;

import java.util.Map ;

import org.springframework.stereotype.Service ;

import com.keepcount.dao.langauge.LanguageSignUpHibernation ;
import com.keepcount.model.langauge.LanguageModel ;

@Service
public class LanguageService {

	public Map < String , LanguageModel > findAllLanguageWords( String language , String country , String areaOfConcern ) {
		return LanguageSignUpHibernation.findAllLanguageWords( language , country , areaOfConcern ) ;
	}

	public Map < String , LanguageModel > changeLanguage( String language , String country , String areaOfConcern ) {
		return LanguageSignUpHibernation.changeLanguage( language , country , areaOfConcern ) ;
	}

}
