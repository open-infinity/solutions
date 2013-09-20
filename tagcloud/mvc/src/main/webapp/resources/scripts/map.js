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

function initialize() {

	directionsDisplay = new google.maps.DirectionsRenderer(rendererOptions);
	pos = new google.maps.LatLng(60.172983, 24.940332);
	
	var mapOptions = {
		zoom : 13,
		center : pos,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	};

	map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
	
	if (navigator.geolocation) {
	    navigator.geolocation.getCurrentPosition(function(position) {
	       pos = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
	       mapOptions.center = pos;
	       map.setCenter(pos);
	    }, function() {});
    }
	
	map.setOptions({
		styles : [ {
			featureType : "poi",
			elementType : "labels",
			stylers : [ {
				visibility : "off"
			} ]
		} ]
	});

	directionsDisplay.setMap(map);
	directionsDisplay.setPanel(document.getElementById('directionsPanel'));
	google.maps.event.addListener(directionsDisplay, 'directions_changed', function() {
		computeTotalDistance(directionsDisplay.directions);
	});

	
	google.maps.event.addListener(map, 'idle', function() {
		bounds = map.getBounds();
		center = map.getCenter();
		$("#searchModel").submit();
	});

	var input = document.getElementById('searchTextField');
	var options = {
		types : [ 'geocode' ]
	};

	
	initAutocomplete();

	
	$(function() {
		$('#tabs')
				.tabs(
						{
						activate : function(event, ui) {

							var $activeTab = $("#tabs").tabs('option',
									'active');

							if ($activeTab == 0) {
								$("#searchModel").submit();
								document.getElementById('informationBox').innerHTML = "Targets found";
								google.maps.event.addListener(map, 'idle',
										function() {
											bounds = map.getBounds();
											center = map.getCenter();
											$("#searchModel").submit();
										});

								google.maps.event.clearListeners(map,
										'click');
								clearAddedMarker();
								$('#targetModel')[0].reset();

							}
							if ($activeTab == 1) {
								//var wayPointCounter = 0;
								$('#panel')[0].style.visibility="hidden";
								google.maps.event.clearListeners(map, 'idle');
								google.maps.event.addListener(
								map,
								'click',
								function(event) {
									$('#directionGuidance')[0].style.visibility="hidden";
									$('#panel')[0].style.visibility="visible";
									var myLatLng = event.latLng;
									var lat = myLatLng.lat();
									var lng = myLatLng.lng();
									goToLocation = myLatLng;
									if (addedMarker) {
										addedMarker.setPosition(myLatLng);
										showAddedMarker();
									} else {
										addedMarker = new google.maps.Marker({
											position : myLatLng,
											map : map
										});
									}									
//									$('#directionGuidance')[0].style.visibility="hidden";
//									$('#panel')[0].style.visibility="visible";
//									var myLatLng = event.latLng;
//									var lat = myLatLng.lat();
//									var lng = myLatLng.lng();
//									goToLocation = myLatLng;
//									var directionMarker = new google.maps.Marker({
//										position : myLatLgn,
//										map : map
//									});
//									directionMarkers[waypointCounter] = myLatLng;
//									directionMarker.setPosition(directionMarkers[i]);
//									directionMarker.setMap(map);
//									directionMarker.push();
//									populateCoordinates(lat, lng);
//									wayPointCounter++;

								});
							}
							if ($activeTab == 2) {
								$('#targetModel')[0].style.visibility="hidden";
								clearMarkers();
								var $list = $("#targetlist");
								$list.empty();
								document.getElementById('informationBox').innerHTML = "Targets nearby";
								google.maps.event.clearListeners(map, 'idle');
								google.maps.event.addListener(
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
											showAddedMarker();
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

										$('#targetModel')[0].style.visibility="visible";
										
										searchNearbyTargets(
												lat, lng);

									});

							}
						}
					});
	});

}

function calcRoute() {
	var selectedMode = document.getElementById('mode').value;
	var request = {
		origin : pos,
		destination : goToLocation,
	//	waypoints : wayPoints,
		travelMode : google.maps.TravelMode[selectedMode]
	};
	directionsService.route(request, function(response, status) {
		if (status == google.maps.DirectionsStatus.OK) {
			directionsDisplay.setDirections(response);
		}
	});
}


