
package com.keepcount.controller.pricing ;

import org.springframework.beans.factory.annotation.Autowired ;
import org.springframework.stereotype.Controller ;
import org.springframework.web.bind.annotation.RequestMapping ;
import org.springframework.web.bind.annotation.RequestMethod ;
import org.springframework.web.bind.annotation.ResponseBody ;

import com.google.gson.Gson ;
import com.keepcount.service.numberformat.NumberFormatService ;

@Controller
public class NumberFormatController {

	@Autowired
	private NumberFormatService numberFormatService ;

	/* This method populates the drop down list of the language formats */
	@RequestMapping( value = "/api/business/numberFormats/language" , method = RequestMethod.GET , produces = "application/json" )
	@ResponseBody
	public String findNumberFormats() {

		return new Gson().toJson( numberFormatService.getPurchasesNumberFormat() ) ;
	}

	
	
}
