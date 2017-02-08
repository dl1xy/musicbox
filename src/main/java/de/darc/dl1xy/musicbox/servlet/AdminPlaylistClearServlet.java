package de.darc.dl1xy.musicbox.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.darc.dl1xy.musicbox.playlist.PlaylistHandler;
import de.darc.dl1xy.musicbox.util.LogUtil;

public class AdminPlaylistClearServlet extends HttpServlet {
	

	private static final long serialVersionUID = -5333367267623217321L;

	@Override
	 protected void doGet(HttpServletRequest request, HttpServletResponse response)
			 throws ServletException, IOException 
	 {		
		LogUtil.getLogger().debug("Delete playlist");
		PlaylistHandler.getInstance().clearPlaylist();
		response.getWriter().write("");
				 
	 }
}
