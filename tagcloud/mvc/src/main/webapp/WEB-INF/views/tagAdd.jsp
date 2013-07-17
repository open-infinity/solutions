<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Tagcloud</title>
	<link rel="stylesheet"
		href="<spring:url value="/resources/styles/token-input-facebook.css" />"
		type="text/css" />
	<script type="text/javascript"
		src="/tagcloud/resources/scripts/jquery.min.js"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/scripts/json.min.js" />"></script>
	<script type="text/javascript"
		src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/scripts/jquery.tokeninput2.js" />"></script>
	<script type="text/javascript"
		src="/tagcloud/resources/scripts/tagpicker.js"></script>
		
	<script type="text/javascript">
		$(document).ready(function() {
			initTagAddOnDocumentReady();
		});
	</script>
</head>
<body>
	<div id="tag_add">
		<form:form modelAttribute="tagModel" action="/tagcloud/tag/${target_id}" method="post" id="tagAddForm">
			<table id="tagAddTable">
				<tr>
					<td><form:label for="text" path="text">add new tag</form:label></td>
					<td><form:input type="text" id="text" name="text" path="text" /></td>
				</tr>
				<tr>
					<td><input id="save" type="submit" value="Save" /></td>
				</tr>
			</table>
		</form:form>
	</div>
	<div>
		<ul>
			<c:forEach var="tag" items="${tags}">
				<li>
					${tag.text}
				</li>
			</c:forEach>
		</ul>
	</div>
</body>
</html>