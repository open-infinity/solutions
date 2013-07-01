var map;
var marker;
var markers = [];
var infoWindow;

var infoWindowContent   = "<b>Add new target</b><br />"
                        + "<form id='addTargetForm'>"
                        + "Name: <input id='targetName' type='text' />"
                        + "<input type='submit' value='Add' />"
                        + "</form>";
	 


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
  });
}

function removePreviousMarker() {
		if (markers.length > 0){
			
			marker = markers.pop();
			marker.setMap(null); 
		}
};

function placeNewMarker(location) {
	
  marker = new google.maps.Marker({
      position: location,
      map: map
  });

  markers.push(marker);
  
  infoWindow.open(map,marker);
}

//google.maps.event.addDomListener(window, 'load', initialize);