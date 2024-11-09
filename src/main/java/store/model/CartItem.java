package store.model;

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

    public int getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }
}
