package photo_renamer;
import javax.swing.JFrame;

import FrontEnd.ImageLister;

public class PhotoRenamer {
	public static final int WINDOW_WIDTH = 890; 
	public static final int WINDOW_HEIGHT = 500;
	
	public static void main(String args[]){
		JFrame frame = new JFrame("PhotoRenamer");
		frame.add(ImageLister.buildPanel(frame, 
				  ImageLister.DEFAULT_HEADER));
		frame.setSize(PhotoRenamer.WINDOW_WIDTH, PhotoRenamer.WINDOW_HEIGHT);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
