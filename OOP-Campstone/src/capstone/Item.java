package capstone;

public class Item {
    private final String itemName;
    private double price;
    private int stock;
    private int id;
    private int categoryId;

    public Item(String itemName, double price, int stock, int categoryId) {
        this.itemName = itemName;
        this.price = price;
        this.stock = stock;
        this.categoryId = categoryId;
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

    public void setCategoryId(int categoryId){
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setId(int id) {
        this.id = id;
    }

    // ToString for testing
    public String toString(){
        return itemName+" | P"+price+" | x"+stock+" | "+categoryId;
    }

}
