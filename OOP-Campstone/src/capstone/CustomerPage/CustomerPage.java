package capstone.CustomerPage;

import capstone.MainMenu.MainMenu;
import capstone.classes.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerPage extends JPanel {

    private final Customer currentUser;
    private int currentCategoryIndex = 0;

    private final List<String> categories = List.of(
            "Frozen Goods",
            "General Necessities",
            "Drinks",
            "Snacks",
            "Canned Goods",
            "Washing Necessities",
            "Bath"
    );

    private final JLabel categoryLabel;
    private String currentCategoryName;
    private JScrollPane scrollPane;
    private final JPanel productPanel;

    protected ArrayList<Item> stock;

    public CustomerPage(Customer user) {

        this.currentUser = user;
        refreshStock();

        // ===== PANEL SETUP =====
        setLayout(new BorderLayout(12, 12));
        setBackground(UserCart.UITheme.BG);
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // ================= TOP AREA =================
        JPanel topContainer = new JPanel();
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
        topContainer.setBackground(UserCart.UITheme.BG);

        // ----- LOGOUT ROW -----
        JButton logoutBtn = new JButton("Logout");
        UserCart.UITheme.styleButton(logoutBtn, UserCart.UITheme.RED);

        JPanel logoutRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        logoutRow.setBackground(UserCart.UITheme.BG);
        logoutRow.add(logoutBtn);

        // ----- CATEGORY NAV ROW -----
        JButton nextBtn = new JButton("Next");
        JButton prevBtn = new JButton("Prev");

        UserCart.UITheme.styleButton(nextBtn, UserCart.UITheme.BLUE);
        UserCart.UITheme.styleButton(prevBtn, UserCart.UITheme.BLUE);

        currentCategoryName = categories.get(currentCategoryIndex);
        categoryLabel = new JLabel(currentCategoryName, SwingConstants.CENTER);
        categoryLabel.setFont(UserCart.UITheme.HEADER);
        categoryLabel.setForeground(UserCart.UITheme.TEXT);

        prevBtn.setEnabled(false);

        nextBtn.addActionListener(e -> {
            if (currentCategoryIndex < categories.size() - 1) {
                currentCategoryIndex++;
                updateCategory(categoryLabel);
            }
            nextBtn.setEnabled(currentCategoryIndex < categories.size() - 1);
            prevBtn.setEnabled(currentCategoryIndex > 0);
        });

        prevBtn.addActionListener(e -> {
            if (currentCategoryIndex > 0) {
                currentCategoryIndex--;
                updateCategory(categoryLabel);
            }
            nextBtn.setEnabled(currentCategoryIndex < categories.size() - 1);
            prevBtn.setEnabled(currentCategoryIndex > 0);
        });

        JPanel categoryRow = new JPanel(new BorderLayout());
        categoryRow.setBackground(UserCart.UITheme.BG);
        categoryRow.add(prevBtn, BorderLayout.WEST);
        categoryRow.add(categoryLabel, BorderLayout.CENTER);
        categoryRow.add(nextBtn, BorderLayout.EAST);

        // Assemble top container
        topContainer.add(logoutRow);
        topContainer.add(Box.createVerticalStrut(8));
        topContainer.add(categoryRow);

        add(topContainer, BorderLayout.NORTH);

        // ================= PRODUCT LIST =================
        productPanel = new JPanel();
        productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));
        productPanel.setBackground(Color.WHITE);

        scrollPane = new JScrollPane(productPanel);
        scrollPane.setBorder(BorderFactory.createLineBorder(UserCart.UITheme.BORDER));
        scrollPane.getVerticalScrollBar().setUnitIncrement(12);

        add(scrollPane, BorderLayout.CENTER);

        // ================= BOTTOM ACTIONS =================
        JButton viewCartBtn = new JButton("View Cart");
        JButton checkOutBtn = new JButton("Check Out");

        UserCart.UITheme.styleButton(viewCartBtn, UserCart.UITheme.BLUE);
        UserCart.UITheme.styleButton(checkOutBtn, UserCart.UITheme.GREEN);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        bottomPanel.setBackground(UserCart.UITheme.BG);
        bottomPanel.add(viewCartBtn);
        bottomPanel.add(checkOutBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        viewCartBtn.addActionListener(e -> viewCartPopup());
        checkOutBtn.addActionListener(e -> performCheckout());

        // ===== LOGOUT ACTION =====
        logoutBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                    this,
                    "Logging out.., "
            );
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.setContentPane(new MainMenu());
            frame.revalidate();
            frame.repaint();
        });

        updateCategory(categoryLabel);
    }

    // ===== REST OF YOUR CODE (UNCHANGED) =====
    private void refreshStock() { stock = DbHandler.getItems(); }

    public void updateCategory(JLabel categoryLabel) {
        currentCategoryName = categories.get(currentCategoryIndex);
        categoryLabel.setText(currentCategoryName);
        showProducts(stock, currentCategoryIndex + 1);
    }

    public void showProducts(List<Item> products, int targetCategoryId) {
        productPanel.removeAll();
        for (Item item : products) {
            if (item.getCategoryId() == targetCategoryId) {
                JPanel panel = new JPanel(new BorderLayout(10, 5));
                panel.setBackground(Color.WHITE);
                panel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 1, 0, UserCart.UITheme.BORDER),
                        BorderFactory.createEmptyBorder(8, 10, 8, 10)
                ));
                panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

                JLabel productName = new JLabel(item.getItemName());
                productName.setFont(UserCart.UITheme.BODY.deriveFont(Font.BOLD));

                JLabel productDetails = new JLabel(
                        "₱" + String.format("%.2f", item.getPrice()) +
                                "  |  Stock: " + item.getStock()
                );
                productDetails.setFont(UserCart.UITheme.BODY);

                JPanel textPanel = new JPanel(new GridLayout(2, 1));
                textPanel.setBackground(Color.WHITE);
                textPanel.add(productName);
                textPanel.add(productDetails);

                JButton addBtn = new JButton("Add");
                UserCart.UITheme.styleButton(addBtn, UserCart.UITheme.GREEN);

                if (item.getStock() <= 0) {
                    addBtn.setText("Out of Stock");
                    addBtn.setEnabled(false);
                }

                addBtn.addActionListener(e -> addToCartLogic(item));

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

        int option = JOptionPane.showConfirmDialog(
                this,
                new Object[]{"How many " + item.getItemName() + "?", spinner},
                "Add to Cart",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (option == JOptionPane.OK_OPTION) {
            int qty = (int) spinner.getValue();
            DbHandler.addToCart(currentUser.getId(), item.getId(), qty);
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
            public boolean isCellEditable(int r, int c) { return false; }
        };

        double grandTotal = 0;
        for (CartItem c : cart) {
            tableModel.addRow(new Object[]{
                    c.getItem().getItemName(),
                    String.format("%.2f", c.getItem().getPrice()),
                    c.getQuantity(),
                    String.format("%.2f", c.getTotal())
            });
            grandTotal += c.getTotal();
        }

        JTable cartTable = new JTable(tableModel);
        UserCart.UITheme.styleTable(cartTable);

        JScrollPane scroll = new JScrollPane(cartTable);
        scroll.setPreferredSize(new Dimension(450, 250));

        JLabel totalLabel = new JLabel(
                "Grand Total: ₱" + String.format("%.2f", grandTotal),
                SwingConstants.RIGHT
        );
        totalLabel.setFont(UserCart.UITheme.HEADER);

        JButton removeBtn = new JButton("Remove Selected");
        UserCart.UITheme.styleButton(removeBtn, UserCart.UITheme.RED);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(totalLabel, BorderLayout.CENTER);
        bottom.add(removeBtn, BorderLayout.EAST);

        JPanel main = new JPanel(new BorderLayout(10, 10));
        main.add(scroll, BorderLayout.CENTER);
        main.add(bottom, BorderLayout.SOUTH);

        removeBtn.addActionListener(e -> {
            int row = cartTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(main, "Please select an item.");
                return;
            }

            CartItem ci = cart.get(row);
            DbHandler.removeFromCart(currentUser.getId(), ci.getItem().getId());
            tableModel.removeRow(row);
            JOptionPane.showMessageDialog(main, "Item removed.");
        });

        JOptionPane.showMessageDialog(this, main, "My Shopping Cart", JOptionPane.PLAIN_MESSAGE);
    }





    private void performCheckout() {

        ArrayList<CartItem> cart = DbHandler.getUserCart(currentUser.getId());

        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cart is empty!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Proceed to checkout?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        DbHandler.deductStock(cart);
        ReceiptGenerator.printReceipt(cart, currentUser.getUsername());
        DbHandler.clearUserCart(currentUser.getId());

        refreshStock();
        updateCategory(categoryLabel);

        JOptionPane.showMessageDialog(this, "Checkout Complete! Receipt saved.");
    }

}
