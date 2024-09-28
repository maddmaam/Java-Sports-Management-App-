package main;
import main.GUI.*;
import java.util.Random;
import main.Teams.*;

/**
 * The class managing the main game environment. The environment handles the game's weekly progression and eventual completion.
 * It also stores any key information related to the game state that is not held by PlayerTeam, including the selected difficulty
 * and references to the current instances of the shop, stadium, and club.
*/

public class Environment {
    // Main game environment! Starts the game, handles player moving between areas,
    // time progression and subsequent store/match generation/updates, etc., completion tasks once game is over

    /**
     * PlayerTeam handling the player's Athletes, Items, and other key information.
    */
    protected static PlayerTeam player;
    /**
     * The game's difficulty level; affects stat generation of enemy athletes.
    */
    protected String difficulty = "Unknown";
    /**
     * The total number of weeks that the game season will run for.
    */
    protected static int totalWeeks = 0;
    /**
     * The current week of the game season. The game will end once this exceeds totalWeeks.
    */
    protected static int currentWeek = 0;
    /**
     * The current instance of the Club class.
    */
    protected Club currentClub;
    /**
     * The current instance of the Shop class. This is replaced with a newly generated version each week.
    */
    protected Shop currentShop;
    /**
     * The current instance of the Stadium class.
    */
    protected static Stadium currentStadium;
    /**
     * Shop UI object.
    */
    protected ShopGUI shopUI;
    /**
     * Club UI object.
    */
    protected ClubGUI clubGUI;
    /**
     * Stadium UI object.
    */
    protected StadiumJFrame stadiumUI;
    // Update shop and stadium each week
    /**
     * Whether the game state is ready to progress to the next week of the season.
    */
    private static boolean progress;
    /**
     * Random number generator; seed either set by the player or randomly on PlayerTeam creation.
    */
    protected Random rand;
    /**
     * Checked by the end game screen to detemine if the player "won" or not.
    */
    private boolean victory;

    /**
     * Constructor. Generates a new PlayerTeam at game start and begins the currentWeek counter.
    */
    public Environment() {
        // Constructor and introductory text\
        if(player == null) // only executes on first contstruction of the class
            player = new PlayerTeam();
        rand = player.getRandomGen();
        if(currentWeek == 0) // only executes on first contstruction of the class
            currentWeek = 1;
        currentClub = new Club(player);
        
    }

    /**
     * Increments the currentWeek counter and generates a new currentShop.
     * Proceeds immediately to the game ending if either currentWeek > totalWeeks OR all of the player's Athletes are injured.
    */
    public void advanceTime() {
        currentWeek += 1;
        int fail = 0;
        for (int i = 0; i < 5; i++) {
            if (player.getTeamMember(i).getInjured() | player.getTeamMember(i).isDummy())
                fail += 1;
            if (player.getReserve(i).getInjured() | player.getReserve(i).isDummy())
                fail += 1;
        }
        if (fail == 10 && player.getMoney() < 100) {
            victory = false;
            endGame();
        } else if (currentWeek > totalWeeks) {
            victory = true;
            endGame();
        } else {
            currentShop = new Shop(player);
        }
    }

    /*
     * Returns the current week int variable in Environment Class.
     * @return      Int representing current week.
     */
    public static int getCurrentWeek(){
        return currentWeek;
    }

    /**
     * Returns total weeks. For testing.
     * @return      Int representing total season weeks.
    */
    public static int getTotalWeeks(){
        return totalWeeks;
    }

    /**
     * Returns player's Team object.
     * @return      Current PlayerTeam.
    */
    public PlayerTeam getPlayer() {
        return player;
    }

    /*
     * Sets total weeks. For testing.
     * @param weeks     Total weeks.
    */
    public void setTotalWeeks(int weeks) {
        totalWeeks = weeks;
    }

    /**
     * Set current week to a specific value. For testing purposes.
     * @param new       Integer to set current week to.
    */
    public void setCurrentWeek(int n) {
        if (n > 0 && n <= totalWeeks)
            currentWeek = n;
    }

    /**
     * Function to choose which action you would like to undertake:
     * <p>&bull; Visit Shop
     * &bull; Visit Club
     * &bull; Visit Stadium
     * @param choice Integer of which choice you want, parsed through a switch statement.
     * @throws IllegalArgumentException Throws this if you do not set choice parameter within the bounds [1,3]
     */
    public void chooseAction(int choice) throws IllegalArgumentException {
        // Prompt player to choose an action/visit an area
        setProg(false);
        switch(choice){
        // everytime this is called the weeks advance, they should only do that if you visit the stadium (need to fix)
            case 1:
                shopUI = new ShopGUI(currentShop, player, this);
                shopUI.setVisible(true);
                break;
            case 2:
                clubGUI = new ClubGUI(player, currentClub, this);
                break;
            case 3:
                stadiumUI = new StadiumJFrame(currentStadium, this);
                break;
            default:
                throw new IllegalArgumentException("Choice is out of bounds");
        }
    }

    /**
     * Updates progress variable; will increment the week counter and advance the game state when set to 'true'.
     * @param newProg       The desired new progress state.
    */
    public static void setProg(boolean newProg) {
        progress = newProg;
    }

    /**
     * Gets the random number generator.
     * @return      Random number generator.
    */
    public Random getRandomGen() {
        return rand;
    }

    /**
     * Gets the player's Team object. For testing purposes.
     * @return      Current instance of PlayerTeam.
    */
    public PlayerTeam getPlayerTeam() {
        return player;
    }

    /**
     * This function creates a new End Game GUI that displays the players statistics from their time playing 
     * the game.
     */
    public void endGame() {
        String endGameString = "";
        if (!victory) {
            endGameString = "Critically low on strength, money, and morale, you and your team are forced to end the season early." +
            "\nWeeks Played: " + totalWeeks 
            + "\nMoney Earned: $" + player.getMoney() + "0" 
            + "\nPoints Gained: " + player.getPoints();;
        } else {
            endGameString = "Congratulations on surviving the season and thank you for playing!! Your final statistics are as follows:" +
            "\nWeeks Played: " + totalWeeks 
            + "\nMoney Earned: $" + player.getMoney() + "0" 
            + "\nPoints Gained: " + player.getPoints();
        }
        new EndGameScreen(endGameString, player.getName());
    }
    
}