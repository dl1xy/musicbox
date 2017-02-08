// When the page is fully loaded...
var page = 0;
var totalPages = 0;
var totalResultSongs = 0;
var totalSongs = 0;
var totalTime = 0;
var searchStr = "";
var songChecked = $('#option-song-cb').prop('checked');
var artistChecked = $('#option-artist-cb').prop('checked');
var albumChecked = $('#option-album-cb').prop('checked');
var random = false;
$(document).ready(function() {
	
	$("#searchBtn").click(function(event) {
		random = false;
		page=0;
		$("#tbody").empty();
    	//$( "#search-div" ).hide();

		$("#search-div-msg").empty().append("<i class=\"fa fa-spinner fa-pulse-la\"></i><br>");
    	
    	searchStr = $("#searchfield").val();
    	
    	if (searchStr.length < 3)
    	{
    		$("#search-div-msg").empty().append("Bitte mind. drei Zeichen eingeben");
    		return;
    	}
    	
    	songChecked = $('#option-song-cb').prop('checked');
    	artistChecked = $('#option-artist-cb').prop('checked');
    	albumChecked = $('#option-album-cb').prop('checked');
   	
    	if (!songChecked && !artistChecked && !albumChecked)
    	{
    		$("#search-div-msg").empty().append("Bitte mind. ein Suchfeld angeben !");
    		return;
    	}
    	searchAjax();	
	});
	
	$("#randomBtn").click(function(event) {
		
		page=0;
		$("#tbody").empty();
    	random = true;

		$("#search-div-msg").empty().append("<i class=\"fa fa-spinner fa-pulse-la\"></i><br>");
    	
    	searchAjax();	
	});
	
	function lastAddedLiveFunc()
    {
        $('div#lastPostsLoader').html('<img src="/images/ajax-loader.gif"/>');
        
        ++page;
        searchAjax();
        $('div#lastPostsLoader').empty();
    };
 
    //lastAddedLiveFunc();
    $(window).scroll(function(){
 
        var wintop = $(window).scrollTop(), docheight = $(document).height(), winheight = $(window).height();
        var  scrolltrigger = 0.95;
 
        if  ((wintop/(docheight-winheight)) > scrolltrigger) {
         //console.log('scroll bottom');
         lastAddedLiveFunc();
        }
    });
});


function searchAjax()
{
	$.ajax({
	    url: '/search',
	    cache: false,
	    data:{"searchStr": searchStr, "page":page, "songChecked":songChecked, "artistChecked":artistChecked, "albumChecked":albumChecked,"random":random},
	    success: function(resp) { // on sucess
    		$("#search-div-msg").empty();
    		printSongs(resp);
    		$('div#lastPostsLoader').empty();
    	},
        fail:function() { // on failure
            	alert("Request failed.");
            }
  	});
}

function addPlaylist(songId)
{
	if ($("#"+songId).hasClass("btn-success") && $("#i_"+songId).hasClass("fa-check"))
		return;
	
	
	
	$.ajax({
	    url: '/playlist_add',
	    cache: false,
	    data:{"songId": songId},
	    success: function(json) { 
	    	if (songId == json.songId)
	    	{
	    		$("#"+songId).removeClass("btn-primary").addClass("btn-success");
	    		$("#i_"+songId).removeClass("fa-list").addClass("fa-check");
	    	}
    			
        },
        fail:function() { // on failure
            alert("Request failed.");
        }
	});
}

function showPlaylistInfo(songId)
{
	// TODO
}

function printSongs(json) {
	
	$( "div#search-div" ).show();
		
	// Then add every band name contained in the list.	
	$.each(json, function(key, val) {
		
		if (key == "page")
		{
			//page = val;			
		}
		
		if (key == "totalAmount")
		{
			totalResultSongs = val;		
		}
		
		if (key == "totalTime")
		{
			totalTime = val;
		}
		
		if (key == "totalSongs")
		{
			totalSongs = val;
		}
		if (key == "songs")
		{			
			$.each(val, function(i, songObject) {
				
				var html= "<tr><td><span><b>"+unescape(songObject.artist)+"</b><br><b><i>"+unescape(songObject.title)+"</i></b><br><small>"+unescape(songObject.album)+"</small><br><small>"+songObject.duration+"</small></span></td>";
					
				if (!songObject.inPlaylist)
				{
					html +="<td><button class=\"btn btn-primary\" onClick=\"javascript:addPlaylist("+songObject.id+")\" id=\""+songObject.id+"\"><i class=\"fa fa-list\" id=\"i_"+songObject.id+"\"></i></button></td></tr>";
				}
				else
				{
					//alert ("song "+songObject.id+" is in playlist");
					html += "<td><button class=\"btn btn-success\"  onClick=\"javascript:showPlaylistInfo("+songObject.id+")\"><i class=\"fa fa-check\"></i></button></td></tr>";
				}
				$("#tbody").append(html);
							
			});			
		}
		
		$("#search-div-msg").empty();
		$("#search-div-msg").append(totalResultSongs+" Ergebnisse<br/>");
		$("#search-div-msg").append(totalTime+"ms Suchdauer<br/>"+totalSongs+" MP3s in der Datenbank<br/>");
		
	});
}
