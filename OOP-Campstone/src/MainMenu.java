import javax.swing.*;

public class MainMenu extends JFrame {
    private JPanel panel1;
    private JButton button1;
    private JButton button2;
    private JPanel ContentPanel;
    private JPanel BtnPanel;

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
