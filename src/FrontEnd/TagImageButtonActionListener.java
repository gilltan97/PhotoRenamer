package FrontEnd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import BackEnd.Image;
import BackEnd.ImageManager;

public class TagImageButtonActionListener implements ActionListener {
	/** The JList of available tags. */
	JList<String> tagsToTagImage;
	/** The JList of all the names the image has had. */
	JList<String> names;
	
	/**
	 * The constructor for this TagImageButtonActionListener.
	 * 
	 * @param tagsToTagImage
	 * 					the JList of available tags
	 * @param names
	 * 					the JList of all the names the image has had
	 */
	public TagImageButtonActionListener(JList<String> tagsToTagImage, JList<String> names){
		this.tagsToTagImage = tagsToTagImage;
		this.names = names;
	}
	
	
	/**
	 * Tag the Image with requested tags by the user
	 *  
	 *  @param e
	 *  	 TagImageButton click by the user
	 */
	@Override
	public void actionPerformed(ActionEvent e){
		if(!this.tagsToTagImage.isSelectionEmpty()){
			
			ArrayList<String> validTags = new ArrayList<String>();
			String imageName = ImageViewer.getPathName().substring(ImageViewer.getPathName().lastIndexOf(File.separator) + 1);
			
			// Check if selected tags are already used to tag the image
			for(Object tag : this.tagsToTagImage.getSelectedValuesList()){
				if(imageName.lastIndexOf((String)tag) == -1){
					validTags.add((String) tag);
				}
			}
			
			if(!validTags.isEmpty()) {
				// Cast the ArrayList of tags to array of tags containing String object 
				String[] selectedTagsAsStringArray = Arrays.copyOf(validTags.toArray(), 
						validTags.toArray().length, 
						String[].class);

				// Tag the image at specified path with the list of paths  
				Image imageToTag = ImageManager.getInstance().getAllImages().get(ImageViewer.getPathName());
				
				imageToTag.tagImage(ImageViewer.getPathName(), selectedTagsAsStringArray);
				ImageViewer.setImagePath(imageToTag.getPathName());
				ImageManager.getInstance().writeToFile();
				
				if(!((DefaultListModel<String>) names.getModel())
						.contains(imageToTag.getName())) {
					((DefaultListModel<String>) names.getModel())
					.addElement(imageToTag.getName());
				}
			}
		}
	}
}
