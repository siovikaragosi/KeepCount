function findAllSignUps () {
	var urlAllSignUps = "/api/findAllSignUps";

	$.ajax ( {
		type : "GET" ,
		url : urlAllSignUps ,
		success : function ( result ) {

			// checkIfEmailExists();

			$.each ( result , function ( i , cartList ) {

			} );
		}
	} );

}

/*
 * 
 * checking if the email provided exists in the system
 * 
 * 
 */

function popup ( title , message , hideOK , hideClose , OKText , closeText ) {

	var modal = $ ( '#myModal' );
	modal.find ( '.modal-body p i strong' ).text ( message );
	modal.find ( '.modal-header h4' ).html ( title );

	if ( hideOK === 'hideOk' ) {
		modal.find ( '.modal-footer #OK' ).hide ();
	}
	else
		if ( hideOK === 'showOk' ) {

			modal.find ( '.modal-footer #OK' ).text ( OKText );
			modal.find ( '.modal-footer #OK' ).show;

		}

	if ( hideClose === 'hideDismiss' ) {

		modal.find ( '.modal-footer #dismiss' ).hide ();

	}
	else
		if ( hideClose === 'showDismiss' ) {

			modal.find ( '.modal-footer #dismiss' ).text ( closeText );
			modal.find ( '.modal-footer #dismiss' ).show;

		}

	$ ( '#myModal' ).modal ( 'show' );

}

function checkIfEmailExists () {

	var email = $ ( '#inputSignUpEmail' ).val ();

	if ( email === '' ) {

		popup ( "empty email!!!" , "Please specify your email" , "hideOk" , "showDismiss" , "" , "OK" );

		return;
	}

	var signUp = {
		"email" : email
	}

	findAllSignUps ();

	var urlCheck = "/api/checkEmailExistence";

	$.ajax ( {
		url : urlCheck ,
		type : 'POST' ,
		data : JSON.stringify ( signUp ) ,
		contentType : 'application/json' ,
		dataType : 'json' ,
		async : false ,
		success : function ( data , textStatus , xhr ) {
			console.log ( xhr.status );
			// alert(xhr.status);
			// refreshTable();
		} ,
		complete : function ( xhr , textStatus ) {
			console.log ( xhr.status );
			if ( xhr.status !== 200 ) {
				// alert(xhr.status);
				console.log ( xhr.status );
			}
			else
				if ( xhr.status === 200 ) {
					// alert(xhr.status);

					console.log ( xhr.status );

					returnEmailExistenceMessage ();

				}

		}

	} );

}

function returnEmailExistenceMessage () {
	var urlResult = "/api/returnEmailExistenceMessage";

	$.ajax ( {
		type : 'GET' ,
		url : urlResult ,
		async : false ,
		success : function ( result ) {
			$.each ( result , function ( i , m ) {
				if ( m.email === 'This email is already signed up' ) {
					console.log ( m.email )
					console.log ( 'OK' );

					// title , message , hideOK , hideClose , OKText , closeText

					popup ( 'Info' , 'This email is already signed up' , 'hideOk' , 'showDismiss' , '' , 'close' );

					return;

				}
				else
					if ( m.email === 'good to go' ) {
						// alert ( "not signed" );

						signUp ();

					}

			} );

		} ,
		error : function ( e ) {
			alert ( "ERROR: " , e );
			console.log ( "ERROR: " , e );
		}
	} );

}

function signUp () {

	alert ( "signup...." );

	var urlPOST = "/api/signup";

	var email = $ ( '#inputSignUpEmail' ).val ();
	var password = $ ( '#inputSignPassword' ).val ();

	if ( email === '' ) {

		popup ( 'Empty email' , 'Please specify your email' , 'hideOk' , 'showDismiss' , '' , 'close' );

		return;
	}

	if ( password === '' ) {

		popup ( 'Empty password' , 'Please set your password' , 'hideOk' , 'showDismiss' , '' , 'close' );

		return;
	}

	var signUp = {
		"email" : email ,
		"password" : password
	}

	$.ajax ( {

		url : urlPOST ,
		type : 'POST' ,
		data : JSON.stringify ( signUp ) ,
		contentType : 'application/json' ,
		dataType : 'json' ,
		async : false ,
		success : function ( data , textStatus , xhr ) {
			console.log ( "post sign up: " + xhr.status );
		} ,

		complete : function ( xhr , textStatus ) {

			if ( xhr.statuc !== 200 ) {
				console.log ( "sign up status: " + xhr.status );
				returnSignUpMessage ();
			}

			else
				if ( xhr.status === 200 ) {
					console.log ( "sign up status: " + xhr.status );
					returnSignUpMessage ();
				}

		}
	} );

}

