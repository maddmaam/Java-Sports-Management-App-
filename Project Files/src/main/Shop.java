package main;

import java.util.*;

import javax.naming.directory.InvalidAttributesException;
import javax.swing.JOptionPane;

import main.Exceptions.OutOfMoneyException;
import main.Purchaseables.Athlete;
import main.Purchaseables.Item;
import main.Teams.*;

//import javax.lang.model.util.ElementScanner14;

/**
 * The class which manages the shop interface; here the player can view generated Items and Athletes for sale, purchase them,
 * or resell unwanted objects from their own roster or inventory. 
 * A new shop with new inventory for sale generates each time the main environment's week counter is incremented.
*/

public class Shop {

    /**
     * The current player's team.
    */
    PlayerTeam currentPlayer;
    /**
     * The shop's current stock of Items.
    */
    ArrayList<Item> itemStock;
    /**
     * The shop's current stock of Athletes.
    */
    ArrayList<Athlete> athleteStock;
    /**
     * The total number of objects of each type for sale.
    */
    int numberOfPurchasables;
    /**
     * Whether or not the shop is being used for inital game setup.
    */
    static boolean startingAthletes = true;
    /**
     * Starting athlete postition for use during initial setup.
    */
    static int startingAthletesPosition = 0;
    /**
     * Array of reserves for use during initial setup.
    */
    Athlete[] reserves;
    /**
     * Whether the player is buying Athletes for their reserves.
    */
    static boolean buyingReserves = false;
    /**
     * Current selected array slot.
    */
    int slotSelected;

    /**
     * Constructor. Generates fresh stock of Athletes and Items.
     * @param player        PlayerTeam to interact with when buying/selling.
    */
    public Shop(PlayerTeam player) {
        currentPlayer = player;
        SetNumberOfPurchasables(5);
        itemStock = genInv(numberOfPurchasables);
        athleteStock = genRoster(numberOfPurchasables);
    }

    /**
     * Constructor. For use during initial game setup. (?)
     * @param player                PlayerTeam to interact with when buying/selling.
     * @param numberOfPurchasables  Given number of objects to generate of each type.
    */
    public Shop(PlayerTeam player, int numberOfPurchasables){
        SetNumberOfPurchasables(numberOfPurchasables);
        currentPlayer = player;
        itemStock = genInv(numberOfPurchasables);
        athleteStock = genRoster(numberOfPurchasables);
        reserves = new Athlete[5];
    }

    /**
     * Sets the number of purchasable objects.
     * @param num       The intended number of objects.
    */
    public void SetNumberOfPurchasables(int num){
        numberOfPurchasables = num;
    }

    /**
     * Returns whether or not the player is buying their initial team.
     * @boolean     True if performing initial setup; else False.
    */
    public static boolean isStartingAthletes(){
        return startingAthletes;
    }

    /**
     * Set the startingAthletes boolean to True.
    */
    public void setStartingAthletes(){
        startingAthletesPosition = 0;
        startingAthletes = true;
    }

    /**
     * Get the current progress in buying starting Athletes.
     * @return      Current array index for starting purchases.
    */
    public static int getStartingAthletesPosition(){
        return startingAthletesPosition;
    }

    /**
     * Returns whether or not the player is buying initial reserves.
     * @return      True if player is buying initial reserves, else False.
    */
    public static boolean isBuyingReserves(){
    	if(startingAthletesPosition > 4 && buyingReserves)
    		buyingReserves = false;
        return buyingReserves;
    }

    /**
     * Set buyingReserves boolean to True.
    */
    public static void setBuyingReserves(){
        startingAthletesPosition = 0;
        buyingReserves = true;
    }

    /**
     * Generates a new weekly inventory of Items for sale using static method provided by Item superclass.
     * @param numberToGenerate      The number of Items to be created.
     * @return                      ArrayList of generated Items.
    */
    public ArrayList<Item> genInv(int numberToGenerate) {
        ArrayList<Item> newInv = new ArrayList<Item>();
        for (int i = 0; i < numberToGenerate; i++) {
            newInv.add(Item.genItem(currentPlayer.getRandomGen()));
        }
        return newInv;
    }

    /**
     * Generates a new weekly inventory of Athletes for sale using static method provided by Athlete superclass.
     * @param numberToGenerate      The number of Athletes to be generated.
     * @return                      ArrayList of generated Athletes.
    */
    public ArrayList<Athlete> genRoster(int numberToGenerate) {
        ArrayList<Athlete> newRoster = new ArrayList<Athlete>();
        for (int i = 0; i < numberToGenerate; i++) 
            newRoster.add(Athlete.genAthlete(currentPlayer.getRandomGen()));
        return newRoster;
    }

