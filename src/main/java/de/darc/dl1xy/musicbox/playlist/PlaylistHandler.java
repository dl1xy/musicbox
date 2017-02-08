package de.darc.dl1xy.musicbox.playlist;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.darc.dl1xy.musicbox.db.DBController;
import de.darc.dl1xy.musicbox.mp3.Mp3Handler;
import de.darc.dl1xy.musicbox.user.UserHandler;
import de.darc.dl1xy.musicbox.util.Converter;
import de.darc.dl1xy.musicbox.util.LogUtil;

public class PlaylistHandler {
	
	final static int MAX_RESULTS_PER_PAGE = 10;
	private static PlaylistHandler instance;
	private int playlistVersion = 0;
	private PlaylistHandler()
	{
		
	}
	
	public static PlaylistHandler getInstance()
	{
		if (instance == null)
			instance = new PlaylistHandler();
		return instance;
	}
	
	 public void addSongToPlayList(int songId, int userId)
	 {
		 Connection connection = DBController.getInstance().getConnection();
		 try {
			 PreparedStatement st = connection.prepareStatement("INSERT INTO playlist (song_id, user_id, indexed) VALUES(?,?,?);");
			 st.setInt(1, songId);
			 st.setInt(2, userId);
			 st.setDate(3,  new Date(System.currentTimeMillis()));
			 st.execute();
			 st.close();
		 } catch (SQLException e) {
		
			e.printStackTrace();
		 }
		 LogUtil.getLogger().debug("Song "+songId+" von User "+userId+" zur Playliste hinzugefügt !");
		 UserHandler.getInstance().incSongsPlaylisted(userId);
		 playlistVersion++;
	 }
	 

	 public int removeSongFromPlayList(int playlistId, int userId)
	 {
		 int result = -1;
		 Connection connection = DBController.getInstance().getConnection();
		 try {
			 PreparedStatement st = connection.prepareStatement("DELETE FROM playlist WHERE id =? AND user_id=?;");
			 st.setInt(1, playlistId);
			 st.setInt(2, userId);
			 result = st.executeUpdate();
			 st.close();
		 } catch (SQLException e) {
		
			e.printStackTrace();
		 }
		 
		 LogUtil.getLogger().debug("Song "+playlistId+ " von "+userId+" eliminiert !");
		 playlistVersion++;
		 return result;
	 }
	
