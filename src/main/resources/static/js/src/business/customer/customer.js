(function() {
	jQuery(init);

	var btnSaveCustomer;
	var btnSaveCustomerChanges;

	var customerNameFld;
	var customerName;

	var customerPhoneNumberFld;
	var customerPhoneNumber;

	var customerEmailFld;
	var customerEmail;

	var customerLocationFld;
	var customerLocation;

	var businessNameFld;
	var businessName;

	var emailOfUserFld;
	var emailOfUser;

	function init() {

		refreshTable();

		btnSaveCustomer = $("#btnSaveCustomer");
		btnSaveCustomer.click(saveCustomerInfo);

		btnSaveCustomerChanges = $("#btnSaveCustomerChanges");
		btnSaveCustomerChanges.click(updateCustomerInfo);

		function refreshTable() {
			businessNameFld = $("#businessNameFld");
			businessName = $.trim($('#businessNameFld').val());

			emailOfUserFld = $("#emailOfUserFld");
			emailOfUser = $.trim($('#emailOfUserFld').val());
			urlGET = "/api/business/customers/" + businessName + "/" + emailOfUser + "/allCustomers";

			// /api/business/customers/{businessName}/{emailOfUser}/allCustomers

			var tBody = $('#createCustomerTable').children('tbody');
			tBody.empty();

			$.ajax({
				type : "GET",
				url : urlGET,
				success : function(result) {
					$.each(result, function(i, customer) {
						// console.log('test');
						// console.log('name:' +
						// customer.customerName);
						var tr = $('<tr>');
						var td = $('<td hidden="hidden">');

						td.append(customer.id);
						tr.append(td);
						tBody.append(tr);

						var td = $('<td>');
						td.append(customer.customerName);
						tr.append(td);
						// tBody.append(tr);

						var td = $('<td>');
						td.append(customer.customerPhoneNumber);
						tr.append(td);

						var td = $('<td>');
						td.append(customer.customerEmail);
						tr.append(td);

						var td = $('<td>');
						td.append(customer.customerLocation);
						tr.append(td);

						var td = $('<td><span class="glyphicon glyphicon-remove btn btn-default"></span></td>');
						td.click(deleteCustomer);
						td.attr("id", customer.id);
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

		function deleteCustomer(event) {
			var td = $(event.currentTarget);
			var id = td.attr('id');
			urlGET = "/api/business/customers/" + businessName + "/" + emailOfUser + "/" + id + "/delete";

			if (!confirm('Delete this customer permanently?'))
				return;

			if (!confirm('Please stay warned once again\nAre you sure you want to delete this customer permanently?'))
				return;

			$.ajax({
				url : urlGET,
				type : 'delete',
				success : refreshTable
			});
			// end ajax
		}

		function updateCustomerInfo() {

			businessNameFld = $("#businessNameFld");
			businessName = $.trim($('#businessNameFld').val());

			emailOfUserFld = $("#emailOfUserFld");
			emailOfUser = $.trim($('#emailOfUserFld').val());

			customerNameFld = $("#customerNameFld");
			customerName = $.trim($('#customerNameFld').val());

			customerPhoneNumberFld = $("#customerPhoneNumberFld");
			customerPhoneNumber = $.trim($('#customerPhoneNumberFld').val());

			customerEmailFld = $("#customerEmailFld");
			customerEmail = $.trim($('#customerEmailFld').val());

			customerLocationFld = $("#customerLocationFld");
			customerLocation = $.trim($('#customerLocationFld').val());

			customerWebsiteFld = $("#customerWebsiteFld");
			customerWebsite = $.trim($('#customerWebsiteFld').val());

			var customerIdFld = $("#customerIdFld");
			var customerId = $.trim($('#customerIdFld').val());

			var customer = {
				"customerName" : customerName,
				"customerPhoneNumber" : customerPhoneNumber,
				"customerEmail" : customerEmail,
				"customerLocation" : customerLocation,
				"customerWebsite" : customerWebsite
			};

			urlPUT = "/api/business/customers/" + businessName + "/" + emailOfUser + "/" + customerId + "/update";

			$.ajax({
				type : "PUT",
				url : urlPUT,
				data : JSON.stringify(customer),
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
						// alert(xhr.status);
						alert('not successful');
						console.log(xhr.status);
					} else if (xhr.status === 200) {
						// alert('you have successfully saved');
						console.log(xhr.status);
						alert('successful');
						refreshTable();
						clearFields();
						// window.location.href =
						// '/keepCount/business/entities/'
						// + businessName + '/' + emailOfUser;
					}

				}
			});

		}

		function saveCustomerInfo() {

			businessNameFld = $("#businessNameFld");
			businessName = $.trim($('#businessNameFld').val());

			emailOfUserFld = $("#emailOfUserFld");
			emailOfUser = $.trim($('#emailOfUserFld').val());

			customerNameFld = $("#customerNameFld");
			customerName = $.trim($('#customerNameFld').val());

			customerPhoneNumberFld = $("#customerPhoneNumberFld");
			customerPhoneNumber = $.trim($('#customerPhoneNumberFld').val());

			customerEmailFld = $("#customerEmailFld");
			customerEmail = $.trim($('#customerEmailFld').val());

			customerLocationFld = $("#customerLocationFld");
			customerLocation = $.trim($('#customerLocationFld').val());

			/*
			 * alert('name: ' + customerName + "\n" + 'tel: ' +
			 * customerPhoneNumber + "\n" + 'email: ' + customerEmail + "\n" +
			 * 'location: ' + customerLocation + "\n" + 'website: ' +
			 * customerWebsite);
			 */

			var customer = {
				"customerName" : customerName,
				"customerPhoneNumber" : customerPhoneNumber,
				"customerEmail" : customerEmail,
				"customerLocation" : customerLocation,
			};

			alert('name: ' + customerName + "\n" + 'tel: ' + customerPhoneNumber + "\n" + 'email: ' + customerEmail + "\n" + 'location: ' + customerLocation);

			if (customerName === '') {
				alert('Customer name must be specified, otherwise this will not be recorded.');
				return false;
			}

			if (customerPhoneNumber === '') {
				alert('Customer phone number must be specified, otherwise this will not be recorded.');
				return false;
			}

			if (customerEmail === '') {
				if (!confirm('Customer email not specified, do you wish to save this info without the customer email?')) {
					return;
				}

			}

			if (customerLocation === '') {
				if (!confirm('Customer location not specified, do you wish to save this info without the customer location?')) {
					return;
				}

			}

			// alert(customer);
			// /api/business/customers/{businessName}/{emailOfUser}" ;
			//		
			// "/api/business/customers/{businessName}/{emailOfUser}"

			var urlPOST = "/api/business/customers/" + businessName + "/" + emailOfUser;

			$.ajax({
				type : "POST",
				url : urlPOST,
				data : JSON.stringify(customer),
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
						// alert(xhr.status);
						alert('not successful');
						console.log(xhr.status);
					} else if (xhr.status === 200) {
						// alert('you have successfully saved');
						console.log(xhr.status);
						alert('successful');
						refreshTable();
						clearFields();
						// window.location.href =
						// '/keepCount/business/entities/'
						// + businessName + '/' + emailOfUser;
					}

				}
			});

		}

		function clearFields() {
			$("#customerNameFld").val("");
			$("#customerPhoneNumberFld").val("");
			$("#customerEmailFld").val("");
			$("#customerLocationFld").val("");
			$("#customerWebsiteFld").val("");
		}

	}

})();