( function () {

	jQuery ( init );

	var businessNameFld;
	var businessName;

	var emailOfUserFld;
	var emailOfUser;

	var btnShowItemList;
	var btnAddToSalesList;

	var itemQuantityStr;

	function init () {

		// var urlURL = "/business/posCash/b5/alimahmoudraage@gmail.com";
		// window.history.pushState ( "object or string" , "Title" , urlURL );

		setURL ();
		populateTableWithin ();
		// refreshNumberFormatList();

		btnAddToSalesList = $ ( "#btnAddToSalesList" );
		btnAddToSalesList.click ( checkQuantityCorrectectness );

		selectingToAddToSalesList ();

		btnShowItemList = $ ( "#btnShowItemList" );
		// btnShowItemList.click(populateTable);

		function setURL () {
			businessNameFld = $ ( "#businessNameFld" );
			businessName = $.trim ( $ ( '#businessNameFld' ).val () );

			emailOfUserFld = $ ( "#emailOfUserFld" );
			emailOfUser = $.trim ( $ ( '#emailOfUserFld' ).val () );

			// window.history.pushState ( "object or string" , "Title" ,
			// "/business/pos/" + businessName + "/" + emailOfUser );
		}

		function refreshNumberFormatList () {
			var urlGETNumberFormat = "/api/business/purchases/numberFormats";

			$ ( '#numberFormatFld' ).append ( '<option value=' + '' + '>' + '' + '</option>' )
			
			$.ajax ( {
				type : "GET" ,
				url : urlGETNumberFormat ,
				success : function ( result ) {
					$.each ( result , function ( i , purhcasesNumberFormat ) {

						console.log ( purhcasesNumberFormat.numberFormatName );
						$ ( '#numberFormatFld' ).append ( '<option value=' + purhcasesNumberFormat.numberFormatName + '>' + purhcasesNumberFormat.numberFormatName + '</option>' )
					} );
				} ,
				error : function ( e ) {
					alert ( "ERROR: " , e );
					console.log ( "ERROR: " , e );
				}
			} );
		}

		function selectingToAddToSalesList () {

			$ ( "#itemListTable" ).on ( 'click' , '#btnAddToSalesList' , function () {

				checkQuantityCorrectectness ();

			} );

		}

		function checkQuantityCorrectectness () {

			businessNameFld = $ ( "#businessNameFld" );
			businessName = $.trim ( $ ( '#businessNameFld' ).val () );

			emailOfUserFld = $ ( "#emailOfUserFld" );
			emailOfUser = $.trim ( $ ( '#emailOfUserFld' ).val () );

			itemSelected = $ ( "#itemFld option:selected" ).text ();
			itemSubCategory = $ ( "#itemFld option:selected" ).text ();
			itemPrice = $ ( '#itemPriceFld' ).val ();

			itemQuantityStr = $ ( '#itemQtyObtainedFld' ).val ();

			var numFormat = $ ( "#numberFormatFld option:selected" ).text ();
			var urlPOST = "/api/business/posCash/" + businessName + "/" + emailOfUser + "/checkQuantityCorrectness?numberFormat=" + numFormat;

			// private BigDecimal id;
			// private BigDecimal itemId;
			// private String item;
			// private String itemSubCategory;
			// private BigDecimal itemQuantity;
			// private String itemQuantityStr;
			// private BigDecimal price;
			// private String priceStr;
			// private String unitOfMeasurement;

			// alert("format: " + numFormat);

			var addToCart = {
				"itemQuantityStr" : itemQuantityStr
			}

			// alert("qty: " + itemQuantityStr);

			$.ajax ( {
				url : urlPOST ,
				// cache : false,
				type : 'POST' ,
				data : JSON.stringify ( addToCart ) ,
				// data : $('#choose_business_form').serialize(),
				contentType : 'application/json' ,
				dataType : 'json' ,
				async : false ,
				success : function ( data , textStatus , xhr ) {
					console.log ( xhr.status );
					// alert(xhr.status);
					// refreshTable();
					checkQuantityNumberSuccess ();
				} ,
				complete : function ( xhr , textStatus ) {
					console.log ( xhr.status );
					if ( xhr.status !== 200 ) {
						// alert(xhr.status);
						console.log ( xhr.status );
						checkQuantityNumberSuccess ();
					}
					else
						if ( xhr.status === 200 ) {
							// alert(xhr.status);
							console.log ( xhr.status );
							checkQuantityNumberSuccess ();
							// refreshTableOnNumberFormatchanged();
							// refreshTableOnSave();
							// refreshTable();
							// clearFields();
						}

				}

			} );

		}

		function checkQuantityNumberSuccess () {

			businessNameFld = $ ( "#businessNameFld" );
			businessName = $.trim ( $ ( '#businessNameFld' ).val () );

			emailOfUserFld = $ ( "#emailOfUserFld" );
			emailOfUser = $.trim ( $ ( '#emailOfUserFld' ).val () );

			itemSelected = $ ( "#itemFld option:selected" ).text ();
			itemSubCategory = $ ( "#itemFld option:selected" ).text ();
			itemPrice = $ ( '#itemPriceFld' ).val ();
			var urlGET = "/api/business/pos/successResponse";

			$.ajax ( {
				type : "GET" ,
				url : urlGET ,
				async : false ,
				success : function ( result ) {
					$.each ( result , function ( i , errorSuccess ) {
						if ( errorSuccess.message === 'The number is ok in English format' ) {
							// console.log('OK');
							// alert("OK");
							// savePricingInfo();
							// notify("Please wait for a while...", 1);
							addToCart ();
						}
						else
							if ( errorSuccess.message === 'The number is NOT ok in English format' ) {
								alert ( "This number format is incorrect for the English number formatting" );
								
								return;
							}

						if ( errorSuccess.message === 'The number is ok in German format' ) {
							// console.log('OK');
							// alert("OK");
							// savePricingInfo();
							addToCart ();
						}
						else
							if ( errorSuccess.message === 'The number is NOT ok in German format' ) {
								alert ( "This number format is incorrect for the German number formatting" );
								return;
							}

						if ( errorSuccess.message === 'The number is ok in French format' ) {
							// console.log('OK');
							// alert("OK");
							// savePricingInfo();
							addToCart ();
						}
						else
							if ( errorSuccess.message === 'The number is NOT ok in French format' ) {
								alert ( "This number format is incorrect for the French number formatting" );
								return;
							}

					} );

				} ,
				error : function ( e ) {
					alert ( "ERROR: " , e );
					console.log ( "ERROR: " , e );
				}
			} );
		}

		function populateTableWithin () {
			populateTable ();

			// $.getScript("pos_cash_populate_item_list_modal.js", function() {
			// populateTable();
			// });

			// window.history.pushState ( "object or string" , "Title" ,
			// "/business/pos/" + businessName + "/" + emailOfUser );
		}

		populateCartList ();
		getTotal ();
		checkAmountPaidInCorrectectness ();
		returnChangeToClient ();
	}
} ) ();



