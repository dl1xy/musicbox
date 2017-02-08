<% session.setAttribute("header_type","stats"); %>
<%@ page import="de.darc.dl1xy.musicbox.user.*" %>
<%@ page import="java.util.*" %>


<jsp:include page="/header.jsp" />
<style>
<!--
	.bar
	{
		background-color:blue; 
		height:18px; 
	}
	.mytable td {
    padding: 5px
}
-->
</style>
<center>
	
	<p>
		
			<table class="mytable" style="width:100%">
				<tr><td colspan="4"><p><h4>Top 5 Most Played</h4></p></td></tr>
				
				<%
				{
					List<UserItem> users = UserHandler.getInstance().getTop5MostPlayed();
					int rank=1;
					float ref=users.get(0).songsPlayed;
					for (UserItem u:users)
					{
						
						int width=(int)Math.round(((float)u.songsPlayed / ref) * 100f);
						out.print("<tr>");
						out.print("<td width=\"10%\">"+(rank++)+".</td>");
						out.print("<td width=\"20%\">"+u.name+"</td>");
						out.print("<td width=\"60%\"><div class=\"bar\" style=\"width:"+width+"%\">&nbsp;</div> </td>");
						out.print("<td width=\"10%\">"+u.songsPlayed+"</td>");
						
						out.print("</tr>");
					}
				}
				%>	
			</table>
			<table class="mytable"  style="width:100%">
				<tr><td colspan="4"><p><h4>Top 5 Best Voted</h4></p></td></tr>
				
				<%
				{
					List<UserItem> users = UserHandler.getInstance().getTop5BestVoted();
					int rank=1;
					float refDownVal = 1f;
					if (users.get(0).voteDownRcvd > 0)
						refDownVal = users.get(0).voteDownRcvd;
					float ref=users.get(0).voteUpRcvd / refDownVal;
					for (UserItem u:users)
					{
						
						float quotaDownVal = 1f;
						if (u.voteDownRcvd > 0)
							quotaDownVal = u.voteDownRcvd;
						float quota=u.voteUpRcvd / quotaDownVal;
						int width=(int)Math.round((quota / ref) * 100f);
						out.print("<tr>");
						out.print("<td width=\"10%\">"+(rank++)+".</td>");
						out.print("<td width=\"20%\">"+u.name+"</td>");
						out.print("<td width=\"60%\"><div class=\"bar\" style=\"width:"+width+"%\">&nbsp;</div></td>");
						out.print("<td width=\"10%\">"+quota+"</td>");						
						out.print("</tr>");
					}
				}
				%>
			</table>
			<table class="mytable" style="width:100%">
				<tr><td colspan="4"><p><h4>Top 5 Most Uploaded</h4></p></td></tr>
				
				<%
				{
					List<UserItem> users = UserHandler.getInstance().getTop5MostUploaded();
					int rank=1;
					float ref=users.get(0).songsUploaded;
					for (UserItem u:users)
					{
						
						int width=(int)Math.round(((float)u.songsUploaded / ref) * 100f);
						out.print("<tr>");
						out.print("<td width=\"10%\">"+(rank++)+".</td>");
						out.print("<td width=\"20%\">"+u.name+"</td>");
						out.print("<td width=\"60%\"><div class=\"bar\" style=\"width:"+width+"%\">&nbsp;</div></td>");
						out.print("<td width=\"10%\">"+u.songsUploaded+"</td>");
						out.print("</tr>");
					}
				}
				%>	
				
			</table>
		
	</p>
</center>

<jsp:include page="/footer.jsp" />