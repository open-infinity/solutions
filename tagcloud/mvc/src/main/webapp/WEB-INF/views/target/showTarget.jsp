<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/WEB-INF/views/common/includes.jsp"%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="container">
	<br />
	<h2>${target.text}</h2>
	<br />
	<div class="sub_menu">
		<b>Target information</b>
	</div>
	
	<h3> </h3>
	<table>
	<tr>
	<td>Coordinates:</td>
	<td>${target.location[1]}, ${target.location[0]}</td>
	</tr>
	<tr>
	<td>Score:</td>
	</tr>
	<tr>
	<td>Tags:</td>
	</tr>
	<tr>
	<td>Comments:</td>
	</tr>
	<tr>
	<td>
	<c:forEach var="comment" items="${comments}">
				<tr>
					<td> </td>
				</tr>
	</c:forEach>
	</td>
	</tr>
	
	</table>
	
		</div>
	</div>
</div>

<c:set var="model" value="targetModel" />
<%@ include file="/WEB-INF/views/common/validation_scripts.jsp"%>
<%@ include file="/WEB-INF/views/common/footer.jsp"%>