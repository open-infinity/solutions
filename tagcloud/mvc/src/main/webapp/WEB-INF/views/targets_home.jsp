<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>welcome to targets home!</title>

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
				<h1 id="target_title">Kaivokatu</h1>

				<div id="tag_bar">
					<h3>Tags:</h3>
					<span>I love this place!</span> <span>Amazing restaurants
						and shops in this area!</span> <span>another tag</span><span> and
						another one ..</span> <span>Nice Hotel right in the corner</span>
				</div>
			</div>

			<div id="map-canvas"></div>
		</div>
		<div id="target_score_main">
			<div>
				<h3>Score: <span id="score_value">8.0</span></h3>
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
			<div>

				<textarea rows="" cols=""></textarea>
			</div>

		</div>

		<div class="comment">

			<div class="comment_header">
				<img alt="profile image"
					src="https://fbcdn-sphotos-c-a.akamaihd.net/hphotos-ak-prn1/p480x480/156541_584368431587813_2134142774_n.jpg" />
				<span>Carlo Sagan</span>
			</div>
			<div class="comment_text">Lorem ipsum dolor sit amet,
				consectetur adipiscing elit. Sed at ante. Mauris eleifend, quam a
				vulputate dictum, massa quam dapibus leo, eget vulputate orci purus
				ut lorem. In fringilla mi in ligula. Pellentesque aliquam quam vel
				dolor. Nunc adipiscing. Sed quam odio, tempus ac, aliquam molestie,
				varius ac, tellus. Vestibulum ut nulla aliquam risus rutrum
				interdum. Pellentesque lorem.</div>


		</div>

		<div class="comment">

			<div class="comment_header">
				<img alt="profile image"
					src="https://fbcdn-sphotos-c-a.akamaihd.net/hphotos-ak-prn1/p480x480/156541_584368431587813_2134142774_n.jpg" />
				<span>Jukka Niemi</span>
			</div>
			<div class="comment_text">Lorem ipsum dolor sit amet,
				consectetur adipiscing elit. Sed at ante. Mauris eleifend, quam a
				vulputate dictum, massa quam dapibus leo, eget vulputate orci purus
				ut lorem. In fringilla mi in ligula. Pellentesque aliquam quam vel
				dolor. Nunc adipiscing. Sed quam odio, tempus ac, aliquam molestie,
				varius ac, tellus. Vestibulum ut nulla aliquam risus rutrum
				interdum. Pellentesque lorem.</div>


		</div>

		<div class="comment">

			<div class="comment_header">
				<img alt="profile image"
					src="https://fbcdn-sphotos-c-a.akamaihd.net/hphotos-ak-prn1/p480x480/156541_584368431587813_2134142774_n.jpg" />
				<span>Jamal Karim</span>
			</div>
			<div class="comment_text">Lorem ipsum dolor sit amet,
				consectetur adipiscing elit. Sed at ante. Mauris eleifend, quam a
				vulputate dictum, massa quam dapibus leo, eget vulputate orci purus
				ut lorem. In fringilla mi in ligula. Pellentesque aliquam quam vel
				dolor. Nunc adipiscing. Sed quam odio, tempus ac, aliquam molestie,
				varius ac, tellus. Vestibulum ut nulla aliquam risus rutrum
				interdum. Pellentesque lorem.</div>


		</div>

		<div class="comment">

			<div class="comment_header">
				<img alt="profile image"
					src="https://fbcdn-sphotos-c-a.akamaihd.net/hphotos-ak-prn1/p480x480/156541_584368431587813_2134142774_n.jpg" />
				<span>Kim kardashian</span>
			</div>
			<div class="comment_text">Lorem ipsum dolor sit amet,
				consectetur adipiscing elit. Sed at ante. Mauris eleifend, quam a
				vulputate dictum, massa quam dapibus leo, eget vulputate orci purus
				ut lorem. In fringilla mi in ligula. Pellentesque aliquam quam vel
				dolor. Nunc adipiscing. Sed quam odio, tempus ac, aliquam molestie,
				varius ac, tellus. Vestibulum ut nulla aliquam risus rutrum
				interdum. Pellentesque lorem.</div>


		</div>

	</div>
</body>
</html>