package main.GUI;

import javax.swing.*;

import main.Teams.PlayerTeam;
import main.*;
import main.GUI.ActionListeners.BuyButtonActionListener;
import main.GUI.ActionListeners.SellButtonActionListener;
import main.Purchaseables.Purchasable;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI Class for Athlete/Item purchase details; extends JFrame.
*/
public class PurchaseableDetailGUI extends JFrame {
    public JFrame detailGUIFrame;
    public JTextArea detailLabel;
    JButton buyButton;
    Purchasable purchase;
    static PlayerTeam currentPlayer;
    static boolean sellingReserves;


    public PurchaseableDetailGUI(int index, Shop currentShop, String itemType, boolean selling, PlayerTeam player, boolean resreves){
        currentPlayer = player;
        setSellingReserves(resreves);
        Initialize(index, currentShop, itemType, selling);
    }

    public void Initialize(int index, Shop currentShop, String itemType, boolean selling){
        if (itemType.equalsIgnoreCase("items") && !selling){
            purchase = currentShop.getItemStock().get(index);
        }
        else if (!selling){
            purchase = currentShop.getAthletesStock().get(index);
        }
        else{
            if (itemType.equalsIgnoreCase("items")){
                purchase = currentPlayer.getItem(index);
            }
            else if (!sellingReserves)
                purchase = currentPlayer.getTeamMember(index);
            else
                purchase = currentPlayer.getReserve(index);
        }
        detailGUIFrame = new JFrame();
        detailGUIFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        detailGUIFrame.setBounds(100, 100, 500, 300);
        if(itemType.equalsIgnoreCase("items")){
            detailGUIFrame.setMinimumSize(new Dimension(650, 300));
        }
        else{
            detailGUIFrame.setMinimumSize(new Dimension(500, 300));
        }
        detailGUIFrame.setResizable(false);
        detailGUIFrame.setTitle("View Details: " + purchase.getName());
        GridBagConstraints gbc = new GridBagConstraints();
        GridBagLayout gbl = new GridBagLayout();
        detailGUIFrame.setLayout(gbl);

        detailLabel = new JTextArea(purchase.getPurchaseDetails());
        detailLabel.setEditable(false);
        detailLabel.setOpaque(false);
        Font currentFont = detailLabel.getFont();
        detailLabel.setFont(new Font(currentFont.getName(), Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth=4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        detailGUIFrame.getContentPane().add(detailLabel, gbc);
        detailGUIFrame.setVisible(true);
        detailGUIFrame.setAlwaysOnTop(true);
        if(!selling){
            buyButton = new JButton("Buy");
            buyButton.addActionListener(new BuyButtonActionListener(currentShop, index, itemType, detailGUIFrame, currentPlayer));
        }
        else{
            buyButton = new JButton("Sell");
            buyButton.addActionListener(new SellButtonActionListener(index, itemType, currentShop, sellingReserves, detailGUIFrame));
        }
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 2;
        buyButton.setPreferredSize(new Dimension(150, 50));
        gbc.insets = new Insets(10, 50, 10, 50);
        detailGUIFrame.getContentPane().add(buyButton, gbc);

    }

    /**
     * Set sellingReseves boolean.
     * @param reservesSold      New value for sellingReseves.
    */
    public static void setSellingReserves(boolean reservesSold){
        sellingReserves = reservesSold;
    }

}
