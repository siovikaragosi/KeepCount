//refreshTable();

$(document)
		.ready(
				function() {

					var businessNameFld = $("#businessNameFld");
					var businessName = $.trim($('#businessNameFld').val());

					var emailOfUserFld = $("#emailOfUserFld");
					var emailOfUser = $.trim($('#emailOfUserFld').val());

					var urlGET = "/api/business/" + businessName + "/"
							+ emailOfUser;

					ajaxGet();

					var tBody = $('#createUserTable').children('tbody');

					// DO GET
					function ajaxGet() {
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
															td
																	.append(user.userFirstName);
															tr.append(td);
															tBody.append(tr);

															var td = $('<td>');
															td
																	.append(user.userLastName);
															tr.append(td);

															var td = $('<td>');
															td
																	.append(user.userPhoneNumber);
															tr.append(td);

															var td = $('<td>');
															td
																	.append(user.userEmail);
															tr.append(td);

															var img = '<img src="'
																	+ user.imagePathBase64
																	+ '" class="img-rounded" style="width=100px; height:120px" />'

															var td = $('<td>');
															td.append(img);
															tr.append(td);

															var td = $('<td><span class="glyphicon glyphicon-remove"></span></td>');
															td
																	.click(deleteUser);
															td.attr("id",
																	user.id);
															tr.append(td);

															tBody.append(tr);

														});

										$("#createUserTable tbody tr:odd")
												.addClass("info");
										$("#createUserTable tbody tr:even")
												.addClass("success");
									},
									error : function(e) {
										alert("ERROR: ", e);
										console.log("ERROR: ", e);
									}
								});
					}

					function deleteUser(event) {
						var td = $(event.currentTarget);
						var id = td.attr('id');
						$.ajax({
							url : urlGET + "/" + id,
							type : 'delete',
							success : refreshTable
						});
						// end ajax
					}

				})

// function deleteUser() {
// alert('delete user');
// }

$(function() {
	$('#btnSaveUser').click(saveUserInfo);
});

function showImagePath() {
	alert($('#imagePreview').attr('src'));
}

// businessNameFld = $("#businessNameFld");
// businessName = $.trim($('#businessNameFld').val());
//
// emailOfUserFld = $("#emailOfUserFld");
// emailOfUser = $.trim($('#emailOfUserFld').val());

function saveUserInfo() {

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

	alert(imageId);

	urlGET = "/api/business/" + businessName + "/" + emailOfUser;

	// alert(userFirstName + "\n" + userLastName + "\n" + userPhoneNumber + "\n"
	// + userEmail + "\n" + userNIN + "\n" + imageBase64Path);

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
		cache : false,
		type : 'POST',
		data : JSON.stringify(user),
		// data : $('#choose_business_form').serialize(),
		contentType : 'application/json',
		dataType : 'json',
		success : function(data, textStatus, xhr) {
			console.log(xhr.status);
			alert(xhr.status);
		},
		complete : function(xhr, textStatus) {
			console.log(xhr.status);
			if (xhr.status !== 200) {
				alert(xhr.status);
				console.log(xhr.status);
			} else if (xhr.status === 200) {
				alert('you have successfully saved');
				refreshTable();
				console.log(xhr.status);
				// window.location.href = '/keepCount/business/entities/'
				// + businessName + '/' + emailOfUser;
			}

		}

	});

}

function refreshTable() {

	var businessNameFld = $("#businessNameFld");
	var businessName = $.trim($('#businessNameFld').val());

	var emailOfUserFld = $("#emailOfUserFld");
	var emailOfUser = $.trim($('#emailOfUserFld').val());

	var tBody = $('#createUserTable').children('tbody');

	var urlGET = "/api/business/" + businessName + "/" + emailOfUser;

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

										var img = '<img src="'
												+ user.imagePathBase64
												+ '" class="img-rounded" style="width=100px; height:120px" />'

										var td = $('<td>');
										td.append(img);
										tr.append(td);

										var td = $('<td><span class="glyphicon glyphicon-remove"></span></td>');
										td.click(deleteUser);
										td.attr("id", user.id);
										tr.append(td);

										tBody.append(tr);

									});

					$("#createUserTable tbody tr:odd").addClass("info");
					$("#createUserTable tbody tr:even").addClass("success");
				},
				error : function(e) {
					alert("ERROR: ", e);
					console.log("ERROR: ", e);
				}
			});
}