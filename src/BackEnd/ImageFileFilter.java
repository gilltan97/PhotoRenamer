package BackEnd;

import java.io.File;
import java.io.FileFilter;

public class ImageFileFilter implements FileFilter {

	// Filters directories and files with extension .jpg or .png.
	public boolean accept(File f) {
		return f.isDirectory() ||
				(f.getName().endsWith(".jpg")) || 
				(f.getName().endsWith(".png")) || 
				(f.getName().endsWith(".PNG")) || 
				(f.getName().endsWith(".JPG"));
	}
}
