package capstone.GroceryList;

import capstone.classes.DbHandler;
import capstone.classes.Item;
// Make sure UITheme is imported
import capstone.classes.UserCart;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.table.JTableHeader; // Import added for header styling
import javax.swing.plaf.basic.BasicButtonUI; // Import for custom button UI
import java.awt.*; // Imported for Color/Font usage if needed locally
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
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // ===== APPLY THEME =====
        // 1. Backgrounds
        groceryListPanel.setBackground(UserCart.UITheme.BG);
        addItemRadioButton.setBackground(UserCart.UITheme.BG);
        removeItemRadioButton.setBackground(UserCart.UITheme.BG);
        changeStockRadioButton.setBackground(UserCart.UITheme.BG);

        // 2. Labels
        titleLabel.setFont(UserCart.UITheme.TITLE);
        titleLabel.setForeground(UserCart.UITheme.TEXT);

        // Optional: Style other labels with BODY font
        JLabel[] labels = {itemsScrollLabel, nameLabel, categoryLabel, priceLabel, quantityLabel};
        for (JLabel lbl : labels) {
            if (lbl != null) {
                lbl.setFont(UserCart.UITheme.BODY);
                lbl.setForeground(UserCart.UITheme.TEXT);
            }
        }

        // 3. Buttons
        // We apply the color using your theme, and the Shape using our new global UI class
        applyGlobalButtonStyle(addNewButton, UserCart.UITheme.BLUE);
        applyGlobalButtonStyle(changeItemStockButton, UserCart.UITheme.BLUE);
        applyGlobalButtonStyle(removeItemButton, UserCart.UITheme.BLUE);
        applyGlobalButtonStyle(doneButton, UserCart.UITheme.GREEN);

        // 4. Table Styling
        table1.setFont(UserCart.UITheme.BODY);
        table1.setRowHeight(28); // Taller rows are easier to read
        table1.setGridColor(new Color(224, 224, 224)); // Light gray grid
        table1.setSelectionBackground(UserCart.UITheme.BLUE); // Blue background when selected
        table1.setSelectionForeground(Color.WHITE); // White text when selected
        table1.setShowVerticalLines(false); // Cleaner look

        // Stylize Header
        JTableHeader header = table1.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(UserCart.UITheme.BLUE);
        header.setForeground(Color.WHITE);
        // =======================

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
        doneButton.addActionListener(e -> goBack());

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

    // ===== GLOBAL STYLE APPLICATION =====
    // You can move this method and the RoundedButtonUI class below to your UITheme.java
    // to make it accessible everywhere in your project.
    private void applyGlobalButtonStyle(JButton button, Color color) {
        UserCart.UITheme.styleButton(button, color); // Sets background color
        button.setUI(new RoundedButtonUI()); // Sets the shape
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setOpaque(false);
    }

    private void loadInventory() {
        java.util.List<Item> itemsFromDb = DbHandler.getItems();
        UserCart.ItemTableModel newModel = new UserCart.ItemTableModel(itemsFromDb);
        table1.setModel(newModel);

        // ===== SET COLUMN WIDTHS =====
        if (table1.getColumnCount() >= 5) {
            table1.getColumnModel().getColumn(0).setPreferredWidth(40);  // ID
            table1.getColumnModel().getColumn(1).setPreferredWidth(220); // Name
            table1.getColumnModel().getColumn(2).setPreferredWidth(80);  // Price
            table1.getColumnModel().getColumn(3).setPreferredWidth(60);  // Stock
            table1.getColumnModel().getColumn(4).setPreferredWidth(120); // Category
        }

        table1.getColumnModel().getColumn(2).setCellRenderer(new PriceRenderer());
        table1.getColumnModel().getColumn(4).setCellRenderer(new CategoryRenderer());

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

    public static void main(String[] args) {
        new GroceryList();
    }
    private void goBack() {
        this.dispose();
    }
}

class RoundedButtonUI extends BasicButtonUI {
    public static ComponentUI createUI(JComponent c) {
        return new RoundedButtonUI();
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color color = c.getBackground();

        if (b.getModel().isPressed()) {
            color = color.darker();
        } else if (b.getModel().isRollover()) {
            color = color.brighter();
        }

        g2.setColor(color);

        g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 20, 20);

        g2.dispose();
        super.paint(g, c);
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

class CategoryRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        int id = -1;
        if (value instanceof Number) {
            id = ((Number) value).intValue();
        } else if (value instanceof String) {
            try {
                id = Integer.parseInt((String) value);
            } catch (NumberFormatException ignored) {}
        }

        if (id != -1) {
            setText(getCategoryName(id));
        }
        return this;
    }

    private String getCategoryName(int id) {
        return switch (id) {
            case 1 -> "Frozen Goods";
            case 2 -> "General Necessities";
            case 3 -> "Drinks";
            case 4 -> "Snacks";
            case 5 -> "Canned Goods";
            case 6 -> "Washing Necessities";
            case 7 -> "Bath";
            default -> String.valueOf(id);
        };
    }
}