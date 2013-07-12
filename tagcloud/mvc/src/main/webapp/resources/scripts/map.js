var map;
var markers = [];
var infoWindow;
var lat;
var lng;
var tabIndex = 0;

var infoWindowContent = "Target's location";


function initialize() {
  var mapOptions = {
    zoom: 13,
    center: new google.maps.LatLng(60.172983,24.940332),
    mapTypeId: google.maps.MapTypeId.ROADMAP
  };
  
  map = new google.maps.Map(document.getElementById('map-canvas'),
      mapOptions);  

  infoWindow = new google.maps.InfoWindow({
	    content: infoWindowContent
  });
  
  google.maps.event.addListener(infoWindow, 'closeclick', clearMarkers);
	 	
  google.maps.event.addListener(map, 'click', function(event){
    if($("#tabs").tabs('option', 'active') != 1) return false;


	clearMarkers();
    placeNewMarker(event.latLng);
    
	var myLatLng = event.latLng;
    lat = myLatLng.lat();
    lng = myLatLng.lng();
    
    populateCoordinates(location);
  });

}
google.maps.event.addDomListener(window, 'load', initialize);

function clearMarkers() {
	for (var i = 0; i < markers.length; i++) {
		markers[i].setMap(null);
	}
	markers.length = 0;
}

function populateCoordinates(){
	document.getElementById("latitude").setAttribute("value", lat);
	document.getElementById("longitude").setAttribute("value", lng);
}

function placeNewMarker(location) {

  var marker = new google.maps.Marker({
      position: location,
      map: map
  });

  markers.push(marker);
  infoWindow.open(map,marker);
}

function placeNewMarkerWithIndex(location, index) {

	  var marker = new google.maps.Marker({
	      position: location,
	      map: map,
	      icon: "http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld="+(index+1)+"|88BBFF|000000",
	      ind: index
	  });
	  
	  markers.push(marker);

	  google.maps.event.addListener(marker, 'mouseover', function(event){
		  setMarkerHighlight(index, true);
		  setTargetDivHighlight($("#targetlist").children().eq(index), true);
	  });
	  
	  google.maps.event.addListener(marker, 'mouseout', function(event){
		  setMarkerHighlight(index, false);
		  setTargetDivHighlight($("#targetlist").children().eq(index), false);
	  });
	  
	}


function setMarkerHighlight(index, highlight){
	if(highlight){
		markers[index].setIcon("http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld="+(index+1)+"|BBDDFF|000000");
	}
	else{
		markers[index].setIcon("http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld="+(index+1)+"|88BBFF|000000");
	}
}

function getMap(){
	return map;
}