function returnSignUpMessage () {

	var urlResult = "/api/returnSignUpMessage";

	$.ajax ( {
		type : 'GET' ,
		url : urlResult ,
		async : false ,
		success : function ( result ) {
			$.each ( result , function ( i , m ) {

				if ( m.email === 'Sign up complete, please login to get started' ) {

					console.log ( m.email )
					console.log ( 'OK' );

					popup ( 'Info' , 'Signup successful' , 'hideOk' , 'showDismiss' , '' , 'close' );

				}

				else
					if ( m.email === 'Sign up failed. Try again' ) {

						popup ( 'Error' , 'Signup failed, re-check your email and internet connection' , 'hideOk' , 'showDismiss' , '' , 'close' );

						return;

					}

			} );

		} ,
		error : function ( e ) {
			alert ( "ERROR: " , e );
			console.log ( "ERROR: " , e );
		}
	} );

}

function validateEmail () {

	var urlPOST = "/api/signup/validateEmail";

	var email = $ ( '#inputSignUpEmail' ).val ();

	var signUp = {
		"email" : email
	}

	$.ajax ( {

		url : urlPOST ,
		type : 'POST' ,
		data : JSON.stringify ( signUp ) ,
		contentType : 'application/json' ,
		dataType : 'json' ,
		async : false ,
		success : function ( data , textStatus , xhr ) {

			console.lgo ( 'post validation of email: ' + xhr.status );

		} ,

		complete : function ( xhr , textStatus ) {

			if ( xhr.status !== 200 ) {

				console.log ( "post validation of email: " + xhr.status );

				returnEmailValidationMessage ();

			}
			else
				if ( xhr.status === 200 ) {
					console.log ( 'post validation of email: ' + xhr.status );

					// testTest ();

					// returnEmailValidationMessage ();
					returnMessageAfterValidatingEmail ();
					returnEmailValidationMessage ();
				}

		}

	} );

}

function testTest () {
	alert ( "return email...." );

}

function returnMessageAfterValidatingEmail () {
	alert ( "return email...." );

	var urlResult = "/api/returnEmailValidationMessage";

	$.ajax ( {

		type : 'GET' ,
		url : urlResult ,
		async : false ,
		success : function ( result ) {
			$.each ( result , function ( i , m ) {

				if ( m.email === 'Please re-check your email.' ) {

					console.log ( m.email );

					popup ( 'Email error' , 'Please re-check your email' , 'hideOk' , 'showDismiss' , '' , 'close' );

					return;

				}

				else
					if ( m.email === 'Email validated.' ) {

						popup ( 'Info' , 'email validated. Correct' , 'hideOk' , 'showDismiss' , '' , 'close' );

						checkIfEmailExists ();

					}

			} );

		} ,
		error : function ( e ) {
			alert ( "ERROR: " , e );
			console.log ( "ERROR: " , e );
		}
	} );

}

function returnEmailValidationMessage () {

	alert ( "return email...." );

	// var urlResult = "/api/returnEmailValidationMessage";
	//
	// $.ajax ( {
	//
	// type : 'GET' ,
	// url : urlResult ,
	// async : false ,
	// success : function ( result ) {
	// $.each ( result , function ( i , m ) {
	//
	// if ( m.email === 'Please re-check your email.' ) {
	//
	// console.log ( m.email );
	//
	// popup ( 'Email error' , 'Please re-check your email' , 'hideOk' , 'showDismiss' , '' , 'close' );
	//
	// return;
	//
	// }
	//
	// else
	// if ( m.email === 'Email validated.' ) {
	//
	// popup ( 'Info' , 'email validated. Correct' , 'hideOk' , 'showDismiss' , '' , 'close' );
	//
	// checkIfEmailExists ();
	//
	// }
	//
	// } );
	//
	// } ,
	// error : function ( e ) {
	// alert ( "ERROR: " , e );
	// console.log ( "ERROR: " , e );
	// }
	// } );

}
