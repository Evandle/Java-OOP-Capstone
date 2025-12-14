package capstone.MainMenu;

import capstone.LoginPage.LoginPage;
import capstone.SignUpPage.SignUpPage;
import capstone.classes.UserCart;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JPanel {

    public MainMenu() {

        // ===== PANEL SETUP =====
        setLayout(new BorderLayout());
        setBackground(UserCart.UITheme.BG);

        // Big padding to center content
        setBorder(BorderFactory.createEmptyBorder(120, 200, 120, 200));

        // ===== TITLE =====
        JLabel title = new JLabel("S-MART", SwingConstants.CENTER);
        title.setFont(UserCart.UITheme.TITLE.deriveFont(40f));
        title.setForeground(UserCart.UITheme.TEXT);

        JLabel subtitle = new JLabel(" Welcome to ", SwingConstants.CENTER);
        subtitle.setFont(UserCart.UITheme.BODY.deriveFont(18f));
        subtitle.setForeground(UserCart.UITheme.TEXT);

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(UserCart.UITheme.BG);

        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(subtitle);
        header.add(Box.createVerticalStrut(10));
        header.add(title);
        header.add(Box.createVerticalStrut(40));

        // ===== BUTTONS =====
        JButton login = new JButton("Login");
        JButton signup = new JButton("Sign Up");

        UserCart.UITheme.styleButton(login, UserCart.UITheme.GREEN);
        UserCart.UITheme.styleButton(signup, UserCart.UITheme.BLUE);

        // Make buttons BIGGER
        Dimension btnSize = new Dimension(220, 55);
        login.setPreferredSize(btnSize);
        signup.setPreferredSize(btnSize);
        login.setMaximumSize(btnSize);
        signup.setMaximumSize(btnSize);
        login.setFont(UserCart.UITheme.BODY.deriveFont(16f));
        signup.setFont(UserCart.UITheme.BODY.deriveFont(16f));

        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
        buttons.setBackground(UserCart.UITheme.BG);

        login.setAlignmentX(Component.CENTER_ALIGNMENT);
        signup.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttons.add(login);
        buttons.add(Box.createVerticalStrut(18));
        buttons.add(signup);

        // ===== CENTER WRAPPER =====
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(UserCart.UITheme.BG);

        center.add(header);
        center.add(buttons);

        add(center, BorderLayout.CENTER);

        // ===== FIX FRAME SIZE ONCE =====
        SwingUtilities.invokeLater(() -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (frame != null) {
                frame.setSize(800, 600);
                frame.setMinimumSize(new Dimension(800, 600));
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);
            }
        });

        // ===== NAVIGATION =====
        login.addActionListener(e -> switchPage(new LoginPage()));
        signup.addActionListener(e -> switchPage(new SignUpPage()));
    }

    private void switchPage(JPanel panel) {
        JFrame f = (JFrame) SwingUtilities.getWindowAncestor(this);
        f.setContentPane(panel);
        f.revalidate();
        f.repaint();
    }
}
