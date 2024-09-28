package main.GUI.ActionListeners;
import main.GUI.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Madison Reilly (jre129)
 * @author David Williamson (dwi79)
 * Action Listener class for the ClubHouse GUI, Implements Java.awt.event.ActionListener
 */
public class ClubHouseActionListener implements ActionListener {

	/**
	 * The current ClubGUI class instance
	 */
	ClubGUI clubFrame;
	/**
	 * The current index selected in the JList
	 */
	int index;

	/**
	 * Constructor, sets values, does not call any methods
	 * @param buttonIndex The index of the selected item or Athlete in the JList
	 * @param frame The Current ClubGUI class instance 
	 */
	public ClubHouseActionListener(int buttonIndex, ClubGUI frame){
		index = buttonIndex;
		clubFrame = frame;
	}

	/**
	 * Implementation of the method from ActionListener, updates the JList and TextPane
	 * @Override
	 * @param event The ActionEvent which occured when an Action was Performed
	 */
	public void actionPerformed(ActionEvent event) {
		clubFrame.updateJList(index);
		clubFrame.updateJTextPane("\n\n\n\n\n");
	}
	
}
