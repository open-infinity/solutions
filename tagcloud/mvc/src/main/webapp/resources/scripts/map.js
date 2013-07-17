var map;
var markers = [];
var tabIndex = 0;
var addedMarker;
var center;
var bounds;

google.maps.event.addDomListener(window, 'load', initialize);

function initialize() {

	var mapOptions = {
		zoom : 13,
		center : new google.maps.LatLng(60.172983, 24.940332),
		mapTypeId : google.maps.MapTypeId.ROADMAP

	};

	map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);

	google.maps.event.addListener(map, 'idle', function() {
		bounds = map.getBounds();
		center = map.getCenter();
		$("#searchModel").submit();
	});

	map.setOptions({
		styles : [ {
			featureType : "poi",
			elementType : "labels",
			stylers : [ {
				visibility : "off"
			} ]
		} ]
	});

	var input = document.getElementById('searchTextField');
	var options = {
		types : [ 'geocode' ]
	};

	var autocomplete = new google.maps.places.Autocomplete(input, options);

	google.maps.event.addListener(autocomplete, 'place_changed', function() {
		var place = autocomplete.getPlace();
		if (!place.geometry) {
			return;
		}
		if (place.geometry.viewport) {
			map.fitBounds(place.geometry.viewport);
		} else {
			map.setCenter(place.geometry.location);
			map.setZoom(13);
		}
	});

	$(function() {
		$('#tabs')
				.tabs(
						{
							activate : function(event, ui) {
								var $activeTab = $("#tabs").tabs('option',
										'active');

								if ($activeTab == 0) {
									hideAddedMarker();
									showMarkers();
									google.maps.event.clearListeners(map,
											'click');
								}
								if ($activeTab == 1) {
									showAddedMarker();
									hideMarkers();

									google.maps.event
											.addListener(
													map,
													'click',
													function(event) {
														var myLatLng = event.latLng;
														var lat = myLatLng
																.lat();
														var lng = myLatLng
																.lng();

														if (addedMarker) {
															addedMarker
																	.setPosition(myLatLng);
														}

														else {
															addedMarker = new google.maps.Marker(
																	{
																		position : myLatLng,
																		map : map
																	});

														}

														populateCoordinates(
																lat, lng);
													});

								}
							}
						});
	});

}

function clearMarkers() {
	for ( var i = 0; i < markers.length; i++) {
		markers[i].setMap(null);
	}
	markers.length = 0;
}

function hideMarkers() {
	for ( var i = 0; i < markers.length; i++) {
		markers[i].setMap(null);
	}
}

function showMarkers() {
	for ( var i = 0; i < markers.length; i++) {
		markers[i].setMap(map);
	}
}

function hideAddedMarker() {
	if (addedMarker) {
		addedMarker.setMap(null);
	}
}

function showAddedMarker() {
	if (addedMarker) {
		addedMarker.setMap(map);
	}
}

function populateCoordinates(lat, lng) {
	document.getElementById("latitude").setAttribute("value", lat);
	document.getElementById("longitude").setAttribute("value", lng);
}

function placeNewMarkerWithIndex(location, index) {

	var marker = new google.maps.Marker({
		position : location,
		map : map,
		icon : "http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld="
				+ (index + 1) + "|88BBFF|000000",
		ind : index
	});

	markers.push(marker);

	google.maps.event.addListener(marker, 'mouseover', function(event) {
		setMarkerHighlight(index, true);
		setTargetDivHighlight($("#targetlist").children().eq(index), true);
	});

	google.maps.event.addListener(marker, 'mouseout', function(event) {
		setMarkerHighlight(index, false);
		setTargetDivHighlight($("#targetlist").children().eq(index), false);
	});

}

function setMarkerHighlight(index, highlight) {
	if (highlight) {
		markers[index]
				.setIcon("http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld="
						+ (index + 1) + "|BBDDFF|000000");
	} else {
		markers[index]
				.setIcon("http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld="
						+ (index + 1) + "|88BBFF|000000");
	}
}

function getMap() {
	return map;
}

function getCenter() {
	return center;
}

function getBounds() {
	return bounds;
}
