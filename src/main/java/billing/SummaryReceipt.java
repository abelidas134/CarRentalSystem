package billing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import carrentalsystemmain.*;
import java.time.*;
import prevention.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import database.DBConnection;
import java.sql.ResultSet;

public class SummaryReceipt extends JPanel implements ActionListener {

    private JPanel panelTitle, panelReceipt;
    private JLabel lblTitle;
    private JButton btnFinish;
    private String resNum, pickDeets, dropDeets, name, plate, rate,
            reservationNumber, paymentMethod, customerName;
    private int daysTotal;
    private LocalDate pickDate;
    private LocalDate dropDate;

    public SummaryReceipt(
            String resNum,
            String pickDeets,
            String dropDeets,
            int daysTotal,
            String name,
            String plate,
            String rate,
            String reservationNumber,
            String paymentMethod,
            String customerName,
            LocalDate pickDate,
            LocalDate dropDate) {

        this.resNum = resNum;
        this.pickDeets = pickDeets;
        this.dropDeets = dropDeets;
        this.daysTotal = daysTotal;
        this.name = name;
        this.plate = plate;
        this.rate = rate;
        this.reservationNumber = reservationNumber;
        this.paymentMethod = paymentMethod;
        this.customerName = customerName;
        this.pickDate = pickDate;
        this.dropDate = dropDate;


        double rentalRate = 0, rentalCost = 0, cleaningFee = 400, damageFee = 0, lateFee = 600;
        double subtotal = 0, tax = 0, total = 0;
        String method = paymentMethod, status = "PAID";

        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/db_rentalcar", "root", "")) {

            String sql = "SELECT amount, method_pay_id, pay_status_id FROM payments WHERE reservation_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, reservationNumber);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getDouble("amount");
                int methodId = rs.getInt("method_pay_id");
                int statusId = rs.getInt("pay_status_id");

                method = (methodId == 1) ? "Cash" :
                         (methodId == 2) ? "Card" : "QR Code";
                status = (statusId == 1) ? "PAID" : "UNPAID";
            }

            rentalRate = Double.parseDouble(rate.replace("P", "").replace(",", "").trim());
            rentalCost = rentalRate * daysTotal;
            damageFee = rentalRate * 0.50;
            subtotal = rentalCost + cleaningFee + damageFee + lateFee;
            tax = subtotal * 0.12;

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        }

        // Layout
        setLayout(null);
        setOpaque(true);
        setBackground(Color.WHITE);

        panelTitle = new JPanel();
        panelTitle.setLayout(null);
        panelTitle.setBounds(200, 20, 200, 40);
        panelTitle.setBorder(BorderFactory.createEtchedBorder());
        panelTitle.setBackground(Color.WHITE);
        add(panelTitle);

        lblTitle = new JLabel("SUMMARY RECEIPT");
        lblTitle.setBounds(25, 0, 200, 40);
        panelTitle.add(lblTitle);

        panelReceipt = new JPanel();
        panelReceipt.setLayout(null);
        panelReceipt.setBounds(75, 90, 450, 400);
        panelReceipt.setBorder(BorderFactory.createEtchedBorder());
        panelReceipt.setBackground(Color.WHITE);
        add(panelReceipt);

        JTextArea receipt = new JTextArea();
        receipt.setEditable(false);
        receipt.setFont(new Font("Monospaced", Font.PLAIN, 13));

        receipt.setText(
                "====================================\n"
                + "          SUMMARY RECEIPT\n"
                + "====================================\n\n"
                + "Reservation Number : " + reservationNumber + "\n"
                + "Customer Name      : " + customerName + "\n"
                + "Vehicle Name       : " + name + "\n"
                + "Plate Number       : " + plate + "\n\n"
                + "Pick-Up Date       : " + pickDeets + "\n"
                + "Drop-Off Date      : " + dropDeets + "\n"
                + "Rental Duration    : " + daysTotal + " day/s\n\n"
                + "------------------------------------\n\n"
                + "Rental Cost        : P" + String.format("%,.2f", rentalCost) + "\n"
                + "Cleaning Fee       : P" + String.format("%,.2f", cleaningFee) + "\n"
                + "Damage Fee         : P" + String.format("%,.2f", damageFee) + "\n"
                + "Late Penalty       : P" + String.format("%,.2f", lateFee) + "\n\n"
                + "------------------------------------\n\n"
                + "Subtotal           : P" + String.format("%,.2f", subtotal) + "\n"
                + "Tax (12%)          : P" + String.format("%,.2f", tax) + "\n\n"
                + "====================================\n"
                + "TOTAL AMOUNT       : P" + String.format("%,.2f", total) + "\n"
                + "====================================\n\n"
                + "Payment Method     : " + method + "\n"
                + "Payment Status     : " + status + "\n\n"
                + "Thank you for choosing\n"
                + "our Car Rental System!"
        );

        JScrollPane scrollPane = new JScrollPane(receipt);
        scrollPane.setBounds(10, 10, 430, 330);
        panelReceipt.add(scrollPane);

        btnFinish = new JButton("Finish");
        btnFinish.setBounds(175, 350, 100, 30);
        btnFinish.addActionListener(this);
        panelReceipt.add(btnFinish);
    }


    @Override
public void actionPerformed(ActionEvent e) {
    if (e.getSource() == btnFinish) {

        double rentalRate = Double.parseDouble(rate.replace("P", ""));
        double rentalCost = rentalRate * daysTotal;
        double cleaningFee = 400;
        double damageFee = rentalRate * 0.50;
        double lateFee = 600;
        double subtotal = rentalCost + cleaningFee + damageFee + lateFee;
        double tax = subtotal * 0.12;
        double total = subtotal + tax;


        if (paymentMethod.equals("QR CODE")) {
            ImageIcon qrIcon = new ImageIcon(getClass().getResource("/img/qr.jpg"));
            Image img = qrIcon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);

            JOptionPane.showMessageDialog(
                    null,
                    "Please scan this QR Code to complete payment.",
                    "QR Payment",
                    JOptionPane.PLAIN_MESSAGE,
                    new ImageIcon(img)
            );

            JOptionPane.showMessageDialog(null, "Payment Successful!");
        } else {
            JOptionPane.showMessageDialog(null, "Transaction Completed Successfully!");
        }

        Prevent.addBooking(plate, pickDate, dropDate);

        JFrame current = (JFrame) SwingUtilities.getWindowAncestor(this);
        current.dispose();
        carrentalsystemmain.Main.openHomepage();
    }
}
}
