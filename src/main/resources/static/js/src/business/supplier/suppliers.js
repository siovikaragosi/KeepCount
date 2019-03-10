/*
 * 
 * Declarations for supplier components in the suppliers.html file
 * 
 * */

var btnSaveSupplier;
var btnSaveSupplierChanges;

var supplierNameFld;
var supplierName;

var supplierPhoneNumberFld;
var supplierPhoneNumber;

var supplierEmailFld;
var supplierEmail;

var supplierLocationFld;
var supplierLocation;

var supplierWebsiteFld;
var supplierWebsite;

function saveNewSupplierInfo() {

	var businessNameFld;
	var businessName;

	var emailOfUserFld;
	var emailOfUser;

	businessNameFld = $( "#businessNameFld" );
	businessName = $.trim( $( '#businessNameFld' ).val() );

	emailOfUserFld = $( "#emailOfUserFld" );
	emailOfUser = $.trim( $( '#emailOfUserFld' ).val() );

	supplierNameFld = $( "#inputSupplierNameId" );
	supplierName = $.trim( $( supplierNameFld ).val() );

	supplierPhoneNumberFld = $( "#inputSupplierPhoneNumberId" );
	supplierPhoneNumber = $.trim( $( supplierPhoneNumberFld ).val() );

	supplierEmailFld = $( "#inputSupplierEmailId" );
	supplierEmail = $.trim( $( supplierEmailFld ).val() );

	supplierLocationFld = $( "#inputSupplierLocationId" );
	supplierLocation = $.trim( $( supplierLocationFld ).val() );

	supplierWebsiteFld = $( "#inputSupplierWebsiteId" );
	supplierWebsite = $.trim( $( supplierWebsiteFld ).val() );

	var supplier = {
		"supplierName" : supplierName ,
		"supplierPhoneNumber" : supplierPhoneNumber ,
		"supplierEmail" : supplierEmail ,
		"supplierLocation" : supplierLocation ,
		"supplierWebsite" : supplierWebsite
	};

	if ( supplierName === '' ) {
		alert( 'Supplier name must be specified, otherwise this will not be recorded.' );
		return false;
	}

	if ( supplierPhoneNumber === '' ) {
		alert( 'Supplier phone number must be specified, otherwise this will not be recorded.' );
		return false;
	}

	if ( supplierEmail === '' ) {
		if ( !confirm( 'Supplier email not specified, do you wish to save this info without the supplier email?' ) ) {
			return;
		}

	}

	if ( supplierLocation === '' ) {
		if ( !confirm( 'Supplier location not specified, do you wish to save this info without the supplier location?' ) ) {
			return;
		}

	}

	if ( supplierWebsite === '' ) {
		if ( !confirm( 'Supplier website not specified, do you wish to save this info without the supplier website?' ) ) {
			return;
		}

	}

	var urlPOST = "/api/business/suppliers/" + businessName + "/" + emailOfUser;

	$.ajax( {
		type : "POST" ,
		url : urlPOST ,
		data : JSON.stringify( supplier ) ,
		contentType : 'application/json' ,
		dataType : 'json' ,
		success : function( data , textStatus , xhr ) {
			console.log( xhr.status );
			alert( xhr.status );
			refreshTable();
		} ,
		complete : function( xhr , textStatus ) {
			console.log( xhr.status );
			if ( xhr.status !== 200 ) {
				// alert(xhr.status);
				alert( 'not successful' );
				console.log( xhr.status );
			} else if ( xhr.status === 200 ) {

				clearFields();
				refreshSuppliersTable();

				// alert('you have successfully saved');
				console.log( xhr.status );
				alert( 'successful' );
				// window.location.href =
				// '/keepCount/business/entities/'
				// + businessName + '/' + emailOfUser;
			}

		}
	} );
}

function clearFields() {
	$( "#inputSupplierNameId" ).val( "" );
	$( "#inputSupplierPhoneNumberId" ).val( "" );
	$( "#inputSupplierEmailId" ).val( "" );
	$( "#inputSupplierLocationId" ).val( "" );
	$( "#inputSupplierWebsiteId" ).val( "" );
}

