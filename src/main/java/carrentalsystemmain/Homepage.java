
package carrentalsystemmain;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Mickey
 */
class Homepage extends JPanel implements ActionListener {
    private JLabel lblHome;
    private JButton btnAdmin;
    private JButton btnCustomer;
    
    Homepage(){
        setBounds(975, 220, 1366, 768);
        setLayout(null);
        setOpaque(false);
        
        lblHome = new JLabel ("Log in your Account");
        lblHome.setBounds(350,150,250,100);
        lblHome.setFont(new Font("Poppins",Font.BOLD,25));
        add(lblHome);
        
        lblHome = new JLabel ("Log in to manage your car rentals and get on the road!");
        lblHome.setFont(new Font("Poppins",Font.PLAIN,14));
        lblHome.setBounds(300,175,500,100);
        add(lblHome);
        
        Color darkAzure = new Color(0, 95, 115);
        btnAdmin = new JButton ("ADMIN");
        btnAdmin.setFont(new Font("Poppins",Font.BOLD,20));
        btnAdmin.setForeground(Color.WHITE);
        btnAdmin.setBackground(darkAzure);
        btnAdmin.setOpaque(true);
        btnAdmin.setFocusPainted(false);
        btnAdmin.setBounds(375,275,175,50);
        add(btnAdmin);
        
        btnCustomer = new JButton ("CUSTOMER");
        btnCustomer.setFont(new Font("Poppins",Font.BOLD,20));
        btnCustomer.setForeground(Color.WHITE);
        btnCustomer.setBackground(darkAzure);
        btnCustomer.setOpaque(true);
        btnCustomer.setFocusPainted(false);
        btnCustomer.setBounds(375,360,175,50);
        add(btnCustomer);
        
        btnAdmin.addActionListener(this);
        btnCustomer.addActionListener(this);
        
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==btnAdmin){
            JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            Container c = mainFrame.getContentPane();
            c.remove(this);
            
            AdminPage ap = new AdminPage();
            ap.setBounds(1100, 250, 1366, 768); 
            c.add(ap);
            
            mainFrame.revalidate();
            mainFrame.repaint();
            
        } else if (e.getSource()==btnCustomer){
            JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            Container c = mainFrame.getContentPane();
            c.remove(this);

            CustomerPage cp = new CustomerPage();
            cp.setBounds(1100, 250, 1366, 768);
            c.add(cp);

            mainFrame.revalidate();
            mainFrame.repaint();
        }
    }
    
    
}