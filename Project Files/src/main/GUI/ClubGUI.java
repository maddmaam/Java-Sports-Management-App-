package main.GUI;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.Club;
import main.Environment;
import main.Teams.PlayerTeam;
import main.GUI.ActionListeners.ClubHouseActionListener;
import main.Purchaseables.Athlete;
import main.Purchaseables.Item;
import main.Purchaseables.Purchasable;

import java.awt.GridBagLayout;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.naming.directory.InvalidAttributesException;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;

/**
 * GUI Class for the Club JFrame, extends the base JFrame class
*/
public class ClubGUI extends JFrame {

	/**
	 * The content Pane of the JFrame
	 */
	private JPanel contentPane;
	/**
	 * Current instance of the PlayerTeam class
	 */
	PlayerTeam currentPlayerTeam;
	/**
	 * A private list of the Athletes in the Team (will automatically switch to reserves)
	 */
	JList athleteList;
	/**
	 * A private list of the item in the players inventory
	 */
	JList itemList;
	/**
	 * The text pane which displays the athlete or item details
	 */
	JTextPane detailTextPane;
	/**
	 * The current Club logic instance
	 */
	Club currentClub;
	/**
	 * Swap button to swap the athletes (or when items being viewed to use item)
	 */
	JButton btnSwapAthletes;
	/**
	 * Button to move Athlete to reserves and vice versa
	 */
	JButton btnMoveToReserves;
	/**
	 * The number of times swap button has been clicked (resets after 2 or more clicks)
	 */
	int timesClickedForSwap = 0;
	/**
	 * The current position of the first athlete swap button was clicked on (in the athlete list)
	 */
	int positionOfAthlete1 = 0;
	/**
	 * Boolean to outline if we are looking at reserves, default value false
	 */
	boolean reserves = false;
	/**
	 * Boolean of whether we are viewing an item or an athlete, default value false
	 */
	boolean item = false;
	/**
	 * Current Environment (the Game Environment) class instance
	 */
	Environment currentGameEnvironment;
	/**
	 * The main menu button 
	 */
	private JButton btnMenu;
	/**
	 * The list model used to display items in the JList
	 */
	DefaultListModel<Item> itemListModel;

