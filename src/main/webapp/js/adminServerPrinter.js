//When the page is fully loaded...
$(document).ready(function() {
	
	// Add an event that triggers when ANY button
	// on the page is clicked...
    $("#uploadPathBtn").click(function(event) {
    	var path = $("input#uploadPath").val();
    	$.ajax({
    	    url: '/admin_server_upload_path',
    	    data:{"uploadPath": path}, 
    	    cache: false,
    	    success: function(resp) { // on sucess
        		;        		
        	},
            fail:function() { // on failure
               	alert("Request failed.");
            }
       	});
    	
   
    });
    
    $("#durationBtn").click(function(event) {
   
    	var minVal = $("input#minDuration").val();
    	var maxVal = $("input#maxDuration").val();
    	
    	
    	$.ajax({
    	    url: '/admin_server_duration',
    	    data:{"minDuration": minVal, "maxDuration":maxVal}, 
    	    cache: false,
    	    success: function(resp) { // on sucess
        		;        		
        	},
            fail:function() { // on failure
               	alert("Request failed.");
            }
       	});
    	
   
    });
    
    $("#searchPathBtn").click(function(event) {
    	$.ajax({
    	    url: '/admin_server_search',
    	    data:{"searchPath": $("input#searchPath").val(), "searchAll":""}, 
    	    cache: false
       	});
    	
   
    });
    
    $("input#searchAll").change(function(event) {
    	$.ajax({
    	    url: '/admin_server_search',
    	    data:{"searchPath": "", "searchAll":$("input#searchAll").prop("checked")}, 
    	    cache: false
       	});
    	
   
    });
});