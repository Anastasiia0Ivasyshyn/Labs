import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class AutoGuiSearch extends JFrame {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/Auto?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root123";

    private final JTextField inputField;
    private final JSlider mpgSlider;
    private final JSlider hpSlider;

    // second window for results
    private final JFrame resultsFrame;
    private final JTextArea resultArea;

    public AutoGuiSearch() {
        super("Auto MPG Query - Main Window");

        inputField = new JTextField(20);
        JButton showButton = new JButton("Show");

        mpgSlider = new JSlider(0, 60, 0);
        mpgSlider.setMajorTickSpacing(10);
        mpgSlider.setMinorTickSpacing(5);
        mpgSlider.setPaintTicks(true);
        mpgSlider.setPaintLabels(true);

        hpSlider = new JSlider(0, 250, 0);
        hpSlider.setMajorTickSpacing(50);
        hpSlider.setMinorTickSpacing(10);
        hpSlider.setPaintTicks(true);
        hpSlider.setPaintLabels(true);

        // top input panel
        JPanel top = new JPanel();
        top.add(new JLabel("Type ALL or car name text:"));
        top.add(inputField);
        top.add(showButton);

        // sliders panel
        JPanel sliders = new JPanel(new GridLayout(4, 1));
        sliders.add(new JLabel("Minimum MPG:"));
        sliders.add(mpgSlider);
        sliders.add(new JLabel("Minimum Horsepower:"));
        sliders.add(hpSlider);

        setLayout(new BorderLayout());
        add(top, BorderLayout.NORTH);
        add(sliders, BorderLayout.SOUTH);

        // results window
        resultsFrame = new JFrame("Results Window");
        resultArea = new JTextArea(25, 85);
        resultArea.setEditable(false);
        resultsFrame.add(new JScrollPane(resultArea));
        resultsFrame.pack();
        resultsFrame.setLocationRelativeTo(this);

        // events
        showButton.addActionListener(this::runQuery);

        ChangeListener sliderListener = e -> runQuery(null);
        mpgSlider.addChangeListener(sliderListener);
        hpSlider.addChangeListener(sliderListener);

        inputField.setText("ALL");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        resultsFrame.setVisible(true);
        runQuery(null);
    }

    private void runQuery(ActionEvent e) {
        String text = inputField.getText().trim();
        int minMpg = mpgSlider.getValue();
        int minHp = hpSlider.getValue();

        boolean showAll = text.equalsIgnoreCase("ALL") || text.isEmpty();

        String sql;
        if (showAll) {
            sql = "SELECT mpg, horsepower, car_name FROM AutoData " +
                  "WHERE mpg >= ? AND horsepower >= ? " +
                  "ORDER BY mpg ASC";
        } else {
            sql = "SELECT mpg, horsepower, car_name FROM AutoData " +
                  "WHERE car_name LIKE ? AND mpg >= ? AND horsepower >= ? " +
                  "ORDER BY mpg ASC";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Input: ").append(text).append("\n");
        sb.append("Min MPG: ").append(minMpg).append(" | Min HP: ").append(minHp).append("\n\n");

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            int idx = 1;
            if (!showAll) {
                ps.setString(idx++, "%" + text + "%");
            }
            ps.setInt(idx++, minMpg);
            ps.setInt(idx, minHp);

            int count = 0;
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    double mpg = rs.getDouble("mpg");
                    double hp = rs.getDouble("horsepower");
                    String name = rs.getString("car_name");

                    sb.append(String.format("mpg=%.1f  hp=%.1f  name=%s%n", mpg, hp, name));
                    count++;
                }
            }
            sb.append("\nTotal rows: ").append(count).append("\n");
            resultArea.setText(sb.toString());

        } catch (SQLException ex) {
            resultArea.setText("SQL Error:\n" + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AutoGuiSearch().setVisible(true));
    }
}
