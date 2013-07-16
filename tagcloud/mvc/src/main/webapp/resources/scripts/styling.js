function createDiv(result, index) {
	var div = $("<div class=\"targetItemDiv\"></div>");
	div.append("<div>" + index + 1 + ": " + result.target.text + "</div>");
	div.append("<div>Recommendation Score: " + result.recommendationScore
			+ "</div>");
	div.append("<div>User Score: " + result.target.score + "</div>");
	var t_id = result.target.id;
	$(div).click(function (){ window.location= "target?target_id="+t_id;});
	div.css('cursor', 'pointer');
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
	var mapHeight = $(window).height() - 50 +"px";
	document.getElementById("map-canvas").style.height = mapHeight;
	var listHeight = $(window).height() - 50 - 20 +"px";
	document.getElementById("scroller").style.height = listHeight;	
	
	var mapwidth = window.getComputedStyle(document.getElementById("headerMiddle_div"),null).getPropertyValue("width");
	document.getElementById("map-canvas").style.width = mapwidth;
}

$(document).ready(function() {
	resizeRightColumn();
});

$(window).resize(function() {
	resizeRightColumn();
});