<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ include file="/WEB-INF/views/common/includes.jsp"%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>

<div id="column1">
	<div id="column1row1">
		<p>I want to go to a place that...</p>
		<form>
			<input type="radio" name="tagChoice" value="must" checked="checked">must
			have<br> <input type="radio" name="tagChoice" value="might">might
			have<br> <input type="radio" name="tagChoice" value="nearby">has
			nearby<br> <input type="text" name="tagFilter"><br>
			<input type="submit" value="add">
		</form>

		must have:
		<div id="mustContainer"></div>
		<div id="advancedSearch">
			might have:
			<div id="mightContainer"></div>
			has nearby:
			<div id="nearbyContainer"></div>


			<p>I want to go there on...</p>
			<form>
				<select name="weekday">
					<option value="monday">Monday</option>
					<option value="tuesday">Tuesday</option>
					<option value="wednesday">Wednesday</option>
					<option value="thursday">Thursday</option>
					<option value="friday">Friday</option>
					<option value="saturday">Saturday</option>
					<option value="sunday">Sunday</option>
				</select> <br>

			</form>
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



<%@ include file="/WEB-INF/views/common/footer.jsp"%>