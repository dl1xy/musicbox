//*** This code is copyright 2002-2003 by Gavin Kistner, !@phrogz.net
//*** It is covered under the license viewable at http://phrogz.net/JS/_ReuseLicense.txt
//*** Reuse or modification is free provided you abide by the terms of that license.
//*** (Including the first two lines above in your source code satisfies the conditions.)

// Include this code (with notice above ;) in your library; read below for how to use it.

String.prototype.toHHMMSS = function () {
    var sec_num = parseInt(this, 10); // don't forget the second param
    var hours   = Math.floor(sec_num / 3600);
    var minutes = Math.floor((sec_num - (hours * 3600)) / 60);
    var seconds = sec_num - (hours * 3600) - (minutes * 60);

    if (hours   < 10) {hours   = "0"+hours;}
    if (minutes < 10) {minutes = "0"+minutes;}
    if (seconds < 10) {seconds = "0"+seconds;}
    var time    = hours+':'+minutes+':'+seconds;
    return time;
}

var page = 0;
var totalSongs = 0;
var counter = 0;
var currentSongId = 0;
var playlistVersion = 0;
var songDurationMs = 0;
var songProgress 	= 0;
var songPosition 	= 0;
var progressBarWidth = $("#playingsongprogress").width();
var delay = 1000;

$(document).ready(function() {
	playlistAjax();
	
});

(function(){
	 setInterval(function(){	 
		 
		 updateSongPositionAndProgress(); 
		 
	 }, delay);
	})();

function vote(playlistId, vote)
{	
	$.ajax({
	    url: '/playlist_vote',
	    cache: false,
	    data:{"playlistId":playlistId, "vote":vote},
	    success: function(resp) {
	    	playlistAjax();
	    }
	});
}

function playlistAjax()
{
	counter = 0;
		
	$.ajax({
	    url: '/playlist', 
	    cache:false,
	    data:{"page": page},
	    success: function(json) {
	    	printPlaylistSongs(json);
	    }
	});	
}

function deleteSong(playlistId)
{
	$.ajax({
	    url: '/playlist_remove', 
	    cache:false,
	    data:{"playlistId": playlistId},
	    success: function(json) {
	    	if (json.queryResult > 0)
	    		playlistAjax();
	    }
	});
}

function showNextPage()
{
	++page;	
	playlistAjax();
}

