package de.darc.dl1xy.musicbox.util;


import java.util.Properties;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.spi.LoggerRepository;

import de.darc.dl1xy.musicbox.system.SystemHandler;

/**
 * Logging functionality is encapsulated in this class. Its a Singleton
 * @author Arne
 *
 */
public class LogUtil 
{
	// Logger ref
	private volatile Logger logger;
	
	
	// singleton instance
	private volatile static LogUtil instance;

	/**
	 * private constructor
	 */
	private LogUtil()
	{
		Properties properties = new Properties();

		PropertyConfigurator prop = new PropertyConfigurator(); 

		LoggerRepository hierarchy = Logger.getRootLogger().getLoggerRepository();
		prop.doConfigure(properties, hierarchy);
	
		logger = Logger.getRootLogger();
		
		String pattern = "%d [%t] %-5p %c - %m%n";
		PatternLayout layout = new PatternLayout(pattern);
		ConsoleAppender consoleAppender = new ConsoleAppender( layout );
		logger.addAppender( consoleAppender );
	}
	
	/**
	 * singleton getter
	 * @return
	 */
	private static LogUtil getInstance()
	{
		if (instance == null)
		{
			instance = new LogUtil();		
		}
		return instance;
	}
	
	/**
	 * simple getter
	 * @return
	 */
	public static Logger getLogger()
	{
		Logger log = LogUtil.getInstance().logger;
		
		log.setLevel(SystemHandler.getInstance().getLogLevel());
		
		return LogUtil.getInstance().logger;
	}
}
