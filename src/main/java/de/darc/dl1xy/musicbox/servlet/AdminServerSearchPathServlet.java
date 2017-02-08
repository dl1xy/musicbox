package de.darc.dl1xy.musicbox.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.darc.dl1xy.musicbox.system.SystemHandler;
import de.darc.dl1xy.musicbox.util.LogUtil;

public class AdminServerSearchPathServlet extends HttpServlet {
	
	private static final long serialVersionUID = 3653936674446698783L;

	@Override
	 protected void doGet(HttpServletRequest request, HttpServletResponse response)
			 throws ServletException, IOException {
		String path = request.getParameter("searchPath");
		String searchAll = request.getParameter("searchAll");
		if (!path.equals(""))
		{
			SystemHandler.getInstance().setSearchFolder(path);
		}
		else
		{
			 boolean checked = Boolean.parseBoolean(searchAll);
			 SystemHandler.getInstance().setSearchAll(checked);
		}
		LogUtil.getLogger().debug("Path:"+path+" searchAll:"+searchAll);
	}
}
