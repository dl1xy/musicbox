<% session.setAttribute("header_type","feedback"); %>


<jsp:include page="/header.jsp" />
<script src="/js/feedbackPrinter.js"></script>
<center>
	
	<p>
		<div id="feedbackDiv" style="width:80%">
			 <div class="form-group">
  				<textarea class="form-control" rows="5" id="feedbackText"></textarea><br/>
  				
  				<p>
  				<button id="feedbackBtn" class="btn btn-primary" style="width:200px"><i class="fa fa-comment"></i> Abschicken</button>
  				</p>
			</div>
			<br/>
		</div>	
		
	</p>
</center>

<jsp:include page="/footer.jsp" />