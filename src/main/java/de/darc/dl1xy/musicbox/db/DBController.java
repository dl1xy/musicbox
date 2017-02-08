package de.darc.dl1xy.musicbox.db;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import de.darc.dl1xy.musicbox.util.LogUtil;

public class DBController {
    
    private static DBController dbcontroller;
    private static Connection connection;
    //private static final String DB_PATH = System.getProperty("user.home") + "/" + "musicbox.db";
    private static final String DB_PATH = "musicbox.db";
    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Fehler beim Laden des JDBC-Treibers");
            e.printStackTrace();
        }
    }
    
    private DBController(){
    	initDBConnection(); 
    }
    
    public static DBController getInstance(){
    	if (dbcontroller == null)
    		dbcontroller = new DBController();
        return dbcontroller;
    }
    
    private void initDBConnection() {
        try {
            if (connection != null)
                return;
            LogUtil.getLogger().info("Creating Connection to Database "+DB_PATH);
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
            if (!connection.isClosed())
            	LogUtil.getLogger().info("...Connection established: "+connection.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    if (!connection.isClosed() && connection != null) {
                        connection.close();
                        if (connection.isClosed())
                        	LogUtil.getLogger().info("Connection to Database closed");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    } 
    
    public void shutdown()
    {
    	try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void createFts4()
    {
    	 long startTime = System.currentTimeMillis();
		 Connection connection = DBController.getInstance().getConnection();
		 try {
			 Statement s = connection.createStatement();
			 s.execute("CREATE VIRTUAL TABLE song_search USING fts4(title, artist,album,duration);");
			 s.close();
		 } catch (SQLException e) {
		
			e.printStackTrace();
		 }
		
		 long endTime = System.currentTimeMillis();
		 
		 LogUtil.getLogger().debug("createFts4 time:"+(endTime-startTime));
    }
    public void fillFts4()
    {
    	 long startTime = System.currentTimeMillis();
		 Connection connection = DBController.getInstance().getConnection();
		 try {
			 Statement s = connection.createStatement();
			 s.execute("INSERT INTO song_search (docid, title, artist, album, duration) SELECT id, title, artist, album, duration FROM songs");
			 s.close();
		 } catch (SQLException e) {
		
			e.printStackTrace();
		 }
		
		 long endTime = System.currentTimeMillis();
		 
		 LogUtil.getLogger().debug("fillFts4 time:"+(endTime-startTime));
    }
    public void dropFts4()
    {
    	 long startTime = System.currentTimeMillis();
		 Connection connection = DBController.getInstance().getConnection();
		 try {
			 Statement s = connection.createStatement();
			 s.execute("DROP TABLE IF EXISTS song_search;");
			 s.close();
		 } catch (SQLException e) {
		
			e.printStackTrace();
		 }
		
		 long endTime = System.currentTimeMillis();
		 
		 LogUtil.getLogger().debug("dropFts4 time:"+(endTime-startTime));
    }
    public Connection getConnection()
    {
    	return connection;
    }
}
