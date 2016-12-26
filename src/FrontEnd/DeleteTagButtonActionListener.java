package FrontEnd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JList;

import BackEnd.TagManager;

public class DeleteTagButtonActionListener implements ActionListener{
	/** The JList of available tags. */ 
	JList<String> listOfTags;
	
	/**
	 * The constructor for this DeleteTagButtonActionListener.
	 * 
	 * @param listOfTags
	 * 				the JList of available tags
	 */
	public DeleteTagButtonActionListener(JList<String> listOfTags){
		this.listOfTags = listOfTags;
	}
	
	
	/**
	 * Remove the tags requested to be deleted by the
	 *  user from the JList in which tags are contained
	 *  
	 *  @param e
	 *  	 DeleteTagButton click by the user
	 */
	@Override
	public void actionPerformed(ActionEvent e){
		// If some tags are selected to delete, 
		// get all the tags to be deleted,
		// iterate over each tag to be deleted and 
		// remove the tag from GUI as well as from back end
		if(!this.listOfTags.isSelectionEmpty()){
			Object[] tagsToDelete = this.listOfTags.getSelectedValuesList().toArray();
			
			for(Object tag : tagsToDelete){
				((DefaultListModel<String>)this.listOfTags.getModel()).removeElement(tag);	
				TagManager.getInstance().deleteTag((String)tag);
			}
			// Save the updated tag list to file
			TagManager.getInstance().writeToFile();
		}
	}
}
