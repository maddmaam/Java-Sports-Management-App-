package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Purchaseables.AllRounder;
import main.Purchaseables.Athlete;
import main.Purchaseables.Attacker;
import main.Purchaseables.Defender;

import java.util.Random;

public class AthleteTest {

    @BeforeEach
    public void init() {
        
    }

    @Test
    public void generationTest() {
        // Testing that random seeding works consistently
        Random rand = new Random(10);
        Athlete testAthlete = Athlete.genAthlete(rand);
        assertTrue(testAthlete instanceof Attacker);
        Athlete testAthlete2 = Athlete.genAthlete(rand);
        assertTrue(testAthlete2 instanceof AllRounder);
        Athlete testAthlete3 = Athlete.genAthlete(rand);
        assertTrue(testAthlete3 instanceof AllRounder);
        Athlete testAthlete4 = Athlete.genAthlete(rand);
        assertTrue(testAthlete4 instanceof Attacker);
        assertFalse(testAthlete4 instanceof Defender);
        assertFalse(testAthlete4 instanceof AllRounder);
        // Athlete testAthlete5 = Athlete.genAthlete(rand);
        // assertTrue(testAthlete5 instanceof AllRounder);

        Random rand2 = new Random(4);
        Athlete testAthlete6 = Athlete.genAthlete(rand2);
        assertTrue(testAthlete6 instanceof Defender);
        Athlete testAthlete7 = Athlete.genAthlete(rand2);
        assertTrue(testAthlete7 instanceof Defender);
    }

    @Test
    public void randomStatGen() {
        Random rand = new Random(-12);
        Athlete testAthlete = Athlete.genAthlete(rand);
        Random rand2 = new Random(-12);
        Athlete testAthlete2 = Athlete.genAthlete(rand2);
        assertEquals(testAthlete.getAtk(), testAthlete2.getAtk());
        assertEquals(testAthlete.getDef(), testAthlete2.getDef());
        assertEquals(testAthlete.getHP(), testAthlete2.getHP());
        Athlete testAthlete3 = Athlete.genAthlete(rand2);
        assertFalse(testAthlete.getAtk() == testAthlete3.getAtk());
        assertFalse(testAthlete.getDef() == testAthlete3.getDef());
        assertFalse(testAthlete.getHP() == testAthlete3.getHP());
    }

    @Test
    public void hpTest() {
        Random rand = new Random();
        Athlete testHP = Athlete.genAthlete(rand);
        assertTrue(testHP.getMaxHP() > 10);
        assertEquals(testHP.getCurrentHP(), testHP.getMaxHP());
        testHP.setCurrentHP(testHP.getCurrentHP() - 5);
        assertEquals(testHP.getCurrentHP(), testHP.getMaxHP() - 5);
        assertTrue(testHP.getCurrentHP() != testHP.getMaxHP());
        testHP.setCurrentHP(-1000);
        assertEquals(0, testHP.getCurrentHP());
        testHP.setCurrentHP(testHP.getMaxHP() + 10);
        assertEquals(testHP.getMaxHP(), testHP.getCurrentHP());
    }

    @Test
    public void specializationTest() {
        Random rand = new Random();
        Athlete testAtk = new Attacker(rand);
        assertEquals("Attacker", testAtk.getSpecialization());
        Athlete testDef = new Defender(rand);
        assertEquals("Defender", testDef.getSpecialization());
        Athlete testAll = new AllRounder(rand);
        assertEquals("All-Rounder", testAll.getSpecialization());
    }

    @Test
    public void injuryTest() {
        Random rand = new Random();
        Athlete testKO = Athlete.genAthlete(rand);
        assertFalse(testKO.getInjured());
        testKO.setCurrentHP(testKO.getCurrentHP() * -1);
        assertTrue(testKO.getInjured());
        testKO.setCurrentHP(1);
        assertFalse(testKO.getInjured());
    }

    @Test
    public void settersTest() {
        Random rand = new Random();
        Athlete testStats = Athlete.genAthlete(rand);
        int baseAtk = testStats.getAtk();
        assertTrue(baseAtk > 0);
        int baseDef = testStats.getDef();
        assertTrue(baseDef > 0);
        testStats.setAtk(baseAtk - 100000);
        testStats.setDef(baseDef + 42);
        assertEquals(baseAtk - 100000, testStats.getAtk());
        assertEquals(baseDef + 42, testStats.getDef());
    }

    @Test
    public void dummyTest() {
        Random rand = new Random();
        Athlete testDummy = Athlete.genAthlete(rand);
        assertFalse(testDummy.isDummy());
        assertTrue(testDummy.getAtk() > 0);
        testDummy.setDummy();
        assertTrue(testDummy.isDummy());
        assertEquals(0, testDummy.getAtk());
    }

    @Test
    public void costTest() {
        Random rand = new Random();
        Athlete testCosts = Athlete.genAthlete(rand);
        assertEquals(testCosts.getBuyCost() - 20, testCosts.getSellCost());
        assertTrue(testCosts.getBuyCost() <= 100);
        assertTrue(testCosts.getBuyCost() >= 50);
    }

    @Test
    public void statTest() {
        Random rand = new Random();
        Athlete testStats = new Athlete(rand);      // No specialization
        assertTrue(testStats.getAtk() <= 10);
        assertTrue(testStats.getDef() <= 10);
        assertTrue(testStats.getAtk() >= 1);
        assertTrue(testStats.getDef() >= 1);
    }

    @Test
    public void statTestSpec() {
        Random rand = new Random();
        Athlete testStats = new Attacker(rand);
        assertTrue(testStats.getAtk() >= 10);
        assertTrue(testStats.getDef() <= 10);
        assertTrue(testStats.getAtk() <= 20);
        assertTrue(testStats.getAtk() > testStats.getDef());
        Athlete testStats2 = new Defender(rand);
        assertTrue(testStats2.getAtk() <= 10);
        assertTrue(testStats2.getDef() >= 10);
        assertTrue(testStats2.getDef() <= 20);
        assertTrue(testStats2.getAtk() < testStats2.getDef());
    }

}
