package main.Purchaseables;

import java.util.Random;

/**
 * An Athlete subclass specialized in the Atk stat. They receive a significant bonus to Atk on generation.
*/

public class Attacker extends Athlete {

    /**
     * Constructor. Applies a +10 Atk bonus, and then additional Atk bonuses if the Athlete's generated Def is still higher than their Atk.
    */
    public Attacker(Random rand) {
        super(rand);
        setAtk(getAtk() + 10);
        while (getDef() >= getAtk()) {
            setAtk(getAtk() + 10);
        }
    }

    /**
     * Gets Athlete's specialization. Overrides generic superclass.
     * @return      String containing "Attacker".
    */
    public String getSpecialization() {
        return "Attacker";
    }

}
