package main.GUI;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.SwingConstants;
import main.*;
import main.GUI.ActionListeners.ViewDetailAction;
import main.Purchaseables.Athlete;
import main.Purchaseables.Purchasable;
import main.Teams.PlayerTeam;

/**
 * GUI Class for the Shop screen; extends JFrame.
*/
public class ShopGUI extends JFrame{
	/**
	 * The JFrame for the Shop GUI main menu 
	 */
	private JFrame shopGUIJFrm;
	/**
	 * The JFrame for the purchase dedicated UI
	 */
	private JFrame buyUI;
	/**Instance of the Shop logic class */
	private static Shop currentShop;
	/** The current stock type being bough or sold, Default Value is "Not Set"*/
	static String stockType = "Not Set";
	/**Integer defined with default value 6, delcared at class level to make effectively final */
	int j = 6;
	/**Stock Labels Array */
	static JLabel[] stockLabels;
	/**View Detail button array */
	static JButton[] detailButtons;
	/**
	 * The JFrame for the selling dedicated UI
	 */
	JFrame sellUI;
	/**Label for the top of the Window */
	JLabel topLabel;
	/**Instance of the PlayerTeam class */
	static PlayerTeam player;
	/**Boolean of whether reserves are selected or not */
	static boolean reserves;
	/**The current Game Environment logic class instance */
	static Environment currentGame;
	/**The label for the players money */
	static JLabel moneyLabel;

	/**
	 * Constructor, calls the initialize method
	 * @param shop Instance of the Shop logic class
	 * @param currentPlayer Instance of the current PlayerTeam class
	 * @param setup Instance of the current Environment class
	 */
	public ShopGUI(Shop shop, PlayerTeam currentPlayer, Environment setup) {
		currentShop = shop;
		player = currentPlayer;
		currentGame = setup;
		initialize();
	}
	/**
	 * @override Overriden Method from {@link JFrame}
	 * @param visibility Boolean of whether visible or not
	 */
	public void setVisible(boolean visibility){
		shopGUIJFrm.setVisible(visibility);
	}

	/**
	 * Initialize the contents of the frame. Private Method
	 */
	private void initialize() {
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 0, 10, 0);
		shopGUIJFrm = new JFrame();
		shopGUIJFrm.setTitle("SENG Soccer Management Sim 2023");
		shopGUIJFrm.setBounds(100, 100, 450, 300);
		shopGUIJFrm.setMinimumSize(new Dimension(450, 300));
		shopGUIJFrm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		shopGUIJFrm.getContentPane().setLayout(gbl);

