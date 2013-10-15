var map;
var tabIndex = 0;
var addedMarker;
var center;
var bounds;
var placesService;
var animTimeout;
var markers = [];
var wayPoints = [];
var directionMarkers = [];

var directionsDisplay;

var rendererOptions = {
	draggable: true
};

var directionsService = new google.maps.DirectionsService();

var goToLocation = new google.maps.LatLng(60.172983, 24.940332);

var pos;

google.maps.event.addDomListener(window, 'load', initialize);

function initialize() {
	directionsDisplay = new google.maps.DirectionsRenderer(rendererOptions);
	if(document.getElementById("latitude").getAttribute("value") > 0.0) {
		pos = new google.maps.LatLng(document.getElementById("latitude").getAttribute("value"), document
				.getElementById("longitude").getAttribute("value"));
	} else {
		pos = new google.maps.LatLng(60.172983, 24.940332);
	}

	var currentZoom = 13;
	if(document.getElementById("map-zoom").getAttribute("value") > 0) {
		currentZoom = parseInt(document.getElementById("map-zoom").getAttribute("value"));
	}

	var mapOptions = {
		zoom: currentZoom,
		center: pos,
		mapTypeId: google.maps.MapTypeId.ROADMAP
	};

	map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);

	if(navigator.geolocation && document.getElementById("latitude").getAttribute("value") == 0.0) {
		navigator.geolocation.getCurrentPosition(function(position) {
			pos = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
			mapOptions.center = pos;
			map.setCenter(pos);
		}, function() {
		});
	}

	map.setOptions({
		styles: [{
			featureType: "poi",
			elementType: "labels",
			stylers: [{
				visibility: "off"
			}]
		}]
	});

	directionsDisplay.setMap(map);
	directionsDisplay.setPanel(document.getElementById('directionsPanel'));
	// google.maps.event.addListener(directionsDisplay, 'directions_changed',
	// function() {
	// computeTotalDistance(directionsDisplay.directions);
	// });

	// when the map is ready
	google.maps.event.addListener(map, 'idle', function() {
		bounds = map.getBounds();
		center = map.getCenter();
		setMapControls();
		populateCoordinates(map.getCenter().lat(), map.getCenter().lng(), map.getZoom());
		$("#searchModel").submit();
	});

	var input = document.getElementById('searchTextField');
	var options = {
		types: ['geocode']
	};

	initAutocomplete();
	accrodionActivation();
}

// handle accordion heading selections
function accrodionActivation() {
	
	$('#accordion').accordion({
		beforeActivate: function(event, ui) {
			// heading 0

			// heading 1
			$('#panel')[0].style.visibility = "hidden";

			// heading 2
			$('#targetModel')[0].style.visibility = "hidden";
		}
	});

	$('#accordion').accordion({
		activate: function(event, ui) {
			var $activeTab = $("#accordion").accordion('option', 'active');

			if($activeTab == 0) {
				$("#searchModel").submit();
				google.maps.event.addListener(map, 'idle', function() {
					bounds = map.getBounds();
					center = map.getCenter();
					populateCoordinates(map.getCenter().lat(), map.getCenter().lng(), map.getZoom());
					$("#searchModel").submit();
				});

				google.maps.event.clearListeners(map, 'click');
				clearAddedMarker();
				$('#targetModel')[0].reset();
			}
			if($activeTab == 1) {
				// var wayPointCounter = 0;
				google.maps.event.clearListeners(map, 'idle');
				google.maps.event.addListener(map, 'click', function(event) {
					$('#directionGuidance')[0].style.visibility = "hidden";
					$('#panel')[0].style.visibility = "visible";
					var myLatLng = event.latLng;
					var lat = myLatLng.lat();
					var lng = myLatLng.lng();
					goToLocation = myLatLng;
					if(addedMarker) {
						addedMarker.setPosition(myLatLng);
						showAddedMarker();
					} else {
						addedMarker = new google.maps.Marker({
							position: myLatLng,
							map: map
						});
					}
				});
			}
			if($activeTab == 2) {
				clearMarkers();
				var $list = $("#targetlist");
				$list.empty();
				google.maps.event.clearListeners(map, 'idle');
				
				google.maps.event.addListener(map, 'click', function(event) {
					var myLatLng = event.latLng;
					var lat = myLatLng.lat();
					var lng = myLatLng.lng();

					if(addedMarker) {
						addedMarker.setPosition(myLatLng);
						showAddedMarker();
					}

					else {
						addedMarker = new google.maps.Marker({
							position: myLatLng,
							map: map
						});
					}

					populateCoordinates(lat, lng, map.getZoom());

					$('#targetModel')[0].style.visibility = "visible";

					searchNearbyTargets(lat, lng);
				});

			}
		}
	});
}

