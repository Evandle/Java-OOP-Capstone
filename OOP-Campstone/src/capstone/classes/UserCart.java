package capstone.classes;

import capstone.CustomerPage.CustomerPage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.table.AbstractTableModel;
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
        topFrame.getContentPane().removeAll();                              // Remove current panel
        topFrame.getContentPane().add(new CustomerPage(currUser));          // Add SignUpPage panel
        topFrame.setSize(450, 350);                            // Resize window for sign up
        topFrame.setLocationRelativeTo(null);                              // Center window
        topFrame.revalidate();
        topFrame.repaint();
    }

    public static class ItemTableModel extends AbstractTableModel {

        private final String[] COLUMN_NAMES = {"ID", "Name", "Price", "Stock", "Category ID"};
        private final List<Item> itemList;

        public ItemTableModel(List<Item> itemList) {
            this.itemList = itemList;
        }

        @Override
        public int getRowCount() {
            return itemList.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMN_NAMES.length;
        }

        @Override
        public String getColumnName(int column) {
            return COLUMN_NAMES[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Item item = itemList.get(rowIndex);

            return switch (columnIndex) {
                case 0 -> item.getId();
                case 1 -> item.getItemName();
                case 2 -> item.getPrice();
                case 3 -> item.getStock();
                case 4 -> item.getCategoryId();
                default -> "N/A";
            };
        }
    }

    public static class UITheme {

        public static final Color GREEN = new Color(46, 204, 113);
        public static final Color BLUE  = new Color(52, 152, 219);
        public static final Color RED   = new Color(231, 76, 60);
        public static final Color BG    = new Color(245, 248, 250);
        public static final Color TEXT  = new Color(44, 62, 80);
        public static final Color BORDER = new Color(220, 225, 230);

        public static final Font TITLE  = new Font("Segoe UI", Font.BOLD, 22);
        public static final Font HEADER = new Font("Segoe UI", Font.BOLD, 16);
        public static final Font BODY   = new Font("Segoe UI", Font.PLAIN, 14);

        public static void stylePanel(JPanel p) {
            p.setBackground(BG);
            p.setBorder(new EmptyBorder(20,20,20,20));
        }

        public static void styleTitle(JLabel l) {
            l.setFont(TITLE);
            l.setForeground(TEXT);
        }

        public static void styleLabel(JLabel l) {
            l.setFont(BODY);
            l.setForeground(TEXT);
        }

        public static void styleButton(JButton b, Color bg) {
            b.setBackground(bg);
            b.setForeground(Color.WHITE);
            b.setFont(BODY);
            b.setFocusPainted(false);
            b.setBorder(BorderFactory.createEmptyBorder(8,14,8,14));
        }

        // ===== NEW: Streamlined Rounded Button Method =====
        public static void styleRoundedButton(JButton b, Color bg) {
            styleButton(b, bg); // Apply base styles (font, color)
            b.setUI(new RoundedButtonUI()); // Apply shape
            b.setContentAreaFilled(false);
            b.setBorderPainted(false);
            b.setOpaque(false);
        }

        public static void styleDanger(JButton b) {
            styleButton(b, RED);
        }

        public static void styleTable(JTable t) {
            t.setRowHeight(28);
            t.setFont(BODY);
            t.getTableHeader().setFont(HEADER);
            t.getTableHeader().setBackground(BLUE);
            t.getTableHeader().setForeground(Color.WHITE);
        }

        // ===== NEW: Internal UI Class for Rounded Shapes =====
        public static class RoundedButtonUI extends BasicButtonUI {

            public static ComponentUI createUI(JComponent c) {
                return new RoundedButtonUI();
            }

            @Override
            public void paint(Graphics g, JComponent c) {
                AbstractButton b = (AbstractButton) c;
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color color = c.getBackground();

                // Interaction states
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
    }
}
