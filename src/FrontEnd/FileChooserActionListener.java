package FrontEnd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The listener for the button to choose a directory.
 */
public class FileChooserActionListener implements ActionListener {
	/** The frame that the panel and button belong to. */
	private JFrame frame;
	/** The panel the button is in. */
	private JPanel imageListPanel;
	/** The label for the full path to the chosen directory. */
	private JLabel directoryLabel;
	/** The file chooser to use when the user clicks. */
	private JFileChooser fileChooser;

	/**
	 * An action listener on the choose directory button.
	 *
	 * @param dirFrame
	 *            the main window
	 * @param dirLabel
	 *            the label for the directory path
	 * @param fileChooser
	 *            the file chooser to use
	 */
	public FileChooserActionListener(JFrame frame, JPanel imageListPanel, 
									 JLabel dirLabel, 
									 JFileChooser fileChooser) {
		this.frame = frame;
		this.imageListPanel = imageListPanel;
		this.directoryLabel = dirLabel;
		this.fileChooser = fileChooser;
	}

	/**
	 * Handle the user clicking on the choose directory button.
	 *
	 * @param e
	 *            the event object
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		int returnVal = fileChooser.showOpenDialog(imageListPanel.getRootPane());
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			// updates dirChosen in ImageViewer
			ImageViewer.dirChosen = file;
			
			// displays the new panel which includes the new list of images
			frame.getContentPane().remove(imageListPanel);
			frame.add(ImageLister.buildPanel(frame, file));
			frame.getContentPane().revalidate();
			frame.getContentPane().repaint();
		} else {
			directoryLabel.setText("No Path Selected");
		}
	}
}
