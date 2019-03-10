
package com.keepcount.service.pos ;

import java.util.List ;

import org.springframework.stereotype.Service ;

import com.keepcount.controller.pos.PosHibernationRetrievals ;
import com.keepcount.dao.errorsuccess.ErrorSuccessHibernation ;
import com.keepcount.model.errorsuccess.ErrorSuccess ;
import com.keepcount.model.pricing.Pricing ;

@Service
public class PosService {

	public List < Pricing > findAllItemsForCart_Direct( String businessId , String numberFormat ) {

		return PosHibernationRetrievals.findAllItemsForCart_Direct( businessId , numberFormat ) ;
	}

	public List < Pricing > findAllItemsForCart_Not_Direct() {

		return PosHibernationRetrievals.findAllItemsForCart_Not_Direct() ;
	}

	public List < ErrorSuccess > getSuccessMessage( String message ) {

		return ErrorSuccessHibernation.getSuccessMessage( message ) ;
	}

}