    /**
     * Select a new slot to be replaced.
     * @param slot      Index of desired slot.
    */
    public void setSlotToReplace(int slot){
        slotSelected = slot;
    }

    /**
     * Returns arraylist of shop's Athlete stock.
     * @return      ArrayList of Athlete objects for sale.
    */
    public ArrayList<Athlete> getAthletesStock(){
        return athleteStock;
    }

    /**
     * Returns arraylist of shop's Item stock.
     * @return      Arraylist of Item objects for sale.
    */
    public ArrayList<Item> getItemStock(){
        return itemStock;
    }

    /**
     * Begins generation of new Athletes and sets the array position to 0.
    */
    public void genNewAthletes(){
        athleteStock = genRoster(numberOfPurchasables);
        startingAthletesPosition = 0;
    }

    /**
     * Processes the purchase of the selected object.
     * @param itemPosition   Array position of the object to be purchased.
     * @param typePurchase  The type of object (Athlete, Item) being purchased.
    */
    public void purchase(int itemPosition, String typePurchase){
        try{
            switch (typePurchase.toUpperCase()){
                case "ATHLETES":
                    Athlete purchaseAthlete = athleteStock.get(itemPosition);
                    currentPlayer.takeMoney(purchaseAthlete.getBuyCost());
                    if (startingAthletes){
                        currentPlayer.addToTeam(purchaseAthlete, startingAthletesPosition++, true);
                        
                    }
                    else{
                        currentPlayer.addToTeam(purchaseAthlete, slotSelected, false);
                    }
                    athleteStock.remove(itemPosition);
                    if (startingAthletesPosition > 4)
                        startingAthletes = false;
                    break;
                case "ITEMS":
                    Item purchaseItem = itemStock.get(itemPosition);
                    currentPlayer.takeMoney(purchaseItem.getBuyCost());
                    currentPlayer.addToInv(purchaseItem);
                    itemStock.remove(itemPosition);
                    break;
                case "RESERVES":
                    Athlete reserveAthlete = athleteStock.get(itemPosition);
                    currentPlayer.takeMoney(reserveAthlete.getBuyCost());
                    if(isBuyingReserves())
                        currentPlayer.addSingleToReserves(startingAthletesPosition++, reserveAthlete);
                    else{
                        currentPlayer.addSingleToReserves(slotSelected, reserveAthlete);
                    }
                    athleteStock.remove(itemPosition);
                    if (startingAthletesPosition > 4 && buyingReserves)
                        buyingReserves = false;
                    break;
                default:
                    throw new IllegalArgumentException("Type Of Purchase not set");
            }
            
        }
        catch (OutOfMoneyException outOfMoneyException){
            JOptionPane.showMessageDialog(null, outOfMoneyException.getMessage(),"An Error occured", JOptionPane.ERROR_MESSAGE);
        }
        catch (IllegalArgumentException typePurchaseException){
            JOptionPane.showMessageDialog(null, typePurchaseException,"An Error occured", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Processes resale of a player-owned object.
     * @param sellType      The type (Athlete, Item) of the object to be sold.
     * @param reserves      Whether the object is a reserve Athlete.
     * @param index         The array index of the object to be sold.
    */
    public void sell(String sellType, boolean reserves, int index){
        try {
            switch(sellType.toUpperCase()){
                case "ITEMS":
                    ArrayList<Item> playerInv = currentPlayer.getInv();
                    if (playerInv.isEmpty()){
                        break;
                    }
                    Item currentItem = playerInv.get(index);
                    double sellCost = currentItem.getSellCost();
                    currentPlayer.addMoney(sellCost);
                    currentPlayer.removeFromInv(index);
                    break;
                case "ATHLETES":
                    Athlete selectedAthlete = currentPlayer.getAllTeam()[0];
                    if(!reserves){
                        selectedAthlete = currentPlayer.getAllTeam()[index];
                        currentPlayer.addMoney(selectedAthlete.getSellCost());
                        currentPlayer.removeMember(selectedAthlete);
                    }
                    else{
                        selectedAthlete = currentPlayer.getAllReserves()[index];
                        currentPlayer.addMoney(selectedAthlete.getSellCost());
                        currentPlayer.emptySlot(currentPlayer.getAllReserves(), index);
                    }
                    break;
                default:
                    throw new InvalidAttributesException("No Sell type string set");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e,"An Error occured", JOptionPane.ERROR_MESSAGE);
        }
    }
}
