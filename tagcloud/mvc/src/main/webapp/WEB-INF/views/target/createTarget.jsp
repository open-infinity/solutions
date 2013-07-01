<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/WEB-INF/views/common/includes.jsp"%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div class="container">
	<br />
	<h2>Create product</h2>
	<br />
	<div class="sub_menu">
		<b>Please Define The Product Information</b>
	</div>
	<br />
	<div id="statusbox"></div>
	<br />

	
	<div id="productForm" class="span-12 last">
		<div id="targetForm" class="span-12 last">
			<form:form modelAttribute="targetModel" action="target" method="post">
				<table id="targetTable">
					<tr>
						<td><form:label for="text" path="text" cssErrorClass="error">Name</form:label></td>
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
					<td>
						<p>
							<input id="save" type="submit" value="Save" />

						</p>
					</td>			
					</tr>
				</table>
			</form:form>
			
		</div>
	</div>
	
	<div id="map-canvas"></div>
	
</div>

<c:set var="model" value="targetModel" />
<c:set var="action" value="/tagcloud/target" />


<%@ include file="/WEB-INF/views/common/validation_scripts.jsp"%>
<%@ include file="/WEB-INF/views/common/footer.jsp"%>