package store.controller;

import java.util.List;
import java.util.Map;
import store.model.Cart;
import store.model.CartItem;
import store.model.Product;
import store.model.Receipt;
import store.view.InputView;
import store.view.OutputView;

public class PaymentSystem {
    private final List<Product> products;
    private final Cart cart;
    private final InputView inputView;
    private final OutputView outputView;

    public PaymentSystem(List<Product> products, Cart cart, InputView inputView, OutputView outputView) {
        this.products = products;
        this.cart = cart;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void start() {
        outputView.printWelcomeMessage();
        run();
    }

    private void run() {
        while (true) {
            outputView.printInventory(products);
            processPurchase(inputView.getPurchaseItems(), inputView.isMembershipDiscount());
            if (!inputView.isAdditionalPurchase()) {
                break;
            }
        }
    }

    private void processPurchase(Map<String, Integer> purchasedItems, boolean isMember) {
        purchasedItems.forEach((productName, quantity) -> addProductToCart(productName, quantity));
        outputView.printReceipt(generateReceipt(isMember));
    }

    private void addProductToCart(String productName, int quantity) {
        Product product = findProductByName(productName);
        if (!isValidProduct(product, quantity)) {
            outputView.printErrorMessage("재고가 부족하거나 잘못된 상품명입니다.");
            return;
        }
        cart.addCartItem(new CartItem(product, quantity));
        product.reduceQuantity(quantity);
    }

    private boolean isValidProduct(Product product, int quantity) {
        return product != null && product.getQuantity() >= quantity;
    }

    private Product findProductByName(String productName) {
        return products.stream()
                .filter(product -> product.getName().equalsIgnoreCase(productName))
                .findFirst()
                .orElse(null);
    }

    private Receipt generateReceipt(boolean isMember) {
        return new Receipt(
                cart.productCountInCart(),
                cart.calculateTotalPrice(),
                cart.calculateTotalPromotionDiscount(),
                cart.calculateTotalMembershiptDiscount(isMember),
                cart.calculateFinalPrice(isMember),
                cart.calculateFreeProducts()
        );
    }
}
