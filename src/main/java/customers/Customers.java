package customers;

import carrentalsystemmain.AdminOption;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import carrentalsystemmain.*;
import reservation.*;
import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Customers extends JPanel implements Searchable {
    
    private JTextField txtId, txtName, txtPhone, txtLicense, txtAddress, txtSearch;
    

    public Customers() {
        setBounds(800, 250, 1000, 1000);
        setLayout(null);
        setOpaque(false);
        Color darkAzure = new Color(0, 95, 115);

        JLabel lblId = new JLabel("Reservation ID:");
        lblId.setBounds(50, 75, 200, 25);
        lblId.setFont(new Font("Poppins",Font.ROMAN_BASELINE,20));
        add(lblId);
        txtId = new JTextField();
        txtId.setBounds(250, 70, 300,40);
        add(txtId);

        JLabel lblName = new JLabel("Full Name:");
        lblName.setBounds(50, 125, 200, 25);
        lblName.setFont(new Font("Poppins",Font.ROMAN_BASELINE,20));
        add(lblName);
        txtName = new JTextField();
        txtName.setBounds(250, 125,300,40);
        add(txtName);

        JLabel lblPhone = new JLabel("Phone Number:");
        lblPhone.setBounds(50, 180, 200, 25);
        lblPhone.setFont(new Font("Poppins",Font.ROMAN_BASELINE,20));
        add(lblPhone);
        txtPhone = new JTextField();
        txtPhone.setBounds(250, 180, 300,40);
        add(txtPhone);

        JLabel lblLicense = new JLabel("Drivers License:");
        lblLicense.setBounds(50, 235, 200, 25);
        lblLicense.setFont(new Font("Poppins",Font.ROMAN_BASELINE,20));
        add(lblLicense);
        txtLicense = new JTextField();
        txtLicense.setBounds(250, 235, 300,40);
        add(txtLicense);

        JLabel lblAddress = new JLabel("Email Address:");
        lblAddress.setBounds(50, 290, 200, 25);
        lblAddress.setFont(new Font("Poppins",Font.ROMAN_BASELINE,20));
        add(lblAddress);
        txtAddress = new JTextField();
        txtAddress.setBounds(250, 290,300,40);
        add(txtAddress);

        JButton btnAdd = new JButton("Add");
        btnAdd.setBounds(600, 75, 100, 25);
        btnAdd.setFont(new Font("Poppins",Font.BOLD,15));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setBackground(darkAzure);
        btnAdd.setOpaque(true);
        btnAdd.setFocusPainted(false);
        add(btnAdd);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.setBounds(600, 128, 100, 25);
        btnUpdate.setFont(new Font("Poppins",Font.BOLD,15));
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setBackground(darkAzure);
        btnUpdate.setOpaque(true);
        btnUpdate.setFocusPainted(false);
        add(btnUpdate);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(600, 188, 100, 25);
        btnDelete.setFont(new Font("Poppins",Font.BOLD,15));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setBackground(darkAzure);
        btnDelete.setOpaque(true);
        btnDelete.setFocusPainted(false);
        add(btnDelete);

        JButton btnClear = new JButton("Clear");
        btnClear.setBounds(600, 238, 100, 25);
        btnClear.setFont(new Font("Poppins",Font.BOLD,15));
        btnClear.setForeground(Color.WHITE);
        btnClear.setBackground(darkAzure);
        btnClear.setOpaque(true);
        btnClear.setFocusPainted(false);
        add(btnClear);

        txtSearch = new JTextField();
        txtSearch.setBounds(50, 5, 300,40);
        add(txtSearch);

        JButton btnSearch = new JButton("Search");
        btnSearch.setBounds(400, 7, 100, 25);
        btnSearch.setFont(new Font("Poppins",Font.BOLD,15));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setBackground(darkAzure);
        btnSearch.setOpaque(true);
        btnSearch.setFocusPainted(false);
        add(btnSearch);

        JButton btnView = new JButton("View");
        btnView.setBounds(550, 7, 100, 25);
        btnView.setFont(new Font("Poppins",Font.BOLD,15));
        btnView.setForeground(Color.WHITE);
        btnView.setBackground(darkAzure);
        btnView.setOpaque(true);
        btnView.setFocusPainted(false);
        add(btnView);

        JButton btnBack = new JButton("Back");
        btnBack.setBounds(5, 475, 180, 40);
        btnBack.setFont(new Font("Poppins",Font.BOLD,15));
        btnBack.setForeground(Color.WHITE);
        btnBack.setBackground(darkAzure);
        btnBack.setOpaque(true);
        btnBack.setFocusPainted(false);
        add(btnBack);
        
        UIManager.put("OptionPane.background", Color.WHITE);
        UIManager.put("Panel.background", Color.WHITE);
        UIManager.put("OptionPane.messageFont",new Font("Poppins", Font.BOLD, 14));
        
        btnAdd.addActionListener(e -> {
        try {
             Connection conn = DBConnection.getConnection();

             String sql = "INSERT INTO customer_book(name, phone, drivers_license, address) "
                         + "VALUES (?, ?, ?, ?)";

         PreparedStatement pst = conn.prepareStatement(sql);

         pst.setString(1, txtName.getText());
         pst.setString(2, txtPhone.getText());
         pst.setString(3, txtLicense.getText());
         pst.setString(4, txtAddress.getText()); 

         pst.executeUpdate();
         conn.close();
        JOptionPane.showMessageDialog(null, "Customer added!");
        clearFields();
    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
     }
  });
        
        btnUpdate.addActionListener(e -> {
        if (txtId.getText().trim().isEmpty()) {
             JOptionPane.showMessageDialog(null, "Customer ID is required.", "Error",JOptionPane.WARNING_MESSAGE);
        return;
    }
        try {
            Connection conn = DBConnection.getConnection();

            String sql =
                "UPDATE customer_book "
                + "SET name = ?, "
                + "phone = ?, "
                + "drivers_license = ?, "
                + "address = ? "
                + "WHERE cus_book_id = ?";

        PreparedStatement pst = conn.prepareStatement(sql);

        pst.setString(1, txtName.getText());
        pst.setString(2, txtPhone.getText());
        pst.setString(3, txtLicense.getText());
        pst.setString(4, txtAddress.getText());

        pst.setInt(5, Integer.parseInt(txtId.getText()));

        int rowsUpdated = pst.executeUpdate();
        conn.close();

        if (rowsUpdated > 0) {
            JOptionPane.showMessageDialog(null, "Customer updated successfully."
            );
            clearFields();
        } else {
            JOptionPane.showMessageDialog(null,"Customer not found.");
        }
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null,"Customer ID must be a number.");
    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null,"Error: " + ex.getMessage());
    }
});
        
      btnDelete.addActionListener(e -> {
            if(txtId.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "ID is required to delete.","Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
          try {
             int id = Integer.parseInt(txtId.getText().trim());
             Connection conn = DBConnection.getConnection();
            String sql =
                "DELETE FROM customer_book WHERE cus_book_id = ?";
            PreparedStatement pst =
                conn.prepareStatement(sql);
                    pst.setInt(1, id);

                int rowsDeleted = pst.executeUpdate();
                    conn.close();

             if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "Customer deleted successfully."
            );
            clearFields();
        } else {
            JOptionPane.showMessageDialog(null, "Customer not found."
            );
        }
    } catch (NumberFormatException ex) {
         JOptionPane.showMessageDialog(null, "Customer ID must be a number."
        );
    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage()
        );
    }
        });

        btnClear.addActionListener(e -> clearFields());

        btnBack.addActionListener(e -> {
            JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            currentFrame.dispose();
            
            JFrame mainFrame = new JFrame();
            mainFrame.setSize(1366, 768);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            
            JLabel background = new JLabel(new ImageIcon(Main.class.getResource("/img/firstBG.png")));
            background.setLayout(null);
            AdminOption ap = new AdminOption();
            ap.setBounds(1100, 250, 1366, 768);
            background.add(ap);
            mainFrame.setContentPane(background);
            mainFrame.setVisible(true);
        });

        btnSearch.addActionListener(e -> {
            String keyword = txtSearch.getText().trim();
            if(keyword.isEmpty()){
                JOptionPane.showMessageDialog(null, "Enter ID or Fullname to search.","Error", JOptionPane.OK_OPTION);
                return;
            }
            search(keyword);
        });

        btnView.addActionListener(e -> {
            String keyword = txtSearch.getText().trim();
        if (keyword.isEmpty()) {
        JOptionPane.showMessageDialog(null,"Enter Customer ID or Name.","Error", JOptionPane.WARNING_MESSAGE );
        return;
    } try {

        Connection conn = DBConnection.getConnection();
        String sql =
                "SELECT * FROM customer_book "
              + "WHERE cus_book_id = ? OR name LIKE ?";

        PreparedStatement pst =
                conn.prepareStatement(sql);

        pst.setString(1, keyword);
        pst.setString(2, "%" + keyword + "%");

        ResultSet rs = pst.executeQuery();
        StringBuilder details = new StringBuilder();

        while (rs.next()) {
            details.append("Customer ID: ")
                   .append(rs.getInt("cus_book_id"))
                   .append("\n")

                   .append("Name: ")
                   .append(rs.getString("name"))
                   .append("\n")

                   .append("Phone: ")
                   .append(rs.getString("phone"))
                   .append("\n")

                   .append("Email: ")
                   .append(rs.getString("email"))
                   .append("\n")

                   .append("License: ")
                   .append(rs.getString("drivers_license"))
                   .append("\n")

                   .append("Address: ")
                   .append(rs.getString("address"))
                   .append("\n\n");
        }
        conn.close();

        if (details.length() == 0) {
            JOptionPane.showMessageDialog(null,"No customer found.");
        } else {
            JOptionPane.showMessageDialog(null,details.toString(), "Customer Details", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
      }
    });
  }       
    @Override
    public void search(String keyword) {
        try {
        Connection conn = DBConnection.getConnection();

        String sql = "SELECT * FROM customer_book WHERE cus_book_id = ? OR name LIKE ?";

        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, keyword);
        pst.setString(2, "%" + keyword + "%");

        ResultSet rs = pst.executeQuery();

        StringBuilder result = new StringBuilder();

        while (rs.next()) {
            result.append(rs.getInt("cus_book_id"))
                  .append(" - ")
                  .append(rs.getString("name"))
                  .append("\n");
        }
        conn.close();

        if (result.length() == 0) {
            JOptionPane.showMessageDialog(null, "No customer found.");
        } else {
            JOptionPane.showMessageDialog(null, result.toString());
        }
    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
    }
}
    private void clearFields(){
        txtId.setText("");
        txtName.setText("");
        txtPhone.setText("");
        txtLicense.setText("");
        txtAddress.setText("");
        txtSearch.setText("");
    }
}