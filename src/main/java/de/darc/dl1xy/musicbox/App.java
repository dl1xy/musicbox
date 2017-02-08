package de.darc.dl1xy.musicbox;

import java.security.NoSuchAlgorithmException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import de.darc.dl1xy.musicbox.db.DBController;
import de.darc.dl1xy.musicbox.util.LogUtil;

public class App
{
	static final boolean SSL = false;//System.getProperty("ssl") != null;
	static final int PORT = 80;//Integer.parseInt(System.getProperty("port", SSL? "8443" : "8080"));
	
	
	public static final boolean NATIVE_JAVA = false;
	
    public static void main( String[] args ) throws NoSuchAlgorithmException
    {
    	//LogUtil.getLogger().info("Working Directory = " + System.getProperty("user.dir"));
    	//LogUtil.getLogger().info("Working Directory = " + System.getProperty("file.encoding"));
    	// init db
    	DBController.getInstance();
    	Mixer.Info[] infos = AudioSystem.getMixerInfo();
    	
    	int counter=0;
    	for (Mixer.Info info:infos)
    	{
    		LogUtil.getLogger().debug("### "+(++counter)+" ### "+ info.getName()+" ####### "+info.getDescription());
    	}
    	/*
    	DBController.getInstance().dropFts4();
    	DBController.getInstance().createFts4();
    	DBController.getInstance().fillFts4();
    	*/
    	// init server
    	Server server = new Server(PORT);
    	
    	// init web context 
    	WebAppContext context = new WebAppContext();
    	context.setContextPath("/");
    	context.setWar("src/main/webapp");
    	
    	server.setHandler(context);
    	try {
    		server.start();
    		server.join();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
}
