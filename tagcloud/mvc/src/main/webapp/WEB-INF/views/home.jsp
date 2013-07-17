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
<link rel="stylesheet"
	href="<spring:url value="/resources/styles/jquery-ui-1.10.3.custom.css" />"
	type="text/css" />

<script type="text/javascript"
	src="<c:url value="/resources/scripts/jquery.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/scripts/jquery-ui-1.10.3.custom.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/scripts/json.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/scripts/jquery.tokeninput.js" />"></script>
<script type="text/javascript"
	src="http://maps.googleapis.com/maps/api/js?v=3&sensor=false"></script>
<script
	src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&libraries=places"></script>
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
			<div id="logo_div">logo here</div>
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
						<li><a href="#add-target-tab">Add Target</a></li>
					</ul>
					<div id="search-tab">
						<p>I want to go to a place that...</p>
						<form:form modelAttribute="searchModel" action="/tagcloud"
							method="post">
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
							</table>
						</form:form>
					</div>
					<div id="add-target-tab">
						<div id="Instructions">Select location by clicking map</div>
						<div id="statusbox"></div>
						<div id="targetForm" class="span-12 last">
							<form:form modelAttribute="targetModel" action="/tagcloud/target"
								method="post">
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
				<div id="informationBox">Info about targets here</div>
				<div id="scroller">
					<div id="targetlist"></div>
				</div>
			</div>
		</div>
	</div>


</body>


</html>