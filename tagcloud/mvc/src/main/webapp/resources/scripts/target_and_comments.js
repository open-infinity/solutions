var map;
var star = "&#x02605;";
var empty_star = "&#x02606;";
var default_author_img = "http://www.howsimple.com/assets/imgs/person_empty.png";
var current_target = null;
var current_user = null;
var default_score;
var test_object;
$('document')
		.ready(
				function() {

					var loading_element_style = "position:absolute;text-align:center; width:100%; margin:0 auto; font-size:xx-large; color:silver;display:block;";
					var main_loading_element = getLoadingElement(
							"main_loading_div", loading_element_style);

					$('#target_main').css('display', 'none');
					$('body').prepend($(main_loading_element));
					$.when(init()).done(function() {
						$(main_loading_element).fadeOut(1000);
						$('#target_main').fadeIn(2000);
					
					});
				});
function getLoadingElement(id, style) {
	var loading_div = $("<div id='" + id + "' style= '" + style
			+ "'> Loading... </div>");
	return loading_div;
}
function init() {
	//getAndPrintTargetListInConsole();
	target_id = getURLParameter("target_id");
	if (target_id != null) {

		console.log("target_id: " + target_id);
		getTargetAndUpdateUi(target_id);
	}
	
}
function scoreTarget(stars) {
	var result = null;
	var obj = null;
	var form = $("<form><input name='score' value='" + stars + "'/></form>");
	if (current_target != null && current_user != null) {
		obj = $.ajax({
			type : 'POST',
			async : false,
			url : 'score/' + current_target.id,
			data : $(form).serialize()

		});
	} else {
		console.log("score target failed target or user can't be null");
	}
	result = $.parseJSON(obj.responseText);
	return result;
}
function getAndPrintTargetListInConsole() {

	$.getJSON("target/list", function(data) {
		var targets = data.result_list;
		$.each(targets, function(i, target) {
			console.log(i + " new model id: " + target.id);
			console.log(i + " text: " + target.text);
			// console.log(target);

		});
	});

}
function getTarget(target_id) {
	var result = null;
	var obj = null;
	obj = $.ajax({
		type : 'GET',
		async : false,
		url : 'target/' + target_id,
	});
	result = $.parseJSON(obj.responseText);
	return result.result_list[0];
}
function getTargetAndUpdateUi(target_id) {

	var target = getTarget(target_id);
	if (target != null) {
		setTargetInUi(target);
	}
}
function setTargetInUi(target) {
	current_target = target;
	$("#target_title").html(target.text);
	$("#score_value").html(target.score);
	setTagsInTagBar(target.tags);
	google_map_initialize(target.location[0], target.location[1]);
	// getTargetCommentsAndUpdateUi(target.id);
	createComments(target.comments);
	default_score = Math.round(target.score);
	if (isUserLoggedIn()) {
		postLoginView(target);
	} else {
		preLoginView(target);
	}
}
function preLoginView(target) {
	$("#comment_form_div").css('display', 'none');
	$("#default_login_main").css('display', 'inline-block');
	$("#default_login_main img").click(function() {
		window.location = "login?next=target?target_id=" + target.id;
	});
	setScoreStars(default_score, true);
	$("#new_tag_div").css('display', 'none');
	$("#header").css('display','none');
}
function postLoginView(target) {
	current_user = getUserFacebookProfile_synchronized();
	$("#default_login_main").css('display', 'none');
	$("#comment_form_div").css('display', 'inline-block');
	$("#comment_form_div form").submit(function() {
		submitComment(this, target.id);
		return false;// disable the default action of the form
	});
	animateScore(default_score, true);
	setupAddNewTagFeature();
	$("#header").css('display','block');
	$("#header img").attr('src','/tagcloud/facebook/photo/'+ current_user.id);
	$("#header_user_name").html(current_user.name);
	$("#header").append(getLogoutElement());
}
function setupAddNewTagFeature() {
	$("#new_tag_div").css('display', 'inline');
	initTagFieldAutoComplete();
	$("#tag_field").keypress(function(e) {
		if (e.which == 13) {
			console.log("field value" + $('#tag_field').val());
			$.when(sendNewtag($("#tag_field").val())).done(function() {
				$("#tag_field").val("");
				var tags = getTargetTags(current_target.id);
				setTagsInTagBar(tags);
			});
		}
	});	
}
function isUserLoggedIn() {
	var logged_in = false;
	$.ajax({
		type : 'GET',
		async : false,
		url : 'check/connection',
		success : function(data) {
			if (!hasError(data) && data.result_list[0] != null) {
				logged_in = data.result_list[0].logged_in;
			}
		}
	});
	//console.log("*** isUserLoggedIn: " + logged_in);
	return logged_in;
}
/**
 * Refactoring needed!!!!!!!!
 * 
 * @param form
 * @param target_id
 */
