package main.Purchaseables;

import java.util.Random;

/**
 * An Athlete subclass with no particular specialization. They recieve a bonus to both Atk and Def on generation,
 * but less so than they would get if they were specialized in a particular stat.
*/

public class AllRounder extends Athlete {

    /**
     * Constructor. Applies +10 Atk and Def bonuses.
     * @param rand      Random number generation object.
    */
    public AllRounder(Random rand) {
        super(rand);
        setAtk(getAtk() + 10);
        setDef(getDef() + 10);
    }

    /**
     * Gets Athlete's specialization. Overrides generic superclass.
     * @return      String containing "All-Rounder".
    */
    public String getSpecialization() {
        return "All-Rounder";
    }

}
