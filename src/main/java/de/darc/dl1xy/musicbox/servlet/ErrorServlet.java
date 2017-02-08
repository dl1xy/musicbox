package de.darc.dl1xy.musicbox.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ErrorServlet extends HttpServlet{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4742089687858448901L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) 
    		 throws ServletException, IOException
    {
       Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
       request.getRequestDispatcher("/index.jsp").forward(request, response);
       
    }
}