package de.darc.dl1xy.musicbox.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import de.darc.dl1xy.musicbox.mp3.Mp3Handler;
import de.darc.dl1xy.musicbox.playlist.PlaylistHandler;

public class Mp3PositionServlet extends HttpServlet {

	private static final long serialVersionUID = 7867733218288499871L;
	Gson gson = new Gson();
	 @Override
	 protected void doGet(HttpServletRequest request, HttpServletResponse response)
			 throws ServletException, IOException 
	 {	
		 Mp3Position mp3pos = new Mp3Position();
		 mp3pos.position = Mp3Handler.getInstance().getSongPosition();
		 mp3pos.progress = Mp3Handler.getInstance().getSongProgress();
		 mp3pos.songId = Mp3Handler.getInstance().getCurrentSongId();
		 mp3pos.playlistVersion = PlaylistHandler.getInstance().getPlaylistVersion();
		 //LogUtil.getLogger().debug("mp3Pos:"+mp3pos);
		 response.setContentType("application/json");
		 response.setCharacterEncoding("UTF-8");
		 response.getWriter().write(gson.toJson(mp3pos));
	 }

}

class Mp3Position
{
	int songId;
	int position;
	int progress;
	int playlistVersion;	
}