package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Random;

import main.*;
import main.Exceptions.InvalidNameException;
import main.Purchaseables.Athlete;
import main.Teams.PlayerTeam;

public class EnvironmentTest {
    
    GameSetup env;

    @BeforeEach
    public void init() {
        env = new GameSetup();
    }

    @Test
    public void difficultyTest() {
        env.setDifficulty("normal");
        assertEquals("Normal", env.getDifficulty());
        env.setDifficulty("hard");
        assertEquals("Hard", env.getDifficulty());
        env.setDifficulty("Normal");
        assertEquals("Normal", env.getDifficulty());
        env.setDifficulty("Invalid input should default to hard");
        assertEquals("Hard", env.getDifficulty());
    }

    @Test
    public void nameTest() {
        assertDoesNotThrow(() -> env.setTeamName("Test Team"), "Name must be between 3 and 15 characters");
        assertThrows(InvalidNameException.class, () -> env.setTeamName("Name that is more than 15 characters"));
        assertThrows(InvalidNameException.class, () -> env.setTeamName("a"));
        assertEquals("Test Team", env.getPlayerTeam().getName());
    }

    @Test
    public void weeksTest() {
        env.setLength(5);
        assertEquals(5, env.getWeeks());
        assertEquals(1, env.getCurrentWeek());  // ???????? why is this static
        env.advanceTime();
        assertEquals(2, env.getCurrentWeek());
        env.advanceTime();
        assertEquals(3, env.getCurrentWeek());
        // How do I test that it exits correctly...
    }

    @Test
    public void seedTest() {
        assertTrue(env.getRandomGen() instanceof Random);
        env.setSeed();
        env.setSeed(1234);
        Random randE = env.getRandomGen();
        Random randP = env.getPlayerTeam().getRandomGen();
        assertEquals(randE, randP);
    }

    @Test
    public void createTeamTest() {
        env.setDifficulty("normal");
        env.createPlayerTeam();
        assertEquals(1200, env.getPlayerTeam().getMoney());
        env.setDifficulty("hard");
        env.createPlayerTeam();
        assertEquals(1000, env.getPlayerTeam().getMoney());
    }

    @Test
    public void clubTest() {
        PlayerTeam player = new PlayerTeam();
        for (int i = 0; i < 5; i++) {
            player.addToTeam(Athlete.genAthlete(player.getRandomGen()), i, false);
            player.addSingleToReserves(i, Athlete.genAthlete(player.getRandomGen()));
        }
        Club club = new Club(player);
        Athlete current0 = player.getTeamMember(0);
        Athlete current1 = player.getTeamMember(1);
        club.moveSlots(0, 1, false);
        assertEquals(current0, player.getTeamMember(1));
        assertEquals(current1, player.getTeamMember(0));
        club.moveToReserves(0, player.getTeamMember(0));
        assertEquals(current1, player.getReserve(0));
        club.moveToMainTeam(0);
        assertEquals(current1, player.getTeamMember(0));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> club.moveSlots(10000, 13586275, false));  // bad indices
    }

}