function submitComment(form, target_id) {
	path = "comment/" + target_id;
	console.log("sendig comment to server, comment's text is: "
			+ $($(form).find("textarea")[0]).val());
	console.log("server path " + path);
	setCommentsDivInLoadingStage();
	try {
		$.ajax({
			type : 'POST',
			cache : false,
			url : path,
			data : $(form).serialize(),
			success : function(data) {
				console.log(data);
				test_object = data;
				if (hasError(data)) {
					handleSubmitCommentErrors(data);
				} else {
					handleSubmitCommentSuccess(data);
				}
			}
		});
	} catch (e) {
		console.log("error" + e);
	}
}
function handleSubmitCommentSuccess(data, target_id) {

	$("#comment_success_header > h3").html(data.message);
	$("#add_comment_errors").css('display', 'none');
	$("#add_comment_success").css('display', 'block');
	$("#target_add_comment_main textarea").val("");
	$.when(getCurrentTargetCommentsAndUpdateUi()).done();
	$("#add_comment_success").delay(4000).slideUp(2000);
}

function handleSubmitCommentErrors(error) {
	$("#comment_errors_header > h3").html(data.message);
	var list = $("#add_comment_errors > ul");
	$(list).html("");
	if (data.error_reasons != null) {
		$.each(data.error_reasons, function(i, reason) {
			$(list).append("<li>" + reason + "</li>");
		});
	}
	$("#add_comment_errors").css('display', 'block');
	$("#add_comment_success").css('display', 'none');
}
function hasError(data) {
	if (data == null) {
		return true;
	}
	return ((data.status != null && data.status != "200") || (data.is_error != null && data.is_error == true));
}

function setCommentsDivInLoadingStage() {
	var loading_element_style = "text-align:center; width:65%; margin:0 auto; font-size:xx-large; color:silver;display:block;";
	var div_id = "comments_loading_div";
	var comments_loading_element = getLoadingElement(div_id,
			loading_element_style);
	$("#comment_container").html(comments_loading_element);
}
function getCurrentTargetCommentsAndUpdateUi() {
	if (current_target != null) {
		var target_id = current_target.id;
		getTargetCommentsAndUpdateUi(target_id);
	} else {
		console.log("current_target is null!!");
	}

}
function getTargetCommentsAndUpdateUi(target_id) {
	$.when({
		loading : setCommentsDivInLoadingStage(),
		comments_list : getTargetComments(target_id)
	}).done(function(data) {
		if (data.comments_list != null) {
			createComments(data.comments_list);
		} else {
			console.log("comments null [getTargetCommentsAndUpdateUi] ");
		}
	});
}
function getTargetComments(target_id) {
	var obj = $.ajax({
		type : 'GET',
		async : false,
		url : 'comment/list/' + target_id,

	});
	var result = $.parseJSON(obj.responseText);
	if (!hasError(result)) {
		return result.result_list;
	}
	return null;

}
function createComments(data_array) {
	$("#comment_container").html("");
	$
			.each(
					data_array,
					function(i, comment) {
						var fb_user = getFacebookProfile_synchronized(comment.profile.facebookId);
						createNewComment("facebook/photo/" + fb_user.id,
								fb_user.name, comment.id, (new Date(
										comment.date)).toLocaleString(),
								comment.text);
					});
}
function getUserFacebookProfile_synchronized() {
	var facebook_profile = null;
	$.ajax({
		type : 'GET',
		async : false,
		url : 'facebook/user/profile/',
		success : function(data) {
			if (!hasError(data) && data.result_list[0] != null) {
				facebook_profile = data.result_list[0];
			}
		}
	});
	return facebook_profile;
}
function getFacebookProfile_synchronized(facebook_id) {
	var facebook_profile = null;
	$.ajax({
		type : 'GET',
		async : false,
		url : 'facebook/profile/' + facebook_id,
		success : function(data) {
			if (!hasError(data) && data.result_list[0] != null) {
				facebook_profile = data.result_list[0];
			}
		}
	});
	return facebook_profile;
}

function createNewComment(author_img, author_name, comment_id, comment_date,
		text) {

	var comment = $("<div></div>");
	$(comment).attr('class', 'comment');
	$(comment).attr('id', comment_id);
	var comment_head = $("<div></div>");
	$(comment_head).attr('class', 'comment_header');
	var comment_text = $("<div></div>");
	$(comment_text).attr('class', 'comment_text');

	$(comment_head).html(
			"<img alt='" + author_name + " image' src='" + author_img
					+ "' /> <span class='comment_name'>" + author_name
					+ "</span><span class='comment_date'>Created: "
					+ comment_date + "</span> ");
	$(comment_text).html(text);

	$(comment).append($(comment_head));
	$(comment).append("<hr />");
	$(comment).append($(comment_text));

	$("#comment_container").append($(comment));
}
function setTagsInTagBar(tags) {
	var bar = $("#tag_container");
	bar.html("");

	$.each(tags, function(i, tag) {
		bar.append("<span id=" + tag.id + ">" + tag.text + "</span>");
	});
}

