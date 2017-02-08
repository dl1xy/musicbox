$(document).ready(function() {
	
	$("#feedbackBtn").click(function(event) {
		$.ajax({
		    url: '/feedback', 
		    cache:false,
		    data:{"msg": $("textarea#feedbackText").val()},
		    success: function(json) {
		    	if (json.errorCode == 0)
		    	{
		    		$("div#feedbackDiv").empty().html(json.feedbackMsg);
		    	}
		    	else
		    	{
		    		$("div#feedbackDiv").append(json.feedbackMsg);
		    	}
		    }
		});
	});
	
	
});