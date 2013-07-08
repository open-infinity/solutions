<script type="text/javascript">	
$(document).ready(function() {
		$("#required").tokenInput("/tagcloud/tag/autocomplete", {
			propertyToSearch : "text",
			preventDuplicates : true,
			theme : "facebook"
		});

		$("#${model}").submit(function() {
			var request = $(this).serializeObject();
			request.required = $("#required").tokenInput("get");
			//var request = $(this).serializeObject();
			$.postJSON("/tagcloud/", request);
			return false;
		});
	});
</script>

