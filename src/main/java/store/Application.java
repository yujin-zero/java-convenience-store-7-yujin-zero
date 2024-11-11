package store;

import java.util.List;
import store.controller.PaymentSystem;
import store.model.Cart;
import store.model.Product;
import store.model.Promotion;
import store.service.ProductLoader;
import store.service.PromotionLoader;
import store.view.InputView;
import store.view.OutputView;

public class Application {

    private static final String PROMOTIONS_FILE_PATH = "src/main/resources/promotions.md";
    private static final String PRODUCTS_FILE_PATH = "src/main/resources/products.md";


    public static void main(String[] args) {
        PromotionLoader promotionLoader = new PromotionLoader();
        List<Promotion> promotions = promotionLoader.loadPromotions(PROMOTIONS_FILE_PATH);

        ProductLoader productLoader = new ProductLoader(promotions);
        List<Product> products = productLoader.loadProduct(PRODUCTS_FILE_PATH);

        Cart cart = new Cart();

        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        PaymentSystem paymentSystem = new PaymentSystem(products, cart, inputView, outputView);

        paymentSystem.start();
    }
}
