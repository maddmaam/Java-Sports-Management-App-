package main.Teams;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import main.Purchaseables.Athlete;
import main.Purchaseables.NameGen;

/**
 * An extension of Team which handles generation of opposing teams for the player to compete against.
 * Athletes generated for their teams gain extra stat boosts compared to the player's, and they reward money and points upon being defeated.
*/

public class OpposingTeam extends Team {

    /**
     * Money given to the player upon defeating this team.
    */
    private int prizeMoney = 75;
    /**
     * Season points given to the player upon defeating this team.
    */
    private int prizePoints = 3;
    /**
     * The current game difficulty.
    */
    private String difficulty;
    /**
     * Random number generator.
    */
    private Random rand;

    /**
     * Constructor. Stores the game difficulty from the main environment and begins generation of enemy team name and arrays.
     * @param diff       Game difficulty setting as a String; should be "Normal" or "Hard".
    */
    public OpposingTeam(String diff, Random randGen) {
        super();
        rand = randGen;
        difficulty = diff;
        genTeam();
        genName();
    }

    /**
     * Generates five random Athletes to populate the team's array.
     * On Normal difficulty, all Athletes have normal Atk and Def, and on Hard they receive an additional +5.
    */
    public void genTeam() {
        for (int i = 0; i < getSize(); i++) {
            Athlete newMember = Athlete.genAthlete(rand);
            if (difficulty.equals("Hard")) {
                newMember.setAtk(newMember.getAtk() + 5);
                newMember.setDef(newMember.getDef() + 5);
            }
            addToTeam(newMember, i, true);
        }
    }

    /**
     * Generates a randomized name using static methods from NameGen and uses it to set the team name.
    */
    public void genName() {
        String newName = genTeam(rand);
        setName(newName);
    }

        /**
     * Generates a randomized name for an OpposingTeam by combining an adjective and noun from adjectives.txt and nouns.txt.
     * If the files cannot be read, name defaults to "Bad Guys".
     * @return      String joining the selected adjective and noun.
    */
    public String genTeam(Random rand) {
        String adj;
        String noun;
        try {
        adj = read("adjectives.txt", 20, rand);
        noun = read("nouns.txt", 20, rand);
        } catch (IOException e) {
            // If files can't be read, name defaults to "Bad Guys"
            return "Bad Guys";
        }
        return adj + " " + noun;
    }

    /**
     * Reads txt files as given by gen() and genTeam(), and returns a random line within the specified bound.
     * If the file cannot be read, throws IOException.
     * @param filename      The name of the file to be read.
     * @param lines         The upper bound of possible lines to read.
     * @return              String containing the contents of a random line as chosen based on the specified bound.
    */
    public String read(String filename, int lines, Random rand) throws IOException {
        int line = 0;
        if (lines > 0)
            line = rand.nextInt(lines);
        String output = new String();
        InputStream in = getClass().getResourceAsStream(filename); 
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        for (int i = 0; i < line; i++) {
            output = reader.readLine();
        }
        //output = Files.readAllLines(Paths.get(filename)).get(line);
        // Probably inefficent but I don't want to spend forever on it
        return output;
    }

    /**
     * Returns the team name.
     * @return  Team name as a String.
    */
    public String toString(){
        return getName();
    }

    /**
     * Rewards the player with money and points based on the game difficulty.
     * On Normal difficulty, the player receives the full reward; on Hard, they receive only 2/3 of the amount.
     * @param player       The PlayerTeam to add the reward values to.
     * @return             String informing the player of their rewards.
    */
    public String givePrize(PlayerTeam player) {
        if (difficulty.equals("Normal")) {
            player.addMoney(prizeMoney);
            player.addPoints(prizePoints);
            return "For your victory, you receive $" + prizeMoney + " and " + prizePoints + " season points!!";
        } else {
            player.addMoney(prizeMoney - 25);
            player.addPoints(prizePoints - 1);
            return "For your victory, you receive $" + (prizeMoney - 25) + " and " + (prizePoints - 1) + " season points!!";
        }
    }

}
