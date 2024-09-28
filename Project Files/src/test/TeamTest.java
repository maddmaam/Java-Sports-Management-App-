package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Random;

import main.*;
import main.Exceptions.OutOfMoneyException;
import main.Purchaseables.AllRounder;
import main.Purchaseables.Athlete;
import main.Purchaseables.Attacker;
import main.Purchaseables.Defender;
import main.Purchaseables.Item;
import main.Teams.*;


public class TeamTest {

    private Random rand;
    
    @BeforeEach
    public void init() {
        rand = new Random();
    }

    @Test
    public void enemyGenTest() {
        Team testOpponent = new OpposingTeam("Normal", rand);
        assertTrue(testOpponent.getName() instanceof String);
        for (Athlete athlete : testOpponent.getAllTeam()) {
            // Non-dummy, specialized Athletes have been generated for all slots
            assertFalse(athlete.isDummy());
            assertTrue(athlete instanceof Attacker || athlete instanceof Defender || athlete instanceof AllRounder);
        }
    }

    @Test
    public void enemyPrizeTest() {
        OpposingTeam testOpponent = new OpposingTeam("Normal", rand);
        PlayerTeam testPlayer = new PlayerTeam();
        testOpponent.givePrize(testPlayer);
        assertEquals(975, testPlayer.getMoney());
        assertEquals(3, testPlayer.getPoints());
        OpposingTeam testOpponent2 = new OpposingTeam("Hard", rand);
        testOpponent2.givePrize(testPlayer);
        assertEquals(1025, testPlayer.getMoney());
        assertEquals(5, testPlayer.getPoints());
    }

    @Test
    public void nameTest() {
        Team testTeam = new PlayerTeam();
        testTeam.setName("Test Dudes");
        assertEquals("Test Dudes", testTeam.getName());
    }

    @Test
    public void randTest() {
        Team testTeam = new PlayerTeam();
        Random rand = new Random(12);
        testTeam.setRandomGen(12);
        assertEquals(rand.nextInt(), testTeam.getRandomGen().nextInt());
        Random rand2 = new Random();
        testTeam.setRandomGen();
        assertNotEquals(rand2.nextInt(), testTeam.getRandomGen().nextInt());
    }

    @Test
    public void killMembers() {
        Team testTeam = new PlayerTeam();
        for (int i = 0; i <5; i++) {
            // Fill team with random members
            testTeam.addToTeam(Athlete.genAthlete(rand), i, false);
            assertFalse(testTeam.getTeamMember(i).isDummy());
        }
        testTeam.removeMember(testTeam.getTeamMember(0));
        assertTrue(testTeam.getTeamMember(0).isDummy());
        testTeam.overwriteSlot(testTeam.getAllTeam(), 1, testTeam.getTeamMember(0));
        assertTrue(testTeam.getTeamMember(1).isDummy());
    }

    @Test
    public void teamStatsTest() {
        Team testTeam = new PlayerTeam();
        int atk = 0;
        int def = 0;
        int hp = 0;
        for (int i = 0; i <5; i++) {
            // Fill team with random members
            Athlete newMember = Athlete.genAthlete(rand);
            testTeam.addToTeam(newMember, i, false);
            atk += newMember.getAtk();
            def += newMember.getDef();
            hp += newMember.getMaxHP();
        }
        int[] stats = testTeam.getTeamStrength();
        assertTrue(atk == stats[0] && stats[1] == def && stats[2] == hp);
        String st = testTeam.printTeamStats();
        String ex = "   Attack: " + atk + "\n   Defence: " + def + "\n   Total HP: " +  hp;
        assertEquals(ex, st);
    }

    @Test
    public void printTest() {
        Team testTeam = new PlayerTeam();
        String ex = "Current Team:";
        for (int i = 0; i <5; i++) {
            Athlete current = testTeam.getTeamMember(i);
            ex += "\n" + (i+1) + ". " + current.getName() +", "+ current.getSpecialization();
        }
        String st = testTeam.printTeam();
        assertEquals(ex, st);
    }

    @Test
    public void reservesTest() {
        PlayerTeam test = new PlayerTeam();
        Athlete[] ex = new Athlete[5];
        for (int i = 0; i < 5; i++) {
            Athlete newMember = Athlete.genAthlete(rand);
            ex[i] = newMember;
        }
        test.addToReserves(ex);
        test.addSingleToReserves(0, ex[0]);
        for (int i = 0; i < 5; i++) {
            assertEquals(ex[i], test.getReserve(i));
        }
        Athlete toRemove = test.getAllReserves()[4];
        test.emptySlot(test.getAllReserves(), 4);
        assertFalse(test.isInTeam(toRemove, test.getAllReserves()));
        assertTrue(test.isInTeam(test.getReserve(1), test.getAllReserves()));
    }

    @Test
    public void invTest() {
        PlayerTeam test = new PlayerTeam();
        Item item = Item.genItem(rand);
        test.addToInv(item);
        assertEquals(item, test.getItem(0));
        Item item2 = Item.genItem(rand);
        test.addToInv(item2);
        assertEquals(item, test.getInv().get(0));
        test.removeFromInv(item);
        assertNotEquals(item, test.getItem(0));
        test.removeFromInv(0);
        assertFalse(test.getInv().contains(item2));
        assertEquals("No item at specified location", test.removeFromInv(0));
        assertEquals("No item at specified location", test.removeFromInv(2000));
        assertEquals("No item at specified location", test.removeFromInv(-1));
    }

    @Test
    public void slotCountTest() {
        PlayerTeam test = new PlayerTeam();
        assertEquals(-1, test.getFreeAthleteSlot());
        assertFalse(test.teamFull());
        test.addToTeam(Athlete.genAthlete(rand), 0, false);
        test.addSingleToReserves(0, Athlete.genAthlete(rand));
        assertFalse(test.teamFull());
        assertEquals(1, test.getFreeAthleteSlot());
        for (int i = 1; i < 5; i++) {
            test.addToTeam(Athlete.genAthlete(rand), i, false);
            if (i < 4)
            assertFalse(test.teamFull());
            test.addSingleToReserves(i, Athlete.genAthlete(rand));
            if (i < 4)
                assertFalse(test.teamFull());
        }
        assertTrue(test.teamFull());
        assertEquals(-1, test.getFreeReserveSlot());
    }

    @Test
    public void moneyTest() {
        PlayerTeam test = new PlayerTeam();
        assertEquals(900, test.getMoney());
        test.takeMoney(800);
        assertEquals(100, test.getMoney());
        test.takeMoney(0);
        assertEquals(100, test.getMoney());
        assertThrows(OutOfMoneyException.class, () -> test.takeMoney(200));
        assertEquals(100, test.getMoney());
        test.takeMoney(100);
        assertEquals(0, test.getMoney());
        test.takeMoney(0);
        assertThrows(IndexOutOfBoundsException.class, () -> test.takeMoney(-100));
        assertEquals(0, test.getMoney());
    }

    @Test
    public void lineupTest() {
        PlayerTeam test = new PlayerTeam();
        String ex = "";
        String ex2 = "Current Team:";
        for (int i = 0; i < 5; i++){
            Athlete newMember = Athlete.genAthlete(rand);
            test.addToTeam(newMember, i, true);
            ex += "\n" + (i+1) + ". " + newMember.getName() + ", " + newMember.getSpecialization() + ". Sells for " + newMember.getSellCost();
            ex2 += "\n" + (i+1) + ". " + newMember.getName() +", "+ newMember.getSpecialization();
        }
        assertEquals(ex, test.printTeam(true));
        assertEquals(ex2, test.printTeam(false));
    }

}
