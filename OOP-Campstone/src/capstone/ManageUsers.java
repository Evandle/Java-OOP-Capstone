package capstone;

import capstone.classes.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class ManageUsers extends JFrame {
    private JTable table1;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton removeButton;
    private JButton addNewCustomerButton;
    private JButton backButton;
    private JPanel addCustomerPane;
    private JButton addNewEmployeeButton;

    public ManageUsers() {
        setContentPane(addCustomerPane);
        setSize(485, 590);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- APPLY THEME ---
        UserCart.UITheme.stylePanel(addCustomerPane);
        UserCart.UITheme.styleTable(table1);

        // --- APPLIED ROUNDED BUTTONS ---
        UserCart.UITheme.styleRoundedButton(addNewCustomerButton, UserCart.UITheme.GREEN);
        UserCart.UITheme.styleRoundedButton(addNewEmployeeButton, UserCart.UITheme.GREEN);

        // Manually applying rounded style with RED since styleDanger() is square
        UserCart.UITheme.styleRoundedButton(removeButton, UserCart.UITheme.RED);

        UserCart.UITheme.styleRoundedButton(backButton, UserCart.UITheme.BLUE);
        // -------------------------------

        // --- TABLE SELECTION SETTINGS (Crucial Fixes) ---
        table1.setModel(createTableModel()); // Load data first

        // 1. Make sure the table is Enabled (allows clicking)
        table1.setEnabled(true);

        // 2. Allow row selection
        table1.setRowSelectionAllowed(true);
        table1.setColumnSelectionAllowed(false);

        // 3. Only allow one row to be picked at a time
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // ------------------------------------------------



        backButton.addActionListener(e -> goBack());
        addNewCustomerButton.addActionListener(e -> addCustomer());
        addNewEmployeeButton.addActionListener(e -> addEmployee());

        removeButton.addActionListener(e -> {
            User selectedUser = getSelectedUser();
            if (selectedUser != null) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to remove User ID " + selectedUser.getId() + "?",
                        "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean success = DbHandler.deleteUser(selectedUser.getUsername());
                    if (success) {
                        refreshTable(); // Helper to reload data
                        JOptionPane.showMessageDialog(this, "User removed.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a user to remove.", "Selection Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        setVisible(true);
    }

    // Separated the Model Creation for cleaner refreshing
    private DefaultTableModel createTableModel() {
        String[] columnNames = {"User ID", "Username", "Role", "Address"};

        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // This prevents TYPING, but still allows SELECTING
            }
        };

        ArrayList<User> userList = DbHandler.getUsers();

        for (User u : userList) {
            String address = "N/A";
            if(u instanceof Customer c) {
                address = c.getAddress() == null ? "None" : c.getAddress();
            }

            if(u instanceof Admin) {
                continue;
            }

            Object[] rowData = {
                    u.getId(),
                    u.getUsername(),
                    u.getClass().getSimpleName().toUpperCase(),
                    address
            };
            model.addRow(rowData);
        }
        return model;
    }

    // Replaced 'loadUserDataIntoTable' with this cleaner refresh method
    private void refreshTable() {
        table1.setModel(createTableModel());
    }

    // Kept for compatibility if you call it elsewhere, but redirects to refreshTable
    private void loadUserDataIntoTable() {
        refreshTable();
    }

    private void goBack() {
        this.dispose();
    }

    private User getSelectedUser() {
        ArrayList<User> userList = DbHandler.getUsers();
        int viewRow = table1.getSelectedRow();

        if (viewRow == -1) {
            return null;
        }

        // Handle sorting if you ever enable it
        int modelRow = table1.convertRowIndexToModel(viewRow);

        Object idValue = table1.getModel().getValueAt(modelRow, 0);
        int selectedId = (int) idValue;

        for (User u : userList) {
            if (u.getId() == selectedId) {
                return u;
            }
        }
        return null;
    }

    public void addCustomer() {
        String username = textField1.getText().trim();
        String password = new String(passwordField1.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        try {
            boolean success = DbHandler.registerUser(username, password, "CUSTOMER", null);
            if (success) {
                JOptionPane.showMessageDialog(this, "Customer added successfully!");
                refreshTable(); // Just refresh table, don't close window
                textField1.setText("");
                passwordField1.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Username taken or error occurred.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public void addEmployee() {
        String username = textField1.getText().trim();
        String password = new String(passwordField1.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        try {
            boolean success = DbHandler.registerUser(username, password, "EMPLOYEE", null);
            if (success) {
                JOptionPane.showMessageDialog(this, "Employee added successfully!");
                refreshTable(); // Just refresh table
                textField1.setText("");
                passwordField1.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Username taken or error occurred.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ManageUsers::new);
    }
}