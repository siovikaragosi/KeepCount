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

	var itemQuantity;
	var itemUnitCost;
	var itemTotalCostManual;
	var credit;
	var clearedBy;
	var amountPaid;
	var installmentPaid;
	var installmentBalance;
	var paymentMethod;
	var supplierName;

	var btnSavePurchase;
	var btnSavePurchasesChanges;
	var btnItemCreditFld;

	var amountPaidFinal;
	var installmentPaidFinal;
	var installmentBalanceFinal;

	function init() {

		// swal('Good job!', 'You clicked the button!', 'success');
		// swal('Any fool can use a computer');

		btnSavePurchase = $("#btnSavePurchase");
		btnSavePurchase.click(savePurchasesInfo);

		btnSavePurchasesChanges = $("#btnSavePurchasesChanges");
		btnSavePurchasesChanges.click(updatePurchasesInfo);

		btnItemCreditFld = $("#itemCreditFld");
		btnItemCreditFld.click(checkCredit);

		refreshNumberFormatList();
		refreshTableOnNumberFormatchanged();
		refreshTableOnSave();

		refreshItemList();
		refreshSupplierList();
		itemChosen();

		allowNumbersQty();
		allowNumbersPriceUnit();
		allowNumbersTotalManual();
		allowNumbersInstallmentPaid();
		allowNumbersInstallmentBalance();
		allowNumbersAmountPaid();

		itemFld = $("#itemFld");
		itemFld.change(itemChosen);

		function itemChosen() {
			itemSelected = $("#itemFld").val();
			$("#itemSubCategoryFld").val($("#itemFld option:selected").text());
		}

		function checkCredit() {
			if ($('#itemCreditFld').is(":checked")) {
				credit = true;
				installmentPaid = $('#installmentPaidFld').val();
				$("#amountPaidFld").val("");
				amountPaid = $('#amountPaidFld').val();
			} else {
				credit = false;
				amountPaid = $('#amounttPaidFld').val();
				$("#installmentPaidFld").val("");
				installmentPaid = $('#installmentPaidFld').val();
			}
		}

		function refreshTableOnSave() {
			var numFormat = $("#numberFormatFld option:selected").text();
			refreshPurchasesTable(numFormat);
		}

		function refreshTableOnNumberFormatchanged() {

			$('#numberFormatFld').on('change', function() {
				var numFormat = $("#numberFormatFld option:selected").text();
				refreshPurchasesTable(numFormat);
			});

		}

		function refreshItemList() {

			businessNameFld = $("#businessNameFld");
			businessName = $.trim($('#businessNameFld').val());

			emailOfUserFld = $("#emailOfUserFld");
			emailOfUser = $.trim($('#emailOfUserFld').val());
			urlGET = "/api/business/purchases/items/" + businessName + "/" + emailOfUser + "/allItems";

			$.ajax({
				type : "GET",
				url : urlGET,
				success : function(result) {
					$.each(result, function(i, item) {

						console.log(item.itemSubCategory);

						$('#itemFld').append('<option value=' + item.itemSubCategory + '>' + item.itemName + ' -- (' + item.itemSubCategory + ')' + '</option>')
					});
				},
				error : function(e) {
					alert("ERROR: ", e);
					console.log("ERROR: ", e);
				}
			});

			window.history.pushState("object or string", "Title", "/business/purchases/" + businessName + "/" + emailOfUser);
		}

		function refreshSupplierList() {
			businessNameFld = $("#businessNameFld");
			businessName = $.trim($('#businessNameFld').val());

			emailOfUserFld = $("#emailOfUserFld");
			emailOfUser = $.trim($('#emailOfUserFld').val());

			urlGET = "/api/business/purchases/suppliers/" + businessName + "/" + emailOfUser + "/allSuppliers";

			$.ajax({
				type : "GET",
				url : urlGET,
				success : function(result) {
					$.each(result, function(i, supplier) {

						console.log(supplier.supplierName);
						$('#supplierNameFld').append('<option value=' + supplier.supplierPhoneNumber + '>' + supplier.supplierName + ' -- (' + supplier.supplierPhoneNumber + ')' + '</option>')
					});
				},
				error : function(e) {
					alert("ERROR: ", e);
					console.log("ERROR: ", e);
				}
			});
			window.history.pushState("object or string", "Title", "/business/purchases/" + businessName + "/" + emailOfUser);
		}

		function refreshNumberFormatList() {
			var urlGETNumberFormat = "/api/business/purchases/numberFormats";

			$.ajax({
				type : "GET",
				url : urlGETNumberFormat,
				success : function(result) {
					$.each(result, function(i, purhcasesNumberFormat) {

						console.log(purhcasesNumberFormat.numberFormatName);
						$('#numberFormatFld').append('<option value=' + purhcasesNumberFormat.numberFormatName + '>' + purhcasesNumberFormat.numberFormatName + '</option>')
					});
				},
				error : function(e) {
					alert("ERROR: ", e);
					console.log("ERROR: ", e);
				}
			});
		}

		function allowNumbersQty() {
			$('#itemQuantityFld').keypress(function(event) {
				if ((event.which > 47 && event.which < 58)
				// back space
				|| (event.which == 8) ||
				// comma
				(event.which == 44)
				// decimal point
				|| (event.which == 46)) {

					return true;
				} else {
					event.preventDefault();
					alert('incorrect character');
				}
			}).on('paste', function(event) {
				event.preventDefault();
			})
		}

		function allowNumbersPriceUnit() {
			$('#itemPricePerUnitFld').keypress(function(event) {
				if ((event.which > 47 && event.which < 58)
				// back space
				|| (event.which == 8) ||
				// comma
				(event.which == 44)
				// decimal point
				|| (event.which == 46)) {

					return true;
				} else {
					event.preventDefault();
					alert('incorrect character');
				}
			}).on('paste', function(event) {
				event.preventDefault();
			})
		}

		function allowNumbersTotalManual() {
			$('#itemTotalManualCostFld').keypress(function(event) {
				if ((event.which > 47 && event.which < 58)
				// back space
				|| (event.which == 8) ||
				// comma
				(event.which == 44)
				// decimal point
				|| (event.which == 46)) {
					return true;
				} else {
					event.preventDefault();
					alert('incorrect character');
				}
			}).on('paste', function(event) {
				event.preventDefault();
			})
		}

		function allowNumbersInstallmentPaid() {
			$('#installmentPaidFld').keypress(function(event) {
				if ((event.which > 47 && event.which < 58)
				// back space
				|| (event.which == 8) ||
				// comma
				(event.which == 44)
				// decimal point
				|| (event.which == 46)) {

					return true;
				} else {
					event.preventDefault();
					alert('incorrect character');
				}
			}).on('paste', function(event) {
				event.preventDefault();
			});
		}

		function allowNumbersInstallmentBalance() {
			$('#installmentBalanceFld').keypress(function(event) {
				if ((event.which > 47 && event.which < 58)
				// back space
				|| (event.which == 8) ||
				// comma
				(event.which == 44)
				// decimal point
				|| (event.which == 46)) {

					return true;
				} else {
					event.preventDefault();
					alert('incorrect character');
				}
			}).on('paste', function(event) {
				event.preventDefault();
			});
		}

		function allowNumbersAmountPaid() {
			$('#amountPaidFld').keypress(function(event) {
				if ((event.which > 47 && event.which < 58)
				// back space
				|| (event.which == 8) ||
				// comma
				(event.which == 44)
				// decimal point
				|| (event.which == 46)) {

					return true;
				} else {
					event.preventDefault();
					alert('incorrect character');
				}
			}).on('paste', function(event) {
				event.preventDefault();
			});
		}

		function savePurchasesInfo() {

			checkCredit();

			businessNameFld = $("#businessNameFld");
			businessName = $.trim($('#businessNameFld').val());

			emailOfUserFld = $("#emailOfUserFld");
			emailOfUser = $.trim($('#emailOfUserFld').val());

			itemSelected = $("#itemFld option:selected").text();
			itemSubCategory = $("#itemFld option:selected").text();
			itemQuantity = $('#itemQuantityFld').val();
			itemUnitCost = $('#itemPricePerUnitFld').val();
			itemTotalCostManual = $('#itemTotalManualCostFld').val();

			amountPaid = $('#amountPaidFld').val();

			clearedBy = $('#clearedByFld').val();
			installmentPaid = $('#installmentPaidFld').val();
			installmentBalance = $('#installmentBalanceFld').val();
			paymentMethod = $('#paymentMethodFld').val();
			supplierName = $('#supplierNameFld option:selected').text();

			if (credit === true) {
				$("#amountPaidFld").val("");

				if (installmentPaid !== 0 || installmentPaid !== null || installmentPaid !== '') {
					installmentPaid = $('#installmentPaidFld').val();
					installmentBalance = $('#installmentBalanceFld').val();
				} else if (installmentPaid === '' || installmentPaid === null) {
					alert("You have to specify the installment paid since this is a credit transaction");
					return false;
				} else if (installmentBalance === '' || installmentBalance === null) {
					alert("You have to specify the installment balance since this is a credit transaction");
					return false;
				}
			} else if (credit === false) {

				$("#installmentPaidFld").val("");

				if (amountPaid !== '' || amountPaid !== 0 || amountPaid !== null) {
					amountPaid = $('#amountPaidFld').val();
				} else if (amountPaid === '' || amountPaid === null) {
					alert("You have to specify the amount paid since this is not a credit transaction");
					return false;
				}

			}

			if (credit === true) {
				if (installmentPaid === '' || installmentPaid === null) {
					alert("Please specify the installment paid since it is a credit transaction.");
					return false;
				}

				if (installmentBalance === '' || installmentBalance === null) {
					alert("Please specify the installment balance since it is a credit transaction.");
					return false;
				}

				if (amountPaid !== null || amountPaid !== '') {
					alert("amount paid will not be recorded since it is a credit transaction.");
				}
				$("#amountPaidFld").val("");
				amountPaid = $('#amountPaidFld').val();
			}

			if (credit === false) {
				if (amountPaid === '' || amountPaid === null) {
					alert("Please specify the amount paid since it is not a credit transaction.");
					return false;
				}

				if (installmentPaid !== null || installmentPaid !== '') {
					alert("installment paid will not be recorded since it is not a credit transaction.");
				}

				if (installmentBalance !== null || installmentBalance !== '') {
					alert("installment balance will not be recorded since it is not a credit transaction.");
				}

				$("#installmentPaidFld").val("");
				installmentPaid = $('#installmentPaidFld').val();

				$("#installmentBalanceFld").val("");
				installmentBalance = $('#installmentBalanceFld').val();
			}

			if (!confirm("Are you sure you want to record this purchase transaction.")) {
				return;
			}

			var purchase = {
				"item" : itemSelected,/* 1 */
				"itemSubCategory" : itemSubCategory, /* 2 */
				"itemQuantityStr" : itemQuantity,/* 3 */
				"itemPricePerUnitStr" : itemUnitCost,/* 4 */
				"itemTotalManualCostStr" : itemTotalCostManual,/* 5 */
				"credit" : credit,/* 6 */
				"clearedBy" : clearedBy,/* 7 */
				"amountPaidStr" : amountPaid,/* 8 */
				"installmentPaidStr" : installmentPaid, /* 9 */
				"installmentBalanceStr" : installmentBalance,/* 10 */
				"paymentMethod" : paymentMethod,/* 11 */
				"supplierName" : supplierName
			/* 12 */
			}

			var urlPOST = "/api/business/purchases/" + businessName + "/" + emailOfUser;

			$.ajax({
				url : urlPOST,
				// cache : false,
				type : 'POST',
				data : JSON.stringify(purchase),
				// data : $('#choose_business_form').serialize(),
				contentType : 'application/json',
				dataType : 'json',
				success : function(data, textStatus, xhr) {
					console.log(xhr.status);
					alert(xhr.status);
					refreshTable();
				},
				complete : function(xhr, textStatus) {
					console.log(xhr.status);
					if (xhr.status !== 200) {
						alert(xhr.status);
						console.log(xhr.status);
					} else if (xhr.status === 200) {
						alert('you have successfully saved this item');
						console.log(xhr.status);
						// refreshTableOnNumberFormatchanged();
						refreshTableOnSave();
						// refreshTable();
						// clearFields();
					}

				}

			});

		}

		function updatePurchasesInfo() {

			checkCredit();

			businessNameFld = $("#businessNameFld");
			businessName = $.trim($('#businessNameFld').val());

			emailOfUserFld = $("#emailOfUserFld");
			emailOfUser = $.trim($('#emailOfUserFld').val());

			itemSelected = $("#itemFld option:selected").text();
			itemSubCategory = $("#itemFld option:selected").text();
			itemQuantity = $('#itemQuantityFld').val();
			itemUnitCost = $('#itemPricePerUnitFld').val();
			itemTotalCostManual = $('#itemTotalManualCostFld').val();

			amountPaid = $('#amountPaidFld').val();

			clearedBy = $('#clearedByFld').val();
			installmentPaid = $('#installmentPaidFld').val();
			installmentBalance = $('#installmentBalanceFld').val();
			paymentMethod = $('#paymentMethodFld').val();
			supplierName = $('#supplierNameFld option:selected').text();

			if (credit === true) {
				$("#amountPaidFld").val("");

				if (installmentPaid !== 0 || installmentPaid !== null || installmentPaid !== '') {
					installmentPaid = $('#installmentPaidFld').val();
					installmentBalance = $('#installmentBalanceFld').val();
				} else if (installmentPaid === '' || installmentPaid === null) {
					alert("You have to specify the installment paid since this is a credit transaction");
					return false;
				} else if (installmentBalance === '' || installmentBalance === null) {
					alert("You have to specify the installment balance since this is a credit transaction");
					return false;
				}
			} else if (credit === false) {

				$("#installmentPaidFld").val("");

				if (amountPaid !== '' || amountPaid !== 0 || amountPaid !== null) {
					amountPaid = $('#amountPaidFld').val();
				} else if (amountPaid === '' || amountPaid === null) {
					alert("You have to specify the amount paid since this is not a credit transaction");
					return false;
				}

			}

			if (credit === true) {
				if (installmentPaid === '' || installmentPaid === null) {
					alert("Please specify the installment paid since it is a credit transaction.");
					return false;
				}

				if (installmentBalance === '' || installmentBalance === null) {
					alert("Please specify the installment balance since it is a credit transaction.");
					return false;
				}

				if (amountPaid !== null || amountPaid !== '') {
					alert("amount paid will not be recorded since it is a credit transaction.");
				}
				$("#amountPaidFld").val("");
				amountPaid = $('#amountPaidFld').val();
			}

			if (credit === false) {
				if (amountPaid === '' || amountPaid === null) {
					alert("Please specify the amount paid since it is not a credit transaction.");
					return false;
				}

				if (installmentPaid !== null || installmentPaid !== '') {
					alert("installment paid will not be recorded since it is not a credit transaction.");
				}

				if (installmentBalance !== null || installmentBalance !== '') {
					alert("installment balance will not be recorded since it is not a credit transaction.");
				}

				$("#installmentPaidFld").val("");
				installmentPaid = $('#installmentPaidFld').val();

				$("#installmentBalanceFld").val("");
				installmentBalance = $('#installmentBalanceFld').val();
			}

			if (!confirm("Are you sure you want to record this purchase transaction.")) {
				return;
			}

			var itemIdFld = $("#itemIdFld");
			var id = $.trim($('#itemIdFld').val());

			var purchase = {
				"item" : itemSelected,/* 1 */
				"itemSubCategory" : itemSubCategory, /* 2 */
				"itemQuantityStr" : itemQuantity,/* 3 */
				"itemPricePerUnitStr" : itemUnitCost,/* 4 */
				"itemTotalManualCostStr" : itemTotalCostManual,/* 5 */
				"credit" : credit,/* 6 */
				"clearedBy" : clearedBy,/* 7 */
				"amountPaidStr" : amountPaid,/* 8 */
				"installmentPaidStr" : installmentPaid, /* 9 */
				"installmentBalanceStr" : installmentBalance,/* 10 */
				"paymentMethod" : paymentMethod,/* 11 */
				"supplierName" : supplierName,/* 12 */
				"id" : id
			/* 13 */
			}

			// "/api/business/purchases/{businessName}/{emailOfUser}/{id}/update"

			var urlPUT = "/api/business/purchases/" + businessName + "/" + emailOfUser + "/" + id + "/update";

			$.ajax({
				url : urlPUT,
				// cache : false,
				type : 'PUT',
				data : JSON.stringify(purchase),
				// data : $('#choose_business_form').serialize(),
				contentType : 'application/json',
				dataType : 'json',
				success : function(data, textStatus, xhr) {
					console.log(xhr.status);
					alert(xhr.status);
					refreshTable();
				},
				complete : function(xhr, textStatus) {
					console.log(xhr.status);
					if (xhr.status !== 200) {
						alert(xhr.status);
						console.log(xhr.status);
					} else if (xhr.status === 200) {
						alert('you have successfully saved this item');
						console.log(xhr.status);
						// refreshTableOnNumberFormatchanged();
						refreshTableOnSave();
						// refreshTable();
						// clearFields();
					}

				}

			});

		}

		// function refreshTableOnNumberFormatchanged() {
		//
		// $('#numberFormatFld').on('change', function() {
		// var numFormat = $("#numberFormatFld option:selected").text();
		// refreshPurchasesTable(numFormat);
		// ref
		// });
		//
		// }

		function refreshPurchasesTable(numberFormat) {

			businessNameFld = $("#businessNameFld");
			businessName = $.trim($('#businessNameFld').val());

			emailOfUserFld = $("#emailOfUserFld");
			emailOfUser = $.trim($('#emailOfUserFld').val());

			// "/api/business/purchases/{businessName}/{emailOfUser}/allPurchases"

			var urlGETPurchases = "/api/business/purchases/" + businessName + "/" + emailOfUser + "/allPurchases?numberFormat=" + numberFormat;

			var tBody = $('#createPurchasesTable').children('tbody');
			tBody.empty();

			$.ajax({
				type : "GET",
				url : urlGETPurchases,
				success : function(result) {
					$.each(result, function(i, purchases) {
						// console.log('test');
						// console.log('name:' +
						// customer.customerName);
						var tr = $('<tr>');
						var td = $('<td hidden="hidden">');

						td.append(purchases.id);
						tr.append(td);
						tBody.append(tr);

						var td = $('<td>');
						td.append(purchases.dateServer);
						tr.append(td);

						var td = $('<td>');
						td.append(purchases.dateClient);
						tr.append(td);

						var td = $('<td>');
						td.append(purchases.item);
						tr.append(td);

						var td = $('<td>');
						td.append(purchases.itemSubCategory);
						tr.append(td);

						var td = $('<td>');
						td.append(purchases.itemQuantityStr);
						tr.append(td);

						var td = $('<td>');
						td.append(purchases.itemPricePerUnitStr);
						tr.append(td);

						var td = $('<td>');
						td.append(purchases.itemTotalManualCostStr);
						tr.append(td);

						var td = $('<td>');
						td.append(purchases.itemTotalAutomaticCostStr);
						tr.append(td);

						if (purchases.credit === false) {
							var td = $('<td>');
							td.append("No");
							tr.append(td);
						} else if (purchases.credit === true) {
							var td = $('<td>');
							td.append("Yes");
							tr.append(td);

						}

						var td = $('<td>');
						td.append(purchases.clearedBy);
						tr.append(td);

						var td = $('<td>');
						td.append(purchases.amountPaidStr);
						tr.append(td);

						var td = $('<td>');
						td.append(purchases.installmentPaidStr);
						tr.append(td);

						var td = $('<td>');
						td.append(purchases.installmentBalanceStr);
						tr.append(td);

						var td = $('<td>');
						td.append(purchases.paymentMethod);
						tr.append(td);

						var td = $('<td>');
						td.append(purchases.supplierName);
						tr.append(td);

						var td = $('<td><span class="glyphicon glyphicon-remove btn btn-default"></span></td>');
						td.click(deletePurchase);
						td.attr("id", purchases.id);
						tr.append(td);

						var td = $('<td><a href="#" id="scroll"></a><span class="glyphicon glyphicon-pencil btnSelect btn btn-default" type="button"></span></td>');
						tr.append(td);

						tBody.append(tr);

					});
					$("#createCustomerTable tbody tr:odd").addClass("info");
					$("#createCustomerTable tbody tr:even").addClass("success");
				}
			});

		}

		function deletePurchase(event) {

			businessNameFld = $("#businessNameFld");
			businessName = $.trim($('#businessNameFld').val());

			emailOfUserFld = $("#emailOfUserFld");
			emailOfUser = $.trim($('#emailOfUserFld').val());

			// alert("biz name: " + businessName + "\n" + "email: " +
			// emailOfUser);
			// return;

			var td = $(event.currentTarget);
			var id = td.attr('id');

			alert("id: " + id);

			var urlDELETEPurchase = "/api/business/purchases/" + businessName + "/" + emailOfUser + "/" + id + "/delete";

			if (!confirm('Delete this purchases details permanently?'))
				return;

			if (!confirm('Please stay warned once again\nAre you sure you want to delete this purchases permanently?'))
				return;

			$.ajax({
				url : urlDELETEPurchase,
				type : 'delete',
				success : refreshTableOnSave
			});
			// end ajax
		}

	}
})();