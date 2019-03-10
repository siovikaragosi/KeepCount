/*(function() {
	jQuery(init);

	
	 * function init() { alert('hello'); }
	 

	var btnChooseBusiness;

	var businessNameFld;
	var emailOfuserFld;

	function init() {

		addMovieBtn = $("#btnChooseBusiness");
		addMovieBtn.click(saveForm);

		// function findAllMovies() {
		//
		// var url = "http://localhost:8080/api/movie";
		//
		// jQuery.ajax({
		// url : url,
		// success : renderMovies
		// });
		//
		// }

		findAllMovies();

		function saveForm() {

			businessNameFld = $("#businessNameFld");
			emailOfuserFld = $("#emailOfuserFld");

			var businessName = $.trim($('#businessNameFld').val());
			var emailOfuser = $.trim($('#emailOfuserFld').val());

			// var movie = {
			// "poster" : poster,
			// "title" : title,
			// "imdb" : imdb,
			// "plot" : plot
			// };

			$.ajax({
				url : "/keepCount/business/chooseBusiness/" + businessName
						+ "/" + emailOfUser,
				type : 'POST',
				// data : JSON.stringify(movie),
				data : $('#choose_business_form').serialize(),
				contentType : 'application/json',
				dataType : 'json',
				success : goToURL,
			})

			
			 * $( "#addMovieBtn" ).dialog({ dialogClass: "no-close", buttons: [ {
			 * text: "OK", click: function() { $( this ).dialog( "close" ); } } ]
			 * });
			 
		}
		
		function goToURL() {

			location.href = '/keepCount/business/entities/' + businessName + '/'
					+ emailOfUser;
		}

// function renderMovies(movies) {
//
// var tbody = $('tbody');
//
// tbody.empty();
//
// for ( var m in movies) {
// var movie = movies[m];
// // console.log(movie);
//
// var tr = $('<tr>');
// var td = $('<td>');
//
// td.append(movie.poster);
// tr.append(td);
//
// var td = $('<td>');
// td.append(movie.title);
// tr.append(td);
//
// var td = $('<td>');
// td.append(movie.imdbId);
// tr.append(td);
//
// var td = $('<td>');
// td.append(movie.plot);
// tr.append(td);
//
// var td = $('<td><span class="glyphicon glyphicon-remove"></span></td>');
// td.click(deleteMovie);
// td.attr("id", movie.imdbId);
// tr.append(td);
//
// tbody.append(tr);
//
// }
// }

// function deleteMovie(event) {
// var td = $(event.currentTarget);
// var imdbId = td.attr("id");
// $.ajax({
// url : "/api/movie/" + imdbId,
// type : 'delete',
// success : findAllMovies
// });
// }
//
// }

}*/