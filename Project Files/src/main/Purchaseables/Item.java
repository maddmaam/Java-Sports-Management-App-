package main.Purchaseables;

import java.util.*;

import main.Teams.PlayerTeam;

/**
 * An Enum supporting generation of Items of different subclasses.
*/

enum ItemType {
    // For generating items of different types
    RESTORE, INJURY, BOOST, REROLL
}

/**
 * The main Item superclass; handles Item costs and generation, including a static method to generate new Items of a random type.
 * Items may be bought by the player from the shop, stored in the player's inventory array, and used on Athlete's in the player's team.
*/

public abstract class Item implements Purchasable {
    
    /**
     * The item's name.
    */
    private String name;
    /**
     * The item's purchase cost.
    */
    private int buyCost;
    /**
     * The item's resale cost. 
    */
    private int sellCost;

    /**
     * Constructor. Generates a placeholder name and purchase/resale costs for a new Item.
    */
    public Item() {
        name = "Generic Placeholder";
        buyCost = 40;
        sellCost = 10;
        // WIP: have specific item types generate their own costs
    }

    /**
     * Gets the Item's name.
     * @return      String containing the item's name as determined by its subclass constructor.
    */
    public String getName() {
        return name;
    }

    /**
     * Sets a new name for the Item.
     * @param newName       String containing the Item's intended new name.
    */
    public void setName(String newName) {
        name = newName;
    }

    /**
     * Get the Item's purchase cost.
     * @return      Int representing the cost to purchase the Item.
    */
    public int getBuyCost() {
        // Return purchase cost
        return buyCost;
    }

    /**
     * Get the Item's resale cost.
     * @return      Int representing the funds obtained if the player resells the item.
    */
    public int getSellCost() {
        return sellCost;
    }

    /**
     * Unused skeleton method to process the player's purchase of an Item. Withdraws funds from the player and adds the Item to their inventory array.
     * Currently handled instead from within Shop class.
     * @param player       PlayerTeam containing the inventory to add the Item to.
    */
    public void buy(PlayerTeam player) {
        // Process player purchase of item
        player.takeMoney(buyCost);
        // WIP: make sure player has enough money
        player.addToInv(this);
        // WIP: remove item from store inventory (???)
    }

    /**
     * Processes resale of Items. Adds the Item's sellCost to the player's money and removes it from their inventory.
     * @param player    PlayerTeam to add the funds to and remove the Item from.
    */
    public void sell(PlayerTeam player) {
        // Process player resale of item
        player.addMoney(sellCost);
        player.removeFromInv(this);
    }

    /**
     * Generates a formatted description of the Item featuring its name and effect.
     * For display in the store and club inventory menus.
     * @return      String displaying the Item's name and effect in a readable format.
    */
    public String getDescription() {
        String desc = "Item Name: " + getName()
        + "\nEffects: " + getEffect();
        return desc;
    }

    /**
     * Abstract method implemented by Item subclasses. Retrieves a description determined by the Item's type.
     * @return      String containing a description of the Item and its effects.
    */
    public abstract String getEffect();
    // Get effects of specific item types

    /**
     * Prints the results of getDescription() followed by the Item's purchase and resale costs in a similar format.
     * @return      String containing Item description, name, and costs.
    */
    public String getPurchaseDetails() {
        // Print item details and costs for display in store
        String purchaseDetail = getDescription() + "\nBuy for: " + getBuyCost() + "\nSell for: " + getSellCost();
        return purchaseDetail;
        // WIP: display either buy or sell cost (or both) depending on context... ?
    }

    /**
     * Overrides default toString() to return the Item's name as definied by its subclass.
     * @return      String containing the Item's name.
    */
    public String toString(){
        return this.getName();
    }

    /**
     * Abstract method implemented by Item subclasses. Activates the Item's effect as determined by its type.
     * @param useOn     Athlete the item is to be used on.
     * @param player    PlayerTeam whose inventory contains the Item to be used.
     * @return          String confirming the Item's effects after use.
    */
    public abstract String use(Athlete useOn, PlayerTeam player);
    // Use item; effects differ based on type

    /**
     * Static method to generate a new Item of a randomized type out of the four available subclasses.
     * HealthRestore are more common than other types, while HealInjury are the rarest.
     * @param rand  Random number generation object as defined at game start.
     * @return      A new randomly-generated Item; either a HeathRestore, StatBoost, StatReroll, or HealInjury.
    */
    public static Item genItem(Random rand) {
        // Some items are rarer than others; roll to decide which one is created
        // WIP: neater implementation + have chances affected by game difficulty
        // WIP???: this really didn't need an enum but here we are; rework later
        Item newItem = new HealthRestore();
        ItemType type;
        int roll = rand.nextInt(100);
        if (roll < 40) {
            type = ItemType.RESTORE;
        } else if (roll < 70) {
            type = ItemType.BOOST;
        } else if (roll < 90) {
            type = ItemType.REROLL;
        } else {
            type = ItemType.INJURY;
        }
        switch (type) {
            case RESTORE:
                newItem = new HealthRestore();
                break;
            case INJURY:
                newItem = new HealInjury();
                break;
            case BOOST:
                newItem = new StatBoost();
                break;
            case REROLL:
                newItem = new StatReroll();
                break;
        }
        return newItem;
    }

}
