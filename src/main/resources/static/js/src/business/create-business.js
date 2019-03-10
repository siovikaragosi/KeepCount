$(function() {
	$('#btnCreateBusiness').click(saveForm);
});

function saveForm() {

	var emailFld;
	var businessNameFld;

	var urlPOST;

	emailFld = $("#emailFld");
	businessNameFld = $("#businessNameFld");

	var email = $.trim($('#emailFld').val());
	var businessName = $.trim($('#businessNameFld').val());

	// Check if empty of not
	if (email === '') {
		alert('Text-field email is empty.');
		return false;
	}

	if (businessNameFld === '' || businessName === '--') {
		alert('Please select a business type.');
		return false;
	}

	$.ajax({
		method : "POST",
		url : "/keepCount/business/createBusiness",
		data : $('#create_business_form').serialize()
	// success : goToURL()
	});

	function goToURL() {
		location.href = '/keepCount/signUp/after-sign-up-success';
	}
}