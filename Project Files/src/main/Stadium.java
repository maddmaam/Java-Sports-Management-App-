package main;

import java.util.*;

import javax.swing.JOptionPane;

import main.Exceptions.CannotPlayMatchException;
import main.Purchaseables.Athlete;
import main.RandomEvents.RandomEvent;
import main.Teams.*;

/**
 * The main stadium class; displays the stadium menus from which the player can either play a match against an opposing team or take a bye.
 * Taking a bye also allows the player to select "special training" for a single Athlete, and has a chance of RandomEvents generating.
 * Both of these actions will advance the game state and increment the main environment's week counter.
*/

public class Stadium {

    /**
     * Random number generator.
    */
    Random random;
    /**
     * A randomly generated integer.
    */
    static int randomNum;
    /**
     * ArrayList of opposing teams the player may choose to face.
    */
    static ArrayList<OpposingTeam> opposingTeams;
    /**
     * The player's team.
    */
    static PlayerTeam currentPlayer;
    /**
     * The main game environment object.
    */
    static GameSetup gameSetup;
    /**
     * The total number of weeks for the game season.
    */
    static int totalWeeks;
    /**
     * A boolean which stores whether the player took a bye last week
     */
    static boolean byeLastWeek = false;

    /**
     * Constructor. Generates a series of OpposingTeams based on the number of weeks in the season.
     * @param player    PlayerTeam containing the player's athlete arrays.
     * @param e         The main game environment.
    */
    public Stadium(PlayerTeam player, GameSetup e) {
        // I am randomly generating the teams here but i think we need to randomly generate them at the start based on how many weeks
        // the player says they will play, so like 11 teams for 10 weeks, and then we only show up to a max of the random int i generate
        // so 11 teams but only show 3-5 until we get till final weeks (since we can only play a match once)
        random = player.getRandomGen();
        gameSetup = e;
        totalWeeks = gameSetup.getWeeks();
        opposingTeams = new ArrayList<OpposingTeam>();
        currentPlayer = player;
        randomNum = random.nextInt(3);
    }

    /**
     * Set the total number of weeks for the season.
     * @param int       Int representing total weeks.
    */
    public static void setTotalWeeks(int weeks){
        totalWeeks = weeks;
    }

    /**
     * Get the total number of weeks.
     * @return          Int representing total weeks.
    */
    public int getWeeks() {
        return totalWeeks;
    }

    /**
     * Displays the possible matches the player can engage in.
     * @return  ArrayList of OpposingTeams the player may compete against.
    */
    public ArrayList<OpposingTeam> matchesDisplay(){
        ArrayList <OpposingTeam> opposingTeamsMatches = new ArrayList<OpposingTeam>();
        randomNum = (random.nextInt(3)) + 3;
        if(randomNum <= totalWeeks - GameSetup.getCurrentWeek()){
            for (int i = 0; i < randomNum; i++){
                if (i < opposingTeams.size()) {
                    opposingTeamsMatches.add(opposingTeams.get(i));
                }
            }
            return opposingTeamsMatches;
        }
        else{
            return opposingTeams;
        }
    }

    /**
     * Sets up generation of OpposingTeam matches.
    */
    public static void genTeamsAtStart(){
        for (int i = 0; i < (totalWeeks+1) + randomNum; i++)
            opposingTeams.add(new OpposingTeam(gameSetup.getDifficulty(), currentPlayer.getRandomGen()));
    }

    /**
     * Checks all Athletes in the player's main team array for injuries.
     * @return      'True' if all Athletes in the team are injured, else 'false'.
    */
    public boolean checkIfPlayerTeamInjured(){
        Athlete[] team = currentPlayer.getAllTeam();
        for (Athlete athlete : team){
            if(!athlete.getInjured()){
                return false;
            }
        }
        return true;

    }

    /**
     * Checks if the player's team is not full.
     * @return      True if the player's main team contains any dummies, else False.
    */
    boolean playerTeamNotFull(){
        for (Athlete athlete: currentPlayer.getAllTeam()){
            if(athlete.isDummy()){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if player won their match.
     * @param player        The PlayerTeam that competed in the match.
     * @param winner        The Team that won the match.
     * @return              'True' if both Teams passed are the same.
    */
    public boolean playerWon(PlayerTeam player, Team winner){
        if (winner.equals(player)){
            return true;
        }
        return false;
    }

    /**
     * Generates a Match object based on the selected OpposingTeam and calls methods from Match to determine the winner.
     * Player is given rewards if they are victorious and the opponent is removed from the array of available matches.
     * @param player        The PlayerTeam to compete against the chosen opponent.
     * @throws CannotPlayMatchException
    */
    public String[] playMatch(PlayerTeam player, int opposition) throws CannotPlayMatchException{
        if (checkIfPlayerTeamInjured()){
            throw new CannotPlayMatchException("How do you expect to play with your team in this condition? Maybe you should take a bye.");
        } else if(playerTeamNotFull()){
            throw new CannotPlayMatchException("How do you expect to play without a full team? Visit the club and organize your members first.");
        }
        Match currenMatch = new Match(player, opposingTeams.get(opposition));
        String[] matchOutcome = currenMatch.compareTeams();
        opposingTeams.remove(opposition);
        gameSetup.advanceTime();
        byeLastWeek = false;
        return matchOutcome;
    }

    /**
     * 
     * Increments the week counter without playing a match.
     * Restores all player's Athletes to full HP and has a 1/4 chance to generate and activate a RandomEvent.
     * 
     * @param e         The main game environment.
     * @param player    PlayerTeam to restore HP for.
     * @throws CannotPlayMatchException
    */
    public void takeBye() throws CannotPlayMatchException{
        if (byeLastWeek){
            throw new CannotPlayMatchException("You took a bye last week! You should play a match, or your players will get bored.");
        }
        for (int i = 0; i <5; i++) {
            // Restore all HP to full
            currentPlayer.getTeamMember(i).setCurrentHP(currentPlayer.getTeamMember(i).getMaxHP());
            currentPlayer.getReserve(i).setCurrentHP(currentPlayer.getReserve(i).getMaxHP());
        }
        if (random.nextInt(4) == 0) {
            // Chance of random event activating
            RandomEvent event = RandomEvent.gen(currentPlayer);
            String outcome = event.activate(currentPlayer);
            JOptionPane.showMessageDialog(null, outcome, "*** Random Event!! ***", JOptionPane.PLAIN_MESSAGE);
        }
        byeLastWeek = true;
        gameSetup.advanceTime();
    }

    /**
     * Presents the player with the option for "special training" when taking a bye. Commencing training will give a selected Athlete a +5 Atk and Def bonus.
     * @param player    The player's team to select Athletes from.
     * @param chosenInteger The integer of the athlete in the array to use
    */
    public String specialTraining(PlayerTeam player, int chosenInteger) {
        Athlete chosenAthlete = player.getTeamMember(chosenInteger);
        chosenAthlete.setAtk(chosenAthlete.getAtk() + 5);
        chosenAthlete.setDef(chosenAthlete.getDef() + 5);
        String returnString = chosenAthlete.getName() + " battles through your rigorous training regimen and gains 5 points to their Atk and Def!\n" + chosenAthlete.getDescription();
        return returnString;
    }

}
