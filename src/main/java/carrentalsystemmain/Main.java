

package carrentalsystemmain;
import java.awt.*;
import javax.swing.*;
import reservation.*;
import database.DBConnection;
import java.sql.Connection;

public class Main {

    public static void main(String[] args) {
        Connection con
                = DBConnection.getConnection();

        if (con != null) {

            System.out.println("CONNECTED");

        } else {

            System.out.println("FAILED");

        }

        openHomepage();
        
    }
    public static void openHomepage() {

        JFrame mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JLabel background = new JLabel(
                new ImageIcon(Main.class.getResource("/img/firstBG.png"))
        );

            background.setLayout(null);

        Homepage hp = new Homepage();
        hp.setBounds(975, 220, 1366, 768);
        hp.setOpaque(false);

        background.add(hp);

        mainFrame.setContentPane(background);
        mainFrame.setVisible(true);
    }
    
}