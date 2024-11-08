package store.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CartTest {

//    외부에서 검증 예정
//    @Test
//    @DisplayName("장바구니에 아이템이 정상적으로 추가되는지 테스트")
//    public void testAddCartItem() {
//        Product product = new Product("사과", 1000, 10, null);
//        CartItem cartItem = new CartItem(product, 2);
//
//        Cart cart = new Cart();
//        cart.addCartItem(cartItem);
//
//        List<CartItem> cartItems = cart.getCartItems();
//        assertEquals(1, cartItems.size(), "장바구니에 아이템이 하나 추가되어야 합니다.");
//        assertEquals(cartItem, cartItems.get(0), "추가된 아이템이 CartItem과 일치해야 합니다.");
//
//    }

    @Test
    @DisplayName("장바구니 전체 수량 계산 테스트")
    public void testCalculateItemCountInCart() {
        Product product1 = new Product("사탕", 500, 10, null);
        Product product2 = new Product("초콜릿", 1000, 10, null);
        CartItem cartItem1 = new CartItem(product1, 2);
        CartItem cartItem2 = new CartItem(product2, 3);

        Cart cart = new Cart();
        cart.addCartItem(cartItem1);
        cart.addCartItem(cartItem2);

        int totalCount = cart.calculateItemCountInCart();
        assertEquals(5, totalCount, "장바구니의 전체 수량이 예상과 다릅니다");
    }

}