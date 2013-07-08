<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page session="false"%>
<%@ include file="/WEB-INF/views/common/includes.jsp"%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>


<!-- The conainter divs for the center and the right columns -->
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
				<p>Veggies sunt bona vobis, proinde vos postulo esse magis
					caulie carrot wakame quandong summer purslane garlic kohlrabi
					prairie turnip mustard artichoke bok choy spring onion mung bean.

					Dulse cauliflower gram watercress sea lettuce dulse gourd epazote
					caulie. Shallot amaranth komatsuna potato pumpkin catsear sweet
					pepper. Spring onion parsnip cauliflower cucumber soybean celtuce
					kakadu plum lotus root watercress leek tatsoi celery gumbo garbanzo
					silver beet. Parsnip lentil broccoli rabe asparagus bamboo shoot
					beet greens. Wakame bitterleaf rutabaga napa cabbage cauliflower
					parsnip ricebean salsify scallion broccoli. Asparagus dulse shallot
					collard greens silver beet scallion catsear turnip greens kale
					brussels sprout celtuce lentil pumpkin chard. Wattle seed radicchio
					leek tigernut welsh onion daikon avocado chard bush tomato kakadu
					plum. Yarrow celtuce radicchio winter purslane catsear bell pepper
					wakame. Spring onion tigernut water spinach beetroot cauliflower
					gram spinach leek arugula grape shallot sweet pepper groundnut
					coriander komatsuna artichoke scallion. Parsley pumpkin summer
					purslane sea lettuce desert raisin broccoli rabe. Sierra leone
					bologi bok choy earthnut pea leek yarrow brussels sprout soko bell
					pepper chickweed tatsoi kakadu plum horseradish tomato. Sweet
					pepper parsnip sierra leone bologi potato daikon garlic turnip fava
					bean bok choy broccoli rabe kombu aubergine. Gourd onion lentil
					catsear okra cucumber collard greens sea lettuce squash turnip
					earthnut pea cauliflower. Brussels sprout prairie turnip ricebean
					cauliflower rock melon azuki bean napa cabbage sierra leone bologi
					water chestnut zucchini lotus root tomatillo scallion grape
					watercress parsley epazote leek. Courgette tigernut salad earthnut
					pea burdock gram radish garbanzo brussels sprout. Gourd winter
					purslane celery water chestnut kakadu plum beet greens squash
					celtuce napa cabbage scallion garlic fava bean turnip salad
					salsify. Kombu peanut dulse chickpea amaranth potato kohlrabi kombu
					asparagus swiss chard parsnip cress rutabaga garbanzo. Spring onion
					turnip greens quandong kakadu plum bok choy scallion beetroot onion
					okra shallot cress bunya nuts nori mung bean green bean tomatillo.
					Aubergine turnip greens fennel bunya nuts ricebean water chestnut
					pumpkin tatsoi tigernut sierra leone bologi dulse pea sprouts
					celtuce onion. Fava bean eggplant napa cabbage sorrel azuki bean
					pumpkin cauliflower bunya nuts watercress. Tomatillo endive bell
					pepper bunya nuts taro sweet pepper courgette bitterleaf catsear.
					Komatsuna coriander j�cama celery horseradish gourd gram cucumber
					zucchini maize.</p>
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
	<div id="statusbox"></div>
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
						<td><input id="save" type="submit" value="Save" /></td>
					</tr>
				</table>

			</form:form>
		</div>
	</div>

	<div id="column1row2"></div>
	<!-- Required so that IE 6.0 doesn't mangle the last entry of content in this column -->
	<p>&nbsp;</p>

</div>
<!-- sideBarLeft, END -->

</div>
<!-- mainContainer, END -->

<div style="height: 562px;" id="bg"></div>

<c:set var="model" value="targetModel" />
<c:set var="action" value="/tagcloud/target" />

<%@ include file="/WEB-INF/views/common/styling.jsp"%>
<%@ include file="/WEB-INF/views/common/validation_scripts.jsp"%>
<%@ include file="/WEB-INF/views/common/footer.jsp"%>