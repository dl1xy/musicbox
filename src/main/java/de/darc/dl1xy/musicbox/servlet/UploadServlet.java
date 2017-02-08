package de.darc.dl1xy.musicbox.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringEscapeUtils;

import de.darc.dl1xy.musicbox.mp3.Mp3Item;
import de.darc.dl1xy.musicbox.mp3.Mp3Parser;
import de.darc.dl1xy.musicbox.playlist.PlaylistHandler;
import de.darc.dl1xy.musicbox.system.SystemHandler;

/**
* Servlet to handle File upload request from Client
* @author Javin Paul
*/
public class UploadServlet extends HttpServlet {
  
	private static final long serialVersionUID = -7417322885922008880L;
	
	
 
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
     
	   HttpSession session = request.getSession(false);
		 
		 //int userId = Integer.parseInt((String)session.getAttribute("userId"));
	  	int userId = (Integer)session.getAttribute("userId");
       //process only if its multipart content
       if(ServletFileUpload.isMultipartContent(request)){
           try {
               List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
             
               for(FileItem item : multiparts){
                   if(!item.isFormField()){
                       String name = new File(item.getName()).getName();
                       if (!name.toLowerCase().endsWith(".mp3"))
                       {	   
                    	   request.setAttribute("message", "Es sind nur MP3s möglich !");
                    	   request.getRequestDispatcher("/upload.jsp").forward(request, response);
                    	   return;
	                   }
                       File file = new File(StringEscapeUtils.unescapeHtml3(SystemHandler.getInstance().getUploadFolder()) + File.separator + name);
                       if (file.exists())
                       {
                    	   request.setAttribute("message", "MP3 exisitiert bereits");
                    	   request.getRequestDispatcher("/upload.jsp").forward(request, response);
                    	   return;
                       }
                       item.write(file);
                       Mp3Item mp3Item = new Mp3Parser().parse(file);
                       //Mp3Handler.getInstance().rebuildDb();
                       PlaylistHandler.getInstance().addSongToPlayList(mp3Item.id, userId);                       
                   }
               }
          
              //File uploaded successfully
              request.setAttribute("message", "MP3 Upload erfolgreich");
           } catch (Exception ex) {
              request.setAttribute("message", "MP3 Upload fehlgeschlagen: " + ex);
           }          
        
       }else{
           request.setAttribute("message",
                                "Sorry, dieses Servlet berarbeitet nur MP3 Uploads");
       }
   
       request.getRequestDispatcher("/upload.jsp").forward(request, response);
    
   }
 
}

