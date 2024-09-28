package main;

import main.Purchaseables.Athlete;
import main.Teams.*;

/**
 * The class managing the club area; here the player can view and rearrage Athletes in their team and reserve arrays, view their inventory, and use Items.
*/

public class Club {

    /**
     * The player's Team object; stores their money, name, inventory, and Athlete arrays.
    */
    private PlayerTeam player;

    /**
     * Constructor.
     * @param p PlayerTeam containing the player's team members, inventory, and other relevant information.
    */
    public Club(PlayerTeam p) {
        player = p;
    }

    /**
     * Swaps the position of two Athletes in the player's arrays with support from the swapSlots(...) method in PlayerTeam.
     * This method is only intended to swap in the same Array, for moving to reserves see PlayerTeam.
     * @param currentSlot The Current slot of the Athlete.
     * @param newSlot The new slot for the Athlete.
     * @param reserve A boolean to determine whether to swap athletes in the reserve or main team.
    */
    public void moveSlots(int currentSlot, int newSlot, boolean reserve) {
        // Swap the occupants of two slots
        player.swapSlots(currentSlot, newSlot, reserve);
    }

    /**
     * Move the given Athlete to the reserve array.
     * @param currentSlot       Athlete's current inTeam slot.
     * @param toReserves        The Athlete to be moved.
    */
    public void moveToReserves(int currentSlot, Athlete toReserves){
        player.swapToReserves(toReserves, currentSlot);
    }

    /**
     * Move an Athlete from the reserves to the main team.
     * @param currentSlot       Athlete's current reserve slot.
    */
    public void moveToMainTeam(int currentSlot){
        Athlete athleteInMainTeamSlot = player.getTeamMember(currentSlot);
        player.swapToReserves(athleteInMainTeamSlot, currentSlot);
    }
}
