package store.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

    private Product productWithPromotion;
    private Product productWithoutPromotion;

    @BeforeEach
    public void setup() {
        Promotion promotion = new Promotion("탄산2+1", 2, 1, LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(1));
        productWithPromotion = new Product("콜라", 1000, 10, promotion);
        productWithoutPromotion = new Product("사탕", 500, 15, null);
    }

    @Test
    @DisplayName("프로모션 추가 품목 계산 테스트")
    public void testCalculateAddPromotionItem() {
        int quantity = 5;
        int addPromotionItem = productWithPromotion.calculateAddPromotionItem(quantity);
        assertEquals(1, addPromotionItem, "추가 프로모션 품목 수가 예상과 다릅니다");

        quantity = 4;
        addPromotionItem = productWithPromotion.calculateAddPromotionItem(quantity);
        assertEquals(0, addPromotionItem, "추가 프로모션 품목 수가 예상과 다릅니다");

    }

    @Test
    @DisplayName("프로모션 미적용 남은 수량 계산 테스트")
    public void testCalculateNonAppliedQuantity() {
        int totalQuantity = 5;
        int nonAppliedQuantity = productWithPromotion.calculateNonAppliedQuantity(totalQuantity);
        assertEquals(2, nonAppliedQuantity, "프로모션 미적용 남은 수량이 예상과 다릅니다");

        totalQuantity = 3;
        nonAppliedQuantity = productWithPromotion.calculateNonAppliedQuantity(totalQuantity);
        assertEquals(0, nonAppliedQuantity, "프로모션 미적용 남은 수량이 예상과 다릅니다");

        totalQuantity = 7;
        nonAppliedQuantity = productWithPromotion.calculateNonAppliedQuantity(totalQuantity);
        assertEquals(1, nonAppliedQuantity, "프로모션 미적용 남은 수량이 예상과 다릅니다");
    }
}
