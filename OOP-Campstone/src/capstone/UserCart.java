package capstone;

import capstone.SignUpPage.SignUpPage;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UserCart extends JPanel{
    private final Customer currUser;
    public UserCart(Customer user, List<Item> Cart){
        this.currUser = user;
        setLayout(new BorderLayout(5,5));
        JPanel productPanel = new JPanel();
        productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));
        productPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane scrollPane = new JScrollPane(productPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(6);

        for(Item item : Cart){
            JPanel panel = new JPanel(new BorderLayout(5, 5));
            JLabel productName = new JLabel(item.getItemName()+" | P"+item.getPrice()+" | x"+item.getStock());
            panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            panel.add(productName, BorderLayout.WEST);
            productPanel.add(panel);
            productPanel.add(Box.createVerticalStrut(7));

        }
        JPanel bottomPanel = new JPanel();
        JButton backBtn = new JButton("Back");
        JButton checkOutBtn = new JButton("Check Out");

        backBtn.addActionListener(e->{
            back();
        });
        bottomPanel.add(backBtn);
        bottomPanel.add(checkOutBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void back(){
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.getContentPane().removeAll();                               // Remove current panel
        topFrame.getContentPane().add(new CustomerPage(currUser));                     // Add SignUpPage panel
        topFrame.setSize(450, 350);                                         // Resize window for sign up
        topFrame.setLocationRelativeTo(null);                                // Center window
        topFrame.revalidate();
        topFrame.repaint();
    }


}