function refreshSuppliersTable() {

	var businessNameFld;
	var businessName;

	var emailOfUserFld;
	var emailOfUser;

	businessNameFld = $( "#businessNameFld" );
	businessName = $.trim( $( '#businessNameFld' ).val() );

	emailOfUserFld = $( "#emailOfUserFld" );
	emailOfUser = $.trim( $( '#emailOfUserFld' ).val() );
	urlGET = "/api/business/suppliers/" + businessName + "/" + emailOfUser + "/allSuppliers";

	console.log( "em:" + emailOfUser );
	console.log( "biz:" + businessName )

	// /api/business/suppliers/{businessName}/{emailOfUser}/allSuppliers

	var tBody = $( '#supplierListTable' ).children( 'tbody' );
	tBody.empty();

	$.ajax( {
		type : "GET" ,
		url : urlGET ,
		success : function( result ) {
			$.each( result , function( i , supplier ) {
				// console.log('test');
				console.log( 'name:' + supplier.supplierName );
				var tr = $( '<tr>' );
				var td = $( '<td hidden="hidden">' );

				td.append( supplier.id );
				tr.append( td );
				tBody.append( tr );

				var td = $( '<td>' );
				td.append( supplier.supplierName );
				tr.append( td );
				// tBody.append(tr);

				var td = $( '<td>' );
				td.append( supplier.supplierPhoneNumber );
				tr.append( td );

				var td = $( '<td>' );
				td.append( supplier.supplierEmail );
				tr.append( td );

				var td = $( '<td>' );
				td.append( supplier.supplierLocation );
				tr.append( td );

				var td = $( '<td>' );
				td.append( supplier.supplierWebsite );
				tr.append( td );
				//
				// var td = $( '<td><span class="glyphicon glyphicon-remove btn
				// btn-default"></span></td>' );
				// td.click( deleteSupplier );
				// td.attr( "id" , supplier.id );
				// tr.append( td );
				//
				// var td = $( '<td><a href="#" id="scroll"></a><span
				// class="glyphicon glyphicon-pencil btnSelect btn btn-default"
				// type="button"></span></td>' );
				// tr.append( td );

				tBody.append( tr );

			} );
			$( "#supplierListTable tbody tr:odd" ).addClass( "info" );
			$( "#supplierListTable tbody tr:even" ).addClass( "success" );
		}
	} );
}

