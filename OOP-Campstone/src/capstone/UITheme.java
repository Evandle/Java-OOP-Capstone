package capstone;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UITheme {

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
}
