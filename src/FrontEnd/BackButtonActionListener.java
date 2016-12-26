package FrontEnd;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFrame;

public class BackButtonActionListener implements ActionListener {
	/** the current frame */
	JFrame frame;
	/** the single current component in this frame */
	Component currComp;
	/** the directory that the user chose to work in */
	File dirChosen;
	
	
	/**
	 * An action listener on the back button.
	 *
	 * @param frame
	 *            the main window
	 * @param currComp
	 * 				the current component in this frame
	 * @param dirChosen
	 * 				the directory that the user chose to work in
	 */
	public BackButtonActionListener(JFrame frame, Component currComp, File dirChosen) {
		this.frame = frame;
		this.currComp = currComp;
		this.dirChosen = dirChosen;
	}

	
	/**
	 * Handle the user clicking on the back button.
	 *
	 * @param e
	 *            the event object
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		frame.getContentPane().remove(currComp);
		frame.add(ImageLister.buildPanel(frame, "Selected Directory " + dirChosen.getPath()));
		frame.getContentPane().revalidate();
		frame.getContentPane().repaint();
	}
}
