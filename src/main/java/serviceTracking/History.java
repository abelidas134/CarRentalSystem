package serviceTracking;

import java.awt.Color;
import javax.swing.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import database.DBConnection;

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
        loadHistoryFromDB();
    }
    
    private void loadHistoryFromDB() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT h.history_id, c.car_name, c.plate_no, h.repair_date, h.repairing, h.next_service_date " +
                         "FROM Car_Maintenance_History h " +
                         "JOIN car c ON h.car_id = c.car_id " +
                         "ORDER BY h.repair_date DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String record = "Vehicle: " + rs.getString("car_name") + " - " + rs.getString("plate_no") +
                        "\nRepair Date: " + rs.getString("repair_date") +
                        "\nWork Performed: " + rs.getString("repairing") +
                        "\nNext Service: " + rs.getString("next_service_date") +
                        "\n----------------------------------------\n";

                txtHistory.append(record);
            }
        } catch (Exception e) {
            e.printStackTrace();
            txtHistory.append("Error loading maintenance history from database.\n");
        }
    }
}