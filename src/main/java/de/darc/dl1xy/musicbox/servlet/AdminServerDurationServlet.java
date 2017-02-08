package de.darc.dl1xy.musicbox.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.darc.dl1xy.musicbox.system.SystemHandler;
import de.darc.dl1xy.musicbox.util.LogUtil;

public class AdminServerDurationServlet extends HttpServlet {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 4897502659547808700L;

	@Override
	 protected void doGet(HttpServletRequest request, HttpServletResponse response)
			 throws ServletException, IOException {
		LogUtil.getLogger().debug("minRaw:"+request.getParameter("minDuration")+" maxRaw:"+request.getParameter("maxDuration"));
		int min = Integer.parseInt(request.getParameter("minDuration"));
		int max = Integer.parseInt(request.getParameter("maxDuration"));
		SystemHandler.getInstance().setMinMaxDuration(min, max);
		LogUtil.getLogger().debug("min:"+min+" max:"+max);
	 }
}