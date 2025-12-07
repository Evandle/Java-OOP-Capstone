package capstone;

import javax.swing.*;

public class RemoveUser extends JFrame{
    private JLabel titleLabel;
    private JLabel usersLabel;
    private JScrollPane usersScrollPane;
    private JTextField textField1;
    private JTextField textField2;
    private JPasswordField passwordField1;
    private JComboBox comboBox1;
    private JPanel removeUserPanel;

    public RemoveUser() {
        setContentPane(removeUserPanel);
        setSize(500,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new RemoveUser();
    }
}
