package main.GUI.ActionListeners;
import main.GUI.*;
import main.*;
import main.Teams.PlayerTeam;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.naming.directory.InvalidAttributesException;
import javax.swing.JOptionPane;

/**
 * Action Listener class for the shop GUI, Implements Java.awt.event.ActionListener
 */
public class ViewDetailAction implements ActionListener{
    /**
     * The index of the item/athlete to view details on
     */
    int index;
    /**
     * The current type of stock being viewed (Items, Reserves, Athletes)
     */
    static String stockString;
    /**
     * The current instance of the Shop Logic Class
     */
    Shop currentShop;
    /**
     * A static boolean to determine if we are Selling or buying stock (Selling=true)
     */
    static boolean sellingStock;
    /**
     * A Static reference to the current instance of the PlayerTeam class
     */
    static PlayerTeam player;
    /**
     * A static boolean to determine 
     */
    static boolean reserves;

    /**
     * Constructor, Doesn't call any methods simply just sets values
     * @param indexToMap The index of the item/Athlete to view details on 
     * @param stringStock The current Stock Purchase/Sell String 
     * @param shop Instance of the Shop logic class
     * @param selling Boolean of whether we are selling stock or not
     * @param currentPlayer Instance of the current PlayerTeam Class
     */
    public ViewDetailAction(int indexToMap, String stringStock, Shop shop, boolean selling, PlayerTeam currentPlayer, boolean reservesSell){
        index = indexToMap;
        stockString = stringStock;
        currentShop = shop;
        player = currentPlayer;
        sellingStock = selling;
        reserves = reservesSell;
    }
    /**
     * @Override Will create a new View Detail GUI on clicking this button
     * @param event The ActionEvent class parsed through when an Action is performed
     */
    
    public void actionPerformed(ActionEvent event) {
        try{
            if (stockString.equalsIgnoreCase("not set"))
                throw new InvalidAttributesException("You need to pick a stock group to view first.");
            if(player.teamFull() && (stockString.equalsIgnoreCase("athletes") || stockString.equalsIgnoreCase("reserves")) && !sellingStock)
                throw new Exception("Your team is full! If you want to hire more athletes, you'll have to let someone go first.");
            new PurchaseableDetailGUI(index, currentShop, stockString, sellingStock, player, reserves);
            
        } catch(Exception exception){
            JOptionPane.showMessageDialog(null, exception.getMessage(), "An error has occured.", 0);
        }
        
    }

    public static void setStockString(String stockType){
        stockString = stockType;
    }

    public int getIndex(){
        return index;
    }

    public static void setSellingReserves(boolean sellReserves){
        reserves = sellReserves;
    }
    
}
