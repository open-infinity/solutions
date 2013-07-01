<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/WEB-INF/views/common/includes.jsp"%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="container">
	<br />
	<h2>Target</h2>
	<br />
	<div class="sub_menu">
		<b>Target information</b>
	</div>
	<br />
	<div id="statusbox"></div>
	<br />
	
	
				<c:forEach var="target" items="${targets}">
				<tr>
					<td><spring:url value="targetModel/{id}" var="id">
							<spring:param name="id" value="${target.id}" />
						</spring:url> <a href="${fn:escapeXml(id)}">${target.id}</a>
					</td>
					<td>${target.text}</td>
				</tr>
			</c:forEach>

	
		</div>
	</div>
</div>

<c:set var="model" value="targetModel" />
<%@ include file="/WEB-INF/views/common/validation_scripts.jsp"%>
<%@ include file="/WEB-INF/views/common/footer.jsp"%>