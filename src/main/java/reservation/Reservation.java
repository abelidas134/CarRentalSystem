package reservation;
import carrentalsystemmain.FoundationFrame;
import javax.swing.*;
import java.awt.*;
import carrentalsystemmain.*;
import billing.*;
import vehicle.*;
import customers.*;



public class Reservation extends JPanel {

    static int reservationCounter = 1001;

    JTextField nameField, contactField, emailField, licenseField, addressField;
    JLabel statusLabel;
    private String rate, name, plate, customerName;
    public Reservation(String rate,String name, String plate) {
        this.rate = rate;
        this.name = name;
        this.plate = plate;
        setBounds(800,175,1000, 600);

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

        JLabel emailLabel = new JLabel("Email Address (Optional)");
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

        statusLabel = new JLabel("");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 15));
        statusLabel.setBounds(420, 470, 300, 30);

        reserveButton.addActionListener(e -> reserveCar());

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
        
        
            
        add(reserveButton);

        add(statusLabel);

        setVisible(true);
    }

   public void reserveCar() {
        String name = nameField.getText();
        this.customerName = name;
        String contact = contactField.getText();
        String email = emailField.getText();
        String licensenum = licenseField.getText();
        String address = addressField.getText();
        
        if (name.isEmpty() || contact.isEmpty()
                || licensenum.isEmpty()) {

            statusLabel.setText("Please fill in the fields!");
            return;
        }
if (email == null || email.trim().isEmpty()) {
    email = "N/A";
}

if (address == null || address.trim().isEmpty()) {
    address = "N/A";
}
        String reservationNumber = "CR-" + reservationCounter++;
        
        Customers.addCustomer(
               reservationCounter - 1,
               name,
               contact,
               licensenum,
               address
       );

        String details =
                "RESERVATION DETAILS\n\n"
                + "Reservation ID     : " + reservationNumber + "\n\n"
                + "Customer Name      : " + name + "\n\n"
                + "Contact Number     : " + contact + "\n\n"
                + "Email Address      : " + email + "\n\n"
                + "Driver's Address   : " + address + "\n\n"
                + "Driver's License Number  : " + licensenum + "\n\n";

        JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
       Container background = mainFrame.getContentPane();

       background.remove(this);

       ReservationDetailsFrame rdf
               = new ReservationDetailsFrame(
                       details,
                       reservationNumber,
                       this.rate,
                       this.name,
                       this.plate,
                       this.customerName
               );
       rdf.setBounds(900, 175, 600, 600);

       background.add(rdf);

       background.revalidate();
       background.repaint();

        statusLabel.setText("Reservation Successful!");

        nameField.setText("");
        contactField.setText("");
        licenseField.setText("");
        System.out.println("Vehicle Name = " + this.name);
        System.out.println("Customer Name = " + this.customerName);
    }
}
