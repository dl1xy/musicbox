<%
	session.setAttribute("header_type","search");
%>
<jsp:include page="/header.jsp" />
<style>
<!--
.btn-random { 
  color: #ffffff; 
  background-color: #FF0099; 
  border-color: #770055; 
} 
 
.btn-random:hover, 
.btn-random:focus, 
.btn-random:active, 
.btn-random.active, 
.open .dropdown-toggle.btn-random { 
  color: #ffffff; 
  background-color: #772266; 
  border-color: #770055; 
} 
 
.btn-random:active, 
.btn-random.active, 
.open .dropdown-toggle.btn-random { 
  background-image: none; 
} 
 
.btn-random.disabled, 
.btn-random[disabled], 
fieldset[disabled] .btn-random, 
.btn-random.disabled:hover, 
.btn-random[disabled]:hover, 
fieldset[disabled] .btn-random:hover, 
.btn-random.disabled:focus, 
.btn-random[disabled]:focus, 
fieldset[disabled] .btn-random:focus, 
.btn-random.disabled:active, 
.btn-random[disabled]:active, 
fieldset[disabled] .btn-random:active, 
.btn-random.disabled.active, 
.btn-random[disabled].active, 
fieldset[disabled] .btn-random.active { 
  background-color: #FF0099; 
  border-color: #770055; 
} 
 
.btn-random .badge { 
  color: #FF009D; 
  background-color: #ffffff; 
}
-->
</style>
<script src="/js/searchPrinter.js"></script>
	<center>
	
			<p>
				<input id="searchfield" type="text" name="searchfield" style="width:200px">
				
			</p>
			<p>
				<label for="option-song"><input id="option-song-cb" type="checkbox" checked>&nbsp;Song&nbsp;</label>
    			<label for="option-artist"><input id="option-artist-cb" type="checkbox" checked>&nbsp;Artist&nbsp;</label>
    			<label for="option-album"><input id="option-album-cb" type="checkbox" checked>&nbsp;Album</label>
			</p>
			
			<p>     
				<button id="searchBtn" class="btn btn-primary" style="width:200px"><i class="fa fa-search"></i> Suche</button>
			</p>
			<p>     
				<button id="randomBtn" class="btn btn-random" style="width:200px"><i class="fa fa-music"></i> Auf gut Glück</button>
			</p>
			
	<div id="search-div-msg"></div>
	
   
	<div id="search-div" style="display:none">
		<table  class="table table-striped">
			<!-- <thead>
				<tr>
					<th><span>Artist</span></th>
					<th>Song</th>
					<th>Album</th>
					<th>L&auml;nge</th>
					<th>&nbsp;</th>
				</tr>
			</thead>
			 -->
			<tbody id="tbody">				
				<!-- <div class="item"></div> -->			
			</tbody>
		</table>
		<div id="lastPostsLoader"></div>
		<!--  <div id="loadmoreajaxloader" style="display:none;"><center><img src="/images/ajax-loader.gif" /></center></div> -->
	</div>
		
	</center>
	
<jsp:include page="/footer.jsp" />