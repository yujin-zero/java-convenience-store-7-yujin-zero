package store.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CartItemTest {

    @Test
    @DisplayName("장바구니에서 단일 상품에 대한 총 가격을 잘 계산하는지 테스트")
    public void testGetTotalPrice() {
        Product product = new Product("콜라", 1000, 10, null);
        CartItem cartItem = new CartItem(product, 3);

        int totalPrice = cartItem.getTotalPrice();

        assertEquals(3000, totalPrice, "총 가격이 예상과 일치하지 않습니다.");
    }
}