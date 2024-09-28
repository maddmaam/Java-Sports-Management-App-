package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.Match;
import main.Purchaseables.Athlete;
import main.*;
import main.Teams.*;

public class MatchTest {
    
    private PlayerTeam playerTeam;
    private OpposingTeam enemyTeam;
    private Match testMatch;

    @BeforeEach
    public void init() {
        playerTeam = new PlayerTeam();
        enemyTeam = new OpposingTeam("Normal", playerTeam.getRandomGen());
        for (int i = 0; i < 5; i++) {
            playerTeam.addToTeam(Athlete.genAthlete(playerTeam.getRandomGen()), i, false);
            enemyTeam.addToTeam(Athlete.genAthlete(playerTeam.getRandomGen()), i, false);
        }
        testMatch = new Match(playerTeam, enemyTeam);
    }

    @Test
    public void compPlayerWins() {
        // Player Athlete has 5 Atk, 5 Def
        playerTeam.getTeamMember(0).setAtk(5);
        playerTeam.getTeamMember(0).setDef(5);
        // Enemy Athlete has 4 Atk, 4 Def
        enemyTeam.getTeamMember(0).setAtk(4);
        enemyTeam.getTeamMember(0).setDef(4);
        // Player Atk vs enemy Def
        assertTrue(testMatch.compareAthletes(0, "Atk"));
        // Player Def vs enemy Atk
        assertTrue(testMatch.compareAthletes(0, "Def"));
        // Both directions
        assertTrue(testMatch.compareAthletes(0, "Both"));
    }

    @Test
    public void compEnemyWins() {
        // Player Athlete has 4 Atk, 4 Def
        playerTeam.getTeamMember(0).setAtk(4);
        playerTeam.getTeamMember(0).setDef(4);
        // Enemy Athlete has 5 Atk, 5 Def
        enemyTeam.getTeamMember(0).setAtk(5);
        enemyTeam.getTeamMember(0).setDef(5);
        // Player Atk vs enemy Def
        assertFalse(testMatch.compareAthletes(0, "Atk"));
        // Player Def vs enemy Atk
        assertFalse(testMatch.compareAthletes(0, "Def"));
        // Both directions, player should still lose unless both stats are higher
        assertFalse(testMatch.compareAthletes(0, "Both"));
        playerTeam.getTeamMember(0).setAtk(6);
        assertFalse(testMatch.compareAthletes(0, "Both"));
        playerTeam.getTeamMember(0).setAtk(4);
        playerTeam.getTeamMember(0).setDef(6);
        assertFalse(testMatch.compareAthletes(0, "Both"));
    }

    @Test
    public void compEqual() {
        // Player Athlete has 5 Atk, 5 Def
        playerTeam.getTeamMember(0).setAtk(5);
        playerTeam.getTeamMember(0).setDef(5);
        // Enemy Athlete has 5 Atk, 5 Def
        enemyTeam.getTeamMember(0).setAtk(5);
        enemyTeam.getTeamMember(0).setDef(5);
        // Player Atk vs enemy Def
        assertFalse(testMatch.compareAthletes(0, "Atk"));
        // Player Def vs enemy Atk
        assertFalse(testMatch.compareAthletes(0, "Def"));
        // Both directions
        assertTrue(testMatch.compareAthletes(0, "Both"));
    }

    @Test
    public void matchPlayerWins() {
        // Player team should be stronger
        for (int i = 0; i < 5; i++) {
            playerTeam.getTeamMember(i).setAtk(100);
            playerTeam.getTeamMember(i).setDef(100);
        }
        String ex = "You won";
        assertTrue(testMatch.compareTeams()[5].contains(ex));
        assertEquals(5, testMatch.getScore());
    }

    @Test
    public void matchNarrowWin() {
        // Player should win match with only 3/5 slots
        for (int i = 0; i < 3; i++) {
            playerTeam.getTeamMember(i).setAtk(100);
            playerTeam.getTeamMember(i).setDef(100);
        }
        for (int i = 3; i < 5; i++) {
            playerTeam.getTeamMember(i).setAtk(0);
            playerTeam.getTeamMember(i).setDef(0);
        }
        String ex = "You won";
        assertTrue(testMatch.compareTeams()[5].contains(ex));
        assertEquals(3, testMatch.getScore());
    }

    @Test
    public void matchNarrowLos() {
        // Player should lose match with 2/5 slots
        for (int i = 0; i < 3; i++) {
            playerTeam.getTeamMember(i).setAtk(0);
            playerTeam.getTeamMember(i).setDef(0);
        }
        for (int i = 3; i < 5; i++) {
            playerTeam.getTeamMember(i).setAtk(100);
            playerTeam.getTeamMember(i).setDef(100);
        }
        String ex = "You lost";
        assertTrue(testMatch.compareTeams()[5].contains(ex));
        assertEquals(2, testMatch.getScore());
    }

