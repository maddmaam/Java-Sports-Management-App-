package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Random;

import main.Purchaseables.Athlete;
import main.RandomEvents.AthleteJoins;
import main.RandomEvents.AthleteQuits;
import main.RandomEvents.RandomEvent;
import main.RandomEvents.StatIncrease;
import main.Teams.PlayerTeam;

public class EventTest {
    
    private Random rand;
    private PlayerTeam player;

    @BeforeEach
    public void init() {
        rand = new Random();
        player = new PlayerTeam();
    }

    @Test
    public void genTest() {
        player.setRandomGen(12);
        RandomEvent testEvent = RandomEvent.gen(player);
        assertTrue(testEvent instanceof AthleteJoins);
        testEvent = RandomEvent.gen(player);
        assertTrue(testEvent instanceof StatIncrease);
        testEvent = RandomEvent.gen(player);
        assertTrue(testEvent instanceof AthleteJoins);
        for (Athlete a : player.getAllTeam()) {
            a.setCurrentHP(0);
        }
        testEvent = RandomEvent.gen(player);
        assertTrue(testEvent instanceof AthleteQuits);
        // Unlikely for AthleteQuits to gen if no players are injured
    }

    @Test
    public void joinTest() {
        RandomEvent testEvent = new AthleteJoins();
        assertTrue(player.getReserve(4).isDummy()); 
        testEvent.activate(player);
        assertFalse(player.getReserve(4).isDummy());        // Adds in last slot of reserves because all are dummies
        for (int i = 3; i > -1; i--) {
            testEvent.activate(player);
            assertFalse(player.getReserve(i).isDummy());
        }
        assertTrue(player.getTeamMember(4).isDummy());
        testEvent.activate(player);
        assertFalse(player.getTeamMember(4).isDummy());     // Reserves are full so adds in last slot of main team
        for (int i = 3; i > -1; i--) {
            testEvent.activate(player);
            assertFalse(player.getTeamMember(i).isDummy());
        }
        assertTrue(player.teamFull());
        String ex = "you don't have any room";
        assertTrue(testEvent.activate(player).contains(ex));     // Failure to add athlete
    }

    @Test
    public void leaveTest() {
        RandomEvent testEvent = new AthleteQuits();
        String ex = "Nothing happens";
        for (int i = 0; i < 5; i++) {
            player.addToTeam(Athlete.genAthlete(rand), i, false);
            player.addSingleToReserves(i, Athlete.genAthlete(rand));
        }
        assertTrue(testEvent.activate(player).contains(ex));    // No injured athletes
        player.getTeamMember(3).setCurrentHP(0);
        testEvent.activate(player);
        assertTrue(player.getTeamMember(3).isDummy());     // Injured athlete was removed
        player.getReserve(0).setCurrentHP(0);
        player.getReserve(4).setCurrentHP(0);        // Should remove first injury found
        testEvent.activate(player);
        assertTrue(player.getReserve(0).isDummy());
        assertFalse(player.getReserve(4).isDummy());       // Slot 4 athlete should still be there
    }

    @Test
    public void buffTest() {
        RandomEvent testEvent = new StatIncrease();
        String ex = "didn't";
        player.setRandomGen(421);
        assertTrue(testEvent.activate(player).contains(ex));    // All athletes are dummies
        for (int i = 0; i < 5; i++) {
            player.addToTeam(Athlete.genAthlete(player.getRandomGen()), i, false);
        }
        ex = "increased";
        int initDef = player.getTeamMember(1).getDef();
        assertTrue(testEvent.activate(player).contains(ex));
        assertEquals(initDef + 5, player.getTeamMember(1).getDef());
    }

}
