package FrontEnd;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import BackEnd.ImageFileLister;
import BackEnd.ImageManager;
import photo_renamer.PhotoRenamer;

public class ImageLister {
	
	// the default contents of the JLabel at the top of the screen
	public static final String DEFAULT_HEADER = 
		"<html><strong>SELECT A DIRECTORY CONTAINING IMAGES YOU WOULD LIKE TO MANAGE</strong></html>";
	
	// all the images that has already been read
	private static Box box = Box.createVerticalBox();
	
	/**
	 *	Builds and returns the JPanel for the Image Lister screen.
	 *
	 *	@param frame
	 *				the frame that this JPanel belongs to
	 *	@param box
	 *				the Box which will contain the list of images to tag
	 *	@param dirLabelContents
	 *				the contents of the JLabel at the top of the screen
	 *	@return the JPanel for this screen
	 */
	public static JPanel buildPanel(JFrame frame, String dirLabelContents) {
		JPanel imageListPanel = new JPanel();
		imageListPanel.setLayout(new BoxLayout(imageListPanel, BoxLayout.Y_AXIS));
		
		// creates the file chooser
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		JLabel directoryLabel = new JLabel(dirLabelContents);
		JScrollPane scrollPane = new JScrollPane(box);
		
		// The directory choosing button.
		JButton openButton = new JButton("Choose Directory");
		openButton.setMnemonic(KeyEvent.VK_D);
		openButton.setActionCommand("disable");
		
		// The listener for openButton.
		ActionListener buttonListener = new FileChooserActionListener(frame, imageListPanel, 
																	  directoryLabel, fileChooser);
		openButton.addActionListener(buttonListener);
		
		// The log button.
		JButton logButton = new JButton("Show log");
		logButton.setHorizontalAlignment(SwingConstants.CENTER);
		logButton.setVerticalAlignment(SwingConstants.CENTER);
		logButton.setMnemonic(KeyEvent.VK_D);
		logButton.setActionCommand("disable");
		
		// ========== add action listener
		logButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LogWindow.buildWindow();
			}
		});
		
		// putting everything together
		imageListPanel.add(directoryLabel);
		imageListPanel.add(scrollPane);
		imageListPanel.add(openButton);
		imageListPanel.add(logButton);
		
		imageListPanel.setSize(PhotoRenamer.WINDOW_WIDTH, PhotoRenamer.WINDOW_HEIGHT);
		imageListPanel.setVisible(true);
		
		return imageListPanel;
	}
	
	/**
	 *	Builds and returns the JPanel for the Image Lister screen.
	 *	This overloading of buildPanel also builds the Box that will
	 *	contain the images to tag.
	 *
	 *	@param frame
	 *				the frame that this JPanel belongs to
	 *	@param file
	 *				the file where the images to tag can be found
	 *	@return the JPanel for this screen
	 */
	public static JPanel buildPanel(JFrame frame, File file) {
		//Box box = Box.createVerticalBox();
		String dirLabelContents = "Selected Directory " + file.getPath();
		List<File> images = ImageFileLister.getImagesUnderDirectory(file);

		for(File f : images) {
			try {
				BufferedImage origImg = ImageIO.read(f);
				BufferedImage scaledImg = new BufferedImage(30, 30, origImg.getType());

				// Resize the image 
				Graphics2D imgGraphics = scaledImg.createGraphics();
				imgGraphics.drawImage(origImg, 0, 0, 30, 30, null);
				imgGraphics.dispose();
				
				// the label which represents the image file, f
				JLabel imgLabel = new JLabel(f.getName());
				imgLabel.setIcon(new ImageIcon(scaledImg));
				// this listener takes the user to the ImageViewer screen
				imgLabel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						frame.getContentPane().remove(0);
						ImageViewer.setImagePath(f.getPath());
						JPanel imageViewerPanel = ImageViewer.buildPanel(frame);
						frame.add(imageViewerPanel);
						frame.getContentPane().revalidate();
						frame.getContentPane().repaint();
					}
				});

				box.add(imgLabel, Box.CENTER_ALIGNMENT);

				// adding each image to allImages if not already there
				ImageManager.getInstance().addToAllImages(f.getPath());
			} catch (IOException ex) {}
		}

		return buildPanel(frame, dirLabelContents);
	}
}
