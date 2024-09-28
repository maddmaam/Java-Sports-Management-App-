package main.Purchaseables;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import main.Teams.PlayerTeam;

/**
 * The main Athlete superclass; handles variables for all stats, names, and costs,
 * as well as provides a static method for generating new Athletes of a random specialization (subclass).
 * Athletes may be purchased by the player from the shop and stored in their team's arrays, or generated as members of opposing teams.
*/

public class Athlete implements Purchasable {

    /**
     * The name of the Athlete.
    */
    private String name;
    /**
     * The cost for the player to purchase the Athlete.
    */
    private double buyCost;
    /**
     * The money the player will receive if they choose to resell the Athlete.
    */ 
    private double sellCost;
    /**
     * 'True' if the Athlete has 0 current HP; otherwise 'false'.
    */
    private boolean injured;
    /**
     * The Athlete's Atk (Attack) stat.
    */
    private int atkStat;
    /**
     * The Athlete's Def (Defense) stat.
    */
    private int defStat;
    /**
     * The Athlete's maximum possible HP (Hit Points).
    */
    private int maxHP;
    /**
     * The Athlete's current HP (Hit Points).
    */
    private int currentHP;
    /**
     * 'True' if the Athlete is a "dummy" member; dummies represent empty array slots and are not intended for gameplay.
    */
    private boolean dummy;
    /**
     * Random number generator.
    */
    private Random rand;

    /**
     * Constructor. Sets the name, costs, and basic stats of a newly generated Athlete.
     * @param r     Random number generation object.
    */
    public Athlete(Random r) {
        rand = r;
        name = genName();
        buyCost = genCost();
        sellCost = buyCost - 20;
        // perhaps the sellcost could decrease porportionally to the length of the time played
        // i.e the value depreciates?
        injured = false;
        atkStat = genStat();
        defStat = genStat();
        maxHP = genStat() + 10;
        currentHP = maxHP;
        dummy = false;
    }

    /**
     * Designates the Athlete as a "dummy" member, representative of an empty slot in team arrays.
     * Dummies are named "Empty", have 0 Atk, 0 Def, and 1 HP. Players should not be able to interact with them,
     * including viewing stats, buying or selling, using items on them, or participating in matches while they are on the team.
    */
    public void setDummy() {
        // Dummy members represent an empty slot in team lineups
        // Make sure player can't compete in matches if there are dummies in their main team
        name = "Empty";
        buyCost = 0;
        sellCost = 0;
        atkStat = 0;
        defStat = 0;
        maxHP = 1;
        currentHP = 1;
        dummy = true;
    }

    /**
     * Checks if the Athlete is a "dummy".
     * @return      'True' if Athlete is a dummy, otherwise 'false'.
    */
    public boolean isDummy() {
        return dummy;
    }

    /**
     * Calls static methods of NameGen to generate the Athlete a random forename and surname from the available options.
     * @return      String containing generated name.
    */
    public String genName() {
        String gen = gen(rand);
        return gen;
    }

    /**
     * Returns a random number for the purpose of initial Atk/Def/HP generation.
     * Does not apply specialization bonuses; these are handled in subclass constructors.
     * @return      Randomly generated int from 1-10 (inclusive).
    */
    public int genStat() {
        // WIP: balance/replace placeholder values
        // Stats proportionate to the game difficulty? 
        int gen = rand.nextInt(10);
        return gen + 1;
    }

    /**
     * Returns a random number for the purpose of Athlete purchase cost generation.
     * Resale costs are subsequently determined by the constructor.
     * @return      Randomly generated double from 50-100 (inclusive).
    */
    public double genCost() {
        // WIP: adjust cost based on generated stats??
        double gen = rand.nextInt(100);
        if (gen < 50) {
            gen += 50;
        }
        return gen++;
    }

    /**
     * Gets Athlete's name.
     * @return      String containing Athlete's name.
    */
    public String getName() {
        return name;
    }

    /**
     * Gets the cost to buy Athlete at the shop or during initial game setup.
     * @return      Double representing Athlete's purchase cost.
    */
    public double getBuyCost() {
        return buyCost;
    }

    /**
     * Gets the amount of money the player receives if they resell the Athlete.
     * @return      Double representing Athlete's resale cost.
    */
    public double getSellCost() {
        return sellCost;
    }

    /**
     * Determines the Athlete's current field position using their index in the given array.
     * If the given Athlete is not in the Array, returns "N/A". Currently unused.
     * @param team      The array to check for position information.
     * @param member    The Athlete to retrieve information for.
     * @return          String containing the given Athlete's current position, or "N/A" if they are not found in the array.
    */
    public String getPosition(Athlete[] team, Athlete member) {
        // WIP: Needs better implementation, also is currently unused in general
        if (team[0] == member || team[1] == member)
            return "Frontline Offense";
        if (team[2] == member || team[3] == member)
            return "Midfield Defense";
        if (team[4] == member)
            return "Rear Guard";
        else return "N/A";
    }

    /**
     * Checks if the Athlete is considered "injured" (O HP). Injured Athletes may prevent a player competing in matches.
     * @return      Whether the Athlete is injured or not.
    */
    public boolean getInjured() {
        return injured;
    }

    /**
     * Gets Athlete's Atk stat.
     * @return      Int representing Athlete's Atk stat.
    */
    public int getAtk() {
        return atkStat;
    }