function calcRoute() {
	var selectedMode = document.getElementById('mode').value;
	var request = {
		origin: pos,
		destination: goToLocation,
		// waypoints : wayPoints,
		travelMode: google.maps.TravelMode[selectedMode]
	};
	directionsService.route(request, function(response, status) {
		if(status == google.maps.DirectionsStatus.OK) {
			directionsDisplay.setDirections(response);
		}
	});
}

function initAutocomplete() {
	placesService = new google.maps.places.PlacesService(map);
	$.widget("custom.catcomplete", $.ui.autocomplete, {
		_renderMenu: function(ul, items) {
			var that = this, currentCategory = "";
			$.each(items, function(index, item) {
				if(item.category != currentCategory) {
					ul.append("<li class='ui-autocomplete-category'>" + item.category + "</li>");
					currentCategory = item.category;
				}
				that._renderItemData(ul, item);
			});
		}
	});

	// clear the HTML5 placeholder text on focus
	$("#searchTextField").focusin(function() {
		$("#searchTextField").attr('placeholder', '');
	});

	// restore the HTML5 placeholder after a focus out
	$("#searchTextField").focusout(function() {
		$("#searchTextField").attr('placeholder', 'Find places');
	})
	
	$("#searchTextField").catcomplete({
		delay: 0,
		source: "/tagcloud/target/autocomplete",
		select: function(event, ui) {
			var request = new Object();
			request.reference = ui.item.id;

			if(ui.item.category == "Location") {
				placesService.getDetails(request, function(place, placesServiceStatus) {
					if(place.geometry.viewport) {
						map.fitBounds(place.geometry.viewport);
					} else {
						map.setCenter(place.geometry.location);
						map.setZoom(17); // Why 17? Because it looks good.
					}
				});
			}

			if(ui.item.category == "Target") {
				window.location = "/tagcloud/target?target_id=" + ui.item.id;
			}
		}
	});
}

function clearMarkers() {
	for( var i = 0; i < markers.length; i++) {
		markers[i].setMap(null);
	}
	markers.length = 0;
}

function clearAddedMarker() {
	if(addedMarker) {
		addedMarker.setMap(null);
	}
}

function showAddedMarker() {
	if(addedMarker) {
		addedMarker.setMap(map);
	}
}

function populateCoordinates(lat, lng, zoom) {
	document.getElementById("latitude").setAttribute("value", lat);
	document.getElementById("longitude").setAttribute("value", lng);
	document.getElementById("map-zoom").setAttribute("value", zoom);
}

