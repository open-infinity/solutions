<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Tagcloud target</title>
	
	<link rel="stylesheet" type="text/css" href="/tagcloud/resources/styles/styles.css" media="screen">
	<link rel="stylesheet" href="/tagcloud/resources/styles/target_and_comments.css" type="text/css">
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
	
	<script type="text/javascript" src="/tagcloud/resources/scripts/jquery.min.js"></script>
	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
	<script src="http://maps.googleapis.com/maps/api/js?v=3.exp&amp;sensor=false&amp;"></script>	
	<script type="text/javascript" src="/tagcloud/resources/scripts/target_and_comments.js"></script>
	
<style>
#header {
	top: 0;
	margin: 0 auto;
	width: 100%;
	height: 70px;
	min-height: 70px;
	margin-bottom: 15px;
	border-bottom: 6px black solid;
	display:none;
}

#header_inner {
	width: 59%;
	margin: 0 auto;
	color: black;
	font-weight: bolder;
}

#header_inner img {
	margin-top: 10px;
	width: 52px;
	height: 52px;
	max-width: 52px;
	max-height: 52px;
	border-radius: 7px;
}

#header_user_name {
	margin-left: 5px;
}

#header_loging_switch {
	float: right;
	margin-right: 5px;
	margin-top: 30px;
}
</style>
</head>

<body>
	<header id="header">
		<div id="header_inner">
			<img alt="user_img"
				src="http://graph.facebook.com/kavan.sole/picture" /> <span
				id="header_user_name"> Kavan Soleimanbeigi</span> 
<!-- 				<span -->
<!-- 				id="header_loging_switch">Logout</span> -->
		</div>
	</header>
	
	<section>
		<div id="target_main">
			<div id="target_header">
				<div id="target_info">
					<h1 id="target_title">
						Title<!-- Empty Title! -->
					</h1>
					<div id="tag_bar">
						<div id="tag_header">
							<strong id="tag_title">Tags:</strong>
							<div id="new_tag_div" style="display: inline;">
								<small> add new tag: </small> <input id="tag_field" name="text"
									type="text" />
							</div>
						</div>
						<div id="tag_container"></div>
					</div>
				</div>
				<div id="map-canvas">
					<!-- Empty Map -->
				</div>
			</div>
			
			<div id="target_score_main">
				<div id="score_container"></div>
				<div id="score_note">
					<img alt="error_png"
						src="http://www.clker.com/cliparts/8/3/3/4/1195445190322000997molumen_red_round_error_warning_icon.svg.med.png" />
					Error on submit new score!
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
				
				<div id="comment_form_div">
					<form method="post" id="comment_form">
						<textarea name="text"></textarea>
						<input id="form_submit" value="send" type="submit">
					</form>
				</div>
			</div>
			
			<div id="comment_container">
			
			</div>
		</div>
	</section>

	<!-- <footer id="footer">
		<b>Copyright &copy; 2013 Tieto Ltd</b>
	</footer> -->
</body>

</html>