package main.Purchaseables;

import main.Teams.PlayerTeam;

/**
 * An Item subclass representing a "stat reroll" which allows the player to generate a new stat value for a chosen Athlete and stat from scratch.
*/

public class StatReroll extends InteractiveItem {

    /**
     * Constructor. Sets the Item's name as determined by subclass. 
    */
    public StatReroll() {
        super();
        setName("Do-Over");
    }

    /**
     * Retrieves the Item's description and an explanation of its effect when used.
     * @return      String describing the Item and its effect.
    */
    public String getEffect() {
        return "An experimental medication recently released onto the market. \nRandomizes the Atk or Def of a selected athlete.";
    }

    /**
     * Removes the Item from the player's inventory and activates its effects. Generates a new Atk value for the Athlete from scratch.
     * Also provides a large additional bonus if the Athlete is an Attacker.
     * @param useOn     The Athlete the Item is to be used on.
     * @param player    PlayerTeam whose inventory contains the Item.
     * @return          String either confirming the successful use of the Item or explaining why it is ineffective.
    */
    public String useOnAtk(Athlete useOn, PlayerTeam player) {
        // Player chose Atk reroll
        int newStat = useOn.genStat();
        if (useOn instanceof Attacker) {
            // Extra buff if stat matches athlete specialization... bigger bonus than they got on initial generation
            newStat += 15;
        }
        useOn.setAtk(newStat);
        player.removeFromInv(this);
        return "Success! " + useOn.getName() + "'s Atk is now " + useOn.getAtk() +
               "!\nThe " + getName() + " has been removed from your inventory.";
    }

    /**
     * Removes the Item from the player's inventory and activates its effects. Generates a new Def value for the Athlete from scratch.
     * Also provides a large additional bonus if the Athlete is a Defender.
     * @param useOn     The Athlete the Item is to be used on.
     * @param player    PlayerTeam whose inventory contains the Item.
     * @return          String either confirming the successful use of the Item or explaining why it is ineffective.
    */
    public String useOnDef(Athlete useOn, PlayerTeam player) {
        // Player chose Def reroll
        int newStat = useOn.genStat();
        if (useOn instanceof Defender) {
            newStat += 15;
        }
        useOn.setDef(newStat);
        player.removeFromInv(this);
        return "Success! " + useOn.getName() + "'s Def is now " + useOn.getDef() + 
               "!\nThe " + getName() + " has been removed from your inventory.";
    }

    /**
     * Removes the Item from the player's inventory and activates its effects. Generates a new HP value for the Athlete from scratch.
     * Also provides a large additional bonus if the Athlete is an AllRounder. Also fully heals the Athlete!!
     * @param useOn     The Athlete the Item is to be used on.
     * @param player    PlayerTeam whose inventory contains the Item.
     * @return          String either confirming the successful use of the Item or explaining why it is ineffective.
    */
    public String useOnHP(Athlete useOn, PlayerTeam player) {
        // Player chose HP reroll
        int newStat = useOn.genStat();
        newStat += 10;
        if (useOn instanceof AllRounder) {
                newStat += 20;
                // Secret special bonus! Or something
            }
        useOn.setMaxHP(newStat);
        useOn.setCurrentHP(newStat);
        player.removeFromInv(this);
        return "Success! " + useOn.getName() + "'s maximum HP is now " + useOn.getMaxHP()
               + "!\nThey feel reinvigorated and are also restored to full health!" +
                "\nThe " + getName() + " has been removed from your inventory.";
    }

}
