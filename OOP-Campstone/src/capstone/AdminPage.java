package capstone;
import capstone.GroceryList.GroceryList;
import capstone.MainMenu.MainMenu;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
public class AdminPage extends JPanel {
    Admin admin = new Admin();
    public AdminPage (Admin admin){
//        this.setBackground(Color.DARK_GRAY);
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(485, 590));
        setBorder(new EmptyBorder(50, 50, 20, 50));
        GridBagConstraints gbc = new GridBagConstraints();
//        ImageIcon adminImage = new ImageIcon("");

        //FOR LABEL
        JLabel adminLabel = new JLabel("\uD83D\uDD12 ADMIN");
        adminLabel.setFont(new Font("SansSerif", Font.BOLD, 22));   // Set font style
        adminLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 130, 0);
        add(adminLabel,gbc);


        JButton addEmployeeBtn = new JButton("Add Employee");
        JButton addCustomerBtn = new JButton("Add Customer");
        JButton removeUserBtn = new JButton("Remove User");
        JButton viewGroceryBtn = new JButton("View Grocery");
        JButton backBtn = new JButton("Home");


        //for Buttons Generally
        Dimension btnSize = new Dimension(0, 30);
        addEmployeeBtn.setPreferredSize(btnSize);
        addCustomerBtn.setPreferredSize(btnSize);
        removeUserBtn.setPreferredSize(btnSize);
        viewGroceryBtn.setPreferredSize(btnSize);
        backBtn.setPreferredSize(btnSize);

        addCustomerBtn.setFocusable(false);
        addEmployeeBtn.setFocusable(false);
        removeUserBtn.setFocusable(false);
        viewGroceryBtn.setFocusable(false);
        backBtn.setFocusable(false);


        // --- FIXED LAYOUT LOGIC ---
        // We will stack them vertically.
        // gridx is ALWAYS 0.
        // gridy increases by 1 for each button.

        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Stretch full width
        gbc.insets = new Insets(1, 0, 2, 0); // Gap between buttons

        //addCustomer
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.weightx = 1.0;
        add (addCustomerBtn,gbc);

        //addEmployee
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.weightx = 1.0;
        add (addEmployeeBtn,gbc);

        //removeUser
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(40, 0, 10, 0);
        add (removeUserBtn,gbc);

        //viewGrocery
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(100, 0, 0, 0);
        add (viewGroceryBtn,gbc);

        //home
        gbc.gridx = 0;
        gbc.gridy = 5;

        // This is the "Spring" trick:
        gbc.weighty = 1.0; // Consumes all remaining vertical space
        gbc.anchor = GridBagConstraints.PAGE_END; // Pushes button to bottom
        gbc.insets = new Insets(0, 0, 50, 0);
        add(backBtn, gbc);
//        JPanel panel1 = new JPanel(); // Panel for Title
//        panel1.setLayout(new BorderLayout());
//        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS)); // Arrange labels vertically
//        panel1.setBorder(BorderFactory.createEmptyBorder(20, 0, 15, 0)); // Top and bottom padding
//        adminLabel.setAlignmentX(Component.CENTER_ALIGNMENT);   // Center horizontally
//        panel1.add(Box.createRigidArea(new Dimension(0, 37))); // Top gap
//        panel1.add(adminLabel);
//        add(panel1, BorderLayout.NORTH);
//        panel1.add(adminLabel, BorderLayout.CENTER);

//        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Panel for add employee and add customer Buttons

//
//        viewGroceryBtn.setAlignmentX(CENTER_ALIGNMENT);
//        addEmployeeBtn.setAlignmentX(LEFT_ALIGNMENT);
//        addCustomerBtn.setAlignmentX(RIGHT_ALIGNMENT);
//        removeUserBtn.setAlignmentX(CENTER_ALIGNMENT);
//        addCustomerBtn.setFocusPainted(false);

//        btnPanel.add(addCustomerBtn,BorderLayout.EAST);
//        btnPanel.add(addEmployeeBtn,BorderLayout.WEST);
//        btnPanel.add(removeUserBtn, BorderLayout.SOUTH);
//        btnPanel.add(viewGroceryBtn,BorderLayout.CENTER);
//        add(btnPanel, BorderLayout.CENTER);

//        JPanel homePanel = new JPanel(); // Last panel for home button
//
//        homePanel.add(backBtn);
//        add(homePanel, BorderLayout.SOUTH);
//        homePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        backBtn.addActionListener(e->{ // go back home
            home();
        });
        addCustomerBtn.addActionListener(e->{ // go to add Customer
            new AddCustomer1();
            cleaner();
        });
        addEmployeeBtn.addActionListener(e->{ // go to add Employee
            new AddEmployee1();
            cleaner();
        });
        viewGroceryBtn.addActionListener(e->{ // go to view Grocery List
            new GroceryList();
            cleaner();
        });
        removeUserBtn.addActionListener(e->{
            new RemoveUser();
            cleaner();
        });
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
    public void cleaner (){
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.getContentPane();
        topFrame.setSize(500, 400);
        topFrame.setLocationRelativeTo(null);
        topFrame.revalidate();
        topFrame.repaint();
        topFrame.dispose();
    }

}
