package de.darc.dl1xy.musicbox.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminUserServlet  extends HttpServlet {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 947525455808297338L;

	@Override
	 protected void doGet(HttpServletRequest request, HttpServletResponse response)
			 throws ServletException, IOException {
		
		request.setAttribute("result", "This is the result of the servlet call");
		request.getRequestDispatcher("/admin_user.jsp").forward(request, response);
				 
	 }
}