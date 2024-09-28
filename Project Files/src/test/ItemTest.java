package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Random;

import main.Teams.PlayerTeam;
import main.Purchaseables.Athlete;
import main.Purchaseables.Attacker;
import main.Purchaseables.Defender;
import main.Purchaseables.HealInjury;
import main.Purchaseables.HealthRestore;
import main.Purchaseables.InteractiveItem;
import main.Purchaseables.Item;
import main.Purchaseables.Purchasable;
import main.Purchaseables.StatBoost;
import main.Purchaseables.StatReroll;

public class ItemTest {

    private Random rand;
    
    @BeforeEach
    public void init() {
        rand = new Random();
    }

    @Test
    public void genTest() {
        rand = new Random(5);
        Item testItem = Item.genItem(rand);
        assertTrue(testItem instanceof StatReroll);
        assertTrue(testItem instanceof Purchasable);
        Item testItem2 = Item.genItem(rand);
        assertTrue(testItem2 instanceof HealInjury);
        rand = new Random(6);
        Item testItem3 = Item.genItem(rand);
        assertTrue(testItem3 instanceof HealthRestore);
        assertFalse(testItem3 instanceof StatBoost);
    }

    @Test
    public void gettersTest() {
        Item testItem = new HealthRestore();
        assertEquals("First Aid Kit", testItem.getName());
        testItem.setName("Test Item");
        assertEquals("Test Item", testItem.getName());
        assertEquals(40, testItem.getBuyCost());
        assertEquals(10, testItem.getSellCost());
        String ex = "Item Name: Test Item\nEffects: A standard Sportsball medical kit containing bandages and painkillers. \nRestores a little HP to a selected athlete.";
        assertEquals(ex, testItem.getDescription());
        assertEquals(ex + "\nBuy for: 40\nSell for: 10", testItem.getPurchaseDetails());
        assertEquals("Test Item", testItem.toString());
    }

    @Test
    public void saleTest() {
        PlayerTeam testPlayer = new PlayerTeam();
        Item testItem = new HealInjury();
        testItem.buy(testPlayer);
        assertEquals(860, testPlayer.getMoney());
        assertTrue(testPlayer.getInv().contains(testItem));
        testItem.sell(testPlayer);
        assertEquals(870, testPlayer.getMoney());
        assertFalse(testPlayer.getInv().contains(testItem));
    }

    @Test
    public void restoreTest() {
        PlayerTeam testPlayer = new PlayerTeam();
        Item testItem = new HealthRestore();
        Athlete testAthlete = Athlete.genAthlete(rand);
        String ex = "Athlete is at full HP already! The item will have no effect.";
        assertEquals(ex, testItem.use(testAthlete, testPlayer));
        testAthlete.setCurrentHP(0);
        ex = "Athlete is much too seriously injured for this to have any effect. You're going to need something stronger.";
        assertEquals(ex, testItem.use(testAthlete, testPlayer));
        testAthlete.setCurrentHP(1);
        testItem.use(testAthlete, testPlayer);
        int expectedHP = (int) Math.rint(testAthlete.getMaxHP() * 0.25) + 1;
        assertEquals(expectedHP, testAthlete.getCurrentHP());
    }

    @Test 
    public void injuryTest() {
        PlayerTeam testPlayer = new PlayerTeam();
        Item testItem = new HealInjury();
        testPlayer.addToInv(testItem);
        assertEquals(testItem.getEffect(), "A vial of mysterious fluid with wholly unscientific effects. \nFully heals an injured (0HP) athlete.");
        Athlete injured = Athlete.genAthlete(rand);
        assertFalse(injured.getInjured());
        assertEquals(testItem.use(injured, testPlayer), "Athlete is not injured! The item will have no effect.");
        injured.setCurrentHP(0);
        assertTrue(injured.getInjured());
        testItem.use(injured, testPlayer);
        assertFalse(testPlayer.getInv().contains(testItem));
        assertFalse(injured.getInjured());
        assertTrue(injured.getCurrentHP() == injured.getMaxHP());
    }

