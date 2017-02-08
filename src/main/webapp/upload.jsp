<% session.setAttribute("header_type","upload"); %>
<jsp:include page="/header.jsp" />
<script src="/js/uploadPrinter.js"></script>
	<center>	
	<form action="upload" method="post" enctype="multipart/form-data">
		<center><input type="file" name="file" accept="audio/mpeg"/></center>
		<br><br>
		
		<center><button type="submit" id="uploadBtn" class="btn btn-primary">Upload</button></center>
	</form>
	<p>
		<div id="songinfo">${requestScope["message"]}</div>
	</p>          
  	</center>
  	
<jsp:include page="/footer.jsp" />