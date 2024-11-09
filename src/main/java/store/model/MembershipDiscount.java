package store.model;

public class MembershipDiscount {
    private static final double DISCOUNT_RATE = 0.3;
    private static final int MAX_DISCOUNT_AMOUNT = 8000;

    public static int calculateDiscountAmount(int totalAmount) {
        int discount = (int) (totalAmount * DISCOUNT_RATE);
        return Math.min(discount, MAX_DISCOUNT_AMOUNT);
    }
}