    @Test
    public void rerollTest() {
        PlayerTeam testPlayer = new PlayerTeam();
        InteractiveItem testItem = new StatReroll();
        Athlete testAthlete = Athlete.genAthlete(rand);
        testPlayer.addToInv(testItem);
        assertEquals(testItem.getEffect(), "An experimental medication recently released onto the market. \nRandomizes the Atk or Def of a selected athlete.");
        String returnedString = testItem.useOnAtk(testAthlete, testPlayer);
        String expectedString = "Success! " + testAthlete.getName() + "'s Atk is now " + testAthlete.getAtk() + "!\nThe " + testItem.getName() + " has been removed from your inventory.";
        assertEquals(expectedString, returnedString);
        assertFalse(testPlayer.getInv().contains(testItem));
        testPlayer.addToInv(testItem);
        returnedString = testItem.useOnDef(testAthlete, testPlayer);
        expectedString = "Success! " + testAthlete.getName() + "'s Def is now " + testAthlete.getDef() + "!\nThe " + testItem.getName() + " has been removed from your inventory.";
        assertEquals(expectedString, returnedString);
        assertFalse(testPlayer.getInv().contains(testItem));
        testPlayer.addToInv(testItem);
        returnedString = testItem.useOnHP(testAthlete, testPlayer);
        expectedString = "Success! " + testAthlete.getName() + "'s maximum HP is now " + testAthlete.getMaxHP() + "!\nThey feel reinvigorated and are also restored to full health!" + "\nThe " + testItem.getName() + " has been removed from your inventory.";
        assertEquals(expectedString, returnedString);
        assertFalse(testPlayer.getInv().contains(testItem));
    }

    @Test
    public void buffTest() {
        PlayerTeam testPlayer = new PlayerTeam();
        InteractiveItem testItem = new StatBoost();
        assertEquals("An inconspicuous bottle of pills. \nIncreases Atk or Def of a selected athlete.", testItem.getEffect());
        Athlete testAthlete = Athlete.genAthlete(rand);
        int initAtk = testAthlete.getAtk();
        int initDef = testAthlete.getDef();
        String initHP = testAthlete.getHP();
        int initCurrentHP = testAthlete.getCurrentHP();
        testPlayer.addToInv(testItem);
        testItem.useOnAtk(testAthlete, testPlayer);
        assertTrue(initAtk < testAthlete.getAtk());
        assertFalse(testPlayer.getInv().contains(testItem));
        testPlayer.addToInv(testItem);
        testItem.useOnDef(testAthlete, testPlayer);
        assertTrue(initDef < testAthlete.getDef());
        assertFalse(testPlayer.getInv().contains(testItem));
        testPlayer.addToInv(testItem);
        testItem.useOnHP(testAthlete, testPlayer);
        assertNotEquals(initHP, testAthlete.getHP());
        assertEquals(initCurrentHP, testAthlete.getCurrentHP());
        assertFalse(testPlayer.getInv().contains(testItem));
    }

    @Test
    public void buffSpecializedTest() {
        PlayerTeam testPlayer = new PlayerTeam();
        InteractiveItem testItem = new StatBoost();
        Athlete testAthlete = new Attacker(rand);
        int initAtk = testAthlete.getAtk();
        testPlayer.addToInv(testItem);
        testItem.useOnAtk(testAthlete, testPlayer);
        assertTrue(initAtk < testAthlete.getAtk());
        assertFalse(testPlayer.getInv().contains(testItem));
        assertTrue(initAtk + 8 == testAthlete.getAtk());

        testAthlete = new Defender(rand);
        int initDef = testAthlete.getDef();
        testPlayer.addToInv(testItem);
        testItem.useOnDef(testAthlete, testPlayer);
        assertTrue(initDef < testAthlete.getDef());
        assertFalse(testPlayer.getInv().contains(testItem));
        assertTrue(initDef + 8 == testAthlete.getDef());
        initAtk = testAthlete.getAtk();
        testPlayer.addToInv(testItem);
        testItem.useOnAtk(testAthlete, testPlayer);
        assertTrue(initAtk + 3 == testAthlete.getAtk());
    }

    @Test
    public void interactiveTest() {
        Item testBoost = new StatBoost();
        assertTrue(testBoost instanceof InteractiveItem);
        Item testReroll = new StatReroll();
        assertTrue(testReroll instanceof InteractiveItem);
        Item testInjury = new HealInjury();
        assertFalse(testInjury instanceof InteractiveItem);
        Item testHeal = new HealthRestore();
        assertFalse(testHeal instanceof InteractiveItem);
    }

}
