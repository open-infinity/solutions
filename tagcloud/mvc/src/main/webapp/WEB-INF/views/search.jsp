<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/WEB-INF/views/common/includes.jsp"%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>


<div class="container">
	<br />
	<h2>Search</h2>
	<br />
	<div class="sub_menu">

	</div>
	<br />
	<div id="statusbox"></div>
	<br />



	<div id="targetForm" class="span-12 last">
		<table class="style: {left-margin: 50px;}">
			<thead>
				<th>Tag</th>
			</thead>
			<c:forEach var="tag" items="${required}}">
				<tr>
					<td>"${tag.id}"</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>

<%@ include file="/WEB-INF/views/common/footer.jsp"%>