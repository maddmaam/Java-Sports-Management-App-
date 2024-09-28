package main.Purchaseables;

import main.Teams.PlayerTeam;

/**
 * An Item subclass representing a "healing potion" that restores an injured (0HP) Athlete to full HP.
*/

public class HealInjury extends Item {
    
    /**
     * Constructor. Sets the Item's name as determined by subclass.
    */
    public HealInjury() {
        super();
        setName("Legally Distinct Phoenix Down");
    }

    /**
     * Retrieves the Item's description and an explanation of its effect when used.
     * @return      String describing the Item and its effect.
    */
    public String getEffect() {
        return "A vial of mysterious fluid with wholly unscientific effects. \nFully heals an injured (0HP) athlete.";
    }

    /**
     * Removes the Item from the player's inventory and activates its effects. Checks if the given Athlete is injured and if so,
     * removes their injured status and restores them to full health; otherwise, no action is taken and the player is notified.
     * @param useOn     The Athlete the Item is to be used on.
     * @param player    PlayerTeam whose inventory contains the Item.
     * @return          String either confirming the successful use of the Item or explaining why it is ineffective.
    */
    public String use(Athlete useOn, PlayerTeam player) {
        // Check if athlete is injured (0 HP or "injured" boolean)
        // If injured, restore athlete HP to full; if not, take no action
        // If used, remove item from player inventory
        if (useOn.getCurrentHP() != 0) {
            return "Athlete is not injured! The item will have no effect.";
        } else {
            useOn.setCurrentHP(Integer.valueOf(useOn.getMaxHP()));
            player.removeFromInv(this);
            return "Success! " + useOn.getName() + " is now at " + useOn.getHP() + " HP. Fully revitalized, they are ready and eager to compete once again."
                 + "\nThe " + getName() + " has been removed from your inventory.";
        }
    }

}
