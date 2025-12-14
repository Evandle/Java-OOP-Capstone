package capstone;

import capstone.UITheme;

import javax.swing.*;
import java.awt.*;

public class AdminPage extends JPanel {

    public AdminPage(Admin admin) {

        // ===== PANEL SETUP =====
        setLayout(new BorderLayout(0, 25));
        setBackground(UITheme.BG);
        setBorder(BorderFactory.createEmptyBorder(50, 60, 40, 60));

        // ===== TITLE =====
        JLabel title = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        title.setFont(UITheme.TITLE);
        title.setForeground(UITheme.TEXT);

        JLabel subtitle = new JLabel("Manage system users and inventory", SwingConstants.CENTER);
        subtitle.setFont(UITheme.BODY);
        subtitle.setForeground(UITheme.TEXT);

        JPanel header = new JPanel(new GridLayout(2, 1, 0, 8));
        header.setBackground(UITheme.BG);
        header.add(title);
        header.add(subtitle);

        add(header, BorderLayout.NORTH);

        // ===== MENU BUTTONS =====
        JPanel menu = new JPanel(new GridLayout(5, 1, 0, 16));
        menu.setBackground(UITheme.BG);

        // 🔑 Limit visual width so it fits nicely in 800×600
        menu.setPreferredSize(new Dimension(280, 300));

        JButton addCust = new JButton("Add Customer");
        JButton addEmp  = new JButton("Add Employee");
        JButton remove  = new JButton("Remove User");
        JButton grocery = new JButton("Grocery List");
        JButton home    = new JButton("Home");

        UITheme.styleButton(addCust, UITheme.GREEN);
        UITheme.styleButton(addEmp, UITheme.GREEN);
        UITheme.styleButton(remove, UITheme.RED);
        UITheme.styleButton(grocery, UITheme.BLUE);
        UITheme.styleButton(home, UITheme.BLUE);

        menu.add(addCust);
        menu.add(addEmp);
        menu.add(remove);
        menu.add(grocery);
        menu.add(home);

        // ===== CENTER WRAPPER =====
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setBackground(UITheme.BG);
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
