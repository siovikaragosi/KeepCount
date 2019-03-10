( function () {
	jQuery ( init );

	var businessNameFld;
	var businessName;

	var emailOfUserFld;
	var emailOfUser;

	var btnShowItemList;

	function init () {

		setURL ();
		// populateTable();
		refreshNumberFormatList ();

		btnShowItemList = $ ( "#btnShowItemList" );
		// btnShowItemList.click(populateTable);

		function setURL () {
			businessNameFld = $ ( "#businessNameFld" );
			businessName = $.trim ( $ ( '#businessNameFld' ).val () );

			emailOfUserFld = $ ( "#emailOfUserFld" );
			emailOfUser = $.trim ( $ ( '#emailOfUserFld' ).val () );

			window.history.pushState ( "object or string" , "Title" , "/business/pos/" + businessName + "/" + emailOfUser );
		}

		function refreshNumberFormatList () {
			var urlGETNumberFormat = "/api/business/purchases/numberFormats";

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

	}
} ) ();

function populateTable () {
	businessNameFld = $ ( "#businessNameFld" );
	businessName = $.trim ( $ ( '#businessNameFld' ).val () );

	emailOfUserFld = $ ( "#emailOfUserFld" );
	emailOfUser = $.trim ( $ ( '#emailOfUserFld' ).val () );

	// var numFormat = $("#numberFormatFld option:selected").text();
	// "/api/business/pos/{businessName}/{emailOfUser}/allItems"
	var urlGETPricings = "/api/business/pos/" + businessName + "/" + emailOfUser + "/allAddToCarts";
	// "?numberFormat=" + numFormat;

	var tBody = $ ( '#itemListTable' ).children ( 'tbody' );
	tBody.empty ();

	$.ajax ( {
		type : "GET" ,
		url : urlGETPricings ,
		success : function ( result ) {
			$.each ( result , function ( i , addToCart ) {
				console.log ( "ID: " + addToCart.itemId );
				console.log ( "item: " + addToCart.item );
				// index 0
				// index 0
				/* 1 */
				// Item ID
				var tr = $ ( '<tr>' );
				var td = $ ( '<td hidden="hidden">' );
				td.append ( addToCart.itemId );
				tr.append ( td );
				tBody.append ( tr );

				// index 1
				/* 1 */
				// item name
				var td = $ ( '<td hidden="hidden">' );
				td.append ( addToCart.item );
				tr.append ( td );

				// index 2
				/* 2 */
				// item name plus its sub category
				var td = $ ( '<td>' );
				td.append ( addToCart.item + '\n' + '(' + addToCart.itemSubCategory + ')' );
				tr.append ( td );

				// index 3
				/* 3 Item sub category hidden */
				// item sub category only
				var td = $ ( '<td hidden="hidden">' );
				td.append ( addToCart.itemSubCategory );
				tr.append ( td );

				// index 4
				/* 4 */
				// item price
				var td = $ ( '<td hidden="hidden">' );
				td.append ( addToCart.price );
				tr.append ( td );

				// index 5
				/* 5 */
				var td = $ ( '<td><input id="itemQtyFld" name="itemPriceFld" type="text" placeholder="Quantity" class="form-control" required="required" /></td>' );
				tr.append ( td );

				// index 6
				/* 6 */
				var td = $ ( '<td hidden="hidden">' );
				td.append ( addToCart.itemId );
				tr.append ( td );

				// index 7
				/* unit of measurement hidden */
				var td = $ ( '<td hidden="hidden">' );
				td.append ( addToCart.unitOfMeasurement );
				tr.append ( td );

				// index 7
				/* button to add the selected item to cart */
				var td = $ ( '<td><button type="button" id="btnAddToSalesList" ><i class="fa fa-cart-plus fa-lg"></button></td>' );
				tr.append ( td );

				// var td = $ ( '<td><button id="btnIncreaseItemInCart" type="button"><i class="fa fa-caret-square-o-up"></button></td>' );
				// tr.append ( td );

				tBody.append ( tr );

			} );
			$ ( "#itemListTable tbody tr:odd" ).addClass ( "info" );
			$ ( "#itemListTable tbody tr:even" ).addClass ( "success" );
		}
	} );
	window.history.pushState ( "object or string" , "Title" , "/business/pos/" + businessName + "/" + emailOfUser );
}
