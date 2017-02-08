package de.darc.dl1xy.musicbox.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet {


	private static final long serialVersionUID = -4527297100155305860L;

	@Override
	 protected void doGet(HttpServletRequest request, HttpServletResponse response)
			 throws ServletException, IOException 
	 {	
		HttpSession session = request.getSession(false);
		session.invalidate();
		
		 Cookie[] cookies = request.getCookies();
		if( cookies != null )
		{
		   
		   for (int i = 0; i < cookies.length; i++)
		   {
		      
		      if (cookies[i].getName().equals("musicbox"))
		      {
		    	  cookies[i].setValue(null);
		    	  cookies[i].setMaxAge(0);
		    	  response.addCookie(cookies[i]);
		    	  response.sendRedirect("/index.jsp");
		    	  return;
		      }
		      
		   }
		}
	   
	   // <--- Here.
	 }

}