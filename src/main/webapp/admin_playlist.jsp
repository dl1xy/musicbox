<% session.setAttribute("header_type","admin_playlist"); %>
<jsp:include page="/header.jsp" />

<script src="/js/adminPlaylistPrinter.js"></script>
	<center>
		
		<p><button id="scanBtn" class="btn btn-primary" style="width:200px"><i class="fa fa-search"></i> Scan MP3s</button></p>
		<p><button id="clearPlaylistBtn" class="btn btn-primary" style="width:200px"><i class="fa fa-list"></i> Clear Playlist</button></p>
		<p><button id="clearMp3sBtn" class="btn btn-primary" style="width:200px"><i class="fa fa-eraser"></i> Clear MP3 Database</button></p>
		<p><button id="playBtn" class="btn btn-primary" style="width:200px"><i class="fa fa-play"></i> Start Playing</button></p>
		<p><div id="scan-div"></div></p>
	</center>
<jsp:include page="/footer.jsp" />