<%@ page language="java" pageEncoding="UTF-8" session="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Tagcloud</title>

<style>
.ui-autocomplete-category {
	font-weight: bold;
	padding: .2em .4em;
	margin: .8em 0 .2em;
	line-height: 1.5;
}
</style>

<script src="/tagcloud/resources/scripts/jquery.min.js"></script>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script src="/tagcloud/resources/scripts/json.min.js"></script>
<script src="/tagcloud/resources/scripts/jquery.tokeninput.js"></script>
<script src="http://maps.googleapis.com/maps/api/js?v=3.exp&amp;sensor=true&amp;libraries=places"></script>
<script src="http://google-maps-utility-library-v3.googlecode.com/svn/trunk/infobox/src/infobox.js"></script>
<script src="/tagcloud/resources/scripts/map.js"></script>

<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
<script>
	$(function() {
		$("#accordion").accordion({
			heightStyle: "content",
			collapsible: true
		});
	});
</script>

<link rel="stylesheet" href="/tagcloud/resources/styles/styles.css" media="screen">
<link rel="stylesheet" href="/tagcloud/resources/styles/display-table-style.css">
<link rel="stylesheet" href="/tagcloud/resources/styles/token-input-facebook.css">
<link rel="stylesheet" href="/tagcloud/resources/styles/accordion.css">
<link rel="stylesheet" href="/tagcloud/resources/styles/styles-narrow.css" media="(max-aspect-ratio: 1/1)">

<script src="/tagcloud/resources/scripts/tagpicker.js"></script>
<script src="/tagcloud/resources/scripts/searchNearbyTargets.js"></script>
<script src="/tagcloud/resources/scripts/validation_script.js"></script>
<script src="/tagcloud/resources/scripts/styling.js"></script>
<script src="/tagcloud/resources/scripts/connections.js"></script>
<script src="/tagcloud/resources/scripts/mainscript.js"></script>
</head>

<body>
	<header id="header">
		<noscript id="noscriptBanner">
			<div>
				<p>Sorry, but Tagcloud works only with JavaScript enabled</p>
			</div>
		</noscript>

		<div id="top-table">
			<div id="top-container">
				<div id="logo_div">
					<img id="logo" alt="logo" src="/tagcloud/resources/img/tagcloud-logo.png">
				</div>
				<div id="headerMiddle_div">
					<input id="searchTextField" type="text" placeholder="Find places">
				</div>
				<div id="login_div"> <!-- CSS decides which img to show -->
					<img id="loginImg" alt="login_facebook" src="/tagcloud/resources/img/login-facebook.png">
					<img id="loginImg-med" alt="login_facebook" src="/tagcloud/resources/img/login-facebook-med.png">
					<img id="loginImg-s" alt="login_facebook" src="/tagcloud/resources/img/login-facebook-s.png">
				</div>
			</div>
		</div>
	</header>

	<section id="section">
		<div id="bc-col1">
			<div id="accordion">
				<h3>
					<a href="#">Search</a>
				</h3>
				<div id="search-tab">
					<p>I want to go to a place that...</p>
					<form id="searchModel" action="/tagcloud" method="post">
						<table id="searchTable">
							<tr>
								<td><label for="required">must be</label></td>
							</tr>
							<tr>
								<td><input id="required" name="required" /></td>
							</tr>
							<tr>
								<td><label for="preferred">preferably also</label></td>
							</tr>
							<tr>
								<td><input id="preferred" name="preferred" /></td>
							</tr>
							<tr>
								<td><label for="nearby">and is near to</label></td>
							</tr>
							<tr>
								<td><input id="nearby" name="nearby" /></td>
							</tr>
							<tr>
								<td><input id="search" type="submit" value="Refresh" /></td>
							</tr>
						</table>
					</form>
				</div>
				<h3>
					<a href="#">Go to...</a>
				</h3>
				<div id="get-directions-tab">
					<div id="directionGuidance">Choose destination..</div>
					<div id="panel">
						<b>Mode of Travel: </b> <br /> <select id="mode" onchange="calcRoute();">
							<option value="">--</option>
							<option value="DRIVING">Driving</option>
							<option value="WALKING">Walking</option>
							<option value="BICYCLING">Bicycling</option>
							<option value="TRANSIT">Transit</option>
						</select>
					</div>
					<div id="directionScroller">
						<div id="directionsPanel"></div>
					</div>
				</div>
				<h3>
					<a href="#">Add target</a>
				</h3>
				<div id="add-target-tab">
					<div id="Instructions">Select location by clicking map</div>
					<div id="statusbox"></div>
					<div id="targetForm" class="span-12 last">
						<form id="targetModel" action="/tagcloud/target" method="post">
							<table id="targetTable">
								<tr>
									<td><label for="text">Name</label></td>
									<td><input id="text" name="text" type="text" value="" /></td>
									<td></td>
								</tr>
								<tr>
									<td><input id="latitude" name="latitude" type="hidden" value="0.0" /></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td><input id="longitude" name="longitude" type="hidden" value="0.0" /></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td><input id="save" type="submit" value="Save" /></td>
									<td></td>
									<td></td>
								</tr>
							</table>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div id="bc-col2">
			<div id="map-canvas"></div>
			<input id="map-zoom" name="map-zoom" type="hidden" value="0"></input>
		</div>
		<div id="bc-col3">
			<div id="scroller">
				<ul id="targetlist"></ul>
			</div>
		</div>
	</section>

	<footer id="footer">
		<b>Copyright &copy; 2013 Tieto Plc</b>
	</footer>
</body>

</html>


