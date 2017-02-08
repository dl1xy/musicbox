<%@ page import="de.darc.dl1xy.musicbox.servlet.LoginServlet" %>
<%
	Cookie cookie = null;
Cookie[] cookies = null;

// Get an array of Cookies associated with this domain
cookies = request.getCookies();
if( cookies != null )
{
   
   for (int i = 0; i < cookies.length; i++)
   {
      cookie = cookies[i];
      if (cookie.getName().equals("musicbox"))
      {
    	  String userInfo = (String)cookie.getValue();
    	  
    	  String [] tokens = userInfo.split(":");
    	  session.setAttribute("username", tokens[0]);
    	  session.setAttribute("userId", Integer.parseInt(tokens[1]));
    	  if (tokens.length == 3)
    	  {
    		  session.setAttribute("admin", Integer.parseInt(tokens[2]));  
    	  }
    	  response.sendRedirect("/main.jsp");
    	  return;
      }
      
   }
}

%>

<!DOCTYPE html>
	<html lang="en">
	<head>
	    <meta charset="utf-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link type="text/css" rel="stylesheet" href="/css/musicbox/global.css">		
		<link type="text/css" rel="stylesheet" href="/css/pure/pure-min.css">		
		<link type="text/css" rel="stylesheet" href="/css/bootstrap/css/bootstrap.min.css">		    
		<!-- <link type="text/css" rel="stylesheet" href="/css/bootstrap/css/bootstrap-responsive.css">  -->
		<link type="text/css" rel="stylesheet" href="/css/font-awesome/css/font-awesome.min.css">
		<!--[if lte IE 8]>
		    <link rel="stylesheet" href="/css/pure/grids-responsive-old-ie-min.css">
		<![endif]-->
		<!--[if gt IE 8]><!-->
		    <link rel="stylesheet" href="/css/pure/grids-responsive-min.css">
		<!--<![endif]-->
		<script src="/js/jquery-2.1.3.js"></script>
		<script>
			function validateForm() {
			    var x = document.forms["nameInput"]["username"].value;
			    if (x == null || x == "" || x.length < 3) {
			    	document.getElementById("error").innerHTML = "<p>Dein Name muss mind. 3 Zeichen haben</p>";
			        return false;
			    }
			    
			    var y = document.forms["nameInput"]["password"].value;
			    if (y == null || y == "") {
			    	document.getElementById("error").innerHTML = "<p>Dein Passwort muss mind. 3 Zeichen haben</p>";
			        return false;
			    }
			}
		</script>

		<title>music.box v1.0 alpha</title>
	</head>
	<body>
	<center>
		<p>		
			<br><img src="/images/header.png"/><br>
		</p>
		<p>
			<div id="error">${requestScope["message"]}</div>
		</p>
		<p>
		  
		  	
            <form action="/login" method="post" class="pure-form pure-form-stacked" name="nameInput"  onsubmit="return validateForm()" accept-charset="utf-8">
    			<fieldset>
      				<p align="center">
			        	<center><input id="username" placeholder="Dein Name" type="text" name="username" style="width:200px"></center><br/>
			       	 	<center></center><input id="password" placeholder="Passwort" type="password" name="password" style="width:200px"></center><br/>
			      	</p>
			      	<div id="error"></div>
			      	<p>     
			       		<center><button type="submit" class="btn btn-primary" name="login" value="login" style="width:200px">Anmelden</button></center>
			       	</p>
			       	
			       	<p>     
			       		<center><button type="submit" class="btn btn-primary" name="register" value="register" style="width:200px">Registrieren</button></center>
			       	</p>
			    </fieldset>
			</form>
			
        </p>
   </center> 
 <script src="/css/bootstrap/js/bootstrap.min.js"></script>	
</body>
</html>
