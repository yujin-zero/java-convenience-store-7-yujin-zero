package store.constants;

import java.util.HashMap;
import java.util.Map;

public enum PromotionType {
    BUY_TWO_GET_ONE,
    BUY_ONE_GET_ONE,
    LIMITED_TIME_DISCOUNT;

    private static final Map<String, PromotionType> NAME_TO_TYPE_MAP = new HashMap<>();

    static {
        NAME_TO_TYPE_MAP.put("탄산2+1", BUY_TWO_GET_ONE);
        NAME_TO_TYPE_MAP.put("MD추천상품", BUY_ONE_GET_ONE);
        NAME_TO_TYPE_MAP.put("반짝할인", LIMITED_TIME_DISCOUNT);
    }

    public static PromotionType fromName(String name) {
        PromotionType type = NAME_TO_TYPE_MAP.get(name);
        if (type == null) {
            throw new IllegalArgumentException("Unknown promotion type: " + name);
        }
        return type;
    }
}
