package vehicle;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import Services.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.table.DefaultTableCellRenderer;
import carrentalsystemmain.*;
import reservation.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import database.DBConnection;



public class Vehicle extends JPanel{
     public static DefaultTableModel model = new DefaultTableModel(
        new String[]{"ID", "Plate Number", "Name", "Status", "Rate"}, 0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
     
     
    public Vehicle(){
        setBounds(600,200,1000, 600);
        setLayout(null);
        setOpaque(false);
        Color darkAzure = new Color(0, 95, 115);
        
        JLabel lblce = new JLabel("What do you want to book today?");
        lblce.setFont(new Font("Arial", Font.BOLD, 25));
        lblce.setBounds(300, 80, 450, 30);
        
        JTable table = new JTable(model);
        table.setRowHeight(35);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
        table.setFont(new Font("Arial", Font.PLAIN, 14));

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
          table.getColumnModel().getColumn(i).setCellRenderer(center);
       }

        JScrollPane spane = new JScrollPane(table);
        spane.setBounds(200, 150, 600, 200);
       
        JButton btndetails = new JButton("View Details");
        btndetails.setBounds(425, 400, 150, 40);
        JButton btnbook = new JButton("Book Vehicle");
        btnbook.setBounds(650, 400, 150, 40);
        JButton btncancel = new JButton("Cancel");
        btncancel.setBounds(200,400,150,40);
        
        
        btndetails.addActionListener(e -> {
    int row = table.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Please select a vehicle first.", "No Selection", JOptionPane.WARNING_MESSAGE);
        return;
    }

    String id = (String)model.getValueAt(row, 0);
    String plate = (String)model.getValueAt(row, 1);
    String name = (String)model.getValueAt(row, 2);
    String status = (String)model.getValueAt(row, 3);
    String rate = (String)model.getValueAt(row, 4);
    new CarDetails(id, name, status, rate, plate);
});
        
        btncancel.addActionListener(e -> {

            JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            currentFrame.dispose();

            JFrame mainFrame = new JFrame();
            mainFrame.setSize(1366, 768);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

            JLabel background = new JLabel(new ImageIcon(Main.class.getResource("/img/firstBG.png")));
            background.setLayout(null);
            CustomerPage ap = new CustomerPage();
            ap.setBounds(1100, 250, 1366, 768);
            background.add(ap);
            mainFrame.setContentPane(background);
            mainFrame.setVisible(true);
        });

        btnbook.addActionListener(e -> {
    int row = table.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(this,"Please select a vehicle first.", "No Selection", JOptionPane.WARNING_MESSAGE);
        return;
    }

    String id = (String)model.getValueAt(row, 0);
    String plate = (String)model.getValueAt(row, 1);
    String name = (String)model.getValueAt(row, 2);
    String rate = (String)model.getValueAt(row, 4);

    try (Connection conn = DBConnection.getConnection()) {
        PreparedStatement ps = conn.prepareStatement(
            "SELECT s.car_status FROM car c JOIN car_status s ON c.car_status_id = s.car_status_id WHERE c.car_id = ?");
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            String currentStatus = rs.getString("car_status");
            if (!currentStatus.equalsIgnoreCase("AVAILABLE")) {
                JOptionPane.showMessageDialog(this,
                    "This vehicle cannot be booked because it is currently " + currentStatus + ".",
                    "Vehicle Not Available", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error checking car status.", "DB Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    JFrame current = (JFrame) SwingUtilities.getWindowAncestor(this);
    current.dispose();

    FoundationFrame ff = new FoundationFrame(new Reservation(rate, name, plate, id));
});
       
               
        add(lblce);
        add(spane);
        add(btndetails);
        add(btnbook);
        add(btncancel);
        
        loadCarsFromDB();
        
        setVisible(true);
        
}
    
    private void loadCarsFromDB() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT c.car_id, c.plate_no, c.car_name, s.car_status, c.rate " +
                         "FROM car c JOIN car_status s ON c.car_status_id = s.car_status_id";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            model.setRowCount(0); 
            
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("car_id"),
                    rs.getString("plate_no"),
                    rs.getString("car_name"),
                    rs.getString("car_status"),
                    "P" + rs.getInt("rate")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading cars from database.", "DB Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
