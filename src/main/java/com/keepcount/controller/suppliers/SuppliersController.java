
package com.keepcount.controller.suppliers ;

import java.math.BigDecimal ;

import javax.servlet.http.HttpServletRequest ;
import javax.servlet.http.HttpServletResponse ;

import org.springframework.beans.factory.annotation.Autowired ;
import org.springframework.stereotype.Controller ;
import org.springframework.ui.Model ;
import org.springframework.web.bind.annotation.PathVariable ;
import org.springframework.web.bind.annotation.RequestBody ;
import org.springframework.web.bind.annotation.RequestMapping ;
import org.springframework.web.bind.annotation.RequestMethod ;
import org.springframework.web.bind.annotation.ResponseBody ;

import com.google.gson.Gson ;
import com.keepcount.controller.business.choose.ChooseBusinessController ;
import com.keepcount.model.business.create.EmailsOfABusinessLogin ;
import com.keepcount.model.suppliers.Suppliers ;
import com.keepcount.model.user.User ;
import com.keepcount.service.suppliers.SuppliersService ;

@Controller
public class SuppliersController {

	private int changeMade = 0 ;

	@Autowired
	private SuppliersService suppliersService ;

	@RequestMapping( value = "/business/suppliers/{businessName}/{emailOfUser}" )
	public String showItemsPage( Model model , @PathVariable( name = "businessName" ) String businessName , @PathVariable( name = "emailOfUser" ) String emailOfUser ) {

		EmailsOfABusinessLogin emailsOfABusiness = new EmailsOfABusinessLogin() ;
		emailOfUser = ChooseBusinessController.getEmailLoggedIn() ;
		businessName = ChooseBusinessController.getNameOfTheBusiness() ;

		model.addAttribute( "emailsOfABusinessForm" , emailsOfABusiness ) ;
		model.addAttribute( "emailOfUser" , emailOfUser ) ;
		model.addAttribute( "businessName" , businessName ) ;

		System.out.println( "em-" + emailOfUser + "-----biz-" + businessName ) ;

		// setChangeMade(1);

		return "/keepCount/business/supplier/suppliers" ;
	}

	@RequestMapping( value = "/api/business/suppliers/{businessName}/{emailOfUser}" , method = RequestMethod.POST )
	@ResponseBody
	public String createNewSupplier( HttpServletRequest request , HttpServletResponse response , @PathVariable( name = "businessName" ) String businessName ,
			@PathVariable( name = "emailOfUser" ) String emailOfUser , @RequestBody Suppliers supplier ) {

		emailOfUser = ChooseBusinessController.getEmailLoggedIn() ;
		businessName = ChooseBusinessController.getNameOfTheBusiness() ;
		String emailOfThebusiness = ChooseBusinessController.getEmailOfThisBusiness() ;
		// String businessId = businessName.concat(emailOfThebusiness);
		String businessId = ChooseBusinessController.getIdOfBusiness().toString() ;

		int result = suppliersService.createNewSupplier( supplier , businessId ) ;
		// System.out.println("sup result: " + result);
		if ( result == 1 ) {
			setChangeMade( 1 ) ;
		} else {
			setChangeMade( 0 ) ;
		}

		return "/api/business/items/{businessName}/{emailOfUser}" ;
	}

	@RequestMapping( value = "/api/business/suppliers/{businessName}/{emailOfUser}/allSuppliers" , method = RequestMethod.GET , produces = "application/json" )
	@ResponseBody
	public String findAllSuppliers( Model model , @PathVariable( name = "businessName" ) String businessName , @PathVariable( name = "emailOfUser" ) String emailOfUser ) {

		EmailsOfABusinessLogin emailsOfABusiness = new EmailsOfABusinessLogin() ;
		emailOfUser = ChooseBusinessController.getEmailLoggedIn() ;
		businessName = ChooseBusinessController.getNameOfTheBusiness() ;
		// String emailOfTheBusiness =
		// ChooseBusinessController.getEmailOfThisBusiness();
		String businessId = ChooseBusinessController.getIdOfBusiness().toString() ;

		model.addAttribute( "emailsOfABusinessForm" , emailsOfABusiness ) ;
		model.addAttribute( "emailOfUser" , emailOfUser ) ;
		model.addAttribute( "businessName" , businessName ) ;

		// String businessId = businessName.concat(emailOfTheBusiness);

		if ( getChangeMade() != 1 ) {
			// System.out.println("sup: not 1");
			System.out.println( "0=" + new Gson().toJson( suppliersService.findAllSuppliersNotDirectly( businessId ) ) ) ;
			return new Gson().toJson( suppliersService.findAllSuppliersNotDirectly( businessId ) ) ;

		} else {
			// System.out.println("sup: 1");
			System.out.println( "1=" + new Gson().toJson( suppliersService.findAllSupplierDirectly( businessId ) ) ) ;
			return new Gson().toJson( suppliersService.findAllSupplierDirectly( businessId ) ) ;

		}

	}

	@RequestMapping( value = "/api/business/suppliers/{businessName}/{emailOfUser}/{id}/delete" , method = RequestMethod.DELETE )
	@ResponseBody
	public String deleteUser( HttpServletRequest request , HttpServletResponse response ,

			@PathVariable( name = "businessName" ) String businessName ,

			@PathVariable( name = "emailOfUser" ) String emailOfUser , @PathVariable( name = "id" ) BigDecimal id ) {

		new ChooseBusinessController() ;
		emailOfUser = ChooseBusinessController.getEmailLoggedIn() ;
		new ChooseBusinessController() ;
		businessName = ChooseBusinessController.getNameOfTheBusiness() ;
		new ChooseBusinessController() ;
		// String emailOfThebusiness =
		// ChooseBusinessController.getEmailOfThisBusiness();

		// String businessId = businessName.concat(emailOfThebusiness);
		String businessId = ChooseBusinessController.getIdOfBusiness().toString() ;

		int result = suppliersService.deleteSupplier( id , businessId ) ;

		if ( result == 1 ) {
			// System.out.println("deleted successfully");
			setChangeMade( 1 ) ;
		} else {
			// System.out.println("something went wrong");
		}

		// setInsertions(0);
		return "/api/business/suppliers/{businessName}/{emailOfUser}/{id}/delete" ;
	}

	@RequestMapping( value = "/api/business/suppliers/{businessName}/{emailOfUser}/{id}/update" , method = RequestMethod.PUT )
	@ResponseBody
	public String updateUser( HttpServletRequest request , HttpServletResponse response , @PathVariable( name = "businessName" ) String businessName ,
			@PathVariable( name = "emailOfUser" ) String emailOfUser , @PathVariable( name = "id" ) BigDecimal id , @RequestBody Suppliers suppliers ) {

		emailOfUser = ChooseBusinessController.getEmailLoggedIn() ;
		businessName = ChooseBusinessController.getNameOfTheBusiness() ;
		String emailOfThebusiness = ChooseBusinessController.getEmailOfThisBusiness() ;
		// String businessId = businessName.concat(emailOfThebusiness);

		// System.out.println("ID same: " + suppliers.getId());
		String businessId = ChooseBusinessController.getIdOfBusiness().toString() ;

		suppliersService.updateSupplier( id , businessId , suppliers ) ;

		setChangeMade( 1 ) ;

		return "/api/business/suppliers/{businessName}/{emailOfUser}/{id}" ;
	}

	public int getChangeMade() {
		return changeMade ;
	}

	public void setChangeMade( int changeMade ) {
		this.changeMade = changeMade ;
	}

}