function google_map_initialize(long, lat) {
	var mapOptions = {
		zoom : 16,
		center : new google.maps.LatLng(lat, long),
		mapTypeId : google.maps.MapTypeId.ROADMAP,
		panControl : false,
		zoomControl : false,
		scaleControl : false,
	};
	map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
	map.setOptions({
		styles : [ {
			featureType : "poi",
			elementType : "labels",
			stylers : [ {
				visibility : "off"
			} ]
		} ]
	});
	var marker = new google.maps.Marker({
		position: new google.maps.LatLng(lat, long),
		map: map
	});
}

function getURLParameter(sParam) {
	var sPageURL = window.location.search.substring(1);
	var sURLVariables = sPageURL.split('&');
	for ( var i = 0; i < sURLVariables.length; i++)

	{
		var sParameterName = sURLVariables[i].split('=');

		if (sParameterName[0] == sParam)

		{

			return sParameterName[1];
		}
	}
}
function animateScore(num, isDefault) {
	$.when(setScoreStars(num, isDefault)).done(setHandlers);
}
function setHandlers() {
	$.each($('.score_symbol'), function(i, span) {
		$(span).attr('onmouseover',
				'animateScore(' + $(span).index() + ',false)');
		$(span).attr('onmouseout', 'animateScore(' + default_score + ',true)');
		$(span).attr('onmousedown',
				'submitScoreAndUpdateView(' + $(span).index() + ')');

	});

}
function submitScoreAndUpdateView(stars) {
	var result = scoreTarget(stars);
	if (!hasError(result) && current_target != null) {
		current_target = getTarget(current_target.id);
		default_score = Math.round(current_target.score);
		setScoreStars(default_score, true);
		$("#score_note").css('display', 'none');
	} else {
		$("#score_note").css('display', 'block');
	}
}
function setScoreStars(num, isDefault) {

	var div = $("#score_container");
	var head = $("<div id='score_head'></div>")
	try {
		$(head).html(
				"<span id='default_score' >Score: " + default_score.toFixed(1)
						+ " </span>");
		if (!isDefault) {
			var num_str = num;
			if (num < 10) {
				num_str = "<span style='color:white;'>0</span>" + num;
			}
			$(head).append(
					"<span id='new_user_score'>click on star to submit "
							+ num_str + " /10 </span>");
		}

		$(div).html($(head));
		var i;
		for (i = 0; i < num; i++) {
			$(div).append("<span class='score_symbol'>&#x02605;</span>")
		}
		for (i; i < 10; i++) {
			$(div).append("<span class='score_symbol'>&#x02606;</span>")
		}
		$(div).append($(getScoreFooter()));

	} catch (e) {
		console.log("Error " + e.toString());
	}
}
function getScoreFooter() {
	var footer = $("<div id='score_footer'></div>");
	var score_str = "";
	footer.html("");
	var isUserScored = false;
	if (current_user != null && current_target != null) {
		$.each(current_target.scores, function(i, score) {
			if (score.profile.facebookId === current_user.id) {
				score_str = "You scored this target by " + score.stars;
				isUserScored = true;
				return;
			}
		});
		footer.append(score_str);
		if (!isUserScored) {
			footer.append("You're not scored this target yet! ");
		}

	}
	return footer;
}
// //****************


function getTargetTags(target_id) {
	var obj = $.ajax({
		type : 'GET',
		async : false,
		url : 'tag/json/' + current_target.id,
	});
	var data = $.parseJSON(obj.responseText);
	if (!hasError(data)) {
		return data.result_list;
	}
	return null;
}
function sendNewtag(tag_text) {
	console.log("submittaa");
	var form = $("<form><input name='text' value='" + tag_text + "' /> </form>");
	$.ajax({
		type : 'POST',
		async : false,
		url : 'tag/json/' + current_target.id,
		data : $(form).serialize(),
		success : function(data) {
			console.log(data);
		}
	});
}
function initTagFieldAutoComplete(){
	$(function() {
		 
		  $( "#tag_field" ).autocomplete({
		    source: "/tagcloud/tag/autocomplete_2",
		    minLength: 1		  
		  });
		});
}
function getLogoutElement() {
	var span = $("<span id='header_loging_switch' style='font-weight:bolder;color:brown;cursor:pointer;'> Logout </span>");
	$(span).click(function() {
		$.getJSON('/tagcloud/logout', function(data) {
			window.location = "/tagcloud/target?target_id="+current_target.id;
		});
	});

	$(span).mouseover(function() {
		$(this).css('color:red');
	});
	$(span).mouseleave(function() {
		$(this).css('color:brown');
	});

	return $(span);
}
