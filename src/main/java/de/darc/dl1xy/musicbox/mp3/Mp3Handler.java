package de.darc.dl1xy.musicbox.mp3;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import de.darc.dl1xy.musicbox.App;
import de.darc.dl1xy.musicbox.db.DBController;
import de.darc.dl1xy.musicbox.playlist.PlaylistHandler;
import de.darc.dl1xy.musicbox.system.SystemHandler;
import de.darc.dl1xy.musicbox.util.Converter;
import de.darc.dl1xy.musicbox.util.LogUtil;

public class Mp3Handler implements Observer
{
	final static int MAX_RESULTS_PER_PAGE = 10;
	private Mp3Player player;
	private Mp3Item song = null;
	private static Mp3Handler instance;
	
	private Mp3Handler()
	{
		player = Mp3Player.getInstance();
		player.addObserver(this);
	}
	
	
	public static Mp3Handler getInstance()
	{
		if (instance == null)
			instance = new Mp3Handler();
		return instance;
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		LogUtil.getLogger().debug("Observer update");
		if (arg1 instanceof Integer)
		{
			if (arg1 == Mp3Player.STOP_PLAYING_NORMAL)
			{
				this.startNextSong();
			}
		}
		
	}
	public void startSong()
	{
		if (player.isPlaying())
			player.stopPlaying();
		else
			startNextSong();
	}
	
	private int startNextSong()
	{
		LogUtil.getLogger().debug("Mp3Handler startNextSong");
		
		try {
			PlaylistHandler.getInstance().deleteLastSong();			
			int playlistId = PlaylistHandler.getInstance().getNextPlaylistId();
			LogUtil.getLogger().debug("startNextSong playlistId:"+playlistId);
			PlaylistHandler.getInstance().setPlayingFlag(playlistId);
			int songId = PlaylistHandler.getInstance().getSongIdByPlaylistId(playlistId);
			this.playSong(songId);
			return playlistId;
		} catch (IOException e) {
			LogUtil.getLogger().error(e.getMessage());
		}
		return -1;
	}
	
	
	private void playSong(int id) throws IOException
	{
		if (id==-1)
			return;
		song = new Mp3Item();
		song.createSongById(id);
		this.playSong(song);
	}
	
	private void playSong(Mp3Item song) throws IOException
	{
		if (SystemHandler.getInstance().getOs() == SystemHandler.OS_LINUX)
		{
			player.playLinux(song.path, song.duration);
		}
		else
		{
			player.playWindows(song.path, song.duration);
		}
	}
	
	
	public int getSongPosition()
	{
		if (!player.isPlaying())
			return 0;
		return (int)player.getPosition();
	}
	public int getSongProgress()
	{
		if (!player.isPlaying())
			return 0;
		return (int)Math.round((double)player.getPosition() / ((double)player.getDuration() / 100f));
	}
	
