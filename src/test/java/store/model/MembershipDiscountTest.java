package store.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MembershipDiscountTest {

    @Test
    @DisplayName("할인 금액 계산 - 최대 할인 금액 이하")
    public void testCalculateDiscountAmount_UnderMaxDiscount() {
        int totalAmount = 10000;
        int discountAmount = MembershipDiscount.calculateDiscountAmount(totalAmount);
        assertEquals(3000, discountAmount, "할인 금액이 예상과 다릅니다");
    }

    @Test
    @DisplayName("할인 금액 계산 - 최대 할인 금액 초과")
    public void testCalculateDiscountAmount_ExceedMaxDiscount() {
        int totalAmount = 50000;
        int discountAmount = MembershipDiscount.calculateDiscountAmount(totalAmount);
        assertEquals(8000, discountAmount, "할인 금액이 최대 할인 금액을 초과하지 않아야 합니다");
    }

    @Test
    @DisplayName("할인 금액 계산 - 총 금액이 0인 경우")
    public void testCalculateDiscountAmount_ZeroTotalAmount() {
        int totalAmount = 0;
        int discountAmount = MembershipDiscount.calculateDiscountAmount(totalAmount);
        assertEquals(0, discountAmount, "총 금액이 0일 때 할인 금액은 0이어야 합니다");
    }
}
