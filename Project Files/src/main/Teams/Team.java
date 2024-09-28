package main.Teams;

import java.util.*;

import main.Purchaseables.Athlete;

/**
 * The abstract superclass for PlayerTeam and OpposingTeam. The superclass handles methods for storing the team's name and active Athletes.
 * Player-specific functions including reserve members and Item inventory are handled by the PlayerTeam subclass.
*/

public abstract class Team {
    
    // Team class, superclass for both player and enemy teams; contains team name and active players

    // Can we add a way for there to be a universal ranking of each team, so the player can be displayed their team rank
    // and then they can also see the team rank of others and so they can choose which team they would rather play off against

    /**
     * The team's name.
    */
    private String name;
    /**
     * The team's size. Set to 5 by constructor.
    */
    private int size;
    /**
     * Main team array for storing Athletes.
    */
    protected Athlete[] inTeam;
    /**
     * Team strength represented as an integer.
    */
    private int teamStrength;
    /**
     * Integer array for calculating team's overall stats.
    */
    int[] overallStats;
    /**
     * Random number generator.
    */
    private Random rand;

    /**
     * Constructor.
    */
    public Team() {
        name = "Unnamed Team";
        size = 5;
        inTeam = new Athlete[size];
        setRandomGen();
    }

    /**
     * Gets team name.
     * @return      String containing team name.
    */
    public String getName() {
        return name;
    }

    /**
     * Sets team name.
     * @param newName   Desired new name.
    */
    public void setName(String newName)  {
        
        name = newName;
    }

    /**
     * Allows the player to set a desired seed for random number generation in certain elements of the game.
     * @param newSeed       Int representing desired random seed.
    */
    public void setRandomGen(int seed) {
        rand = new Random(seed);
    }

    /**
     * Starts a random number generator for generation of random events, Items, etc. during team generation.
     * May be overriden by a specific seed given by the player if desired.
    */
    public void setRandomGen() {
        rand = new Random();
    }

    /**
     * Gets the number generator object.
     * @return      Random number generator.
    */    
    public Random getRandomGen() {
        return rand;
    }

    /**
     * Gets length of main team array. Always set to 5 by default.
     * @return         Int representing size of main Athlete[] array. Should always be 5. 
    */
    public int getSize() {
        // Return team size (cannot be changed)
        return size;
    }

    /**
     * Gets a specific Athlete from the given slot.
     * @param slot      The array index to retrieve an Athlete from.
     * @return          The Athlete found in the main team at the specified slot.
    */
    public Athlete getTeamMember(int slot) {
        return inTeam[slot];
    }

    /**
     * Generates a dummy and replaces the given Athlete in the team array with them.
     * Use with caution!
     * @param member        The Athlete to be replaced.
    */
    public void removeMember(Athlete member) {
        // Redundant?????? Remove? Maybe? Not sure.
        Athlete dummy = new Athlete(rand);
        dummy.setDummy();
        inTeam[Arrays.asList(inTeam).indexOf(member)] = dummy;
    }

    /**
     * Gets the entire team array.
     * @return          Athlete[] array containing the entire active team.
    */
    public Athlete[] getAllTeam() {
        return inTeam;
    }

    /**
     * Adds an Athlete to the active team. Outside of initial setup, slots already containing a non-dummy Athlete cannot be overwritten.
     * @param newMember         The new Athlete to be added.
     * @param slot              The slot to add the given Athlete at.
     * @param firstSetup        'True' if being called during initial game setup.
    */
    public void addToTeam(Athlete newMember, int slot, boolean firstSetup) {
        // This is for adding from the shop, not swapping
        if (!firstSetup){
            if (inTeam[slot].isDummy())
                inTeam[slot] = newMember;
        }
        else{
            inTeam[slot] = newMember;
        }
    }

    /**
     * Completely overwrites the given slot with the given Athlete. Use with extreme caution, as the previous occupant will be discarded.
     * @param array         The array for the Athlete to be added to.
     * @param slot          The index for the Athlete to be added to in the given array.
     * @param athlete       The Athlete to be added.
    */
    public void overwriteSlot(Athlete[] array, int slot, Athlete athlete) {
        // Will completely replace the previous occupant of slot, use with care!!
        array[slot] = athlete;
    }

    /**
     * Calculates overall team strength by summing Athlete stats.
     * @param athlete           The Athlete to get stat numbers from.
     * @param currentStrength   The value to be added to.
     * @param currentStat       The stat to be calulated.
    */
    public int genTeamStrength(Athlete athlete, int currentStrength, int currentStat){
        switch(currentStat){
            case 1:
                currentStrength += athlete.getDef();
                break;
            case 2:
                currentStrength += athlete.getMaxHP();
                break;
            default:
                currentStrength += athlete.getAtk();
                break;
        }
        return currentStrength;
    }

    /**
     * Calculates overall team stats across the entire Athlete array with support from genTeamStrength(...).
     * @return      Integer array of results.
    */
    public int[] getTeamStrength(){
        overallStats = new int[3];
        for (int i = 0; i < 3; i++){
            int currentStrength = 0;
            for (Athlete athlete : inTeam){
                currentStrength = genTeamStrength(athlete, currentStrength, i);
            }
            overallStats[i] = currentStrength;
        }
        return overallStats;
    }

    /**
     * Gets names of all members of the Athlete array along with their position numbers and specialization.
     * @return      String containing names, specializations, and slot numbers of all Athletes.
    */
    public String printTeam(){
        String s = "Current Team:";
        for (int i = 0; i < inTeam.length; i++)
            s += "\n" + (i+1) + ". " + inTeam[i].getName() +", "+ inTeam[i].getSpecialization();
        return s; 
    }

    /**
     * Formats the overall team stats as generated by getTeamStrength().
     * @return      String containing team stats formatted for readability.
    */
    public String printTeamStats(){
        int[] stats = getTeamStrength();
        try {
            return "   Attack: " + stats[0] + "\n   Defence: " + stats[1] + "\n   Total HP: " +  stats[2];
        } catch (IndexOutOfBoundsException e) {
            return "The stats weren't generated correctly, and something went wrong. Sorry.";
        }
        
    }

}