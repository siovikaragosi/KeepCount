/* sign up elements and components */

( function() {
	jQuery( init );

	var c;

	function init() {

		// All these commented below tried to get all elements bu the
		// uncommented superpowered them all

		/*
		 * var c = document.body.childNodes;
		 * 
		 * for ( i = 0 ; i < c.length ; i++ ) { console.log( "body tags: " + c [
		 * i ].id ); }
		 * 
		 * var t = document.getElementById( 'id_body' ).childNodes; for ( i = 0 ;
		 * i < t.length ; i++ ) alert( t [ i ].id );
		 */

		var x = document.getElementById( "id_body" ).querySelectorAll( "*" );

		for ( y = 0 ; y < x.length ; y++ ) {
			console.log( "....: " + x [ y ].id );
		}

		// var c = .querySelectorAll( "*" );

		//		
		// $( '#body' ).children.each( function() {
		// alert( $( 'id' ) );
		// } );

	}
} )();

function chooseLanguageSignUp() {

	var urlLanguage = "/api/languageAtSignUp/";

	$.ajax( {
		type : "GET" ,
		url : urlLanguage ,
		success : function( result ) {
			$.each( result , function( i , langChoose ) {

			} );
		}
	} );

}
