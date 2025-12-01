package capstone;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerPage extends JPanel {
    private final Customer currentUser;
    private int currentCategoryIndex = 0;
    private final List<String> categories = List.of("Meat","Vegetables","Fruits");

    private final JLabel categoryLabel;
    private String currentCategoryName;
    private JScrollPane scrollPane;
    private final JPanel productPanel;

    // Stock of items from database
    protected ArrayList<Item> stock;
    public CustomerPage(Customer user) {
        this.currentUser = user;
        this.stock = DbHandler.getItems();
        setLayout(new BorderLayout(10, 10));
        setSize(450, 350);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        JButton nextBtn = new JButton("Next");
        JButton prevBtn = new JButton("Prev");
        categoryLabel = new JLabel(categories.get(currentCategoryIndex), SwingConstants.CENTER);
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
        productPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        scrollPane = new JScrollPane(productPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(6);

        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton viewCartBtn = new JButton("View Cart");
        JButton checkOutBtn = new JButton("Check Out");

        viewCartBtn.addActionListener(e->{
            viewCart(currentUser);
        });

        bottomPanel.add(viewCartBtn);
        bottomPanel.add(checkOutBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        currentCategoryName = categories.get(currentCategoryIndex);
        updateCategory(categoryLabel);
    }

    public void updateCategory(JLabel categoryLabel){
        currentCategoryName = categories.get(currentCategoryIndex);
        categoryLabel.setText(currentCategoryName);

        showProducts(stock, currentCategoryIndex+1);
    }

    public void showProducts(List<Item> products, int currentCategoryIndex){
        productPanel.removeAll();
        for(Item item : products){
            JPanel panel = new JPanel(new BorderLayout(5, 5));


            JButton viewBtn = new JButton("VIEW");
            viewBtn.addActionListener(e->{
                viewProduct(item);
            });
            if (item.getCategoryId()+1 == currentCategoryIndex){
                JLabel productName = new JLabel(item.getItemName()+" | P"+item.getPrice()+" | x"+item.getStock());
                panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                panel.add(productName, BorderLayout.WEST);
                panel.add(viewBtn, BorderLayout.EAST);
                productPanel.add(panel);
                productPanel.add(Box.createVerticalStrut(5));
            }

        }
        productPanel.revalidate();
        productPanel.repaint();
    }

    public void viewProduct(Item item){

    }

    public void viewCart(Customer user){
        List<Item> currCart = user.getCart();
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this); // Get the parent JFrame
        topFrame.getContentPane().removeAll();                              // Remove current panel
        topFrame.getContentPane().add(new UserCart(currentUser,currCart));                     // Add LoginPage panel
        topFrame.setSize(400, 300);                                         // Resize window
        topFrame.setLocationRelativeTo(null);                                // Center window on screen
        topFrame.revalidate();                                               // Refresh layout
        topFrame.repaint();
    }

}