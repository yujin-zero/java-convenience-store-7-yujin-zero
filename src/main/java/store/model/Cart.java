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
                .mapToInt(CartItem::calculatePromotionDiscountPrice)
                .sum();
    }

    public int calculateTotalMembershiptDiscount(boolean member) {
        if (!member) {
            return 0;
        }
        int nonPromotionPrice = calculateTotalNonPromotionAmount();
        int membershiptDiscountPrice = MembershipDiscount.calculateDiscountAmount(nonPromotionPrice);

        return membershiptDiscountPrice;
    }

    private int calculateTotalNonPromotionAmount() {
        return cart.stream()
                .mapToInt(CartItem::calculateNonPromotionPrice)
                .sum();
    }
}
