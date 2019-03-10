(function() {
	jQuery(init);

	var urlGET;

	var businessNameFld;
	var businessName;

	var emailOfUserFld;
	var emailOfUser;

	var itemNameFld;
	var itemName;

	var itemFld;
	var itemSelected;

	var itemSubCategoryFld;

	var btnSavePricingChanges;
	var numberFormatObtained;
	var colItemSubCategory;

	function init() {

		// buttonUpdateClickableOrNot();
		// buttonSaveClickableOrNot();

		allowNumbersFormat();

		// refreshPricingTable();

		selectingTableRowForEditing();
		tableHeaderScroll();

		btnSavePricingChanges = $("#btnSavePricingChanges");
		btnSavePricingChanges.click(checkPriceCorrectectnessUpdate);

		function tableHeaderScroll() {
			$(window).scroll(function() {
				if ($(this).scrollTop()) {
					$('#toTop').fadeIn();
				} else {
					$('#toTop').fadeOut();
				}
			});

			$("#toTop").click(function() {
				// 1 second of animation time
				// html works for FFX but not Chrome
				// body works for Chrome but not FFX
				// This strange selector seems to work universally
				$("html, body").animate({
					scrollTop : 0
				}, 1000);
			});
		}

		function selectingTableRowForEditing() {
			$("#createPricingTable").on('click', '.btnSelect', function() {
				// '.btnSelect'
				alert("test");

				// get the current row
				var currentRow = $(this).closest("tr");

				// get current row, item ID
				var colPriceId = currentRow.find("td:eq(0)").text();
				alert(colPriceId);

				var colDateServer = currentRow.find("td:eq(1)").text();

				var colDateClient = currentRow.find("td:eq(2)").text();
				alert("Date Client: " + colDateClient);

				// get current row, item name
				var colItemPrice = currentRow.find("td:eq(3)").text();
				alert("price: " + colItemPrice);

				// get current row, item sub category
				var colItemId = currentRow.find("td:eq(4)").text();
				alert("sub: " + colItemId);

				// get current row, item quantity
				colItemSubCategory = currentRow.find("td:eq(5)").text();
				// alert("sub: " + colItemQuantity);

				if (!confirm('You are choosing this row for editing.\nAre you sure about this?'))
					return;

				/* Select by description for jQuery v1.6+: */
				// var text1 = colItemSubCategory;
				// $("#itemFld option").filter(function() {
				// // may want to use $.trim in here
				// return $(this).text() == text1;
				// }).prop('selected', true);
				/* Select by description for jQuery v1.6+: */

				// $('#itemCreditFld').attr('src', colSamplePhotoOne);
				$("#itemPriceFld").val(colItemPrice);
				$("#itemPriceIdFld").val(colPriceId);
				$("#itemIdFld").val(colItemId);
				$("#dateClient").val(colDateClient);
				$("#dateServer").val(colDateServer);

				sendIdSearchable();

			});
			// getNameOfItemForEditing();

		}

		function allowNumbersFormat() {

			$('#itemPriceFld').keypress(function(event) {
				if ((event.which > 47 && event.which < 58)
				// back space
				|| (event.which == 8)
				// comma
				|| (event.which == 44)
				// decimal point
				|| (event.which == 46) ||
				// space
				(event.which == 32)) {

					return true;
				} else {
					event.preventDefault();
					alert('incorrect character');
				}
			}).on('paste', function(event) {
				event.preventDefault();
			});

		}

		function checkPriceNumberSuccess() {

			businessNameFld = $("#businessNameFld");
			businessName = $.trim($('#businessNameFld').val());

			emailOfUserFld = $("#emailOfUserFld");
			emailOfUser = $.trim($('#emailOfUserFld').val());

			itemSelected = $("#itemFld option:selected").text();
			itemSubCategory = $("#itemFld option:selected").text();
			itemPrice = $('#itemPriceFld').val();
			var urlGET = "/api/business/pricing/successResponse";

			$.ajax({
				type : "GET",
				url : urlGET,
				async : false,
				success : function(result) {
					$.each(result, function(i, errorSuccess) {
						if (errorSuccess.message === 'The number is ok in English format') {
							// console.log('OK');
							// alert("OK");
							savePricingInfo();
						} else if (errorSuccess.message === 'The number is NOT ok in English format') {
							alert("This number format is incorrect for the English number formatting");
							return;
						}

						if (errorSuccess.message === 'The number is ok in German format') {
							// console.log('OK');
							// alert("OK");
							savePricingInfo();
						} else if (errorSuccess.message === 'The number is NOT ok in German format') {
							alert("This number format is incorrect for the German number formatting");
							return;
						}

						if (errorSuccess.message === 'The number is ok in French format') {
							// console.log('OK');
							// alert("OK");
							savePricingInfo();
						} else if (errorSuccess.message === 'The number is NOT ok in French format') {
							alert("This number format is incorrect for the French number formatting");
							return;
						}

					});

				},
				error : function(e) {
					alert("ERROR: ", e);
					console.log("ERROR: ", e);
				}
			});
		}

		function checkPriceCorrectectness() {

			businessNameFld = $("#businessNameFld");
			businessName = $.trim($('#businessNameFld').val());

			emailOfUserFld = $("#emailOfUserFld");
			emailOfUser = $.trim($('#emailOfUserFld').val());

			itemSelected = $("#itemFld option:selected").text();
			itemSubCategory = $("#itemFld option:selected").text();
			itemPrice = $('#itemPriceFld').val();

			var numFormat = $("#numberFormatFld option:selected").text();
			var urlPOST = "/api/business/pricing/" + businessName + "/" + emailOfUser + "/checkPriceCorrectness?numberFormat=" + numFormat;

			var price = {
				"priceStr" : itemPrice
			}

			$.ajax({
				url : urlPOST,
				// cache : false,
				type : 'POST',
				data : JSON.stringify(price),
				// data : $('#choose_business_form').serialize(),
				contentType : 'application/json',
				dataType : 'json',
				async : false,
				success : function(data, textStatus, xhr) {
					// console.log(xhr.status);
					// alert(xhr.status);
					// refreshTable();
					checkPriceNumberSuccess();
				},
				complete : function(xhr, textStatus) {
					console.log(xhr.status);
					if (xhr.status !== 200) {
						// alert(xhr.status);
						// console.log(xhr.status);
						checkPriceNumberSuccess();
					} else if (xhr.status === 200) {
						// alert(xhr.status);
						console.log(xhr.status);
						checkPriceNumberSuccess();
						// refreshTableOnNumberFormatchanged();
						// refreshTableOnSave();
						// refreshTable();
						// clearFields();
					}

				}

			});

		}

		function sendIdSearchable() {
			var idSearchable = $("#itemIdFld").val();

			if (idSearchable === '') {
				return;
			}

			var urlPOST = "/api/business/pricing/sendIdSearchableForEditing?idSearchable=" + idSearchable;

			var item = {
				"id" : idSearchable
			}

			alert("id item: " + idSearchable)

			$.ajax({
				url : urlPOST,
				// cache : false,
				type : 'POST',
				data : JSON.stringify(item),
				// data : $('#choose_business_form').serialize(),
				contentType : 'application/json',
				dataType : 'json',
				async : false,
				success : function(data, textStatus, xhr) {
					console.log(xhr.status);
					alert(xhr.status);
					// refreshTable();
				},
				complete : function(xhr, textStatus) {
					console.log(xhr.status);
					if (xhr.status !== 200) {
						alert(xhr.status);
						console.log(xhr.status);
					} else if (xhr.status === 200) {
						alert(xhr.status);
						console.log(xhr.status);
						getNameOfItemForEditing();
						// refreshTableOnNumberFormatchanged();
						// refreshTableOnSave();
						// refreshTable();
						// clearFields();
					}

				}

			});

		}

		function getNameOfItemForEditing() {

			var urlGET = "/api/business/pricing/getNameOfItemForEditing";

			$.ajax({
				type : "GET",
				url : urlGET,
				async : false,
				success : function(result) {
					$.each(result, function(i, item) {

						alert("ID: " + item.id);
						alert("item name: " + item.itemName)
						console.log("item ID: " + item.id);
						alert("sub: .." + colItemSubCategory);
						var selectedText = item.itemName + " -- (" + colItemSubCategory + ")";

						/* Select by description for jQuery v1.6+: */
						var text1 = selectedText;
						$("#itemFld option").filter(function() {
							// may want to use $.trim in here
							return $(this).text() == text1;
						}).prop('selected', true);

					});

				},
				error : function(e) {
					alert("ERROR: ", e);
					console.log("ERROR: ", e);
				}
			});
		}

		function checkPriceCorrectectnessUpdate() {

			businessNameFld = $("#businessNameFld");
			businessName = $.trim($('#businessNameFld').val());

			emailOfUserFld = $("#emailOfUserFld");
			emailOfUser = $.trim($('#emailOfUserFld').val());

			itemSelected = $("#itemFld option:selected").text();
			itemSubCategory = $("#itemFld option:selected").text();
			itemPrice = $('#itemPriceFld').val();

			var numFormat = $("#numberFormatFld option:selected").text();
			var urlPOST = "/api/business/pricing/" + businessName + "/" + emailOfUser + "/checkPriceCorrectness?numberFormat=" + numFormat;

			var price = {
				"priceStr" : itemPrice
			}

			$.ajax({
				url : urlPOST,
				// cache : false,
				type : 'POST',
				data : JSON.stringify(price),
				// data : $('#choose_business_form').serialize(),
				contentType : 'application/json',
				dataType : 'json',
				async : false,
				success : function(data, textStatus, xhr) {
					// console.log(xhr.status);
					// alert(xhr.status);
					// refreshTable();
					checkPriceNumberSuccessUpdate();
				},
				complete : function(xhr, textStatus) {
					console.log(xhr.status);
					if (xhr.status !== 200) {
						// alert(xhr.status);
						// console.log(xhr.status);
						checkPriceNumberSuccessUpdate();
					} else if (xhr.status === 200) {
						// alert(xhr.status);
						console.log(xhr.status);
						checkPriceNumberSuccessUpdate();
						// refreshTableOnNumberFormatchanged();
						// refreshTableOnSave();
						// refreshTable();
						// clearFields();
					}

				}

			});

		}

		function checkPriceNumberSuccessUpdate() {

			businessNameFld = $("#businessNameFld");
			businessName = $.trim($('#businessNameFld').val());

			emailOfUserFld = $("#emailOfUserFld");
			emailOfUser = $.trim($('#emailOfUserFld').val());

			itemSelected = $("#itemFld option:selected").text();
			itemSubCategory = $("#itemFld option:selected").text();
			itemPrice = $('#itemPriceFld').val();
			var urlGET = "/api/business/pricing/successResponse";

			$.ajax({
				type : "GET",
				url : urlGET,
				async : false,
				success : function(result) {
					$.each(result, function(i, errorSuccess) {
						if (errorSuccess.message === 'The number is ok in English format') {
							// console.log('OK');
							// alert("OK");
							updatePricingInfo();
						} else if (errorSuccess.message === 'The number is NOT ok in English format') {
							alert("This number format is incorrect for the English number formatting");
							return;
						}

						if (errorSuccess.message === 'The number is ok in German format') {
							// console.log('OK');
							// alert("OK");
							updatePricingInfo();
						} else if (errorSuccess.message === 'The number is NOT ok in German format') {
							alert("This number format is incorrect for the German number formatting");
							return;
						}

						if (errorSuccess.message === 'The number is ok in French format') {
							// console.log('OK');
							// alert("OK");
							updatePricingInfo();
						} else if (errorSuccess.message === 'The number is NOT ok in French format') {
							alert("This number format is incorrect for the French number formatting");
							return;
						}

					});

				},
				error : function(e) {
					alert("ERROR: ", e);
					console.log("ERROR: ", e);
				}
			});
		}

		function updatePricingInfo() {

			businessNameFld = $("#businessNameFld");
			businessName = $.trim($('#businessNameFld').val());

			emailOfUserFld = $("#emailOfUserFld");
			emailOfUser = $.trim($('#emailOfUserFld').val());

			itemSelected = $("#itemFld option:selected").text();
			itemSubCategory = $("#itemFld option:selected").text();
			itemPrice = $('#itemPriceFld').val();

			var dateClient = $('#dateClient').val();
			var dateServer = $('#dateServer').val();

			// alert("dc: " + dateClient);
			// alert("ds: " + dateServer);

			// return;

			var numFormat = $("#numberFormatFld option:selected").text();

			var id = $('#itemPriceIdFld').val();

			alert("id------: " + id);

			// return;

			/*
			 * private BigDecimal id; private String dateServer; private String
			 * dateClient; private BigDecimal price; private String priceStr;
			 * private BigDecimal itemId; private String itemSubCategory;
			 */

			var pricing = {
				// "dateServer" : dateServer,
				// "dateClient" : dateClient,
				// "id" : id,
				"priceStr" : itemPrice,
				"itemSubCategory" : itemSelected
			}

			// var urlPUT =
			//
			"/api/business/pricing/{businessName}/{emailOfUser}/updatePricing";
			var urlPUT = "/api/business/pricing/" + businessName + "/" + emailOfUser + "/updatePricing?id=" + id + "&numberFormat=" + numFormat;

			$.ajax({
				url : urlPUT,
				// cache : false,
				type : 'PUT',
				data : JSON.stringify(pricing),
				// data : $('#choose_business_form').serialize(),
				contentType : 'application/json',
				dataType : 'json',
				success : function(data, textStatus, xhr) {
					console.log(xhr.status);
					// alert(xhr.status);
					// refreshTable();
					// refreshPricingTable();
				},
				complete : function(xhr, textStatus) {
					console.log(xhr.status);
					if (xhr.status !== 200) {
						alert(xhr.status);
						console.log(xhr.status);
					} else if (xhr.status === 200) {
						// alert('you have successfully saved this item');
						refreshPricingTable();
						console.log(xhr.status);
						// refreshTableOnNumberFormatchanged();
						messageUponUpdatePrice();
						// refreshTableOnSave();
						// refreshTable();
						// clearFields();
					}

				}

			});

		}

		function messageUponUpdatePrice() {
			businessNameFld = $("#businessNameFld");
			businessName = $.trim($('#businessNameFld').val());

			emailOfUserFld = $("#emailOfUserFld");
			emailOfUser = $.trim($('#emailOfUserFld').val());

			itemSelected = $("#itemFld option:selected").text();
			itemSubCategory = $("#itemFld option:selected").text();
			itemPrice = $('#itemPriceFld').val();
			var urlGET = "/api/business/pricing/insertionComplete";

			$.ajax({
				type : "GET",
				url : urlGET,
				async : false,
				success : function(result) {
					$.each(result, function(i, errorSuccess) {

						if (errorSuccess.message === '1') {
							alert("You have successfully record this price info");
						} else if (errorSuccess.message === '0') {
							alert("Not successful!!! This price info was not recorded");
						}

					});

				},
				error : function(e) {
					alert("ERROR: ", e);
					console.log("ERROR: ", e);
				}
			});
		}

		function refreshPricingTable() {

			businessNameFld = $("#businessNameFld");
			businessName = $.trim($('#businessNameFld').val());

			emailOfUserFld = $("#emailOfUserFld");
			emailOfUser = $.trim($('#emailOfUserFld').val());

			var numFormat = $("#numberFormatFld option:selected").text();

			var urlGETPricings = "/api/business/pricing/" + businessName + "/" + emailOfUser + "/allPrices?numberFormat=" + numFormat;

			var tBody = $('#createPricingTable').children('tbody');
			tBody.empty();

			$.ajax({
				type : "GET",
				url : urlGETPricings,
				success : function(result) {
					$.each(result, function(i, pricing) {

						// console.log("id: " + pricing.id)

						var tr = $('<tr>');

						/* 1 */
						var td = $('<td hidden="hidden">');

						td.append(pricing.id);
						tr.append(td);
						tBody.append(tr);

						/* 2 */
						var td = $('<td>');
						td.append(pricing.dateServer);
						tr.append(td);

						/* 3 */
						var td = $('<td>');
						td.append(pricing.dateClient);
						tr.append(td);

						/* 4 */
						var td = $('<td>');
						td.append(pricing.priceStr);
						tr.append(td);

						/* 5 */
						var td = $('<td hidden="hidden">');
						td.append(pricing.itemId);
						tr.append(td);

						/* 6 */
						var td = $('<td>');
						td.append(pricing.itemSubCategory);
						tr.append(td);

						/* 7 */
						var td = $('<td><span class="glyphicon glyphicon-remove btn btn-default"></span></td>');
						td.click(deletePricing);
						td.attr("id", pricing.id);
						tr.append(td);

						/* 8 */
						var td = $('<td><a href="#" id="scroll"></a><span class="glyphicon glyphicon-pencil btnSelect btn btn-default" type="button"></span></td>');
						tr.append(td);

						tBody.append(tr);

					});
					$("#createCustomerTable tbody tr:odd").addClass("info");
					$("#createCustomerTable tbody tr:even").addClass("success");
				}
			});

		}

		function deletePricing(event) {

			businessNameFld = $("#businessNameFld");
			businessName = $.trim($('#businessNameFld').val());

			emailOfUserFld = $("#emailOfUserFld");
			emailOfUser = $.trim($('#emailOfUserFld').val());

			var td = $(event.currentTarget);
			var id = td.attr('id');

			// alert("id: " + id);

			var urlDELETEPricing = "/api/business/pricing/" + businessName + "/" + emailOfUser + "/" + id + "/delete";

			if (!confirm('Delete this price permanently?'))
				return;

			if (!confirm("Please stay warned once again\nAre you sure you want to delete this purchases permanently?\nYou will not be able to make any more sales of this particular item if you delete the price"))
				return;

			$.ajax({
				url : urlDELETEPricing,
				type : 'delete',
				success : refreshPricingTable
			});
			// end ajax
		}

		function clearFields() {
			$("#itemPriceFld").val("");
			$("#itemIdFld").val("");
			$("#dateServer").val("");
			$("#dateClient").val("");
			$("#itemPriceIdFld").val("");
		}

		// function buttonUpdateClickableOrNot() {
		// var itemId = $('#itemIdFld').val();
		// var priceId = $('#itemPriceIdFld').val();
		// var price = $('#itemPriceFld').val();
		// if (itemId === '' && priceId === '' && price === '') {
		// $('#btnSavePricingChanges').attr("disabled", true);
		// }
		//
		// }
		//
		// function buttonSaveClickableOrNot() {
		// var itemId = $('#itemIdFld').val();
		// var priceId = $('#itemPriceIdFld').val();
		// var price = $('#itemPriceFld').val();
		// if (price === '') {
		// $('#btnSavePurchase').attr("disabled", true);
		// }
		//
		// }

	}
})();