function updateSongPositionAndProgress()
{
	
	$.ajax({
	    url: '/position', 
	    cache:false,
	    success: function(json) {
	    	console.log("song id "+json.songId);
	    
	    	songPosition = json.position;
	    	songProgress = json.progress;
	    	
	    	var position = songPosition / 1000;
	    //	console.log("songPosition:"+position+" songProgress:"+songProgress);
	    	position += "";	
	    	$("#playingsongtimer").html(position.toHHMMSS());
	    	$("#playingsongprogress").width(60/100 * songProgress);
	    	if (currentSongId != json.songId)
			{			
	    		currentSongId = json.songId;
				 songPosition = 0;
				 playlistAjax();
				 return;
			 }
	    	
	    	if(playlistVersion != json.playlistVersion)
	    	{
	    		playlistVersion = json.playlistVersion;
	    		playlistAjax();
	    		return;
	    	}
	    }
	});
	
}
function printPlaylistSongs(json) {
	
	
	$( "div#playlist-div" ).show();
	
	$.each(json, function(key, val) {
		
		if (key == "currentSong")
		{
			currentSongId = val.songId;
			songDurationMs = val.songDurationMs;
			songProgress = val.songProgress;
			songPosition = val.songPosition;
			console.log();
			var tapeTitle = unescape(val.songArtist)+" - "+unescape(val.songTitle);
			if (tapeTitle.length > 26)
				tapeTitle = tapeTitle.substring(0,26) + "...";
			console.log("id:"+currentSongId+" songDurationMs:"+songDurationMs+" tapeTitle:"+tapeTitle+" songProgress:"+songProgress+" songPosition:"+songPosition);
			$("#playingsongtitle").empty();
			$("#playingsonguser").empty();
			$("#playingsongtitle").html(tapeTitle);
			$("#playingsonguser").html("Song von "+unescape(val.userName));
			//updateSongPositionAndProgress();
			
		}
		
		else if (key == "totalSongs")
		{
			$("#totalsongs").empty();
			$("#totalsongs").html(val+" Songs mit ");
			totalSongs = val;
			
		}
		
		else if (key == "totalPlaytime")
		{
			$("#totalsongs").append(val+" Spielzeit<br>");
		}
		
		else if (key == "songs")
		{
			var songs = val.length;
			
			if ((totalSongs) % songs > 0)
			{
				$("div#lastPostsLoader").empty();
				$("div#lastPostsLoader").html("<button id=\"moreBtn\" class=\"btn btn-primary\" onClick=\"javascript:showNextPage()\">Mehr ...</button>");
			}
			else
			{
				$("div#lastPostsLoader").empty();
			}
			
			
			$("#tbody").empty();
			
			$.each(val, function(i, songObject) {
				var html=
					" 		<tr>" +
					"			<td><p>"+(++counter)+"</p></td>" +
					"			<td><p><span><b>"+unescape(songObject.songArtist)+"</b><br><b><i>"+unescape(songObject.songTitle)+"</i></b><br><small>"+unescape(songObject.songAlbum)+"</small><br><small>"+songObject.songDuration+"</small></span></p></td>" +
					"			<td><p>"+unescape(songObject.userName)+"</p></td>";
				
					if (songObject.userId == $("#userId").val())
					{
						html +=	"<td align=\"center\"><p><button class=\"btn btn-small\" type=\"button\"><i class=\"fa fa-chevron-circle-up\"></i>&nbsp;"+songObject.up+"</button></td>"+
						"<td align=\"center\"><p><button class=\"btn btn-small\" type=\"button\"><i class=\"fa fa-chevron-circle-down\"></i>&nbsp;"+songObject.down+"</button></td>";
					}
					else 
					{
						if (songObject.isVoted == false)
						{	
							html +=	"<td align=\"center\"><p><button class=\"btn btn-small btn-success\" type=\"button\" onClick=\"javascript:vote("+songObject.id+", 1)\"><i class=\"fa fa-chevron-circle-up\"></i>&nbsp;"+songObject.up+"</button></td>"+
									"<td align=\"center\"><p><button class=\"btn btn-small btn-danger\" type=\"button\"  onClick=\"javascript:vote("+songObject.id+", 0)\"><i class=\"fa fa-chevron-circle-down\"></i>&nbsp;"+songObject.down+"</button></td>";
						}
						else
						{
							if (songObject.vote == true)
							{
								html +=	"<td align=\"center\"><p><button class=\"btn btn-small btn-success\" type=\"button\"><i class=\"fa fa-chevron-circle-up\"></i>&nbsp;"+songObject.up+"</button></td>"+
								"<td align=\"center\"><p><button class=\"btn btn-small\" type=\"button\"><i class=\"fa fa-chevron-circle-down\"></i>&nbsp;"+songObject.down+"</button></td>";
							}
							else
							{
								html +=	"<td align=\"center\"><p><button class=\"btn btn-small\" type=\"button\"><i class=\"fa fa-chevron-circle-up\"></i>&nbsp;"+songObject.up+"</button></td>"+
								"<td align=\"center\"><p><button class=\"btn btn-small btn-danger\" type=\"button\"><i class=\"fa fa-chevron-circle-down\"></i>&nbsp;"+songObject.down+"</button></td>";
							}
						}
					}
				if (songObject.userId != $("#userId").val())
					html += "	<td align=\"center\"><p>&nbsp;</td>";	
				else
					html += "	<td align=\"center\"><p><button class=\"btn btn-small btn-warning\" type=\"button\"  onClick=\"javascript:deleteSong("+songObject.id+")\"><i class=\"fa fa-trash\"></i></button></td>";	
				
				html += "		</tr>";
				
				$("#tbody").append(html);
								
			});			
		}
	});
	
};
/*
$(function() {
    startRefresh();
});

function startRefresh() {
    setTimeout(startRefresh,1000);
    playlistAjax();
}

*/