
function searchNearbyTargets(lat, lng) {

	var request = new Object();
	request.location = new Array(lng, lat);

	$.postJSON("/tagcloud/nearby", request, function(resultJson) {
		var $list = $("#targetlist");
		$list.empty();
		clearMarkers();
		var logged_in = isUserLoggedIn();
		$.each(resultJson.results, function(index, value) {
			$list.append(createDiv(value, index, logged_in));
			resizeRightColumn();
			var loc = new google.maps.LatLng(value.target.location[1],
					value.target.location[0]);

			placeNewMarkerWithIndex(value, loc, index);
		});

		styleTargetDivs();

	}, function(error) {
		console.log("error: " + error.responseText);
	});

}

