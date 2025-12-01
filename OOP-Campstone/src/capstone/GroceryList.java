package capstone;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;

public class GroceryList extends JFrame {
    private JPanel groceryListPanel;
    private JComboBox categoryContainer;
    private JTextField nameAdder;
    private JRadioButton changeStockRadioButton;
    private JRadioButton addItemRadioButton;
    private JRadioButton removeItemRadioButton;
    private JButton addNewButton;
    private JButton removeItemButton;
    private JButton changeItemStockButton;
    private JButton doneButton;
    private JTextField qtyAdder;
    private JTextField priceAdder;
    private JTable table1;
    private JScrollPane itemListScroller;
    private JLabel titleLabel;
    private JLabel itemsScrollLabel;
    private JLabel nameLabel;
    private JLabel categoryLabel;
    private JLabel priceLabel;
    private JLabel quantityLabel;


    public GroceryList(){
        setContentPane(groceryListPanel);
        setSize(500,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        changeItemStockButton.setVisible(false);
        removeItemButton.setVisible(false);
        addItemRadioButton.setSelected(true);
        loadInventory();
        setLocationRelativeTo(null);
        setVisible(true);

        addItemRadioButton.addActionListener(e -> {
            addNewButton.setVisible(true);
            changeItemStockButton.setVisible(false);
            removeItemButton.setVisible(false);

            nameAdder.setEnabled(true);
            priceAdder.setEnabled(true);
            qtyAdder.setEnabled(true);
            categoryContainer.setEnabled(true);
        });

        removeItemRadioButton.addActionListener(e -> {
            addNewButton.setVisible(false);
            changeItemStockButton.setVisible(false);
            removeItemButton.setVisible(true);

            nameAdder.setEnabled(false);
            priceAdder.setEnabled(false);
            qtyAdder.setEnabled(false);
            categoryContainer.setEnabled(false);
        });

        changeStockRadioButton.addActionListener(e -> {
            addNewButton.setVisible(false);
            changeItemStockButton.setVisible(true);
            removeItemButton.setVisible(false);

            nameAdder.setEnabled(false);
            priceAdder.setEnabled(false);
            qtyAdder.setEnabled(true);
            categoryContainer.setEnabled(false);
        });

        addNewButton.addActionListener(e -> {
            String itemName = nameAdder.getText();
            String priceText = priceAdder.getText();
            String stockText = qtyAdder.getText();
            if (itemName.isEmpty() || priceText.isEmpty() || stockText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                return;
            }

            try {
                double price = Double.parseDouble(priceText);
                int stock = Integer.parseInt(stockText);
                int categoryId = categoryContainer.getSelectedIndex() + 1;
                Item newItem = new Item(itemName, price, stock, categoryId);
                boolean success = DbHandler.addItem(newItem);
                if (success) {
                    loadInventory();
                    JOptionPane.showMessageDialog(this, "Item added successfully!");
                    nameAdder.setText("");
                    qtyAdder.setText("");
                    priceAdder.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add item. Name might be taken.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for Price and Stock.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        removeItemButton.addActionListener(e -> {
            int selectedId = getSelectedItemId();
            if (selectedId != -1) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to remove Item ID " + selectedId + "?",
                        "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean success = DbHandler.deleteItem(selectedId);
                    if (success) {
                        loadInventory();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an item to remove.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        changeItemStockButton.addActionListener(e -> {
            int selectedId = getSelectedItemId();
            if (selectedId != -1) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to change the stock of Item ID " + selectedId + "?",
                        "Confirm Changes", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean success = DbHandler.setStock(selectedId, Integer.parseInt(qtyAdder.getText()));
                    if (success) {
                        loadInventory();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an item to change the stock.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        doneButton.addActionListener(e -> System.exit(0));

        nameAdder.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetter(c) && c != ' ' && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
            }
        });

        qtyAdder.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
                    e.consume();
                }
            }
        });

        priceAdder.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '.' && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
                    e.consume();
                    return;
                }
                if (c == '.' && priceAdder.getText().contains(".")) {
                    e.consume();
                }
            }
        });
    }

    private void loadInventory() {
        java.util.List<Item> itemsFromDb = DbHandler.getItems();
        ItemTableModel newModel = new ItemTableModel(itemsFromDb);
        table1.setModel(newModel);
        table1.getColumnModel().getColumn(2).setCellRenderer(new PriceRenderer());
        categoryContainer.removeAllItems();
        java.util.List<String> categories = DbHandler.getCategoryNames();
        for (String cat : categories) {
            categoryContainer.addItem(cat);
        }
    }

    private int getSelectedItemId() {
        int viewRow = table1.getSelectedRow();
        if (viewRow == -1) {
            return -1;
        }
        int modelRow = table1.convertRowIndexToModel(viewRow);
        Object idValue = table1.getModel().getValueAt(modelRow, 0);
        return (int) idValue;
    }

    static void main(String[] args) {
        new GroceryList();
    }

}

class PriceRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (value instanceof Number) {
            String formattedPrice = String.format("₱ %,.2f", ((Number) value).doubleValue());
            setText(formattedPrice);
        }
        setHorizontalAlignment(LEFT);
        return cell;
    }
}

