var map;
var star = "&#x02605;";
var empty_star = "&#x02606;";
var default_author_img = "http://www.howsimple.com/assets/imgs/person_empty.png";
$('document').ready(function() {

	getAndPrintTargetListInConsole();
	target_id = getURLParameter("target_id");
	if (target_id != null) {

		console.log("target_id: " + target_id);
		getTargeAndUpdateUi(target_id);
	}

});
function getAndPrintTargetListInConsole() {

	$.getJSON("target/list", function(data) {
		$.each(data, function(i, target) {
			console.log(i + " id: " + target.id);
			console.log(i + " text: " + target.text);
			console.log(target);

		});
	});

}
function getTargeAndUpdateUi(target_id) {

	$.getJSON("target/" + target_id, function(data) {
		target = data;
		setTargetInUi(target);
	});

}
function setTargetInUi(target) {
	$("#target_title").html(target.text);
	$("#score_value").html(target.score);
	setTagsInTagBar(target.tags);
	google_map_initialize(target.location[0], target.location[1]);
	getTargetCommentsAndUpdateUi(target.id);
}

function getTargetCommentsAndUpdateUi(target_id) {
	$("#comment_container").html("");
	$.getJSON("comment/list/" + target_id, function(data) {
		$.each(data, function(i, comment) {
			createNewComment(default_author_img, comment.profile.facebookId,
					comment.id, comment.text);
		});
	});

}

function createNewComment(author_img, author_name, comment_id, text) {
	var comment = $("<div></div>");
	$(comment).attr('class', 'comment');
	$(comment).attr('id', comment_id);
	var comment_head = $("<div></div>");
	$(comment_head).attr('class', 'comment_header');
	var comment_text = $("<div></div>");
	$(comment_text).attr('class', 'comment_text');

	$(comment_head).html(
			"<img alt='" + author_name + " image' src='" + author_img
					+ "' /> <span>" + author_name + "</span>");
	$(comment_text).html(text);

	$(comment).append($(comment_head));
	$(comment).append($(comment_text));

	$("#comment_container").append($(comment));
}
function setTagsInTagBar(tags) {
	var bar = $("#tag_bar > div");
	bar.html("");

	$.each(tags, function(i, tag) {
		bar.append("<span id=" + tag.id + ">" + tag.text + "</span>");
	});
}

function google_map_initialize(long, lat) {
	var mapOptions = {
		zoom : 16,
		center : new google.maps.LatLng(lat, long),
		mapTypeId : google.maps.MapTypeId.ROADMAP,
		panControl : false,
		zoomControl : false,
		scaleControl : false,
	};
	map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
}

function getURLParameter(sParam) {
	var sPageURL = window.location.search.substring(1);
	var sURLVariables = sPageURL.split('&');
	for ( var i = 0; i < sURLVariables.length; i++)

	{
		var sParameterName = sURLVariables[i].split('=');

		if (sParameterName[0] == sParam)

		{

			return sParameterName[1];
		}
	}
}
