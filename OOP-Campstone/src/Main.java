import capstone.Admin;
import capstone.DbHandler;
import capstone.Employee;
import capstone.MainMenu;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        DbHandler.connect();

        Admin myAdmin = new Admin();

        Employee newGuy = new Employee("john", "password123");
        Employee newGuy2 = new Employee("bob", "password123");
        Employee newGuy3 = new Employee("carl", "password123");
        myAdmin.addEmployee(newGuy);
        myAdmin.addEmployee(newGuy2);
        myAdmin.addEmployee(newGuy3);

        // main frame
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Grocery Shopping System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 400);
            frame.setContentPane(new MainMenu()); // Start with MainMenu
            frame.setLocationRelativeTo(null); // Center window
            frame.setVisible(true);
            frame.setResizable(false);
        });
    }

}
