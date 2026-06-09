package carrentalsystemmain;

import java.awt.event.*;
import javax.swing.*;
import customers.*;
import java.awt.*;
import serviceTracking.*;
import Services.*;
import javax.swing.table.*;
import vehicle.*;


public class AdminOption extends JPanel implements ActionListener{
    private JButton btnCustomer, btnBack, btnMaintenance;
    
    public AdminOption() {
        setBounds(0,0,600, 600);
        setLayout(null);
        setOpaque(false);
        Color darkAzure = new Color(0, 95, 115);
        
        btnCustomer = new JButton("Customer Records");
        btnCustomer.setBounds(300, 210, 230, 50);
        btnCustomer.setFont(new Font("Poppins",Font.BOLD,15));
        btnCustomer.setForeground(Color.WHITE);
        btnCustomer.setBackground(darkAzure);
        btnCustomer.setOpaque(true);
        btnCustomer.setFocusPainted(false);
        add(btnCustomer);
        
        btnMaintenance = new JButton("Maintenance & Tracking");
        btnMaintenance.setBounds(300, 285, 230, 50);
        btnMaintenance.setFont(new Font("Poppins",Font.BOLD,15));
        btnMaintenance.setForeground(Color.WHITE);
        btnMaintenance.setBackground(darkAzure);
        btnMaintenance.setOpaque(true);
        btnMaintenance.setFocusPainted(false);
        add(btnMaintenance);
        
        btnBack = new JButton ("Back");
        btnBack.setBounds(225,400,100,25);
        btnBack.setFont(new Font("Poppins",Font.BOLD,15));
        btnBack.setForeground(Color.WHITE);
        btnBack.setBackground(darkAzure);
        btnBack.setOpaque(true);
        btnBack.setFocusPainted(false);
        add(btnBack);
        
        btnCustomer.addActionListener(this);
        btnMaintenance.addActionListener(this);
        btnBack.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==btnCustomer){
            JFrame current = (JFrame) SwingUtilities.getWindowAncestor(this);
            current.dispose();

            FoundationFrame ff = new FoundationFrame(new Customers());
        }else if (e.getSource()==btnMaintenance){
            JFrame current = (JFrame) SwingUtilities.getWindowAncestor(this);
            current.dispose();
            
            FoundationFrame ff = new FoundationFrame(new ServiceTracking(Vehicle.model));
        }else if (e.getSource()==btnBack){
            JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            Container background = mainFrame.getContentPane();
            background.remove(this);
            AdminPage ap = new AdminPage();
            ap.setBounds(1100, 250, 1366, 768);
            background.add(ap);
            background.revalidate();
            background.repaint();
        }
    }
}