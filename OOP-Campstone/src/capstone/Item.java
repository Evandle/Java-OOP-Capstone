package capstone;

public class Item {
    private final String itemName;
    private double price;
    private int stock;
    private int id;
    public Item(String itemName, double price, int stock) {
        this.itemName = itemName;
        this.price = price;
        this.stock = stock;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
