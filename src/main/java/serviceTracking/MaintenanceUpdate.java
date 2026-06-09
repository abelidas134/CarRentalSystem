
package serviceTracking;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import database.DBConnection;

//Updating maintenance
public class MaintenanceUpdate extends JFrame implements ActionListener {
    JLabel lblmaintenanceTitle,lblcarModelPlatenum, lblLastMaintenanceDate, lblRepairing, lblnextService;//suggestion na may client ID tayo per account
    JTextField txtLastMaintenanceDate, txtRepairing, txtnextService;
    JButton btnback, btnUpdate;
    JLabel lblVehicle;
    JComboBox<String> cmbVehicle;
    
    public MaintenanceUpdate(){
        setSize(600,600);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        lblVehicle = new JLabel("Vehicle:");
        lblVehicle.setBounds(30, 90, 100, 30);
        add(lblVehicle);

        cmbVehicle = new JComboBox<>();

        for (int i = 0; i < ServiceTracking.vehicleModel.getRowCount(); i++) {

            String plate
                    = ServiceTracking.vehicleModel
                            .getValueAt(i, 1)
                            .toString();

            String vehicle
                    = ServiceTracking.vehicleModel
                            .getValueAt(i, 2)
                            .toString();

            cmbVehicle.addItem(vehicle + " - " + plate);
        }
        cmbVehicle.setBounds(210, 90, 200, 30);
        add(cmbVehicle);
        
        lblLastMaintenanceDate = new JLabel("Last Repair Date:");
        lblLastMaintenanceDate.setBounds(30, 150, 150, 30);
        add(lblLastMaintenanceDate);
        
        lblRepairing = new JLabel("Repairing:");
        lblRepairing.setBounds(30, 210, 150, 30);
        add(lblRepairing);
        
        lblnextService = new JLabel("Next service:"); 
        lblnextService.setBounds(30, 270, 150, 30);
        add(lblnextService);
        
        txtLastMaintenanceDate = new JTextField("");
        txtLastMaintenanceDate.setBounds(210, 150, 200, 30);
        add(txtLastMaintenanceDate);
        
        txtRepairing = new JTextField("");
        txtRepairing.setBounds(210,210,200, 30);
        add(txtRepairing);
        
        txtnextService = new JTextField (""); 
        txtnextService.setBounds(210, 270, 200, 30);
        add(txtnextService);
        
        btnback = new JButton("Back");
        btnback.setBounds(30,480,100,25);
        add(btnback);
        
        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(450,480,100,25);
        add(btnUpdate);
        
        btnback.addActionListener(this);
        btnUpdate.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent j) {
        String vehicle = cmbVehicle.getSelectedItem().toString();
        
        if (j.getSource() == btnback) 
        {
            dispose();
        }
       
       else if (j.getSource() == btnUpdate) 
       {
            String LastMaintenanceDate = txtLastMaintenanceDate.getText().trim();
            String Repairing = txtRepairing.getText().trim();
            String nextService = txtnextService.getText().trim();

            if (LastMaintenanceDate.isEmpty()
                   || Repairing.isEmpty()
                   || nextService.isEmpty()) {
               JOptionPane.showMessageDialog(null,
                       "Please fill in all fields before updating!");
           } else {
               
                String plate = vehicle.split(" - ")[1];
                String carId = null;

                
                for (int i = 0; i < ServiceTracking.vehicleModel.getRowCount(); i++) {
                    if (ServiceTracking.vehicleModel.getValueAt(i, 1).toString().equals(plate)) {
                        carId = ServiceTracking.vehicleModel.getValueAt(i, 0).toString();
                        break;
                    }
                }

                if (carId == null) {
                    JOptionPane.showMessageDialog(null, "Car ID not found for selected vehicle.");
                    return;
                }

                try (Connection conn = DBConnection.getConnection()) {
                    String sql = "INSERT INTO Car_Maintenance_History (car_id, repair_date, repairing, next_service_date) VALUES (?, ?, ?, ?)";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, carId);
                    ps.setString(2, LastMaintenanceDate);
                    ps.setString(3, Repairing);
                    ps.setString(4, nextService);
                    ps.executeUpdate();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error saving maintenance record to database.");
                    return;
                }

                String record = "Vehicle: " + vehicle
                        + "\nRepair Date: " + LastMaintenanceDate
                        + "\nWork Performed: " + Repairing
                        + "\nNext Service: " + nextService
                        + "\n----------------------------------------\n";

                History.txtHistory.append(record);
                Maintenance.maintenanceStatus.setText(vehicle + " - " + Repairing);

                JOptionPane.showMessageDialog(null, "Maintenance Updated!");
                this.dispose();
            }
        }
    }
    
}
