import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.util.HashSet;
import java.util.Set;

/**
 * Main GUI class for The Wiki Game application.
 * Provides a user interface for exploring Wikipedia link chains.
 */
public class GUI extends JFrame {
    private JPanel rootPanel;
    private JButton newChallengeButton;
    private JLabel timerLabel;
    private JPanel headerPanel;
    private JPanel centerPanel;
    private JPanel leftSection;
    private JPanel rightSection;
    private JList<String> listL1;
    private JList<String> listL2;
    private JList<String> listL3;
    private JList<String> listR1;
    private JList<String> listR2;
    private JList<String> listR3;
    private JPanel bottomPanel;
    private JLabel messageLabel;
    private JTextField messageArea;

    private char startLetter;
    private char endLetter;

    /**
     * Constructor for GUI class.
     * Initializes the window and sets up all components and event handlers.
     */
    public GUI() {
        setContentPane(rootPanel);
        setTitle("CS 242 Project - Zak Konik");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Initialize the lists with sample data
        initializeLists();

        // Create bottom panel if not created by form
        createBottomPanelIfNeeded();

        // event handlers
        newChallengeButton.addActionListener(e -> handleNewChallenge());

        // listeners to all lists
        listL1.addListSelectionListener(e -> handleLeftColumn1Selection());
        listL2.addListSelectionListener(e -> handleLeftColumn2Selection());
        listL3.addListSelectionListener(e -> handleLeftColumn3Selection());
        listR3.addListSelectionListener(e -> handleRightColumn1Selection());
        listR2.addListSelectionListener(e -> handleRightColumn2Selection());
        listR1.addListSelectionListener(e -> handleRightColumn3Selection());
    }

    /**
     * Creates the bottom panel with message label and text area if not already created by the form.
     */
    private void createBottomPanelIfNeeded() {
        if (bottomPanel == null) {
            bottomPanel = new JPanel();
            bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));

            messageLabel = new JLabel("Message:");
            messageArea = new JTextField();
            messageArea.setEditable(false);

            bottomPanel.add(messageLabel);
            bottomPanel.add(Box.createHorizontalStrut(5));
            bottomPanel.add(messageArea);