function popup ( title , message , hideOK , hideClose , OKText , closeText ) {

	var modal = $ ( '#myModal' );
	modal.find ( '.modal-body p i strong' ).text ( message );
	modal.find ( '.modal-header h4' ).html ( title );

	if ( hideOK === 'hideOk' ) {
		modal.find ( '.modal-footer #OK' ).hide ();
	}
	else
		if ( hideOK === 'showOk' ) {

			modal.find ( '.modal-footer #OK' ).text ( OKText );
			modal.find ( '.modal-footer #OK' ).show;

		}

	if ( hideClose === 'hideDismiss' ) {

		modal.find ( '.modal-footer #dismiss' ).hide ();

	}
	else
		if ( hideClose === 'showDismiss' ) {

			modal.find ( '.modal-footer #dismiss' ).text ( closeText );
			modal.find ( '.modal-footer #dismiss' ).show;

		}

	$ ( '#myModal' ).modal ( 'show' );

}



/* Adding items to cart */

function addToCart () {
	var numFormat = $ ( "#numberFormatFld option:selected" ).text ();

	itemQuantityStr = $ ( '#itemQtyObtainedFld' ).val ();
	var itemPriceStr = $ ( '#itemPriceObtainedFld' ).val ();
	var itemId = $ ( '#itemIDObtainedFld' ).val ();
	var itemName = $ ( '#itemNameObtainedFld' ).val ();

	var urlPOST = "/api/business/posCash/addItemToCart?numberFormat=" + numFormat;

	var cartList = {
		"item" : itemName ,
		"itemId" : itemId ,
		"itemQuantityStr" : itemQuantityStr ,
		"unitCostStr" : itemPriceStr
	}

	$.ajax ( {
		url : urlPOST ,
		// cache : false,
		type : 'POST' ,
		data : JSON.stringify ( cartList ) ,
		// data : $('#choose_business_form').serialize(),
		contentType : 'application/json' ,
		dataType : 'json' ,
		async : false ,
		success : function ( data , textStatus , xhr ) {
			console.log ( xhr.status );
			// alert(xhr.status);
			// refreshTable();
		} ,
		complete : function ( xhr , textStatus ) {
			console.log ( xhr.status );

			
			if ( xhr.status !== 200 ) {
				// alert(xhr.status);
				console.log ( xhr.status );
			}
			else
				if ( xhr.status === 200 ) {
			
					// alert(xhr.status);
					populateCartList ();
					getTotal ();
					checkAmountPaidInCorrectectness ();
					returnChangeToClient ();
					console.log ( xhr.status );
					// refreshTableOnNumberFormatchanged();
					// refreshTableOnSave();
					// refreshTable();
					// clearFields();
				}

		}

	} );

}

