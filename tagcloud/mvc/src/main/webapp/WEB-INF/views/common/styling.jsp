<script type="text/javascript">	

function resize_bg_div() {

	var headerHeight = window.getComputedStyle(
			document.getElementById("header")).getPropertyValue("height")
			.split("px")[0];
	var leftWidth = window.getComputedStyle(
			document.getElementById("sideBarLeft")).getPropertyValue(
			"width").split("px")[0];
	var rightWidth = window.getComputedStyle(
			document.getElementById("sideBarRight")).getPropertyValue(
			"width").split("px")[0];
	var mapHeight = window.innerHeight - headerHeight - 20;
	var mapWidth = window.innerWidth - leftWidth - rightWidth - 40;
	if (mapHeight < 200) {
		mapHeight = 200;
	}
	if (mapWidth < 340) {

		mapWidth = 340;
	}
	document.getElementById("centerColumn").style.height = mapHeight + "px";
	document.getElementById("centerColumn").style.width = mapWidth + "px";

	document.getElementById("sideBarRight").style.height = mapHeight - 20
			+ "px";

	// This function will determine which of the three columns or the window.height
	// is the largest and then set the bg div to be that height.

	// This assumes that any div markup that is above our columns is wrapped
	// in a single div with the id=header
	var var_bg_offset = document.getElementById('header').offsetHeight;

	// First we create an array and add to it the heights of each of the three columns
	// and the window height
	array_colHeights = new Array();
	array_colHeights
			.push(document.getElementById("sideBarLeft").offsetHeight);
	array_colHeights
			.push(document.getElementById("centerColumn").offsetHeight);
	array_colHeights
			.push(document.getElementById("sideBarRight").offsetHeight);

	// Instead of the raw window.innerHeight we need to take into account the offset size
	// of our header divs
	array_colHeights.push(window.innerHeight - var_bg_offset);

	// Sorting our array in descending order
	array_colHeights.sort(function(a, b) {
		return b - a;
	});

	// Now we'll set our bg div to the height of our largest div or window height
	document.getElementById('bg').style.height = array_colHeights[0] + "px";
	delete array_colHeights;
	delete var_bg_offset;

}

$(document).ready(function() {
	resize_bg_div()
});

$(window).resize(function() {
	resize_bg_div();
});
</script>

