package main.GUI.ActionListeners;
import main.GUI.*;
import main.Teams.PlayerTeam;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import main.Stadium;
import main.Exceptions.CannotPlayMatchException;

/**
 * @author Madison Reilly (jre129)
 * @author David Williamson (dwi79)
 * Action Listener class for the Match GUI, Implements Java.awt.event.ActionListener
 */
public class MatchActionListener implements ActionListener {
    /**
     * Index of the current button, corresponds to the index in the match array 
     */
    int buttonIndex;
    /**
     * Current Instance of Stadium class
     */
    Stadium stadium;
    /**
     * Current instance of the PlayerTeam class
     */
    PlayerTeam currentPlayer;
    /**
     * Constructor, calls no methods only sets variables
     * @param currentStadium The current instance of the stadium class
     * @param index The index of the button in the JButtons array
     * @param player The current instance of the PlayerTeam class
     */
    public MatchActionListener(Stadium currentStadium, int index, PlayerTeam player){
        stadium = currentStadium;
        buttonIndex = index;
        currentPlayer = player;
    }
    /**
     * This method will play the match when the button is clicked
     * @override
     * @param event The ActionEvent class sent when an action is performed
     */
    public void actionPerformed(ActionEvent event) {
        String[] matchString;
        try {
            matchString = stadium.playMatch(currentPlayer, buttonIndex);
            new MatchDetailGUI(matchString);
        } catch (CannotPlayMatchException cannotPlayMatchException) {
            JOptionPane.showMessageDialog(null, cannotPlayMatchException.getMessage(), "An error has occured", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