function notify ( message , status ) {
	$ ( '.kdnotification-title' ).html ( message );
	funcking ();
	if ( status == 1 ) {
		$ ( '#notification' ).css ( {
			'background-color' : 'rgba(0,0,0,.4)'
		} ).fadeIn ( 'fast' ).delay ( 2000 ).fadeOut ( 'slow' );
	}
	else {
		$ ( '#notification' ).css ( {
			'background-color' : 'rgba(216,0,12,.6)'
		} ).fadeIn ( 'slow' ).delay ( 2000 ).fadeOut ( 'slow' );
	}
}

function funcking () {
	var kd = $ ( '.kdnotification' );
	var viewportHeight = $ ( window ).height () , viewportWidth = $ ( window ).width () , kdheight = kd.height () , kdwidth = kd.width () , hdiff = viewportHeight - kdheight , vdiff = viewportWidth
			- kdwidth , left = vdiff / 2 , top = hdiff / 2;
	kd.css ( {
		'top' : top + 'px' ,
		'left' : left + 'px'
	} );
}

function subtractAnItemFromCart ( itemId ) {

	// var urlSubtractAnItem = "/api/business/posCash/subtractItemFromCart";

	var numFormat = $ ( "#numberFormatFld option:selected" ).text ();

	var urlPOST = "/api/business/posCash/subtractItemFromCart?numberFormat=" + numFormat;

	var cartList = {
		"itemId" : itemId
	}

	$.ajax ( {
		url : urlPOST ,
		// cache : false,
		type : 'POST' ,
		data : JSON.stringify ( cartList ) ,
		// data : $('#choose_business_form').serialize(),
		contentType : 'application/json' ,
		dataType : 'json' ,
		async : false ,
		success : function ( data , textStatus , xhr ) {
			console.log ( xhr.status );
		} ,
		complete : function ( xhr , textStatus ) {
			console.log ( xhr.status );
			if ( xhr.status !== 200 ) {
				console.log ( xhr.status );
			}
			else
				if ( xhr.status === 200 ) {
					populateCartList ();
					getTotal ();
					checkAmountPaidInCorrectectness ();
					returnChangeToClient ();
					console.log ( xhr.status );
				}

		}

	} );

}

( function () {

	jQuery ( init );

	var unit;

	function init () {

		selectingToAddToSalesList ();

		function selectingToAddToSalesList () {
			$ ( "#cartListTable" ).on ( 'click' , '#btnSubtractItemFromCart' , function () {

				// get the current row
				var currentRow = $ ( this ).closest ( "tr" );

				// itemId
				var itemId = currentRow.find ( "td:eq(0)" ).text ();

				subtractAnItemFromCart ( itemId );

			} );

		}

	}

} ) ();

function increaseAnItemInCart ( itemId ) {

	var numFormat = $ ( "#numberFormatFld option:selected" ).text ();

	var urlPOST = "/api/business/posCash/increaseItemInCart?numberFormat=" + numFormat;

	var cartList = {
		"itemId" : itemId
	}

	$.ajax ( {
		url : urlPOST ,
		// cache : false,
		type : 'POST' ,
		data : JSON.stringify ( cartList ) ,
		// data : $('#choose_business_form').serialize(),
		contentType : 'application/json' ,
		dataType : 'json' ,
		async : false ,
		success : function ( data , textStatus , xhr ) {
			console.log ( xhr.status );
			// alert(xhr.status);
			// refreshTable();
		} ,
		complete : function ( xhr , textStatus ) {
			console.log ( xhr.status );
			if ( xhr.status !== 200 ) {
				// alert(xhr.status);
				console.log ( xhr.status );
			}
			else
				if ( xhr.status === 200 ) {
					// alert(xhr.status);
					populateCartList ();
					getTotal ();
					checkAmountPaidInCorrectectness ();
					returnChangeToClient ();
					console.log ( xhr.status );
				}

		}

	} );

}

