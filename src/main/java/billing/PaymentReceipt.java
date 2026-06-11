/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package billing;

import java.awt.*;
import java.awt.event.*;
import java.time.*;
import javax.swing.*;
import javax.swing.BorderFactory;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
/**
 *
 * @author Mickey
 */
class PaymentReceipt extends JPanel implements ActionListener{
    private JLabel lblPage, lblInvoice, lblRes, lblPick, lblDrop,lblDays,
            lblBreakdown, lblRentalCost, lblClean, lblDamage, lblLate,
            lblSubTotal, lblTax, lblTotal,lblTotalAmount, lblCostPrice, lblCleanCost,
            lblDamageCost, lblLateCost,lblTaxCost;
    private JPanel panelBill,panelMenu;
    private JButton btnBack, btnContinue;
    private String resNum, pickDeets, dropDeets, name, plate,rate,reservationNumber, customerName;
    private int daysTotal;
    private LocalDate pickDate;
    private LocalDate dropDate;
    
    PaymentReceipt(String resNum,String pickDeets, String dropDeets,Integer daysTotal, String name, 
            String plate, String rate, String reservationNumber, String customerName,LocalDate pickDate, LocalDate dropDate) {
        this.resNum = resNum;
        this.pickDeets = pickDeets;
        this.dropDeets = dropDeets;
        this.daysTotal = daysTotal;
        this.name = name;
        this.plate = plate;
        this.rate = rate;
        this.reservationNumber = reservationNumber;
        this.customerName = customerName;
        this.pickDate = pickDate;
        this.dropDate = dropDate;
        double rentalRate = Double.parseDouble(rate.replace("P", ""));
        
        setSize(600,600);
        setLayout(null);
        setOpaque(false);
        Color darkAzure = new Color(0, 95, 115);
        
        panelBill = new JPanel ();
        panelBill.setLayout(null);
        panelBill.setBounds(220,20,135,40);
        add(panelBill);
        panelBill.setBorder(BorderFactory.createEtchedBorder());
        
        panelMenu = new JPanel ();
        panelMenu.setLayout(null);
        panelMenu.setBounds(75,100,450,400);
        add(panelMenu);
        panelMenu.setBorder(BorderFactory.createEtchedBorder());
        panelMenu.setBackground(Color.WHITE);
        
        lblPage = new JLabel ("PAYMENT DETAILS");
        lblPage.setBounds(10,0,150,40);
        panelBill.add(lblPage);
        panelBill.setBackground(Color.WHITE);
        
        lblPage = new JLabel ("Here are your full billing details.");
        lblPage.setBounds(200,40,500,75);
        add(lblPage);
        
        btnContinue = new JButton ("Confirm");
        btnContinue.setBounds(475,520,100,30);
        add(btnContinue);
        
        lblInvoice = new JLabel ("CAR INVOICE");
        lblInvoice.setBounds(180,40,100,20);
        panelMenu.add(lblInvoice);
        
        lblInvoice = new JLabel ("=========================================================================");
        lblInvoice.setBounds(0,20,600,10);
        panelMenu.add(lblInvoice);
        
        lblInvoice = new JLabel ("=========================================================================");
        lblInvoice.setBounds(0,60,600,20);
        panelMenu.add(lblInvoice);
        
        lblRes = new JLabel ("RESERVATION NUMBER: ");
        lblRes.setBounds(90,40,600,100);
        panelMenu.add(lblRes);
        
        lblRes = new JLabel (resNum);
        lblRes.setBounds(270,40,600,100);
        panelMenu.add(lblRes);
        
        lblPick = new JLabel ("PICK-UP DATE: ");
        lblPick.setBounds(90,60,600,100);
        panelMenu.add(lblPick);
        
        lblRes = new JLabel (pickDeets);
        lblRes.setBounds(270,60,600,100);
        panelMenu.add(lblRes);
        
        lblDrop = new JLabel ("DROP-OFF DATE: ");
        lblDrop.setBounds(90,80,600,100);
        panelMenu.add(lblDrop);
        
        lblRes = new JLabel (dropDeets);
        lblRes.setBounds(270,80,600,100);
        panelMenu.add(lblRes);
        
        lblDays = new JLabel ("RENTED DAYS: ");
        lblDays.setBounds(90,100,600,100);
        panelMenu.add(lblDays);
        
        lblDays = new JLabel (String.valueOf(daysTotal));
        lblDays.setBounds(270,100,600,100);
        panelMenu.add(lblDays);
        //lblTaxCost;
        
        lblInvoice = new JLabel ("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        lblInvoice.setBounds(0,155,600,20);
        panelMenu.add(lblInvoice);
        
        lblBreakdown = new JLabel("BREAKDOWN: ");
        lblBreakdown.setBounds(10, 130, 600, 100);
        panelMenu.add(lblBreakdown);
        
        lblRentalCost = new JLabel("Rental Cost:        "+daysTotal+" day/s x "+rentalRate);
        lblRentalCost.setBounds(15, 150, 600, 100);
        panelMenu.add(lblRentalCost);
        
        lblCostPrice = new JLabel("P"+String.format("%,.2f",daysTotal*rentalRate));
        lblCostPrice.setBounds(270, 150, 600, 100);
        panelMenu.add(lblCostPrice);
        
        lblClean = new JLabel("Cleaning Fee: ");
        lblClean.setBounds(15, 170, 600, 100);
        panelMenu.add(lblClean);
        
        lblCleanCost = new JLabel("P400.00");
        lblCleanCost.setBounds(270, 170, 600, 100);
        panelMenu.add(lblCleanCost);
        
        lblDamage = new JLabel("Damage Fee: ");
        lblDamage.setBounds(15, 190, 600, 100);
        panelMenu.add(lblDamage);
        
        lblDamageCost = new JLabel("P"+String.format("%,.2f",rentalRate*0.50));
        lblDamageCost.setBounds(270, 190, 600, 100);
        panelMenu.add(lblDamageCost);
        
        lblLate = new JLabel("Late Penalty: ");
        lblLate.setBounds(15, 210, 600, 100);
        panelMenu.add(lblLate);
        
        lblLateCost = new JLabel("P600.00");
        lblLateCost.setBounds(270, 210, 600, 100);
        panelMenu.add(lblLateCost);
        
        lblInvoice = new JLabel ("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        lblInvoice.setBounds(0,265,600,20);
        panelMenu.add(lblInvoice);
        
        lblInvoice = new JLabel ("SUBTOTAL: ");
        lblInvoice.setBounds(15,280,600,20);
        panelMenu.add(lblInvoice);
        
        double subTotal = 400 + 600 + (rentalRate * 0.50) + (rentalRate * daysTotal);
        lblSubTotal = new JLabel("P" + String.format("%,.2f", subTotal));
        lblSubTotal.setBounds(270, 280, 600, 20);
        panelMenu.add(lblSubTotal);
        
        lblTax = new JLabel ("Tax (12%): ");
        lblTax.setBounds(15,300,600,20);
        panelMenu.add(lblTax);
        
        double tax = subTotal*0.12;
        lblTaxCost = new JLabel ("P"+String.format("%,.2f",tax));
        lblTaxCost.setBounds(270,300,600,20);
        panelMenu.add(lblTaxCost);
        
        lblInvoice = new JLabel ("=========================================================================");
        lblInvoice.setBounds(0,325,600,10);
        panelMenu.add(lblInvoice);
        
        lblTotal = new JLabel ("TOTAL AMOUNT: ");
        lblTotal.setBounds(15,345,600,20);
        panelMenu.add(lblTotal);
        
        lblTotalAmount = new JLabel ("P"+String.format("%,.2f",subTotal+tax));
        lblTotalAmount.setBounds(270,345,600,20);
        panelMenu.add(lblTotalAmount);
        
        lblInvoice = new JLabel ("=========================================================================");
        lblInvoice.setBounds(0,375,600,10);
        panelMenu.add(lblInvoice);
                
        btnContinue.addActionListener(this);
        
    }

    @Override
public void actionPerformed(ActionEvent e) {
    if (e.getSource() == btnContinue) {
     
        double rentalRate = Double.parseDouble(rate.replace("P", ""));
        double subTotal = 400 + 600 + (rentalRate * 0.50) + (rentalRate * daysTotal);
        double tax = subTotal * 0.12;
        double totalAmount = subTotal + tax;

        try (Connection conn = DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/db_rentalcar", "root", "")) {

    String sql = "INSERT INTO payments (reservation_id, amount, method_pay_id, pay_status_id) VALUES (?, ?, ?, ?)";
    PreparedStatement ps = conn.prepareStatement(sql);

    ps.setString(1, reservationNumber);
    ps.setDouble(2, totalAmount);
    ps.setInt(3, 1);
    ps.setInt(4, 1);

    int rows = ps.executeUpdate();
    if (rows > 0) {
        JOptionPane.showMessageDialog(null, "Payment confirmed and saved!");
    } else {
        JOptionPane.showMessageDialog(null, "Payment not saved!");
    }
} catch (Exception ex) {
    ex.printStackTrace();
    JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
    return;
}
        JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        Container background = mainFrame.getContentPane();
        background.remove(this);

        Payment ap = new Payment(
            resNum,
            pickDeets,
            dropDeets,
            daysTotal,
            name,
            plate,
            rate,
            reservationNumber,
            customerName,
            pickDate,
            dropDate
        );
        ap.setBounds(800, 250, 1366, 768);
        background.add(ap);
        background.revalidate();
        background.repaint();
    }
}
}