function computeTotalDistance(result) {
	var total = 0;
	var myroute = result.routes[0];
	for ( var i = 0; i < myroute.legs.length; i++) {
		total += myroute.legs[i].distance.value;
	}
	total = total / 1000.
	document.getElementById('total').innerHTML = total + ' km';
}


// function computeTotalDistance(result) {
//	  var total = 0;
//	  var myroute = result.routes[0];
//	  for (var i = 0; i < myroute.legs.length; i++) {
//	    total += myroute.legs[i].distance.value;
//	  }
//	  total = total / 1000.
//	  document.getElementById('total').innerHTML = total + ' km';
//	}


google.maps.event.addDomListener(window, 'load', initialize);

function initAutocomplete() {
	placesService = new google.maps.places.PlacesService(map);		
	$.widget( "custom.catcomplete", $.ui.autocomplete, {
		    _renderMenu: function( ul, items ) {
		      var that = this,
		        currentCategory = "";
		      $.each( items, function( index, item ) {
		        if ( item.category != currentCategory ) {
		          ul.append( "<li class='ui-autocomplete-category'>" + item.category + "</li>" );
		          currentCategory = item.category;
		        }
		        that._renderItemData( ul, item );
		      });
		    }
		  });	
	  
	  $( "#searchTextField" ).catcomplete({
	      delay: 0,
	      source: "/tagcloud/target/autocomplete",
	      select: function( event, ui ) {
		  	var request = new Object();
		  	request.reference = ui.item.id;
		  	
		  	if(ui.item.category == "Location"){
		  		placesService.getDetails(request, function(place, placesServiceStatus){
	    	  		if (place.geometry.viewport) {
	    	  	      map.fitBounds(place.geometry.viewport);
	    	  	    } else {
	    	  	      map.setCenter(place.geometry.location);
	    	  	      map.setZoom(17);  // Why 17? Because it looks good.
	    	  	    }
	    	  	});		  		
		  	}
		  	
		  	if(ui.item.category == "Target"){
		  		window.location = "/tagcloud/target?target_id="+ui.item.id;  		
		  	}
  		}
    });  
}

function clearMarkers() {
	for ( var i = 0; i < markers.length; i++) {
		markers[i].setMap(null);
	}
	markers.length = 0;
}

function clearAddedMarker() {
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

function placeNewMarkerWithIndex(location, index, target_id) {

	var marker = new google.maps.Marker({
		position : location,
		map : map,
		icon : "http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld="
				+ (index + 1) + "|88BBFF|000000",
		ind : index
	});

	if(target_id){
		marker.target = target_id;
	}
	

	markers.push(marker);

	google.maps.event.addListener(marker, 'mouseover', function(event) {
		setMarkerHighlight(index, true);
		setTargetDivHighlight($("#targetlist").children().eq(index), true);
		var parentDiv = $("#scroller");
		var innerListItem = $("#targetlist").children().eq(index);
		
		if(!isScrolledIntoView(innerListItem, parentDiv)){
			var scrollTopValue = parentDiv.scrollTop() + (innerListItem.position().top - parentDiv.position().top) - (parentDiv.height()/2) + (innerListItem.height()/2);
			animTimeout = setTimeout(function() {
				parentDiv.animate({
					scrollTop: scrollTopValue
				}, {
					duration : 1000,
					queue : false
				});
			}, 350);			
			
				
		}
		
			
		
	});

	google.maps.event.addListener(marker, 'mouseout', function(event) {
		setMarkerHighlight(index, false);
		setTargetDivHighlight($("#targetlist").children().eq(index), false);
		if(animTimeout){
			clearTimeout(animTimeout)
		}
	});
	
	
	google.maps.event.addListener(marker, 'click', function(event) {
		if(marker.target){
			window.location = "/tagcloud/target?target_id="+marker.target;
		}
		
	});

}


function isScrolledIntoView(elem, view)
{
    var viewTop = view.position().top;
    var viewBottom = viewTop + view.height();

    var elemTop = elem.position().top;
    var elemBottom = elemTop + elem.height();

    return ((elemBottom <= viewBottom) && (elemTop >= viewTop));
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
