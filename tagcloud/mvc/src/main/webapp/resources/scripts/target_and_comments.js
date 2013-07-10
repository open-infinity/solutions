var map;
var star = "&#x02605;";
var empty_star = "&#x02606;";
$('document').ready(function() {

	// getTargetList();
	getTargetListJson();
	google.maps.event.addDomListener(window, 'load', google_map_initialize);
});
function getTargetList(data) {

	$.each(data, function(i, target) {
		if (i == 0) {
			setTargetInUi(target);
		}
		console.log(i + " id: " + target.id);
		console.log(i + " text: " + target.text);
		console.log(target);

	});

}

function setTargetInUi(target){
	$("#target_title").html(target.text);
	$("#score_value").html(target.score);
	setTagsInTagBar(target.tags);
	
} 
function setTagsInTagBar(tags){
	var tag_bar = $("#tag_bar");
	tag_bar.html("<h3>tags:</h3>");
	$.each(tags,function(i,tag){
		
	});
}
function getTargetListJson() {
	console.log("lalal");

	$.getJSON("target/list", function(data) {
		getTargetList(data);
	});

}
//
function google_map_initialize() {

	var mapOptions = {
		zoom : 16,
		center : new google.maps.LatLng(60.170293, 24.939473),
		mapTypeId : google.maps.MapTypeId.ROADMAP,
		panControl : false,
		zoomControl : false,
		scaleControl : false,
	};
	map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
}

function getTargetList_example(data) {
	var div = $('#targets_list_div');

	$.each(data, function(i, target) {
		var el = $('<div></div>');
		el.attr("id", target.id);
		el.append("osoite: " + target.text);
		div.append(el);
		div.append("<br/>");

	});

}
