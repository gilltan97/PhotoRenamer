package FrontEnd;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import BackEnd.TagManager;

public class AddTagButtonActionListener implements ActionListener {
	/** The JList of available tags. */ 
	JList<String> listOfTags;
	/** The TextField potentially containing a new tag to add. */
	TextField newTag;
	
	/**
	 * The constructor for this AddTagButtonActionListener.
	 * 
	 * @param listOfTags
	 * 				the JList of available tags
	 * @param newTag
	 * 				the TextField potentially containing a new tag to add
	 */
	public AddTagButtonActionListener(JList<String> listOfTags, TextField newTag){
		this.listOfTags = listOfTags;
		this.newTag = newTag;
	}
	
	/**
	 * Handle the user clicking on the add tag button.
	 *
	 * @param e
	 *            the event object
	 */
	@Override
	public void actionPerformed(ActionEvent e){
		// If text is contained in the TextField
		if (this.newTag.getText().length() > 0){
			// If users tag name do not contain '@' add it
			String newTagName = this.newTag.getText().charAt(0) == '@'? 
								this.newTag.getText():
								'@' + this.newTag.getText();
			// Add the tag in the backed and save it to file
			TagManager.getInstance().addTag(newTagName);
			TagManager.getInstance().writeToFile();
			// update the list of all the tags in GUI
			((DefaultListModel<String>)this.listOfTags.getModel()).addElement(newTagName);	
			this.newTag.setText("");
		}
	}
}
