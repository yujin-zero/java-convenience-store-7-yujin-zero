package store.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CartTest {

    private Cart cart;

    @BeforeEach
    public void setup() {
        cart = new Cart();
        Promotion promotion = new Promotion("탄산2+1", 2, 1, LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(1));
        Product product1 = new Product("콜라", 1000, 10, promotion);
        Product product2 = new Product("사탕", 200, 10, null);
        cart.addCartItem(new CartItem(product1, 3));
        cart.addCartItem(new CartItem(product2, 2));
    }

    @Test
    @DisplayName("장바구니 전체 수량 계산 테스트")
    public void testCalculateItemCountInCart() {
        int totalCount = cart.calculateItemCountInCart();
        assertEquals(5, totalCount, "장바구니의 전체 수량이 예상과 다릅니다");
    }

    @Test
    @DisplayName("총 금액 계산 테스트")
    public void testCalculateTotalPrice() {
        int totalPrice = cart.calculateTotalPrice();
        assertEquals(3400, totalPrice);
    }

    @Test
    @DisplayName("총 프로모션 적용 할인 금액 테스트")
    public void testCalculateTotalPromotionDiscount() {
        int totalPromotionDiscount = cart.calculateTotalPromotionDiscount();
        assertEquals(1000, totalPromotionDiscount);
    }

    @Test
    @DisplayName("총 멤버십 적용 할인 금액 테스트")
    public void testCalculateTotalMembershipDiscount() {
        int totalMembershipDiscount = cart.calculateTotalMembershiptDiscount(true);
        assertEquals(400 * 0.3, totalMembershipDiscount);
    }

    @Test
    @DisplayName("멤버십 적용 안했을 때 할인 금액 테스트")
    public void testCalculateTotalMembershipDiscount_NoMember() {
        int totalMembershipDiscount = cart.calculateTotalMembershiptDiscount(false);
        assertEquals(0, totalMembershipDiscount);
    }
}