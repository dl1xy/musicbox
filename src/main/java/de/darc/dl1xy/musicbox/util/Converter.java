package de.darc.dl1xy.musicbox.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.darc.dl1xy.musicbox.db.DBController;

public class Converter {

	public static String durationIntToString(int duration)
	{
		int second = (int) (((double)duration / 1000f) % 60f);
		int minute = (int) (((double)duration / (1000f * 60f)) % 60f);
		int hour = (int) (((double)duration / (1000f * 60f * 60f)) % 24f);
		String time = String.format("%02d:%02d:%02d", hour, minute, second);
		return time;
	}
	
	public static String getGenreById(int id)
	{
		String result = "";
		Connection connection = DBController.getInstance().getConnection();
		try {
			 Statement s = connection.createStatement();
			 //ResultSet rs = s.executeQuery("SELECT s.id AS songId, s.title, s.artist, m.genre, s.album, s.duration, u.id AS userId, u.name, SUM(v.pro) AS up, SUM(v.contra) AS down FROM songs s, user u, playlist p, mp3genres m, votes v WHERE p.song_id = s.id AND p.user_id = u.id AND m.id=s.genre AND v.song_id=s.id;");
			 ResultSet rs = s.executeQuery("SELECT genre FROM mp3genres WHERE id="+id);
			 while (rs.next())
			 {
				 result = rs.getString("genre");
			 }
			 rs.close();
			 s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
