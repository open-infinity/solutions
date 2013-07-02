var map;
var marker;
var markers = [];
var infoWindow;
var lat;
var lng;

var infoWindowContent   = "Target's location";


function initialize() {
  var mapOptions = {
    zoom: 13,
    center: new google.maps.LatLng(-34.397, 150.644),
    mapTypeId: google.maps.MapTypeId.ROADMAP
  };
  
  map = new google.maps.Map(document.getElementById('map-canvas'),
      mapOptions);  

  infoWindow = new google.maps.InfoWindow({
	    content: infoWindowContent
  });
  
  google.maps.event.addListener(infoWindow, 'closeclick', removePreviousMarker);
	 	
  google.maps.event.addListener(map, 'click', function(event){
	removePreviousMarker();
    placeNewMarker(event.latLng);
    
	var myLatLng = event.latLng;
    lat = myLatLng.lat();
    lng = myLatLng.lng();
    
    populateCoordinates(location);
  });
}

function removePreviousMarker() {
		if (markers.length > 0){
			
			marker = markers.pop();
			marker.setMap(null); 
		}
};

function populateCoordinates(){
	document.getElementById("latitude").setAttribute("value", lat);
	document.getElementById("longitude").setAttribute("value", lng);
}

function placeNewMarker(location) {

  marker = new google.maps.Marker({
      position: location,
      map: map
  });

  markers.push(marker);
  infoWindow.open(map,marker);
}
