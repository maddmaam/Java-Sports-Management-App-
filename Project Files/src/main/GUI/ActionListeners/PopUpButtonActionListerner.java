package main.GUI.ActionListeners;
import main.GUI.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.naming.directory.InvalidAttributesException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import main.Teams.PlayerTeam;
import main.Stadium;
import main.Exceptions.CannotPlayMatchException;


/**
 * @author Madison Reilly (jre129)
 * @author David Williamson (dwi79)
 * Action Listener class for popups, Implements Java.awt.event.ActionListener
 */
public class PopUpButtonActionListerner implements ActionListener{

    /**
     * The current JFrame instance (StadiumGUI)
     */
    JFrame currentFrame;
    /**
     * The current Stadium logic Class instance
     */
    Stadium currentStadium;
    /**
     * The current PlayerTeam class Instance
     */
    PlayerTeam currentPlayer;
    /**
     * Constructor, calls no methods, only sets variables
     * @param frame The StadiumGUI JFrame which is currently open
     * @param stadium The Stadium logic class which StadiumGUI is using
     * @param player The current PlayerTeam class instance
     */
    public PopUpButtonActionListerner(JFrame frame, Stadium stadium, PlayerTeam player){
        currentFrame = frame;
        currentStadium = stadium;
        currentPlayer = player;
    }

    /**
     * @override
     * 
     */
    public void actionPerformed(ActionEvent arg0) {
    	int choice = JOptionPane.showConfirmDialog(currentFrame, "Would you like to take a bye?", "Bye", JOptionPane.YES_NO_OPTION);
    	if (choice == JOptionPane.YES_OPTION) {
            try {
                currentStadium.takeBye();
                int specialTrainingChoice = JOptionPane.showConfirmDialog(currentFrame, "Would you like to have one of your athletes undergo special training during their break?", "Special Training", JOptionPane.YES_NO_OPTION);
                if (specialTrainingChoice == JOptionPane.YES_OPTION){
                    currentFrame.dispose();
                    new SpecialTrainingGUI(currentPlayer, currentStadium);
                }
                else{
                    try {
                        new GameEnvironmentGUI();
                        currentFrame.dispose();
                    } catch (InvalidAttributesException invalidAttributesException) {
                        JOptionPane.showMessageDialog(currentFrame, invalidAttributesException.getMessage(), "An error has occured.", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (CannotPlayMatchException cannotPlayMatchException) {
                JOptionPane.showMessageDialog(currentFrame, cannotPlayMatchException.getMessage(), "An error has occured.", JOptionPane.ERROR_MESSAGE);
            }
            
    	}
    	else {
    		//System.out.println("The answer is no!"); this was used for testing
    	}
    }

}
