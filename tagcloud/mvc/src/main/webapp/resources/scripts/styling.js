function createDiv(result, index, logged_in) {
	var div;
	if (result.target.facebookLikes == 1 && logged_in) {
		div = $("<li class=\"targetItemDiv facebook\" title=\"Click to open\"></li>");
	} else if (result.target.facebookLikes == 2 && logged_in) {
		div = $("<li class=\"targetItemDiv facebookFrend\" title=\"Click to open\"></li>");
	} else {
		div = $("<li class=\"targetItemDiv\" title=\"Click to open\"></li>");
	}
	var image = "";
	if (result.target.facebookLikes == 1 && logged_in) {
		image = "<img id=\"facebook-img\" alt=\"I Like this in facebook\" "
				+ "src=\"/tagcloud/resources/img/facebook-like.png\" height=\"16\" width=\"16\" ";
		image = image.concat(" title=", "\"From my facebook interests\">");
	} else if (result.target.facebookLikes == 2 && logged_in) {
		image = "<img id=\"facebook-img\" alt=\"My friends like this in facebook\" "
				+ "src=\"/tagcloud/resources/img/friends.png\" height=\"20\" width=\"20\" ";
		image = image.concat(" title=\"", result.target.facebookFriends,
				" likes this in facebook\">");
	}
	div.append("<div>" + (index + 1) + ": " + result.target.text + image
			+ "</div>");
	var tags = [];
	$.each(result.requiredTags, function(index, value) {
		tags.push(value.text);
	});
	$.each(result.preferredTags, function(index, value) {
		tags.push(value.text);
	});
	$.each(result.target.tags, function(index, value) {
		if (tags.length >= 5)
			return false;
		if (jQuery.inArray(value.text, tags) == -1)
			tags.push(value.text);
	});

	div.append("<div class=\"recommendation_tags\">" + tags.join(", ")
			+ "</div>");
	div.append("<div>User Score: " + result.target.score.toFixed(1) + "</div>");
	var t_id = result.target.id;
	$(div).click(function() {
		window.location = "target?target_id=" + t_id;
	});
	div.css('cursor', 'pointer');
	if (result.target.facebookLikes == 1 && logged_in) {
		div.css('border', '3px solid red');
	} else if (result.target.facebookLikes == 2 && logged_in) {
		div.css('border', '3px solid orange');
	}
	return div;
}

function setTargetDivHighlight(div, highlighted) {
	if (highlighted) {
		div.css('background-color', '#BBDDFF');
	} else {
		div.css('background-color', '#88BBFF');
	}
}

function styleTargetDivs() {
	$(".targetItemDiv").hover(function() {
		setTargetDivHighlight($(this), true);
		setMarkerHighlight($(this).index(), true);
	}, function() {
		setTargetDivHighlight($(this), false);
		setMarkerHighlight($(this).index(), false);
	});
}

function resizeRightColumn() {
	document.getElementById("section").style.height = ($(window).height()
			- $("#header").height() - $("#footer").height())
			+ "px";

	if ($(window).height() < $(window).width()) { // landscape display port
		// set col3 as high as the content (=targetDivs) requires
		if ($("#targetlist").outerHeight(true) < $("#section").height()) {
			document.getElementById("scroller").style.height = $("#targetlist")
					.outerHeight(true)
					+ "px";
		} else {
			document.getElementById("scroller").style.height = $("#section")
					.height()
					+ "px";
		}
	} else { //portrait display port
		$("#scroller").first().css("height", "");
	}
}

function resizeMapView() {
	document.getElementById("map-canvas").style.height = $("#section").height() + "px";
}

function setMapControls() {
	// zoom control
	var gmBase = $(".gm-style").first()
		.css("left", $("#bc-col1").outerWidth(true) + "px")
		.css("overflow", "visible");

	var gmBase = $(".gm-style > div").first()
	.css("left", -($("#bc-col1").outerWidth(true)) + "px");
}

function resizeDirectionsColumn() {
	var totalHeaderHeight = 0;
	$(".ui-accordion-header").each(function(i, header) {
		totalHeaderHeight += (header.clientHeight + 2);
	});

	var dircetionsScrollerHeight = ($("#section").outerHeight(true)
			- $("#directionGuidance").outerHeight(true)
			- $("#panel").outerHeight(true) - totalHeaderHeight);
	if ($(window).height() >= $(window).width()) {
		dircetionsScrollerHeight -= $("#bc-col3").height();
	}
	document.getElementById("directionScroller").style.height = dircetionsScrollerHeight
			+ "px";
}

$(document).ready(function() {
	resizeRightColumn();
	resizeMapView();
	resizeDirectionsColumn();
	setMapControls();
});

$(window).resize(function() {
	resizeRightColumn();
	resizeMapView();
	resizeDirectionsColumn();
	setMapControls();
});

$(document).ready(function() {
	$("#accordion > h3").click(function() {
		resizeDirectionsColumn();
	});
});
