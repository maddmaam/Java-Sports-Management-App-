package main.Purchaseables;

import main.Teams.PlayerTeam;
/**
 * An interface describing methods to be implemented by objects purchasable from the shop. This includes both Athletes and Items.
*/

public interface Purchasable {

    /**
     * Retrieve the object's name.
    */
    public String getName();

    /**
     * Process the purchase of the given item.
     * @param player        PlayerTeam to retrieve funds from and give the purchased object to.
    */
    public void buy(PlayerTeam player);
    
    /**
     * Retrieve details about the object.
    */
    public String getPurchaseDetails();

}