/*
 * ( function() { jQuery( init );
 * 
 * function init() {
 * 
 * refreshTable();
 * 
 * btnSaveSupplier = $( "#btnSaveSupplier" ); btnSaveSupplier.click(
 * saveSupplierInfo );
 * 
 * btnSaveSupplierChanges = $( "#btnSaveSupplierChanges" );
 * btnSaveSupplierChanges.click( updateSupplierInfo );
 * 
 * function refreshTable() { businessNameFld = $( "#businessNameFld" );
 * businessName = $.trim( $( '#businessNameFld' ).val() );
 * 
 * emailOfUserFld = $( "#emailOfUserFld" ); emailOfUser = $.trim( $(
 * '#emailOfUserFld' ).val() ); urlGET = "/api/business/suppliers/" +
 * businessName + "/" + emailOfUser + "/allSuppliers"; //
 * /api/business/suppliers/{businessName}/{emailOfUser}/allSuppliers
 * 
 * var tBody = $( '#createSupplierTable' ).children( 'tbody' ); tBody.empty(); $
 * .ajax( { type : "GET" , url : urlGET , success : function( result ) { $
 * .each( result , function( i , supplier ) { // console.log('test'); //
 * console.log('name:' + // supplier.supplierName); var tr = $( '<tr>' ); var
 * td = $( '<td hidden="hidden">' );
 * 
 * td.append( supplier.id ); tr.append( td ); tBody.append( tr );
 * 
 * var td = $( '<td>' ); td.append( supplier.supplierName ); tr.append( td ); //
 * tBody.append(tr);
 * 
 * var td = $( '<td>' ); td.append( supplier.supplierPhoneNumber ); tr.append(
 * td );
 * 
 * var td = $( '<td>' ); td.append( supplier.supplierEmail ); tr.append( td );
 * 
 * var td = $( '<td>' ); td.append( supplier.supplierLocation ); tr.append( td );
 * 
 * var td = $( '<td>' ); td.append( supplier.supplierWebsite ); tr.append( td );
 * 
 * var td = $( '<td><span class="glyphicon glyphicon-remove btn btn-default"></span></td>' );
 * td.click( deleteSupplier ); td.attr( "id" , supplier.id ); tr.append( td );
 * 
 * var td = $( '<td><a href="#" id="scroll"></a><span class="glyphicon
 * glyphicon-pencil btnSelect btn btn-default" type="button"></span></td>' );
 * tr.append( td );
 * 
 * tBody.append( tr ); } ); $( "#createSupplierTable tbody tr:odd" ).addClass(
 * "info" ); $( "#createSupplierTable tbody tr:even" ).addClass( "success" ); } } ); }
 * 
 * function deleteSupplier( event ) { var td = $( event.currentTarget ); var id =
 * td.attr( 'id' ); urlGET = "/api/business/suppliers/" + businessName + "/" +
 * emailOfUser + "/" + id + "/delete";
 * 
 * if ( !confirm( 'Delete this supplier permanently?' ) ) return;
 * 
 * if ( !confirm( 'Please stay warned once again\nAre you sure you want to
 * delete this supplier permanently?' ) ) return;
 * 
 * $.ajax( { url : urlGET , type : 'delete' , success : refreshTable } ); // end
 * ajax }
 * 
 * function updateSupplierInfo() {
 * 
 * businessNameFld = $( "#businessNameFld" ); businessName = $.trim( $(
 * '#businessNameFld' ).val() );
 * 
 * emailOfUserFld = $( "#emailOfUserFld" ); emailOfUser = $.trim( $(
 * '#emailOfUserFld' ).val() );
 * 
 * supplierNameFld = $( "#supplierNameFld" ); supplierName = $.trim( $(
 * '#supplierNameFld' ).val() );
 * 
 * supplierPhoneNumberFld = $( "#supplierPhoneNumberFld" ); supplierPhoneNumber =
 * $.trim( $( '#supplierPhoneNumberFld' ).val() );
 * 
 * supplierEmailFld = $( "#supplierEmailFld" ); supplierEmail = $.trim( $(
 * '#supplierEmailFld' ).val() );
 * 
 * supplierLocationFld = $( "#supplierLocationFld" ); supplierLocation = $.trim( $(
 * '#supplierLocationFld' ).val() );
 * 
 * supplierWebsiteFld = $( "#supplierWebsiteFld" ); supplierWebsite = $.trim( $(
 * '#supplierWebsiteFld' ).val() );
 * 
 * 
 * alert('name: ' + supplierName + "\n" + 'tel: ' + supplierPhoneNumber + "\n" +
 * 'email: ' + supplierEmail + "\n" + 'location: ' + supplierLocation + "\n" +
 * 'website: ' + supplierWebsite);
 * 
 * 
 * var supplierIdFld = $( "#supplierIdFld" ); var supplierId = $.trim( $(
 * '#supplierIdFld' ).val() );
 * 
 * var supplier = { "supplierName" : supplierName , "supplierPhoneNumber" :
 * supplierPhoneNumber , "supplierEmail" : supplierEmail , "supplierLocation" :
 * supplierLocation , "supplierWebsite" : supplierWebsite }; //
 * /api/business/{businessName}/{emailOfUser}/{id}/update
 * 
 * urlPUT = "/api/business/suppliers/" + businessName + "/" + emailOfUser + "/" +
 * supplierId + "/update";
 * 
 * $.ajax( { type : "PUT" , url : urlPUT , data : JSON.stringify( supplier ) ,
 * contentType : 'application/json' , dataType : 'json' , success : function(
 * data , textStatus , xhr ) { console.log( xhr.status ); alert( xhr.status );
 * refreshTable(); } , complete : function( xhr , textStatus ) { console.log(
 * xhr.status ); if ( xhr.status !== 200 ) { // alert(xhr.status); alert( 'not
 * successful' ); console.log( xhr.status ); } else if ( xhr.status === 200 ) { //
 * alert('you have successfully saved'); console.log( xhr.status ); alert(
 * 'successful' ); refreshTable(); clearFields(); // window.location.href = //
 * '/keepCount/business/entities/' // + businessName + '/' + emailOfUser; } } } ); }
 * 
 * function saveSupplierInfo() {
 * 
 * businessNameFld = $( "#businessNameFld" ); businessName = $.trim( $(
 * '#businessNameFld' ).val() );
 * 
 * emailOfUserFld = $( "#emailOfUserFld" ); emailOfUser = $.trim( $(
 * '#emailOfUserFld' ).val() );
 * 
 * supplierNameFld = $( "#supplierNameFld" ); supplierName = $.trim( $(
 * '#supplierNameFld' ).val() );
 * 
 * supplierPhoneNumberFld = $( "#supplierPhoneNumberFld" ); supplierPhoneNumber =
 * $.trim( $( '#supplierPhoneNumberFld' ).val() );
 * 
 * supplierEmailFld = $( "#supplierEmailFld" ); supplierEmail = $.trim( $(
 * '#supplierEmailFld' ).val() );
 * 
 * supplierLocationFld = $( "#supplierLocationFld" ); supplierLocation = $.trim( $(
 * '#supplierLocationFld' ).val() );
 * 
 * supplierWebsiteFld = $( "#supplierWebsiteFld" ); supplierWebsite = $.trim( $(
 * '#supplierWebsiteFld' ).val() );
 * 
 * var supplier = { "supplierName" : supplierName , "supplierPhoneNumber" :
 * supplierPhoneNumber , "supplierEmail" : supplierEmail , "supplierLocation" :
 * supplierLocation , "supplierWebsite" : supplierWebsite };
 * 
 * if ( supplierName === '' ) { alert( 'Supplier name must be specified,
 * otherwise this will not be recorded.' ); return false; }
 * 
 * if ( supplierPhoneNumber === '' ) { alert( 'Supplier phone number must be
 * specified, otherwise this will not be recorded.' ); return false; }
 * 
 * if ( supplierEmail === '' ) { if ( !confirm( 'Supplier email not specified,
 * do you wish to save this info without the supplier email?' ) ) { return; } }
 * 
 * if ( supplierLocation === '' ) { if ( !confirm( 'Supplier location not
 * specified, do you wish to save this info without the supplier location?' ) ) {
 * return; } }
 * 
 * if ( supplierWebsite === '' ) { if ( !confirm( 'Supplier website not
 * specified, do you wish to save this info without the supplier website?' ) ) {
 * return; } }
 * 
 * var urlPOST = "/api/business/suppliers/" + businessName + "/" + emailOfUser;
 * 
 * $.ajax( { type : "POST" , url : urlPOST , data : JSON.stringify( supplier ) ,
 * contentType : 'application/json' , dataType : 'json' , success : function(
 * data , textStatus , xhr ) { console.log( xhr.status ); alert( xhr.status );
 * refreshTable(); } , complete : function( xhr , textStatus ) { console.log(
 * xhr.status ); if ( xhr.status !== 200 ) { // alert(xhr.status); alert( 'not
 * successful' ); console.log( xhr.status ); } else if ( xhr.status === 200 ) { //
 * alert('you have successfully saved'); console.log( xhr.status ); alert(
 * 'successful' ); refreshTable(); clearFields(); // window.location.href = //
 * '/keepCount/business/entities/' // + businessName + '/' + emailOfUser; } } } ); }
 * 
 * function clearFields() { $( "#inputSupplierNameId" ).val( "" ); $(
 * "#inputSupplierPhoneNumberId" ).val( "" ); $( "#inputSupplierEmailId" ).val( "" ); $(
 * "#inputSupplierLocationId" ).val( "" ); $( "#inputSupplierWebsiteId" ).val( "" ); } } }
 * )();
 * 
 */