( function () {

	jQuery ( init );

	var unit;

	function init () {

		selectingToAddToSalesList ();

		function selectingToAddToSalesList () {
			$ ( "#cartListTable" ).on ( 'click' , '#btnIncreaseItemInCart' , function () {

				// get the current row
				var currentRow = $ ( this ).closest ( "tr" );

				// itemId
				var itemId = currentRow.find ( "td:eq(0)" ).text ();
				// $("#itemIDObtainedFld").val(itemId);
				// alert("from cart: " + itemId);

				increaseAnItemInCart ( itemId );

			} );

		}

	}

} ) ();

function removeItemFromCart ( itemId ) {

	var numFormat = $ ( "#numberFormatFld option:selected" ).text ();

	var urlPOST = "/api/business/posCash/removeItemFromCart?numberFormat=" + numFormat;

	var cartList = {
		"itemId" : itemId
	}

	$.ajax ( {
		url : urlPOST ,
		// cache : false,
		type : 'POST' ,
		data : JSON.stringify ( cartList ) ,
		// data : $('#choose_business_form').serialize(),
		contentType : 'application/json' ,
		dataType : 'json' ,
		async : false ,
		success : function ( data , textStatus , xhr ) {
			console.log ( xhr.status );
			// alert(xhr.status);
			// refreshTable();
		} ,
		complete : function ( xhr , textStatus ) {
			console.log ( xhr.status );
			if ( xhr.status !== 200 ) {
				// alert(xhr.status);
				console.log ( xhr.status );
			}
			else
				if ( xhr.status === 200 ) {
					// alert(xhr.status);
					populateCartList ();
					getTotal ();
					checkAmountPaidInCorrectectness ();
					returnChangeToClient ();
					console.log ( xhr.status );
				}

		}

	} );

}

( function () {

	jQuery ( init );

	var unit;

	function init () {

		selectingToAddToSalesList ();

		function selectingToAddToSalesList () {
			$ ( "#cartListTable" ).on ( 'click' , '#btnRemoveItemFromCart' , function () {

				// get the current row
				var currentRow = $ ( this ).closest ( "tr" );

				// itemId
				var itemId = currentRow.find ( "td:eq(0)" ).text ();
				// $("#itemIDObtainedFld").val(itemId);
				// alert("from cart: " + itemId);

				removeItemFromCart ( itemId );

			} );

		}

	}

} ) ();

function populateCartList () {

	var numFormat = $ ( "#numberFormatFld option:selected" ).text ();

	var urlGETCartList = "/api/business/posCash/cartList?numberFormat=" + numFormat;

	var tBody = $ ( '#cartListTable' ).children ( 'tbody' );
	tBody.empty ();

	$.ajax ( {
		type : "GET" ,
		url : urlGETCartList ,
		success : function ( result ) {
			$.each ( result , function ( i , cartList ) {
				console.log ( "ID: " + cartList.itemId );

				// index 0
				/* 1 */
				// Item ID
				var tr = $ ( '<tr>' );
				var td = $ ( '<td hidden="hidden">' );
				td.append ( cartList.itemId );
				tr.append ( td );
				tBody.append ( tr );

				// index 1
				/* 1 */
				// item name
				var td = $ ( '<td>' );
				td.append ( cartList.item );
				tr.append ( td );

				// index 2
				/* 2 */
				// item name plus its sub category
				var td = $ ( '<td>' );
				td.append ( cartList.itemQuantityStr );
				tr.append ( td );

				// index 3
				/* 3 Item sub category hidden */
				// item sub category only
				var td = $ ( '<td>' );
				td.append ( cartList.unitCostStr );
				tr.append ( td );

				// index 4
				/* 4 */
				// item price
				var td = $ ( '<td>' );
				td.append ( cartList.costOfItemStr );
				tr.append ( td );

				// index 5
				/* 5 */
				// a href='javascript:void(0);' class='remove'><span
				// class='glyphicon glyphicon-remove'></span></a>
				var td = $ ( '<td><button id="btnIncreaseItemInCart" type="button"><i class="fa fa-arrow-circle-up fa-lg"></button></td>' );
				tr.append ( td );
				// index 6
				/* 6 */
				var td = $ ( '<td><button id="btnSubtractItemFromCart" type="button"><i class="fa fa-arrow-circle-down fa-lg"></button></td>' );
				tr.append ( td );

				// index 7
				/* unit of measurement hidden */
				var td = $ ( '<td><button id="btnRemoveItemFromCart" type="button"><i class="fa fa-remove fa-lg"></button></td>' );
				tr.append ( td );
				// index 7
				/* button to add the selected item to cart */
				// var td = $('<td><input type="button" value="Add"
				// id="btnAddToSalesList" class="btn btn-default" /></td>');
				// tr.append(td);
				tBody.append ( tr );

			} );
			$ ( "#itemListTable tbody tr:odd" ).addClass ( "info" );
			$ ( "#itemListTable tbody tr:even" ).addClass ( "success" );
		}
	} );
	// window.history.pushState("object or string", "Title", "/business/pos/" +
	// businessName + "/" + emailOfUser);
}

