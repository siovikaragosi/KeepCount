var businessNameFld;
var businessName;

var emailOfUserFld;
var emailOfUser;

var quantityOfItemBeingPurchased;
var unitCostOfItemBeingPurchased;
var discountReceived;
var amountPaidForThePurchasedItem;
var totalToBePaidManual;
var totalToBePaidAuto;

var numFormat;

function theConstantVaribles() {

	businessNameFld = $( "#businessNameFld" );
	businessName = $.trim( $( '#businessNameFld' ).val() );
	emailOfUserFld = $( "#emailOfUserFld" );
	emailOfUser = $.trim( $( '#emailOfUserFld' ).val() );
	numFormat = $( "#numberFormatFld option:selected" ).text();

}

/*
 * function to post quantity of the item being purchased alone at the very
 * beginning. This means no any other calculation is done
 */

function postQuantityOfItemForVerification() {

	theConstantVaribles();

	quantityOfItemBeingPurchased = $( '#inputPurchaseQuantityId' ).val();
	var purchasesTwo = {
		"itemQuantityStr" : quantityOfItemBeingPurchased ,
	}
	var urlPOST = "/api/business/purchasesTwo/" + businessName + "/" + emailOfUser
			+ "/checkItemQuantityCorrectness?numberFormat=" + numFormat;
	if ( ( quantityOfItemBeingPurchased === null ) || ( quantityOfItemBeingPurchased === '' ) ) {

	} else {
		postNumbers();
	}

}

function postNumbers() {

	theConstantVaribles();

	quantityOfItemBeingPurchased = $( '#inputPurchaseQuantityId' ).val();

	var purchasesTwo = {
		"itemQuantityStr" : quantityOfItemBeingPurchased ,
	}

	var urlPOST = "/api/business/purchasesTwo/" + businessName + "/" + emailOfUser
			+ "/checkItemQuantityCorrectness?numberFormat=" + numFormat;

	$.ajax( {
		url : urlPOST ,
		type : 'POST' ,
		data : JSON.stringify( purchasesTwo ) ,
		contentType : 'application/json' ,
		dataType : 'json' ,
		async : false ,
		success : function( data , textStatus , xhr ) {
			console.log( xhr.status );
		} ,
		complete : function( xhr , textStatus ) {
			console.log( xhr.status );
			if ( xhr.status !== 200 ) {
				console.log( xhr.status );
			} else if ( xhr.status === 200 ) {
				console.log( xhr.status );

				if ( ( unitCostOfItemBeingPurchased === null ) || ( unitCostOfItemBeingPurchased === '' ) ) {
					return;
				} else {
					getTotalToBePaidAuto();
				}

			}
		}
	} );
}

/*
 * 
 * Post the amount paid
 */

function postAmountPaid() {

	theConstantVaribles();

	amountPaidForThePurchasedItem = $( '#inputPurchaseAmountPaidId' ).val();

	var purchasesTwo = {
		"amountClearedStr" : amountPaidForThePurchasedItem
	}

	var urlPOST = "/api/business/purchasesTwo/" + businessName + "/" + emailOfUser
			+ "/checkAmountPaidCorrectness?numberFormat=" + numFormat;

	$.ajax( {
		url : urlPOST ,
		type : 'POST' ,
		data : JSON.stringify( purchasesTwo ) ,
		contentType : 'application/json' ,
		dataType : 'json' ,
		async : false ,
		success : function( data , textStatus , xhr ) {
			console.log( xhr.status );
		} ,
		complete : function( xhr , textStatus ) {
			console.log( xhr.status );
			if ( xhr.status !== 200 ) {
				console.log( xhr.status );
			} else if ( xhr.status === 200 ) {
				console.log( xhr.status );

				// if ( ( unitCostOfItemBeingPurchased === null ) || (
				// unitCostOfItemBeingPurchased === '' ) ) {
				// return;
				// } else {
				// getTotalToBePaidAuto();
				// }

			}
		}
	} );
}

function postUnitCost() {

	theConstantVaribles();

	unitCostOfItemBeingPurchased = $( '#inputPurchaseUnitCostId' ).val();

	var purchasesTwo = {
		"unitCostStr" : unitCostOfItemBeingPurchased
	}

	if ( ( unitCostOfItemBeingPurchased === null ) || ( unitCostOfItemBeingPurchased === '' ) ) {
		return;
	}

	// "/api/business/purchasesTwo/{businessName}/{emailOfUser}/checkUnitCostCorrectness"

	var urlPOST = "/api/business/purchasesTwo/" + businessName + "/" + emailOfUser
			+ "/checkUnitCostCorrectness?numberFormat=" + numFormat;
	$.ajax( {
		url : urlPOST ,
		type : 'POST' ,
		data : JSON.stringify( purchasesTwo ) ,
		contentType : 'application/json' ,
		dataType : 'json' ,
		async : false ,
		success : function( data , textStatus , xhr ) {
			console.log( xhr.status );

		} ,
		complete : function( xhr , textStatus ) {
			console.log( xhr.status );
			if ( xhr.status !== 200 ) {
				console.log( xhr.status );
			} else if ( xhr.status === 200 ) {
				console.log( xhr.status );

				quantityOfItemBeingPurchased = $( '#inputPurchaseQuantityId' ).val();
				if ( ( quantityOfItemBeingPurchased === null ) || ( quantityOfItemBeingPurchased === '' ) ) {

				} else {
					getTotalToBePaidAuto();
				}

			}
		}
	} );
}

/*
 * This functions calculates the total to be paid (auto)
 * 
 */

function getTotalToBePaidAuto() {

	theConstantVaribles();

	// "/api/business/purchasesTwo/{businessName}/{emailOfUser}/totalToBePaidAuto"

	var urlGET = "/api/business/purchasesTwo/" + businessName + "/" + emailOfUser + "/totalToBePaidAuto";

	$.ajax( {
		type : "GET" ,
		url : urlGET ,
		async : false ,
		success : function( result ) {
			$.each( result , function( i , purchasesTwo ) {

				$( '#inputPurchaseTotalToBePaidAutoId' ).val( purchasesTwo.totalCostAutoStr );

			} );

		} ,
		error : function( e ) {
			alert( "ERROR: " , e );
			console.log( "ERROR: " , e );
		}
	} );

}

/*
 * This functions return the amount paid
 * 
 */

function getAmountPaid() {

	theConstantVaribles();

	// "/api/business/purchasesTwo/{businessName}/{emailOfUser}/totalToBePaidAuto"

	var urlGET = "/api/business/purchasesTwo/" + businessName + "/" + emailOfUser + "/getAmountPaidAuto";

	$.ajax( {
		type : "GET" ,
		url : urlGET ,
		async : false ,
		success : function( result ) {
			$.each( result , function( i , purchasesTwo ) {

				$( '#inputPurchaseAmountPaidId' ).val( purchasesTwo.amountPaidStr );

			} );

		} ,
		error : function( e ) {
			alert( "ERROR: " , e );
			console.log( "ERROR: " , e );
		}
	} );

}

/*
 * 
 * When the number format has been changed
 * 
 */

function uponChangingNumberFormat() {

	postQuantityOfItemForVerification();

}
