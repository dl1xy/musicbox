package de.darc.dl1xy.musicbox.mp3;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import javazoom.spi.mpeg.sampled.file.MpegAudioFileFormat;
import de.darc.dl1xy.musicbox.util.LogUtil;

public class Mp3Parser {

	static HexBinaryAdapter hexBin = new HexBinaryAdapter();
	
	public Mp3Item parse(File file) throws NoSuchAlgorithmException, UnsupportedAudioFileException, IOException
	{
		
		Mp3Item song = new Mp3Item();
		AudioFileFormat baseFileFormat = null;
		//AudioFormat baseFormat = null;
		
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		String hash = hexBin.marshal(md5.digest(file.getAbsolutePath().getBytes()));
		
		if (song.isHashExisting(hash))
		{
			return null;
		}
		 
		baseFileFormat = AudioSystem.getAudioFileFormat(file);
	
		//baseFormat = baseFileFormat.getFormat();
		// TAudioFileFormat properties
		if (baseFileFormat instanceof MpegAudioFileFormat)
		{
			
		    @SuppressWarnings("unchecked")
			Map<String, Object>	properties = ((MpegAudioFileFormat)baseFileFormat).properties();
		    
		    String title = (String) properties.get("title");
		    String author = (String) properties.get("author");
		    if ((title == null || title.equals("")) && (author == null || author.equals("")))
		    {
		    	return null;
		    }
		    String album = (String) properties.get("album");
		    long duration = (Long) properties.get("duration"); 
		   
		  
		    song.title = title;
		    song.artist = author;
		    song.album = album;
		    song.duration = duration / 1000;
		    song.fileName = file.getName ();
		    song.path = file.getAbsolutePath();
		    song.hash = hash;
		    song.genre = (String)properties.get("mp3.id3tag.genre");
		    song = song.serialize();
		    LogUtil.getLogger().debug(song);
		}
		
		return song;
	}
}
