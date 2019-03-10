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

function reShowTheNePurchasesTwoPopUp() {

	$( '#addNewPurchaseModal' ).modal( 'show' );

}