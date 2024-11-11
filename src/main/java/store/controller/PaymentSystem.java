package store.controller;

import java.util.List;
import java.util.Map;
import store.constants.ErrorMessages;
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
        cart.clear();
        purchasedItems.forEach((productName, quantity) -> addProductToCart(productName, quantity));

        boolean isMember = inputView.isMembershipDiscount();
        outputView.printReceipt(generateReceipt(isMember));
    }

    private void addProductToCart(String productName, int quantity) {
        Product promoProduct = findProductByNameAndPromotionStatus(productName, true);
        Product generalProduct = findProductByNameAndPromotionStatus(productName, false);

        if (isQuantityExceedsAvailableStock(quantity, promoProduct, generalProduct)) {
            outputView.printErrorMessage(ErrorMessages.EXCEEDS_STOCK);
            return;
        }

        int promoQuantity = calculateApplicablePromoQuantity(quantity, promoProduct);
        processProductAddition(productName, quantity, promoQuantity, promoProduct);
    }

    private boolean isQuantityExceedsAvailableStock(int quantity, Product promoProduct, Product generalProduct) {
        int promoProductQuantity = 0;
        int generalProductQuantity = 0;

        if (promoProduct != null) {
            promoProductQuantity = promoProduct.getQuantity();
        }
        if (generalProduct != null) {
            generalProductQuantity = generalProduct.getQuantity();
        }
        return quantity > promoProductQuantity + generalProductQuantity;
    }

    private int calculateApplicablePromoQuantity(int quantity, Product promoProduct) {
        if (promoProduct != null && promoProduct.getPromotion().isApplicable(quantity)) {
            return Math.min(quantity, promoProduct.getQuantity());
        }
        return 0;
    }

    private void processProductAddition(String productName, int quantity, int promoQuantity, Product promoProduct) {
        if (promoQuantity == 0) {
            addGeneralProductToCart(productName, quantity);
            return;
        }

        int nonAppliedPromoQuantity = promoProduct.calculateNonAppliedQuantity(promoQuantity);
        handleRemainingQuantity(productName, quantity, promoQuantity, nonAppliedPromoQuantity);
    }


    private void handleRemainingQuantity(String productName, int quantity, int promoQuantity,
                                         int nonAppliedPromoQuantity) {
        int remainingQuantity = quantity - (promoQuantity - nonAppliedPromoQuantity);

        if (remainingQuantity > 0 && findProductByNameAndPromotionStatus(productName, true).getQuantity() < quantity
                && inputView.confirmStandartPriceForRemainder(productName, remainingQuantity)) {
            int pQuantity = addPromoProductToCart(productName, quantity);
            addGeneralProductToCart(productName, quantity - pQuantity);
            return;
        }

        addPromoProductToCart(productName, promoQuantity - nonAppliedPromoQuantity);
        addGeneralProductToCart(productName, quantity - promoQuantity);
    }


    private int addPromoProductToCart(String productName, int quantity) {
        Product promoProduct = findProductByNameAndPromotionStatus(productName, true);
        int promoQuantity = 0;
        if (promoProduct != null) {
            promoQuantity = Math.min(quantity, promoProduct.getQuantity());
        }

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
            return;
        }

        addOnlyPromoItems(promoProduct, promoQuantity);
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
