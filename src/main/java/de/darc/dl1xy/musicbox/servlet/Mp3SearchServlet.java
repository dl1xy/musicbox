package de.darc.dl1xy.musicbox.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;

import com.google.gson.Gson;

import de.darc.dl1xy.musicbox.mp3.Mp3Handler;
import de.darc.dl1xy.musicbox.mp3.Mp3SearchItem;
import de.darc.dl1xy.musicbox.mp3.Mp3SearchResult;
import de.darc.dl1xy.musicbox.util.LogUtil;

public class Mp3SearchServlet extends HttpServlet {
	
	private static final long serialVersionUID = -2013154943105722996L;
	
	
	final Gson gson = new Gson();
	
	 @Override
	 protected void doGet(HttpServletRequest request, HttpServletResponse response)
			 throws ServletException, IOException {
		
		 //System.out.println("SEARCH");
		 
		 boolean random = Boolean.parseBoolean(request.getParameter("random").toLowerCase());
		 Mp3SearchResult result 	= null;
		 List<Mp3SearchItem> songs 	= null;
		 int count 	= 0;
		 int page 	= 0;
		 
		 final long startTime = System.currentTimeMillis();
		 final int totalSongs = Mp3Handler.getInstance().countSongs();
		 
		 if (!random)
		 {			 
			 String searchStr = request.getParameter("searchStr").toLowerCase();
			 
			 LogUtil.getLogger().debug("Suchstring:"+searchStr);
			 
			 if (searchStr == null || searchStr == "" )
				 	return;
			 
			 searchStr = StringEscapeUtils.escapeHtml4(searchStr);
			 
			 page = Integer.parseInt(request.getParameter("page"));
			 
			 final boolean songChecked = Boolean.parseBoolean(request.getParameter("songChecked").toLowerCase());
			 final boolean artistChecked = Boolean.parseBoolean(request.getParameter("artistChecked").toLowerCase());
			 final boolean albumChecked = Boolean.parseBoolean(request.getParameter("albumChecked").toLowerCase());
			
			 count = Mp3Handler.getInstance().countSearchSongs(searchStr, songChecked, artistChecked, albumChecked);
			 songs = Mp3Handler.getInstance().searchSongs(searchStr, songChecked, artistChecked, albumChecked, page );
			 LogUtil.getLogger().debug("Mp3SearchServlet songs:"+songs.size());
		 }
		 else
		 {
			 count = 1;
			 songs = Mp3Handler.getInstance().randomSong();
		 }
		 
		 final long endTime = System.currentTimeMillis();
		 result = new Mp3SearchResult(count, songs, page,endTime - startTime, totalSongs);
		 response.setContentType("application/json");
		 response.setCharacterEncoding("UTF-8");
		 response.getWriter().write(gson.toJson(result));
		 
	 }
	 
	 
	
}
