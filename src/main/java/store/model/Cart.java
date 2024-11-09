package store.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public int calculateFinalPrice(boolean member) {
        int totalPrice = calculateTotalPrice();
        int totalPromotionDiscount = calculateTotalPromotionDiscount();
        int totalMembershipDiscount = calculateTotalMembershiptDiscount(member);

        return totalPrice - totalPromotionDiscount - totalMembershipDiscount;
    }

    public Map<String, Integer> calculateFreeProducts() {
        Map<String, Integer> freeProducts = new HashMap<>();

        for (CartItem item : cart) {
            Map<String, Integer> freeProduct = item.freeProduct();
            freeProduct.forEach(freeProducts::put);
        }

        return freeProducts;
    }
}
