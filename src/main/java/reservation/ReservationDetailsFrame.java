    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reservation;

import billing.ResNumLog;
import carrentalsystemmain.FoundationFrame;
import java.awt.Container;
import java.awt.Font;
import javax.swing.*;
import vehicle.Vehicle;


import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Mickey
 */
    public class ReservationDetailsFrame extends JPanel {

    JTextArea detailsArea;
    String rate, name, plate, customerName, carId;
    private Reservation reservationPanel; 
    
    public ReservationDetailsFrame(
        String details,
        String reservationNumber,
        String rate,
        String name,
        String plate,
        String customerName,
        String carId,
        Reservation reservationPanel 
    ) 
    
    {
        this.rate = rate;
        this.name = name;
        this.plate = plate;
        this.customerName = customerName;
        this.carId = carId;
        this.reservationPanel = reservationPanel;
            
            
        setBounds(1000,100,600, 600);

        setLayout(null);

        JLabel titleLabel = new JLabel("RESERVATION DETAILS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBounds(160, 30, 300, 30);

        detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
        detailsArea.setText(details);

        JScrollPane scrollPane = new JScrollPane(detailsArea);
        scrollPane.setBounds(70, 100, 450, 320);

        JButton backButton = new JButton("Back");
        backButton.setBounds(150, 460, 120, 40);

        JButton closeButton = new JButton("Check Out");
        closeButton.setBounds(300, 460, 120, 40);

        closeButton.addActionListener(e -> {
        JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        Container background = mainFrame.getContentPane();
        background.remove(this);

         ResNumLog ap = new ResNumLog(
            reservationNumber,
            rate,
            name,
            plate,
            customerName,
            details,
            carId,
            reservationPanel 
        );


        ap.setBounds(850, 200, 1366, 768);
        background.add(ap);
        background.revalidate();
        background.repaint();
    });
        
        backButton.addActionListener(e -> {
    int confirm = JOptionPane.showConfirmDialog(this,
        "Going back will permanently delete your reservation and free the car. Continue?",
        "Confirm Deletion",
        JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
        try (Connection conn = DBConnection.getConnection()) {
         
            String deleteSql = "DELETE FROM reservation WHERE reservation_id = ?";
            PreparedStatement psDelete = conn.prepareStatement(deleteSql);
            psDelete.setString(1, reservationNumber);
            psDelete.executeUpdate();

        
            String updateSql = "UPDATE car SET car_status_id = (SELECT car_status_id FROM car_status WHERE car_status = 'AVAILABLE') WHERE car_id = ?";
            PreparedStatement psUpdate = conn.prepareStatement(updateSql);
            psUpdate.setString(1, carId);
            psUpdate.executeUpdate();

            JOptionPane.showMessageDialog(this,
                "Reservation deleted and car status reset to AVAILABLE.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error deleting reservation or updating car status: " + ex.getMessage(),
                "DB Error",
                JOptionPane.ERROR_MESSAGE);
        }

 
        JFrame current = (JFrame) SwingUtilities.getWindowAncestor(this);
        current.dispose();

        FoundationFrame ff = new FoundationFrame(new Vehicle());
    }
});

        add(titleLabel);
        add(scrollPane);
        add(closeButton);
        add(backButton);

        setVisible(true);
    }
}