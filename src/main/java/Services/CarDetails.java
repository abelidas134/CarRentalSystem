package Services;

import vehicle.*;
import java.awt.*;
import javax.swing.*;

public class CarDetails extends JFrame {
    public CarDetails (String id ,String name, String status, String rate, String plate){
        
        setTitle("Details");
        setSize(600,400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        
        JLabel lbltitle = new JLabel("VEHICLE DETAILS");
        lbltitle.setFont(new Font("Arial", Font.BOLD, 20));
        lbltitle.setBounds(70, 20, 350, 35);

        JSeparator sep = new JSeparator();
        sep.setBounds(50, 60, 500, 2);
        
        Container pane = getContentPane();
                   
        int k = 100, v = 300, y = 80, h = 30, gap = 35;
        
        addRow("VEHICLE ID:", id , k, v, y, pane, h); y += gap;
        addRow("MODEL:", name, k, v, y, pane, h); y += gap;
        addRow("PLATE NUMBER:", plate, k, v, y, pane, h); y += gap;
        addRow("RATE:", rate, k, v, y, pane, h); y += gap;
        addRow("STATUS:", status, k, v, y, pane, h); y += gap;
        addRow("CONDITION:","Good", k, v, y, pane, h); y += gap;
//        addRow("LAST MAINTENANCE:", "N/A", k, v, y, pane, h); y += gap;
//        addRow("NEXT SERVICE DUE:", "N/A", k, v, y, pane, h); y += gap;
        
                
        JButton btnclose = new JButton("Close");
        btnclose.setBounds(250,300,100,40);
        
        btnclose.addActionListener(e -> dispose());
        
        add(lbltitle);
        add(sep);
        add(btnclose);
              
        setVisible(true);
    }
    public static void  addRow(String key, String value, int k, int v, int y, Container pane,int h) {
        JLabel lblkey = new JLabel(key);
        lblkey.setFont(new Font("Arial", Font.BOLD, 15));
        lblkey.setBounds(k, y, 180, h);

        JLabel lblval = new JLabel(value);
        lblval.setFont(new Font("Arial", Font.BOLD, 15));
        lblval.setBounds(v, y, 250, h);
        
        pane.add(lblkey);
        pane.add(lblval);
    
}
    }