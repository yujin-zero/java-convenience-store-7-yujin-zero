package store.view;

import java.util.List;
import store.model.Product;

public class OutputView {

    public static void printWelcomeMessage() {
        System.out.println("안녕하세요. W편의점입니다.");
    }

    public static void printInventory(List<Product> products) {
        System.out.println("현재 보유하고 있는 상품입니다.\n");
        products.forEach(product -> {
            String promotionInfo = getPromotionInfo(product);
            String stockStatus = getStockStatus(product);
            System.out.printf("- %s %d원 %s %s\n", product.getName(), product.getPrice(), stockStatus, promotionInfo);
        });
    }

    public static void printErrorMessage(String errorMessage) {
        System.out.printf("[ERROR] %s\n", errorMessage);
    }

    private static String getPromotionInfo(Product product) {
        if (product.getPromotion() != null) {
            return " " + product.getPromotion().getName();
        }
        return "";
    }

    private static String getStockStatus(Product product) {
        if (product.getQuantity() > 0) {
            return product.getQuantity() + "개";
        }
        return "재고 없음";
    }

}
