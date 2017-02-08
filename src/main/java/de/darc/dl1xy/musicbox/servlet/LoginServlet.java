package de.darc.dl1xy.musicbox.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.darc.dl1xy.musicbox.user.UserHandler;

public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = -2812485942502608457L;
	public static final int WRONG_USERNAME_OR_PASSWORD = 1;
	public static final int USERNAME_EXISTS = 2;
	
	private UserHandler userHandler;
	@Override
	 protected void doPost(HttpServletRequest request, HttpServletResponse response)
			 throws ServletException, IOException {
		
				 
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		String register = request.getParameter("register");
		String login = request.getParameter("login");
		
		userHandler = UserHandler.getInstance();
		
		if (login != null)
		{
			int userId = userHandler.userExists(username, password);
			if (userId == -1)
			{
				// ERROR msg
				request.setAttribute("login_error", "Falscher Name und/oder Passwort");
				request.getRequestDispatcher("/index.jsp").forward(request, response);
				return;				
			}
			else
			{
				this.setCookie(userId, username, userHandler.isAdmin(userId), request, response);
			}
		}
		
		else if (register != null)
		{
			if (userHandler.userNameExists(username))
			{
				request.setAttribute("login_error", "Der Name existiert bereits");
				request.getRequestDispatcher("/index.jsp").forward(request, response);
				return;
			}
			int userId = userHandler.addUser(username, password, request.getHeader("User-Agent"));
			
			this.setCookie(userId, username, userHandler.isAdmin(userId), request, response);		
		}
	 }

	 private void setCookie(int userId, String username, boolean isAdmin, HttpServletRequest request, HttpServletResponse response) throws IOException
	 {
		 
		HttpSession session = request.getSession(true);
		session.setAttribute("username", username);
		session.setAttribute("userId", userId);
		session.setAttribute("admin", isAdmin?1:0);
		
		Cookie name = new Cookie("musicbox", username+":"+userId+":"+(isAdmin?1:0));
		name.setMaxAge(-1);
		
		response.addCookie(name);
		response.sendRedirect("/main.jsp");
		return;	
	 }
}