var totalToHelpInCheckOut;


function getTotal () {

	var numFormat = $ ( "#numberFormatFld option:selected" ).text ();

	var urlGETCartList = "/api/business/posCash/total?numberFormat=" + numFormat;

	$.ajax ( {
		type : "GET" ,
		url : urlGETCartList ,
		success : function ( result ) {
			$.each ( result , function ( i , cartList ) {
				console.log ( "ID tot: " + cartList.itemId );
				console.log ( "total: " + cartList.totalCost );

				totalToHelpInCheckOut = cartList.totalCost;
				console.log('tot to help: ' + totalToHelpInCheckOut);
				
				$ ( "#totalFld" ).text ( cartList.totalCostStr );

			} );
		}
	} );
	// window.history.pushState("object or string", "Title", "/business/pos/" +
	// businessName + "/" + emailOfUser);
}

function returnChangeToClient () {

	var numFormat = $ ( "#numberFormatFld option:selected" ).text ();

	var urlGETCartList = "/api/business/posCash/returnChangeToClient?numberFormat=" + numFormat;

	$.ajax ( {
		type : "GET" ,
		url : urlGETCartList ,
		success : function ( result ) {
			$.each ( result , function ( i , cartList ) {
				console.log ( "change: " + cartList.changeStr );

				$ ( "#changeFld" ).text ( cartList.changeStr );

			} );
		}
	} );
	// window.history.pushState("object or string", "Title", "/business/pos/" +
	// businessName + "/" + emailOfUser);
}

function checkAmountPaidInCorrectectness () {

	businessNameFld = $ ( "#businessNameFld" );
	businessName = $.trim ( $ ( '#businessNameFld' ).val () );

	emailOfUserFld = $ ( "#emailOfUserFld" );
	emailOfUser = $.trim ( $ ( '#emailOfUserFld' ).val () );

	var numFormat = $ ( "#numberFormatFld option:selected" ).text ();

	var urlPOST = "/api/business/posCash/" + businessName + "/" + emailOfUser + "/checkAmountPaidInCorrectness?numberFormat=" + numFormat;

	var amountPaidStr = $ ( '#amountPaidFld' ).val ();

	var cartList = {
		"amountPaidInStr" : amountPaidStr
	}

	// alert("qty: " + itemQuantityStr);

	$.ajax ( {
		url : urlPOST ,
		// cache : false,
		type : 'POST' ,
		data : JSON.stringify ( cartList ) ,
		// data : $('#choose_business_form').serialize(),
		contentType : 'application/json' ,
		dataType : 'json' ,
		async : false ,
		success : function ( data , textStatus , xhr ) {
			console.log ( xhr.status );
			// alert(xhr.status);
			// refreshTable();
			checkAmountPaidNumberSuccess ();
		} ,
		complete : function ( xhr , textStatus ) {
			if ( xhr.status !== 200 ) {
				// alert(xhr.status);
				console.log ( xhr.status );
				checkAmountPaidNumberSuccess ();
			}
			else
				if ( xhr.status === 200 ) {
					// alert(xhr.status);
					console.log ( xhr.status );
					checkAmountPaidNumberSuccess ();
				}

		}

	} );

}

