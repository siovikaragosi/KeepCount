function refreshNumberFormatList() {
	var urlGETNumberFormat = "/api/business/numberFormats/language";

	$.ajax( {
		type : "GET" ,
		url : urlGETNumberFormat ,
		async : false ,
		success : function( result ) {

			$( '#numberFormatFld' ).empty();

			$.each( result , function( i , purhcasesNumberFormat ) {

				console.log( purhcasesNumberFormat.numberFormatName );
				$( '#numberFormatFld' ).append(
						'<option value=' + purhcasesNumberFormat.numberFormatName + '>'
								+ purhcasesNumberFormat.numberFormatName + '</option>' )
			} );

		} ,
		error : function( e ) {
			alert( "ERROR: " , e );
			console.log( "ERROR: " , e );
		}
	} );
}