		shopGUIJFrm.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e){
				if(!Shop.isBuyingReserves() && !Shop.isStartingAthletes()){
					new GameEnvironmentGUI(currentGame);
					shopGUIJFrm.setVisible(false);
					shopGUIJFrm.dispose();
				}
			}
		});
		
		JLabel lblWelcome = new JLabel("Welcome to the Shop");
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setFont(new Font("Dialog", Font.BOLD, 24));
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 6;
		gbc.gridheight = 2;
		shopGUIJFrm.getContentPane().add(lblWelcome, gbc);

		JLabel lblChoice = new JLabel("What would you like to do?");
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 6;
		gbc.gridheight = 2;
		gbc.insets = new Insets(10, 60, 10, 20);
		shopGUIJFrm.getContentPane().add(lblChoice, gbc);
		
		JButton btnPurchase = new JButton("Purchase");
		btnPurchase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initializeBuyUI(currentShop.getAthletesStock(), buyUI, false);
			}
		});
		btnPurchase.setPreferredSize(new Dimension(125, 25));
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(10, 0, 10, 30);
		shopGUIJFrm.getContentPane().add(btnPurchase, gbc);
		
		JButton btnSell = new JButton("Sell");
		btnSell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!Shop.isBuyingReserves() && !Shop.isStartingAthletes()){
					initializeBuyUI(Arrays.asList(player.getAllTeam()), sellUI, true);
				} else {
					JOptionPane.showMessageDialog(buyUI, "You don't have anything to sell yet! Buy some athletes first.", "Warning!", JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		btnSell.setPreferredSize(new Dimension(125, 25));
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		gbc.insets = new Insets(10, 30, 10, 0);
		shopGUIJFrm.getContentPane().add(btnSell, gbc);

	}

	/**
	 * Shows the buy or Sell UI for the player, accepts List of Athletes as well as the UIFrame to display these on
	 * 
	 * @param uiToShow The JFrame UI to show to the player
	 * @param athletes The Athlete list to show to the player
	 * @param selling Boolean of whether we are selling or not
	 */
	public void initializeBuyUI(List<Athlete> athletes, JFrame uiToShow, boolean selling){

		int largestArray = Math.max(athletes.size(), currentShop.getItemStock().size());
		j=6;
		detailButtons = new JButton[largestArray];
		stockLabels = new JLabel[largestArray];

		shopGUIJFrm.setVisible(false);
		shopGUIJFrm.dispose();
		
		createBuySellUI(uiToShow, largestArray, selling);
	}
	/**
	 * Statically set the purchase/sell type string
	 * @param purchaseString The string of the purchase/sell type
	 */
	public static void setPurchaseTypeString(String purchaseString){
		stockType = purchaseString;
	}
	/**
	 * Update the JLabels in the sell or the Buy UI
	 * @param selling Boolean of whether or not we are selling 
	 */
	public static void updateJLabels(boolean selling){
		if(stockType.equalsIgnoreCase("not set")){
			JOptionPane.showMessageDialog(null, "You need to pick either Item/Athletes to Buy/Sell", "An error has occured", JOptionPane.ERROR_MESSAGE);
		}
		if(Shop.isBuyingReserves()){
			stockType = "reserves";
			reserves = true;
		}
		for(int i = 0; i < stockLabels.length; i++){
			try {
				Purchasable currentPurchasable = new Athlete(player.getRandomGen());
				if(!selling){
					if(stockType.equalsIgnoreCase("items")){
						currentPurchasable = currentShop.getItemStock().get(i);
					}
					else{
						currentPurchasable = currentShop.getAthletesStock().get(i);
					}
				}
				else{
					if(stockType.equalsIgnoreCase("items")){
						currentPurchasable = player.getItem(i);
					}
					else if(stockType.equalsIgnoreCase("athletes") && !reserves){
						currentPurchasable = player.getAllTeam()[i];
					}
					else{
						currentPurchasable = player.getReserve(i);
					}
				}
				stockLabels[i].setText(currentPurchasable.getName());
				detailButtons[i].setVisible(true);
			} catch (IndexOutOfBoundsException outOfBounds) {
				stockLabels[i].setText("");
				detailButtons[i].setVisible(false);
			}
		}
		moneyLabel.setText("Current Money: $" + player.getMoney());
	}
	/**
	 * Creates the Buy / Sell UI
	 * @param uiFrame The UI Frame to add all the items to 
	 * @param itemSize The size of the Item Array (typically this will be the same or larger than the atheletes array)
	 * @param selling Boolean of whether we are selling or not
	 */
	public void createBuySellUI(JFrame uiFrame, int itemSize, boolean selling){
		if(!selling){
			uiFrame = new JFrame("SENG Soccer Management Sim 2023");
			uiFrame.setBounds(100, 100, 750, 500);
			uiFrame.setMinimumSize(new Dimension(750, 500));
			uiFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			buyUI = uiFrame;
			buyUI.setAlwaysOnTop(true);
			buyUI.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e){
					if(Shop.isStartingAthletes() || Shop.isBuyingReserves()){
						//new PopUpGUI("You need to purchase starting athletes before continuing", "Error", true);
						JOptionPane.showMessageDialog(buyUI, "You can't leave yet! You're not finished buying athletes.", "Warning!", JOptionPane.PLAIN_MESSAGE);
						return;
					}
					buyUI.setVisible(false);
					buyUI.dispose();
				}
			});
		}
		else{
			uiFrame = new JFrame("SENG Soccer Management Sim 2023");
			uiFrame.setBounds(100, 100, 750, 500);
			uiFrame.setMinimumSize(new Dimension(750, 500));
			uiFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			sellUI = uiFrame;
			sellUI.setAlwaysOnTop(true);
		}

		GridBagLayout gridBagLayout = new GridBagLayout();
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		uiFrame.setLayout(gridBagLayout);
		uiFrame.setAlwaysOnTop(true);
		if(!selling){
			topLabel = new JLabel("What would you like to purchase?");
		}
		else{
			topLabel = new JLabel("What would you like to sell?");
		}
		Font topLabelFont = topLabel.getFont();
		topLabel.setFont(new Font(topLabelFont.getName(), Font.BOLD, 14));
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 6;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.insets = new Insets(10, 10, 10, 0);
		uiFrame.getContentPane().add(topLabel, gridBagConstraints);
		uiFrame.setVisible(true);

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 6;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.insets = new Insets(2, 55, 2, 10);
		moneyLabel = new JLabel("Current Money: $" + player.getMoney());
		uiFrame.getContentPane().add(moneyLabel, gridBagConstraints);

		JButton btnLeave = new JButton("Main Menu");
		btnLeave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!Shop.isBuyingReserves() && !Shop.isStartingAthletes()){
					new GameEnvironmentGUI(currentGame);
					shopGUIJFrm.setVisible(false);
					shopGUIJFrm.dispose();
					if (!selling) {
						buyUI.setVisible(false);
						buyUI.dispose();
					} else {
						sellUI.setVisible(false);
						sellUI.dispose();
					}
				} else {
					JOptionPane.showMessageDialog(buyUI, "You can't leave yet! You're not finished buying athletes.", "Warning!", JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		btnLeave.setPreferredSize(new Dimension(125, 25));
		gridBagConstraints.gridx = -9;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.insets = new Insets(10,10,10,0);
		btnLeave.setPreferredSize(new Dimension(125, 25));
		uiFrame.getContentPane().add(btnLeave, gridBagConstraints);

		JButton itemButton = new JButton("Items");
		if(!selling){
			itemButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						if (Shop.isStartingAthletes() || Shop.isBuyingReserves())
							throw new Exception("You need to purchase athletes before buying items");
						stockType = "Items";
						ViewDetailAction.setStockString(stockType);
						updateJLabels(selling);
					} catch (Exception exception) {
						JOptionPane.showMessageDialog(buyUI, "You can't buy items yet! You're not finished buying athletes.", "Warning!", JOptionPane.PLAIN_MESSAGE);
						//new ErrorGUI(exception);
					}
				}
			});
		}
		else{
			itemButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						stockType = "Items";
						ViewDetailAction.setStockString(stockType);
						updateJLabels(selling);
					} catch (Exception exception) {
						JOptionPane.showMessageDialog(shopGUIJFrm.getContentPane(), exception.getMessage(), "An error has occured", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
		}
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.insets = new Insets(10,10,10,0);
		itemButton.setPreferredSize(new Dimension(125, 25));
		uiFrame.getContentPane().add(itemButton, gridBagConstraints);

		JButton athleteButton = new JButton("Athletes");
		athleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				if(Shop.isBuyingReserves()){
					stockType = "reserves";
					reserves = true;
				}
				else{
					stockType = "Athletes";
					reserves = false;
				}
				ViewDetailAction.setSellingReserves(false);
				ViewDetailAction.setStockString(stockType);
				updateJLabels(selling);
			}
		});
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.insets = new Insets(10,0,10,10);
		athleteButton.setPreferredSize(new Dimension(125, 25));
		uiFrame.getContentPane().add(athleteButton, gridBagConstraints);

		if(selling){
			JButton reservesButton = new JButton("Reserves");
			reservesButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					stockType = "Athletes";
					ViewDetailAction.setStockString(stockType);
					ViewDetailAction.setSellingReserves(true);
					reserves = true;
					updateJLabels(selling);
				}
				
			});
			gridBagConstraints.gridx = 2;
			gridBagConstraints.gridy = 4;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.gridheight = 2;
			gridBagConstraints.insets = new Insets(10,0,10,10);
			reservesButton.setPreferredSize(new Dimension(125, 25));
			uiFrame.getContentPane().add(reservesButton, gridBagConstraints);
		}

		// for loop to add all options available to purchase
		for (int i = 0; i < itemSize; i++){
			stockLabels[i] = new JLabel();
			stockLabels[i].setText("");
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = j;
			gridBagConstraints.gridwidth = 3;
			gridBagConstraints.gridheight = 2;
			gridBagConstraints.insets = new Insets(10, 10, 10, 10);
			uiFrame.getContentPane().add(stockLabels[i], gridBagConstraints);
			
			detailButtons[i] = new JButton("View Details");
			detailButtons[i].addActionListener(new ViewDetailAction(i, stockType, currentShop, selling, player, reserves));
			gridBagConstraints.gridx = 4;
			gridBagConstraints.gridy = j;
			gridBagConstraints.gridwidth = 1;
			gridBagConstraints.gridheight = 2;
			gridBagConstraints.insets = new Insets(10, 10, 10, 10);
			detailButtons[i].setPreferredSize(new Dimension(125, 25));
			uiFrame.getContentPane().add(detailButtons[i], gridBagConstraints);
			j+=2;
		}
	}

}
