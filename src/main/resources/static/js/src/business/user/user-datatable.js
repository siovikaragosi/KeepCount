$(document).ready(
		function() {

			var businessNameFld = $("#businessNameFld");
			var businessName = $.trim($('#businessNameFld').val());

			var emailOfUserFld = $("#emailOfUserFld");
			var emailOfUser = $.trim($('#emailOfUserFld').val());

			var urlGET = "/api/business/" + businessName + "/" + emailOfUser;

			ajaxGet();

			// DO GET
			function ajaxGet() {
				$
						.ajax({
							type : "GET",
							url : urlGET,
							success : function(result) {
								$.each(result,
										function(i, user) {

											var userRow = 
												'<tr>'

												+ '<td>'+ user.userFirstName.toUpperCase()+ '</td>' 
												
												+ '<td>'+ user.userLastName+ '</td>'
												
												+ '<td>'+ user.userPhoneNumber+ '</td>'
												
												+ '<td>'+ user.userEmail + '</td>' 
												
												+'<td>' + user.userNIN+ '</td>' 
												
												+ '<td>'+ '<img src="'+ user.imagePathBase64+ '" class="img-rounded" style="width=100px; height:120px" />' + '</td>'
													
												+

												'</tr>';

											// <td> <img
											// th:src="*{user.imagePathBase64}"
//											 class="img-rounded"
//											 style="width: 100px;
//											 height: 120px;"/></td>

											
											$('#createUserTable tbody').append(
													userRow);

										});

								$("#createUserTable tbody tr:odd").addClass(
										"info");
								$("#createUserTable tbody tr:even").addClass(
										"success");
							},
							error : function(e) {
								alert("ERROR: ", e);
								console.log("ERROR: ", e);
							}
						});
			}
		})
		
		
		