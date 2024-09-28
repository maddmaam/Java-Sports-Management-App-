package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Random;
import java.util.ArrayList;

import main.*;
import main.Exceptions.CannotPlayMatchException;
import main.Purchaseables.Athlete;
import main.Teams.*;

public class StadiumTest {
    
    private Random rand;
    private Stadium stadium;
    private PlayerTeam player;
    private GameSetup e;
    
    @BeforeEach
    public void init() {
        rand = new Random();
        player = new PlayerTeam();
        e = new GameSetup();
        stadium = new Stadium(player, e);
    }

    @Test
    public void weeksTest() {
        stadium.setTotalWeeks(6);
        assertEquals(stadium.getWeeks(), 6);
    }

    @Test
    public void matchTest() {
        stadium.setTotalWeeks(5);           // Lower bound
        e.setCurrentWeek(1);
        stadium.genTeamsAtStart();
        ArrayList<OpposingTeam> matches = stadium.matchesDisplay();
        assertTrue(matches.size() >= 3 && matches.size() <= 8);

        stadium.setTotalWeeks(15);           // Upper bound
        stadium.genTeamsAtStart();
        ArrayList<OpposingTeam> matches2 = stadium.matchesDisplay();
        assertTrue(matches2.size() >= 3 && matches2.size() <= 8);
    }

    @Test
    public void injuryTest() {
        for (int i = 0; i < 5; i++) {
            player.addToTeam(Athlete.genAthlete(rand), i, false);
        }
        assertFalse(stadium.checkIfPlayerTeamInjured());
        assertFalse(player.getTeamMember(0).getInjured());
        assertFalse(player.getTeamMember(1).getInjured());
        player.getTeamMember(0).setCurrentHP(0);
        assertTrue(player.getTeamMember(0).getInjured());
        assertFalse(player.getTeamMember(1).getInjured());
        assertFalse(stadium.checkIfPlayerTeamInjured());
        for (int i = 0; i < 5; i++) {
            player.getTeamMember(i).setCurrentHP(0);
        }
        assertTrue(stadium.checkIfPlayerTeamInjured());
    }

    @Test
    public void winCheckTest() {
        assertTrue(stadium.playerWon(player, player));
        OpposingTeam enemy = new OpposingTeam("Normal", rand);
        assertFalse(stadium.playerWon(player, enemy));
    }

    @Test
    public void playTest() {
        for (int i = 0; i < 5; i++) {
            player.addToTeam(Athlete.genAthlete(rand), i, false);
            player.getTeamMember(i).setAtk(100);
            player.getTeamMember(i).setDef(100);
        }
        stadium.setTotalWeeks(10);
        e.setCurrentWeek(1);
        e.setTotalWeeks(10);
        stadium.genTeamsAtStart();
        String ex = "You won";
        String[] results;
        try {
            results = stadium.playMatch(player, 0);
            assertTrue(results[5].contains(ex));
        } catch (CannotPlayMatchException e1) {
            e1.printStackTrace();
        }
        
        for (int i = 0; i < 5; i++) {
            player.getTeamMember(i).setAtk(1);
            player.getTeamMember(i).setDef(1);
        }
        try {
            results = stadium.playMatch(player, 0);
            assertFalse(results[5].contains(ex));
        } catch (CannotPlayMatchException e1) {
            e1.printStackTrace();
        }
        
    }

    @Test
    public void byeTest() {
        for (int i = 0; i < 5; i++) {
            player.addToTeam(Athlete.genAthlete(rand), i, false);
            player.getTeamMember(i).setCurrentHP(player.getTeamMember(i).getCurrentHP() - 1);
            assertNotEquals(player.getTeamMember(i).getCurrentHP(), player.getTeamMember(i).getMaxHP());
        }
        e.setCurrentWeek(1);
        e.setTotalWeeks(10);
        try {
            stadium.takeBye();
        } catch (CannotPlayMatchException e1) {
            e1.printStackTrace();
        }
        // All should be healed
        for (Athlete a : player.getAllTeam()) {
            assertEquals(a.getCurrentHP(), a.getMaxHP());
        }
        assertEquals(2, e.getCurrentWeek());
        // Week advanced
    }

    @Test
    public void trainingTest() {
        // WIP
    }

}
