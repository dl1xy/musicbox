package de.darc.dl1xy.musicbox.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import de.darc.dl1xy.musicbox.playlist.Playlist;
import de.darc.dl1xy.musicbox.playlist.PlaylistHandler;


public class PlaylistServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6213949606861879069L;
	Gson gson = new Gson();
	 @Override
	 protected void doGet(HttpServletRequest request, HttpServletResponse response)
			 throws ServletException, IOException 
	 {	
		 /*
		 HttpSession session = request.getSession(false);
		 if (session == null || session.isNew())
		 {
			 response.sendRedirect("/index.jsp");
			 //request.getRequestDispatcher("/index.jsp").forward(request, response);
			 return;
		 }
		 */
		 Playlist playlist = PlaylistHandler.getInstance().createPlaylist(request, response);
		 //System.out.println(playlist);
		 response.setContentType("application/json");
		 response.setCharacterEncoding("UTF-8");
		 response.getWriter().write(gson.toJson(playlist));
	 }
	 
}