    /**
     * Sets Athlete's Atk stat to a new value.
     * @param newAtk    Int representing the Athlete's new Atk value.
    */
    public void setAtk(int newAtk) {
        atkStat = newAtk;
    }

    /**
     * Gets Athlete's Def stat.
     * @return      Int representing Athlete's Def stat.
    */
    public int getDef() {
        return defStat;
    }

    /**
     * Sets Athlete's Def stat to a new value.
     * @param newDef    Int representing the Athlete's new Def value.
    */
    public void setDef(int newDef) {
        defStat = newDef;
    }

    /**
     * Gets a String readout of Athlete's HP for displaying to the player.
     * @return      String containing Athlete's HP in the format "current/maximum".
    */
    public String getHP() {
        return currentHP + "/" + maxHP;
    }

    /**
     * Gets Athlete's maximum HP stat.
     * @return      Int representing Athlete's maximum HP.
    */
    public int getMaxHP() {
        return maxHP;
    }

    /**
     * Sets Athlete's maximum HP to a new value.
     * @param newHP     Int representing the Athlete's new maximum HP threshold.
    */
    public void setMaxHP(int newHP) {
        maxHP = newHP;
    }

    /**
     * Gets Athlete's current HP stat.
     * @return      Int representing Athlete's current HP.
    */
    public int getCurrentHP() {
        return currentHP;
    }

    /**
     * Sets Athlete's current HP to a new value. Current HP may not exceed the Athlete's maximum nor fall below 0.
     * If the given value is higher than the Athlete's maximum HP, their current HP will instead be set to the maximum. 
     * If the value is below 0, their current HP will be set to 0, the Athlete will be designated "injured", and the player will recieve a warning message.
     * @param newHP     Int representing the Athlete's indended new HP.
     * @return          String describing the changes to Athlete's HP.
    */
    public String setCurrentHP(int newHP) {
        if (newHP > maxHP) {
            injured = false;
            currentHP = maxHP;
            return name + "'s HP is now " + getHP() + ".";
        } else if (newHP <= 0) {
            injured = true;
            currentHP = 0;
            return name + "'s HP is " + getHP() + "!! They have been seriously injured and can no longer compete!";
        } else {
            injured = false;
            currentHP = newHP;
            return name + "'s HP is now " + getHP() + ".";
        }
    }

    /**
     * Gets Athlete's specialization. Overriden by subclasses; if the Athlete is not one of the subclasses designating a specialization,
     * "No Specialization" is provided instead.
     * @return      String containing "No Specialization".
    */
    public String getSpecialization() {
        // None if they are not one of the subclass types
        return "No Specialization";
    }

    /**
     * Formats a readout of the Athlete's name, specialization, and stats for display to the player through the club and shop.
     * @return      String containing Athlete's name, specialization, stats in a readable format.
    */
    public String getDescription() {
        String desc = "\nAthlete Name: " + name + "\n"
                    +" | Specialization: " + getSpecialization() + "\n"
                    +" | ATK: " + getAtk() + " | DEF: " + getDef() + " | HP: " + getHP();
        return desc;
    }

    /**
     * Prints the results of getDescription() followed by a similarly formatted readout of Athlete's purchase and resale costs.
    */
    public String getPurchaseDetails() {
        String purchaseDetail = getDescription() + "\nBuy for: " + getBuyCost() + "\nSell for: " + getSellCost() + "\n";
        return purchaseDetail;
    }

    /**
     * Unused skeleton method to process the player's purchase of the Athlete. Withdraws funds, allows player to set the Athlete's name,
     * and (unimplemented) adds them to one of the player's team arrays.
     * @param player        PlayerTeam containing the money and arrays required for processing.
    */
    public void buy(PlayerTeam player) {
        // Let player buy an athlete, nickname them, and add them to their roster
        // Currently unused ??
        player.takeMoney(buyCost);
    }

    /**
     * toString override; returns Athlete's name.
     * @return      Athlete's name as a string.
    */
    @Override
    public String toString(){
        return getName();
    }

    /**
     * Static method; randomly generates a new Athlete object from one of the three possible subclasses, with (relatively) equal likelihoods of each.
     * @param rand  Random number generator object.
     * @return      Either a newly-generated Attacker, Defender, or All-Rounder.
    */
    public static Athlete genAthlete(Random rand) {
        // Static method; generate a new athlete with a random specialization
        Athlete newAthlete;
        int roll = rand.nextInt(100);
        if (roll < 33) {
            newAthlete = new Attacker(rand);
        } else if (roll < 66) {
            newAthlete = new Defender(rand);
        } else {
            newAthlete = new AllRounder(rand);
        }
        return newAthlete;
    }

        /**
     * Static method; generates a randomized forename and surname for a new Athlete by combining choices from forenames.txt and surnames.txt.
     * If the files cannot be read, the name defaults to "John Smith".
     * @return      String joining the selected forename and surname.
    */
    public String gen(Random rand) {
        String forename;
        String surname;
        try {
        forename = read("forenames.txt", 60, rand);
        surname = read("surnames.txt", 60, rand);
        } catch (IOException e) {
            // If files can't be read, name defaults to "John Smith"
            return "John Smith";
        }
        return forename + " " + surname;
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

}
