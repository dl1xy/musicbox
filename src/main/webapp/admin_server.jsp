<% session.setAttribute("header_type","admin_server"); %>
<%@ page import="de.darc.dl1xy.musicbox.system.*" %>
<%@ page import="org.apache.commons.lang3.StringEscapeUtils" %>
<%
	String uploadFolder= StringEscapeUtils.unescapeHtml3(SystemHandler.getInstance().getUploadFolder());
	String searchFolder= StringEscapeUtils.unescapeHtml3(SystemHandler.getInstance().getSearchFolder());
	int minDuration = SystemHandler.getInstance().getMinMp3Duration();
	int maxDuration = SystemHandler.getInstance().getMaxMp3Duration();
%>

<jsp:include page="/header.jsp" />
<script src="/js/adminServerPrinter.js"></script>
	<center>
		
		<p align="center">
			<input id="uploadPath" value="<% out.print(uploadFolder); %>" type="text" name="uploadPath" style="width:200px">
		</p>
		<p align="center">
			<button id="uploadPathBtn" class="btn btn-primary" style="width:200px"><i class="fa fa-upload"></i> Upload-Pfad</button>
		</p>
		<p align="center">
			Min: <input type="number" id="minDuration" value="<% out.print(minDuration); %>" min="0" max="1000000" size="3"> <br/>
			Max: <input type="number" id="maxDuration"  value="<% out.print(maxDuration); %>" min="1" max="1000000" size="3">
		</p>
		<p align="center">
			<button id="durationBtn" class="btn btn-primary" style="width:200px"><i class="fa fa-clock-o"></i> Min/Max Duration</button>
		</p>
		<p align="center">
			Alles suchen: <input id="searchAll" type="checkbox" name="searchAll" <% if(SystemHandler.getInstance().isSearchAll()) out.print("checked"); %>>
		</p>
		<p align="center">
			<input id="searchPath" value="<% out.print(searchFolder); %>" type="text" name="searchPath" style="width:200px">
		</p>
		<p align="center">
			<button id="searchPathBtn" class="btn btn-primary" style="width:200px"><i class="fa fa-search"></i> Such-Pfad</button>
		</p>
	</center>
<jsp:include page="/footer.jsp" />