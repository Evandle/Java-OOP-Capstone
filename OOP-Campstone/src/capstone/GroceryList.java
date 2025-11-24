package capstone;

import javax.swing.*;

public class GroceryList extends JFrame {
    private JLabel titleLabel;
    private JLabel categoryLabel;
    private JComboBox categoryBoxContainer;
    private JLabel itemsScrollLabel;
    private JScrollPane itemListScroller;
    private JTextField qtyAdder;
    private JLabel quantityLabel;
    private JPanel groceryListPanel;
    private JRadioButton addStockRadio;
    private JRadioButton removeItemRadio;
    private JButton addNewItemButton;
    private JButton removeButton;
    private JButton doneButton;

    public GroceryList(){
        setContentPane(groceryListPanel);
        setSize(600,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    static void main() {
        new GroceryList();
    }
}
