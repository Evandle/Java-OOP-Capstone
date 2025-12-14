package capstone;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class AddCustomer1 extends JFrame {
    private JTable table1;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton removeButton;
    private JButton addNewCustomerButton;
    private JButton backButton;
    private JPanel addCustomerPane;

    public AddCustomer1 () {
        setContentPane(addCustomerPane);
        setSize(485,590);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        loadUserDataIntoTable();
        setLocationRelativeTo(null);
        setVisible(true);

        backButton.addActionListener(e -> {
            goBack();
        });
        addNewCustomerButton.addActionListener(e -> {
            addCustomer();
        });
        removeButton.addActionListener(e -> {
            new RemoveUser();
            dispose();
        });
    }

    private void loadUserDataIntoTable() {
        String[] columnNames = {"User ID", "Username", "Role", "Address"};

        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ArrayList<Customer> userList = DbHandler.getCustomer();

        for (Customer u : userList) {
            String address = u.getAddress() == null ? "None" : u.getAddress();

            Object[] rowData = {
                    u.getId(),
                    u.getUsername(),
                    u.getClass().getSimpleName().toUpperCase(),
                    address
            };


            model.addRow(rowData);
        }
        table1.setModel(model);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddCustomer1());
    }
    private void goBack() {
        Admin admin = new Admin();
        setContentPane(new AdminPage((Admin) admin));                      // go back to admin panel                     // Resize window
        pack();
        setLocationRelativeTo(null);
        validate();                                                 // Refresh layout
        repaint();
    }
    public void addCustomer(){
        String username = textField1.getText().trim();                   // Get entered username
        String password = new String(passwordField1.getPassword()).trim();

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
                new AddCustomer1();
                dispose();
            } else {
                // Show error if username is taken or registration failed
                JOptionPane.showMessageDialog(this, "Username is already taken or an error occurred.", "Sign Up Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch(Exception ex) {
            // Handle database connection errors
            JOptionPane.showMessageDialog(this, "Error connecting to database: " + ex.getMessage(), "Sign Up Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
