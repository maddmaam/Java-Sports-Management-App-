package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.*;
import main.Exceptions.OutOfMoneyException;
import main.Purchaseables.Athlete;
import main.Purchaseables.Item;
import main.Teams.PlayerTeam;

public class ShopTest {

    private Shop shop;
    private PlayerTeam player;
    
    @BeforeEach
    public void init() {
        player = new PlayerTeam();
        shop = new Shop(player);
    }

    @Test
    public void buyTest() {
        double initMoney = player.getMoney();
        shop.purchase(0, "Athletes");
        assertNotEquals(player.getMoney(), initMoney);
        assertFalse(player.getTeamMember(0).isDummy());
        initMoney = player.getMoney();
        shop.purchase(0, "Items");
        assertNotEquals(player.getMoney(), initMoney);
        assertEquals(player.getInv().size(), 1);
        initMoney = player.getMoney();
        shop.purchase(0, "Reserves");
        assertNotEquals(player.getMoney(), initMoney);
        assertFalse(player.getReserve(0).isDummy());
        player.takeMoney(player.getMoney());
        assertEquals(player.getMoney(), 0);
        //shop.purchase(0, "Items");
        //assertThrows(OutOfMoneyException.class, () -> player.getMoney());
        // I don't know how to test this....
    }

    @Test
    public void sellTest() {
        player.addToInv(Item.genItem(player.getRandomGen()));
        assertEquals(1, player.getInv().size());
        shop.sell("items", false, 0);
        assertEquals(0, player.getInv().size());
        player.addToTeam(Athlete.genAthlete(player.getRandomGen()), 0, false);
        assertFalse(player.getTeamMember(0).isDummy());
        shop.sell("athletes", false, 0);
        assertTrue(player.getTeamMember(0).isDummy());
        player.addSingleToReserves(0, Athlete.genAthlete(player.getRandomGen()));
        assertFalse(player.getReserve(0).isDummy());
        shop.sell("athletes", true, 0);
        assertTrue(player.getReserve(0).isDummy());
    }

}
