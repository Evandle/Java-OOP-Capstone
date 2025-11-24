package capstone;

import javax.swing.*;

public class LogIn extends JFrame {
    private JTextField txtUserName;
    private JPanel LogInPane;
    private JTextField txtPassword;
    private JButton BtnLogIn;

    public  LogIn() {
        setContentPane(LogInPane);
        setTitle("LogIn");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}


