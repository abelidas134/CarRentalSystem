/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carrentalsystemmain;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import reservation.Reservation;
import vehicle.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import database.DBConnection;
import java.sql.SQLException;

/**
 *
 * @author Mickey
 */
public class CustomerPage extends JPanel implements ActionListener {
    private JLabel lblMessage, lblUserName, lblPassword;
    private JTextField txtUserName;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnBack;
    
    public CustomerPage(){
        setBounds(0,0,600,600);
        setLayout(null);
        setOpaque(false);
        Color darkAzure = new Color(0, 95, 115);
        
        lblMessage = new JLabel ("CUSTOMER ACCOUNT");
        lblMessage.setBounds(220, 125, 500,100);
        lblMessage.setFont(new Font("Poppins",Font.BOLD,25));
        add(lblMessage);
        
        lblMessage = new JLabel ("Check your receipt and login your account!");
        lblMessage.setBounds(220, 150, 500,100);
        lblMessage.setFont(new Font("Poppins",Font.PLAIN,14));
        add(lblMessage);
        
        lblUserName = new JLabel ("Username:");
        lblUserName.setBounds(190,190, 150,100);
        lblUserName.setFont(new Font("Poppins",Font.BOLD,15));
        add(lblUserName);
        
        txtUserName = new JTextField (" ");
        txtUserName.setBounds(310,225,200,40);
        add(txtUserName);
        
        lblPassword = new JLabel ("Password:");
        lblPassword.setBounds(190,250,100,100);
        lblPassword.setFont(new Font("Poppins",Font.BOLD,15));
        add(lblPassword);
        
        txtPassword = new JPasswordField ("");
        txtPassword.setBounds(310,285,200,40);
        add(txtPassword);
        
        btnLogin = new JButton ("Log In");
        btnLogin.setFont(new Font("Poppins",Font.BOLD,15));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBackground(darkAzure);
        btnLogin.setOpaque(true);
        btnLogin.setFocusPainted(false);
        btnLogin.setBounds(370,350,100,25);
        add(btnLogin);
        
        btnBack = new JButton ("Back");
        btnBack.setFont(new Font("Poppins",Font.BOLD,15));
        btnBack.setForeground(Color.WHITE);
        btnBack.setBackground(darkAzure);
        btnBack.setOpaque(true);
        btnBack.setFocusPainted(false);
        btnBack.setBounds(225,350,100,25);
        add(btnBack);
        
        btnLogin.addActionListener(this);
        btnBack.addActionListener(this);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==btnLogin){
            String userName = txtUserName.getText().trim();
            String pass = txtPassword.getText().trim();
            
        try (Connection conn = DBConnection.getConnection()) {
        String sql = "SELECT * FROM customer WHERE username = ? AND password = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, userName);
        ps.setString(2, pass);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) 
            {
            JFrame current = (JFrame) SwingUtilities.getWindowAncestor(this);
            current.dispose();

            FoundationFrame ff = new FoundationFrame(new Vehicle());
            } 
        else 
            {
            lblMessage.setText("Access Denied: Invalid inputs.");
            lblMessage.setFont(new Font("Poppins", Font.CENTER_BASELINE, 13));
            lblMessage.setForeground(Color.RED);
            }
        } 
        catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            
        }
        else if (e.getSource()==btnBack){
            JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            Container background = mainFrame.getContentPane();
            background.remove(this);
            Homepage hp = new Homepage();
            hp.setBounds(975, 220, 1366, 768);  
            background.add(hp);
            background.revalidate();
            background.repaint();
    }
        
    }
}
