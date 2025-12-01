package capstone;

import java.io.*;

public class CartItem {
    private final Item item;
    private int quantity;

    public CartItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public double getTotal() {
        return item.getPrice() * quantity;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    //Partial receipt class
    public static class Receipt extends CartItem{
        public Receipt(Item item, int quantity) {
           super(item, quantity);

            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("Receipt.txt"));
                bw.write(super.item.getItemName() + "-" + quantity + "-" + super.item.getPrice());
                bw.newLine();
                bw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
