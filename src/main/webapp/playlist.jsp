<%
	session.setAttribute("header_type","playlist");

%>
<jsp:include page="header.jsp" />
<script src="/js/playlistPrinter.js"></script>
<input type="hidden" id="userId" value="<% out.print((Integer)session.getAttribute("userId")); %>"/>
<center>
	<p>
		<div id="nowplaying">
			<div class="playingsong"  style="background-image:url(/images/tape_small.gif)">
				<div class="playingsongtitle" id="playingsongtitle" style="text-align: center; font-size:10px;position:absolute;margin-left:14px;margin-top:17px;width:170px;height:20px;"></div>
				<div class="playingsonguser" id="playingsonguser" style="text-align: center;font-size:12px;position:absolute;margin-left:10px;margin-top:75px;width:180px;height:16px;"></div>
				<div class="playingsongtimer" id="playingsongtimer" style="text-align: center;font-size:12px;position:absolute;margin-left:70px;margin-top:98px;width:60px;height:18px;"></div>
				<div class="playingsongprogressborder" id="playingsongprogressborder" style="border: 1px solid;border-color:#000;background:#ffffff;position:absolute;margin-left:69px;margin-top:113px;width:62px;height:8px;"></div>
				<div class="playingsongprogress" id="playingsongprogress" style="background:#ff0000;position:absolute;margin-left:70px;margin-top:114px;width:0px;height:6px;"></div>
			</div>
		</div>
	</p>
	<p>
		<div id="totalsongs"></div>
	</p>
	<p>
		<div id="playlist-div" style="display:none">
			<table id="playlist" class="table table-striped">			
				<tbody id="tbody">				
						
				</tbody>
			</table>
			
		</div>
		<div id="lastPostsLoader"></div>	
	</p>
</center>	

<jsp:include page="footer.jsp" />