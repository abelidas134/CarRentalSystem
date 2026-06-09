package vehicle;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import Services.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.table.DefaultTableCellRenderer;
import carrentalsystemmain.*;
import reservation.*;

public class Vehicle extends JPanel{
    public static DefaultTableModel model = new DefaultTableModel(
            new String[][]{
                {"V001", "KO3AN0", "Toyota Vios", "AVAILABLE", "P200"},
                {"V002", "H3KAO9", "Honda Civic", "AVAILABLE", "P230"},
                {"V003", "GW3H5D", "Ford Ranger", "AVAILABLE", "P330"},
                {"V004", "1SN4US", "Nissan Altima", "AVAILABLE", "P280"},
                {"V005", "GSV4U8", "Hyundai Elantra", "AVAILABLE", "P350"}
            },
            new String[]{"ID", "Plate Number", "Name", "Status", "Rate"}
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

                new CarDetails(id,name,status, rate, plate);
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
            String status = (String)model.getValueAt(row, 3);
            String rate = (String)model.getValueAt(row, 4);
            
            JFrame current = (JFrame) SwingUtilities.getWindowAncestor(this);
            current.dispose();

            FoundationFrame ff = new FoundationFrame(new Reservation(rate, name, plate));
           });
       
               
        add(lblce);
        add(spane);
        add(btndetails);
        add(btnbook);
        add(btncancel);
        
        setVisible(true);
        
}
    
}