	/**
	 * Create the Frame
	 * @param player The current instance of the player class 
	 * @param club The current instance of the Club Logic class
	 * @param environment The current Game Environment class instance
	 */
	public ClubGUI(PlayerTeam player, Club club, Environment environment) {
		currentPlayerTeam = player;
		currentClub = club;
		currentGameEnvironment = environment;
		initialize();
		
	}
	/**
	 * Used to construct the frame, Private method
	 */
	void initialize(){
		setTitle("SENG Soccer Management Sim 2023");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 672, 266);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		contentPane.setLayout(gbl_contentPane);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event){
				new GameEnvironmentGUI(currentGameEnvironment);
				dispose();
			}
		});
		
		btnMenu = new JButton("Main Menu");
		btnMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ClubGUI.setVisible(false);
				// ClubGUI.dispose();

					try {
						new GameEnvironmentGUI();
						setVisible(false);
						dispose();
					} catch (InvalidAttributesException InvalidAttributesException) {
						JOptionPane.showMessageDialog(contentPane, InvalidAttributesException.getMessage(), "An error has occured.", JOptionPane.ERROR_MESSAGE);
					}
			}
		});
		
		JLabel lblClubhouse = new JLabel("Clubhouse");
		lblClubhouse.setFont(new Font("Dialog", Font.BOLD, 16));
		GridBagConstraints gbc_lblClubhouse = new GridBagConstraints();
		gbc_lblClubhouse.gridwidth = 4;
		gbc_lblClubhouse.insets = new Insets(0, 0, 5, 5);
		gbc_lblClubhouse.gridx = 9;
		gbc_lblClubhouse.gridy = 0;
		contentPane.add(lblClubhouse, gbc_lblClubhouse);
		GridBagConstraints gbc_btnMenu = new GridBagConstraints();
		gbc_btnMenu.insets = new Insets(0, 0, 5, 5);
		gbc_btnMenu.gridx = 16;
		gbc_btnMenu.gridy = 0;
		contentPane.add(btnMenu, gbc_btnMenu);
		
		JButton btnInv = new JButton("View Inventory");
		GridBagConstraints gbc_btnInv = new GridBagConstraints();
		gbc_btnInv.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnInv.gridwidth = 5;
		gbc_btnInv.insets = new Insets(0, 0, 5, 5);
		gbc_btnInv.gridx = 0;
		gbc_btnInv.gridy = 2;
		btnInv.addActionListener(new ClubHouseActionListener(1, this));
		contentPane.add(btnInv, gbc_btnInv);
		
		JButton btnViewCurrentTeam = new JButton("View Main Team");
		GridBagConstraints gbc_btnViewCurrentTeam = new GridBagConstraints();
		gbc_btnViewCurrentTeam.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnViewCurrentTeam.gridwidth = 5;
		gbc_btnViewCurrentTeam.insets = new Insets(0, 0, 5, 5);
		gbc_btnViewCurrentTeam.gridx = 5;
		gbc_btnViewCurrentTeam.gridy = 2;
		btnViewCurrentTeam.addActionListener(new ClubHouseActionListener(2, this));
		contentPane.add(btnViewCurrentTeam, gbc_btnViewCurrentTeam);

		
		
		JButton btnViewReserves = new JButton("View Reserves");
		GridBagConstraints gbc_btnViewReserves = new GridBagConstraints();
		gbc_btnViewReserves.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnViewReserves.gridwidth = 8;
		gbc_btnViewReserves.insets = new Insets(0, 0, 5, 0);
		gbc_btnViewReserves.gridx = 10;
		gbc_btnViewReserves.gridy = 2;
		btnViewReserves.addActionListener(new ClubHouseActionListener(3, this));
		contentPane.add(btnViewReserves, gbc_btnViewReserves);

		DefaultListModel<Athlete> athleteListModel = new DefaultListModel<>();
		athleteListModel.addAll(Arrays.asList(currentPlayerTeam.getAllTeam()));
		
		athleteList = new JList<>(athleteListModel);
		athleteList.setFont(new Font("SansSerif", Font.PLAIN, 12));
		athleteList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel.gridwidth = 9;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 3;
		contentPane.add(athleteList, gbc_lblNewLabel);
		
		itemList = new JList<>();
		itemList.setFont(new Font("SansSerif", Font.PLAIN, 12));
		GridBagConstraints gbc_itemList = new GridBagConstraints();
		gbc_itemList.fill = GridBagConstraints.HORIZONTAL;
		gbc_itemList.gridwidth = 9;
		gbc_itemList.insets = new Insets(0, 0, 5, 5);
		gbc_itemList.gridx = 0;
		gbc_itemList.gridy = 3;
		contentPane.add(itemList, gbc_itemList);
		itemList.setVisible(false);
		
		detailTextPane = new JTextPane();
		detailTextPane.setText("\n\n\n\n\n");
		detailTextPane.setEditable(false);
		GridBagConstraints gbc_detailTextPane = new GridBagConstraints();
		gbc_detailTextPane.fill = GridBagConstraints.HORIZONTAL;
		gbc_detailTextPane.insets = new Insets(0, 0, 5, 0);
		gbc_detailTextPane.gridwidth =9;
		gbc_detailTextPane.gridx = 9;
		gbc_detailTextPane.gridy = 3;
		contentPane.add(detailTextPane, gbc_detailTextPane);
		
		JButton detailButton = new JButton("View Details");
		GridBagConstraints gbc_detailButton = new GridBagConstraints();
		gbc_detailButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_detailButton.gridwidth = 5;
		gbc_detailButton.insets = new Insets(0, 0, 0, 5);
		gbc_detailButton.gridx = 5;
		gbc_detailButton.gridy = 4;
		detailButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					
					if(athleteList.isVisible()) {
						if(athleteList.isSelectionEmpty())
							throw new InvalidAttributesException("You need to pick an item or athlete to view.");
						Athlete selectedAthlete;
						if(reserves){
							selectedAthlete = currentPlayerTeam.getReserve(athleteList.getSelectedIndex());
						} else{
							selectedAthlete = currentPlayerTeam.getTeamMember(athleteList.getSelectedIndex());
						}
						updateJTextPane(selectedAthlete.getDescription());
					}
					else {
						if(itemList.isSelectionEmpty())
							throw new InvalidAttributesException("You need to pick an item or athlete to view.");
						Item selectedItem = currentPlayerTeam.getInv().get(itemList.getSelectedIndex());
						updateJTextPane(selectedItem.getDescription());
					}
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(contentPane, exception.getMessage(), "An error has occured.", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		btnSwapAthletes = new JButton("Swap");
		GridBagConstraints gbc_btnSwapAthletes = new GridBagConstraints();
		gbc_btnSwapAthletes.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSwapAthletes.gridwidth = 5;
		gbc_btnSwapAthletes.insets = new Insets(0, 0, 0, 5);
		gbc_btnSwapAthletes.gridx = 0;
		gbc_btnSwapAthletes.gridy = 4;
		btnSwapAthletes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(item){
					int selected = itemList.getSelectedIndex();
					if (currentPlayerTeam.getInv().size() > 0 && selected >= 0) {
						
						new UseItemGUI(currentPlayerTeam, currentPlayerTeam.getItem(selected));
						itemListModel.remove(selected);
						contentPane.validate();
					} else {
						JOptionPane.showMessageDialog(contentPane, "You need to select an item first!", "An error has occured.", JOptionPane.ERROR_MESSAGE);
					}
				}
				else if(++timesClickedForSwap >= 2 && positionOfAthlete1 != -1){
					int currentSlot = athleteList.getSelectedIndex();
					if(reserves){
						currentClub.moveSlots(currentSlot, positionOfAthlete1, true);
						updateJList(3);
						timesClickedForSwap = 0;
					}
					else{
						currentClub.moveSlots(positionOfAthlete1, currentSlot, false);
						updateJList(2);
						timesClickedForSwap = 0;
					}
				}
				else
					positionOfAthlete1 = athleteList.getSelectedIndex();

				

			}
			
		});
		contentPane.add(btnSwapAthletes, gbc_btnSwapAthletes);
		contentPane.add(detailButton, gbc_detailButton);
		
		btnMoveToReserves = new JButton("Swap to Reserves");
		GridBagConstraints gbc_btnMoveToReserves = new GridBagConstraints();
		gbc_btnMoveToReserves.gridwidth = 8;
		gbc_btnMoveToReserves.gridx = 10;
		gbc_btnMoveToReserves.gridy = 4;
		contentPane.add(btnMoveToReserves, gbc_btnMoveToReserves);
		setMinimumSize(new Dimension(500,250));

		btnMoveToReserves.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!athleteList.isSelectionEmpty()){
					int index = athleteList.getSelectedIndex();
					Athlete toSwap = currentPlayerTeam.getTeamMember(index);
					currentClub.moveToReserves(index, toSwap);
					if(reserves) updateJList(3); 
					else updateJList(2);
				}
				else{
					JOptionPane.showMessageDialog(contentPane, "You need to select an athlete first!", "An error has occured.", JOptionPane.ERROR_MESSAGE);
				}
			}
			
		});
		setVisible(true);
	}

	/**
	 * Used to change the JList values within the club GUIFrame, for when the List itself is updated
	 * @param index The index of the item or Athlete which has been removed/changed in the list and needs updating
	 */
	public void updateJList(int index) {
		if (index == 1){
			athleteList.setVisible(false);
			if(itemList != null){
				itemList.setVisible(false);
			} else{
				itemList = new JList<>();
			}
			itemListModel = new DefaultListModel<>();
			itemListModel.addAll(currentPlayerTeam.getInv());
			itemList.setModel(itemListModel);
			itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			itemList.setVisible(true);
			btnMoveToReserves.setVisible(false);
			btnSwapAthletes.setText("Use Item");
			item = true;
		}
		else if (index == 2){
			athleteList.setVisible(false);
			if(itemList != null){
				itemList.setVisible(false);
			}
			DefaultListModel<Athlete> athleteListModel = new DefaultListModel<>();
			athleteListModel.addAll(Arrays.asList(currentPlayerTeam.getAllTeam()));
			athleteList.setModel(athleteListModel);
			athleteList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			btnMoveToReserves.setVisible(true);
			athleteList.setVisible(true);
			btnSwapAthletes.setText("Swap");
			btnMoveToReserves.setText("Swap to Reserves");
			reserves = false;
			item = false;
		}
		else if (index == 3){
			athleteList.setVisible(false);
			if(itemList != null){
				itemList.setVisible(false);
			}
			DefaultListModel<Athlete> athleteListModel = new DefaultListModel<>();
			athleteListModel.addAll(Arrays.asList(currentPlayerTeam.getAllReserves()));
			athleteList.setModel(athleteListModel);
			athleteList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			athleteList.setVisible(true);
			btnMoveToReserves.setText("Move to Main Team");
			btnMoveToReserves.setVisible(true);
			reserves = true;
			item = false;
		}
	}

	/**
	 * Update the detail information text pane
	 * @param stringToUpdate The string which will be displayed on the view detail text pane
	 */
	public void updateJTextPane(String stringToUpdate) {
		detailTextPane.setText(stringToUpdate);
	}

}
