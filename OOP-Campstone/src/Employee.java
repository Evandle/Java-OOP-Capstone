public class Employee extends User {

    public Employee(String username, String password) {
        super(username, password);
    }

    public void setPrice(Item item, double price) {
        item.setPrice(price);
    }

    public void addItem(Item item) {

    }

    public void removeItem(Item item) {

    }

    public void changeStock(Item item, int stock) {
        item.setStock(stock);
    }

}
