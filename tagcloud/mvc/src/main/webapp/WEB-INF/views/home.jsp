<%-- <%@ page session="false"%> --%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet"
		href="<spring:url value="/resources/styles/styles.css" />"
		type="text/css" />
	<link rel="stylesheet"
		href="<spring:url value="/resources/styles/threeColumn.css" />"
		type="text/css" />
	<link rel="stylesheet"
		href="<spring:url value="/resources/styles/token-input-facebook.css" />"
		type="text/css" />
	<link rel="stylesheet"
		href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
	
	
	<script type="text/javascript"
		src="<c:url value="/resources/scripts/jquery.min.js" />"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/scripts/json.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/scripts/map.js" />"></script>
	<script type="text/javascript"
		src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/scripts/styling.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/scripts/jquery.tokeninput.js" />"></script>
	<script>
		$(function() {
			$("#tabs").tabs();
		});
	</script>
	<script type="text/javascript" src="<c:url value="/resources/scripts/tagpicker.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/scripts/validation_script.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/scripts/target_result_div_styling.js" />"></script>
	<script type="text/javascript" src="<c:url value="/resources/scripts/mainscript.js" />"></script>
  	
	<title>TagCloud</title>
</head>

<body>
	<div id="mainContainer">

		<div id="header"></div>
		<!-- The container divs for the center and the right columns -->
		<div id="centerRightColumnContainer">

			<div id="centerRightColumnPositioner">

				<div id="centerColumnContainer">

					<div id="centerColumn">
						<div id="map-canvas"></div>

					</div>
					<!-- centerColumn, END -->

				</div>
				<!-- centerColumnContainer, END -->


				<div id="sideBarRight">
					<div id="column3row1">
						<p>list targets here</p>
						<div id="targetlist">
							
						</div>
					</div>

				</div>
				<!-- sideBarRight, END -->

			</div>
			<!-- centerRightColumnPositioner, END -->

		</div>
		<!-- centerRightColumnContainer, END -->


		<!-- The left column div -->
		<div id="sideBarLeft">

			<div id="column1row1">

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
									<td><form:input type="text" id="required" name="required"
											path="required" /></td>
								</tr>
                                <tr>
									<td><form:label for="preferred" path="preferred">preferably also</form:label></td>
									<td><form:input type="text" id="preferred" name="preferred"
											path="preferred" /></td>
								</tr>
                                <tr>
									<td><form:label for="nearby" path="nearby">and is near to</form:label></td>
									<td><form:input type="text" id="nearby" name="nearby"
											path="nearby" /></td>
								</tr>
																								
								<tr>
									<td><input id="search" type="submit" value="Save" /></td>
								</tr>
							</table>
						</form:form>

					</div>
					<div id="add-target-tab">
						<div id="statusbox"></div>
						<div id="targetForm" class="span-12 last">
							<form:form modelAttribute="targetModel" action="/tagcloud/targetaaaaaaaaaaa" method="post">
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

			<!-- Required so that IE 6.0 doesn't mangle the last entry of content in this column -->
			<p>&nbsp;</p>

		</div>
		<!-- sideBarLeft, END -->

	</div>
	<!-- mainContainer, END -->


	<div style="height: 562px;" id="bg"></div>

	<%-- 	<c:set var="model" value="searchModel" /> --%>
	<%-- 	<c:set var="action" value="/tagcloud" /> --%>

	<script type="text/javascript">
		google.maps.event.addDomListener(window, 'load', initialize);
	</script>
	
	
</body>
</html>