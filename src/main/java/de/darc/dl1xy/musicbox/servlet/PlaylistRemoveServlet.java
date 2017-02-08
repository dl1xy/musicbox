package de.darc.dl1xy.musicbox.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import de.darc.dl1xy.musicbox.playlist.PlaylistHandler;

public class PlaylistRemoveServlet extends HttpServlet {
	private static Gson gson = new Gson();
	private static final long serialVersionUID = -6908508279181614036L;

	@Override
	 protected void doGet(HttpServletRequest request, HttpServletResponse response)
			 throws ServletException, IOException {
		 
		 HttpSession session = request.getSession(false);
		 
		 //int userId = Integer.parseInt((String)session.getAttribute("userId"));
		 int userId = (Integer)session.getAttribute("userId");
		 int playlistId = Integer.parseInt((String)request.getParameter("playlistId"));	
		 
		 int queryResult = PlaylistHandler.getInstance().removeSongFromPlayList(playlistId, userId);
		 PlaylistRemoveResult result = new PlaylistRemoveResult();
		 result.queryResult = queryResult;
		 response.setContentType("application/json");
		 response.setCharacterEncoding("UTF-8");
		 response.getWriter().write(gson.toJson(result));
	 }
}

class PlaylistRemoveResult
{
	int queryResult;
}