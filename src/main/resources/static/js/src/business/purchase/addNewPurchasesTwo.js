var businessNameFld;
var businessName;

var emailOfUserFld;
var emailOfUser;

var dateOfPurchases;
var idOfItemBeingPurchased;
var quantityOfItemBeingPurchased;
var unitCostOfItemBeingPurchased;
var isCreditPurchaseOrNot;
var discountReceived;
var amountPaidForThePurchasedItem;
var totalToBePaidManual;
var totalToBePaidAuto;
var idOfTheSupplierOfTheItem;
var narration;

var purchasesTwoSave;

function theConstantVariblesInHere() {

	businessNameFld = $( "#businessNameFld" );
	businessName = $.trim( $( '#businessNameFld' ).val() );
	emailOfUserFld = $( "#emailOfUserFld" );
	emailOfUser = $.trim( $( '#emailOfUserFld' ).val() );
	numFormat = $( "#numberFormatFld option:selected" ).text();

}

function saveNePurchasesTwo() {
	// "/api/business/purchasesTwo/{businessName}/{emailOfUser}/savePurchasesTwo"
	theConstantVariblesInHere();

	var urlPOST = "/api/business/purchasesTwo/" + businessName + "/" + emailOfUser + "/savePurchasesTwo";

	alert( urlPOST );

	$.ajax( {
		url : urlPOST ,
		// cache : false,
		type : 'POST' ,
		data : JSON.stringify( purchasesTwoSave ) ,
		// data : $('#choose_business_form').serialize(),
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
			}

		}

	} );

}

function addNewPurchasesTwo() {

	dateOfPurchases = $( '#inputPurchaseDateId' ).val();
	idOfItemBeingPurchased = $( '#inputPurchaseItemId' ).val();
	quantityOfItemBeingPurchased = $( '#inputPurchaseQuantityId' ).val();

	unitCostOfItemBeingPurchased = $( '#inputPurchaseUnitCostId' ).val();

	if ( $( '#inputPurchaseCreditId' ).is( ":checked" ) ) {
		isCreditPurchaseOrNot = true;
	} else {
		isCreditPurchaseOrNot = false;
	}

	discountReceived = $( '#inputPurchaseDiscountReceivedId' ).val();
	amountPaidForThePurchasedItem = $( '#inputPurchaseAmountPaidId' ).val();
	totalToBePaidManual = $( '#inputPurchaseTotalToBePaidManualId' ).val();
	totalToBePaidAuto = $( '#inputPurchaseTotalToBePaidAutoId' ).val();
	idOfTheSupplierOfTheItem = $( '#inputPurchaseSupplierId' ).val();

	/*
	 * making sure the supplier is specified in case of a specified credit
	 * purchase
	 */
	if ( ( isCreditPurchaseOrNot === true )
			&& ( ( idOfTheSupplierOfTheItem === '' ) || ( idOfTheSupplierOfTheItem === null ) ) ) {

		popup( 'alert' , 'Please specify the supplier. A credit purchase must explicitly specify the supplier' ,
				'hideOk' , 'showDismiss' , '' , 'close' );
		return;

	}

	/*
	 * making sure the credit check is true (checked) in case of amount paid is
	 * not specified
	 */
	if ( ( amountPaidForThePurchasedItem === null || amountPaidForThePurchasedItem === '' )
			&& ( isCreditPurchaseOrNot !== true ) ) {

		popup( 'alert' , 'Please specify this purchase as credit since no amount paid is specified.' , 'hideOk' ,
				'showDismiss' , '' , 'close' );
		return;

	}

	narration = $( '#inputPurchaseNarrationId' ).val();

	if ( idOfItemBeingPurchased === '' || idOfItemBeingPurchased === null ) {

		popup( 'empty item' , 'Please choose an item' , 'hideOk' , 'showDismiss' , '' , 'close' );
		// reShowTheNePurchasesTwoPopUp();
		hidePopup();
		return;

	}

	if ( quantityOfItemBeingPurchased === '' || quantityOfItemBeingPurchased === null ) {

		popup( 'empty quantity' , 'Please specify the item quantity to be purchased' , 'hideOk' , 'showDismiss' , '' ,
				'close' );

		hidePopup();

		// reShowTheNePurchasesTwoPopUp();
		return;

	}

	if ( isCreditPurchaseOrNot === false
			&& ( amountPaidForThePurchasedItem === null || amountPaidForThePurchasedItem === '' ) ) {

		popup( 'no amount paid' , 'it\'s not a credit purchase. Please specify the amount paid' , 'hideOk' ,
				'showDismiss' , '' , 'close' );
		hidePopup();
		// reShowTheNePurchasesTwoPopUp();
		return;

	}

	var purchasesTwo = {
		"dateClient" : dateOfPurchases ,
		"itemId" : idOfItemBeingPurchased ,
		"itemQuantityStr" : quantityOfItemBeingPurchased ,
		"unitCostStr" : unitCostOfItemBeingPurchased ,
		"credit" : isCreditPurchaseOrNot ,
		"discountReceivedStr" : discountReceived ,
		"amountClearedStr" : amountPaidForThePurchasedItem ,
		"totalCostManualStr" : totalToBePaidManual ,
		"totalCostAutoStr" : totalToBePaidAuto ,
		"supplierId" : idOfTheSupplierOfTheItem ,
		"narration" : narration
	}

	purchasesTwoSave = purchasesTwo;

	// popup ( 'empty cart' , 'Please add items to cart before you can sell' ,
	// 'hideOk' , 'showDismiss' , '' , 'close' );

	businessNameFld = $( "#businessNameFld" );
	businessName = $.trim( $( '#businessNameFld' ).val() );

	emailOfUserFld = $( "#emailOfUserFld" );
	emailOfUser = $.trim( $( '#emailOfUserFld' ).val() );

	var numFormat = $( "#numberFormatFld option:selected" ).text();
	// alert( numFormat );

	var urlPOST = "/api/business/purchasesTwo/" + businessName + "/" + emailOfUser
			+ "/checkItemQuantityCorrectness?numberFormat=" + numFormat;

	$.ajax( {
		url : urlPOST ,
		// cache : false,
		type : 'POST' ,
		data : JSON.stringify( purchasesTwo ) ,
		// data : $('#choose_business_form').serialize(),
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

				saveNePurchasesTwo();

			}

		}

	} );

}

/*
 * function to get the id of the item chosen
 * 
 */
function findIdOfSelectedItem() {
	idOfItemBeingPurchased = $( '#inputPurchaseItemId' ).val();
	return idOfItemBeingPurchased;
}

function reShowTheNePurchasesTwoPopUp() {

	$( '#addNewPurchaseModal' ).modal( 'show' );

	$( '#addNewPurchaseModal' ).modal( 'focus' );

}

function hidePopup() {
	$( '#addNewPurchaseModal' ).modal( 'hide' );
}
