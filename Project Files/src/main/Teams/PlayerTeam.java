package main.Teams;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.*;

import main.Exceptions.OutOfMoneyException;
import main.Purchaseables.Athlete;
import main.Purchaseables.Item;

/**
 * An extension of Team that handles methods and variables needed for the player's team, including their selected name,
 * their inventory, reserve players, money, and season points.
*/

public class PlayerTeam extends Team {

    /**
     * Array containing the player's reserve team members.
    */
    private Athlete[] reserves;
    /**
     * Array containing the Items in the player's possession.
    */
    private ArrayList<Item> inventory;
    /**
     * The player's current money.
    */
    private double money;
    /**
     * The player's current season points.
    */
    private int points;
    /**
     * Int tracking free main team slots.
    */
    int freeAthleteSlot = -1;
    /**
     * Int tracking free reserve team slots.
    */
    int freeReserveSlot = -1;

    /**
     * Constructor. Fills the initial team arrays with dummy Athlete references.
    */
    public PlayerTeam() {
        super();
        money = 900;
        String teamName = "";
        setName(teamName);
        Athlete dummy = new Athlete(getRandomGen());
        dummy.setDummy();
        // Fill initial team and reserve arrays with dummy athlete references
        for (int i = 0; i < getSize(); i++) {
            addToTeam(dummy, i, true);
        }
        reserves = new Athlete[5];
        for (int i = 0; i <5; i++) {
            reserves[i] = dummy;
        }
        // 5 reserves as per project brief
        inventory = new ArrayList<Item>();
        points = 0;
    }

    /**
     * Gets reserve member from a specific array slot.
     * @param slot      The slot to retrieve from.
     * @return          The Athlete found in the given slot.
    */
    public Athlete getReserve(int slot) {
        return reserves[slot];
    }

    /**
     * Gets the player's entire reserve array.
     * @return      Athlete[] reserve array.
    */
    public Athlete[] getAllReserves() {
        // Get entire reserve array
        return reserves;
    }

    /**
     * Gets an item from a specific inventory slot.
     * @param slot      The slot to retrieve from.
     * @return          The item found in the given slot.
    */
    public Item getItem(int slot) {
        return inventory.get(slot);
    }

    /**
     * Gets the player's entire inventory ArrayList.
     * @return          ArrayList < Item > inventory array.
    */
    public ArrayList<Item> getInv() {
        return inventory;
    }

    /**
     * Adds an Item to the inventory ArrayList.
     * @param newItem       The item to be added.
    */
    public void addToInv(Item newItem) {
        inventory.add(newItem);
    }

    /**
     * Removes an Item from the inventory ArrayList using the Item's object reference.
     * @param toRemove      The item to be removed.
    */
    public void removeFromInv(Item toRemove) {
        inventory.remove(toRemove);
    }

    /**
     * Remove an item from the inventory ArrayList using the Item's slot number.
     * @param itemSlot      The slot to remove the item from.
    */
    public String removeFromInv(int itemSlot){
        if (itemSlot >= 0 && itemSlot < inventory.size()) {
            inventory.remove(itemSlot);
            return "The item was removed from your inventory.";
        } else {
            return "No item at specified location";
        }
    }
    
    /**
     * Gets the player's current money.
     * @return      Double representing the player's available funds.
    */
    public double getMoney() {
        return money;
    }

    /**
     * Set the player's initial funds at game setup.
     * @param moneyAmount       The amount of money to be given.
    */
    public void setStartingMoney(int moneyAmount){
        money = moneyAmount;
    }

    /**
     * Add to the player's current money.
     * @param gains     The amount of money to add to the current total.
    */
    public void addMoney(double gains) {
        money += gains;
    }

    /**
     * Check if player's Athlete arrays contain dummies.
     * @return      False if any dummies are found; true otherwise.
    */
    public boolean teamFull(){
        freeAthleteSlot = -1;
        freeReserveSlot = -1;
        for (int i =0, j = 0; (i < inTeam.length && j < reserves.length); i++, j++){
            if(inTeam[i].isDummy()){
                freeAthleteSlot = i;
                return false;
            }
            else if (reserves[j].isDummy()){
                freeReserveSlot = j;
                return false;
            }
        }
        return true;
    }

    /**
     * Get freeAthleteSlot variable.
     * @return  Int tracking free slots in main array.
    */
    public int getFreeAthleteSlot(){
        return freeAthleteSlot;
    }

    /**
     * Get freeReserveSlot variable.
     * @return  Int tracking free slots in reserve array.
    */
    public int getFreeReserveSlot(){
        return freeReserveSlot;
    }

    /**
     * Reduce the player's current money. Throws OutOfMoneyException if the player does not have enough to cover the desired cost.
     * @param losses    The amount of money to remove.
    */
    public void takeMoney(double losses) throws OutOfMoneyException{
        if (losses < 0) {
            throw new IndexOutOfBoundsException("You can't add negative funds!");
        }
        if (money >= 0 && money - losses >= 0) {
            money -= losses;
        } else {
            throw new OutOfMoneyException("You do not have enough money.");
        }
    }

