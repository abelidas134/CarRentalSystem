package reservation;

import carrentalsystemmain.FoundationFrame;
import javax.swing.*;
import java.awt.*;
import carrentalsystemmain.*;
import billing.*;
import vehicle.*;
import customers.*;
import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Reservation extends JPanel {

    
    JTextField nameField, contactField, emailField, licenseField, addressField;
    JLabel statusLabel;
    private String details;
    private String reservationNumber;
    private String rate, name, plate, customerName, carId;

    public Reservation(String rate, String name, String plate, String carId) {
        this.rate = rate;
        this.name = name;
        this.plate = plate;
        this.carId = carId;
        setBounds(800, 175, 1000, 600);

        setLayout(null);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(5, 475, 180, 40);
        add(backBtn);

        backBtn.addActionListener(e -> {
            JFrame current = (JFrame) SwingUtilities.getWindowAncestor(this);
            current.dispose();

            FoundationFrame ff = new FoundationFrame(new Vehicle());
        });

        JLabel titleLabel = new JLabel("BOOKING AND RESERVATION");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setBounds(320, 30, 500, 40);

        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        nameLabel.setBounds(150, 120, 250, 30);

        nameField = new JTextField();
        nameField.setBounds(420, 120, 300, 35);

        JLabel contactLabel = new JLabel("Contact Number:");
        contactLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        contactLabel.setBounds(150, 190, 250, 30);

        contactField = new JTextField();
        contactField.setBounds(420, 190, 300, 35);

        JLabel emailLabel = new JLabel("Email Address:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        emailLabel.setBounds(150, 260, 250, 30);

        emailField = new JTextField();
        emailField.setBounds(420, 260, 300, 35);

        JLabel licenseLabel = new JLabel("Driver's License Number");
        licenseLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        licenseLabel.setBounds(150, 330, 250, 30);

        licenseField = new JTextField();
        licenseField.setBounds(420, 330, 300, 35);

        JLabel addressLabel = new JLabel("House Address(Optional)");
        addressLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        addressLabel.setBounds(150, 400, 250, 30);

        addressField = new JTextField();
        addressField.setBounds(420, 400, 300, 35);

        JButton reserveButton = new JButton("Reserve");
        reserveButton.setFont(new Font("Arial", Font.BOLD, 16));
        reserveButton.setBounds(700, 475, 180, 40);
     
        reserveButton.addActionListener(e -> {
        java.time.LocalDate pickup = java.time.LocalDate.now();
        java.time.LocalDate dropoff = java.time.LocalDate.now().plusDays(1);

    String resNum = reserveCar(pickup, dropoff);
    if (resNum != null) {
        goToReservationDetailsFrame();
    }
});
        
        add(reserveButton);

        statusLabel = new JLabel("");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 15));
        statusLabel.setBounds(420, 470, 300, 30);

        add(titleLabel);

        add(nameLabel);
        add(nameField);

        add(contactLabel);
        add(contactField);

        add(licenseLabel);
        add(licenseField);

        add(emailLabel);
        add(emailField);

        add(addressLabel);
        add(addressField);

        add(statusLabel);

        setVisible(true);
    }

    public String reservationDetails;

    public String reserveCar(java.time.LocalDate d1, java.time.LocalDate d2) {
        String name = nameField.getText();
        this.customerName = name;
        
        String contact = contactField.getText().trim();
        String email = emailField.getText().trim();
        String licensenum = licenseField.getText().trim();
        String address = addressField.getText().trim();

        if (name.isEmpty() || contact.isEmpty() || licensenum.isEmpty()) {
            statusLabel.setText("Please fill in the fields!");
            return null;
        }
        if (!contact.matches("\\d{11}")) {
                JOptionPane.showMessageDialog(this, "Phone number must contain exactly 11 digits.",
            "Invalid Phone Number", JOptionPane.WARNING_MESSAGE);
            return null;
}
        if (email == null || email.trim().isEmpty()) {
            email = "N/A";
        }
        if (address == null || address.trim().isEmpty()) {
            address = "N/A";
        }

        try (Connection conn = DBConnection.getConnection()) {
            
            // Generate ID
            String getLastIdSql = "SELECT reservation_id FROM reservation ORDER BY reservation_id DESC LIMIT 1";
            Statement stmt = conn.createStatement();
            ResultSet rsLast = stmt.executeQuery(getLastIdSql);
            int nextNumber = 1001;
            if (rsLast.next()) {
                String lastId = rsLast.getString("reservation_id");
                nextNumber = Integer.parseInt(lastId.replace("CR-", "")) + 1;
            }
            this.reservationNumber = "CR-" + nextNumber;
            
                        // Insert customer record
            String customerBookSql = "INSERT INTO customer_book(name, phone, email, drivers_license, address) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement customerBookPst = conn.prepareStatement(customerBookSql, Statement.RETURN_GENERATED_KEYS);
            customerBookPst.setString(1, name);
            customerBookPst.setString(2, contact);
            customerBookPst.setString(3, email);
            customerBookPst.setString(4, licensenum);
            customerBookPst.setString(5, address);
            customerBookPst.executeUpdate();

            ResultSet rsCustomer = customerBookPst.getGeneratedKeys();
            int customerBookId = rsCustomer.next() ? rsCustomer.getInt(1) : 0;

            // Insert reservation
            String reservationSql = "INSERT INTO reservation(reservation_id, cus_book_id, car_id, pickup_date, dropoff_date, car_status_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement reservationPst = conn.prepareStatement(reservationSql);
            reservationPst.setString(1, reservationNumber);
            reservationPst.setInt(2, customerBookId);
            reservationPst.setString(3, carId);
            reservationPst.setDate(4, java.sql.Date.valueOf(d1));
            reservationPst.setDate(5, java.sql.Date.valueOf(d2));
            reservationPst.setInt(6, 2);
            reservationPst.executeUpdate();

            // Update car status
            PreparedStatement updateCarPst = conn.prepareStatement("UPDATE car SET car_status_id = ? WHERE car_id = ?");
            updateCarPst.setInt(1, 2);
            updateCarPst.setString(2, carId);
            updateCarPst.executeUpdate();

            String details
                    = "RESERVATION DETAILS\n\n"
                    + "Reservation ID     : " + reservationNumber + "\n\n"
                    + "Customer Name      : " + name + "\n\n"
                    + "Contact Number     : " + contact + "\n\n"
                    + "Email Address      : " + email + "\n\n"
                    + "Driver's Address   : " + address + "\n\n"
                    + "Driver's License Number  : " + licensenum + "\n\n";

    
            this.reservationDetails = details;

            statusLabel.setText("Reservation Successful!");
            return this.reservationNumber;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            return null;
        }
    }
    
    
    
        public void goToReservationDetailsFrame() {
        ReservationDetailsFrame rdf = new ReservationDetailsFrame(
            reservationDetails,
            reservationNumber,
            rate,
            name,
            plate,
            customerName,
            carId,
            this
        );

        JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        Container background = mainFrame.getContentPane();
        background.remove(this);
        background.add(rdf); 
        background.revalidate();
        background.repaint();
    }

    public String getCarId() {
        return carId;
    }

    public String getReservationDetails() {
        return reservationDetails;
    }
}
