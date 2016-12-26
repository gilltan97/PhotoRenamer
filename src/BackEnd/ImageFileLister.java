package BackEnd;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageFileLister {
	
	/**
	 * Add all the file paths to List  paths
	 * 
	 * @param directory
	 * 				Path of the directory from which all the image files are added to files
	 * @param files
	 * 			List of all the image or directory files in directory
	 */
	private static void getImagesUnderDirectory(File directory, List<File> files) {
		if(directory.isFile()) {
			files.add(directory);
		} else {
			// ImageFileFilter filters out non-image files in the directory			
			for(File child: directory.listFiles(new ImageFileFilter())) {
				getImagesUnderDirectory(child, files); 
			}
		}
	}
	
	
	/**
	 * Return a List of the Files for all the images under directory so that the name and 
	 * file path can be accessed for each.
	 * 
	 * @param directory	
	 * 				a directory or an image file
	 * @return a List of Files
	 */
	public static List<File> getImagesUnderDirectory(File directory) {
		List<File> files = new ArrayList<File>();
		getImagesUnderDirectory(directory, files);
		return files;
	}
}
