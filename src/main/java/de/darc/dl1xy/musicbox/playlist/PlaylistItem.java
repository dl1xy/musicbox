package de.darc.dl1xy.musicbox.playlist;

public class PlaylistItem {
	int id;
	int songId;
	String songTitle;
	String songArtist;
	String songGenre;
	String songAlbum;
	String songDuration;
	int songPosition;
	int songProgress;
	int songDurationMs;
	int userId;
	String userName;
	int up;
	int down;
	boolean isVoted = false;
	boolean vote = false;
	@Override
	public String toString() {
		return "PlaylistItem [id=" + id + ", songId=" + songId + ", songTitle="
				+ songTitle + ", songArtist=" + songArtist + ", songGenre="
				+ songGenre + ", songAlbum=" + songAlbum + ", songDuration="
				+ songDuration + ", songPosition=" + songPosition
				+ ", songProgress=" + songProgress + ", songDurationMs="
				+ songDurationMs + ", userId=" + userId + ", userName="
				+ userName + ", up=" + up + ", down=" + down + ", isVoted="
				+ isVoted + ", vote=" + vote + "]";
	}
	
	
	
}
