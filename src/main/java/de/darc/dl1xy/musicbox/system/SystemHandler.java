package de.darc.dl1xy.musicbox.system;

import org.apache.log4j.Level;


public class SystemHandler {
	public static final int OS_LINUX = 1;
	public static final int OS_WINDOWS = 2;
	

	private static SystemHandler instance;
	private boolean isSearchAll = false;
	private String uploadFolder = "/media/musicbox/upload";
	private String searchFolder = "/media/musicbox/mp3";
	private Level logLevel = Level.DEBUG;
	private int minMp3Duration = 0;
	private int maxMp3Duration = 10000;
	
	private SystemHandler()
	{
		
	}
	
	public static SystemHandler getInstance()
	{
		if (instance == null)
			instance = new SystemHandler();
		return instance;
	}

	public String getUploadFolder() {
		return uploadFolder;
	}

	public void setUploadFolder(String uploadFolder) {
		this.uploadFolder = uploadFolder;
	}

	public Level getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(Level logLevel) {
		this.logLevel = logLevel;
	}

	public int getMinMp3Duration() {
		return minMp3Duration;
	}

	public int getMinMp3DurationMs() {
		return minMp3Duration * 1000;
	}
	
	public void setMinMp3Duration(int minMp3Duration) {
		this.minMp3Duration = minMp3Duration;
	}

	public int getMaxMp3Duration() {
		return maxMp3Duration;
	}

	public int getMaxMp3DurationMs() {
		return maxMp3Duration * 1000;
	}
	
	public void setMaxMp3Duration(int maxMp3Duration) {
		this.maxMp3Duration = maxMp3Duration;
	}
	
	public void setMinMaxDuration(int min, int max)
	{
		this.minMp3Duration = min;
		this.maxMp3Duration = max;
	}

	public String getSearchFolder() {
		return searchFolder;
	}

	public void setSearchFolder(String searchFolder) {
		this.searchFolder = searchFolder;
	}

	public boolean isSearchAll() {
		return isSearchAll;
	}

	public void setSearchAll(boolean isSearchAll) {
		this.isSearchAll = isSearchAll;
	}
	
	public int getOs()
	{
		String osName = System.getProperty("os.name" );
        String[] cmd = new String[3];
        if( osName.contains("Windows"))
        {
        	return OS_WINDOWS;
        }
        return OS_LINUX;
	}
}