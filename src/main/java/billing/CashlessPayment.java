package billing;

import java.awt.*;
import java.awt.event.*;
import java.time.*;
import javax.swing.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class CashlessPayment extends JPanel implements ActionListener {

    private JPanel panelBill, panelMenu;
    private JLabel lblMode;
    private JButton btnCard, btnQR,btnBack;
    private String resNum, pickDeets, dropDeets, name, plate, rate, reservationNumber, customerName;
    private int daysTotal;
    private LocalDate pickDate;
    private LocalDate dropDate;
    
    public CashlessPayment(String resNum,String pickDeets, String dropDeets,Integer daysTotal, String name, 
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

        setBounds(600,100,600,600);
        setLayout(null);
        setOpaque(false);
        Color darkAzure = new Color(0, 95, 115);
        
        panelBill = new JPanel();
        panelBill.setLayout(null);
        panelBill.setBounds(200,20,200,40);
        panelBill.setBorder(BorderFactory.createEtchedBorder());
        panelBill.setBackground(Color.WHITE);
        add(panelBill);

        lblMode = new JLabel("CASHLESS PAYMENT");
        lblMode.setBounds(20,0,200,40);
        panelBill.add(lblMode);

        panelMenu = new JPanel();
        panelMenu.setLayout(null);
        panelMenu.setBounds(75,100,450,400);
        panelMenu.setBorder(BorderFactory.createEtchedBorder());
        panelMenu.setBackground(Color.WHITE);
        add(panelMenu);

        btnCard = new JButton("CARD");
        btnCard.setBounds(125,120,200,50);
        panelMenu.add(btnCard);

        btnQR = new JButton("QR CODE");
        btnQR.setBounds(125,220,200,50);
        panelMenu.add(btnQR);
        

        
        btnBack = new JButton ("Back");
        btnBack.setBounds(50,350,100,25);
        panelMenu.add(btnBack);
        
        btnBack.addActionListener(this);
        btnCard.addActionListener(this);
        btnQR.addActionListener(this);

        setVisible(true);
    }

@Override
public void actionPerformed(ActionEvent e) {

    if (e.getSource() == btnCard) {
        try (Connection conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/db_carrental", "root", "")) {

            String sql = "INSERT INTO payments (reservation_id, amount, method_pay_id, pay_status_id) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            double amount = daysTotal * Double.parseDouble(rate.replace("P", ""));
            ps.setString(1, reservationNumber);
            ps.setDouble(2, amount);
            ps.setInt(3, 2);
            ps.setInt(4, 1); 

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Card payment saved successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        }

        JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        Container background = mainFrame.getContentPane();
        background.remove(this);

        SummaryReceipt sr = new SummaryReceipt(
            resNum, pickDeets, dropDeets, daysTotal, name, plate, rate,
            reservationNumber, "CARD", customerName, pickDate, dropDate
        );
        sr.setBounds(550, 200, 1366, 768);
        background.add(sr);
        background.revalidate();
        background.repaint();
    }
    else if (e.getSource() == btnQR) {
        
        try (Connection conn = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/db_rentalcar", "root", "")) {

            String sql = "INSERT INTO payments (reservation_id, amount, method_pay_id, pay_status_id) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            double amount = daysTotal * Double.parseDouble(rate.replace("P", ""));
            ps.setString(1, reservationNumber);
            ps.setDouble(2, amount);
            ps.setInt(3, 3); 
            ps.setInt(4, 1); 

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "QR Code payment saved successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        }
        
    
        JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        Container background = mainFrame.getContentPane();
        background.remove(this);

        SummaryReceipt sr = new SummaryReceipt(
            resNum, pickDeets, dropDeets, daysTotal, name, plate, rate,
            reservationNumber, "QR CODE", customerName, pickDate, dropDate
        );
        sr.setBounds(800, 250, 1366, 768);
        background.add(sr);
        background.revalidate();
        background.repaint();
    }
    else if (e.getSource() == btnBack) {
        JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        Container background = mainFrame.getContentPane();
        background.remove(this);

        Payment ap = new Payment(
            resNum, pickDeets, dropDeets, daysTotal, name, plate, rate,
            reservationNumber, customerName, pickDate, dropDate
        );
        ap.setBounds(800, 250, 1366, 768);
        background.add(ap);
        background.revalidate();
        background.repaint();
    }
}
}