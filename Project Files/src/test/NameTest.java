package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Purchaseables.Athlete;
import main.Purchaseables.NameGen;

import java.util.Random;
import java.io.IOException;

public class NameTest {
    
    private Random rand;

    @BeforeEach
    public void init() {
        rand = new Random();
    }

    @Test
    public void seedTest() {
        // Check that random seeding works
        rand = new Random(69);
        Athlete test = new Athlete(rand);
        String testName = test.gen(rand);
        assertEquals("Ernest Paolini", testName);
        String testName2 = test.gen(rand);
        assertEquals("Ian Martine", testName2);
    }

    @Test
    public void strTest() {
        // Check that return is a String??
        assertTrue(NameGen.gen(rand) instanceof String);
    }

    @Test
    public void exceptionTest() {
        // Check that exception thrown for bad filenames
        assertThrows(IOException.class, () -> NameGen.read("res/notafile.txt", 60, rand));
        assertThrows(IOException.class, () -> NameGen.read("res/notafile.txt", 0, rand));
    }

}
