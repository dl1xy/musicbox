package de.darc.dl1xy.musicbox.mp3;

import java.util.List;

public class Mp3SearchResult {
	
	int totalAmount;
	List<Mp3SearchItem> songs;
	int page;
	long totalTime;
	int totalSongs;
	public Mp3SearchResult(int totalAmount, List<Mp3SearchItem> songs, int page, long totalTime, int totalSongs) {
		super();
		this.totalAmount = totalAmount;
		this.songs = songs;
		this.page = page;
		this.totalTime = totalTime;
		this.totalSongs = totalSongs;
	}
	
}
