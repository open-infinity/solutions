<%-- <%@ page session="false"%> --%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />

<link rel="stylesheet"
	href="<spring:url value="/resources/styles/styles.css" />"
	type="text/css" />
<link rel="stylesheet"
	href="<spring:url value="/resources/styles/display-table-style.css" />"
	type="text/css" />
<link rel="stylesheet"
	href="<spring:url value="/resources/styles/token-input-facebook.css" />"
	type="text/css" />
<%--<link rel="stylesheet"
	href="<spring:url value="/resources/styles/jquery-ui-1.10.3.custom.css" />"
	type="text/css" />--%>
 <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
	
<style>
  .ui-autocomplete-category {
    font-weight: bold;
    padding: .2em .4em;
    margin: .8em 0 .2em;
    line-height: 1.5;
  }
</style>

<script type="text/javascript"
	src="<c:url value="/resources/scripts/jquery.min.js" />"></script>
<%--<script type="text/javascript"
	src="<c:url value="/resources/scripts/jquery-ui-1.10.3.custom.js" />"></script>--%>

  <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	  <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
		
<script type="text/javascript"
	src="<c:url value="/resources/scripts/json.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/scripts/jquery.tokeninput.js" />"></script>
<script
	src="http://maps.googleapis.com/maps/api/js?v=3.exp&sensor=true&libraries=places"></script>
<script type="text/javascript"
	src="<c:url value="/resources/scripts/map.js" />"></script>
<script>
	$(function() {
		$("#tabs").tabs();
	});
</script>

<script type="text/javascript"
	src="<c:url value="/resources/scripts/tagpicker.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/scripts/searchNearbyTargets.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/scripts/validation_script.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/scripts/styling.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/scripts/connections.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/scripts/mainscript.js" />"></script>

<title>TagCloud</title>
</head>

<body>
	<div id="top-table">
		<div id="top-container">
			<div id="logo_div"><img id="logoImg" alt="logo" src="<c:url value="/resources/img/tagcloud-logo.png" />" /></div>
			<div id="headerMiddle_div">
				<input id="searchTextField" type="text" placeholder="Find places">
			</div>
			<div id="login_div">
				<img id="loginImg" alt="login_facebook"
					src="<c:url value="/resources/img/login-facebook.png" />" />
			</div>
		</div>
	</div>
	<div id="bottom-table">
		<div id="bottom-container">
			<div id="bc-col1">
				<div id="tabs">
					<ul>
						<li><a href="#search-tab">Search</a></li>
						<li><a href="#get-directions-tab">Go to..</a></li>
						<li><a href="#add-target-tab">Add Target</a></li>
					</ul>
					<div id="search-tab">
						<p>I want to go to a place that...</p>
						<form:form modelAttribute="searchModel"
							action="/tagcloud" method="post">
							<table id="searchTable">
								<tr>
									<td><form:label for="required" path="required">must be</form:label></td>
								</tr>
								<tr>
									<td><form:input type="text" id="required" name="required"
											path="required" /></td>
								</tr>
								<tr>
									<td><form:label for="preferred" path="preferred">preferably also</form:label></td>
								</tr>
								<tr>
									<td><form:input type="text" id="preferred"
											name="preferred" path="preferred" /></td>
								</tr>
								<tr>
									<td><form:label for="nearby" path="nearby">and is near to</form:label></td>
								</tr>
								<tr>
									<td><form:input type="text" id="nearby" name="nearby"
											path="nearby" /></td>
								</tr>
								<tr>
									<td><input id="search" type="submit" value="Refresh" /></td>
								</tr>
							</table>
						</form:form>
					</div>
					<div id="get-directions-tab">
						<div id="panel">
							<b>Mode of Travel: </b>
							<br />
							<select id="mode" onchange="calcRoute();">
								<option value="DRIVING">Driving</option>
								<option value="WALKING">Walking</option>
								<option value="BICYCLING">Bicycling</option>
								<option value="TRANSIT">Transit</option>
							</select>
						</div>
					</div>
					<div id="add-target-tab">
						<div id="Instructions">Select location by clicking map</div>
						<div id="statusbox"></div>
						<div id="targetForm" class="span-12 last">
							<form:form modelAttribute="targetModel"
								action="/tagcloud/target" method="post">
								<table id="targetTable">
									<tr>
										<td><form:label for="text" path="text"
												cssErrorClass="error">Name</form:label></td>
										<td><form:input path="text" /></td>
										<td><form:errors path="text" /></td>
									</tr>
									<tr>
										<form:hidden id="latitude" path="latitude" />
									</tr>
									<tr>
										<form:hidden id="longitude" path="longitude" />
									</tr>
									<tr>
										<td><input id="save" type="submit" value="Save" /></td>
									</tr>
								</table>
							</form:form>
						</div>
					</div>
				</div>
			</div>
			<div id="bc-col2">
				<div id="map-canvas"></div>
			</div>
			<div id="bc-col3">
				<div id="informationBox">Targets found</div>
				<div id="scroller">
					<div id="targetlist"></div>
				</div>
			</div>
		</div>
	</div>


</body>


</html>