    /**
     * Get player's current season points.
     * @return      Int representing player's current points.
    */
    public int getPoints() {
        // Return current points
        return points;
    }

    /**
     * Add season points to the player's current score.
     * @param increase      The amount of points to be added.
    */
    public void addPoints(int increase) {
        // Player cannot lose points
        points += increase;
    }

    /**
     * Allows the player to move an Athlete from one array slot to another.
     * @param newAthlete        The Athlete to be moved.
     * @param destination       The array the given Athlete is to be moved to.
    */
    public void swapSlots(int originalIndex, int destination, boolean swapToReserves) {
        if(swapToReserves){
            Athlete originAthlete = reserves[originalIndex];
            Athlete athleteToReplace = reserves[destination];
            reserves[originalIndex] = athleteToReplace;
            reserves[destination] = originAthlete;
        }
        else{
            Athlete originAthlete = inTeam[originalIndex];
            Athlete athleteToReplace = inTeam[destination];
            inTeam[originalIndex] = athleteToReplace;
            inTeam[destination] = originAthlete;
        }
    }

    /**
     * @override
     * 
     * This implementation will change the variables within the class itself not within the array parsed through
     * @param array         The array for the Athlete to be added to.
     * @param slot          The index for the Athlete to be added to in the given array.
     * @param athlete       The Athlete to be added.
    */
    public void overwriteSlot(Athlete[] array, int slot, Athlete athlete) {
        // Will completely replace the previous occupant of slot, use with care!!
        if (array.equals(inTeam))
            inTeam[slot] = athlete;
        else{
            reserves[slot] = athlete;
        }
    }

    /**
     * Swap an Athlete to the reserve array.
     * @param toReserves        Athlete to be moved.
     * @param indexOfAthlete    Athlete's array index.
    */
    public void swapToReserves(Athlete toReserves, int indexOfAthlete){
        Athlete otherAthlete = reserves[indexOfAthlete];
        reserves[indexOfAthlete] = toReserves;

        inTeam[indexOfAthlete] = otherAthlete;
    }

    /**
     * Adds Athletes from the given array to the player's reserve array. For use only during initial game setup.
     * @param reserveAthletes       The array to add Athletes from.
    */
    public void addToReserves(Athlete[] reserveAthletes){
        // Add athletes to the reserves; will only ever be called when first constructing the team
        for (int i = 0; i < reserveAthletes.length; i++)
            reserves[i] = reserveAthletes[i];
    }
    /**
     * Returns details of all Athlete members of the player's main team array.
     * @param toSell    Whether the information is being printed for purposes of selling Athletes at the shop; in this case, the resale price is also printed.
     * @return          String containing the names, specializations, and (depending on toSell bool) sell costs of Athletes in the main team.
     */
    public String printTeam(boolean toSell){
        String s = "";
        if(!toSell)
            return super.printTeam();
        else {
            for (int i = 0; i < 5; i++){
                String name = inTeam[i].getName();
                String specialization = inTeam[i].getSpecialization();
                double sellCost = inTeam[i].getSellCost();
                s += "\n" + (i+1) + ". " + name + ", " + specialization + ". Sells for " + sellCost;
            }
            return s;
        }
    }

    /**
     * Adds a new Athlete to the given reserve slot. For use only by AthleteJoins random events.
     * @param newAthlete       The Athlete to be added.
     * @param slot             The slot to add the Athlete to.
     * @return                 String confirming new Athlete's placement.
    */
    public String addRandom(Athlete newAthlete, int slot) {
        reserves[slot] = newAthlete;
        return newAthlete.getName() + " was added to reserve slot " + (slot + 1) + ".";
    }

    /**
     * Replaces any Athlete in the given array and slot with a dummy. Use with caution!
     * @param roster        The array to remove an Athlete from.
     * @param slot          The slot within the given array to remove an Athlete from.
    */
    public void emptySlot(Athlete[] roster, int slot) {
        Athlete dummy = new Athlete(getRandomGen());
        dummy.setDummy();
        roster[slot] = dummy;
    }

    /**
     * Checks if a given Athlete is in the given array.
     * @param athlete       The Athlete to be searched for.
     * @param lineup        The array to be searched for the given Athlete.
     * @return              'True' if the Athlete was found.
    */
    public boolean isInTeam(Athlete athlete, Athlete[] lineup) {
        for (Athlete checkAgainst : lineup) {
            if (checkAgainst == athlete) {
                return true;
            }
        }
        return false;
    }

    /**
     * Add a single Athlete to the reserve array.
     * @param slot      Slot to add Athlete to.
     * @param athlete   The Athlete to be added.
    */
    public void addSingleToReserves(int slot, Athlete athlete){
        reserves[slot] = athlete;
    }

}
