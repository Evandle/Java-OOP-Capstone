import capstone.DbHandler;
import capstone.Item;
import capstone.MainMenu.MainMenu;

import javax.swing.*;
import java.sql.SQLOutput;

public class Main {
    public static void main(String[] args) {
        DbHandler.connect();

        // Test run for files
        java.util.ArrayList<Item> stock = DbHandler.getItems();
        for (Item i : stock){
            System.out.println(i);
        }


        // main frame
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Grocery Shopping System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 400);
            frame.setContentPane(new MainMenu()); // Start with MainMenu
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setResizable(false);
        });
    }

}