package store.model;

import java.util.Map;

public class Receipt {
    private final Map<Product, Integer> purchasedItems;
    private final int totalPrice;
    private final int totalPromotionDiscount;
    private final int totalMembershipDiscount;
    private final int finalAmount;
    private final int totalItemCount;
    private final Map<String, Integer> freeItems;

    public Receipt(Map<Product, Integer> purchasedItems, int totalPrice, int totalPromotionDiscount,
                   int totalMembershipDiscount, int finalAmount, int totalItemCount, Map<String, Integer> freeItems) {
        this.purchasedItems = purchasedItems;
        this.totalPrice = totalPrice;
        this.totalPromotionDiscount = totalPromotionDiscount;
        this.totalMembershipDiscount = totalMembershipDiscount;
        this.finalAmount = finalAmount;
        this.totalItemCount = totalItemCount;
        this.freeItems = freeItems;
    }

    public Map<Product, Integer> getPurchasedItems() {
        return purchasedItems;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getTotalPromotionDiscount() {
        return totalPromotionDiscount;
    }

    public int getTotalMembershipDiscount() {
        return totalMembershipDiscount;
    }

    public int getFinalAmount() {
        return finalAmount;
    }

    public Map<String, Integer> getFreeItems() {
        return freeItems;
    }

    public Object getTotalItemCount() {
        return totalItemCount;
    }
}
