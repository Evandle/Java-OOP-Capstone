package capstone;
import capstone.GroceryList.GroceryList;
import capstone.MainMenu.MainMenu;
import javax.swing.*;
import java.awt.*;

public class AdminPage extends JPanel {
    public AdminPage (Admin admin){
        setLayout(new BorderLayout(10,10));
//        ImageIcon adminImage = new ImageIcon("");
        JPanel panel1 = new JPanel(); // Panel for Title
        panel1.setLayout(new BorderLayout());
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS)); // Arrange labels vertically
        panel1.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Top and bottom padding
        JLabel adminLabel = new JLabel("\uD83D\uDD12 ADMIN");
        adminLabel.setFont(new Font("SansSerif", Font.BOLD, 36));   // Set font style and size
        adminLabel.setAlignmentX(Component.CENTER_ALIGNMENT);   // Center horizontally
        panel1.add(Box.createRigidArea(new Dimension(0, 45))); // Top gap
        panel1.add(adminLabel);
        add(panel1, BorderLayout.NORTH);
        panel1.add(adminLabel, BorderLayout.CENTER);
        JPanel btnPanel = new JPanel(new FlowLayout(100)); // Panel for Buttons
        JButton addEmployeeBtn = new JButton("Add Employee");
        JButton addCustomerBtn = new JButton("Add Customer");
        JButton viewGroceryBtn = new JButton("View Grocery List");

        viewGroceryBtn.setAlignmentX(CENTER_ALIGNMENT);
        addEmployeeBtn.setAlignmentX(LEFT_ALIGNMENT);
        addCustomerBtn.setAlignmentX(RIGHT_ALIGNMENT);
        btnPanel.add(addEmployeeBtn,BorderLayout.WEST);
        btnPanel.add(addCustomerBtn,BorderLayout.EAST);
        btnPanel.add(viewGroceryBtn,BorderLayout.CENTER);
        add(btnPanel, BorderLayout.CENTER);
        JPanel homePanel = new JPanel(); // Last panel for home button
        JButton backBtn = new JButton("Home");
        homePanel.add(backBtn);
        add(homePanel, BorderLayout.SOUTH);
        homePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        backBtn.addActionListener(e->{ // go back home
            home();
        });
        addCustomerBtn.addActionListener(e->{ // go to add Customer
            home();
        });
        addEmployeeBtn.addActionListener(e->{ // go to add Employee
            home();
        });
        viewGroceryBtn.addActionListener(e->{ // go to view Grocery List
            groceryList();
        });
//        addEmployeeBtn.setEnabled(true);
//        addCustomerBtn.setEnabled(true);
    }
    public void home (){
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.getContentPane();
        topFrame.setContentPane(new MainMenu());
        topFrame.setSize(500, 400);
        topFrame.setLocationRelativeTo(null);
        topFrame.revalidate();
        topFrame.repaint();
    }
    public void groceryList (){
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.getContentPane();
        topFrame.setContentPane(new GroceryList());
        topFrame.setSize(500, 400);
        topFrame.setLocationRelativeTo(null);
        topFrame.revalidate();
        topFrame.repaint();
        topFrame.dispose();
    }

}

