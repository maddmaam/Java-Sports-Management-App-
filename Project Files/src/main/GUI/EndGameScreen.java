package main.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Environment;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/**
 * End game screen class, shown at the games end, extends JFrame
 */

public class EndGameScreen extends JFrame {

	/**
	 * The contentPane of the EndGameScreen, all elements are added to this
	 */
	private JPanel contentPane;
	/**
	 * The current (and final) instance of the gameEnvironment Class
	 */
	Environment gameEnvironment;

	/**
	 * Constructor, needs a String to Display
	 * @param endGameString The string to show in the Text Pane at the end of the Game
	 */
	public EndGameScreen(String endGameString, String teamName ) {
		initialize(endGameString, teamName);
	}
	/**
	 * Initializes the End Game Window
	 * <p> This method is private
	 * @param textPaneString The text which is parsed through and shown on the JTextPane
	 * @param nameOfTeam The name the player set for the Team at the start of the game
	 */
	void initialize(String textPaneString, String nameOfTeam) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 305, 305);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 27, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut.gridx = 0;
		gbc_horizontalStrut.gridy = 0;
		contentPane.add(horizontalStrut, gbc_horizontalStrut);
		
		JLabel lblHeader = new JLabel("*** Game Complete! ***");
		lblHeader.setFont(new Font("DialogInput", Font.BOLD, 16));
		GridBagConstraints gbc_lblHeader = new GridBagConstraints();
		gbc_lblHeader.insets = new Insets(0, 0, 5, 5);
		gbc_lblHeader.gridwidth = 15;
		gbc_lblHeader.gridx = 2;
		gbc_lblHeader.gridy = 0;
		contentPane.add(lblHeader, gbc_lblHeader);
		
		JLabel lblTeamNameHere = new JLabel(nameOfTeam);
		GridBagConstraints gbc_lblTeamNameHere = new GridBagConstraints();
		gbc_lblTeamNameHere.insets = new Insets(0, 0, 5, 5);
		gbc_lblTeamNameHere.gridwidth = 5;
		gbc_lblTeamNameHere.gridx = 11;
		gbc_lblTeamNameHere.gridy = 1;
		contentPane.add(lblTeamNameHere, gbc_lblTeamNameHere);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_2 = new GridBagConstraints();
		gbc_horizontalStrut_2.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut_2.gridx = 2;
		gbc_horizontalStrut_2.gridy = 2;
		contentPane.add(horizontalStrut_2, gbc_horizontalStrut_2);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut_1 = new GridBagConstraints();
		gbc_horizontalStrut_1.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrut_1.gridx = 0;
		gbc_horizontalStrut_1.gridy = 3;
		contentPane.add(horizontalStrut_1, gbc_horizontalStrut_1);
		
		JTextPane txtpnTextPane = new JTextPane();
		txtpnTextPane.setEditable(false);
		txtpnTextPane.setText(textPaneString);
		GridBagConstraints gbc_txtpnTextPane = new GridBagConstraints();
		gbc_txtpnTextPane.gridwidth = 15;
		gbc_txtpnTextPane.insets = new Insets(0, 0, 5, 5);
		gbc_txtpnTextPane.fill = GridBagConstraints.BOTH;
		gbc_txtpnTextPane.gridx = 2;
		gbc_txtpnTextPane.gridy = 3;
		contentPane.add(txtpnTextPane, gbc_txtpnTextPane);
		
		JButton btnQuit = new JButton("End Game");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		GridBagConstraints gbc_btnQuit = new GridBagConstraints();
		gbc_btnQuit.insets = new Insets(0, 0, 0, 5);
		gbc_btnQuit.gridx = 16;
		gbc_btnQuit.gridy = 4;
		contentPane.add(btnQuit, gbc_btnQuit);
		setVisible(true);
		setResizable(false);
	}
}
