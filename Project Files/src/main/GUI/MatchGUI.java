package main.GUI;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import main.Environment;
import main.Teams.OpposingTeam;
import main.Teams.PlayerTeam;
import main.Stadium;
import main.Exceptions.CannotPlayMatchException;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import java.awt.Font;

/**
 * GUI Class for the Match screen; extends JFrame.
*/
public class MatchGUI extends JFrame {
	/**
	 * The Conent Pane of the GUI
	 */
	private JPanel contentPane;
	/**
	 * The Match Labels Array
	 */
	JLabel[] matchJLabels;
	/**
	 * The Play button for each match array
	 */
	JButton[] matchJButtons;
	/**
	 * Instance of the Stadium logic class
	 */
	Stadium currentStadium;
	/**
	 * The Team stats arrays
	 */
	JTextArea[] teamStats;
	/**
	 * Instance of the 
	 */
	PlayerTeam currentPlayer;

	/**
	 * Create the Frame
	 * @param matchStadium instance of Stadium class, will be used to play the Match
	 * @param player instance of PlayerTeam class, used to play the match in the Stadium
	 * @param environemnt current instance of the environment class
	 */
	public MatchGUI(Stadium matchStadium, PlayerTeam player, Environment environment) {
		currentStadium = matchStadium;
		currentPlayer = player;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 450);
		setMinimumSize(new Dimension(450, 450));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		contentPane.setLayout(gbl_contentPane);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event){
				new GameEnvironmentGUI(environment);
				dispose();
			}
		});
		
		JLabel lblChooseAMatch = new JLabel("Choose a Match");
		lblChooseAMatch.setFont(new Font("Dialog", Font.BOLD, 14));
		GridBagConstraints gbc_lblChooseAMatch = new GridBagConstraints();
		gbc_lblChooseAMatch.gridwidth = 2;
		gbc_lblChooseAMatch.insets = new Insets(5, 45, 5, 5);
		gbc_lblChooseAMatch.gridx = 0;
		gbc_lblChooseAMatch.gridy = 0;
		contentPane.add(lblChooseAMatch, gbc_lblChooseAMatch);
		
		setMatchLabels();

		setAlwaysOnTop(true);
		setVisible(true);

	}
	/**
	 * Method which updates the Match JLabels in the Label Array
	 */
	public void setMatchLabels(){
		ArrayList<OpposingTeam> opposingTeams = currentStadium.matchesDisplay();
		matchJLabels = new JLabel[opposingTeams.size()];
		matchJButtons = new JButton[opposingTeams.size()];
		teamStats = new JTextArea[opposingTeams.size()];
		for (int i = 0, j = 2; i < opposingTeams.size() && j < opposingTeams.size() * 2 + 2; i++, j+=2){
			matchJLabels[i] = new JLabel(opposingTeams.get(i).getName());
			matchJLabels[i].setHorizontalAlignment(JLabel.LEADING);
			matchJLabels[i].setPreferredSize(new Dimension(140, 10));
			GridBagConstraints gbc_lblEnemyTeam = new GridBagConstraints();
			gbc_lblEnemyTeam.gridwidth = 1;
			gbc_lblEnemyTeam.insets = new Insets(10, 0, 10, 5);
			gbc_lblEnemyTeam.gridx = 0;
			gbc_lblEnemyTeam.gridy = j;
			contentPane.add(matchJLabels[i], gbc_lblEnemyTeam);

			int[] teamStrength = opposingTeams.get(i).getTeamStrength();

			String statText = "Attack: " + teamStrength[0]+ "\nDefence: " + teamStrength[1] + "\nTotal HP: " + teamStrength[2];

			teamStats[i] = new JTextArea(statText);
			teamStats[i].setEditable(false);
			teamStats[i].setOpaque(false);
			GridBagConstraints gbc_teamStats = new GridBagConstraints();
			gbc_teamStats.gridwidth = 1;
			gbc_teamStats.insets = new Insets(10, 0, 10, 5);
			gbc_teamStats.gridx = 1;
			gbc_teamStats.gridy = j;
			contentPane.add(teamStats[i], gbc_teamStats);

			matchJButtons[i] = new JButton("Play");
			GridBagConstraints gbc_btnPlay = new GridBagConstraints();
			gbc_btnPlay.insets = new Insets(10, 0, 10, 5);
			gbc_btnPlay.gridx = 3;
			gbc_btnPlay.gridy = j;
			final int index = i;
			matchJButtons[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String[] matchString;
					try {
						matchString = currentStadium.playMatch(currentPlayer, index);
						new MatchDetailGUI(matchString);
						setVisible(false);
						dispose();
					} catch (CannotPlayMatchException cannotPlayMatchException) {
						JOptionPane.showMessageDialog(contentPane, cannotPlayMatchException.getMessage(), "An error has occured.", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			contentPane.add(matchJButtons[i], gbc_btnPlay);
			
		}
	}

}
