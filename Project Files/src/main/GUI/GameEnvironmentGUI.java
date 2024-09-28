package main.GUI;

import java.awt.*;

import javax.naming.directory.InvalidAttributesException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import main.Environment;
import main.GameSetup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * GUI Class for the main environment screen; extends JFrame.
*/
public class GameEnvironmentGUI extends JFrame {
	/**
	 * The Conent Pane for the GUI JFrame
	 */
	private JPanel contentPane;
	/**
	 * The current games Environment logic class, statically typed as will not change throughout game
	 */
	static Environment currentEnvironment;

	/**
	 * Constructor, called when static type currentEnvironment previously set
	 * @throws InvalidAttributesException if currentEnvironment has not been previously set
	 */
	public GameEnvironmentGUI() throws InvalidAttributesException{
		if (currentEnvironment == null){
			throw new InvalidAttributesException("GameEnvironment Variable not set");
		}
		initialize();
	}

	/**
	 * Initial Constructor, calls sub method to initialize the frame
	 * @param gameEnvironment The current instance of the Game Environment class
	 */
	public GameEnvironmentGUI(Environment gameEnvironment) {
		currentEnvironment = gameEnvironment;
		initialize();
	}
	/**
	 * Initializes the Environment GUI, private method, called on Construction of GameEnvironmentGUI object
	 */
	void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		if(Environment.getCurrentWeek() <= Environment.getTotalWeeks())
			setVisible(true);
		
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblPrompt = new JLabel("What would you like to do?");
		lblPrompt.setFont(new Font("Dialog", Font.BOLD, 16));
		GridBagConstraints gbc_lblPrompt = new GridBagConstraints();
		gbc_lblPrompt.insets = new Insets(01, 0, 5, 5);
		gbc_lblPrompt.gridx = 5;
		gbc_lblPrompt.gridy = 0;
		contentPane.add(lblPrompt, gbc_lblPrompt);
		
		JLabel lblCurrentWeek = new JLabel("Current Week: " + currentEnvironment.getCurrentWeek() + " | Current Points: " + currentEnvironment.getPlayer().getPoints());
		lblCurrentWeek.setFont(new Font("Dialog", Font.PLAIN, 12));
		GridBagConstraints gbc_lblCurrentWeek = new GridBagConstraints();
		gbc_lblCurrentWeek.insets = new Insets(3, 0, 5, 5);
		gbc_lblCurrentWeek.gridx = 5;
		gbc_lblCurrentWeek.gridy = 2;
		contentPane.add(lblCurrentWeek, gbc_lblCurrentWeek);
		
		JButton btnVisitShop = new JButton("Visit Shop");
		btnVisitShop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				currentEnvironment.chooseAction(1);
				dispose();
			}
		});
		btnVisitShop.setPreferredSize(new Dimension(135, 25));
		GridBagConstraints gbc_btnVisitShop = new GridBagConstraints();
		gbc_btnVisitShop.insets = new Insets(10, 5, 5, 5);
		gbc_btnVisitShop.gridx = 5;
		gbc_btnVisitShop.gridy = 4;
		contentPane.add(btnVisitShop, gbc_btnVisitShop);
		
		JButton btnVisitClub = new JButton("Visit Club");
		btnVisitClub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				currentEnvironment.chooseAction(2);
				dispose();
			}
		});
		
		GridBagConstraints gbc_btnVisitClub = new GridBagConstraints();
		gbc_btnVisitClub.insets = new Insets(10, 5, 5, 5);
		gbc_btnVisitClub.gridx = 5;
		gbc_btnVisitClub.gridy = 6;
		btnVisitClub.setPreferredSize(new Dimension(135, 25));
		contentPane.add(btnVisitClub, gbc_btnVisitClub);
		
		JButton btnVisitStadium = new JButton("Visit Stadium");
		btnVisitStadium.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				currentEnvironment.chooseAction(3);
				dispose();
			}
		});
		btnVisitStadium.setPreferredSize(new Dimension(135, 25));
		GridBagConstraints gbc_btnVisitStadium = new GridBagConstraints();
		gbc_btnVisitStadium.insets = new Insets(10, 5, 0, 5);
		gbc_btnVisitStadium.gridx = 5;
		gbc_btnVisitStadium.gridy = 8;
		contentPane.add(btnVisitStadium, gbc_btnVisitStadium);
		
	}

}
