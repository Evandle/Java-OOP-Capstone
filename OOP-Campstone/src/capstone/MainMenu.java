package capstone;

import javax.swing.*;

public class MainMenu extends JFrame {
    private JPanel panel1;
    private JButton AddToCart;
    private JButton toCheckOutButton;
    private JPanel ContentPanel;
    private JPanel BtnPanel;
    private JTextField ChosenToCart;

    public MainMenu(){
        setContentPane(panel1);
        setSize(600,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    static void main() {
        new MainMenu();
    }

}
