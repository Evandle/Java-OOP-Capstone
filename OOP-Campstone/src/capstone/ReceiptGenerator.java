package capstone;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReceiptGenerator {

    private static final String FOLDER_NAME = "Receipts";

    public static void printReceipt(ArrayList<CartItem> cart, String username) {
        File directory = new File(FOLDER_NAME);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                System.out.println("Created new folder: " + FOLDER_NAME);
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename = "Receipt_" + username + "_" + timeStamp + ".txt";

        File receiptFile = new File(directory, filename);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(receiptFile))) {

            bw.write("=========================================");
            bw.newLine();
            bw.write("              STORE RECEIPT              ");
            bw.newLine();
            bw.write("=========================================");
            bw.newLine();
            bw.write("Customer: " + username);
            bw.newLine();
            bw.write("Date:     " + new Date());
            bw.newLine();
            bw.write("-----------------------------------------");
            bw.newLine();

            double grandTotal = 0;

            bw.write(String.format("%-20s %5s %10s", "Item", "Qty", "Subtotal"));
            bw.newLine();
            bw.write("-----------------------------------------");
            bw.newLine();

            for (CartItem c : cart) {
                String name = c.getItem().getItemName();
                if (name.length() > 18) {
                    name = name.substring(0, 15) + "...";
                }

                int qty = c.getQuantity();
                double subtotal = c.getTotal();
                grandTotal += subtotal;

                String line = String.format("%-20s %5d %10.2f", name, qty, subtotal);
                bw.write(line);
                bw.newLine();
            }

            bw.write("-----------------------------------------");
            bw.newLine();
            bw.write(String.format("GRAND TOTAL:              P %10.2f", grandTotal));
            bw.newLine();
            bw.write("=========================================");
            bw.newLine();
            bw.write("       Thank you for your purchase!      ");
            bw.newLine();
            bw.write("=========================================");

            System.out.println("Receipt saved to: " + receiptFile.getAbsolutePath());

        } catch (IOException e) {
            System.err.println("Error printing receipt: " + e.getMessage());
            e.printStackTrace();
        }
    }
}