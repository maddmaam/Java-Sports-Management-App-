package main.Purchaseables;

import main.Teams.PlayerTeam;

/**
 * An Item subclass representing "performance enhancing medication" which increases a selected Athlete's stats, with bonuses based on their specialization.
*/

public class StatBoost extends InteractiveItem {

    /**
     * Constructor. Sets the Item's name as determined by subclass.
    */
    public StatBoost() {
        super();
        setName("Completely Legal Performance Enhancers");
    }

    /**
     * Retrieves the Item's description and an explanation of its effect when used.
     * @return      String describing the Item and its effect.
    */
    public String getEffect() {
        return "An inconspicuous bottle of pills. \nIncreases Atk or Def of a selected athlete.";
    }

    /**
     * Removes the Item from the player's inventory and activates its effects. Applies a +10 bonus to the chosen Athlete's Atk,
     * with an extra +5 bonus given if the Athlete is an Attacker.
     * @param useOn     The Athlete the Item is to be used on.
     * @param player    PlayerTeam whose inventory contains the Item.
     * @return          String either confirming the successful use of the Item or explaining why it is ineffective.
    */
    public String useOnAtk(Athlete useOn, PlayerTeam player) {
        int buff = 3;
        // Player chose Atk boost
        if (useOn instanceof Attacker) {
            // Extra buff if stat matches athlete specialization
            buff += 5;
        }
        useOn.setAtk(useOn.getAtk() + buff);
        player.removeFromInv(this);
        return "Success! " + useOn.getName() + "'s Atk was increased by "
                + buff + " points and is now " + useOn.getAtk() + 
                "!\nThe " + getName() + " has been removed from your inventory.";
    }

    /**
     * Removes the Item from the player's inventory and activates its effects. Applies a +10 bonus to the chosen Athlete's Def,
     * with an extra +5 bonus given if the Athlete is an Defender.
     * @param useOn     The Athlete the Item is to be used on.
     * @param player    PlayerTeam whose inventory contains the Item.
     * @return          String either confirming the successful use of the Item or explaining why it is ineffective.
    */
    public String useOnDef(Athlete useOn, PlayerTeam player) {
        int buff = 3;
        // Player chose Def boost
        if (useOn instanceof Defender) {
            buff += 5;
        }
        useOn.setDef(useOn.getDef() + buff);
        player.removeFromInv(this);
        return "Success! " + useOn.getName() + "'s Def was increased by "
                 + buff + " points and is now " + useOn.getDef() + 
                 "!\nThe " + getName() + " has been removed from your inventory.";
    }

    /**
     * Removes the Item from the player's inventory and activates its effects. Applies a +10 bonus to the chosen Athlete's HP,
     * with an extra +5 bonus given if the Athlete is an AllRounder.
     * @param useOn     The Athlete the Item is to be used on.
     * @param player    PlayerTeam whose inventory contains the Item.
     * @return          String either confirming the successful use of the Item or explaining why it is ineffective.
    */
    public String useOnHP(Athlete useOn, PlayerTeam player) {
        int buff = 3;
        // Player chose HP boost
        if (useOn instanceof AllRounder) {
            buff += 5;
        }
        useOn.setMaxHP(useOn.getMaxHP() + buff);
        player.removeFromInv(this);
        return "Success! " + useOn.getName() + "'s HP was increased by "
                 + buff + " points and is now " + useOn.getHP() + 
                 "!\nThe " + getName() + " has been removed from your inventory.";
    }

}