function checkAmountPaidNumberSuccess () {

	var urlGET = "/api/business/pos/successResponse";

	$.ajax ( {
		type : "GET" ,
		url : urlGET ,
		async : false ,
		success : function ( result ) {
			$.each ( result , function ( i , errorSuccess ) {
				if ( errorSuccess.message === 'The number is ok in English format' ) {
					// console.log('OK');
					// alert("OK");
					getAmountPaidFromClient ();
				}
				else
					if ( errorSuccess.message === 'The number is NOT ok in English format' ) {
						alert ( "This number format is incorrect for the English number formatting" );
						return;
					}

				if ( errorSuccess.message === 'The number is ok in German format' ) {
					// console.log('OK');
					// alert("OK");
					getAmountPaidFromClient ();
				}
				else
					if ( errorSuccess.message === 'The number is NOT ok in German format' ) {
						alert ( "This number format is incorrect for the German number formatting" );
						return;
					}

				if ( errorSuccess.message === 'The number is ok in French format' ) {
					// console.log('OK');
					// alert("OK");
					getAmountPaidFromClient ();
				}
				else
					if ( errorSuccess.message === 'The number is NOT ok in French format' ) {
						alert ( "This number format is incorrect for the French number formatting" );
						return;
					}

			} );

		} ,
		error : function ( e ) {
			// alert("ERROR: ", e);
			// console.log("ERROR: ", e);
		}
	} );
}

function getAmountPaidFromClient () {

	var numFormat = $ ( "#numberFormatFld option:selected" ).text ();
	var urlPOST = "/api/business/posCash/getAmountPaidFromClient?numberFormat=" + numFormat;

	var amountPaidStr = $ ( '#amountPaidFld' ).val ();

	if ( amountPaidStr === '' ) {
		amountPaidStr = 0;
		$ ( "#amountPaidFld" ).val ( "0" );
	}

	var amountPaidStr = $ ( '#amountPaidFld' ).val ();

	var cartList = {
		"amountPaidInStr" : amountPaidStr
	}

	$.ajax ( {
		url : urlPOST ,
		// cache : false,
		type : 'POST' ,
		data : JSON.stringify ( cartList ) ,
		// data : $('#choose_business_form').serialize(),
		contentType : 'application/json' ,
		dataType : 'json' ,
		async : false ,
		success : function ( data , textStatus , xhr ) {
			console.log ( xhr.status );
			// alert(xhr.status);
			// refreshTable();
		} ,
		complete : function ( xhr , textStatus ) {
			console.log ( xhr.status );
			if ( xhr.status !== 200 ) {
				// alert(xhr.status);
				console.log ( xhr.status );
			}
			else
				if ( xhr.status === 200 ) {
					// alert(xhr.status);
					console.log ( xhr.status );
				}

		}

	} );

}



function verifyChange () {
	
	getTotal();
	populateCartList();
	checkAmountPaidInCorrectectness ();/*
										 * This function has to come before the
										 * returning of change to the user as
										 * done here
										 */
	returnChangeToClient();

	
	
	if ( totalToHelpInCheckOut === '' || totalToHelpInCheckOut === '0' ) {
		popup ( 'empty cart' , 'Please add items to cart before you can sell' , 'hideOk' , 'showDismiss' , '' , 'close' );
		return;
	}
	
	var urlVerify = "/api/business/posCash/verifyChange";
	
	var emailOrTelephoneNumberOfCustomer = $ ( '#telOrEmailFld' ).val ();
	if(emailOrTelephoneNumberOfCustomer === ''){
		popup ( 'empty telelphone number or email' , 'please specify customer\'s phone number or email' , 'hideOk' , 'showDismiss' , '' , 'close' );
		return;
	 }
	

	$.ajax ( {
		type:'GET',
		url : urlVerify ,
		async : false ,
		success : function ( result ) {
			$.each ( result , function ( i , errorSuccess ) {
				if ( errorSuccess.message === 'successful' ) {
					 console.log('OK');
					 checkIfCustomerEntryIsTelOrEmail();
				}
				else
					if ( errorSuccess.message === 'Amount paid is less, transaction will not complete successfully' ) {
// alert ( "Amount paid is less, transaction will not complete successfully" );
						popup ( 'Transaction error' , 'The customer has paid less than expected. This transaction can not proceed to completion' , 'hideOk' , 'showDismiss' , '' , 'close' );
						return;
					}
			} );
		} ,
		error : function ( e ) {
			alert ( "ERROR: " , e );
			console.log ( "ERROR: " , e );
		}
	} );
}

