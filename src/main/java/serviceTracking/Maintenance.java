package serviceTracking;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import database.DBConnection;

//Maintenance
public class Maintenance extends JPanel implements ActionListener{
    JLabel lblcarModelPlatenum, lbl2ndHeadStatus;
    public static JLabel maintenanceStatus;
    JButton btnback, btnUpdateStats;
    
    public Maintenance(){
        setBounds(0, 0, 900, 500);
        setLayout(null);
        setOpaque(false);
        Color darkAzure = new Color(0, 95, 115);
        
        lbl2ndHeadStatus = new JLabel("<< Status >>");
        lbl2ndHeadStatus.setBounds(30, 40, 300, 40);
        add(lbl2ndHeadStatus);
        
        maintenanceStatus = new JLabel("No maintenance updates available.");
        maintenanceStatus.setBounds(30, 100, 700, 40);

        add(maintenanceStatus);
        
        btnUpdateStats = new JButton("UPDATE STATUS");
        btnUpdateStats.setBounds(30, 170, 180, 40);
        add(btnUpdateStats);
        
        

        btnUpdateStats.addActionListener(this);
        loadLatestMaintenance();
    }
    @Override
    public void actionPerformed(ActionEvent j) {
         
          if(j.getSource() == btnUpdateStats)
            {
                MaintenanceUpdate updStatNissan4567 = new MaintenanceUpdate();
                updStatNissan4567.setVisible(true);
            }
    }
    
    private void loadLatestMaintenance() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT c.car_name, c.plate_no, h.repairing, h.repair_date " +
                         "FROM Car_Maintenance_History h " +
                         "JOIN car c ON h.car_id = c.car_id " +
                         "ORDER BY h.history_id DESC LIMIT 1";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String vehicle = rs.getString("car_name") + " - " + rs.getString("plate_no");
                String repairing = rs.getString("repairing");
                String date = rs.getString("repair_date");

                maintenanceStatus.setText(vehicle + " | " + repairing + " (Last repair: " + date + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
            maintenanceStatus.setText("Error loading maintenance info.");
        }
    }
    
}
