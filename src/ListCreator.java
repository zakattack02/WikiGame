import java.util.*;

/**
 * This class provides static methods to create content for JList GUI
 * components. It does not have anything to do with implementations of
 * Java's List interface in the Collections framework.
 */
public class ListCreator {

    private static final Random random = new Random();

    // Private no-arg constructor to prevent attempts to instantiate this class.
    private ListCreator() {}

    /**
     * Creates arrays of mock list items. Each item is of the form
     * "{prefix}item {n}", where prefix is from the arguments, and n
     * ranges from 0 to count-1. If a negative or zero count is given,
     * an empty list is returned. If prefix is null it is taken to be
     * the empty string.
     * @param count the number of items to produce
     * @param prefix the prefix for each item (can be null or empty)
     * @return array of Strings of the form "{prefix}item {n}"
     */
    public static String[] makeList(int count, String prefix) {
        String[] result = new String[0];
        if (count > 0) {
            result = new String[count];
            prefix = (prefix == null ? "" : prefix);
            for (int i = 0; i < count; ++i) {
                result[i] = prefix + "item " + i;
            }
        }
        return result;
    }

    /**
     * Generates strings by appending random characters to the end of selected base strings.
     * For each base string, creates multiple new strings by adding random characters.
     * Duplicates are automatically removed.
     * Uses the makeList concept but with random characters instead of sequential numbers.
     *
     * @param baseStrings the list of base strings to extend
     * @param countPerBase number of strings to generate per base string
     * @param additionalChars number of random characters to append to the end
     * @return array of unique generated strings
     * @throws IllegalArgumentException if baseStrings is null or empty, or counts are negative
     */
    public static String[] appendRandomChars(List<String> baseStrings, int countPerBase, int additionalChars) {
        if (baseStrings == null || baseStrings.isEmpty()) {
            throw new IllegalArgumentException("Base strings cannot be null or empty");
        }
        if (countPerBase < 0) {
            throw new IllegalArgumentException("Count per base cannot be negative");
        }
        if (additionalChars < 0) {
            throw new IllegalArgumentException("Additional characters cannot be negative");
        }

        Set<String> uniqueStrings = new HashSet<>();

        for (String base : baseStrings) {
            if (base == null || base.isEmpty()) {
                continue;
            }

            // Determine case based on the base string - similar to makeList's prefix handling
            boolean isUpperCase = Character.isUpperCase(base.charAt(0));

            int attempts = 0;
            int generated = 0;
            // Generate countPerBase items per base string (similar to makeList's count parameter)
            while (generated < countPerBase && attempts < countPerBase * 3) {
                StringBuilder sb = new StringBuilder(base);

                // Append random characters to the end (similar to makeList adding "item {n}")
                for (int i = 0; i < additionalChars; i++) {
                    if (isUpperCase) {
                        sb.append(randomUpperCaseLetter());
                    } else {
                        sb.append(randomLowerCaseLetter());
                    }
                }

                if (uniqueStrings.add(sb.toString())) {
                    generated++;
                }
                attempts++;
            }
        }

        return uniqueStrings.toArray(new String[0]);
    }

    /**
     * Generates strings by prepending random characters to the beginning of selected base strings.
     * For each base string, creates multiple new strings by adding random characters at the start.
     * For right-side columns in the game, prepends UPPERCASE letters regardless of the base string's case.
     * Duplicates are automatically removed.
     * Uses the makeList concept but prepends random characters instead of using a prefix.
     *
     * @param baseStrings the list of base strings to extend
     * @param countPerBase number of strings to generate per base string
     * @param additionalChars number of random characters to prepend to the beginning
     * @return array of unique generated strings
     * @throws IllegalArgumentException if baseStrings is null or empty, or counts are negative
     */
    public static String[] prependRandomChars(List<String> baseStrings, int countPerBase, int additionalChars) {
        if (baseStrings == null || baseStrings.isEmpty()) {
            throw new IllegalArgumentException("Base strings cannot be null or empty");
        }
        if (countPerBase < 0) {
            throw new IllegalArgumentException("Count per base cannot be negative");
        }
        if (additionalChars < 0) {
            throw new IllegalArgumentException("Additional characters cannot be negative");
        }

        Set<String> uniqueStrings = new HashSet<>();

        for (String base : baseStrings) {
            if (base == null || base.isEmpty()) {
                continue;
            }

            // For the right-side columns, we always prepend uppercase letters
            // yZ -> XyZ -> WXyZ -> VWXyZ
            // This is similar to makeList's prefix concept but with random characters
            int attempts = 0;
            int generated = 0;
            while (generated < countPerBase && attempts < countPerBase * 3) {
                StringBuilder sb = new StringBuilder();

                // Prepend random UPPERCASE characters to the beginning
                for (int i = 0; i < additionalChars; i++) {
                    sb.append(randomUpperCaseLetter());
                }
                sb.append(base);

                if (uniqueStrings.add(sb.toString())) {
                    generated++;
                }
                attempts++;
            }
        }

        return uniqueStrings.toArray(new String[0]);
    }

    /**
     * Generates a random uppercase letter.
     * @return a random uppercase letter from A to Z
     */
    private static char randomUpperCaseLetter() {
        return (char) ('A' + random.nextInt(26));
    }

    /**
     * Generates a random lowercase letter.
     * @return a random lowercase letter from a to z
     */
    private static char randomLowerCaseLetter() {
        return (char) ('a' + random.nextInt(26));
    }
}
