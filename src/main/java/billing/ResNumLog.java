/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package billing;

import carrentalsystemmain.FoundationFrame;
import java.awt.Color;
import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import reservation.*;
/**
 *
 * @author Mickey
 */
public class ResNumLog extends JPanel implements ActionListener {
    private JLabel lblPage,lblResNo;
    private JTextField txtResNo;
    private JButton btnEnter,btnBack;
    private String reservationNumber,rate, name, plate, customerName,details, carId;    
    
    public ResNumLog(
            String reservationNumber,
            String rate,
            String name,
            String plate,
            String customerName,
            String details,
            String carId) {

        this.reservationNumber = reservationNumber;
        this.rate = rate;
        this.name = name;
        this.plate = plate;
        this.customerName = customerName;
        this.details = details;
        this.carId = carId;
    
        
        setBounds(600,100,600,600);
        setLayout(null);
        setOpaque(false);
        Color darkAzure = new Color(0, 95, 115);
        
        lblPage = new JLabel ("PAYMENT PORTAL");
        lblPage.setBounds(220,75,200,100);
        lblPage.setFont(new Font("Poppins",Font.BOLD,20));
        add(lblPage);
        
        lblResNo = new JLabel ("RESERVATION NUMBER: ");
        lblResNo.setBounds(100,150,200,100);
        lblResNo.setFont(new Font("Poppins",Font.BOLD,15));
        add(lblResNo);
        
        txtResNo = new JTextField ();
        txtResNo.setBounds(300,175,200,40);
        add(txtResNo);
        
        btnEnter = new JButton ("Enter");
        btnEnter.setBounds(320, 350, 120, 40);        
        btnEnter.setFont(new Font("Poppins",Font.BOLD,15));
        btnEnter.setForeground(Color.WHITE);
        btnEnter.setBackground(darkAzure);
        btnEnter.setOpaque(true);
        btnEnter.setFocusPainted(false);
        add(btnEnter);
        
        btnBack = new JButton("Back");
        btnBack.setBounds(180, 350, 120, 40);        
        btnBack.setFont(new Font("Poppins",Font.BOLD,15));
        btnBack.setForeground(Color.WHITE);
        btnBack.setBackground(darkAzure);
        btnBack.setOpaque(true);
        btnBack.setFocusPainted(false);
        add(btnBack);
        

        btnBack.addActionListener(this);
        btnEnter.addActionListener(this);
        
        UIManager.put("OptionPane.background", Color.WHITE);
        UIManager.put("Panel.background", Color.WHITE);
        UIManager.put("OptionPane.messageFont",new Font("Poppins", Font.BOLD, 14));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnEnter) {

            String resNoText = txtResNo.getText().trim();

            if (resNoText == null || resNoText.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a reservation number!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (resNoText.equals(reservationNumber)) {
                JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                Container background = mainFrame.getContentPane();
                background.remove(this);
                DatesBilling ap = new DatesBilling(reservationNumber, rate, name, plate, customerName);
                ap.setBounds(800, 250, 1366, 768);
                background.add(ap);
                background.revalidate();
                background.repaint();
            } else {
                JOptionPane.showMessageDialog(null,
                        "Reservation number is not recorded. Try again!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource()==btnBack){
            JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            Container background = mainFrame.getContentPane();

            background.remove(this);

            ReservationDetailsFrame rdf
                    = new ReservationDetailsFrame(
                            details,
                            reservationNumber,
                            rate,
                            name,
                            plate,
                            customerName,
                            carId
                    );

            rdf.setBounds(900, 175, 600, 600);

            background.add(rdf);
            background.revalidate();
            background.repaint();
        }
    }
    
}
