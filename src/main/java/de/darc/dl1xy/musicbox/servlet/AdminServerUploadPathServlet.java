package de.darc.dl1xy.musicbox.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.darc.dl1xy.musicbox.system.SystemHandler;

public class AdminServerUploadPathServlet extends HttpServlet {
	 /**
	 * 
	 */
	private static final long serialVersionUID = -7954043193444657340L;

	@Override
	 protected void doGet(HttpServletRequest request, HttpServletResponse response)
			 throws ServletException, IOException {
		String path = (String)request.getParameter("uploadPath");
		
		File f = new File(path);
		if (f.isDirectory())
		{
			SystemHandler.getInstance().setUploadFolder(path);
			response.getWriter().write("Pfad geändert"); 
		}
		else
		{
			response.getWriter().write("Kein gültiges Verzeichnis");
		}
	 }
}