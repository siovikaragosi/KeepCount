(function() {
	jQuery(init);

	var urlGET;

	var businessNameFld;
	var businessName;

	var emailOfUserFld;
	var emailOfUser;

	var btnSavePricingChanges;
	var numberFormatObtained;
	var colItemSubCategory;
	var btnSaveMeasurementUnit;
	function init() {

		tableHeaderScroll();

		btnSaveMeasurementUnit = $("#btnSaveMeasurementUnit");
		btnSaveMeasurementUnit.click(saveUnitOfMeasurement);

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
					}

				}

			});

		}

		function saveUnitOfMeasurement() {

			businessNameFld = $("#businessNameFld");
			businessName = $.trim($('#businessNameFld').val());

			emailOfUserFld = $("#emailOfUserFld");
			emailOfUser = $.trim($('#emailOfUserFld').val());

			emailOfUserFld = $("#emailOfUserFld");
			emailOfUser = $.trim($('#emailOfUserFld').val());

			var itemId = $('#UnitItemIdFld').val();
			var unitOfMeasurement = $('#unitOfMeasurementFld').val();

			// "/api/business/items/unitOfMeasurement/{businessName}/{emailOfUser}"

			var urlPOST = "/api/business/items/unitOfMeasurement/" + businessName + "/" + emailOfUser;

			// private BigDecimal id;
			// private BigDecimal itemId;
			// private String unitOfMeasurement;

			var unit = {
				"itemId" : itemId,
				"unitOfMeasurement" : unitOfMeasurement
			}

			$.ajax({
				url : urlPOST,
				// cache : false,
				type : 'POST',
				data : JSON.stringify(unit),
				// data : $('#choose_business_form').serialize(),
				contentType : 'application/json',
				dataType : 'json',
				async : false,
				success : function(data, textStatus, xhr) {
					console.log(xhr.status);
					alert(xhr.status);
					// refreshTable();
					// checkPriceNumberSuccessUpdate();
				},
				complete : function(xhr, textStatus) {
					console.log(xhr.status);
					if (xhr.status !== 200) {
						alert(xhr.status);
						console.log(xhr.status);
						// checkPriceNumberSuccessUpdate();
					} else if (xhr.status === 200) {
						// alert(xhr.status);
						console.log(xhr.status);
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

	}
})();