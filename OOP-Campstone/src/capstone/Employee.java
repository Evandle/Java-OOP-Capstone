package capstone;

public class Employee extends User {

    public Employee(String username, String password) {
        super(username, password);
    }
    public Employee(int id, String username, String password) {
        super(id, username, password);
    }

    public void setPrice(Item item, double price) {
        item.setPrice(price);
    }

    public void addItem(Item item, String name, double price, int stock, int categoryId) {
        Item newItem = new Item(name, price, stock, categoryId);

        boolean success = DbHandler.addItem(newItem);

        if(success)
            System.out.println("Item added successfully!");
    }

    public void removeItem(Item item) {
        boolean success = DbHandler.deleteItem(item.getId());
        if(success)
            System.out.println("Item deleted successfully!");
    }

    public void setStock(Item item, int stock) {
        boolean success = DbHandler.setStock(item.getId(), stock);
        if(success)
            System.out.println("Stock set successfully!");
    }

    // Employees should be using addStock by default instead of setStock to
    // avoid accidental overwriting of the entire stock
    // and in case two users concurrently change the stock

    public void addStock(Item item, int stock) {
        boolean success = DbHandler.updateStock(item.getId(), stock);
        if(success)
            System.out.println("Stock added successfully!");
    }

}
