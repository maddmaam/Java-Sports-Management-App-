package main.RandomEvents;

import main.Teams.PlayerTeam;
import main.Purchaseables.Athlete;

/**
 * A RandomEvent subclass handing an event in which an injured (0HP) player is permanently removed from the player's team.
*/

public class AthleteQuits extends RandomEvent {

    /**
     * Activates the random event. Iterates over the player's team and reserve arrays and selects the first injured (0HP) athlete found for removal.
     * The player is informed and the removed Athlete is replaced by a dummy.
     * @param player    PlayerTeam containing the player's Athlete arrays.
    */
    public String activate(PlayerTeam player) {
        // Activate effect
        for (int i = 0; i <5; i++) {
            Athlete athlete = player.getAllTeam()[i];
            if (athlete.getCurrentHP() == 0) {
                player.emptySlot(player.getAllTeam(), i);
                return "Random Event!! \nOh no! Traumatized by their recent injuries, "
                 + athlete.getName() + " has decided to quit the team! \nThey have been removed from your roster.";
            }
            Athlete nextAthlete = player.getAllReserves()[i];
            if (nextAthlete.getCurrentHP() == 0) {
                player.emptySlot(player.getAllReserves(), i);
                return "Random Event!! \nOh no! Traumatized by their recent injuries, "
                 + nextAthlete.getName() + " has decided to quit the team! \nThey have been removed from your roster.";
            }
        }
        return "Random Event!! \n...Nothing happens!! At least your team seem to be in good health.";
    }
    
}