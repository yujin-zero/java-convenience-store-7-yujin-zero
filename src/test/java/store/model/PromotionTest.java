package store.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PromotionTest {

    @Test
    @DisplayName("프로모션 적용 가능 여부 확인")
    public void testIsApplicable() {
        Promotion promotion = new Promotion("탄산2+1", 3, 1, LocalDate.of(2024, 11, 1).atStartOfDay(),
                LocalDate.of(2024, 12, 1).atStartOfDay());
        Product product = new Product("콜라", 1000, 10, promotion);

        boolean applicable = promotion.isApplicable(product, 5);
        assertTrue(applicable, "프로모션 적용이 가능해야 합니다.");

        Promotion expiredPromotion = new Promotion("탄산2+1", 2, 1,
                LocalDate.of(2023, 11, 1).atStartOfDay(), LocalDate.of(2023, 12, 1).atStartOfDay());
        boolean notApplicable = expiredPromotion.isApplicable(product, 5);
        assertEquals(false, notApplicable, "프로모션 적용이 불가능해야 합니다.");

        boolean insufficientQuantity = promotion.isApplicable(product, 2);
        assertEquals(false, insufficientQuantity, "구매 수량이 부족한 경우 프로모션 적용이 불가능해야 합니다.");
    }

    @Test
    @DisplayName("프로모션 할인 금액 계산")
    public void testCalculateDiscountAmount() {
        Promotion promotion = new Promotion("탄산2+1", 2, 1,
                LocalDate.of(2024, 11, 1).atStartOfDay(), LocalDate.of(2024, 12, 1).atStartOfDay());
        int discountAmount = promotion.calculateDiscountAmount(7, 1000);

        assertEquals(2000, discountAmount, "할인 금액이 예상과 일치하지 않습니다.");
    }

    @Test
    @DisplayName("증정 상품 수량 계산")
    public void testCalculateFreeItems() {
        Promotion promotion = new Promotion("탄산2+1", 2, 1,
                LocalDate.of(2024, 11, 1).atStartOfDay(), LocalDate.of(2024, 12, 1).atStartOfDay());
        int freeItems = promotion.calculateFreeItems(7);

        assertEquals(2, freeItems, "증정 상품 수량이 예상과 일치하지 않습니다.");
    }
}