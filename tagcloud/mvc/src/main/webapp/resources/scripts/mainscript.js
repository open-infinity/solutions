$(document).ready( function() { 
	console.log("docu ready");
	initTagSearchOnDocumentReady();
	initFormValidation("targetModel", "/tagcloud/target");
	styleTargetDivs();
});
