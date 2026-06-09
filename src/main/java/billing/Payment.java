/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package billing;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.*;
import java.time.*;
import javax.swing.*;
import prevention.*;

/**
 *
 * @author Mickey
 */
class Payment extends JPanel implements ActionListener{
    private JPanel panelBill,panelMenu;
    private JLabel lblMode;
    private JButton btnCash, btnCashless,btnBack;
    private String resNum, pickDeets, dropDeets, name, plate,rate,reservationNumber, customerName;
    private int daysTotal;
    private LocalDate pickDate;
    private LocalDate dropDate;
    
    Payment(String resNum,String pickDeets, String dropDeets,Integer daysTotal, String name, 
            String plate, String rate, String reservationNumber, String customerName,LocalDate pickDate, LocalDate dropDate ){
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
        
        
        setBounds(400,100,600,600);
        setLayout(null);
        setOpaque(false);
        Color darkAzure = new Color(0, 95, 115);
        
        panelBill = new JPanel ();
        panelBill.setLayout(null);
        panelBill.setBounds(200,20,200,40);
        add(panelBill);
        panelBill.setBorder(BorderFactory.createEtchedBorder());
        
        panelMenu = new JPanel ();
        panelMenu.setLayout(null);
        panelMenu.setBounds(75,100,450,400);
        add(panelMenu);
        panelMenu.setBorder(BorderFactory.createEtchedBorder());
        panelMenu.setBackground(Color.WHITE);
        
        lblMode = new JLabel ("MODE OF TRANSACTION");
        lblMode.setBounds(30,0,200,40);
        panelBill.add(lblMode);
        panelBill.setBackground(Color.WHITE);
        
        btnCash = new JButton("CASH");
        btnCash.setBounds(125, 120, 200, 50);
        panelMenu.add(btnCash);

        btnCashless = new JButton("CASHLESS");
        btnCashless.setBounds(125, 220, 200, 50);
        panelMenu.add(btnCashless);

        btnBack = new JButton ("Back");
        btnBack.setBounds(50,350,100,25);
        panelMenu.add(btnBack);
        
        btnBack.addActionListener(this);
        
        btnCash.addActionListener(this);
        btnCashless.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnCash) {
            JOptionPane.showMessageDialog(null,
                    "Please proceed to the cashier for cash payment.");
            JFrame current = (JFrame) SwingUtilities.getWindowAncestor(this);
            current.dispose();

            carrentalsystemmain.Main.openHomepage();
        } 
        else if (e.getSource() == btnCashless) {
//            CashlessPayment cp = new CashlessPayment();
//            cp.setVisible(true);
            JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            Container background = mainFrame.getContentPane();
            background.remove(this);
            System.out.println("RATE = " + rate);
            CashlessPayment ap = new CashlessPayment(
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
        }else if (e.getSource()==btnBack){
            JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            Container background = mainFrame.getContentPane();
            background.remove(this);
            System.out.println("RATE = " + rate);
            PaymentReceipt ap = new PaymentReceipt(
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
            ap.setBounds(800, 250,1366, 768);
            background.add(ap);
            background.revalidate();
            background.repaint();
        }
    }
    
}
