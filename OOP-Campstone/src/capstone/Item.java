package capstone;

public class Item {
    private final String itemName;
    private double price;
    private int stock;

    public Item(String itemName, double price, int stock) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public int getStock() {
        return stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

}
