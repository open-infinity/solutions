function initTagSearchOnDocumentReady() {
	initTokenInput("#required", "#searchModel");
	initTokenInput("#preferred", "#searchModel");
	initTokenInput("#nearby", "#searchModel");
	
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
				  
				  placeNewMarkerWithIndex(loc, index, value.target.id);
			});
			
			styleTargetDivs();

		}, function(error){
			console.log("error: "+error.responseText);
		});
		return false;
	});
}

function initTagAddOnDocumentReady() {
	$("#text").tokenInput("/tagcloud/tag/autocomplete", {
		propertyToSearch : "text",
		preventDuplicates : true,
		theme : "facebook",
		resultsLimit : 8,
		tokenLimit : 1,
        onAdd: function(){
            $("#tagAddForm").submit();
            $("#text").tokenInput("clear");
        }
	});
	
	$("#tagAddForm").submit(function() {
		console.log("submittaa");
		var request = $(this).serializeObject();
		request.text = $("#text").tokenInput("get")[0].text
		request.id = "not yet generated";

		var action = document.getElementById("tagAddForm").getAttribute("action");

		$.postJSON(action, request, 
			function(resultJson){
				window.location = action;
			}, 
			function(error){
				console.log("error: "+error.responseText);
			}
		);
		
		return false;
	});
}



function initTokenInput(field, model){
	$(field).tokenInput("/tagcloud/tag/autocomplete", {
		propertyToSearch : "text",
		preventDuplicates : true,
		theme : "facebook",
		resultsLimit : 8,
        onAdd: function(){
            $(model).submit();
        },
        onDelete: function(){
            $(model).submit();
        }
	});
	
}
