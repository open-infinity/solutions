function initRequiredOnDocumentReady() {
	console.log("init tagpicker");
	$("#required").tokenInput("/tagcloud/tag/autocomplete", {
		propertyToSearch : "text",
		preventDuplicates : true,
		theme : "facebook",
		resultsLimit : 8
	});

	$("#searchModel").submit(function() {
		var request = $(this).serializeObject();
		request.required = $("#required").tokenInput("get");
		$.postJSON("/tagcloud/", request);
		return false;
	});
}
