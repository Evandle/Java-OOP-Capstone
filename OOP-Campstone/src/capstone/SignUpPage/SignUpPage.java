package capstone.SignUpPage;

import capstone.classes.DbHandler;
import capstone.MainMenu.MainMenu;
import capstone.classes.UserCart;

import javax.swing.*;
import java.awt.*;

public class SignUpPage extends JPanel {

    // ===== FORM FIELDS =====
    // Text fields for user input
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField passwordConfirmField;
    private JTextField addressField;

    // Buttons for actions
    private JButton signUpButton;
    private JButton backButton;

    public SignUpPage() {

        // ===== PANEL SETUP =====
        //GridBagLayout for flexible and centered layout
        setLayout(new GridBagLayout());
        setBackground(UserCart.UITheme.BG);

        //padding around the panel to center content nicely
        setBorder(BorderFactory.createEmptyBorder(60, 160, 60, 160));

        //controls component positioning
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12); // spacing between components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // span across two columns

        // ===== TITLE =====
        JLabel title = new JLabel("Create Account", SwingConstants.CENTER);
        title.setFont(UserCart.UITheme.TITLE.deriveFont(30f));
        title.setForeground(UserCart.UITheme.TEXT);
        add(title, gbc);

        // ===== FORM PANEL =====
        // Holds labels and input fields in a grid
        JPanel form = new JPanel(new GridLayout(4, 2, 16, 18));
        form.setBackground(UserCart.UITheme.BG);

        // Username
        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(UserCart.UITheme.BODY);
        userLabel.setForeground(UserCart.UITheme.TEXT);

        usernameField = new JTextField();
        usernameField.setFont(UserCart.UITheme.BODY);
        usernameField.setPreferredSize(new Dimension(220, 36));

        // Password
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(UserCart.UITheme.BODY);
        passLabel.setForeground(UserCart.UITheme.TEXT);

        passwordField = new JPasswordField();
        passwordField.setFont(UserCart.UITheme.BODY);
        passwordField.setPreferredSize(new Dimension(220, 36));

        // Confirm Password
        JLabel confirmLabel = new JLabel("Confirm Password");
        confirmLabel.setFont(UserCart.UITheme.BODY);
        confirmLabel.setForeground(UserCart.UITheme.TEXT);

        passwordConfirmField = new JPasswordField();
        passwordConfirmField.setFont(UserCart.UITheme.BODY);
        passwordConfirmField.setPreferredSize(new Dimension(220, 36));

        // Address
        JLabel addressLabel = new JLabel("Address");
        addressLabel.setFont(UserCart.UITheme.BODY);
        addressLabel.setForeground(UserCart.UITheme.TEXT);

        addressField = new JTextField();
        addressField.setFont(UserCart.UITheme.BODY);
        addressField.setPreferredSize(new Dimension(220, 36));

        // Add components to the form panel
        form.add(userLabel);
        form.add(usernameField);
        form.add(passLabel);
        form.add(passwordField);
        form.add(confirmLabel);
        form.add(passwordConfirmField);
        form.add(addressLabel);
        form.add(addressField);

        // Add form panel below title
        gbc.gridy++;
        add(form, gbc);

        // ===== BUTTONS =====
        signUpButton = new JButton("Sign Up");
        backButton = new JButton("Back");

        // Apply theme styles
        UserCart.UITheme.styleButton(signUpButton, UserCart.UITheme.GREEN);
        UserCart.UITheme.styleButton(backButton, UserCart.UITheme.BLUE);

        // ===== STANDARD SIZING =====
        // Matched to text fields (220 width, 36 height) for consistency
        Dimension standardSize = new Dimension(220, 36);

        signUpButton.setPreferredSize(standardSize);
        signUpButton.setMaximumSize(standardSize); // Ensures BoxLayout respects width

        backButton.setPreferredSize(standardSize);
        backButton.setMaximumSize(standardSize);

        signUpButton.setFont(UserCart.UITheme.BODY);
        backButton.setFont(UserCart.UITheme.BODY);

        // Stack buttons vertically
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(UserCart.UITheme.BG);

        signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(signUpButton);
        buttonPanel.add(Box.createVerticalStrut(12)); // space between buttons
        buttonPanel.add(backButton);

        // Add buttons under the form
        gbc.gridy++;
        add(buttonPanel, gbc);

        // ===== ACTION LISTENERS =====
        signUpButton.addActionListener(e -> signUp());
        backButton.addActionListener(e -> goBack());

    }

    /** Handles user registration when Sign Up button is clicked **/

    private void signUp() {

        // Retrieve user input
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String confirmPassword = new String(passwordConfirmField.getPassword()).trim();
        String address = addressField.getText().trim();

        // Validate that all fields are filled
        if (username.isEmpty() || password.isEmpty()
                || confirmPassword.isEmpty() || address.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all fields.",
                    "Sign Up Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Check if passwords match
        if (!passwordConfirmChecker(password, confirmPassword)) {
            JOptionPane.showMessageDialog(
                    this,
                    "Password confirmation doesn't match.",
                    "Password Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Check password strength
        if (!passwordChecker(password)) {
            JOptionPane.showMessageDialog(
                    this,
                    "Password requires at least 9 characters, a symbol, a capital letter, and a number.",
                    "Password Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        try {
            // Register user as CUSTOMER
            boolean success = DbHandler.registerUser(
                    username, password, "CUSTOMER", address
            );

            if (success) {
                JOptionPane.showMessageDialog(
                        this,
                        "Sign up successful! You can now log in.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
                goBack(); // Return to main menu
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Username is already taken or an error occurred.",
                        "Sign Up Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error connecting to database: " + ex.getMessage(),
                    "Sign Up Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Checks password strength:
     * - At least 9 characters
     * - At least 1 uppercase letter
     * - At least 1 number
     * - At least 1 special character
     */
    public boolean passwordChecker(String password) {

        if (password.length() < 9) return false;

        boolean hasUpper = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isDigit(c)) hasNumber = true;
            else if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }

        return hasUpper && hasNumber && hasSpecial;
    }

    /** Confirms that password and confirm password match **/

    public boolean passwordConfirmChecker(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    /** Returns the user back to the main menu **/

    private void goBack() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.setContentPane(new MainMenu());
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }
}