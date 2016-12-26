package BackEnd;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ImageManager {
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
	
	private final static ImageManager imageManagerInstance = new ImageManager();	 
	
	private Map<String, Image> allImages;
	private String FILE_PATH = "src" + File.separator +
                               "BackEnd" + File.separator +
                               "ManagerFiles" + File.separator +
                               "imgData.ser"; 
	// the strategy of reading and writing that this ImageManager will use
	private FileInputterOutputter ioStrategy = new SerialDataFileInputterOutputter();
	
	
	/**
	 * Returns the single instance of ImageManager that exists.
	 * 
	 * @return	ImageManager object
	 */
	public static ImageManager getInstance(){
		return ImageManager.imageManagerInstance;
	}
	
	
	private ImageManager() {
		// Initialize allImages
		allImages = new HashMap<String, Image>();
		
		// If file exists then read the logs to allLogs from that file
		if (new File(FILE_PATH).exists()){
			this.readFromFile();
		}
	}

	
	/**
	 * Return the all images mapped to their respected paths.
	 * 
	 * @return
	 * 		Return a Map thats maps file paths to file names
	 */
	public Map<String, Image> getAllImages() {
		return allImages;
	}
	
	
	/**
	 * Checks if imagePathName is in allImages. If not, add it 
	 * to <code>allImages</code>. This is used before adding 
	 * tags to an image.
	 * 
	 * @param imagePath
	 * 				path of the image to be mapped to the 
	 * 				Image object in <code>allImages</code>
	 */
	public void addToAllImages(String imagePath) {
		if(!allImages.containsKey(imagePath)) {
			Image img = new Image(imagePath);
			allImages.put(imagePath, img);
		}
	}
	
	/**
	 * Remove the old path named mapped to the image object and add 
	 * a new path name of the updated copy of that image. 
	 * Also update the path name of <code>updatedImage</code>.
	 * 
	 * @param oldPathName
	 * 				Path mapped to the out of date Image object 
	 * @param newPathName
	 * 				Path mapped to the out up date Image object
	 * @param updatedImage
	 * 					Up to date Image to mapped to its file path
	 */
	protected void updatePathName(String oldPathName, 
								  String newPathName, 
								  Image updatedImage){
		
		updatedImage.setPathName(newPathName);
		ImageManager.getInstance().allImages.remove(oldPathName);
		ImageManager.getInstance().allImages.put(newPathName, updatedImage);
	}
	
	
	/**
	 * This method reads object of type <code>HashMap(String, Image)</code> and stores 
	 * in instance variable <code>allImages</code>
	 * 
	 * @serialData read data as <code>HashMap(Sting, Image)</code> and store it
	 * 			   in instance variable <code>allTags</code>
	 */
	public void readFromFile() {
		this.allImages = ioStrategy.readFromFile(FILE_PATH);
	}
	
	
	/**
	 * This method writes <code>HashMap<String, Image> allImages </code>
	 * to a file at file path <code>FILE_PATH</code>
	 * 
	 * @serialData write data as <code>HashMap<String, Image></code> 
	 * 			   in instance variable file at specified path 
	 */
	public void writeToFile() {
		ioStrategy.writeToFile(FILE_PATH, this.allImages);
	}
}
