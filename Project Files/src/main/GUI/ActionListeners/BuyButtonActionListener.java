package main.GUI.ActionListeners;
import main.GUI.*;
import main.*;
import main.Teams.PlayerTeam;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


/**
 * @author Madison Reilly (jre129)
 * @author David Williamson (dwi79)
 * Action Listener class for the Buy Button. Implements the Java Awt Event ActionListener
 */
public class BuyButtonActionListener implements ActionListener{

    /**
     * Index of the selected athlete that details are being viewed for
     */
    int index;
    /**
     * Current instance of the shop, used to purchase athlete
     */
    Shop currentShop;
    /**
     * The Type of purchase that will be made - static through all instances of this class
     */
    static String purchaseString;
    /**
     * The current view detail GUI frame
     */
    JFrame currentGUI;
    /**
     * The players current Team (class instance)
     */
    PlayerTeam currentPlayer;


    /**
     * Constructor, used to set all the variables in the Class. Does not call any methods
     * @param shop The current instance of the shop class
     * @param indexNum The index of the athlete being viewed
     * @param pString The current purchase type string
     * @param detailGUIFrame The current Athlete or Item detail frame instance
     * @param player The current instance of the PlayerTeam class
     */
    public BuyButtonActionListener(Shop shop, int indexNum, String pString, JFrame detailGUIFrame, PlayerTeam player){
        index = indexNum;
        currentShop = shop;
        purchaseString = pString;
        currentGUI = detailGUIFrame;
        currentPlayer = player;
    }


    /**
     * @Override
     * @param arg0 The actionevent argument parsed when an action is performed
     */
    public void actionPerformed(ActionEvent arg0) {
        if(Shop.isBuyingReserves())
            currentShop.purchase(index, "reserves");
        else if(Shop.isStartingAthletes())
            currentShop.purchase(index, purchaseString);
        else if (currentPlayer.getFreeAthleteSlot() == -1 && currentPlayer.getFreeReserveSlot() != -1){
            currentShop.setSlotToReplace(currentPlayer.getFreeReserveSlot());
            currentShop.purchase(index, "reserves");
        }
        else if (currentPlayer.getFreeReserveSlot() == -1 && currentPlayer.getFreeAthleteSlot() != -1){
            currentShop.setSlotToReplace(currentPlayer.getFreeAthleteSlot());
            currentShop.purchase(index, purchaseString);
        }    
        else
            currentShop.purchase(index, purchaseString);
        
        ShopGUI.updateJLabels(false);
        currentGUI.setVisible(false);
        if(Shop.getStartingAthletesPosition() > 4 && currentPlayer.getAllReserves()[0].isDummy()){
            Shop.setBuyingReserves();

            JOptionPane.showMessageDialog(currentGUI, "Your starting lineup is full! Now let's buy some reserves.\nOnce you're done, head to the main menu to get started!", "Alert", JOptionPane.PLAIN_MESSAGE);
            //new PopUpGUI("Perfect, we have purchased our starting athletes\n now lets buy some reserves", "Alert", true);
            currentShop.genNewAthletes();
            ShopGUI.updateJLabels(false);
            ShopGUI.setPurchaseTypeString("reserves");
        }     
        currentGUI.dispose();
    }

    
}
