package de.darc.dl1xy.musicbox.mp3;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sound.sampled.UnsupportedAudioFileException;

import de.darc.dl1xy.musicbox.db.DBController;
import de.darc.dl1xy.musicbox.misc.FileStoreInfo;
import de.darc.dl1xy.musicbox.system.SystemHandler;
import de.darc.dl1xy.musicbox.util.LogUtil;

public class Mp3FileScanner {

	private Mp3Parser parser = new Mp3Parser();
	private int counter = 0;

	public List<FileStoreInfo> getFileStoreInfo()
	{
		List<FileStoreInfo> infoList = new ArrayList<FileStoreInfo>();
		
		for (FileStore store : FileSystems.getDefault().getFileStores()) {
			
			try {
				infoList.add(new FileStoreInfo(counter, this.getRootPath(store).toString()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
		}
		return infoList;
	}
	
	public int scan()
	{
		LogUtil.getLogger().info("scanning..." + new Date());
		if (SystemHandler.getInstance().isSearchAll())
		{
			for (FileStore store : FileSystems.getDefault().getFileStores()) {
	            try {
	            	LogUtil.getLogger().debug(this.getRootPath(store));
					File f = this.getRootPath(store).toFile();
					this.printFnames(f);
				} catch (IOException e) {
					e.printStackTrace();
				}            
	        }
		}
		else
		{
			File f = new File(SystemHandler.getInstance().getSearchFolder());
			this.printFnames(f);
		}
		
		LogUtil.getLogger().info("total scan:"+counter+ " time:"+new Date());
		DBController.getInstance().dropFts4();
    	DBController.getInstance().createFts4();
    	DBController.getInstance().fillFts4();
		return counter;
	}
	
	public void printFnames(File f)
	{
		File[] faFiles = f.listFiles();
		if (faFiles != null)
		{
			for(File file: faFiles)
			{
				if(file.getName().toLowerCase().endsWith("mp3") && !file.isDirectory())
				{
					try {
						if (parser.parse(file) != null)
						{
							++counter;						
						}
					} 
					catch (IOException e){
						LogUtil.getLogger().error(file.getName()+" IOException");
					} catch (NoSuchAlgorithmException e) {
						LogUtil.getLogger().error(file.getName()+" NoSuchAlgorithmException");
					} catch (UnsupportedAudioFileException e) {
						LogUtil.getLogger().error(file.getName()+" UnsupportedAudioFileException");
					}
				}
				if(file.isDirectory())
				{
					printFnames(file);
				}
			}
		}
	}
	
	
	public Path getRootPath(FileStore fs) throws IOException {
	    Path media = Paths.get("/media");
	    if (media.isAbsolute() && Files.exists(media)) { // Linux
	        try (DirectoryStream<Path> stream = Files.newDirectoryStream(media)) {
	            for (Path p : stream) {
	                if (Files.getFileStore(p).equals(fs)) {
	                    return p;
	                }
	            }
	        }
	    } else { // Windows
	        IOException ex = null;
	        for (Path p : FileSystems.getDefault().getRootDirectories()) {
	            try {
	                if (Files.getFileStore(p).equals(fs)) {
	                    return p;
	                }
	            } catch (IOException e) {
	                ex = e;
	            }
	        }
	        if (ex != null) {
	            throw ex;
	        }
	    }
	    return null;
	}
}
