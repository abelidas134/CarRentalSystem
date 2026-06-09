package serviceTracking;

import java.awt.Color;
import javax.swing.*;

public class History extends JPanel {

    public static JTextArea txtHistory;

    public History() {

        setSize(900,500);
        setLayout(null);
        setOpaque(false);

        txtHistory = new JTextArea();
        txtHistory.setEditable(false);
        txtHistory.setLineWrap(true);
        txtHistory.setWrapStyleWord(true);

        txtHistory.setText(
                "=== VEHICLE MAINTENANCE HISTORY ===\n\n"
        );

        JScrollPane scrollPane = new JScrollPane(txtHistory);
        scrollPane.setBounds(30, 60, 850, 350);
        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane);
    }
}