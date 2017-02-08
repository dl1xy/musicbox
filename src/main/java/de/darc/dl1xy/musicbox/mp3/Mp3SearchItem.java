package de.darc.dl1xy.musicbox.mp3;

public class Mp3SearchItem {
	int id;
	String title;
	String artist;
	String album;
	String duration;
	boolean inPlaylist;
	
	public Mp3SearchItem(int id, String title, String artist, String album,
			String duration, boolean inPlaylist) {
		super();
		this.id = id;
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.duration = duration;
		this.inPlaylist = inPlaylist;
	}
	
	@Override
	public String toString() {
		return "SongSearch [id=" + id + ", title=" + title + ", artist="
				+ artist + ", album=" + album + ", duration=" + duration
				+ ", inPlaylist=" + inPlaylist + "]";
	}
}

