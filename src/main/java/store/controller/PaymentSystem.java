package store.controller;

import java.util.List;
import java.util.Map;
import store.model.Cart;
import store.model.CartItem;
import store.model.ItemDetail;
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
        int promoQuantity = addPromoProductToCart(productName, quantity);
        addGeneralProductToCart(productName, quantity - promoQuantity);
    }

    private int addPromoProductToCart(String productName, int quantity) {
        Product promoProduct = findProductByNameAndPromotionStatus(productName, true);
        int promoQuantity = (promoProduct != null) ? Math.min(quantity, promoProduct.getQuantity()) : 0;
        if (promoQuantity > 0) {
            cart.addCartItem(new CartItem(promoProduct, promoQuantity));
            promoProduct.reduceQuantity(promoQuantity);
        }
        return promoQuantity;
    }

    private void addGeneralProductToCart(String productName, int remainingQuantity) {
        if (remainingQuantity <= 0) {
            return;
        }

        Product generalProduct = findProductByNameAndPromotionStatus(productName, false);
        if (isValidProduct(generalProduct, remainingQuantity)) {
            cart.addCartItem(new CartItem(generalProduct, remainingQuantity));
            generalProduct.reduceQuantity(remainingQuantity);
        } else {
            outputView.printErrorMessage("재고가 부족하거나 잘못된 상품명입니다.");
        }
    }

    private Product findProductByNameAndPromotionStatus(String productName, boolean isPromotion) {
        return products.stream()
                .filter(product -> product.getName().equalsIgnoreCase(productName)
                        && ((isPromotion && product.getPromotion() != null)
                        || (!isPromotion && product.getPromotion() == null)))
                .findFirst()
                .orElse(null);
    }

    private boolean isValidProduct(Product product, int quantity) {
        return product != null && product.getQuantity() >= quantity;
    }

    private Receipt generateReceipt(boolean isMember) {
        Map<String, ItemDetail> combinedItems = cart.getCombinedItemDetails();
        return new Receipt(
                combinedItems,
                cart.calculateTotalPrice(),
                cart.calculateTotalPromotionDiscount(),
                cart.calculateTotalMembershiptDiscount(isMember),
                cart.calculateFinalPrice(isMember),
                cart.calculateItemCountInCart(),
                cart.calculateFreeProducts()
        );
    }
}
