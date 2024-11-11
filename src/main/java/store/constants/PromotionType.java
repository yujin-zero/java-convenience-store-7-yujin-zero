package store.constants;

public enum PromotionType {
    BUY_ONE_GET_ONE,
    BUY_TWO_GET_ONE,
    LIMITED_TIME_DISCOUNT;

    public static PromotionType fromName(String name) {
        switch (name) {
            case "탄산2+1":
                return BUY_TWO_GET_ONE;
            case "MD추천상품":
                return BUY_ONE_GET_ONE;
            case "반짝할인":
                return LIMITED_TIME_DISCOUNT;
            default:
                throw new IllegalArgumentException("Unknown promotion type: " + name);
        }
    }
}