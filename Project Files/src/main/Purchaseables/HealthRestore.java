package main.Purchaseables;

import main.Teams.PlayerTeam;

/**
 * An Item subclass representing a "first aid kit" which restores a fraction of an Athlete's depleted HP.
*/

public class HealthRestore extends Item {

    /**
     * Constructor. Sets the Item's name as determined by subclass. 
    */
    public HealthRestore() {
        super();
        setName("First Aid Kit");
    }

    /**
     * Retrieves the Item's description and an explanation of its effect when used.
     * @return      String describing the Item and its effect.
    */
    public String getEffect() {
        return "A standard Sportsball medical kit containing bandages and painkillers. \nRestores a little HP to a selected athlete.";
    }

    /**
     * Removes the Item from the player's inventory and activates its effects. Checks if the given Athlete is below maximum HP and if so,
     * restores their current HP by (approximately) 25% of their maximum; if not, no action is taken and the player is notified.
     * @param useOn     The Athlete the Item is to be used on.
     * @param player    PlayerTeam whose inventory contains the Item.
     * @return          String either confirming the successful use of the Item or explaining why it is ineffective.
    */
    public String use(Athlete useOn, PlayerTeam player) {
        // Check if chosen athlete is below full HP
        // If so, restore HP by some amount; if not, no action
        // If used, remove item from player inventory
        if (useOn.getCurrentHP() == useOn.getMaxHP()) {
            return "Athlete is at full HP already! The item will have no effect.";
        } else if (useOn.getInjured()) {
            return "Athlete is much too seriously injured for this to have any effect. You're going to need something stronger.";
        } else {
            int toHeal = (int) Math.rint(useOn.getMaxHP() * 0.25);
            int newHP = useOn.getCurrentHP() + toHeal;
            // WIP: game balance... currently recovering 25% of max HP; also neater implementation lol
            useOn.setCurrentHP(newHP);
            player.removeFromInv(this);
            return "Success! " + useOn.getName() + " recovered " + toHeal + " health and is now at " + useOn.getHP()
                 + " HP.\nThe " + getName() + " has been removed from your inventory.";
        }
    }

}
