package de.darc.dl1xy.musicbox.mp3;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang3.StringEscapeUtils;

import de.darc.dl1xy.musicbox.db.DBController;
import de.darc.dl1xy.musicbox.util.LogUtil;

public class Mp3Item{
	
	public int id;	
	public String title;
	public String artist;
	public String genre;
	public String album;
	public String fileName;
	public String path;
	public String hash;
	public long duration;
	public int played;

	
	public Mp3Item()
	{
		
	}


	

	@Override
	public String toString() {
		return "Mp3Item [id=" + id + ", title=" + title + ", artist=" + artist
				+ ", genre=" + genre + ", album=" + album + ", fileName="
				+ fileName + ", path=" + path + ", hash=" + hash
				+ ", duration=" + duration + ", played=" + played + "]";
	}




	
	public Mp3Item serialize() {
		Connection connection = DBController.getInstance().getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO songs (title, artist, genre, album, fileName, path, duration, indexed, hash) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
			//PreparedStatement ps = connection.prepareStatement("INSERT INTO songs (title, artist) VALUES (?, ?)");
			ps.setString(1, StringEscapeUtils.escapeHtml4(title));
			ps.setString(2, StringEscapeUtils.escapeHtml4(artist));			
			ps.setString(3, StringEscapeUtils.escapeHtml4(genre));
			ps.setString(4, StringEscapeUtils.escapeHtml4(album));
			ps.setString(5, StringEscapeUtils.escapeHtml4(fileName));
			ps.setString(6, StringEscapeUtils.escapeHtml4(path));
			ps.setLong(7, duration);
			ps.setDate(8,  new Date(System.currentTimeMillis()));
			ps.setString(9,  hash);
			ps.execute();
			ps.close();
			
			 Statement s = connection.createStatement();
			 ResultSet rs = s.executeQuery("SELECT max(id) FROM songs");
			 while (rs.next())
			 {
				 this.id = rs.getInt(1);
				 break;
			 }
			 rs.close();
			 s.close();
			
			 // now put into virtaul table
			 PreparedStatement vs = connection.prepareStatement("INSERT INTO song_search (title, artist, album, duration) VALUES (?,?,?,?)");
			// vs.setInt(1, id);
			 vs.setString(1, StringEscapeUtils.escapeHtml4(title));
			 vs.setString(2, StringEscapeUtils.escapeHtml4(artist));			
			 vs.setString(3, StringEscapeUtils.escapeHtml4(album));
			 vs.setLong(4, duration);
			 vs.execute();
			 vs.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return this;
	}


	public void update() {
		Connection connection = DBController.getInstance().getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE songs SET played=?");
			//PreparedStatement ps = connection.prepareStatement("INSERT INTO songs (title, artist) VALUES (?, ?)");
			ps.setInt(1, this.played);
			ps.execute();
			ps.close();
			LogUtil.getLogger().debug("serialized");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	public void createSongByPath(String path)
	{
		
		this.id = 666;
		this.title = "Test song";
		this.artist = "Test song";
		this.genre = "test";
		this.album = "Test Album";
		this.path = path;
		this.duration = 36000L;
		this.fileName ="h3nry.mp3";
		this.hash = "AAAABBBBCCCCDDDDDEEEEFFFFGGGGHHHH";
		
		System.err.println(this);

	}
	
	public void createSongById(int id)
	{
		
		Connection connection = DBController.getInstance().getConnection();
		try {
			
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM songs WHERE id=?");
			//PreparedStatement ps = connection.prepareStatement("INSERT INTO songs (title, artist) VALUES (?, ?)");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				
				this.id = id;
				this.title = rs.getString ("title");
				this.artist = rs.getString ("artist");
				this.genre = rs.getString ("genre");
				if (this.genre == null)
					this.genre = "";
				this.album = rs.getString ("album");
				if (this.album == null)
					this.album = "";
				this.path = rs.getString ("path");
				this.duration = rs.getInt ("duration");
				this.fileName = rs.getString ("fileName");
				this.hash = rs.getString ("hash");
				
				LogUtil.getLogger().debug(this);
			}
			ps.close();
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	public boolean isHashExisting(String hash)
	{
		boolean result = false;
		Connection connection = DBController.getInstance().getConnection();
		try {
			
			PreparedStatement ps = connection.prepareStatement("SELECT hash FROM songs WHERE hash=?");
			ps.setString(1, hash);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{	
				result = true;
				break;
			}
			ps.close();
			rs.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return result;
	}
}
