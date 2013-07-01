<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/WEB-INF/views/common/includes.jsp"%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>


<div class="container">
	<br />
	<h2>List Targets</h2>
	<br />
	<div class="sub_menu">

	</div>
	<br />
	<div id="statusbox"></div>
	<br />



	<div id="targetForm" class="span-12 last">
		<table class="style: {left-margin: 50px;}">
			<thead>
				<th>Target</th>
				<th>Some Value</th>
			</thead>
			<c:forEach var="target" items="${targets}">
				<tr>
					<td><spring:url value="targetModel/{id}" var="id">
							<spring:param name="id" value="${target.id}" />
						</spring:url> <a href="${fn:escapeXml(id)}">${target.id}</a>
					</td>
					<td>${target.text}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>