package BackEnd;

import java.util.List;
import java.util.ArrayList;
import java.io.File;

public class TagManager {
	/**
	 * This class uses singleton design pattern.
	 * It only allows a single instance to exist at once,
	 * and allows classes to get the instance.
	 * 
	 * It also uses the strategy pattern. 
	 * This class stores the strategy of reading and writing
	 * in a variable ioStrategy. And its readFromFile() and
	 * writeToFile() methods use this variable.
	 */

	private static final TagManager TagManagerInstance = new TagManager();
	
	private List<String> allTags;
	private String FILE_PATH = "src" + File.separator +
			                   "BackEnd" + File.separator +
			                   "ManagerFiles" + File.separator +
			                   "tagData.ser"; 
	// the strategy of reading and writing that this TagManager will use
	private FileInputterOutputter ioStrategy = new SerialDataFileInputterOutputter();
	
	
	private TagManager(){
		// Initialize allTags
		this.allTags = new ArrayList<String>();
		
		// If file exists then read the logs to allLogs from that file
		if (new File(FILE_PATH).exists()){
			this.readFromFile();
		}
	}
	
	/**
	 * Returns the single instance of TagManager that exists.
	 * 
	 * @return	TagManager object
	 */
	public static TagManager getInstance(){
		return TagManager.TagManagerInstance;
	}
	
	
	/**
	 * Add a new tag in the array of current tags with which an image 
	 * can be tagged.
	 * 
	 * @param tagName
	 * 				tag to be added in the list of all the tags
	 * @return void
	 */
	public void addTag(String tagName){
		// If tagName does not exist in the list of tags then add the tag
		if (this.allTags.indexOf(tagName) == -1){
			this.allTags.add(tagName);
		}
	}
	
	
	/**
	 * Remove the specified tag from the list of all the tags
	 * 
	 * @param tagName
	 * 				tag to be removed in the list of all the tags		
	 */
	public void deleteTag(String tagName){
		// If tagName exist in the list of tags then remove the tag
		if (this.allTags.indexOf(tagName) != -1){
			this.allTags.remove(tagName);
		}
	}
	
	
	/**
	 * Return an array list of all the tags available to tag an image with. 
	 * 
	 * @return Array list of all the tags to tag an image with
	 */
	public List<String> getAllTags(){
		return allTags;
	}
	
	
	/**
	 * return the file path of the file to which tag object
	 *  is written and from which data is read
	 * @return
	 * 		the file path of the file that saves tag objects
	 */
	public String getTagManagerDataFilePath(){
		return FILE_PATH;
	}
	
	
	/**
	 * This method reads object of type <code>List</code> and stores 
	 * in instance variable <code>allTags</code>.
	 * 
	 * @serialData read data as <code>List</code> and store it
	 * 			   in instance variable <code>allTags</code>
	 */
	public void readFromFile(){
		this.allTags = ioStrategy.readFromFile(FILE_PATH);
	}
	
	
	/**
	 * This method writes <code>List allImages</code> to a file 
	 * at path <code>FILE_PATH</code>
	 * 
	 * @serialData write data as <code>List</code> 
	 * 			   in instance variable file at specified path 
	 */
	public void writeToFile(){
		ioStrategy.writeToFile(FILE_PATH, this.allTags);
	}
}
	