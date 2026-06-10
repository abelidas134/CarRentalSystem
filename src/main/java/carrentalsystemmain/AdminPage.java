
package carrentalsystemmain;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import database.DBConnection;
import java.sql.SQLException;

/**
 *
 * @author Mickey
 */
public class AdminPage extends JPanel implements ActionListener {
    private JLabel lblMessage, lblUserName, lblPassword,lblError;
    private JTextField txtUserName;
    private JButton btnLogin, btnBack;
    private JPasswordField txtPassword;
    
    public AdminPage(){
        setBounds(0,0,600,600);
        setLayout(null);
        setOpaque(false);
        
        lblMessage = new JLabel ("ADMIN ACCOUNT");
        lblMessage.setBounds(250, 125, 500,100);
        lblMessage.setFont(new Font("Poppins",Font.BOLD,25));
        add(lblMessage);
        
        lblError = new JLabel ();
        lblError.setBounds(250, 350, 500,100);
        lblError.setFont(new Font("Poppins",Font.BOLD,25));
        add(lblError);
        
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
        
        Color darkAzure = new Color(0, 95, 115);
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
        
        btnBack.addActionListener(this);
        btnLogin.addActionListener(this);
        
        
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        
        if (e.getSource()==btnLogin){
            String userName = txtUserName.getText().trim();
            String pass = txtPassword.getText().trim();
            try (Connection conn = DBConnection.getConnection()) {
                
            String sql = "SELECT * FROM admin WHERE admin_user = ? AND Admin_pass = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userName);
            ps.setString(2, pass);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) 
            {
                JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                Container c = mainFrame.getContentPane();
                c.remove(this);

                AdminOption ap = new AdminOption();
                ap.setBounds(1100, 250, 1366, 768);
                c.add(ap);

                mainFrame.revalidate();
                mainFrame.repaint();
            } 
        else 
            {
                lblError.setText("Access Denied: Invalid credentials.");
                lblError.setFont(new Font("Poppins", Font.CENTER_BASELINE, 13));
                lblError.setForeground(Color.RED);
            }
    } catch (SQLException sqlException) {
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
