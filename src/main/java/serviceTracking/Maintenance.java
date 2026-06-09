package serviceTracking;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

//Maintenance
public class Maintenance extends JPanel implements ActionListener{
    JLabel lblcarModelPlatenum, lbl2ndHeadStatus;
    public static JLabel maintenanceStatus;
    JButton btnback, btnUpdateStats;
    
    public Maintenance(){
        setBounds(0, 0, 900, 500);
        setLayout(null);
        setOpaque(false);
        Color darkAzure = new Color(0, 95, 115);
        
        lbl2ndHeadStatus = new JLabel("<< Status >>");
        lbl2ndHeadStatus.setBounds(30, 40, 300, 40);
        add(lbl2ndHeadStatus);
        
        maintenanceStatus = new JLabel("No maintenance updates available.");
        maintenanceStatus.setBounds(30, 100, 700, 40);

        add(maintenanceStatus);
        
        btnUpdateStats = new JButton("UPDATE STATUS");
        btnUpdateStats.setBounds(30, 170, 180, 40);
        add(btnUpdateStats);
        
        

        btnUpdateStats.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent j) {
         
          if(j.getSource() == btnUpdateStats)
            {
                MaintenanceUpdate updStatNissan4567 = new MaintenanceUpdate();
                updStatNissan4567.setVisible(true);
            }
    }
    
}
