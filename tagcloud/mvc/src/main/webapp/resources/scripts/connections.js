var current_facebook_profile;
function setConnectionButtons() {
	//console.log("*** setConnectionButtons");
	if (isUserLoggedIn()) {
		postLoginView();
	} else {
		preLoginView();
	}
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
	return logged_in;
}
function hasError(data) {
	return ((data.status != null && data.status != "200") || (data.is_error != null && data.is_error == true));
}
function preLoginView() {
	console.log("*** preLoginView");
	$("#login_div img").css('display', 'block');
	$("#login_div img").click(function() {
		window.location = "login?next=/tagcloud"
	});
}
function postLoginView() {
	console.log("*** postLoginView");
	$("#login_div img").css('display', 'none');
	current_facebook_profile = getUserFacebookProfile_synchronized();
	loggedInUserInfoView();
}
function loggedInUserInfoView() {
	console.log("*** loggedInUserInfoView");
	var div = $("<div id= '#home_user_div'> </div>");
	var photo = $("<img alt='user image' src='/tagcloud/facebook/photo/"
			+ current_facebook_profile.id
			+ "' style='width: 52px; height:52px;' /> ");
	var name = $("<span style='font-weight:bolder; margin-left:5px;'>"
			+ current_facebook_profile.name + "</span>");
	$(div).attr("style", "white-space:nowrap;");
	$(div).append($(photo));
	$(div).append($(name));
	$(div).append(getLogoutElement());
	$("#login_div").html($(div));
}
function getUserFacebookProfile_synchronized() {
	console.log("*** getUserFacebookProfile_synchronized");
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
function getLogoutElement() {
	var span = $("<span style='font-weight:bolder;color:brown;float:right; margin-right:5px; cursor:pointer;'> Logout </span>");
	$(span).click(function() {
		$.getJSON('/tagcloud/logout', function(data) {
			window.location = "/tagcloud";
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
