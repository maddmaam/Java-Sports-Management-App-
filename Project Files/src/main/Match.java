package main;

import java.util.concurrent.TimeUnit;

import main.Purchaseables.Athlete;
import main.Teams.*;

/**
 * The class that handles Sportsball matches between the player and an opposing team.
 * Compares the Athletes in same-numbered slots across each team's array and determines a winner based on the results.
*/

public class Match {

    /**
     * The player's team.
    */
    private PlayerTeam player;
    /**
     * The opposing team. 
    */
    private OpposingTeam enemy;
    /**
     * Number of comparisons won by player in an ongoing match.
    */
    private int score;
    /**
     * Strings depicting match results to be displayed on the GUI.
    */
    String[] guiStrings;

    /**
     * Constructor.
     * @param playerTeam    The player's team.
     * @param enemyTeam     The opposing team.
    */
    public Match(PlayerTeam playerTeam, OpposingTeam enemyTeam) {
        player = playerTeam;
        enemy = enemyTeam;
        score = 0;
        guiStrings = new String[6];
    }

    /**
     * Runs comparisons between Athletes in the player's team array versus those in the same slot number of the enemy's team array.
     * Slots 0-1 are "attacker" positions where the player's Athlete's Atk is compared against the enemy's Def to determine the winner.
     * Slots 2-3 are "defender" positions where the player's Athlete's Def is compared with the enemy's Atk.
     * Slot 4 is the "final showdown" and compares in both directions.
     * The player is victorious if their Athletes won at least 3/5 comparisons (as per the variable 'score'). 
     * Any lost comparisons result in the player's Athlete taking a -5HP damage penalty.
     * @return      The winning team.
    */
    public String[] compareTeams() {
        // need sub function that creates a match GUI, from that adds the returned strings from the subfunctions
        // matchGUI(string part1, string part2, string part3)
        for(int i = 0; i < 5; i++){
            guiStrings[i] = compareSlots(i);
        }
        String matchOutcome;
        // Here are some strings for you to print in the GUI
        if (score >= 3) {
            matchOutcome = postGame(true);
            guiStrings[5] = matchOutcome;
        } else {
            matchOutcome = postGame(false);
            guiStrings[5] = matchOutcome;
        }
        return guiStrings;
    }

    /**
     * Generates a string describing the outcome of comparison between Athletes in the given slot of the player and enemy teams.
     * @param slotNum       Slot to compare Athletes from.
     * @return              String describing the outcome of the comparison.
    */
    public String compareSlots(int slotNum) {
        Athlete playerAthlete = player.getTeamMember(slotNum);
        Athlete enemyAthlete = enemy.getTeamMember(slotNum);
        String stat;
        if (slotNum <= 1)
            stat = "Atk";
        else if (slotNum <= 3)
            stat = "Def";
        else
            stat = "Both";
        if (playerAthlete.getCurrentHP() == 0) {
            // Automatic loss if player athlete has 0 HP
            return "\n" + playerAthlete.getName() + " staggers and collapses!! They are much too weak to compete! ";
        }
        else if (compareAthletes(slotNum, stat)) {
            // Player Athlete wins
            score += 1;
            if (slotNum <= 1) {
                // Atk slots
                return "\n" + playerAthlete.getName() + " attacks succesfully!! \n"
                            + printStats(playerAthlete, enemyAthlete);
            } else if (slotNum <= 3) {
                // Def slots
                return "\n" + playerAthlete.getName() + " defends succesfully!! \n"
                            + printStats(playerAthlete, enemyAthlete);
            } else {
                // Goal slot
                return "\n" + playerAthlete.getName() + " protects the goal from an opponent!! \n"
                            + printStats(playerAthlete, enemyAthlete);
            }
        } else {
            // Opponent Athlete wins
            playerAthlete.setCurrentHP(playerAthlete.getCurrentHP() - 5);
            if (slotNum <= 1) {
                // Atk slots
                return "\n" + playerAthlete.getName() + "'s offensive is blocked!! " +
                       "\n" + "They take 5 damage and are now at " + playerAthlete.getHP() + " HP. \n"
                        + printStats(playerAthlete, enemyAthlete);
            } else if (slotNum <=3) {
                // Def slots
                return "\n" + playerAthlete.getName() + "'s defense isn't strong enough!! " +
                       "\n" + "They take 5 damage and are now at " + playerAthlete.getHP() + " HP. \n"
                       + printStats(playerAthlete, enemyAthlete);
            } else {
                // Goal slot
                return "\n" + playerAthlete.getName() + " can't stop the opponent from scoring!! " +
                       "\n" + "They take 5 damage and are now at " + playerAthlete.getHP() + " HP. \n"
                       + printStats(playerAthlete, enemyAthlete);

            }
        }
    }

    /**
     * Formats a string showing the stats of two Athletes alongside each other.
     * @param playerAthlete     An Athlete from the player's team.
     * @param enemyAthlete      An Athlete from the opposing team.
     * @return                  String containing readable depiction of Athlete's stats.
    */
    public String printStats(Athlete playerAthlete, Athlete enemyAthlete) {
        return "[ " + playerAthlete.getName() + "'s ATK: " + playerAthlete.getAtk() + ", DEF: " + playerAthlete.getAtk() +
        " | Opponent " + enemyAthlete.getName() + "'s ATK: " + enemyAthlete.getAtk() + ", DEF: " + enemyAthlete.getDef() + " ]";
    }

    /**
     * Compares two Athletes from the given array slot based on the given stat and determines if the player's Athlete is stronger.
     * @param slot      The array slot from which to compare Athletes.
     * @param stat      The player's stat to be compared; player's Atk will be compared with enemy's Def and vice versa.
     * @return          Whether the player's Athlete won or not.
    */
    public boolean compareAthletes(int slot, String stat) {
        boolean playerWins = false;
        if (stat == "Atk")
            if (player.getTeamMember(slot).getAtk() > enemy.getTeamMember(slot).getDef())
                playerWins = true;
        if (stat == "Def")
            if (player.getTeamMember(slot).getDef() > enemy.getTeamMember(slot).getAtk())
                playerWins = true;
        if (stat == "Both")
            if (player.getTeamMember(slot).getDef() >= enemy.getTeamMember(slot).getAtk() && player.getTeamMember(slot).getAtk() >= enemy.getTeamMember(slot).getDef())
                playerWins = true;
        return playerWins;
    }

    /**
     * Applies Athlete HP penalties after a match. If the player lost, all their active Athletes lose -5HP, otherwise they lose -2HP.
     * @param playerWon         Whether the player won the match or not.
     * @return                  String describing match outcome.
    */
    public String postGame(boolean playerWon) {
        // WIP: check game balance
        if (playerWon) {
            for (int i = 0; i < 5; i++) {
                int newHP = player.getTeamMember(i).getCurrentHP() - 2;
                player.getTeamMember(i).setCurrentHP(newHP);
            }
            return "\nYou won the match!! Your glory and power as a manager grows even greater! " + enemy.givePrize(player);
        } else {
            for (int i = 0; i < 5; i++) {
                int newHP = player.getTeamMember(i).getCurrentHP() - 5;
                player.getTeamMember(i).setCurrentHP(newHP);
            }
            return "\nYou lost the match! The team are exhausted and lose a significant amount of HP. Make sure to check their status at the club later.";
        }
    }

    /** 
     * Gets the Player's score for the match. For testing purposes only.
     * @return      Int representing player's succesful Athlete comparisons.
    */
    public int getScore() {
        return score;
    }

}
