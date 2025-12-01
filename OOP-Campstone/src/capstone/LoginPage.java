package capstone;

import capstone.MainMenu.MainMenu;

import javax.swing.*;
import java.awt.*;

public class LoginPage extends JPanel {

    // GUI components
    private JTextField usernameField;      // Input field for username
    private JPasswordField passwordField;  // Input field for password
    private JButton loginButton;           // Button to trigger login
    private JButton backButton;            // Button to go back to main menu

    public LoginPage() {
        // Set layout manager for this panel
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5); // Spacing around components

        // Title label
        JLabel title = new JLabel("Customer Login");
        title.setFont(new Font("SansSerif", Font.BOLD, 22)); // Set font size and style
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(title, gbc);

        // Username label and text field
        gbc.gridwidth = 1; // Reset grid width
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Username:"), gbc);
        usernameField = new JTextField(15);
        gbc.gridx = 1;
        add(usernameField, gbc);

        // Password label and password field
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Password:"), gbc);
        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        add(passwordField, gbc);

        // Login and Back buttons
        loginButton = new JButton("Login");
        backButton = new JButton("Back");
        JPanel buttonPanel = new JPanel(); // Panel to hold buttons
        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        add(buttonPanel, gbc);

        // Button action listeners
        loginButton.addActionListener(e -> login()); // Call login() method on click
        backButton.addActionListener(e -> goBack()); // Call goBack() method on click
    }

    /** Handles the login process when login button is clicked **/

    private void login() {
        String username = usernameField.getText().trim(); // Get entered username
        String password = new String(passwordField.getPassword()).trim(); // Get entered password

        // Validate input fields
        if(username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in both fields.", "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Authenticate user via DbHandler
            User user = DbHandler.loginUser(username, password);

            if(user == null) {
                // Show error if credentials are invalid
                JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Show welcome message if login successful
            JOptionPane.showMessageDialog(this, "Login successful! Welcome, " + user.getUsername());

            // Switch panel depending on user type
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.getContentPane().removeAll(); // Clear current panel

            if(user instanceof Admin) {
                // To implement AdminPage, remove "//" below
                // topFrame.getContentPane().add(new AdminPage((Admin) user));
            } else if(user instanceof Customer) {
                // To implement CustomerPage, remove "//" below
                topFrame.getContentPane().add(new CustomerPage((Customer) user));
            }

            topFrame.revalidate(); // Refresh layout
            topFrame.repaint();    // Repaint panel

        } catch (Exception ex) {
            // Handle any database connection errors
            JOptionPane.showMessageDialog(this, "Error connecting to database: " + ex.getMessage(), "Login Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Handles the back button action to return to main menu **/

    private void goBack() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.getContentPane().removeAll(); // Remove current panel
        topFrame.getContentPane().add(new MainMenu()); // Add main menu panel
        topFrame.setSize(500, 400);                 // Resize frame to main menu
        topFrame.setLocationRelativeTo(null);       // Center window
        topFrame.validate();                         // Recalculate layout
        topFrame.repaint();                          // Refresh frame
    }
}
