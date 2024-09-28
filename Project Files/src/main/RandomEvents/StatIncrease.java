package main.RandomEvents;

import java.util.*;

import main.Teams.PlayerTeam;
import main.Purchaseables.Athlete;

/**
 * A RandomEvent subclass handling an event in which a random Athlete in the player's team receives a +5 bonus to Atk or Def.
*/

public class StatIncrease extends RandomEvent {

    /**
     * Activates the random event. Selects a random slot in the player's team and, if the slot does not contain a dummy, applies a +5 boost to
     * either Atk or Def, also selected at random. If the selected slot is "empty" (contains a dummy), nothing happens.
     * @param player    PlayerTeam containing the player's Athlete arrays.
    */
    public String activate(PlayerTeam player) {
        // If chosen slot is empty, nothing happens (?)
        Random rand = player.getRandomGen();
        int athIndex = rand.nextInt(5);
        Athlete athlete = player.getAllTeam()[athIndex];
        if (! athlete.isDummy()) {
            int toBoost = rand.nextInt(2);
            if (toBoost == 0) {
                athlete.setAtk(athlete.getAtk() + 5);
                return "Random Event!! \n" + athlete.getName() + " suddenly feels a surge of athletic energy! Their ATK has increased by 5.";
            } else {
                athlete.setDef(athlete.getDef() + 5);
                return "Random Event!! \n" + athlete.getName() + "'s desire to protect their teammates grants them new power! Their DEF has increased by 5.";
            }
        } else {
            return "Random Event!! \n...You get the distinct feeling that something exciting could have happened here. \nBut it didn't.";
        }
    }
    
}
