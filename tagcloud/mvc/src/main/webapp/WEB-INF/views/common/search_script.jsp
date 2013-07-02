<script type="text/javascript">	
	$(document).ready(function() {
		$("#${model}").submit(function() {
			var request = $(this).serializeObject();
			$.postJSON("${action}", request);
			return false;				
		});
	});
</script>