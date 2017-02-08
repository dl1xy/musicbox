package de.darc.dl1xy.musicbox.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import de.darc.dl1xy.musicbox.user.UserHandler;
import de.darc.dl1xy.musicbox.util.LogUtil;

public class FeedbackServlet extends HttpServlet {

	private static Gson gson = new Gson();
	private static final long serialVersionUID = -5847292336287590372L;

	@Override
	 protected void doGet(HttpServletRequest request, HttpServletResponse response)
			 throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		 
		 //int userId = Integer.parseInt((String)session.getAttribute("userId"));
		int userId = (Integer)session.getAttribute("userId");
		String msg = request.getParameter("msg");
		LogUtil.getLogger().debug("FeedbackServlet msg:"+msg);
		FeedbackResult result = new FeedbackResult();
		if (msg == null)
		{
			result.feedbackMsg = "Hast Du mir etwa nix zu sagen ?";
			result.errorCode = 1;
		}
		else
		if (msg.length() > 1000)
		{
			result.feedbackMsg = "Uiii, soviel Text will ich nicht lesen !";
			result.errorCode = 2;
		}
		else if (msg.length() < 4)
		{
			result.feedbackMsg = "Was soll ich denn mit sowendig Information anfangen ?";
			result.errorCode = 3;
		}
		else
		{
			UserHandler.getInstance().addFeedback(userId, msg);
			result.feedbackMsg = "Danke für dein Feedback, bitte nimm Dir ein St&uuml;ck Kuchen und ... PARTY ON !";
			result.errorCode = 0;
		}
		 
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(gson.toJson(result));
		
	 }
}

class FeedbackResult
{
	String feedbackMsg;
	int errorCode = 0;
}