function showTheListOfBusinessesOrAccountsFromWhichOneAccountIsToBeChosenForCheck() {
	$ ( '#checkOutModal' ).modal ( 'show' );
}

function checkIfCustomerEntryIsTelOrEmail() {
	var urlCheck = "/api/business/posCash/checkIfCustomerEntryIsTelOrEmail";
	var telOrEmail = $("#telOrEmailFld").val();
	
	var emailsTels = {
			"email":telOrEmail
	}
	
	$.ajax({
		type:'POST',
		url:urlCheck,
		data : JSON.stringify ( emailsTels ) ,
		contentType : 'application/json' ,
		success : function ( data , textStatus , xhr ) {

			console.log ( 'post email or tel: ' + xhr.status );

		} ,

		complete : function ( xhr , textStatus ) {

			if ( xhr.status !== 200 ) {

				console.log ( 'post email or tel: ' + xhr.status );

			}
			else
				if ( xhr.status === 200 ) {

					console.log ( 'post validation of email: ' + xhr.status );

					returnCheckIfCustomerEntryIsTelOrEmail();

				}
		}
	} );
}

function returnCheckIfCustomerEntryIsTelOrEmail() {

	var urlCheckResult = "/api/business/posCash/returnCheckIfCustomerEntryIsTelOrEmail";

	$.ajax ( {
		type:'GET',
		url : urlCheckResult ,
		async : false ,
		success : function ( result ) {
			$.each ( result , function ( i , emailsPhoneNumbersAndNamesOfBusinesses ) {
				if ( emailsPhoneNumbersAndNamesOfBusinesses.email === 'it is an email from the customer' || emailsPhoneNumbersAndNamesOfBusinesses.email === 'it is a telephone number from the customer' ) {
					returnNamesOfBusinessesToTheUserBasingOnEntryOfCustomerEmailOrTelephone();
				}
			} );
		} ,
		error : function ( e ) {
			alert ( "ERROR: " , e );
			console.log ( "ERROR: " , e );
		}
	} );
}



function returnNamesOfBusinessesToTheUserBasingOnEntryOfCustomerEmailOrTelephone() {

	var urlNamesOfBusinesses = "/api/business/posCash/returnNamesOfBusinessesToTheUserBasingOnEntryOfCustomerEmailOrTelephone";

	$ ( '#inputCustomerBusinessName' ).empty();

	
	$.ajax ( {
		type:'GET',
		url : urlNamesOfBusinesses ,
		async : false ,
		success : function ( result ) {
			$.each ( result , function ( i , emailsPhoneNumbersAndNamesOfBusinesses ) {
				$ ( '#inputCustomerBusinessName' ).append ( '<option value=' + emailsPhoneNumbersAndNamesOfBusinesses.businessName + '>' + emailsPhoneNumbersAndNamesOfBusinesses.businessName + '</option>' );
			} );
		} ,
		error : function ( e ) {
			alert ( "ERROR: " , e );
			console.log ( "ERROR: " , e );
		}
	} );
	
	showTheListOfBusinessesOrAccountsFromWhichOneAccountIsToBeChosenForCheck();

}


function validateEmailLogin () {
	
	if(tBody.em)
	
	var urlPOST = "/api/login/validateEmail";
	
	var email = $ ( '#telOrEmailFld' ).val ();

	if ( email === '' ) {

		popup ( 'empty email' , 'please specify your email' , 'hideOk' , 'showDismiss' , '' , 'close' );

		return;

	}

	var signUp = {
		"email" : email
	}

	$.ajax ( {

		url : urlPOST ,
		type : 'POST' ,
		data : JSON.stringify ( signUp ) ,
		contentType : 'application/json' ,
		dataType : 'json' ,
		async : false ,
		success : function ( data , textStatus , xhr ) {

			console.log ( 'post validation of email: ' + xhr.status );

		} ,

		complete : function ( xhr , textStatus ) {

			if ( xhr.status !== 200 ) {

				console.log ( "post validation of email: " + xhr.status );

				returnEmailValidationMessage ();

			}
			else
				if ( xhr.status === 200 ) {

					console.log ( 'post validation of email: ' + xhr.status );

					// checkIfLoginEmailExists ();

					returnEmailValidationMessage ();

				}

		}

	} );

}
