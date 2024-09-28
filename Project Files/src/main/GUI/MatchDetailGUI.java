package main.GUI;

import javax.naming.directory.InvalidAttributesException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;

/**
 * GUI Class for Match details; extends JFrame.
*/
public class MatchDetailGUI extends JFrame{
    /**
     * The current Content Pane for the JFrame GUI
     */
    JPanel contentPanel;
    /**
     * A String array of the outcome of the match which will be displayed to the user
     */
    String[] matchOutcomeStrings;
    /**
     * A JLabel array of the Displayed Strings
     */
    JLabel[] displayedOutcomeStrings;
    /**
     * A boolean to determine if we have completed displaying the match
     */
    boolean completedDisplayingMatch;
    /**
     * A swing Timer to wait before firing another actionEvent
     */
    Timer waitTimer;
    /** Integer assigned to 0, assigned at class level as need to be
     * effectively final for sub method implementation
     */
    int i = 0, j = 0;

    /**
     * Constructor, calls the private initialize method to show the JFrame
     * @param matchStrings The match outcome String Array
     */
    public MatchDetailGUI(String[] matchStrings){
        matchOutcomeStrings = matchStrings;
        completedDisplayingMatch = false;
        initialize();
    }
    /**
     * Private method to iniatlize the window frame, called on construction of {@link MatchDetailGUI}
     */
    void initialize(){
        contentPanel = new JPanel();
        GridBagLayout gBagLayout = new GridBagLayout();
        contentPanel.setLayout(gBagLayout);
        displayedOutcomeStrings = new JLabel[6];
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
			public void windowClosing(WindowEvent e){
				if(completedDisplayingMatch){
                    dispose();
                }
			}
        });
        setBounds(100, 100, 1400, 300);
		setMinimumSize(new Dimension(1400, 300));
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        for(int i = 0; i < 6; i ++){
            displayedOutcomeStrings[i] = new JLabel(matchOutcomeStrings[i]);
        }
        contentPanel.setVisible(true);
        completedDisplayingMatch = showMatchStrings();
        setContentPane(contentPanel);

        setVisible(true);
        setAlwaysOnTop(true);
        
    }

    boolean showMatchStrings(){
        waitTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Font currentFont = displayedOutcomeStrings[i].getFont();
                if(i == 5){
                    displayedOutcomeStrings[i].setFont(currentFont.deriveFont(Font.BOLD, 14));
                }
                GridBagConstraints gBagConstraints = new GridBagConstraints();
                gBagConstraints.gridwidth = 1;
                gBagConstraints.insets = new Insets(10, 0, 10, 5);
                gBagConstraints.gridx = 0;
                gBagConstraints.gridy = j;
                contentPanel.add(displayedOutcomeStrings[i], gBagConstraints);
                displayedOutcomeStrings[i].setVisible(true);
                if (i==5) {
                    JButton btnContinue = new JButton("Continue");
                    GridBagConstraints gbc_btnContinue = new GridBagConstraints();
                    gbc_btnContinue.gridx = 0;
                    gbc_btnContinue.gridy = 15;
                    contentPanel.add(btnContinue, gbc_btnContinue);
                    btnContinue.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                try {
                                    new GameEnvironmentGUI();
                                    setVisible(false);
                                    dispose();
                                } catch (InvalidAttributesException invalidAttributesException) {
                                    JOptionPane.showMessageDialog(contentPanel, invalidAttributesException.getMessage(), "An error has occured.", JOptionPane.ERROR_MESSAGE);
                                }
                        }
                    });
                }
                validate();
                i++;
                j+=2;
                if(i == 6 || j == 12){
                    waitTimer.stop();
                }
            }
        });
        waitTimer.start();
        return true;
    }
}
