package de.darc.dl1xy.musicbox.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import de.darc.dl1xy.musicbox.mp3.Mp3Handler;
import de.darc.dl1xy.musicbox.util.LogUtil;

public class AdminMp3PlayServlet extends HttpServlet {

	static Gson gson = new Gson();
	private static final long serialVersionUID = 4587106041671689107L;

	
	@Override
	 protected void doGet(HttpServletRequest request, HttpServletResponse response)
			 throws ServletException, IOException 
	 {		
		LogUtil.getLogger().debug("Play mp3s");
		AdminMp3PlayResult result = new AdminMp3PlayResult();
		Mp3Handler.getInstance().startSong();
		 //LogUtil.getLogger().debug("mp3Pos:"+mp3pos);
		 response.setContentType("application/json");
		 response.setCharacterEncoding("UTF-8");
		 response.getWriter().write(gson.toJson(result));
	 }
}


class AdminMp3PlayResult
{
	int playlistId;
}