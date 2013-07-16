function initTagSearchOnDocumentReady() {
	initTokenInput("#required");
	initTokenInput("#preferred");
	initTokenInput("#nearby");
	
	$("#searchModel").submit(function() {
		var request = $(this).serializeObject();
		request.required = $("#required").tokenInput("get");
		request.preferred = $("#preferred").tokenInput("get");
		request.nearby = $("#nearby").tokenInput("get");
		var center = getCenter();
		request.location = new Array(center.lng(), getMap().getCenter().lat());
		var bounds = getBounds();
		request.bounds = new Array(bounds.getNorthEast().lng(), bounds.getNorthEast().lat(), 
			bounds.getSouthWest().lng(), bounds.getSouthWest().lat());
		$.postJSON("/tagcloud/", request, function(resultJson){
			var $list = $("#targetlist");
			$list.empty();
			clearMarkers();
			$.each(resultJson.results, function(index, value) {
				  $list.append(createDiv(value, index));
				  var loc = new google.maps.LatLng(value.target.location[1], value.target.location[0]);
				  
				  placeNewMarkerWithIndex(loc, index);
			});
			
			styleTargetDivs();

		}, function(error){
			console.log("error: "+error.responseText);
		});
		return false;
	});
}

function initTokenInput(field){
	$(field).tokenInput("/tagcloud/tag/autocomplete", {
		propertyToSearch : "text",
		preventDuplicates : true,
		theme : "facebook",
		resultsLimit : 8,
        onAdd: function(){
            $("#searchModel").submit();
        },
        onDelete: function(){
            $("#searchModel").submit();
        }
	});
	
}
