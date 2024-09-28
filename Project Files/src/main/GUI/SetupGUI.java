package main.GUI;

import java.awt.*;
import java.awt.event.*;

import javax.management.InvalidAttributeValueException;
import javax.swing.*;
import javax.swing.event.*;

import main.*;

/**
 * GUI Class for initial game setup; extends JFrame.
*/
public class SetupGUI extends JFrame{
	/**
	 * The current GUIJFrame
	 */
	private JFrame setupGUIJFrame;
	/**Text field to enter your teams name */
	private JTextField txtEnterYourTeams;
	/**Current instance of the GameSetup class */
	private static GameSetup gameEnvironment;
	/**The Label for the Game Length Slider */
	JLabel sliderLabel;
	/**TextField for the user to enter a seed */
	JTextField txtEnterSeed;

	/**
	 * Create the application. Calls initialize private method
	 */
	public SetupGUI() {
		initialize();
	}
	/**
	 * Static method to set the GameSetup class to be used by this class
	 * @param environment
	 */
	public static void setGameEnvironment(GameSetup environment){
		gameEnvironment = environment;
	}

	/**
	 * Change the GUI visibility
	 * @param visibility Boolean whether it is visible
	 */
	@Override
	public void setVisible(boolean visibility){
		setupGUIJFrame.setVisible(visibility);
	}

	/**
	 * Initialize the contents of the frame. Private Method
	 */
	private void initialize() {
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 0, 10, 0);
		setupGUIJFrame = new JFrame();
		setupGUIJFrame.setTitle("SENG Soccer Management Sim 2023");
		setupGUIJFrame.setBounds(100, 100, 450, 304);
		setupGUIJFrame.setMinimumSize(new Dimension(350, 350));
		setupGUIJFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setupGUIJFrame.getContentPane().setLayout(layout);
		
		JLabel welcomeLbl = new JLabel("Welcome to the game!!");
		Font currentFont = welcomeLbl.getFont();
		welcomeLbl.setFont(new Font(currentFont.getName(), Font.BOLD, 16));
		//lblNewLabel.setBounds(95, 12, 270, 15);
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.gridheight = 2;
		setupGUIJFrame.getContentPane().add(welcomeLbl, gridBagConstraints);
		
		JLabel lblTeamName = new JLabel("Enter a name for your team:");
		gridBagConstraints.gridx=0;
		gridBagConstraints.gridy=2;
		gridBagConstraints.gridwidth = 1;
		//lblWhatWouldYou.setSize(30, 30);
		setupGUIJFrame.getContentPane().add(lblTeamName,gridBagConstraints);

		txtEnterYourTeams = new JTextField();
		txtEnterYourTeams.setText("(3-15 characters)");
		gridBagConstraints.gridx=3;
		gridBagConstraints.gridy=2;
		setupGUIJFrame.getContentPane().add(txtEnterYourTeams,gridBagConstraints);
		txtEnterYourTeams.setColumns(10);
		
		JLabel lblSelectDifficulty = new JLabel("Select difficulty:");
		gridBagConstraints.gridx=0;
		gridBagConstraints.gridy=4;
		gridBagConstraints.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridwidth = 4;
		setupGUIJFrame.getContentPane().add(lblSelectDifficulty, gridBagConstraints);

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Normal", "Hard"}));
		//comboBox.setSize(50, 25);
		gridBagConstraints.gridx=0;
		gridBagConstraints.gridy=6;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.insets = new Insets(0, 0, 10, 0);
		gridBagConstraints.gridwidth = 4;
		setupGUIJFrame.getContentPane().add(comboBox, gridBagConstraints);

		JLabel lblSetTheSeason = new JLabel("Set season length:");
		//lblSetTheSeason.setSize(179, 15);
		gridBagConstraints.gridx=0;
		gridBagConstraints.gridy=8;
		gridBagConstraints.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridwidth = 4;
		setupGUIJFrame.getContentPane().add(lblSetTheSeason, gridBagConstraints);

		JSlider slider = new JSlider();
		slider.setValue(10);
		slider.setMaximum(15);
		slider.setMinimum(5);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e){
				sliderLabel.setText(String.format("%02d", slider.getValue()) + " weeks");
			}
		});
		gridBagConstraints.gridx=0;
		gridBagConstraints.gridy=10;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints.gridwidth = 4;
		setupGUIJFrame.getContentPane().add(slider, gridBagConstraints);

		sliderLabel = new JLabel();
		sliderLabel.setText(String.format("%02d", slider.getValue()) + " weeks");

		gridBagConstraints.gridx=0;
		gridBagConstraints.gridy=12;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.insets = new Insets(0, 137, 10, 0);
		gridBagConstraints.gridwidth = 1;
		setupGUIJFrame.getContentPane().add(sliderLabel, gridBagConstraints);

		JLabel seedLabel = new JLabel("Enter RNG seed (optional):");
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy=14;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.insets = new Insets(10, 0, 10, 0);
		setupGUIJFrame.getContentPane().add(seedLabel, gridBagConstraints);

		txtEnterSeed = new JTextField("");
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 14;
		setupGUIJFrame.getContentPane().add(txtEnterSeed, gridBagConstraints);
		txtEnterSeed.setColumns(10);

		JButton btnConfirmSetting = new JButton("Start!!");
		btnConfirmSetting.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				try{
					gameEnvironment.setTeamName(txtEnterYourTeams.getText());
					gameEnvironment.setDifficulty(comboBox.getSelectedItem().toString());
					
					gameEnvironment.setLength(slider.getValue());
					if(!checkIfInteger(txtEnterSeed.getText())){
						if(txtEnterSeed.getText().equalsIgnoreCase(""))
							gameEnvironment.setSeed();
						else if(txtEnterSeed.getText().length() == 0)
							gameEnvironment.setSeed();
						else
							throw new InvalidAttributeValueException("RNG seed must be an integer or left blank.");
					}
					else
						gameEnvironment.setSeed(Integer.valueOf(txtEnterSeed.getText()));
					gameEnvironment.createPlayerTeam();
					gameEnvironment.buyStartingAthletes();
					Stadium.setTotalWeeks(slider.getValue());
					Stadium.genTeamsAtStart();
					setupGUIJFrame.setVisible(false);
					setupGUIJFrame.dispose();
				}
				catch(Exception exception){
					JOptionPane.showMessageDialog(setupGUIJFrame.getContentPane(), exception.getMessage(), "An error has occured.", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 16;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.insets = new Insets(10, 0, 10, 0);
		setupGUIJFrame.getContentPane().add(btnConfirmSetting, gridBagConstraints);
	}

	/**
	 * Check if player's input string is an integer.
	 * @param stringToCheck		Player input as a String.
	 * @return					True if input is a valid int, else False.
	 */
	boolean checkIfInteger(String stringToCheck){
		try {
			Integer.parseInt(stringToCheck);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
