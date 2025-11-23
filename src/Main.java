import javax.swing.*;
import java.util.*;

/**
 * Main entry point for The Wiki Game application.
 * Contains utility methods for generating random strings for the game.
 * https://gitlab.com/dtuinstr-cs242-fall2025/LayoutTextListsDemo/-/blob/main/src/ListCreator.java?ref_type=heads
 */
public class Main {

    private static final Random random = new Random();

    /**
     * Launches the GUI application.
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUI frame = new GUI();
            frame.setVisible(true);
        });
    }

    /**
     * Generates a random uppercase letter.
     * @return a random uppercase letter from A to Z
     */
    public static char randomUpperCaseLetter() {
        return (char) ('A' + random.nextInt(26));
    }

    /**
     * Generates a random lowercase letter.
     * @return a random lowercase letter from a to z
     */
    public static char randomLowerCaseLetter() {
        return (char) ('a' + random.nextInt(26));
    }

    /**
     * Generates an array of random strings with a given prefix and number of additional characters.
     * Duplicates are automatically removed.
     * @param count the number of strings to generate (before duplicate removal)
     * @param prefix the prefix for each string (cannot be null)
     * @param additionalChars the number of random characters to append
     * @return array of unique strings
     * @throws IllegalArgumentException if count is negative or prefix is null
     */
    public static String[] generateRandomStrings(int count, String prefix, int additionalChars) {
        if (count < 0) {
            throw new IllegalArgumentException("Count cannot be negative");
        }
        if (prefix == null) {
            throw new IllegalArgumentException("Prefix cannot be null");
        }
        if (additionalChars < 0) {
            throw new IllegalArgumentException("Additional characters cannot be negative");
        }

        Set<String> uniqueStrings = new HashSet<>();
        boolean isUpperCase = !prefix.isEmpty() && Character.isUpperCase(prefix.charAt(0));

        // Generate strings until we have enough unique ones or hit a reasonable limit
        int attempts = 0;
        while (uniqueStrings.size() < count && attempts < count * 3) {
            StringBuilder sb = new StringBuilder(prefix);
            for (int i = 0; i < additionalChars; i++) {
                if (isUpperCase) {
                    sb.append(randomUpperCaseLetter());
                } else {
                    sb.append(randomLowerCaseLetter());
                }
            }
            uniqueStrings.add(sb.toString());
            attempts++;
        }

        return uniqueStrings.toArray(new String[0]);
    }
}
