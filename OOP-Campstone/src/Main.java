import capstone.DbHandler;
import capstone.Item;
import capstone.MainMenu.MainMenu;

import javax.swing.*;
import java.sql.SQLOutput;

public class Main {
    public static void main(String[] args) {
        DbHandler.connect();

        // Test run for files
//        java.util.ArrayList<Item> stock = DbHandler.getItems();
//        java.util.ArrayList<Item> stock1 = new java.util.ArrayList<>();
////        stock1.add(new Item("Potato", 30, 10, 1));
////        stock1.add(new Item("Carrot", 20, 10, 1));
////        stock1.add(new Item("Eggplant", 15, 10, 1));
////        stock1.add(new Item("Pickle", 10, 10, 1));
////        stock1.add(new Item("Celery", 20, 10, 1));
//
//        for (Item i : stock){
////            i.setCategoryId(1);
////            i.setStock(50);
//            System.out.println(i);
////            DbHandler.addItem(i);
//        }
        
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