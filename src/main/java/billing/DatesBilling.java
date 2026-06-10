package billing;

import carrentalsystemmain.FoundationFrame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import prevention.*;
import reservation.Reservation;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import reservation.ReservationDetailsFrame;


import java.sql.Connection;
import java.sql.DriverManager;


public class DatesBilling extends JPanel implements ActionListener {
    private JPanel panelBill,panelPick, panelDrop;
    private JLabel lblSec, lblPick, lblDrop,lblYear, lblMonth, lblDay;
    private JTextField txtYearPick, txtYearDrop;
    private JComboBox comboDayPick, comboDayDrop, comboMonthPick, comboMonthDrop;
    private String [] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September","October", "November", "December"};
    private JButton btnBack, btnContinue;
    private Integer[] feb28 = new Integer[28];
    private Integer[] feb29 = new Integer[29];
    private Integer[] month30 = new Integer[30];
    private Integer[] month31 = new Integer[31];
    private String monthPickStmnt, monthDropStmnt, res,reservationNumber, rate, name, plateresNum, pickDeets,  dropDeets, resNum,plate, customerName,details;
    private Integer daysTotal;
    
    {
        for (int i=0;i<feb28.length;i++) feb28[i]=i+1;
        for (int i=0;i<feb29.length;i++) feb29[i]=i+1;
        for (int i=0;i<month30.length;i++) month30[i]=i+1;
        for (int i=0;i<month31.length;i++) month31[i]=i+1;
    }
    
    private Reservation reservationPanel;

