package de.darc.dl1xy.musicbox.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.darc.dl1xy.musicbox.playlist.PlaylistHandler;
import de.darc.dl1xy.musicbox.util.LogUtil;

public class PlaylistVoteServlet extends HttpServlet {
	 /**
	 * 
	 */
	private static final long serialVersionUID = -2911565102735936644L;

	@Override
	 protected void doGet(HttpServletRequest request, HttpServletResponse response)
			 throws ServletException, IOException {
		 HttpSession session = request.getSession(false);
		 
		 int userId = (int)session.getAttribute("userId");
		 LogUtil.getLogger().debug("PlaylistVoteServlet userId:"+userId+" playlistId:"+(String)request.getParameter("playlistId"));
		 
		 
		 int playlistId = Integer.parseInt((String)request.getParameter("playlistId"));		 
		 int vote = Integer.parseInt((String)request.getParameter("vote"));
		 
		 PlaylistHandler.getInstance().addVote(playlistId, userId, vote);
	 }
	 
	

}
