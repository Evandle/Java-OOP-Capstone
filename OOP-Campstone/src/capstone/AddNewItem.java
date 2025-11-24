package capstone;

import javax.swing.*;

public class AddNewItem extends JFrame {
    private JPanel addNewItemPanel;
    private JLabel titleLabel;
    private JComboBox categoryCB;
    private JLabel newItemCategory;
    private JTextField textField1;
    private JLabel nameLabel;
    private JButton addButton;
    private JLabel quantityLabel;
    private JTextField textField2;
    private JButton doneButton;

    public AddNewItem(){
        setContentPane(addNewItemPanel);
        setSize(300,330);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    static void main() {
        new AddNewItem();
    }
}
