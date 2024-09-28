package main.RandomEvents;

import main.Teams.PlayerTeam;
import main.Purchaseables.Athlete;

/**
 * A RandomEvent subclass handling an event in which a new randomly generated Athlete can be added to the player's team for free.
*/

public class AthleteJoins extends RandomEvent {

    /**
     * A free array slot to add the new Athlete to.
    */
    private int freeSlot;
    /**
     * "Team" or "Reserves".
    */
    private String addTo;

    /**
     * Constructor.
    */
    public AthleteJoins() {
        
    }

    /**
     * Activates the random event. A new Athlete is generated via the static method Athlete.genAthlete()
     * and added to the player's team if they have any free slots available.
     * @param player    PlayerTeam containing the player's athlete arrays.
     * @return          String confirming outcome of the event.
    */
    public String activate(PlayerTeam player) {
        // Activate effect
        freeSlot = -1;
        addTo = "";
        Athlete newAthlete = Athlete.genAthlete(player.getRandomGen());
        String message = "Random Event!! \nA wandering unaffiliated player, " 
        + newAthlete.getName() + ", has taken notice of your managerial prowess. They want to join your team!! ";
        // Find the last available free slot
        for (int j = 0; j < 5; j++) {
            if (player.getTeamMember(j).isDummy()) {
                freeSlot = j;
                addTo = "Team";
            }
        }
        for (int i = 0; i < 5; i++) {
            if (player.getReserve(i).isDummy()) {
                freeSlot = i;
                addTo = "Reserves";
            }
        }
        if (freeSlot == -1) {
            // If there are no free slots...
            message += "\n...However, you don't have any room!!\n" + newAthlete.getName() + "'s hopes and dreams are crushed, and they leave disappointed.";
        } else {
            if (addTo.equals("Team")) {
                player.addToTeam(newAthlete, freeSlot, false);
            } else if (addTo.equals("Reserves")) {
                player.addSingleToReserves(freeSlot, newAthlete);
            }
            message += "\n" + newAthlete.getName() + " has been added to your team. You simply cannot refuse their heartfelt application.";
        }
        return message;
    }
}