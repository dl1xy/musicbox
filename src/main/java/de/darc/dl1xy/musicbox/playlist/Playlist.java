package de.darc.dl1xy.musicbox.playlist;

import java.util.List;

public class Playlist {

	int totalSongs;
	String totalPlaytime;
	PlaylistItem currentSong;
	List<PlaylistItem> songs;
	
	public Playlist()
	{
		
	}
	
	public Playlist(int totalSongs, String totalPlaytime,
			PlaylistItem currentSong, List<PlaylistItem> songs) {
		super();
		this.currentSong = currentSong;
		this.totalSongs = totalSongs;
		this.totalPlaytime = totalPlaytime;
		this.songs = songs;
	}

	@Override
	public String toString() {
		return "Playlist [totalSongs=" + totalSongs + ", totalPlaytime="
				+ totalPlaytime + ", currentSong=" + currentSong + ", songs="
				+ songs + "]";
	}

	
}
