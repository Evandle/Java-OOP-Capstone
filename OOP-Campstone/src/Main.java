import capstone.classes.DbHandler;
import capstone.MainMenu.MainMenu;
import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;


public class Main {
    public static void main(String[] args) {
        try (Connection conn = DbHandler.connect()){
            // execute SQL
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // main frame
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Grocery Shopping System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new MainMenu()); // Start with MainMenu
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setVisible(true);
        });
    }

}