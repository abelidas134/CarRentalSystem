package customers;

import carrentalsystemmain.AdminOption;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import carrentalsystemmain.*;
import reservation.*;

public class Customers extends JPanel implements Searchable {
    static  ArrayList<CustomerMethods> customerList = new ArrayList<>();

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
            if(txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "ID cannot be empty.","Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id;
            try {
                id = Integer.parseInt(txtId.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "ID must be a number.","Error", JOptionPane.WARNING_MESSAGE);
                return;
            } 
           StringBuilder missing = new StringBuilder();

            if(txtName.getText().isEmpty()) missing.append("Fullname, ");
            if(txtPhone.getText().isEmpty()) missing.append("Phone Number, ");
            if(txtLicense.getText().isEmpty()) missing.append("Driver's License, ");
            if(txtAddress.getText().isEmpty()) missing.append("Address, ");

            if(missing.length() > 0){
                JOptionPane.showMessageDialog(null,"Missing fields: " + missing.substring(0, missing.length() - 2));
                   return;
}        
            for(CustomerMethods c : customerList){
                if(c.getId() == id){
                    JOptionPane.showMessageDialog(null, "Customer with this ID already exists.","Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
            CustomerMethods c = new CustomerMethods(
                id,
                txtName.getText(),
                txtPhone.getText(),
                txtLicense.getText(),
                txtAddress.getText()            
            );
            customerList.add(c);
            JOptionPane.showMessageDialog(null, "Customer added.","Successful", JOptionPane.PLAIN_MESSAGE);
            clearFields();
        });

        btnUpdate.addActionListener(e -> {
            if(txtId.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "ID is required to update.","Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id;
            try {
                id = Integer.parseInt(txtId.getText());
            } catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(null, "ID must be a number.","Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            CustomerMethods found = null;
            for(CustomerMethods c : customerList){
                if(c.getId() == id){
                    found = c;
                    break;
                }
            }
            if(found == null){
                JOptionPane.showMessageDialog(null, "Customer not found.","Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            found.setName(txtName.getText());
            found.setPhone(txtPhone.getText());
            found.setLicense(txtLicense.getText());
            found.setAddress(txtAddress.getText());
            JOptionPane.showMessageDialog(null, "Customer updated.","Updates", JOptionPane.PLAIN_MESSAGE);
            clearFields();
        });

        btnDelete.addActionListener(e -> {
            if(txtId.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "ID is required to delete.","Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id;
            try {
                id = Integer.parseInt(txtId.getText());
            } catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(null, "ID must be a number.","Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            boolean removed = customerList.removeIf(c -> c.getId() == id);
            if(removed){
                JOptionPane.showMessageDialog(null, "Customer deleted.","Error", JOptionPane.PLAIN_MESSAGE);
                clearFields();
            } else {
                JOptionPane.showMessageDialog(null, "Customer not found.","Error", JOptionPane.PLAIN_MESSAGE);
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
            if(keyword.isEmpty()){
                JOptionPane.showMessageDialog(null, "Enter ID or Fullname to search before viewing.","Error", JOptionPane.OK_OPTION);
                return;
            }
            ArrayList<CustomerMethods> results = searchResults(keyword);
            if(results.isEmpty()){
                JOptionPane.showMessageDialog(null, "No customer found.","Error", JOptionPane.OK_OPTION);
            } else if(results.size() == 1){
                CustomerMethods c = results.get(0);
                showCustomerDetails(c);
            } else {
                String[] options = new String[results.size()];
                for(int i=0; i<results.size(); i++){
                    options[i] = results.get(i).getId() + " - " + results.get(i).getName();
                }
                String choice = (String) JOptionPane.showInputDialog(this, 
                    "Multiple customers found. Select one:", "Select Customer",
                    JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                if(choice != null){
                    int chosenId = Integer.parseInt(choice.split(" - ")[0]);
                    CustomerMethods chosen = null;
                    for(CustomerMethods c : results){
                        if(c.getId() == chosenId){
                            chosen = c;
                            break;
                        }
                    }
                    if(chosen != null){
                        showCustomerDetails(chosen);
                    }
                }
            }
        });
    }

    private void clearFields(){
        txtId.setText("");
        txtName.setText("");
        txtPhone.setText("");
        txtLicense.setText("");
        txtAddress.setText("");
        txtSearch.setText("");
    }

    private void showCustomerDetails(CustomerMethods c){
        JOptionPane.showMessageDialog(null,
            "ID: " + c.getId() +
            "\nName: " + c.getName() +
            "\nPhone: " + c.getPhone() +
            "\nLicense: " + c.getLicense() +
            "\nAddress: " + c.getAddress(),
            "Customer Details", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void search(String searchTerm){
    ArrayList<CustomerMethods> matchedCustomers = searchResults(searchTerm);
    
    if(matchedCustomers.isEmpty()){
        JOptionPane.showMessageDialog(null, "No customer found.");
    } else {
        StringBuilder message = new StringBuilder("Found " + matchedCustomers.size() + " customer(s):\n");
        
        for(CustomerMethods customer : matchedCustomers){
            message.append(customer.getId())
                   .append(" - ")
                   .append(customer.getName())
                   .append("\n");
        }
        
        JOptionPane.showMessageDialog(null, message.toString());
    }
}

    public static  ArrayList<CustomerMethods> searchResults(String keyword){
        ArrayList<CustomerMethods> results = new ArrayList<>();
        for(CustomerMethods c : customerList){
            if(String.valueOf(c.getId()).equals(keyword) || c.getName().toLowerCase().contains(keyword.toLowerCase())){
                results.add(c);
            }
        }
        return results;
    }
    public static void addCustomer(
            int id,
            String name,
            String phone,
            String license,
            String address) {

        CustomerMethods customer = new CustomerMethods(
                id,
                name,
                phone,
                license,
                address
        );

        customerList.add(customer);
    }
}