	public void clearMp3s()
	{
		Connection connection = DBController.getInstance().getConnection();
		
		try {
			PreparedStatement s = connection.prepareStatement("DELETE FROM songs");
			s.execute();
			s.close();	
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
		DBController.getInstance().dropFts4();
	}
	
	public void incSongPlayed(int songId)
	 {
		Connection connection = DBController.getInstance().getConnection();
		try {
			 PreparedStatement s = connection.prepareStatement("UPDATE songs SET played = played +1 WHERE id=?");
			 s.setInt(1, songId);
			 s.execute();
			 s.close();
			 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	 }
	
	public int countSongs ()
	 {
		int result=0;
		Connection connection = DBController.getInstance().getConnection();
		try {
			 PreparedStatement s = connection.prepareStatement("SELECT MAX(id) FROM songs");
			 ResultSet rs = s.executeQuery();
			 while (rs.next())
			 {
				 result = rs.getInt(1);
			 }
			 s.close();
			 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	 }
	
	public int countSearchSongs(String searchStr, boolean songChecked, boolean artistChecked, boolean albumChecked)
	{
		int result=0;
		
		if (songChecked && artistChecked && albumChecked)
		{
			result += countSearchSongs(searchStr, "song_search");
		}
		else
		{
			if(songChecked)
			{
				result += countSearchSongs(searchStr, "title");
			}
			if(artistChecked)
			{
				result += countSearchSongs(searchStr, "artist");
			}
			if(albumChecked)
			{
				result += countSearchSongs(searchStr, "album");
			}
		}
		return result;
	}
	private int countSearchSongs(String searchStr, String column)
	 {
		 int count = 0;
		 searchStr = searchStr.replace("-", " ");
		 searchStr = searchStr.replace(" ", " AND ");
		 final String queryStr = "SELECT COUNT(docid) FROM song_search WHERE "+column+" MATCH '"+searchStr+"*' AND duration >="+SystemHandler.getInstance().getMinMp3DurationMs()+" AND duration <="+SystemHandler.getInstance().getMaxMp3DurationMs();
				
		 Connection connection = DBController.getInstance().getConnection();
		 try {
			 PreparedStatement ps = connection.prepareStatement(queryStr);
			 ResultSet rs = ps.executeQuery();
			 while (rs.next())
			 {
				 count = rs.getInt(1);
				 break;
			 }	 
			 rs.close();
			 ps.close();
		 } catch (SQLException e) {
		
			e.printStackTrace();
		 }
		
		 return count;
	 }
	
	public List<Mp3SearchItem> searchSongs(String searchStr, boolean songChecked, boolean artistChecked, boolean albumChecked, int page)
	 {
		List<Mp3SearchItem> songs = new ArrayList<Mp3SearchItem>();
		
		if (songChecked && artistChecked && albumChecked)
		{
			songs.addAll(searchSongs(searchStr, "song_search", page));
		}
		else
		{
			if(songChecked)
			{
				songs.addAll(searchSongs(searchStr, "title", page));
			}
			if(artistChecked)
			{
				songs.addAll(searchSongs(searchStr, "artist", page));
			}
			if(albumChecked)
			{
				songs.addAll(searchSongs(searchStr, "album", page));
			}
		}
		return songs;
	}
	 private List<Mp3SearchItem> searchSongs(String searchStr, String column, int page)
	 {
		 LogUtil.getLogger().debug("searchSongs searchStr:"+searchStr+" column:"+column+" page:"+page);
		 List<Mp3SearchItem> songs = new ArrayList<Mp3SearchItem>();
		 searchStr = searchStr.replace("-", " ");
		 searchStr = searchStr.replace(" ", " AND ");
		 final String queryStr = "SELECT rowid AS id, title, artist, album, duration FROM song_search ss WHERE "+column+" MATCH '"+searchStr+"*' AND duration >="+SystemHandler.getInstance().getMinMp3DurationMs()+" AND duration <="+SystemHandler.getInstance().getMaxMp3DurationMs()+" LIMIT "+(page*MAX_RESULTS_PER_PAGE)+", "+MAX_RESULTS_PER_PAGE;
		 
		 Connection connection = DBController.getInstance().getConnection();
		 try {
			 PreparedStatement ps = connection.prepareStatement(queryStr);
			 ResultSet rs = ps.executeQuery();
			 while (rs.next())
			 {
				 LogUtil.getLogger().debug("result found !");
				 Mp3SearchItem song = new Mp3SearchItem(
						 rs.getInt("id"),
						 rs.getString("title"),
						 rs.getString("artist"),
						 rs.getString("album"),
						 Converter.durationIntToString(rs.getInt("duration")),
						 PlaylistHandler.getInstance().isSongInPlaylist(rs.getInt("id"))
						 );
				 songs.add(song);
			 }	 
			 rs.close();
			 ps.close();
		 } catch (SQLException e) {
		
			e.printStackTrace();
		 }
		
		 return songs;
	 }
	 
	 public List<Mp3SearchItem> randomSong()
	 {
		 List<Mp3SearchItem> songs = new ArrayList<Mp3SearchItem>();
		 int maxId = this.countSongs();
		 
		 
		 Connection connection = DBController.getInstance().getConnection();
		 
		 boolean fits=false;
		 while (!fits)
		 {
			 int randomId = (int)(Math.random() * maxId) + 1;
			 try {
				 PreparedStatement ps = connection.prepareStatement("SELECT id, title, artist, album, duration FROM songs WHERE id=?");
				 ps.setInt(1, randomId);
				 ResultSet rs = ps.executeQuery();
				 while (rs.next())
				 {
					 int duration = rs.getInt("duration");
					 if (duration >= SystemHandler.getInstance().getMinMp3DurationMs() && duration <= SystemHandler.getInstance().getMaxMp3DurationMs())
					 {
						 Mp3SearchItem song = new Mp3SearchItem(
								 rs.getInt("id"),
								 rs.getString("title"),
								 rs.getString("artist"),
								 rs.getString("album"),
								 Converter.durationIntToString(duration),
								 PlaylistHandler.getInstance().isSongInPlaylist(rs.getInt("id"))
								 );
						 songs.add(song);
						 fits = true;
					 }					
				 }				 
				 rs.close();
				 ps.close();
			 } catch (SQLException e) {
			
				e.printStackTrace();
			 }
		 }
		
		 return songs;
	 }
	 
	 public void rebuildDb()
	 {
		 long startTime = System.currentTimeMillis();
		 Connection connection = DBController.getInstance().getConnection();
		 try {
			 Statement s = connection.createStatement();
			 s.executeQuery("INSERT INTO song_search(song_search) VALUES ('rebuild')");
			 s.close();
		 } catch (SQLException e) {
		
			e.printStackTrace();
		 }
		
		 long endTime = System.currentTimeMillis();
		 
		 LogUtil.getLogger().debug("rebuild time:"+(endTime-startTime));
	 }
	 
	 public int getCurrentSongId()
	 {
		 if (this.song == null)
			 return 0;
		 return this.song.id;
	 }
}
