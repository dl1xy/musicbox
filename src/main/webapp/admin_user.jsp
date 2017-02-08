
<% session.setAttribute("header_type","admin_user"); %>

<%@ page import="de.darc.dl1xy.musicbox.db.*" %>
<%@ page import="de.darc.dl1xy.musicbox.user.*" %>
<%@ page import="java.util.*" %>


<jsp:include page="/header.jsp" />
<center>
	<p>
		<div>
			<table id="userList" class="table table-striped">
				<thead>
			      <tr>
			         <th>&nbsp;</th>
			         <th><i class="fa fa-child"></i></th>
			         <th><i class="fa fa-mobile"></i></th>
			         <th><i class="fa fa-chevron-circle-up"></i> R</th>
			         <th><i class="fa fa-chevron-circle-down"></i> R</th>
			         <th><i class="fa fa-chevron-circle-up"></i> S</th>
			         <th><i class="fa fa-chevron-circle-down"></i> S</th>
			         <th><i class="fa fa-upload"></i></th>
			         <th><i class="fa fa-play"></i></th>
			         <th><i class="fa fa-list"></i></th>
			         <th><i class="fa fa-gears"></i></th>
			      </tr>
			   </thead>
				<tbody id="tbody">				
				<%
					List<UserItem> users = UserHandler.getInstance().getAllUsers();
					for (UserItem u:users)
					{
						out.print("<tr>");
						out.print("<td>"+u.id+"</td>");
						out.print("<td>"+u.name+"</td>");
						out.print("<td>"+UserHandler.getInstance().parseUserAgent(u.userAgent)+"</td>");
						out.print("<td>"+u.voteUpRcvd+"</td>");
						out.print("<td>"+u.voteDownRcvd+"</td>");
						out.print("<td>"+u.voteUpSent+"</td>");
						out.print("<td>"+u.voteDownSent+"</td>");
						out.print("<td>"+u.songsUploaded+"</td>");
						out.print("<td>"+u.songsPlayed+"</td>");
						out.print("<td>"+u.songsPlaylisted+"</td>");
						out.print("<td>");
						if (u.isAdmin)
							out.print("<i class=\"fa fa-check\"></i>");
						else
							out.print("&nbsp;");
						out.print("</td>");
						out.print("</tr>");
					}
				%>	
				</tbody>
			</table>
		</div>
	</p>
</center>

<jsp:include page="/footer.jsp" />