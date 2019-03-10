
package com.keepcount.controller.item ;

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
import com.keepcount.model.item.Item ;
import com.keepcount.model.item.UnitOfMeasurement ;
import com.keepcount.service.item.ItemService ;
import com.keepcount.service.item.UnitOfMeasurementService ;

@Controller
public class ItemController {

	private int changeMade = 0 ;

	private static BigDecimal id ;

	@Autowired
	private ItemService itemService ;

	@Autowired
	private UnitOfMeasurementService unitOfMeasurementService ;

	@RequestMapping( value = "/business/items/{businessName}/{emailOfUser}" )
	public String showItemsPage( Model model , @PathVariable( name = "businessName" ) String businessName , @PathVariable( name = "emailOfUser" ) String emailOfUser ) {

		EmailsOfABusinessLogin emailsOfABusiness = new EmailsOfABusinessLogin() ;
		emailOfUser = ChooseBusinessController.getEmailLoggedIn() ;
		businessName = ChooseBusinessController.getNameOfTheBusiness() ;

		model.addAttribute( "emailsOfABusinessForm" , emailsOfABusiness ) ;
		model.addAttribute( "emailOfUser" , emailOfUser ) ;
		model.addAttribute( "businessName" , businessName ) ;

		// setId(ChooseBusinessController.getIdOfBusiness());

		setChangeMade( 1 ) ;

		return "/keepCount/business/item/item" ;
	}

	@RequestMapping( value = "/api/business/items/{businessName}/{emailOfUser}" , method = RequestMethod.POST )
	@ResponseBody
	public String createNewItem( HttpServletRequest request , HttpServletResponse response , @PathVariable( name = "businessName" ) String businessName ,
			@PathVariable( name = "emailOfUser" ) String emailOfUser , @RequestBody Item item ) {

		emailOfUser = ChooseBusinessController.getEmailLoggedIn() ;
		businessName = ChooseBusinessController.getNameOfTheBusiness() ;
		// String emailOfThebusiness =
		// ChooseBusinessController.getEmailOfThisBusiness();

		String businessId = ChooseBusinessController.getIdOfBusiness().toString() ;

		int result = itemService.createNewItem( item , businessId ) ;

		if ( result == 0 ) {
			setChangeMade( 1 ) ;
		}

		return "/api/business/items/{businessName}/{emailOfUser}" ;
	}

	@RequestMapping( value = "/api/business/items/unitOfMeasurement/{businessName}/{emailOfUser}" , method = RequestMethod.POST )
	@ResponseBody
	public String createNewItemUnitOfMeasurement( HttpServletRequest request , HttpServletResponse response , @PathVariable( name = "businessName" ) String businessName ,
			@PathVariable( name = "emailOfUser" ) String emailOfUser , @RequestBody UnitOfMeasurement measurement ) {

		emailOfUser = ChooseBusinessController.getEmailLoggedIn() ;
		businessName = ChooseBusinessController.getNameOfTheBusiness() ;

		String businessId = ChooseBusinessController.getIdOfBusiness().toString() ;

		int result = unitOfMeasurementService.newUnit( measurement , businessId ) ;

		if ( result == 0 ) {
			setChangeMade( 1 ) ;
		}

		return "/api/business/items/unitOfMeasurement/{businessName}/{emailOfUser}" ;
	}

	@RequestMapping( value = "api/business/items/{businessName}/{emailOfUser}/{id}/delete" , method = RequestMethod.DELETE )
	@ResponseBody
	public String deleteItem( HttpServletRequest request , HttpServletResponse response ,

			@PathVariable( name = "businessName" ) String businessName ,

			@PathVariable( name = "emailOfUser" ) String emailOfUser , @PathVariable( name = "id" ) BigDecimal id ) {

		emailOfUser = ChooseBusinessController.getEmailLoggedIn() ;
		businessName = ChooseBusinessController.getNameOfTheBusiness() ;
		// String emailOfThebusiness =
		// ChooseBusinessController.getEmailOfThisBusiness();

		String businessId = ChooseBusinessController.getIdOfBusiness().toString() ;

		int result = itemService.deleteItem( id , businessId ) ;

		if ( result == 1 ) {
			System.out.println( "deleted successfully" ) ;
			setChangeMade( 1 ) ;
		} else {
			System.out.println( "something went wrong" ) ;
		}

		// setInsertions(0);
		return "/api/business/items/{businessName}/{emailOfUser}" ;
	}

	@RequestMapping( value = "/api/business/items/{businessName}/{emailOfUser}/{id}/update" , method = RequestMethod.PUT )
	@ResponseBody
	public String updateItem( HttpServletRequest request , HttpServletResponse response ,

			@PathVariable( name = "businessName" ) String businessName ,

			@PathVariable( name = "emailOfUser" ) String emailOfUser , @PathVariable( name = "id" ) String id , @RequestBody Item item ) {

		emailOfUser = ChooseBusinessController.getEmailLoggedIn() ;
		businessName = ChooseBusinessController.getNameOfTheBusiness() ;
		// String emailOfThebusiness =
		// ChooseBusinessController.getEmailOfThisBusiness();

		String businessId = ChooseBusinessController.getIdOfBusiness().toString() ;

		int result = itemService.updateItem( new BigDecimal( id ) , businessId , item ) ;
		System.out.println( "result item update: " + result ) ;
		if ( result == 1 ) {
			System.out.println( "edited successfully" ) ;
			setChangeMade( 1 ) ;
		} else {
			System.out.println( "something went wrong" ) ;
		}

		// setInsertions(0);
		return "/api/business/items/{businessName}/{emailOfUser}" ;
	}

	@RequestMapping( value = "/api/business/items/{businessName}/{emailOfUser}/allItems" , method = RequestMethod.GET , produces = "application/json" )
	@ResponseBody
	public String findAllItems( Model model , @PathVariable( name = "businessName" ) String businessName , @PathVariable( name = "emailOfUser" ) String emailOfUser ) {

		EmailsOfABusinessLogin emailsOfABusiness = new EmailsOfABusinessLogin() ;
		emailOfUser = ChooseBusinessController.getEmailLoggedIn() ;
		businessName = ChooseBusinessController.getNameOfTheBusiness() ;
		// String emailOfTheBusiness =
		// ChooseBusinessController.getEmailOfThisBusiness();

		model.addAttribute( "emailsOfABusinessForm" , emailsOfABusiness ) ;
		model.addAttribute( "emailOfUser" , emailOfUser ) ;
		model.addAttribute( "businessName" , businessName ) ;

		String businessId = ChooseBusinessController.getIdOfBusiness().toString() ;

		String allItems = null ;

		if ( getChangeMade() == 0 ) {
			allItems = new Gson().toJson( itemService.findAllItemsWithoutRefreshing( businessId ) ) ;
			return allItems ;
		} else {
			allItems = new Gson().toJson( itemService.findAllItemsWithRefreshing( businessId ) ) ;
			return allItems ;
		}

	}

	public int getChangeMade() {
		return changeMade ;
	}

	public void setChangeMade( int changeMade ) {
		this.changeMade = changeMade ;
	}

	public static BigDecimal getId() {
		return id ;
	}

	public static void setId( BigDecimal id ) {
		ItemController.id = id ;
	}

}
