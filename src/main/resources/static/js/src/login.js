function findAllSignUpsForLogin() {
	var urlAllSignUps = "/api/login/findAllSignUps";

	$.ajax( {
		type : "GET" ,
		url : urlAllSignUps ,
		success : function( result ) {

			// checkIfEmailExists();

			$.each( result , function( i , cartList ) {

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

function popup( title , message , hideOK , hideClose , OKText , closeText ) {

	var modal = $( '#myModal' );
	modal.find( '.modal-body p i strong' ).text( message );
	modal.find( '.modal-header h4' ).html( title );

	if ( hideOK === 'hideOk' ) {
		modal.find( '.modal-footer #OK' ).hide();
	} else if ( hideOK === 'showOk' ) {

		modal.find( '.modal-footer #OK' ).text( OKText );
		modal.find( '.modal-footer #OK' ).show;

	}

	if ( hideClose === 'hideDismiss' ) {

		modal.find( '.modal-footer #dismiss' ).hide();

	} else if ( hideClose === 'showDismiss' ) {

		modal.find( '.modal-footer #dismiss' ).text( closeText );
		modal.find( '.modal-footer #dismiss' ).show;

	}

	$( '#myModal' ).modal( 'show' );

}

function checkIfLoginEmailExists() {

	var email = $( '#inputLoginEmail' ).val();

	if ( email === '' ) {

		popup( "empty email!!!" , "Please specify your email" , "hideOk" , "showDismiss" , "" , "OK" );

		return;
	}

	var signUp = {
		"email" : email
	}

	var urlCheck = "/api/login/checkEmailExistence";

	$.ajax( {
		url : urlCheck ,
		type : 'POST' ,
		data : JSON.stringify( signUp ) ,
		contentType : 'application/json' ,
		dataType : 'json' ,
		async : false ,
		success : function( data , textStatus , xhr ) {
			console.log( xhr.status );
			// alert(xhr.status);
			// refreshTable();
		} ,
		complete : function( xhr , textStatus ) {
			console.log( xhr.status );
			if ( xhr.status !== 200 ) {
				// alert(xhr.status);
				console.log( xhr.status );
			} else if ( xhr.status === 200 ) {
				// alert(xhr.status);

				console.log( xhr.status );
				returnLoginEmailExistenceMessage();

			}

		}

	} );

}

function returnLoginEmailExistenceMessage() {

	var urlResult = "/api/login/returnEmailExistenceMessage";

	$.ajax( {

		type : 'GET' ,
		url : urlResult ,
		async : false ,
		success : function( result ) {
			$.each( result , function( i , m ) {

				if ( m.email === 'good to continue to choose business' ) {

					console.log( m.email );

					// popup( 'Email existence message' , 'email is contained' ,
					// 'hideOk' , 'showDismiss' , '' ,
					// 'close' );

					checkIfLoginPasswordExists();

				}

				else if ( m.email === 'email does not exist' ) {

					popup( 'message' , 'this email is not yet signed up, please sign it up to continue' , 'hideOK' ,
							'showDismiss' , '' );

					return;

				}

			} );

		} ,
		error : function( e ) {
			alert( "ERROR: " , e );
			console.log( "ERROR: " , e );
		}
	} );

}

function checkIfLoginPasswordExists() {

	var password = $( '#inputLoginPassword' ).val();

	if ( password === '' ) {

		popup( "empty email!!!" , "Please specify your password" , "hideOk" , "showDismiss" , "" , "OK" );

		return;
	}

	var signUp = {
		"password" : password
	}

	var urlCheck = "/api/login/checkPasswordExistence";

	$.ajax( {
		url : urlCheck ,
		type : 'POST' ,
		data : JSON.stringify( signUp ) ,
		contentType : 'application/json' ,
		dataType : 'json' ,
		async : false ,
		success : function( data , textStatus , xhr ) {
			console.log( xhr.status );
		} ,
		complete : function( xhr , textStatus ) {
			console.log( xhr.status );
			if ( xhr.status !== 200 ) {
				console.log( xhr.status );
			} else if ( xhr.status === 200 ) {

				console.log( xhr.status );

				returnLoginPasswordExistence();

			}

		}

	} );

}

function returnLoginPasswordExistence() {

	var urlResult = "/api/login/returnEmailExistenceMessage";

	$.ajax( {

		type : 'GET' ,
		url : urlResult ,
		async : false ,
		success : function( result ) {

			$.each( result , function( i , m ) {

				if ( m.email === 'Password exists' ) {

					console.log( m.email );

					// popup( 'Password message' , 'password exits' , 'hideOk' ,
					// 'showDismiss' , '' , 'close' );

					var url = "/business/createBusiness";
					window.location.href = url;

				}

				else if ( m.email === 'Password does not exist' ) {

					popup( 'Password message' , 'wrong login credentials. Please try again' , 'hideOk' , 'showDismiss' ,
							'' , 'close' );

					return;

				}

			} );

		} ,
		error : function( e ) {
			alert( "ERROR: " , e );
			console.log( "ERROR: " , e );
		}
	} );

}

function login() {

	var urlPOST = "/api/login";

	var email = $( '#inputLoginEmail' ).val();
	var password = $( '#inputLoginPassword' ).val();

	if ( email === '' ) {

		popup( 'Empty email' , 'Please specify your email' , 'hideOk' , 'showDismiss' , '' , 'close' );

		return;
	}

	if ( password === '' ) {

		popup( 'Empty password' , 'Please set your password' , 'hideOk' , 'showDismiss' , '' , 'close' );

		return;
	}

	var signUp = {
		"email" : email ,
		"password" : password
	}

	$.ajax( {

		url : urlPOST ,
		type : 'POST' ,
		data : JSON.stringify( signUp ) ,
		contentType : 'application/json' ,
		dataType : 'json' ,
		async : false ,
		success : function( data , textStatus , xhr ) {
			console.log( "post sign up: " + xhr.status );
		} ,

		complete : function( xhr , textStatus ) {

			if ( xhr.statuc !== 200 ) {
				console.log( "sign up status: " + xhr.status );
				returnLoginMessage();
			}

			else if ( xhr.status === 200 ) {
				console.log( "sign up status: " + xhr.status );
				returnLoginMessage();
			}

		}
	} );

}

function returnLoginMessage() {

	var urlResult = "/api/returnLoginMessage";

	$.ajax( {
		type : 'GET' ,
		url : urlResult ,
		async : false ,
		success : function( result ) {
			$.each( result , function( i , m ) {

				if ( m.email === 'Sign up complete, please login to get started' ) {

					console.log( m.email )
					console.log( 'OK' );

					popup( 'Info' , 'Signup successful' , 'hideOk' , 'showDismiss' , '' , 'close' );

				}

				else if ( m.email === 'Sign up failed. Try again' ) {

					popup( 'Error' , 'Signup failed, re-check your email and internet connection' , 'hideOk' ,
							'showDismiss' , '' , 'close' );

					return;

				}

			} );

		} ,
		error : function( e ) {
			alert( "ERROR: " , e );
			console.log( "ERROR: " , e );
		}
	} );

}

function validateEmailLogin() {

	var urlPOST = "/api/login/validateEmail";

	var email = $( '#inputLoginEmail' ).val();

	// alert ( "email: " + email );
	// return;

	if ( email === '' ) {

		popup( 'empty email' , 'please specify your email' , 'hideOk' , 'showDismiss' , '' , 'close' );

		return;

	}

	var signUp = {
		"email" : email
	}

	$.ajax( {

		url : urlPOST ,
		type : 'POST' ,
		data : JSON.stringify( signUp ) ,
		contentType : 'application/json' ,
		dataType : 'json' ,
		async : false ,
		success : function( data , textStatus , xhr ) {

			console.log( 'post validation of email: ' + xhr.status );

		} ,

		complete : function( xhr , textStatus ) {

			if ( xhr.status !== 200 ) {

				console.log( "post validation of email: " + xhr.status );

			} else if ( xhr.status === 200 ) {

				// console.log( 'post validation of email: ' + xhr.status );

				// checkIfLoginEmailExists ();

				returnEmailValidationMessage();

			}

		}

	} );

}

function returnEmailValidationMessage() {

	var urlResult = "/api/login/returnEmailValidationMessage";
	$.ajax( {
		type : 'GET' ,
		url : urlResult ,
		async : false ,
		success : function( result ) {
			$.each( result , function( i , m ) {
				if ( m.email !== 'Email validated.' ) {
					// alert( 'no vlid email' );
					// console.log( m.email );

					popup( 'Email error' , 'Please re-check your email' , 'hideOk' , 'showDismiss' , '' , 'close' );

					return;

				}

				else if ( m.email === 'Email validated.' ) {

					// popup ( 'Info' , 'email validated. Correct' , 'hideOk' ,
					// 'showDismiss' , '' , 'close' );

					checkIfLoginEmailExists();

					// checkIfLoginEmailExists ();

				}

			} );

		} ,
		error : function( e ) {
			alert( "ERROR: " , e );
			console.log( "ERROR: " , e );
		}
	} );

}
