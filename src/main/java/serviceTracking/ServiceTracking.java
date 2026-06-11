package serviceTracking;

import Services.*;
import carrentalsystemmain.AdminOption;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import carrentalsystemmain.*;
import serviceTracking.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import database.DBConnection;

public class ServiceTracking extends JPanel {
    public static DefaultTableModel vehicleModel;

    public ServiceTracking(DefaultTableModel model) {

        this.vehicleModel = model;

        setBounds(800, 200, 1000, 600);
        setLayout(null);
        setOpaque(false);
        Color darkAzure = new Color(0, 95, 115);
                
        //STATUS CHANGE
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Arial", Font.BOLD, 14));
        tabs.setBounds(0, 0, 950, 560);

        JPanel vehiclePanel = new JPanel(null);
        JLabel lbltitle = new JLabel("VEHICLE MANAGEMENT");
        lbltitle.setFont(new Font("Arial", Font.BOLD, 16));
        lbltitle.setBounds(50, 20, 300, 30);

        JTable vehicleTable = new JTable(model);
        vehicleTable.setRowHeight(30);
        vehicleTable.setFont(new Font("Arial", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(vehicleTable);
        scroll.setBounds(20, 60, 900, 330);
        
        JButton btnAdd = new JButton("Add");
        btnAdd.setBounds(30, 430, 120, 40);
        
        JButton btnSearch = new JButton("Search");
        btnSearch.setBounds(170, 430, 120, 40);
        
        JButton btnDel = new JButton("Delete");
        btnDel.setBounds(450, 430, 120, 40);

        
        JButton btnUpdateDet = new JButton("Update");
        btnUpdateDet.setBounds(310, 430, 120, 40);

        JButton btnStatus = new JButton("Change Status");
        btnStatus.setBounds(590, 430, 170, 40);
        
        JButton btnback = new JButton("Back");
        btnback.setBounds(780, 430, 120, 40);

        
        
        btnback.addActionListener(e -> {
            JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            currentFrame.dispose();

            JFrame mainFrame = new JFrame();
            mainFrame.setSize(1366, 768);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

            JLabel background = new JLabel(new ImageIcon(Main.class.getResource("/img/firstBG.png")));
            background.setLayout(null);
            AdminOption ap = new AdminOption();
            ap.setBounds(1100, 250, 1366, 768);
            background.add(ap);
            mainFrame.setContentPane(background);
            mainFrame.setVisible(true);
        });

        btnStatus.addActionListener(e -> {
            int row = vehicleTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Please select a vehicle.", "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            String[] options = {"AVAILABLE", "RESERVED", "RENTED", "UNDER MAINTENANCE"};
            String chosen = (String) JOptionPane.showInputDialog(
                null, "Select new status:", "Change Status",
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]
            );
             if (chosen != null) {
                String id = model.getValueAt(row, 0).toString();
                model.setValueAt(chosen, row, 3);

                int statusId = switch (chosen) {
                    case "AVAILABLE" -> 1;
                    case "RESERVED" -> 2;
                    case "RENTED" -> 3;
                    case "UNDER MAINTENANCE" -> 4;
                    default -> 1;
                };

                try (Connection conn = DBConnection.getConnection()) {
                    String sql = "UPDATE car SET car_status_id=? WHERE car_id=?";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setInt(1, statusId);
                    ps.setString(2, id);
                    ps.executeUpdate();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
       btnAdd.addActionListener(e -> {
            String id = JOptionPane.showInputDialog("Vehicle ID:");
            if (id == null || id.trim().isEmpty()) return;

            String plate = JOptionPane.showInputDialog("Plate Number:");
            if (plate == null || plate.trim().isEmpty()) return;

            String name = JOptionPane.showInputDialog("Vehicle Name:");
            if (name == null || name.trim().isEmpty()) return;

            String rate = JOptionPane.showInputDialog("Rate:");
            if (rate == null || rate.trim().isEmpty()) return;

            try (Connection conn = DBConnection.getConnection()) {
               
                String checkSql = "SELECT COUNT(*) FROM car WHERE car_id = ? OR plate_no = ?";
                PreparedStatement psCheck = conn.prepareStatement(checkSql);
                psCheck.setString(1, id);
                psCheck.setString(2, plate);
                var rs = psCheck.executeQuery();

                if (rs.next() && rs.getInt(1) > 0) {
                    JOptionPane.showMessageDialog(null,
                        "A vehicle with this ID or Plate Number already exists!",
                        "Duplicate Entry",
                        JOptionPane.ERROR_MESSAGE);
                    return; 
                }

               
                String sql = "INSERT INTO car (car_id, car_status_id, plate_no, car_name, rate) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, id);
                ps.setInt(2, 1);
                ps.setString(3, plate);
                ps.setString(4, name);
                ps.setInt(5, Integer.parseInt(rate));
                ps.executeUpdate();

               
                model.addRow(new Object[]{id, plate, name, "AVAILABLE", "P" + rate});
                JOptionPane.showMessageDialog(null, "Vehicle added successfully!");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,
                    "Error adding vehicle: " + ex.getMessage(),
                    "DB Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
       
        btnDel.addActionListener(e -> {

          int row = vehicleTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Select a vehicle first.");
                return;
            }

            String id = model.getValueAt(row, 0).toString();
            model.removeRow(row);

            try (Connection conn = DBConnection.getConnection()) {
                String sql = "DELETE FROM car WHERE car_id=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, id);
                ps.executeUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            JOptionPane.showMessageDialog(null, "Vehicle deleted.");
        });
        
        btnUpdateDet.addActionListener(e -> {

            int row = vehicleTable.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(null,
                        "Select a vehicle first.");
                return;
            }

            String plate = JOptionPane.showInputDialog("Plate Number:", model.getValueAt(row, 1));
            String name = JOptionPane.showInputDialog("Vehicle Name:", model.getValueAt(row, 2));
            String rate = JOptionPane.showInputDialog("Rate:", model.getValueAt(row, 4));

            if (plate != null) {
                model.setValueAt(plate, row, 1);
            }
            if (plate == null || name == null || rate == null) {
                return;
            }

            if (plate.trim().isEmpty()
                    || name.trim().isEmpty()
                    || rate.trim().isEmpty()) {

                JOptionPane.showMessageDialog(
                        null,
                        "All fields are required."
                );
                return;
            }

            if (name != null) {
                model.setValueAt(name, row, 2);
            }

            if (rate != null) {
                model.setValueAt(rate, row, 4);
            }


            String id = model.getValueAt(row, 0).toString();
            model.setValueAt(plate, row, 1);
            model.setValueAt(name, row, 2);
            model.setValueAt(rate, row, 4);

            try (Connection conn = DBConnection.getConnection()) {
                String sql = "UPDATE car SET plate_no=?, car_name=?, rate=? WHERE car_id=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, plate);
                ps.setString(2, name);
                ps.setInt(3, Integer.parseInt(rate.replace("P", "")));
                ps.setString(4, id);
                ps.executeUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            JOptionPane.showMessageDialog(null, "Vehicle updated.");
        });
        
        btnSearch.addActionListener(e -> {

            String keyword = JOptionPane.showInputDialog("Enter Vehicle ID or Name:");

            if (keyword == null || keyword.isEmpty()) {
                return;
            }

            boolean found = false;

            for (int i = 0; i < model.getRowCount(); i++) {

                String id
                        = model.getValueAt(i, 0)
                                .toString();

                String name
                        = model.getValueAt(i, 2)
                                .toString();

                if (id.equalsIgnoreCase(keyword)
                        || name.toLowerCase()
                                .contains(keyword.toLowerCase())) {

                    vehicleTable.setRowSelectionInterval(i, i);

                    vehicleTable.scrollRectToVisible(
                            vehicleTable.getCellRect(
                                    i, 0, true));

                    found = true;
                    break;
                }
            }

            if (!found) {
                JOptionPane.showMessageDialog(
                        null,
                        "Vehicle not found.");
            }
        });

        vehiclePanel.add(lbltitle);
        vehiclePanel.add(scroll);
        vehiclePanel.add(btnStatus);
        vehiclePanel.add(btnback);
        vehiclePanel.add(btnAdd);
        vehiclePanel.add(btnSearch);
        vehiclePanel.add(btnUpdateDet);
        vehiclePanel.add(btnDel);
        
        //HISTORY   
        JPanel historyPanel = new JPanel(null);
        JLabel lbltitle2 = new JLabel("VEHICLE HISTORY");
        lbltitle2.setFont(new Font("Arial", Font.BOLD, 16));
        lbltitle2.setBounds(50, 20, 300, 30);
        History h = new History();
        h.setBounds(0, 0, 900, 500);
        historyPanel.add(h);       
        historyPanel.add(lbltitle2);
        
        //MAINTENANCE       
        JPanel maintenancePanel = new JPanel(null);
        JLabel lbltitle3 = new JLabel("VEHICLE MAINTENANCE");
        lbltitle3.setFont(new Font("Arial", Font.BOLD, 16));
        lbltitle3.setBounds(50, 20, 300, 30);
       
        maintenancePanel.add(lbltitle3);
        Maintenance m = new Maintenance();
        m.setBounds(20, 20, 900, 500);
        maintenancePanel.add(m);
        
       
        tabs.addTab("Vehicles", vehiclePanel);
        tabs.addTab("History", historyPanel);
        tabs.addTab("Maintenance", maintenancePanel);
        
        add(tabs);
        loadCarsFromDB();
        setVisible(true);
    }
    
    private void loadCarsFromDB() {
    try (Connection conn = DBConnection.getConnection()) {
        String sql = "SELECT c.car_id, c.plate_no, c.car_name, s.car_status, c.rate " +
                     "FROM car c JOIN car_status s ON c.car_status_id = s.car_status_id";
        PreparedStatement ps = conn.prepareStatement(sql);
        var rs = ps.executeQuery();

        vehicleModel.setRowCount(0);
        while (rs.next()) {
            vehicleModel.addRow(new Object[]{
                rs.getString("car_id"),
                rs.getString("plate_no"),
                rs.getString("car_name"),
                rs.getString("car_status"),
                "P" + rs.getInt("rate")
            });
        }
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error loading cars from database.", "DB Error", JOptionPane.ERROR_MESSAGE);
    }
}
}
