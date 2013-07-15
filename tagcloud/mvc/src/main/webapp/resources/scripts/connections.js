
function setConnectionButtons() {
	if(isUserLoggedIn()){
		postLoginView();
	}else{
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
function preLoginView(){
	$("#login_div").css('display','block');
	$("#login_div img").click(function(){ window.location ="login?next=target"});
}
function postLoginView(){
	$("#login_div").css('display','none');
}