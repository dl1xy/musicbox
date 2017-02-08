package de.darc.dl1xy.musicbox.user;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.darc.dl1xy.musicbox.db.DBController;

public class UserHandler {
	private static UserHandler instance;
	
	private UserHandler()
	{
		
	}
	
	public static UserHandler getInstance()
	{
		if (instance == null)
			instance = new UserHandler();
		return instance;
	}
	
	public int addUser(String username, String password,  String userAgent)
	 {
		 int userId = -1;
		
		 Connection connection = DBController.getInstance().getConnection();
		 try {
			
			 PreparedStatement ps = connection.prepareStatement("INSERT INTO user (name, password, userAgent) VALUES (?,?,?)");
			 ps.setString(1,  username);
			 ps.setString(2,  password);
			 ps.setString(3,  userAgent);
			 ps.execute();
			 ps.close();
			 
			 Statement s = connection.createStatement();
			 ResultSet rs = s.executeQuery("SELECT max(id) FROM user");
			 while (rs.next())
			 {
				 userId = rs.getInt(1);
				 break;
			 }
		 } 
		 catch (SQLException e) 
		 {
		
			e.printStackTrace();
		 }
		 return userId;
	 }
	
	 public int userExists(String username, String password)
	 {
		 int userId = -1;
		 
		 Connection connection = DBController.getInstance().getConnection();
		 try {
			
			 PreparedStatement ps = connection.prepareStatement("SELECT id FROM user WHERE name=? AND password=?");
			 ps.setString(1,  username);
			 ps.setString(2,  password);
			 ResultSet rs = ps.executeQuery();
			 
			 while (rs.next())
			 {
				 userId = rs.getInt(1);
				 break;
			 }
			 ps.close();
			 rs.close();			 
		 } 
		 catch (SQLException e) 
		 {		
			e.printStackTrace();
		 }
		 
		 return userId;
	 }
	 
	 public boolean userNameExists(String username)
	 {
		 boolean result = false;
			 
		 Connection connection = DBController.getInstance().getConnection();
		 try {
			
			 PreparedStatement ps = connection.prepareStatement("SELECT id FROM user WHERE LOWER(name)=?");
			 ps.setString(1,  username.toLowerCase());
			 
			 ResultSet rs = ps.executeQuery();
			 
			 while (rs.next())
			 {
				 result = true;
				 break;
			 }
			 ps.close();
			 rs.close();			 
		 } 
		 catch (SQLException e) 
		 {		
			e.printStackTrace();
		 }
		 
		 return result;
	 }
	 
	 public boolean isAdmin(int userId)
	 {
		 boolean result = false;
		 
		 Connection connection = DBController.getInstance().getConnection();
		 try {
			
			 PreparedStatement ps = connection.prepareStatement("SELECT id FROM user WHERE id=? AND admin=1");
			 ps.setInt(1,  userId);
			 
			 ResultSet rs = ps.executeQuery();
			 
			 while (rs.next())
			 {
				 result = true;
				 break;
			 }
			 ps.close();
			 rs.close();			 
		 } 
		 catch (SQLException e) 
		 {		
			e.printStackTrace();
		 }
		 
		 return result;
	 }
	 
