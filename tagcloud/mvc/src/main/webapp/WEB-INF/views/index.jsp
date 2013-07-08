
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<%-- <%@ page session="false"%> --%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet"
	href="<spring:url value="/resources/styles/styles.css" />"
	type="text/css" />
<link rel="stylesheet"
	href="<spring:url value="/resources/styles/threeColumn.css" />"
	type="text/css" />
<script type="text/javascript"
	src="<c:url value="/resources/scripts/jquery.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/scripts/json.min.js" />"></script>



<title>TagCloud</title>
</head>
<body>
<div id="header"></div>
	<div id="mainContainer">

		

	</div>
	<!-- mainContainer, END -->
	<div id="footer"></div>

	<div style="height: 562px;" id="bg"></div>

	<c:set var="model" value="searchModel" />
	<c:set var="action" value="/tagcloud" />

	<%@ include file="/WEB-INF/views/common/styling.jsp"%>
	<%@ include file="/WEB-INF/views/common/search_script.jsp"%>

	<script type="text/javascript">
		google.maps.event.addDomListener(window, 'load', initialize);
	</script>
</body>
</html>