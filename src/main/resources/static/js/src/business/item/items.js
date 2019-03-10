( function() {
	jQuery( init );

	var urlGET;

	var businessNameFld;
	var businessName;

	var emailOfUserFld;
	var emailOfUser;

	var itemNameFld;
	var itemName;
	var itemNameUpdateFld;
	var itemNameUpdate;

	var itemCategoryFld;
	var itemCategory;
	var itemCategoryUpdateFld;
	var itemCategoryUpdate;

	var itemSubCategoryFld;
	var itemSubCategory;
	var itemSubCategoryUpdateFld;
	var itemSubCategoryUpdate;

	var itemDescriptionFld;
	var itemDescription;
	var itemDescriptionUpdateFld;
	var itemDescriptionUpdate;

	var itemSamplePhotoOneURLFld;
	var itemSamplePhotoOneURL;
	var itemSamplePhotoOneURLUpdateFld;
	var itemSamplePhotoOneURLUpdate;

	var itemSamplePhotoTwoURLFld;
	var itemSamplePhotoTwoURL;
	var itemSamplePhotoTwoURLUpdateFld;
	var itemSamplePhotoTwoURLUpdate;

	var itemSamplePhotoThreeURLFld;
	var itemSamplePhotoThreeURL;
	var itemSamplePhotoThreeURLUpdateFld;
	var itemSamplePhotoThreeURLUpdate;

	var previousSamplePhotoOne;
	var previousSamplePhotoOneUpdate;

	var previousSamplePhotoTwo;
	var previousSamplePhotoTwoUpdate;

	var previousSamplePhotoThree;
	var previousSamplePhotoThreeUpdate;

	var btnSaveItem;
	var btnSaveItemChanges;

	function init() {

		refreshTable();

		btnSaveItem = $( "#btnSaveItem" );
		btnSaveItem.click( saveItemInfo );

		btnSaveItemChanges = $( "#btnSaveItemChanges" );
		btnSaveItemChanges.click( updateItem );

		function deleteItem( event ) {
			var td = $( event.currentTarget );
			var id = td.attr( 'id' );

			urlDeleteItem = "/api/business/items/" + businessName + "/" + emailOfUser + "/" + id + "/delete";
			if ( !confirm( 'Delete this item permanently?' ) )
				return;

			if ( !confirm( 'Please stay warned once again\nAre you sure you want to DELETE this item permanently?' ) )
				return;

			$.ajax( {
				url : urlDeleteItem ,
				type : 'delete' ,
				success : refreshTable
			} );
			// end ajax
		}

		function updateItem() {
			businessNameFld = $( "#businessNameFld" );
			businessName = $.trim( $( '#businessNameFld' ).val() );

			emailOfUserFld = $( "#emailOfUserFld" );
			emailOfUser = $.trim( $( '#emailOfUserFld' ).val() );

			itemNameUpdateFld = $( "#itemNameUpdateFld" );
			itemNameUpdate = $.trim( $( '#itemNameUpdateFld' ).val() );

			itemCategoryUpdateFld = $( "#itemCategoryUpdateFld" );
			itemCategoryUpdate = $.trim( $( '#itemCategoryUpdateFld' ).val() );

			itemSubCategoryUpdateFld = $( "#itemSubCategoryUpdateFld" );
			itemSubCategoryUpdate = $.trim( $( '#itemSubCategoryUpdateFld' ).val() );

			itemDescriptionUpdateFld = $( "#itemDescriptionUpdateFld" );
			itemDescriptionUpdate = $.trim( $( '#itemDescriptionUpdateFld' ).val() );

			itemSamplePhotoOneURLUpdateFld = $( "#itemSamplePhotoOneURLUpdateFld" );
			itemSamplePhotoOneURLUpdate = $.trim( $( '#itemSamplePhotoOneURLUpdateFld' ).val() );

			itemSamplePhotoTwoURLUpdateFld = $( "#itemSamplePhotoTwoURLUpdateFld" );
			itemSamplePhotoTwoURLUpdate = $.trim( $( '#itemSamplePhotoTwoURLUpdateFld' ).val() );

			itemSamplePhotoThreeURLUpdateFld = $( "#itemSamplePhotoThreeURLUpdateFld" );
			itemSamplePhotoThreeURLUpdate = $.trim( $( '#itemSamplePhotoThreeURLUpdateFld' ).val() );

			previousSamplePhotoOneUpdate = $.trim( $( '#previousItemSamplePhotoOneURLUpdateFld' ).val() );

			if ( ( itemSamplePhotoOneURLUpdate === null || itemSamplePhotoOneURLUpdate === '' )
					&& ( previousSamplePhotoOneUpdate === null || previousSamplePhotoOneUpdate === '' ) ) {
				if ( confirm( 'sample photo 1. Are you sure you do not want a sample photo 1?' ) ) {
					return;
				}
				itemSamplePhotoOneURL = null;
			} else if ( ( itemSamplePhotoOneURLUpdate === null || itemSamplePhotoOneURLUpdate === '' )
					&& ( previousSamplePhotoOneUpdate !== null && previousSamplePhotoOneUpdate !== '' ) ) {
				if ( confirm( 'sample photo 1 was not changed. Do you maintain the previous photo?' ) ) {
					return;
				}
				itemSamplePhotoOneURLUpdate = previousSamplePhotoOneUpdate;
			}

			previousSamplePhotoTwoUpdate = $.trim( $( '#previousItemSamplePhotoTwoURLUpdateFld' ).val() );

			if ( ( itemSamplePhotoTwoURLUpdate === null || itemSamplePhotoTwoURLUpdate === '' )
					&& ( previousSamplePhotoTwoUpdate === null || previousSamplePhotoTwoUpdate === '' ) ) {
				if ( !confirm( 'sample photo 2. Are you sure you do not want a sample photo 2?' ) ) {
					return;
				}
				itemSamplePhotoTwoURLUpdate = null;
			} else if ( ( itemSamplePhotoTwoURLUpdate === null || itemSamplePhotoTwoURLUpdate === '' )
					&& ( previousSamplePhotoTwoUpdate !== null && previousSamplePhotoTwoUpdate !== '' ) ) {
				if ( confirm( 'sample photo 2 was not changed. Do you maintain the previous photo?' ) ) {
					return;
				}
				itemSamplePhotoTwoURLUpdate = previousSamplePhotoTwoUpdate;
			}

			previousSamplePhotoThreeUpdate = $.trim( $( '#previousItemSamplePhotoThreeURLUpdateFld' ).val() );

			if ( ( itemSamplePhotoThreeURLUpdate === null || itemSamplePhotoThreeURLUpdate === '' )
					&& ( previousSamplePhotoThreeUpdate === null || previousSamplePhotoThreeUpdate === '' ) ) {
				if ( !confirm( 'sample photo 3. Are you sure you do not want a sample photo 3?' ) ) {
					return;
				}
				itemSamplePhotoThreeURLUpdate = null;
			} else if ( ( itemSamplePhotoThreeURLUpdate === null || itemSamplePhotoThreeURLUpdate === '' )
					&& ( previousSamplePhotoThreeUpdate !== null && previousSamplePhotoThreeUpdate !== '' ) ) {
				if ( confirm( 'sample photo 3 was not changed. Do you maintain the previous photo?' ) ) {
					return;
				}
				itemSamplePhotoThreeURLUpdate = previousSamplePhotoThreeUpdate;
			}

			var itemId = $.trim( $( '#itemIdFld' ).val() );
			if ( itemId === '' || itemId === null ) {
				alert( 'You must choose from the table what to be edited' );
				return;
			}

			if ( itemName === "" ) {
				alert( 'The item name can not be empty.\nPlease provide a name' );
				return;
			}

			var item = {
				"itemName" : itemNameUpdate ,
				"itemCategory" : itemCategoryUpdate ,
				"itemSubCategory" : itemSubCategoryUpdate ,
				"itemDescription" : itemDescriptionUpdate ,
				"itemSamplePhotoOne" : itemSamplePhotoOneURLUpdate ,
				"itemSamplePhotoTwo" : itemSamplePhotoTwoURLUpdate ,
				"itemSamplePhotoThree" : itemSamplePhotoThreeURLUpdate
			};

			var urlPUT = "/api/business/items/" + businessName + "/" + emailOfUser + "/" + itemId + "/update";

			$.ajax( {
				url : urlPUT ,
				type : 'PUT' ,

				data : JSON.stringify( item ) ,
				// data : $('#choose_business_form').serialize(),
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
						alert( xhr.status );
						console.log( xhr.status );
					} else if ( xhr.status === 200 ) {
						alert( 'you have successfully updated use info' );
						console.log( xhr.status );
						refreshTable();
						clearFields();
					}

				}
			} );
			// end ajax
		}

		function refreshTable() {

			businessNameFld = $( "#businessNameFld" );
			businessName = $.trim( $( '#businessNameFld' ).val() );

			emailOfUserFld = $( "#emailOfUserFld" );
			emailOfUser = $.trim( $( '#emailOfUserFld' ).val() );
			urlGET = "/api/business/items/" + businessName + "/" + emailOfUser + "/allItems";

			var tBody = $( '#createItemTable' ).children( 'tbody' );

			tBody.empty();

			$
					.ajax( {
						type : "GET" ,
						url : urlGET ,
						success : function( result ) {
							$
									.each(
											result ,
											function( i , item ) {
												// console.log(item.itemFirstName);

												// 1
												// Getting the item name
												var tr = $( '<tr>' );
												var td = $( '<td>' );
												td.append( item.itemName );
												tr.append( td );
												tBody.append( tr );

												// 2
												// Getting the item category
												var td = $( '<td>' );
												td.append( item.itemCategory );
												tr.append( td );

												// 3
												// Getting the item sub category
												var td = $( '<td>' );
												td.append( item.itemSubCategory );
												tr.append( td );

												// 4
												// Getting the item description
												var td = $( '<td>' );
												td.append( item.itemDescription );
												tr.append( td );

												// 5
												// Getting the item sample photo
												// one
												var img = '<img src="'
														+ item.itemSamplePhotoOne
														+ '" class="img-rounded nr" style="width=100px; height:120px" />'

												var td = $( '<td>' );
												td.append( img );
												tr.append( td );

												/* 6 */

												/*
												 * Getting the item sample photo
												 * one text (Base64)
												 */
												var td = $( '<td hidden="hidden"></td>' );
												td.append( item.itemSamplePhotoOne );
												tr.append( td );

												// 7
												var img = '<img src="'
														+ item.itemSamplePhotoTwo
														+ '" class="img-rounded nr" style="width=100px; height:120px" />'

												var td = $( '<td>' );
												td.append( img );
												tr.append( td );

												/* 8 */
												/*
												 * Getting the item sample photo
												 * two text (Base64)
												 */
												var td = $( '<td hidden="hidden"></td>' );
												td.append( item.itemSamplePhotoTwo );
												tr.append( td );

												// 9
												var img = '<img src="'
														+ item.itemSamplePhotoThree
														+ '" class="img-rounded nr" style="width=100px; height:120px" />'

												var td = $( '<td>' );
												td.append( img );
												tr.append( td );

												/* 10 */
												/*
												 * Getting the item sample photo
												 * Three text (Base64)
												 */
												var td = $( '<td hidden="hidden"></td>' );
												td.append( item.itemSamplePhotoThree );
												tr.append( td );

												// 11
												var td = $( '<td hidden="hidden"></td>' );
												td.append( item.id );
												tr.append( td );

												var td = $( '<td><span class="fa fa-remove btn btn-danger" type="button"></span></td>' );
												td.click( deleteItem );
												td.attr( "id" , item.id );
												tr.append( td );

												var td = $( '<td><a href="#" id="scroll"></a><span data-toggle="modal" data-target="#updateSelectedItemModal" class="fa fa-pencil btnSelect btn btn-success" type="button"></span></td>' );
												// td.click(selectForUpdate);
												// td.attr("id", user.id);
												tr.append( td );

												var td = $( '<td><button type="button" id="btnSetMeasurementUnit" class="btn btn-default" data-toggle="modal" data-target="#setMeasurementUnit">Set measurement unit</button></td>' );
												td.click( populateItemNameForSettingMeasurementUnit );
												td.attr( "subCat" , item.itemSubCategory );
												td.attr( "itemId" , item.id );
												tr.append( td );

												tBody.append( tr );

											} );

							$( "#createItemTable tbody tr:odd" ).addClass( "info" );
							$( "#createItemTable tbody tr:even" ).addClass( "success" );
						}

						,
						error : function( e ) {
							alert( "ERROR: " , e );
							console.log( "ERROR: " , e );
						}
					} );

			window.history.pushState( "object or string" , "Title" , "/business/items/" + businessName + "/"
					+ emailOfUser );
			// /business/items/{businessName}/{emailOfUser}
		}

		function populateItemNameForSettingMeasurementUnit( event ) {
			var td = $( event.currentTarget );
			var subCat = td.attr( 'subCat' );
			$( "#idItemNameMeasurementUnit" ).text( subCat );

			var td2 = $( event.currentTarget );
			var id = td.attr( 'itemId' );
			// $("#idItemNameMeasurementUnit").text(subCat);
			$( "#UnitItemIdFld" ).val( id );
			// alert(id);
		}

		function saveItemInfo() {

			businessNameFld = $( "#businessNameFld" );
			businessName = $.trim( $( '#businessNameFld' ).val() );

			emailOfUserFld = $( "#emailOfUserFld" );
			emailOfUser = $.trim( $( '#emailOfUserFld' ).val() );

			itemNameFld = $( "#itemNameFld" );
			itemName = $.trim( $( '#itemNameFld' ).val() );

			itemCategoryFld = $( "#itemCategoryFld" );
			itemCategory = $.trim( $( '#itemCategoryFld' ).val() );

			itemSubCategoryFld = $( "#itemSubCategoryFld" );
			itemSubCategory = $.trim( $( '#itemSubCategoryFld' ).val() );

			itemDescriptionFld = $( "#itemDescriptionFld" );
			itemDescription = $.trim( $( '#itemDescriptionFld' ).val() );

			itemSamplePhotoOneURLFld = $( "#itemSamplePhotoOneURLFld" );
			itemSamplePhotoOneURL = $.trim( $( '#itemSamplePhotoOneURLFld' ).val() );

			itemSamplePhotoTwoURLFld = $( "#itemSamplePhotoTwoURLFld" );
			itemSamplePhotoTwoURL = $.trim( $( '#itemSamplePhotoTwoURLFld' ).val() );

			itemSamplePhotoThreeURLFld = $( "#itemSamplePhotoThreeURLFld" );
			itemSamplePhotoThreeURL = $.trim( $( '#itemSamplePhotoThreeURLFld' ).val() );

			if ( itemSamplePhotoOneURL === '' ) {

				if ( !confirm( 'Sample photo 1 not chosen. Do you want to continue without it?' ) )
					return;

				itemSamplePhotoOneURL = null;

			}

			if ( itemSamplePhotoTwoURL === '' ) {

				if ( !confirm( 'Sample photo 2 not chosen. Do you want to continue without it?' ) )
					return;

				itemSamplePhotoTwoURL = null;

			}

			if ( itemSamplePhotoThreeURL === '' ) {

				if ( !confirm( 'Sample photo 3 not chosen. Do you want to continue without it?' ) )
					return;

				itemSamplePhotoThreeURL = null;

			}

			urlGET = "/api/business/items/" + businessName + "/" + emailOfUser;

			if ( itemName === "" ) {
				alert( 'The item name can not be empty.\nPlease provide a name' );
				return;
			}

			var item = {
				"itemName" : itemName ,
				"itemCategory" : itemCategory ,
				"itemSubCategory" : itemSubCategory ,
				"itemDescription" : itemDescription ,
				"itemSamplePhotoOne" : itemSamplePhotoOneURL ,
				"itemSamplePhotoTwo" : itemSamplePhotoTwoURL ,
				"itemSamplePhotoThree" : itemSamplePhotoThreeURL
			};

			// alert(itemSamplePhotoTwoURL);
			// alert(itemSamplePhotoThreeURL);
			//
			// return;

			$.ajax( {
				url : urlGET ,
				// cache : false,
				type : 'POST' ,
				data : JSON.stringify( item ) ,
				// data : $('#choose_business_form').serialize(),
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
						alert( xhr.status );
						console.log( xhr.status );
					} else if ( xhr.status === 200 ) {
						alert( 'you have successfully saved this item' );
						console.log( xhr.status );
						refreshTable();
						clearFields();
					}

				}

			} );

		}
		function clearFields() {
			$( "#itemNameFld" ).val( "" );
			$( "#itemCategoryFld" ).val( "" );
			$( "#itemSubCategoryFld" ).val( "" );
			$( "#itemDescriptionFld" ).val( "" );

			$( "#itemSamplePhotoOneURLFld" ).val( "" );
			$( '#samplePhotoOnePreview' ).attr( 'src' , null );
			$( "#previousItemSamplePhotoOneURLFld" ).val( "" );

			$( "#itemSamplePhotoTwoURLFld" ).val( "" );
			$( '#samplePhotoTwoPreview' ).attr( 'src' , null );
			$( "#previousItemSamplePhotoTwoURLFld" ).val( "" );

			$( "#itemSamplePhotoThreeURLFld" ).val( "" );
			$( '#samplePhotoThreePreview' ).attr( 'src' , null );
			$( "#previousItemSamplePhotoThreeURLFld" ).val( "" );
		}

	}
} )();