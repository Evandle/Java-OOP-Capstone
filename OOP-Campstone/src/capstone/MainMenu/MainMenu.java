package capstone.MainMenu;

import capstone.DbHandler;
import capstone.Item;
import capstone.LoginPage;
import capstone.SignUpPage.SignUpPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel {
    // Top panel to hold welcome and title labels
    JPanel topPanel = new JPanel(new GridLayout(2,1));
    private JButton loginButton;     // Button to open login page
    private JButton signUpButton;    // Button to open sign up page

    public MainMenu() {
        setLayout(new BorderLayout()); // Use BorderLayout for overall panel

        // Top Panel: Welcome & Title
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS)); // Arrange labels vertically
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Top and bottom padding

        // Welcome to label
        JLabel welcomeLabel = new JLabel("Welcome to", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 24)); // Set font style and size
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);       // Center horizontally

        // S-MART label with emoji
        JLabel titleLabel = new JLabel("🛒 S-MART", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));   // Set font style and size
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);   // Center horizontally

        // Add labels with vertical spacing
        topPanel.add(Box.createRigidArea(new Dimension(0, 45))); // Top gap
        topPanel.add(welcomeLabel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 5)));  // Small gap between labels
        topPanel.add(titleLabel);

        add(topPanel, BorderLayout.NORTH); // Add top panel to the north section

        // Buttons Panel: Login & Sign Up
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout()); // Buttons arranged horizontally

        // Initialize buttons
        loginButton = new JButton("Login");
        signUpButton = new JButton("Sign Up");

        loginButton.setFocusPainted(false);

        // Set consistent size for buttons
        loginButton.setPreferredSize(new Dimension(120, 40));
        signUpButton.setPreferredSize(new Dimension(120, 40));

        // Add buttons to panel
        buttonPanel.add(loginButton);
        buttonPanel.add(signUpButton);

        add(buttonPanel, BorderLayout.CENTER); // Add button panel to center of main panel

        // Button Action Listeners
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLogin(); // Call method to open login page
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSignUp(); // Call method to open sign up page
            }
        });
    }

    /** Opens the Login Page **/

    private void openLogin() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this); // Get the parent JFrame
        topFrame.getContentPane().removeAll();                              // Remove current panel
        topFrame.getContentPane().add(new LoginPage());                     // Add LoginPage panel
        topFrame.setSize(400, 300);                                         // Resize window
        topFrame.setLocationRelativeTo(null);                                // Center window on screen
        topFrame.revalidate();                                               // Refresh layout
        topFrame.repaint();                                                  // Repaint JFrame
    }

    /** Opens the Sign-Up Page **/

    private void openSignUp() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.getContentPane().removeAll();                               // Remove current panel
        topFrame.getContentPane().add(new SignUpPage());                     // Add SignUpPage panel
        topFrame.setSize(450, 350);                                         // Resize window for sign up
        topFrame.setLocationRelativeTo(null);                                // Center window
        topFrame.revalidate();
        topFrame.repaint();
    }
}
