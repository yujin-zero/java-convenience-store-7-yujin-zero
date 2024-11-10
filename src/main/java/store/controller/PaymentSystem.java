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
            processPurchase(inputView.getPurchaseItems());
            if (!inputView.isAdditionalPurchase()) {
                break;
            }
        }
    }

    private void processPurchase(Map<String, Integer> purchasedItems) {
        purchasedItems.forEach((productName, quantity) -> addProductToCart(productName, quantity));

        boolean isMember = inputView.isMembershipDiscount();
        outputView.printReceipt(generateReceipt(isMember));
    }

    private void addProductToCart(String productName, int quantity) {
        int promoQuantity = addPromoProductToCart(productName, quantity);

        addGeneralProductToCart(productName, quantity - promoQuantity);
    }

    private int addPromoProductToCart(String productName, int quantity) {
        Product promoProduct = findProductByNameAndPromotionStatus(productName, true);
        int promoQuantity = (promoProduct != null) ? Math.min(quantity, promoProduct.getQuantity()) : 0;

        if (promoQuantity > 0 && (quantity - promoQuantity != 0)) {
            return addPromoItemsDirectly(promoProduct, promoQuantity);
        }

        if (promoQuantity > 0) {
            processFullyPromoProduct(promoProduct, productName, quantity, promoQuantity);
        }
        return promoQuantity;
    }

    private int addPromoItemsDirectly(Product promoProduct, int promoQuantity) {
        cart.addCartItem(new CartItem(promoProduct, promoQuantity));
        promoProduct.reduceQuantity(promoQuantity);
        return promoQuantity;
    }

    private void processFullyPromoProduct(Product promoProduct, String productName, int quantity, int promoQuantity) {
        int addPromoItemCount = promoProduct.calculateAddPromotionItem(quantity);

        if (addPromoItemCount > 0 && inputView.confirmAddtionPromotionItem(productName, addPromoItemCount)) {
            addCombinedPromoItems(promoProduct, promoQuantity + addPromoItemCount);
        } else {
            addOnlyPromoItems(promoProduct, promoQuantity);
        }
    }

    private void addCombinedPromoItems(Product promoProduct, int totalQuantity) {
        cart.addCartItem(new CartItem(promoProduct, totalQuantity));
        promoProduct.reduceQuantity(totalQuantity);
    }

    private void addOnlyPromoItems(Product promoProduct, int promoQuantity) {
        cart.addCartItem(new CartItem(promoProduct, promoQuantity));
        promoProduct.reduceQuantity(promoQuantity);
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
            outputView.printErrorMessage("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
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
