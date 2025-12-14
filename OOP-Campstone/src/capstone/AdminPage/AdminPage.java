package capstone.AdminPage;

import capstone.GroceryList.GroceryList;
import capstone.ManageUsers;
import capstone.classes.Admin;
import capstone.classes.UserCart;

import javax.swing.*;
import java.awt.*;

public class AdminPage extends JPanel {

    public AdminPage(Admin admin) {

        // ===== PANEL SETUP =====
        setLayout(new BorderLayout(0, 25));
        setBackground(UserCart.UITheme.BG);
        setBorder(BorderFactory.createEmptyBorder(50, 60, 40, 60));

        // ===== TITLE =====
        JLabel title = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        title.setFont(UserCart.UITheme.TITLE);
        title.setForeground(UserCart.UITheme.TEXT);

        JLabel subtitle = new JLabel("Manage system users and inventory", SwingConstants.CENTER);
        subtitle.setFont(UserCart.UITheme.BODY);
        subtitle.setForeground(UserCart.UITheme.TEXT);

        JPanel header = new JPanel(new GridLayout(2, 1, 0, 8));
        header.setBackground(UserCart.UITheme.BG);
        header.add(title);
        header.add(subtitle);

        add(header, BorderLayout.NORTH);

        // ===== MENU BUTTONS =====
        JPanel menu = new JPanel(new GridLayout(5, 1, 0, 16));
        menu.setBackground(UserCart.UITheme.BG);

        // 🔑 Limit visual width so it fits nicely in 800×600
        menu.setPreferredSize(new Dimension(280, 300));

        JButton manageUsers = new JButton("Manage Users");
        JButton grocery = new JButton("Grocery List");
        JButton home    = new JButton("Home");

        UserCart.UITheme.styleButton(manageUsers, UserCart.UITheme.GREEN);
        UserCart.UITheme.styleButton(grocery, UserCart.UITheme.BLUE);
        UserCart.UITheme.styleButton(home, UserCart.UITheme.BLUE);

        manageUsers.addActionListener(e-> new ManageUsers());
        grocery.addActionListener(e-> new GroceryList());

        menu.add(manageUsers);
        menu.add(grocery);
        menu.add(home);

        // ===== CENTER WRAPPER =====
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setBackground(UserCart.UITheme.BG);
        centerWrapper.add(menu);

        add(centerWrapper, BorderLayout.CENTER);

        // ===== ACTIONS =====
        home.addActionListener(e -> {
            JFrame f = (JFrame) SwingUtilities.getWindowAncestor(this);
            f.setContentPane(new capstone.MainMenu.MainMenu());
            f.revalidate();
            f.repaint();
        });
    }
}
