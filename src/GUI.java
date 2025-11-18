import javax.swing.*;

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

    public GUI() {
        setContentPane(rootPanel);
        setTitle("The Wiki Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Initialize the lists with sample data
        initializeLists();

        // Add event handlers
        newChallengeButton.addActionListener(e -> {
            System.out.println("New Challenge button clicked!");
        });
    }

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUI frame = new GUI();
            frame.setVisible(true);
        });
    }
}
