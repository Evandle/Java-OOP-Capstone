package capstone;

import capstone.MainMenu.MainMenu;

import javax.swing.*;
import java.awt.*;

public class AddCustomer extends JPanel {

    private JTextField usernameField;      // Input field for username
    private JPasswordField passwordField;  // Input field for password
    private JButton addButton;           // Button to trigger login
    private JButton backButton;            // Button to go back to main menu
    private Admin admin;

    public AddCustomer() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);


        //Panel for TITLE
        JLabel addCustomerTitle = new JLabel("Add Customer"); //title label
        addCustomerTitle.setFont(new Font("SanSerif", Font.BOLD, 36)); // just some title fonts
        gbc.gridx = 0; gbc.gridy = 0;  gbc.gridwidth = 2;
        add(addCustomerTitle, gbc);

        gbc.gridwidth = 1;
        gbc.gridx=0; gbc.gridy = 2;
        add(new JLabel("Username:"),gbc);
        usernameField = new JTextField(15);
        gbc.gridx = 1;
        add(usernameField,gbc);


        gbc.gridx=0; gbc.gridy = 3;
        add(new JLabel("Password:"),gbc);
        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        add(passwordField,gbc);

        addButton = new JButton("Add New Customer");
        backButton = new JButton("Back");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(backButton);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        add(buttonPanel,gbc);

        addButton.addActionListener(e->addCustomer());
        backButton.addActionListener(e-> {
            admin();
        });


    }
    public void admin (){
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.getContentPane();
        topFrame.setContentPane(new AdminPage(admin));
        topFrame.setLocationRelativeTo(null);
        topFrame.revalidate();
        topFrame.repaint();
    }
    public void addCustomer(){
        String username = usernameField.getText().trim();                   // Get entered username
        String password = new String(passwordField.getPassword()).trim();

        if(username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Hardcode role as CUSTOMER for new users
            boolean success = DbHandler.registerUser(username, password, "CUSTOMER", null);
            if(success) {
                // Show success message and return to main menu
                JOptionPane.showMessageDialog(this, "Sign up successful! Customer can now log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
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

    private void goBack() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this); // Get parent JFrame
        topFrame.getContentPane().removeAll();                              // Remove current panel
        topFrame.getContentPane().add(new AdminPage(admin));                      // go back to admin panel
        topFrame.setSize(485, 590);                                         // Resize window
        topFrame.setLocationRelativeTo(null);                                // Center window
        topFrame.pack();                                                // Refresh layout
        topFrame.repaint();                                                  // Repaint JFrame
    }
}
