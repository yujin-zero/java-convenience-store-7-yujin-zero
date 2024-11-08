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

    public int calculateItemCountInCart() {
        return cart.stream().mapToInt(CartItem::getQuantity).sum();
    }

    public int calculateTotalPrice() {
        return cart.stream()
                .mapToInt(CartItem::calculateTotalPrice)
                .sum();
    }

    public int calculateTotalPromotionDiscount() {
        return cart.stream()
                .mapToInt(this::calculateDiscountForItem)
                .sum();
    }

    private int calculateDiscountForItem(CartItem item) {
        Product product = item.getProduct();
        Promotion promotion = product.getPromotion();
        int quantity = item.getQuantity();

        if (promotion != null && promotion.isApplicable(product, quantity)) {
            return promotion.calculateDiscountAmount(quantity, product.getPrice());
        }
        return 0;
    }
}
