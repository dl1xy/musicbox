package de.darc.dl1xy.musicbox.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import de.darc.dl1xy.musicbox.mp3.Mp3FileScanner;

public class AdminMp3ScannerServlet extends HttpServlet {
	private static Gson gson = new Gson();
	private static final long serialVersionUID = -408561760016569551L;

	@Override
	 protected void doGet(HttpServletRequest request, HttpServletResponse response)
			 throws ServletException, IOException {
		Mp3FileScanner scanner = new Mp3FileScanner();
		long start = System.currentTimeMillis();
		int amount = scanner.scan();
		long end = System.currentTimeMillis();
		
		long diff = end - start;
		
		long second = (diff / 1000) % 60;
		long minute = (diff / (1000 * 60)) % 60;
		long hour = (diff / (1000 * 60 * 60)) % 24;
		
		String time = String.format("%02d:%02d:%02d", hour, minute, second);
		
		AdminMp3ScannerResult result = new AdminMp3ScannerResult();
		result.filesFound = amount;
		result.workingTime = time;
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(gson.toJson(result));
		 
	 }
}

class AdminMp3ScannerResult
{
	int filesFound;
	String workingTime;
}
