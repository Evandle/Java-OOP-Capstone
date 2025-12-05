package capstone;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerPage extends JPanel {
    private final Customer currentUser;
    private int currentCategoryIndex = 0;

    private final List<String> categories = List.of(
            "Frozen Goods",        // Index 0 -> ID 1
            "General Necessities", // Index 1 -> ID 2
            "Drinks",              // Index 2 -> ID 3
            "Snacks",              // Index 3 -> ID 4
            "Canned Goods",        // Index 4 -> ID 5
            "Washing Necessities", // Index 5 -> ID 6
            "Bath"                 // Index 6 -> ID 7
    );

    private final JLabel categoryLabel;
    private String currentCategoryName;
    private JScrollPane scrollPane;
    private final JPanel productPanel;

    protected ArrayList<Item> stock;

    public CustomerPage(Customer user) {
        this.currentUser = user;
        refreshStock();

        setLayout(new BorderLayout(10, 10));
        setSize(450, 350);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        JButton nextBtn = new JButton("Next");
        JButton prevBtn = new JButton("Prev");

        currentCategoryName = categories.get(currentCategoryIndex);
        categoryLabel = new JLabel(currentCategoryName, SwingConstants.CENTER);
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 16));

        prevBtn.setEnabled(currentCategoryIndex > 0);

        nextBtn.addActionListener(e->{
            if(currentCategoryIndex < categories.size()-1){
                currentCategoryIndex++;
                updateCategory(categoryLabel);
            }
            nextBtn.setEnabled(currentCategoryIndex < categories.size()-1);
            prevBtn.setEnabled(currentCategoryIndex > 0);
        });

        prevBtn.addActionListener(e->{
            if(currentCategoryIndex > 0){
                currentCategoryIndex--;
                updateCategory(categoryLabel);
            }
            nextBtn.setEnabled(currentCategoryIndex < categories.size()-1);
            prevBtn.setEnabled(currentCategoryIndex > 0);
        });

        topPanel.add(categoryLabel, BorderLayout.CENTER);
        topPanel.add(nextBtn, BorderLayout.EAST);
        topPanel.add(prevBtn, BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH);

        productPanel = new JPanel();
        productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(productPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);

        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton viewCartBtn = new JButton("View Cart");
        JButton checkOutBtn = new JButton("Check Out");

        viewCartBtn.addActionListener(e->{
            viewCartPopup();
        });

        checkOutBtn.addActionListener(e -> {
            performCheckout();
        });

        bottomPanel.add(viewCartBtn);
        bottomPanel.add(checkOutBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        updateCategory(categoryLabel);
    }

    private void refreshStock() {
        this.stock = DbHandler.getItems();
    }

    public void updateCategory(JLabel categoryLabel){
        currentCategoryName = categories.get(currentCategoryIndex);
        categoryLabel.setText(currentCategoryName);

        showProducts(stock, currentCategoryIndex + 1);
    }

    public void showProducts(List<Item> products, int targetCategoryId){
        productPanel.removeAll();

        for(Item item : products){
            if (item.getCategoryId() == targetCategoryId){
                JPanel panel = new JPanel(new BorderLayout(10, 5));
                panel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0,0,1,0, Color.LIGHT_GRAY),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ));
                panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

                JLabel productName = new JLabel(item.getItemName());
                productName.setFont(new Font("Arial", Font.BOLD, 14));

                JLabel productDetails = new JLabel("P" + String.format("%.2f", item.getPrice()) + " | Stock: " + item.getStock());

                JPanel textPanel = new JPanel(new GridLayout(2, 1));
                textPanel.add(productName);
                textPanel.add(productDetails);

                JButton addBtn = new JButton("Add");
                if (item.getStock() <= 0) {
                    addBtn.setText("Out of Stock");
                    addBtn.setEnabled(false);
                }

                addBtn.addActionListener(e->{
                    addToCartLogic(item);
                });

                panel.add(textPanel, BorderLayout.CENTER);
                panel.add(addBtn, BorderLayout.EAST);

                productPanel.add(panel);
            }
        }
        productPanel.revalidate();
        productPanel.repaint();
    }

    private void addToCartLogic(Item item) {
        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, item.getStock(), 1);
        JSpinner spinner = new JSpinner(model);

        int option = JOptionPane.showConfirmDialog(this, new Object[]{"How many " + item.getItemName() + "?", spinner}, "Add to Cart", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            int qty = (int) spinner.getValue();
            boolean success = DbHandler.addToCart(currentUser.getId(), item.getId(), qty);

            if (success) {
                JOptionPane.showMessageDialog(this, "Added " + qty + " x " + item.getItemName() + " to cart.");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add to cart.");
            }
        }
    }

    private void viewCartPopup() {
        ArrayList<CartItem> cart = DbHandler.getUserCart(currentUser.getId());

        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Your cart is empty.");
            return;
        }

        String[] columns = {"Item", "Price", "Qty", "Total"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
            @Override // Make cells read-only
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        double[] grandTotalWrapper = {0.0};

        for (CartItem c : cart) {
            tableModel.addRow(new Object[]{
                    c.getItem().getItemName(),
                    String.format("%.2f", c.getItem().getPrice()),
                    c.getQuantity(),
                    String.format("%.2f", c.getTotal())
            });
            grandTotalWrapper[0] += c.getTotal();
        }

        JTable cartTable = new JTable(tableModel);
        cartTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(cartTable);
        scroll.setPreferredSize(new Dimension(450, 250));

        JLabel totalLabel = new JLabel("Grand Total: P" + String.format("%.2f", grandTotalWrapper[0]), SwingConstants.RIGHT);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton removeBtn = new JButton("Remove Selected");
        removeBtn.setForeground(Color.RED);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(totalLabel, BorderLayout.CENTER);
        bottomPanel.add(removeBtn, BorderLayout.EAST);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(scroll, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        removeBtn.addActionListener(e -> {
            int selectedRow = cartTable.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(mainPanel, "Please select an item to remove.");
                return;
            }

            CartItem itemToRemove = cart.get(selectedRow);

            int confirm = JOptionPane.showConfirmDialog(mainPanel,
                    "Remove " + itemToRemove.getItem().getItemName() + " from cart?",
                    "Confirm Remove", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                DbHandler.removeFromCart(currentUser.getId(), itemToRemove.getItem().getId());

                grandTotalWrapper[0] -= itemToRemove.getTotal();
                totalLabel.setText("Grand Total: P" + String.format("%.2f", grandTotalWrapper[0]));

                cart.remove(selectedRow);

                tableModel.removeRow(selectedRow);

                JOptionPane.showMessageDialog(mainPanel, "Item removed.");
            }
        });

        JOptionPane.showMessageDialog(this, mainPanel, "My Shopping Cart", JOptionPane.PLAIN_MESSAGE);
    }

    private void performCheckout() {
        ArrayList<CartItem> cart = DbHandler.getUserCart(currentUser.getId());

        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cart is empty!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Proceed to checkout?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        DbHandler.deductStock(cart);

        ReceiptGenerator.printReceipt(cart, currentUser.getUsername());

        DbHandler.clearUserCart(currentUser.getId());

        refreshStock();
        updateCategory(categoryLabel);

        JOptionPane.showMessageDialog(this, "Checkout Complete! Receipt saved.");
    }

    public void viewProduct(Item item){
        JOptionPane.showMessageDialog(this, "Selected: " + item.getItemName());
    }

    public void viewCart(Customer user){
        viewCartPopup();
    }
}