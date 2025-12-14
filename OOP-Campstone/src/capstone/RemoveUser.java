package capstone;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class RemoveUser extends JFrame{
    private JLabel titleLabel;
    private JLabel usersLabel;
     JScrollPane usersScrollPane;
    private JPanel removeUserPanel;
    private JButton doneButton;
    private JButton removeButton;
    private JTable table1;

    public RemoveUser() {
        setContentPane(removeUserPanel);
        setSize(485,590);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        loadUserDataIntoTable();
        setLocationRelativeTo(null);
        setVisible(true);

        doneButton.addActionListener(e -> {
            goBack();
        });
        removeButton.addActionListener(e -> {
            User selectedUser = getSelectedUser();
            if (selectedUser != null) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to remove Item ID " + selectedUser.getId() + "?",
                        "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean success = DbHandler.deleteUser(selectedUser.getUsername());
                    if (success) {
                        loadUserDataIntoTable();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an item to remove.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            }
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

        ArrayList<User> userList = DbHandler.getUsers();

        for (User u : userList) {
            String address = "None";


            if (u instanceof Customer) {
                address = ((Customer) u).getAddress();
                if (address == null) {
                    address = "None";
                }
            }

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

    private User getSelectedUser() {
        ArrayList<User> userList = DbHandler.getUsers();
        int viewRow = table1.getSelectedRow();
        if (viewRow == -1) {
            return null;
        }
        int modelRow = table1.convertRowIndexToModel(viewRow);
        Object idValue = table1.getModel().getValueAt(modelRow, 0);
        int selectedId = (int) idValue;

        if(userList != null){
            for(User u : userList){
                if(u.getId() == selectedId){
                    return u;
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RemoveUser());
    }
    private void goBack() {
        Admin admin = new Admin();
        setContentPane(new AdminPage((Admin) admin));                      // go back to admin panel                     // Resize window
        pack();
        setLocationRelativeTo(null);
        validate();                                                 // Refresh layout
        repaint();
    }
}