    public DatesBilling(String reservationNumber, String rate, String name, String plate, String customerName, Reservation reservationPanel) {
        this.reservationNumber = reservationNumber;
        this.rate =rate;
        this.name = name;
        this.plate = plate;
        this.customerName = customerName;
        this.reservationPanel = reservationPanel;
        
        setBounds(0,0,600,600);
        setLayout(null);
        setOpaque(false);
        Color darkAzure = new Color(0, 95, 115);
        
        panelBill = new JPanel ();
        panelBill.setLayout(null);
        panelBill.setBounds(220,10,125,40);
        add(panelBill);
        panelBill.setBorder(BorderFactory.createEtchedBorder());
        
        lblSec = new JLabel ("BILLING SYSTEM");
        lblSec.setBounds(10,0,125,40);
        panelBill.add(lblSec);
        panelBill.setBackground(Color.WHITE);
        
        lblSec = new JLabel ("Fill in the pick-up and drop-off details below.");
        lblSec.setBounds(165,30,500,75);
        add(lblSec);
        
        panelPick = new JPanel ();
        panelPick.setLayout(null);
        panelPick.setBounds(75,100,435,175);
        add(panelPick);
        panelPick.setBorder(BorderFactory.createEtchedBorder());
        panelPick.setBackground(Color.WHITE);

        lblPick = new JLabel ("PICK UP");
        lblPick.setBounds(50,25,100,100);
        panelPick.add(lblPick);
        
        lblYear = new JLabel ("Year:");
        lblYear.setBounds(200,0,100,75);
        panelPick.add(lblYear);
        
        txtYearPick = new JTextField(String.valueOf(java.time.Year.now().getValue()));
        txtYearPick.setBounds(260,23,100,25);
        panelPick.add(txtYearPick);
        
        lblMonth = new JLabel ("Month:");
        lblMonth.setBounds(200,25,100,100);
        panelPick.add(lblMonth);
        
        comboMonthPick = new JComboBox (months);
        comboMonthPick.setBounds(260,65,100,25);
        panelPick.add(comboMonthPick);
        
        lblDay = new JLabel ("Day:");
        lblDay.setBounds(200,70,100,100);
        panelPick.add(lblDay);
        
        comboDayPick = new JComboBox (month31);
        comboDayPick.setBounds(260,110,100,25);
        panelPick.add(comboDayPick);
        
        panelDrop = new JPanel ();
        panelDrop.setLayout(null);
        panelDrop.setBounds(75,300,435,175);
        add(panelDrop);
        panelDrop.setBorder(BorderFactory.createEtchedBorder());
        panelDrop.setBackground(Color.WHITE);

        lblDrop = new JLabel ("DROP OFF");
        lblDrop.setBounds(50,25,100,100);
        panelDrop.add(lblDrop);
        
        lblYear = new JLabel ("Year:");
        lblYear.setBounds(200,0,100,75);
        panelDrop.add(lblYear);
        
        txtYearDrop = new JTextField(String.valueOf(java.time.Year.now().getValue()));
        txtYearDrop.setBounds(260,23,100,25);
        panelDrop.add(txtYearDrop);
        
        lblMonth = new JLabel ("Month:");
        lblMonth.setBounds(200,25,100,100);
        panelDrop.add(lblMonth);
        
        comboMonthDrop = new JComboBox (months);
        comboMonthDrop.setBounds(260,65,100,25);
        panelDrop.add(comboMonthDrop);
        
        lblDay = new JLabel ("Day:");
        lblDay.setBounds(200,70,100,100);
        panelDrop.add(lblDay);
        
        comboDayDrop = new JComboBox (month31);
        comboDayDrop.setBounds(260,110,100,25);
        panelDrop.add(comboDayDrop);
        
//        btnBack = new JButton ("Back");
//        btnBack.setBounds(10,520,75,30);
//        add(btnBack);
        
        btnContinue = new JButton ("Continue");
        btnContinue.setBounds(475,520,100,30);
        add(btnContinue);
        
        btnContinue.addActionListener(this);
        comboMonthPick.addActionListener(this);
        comboMonthDrop.addActionListener(this);
        txtYearPick.addActionListener(this);        
        txtYearDrop.addActionListener(this); 
               
    }
@Override
public void actionPerformed(ActionEvent e) {
   
    if (e.getSource() == btnContinue) {
        try {
         
            Integer dayPick = (Integer) comboDayPick.getSelectedItem();
            Integer dayDrop = (Integer) comboDayDrop.getSelectedItem();
            if (dayPick == null || dayDrop == null) {
                JOptionPane.showMessageDialog(this, "Please select both pick-up and drop-off days!");
                return;
            }

            String y1 = txtYearPick.getText().trim();
            String y2 = txtYearDrop.getText().trim();
            if (!y1.matches("\\d+") || !y2.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Enter valid years!");
                return;
            }

            int yearPick = Integer.parseInt(y1);
            int yearDrop = Integer.parseInt(y2);
            int monthPick = comboMonthPick.getSelectedIndex() + 1;
            int monthDrop = comboMonthDrop.getSelectedIndex() + 1;

            LocalDate d1 = LocalDate.of(yearPick, monthPick, dayPick);
            LocalDate d2 = LocalDate.of(yearDrop, monthDrop, dayDrop);

            if (d2.isBefore(d1)) {
                JOptionPane.showMessageDialog(this, "Drop-off must be after pick-up!");
                return;
            }

            PaymentReceipt receiptPanel = new PaymentReceipt(
            reservationNumber,
            d1.toString(),   
            d2.toString(),   
            (int) ChronoUnit.DAYS.between(d1, d2), 
            name,
            plate,
            rate,
            reservationNumber,
            customerName,
            d1,
            d2
        );

        receiptPanel.setBounds(800, 250, 600, 600);

        JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        Container contentPane = mainFrame.getContentPane();
        contentPane.remove(this);
        contentPane.add(receiptPanel);
        contentPane.revalidate();
        contentPane.repaint();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Unexpected error: " + ex.getMessage());
        }
    }
}

//METHODSS
public void monthPick() {
        String m = ((String) comboMonthPick.getSelectedItem()).toLowerCase();
        if (m.matches("january|march|may|july|august|october|december")) {
            comboDayPick.setModel(new DefaultComboBoxModel<>(month31));
        } else if (m.equals("february")) {
            String y = txtYearPick.getText().trim();
            if (y.matches("\\d+")) {
                int year = Integer.parseInt(y);
                boolean leap = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
                comboDayPick.setModel(new DefaultComboBoxModel<>(leap ? feb29 : feb28));
            } else {
                comboDayPick.setModel(new DefaultComboBoxModel<>(feb28));
            }
        } else {
            comboDayPick.setModel(new DefaultComboBoxModel<>(month30));
        }
    }
    
    public void monthDrop() {
        String m = ((String) comboMonthDrop.getSelectedItem()).toLowerCase();
        if (m.matches("january|march|may|july|august|october|december")) {
            comboDayDrop.setModel(new DefaultComboBoxModel<>(month31));
        } else if (m.equals("february")) {
            String y = txtYearDrop.getText().trim();
            if (y.matches("\\d+")) {
                int year = Integer.parseInt(y);
                boolean leap = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
                comboDayDrop.setModel(new DefaultComboBoxModel<>(leap ? feb29 : feb28));
            } else {
                comboDayDrop.setModel(new DefaultComboBoxModel<>(feb28));
            }
        } else {
            comboDayDrop.setModel(new DefaultComboBoxModel<>(month30));
        }
    }
        }