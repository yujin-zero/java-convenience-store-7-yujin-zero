package store.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> cart;

    public Cart() {
        cart = new ArrayList<>();
    }

    public void addCartItem(CartItem item) {
        cart.add(item);
    }

    public int getItemCountInCart() {
        return cart.stream().mapToInt(CartItem::getQuantity).sum();
    }
}
