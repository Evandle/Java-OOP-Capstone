package capstone;

public class CartItem {
    private final Item item;
    private int quantity;

    public CartItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public CartItem(int id, String name, double price, int quantity) {
        this.item = new Item(name, price, 0, 0);
        this.item.setId(id);
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


}
