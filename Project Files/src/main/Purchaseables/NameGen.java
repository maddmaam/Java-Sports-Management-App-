package main.Purchaseables;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

/**
 * This class provides static methods for generating names for both Athletes and OpposingTeams by pulling from the included txt files.
*/

public class NameGen {

    /**
     * Static method; generates a randomized forename and surname for a new Athlete by combining choices from forenames.txt and surnames.txt.
     * If the files cannot be read, the name defaults to "John Smith".
     * @return      String joining the selected forename and surname.
    */
    public static String gen(Random rand) {
        String forename;
        String surname;
        try {
        forename = read("res/forenames.txt", 60, rand);
        surname = read("res/surnames.txt", 60, rand);
        } catch (IOException e) {
            // If files can't be read, name defaults to "John Smith"
            return "John Smith";
        }
        return forename + " " + surname;
    }

    /**
     * Static method; generates a randomized name for an OpposingTeam by combining an adjective and noun from adjectives.txt and nouns.txt.
     * If the files cannot be read, name defaults to "Bad Guys".
     * @return      String joining the selected adjective and noun.
    */
    public static String genTeam(Random rand) {
        String adj;
        String noun;
        try {
        adj = read("res/adjectives.txt", 20, rand);
        noun = read("res/nouns.txt", 20, rand);
        } catch (IOException e) {
            // If files can't be read, name defaults to "Bad Guys"
            return "Bad Guys";
        }
        return adj + " " + noun;
    }

    /**
     * Reads txt files as given by gen() and genTeam(), and returns a random line within the specified bound.
     * If the file cannot be read, throws IOException.
     * @param filename      The name of the file to be read.
     * @param lines         The upper bound of possible lines to read.
     * @return              String containing the contents of a random line as chosen based on the specified bound.
    */
    public static String read(String filename, int lines, Random rand) throws IOException {
        int line = 0;
        if (lines > 0)
            line = rand.nextInt(lines);
        String output;
        // InputStream in = getClass().getResourceAsStream("/img/file.png"); 
        // BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        output = Files.readAllLines(Paths.get(filename)).get(line);
        // Probably inefficent but I don't want to spend forever on it
        return output;
    }

}
