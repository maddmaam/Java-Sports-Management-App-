package main;

import main.Exceptions.InvalidNameException;
import javax.swing.JOptionPane;

import main.GUI.*;
import main.Teams.*;

/**
 * An extension of the main Environment class; handles the initial game setup tasks such as setting the game difficulty and length and
 * generating the player's initial team.
*/

public class GameSetup extends Environment {

    /**
     * Game setup GUI object.
    */
    private StartAppGUI startGUI;
    
    /**
     * Constructor. Starts the GUI, generates the initial state of the stadium, and begins inital generation of the player's Athletes.
    */
    public GameSetup(){
        super();
        startGUI = new StartAppGUI();
        startGUI.startFrame.setVisible(true);
        SetupGUI.setGameEnvironment(this);
        super.currentStadium = new Stadium(player, this);
    }
    
    /**
     * Sets the game difficulty. Will affect the player's initial funds and stat generation for enemy Athletes.
     * @param difficulty        String containing the selected difficulty as chosen by the player.
    */
    public void setDifficulty(String difficulty){
        if (difficulty.equalsIgnoreCase("normal"))
            super.difficulty = "Normal";
        else    
            super.difficulty = "Hard";
    }

    /**
     * Gets the stored difficulty setting.
     * @return      String containing either "Normal" or "Hard".
    */
    public String getDifficulty(){
        return difficulty;
    }

    /**
     * Checks if player's team name is within acceptable bounds (3-15 characters).
     * @param teamName      Intended team name.
     * @throws InvalidNameException
    */
    public void setTeamName(String teamName) throws InvalidNameException{
        if(teamName.length() < 3 || teamName.length() > 15)
            throw new InvalidNameException("Your name must be between 3 and 15 characters.");
        super.player.setName(teamName);
    }

    /**
     * Set the game length.
     * @param weeks     Number of weeks for the season to last.
    */
    public void setLength(int weeks) {
        // Prompt player to enter game length (in weeks)
        super.totalWeeks = weeks;
        //this is a good place for a JUnit test because can assert equals that the weeks equals
        // the integer being parsed to the function
    }

    /**
     * Gets the total number of weeks the game season will last.
     * @return      Int representing the final week of the season.
    */
    public int getWeeks(){
        return super.totalWeeks;
    }

    /**
     * Gets the current week of the game season.
     * @return      Int representing the current week.
    */
    public static int getCurrentWeek(){
        return currentWeek;
    }
    
    /**
     * Generates a Shop, sets the player's starting funds, and prompts player to purchase Athletes until their arrays are full.
    */
    public void createPlayerTeam(){
        // Have increased starting money to avoid situations where you can't afford a full starting lineup
        super.currentShop = new Shop(player, 8);
        if (difficulty.equalsIgnoreCase("normal"))
            player.setStartingMoney(1200);
        if (difficulty.equalsIgnoreCase("hard"))
            player.setStartingMoney(1000);
    }

    /**
     * WIP: GUI JAVADOC ???
    */
    public void buyStartingAthletes() {
        // need to find a way to call the GUI the required number of times until the player has filled out their team

        // perhaps i just make it so when the team is full the windows will close?
        // could also create a new BUYUI everytime i buy something so it removes a choice from the athletes list, that way then it will check if the team is full
        // and wont show it after a certain number of times - While loop here doesnt work
        //PopUpGUI popUp = new PopUpGUI("Let's get started by hiring some athletes for the team!", "Alert", true);
        JOptionPane.showMessageDialog(null, "Let's get started by hiring some athletes for the team!", "Welcome!", JOptionPane.PLAIN_MESSAGE);
        super.chooseAction(1);
    }

    /**
     * Set the random number generator to an unspecified seed.
    */
    public void setSeed(){
        player.setRandomGen();
        super.rand = player.getRandomGen();
    }

    /**
     * Set the random number generator to the given seed.
     * @param seed      Int for random number gen.
    */
    public void setSeed(int seed){
        player.setRandomGen(seed);
        super.rand = player.getRandomGen();
    }

    /**
     * Launches the game!!
    */
    public static void main(String[] args) {
        // note to self we should make all calls to environment methods within this class because it is an extension of the 
        // super class
        GameSetup setup = new GameSetup();
    }
}
