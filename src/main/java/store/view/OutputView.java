package store.view;

import java.util.List;
import store.model.Product;
import store.model.Receipt;

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
        System.out.println();
    }

    public static void printReceipt(Receipt receipt) {
        System.out.println("==============W 편의점================");
        System.out.println("상품명\t\t수량\t금액");
        receipt.getPurchasedItems().forEach((itemName, detail) -> {
            int quantity = detail.getQuantity();
            int price = detail.getPrice();
            System.out.printf("%s\t\t%d\t%,d\n", itemName, quantity, quantity * price);
        });

        System.out.println("=============증정 상품===============");
        receipt.getFreeItems().forEach((item, quantity) ->
                System.out.printf("%s\t\t%d\n", item, quantity)
        );

        System.out.println("====================================");
        System.out.printf("총구매액\t\t%d\t%d\n", receipt.getTotalItemCount(), receipt.getTotalPrice());
        System.out.printf("행사할인\t\t\t-%d\n", receipt.getTotalPromotionDiscount());
        System.out.printf("멤버십할인\t\t\t-%d\n", receipt.getTotalMembershipDiscount());
        System.out.printf("내실돈\t\t\t%d\n", receipt.getFinalAmount());
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
