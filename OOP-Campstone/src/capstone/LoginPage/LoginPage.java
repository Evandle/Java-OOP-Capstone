package capstone.LoginPage;

import capstone.AdminPage.AdminPage;
import capstone.CustomerPage.CustomerPage;
import capstone.MainMenu.MainMenu;
import capstone.classes.*;

import javax.swing.*;
import java.awt.*;

public class LoginPage extends JPanel {

    // ===== INPUT FIELDS =====
    private JTextField usernameField;
    private JPasswordField passwordField;

    // ===== BUTTONS =====
    private JButton loginButton;
    private JButton backButton;

    public LoginPage() {

        // ===== PANEL SETUP =====
        // GridBagLayout allows flexible positioning
        setLayout(new GridBagLayout());
        setBackground(UserCart.UITheme.BG);

        // Padding to center content
        setBorder(BorderFactory.createEmptyBorder(80, 180, 80, 180));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(14, 14, 14, 14);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        // ===== TITLE =====
        JLabel title = new JLabel("Customer Login", SwingConstants.CENTER);
        title.setFont(UserCart.UITheme.TITLE.deriveFont(32f));
        title.setForeground(UserCart.UITheme.TEXT);
        add(title, gbc);

        // ===== FORM PANEL =====
        // Holds username and password inputs
        JPanel form = new JPanel(new GridLayout(2, 2, 15, 20));
        form.setBackground(UserCart.UITheme.BG);

        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(UserCart.UITheme.BODY.deriveFont(15f));
        userLabel.setForeground(UserCart.UITheme.TEXT);

        usernameField = new JTextField();
        usernameField.setFont(UserCart.UITheme.BODY.deriveFont(15f));
        // Standard Text Field Size
        usernameField.setPreferredSize(new Dimension(220, 36));

        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(UserCart.UITheme.BODY.deriveFont(15f));
        passLabel.setForeground(UserCart.UITheme.TEXT);

        passwordField = new JPasswordField();
        passwordField.setFont(UserCart.UITheme.BODY.deriveFont(15f));
        passwordField.setPreferredSize(new Dimension(220, 36));

        // Add components to form panel
        form.add(userLabel);
        form.add(usernameField);
        form.add(passLabel);
        form.add(passwordField);

        gbc.gridy++;
        add(form, gbc);

        // ===== BUTTON PANEL =====
        loginButton = new JButton("Login");
        backButton = new JButton("Back");

        UserCart.UITheme.styleButton(loginButton, UserCart.UITheme.GREEN);
        UserCart.UITheme.styleButton(backButton, UserCart.UITheme.BLUE);

        // ===== STANDARD SIZING =====
        // Matched to text fields (220 width, 36 height) for consistency
        Dimension standardSize = new Dimension(220, 36);

        loginButton.setPreferredSize(standardSize);
        loginButton.setMaximumSize(standardSize); // Ensures BoxLayout respects width

        backButton.setPreferredSize(standardSize);
        backButton.setMaximumSize(standardSize);

        loginButton.setFont(UserCart.UITheme.BODY.deriveFont(15f));
        backButton.setFont(UserCart.UITheme.BODY.deriveFont(15f));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(UserCart.UITheme.BG);

        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(loginButton);
        buttonPanel.add(Box.createVerticalStrut(10)); // space between buttons
        buttonPanel.add(backButton);


        gbc.gridy++;
        add(buttonPanel, gbc);

        // ===== ACTION LISTENERS =====
        loginButton.addActionListener(e -> login());
        backButton.addActionListener(e -> goBack());
    }

    /** Handles login authentication **/

    private void login() {

        // Retrieve user input
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        // Validate empty fields
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in both fields.",
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        try {
            // Attempt login from database
            User user = DbHandler.loginUser(username, password);

            // Invalid credentials
            if (user == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Invalid username or password.",
                        "Login Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // Successful login
            JOptionPane.showMessageDialog(
                    this,
                    "Login successful! Welcome, " + user.getUsername()
            );

            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);

            // Redirect based on user role
            frame.setContentPane(
                    user instanceof Admin
                            ? new AdminPage((Admin) user)
                            : new CustomerPage((Customer) user)
            );

            frame.revalidate();
            frame.repaint();
            frame.setVisible(true); // Ensure frame updates

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error connecting to database: " + ex.getMessage(),
                    "Login Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /** Returns user to the main menu **/

    private void goBack() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.setContentPane(new MainMenu());
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }
}