    @Test
    public void matchEnemyWins() {
        // Enemy team should be stronger
        for (int i = 0; i < 5; i++) {
            enemyTeam.getTeamMember(i).setAtk(100);
            enemyTeam.getTeamMember(i).setDef(100);
        }
        String ex = "You lost";
        assertTrue(testMatch.compareTeams()[5].contains(ex));
        assertEquals(0, testMatch.getScore());
    }

    @Test
    public void atkSlotTest() {
        // Test compareSlots method on slots 0-1
        // Player Athlete has 5 Atk, 5 Def
        playerTeam.getTeamMember(0).setAtk(5);
        playerTeam.getTeamMember(0).setDef(5);
        // Enemy Athlete has 4 Atk, 4 Def
        enemyTeam.getTeamMember(0).setAtk(4);
        enemyTeam.getTeamMember(0).setDef(4);
        // Player Atk vs enemy Def
        String expected = "attacks succesfully!!";
        String outcome = testMatch.compareSlots(0);
        assertTrue(outcome.contains(expected));
        assertEquals(1, testMatch.getScore());
        // Now player Athlete has 4 Atk, 4 Def
        playerTeam.getTeamMember(1).setAtk(4);
        playerTeam.getTeamMember(1).setDef(4);
        // Enemy Athlete has 5 Atk, 5 Def
        enemyTeam.getTeamMember(1).setAtk(5);
        enemyTeam.getTeamMember(1).setDef(5);
        // Player Atk vs enemy Def
        String expected2 = "blocked!!";
        assertTrue(testMatch.compareSlots(1).contains(expected2));
        assertEquals(1, testMatch.getScore());
    }

    @Test
    public void defSlotTest() {
        // Test compareSlots method on slots 2-3
        // Player Athlete has 5 Atk, 5 Def
        playerTeam.getTeamMember(2).setAtk(5);
        playerTeam.getTeamMember(2).setDef(5);
        // Enemy Athlete has 4 Atk, 4 Def
        enemyTeam.getTeamMember(2).setAtk(4);
        enemyTeam.getTeamMember(2).setDef(4);
        // Player Def vs enemy Atk
        String expected = "succesfully!!";
        assertTrue(testMatch.compareSlots(2).contains(expected));
        assertEquals(1, testMatch.getScore());
        // Now player Athlete has 4 Atk, 4 Def
        playerTeam.getTeamMember(3).setAtk(4);
        playerTeam.getTeamMember(3).setDef(4);
        // Enemy Athlete has 5 Atk, 5 Def
        enemyTeam.getTeamMember(3).setAtk(5);
        enemyTeam.getTeamMember(3).setDef(5);
        // Player Def vs enemy Atk
        String expected2 = "isn't strong enough";
        assertTrue(testMatch.compareSlots(3).contains(expected2));
        assertEquals(1, testMatch.getScore());
    }

    @Test
    public void finalSlotTest() {
        // Test compareSlots method on slot 4

    }

    @Test
    public void postgamePlayerWon() {
        // Postgame where player wins, athletes lose 2 HP
        Integer[] listHP = new Integer[5];
        for (int i = 0; i < 5; i++) {
            listHP[i] = playerTeam.getTeamMember(i).getCurrentHP();
        }
        String expected = "\nYou won the match!!";
        assertTrue(testMatch.postGame(true).contains(expected));
        for (int i = 0; i < 5; i++) {
            assertEquals((listHP[i] - 2), playerTeam.getTeamMember(i).getCurrentHP());
        }
    }

    @Test
    public void postgamePlayerLost() {
        // Postgame where player loses, athletes lose 5 HP
        Integer[] listHP = new Integer[5];
        for (int i = 0; i < 5; i++) {
            listHP[i] = playerTeam.getTeamMember(i).getCurrentHP();
        }
        String expected = "\nYou lost the match! The team are exhausted and lose a significant amount of HP. Make sure to check their status at the club later.";
        assertEquals(expected, testMatch.postGame(false));
        for (int i = 0; i < 5; i++) {
            assertEquals((listHP[i] - 5), playerTeam.getTeamMember(i).getCurrentHP());
        }
    }

    @Test
    public void printTest() {
        // Test stat readout... kind of redundant
        Athlete playerAthlete = playerTeam.getTeamMember(4);
        Athlete enemyAthlete = enemyTeam.getTeamMember(4);
        String expected = "[ " + playerAthlete.getName() + "'s ATK: " + playerAthlete.getAtk() + ", DEF: " + playerAthlete.getAtk() +
        " | Opponent " + enemyAthlete.getName() + "'s ATK: " + enemyAthlete.getAtk() + ", DEF: " + enemyAthlete.getDef() + " ]";
        assertEquals(expected, testMatch.printStats(playerAthlete, enemyAthlete));
    }

}
