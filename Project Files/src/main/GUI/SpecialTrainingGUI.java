package main.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Teams.PlayerTeam;
import main.Stadium;

import java.awt.GridBagLayout;
import javax.swing.JSplitPane;
import java.awt.GridBagConstraints;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;

import java.awt.Insets;
import java.awt.event.*;

import javax.naming.directory.InvalidAttributesException;
import javax.swing.AbstractListModel;
import java.awt.Font;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.JLabel;

/**
 * GUI Class for the Special Training screen; extends JFrame.
*/
public class SpecialTrainingGUI extends JFrame {
	/**Conent Pane for the Special Training GUI */
	private JPanel contentPane;
	/**Instance of PlayerTeam class */
	PlayerTeam player;
	/**Array of the Athlete Names in the player team */
	String[] athleteNames;
	/**Instance of the Stadium Logic Class */
	Stadium currentStadium;
	/** Swing Timer to wait until sending ActionEvent  */
	Timer waitTimer;
	/** Number of loops the waitTimer has been through */
	int timerLoops;
	/**Boolean for if the Special Training button has been pressed */
	boolean buttonPressed;

	/**
	 * Constructor, needs a PlayerTeam input and a Stadium input
	 * @param playerTeam current PlayerTeam class instance, used to see which athletes are in the team
	 * @param stadium current Stadium class instance, will call the special training function
	 */
	public SpecialTrainingGUI(PlayerTeam playerTeam, Stadium stadium) {
		player = playerTeam;
		currentStadium = stadium;
		buttonPressed = false;
		initalize();
	}
	/** Initializes the frame, private method */
	void initalize(){
		athleteNames = new String[player.getAllTeam().length];
		for (int i = 0; i < player.getAllTeam().length; i++){
			athleteNames[i] = player.getTeamMember(i).toString();
		}

		waitTimer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(timerLoops > 1){
					waitTimer.stop();
					try {
						new GameEnvironmentGUI();
					} catch (InvalidAttributesException invalidAttributesException) {
						JOptionPane.showMessageDialog(contentPane, invalidAttributesException.getMessage(), "An error has occured", JOptionPane.ERROR_MESSAGE);
					}
					dispose();
				}
				timerLoops++;
			}
			
		});

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblWelcome = new JLabel("Welcome to Special Training");
		GridBagConstraints gbc_lblWelcome = new GridBagConstraints();
		gbc_lblWelcome.gridwidth = 8;
		gbc_lblWelcome.insets = new Insets(0, 45, 5, 5);
		gbc_lblWelcome.gridx = 1;
		gbc_lblWelcome.gridy = 0;
		contentPane.add(lblWelcome, gbc_lblWelcome);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut.gridx = 2;
		gbc_verticalStrut.gridy = 0;
		contentPane.add(verticalStrut, gbc_verticalStrut);
		
		JList athleteList = new JList();
		athleteList.setForeground(Color.WHITE);
		athleteList.setBackground(Color.DARK_GRAY);
		athleteList.setFont(new Font("SansSerif", Font.PLAIN, 16));
		athleteList.setModel(new AbstractListModel() {
			String[] values = athleteNames;
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		GridBagConstraints gbc_athleteList = new GridBagConstraints();
		gbc_athleteList.gridwidth = 6;
		gbc_athleteList.insets = new Insets(0, 0, 5, 5);
		gbc_athleteList.fill = GridBagConstraints.BOTH;
		gbc_athleteList.gridx = 0;
		gbc_athleteList.gridy = 1;
		contentPane.add(athleteList, gbc_athleteList);
		
		JTextPane txtpnCurrentStats = new JTextPane();
		txtpnCurrentStats.setText("Current Stats:");
		GridBagConstraints gbc_txtpnCurrentStats = new GridBagConstraints();
		gbc_txtpnCurrentStats.gridwidth = 10;
		gbc_txtpnCurrentStats.insets = new Insets(0, 0, 5, 0);
		gbc_txtpnCurrentStats.fill = GridBagConstraints.BOTH;
		gbc_txtpnCurrentStats.gridx = 6;
		gbc_txtpnCurrentStats.gridy = 1;
		contentPane.add(txtpnCurrentStats, gbc_txtpnCurrentStats);
		
		JButton btnApplySpecialTraining = new JButton("Specially Train");
		btnApplySpecialTraining.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!buttonPressed){
					String newStats = currentStadium.specialTraining(player, athleteList.getSelectedIndex());
					txtpnCurrentStats.setText(newStats);
					buttonPressed = true;
					waitTimer.start();
				}
			}
			
		});
		GridBagConstraints gbc_btnApplySpecialTraining = new GridBagConstraints();
		gbc_btnApplySpecialTraining.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnApplySpecialTraining.gridwidth = 6;
		gbc_btnApplySpecialTraining.insets = new Insets(0, 0, 0, 5);
		gbc_btnApplySpecialTraining.gridx = 0;
		gbc_btnApplySpecialTraining.gridy = 2;
		contentPane.add(btnApplySpecialTraining, gbc_btnApplySpecialTraining);

		MouseListener mouseListen = new MouseAdapter() {
			public void mouseClicked(MouseEvent mouseEvent) {
			  JList evtList = (JList) mouseEvent.getSource();
			  if (mouseEvent.getClickCount() == 2) {
				int index = evtList.locationToIndex(mouseEvent.getPoint());
				if (index >= 0) {
					String currentString = "Current Stats: \n" + player.getTeamMember(index).getDescription();
					txtpnCurrentStats.setText(currentString);
				}
			  }
			}
		};

		athleteList.addMouseListener(mouseListen);
		setVisible(true);
		setAutoRequestFocus(true);
		setAlwaysOnTop(true);
	}

}
