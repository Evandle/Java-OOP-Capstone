package capstone;

import javax.swing.*;
import java.awt.*;

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
        setSize(400,500);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(addStockRadio);
        radioGroup.add(removeItemRadio);

        addStockRadio.addActionListener(e -> {
            removeButton.setVisible(false);       // hide remove button
            qtyAdder.setVisible(true);            // show quantity field
            qtyAdder.setEnabled(true);            // make it accessible
        });

        // Listener for Remove Item radio
        removeItemRadio.addActionListener(e -> {
            setSize(400,500);
            removeButton.setVisible(true);        // show remove button
            quantityLabel.setVisible(false);
            qtyAdder.setVisible(false);           // hide quantity field
            qtyAdder.setEnabled(false);           // disable it
        });

        setVisible(true);
    }

    static void main() {
        new GroceryList();
    }
}
