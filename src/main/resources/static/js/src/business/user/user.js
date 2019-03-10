(function() {
	jQuery(init);

	// $('.tableFixHead').on(
	// 'scroll',
	// function() {
	// $('thead', this).css('transform',
	// 'translateY(' + this.scrollTop + 'px)');
	// });

	var urlGET;

	var businessNameFld;
	var businessName;

	var emailOfUserFld;
	var emailOfUser;

	var userFirstNameFld;
	var userFirstName;

	var userLastNameFld;
	var userLastName;

	var userPhoneNumberFld;
	var userPhoneNumber;

	var userEmailFld;
	var userEmail;

	var userNINFld;
	var userNIN;

	var imageIdFld;
	var imageId;
	var btnSaveUser;
	var btnSaveUserChanges;

	function init() {

		refreshTable();

		btnSaveUser = $("#btnSaveUser");
		btnSaveUser.click(saveUserInfo);

		btnSaveUserChanges = $("#btnSaveUserChanges");
		btnSaveUserChanges.click(updateUser);

		function deleteUser(event) {
			var td = $(event.currentTarget);
			var id = td.attr('id');
			urlGET = "/api/business/" + businessName + "/" + emailOfUser;

			if (!confirm('Delete this user completely?'))
				return;

			$.ajax({
				url : urlGET + "/" + id + "/delete",
				type : 'delete',
				success : refreshTable
			});
			// end ajax
		}

		function updateUser() {
			businessNameFld = $("#businessNameFld");
			businessName = $.trim($('#businessNameFld').val());

			emailOfUserFld = $("#emailOfUserFld");
			emailOfUser = $.trim($('#emailOfUserFld').val());

			userFirstNameFld = $("#userFirstNameFld");
			userFirstName = $.trim($('#userFirstNameFld').val());

			userFirstNameFld = $("#userFirstNameFld");
			userFirstName = $.trim($('#userFirstNameFld').val());

			userLastNameFld = $("#userLastNameFld");
			userLastName = $.trim($('#userLastNameFld').val());

			userPhoneNumberFld = $("#userPhoneNumberFld");
			userPhoneNumber = $.trim($('#userPhoneNumberFld').val());

			userEmailFld = $("#userEmailFld");
			userEmail = $.trim($('#userEmailFld').val());

			userNINFld = $("#userNINFld");
			userNIN = $.trim($('#userNINFld').val());

			imageIdFld = $("#imageIdFld");
			imageId = $.trim($('#imageIdFld').val());

			var userIdFld = $("#userIdFld");
			var userId = $.trim($('#userIdFld').val());

			alert('First Name: ' + userFirstName + "\n" + 'Last Name: '
					+ userLastName + "\n" + 'Phone Number: ' + userPhoneNumber
					+ "\n" + 'Email Address: ' + userEmail + "\n" + 'NIN: '
					+ userNIN + "\n" + 'ID: ' + userId);

			var user = {
				"id" : userId,
				"userFirstName" : userFirstName,
				"userLastName" : userLastName,
				"userPhoneNumber" : userPhoneNumber,
				"userEmail" : userEmail,
				"userNIN" : userNIN,
				"imagePathBase64" : imageId
			};

			// /api/business/{businessName}/{emailOfUser}/{id}/update

			urlGET = "/api/business/" + businessName + "/" + emailOfUser + "/"
					+ userId + "/update";

			$.ajax({
				url : urlGET,
				type : 'put',

				data : JSON.stringify(user),
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
						alert('you have successfully updated use info');
						console.log(xhr.status);
						refreshTable();
						clearFields();
						// window.location.href =
						// '/keepCount/business/entities/'
						// + businessName + '/' + emailOfUser;
					}

				}
			});
			// end ajax
		}

		function refreshTable() {

			businessNameFld = $("#businessNameFld");
			businessName = $.trim($('#businessNameFld').val());

			emailOfUserFld = $("#emailOfUserFld");
			emailOfUser = $.trim($('#emailOfUserFld').val());
			urlGET = "/api/business/" + businessName + "/" + emailOfUser;

			// alert(window.location.protocol);
			// alert(window.location.host);
			// alert(window.location.pathname);
			//
			// alert(location);

			// window.location.href = myNewUrl;
			// alert('url new: ' + window.location.href);

			// var newURL = window.location.protocol + "//" +
			// window.location.host
			// + "/" + window.location.pathname + "/test";

			// window.location.href = urlGET;
			// replaceURL(businessName);

			// ajaxGet();

			// function geturl()
			// {
			// var url=window.location.href;
			// alert(url.replace('.com/', '.com/es/'));;
			//			
			// }

			// window.location.replace('businessName' + businessName);

			// var url = window.location.href;
			// alert(url.replace('/{businessName}', +"/" + businessName));

			var tBody = $('#createUserTable').children('tbody');

			tBody.empty();

			$
					.ajax({
						type : "GET",
						url : urlGET,
						success : function(result) {
							$
									.each(
											result,
											function(i, user) {
												// console.log(user.userFirstName);

												var tr = $('<tr>');
												var td = $('<td>');
												td.append(user.userFirstName);
												tr.append(td);
												tBody.append(tr);

												var td = $('<td>');
												td.append(user.userLastName);
												tr.append(td);

												var td = $('<td>');
												td.append(user.userPhoneNumber);
												tr.append(td);

												var td = $('<td>');
												td.append(user.userEmail);
												tr.append(td);

												var td = $('<td></td>');
												td.append(user.userNIN);
												tr.append(td);

												var img = '<img src="'
														+ user.imagePathBase64
														+ '" class="img-rounded nr" style="width=100px; height:120px" />'

												var td = $('<td>');
												td.append(img);
												tr.append(td);

												var td = $('<td hidden="hidden"></td>');
												td.append(user.id);
												tr.append(td);

												var td = $('<td><span class="glyphicon glyphicon-remove btn btn-default"></span></td>');
												td.click(deleteUser);
												td.attr("id", user.id);
												tr.append(td);

												var td = $('<td><a href="#" id="scroll"></a><span class="glyphicon glyphicon-pencil btnSelect btn btn-default" type="button"></span></td>');
												// td.click(selectForUpdate);
												// td.attr("id", user.id);
												tr.append(td);

												tBody.append(tr);

											});

							$("#createUserTable tbody tr:odd").addClass("info");
							$("#createUserTable tbody tr:even").addClass(
									"success");
						}

						,
						error : function(e) {
							alert("ERROR: ", e);
							console.log("ERROR: ", e);
						}
					});

			window.history.pushState("object or string", "Title",
					"/business/users/" + businessName + "/" + emailOfUser);
			// /business/users/{businessName}/{emailOfUser}
		}

		function saveUserInfo() {

			businessNameFld = $("#businessNameFld");
			businessName = $.trim($('#businessNameFld').val());

			emailOfUserFld = $("#emailOfUserFld");
			emailOfUser = $.trim($('#emailOfUserFld').val());

			userFirstNameFld = $("#userFirstNameFld");
			userFirstName = $.trim($('#userFirstNameFld').val());

			userFirstNameFld = $("#userFirstNameFld");
			userFirstName = $.trim($('#userFirstNameFld').val());

			userLastNameFld = $("#userLastNameFld");
			userLastName = $.trim($('#userLastNameFld').val());

			userPhoneNumberFld = $("#userPhoneNumberFld");
			userPhoneNumber = $.trim($('#userPhoneNumberFld').val());

			userEmailFld = $("#userEmailFld");
			userEmail = $.trim($('#userEmailFld').val());

			userNINFld = $("#userNINFld");
			userNIN = $.trim($('#userNINFld').val());

			imageIdFld = $("#imageIdFld");
			imageId = $.trim($('#imageIdFld').val());

			urlGET = "/api/business/" + businessName + "/" + emailOfUser;

			var user = {
				"userFirstName" : userFirstName,
				"userLastName" : userLastName,
				"userPhoneNumber" : userPhoneNumber,
				"userEmail" : userEmail,
				"userNIN" : userNIN,
				"imagePathBase64" : imageId
			};

			$.ajax({
				url : urlGET,
				// cache : false,
				type : 'POST',
				data : JSON.stringify(user),
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
						alert('you have successfully saved');
						console.log(xhr.status);
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
			$("#userFirstNameFld").val("");
			$("#userLastNameFld").val("");
			$("#userPhoneNumberFld").val("");
			$("#userEmailFld").val("");
			$("#userNINFld").val("");
			$("#imageIdFld").val("");
		}

	}
})();