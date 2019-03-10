( function () {

	jQuery ( init );

	var unit;

	function init () {

		selectingToAddToSalesList ();

		function selectingToAddToSalesList () {
			$ ( "#itemListTable" ).on ( 'click' , '#btnAddToSalesList' , function () {

				// get the current row
				var currentRow = $ ( this ).closest ( "tr" );

				// itemId
				var itemId = currentRow.find ( "td:eq(0)" ).text ();
				$ ( "#itemIDObtainedFld" ).val ( itemId );

				// item name + (sub category)
				var col1 = currentRow.find ( "td:eq(1)" ).text ();
				$ ( "#itemNameObtainedFld" ).val ( col1 );

				// sub category only
				var col2 = currentRow.find ( "td:eq(2)" ).text ();

				// price
				var col3 = currentRow.find ( "td:eq(3)" ).text ();

				var col4 = currentRow.find ( "td:eq(4)" ).text ();
				$ ( "#itemPriceObtainedFld" ).val ( col4 );

				// Quantity
				var qty = currentRow.find ( "input" ).val ();
				$ ( "#itemQtyObtainedFld" ).val ( qty );

				var colItemId = currentRow.find ( "tq:eq(5)" ).text ();

				// unit of measurement
				var col6 = currentRow.find ( "td:eq(6)" ).text ();
				// unitOfMeasurement = col7;

				var col7 = currentRow.find ( "td:eq(7)" ).text ();

				var col9 = currentRow.find ( "td:eq(8)" ).text ();

			} );

		}

	}

} ) ();