            // Add to root panel
            rootPanel.add(bottomPanel, java.awt.BorderLayout.SOUTH);
        }
    }

    /**
     * Initializes all list components with their data models.
     */
    private void initializeLists() {
        // Initialize left section lists
        DefaultListModel<String> model1 = new DefaultListModel<>();
        listL1.setModel(model1);

        DefaultListModel<String> model2 = new DefaultListModel<>();
        listL2.setModel(model2);

        DefaultListModel<String> model3 = new DefaultListModel<>();
        listL3.setModel(model3);

        // Initialize right section lists
        DefaultListModel<String> modelR1 = new DefaultListModel<>();
        listR1.setModel(modelR1);

        DefaultListModel<String> modelR2 = new DefaultListModel<>();
        listR2.setModel(modelR2);

        DefaultListModel<String> modelR3 = new DefaultListModel<>();
        listR3.setModel(modelR3);
    }

    /**
     * Handles the "New Challenge" button click event.
     * Resets the game state and generates new random data.
     */
    private void handleNewChallenge() {
        // Clear all lists
        clearAllLists();

        // Reset timer
        timerLabel.setText("00:00:00");

        // random start and end letters
        startLetter = Main.randomUpperCaseLetter();
        endLetter = Main.randomUpperCaseLetter();
        while (endLetter == startLetter) {
            endLetter = Main.randomUpperCaseLetter();
        }

        // Update titles
        updateBorderTitle(leftSection, "Start: " + startLetter);
        updateBorderTitle(rightSection, "End: " + endLetter);

        // Clear message
        if (messageArea != null) {
            messageArea.setText("");
        }

        // Populate first columns
        populateColumn1();
        populateColumn6();
    }

    /**
     * Updates the border title of a panel.
     * @param panel the panel to update
     * @param title the new title text
     */
    private void updateBorderTitle(JPanel panel, String title) {
        if (panel != null && panel.getBorder() instanceof TitledBorder) {
            TitledBorder border = (TitledBorder) panel.getBorder();
            border.setTitle(title);
            panel.repaint();
        }
    }

    /**
     * Clears all list models.
     */
    private void clearAllLists() {
        clearList(listL1);
        clearList(listL2);
        clearList(listL3);
        clearList(listR1);
        clearList(listR2);
        clearList(listR3);
    }

    /**
     * Clears a single list.
     * @param list the list to clear
     */
    private void clearList(JList<String> list) {
        DefaultListModel<String> model = (DefaultListModel<String>) list.getModel();
        model.clear();
    }

    /**
     * Populates column 1 (leftmost) with random strings.
     */
    private void populateColumn1() {
        String[] items = Main.generateRandomStrings(10,
            String.valueOf(startLetter), 1);
        populateList(listL1, items);
    }

    /**
     * Populates column 6 (rightmost) with random strings.
     * Creates strings of the form yZ where y is a random lowercase letter
     * and Z is the end letter (uppercase).
     */
    private void populateColumn6() {
        Set<String> uniqueStrings = new HashSet<>();
        int attempts = 0;
        // Generate 10 unique strings of the form yZ (lowercase + uppercase end letter)
        while (uniqueStrings.size() < 10 && attempts < 30) {
            String str = String.valueOf(Main.randomLowerCaseLetter()) + endLetter;
            uniqueStrings.add(str);
            attempts++;
        }
        populateList(listR3, uniqueStrings.toArray(new String[0]));
    }

    /**
     * Populates a list with an array of strings.
     * @param list the list to populate
     * @param items the items to add
     */
    private void populateList(JList<String> list, String[] items) {
        DefaultListModel<String> model = (DefaultListModel<String>) list.getModel();
        model.clear();
        for (String item : items) {
            model.addElement(item);
        }
    }

    /**
     * Handles selection in left column 1.
     */
    private void handleLeftColumn1Selection() {
        if (!listL1.getValueIsAdjusting()) {
            java.util.List<String> selected = listL1.getSelectedValuesList();
            if (!selected.isEmpty()) {
                String[] items = ListCreator.appendRandomChars(selected, 5, 1);
                populateList(listL2, items);
                checkForMatch();
            }
        }
    }

    /**
     * Handles selection in left column 2.
     */
    private void handleLeftColumn2Selection() {
        if (!listL2.getValueIsAdjusting()) {
            java.util.List<String> selected = listL2.getSelectedValuesList();
            if (!selected.isEmpty()) {
                String[] items = ListCreator.appendRandomChars(selected, 5, 1);
                populateList(listL3, items);
                checkForMatch();
            }
        }
    }

    /**
     * Handles selection in left column 3.
     */
    private void handleLeftColumn3Selection() {
        if (!listL3.getValueIsAdjusting()) {
            checkForMatch();
        }
    }

    /**
     * Handles selection in right column 1 (rightmost).
     */
    private void handleRightColumn1Selection() {
        if (!listR3.getValueIsAdjusting()) {
            java.util.List<String> selected = listR3.getSelectedValuesList();
            if (!selected.isEmpty()) {
                String[] items = ListCreator.prependRandomChars(selected, 5, 1);
                populateList(listR2, items);
                checkForMatch();
            }
        }
    }

    /**
     * Handles selection in right column 2.
     */
    private void handleRightColumn2Selection() {
        if (!listR2.getValueIsAdjusting()) {
            java.util.List<String> selected = listR2.getSelectedValuesList();
            if (!selected.isEmpty()) {
                String[] items = ListCreator.prependRandomChars(selected, 5, 1);
                populateList(listR1, items);
                checkForMatch();
            }
        }
    }

    /**
     * Handles selection in right column 3.
     */
    private void handleRightColumn3Selection() {
        if (!listR1.getValueIsAdjusting()) {
            checkForMatch();
        }
    }

    /**
     * Checks if there are matching strings between left column 3 and right column 1.
     * If a match is found, displays the chain in the message area.
     */
    private void checkForMatch() {
        DefaultListModel<String> modelL3 = (DefaultListModel<String>) listL3.getModel();
        DefaultListModel<String> modelR1 = (DefaultListModel<String>) listR1.getModel();

        if (modelL3.isEmpty() || modelR1.isEmpty()) {
            return;
        }

        // Find matching strings
        for (int i = 0; i < modelL3.getSize(); i++) {
            String leftItem = modelL3.getElementAt(i);
            for (int j = 0; j < modelR1.getSize(); j++) {
                String rightItem = modelR1.getElementAt(j);
                if (leftItem.equals(rightItem)) {
                    displayChain(leftItem);
                    return;
                }
            }
        }
    }

    /**
     * Displays the complete chain from start to end letter.
     * @param matchString the string where left and right chains meet
     */
    private void displayChain(String matchString) {
        if (messageArea == null) return;

        // Build left chain
        String leftChain = buildLeftChain(matchString);
        // Build right chain
        String rightChain = buildRightChain(matchString);

        // Combine chains
        String fullChain = leftChain + " == " + rightChain;
        messageArea.setText(fullChain);
    }

    /**
     * Builds the left side of the chain.
     * @param matchString the matching string
     * @return the left chain as a string
     */
    private String buildLeftChain(String matchString) {
        java.util.List<String> selected1 = listL1.getSelectedValuesList();
        java.util.List<String> selected2 = listL2.getSelectedValuesList();

        String item1 = selected1.isEmpty() ? "" : selected1.get(0);
        String item2 = selected2.isEmpty() ? "" : selected2.get(0);

        StringBuilder chain = new StringBuilder();
        chain.append(startLetter);
        if (!item1.isEmpty()) {
            chain.append(" - ").append(item1);
        }
        if (!item2.isEmpty()) {
            chain.append(" - ").append(item2);
        }
        chain.append(" - ").append(matchString);

        return chain.toString();
    }

    /**
     * Builds the right side of the chain.
     * The right side shows: match -> column 5 selection -> column 6 selection -> end letter
     * This represents the path from the match back to the end letter.
     * @param matchString the matching string
     * @return the right chain as a string
     */
    private String buildRightChain(String matchString) {
        java.util.List<String> selected3 = listR3.getSelectedValuesList(); // Column 6 selections
        java.util.List<String> selected2 = listR2.getSelectedValuesList(); // Column 5 selections

        String item3 = selected3.isEmpty() ? "" : selected3.get(0); // Column 6 item
        String item2 = selected2.isEmpty() ? "" : selected2.get(0); // Column 5 item

        StringBuilder chain = new StringBuilder();
        chain.append(matchString); // PKTL
        if (!item2.isEmpty()) {
            chain.append(" - ").append(item2); // KTL
        }
        if (!item3.isEmpty()) {
            chain.append(" - ").append(item3); // TL
        }
        chain.append(" - ").append(endLetter); // L

        return chain.toString();
    }

    /**
     * Main method to launch the application.
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUI frame = new GUI();
            frame.setVisible(true);
        });
    }

}
