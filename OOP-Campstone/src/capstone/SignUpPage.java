package capstone;

import javax.swing.*;
import java.awt.*;

public class SignUpPage extends JPanel {

    // GUI components
    private JTextField usernameField;      // Input field for entering username
    private JPasswordField passwordField;  // Input field for entering password
    private JButton signUpButton;          // Button to register a new user
    private JButton backButton;            // Button to return to the main menu

    public SignUpPage() {
        // Use GridBagLayout for flexible positioning
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5); // Spacing around components

        // Title Label
        JLabel title = new JLabel("User Sign Up");
        title.setFont(new Font("SansSerif", Font.BOLD, 22)); // Font style and size
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;      // Span two columns
        add(title, gbc);

        // Username Label and Field
        gbc.gridwidth = 1; // Reset grid width
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Username:"), gbc);
        usernameField = new JTextField(15); // Input field of width 15
        gbc.gridx = 1;
        add(usernameField, gbc);

        // Password Label and Field
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Password:"), gbc);
        passwordField = new JPasswordField(15); // Input field hides characters
        gbc.gridx = 1;
        add(passwordField, gbc);

        // Buttons: Sign Up and Back
        signUpButton = new JButton("Sign Up");
        backButton = new JButton("Back");
        JPanel buttonPanel = new JPanel(); // Panel to hold buttons
        buttonPanel.add(signUpButton);
        buttonPanel.add(backButton);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; // Span two columns
        add(buttonPanel, gbc);

        // Button Action Listeners
        signUpButton.addActionListener(e -> signUp()); // Register user when clicked
        backButton.addActionListener(e -> goBack());   // Go back to main menu
    }

    /** Handles user registration **/

    private void signUp() {
        String username = usernameField.getText().trim();                // Get entered username
        String password = new String(passwordField.getPassword()).trim(); // Get entered password

        // Validate that both fields are filled
        if(username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Hardcode role as CUSTOMER for new users
            boolean success = DbHandler.registerUser(username, password, "CUSTOMER");
            if(success) {
                // Show success message and return to main menu
                JOptionPane.showMessageDialog(this, "Sign up successful! You can now log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
                goBack();
            } else {
                // Show error if username is taken or registration failed
                JOptionPane.showMessageDialog(this, "Username is already taken or an error occurred.", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch(Exception ex) {
            // Handle database connection errors
            JOptionPane.showMessageDialog(this, "Error connecting to database: " + ex.getMessage(), "Sign Up Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Returns to the main menu **/
    private void goBack() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this); // Get parent JFrame
        topFrame.getContentPane().removeAll();                              // Remove current panel
        topFrame.getContentPane().add(new MainMenu());                      // Add main menu panel
        topFrame.setSize(500, 400);                                         // Resize window
        topFrame.setLocationRelativeTo(null);                                // Center window
        topFrame.validate();                                                 // Refresh layout
        topFrame.repaint();                                                  // Repaint JFrame
    }
}
