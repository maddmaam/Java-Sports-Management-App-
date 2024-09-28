package main.Purchaseables;

import java.util.Random;

/**
 * An Athlete subclass specialized in the Def stat. They receive a significant bonus to Def on generation.
*/

public class Defender extends Athlete {

    /**
     * Constructor. Applies a +10 Def bonus, and then additional Def bonuses if the Athlete's generated Atk is still higher than their Def.
     * @param rand      Random number generation object.
    */
    public Defender(Random rand) {
        super(rand);
        setDef(getDef() + 10);
        while (getDef() <= getAtk()) {
            setDef(getDef() + 10);
        }
    }

    /**
     * Gets Athlete's specialization. Overrides generic superclass.
     * @return      String containing "Defender".
    */
    public String getSpecialization() {
        return "Defender";
    }
    
}