	 public void incSongsPlayed(int userId)
	 {
		Connection connection = DBController.getInstance().getConnection();
		try {
			 PreparedStatement s = connection.prepareStatement("UPDATE user SET songs_played = songs_played +1 WHERE id=?");
			 s.setInt(1, userId);
			 s.execute();
			 s.close();
			 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	 }
	 
	 public void incSongsPlaylisted(int userId)
	 {
		Connection connection = DBController.getInstance().getConnection();
		try {
			 PreparedStatement s = connection.prepareStatement("UPDATE user SET songs_playlisted = songs_playlisted +1 WHERE id=?");
			 s.setInt(1, userId);
			 s.execute();
			 s.close();
			 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	 }
	 
	 
	 public UserItem getUserById(int userId)
	 {
		 UserItem user = null; 
		 Connection connection = DBController.getInstance().getConnection();
		 try {
			 PreparedStatement s = connection.prepareStatement("SELECT * FROM user WHERE id=? LIMIT 1");
			 s.setInt(1, userId);
			 ResultSet rs = s.executeQuery();
			 while (rs.next())
			 {
				 user = new UserItem();
				 user.id = rs.getInt("id");
				 user.name = rs.getString("name");
				 user.userAgent = rs.getString("userAgent");
				 user.voteUpRcvd = rs.getInt("vote_up_rcvd");
				 user.voteDownRcvd = rs.getInt("vote_down_rcvd");
				 user.voteUpSent = rs.getInt("vote_up_sent");
				 user.voteDownSent = rs.getInt("vote_down_sent");
				 user.songsUploaded = rs.getInt("songs_uploaded");
				 user.songsPlayed = rs.getInt("songs_played");
				 user.songsPlaylisted = rs.getInt("songs_playlisted");
				 user.isAdmin = rs.getBoolean("admin");
			 }
			 rs.close();
			 s.close();
			 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 
		 return user;
	 }
	 
	 public List<UserItem> getAllUsers()
	 {
		 List<UserItem> users = new ArrayList<UserItem>(); 
		 Connection connection = DBController.getInstance().getConnection();
		 try {
			 PreparedStatement s = connection.prepareStatement("SELECT id FROM user ORDER BY id");
			 
			 ResultSet rs = s.executeQuery();
			 while (rs.next())
			 {
				UserItem user = this.getUserById(rs.getInt("id"));
				if (user != null)
					users.add(user);
			 }
			 rs.close();
			 s.close();
			 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 
		 return users;

	 }
	 
	 public String parseUserAgent(String userAgent)
	 {
		 final String prefix = "<i class=\"fa fa-";
		 final String suffix = "\"></i>";
		 if (userAgent.toLowerCase().contains("windows"))
			 return prefix + "windows" + suffix;
		 if (userAgent.toLowerCase().contains("mac"))
			 return prefix + "apple" + suffix;
		 if (userAgent.toLowerCase().contains("android"))
			 return prefix + "android" + suffix;
		
		 
		 return prefix + "question" + suffix;
	 }
	 
	 public List<UserItem> getTop5MostPlayed()
	 {
		 List<UserItem> users = new ArrayList<UserItem>(); 
		 Connection connection = DBController.getInstance().getConnection();
		 try {
			 PreparedStatement s = connection.prepareStatement("SELECT id FROM user WHERE admin=0 ORDER BY songs_played DESC LIMIT 5");
			 
			 ResultSet rs = s.executeQuery();
			 while (rs.next())
			 {
				UserItem user = this.getUserById(rs.getInt("id"));
				if (user != null)
					users.add(user);
			 }
			 rs.close();
			 s.close();
			 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 
		 return users;
	 }
	 
	 public int addFeedback(int userId, String msg)
	 {
		 int result = -1;
		 Connection connection = DBController.getInstance().getConnection();
		 try {
			 PreparedStatement s = connection.prepareStatement("INSERT INTO feedback (user_id, msg, timestamp) VALUES (?,?,?)");
			 s.setInt(1, userId);
			 s.setString(2, msg);
			 s.setDate(3, new Date(System.currentTimeMillis()));
			 result = s.executeUpdate();			 
			 s.close();
			 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	 }
	 public List<UserItem> getTop5BestVoted()
	 {
		 List<UserItem> users = new ArrayList<UserItem>(); 
		 Connection connection = DBController.getInstance().getConnection();
		 try {
			 PreparedStatement s = connection.prepareStatement("SELECT id FROM user WHERE admin=0 ORDER BY vote_up_rcvd/CASE WHEN vote_down_rcvd = 0 THEN 1 ELSE vote_down_rcvd END DESC LIMIT 5");
			 
			 ResultSet rs = s.executeQuery();
			 while (rs.next())
			 {
				UserItem user = this.getUserById(rs.getInt("id"));
				if (user != null)
					users.add(user);
			 }
			 rs.close();
			 s.close();
			 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 
		 return users;
		 
	 }
	 
	 public List<UserItem> getTop5MostUploaded()
	 {
		 List<UserItem> users = new ArrayList<UserItem>(); 
		 Connection connection = DBController.getInstance().getConnection();
		 try {
			 PreparedStatement s = connection.prepareStatement("SELECT id FROM user WHERE admin=0 ORDER BY songs_uploaded DESC LIMIT 5");
			 
			 ResultSet rs = s.executeQuery();
			 while (rs.next())
			 {
				UserItem user = this.getUserById(rs.getInt("id"));
				if (user != null)
					users.add(user);
			 }
			 rs.close();
			 s.close();
			 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 
		 return users;
	 }
}
