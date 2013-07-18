
function searchNearbyTargets(lat, lng) {

	var request = new Object();
	request.location = new Array(lng, lat);

	$.postJSON("/tagcloud/nearby", request, function(resultJson) {
		var $list = $("#targetlist");
		$list.empty();
		clearMarkers();
		$.each(resultJson.results, function(index, value) {
			$list.append(createDiv(value, index));
			var loc = new google.maps.LatLng(value.target.location[1],
					value.target.location[0]);

			placeNewMarkerWithIndex(loc, index);
		});

		styleTargetDivs();

	}, function(error) {
		console.log("error: " + error.responseText);
	});

}

