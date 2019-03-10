/*
 * "/api/business/purchasesTwo/{businessName}/{emailOfUser}/allItems"
 * */

var businessNameFld;
var businessName;

var emailOfUserFld;
var emailOfUser;
/*
 * This code (function) below populates the item list (all items) to the select
 * drop down. It picks it from the class PurchasesTwoController
 * 
 * 
 */
function populateItemsForPurchase() {
	businessNameFld = $( "#businessNameFld" );
	businessName = $.trim( $( '#businessNameFld' ).val() );

	emailOfUserFld = $( "#emailOfUserFld" );
	emailOfUser = $.trim( $( '#emailOfUserFld' ).val() );

	var urlGETNumberFormat = "/api/business/purchasesTwo/" + businessName + "/" + emailOfUser + "/allItems";

	$( '#inputPurchaseItemId' ).append( '<option value=' + '' + '>' + '' + '</option>' )

	$.ajax( {
		type : "GET" ,
		url : urlGETNumberFormat ,
		success : function( result ) {
			$.each( result , function( i , item ) {

				console.log( "id-" + item.id );

				$( '#inputPurchaseItemId' ).append(
						'<option value=' + item.id + '>' + item.itemName + ' -- (' + item.itemSubCategory + ')'
								+ '</option>' );

			} );
		} ,
		error : function( e ) {
			alert( "ERROR: " , e );
			console.log( "ERROR: " , e );
		}
	} );
}

/*
 * This code (function) below populates the supplier list (all suppliers) to the
 * select drop down. It picks it from the class PurchasesTwoController
 * 
 * 
 */
function populateSuppliersForPurchase() {
	businessNameFld = $( "#businessNameFld" );
	businessName = $.trim( $( '#businessNameFld' ).val() );

	emailOfUserFld = $( "#emailOfUserFld" );
	emailOfUser = $.trim( $( '#emailOfUserFld' ).val() );

	var urlGETNumberFormat = "/api/business/purchasesTwo/" + businessName + "/" + emailOfUser + "/allSuppliers";

	$( '#inputPurchaseSupplierId' ).append( '<option value=' + '' + '>' + '' + '</option>' )

	$.ajax( {
		type : "GET" ,
		url : urlGETNumberFormat ,
		success : function( result ) {
			$.each( result , function( i , supplier ) {

				$( '#inputPurchaseSupplierId' ).append(
						'<option value=' + supplier.id + '>' + supplier.supplierName + '</option>' );

			} );
		} ,
		error : function( e ) {
			alert( "ERROR: " , e );
			console.log( "ERROR: " , e );
		}
	} );
}

function populateBothItemAndSupplierDropDownLists() {
	populateItemsForPurchase();
	populateSuppliersForPurchase();
}