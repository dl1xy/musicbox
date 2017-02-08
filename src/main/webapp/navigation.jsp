<%
String site = (String)session.getAttribute( "header_type" );
String btnPlaylist = ""; 
String btnUpload = "";
String btnSearch = "";
String btnStats = "";
String btnFeedback = "";
String btnAdminPlaylist = ""; 
String btnAdminServer = "";
String btnAdminUser = "";
String btnActive = " class='active'";

if (site != null && !site.equals(""))
{
	
	
	if (site.equals("playlist"))
		btnPlaylist = btnActive;
	else if (site.equals("search"))
		btnSearch = btnActive;
	else if (site.equals("upload"))
		btnUpload = btnActive;
	else if (site.equals("stats"))
		btnStats = btnActive;
	else if (site.equals("feedback"))
		btnFeedback = btnActive;
	else if (site.equals("admin_playlist"))
		btnAdminPlaylist = btnActive;
	else if (site.equals("admin_server"))
		btnAdminServer = btnActive;
	else if (site.equals("admin_user"))
		btnAdminUser = btnActive;
}

%>
 <nav class="navbar navbar-default">
   <div class="container-fluid">
     <div class="navbar-header">
       <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
         <span class="sr-only">Toggle navigation</span>
         <span class="icon-bar"></span>
         <span class="icon-bar"></span>
         <span class="icon-bar"></span>
       </button>
       <a class="navbar-brand" href="/index.jsp"><jsp:include page="/logo.jsp" /></a>
      </div>
      <div id="navbar" class="navbar-collapse collapse">
        <ul class="nav navbar-nav">
			<li<% out.print(btnPlaylist); %>><a href="/playlist.jsp"><i class="fa fa-list"></i> Playlist</a></li>
			<li<% out.print(btnSearch); %>><a href="/search.jsp"><i class="fa fa-search"></i> Suche</a></li>
			<li<% out.print(btnUpload); %>><a href="/upload.jsp"><i class="fa fa-upload"></i> Upload</a></li>
			<li<% out.print(btnStats); %>><a href="/stats.jsp"><i class="fa fa-bar-chart"></i> Stats</a></li>
			<li<% out.print(btnFeedback); %>><a href="/feedback.jsp"><i class="fa fa-comment"></i> Feedback</a></li>
			
    	<% 
    		if ((Integer)session.getAttribute("admin")==1) 
    		{ 
    	%> 
    		<li<% out.print(btnAdminServer); %>><a href="/admin_server.jsp"><i class="fa fa-gears"></i> Server</a></li>
			<li<% out.print(btnAdminPlaylist); %>><a href="/admin_playlist.jsp"><i class="fa fa-play"></i> Control</a></li>
			<li<% out.print(btnAdminUser); %>><a href="/admin_user.jsp"><i class="fa fa-users"></i> User</a></li>     
        <% 
        } 
        %>
        	<li><a href="/logout"><i class="fa fa-times"></i> Logout</a></li>
       </ul>
	
     </div><!--/.nav-collapse -->
   </div><!--/.container-fluid -->

 </nav>