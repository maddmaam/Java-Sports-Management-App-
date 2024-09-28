package main.RandomEvents;

import java.util.*;

import main.Teams.PlayerTeam;
import main.Purchaseables.Athlete;

/**
 * The abstract superclass for all random events. Handles deciding if an event will activate or not as well as its type.
 * Includes a static method to generate new events of a randomized type depending on the status of the player's team.
*/

public abstract class RandomEvent {

    /**
     * Constructor. Does nothing.
    */
    public RandomEvent () {

    }

    /**
     * Activates the event and processes its effects. Implemented by subclasses.
     * @param player    PlayerTeam to apply the event's effects to.
    */
    public abstract String activate(PlayerTeam player);
    // Activate event with effect determined by subclass

    /**
     * Static method; generates a RandomEvent from one of the three possible subclass types.
     * StatIncrease has the highest chance by default. The chance of AthleteJoins increases with the number of the player's reserve slots that contain dummies,
     * while the chance of AthleteQuits increases with the number of injured Athletes in the player's team.
     * @param player    PlayerTeam to read array states from.
     * @return          Either a StatIncrease, AthleteQuits, or AthleteJoins event object.
    */
    public static RandomEvent gen(PlayerTeam player) {
        RandomEvent event;
        int dummies = 0;
        for (Athlete reserve : player.getAllReserves()) {
            if (reserve.isDummy()) {
                dummies += 1;
            }
        }
        // Chance of AthleteQuits depends on number of fainted athletes
        int injuries = 0;
        for (int i = 0; i < 5; i++) {
            if (player.getTeamMember(i).getInjured())
                injuries += 1;
            if (player.getReserve(i).getInjured())
                injuries += 1;
        }
        Random rand = player.getRandomGen();
        int roll = rand.nextInt(5 + injuries + dummies);
        if (roll <= 3) {
            // Higher chance than other events without modifiers
            event = new StatIncrease();
        } else if (roll <= injuries + 4) {
            // Increases with number of KOs (as above)
            event = new AthleteQuits();
        } else {
            // Chance increases with number of open reserve slots (see above)
            // Shouldn't happen if there are no dummies in player slots
            event = new AthleteJoins();
        }
        return event;
    }
    
}
