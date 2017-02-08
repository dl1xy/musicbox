package de.darc.dl1xy.musicbox.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.darc.dl1xy.musicbox.mp3.Mp3Handler;
import de.darc.dl1xy.musicbox.util.LogUtil;

public class AdminMp3ClearServlet extends HttpServlet {

	private static final long serialVersionUID = -5940404065059843266L;

	@Override
	 protected void doGet(HttpServletRequest request, HttpServletResponse response)
			 throws ServletException, IOException 
	 {		
		LogUtil.getLogger().debug("Delete mp3s");
		Mp3Handler.getInstance().clearMp3s();
		LogUtil.getLogger().debug("Löschen erfolgreich !");
	 }
}