(function() {
	jQuery(init);

	var btnChooseBusiness;
	var businessNameFld;
	var emailOfUserFld;
	var businessName;
	var emailOfUser;

	function init() {

		btnChooseBusiness = $("#btnChooseBusiness");
		btnChooseBusiness.click(saveForm);

		function saveForm() {

			alert('testing...');

			businessNameFld = $("#businessNameFld");
			businessName = $.trim($('#businessNameFld').val());

			emailOfUserFld = $("#emailOfUserFld");
			emailOfUser = $.trim($('#emailOfUserFld').val());

			var choose = {
				"businessName" : businessName
			};

			$.ajax({
				url : "/business/chooseBusiness/" + businessName
						+ "/" + emailOfUser,
				cache : false,
				type : 'POST',
				data : JSON.stringify(choose),
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
					} else if (xhr.status === 200) {
						alert('you have successfully saved');
						window.location.href = '/keepCount/business/entities/'
								+ businessName + '/' + emailOfUser;
					}

				}
			});
		}
	}
})();
