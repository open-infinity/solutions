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
		image = "<img id=\"facebook-img\" alt=\"I Like this in facebook\" " +
				"src=\"/tagcloud/resources/img/facebook-like.png\" height=\"16\" width=\"16\" ";
		image = image.concat(" title=", "\"From my facebook interests\">");
	} else if (result.target.facebookLikes == 2 && logged_in) {
		image = "<img id=\"facebook-img\" alt=\"My friends like this in facebook\" " +
				"src=\"/tagcloud/resources/img/friends.png\" height=\"20\" width=\"20\" ";
		image = image.concat(" title=\"", result.target.facebookFriends, " likes this in facebook\">");
	}
	div.append("<div>" + (index + 1) + ": " + result.target.text + image + "</div>");
	var tags = [];
	$.each(result.requiredTags, function(index, value) {
		tags.push(value.text);
	});
	$.each(result.preferredTags, function(index, value) {
		tags.push(value.text);
	});
	$.each(result.target.tags, function(index, value) {
		if(tags.length >= 5) return false;
		if(jQuery.inArray(value.text, tags) == -1) tags.push(value.text); 
	});
	
	div.append("<div class=\"recommendation_tags\">"+tags.join(", ")+"</div>");
	div.append("<div>User Score: " + result.target.score.toFixed(0) + "</div>");
	var t_id = result.target.id;
	$(div).click(function (){ window.location= "target?target_id="+t_id;});
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
	console.log("*** styleTargetDivs");
	$(".targetItemDiv").hover(function() {
		setTargetDivHighlight($(this), true);
		setMarkerHighlight($(this).index(), true);
	}, function() {
		setTargetDivHighlight($(this), false);
		setMarkerHighlight($(this).index(), false);
	});
}

function resizeRightColumn() {
	var mapHeight = $(window).height() - 70 +"px";
	
	document.getElementById("map-canvas").style.height = mapHeight;
	var listHeight = $(window).height() - 70 - 20 +"px";
	document.getElementById("scroller").style.height = listHeight;
}

function resizeDirectionsColumn() {
	var listHeight = $(window).height() - 248 - 20 +"px";
	document.getElementById("directionScroller").style.height = listHeight;	
}

$(document).ready(function() {
	resizeRightColumn();
	resizeDirectionsColumn();
});

$(window).resize(function() {
	resizeRightColumn();
	resizeDirectionsColumn();
});