	 public int getTotalPlaylistSongsAmount()
	{
		Connection connection = DBController.getInstance().getConnection();
		
		int result = -1;
		try {
			 PreparedStatement s = connection.prepareStatement("SELECT COUNT(*) FROM playlist WHERE isPlaying=0");
			 ResultSet rs = s.executeQuery();
			 while (rs.next())
			 {
				 result = rs.getInt(1);
				 break;
			 }
			 s.close();
			 rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int getTotalPlaylistSongsDuration()
	{
		Connection connection = DBController.getInstance().getConnection();
		
		int result = -1;
		try {
			 PreparedStatement s = connection.prepareStatement("SELECT SUM(s.duration) FROM playlist p, songs s WHERE s.id == p.song_id AND isPlaying=0");
			 ResultSet rs = s.executeQuery();
			 while (rs.next())
			 {
				 result = rs.getInt(1);
				 break;
			 }
			 s.close();
			 rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void clearPlaylist()
	{
		Connection connection = DBController.getInstance().getConnection();
		
		try {
			PreparedStatement s = connection.prepareStatement("DELETE FROM playlist");
			s.execute();
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Playlist createPlaylist(HttpServletRequest request,  HttpServletResponse response) throws IOException
	{
		HttpSession session = request.getSession(false);
		final int userId = (Integer)session.getAttribute("userId");
		final int page = Integer.parseInt(request.getParameter("page"));
		Playlist playlist = new Playlist();
		playlist.currentSong = this.currentSong(userId);
		playlist.totalSongs = this.getTotalPlaylistSongsAmount();
		playlist.totalPlaytime = Converter.durationIntToString(getTotalPlaylistSongsDuration());
		playlist.songs = this.createAllPlaylistSongs(page, userId);
		return playlist;
	}
	
	
	public PlaylistItem currentSong(int userId)
	{
		PlaylistItem sp = new PlaylistItem();
		Connection connection = DBController.getInstance().getConnection();
		
		try {
			 PreparedStatement s = connection.prepareStatement("SELECT p.id FROM playlist p WHERE  p.isPlaying=1  LIMIT 1");
			 ResultSet rs = s.executeQuery();
			 while (rs.next())
			 {	
				 sp = this.getSongByPlaylistId(rs.getInt(1));
				 break;
			 }
			 s.close();
			 rs.close();
			 
			 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sp.songPosition = Mp3Handler.getInstance().getSongPosition();
		 sp.songProgress = Mp3Handler.getInstance().getSongProgress();
		 
		 sp = this.isVoted(sp, userId);
		return sp;
	}
	
	public PlaylistItem getSongByPlaylistId(int playlistId)
	{
		//LogUtil.getLogger().debug("getSongByPlaylistId "+playlistId);
		PlaylistItem sp = new PlaylistItem();
		Connection connection = DBController.getInstance().getConnection();
		
		try {
			// playlist id
			 PreparedStatement playlistStmt = connection.prepareStatement("SELECT p.id, p.song_id, p.user_id, p.up, p.down FROM playlist p WHERE p.id=?  LIMIT 1");
			 playlistStmt.setInt(1, playlistId);
			 ResultSet playlistStmtRs = playlistStmt.executeQuery();
			 while (playlistStmtRs.next())
			 {	
				 sp.id 		= playlistStmtRs.getInt("id");
				 sp.songId 	= playlistStmtRs.getInt("song_id");
				 sp.userId 	= playlistStmtRs.getInt("user_id");
				 sp.up 		= playlistStmtRs.getInt("up");
				 sp.down 	= playlistStmtRs.getInt("down");				
				 
				// LogUtil.getLogger().debug(sp);
				 break;
			 }
			 playlistStmtRs.close();
			 playlistStmt.close();
			 
			 // song
			 PreparedStatement songStmt = connection.prepareStatement("SELECT s.title, s.artist, s.album, s.duration FROM songs s WHERE s.id=?  LIMIT 1");
			 songStmt.setInt(1, sp.songId);
			 ResultSet songStmtRs = songStmt.executeQuery();
			 while (songStmtRs.next())
			 {	
				 
				 sp.songTitle = songStmtRs.getString("title");
				 sp.songArtist = songStmtRs.getString("artist");
				 sp.songAlbum = songStmtRs.getString("album");
				 sp.songDuration = Converter.durationIntToString(songStmtRs.getInt("duration"));
				 sp.songDurationMs = songStmtRs.getInt("duration");
				 //LogUtil.getLogger().info("duration:"+sp.songDurationMs);
				 
				// LogUtil.getLogger().debug(sp);
				 break;
			 }
			 songStmt.close();
			 songStmt.close();
			 //user
			 PreparedStatement userStmt = connection.prepareStatement("SELECT u.name  FROM user u WHERE  u.id = ? LIMIT 1");
			 userStmt.setInt(1, sp.userId);
			 ResultSet userStmtRs =userStmt.executeQuery();
			 while (userStmtRs.next())
			 {	
				 sp.userName = userStmtRs.getString("name");				
				 break;
			 }
			 userStmt.close();
			 userStmtRs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//LogUtil.getLogger().debug("getSongByPlaylistId "+sp);
		return sp;
	}
	
	public List<PlaylistItem> createAllPlaylistSongs(int page, int userId) throws IOException
	{
		List<PlaylistItem> list = new ArrayList<PlaylistItem>();
		Connection connection = DBController.getInstance().getConnection();
		long start = System.currentTimeMillis();
		try {
			 //PreparedStatement s = connection.prepareStatement("SELECT s.id AS songId, s.title, s.artist, s.album, s.duration, u.id AS userId, u.name, p.up, p.down FROM songs s, user u, playlist p WHERE p.song_id = s.id AND p.user_id = u.id  AND isPlaying=0 ORDER BY p.up/CASE WHEN p.down = 0 THEN 1 ELSE p.down END DESC LIMIT "+(page*MAX_RESULTS_PER_PAGE)+", "+MAX_RESULTS_PER_PAGE);
			 //PreparedStatement s = connection.prepareStatement("SELECT p.id, s.id AS songId, s.title, s.artist, s.album, s.duration, u.id AS userId, u.name, p.up, p.down FROM songs s, user u, playlist p WHERE p.song_id = s.id AND p.user_id = u.id AND isPlaying = 0 ORDER BY p.up/CASE WHEN p.down = 0 THEN 1 ELSE p.down END DESC, p.indexed ASC LIMIT 0, "+((page + 1) * MAX_RESULTS_PER_PAGE));

			PreparedStatement s = connection.prepareStatement("SELECT p.id FROM playlist p WHERE p.isPlaying = 0 ORDER BY (p.up - p.down) DESC, p.indexed ASC LIMIT 0, "+((page + 1) * MAX_RESULTS_PER_PAGE));//p.up/CASE WHEN p.down = 0 THEN 1 ELSE p.down END DESC, p.indexed ASC LIMIT 0, "+((page + 1) * MAX_RESULTS_PER_PAGE));

			 ResultSet rs = s.executeQuery();
			 while (rs.next())
			 {
				 PlaylistItem sp = this.getSongByPlaylistId(rs.getInt(1));
				 sp = this.isVoted(sp, userId);
							
				 list.add(sp);
			 }
			 s.close();
			 rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		//LogUtil.getLogger().debug("createAllPlaylistSongs duration:"+(end-start));
		return list;
	}
	
	public int getNextPlaylistId() throws IOException
	{
		int result = -1;
		Connection connection = DBController.getInstance().getConnection();
		
		try {
			 PreparedStatement s = connection.prepareStatement("SELECT id FROM playlist ORDER BY up/CASE WHEN down = 0 THEN 1 ELSE down END DESC LIMIT 1");

			 ResultSet rs = s.executeQuery();
			 while (rs.next())
			 {
				 result = rs.getInt(1);
			 }
			 s.close();
			 rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		LogUtil.getLogger().debug("PlaylistHandler getNextPlaylisId:"+result);
		return result;
	}
	
	public void setPlayingFlag(int playlistId) throws IOException
	{
		
		// TODO inc counters
		PlaylistItem song = this.getSongByPlaylistId(playlistId);
		
		UserHandler userHandler = UserHandler.getInstance();
		userHandler.incSongsPlayed(song.userId);
		
		Mp3Handler mp3Handler = Mp3Handler.getInstance();
		mp3Handler.incSongPlayed(song.songId);
		Connection connection = DBController.getInstance().getConnection();
		try {
			 PreparedStatement s = connection.prepareStatement("UPDATE playlist SET isPlaying=1 WHERE id=?");
			 s.setInt(1, playlistId);
			 s.execute();
			 s.close();
			 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void deletePlaylistSong(int playlistId) throws IOException
	{
		
		Connection connection = DBController.getInstance().getConnection();
		
		try {
			 PreparedStatement s = connection.prepareStatement("DELETE FROM playlist WHERE id=?");
			 s.setInt(1, playlistId);
			 s.execute();
			 s.close();
			 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		playlistVersion++;
		
	}
	
	public void deleteLastSong() throws IOException
	{		
		Connection connection = DBController.getInstance().getConnection();
		
		try {
			 PreparedStatement s = connection.prepareStatement("DELETE FROM playlist WHERE isPlaying=1");
			 
			 s.execute();
			 s.close();
			 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		LogUtil.getLogger().debug("Playlist Song gelöscht!");
		playlistVersion++;
		
		
	}
	private PlaylistItem isVoted(PlaylistItem item, int userId)
	{		
		 Connection connection = DBController.getInstance().getConnection();
		 try {
			 PreparedStatement voteStmt = connection.prepareStatement("SELECT vote FROM votes WHERE playlist_id=? AND user_id=?");
			 voteStmt.setInt(1, item.id);
			 voteStmt.setInt(2, userId);
			 ResultSet rs = voteStmt.executeQuery();
			 while (rs.next())
			 {
				 item.isVoted = true;
				 item.vote = (rs.getInt("vote") == 1)?true:false;
			 }
			 voteStmt.close();
			 rs.close();
		 } catch (SQLException e) {
		
			e.printStackTrace();
		 }
		 
		 return item;
	}
	
	 public void addVote(int playlistId, int userId, int vote)
	 {
		 
		 // normal  user-vote
		 String userQuery = "";
		 if(vote == 1)
			 userQuery = "UPDATE user SET vote_up_sent = vote_up_sent+1 WHERE id="+userId;
		 else
		 {
			 userQuery = "UPDATE user SET vote_down_sent = vote_down_sent+1 WHERE id="+userId;
		 }
		 
		 // author user-vote
		 String authorQuery = "";
		 if(vote == 1)
			 authorQuery = "UPDATE user SET vote_up_rcvd = vote_up_rcvd+1 WHERE id=(SELECT user_id FROM playlist WHERE id="+playlistId+")";
		 else
		 {
			 authorQuery = "UPDATE user SET vote_down_rcvd = vote_down_rcvd+1 WHERE id=(SELECT user_id FROM playlist WHERE id="+playlistId+")";
		 }
		 // playlist vote
		 String playlistQuery = "";
		 if(vote == 1)
			 playlistQuery = "UPDATE playlist SET up = up+1 WHERE id="+playlistId;
		 else
		 {
			 playlistQuery = "UPDATE playlist SET down = down+1 WHERE id="+playlistId;
		 }
		 
		 Connection connection = DBController.getInstance().getConnection();
		 try {
			 PreparedStatement votesStmt = connection.prepareStatement("INSERT INTO votes (playlist_id, song_id, user_id, vote) VALUES (?,?,?,?)");
			 votesStmt.setInt(1, playlistId);
			 votesStmt.setInt(2, this.getSongIdByPlaylistId(playlistId));
			 votesStmt.setInt(3, userId);
			 votesStmt.setInt(4, vote);
			 votesStmt.execute();
			 votesStmt.close();
			 
			 PreparedStatement userStmt = connection.prepareStatement(userQuery);
			 userStmt.execute();
			 userStmt.close();
			 
			 PreparedStatement authorsStmt = connection.prepareStatement(authorQuery);
			 authorsStmt.execute();
			 authorsStmt.close();
			 
			 PreparedStatement playlistStmt = connection.prepareStatement(playlistQuery);
			 playlistStmt.execute();
			 playlistStmt.close();
		 } catch (SQLException e) {
		
			e.printStackTrace();
		 }
		 playlistVersion++;
	 }
	 
	 
	 public boolean isSongInPlaylist(int songId)
	 {
		 boolean result = false;
		 Connection connection = DBController.getInstance().getConnection();
		 try {
			 PreparedStatement ps = connection.prepareStatement("SELECT id FROM playlist WHERE song_id=?");
			 ps.setInt(1,songId);
			 ResultSet rs = ps.executeQuery();
			 while (rs.next())
			 {
				 result = true;
				 break;
			 }	 			 
			 rs.close();
			 ps.close();
		 } catch (SQLException e) {
		
			e.printStackTrace();
		 }
		 return result;
	 }
	 
	 public int getSongIdByPlaylistId(int playlistId)
	 {
		 int result = -1;
		 Connection connection = DBController.getInstance().getConnection();
		 try {
			 PreparedStatement ps = connection.prepareStatement("SELECT song_id FROM playlist WHERE id=? LIMIT 1");
			 ps.setInt(1,playlistId);
			 ResultSet rs = ps.executeQuery();
			 while (rs.next())
			 {
				 result = rs.getInt(1);
				 break;
			 }	 			 
			 rs.close();
			 ps.close();
		 } catch (SQLException e) {
		
			e.printStackTrace();
		 }
		 //LogUtil.getLogger().debug("getSongIdByPlaylistId playlistId:"+playlistId+" songId:"+result);
		 return result;
	 }

	public int getPlaylistVersion() {
		return playlistVersion;
	}
	 
	 
}
