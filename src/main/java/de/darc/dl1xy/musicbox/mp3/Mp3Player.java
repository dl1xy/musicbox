package de.darc.dl1xy.musicbox.mp3;

import java.io.IOException;
import java.util.Observable;

import de.darc.dl1xy.musicbox.App;
import de.darc.dl1xy.musicbox.util.LogUtil;

public class Mp3Player extends Observable{
	

	public final static Integer STOP_PLAYING_NORMAL = 0;
	public final static Integer STOP_PLAYING_FORCED = 1;
	public final static Integer START_PLAYING = 2;
	public final static Integer PAUSE_PLAYING = 3;
	
	
	private static Mp3Player instance;
	//G:\mp3\Breakcore\h3nry.mp3
	
	private volatile boolean isPlaying = false;
	
	private long startTime;
	private long duration;
	private long position;
	
	private Mp3PlayerThread playerThread;
	private Mp3Player()
	{
		playerThread = new Mp3PlayerThread();
	}
	
	public static Mp3Player getInstance()
	{
		if (instance == null)
			instance = new Mp3Player();
		return instance;
	}
	
	
	
	public void playWindows(String path, long duration) throws IOException
	{
		this.duration = duration;
		String[] startCmd = new String[]{"cmd", "/c", "E:\\mpg123-1.22.0-x86-64\\mpg123.exe", path };
		String[] killCmd = new String[]{"cmd", "/c", "taskkill", "/im", "mpg123.exe", "/f" };

		this.playThread(startCmd,killCmd);
		
	}
	
	public void playLinux(String path, long duration) throws IOException
	{
		this.duration = duration;
		String[] startCmd = new String[]{"/bin/sh", "-c", "/usr/bin/mpg123 '"+path+"'" };
		String[] killCmd = new String[]{"/bin/sh", "-c", "killall mpg123" };
		
		
		this.playThread(startCmd, killCmd);
	}
	
	private void playThread(String[] startCmd, String[] killCmd)
	{
		try {
			startTime = System.currentTimeMillis();
			playerThread.setStartCmd(startCmd);
			playerThread.setKillCmd(killCmd);
			isPlaying = true;
			setChanged();
			this.notifyObservers(START_PLAYING);
			LogUtil.getLogger().debug("playThread startet !");
			
			playerThread.start();
			
			while (isPlaying && this.getPosition() < this.duration)
			{
				; // do nothing
			}
			LogUtil.getLogger().debug("playThread stoping !");
			playerThread.stopPlaying();
			playerThread = null;
			isPlaying = false;
		 	this.stop();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public void stopPlaying()
	{
		LogUtil.getLogger().debug("Stop Playing !!! isPlaying:"+isPlaying);
		isPlaying = false;
		playerThread.interrupt();
	}
	
	private void stop() throws IOException
	{
		setChanged();
		this.notifyObservers(STOP_PLAYING_NORMAL);
		LogUtil.getLogger().debug("Playing Stopped");
	}
	
	
	public boolean isPlaying()
	{
		return isPlaying;
	}
	
	public long getPosition()
	{
		if (App.NATIVE_JAVA)
			return this.position;
		//LogUtil.getLogger().info("pos: "+(System.currentTimeMillis() - startTime));
		return System.currentTimeMillis() - startTime;
	}
	
	public long getDuration()
	{
		return this.duration;
	}
	
	
}
