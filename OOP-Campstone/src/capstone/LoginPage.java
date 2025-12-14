package capstone;

import capstone.MainMenu.MainMenu;
import capstone.UITheme;

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
        setBackground(UITheme.BG);

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
        title.setFont(UITheme.TITLE.deriveFont(32f));
        title.setForeground(UITheme.TEXT);
        add(title, gbc);

        // ===== FORM PANEL =====
        // Holds username and password inputs
        JPanel form = new JPanel(new GridLayout(2, 2, 15, 20));
        form.setBackground(UITheme.BG);

        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(UITheme.BODY.deriveFont(15f));
        userLabel.setForeground(UITheme.TEXT);

        usernameField = new JTextField();
        usernameField.setFont(UITheme.BODY.deriveFont(15f));
        usernameField.setPreferredSize(new Dimension(220, 36));

        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(UITheme.BODY.deriveFont(15f));
        passLabel.setForeground(UITheme.TEXT);

        passwordField = new JPasswordField();
        passwordField.setFont(UITheme.BODY.deriveFont(15f));
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

        UITheme.styleButton(loginButton, UITheme.GREEN);
        UITheme.styleButton(backButton, UITheme.BLUE);

        Dimension btnSize = new Dimension(140, 45);
        loginButton.setPreferredSize(btnSize);
        backButton.setPreferredSize(btnSize);

        loginButton.setFont(UITheme.BODY.deriveFont(15f));
        backButton.setFont(UITheme.BODY.deriveFont(15f));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(UITheme.BG);

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
    }
}
