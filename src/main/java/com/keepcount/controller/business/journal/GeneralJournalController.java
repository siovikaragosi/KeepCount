
package com.keepcount.controller.business.journal ;

import java.util.List ;

import javax.servlet.http.HttpServletRequest ;
import javax.servlet.http.HttpServletResponse ;

import org.springframework.beans.factory.annotation.Autowired ;
import org.springframework.stereotype.Controller ;
import org.springframework.ui.Model ;
import org.springframework.web.bind.annotation.PathVariable ;
import org.springframework.web.bind.annotation.RequestMapping ;
import org.springframework.web.bind.annotation.RequestMethod ;
import org.springframework.web.bind.annotation.ResponseBody ;

import com.keepcount.controller.business.choose.ChooseBusinessController ;
import com.keepcount.model.business.create.EmailsOfABusinessLogin ;
import com.keepcount.model.business.journal.GeneralJournal ;
import com.keepcount.model.business.ledger.Ledger ;
import com.keepcount.service.business.journal.GeneralJournalService ;
import com.keepcount.service.business.ledger.LedgerService ;

@Controller
public class GeneralJournalController {

	@Autowired
	private GeneralJournalService generalJournalService ;

	private int changeMade = 0 ;

	public int getChangeMade() {
		return changeMade ;
	}

	public void setChangeMade( int changeMade ) {
		this.changeMade = changeMade ;

	}

	@RequestMapping( value = "/business/generalJournal/{businessName}/{emailOfUser}" )
	public String showPurchasesPage( Model model , @PathVariable( name = "businessName" ) String businessName , @PathVariable( name = "emailOfUser" ) String emailOfUser ) {
		EmailsOfABusinessLogin emailsOfABusiness = new EmailsOfABusinessLogin() ;
		emailOfUser = ChooseBusinessController.getEmailLoggedIn() ;
		businessName = ChooseBusinessController.getNameOfTheBusiness() ;
		model.addAttribute( "emailOfUser" , emailOfUser ) ;
		model.addAttribute( "businessName" , businessName ) ;
		model.addAttribute( "emailsOfABusinessForm" , emailsOfABusiness ) ;

		System.out.println( "em and bizName at purch2: " + emailOfUser + " : " + businessName ) ;

		setChangeMade( 1 ) ;
		setChangeMade( 0 ) ;
		return "/keepCount/business/generalJournal/generalJournal" ;
	}

	/*
	 * this method saves the general journals details
	 */

	@RequestMapping( value = "/api/business/generalJournal/{businessName}/{emailOfUser}/saveJournals" , method = RequestMethod.POST )
	@ResponseBody
	public String apiToSaveJournals( HttpServletRequest request , HttpServletResponse response , @PathVariable( name = "businessName" ) String businessName ,
			@PathVariable( name = "emailOfUser" ) String emailOfUser ) {

		String businessId = ChooseBusinessController.getIdOfBusiness().toString() ;

		// GeneralJournalHelperToPickFromOtherClasses generalJournalHelperToPickFromOtherClasses = new
		// GeneralJournalHelperToPickFromOtherClasses() ;
		// generalJournalHelperToPickFromOtherClasses.getGeneralJournalsToBeTakenToTheGenJournalController()
		// ;

		return "/api/business/generalJournal/{businessName}/{emailOfUser}/saveJournals" ;
	}

	public static int [ ] anotherPostMethodToEvadeTriggeringFromTheWeb( List < GeneralJournal > generalJournalToEnhanceBatch , String businessId ) {
		GeneralJournalService journalService = new GeneralJournalService() ;

		System.out.println( "the gj list: " + generalJournalToEnhanceBatch ) ;

		return journalService.newJournal( generalJournalToEnhanceBatch , businessId ) ;
	}

}
