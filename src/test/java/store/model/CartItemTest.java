package store.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CartItemTest {

    @Test
    @DisplayName("장바구니에서 단일 상품에 대한 총 가격을 잘 계산하는지 테스트")
    public void testGetTotalPrice() {
        Product product = new Product("콜라", 1000, 10, null);
        CartItem cartItem = new CartItem(product, 3);

        int totalPrice = cartItem.calculateTotalPrice();

        assertEquals(3000, totalPrice, "총 가격이 예상과 일치하지 않습니다.");
    }

    @Test
    @DisplayName("프로모션 할인 금액 계산 테스트")
    public void testCalculatePromotionDiscountPrice() {
        Promotion promotion = new Promotion("탄산2+1", 2, 1, LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(1));
        Product product = new Product("콜라", 1000, 10, promotion);
        CartItem item = new CartItem(product, 5);

        int discountPrice = item.calculatePromotionDiscountPrice();
        assertEquals(1000, discountPrice);
    }

    @Test
    @DisplayName("프로모션 없을 때 할인 금액 0인지 테스트")
    public void testCalculatePromotionDiscountPrice_NoPromotion() {
        Product product = new Product("콜라", 1000, 10, null);
        CartItem item = new CartItem(product, 5);

        int discountPrice = item.calculatePromotionDiscountPrice();
        assertEquals(0, discountPrice);
    }

    @Test
    @DisplayName("프로모션 아닌 상품 금액 테스트")
    public void testCalculateNonPromotionPrice() {
        Promotion promotion = new Promotion("탄산2+1", 2, 1, LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(1));
        Product product = new Product("콜라", 1000, 10, promotion);
        CartItem item = new CartItem(product, 5);

        int nonPromotionPrice = item.calculateNonPromotionPrice();
        assertEquals(2000, nonPromotionPrice);
    }

    @Test
    @DisplayName("프로모션 없을 때 프로모션 아닌 상품 금액 테스트")
    public void testCalculateNonPromotionPrice_WithNoPromotion() {
        Product product = new Product("콜", 1000, 10, null);
        CartItem item = new CartItem(product, 5);

        int nonPromotionPrice = item.calculateNonPromotionPrice();
        assertEquals(5000, nonPromotionPrice);
    }

}