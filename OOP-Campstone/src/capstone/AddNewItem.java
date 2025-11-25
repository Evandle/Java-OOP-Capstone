package capstone;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddNewItem extends JFrame {
    private JPanel addNewItemPanel;
    private JComboBox categoryCB;
    private JTextField nameTF;
    private JButton addButton;
    private JTextField stockTF;
    private JButton doneButton;
    private JTextField priceTF;
    private JLabel titleLabel;
    private JLabel newItemCategory;
    private JLabel nameLabel;
    private JLabel stockLabel;
    private JLabel priceLabel;

    public AddNewItem(){
        setContentPane(addNewItemPanel);
        setSize(300,400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null); //Center

        addButton.addActionListener(e -> addItemToDatabase());
        doneButton.addActionListener(e -> dispose());
    }

    private void addItemToDatabase() {
        String name = nameTF.getText();
        String stockText = stockTF.getText();
        String priceText = priceTF.getText(); // Make sure you added this!

        if (name.isEmpty() || stockText.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        try {

            int stock = Integer.parseInt(stockText);
            double price = Double.parseDouble(priceText);

            //WIP DO LATER
            int categoryId = categoryCB.getSelectedIndex() + 1;


            Item newItem = new Item(name, price, stock);
            boolean success = DbHandler.addItem(newItem);

            if (success) {
                JOptionPane.showMessageDialog(this, "Item added successfully!");

                nameTF.setText("");
                stockTF.setText("");
                priceTF.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add item. Name might be taken.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for Price and Stock.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    static void main() {
        new AddNewItem();
    }
}
