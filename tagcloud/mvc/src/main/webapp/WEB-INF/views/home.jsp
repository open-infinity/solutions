<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page session="false"%>
<%@ include file="/WEB-INF/views/common/includes.jsp"%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>


<c:set var="model" value="searchModel" />
<c:set var="action" value="/tagcloud" />



<div id="column1">
	<div id="column1row1">
		<p>I want to go to a place that...</p>
		
		
		<p>I want to go there on...</p>
		<form:form modelAttribute="searchModel" action="${model}" method="post">
			<table id="searchTable">
				<tr>
					<td><form:label for="required" path="required">Name</form:label></td>
					<td><form:input type="text" id="required" name="required" path="required"/></td>
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
	<div id="column1row2">
		<p>


		</p>
	</div>
</div>
<div id="column2">
	<div id="map-canvas"></div>
</div>

<script type="text/javascript">
			$(document).ready(function () {
			    $("#required").tokenInput("/tagcloud/tag/autocomplete", {
			    	propertyToSearch: "text",
			    	preventDuplicates: true,
			    	theme: "facebook"
			    });
				
			    $("#${model}").submit(function() {
			    	var request = $(this).serializeObject();
  		            request.required = $("#required").tokenInput("get");
  		            //var request = $(this).serializeObject();
			    	$.postJSON("/tagcloud/", request);
		            return false;
				});
			});
		</script>
		

<%@ include file="/WEB-INF/views/common/footer.jsp"%>