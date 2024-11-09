package store.model;

import java.util.Collections;
import java.util.Map;

public class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public int calculateTotalPrice() {
        return product.getPrice() * quantity;
    }

    public int calculatePromotionDiscountPrice() {
        if (product.getPromotion() == null || !product.getPromotion().isApplicable(quantity)) {
            return 0;
        }
        int promotionDiscountAmount = product.getPromotion().calculateDiscountAmount(quantity, product.getPrice());
        return promotionDiscountAmount;
    }

    public int calculateNonPromotionPrice() {
        if (product.getPromotion() == null || !product.getPromotion().isApplicable(quantity)) {
            return product.getPrice() * quantity;
        }
        int promotionAppliedItemCount = product.getPromotion().calculateAppliedItemCount(quantity);
        int remainItemCount = quantity - promotionAppliedItemCount;

        return remainItemCount * product.getPrice();
    }

    public Map<String, Integer> freeProduct() {
        String freeProductName = product.getName();
        int freeProductCount = product.getPromotion().calculateFreeItems(quantity);

        if (freeProductCount > 0) {
            return Collections.singletonMap(freeProductName, freeProductCount);
        }
        return Collections.emptyMap();
    }

    public int getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }
}
