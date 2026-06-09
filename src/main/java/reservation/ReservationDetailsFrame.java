/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reservation;

import billing.ResNumLog;
import carrentalsystemmain.FoundationFrame;
import java.awt.Container;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 *
 * @author Mickey
 */
public class ReservationDetailsFrame extends JPanel {

    JTextArea detailsArea;
    String rate, name, plate, customerName;
        
    public ReservationDetailsFrame(
            String details,
            String reservationNumber,
            String rate,
            String name,
            String plate,
            String customerName) {
            this.rate = rate;
            this.name = name;
            this.plate = plate;
            this.customerName = customerName;
            
            
        setBounds(1000,100,600, 600);

        setLayout(null);

        JLabel titleLabel = new JLabel("RESERVATION DETAILS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBounds(160, 30, 300, 30);

        detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
        detailsArea.setText(details);

        JScrollPane scrollPane = new JScrollPane(detailsArea);
        scrollPane.setBounds(70, 100, 450, 320);

        JButton backButton = new JButton("Back");
        backButton.setBounds(150, 460, 120, 40);

        JButton closeButton = new JButton("Check Out");
        closeButton.setBounds(300, 460, 120, 40);

        closeButton.addActionListener(e -> {
            JFrame mainFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            Container background = mainFrame.getContentPane();
            background.remove(this);
            ResNumLog ap
                    = new ResNumLog(
                            reservationNumber,
                            rate,
                            name,
                            plate,
                            customerName,
                            details
                    );
            ap.setBounds(850, 200, 1366, 768);
            background.add(ap);
            background.revalidate();
            background.repaint();
        });
        
        backButton.addActionListener(e -> {
            JFrame current = (JFrame) SwingUtilities.getWindowAncestor(this);
            current.dispose();

            FoundationFrame ff = new FoundationFrame(new Reservation(rate, name, plate));
        
        });

        add(titleLabel);
        add(scrollPane);
        add(closeButton);
        add(backButton);

        setVisible(true);
    }
}
