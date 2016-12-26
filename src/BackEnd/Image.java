package BackEnd;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Image implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String name;
	// the tags on this Image
	private Set<String> tags;
	// all of the names that this Image has had in the past
	private List<String> allNames;
	private String imgPath;
	
	
	public Image(String imgPath) {
		this.imgPath = imgPath;
		this.name = imgPath.substring(imgPath.lastIndexOf(File.separator) + 1);
		tags = new HashSet<String>();
		allNames = new ArrayList<String>();
		// adds the initial name to allNames
		allNames.add(this.name);
	}
	
	/**
	 * Returns the pathName of the image file.
	 * 
	 * @return the name of this image file
	 */
	public String getPathName(){
		return this.imgPath;
	}
	
	/**
	 * set the pathName of the image file to newPath.
	 * 
	 * @return the name of this image file
	 */
	public void setPathName(String newPath){
		this.imgPath = newPath;
	}
	
	/**
	 * Returns the name of the image file.
	 * 
	 * @return the name of this image file
	 */
	public String getName() {
		return name;
	}
	
	
	/**
	 * Returns all the tags associated to this image file.
	 * 
	 * @return set of all the tags 
	 */
	public Set<String> getTags() {
		return tags;
	}
	
	
	/**
	 * Return an array of all the names of this image file in history 
	 * 
	 * @return Array containing all the names of this file previously 
	 */
	public List<String> getAllNames(){
		return this.allNames;
	}
	
	
	/** 
	 * Removes the tags in tagsToRemove from this Image. 
	 * Logs the renaming, updates name, adds name to allNames, 
	 * removes each tag from tags, and renames the file.
	 * 
	 * @param imagePath
	 * 				the path of the file that corresponds with this Image
	 * @param tagsToRemove
	 * 					the tags to remove from this Image
	 */
	public void removeTags(String imagePath, String[] tagsToRemove){
		if(tagsToRemove.length != 0){
			// removing the tags from the path name
			String newPathName = imagePath;
			
			for(String tag: tagsToRemove) {
				// makes sure to remove the space before the tag as well
				newPathName = newPathName.replace(" " + tag, "");
				
				// removing tag from the set of tags
				this.tags.remove(tag);
			}
			// getting only the name from newPathName
			String newName = newPathName.substring
					(newPathName.lastIndexOf(File.separator) + 1);
			
			// log this renaming
			LogManager.getInstance().addLog(imagePath,this.name, newName);
			
			// add the new name to allNames and update the name
			this.name = newName;
			if(!allNames.contains(this.name)) 
				allNames.add(this.name);
			
			// actually renaming the file
			new File(imagePath).renameTo(new File(newPathName));
			
			// updating the map of allImages in ImageManager
			ImageManager.getInstance().updatePathName(imagePath, newPathName, this);
		}
	}
	
	
	/** 
	 * Tags this Image with the tags in tagsToAdd. Renames the file 
	 * associated with this Image, given by imagePathName, to include
	 * the tags. 
	 * 
	 * Also updates name, adds the new name to allNames, and adds 
	 * each tag to tags.
	 * 
	 * @param imagePath	
	 * 				the pathname of the image file that corresponds to 
	 * 				this Image object
	 * @param tagsToAdd			
	 * 				an array of tags to be added to this Image
	 */
	public void tagImage(String imagePath, String[] tagsToAdd) {
		int index = imagePath.lastIndexOf(".");
		// everything except the file extension
		String newPathName = imagePath.substring(0, index);

		// adding each tag to the end of the path name
		// and adds each tag to the list of tags on this image
		for(String tag: tagsToAdd) {
			newPathName += " " + tag;
			this.tags.add(tag);
		}
		
		//add the file extension to the path name
		newPathName += imagePath.substring(index);

		// gets only the name
		String newName = newPathName.substring
				(newPathName.lastIndexOf(File.separator) + 1);

		// logs this renaming
		LogManager.getInstance().addLog(imagePath, this.name, newName);

		// updates name
		this.name = newName;

		// adds the new name to allNames
		if(!allNames.contains(name)) 
			this.allNames.add(name);

		// actually renames the file
		new File(imagePath).renameTo(new File(newPathName));
		
		// updating the map of allImages in ImageManager
		ImageManager.getInstance().updatePathName(imagePath, newPathName, this);
	}
	
	
	/** 
	 * Renames this image to the specified name, presumably retrieved from allNames. 
	 * Updates name, generates a new tags set, and renames the file.
	 * 
	 * @param imagePath
	 * 				Path of the image that is to be reverted 
	 * @param name
	 * 			Name the file is required to be revered to 
	 */
	public void revertName(String imagePath, String name) {
		this.name = name;

		// logs this renaming
		LogManager.getInstance().addLog(imagePath, this.name, name);
		
		if(name.contains("@")) {
			// taking in the tags in name by reading through the file name
			// and generating an array list of each tag
			List<String> ts = new ArrayList<String>();
			int index = name.indexOf("@");
			int lastIndex = name.lastIndexOf("@");

			while(index != lastIndex) {
				int nextIndex = name.indexOf("@", index + 1);
				// substring goes up to nextIndex - 1 so that the space is not included
				ts.add(name.substring(index, nextIndex - 1));

				index = nextIndex;
			}

			// adding the last tag, ignoring the file extension
			ts.add(name.substring(lastIndex, name.lastIndexOf(".")));

			// generates new tags set from the tags in ts
			this.tags = new HashSet<String>(ts);
		}

		String newPathName = imagePath.substring
				(0, imagePath.lastIndexOf(File.separator) + 1) + name;

		// creates a new file with the same path as imagePathName, except
		// with the new name at the end, and renames the file to this file
		new File(imagePath).renameTo(new File(newPathName));

		// updating the map of allImages in ImageManager
		ImageManager.getInstance().updatePathName(imagePath, newPathName, this);
	}
}
