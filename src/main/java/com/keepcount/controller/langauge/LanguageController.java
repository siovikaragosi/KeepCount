
package com.keepcount.controller.langauge ;

import org.springframework.beans.factory.annotation.Autowired ;
import org.springframework.stereotype.Controller ;
import org.springframework.web.bind.annotation.RequestMapping ;

import com.google.gson.Gson ;
import com.keepcount.service.langauge.LanguageService ;

@Controller
public class LanguageController {

	@Autowired
	private LanguageService languageService ;

	@RequestMapping( value = "/business/language" )
	public String defaultDefualtAtSignUpLogin( String areaOfConcern ) {

		return new Gson().toJson( languageService.findAllLanguageWords( "en" , "US" , areaOfConcern ) ) ;
	}

}
