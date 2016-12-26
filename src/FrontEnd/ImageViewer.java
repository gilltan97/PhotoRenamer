package FrontEnd;

import photo_renamer.PhotoRenamer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.TextField;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import BackEnd.ImageManager;
import BackEnd.TagManager;

public class ImageViewer {
	private static String imagePath;
	
	// Name of the image, a label in the JPanel
	private static JLabel imageName = new JLabel();
	
	// Height and width of the image
	private static final int IMAGE_WIDTH = 500;
	private static final int IMAGE_HEIGHT = 460;
	// The directory that the user chose in Image Lister screen.
	// This is so that the back button can recreate the Image Lister screen
	public static File dirChosen;
	
	
	/**
	 * Return the name of the viewed image.
	 * 
	 * @return
	 * 		the name of the viewed image
	 */
	public static JLabel getimageName(){
		return ImageViewer.imageName;
	}
	
	
	/**
	 * Set the path of the image to new path.
	 * 
	 * @param newPath
	 * 			new path to the image to replace the 
	 * 			old path once the image is tagged
	 */
	public static void setImagePath(String newPath){
		ImageViewer.imagePath = newPath;
		// Change the Label to name the image 
		// at newPath once new imagePath is set
		ImageViewer.imageName.setText("Image Name: " + ImageManager.getInstance()
		 														   .getAllImages()
	                                                               .get(ImageViewer
	                                                               .getPathName())
	                                                               .getName());
		}
	
	
	/**
	 * Return the path name of the viewed image.
	 * 
	 * @return 
	 * 		the path name of the image as String
	 */
	public static String getPathName(){
		return ImageViewer.imagePath;
	}
	
	
	/**
	 * Creates a new JPanel with image to tag, 
	 * and other cool features manage the image 
	 * 
	 * @return
	 * 		A JPanel containing the image and buttons 
	 * 		to manage the image file
	 */
	public static JPanel buildPanel(JFrame frame) {
		// Create the panel
		JPanel imageViewerPanel = new JPanel();
		// set layout so we can specify the 
		// position of elements with BorderLayout
		imageViewerPanel.setLayout(new BorderLayout());
		
		// ============================== Image Pane ==============================
		
		// Image panel to view the image
		JPanel imagePanel = new JPanel();
		imagePanel.setLayout(new BorderLayout());
		BufferedImage origImg = null;
		
		// Try to read and view the image 
		try{
			origImg = ImageIO.read(new File(imagePath));
		}
		// If something went wrong do nothing 
		catch(IOException e){}
		
		// Resize the image 
		BufferedImage scaledImg = new BufferedImage(
				IMAGE_WIDTH, IMAGE_HEIGHT, origImg.getType());
		Graphics2D imgGraphics = scaledImg.createGraphics();
		imgGraphics.drawImage(origImg, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, null);
		imgGraphics.dispose();
		
		// Once the image is read make image icon from that image 
		// Add image to the panel and add panel to the window 
		ImageIcon imgIcon = new ImageIcon(scaledImg);
		JLabel imageLabel = new JLabel(null, imgIcon, JLabel.CENTER );
		imagePanel.add(imageLabel, BorderLayout.CENTER);
		// name of this image
		imageName.setHorizontalAlignment(JLabel.CENTER);
		imagePanel.add(imageName, BorderLayout.NORTH);
		
		// ============================== Back Button ==============================

		JButton backButton = new JButton("Back");
		backButton.addActionListener(new BackButtonActionListener(frame, 
				imageViewerPanel, dirChosen));
		imagePanel.add(backButton, BorderLayout.SOUTH);
		
		//============================== Names Panel ============================== 
		
		JPanel namesPanel = new JPanel();
		namesPanel.setLayout(new BorderLayout());
		JLabel allNamesHeader = new JLabel("<html><em><u><font color='blue'>Revert"+
										   " to previous file names here</font></u>"+
										   "</em></html>");

		// retrieve all names from the Image
		// object corresponding with imagePath
		List<String> allNames = ImageManager.getInstance()
											.getAllImages()
											.get(imagePath)
											.getAllNames();

		JList<String> names = new JList<String>(new DefaultListModel<String>());

		for(String name: allNames) {
			((DefaultListModel<String>) names.getModel())
			.addElement(name);
		}

		names.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane nameScroller = new JScrollPane(names);
		nameScroller.setPreferredSize(new Dimension(380, 200));

		namesPanel.add(nameScroller);
		namesPanel.add(allNamesHeader, BorderLayout.NORTH);

		// button to revert the name of the image 
		// to the selected names
		JButton revertButton = new JButton("Revert name");
		revertButton.addActionListener
		(new RevertButtonActionListener(names, imagePath));
		namesPanel.add(revertButton, BorderLayout.SOUTH);
		
		// ================ Tags Panel ================
		
		// Make a new panel to add check box list of all the tags to
		ArrayList<String> tagList = (ArrayList<String>)TagManager
				                                       .getInstance()
				                                       .getAllTags();
		// Initialize JList where all the tags will go
		JList<String> jlCheckBox = new JList<String>(new DefaultListModel<String>());
		
		// Add the all check boxes with tag name
		for(int i = 0; i < tagList.size(); i++){
			((DefaultListModel<String>)jlCheckBox.getModel())
			                                     .addElement(tagList.get(i));
		} 	
		
		JScrollPane jsPane = new JScrollPane(jlCheckBox);
		jsPane.setPreferredSize(new Dimension(380, 200));
		JPanel tagsPanel = new JPanel();
		// set layout so we can specify the position of elements with BorderLayout
		tagsPanel.setLayout(new BorderLayout());
		tagsPanel.add(jsPane);
		
		JLabel allTagsHeader = new JLabel("<html><em><u><font color='blue'>Tag "+
				                          "this image with the tags below</font>"+
				                          "</u></em></html>");
		
		// ============================== Tag Buttons ==============================
		
		// All the buttons that go in window
		JButton deleteTagButton = new JButton("Delete Tag");
		// Listen to the action performed by delete tag button
		deleteTagButton.addActionListener(new DeleteTagButtonActionListener(jlCheckBox));
		JButton tagImageButton = new JButton("Tag Image");
		tagImageButton.addActionListener(new TagImageButtonActionListener(jlCheckBox, names));
		
		JPanel buttonPanel = new JPanel();
		// set layout so we can specify the position
		// of elements with BorderLayout
		buttonPanel.setLayout(new BorderLayout());
		buttonPanel.add(deleteTagButton, BorderLayout.WEST);
		buttonPanel.add(tagImageButton, BorderLayout.CENTER);
		tagsPanel.add(buttonPanel, BorderLayout.SOUTH);
		tagsPanel.add(allTagsHeader, BorderLayout.NORTH);
		
	//============================ Adding Tags Panel ==============================
		
		JPanel tagAdderPanel = new JPanel();
		tagAdderPanel.setLayout(new BorderLayout());
		TextField tf = new TextField(20);
		JButton addTagButton = new JButton("Add Tag");
		// Listen to the action performed by add tag button
		addTagButton.addActionListener
							(new AddTagButtonActionListener(jlCheckBox, tf));
		JLabel addTagLabel = new JLabel("<html><em><u><font color='blue'>"+
				                        "Add a tag by typing in the text"+
										"field</font></u></em></html>");
		tagAdderPanel.add(addTagLabel, BorderLayout.NORTH);
		tagAdderPanel.add(tf, BorderLayout.CENTER);

		tagAdderPanel.add(addTagButton, BorderLayout.SOUTH);
		
		JPanel sidePanel = new JPanel();
		sidePanel.setLayout(new BorderLayout());
		sidePanel.add(tagsPanel, BorderLayout.CENTER);
		sidePanel.add(namesPanel, BorderLayout.SOUTH);
		sidePanel.add(tagAdderPanel, BorderLayout.NORTH);
		
		imageViewerPanel.add(imagePanel, BorderLayout.CENTER);
		imageViewerPanel.add(sidePanel, BorderLayout.EAST);
		imageViewerPanel.setSize(PhotoRenamer.WINDOW_WIDTH, 
				                 PhotoRenamer.WINDOW_HEIGHT);
		
		imageViewerPanel.setVisible(true);
		return imageViewerPanel;
	}
}
