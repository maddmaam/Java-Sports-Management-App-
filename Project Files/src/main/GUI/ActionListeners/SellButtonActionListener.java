package main.GUI.ActionListeners;
import main.GUI.*;
import main.*;

import java.awt.event.*;

import javax.swing.JFrame;

/**
 * @author Madison Reilly (jre129)
 * @author David Williamson (dwi79)
 * Action Listener class for the Shop selling GUI, Implements Java.awt.event.ActionListener
 */
public class SellButtonActionListener implements ActionListener {

    /**
     * The interger of the sell button (used for the index of the item/Athlete to sell)
     */
    int index;
    /**
     * The current instance of the Shop logic class
     */
    Shop currentShop;
    /**
     * The current Sell-type String - Static Typed between all instances of this class
     */
    static String sellString;
    /**
     * Boolean which determins whether we are selling reserves or normal Athletes, as they are instances of the Athlete Class
     */
    boolean sellingReserves;
    /**
     * The current View Detail JFrame window
     */
    JFrame currentWindow;

    /**
     * Constructor, used to set the values to allow the sell button to function
     * 
     * @param sellIndex Index of the item/athlete being sold in the players athlete/item array
     * @param reserves Set this to true when selling athletes from the reserve team
     * @param purchaseString String which references the type of sale (Item/Athlete)
     * @param shop The shop class which the sale is going to be completed through
     * @param currentWindowFrame The current View Detail JFrame 
     */
    public SellButtonActionListener(int sellIndex, String purchaseString, Shop shop, boolean reserves, JFrame currentwindowFrame){
        index = sellIndex;
        sellString = purchaseString;
        currentShop = shop;
        sellingReserves = reserves;
        currentWindow = currentwindowFrame;
    }

    /**
     * @override This will sell the selected item/Athlete and update the shop to reflect
     * @param event The ActionEvent class being parsed when an action is performed
     */
    public void actionPerformed(ActionEvent event) {
        currentShop.sell(sellString, sellingReserves, index);
        ShopGUI.updateJLabels(true);
        currentWindow.setVisible(false);
        currentWindow.dispose();
    }
    
}
