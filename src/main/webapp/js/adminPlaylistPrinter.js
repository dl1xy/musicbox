

//When the page is fully loaded...
$(document).ready(function() {
	
	$("#scanBtn").click(function(event) {
    	
    	$("#scan-div").empty().html("<p><i class=\"fa fa-spinner fa-pulse fa-2x\"></i><br>Scanning ...</p>");
    	$.ajax({
    	    url: '/admin_scanner',
    	    cache: false,
    	   success: function(json) { 
    		   $("#scan-div").empty().append(json.filesFound +" MP3s gefungen, Suchzeit:"+json.workingTime);       		
        	},
            fail:function() { // on failure
               	alert("Request failed.");
            }
       	});
    	
    	
    });
    
    $("#clearPlaylistBtn").click(function(event) {
    	
    	$("#scan-div").empty().html("<p><i class=\"fa fa-spinner fa-pulse fa-2x\"></i><br>L&oouml;sche Playlist ...</p>");    	
    	
    	$.ajax({
    	    url: '/admin_playlist_clear',
    	    cache: false,
    	   success: function(resp) { // on sucess
    		   $("#scan-div").empty().html("<p>MP3s wurden aus der Playlist gel&ouml;scht");        		
        	},
            fail:function() { // on failure
               	alert("Request failed.");
            }
       	});
    	      	
    });
    
    $("#clearMp3sBtn").click(function(event) {
    	
    	$("#scan-div").empty().html("<p><i class=\"fa fa-spinner fa-pulse fa-2x\"></i><br>L&oouml;sche MP3s ...</p>");    	
    	
    	$.ajax({
    	    url: '/admin_mp3_clear',
    	    cache: false,
    	   success: function(resp) { // on sucess
    		   $("#scan-div").empty().html("<p>MP3s wurden aus der Datenbank gel&ouml;scht");      		
        	},
            fail:function() { // on failure
               	alert("Request failed.");
            }
       	});
    	     	
    });
    
    $("#playBtn").click(function(event) {
	
    	$("#scan-div").empty().append("<p><i class=\"fa fa-spinner fa-pulse fa-2x\"></i><br>Starte MP3 ...</p>");    	
    	
    	$.ajax({
    	    url: '/admin_play',
    	    cache: false,
    	   success: function(json) { // on sucess
    		   var pid = json.playlistId;
    		   $("#scan-div").empty().html("<p>Song mit PID "+pid+" gestartet");        		
        	},
            fail:function() { // on failure
               	alert("Request failed.");
            }
       	});
    	        	
    });
});