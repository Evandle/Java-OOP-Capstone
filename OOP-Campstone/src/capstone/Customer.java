package capstone;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    private String address;
    private List<CartItem> cart = new ArrayList<>();

    public Customer(String username, String password, String address, List<CartItem> cart) {
        super(username, password);
        this.address = address;
        this.cart = cart;
    }

    public Customer(String username, String password,  String address) {
        super(username, password);
        this.address = address;
    }

    public double computeCartTotal(){
        // computes the cart's total
        double sum = 0;
        for (CartItem c : cart){
            sum += c.getItem().getPrice() * c.getQuantity();
        }
        return sum;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addToCart(Item item, int quantity) {
        for(CartItem cartItem : cart)  {
            if(cartItem.getItem() == item) {
                cartItem.addQuantity(quantity);
                return;
            }
        }
        cart.add(new CartItem(item, quantity));
    }
}
