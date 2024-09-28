package main.Purchaseables;
import main.Teams.PlayerTeam;
import main.GUI.StatChoiceDialog;

/**
 * An abstract class defining methods for using Items that require input from the player.
 * The player may select an Athlete stat for the given item to be applied to, and the effects will be applied as defined in the Item's subclass.
*/

public abstract class InteractiveItem extends Item {

    /**
     * Abstract; use the Item on the given Athlete's ATK stat.
     * @param useOn     Athlete to use the Item on.
     * @param player    Current PlayerTeam object.
     * @return          String confirming the Item's effects.
    */
    public abstract String useOnAtk(Athlete useOn, PlayerTeam player);

    /**
     * Abstract; use the Item on the given Athlete's DEF stat.
     * @param useOn     Athlete to use the Item on.
     * @param player    Current PlayerTeam object.
     * @return          String confirming the Item's effects.
    */
    public abstract String useOnDef(Athlete useOn, PlayerTeam player);

    /**
     * Abstract; use the Item on the given Athlete's HP stat.
     * @param useOn     Athlete to use the Item on.
     * @param player    Current PlayerTeam object.
     * @return          String confirming the Item's effects.
    */
    public abstract String useOnHP(Athlete useOn, PlayerTeam player);

    /**
     * Prompts the player to choose a stat and calls the applicable useOnStat method.
     * @param useOn     Athlete to use the Item on.
     * @param player    Current PlayerTeam object.
     * @return          String from applied useOnStat method confirming the Item's effects.
    */
    public String use(Athlete useOn, PlayerTeam player) {
        String stat = StatChoiceDialog.chooseStat();
        if (stat == "Atk") {
            return useOnAtk(useOn, player);
        }
        if (stat == "Def") {
            return useOnDef(useOn, player);
        }
        if (stat == "HP") {
            return useOnHP(useOn, player);
        }
        return "You decided not to use the item.";
    }
    
}
