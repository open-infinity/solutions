<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Tagcloud</title>

<link rel="stylesheet"
	href="/tagcloud/resources/styles/target_and_comments.css" />
<script type="text/javascript"
	src="/tagcloud/resources/scripts/jquery.min.js"></script>
<script type="text/javascript"
	src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script
	src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>
<script type="text/javascript"
	src="/tagcloud/resources/scripts/target_and_comments.js"></script>
</head>
<body>
	<div id="target_main">
		<div id="target_header">
			<div id="target_info">
				<h1 id="target_title">
					<!-- 				Empty Title! -->
				</h1>

				<div id="tag_bar">
					<h3>Tags:</h3>
					<div>
						<!-- 						empty tag bar! -->
						<!-- 						<span>I love this place!</span> <span>Amazing restaurants -->
						<!-- 							and shops in this area!</span> <span>another tag</span><span> and -->
						<!-- 							another one ..</span> <span>Nice Hotel right in the corner</span> -->
					</div>
				</div>
			</div>

			<div id="map-canvas">
				<!-- 			Empty Map -->
			</div>
		</div>
		<div id="target_score_main">
			<div>
				<h3>
					Score: <span id="score_value"> <!-- empty scroe value-->
					</span>
				</h3>
				<span class="score_symbol">&#x02605;</span> <span
					class="score_symbol">&#x02605;</span> <span class="score_symbol">&#x02605;</span>
				<span class="score_symbol">&#x02605;</span> <span
					class="score_symbol">&#x02605;</span> <span class="score_symbol">&#x02605;</span>
				<span class="score_symbol">&#x02605;</span> <span
					class="score_symbol">&#x02605;</span> <span class="score_symbol">&#x02606;</span>
				<span class="score_symbol">&#x02606;</span>

			</div>

		</div>
		<div id="target_add_comment_main">

			<div id="add_comment_success">
				<div id="comment_success_header">
					<!--  empty success header-->
					<img alt="error_png"
						src="http://sms.smsmarketing360.com/img/success_icon.png" />
					<h3></h3>
				</div>

			</div>

			<div id="add_comment_errors">
				<div id="comment_errors_header">
					<!--  empty error header-->
					<img alt="error_png"
						src="http://www.clker.com/cliparts/8/3/3/4/1195445190322000997molumen_red_round_error_warning_icon.svg.med.png" />
					<h3></h3>
				</div>
				<ul>
					<!--  empty error list-->
				</ul>
			</div>
			<div id="default_login_main">
			
					<h3>Login and tell your opinion about this place!</h3>
					<hr />
				<img alt="login_facebook"
					src="https://github.com/adozenlines/facebook-ios-sdk/diff_blob/adb3a65dfff79dc8dc30a1e3220a6bd0db017540/src/FacebookSDKResources.bundle/FBConnect/images/LoginWithFacebookPressed@2x.png?raw=true" />
			</div>


			<div>
				<form action="" method="post" id="comment_form">
					<textarea rows="" cols="" name="text"></textarea>
					<input value="send" type="submit">
				</form>


			</div>

		</div>
		<div id="comment_container">
			<!-- 			empty comment container -->

			<!-- 		example comment
			<!--	<div class="comment"> -->
			<!-- 				<div class="comment_header"> -->
			<!-- 					<img alt="profile image" -->
			<!-- 						src="http://www.howsimple.com/assets/imgs/person_empty.png" /> <span>Carlo -->
			<!-- 						Sagan</span> -->
			<!-- 				</div> -->
			<!-- 				<div class="comment_text">Lorem ipsum dolor sit amet, -->
			<!-- 					consectetur adipiscing elit. Sed at ante. Mauris eleifend, quam a -->
			<!-- 					vulputate dictum, massa quam dapibus leo, eget vulputate orci purus -->
			<!-- 					ut lorem. In fringilla mi in ligula. Pellentesque aliquam quam vel -->
			<!-- 					dolor. Nunc adipiscing. Sed quam odio, tempus ac, aliquam molestie, -->
			<!-- 					varius ac, tellus. Vestibulum ut nulla aliquam risus rutrum -->
			<!-- 					interdum. Pellentesque lorem.</div> -->
			<!-- 			</div> -->
		</div>


	</div>
</body>
</html>