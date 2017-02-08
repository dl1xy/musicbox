package de.darc.dl1xy.musicbox.mp3;

import java.io.IOException;

import de.darc.dl1xy.musicbox.util.LogUtil;

public class Mp3PlayerThread extends Thread{

	private Process p;

	private int processResult = Integer.MAX_VALUE;
	private String[] startCmd;
	private String[] killCmd;
	private volatile boolean running = true;

    
	public Mp3PlayerThread()
	{
		
	}
	
	public void run()
	{
		while(running)
		{
			try {
				ProcessBuilder builder = new ProcessBuilder( startCmd );
				p = builder.start();
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			running= false;
		}
	}
	
	public void stopPlaying()
	{
		LogUtil.getLogger().debug("Mp3PlayerThread.stopPlaying()");
		running = false;
		p.destroyForcibly();
		try {
			ProcessBuilder builder = new ProcessBuilder( killCmd );
			p = builder.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean isDone()
	{
		
		if (!running || processResult != Integer.MAX_VALUE)
		{
			LogUtil.getLogger().debug("isDone running:"+running+" processResult:"+processResult);
			return true;
		}
		return false;
	}

	public void setStartCmd(String[] startCmd) {
		this.startCmd = startCmd;
	}

	public void setKillCmd(String[] killCmd) {
		this.killCmd = killCmd;
	}
	
	
}