function placeNewMarkerWithIndex(result, location, index, target_id) {
	var innerListItem = $("#targetlist").children().eq(index);
	var marker;
	if(innerListItem.hasClass("facebook")) {
		marker = new google.maps.Marker({
			position: location,
			map: map,
			icon: "http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=" + (index + 1) + "|FF0000|000000",
			ind: index
		});
	} else if(innerListItem.hasClass("facebookFrend")) {
		marker = new google.maps.Marker({
			position: location,
			map: map,
			icon: "http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=" + (index + 1) + "|FFA500|000000",
			ind: index
		});
	} else {
		marker = new google.maps.Marker({
			position: location,
			map: map,
			icon: "http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=" + (index + 1) + "|88BBFF|000000",
			ind: index
		});
	}

	var tags = [];
	$.each(result.target.tags, function(index, value) {
		if(tags.length >= 5)
			return false;
		if(jQuery.inArray(value.text, tags) == -1)
			tags.push(value.text);
	});

	var boxText = document.createElement("div");
	boxText.style.cssText = "border: 1px solid black; margin-top: 8px; background: yellow; padding: 5px;";
	boxText.innerHTML = "<b>" + result.target.text + "</b>" + "<br>" + "<div style=\"color: gray\">" + tags.join(", ")
			+ "</div>" + "User score: " + result.target.score.toFixed(1) + "<br><i>Click to open</i>";

	var myOptions = {
		content: boxText,
		disableAutoPan: false,
		maxWidth: 0,
		pixelOffset: new google.maps.Size(-80, 0),
		zIndex: null,
		boxStyle: {
			background: "no-repeat",
			opacity: 0.75,
			width: (tags.length >= 4) ? "220px" : "160px"
		},
		closeBoxMargin: "10px 2px 2px 2px",
		closeBoxURL: "",
		infoBoxClearance: new google.maps.Size(1, 1),
		isHidden: false,
		pane: "floatPane",
		enableEventPropagation: false
	};

	var infoBox = new InfoBox(myOptions);

	if(target_id) {
		marker.target = target_id;
	}

	markers.push(marker);

	// scroll target list item to visible area after a mouseover
	google.maps.event.addListener(marker, 'mouseover', function(event) {
		var parentDiv = $("#scroller");
		var innerListItem = $("#targetlist").children().eq(index);
		setMarkerHighlight(index, true);
		setTargetDivHighlight($("#targetlist").children().eq(index), true);
		infoBox.open(map, marker);

		if(!isScrolledIntoView(innerListItem, parentDiv)) {
			var scrollTopValue = parentDiv.scrollTop() + (innerListItem.position().top - parentDiv.position().top)
					- (parentDiv.height() / 2) + (innerListItem.height() / 2);
			animTimeout = setTimeout(function() {
				parentDiv.animate({
					scrollTop: scrollTopValue
				}, {
					duration: 1000,
					queue: false
				});
			}, 350);

			var scrollLeftValue = parentDiv.scrollLeft() + (innerListItem.position().left - parentDiv.position().left)
					- (parentDiv.width() / 2) + (innerListItem.width() / 2);
			animTimeout = setTimeout(function() {
				parentDiv.animate({
					scrollLeft: scrollLeftValue
				}, {
					duration: 1000,
					queue: false
				});
			}, 350);
		}
	});

	google.maps.event.addListener(marker, 'mouseout', function(event) {
		setMarkerHighlight(index, false);
		setTargetDivHighlight($("#targetlist").children().eq(index), false);
		infoBox.close(map, marker);
		if(animTimeout) {
			clearTimeout(animTimeout);
		}
	});

	google.maps.event.addListener(marker, 'click', function(event) {
		if(marker.target) {
			window.location = "/tagcloud/target?target_id=" + marker.target;
		}
	});

}

function isScrolledIntoView(elem, view) {
	var viewTop = view.position().top;
	var viewBottom = viewTop + view.height();
	var elemTop = elem.position().top;
	var elemBottom = elemTop + elem.height();

	var viewLeft = view.position().left;
	var viewRight = viewLeft + view.width();
	var elemLeft = elem.position().left;
	var elemRight = elemLeft + elem.width();

	return ((elemBottom <= viewBottom) && (elemTop >= viewTop) && (elemRight <= viewRight) && (elemLeft >= viewLeft));
}

function setMarkerHighlight(index, highlight) {
	var innerListItem = $("#targetlist").children().eq(index);
	if(innerListItem.hasClass("facebook")) {
		if(highlight) {
			markers[index].setIcon("http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=" + (index + 1)
					+ "|F08080|000000");
		} else {
			markers[index].setIcon("http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=" + (index + 1)
					+ "|FF0000|000000");
		}
	} else if(innerListItem.hasClass("facebookFrend")) {
		if(highlight) {
			markers[index].setIcon("http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=" + (index + 1)
					+ "|FFDA9A|000000");
		} else {
			markers[index].setIcon("http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=" + (index + 1)
					+ "|FFA500|000000");
		}
	} else {
		if(highlight) {
			markers[index].setIcon("http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=" + (index + 1)
					+ "|BBDDFF|000000");
		} else {
			markers[index].setIcon("http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=" + (index + 1)
					+ "|88BBFF|000000");
		}
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
