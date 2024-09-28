package main.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Environment;
import main.GameSetup;
import main.Stadium;
import main.GUI.ActionListeners.PopUpButtonActionListerner;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;
import java.awt.Component;
import java.awt.Dimension;

import javax.naming.directory.InvalidAttributesException;
import javax.swing.Box;
import javax.swing.JButton;

/**
 * GUI Class for the Stadium screen; extends {@link JFrame}.
*/
public class StadiumJFrame extends JFrame {
	/**The ConentPane for the StadiumJFrame */
	private JPanel contentPane;
	/**Instance of the Stadium Logic class */
	private Stadium currentStadium;
	/**Instance of the gameEnvironemnt Logic Class */
	Environment gameEnvironment;

	/**
	 * Creates the frame and sets the Constructor.
	 * @param Stadium The current stadium class instance 
	 * @param GameEnviro The current Game environment class instance
	 */
	public StadiumJFrame(Stadium stadium, Environment gameEnviro) {
		currentStadium = stadium;
		gameEnvironment = gameEnviro;
		setTitle("SENG Soccer Management Sim 2023");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblWelcome = new JLabel("Welcome to the Stadium");
		lblWelcome.setFont(new Font("Dialog", Font.BOLD, 16));
		GridBagConstraints gbc_lblWelcome = new GridBagConstraints();
		gbc_lblWelcome.gridwidth = 6;
		gbc_lblWelcome.insets = new Insets(0, 0, 5, 0);
		gbc_lblWelcome.gridx = 0;
		gbc_lblWelcome.gridy = 0;
		contentPane.add(lblWelcome, gbc_lblWelcome);
				
		JLabel lblCurrentWeek = new JLabel("Current Week: " + GameSetup.getCurrentWeek());
		GridBagConstraints gbc_lblCurrentWeek = new GridBagConstraints();
		gbc_lblCurrentWeek.insets = new Insets(0, 5, 5, 5);
		gbc_lblCurrentWeek.gridwidth = 2;
		gbc_lblCurrentWeek.gridx = 4;
		gbc_lblCurrentWeek.gridy = 2;
		contentPane.add(lblCurrentWeek, gbc_lblCurrentWeek);

		JButton btnTakeBye = new JButton("Take Bye");
		GridBagConstraints gbc_btnTakeBye = new GridBagConstraints();
		gbc_btnTakeBye.gridwidth = 2;
		gbc_btnTakeBye.insets = new Insets(20, 12, 20, 10);
		gbc_btnTakeBye.gridx = 4;
		gbc_btnTakeBye.gridy = 4;
		btnTakeBye.setPreferredSize(new Dimension(135, 25));
		btnTakeBye.addActionListener(new PopUpButtonActionListerner(this, stadium, gameEnviro.getPlayerTeam()));
		contentPane.add(btnTakeBye, gbc_btnTakeBye);
		
		JButton btnPlayMatch = new JButton("Play Match");
		GridBagConstraints gbc_btnPlayMatch = new GridBagConstraints();
		gbc_btnPlayMatch.gridwidth = 2;
		gbc_btnPlayMatch.insets = new Insets(20, 12, 20, 10);
		gbc_btnPlayMatch.gridx = 4;
		gbc_btnPlayMatch.gridy = 6;
		btnPlayMatch.setPreferredSize(new Dimension(135, 25));
		btnPlayMatch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new MatchGUI(currentStadium, gameEnvironment.getPlayerTeam(), gameEnviro);
				setVisible(false);
				dispose();
			}
			
		});
		contentPane.add(btnPlayMatch, gbc_btnPlayMatch);
		
		JButton btnMainMenu = new JButton("Main Menu");
		btnMainMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					new GameEnvironmentGUI();
					setVisible(false);
					dispose();
				} catch (InvalidAttributesException invalidAttributesException) {
					JOptionPane.showMessageDialog(contentPane, invalidAttributesException.getMessage(), "An error has occured", 0);
				}
			}
			
		});
		GridBagConstraints gbc_btnMainMenu = new GridBagConstraints();
		gbc_btnMainMenu.gridwidth = 2;
		gbc_btnMainMenu.insets = new Insets(20, 12, 20, 10);
		gbc_btnMainMenu.gridx = 4;
		gbc_btnMainMenu.gridy = 8;
		btnMainMenu.setPreferredSize(new Dimension(135, 25));
		contentPane.add(btnMainMenu, gbc_btnMainMenu);

		setVisible(true);

	}

}
