package capstone;

public class Employee extends User {

    public Employee(String username, String password) {
        super(username, password);
    }

    public void setPrice(Item item, double price) {
        item.setPrice(price);
    }

    public void addItem(Item item) {
    // Added some implementations
        DbHandler.addItem(item);
    }

    public void removeItem(Item item) {

    }

    public void changeStock(Item item, int stock) {
        item.setStock(stock);
    }

    //Employees should be using this instead of changeStock to avoid
    //accidental overwriting of the entire stock and in case
    //two users concurrently change the stock

    public void addStock(Item item, int stock) {
        item.setStock(item.getStock() + stock);
    }

}
