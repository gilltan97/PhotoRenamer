package FrontEnd;

import Main.PhotoRenamer;

import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import BackEnd.LogManager;

public class LogWindow {
	
	/**
	 * Builds and displays the log window, which shows all the logs.
	 */
	public static void buildWindow(){
		JFrame f = new JFrame("Logs");
		
		// Add scrollable text area to the window 
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setFont(new Font("Ariel", Font.PLAIN, 16));
		JScrollPane scrollPane = new JScrollPane(textPane);
		
		// Load all the logs from file, 
		// then get all the logs to show to the user
		LogManager.getInstance().readFromFile();
		textPane.setText(LogManager.getInstance().getLogs());
		
		f.add(scrollPane);
		f.setSize(PhotoRenamer.WINDOW_WIDTH, PhotoRenamer.WINDOW_HEIGHT);
		f.setResizable(false);
		f.setVisible(true);
	}
}
