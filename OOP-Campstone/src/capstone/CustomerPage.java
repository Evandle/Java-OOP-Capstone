package capstone;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerPage extends JPanel {
    private final Customer currentUser;
    private int currentCategoryIndex = 0;
    private List<String> categories = List.of("Vegetables","Meat","Fruits");
    private String currentCategory;
    private JScrollPane scrollPane;
    private JPanel productPanel;
    private JLabel categoryLabel;
    public CustomerPage(Customer user) {
        this.currentUser = user;
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        JButton nextBtn = new JButton("Next");
        JButton prevBtn = new JButton("Prev");
        categoryLabel = new JLabel(categories.get(currentCategoryIndex), SwingConstants.CENTER);

        prevBtn.setEnabled(currentCategoryIndex > 0);
        nextBtn.addActionListener(e->{

            if(currentCategoryIndex < categories.size()-1){
                currentCategoryIndex++;
                updateCategory(currentCategoryIndex);
            }
            nextBtn.setEnabled(currentCategoryIndex < categories.size()-1);
            prevBtn.setEnabled(currentCategoryIndex > 0);

        });
        prevBtn.addActionListener(e->{
            if(currentCategoryIndex > 0){
                currentCategoryIndex--;
                updateCategory(currentCategoryIndex);
            }
            nextBtn.setEnabled(currentCategoryIndex < categories.size()-1);
            prevBtn.setEnabled(currentCategoryIndex > 0);
        });

        topPanel.add(categoryLabel, BorderLayout.CENTER);
        topPanel.add(nextBtn, BorderLayout.EAST);
        topPanel.add(prevBtn, BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH);

        productPanel = new JPanel(new FlowLayout(10, 10, 10));
        scrollPane = new JScrollPane(productPanel);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton viewCartBtn = new JButton("View Cart");
        JButton checkOutBtn = new JButton("Check Out");
        bottomPanel.add(viewCartBtn);
        bottomPanel.add(checkOutBtn);

        add(bottomPanel, BorderLayout.SOUTH);

    }

    public void updateCategory(int currentCategoryIndex){
        currentCategory = categories.get(currentCategoryIndex);
        categoryLabel.setText(currentCategory);

//        for(int i = 0; i < 20; i++){
//            demoProducts.add(new CartItem(currentCategory, 10, 10 + i));
//        }
        showProducts(Cart, currentCategoryIndex-1);
    }

    public void showProducts(List<String> products, int currentCategoryIndex){
        for(CartItem item : Cart){
            scrollPane.add(new JPanel());
        }
    }
}