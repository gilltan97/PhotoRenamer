package FrontEnd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JList;

import BackEnd.Image;
import BackEnd.ImageManager;

public class RevertButtonActionListener implements ActionListener {
	/** The JList of all the names the image has had. */
	JList<String> names;
	/** The path of the image being viewed. */
	String imagePath;
	
	public RevertButtonActionListener(JList<String> names, String imagePath) {
		this.names = names;
		this.imagePath = imagePath;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String name = names.getSelectedValue();
		if((name != null) && 
				(ImageManager.getInstance().getAllImages().containsKey(ImageViewer.getPathName()))) {
			Image image = ImageManager.getInstance().getAllImages().get(ImageViewer.getPathName());
			image.revertName(ImageViewer.getPathName(), name);
			ImageViewer.setImagePath(image.getPathName());
			ImageManager.getInstance().writeToFile();
		} else {
			System.out.println("the image path could not be found in allImages!!");
		}
	}
}
