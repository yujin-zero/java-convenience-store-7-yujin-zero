package store.view;

import java.util.List;
import store.constants.OutputMessages;
import store.model.Product;
import store.model.Receipt;

public class OutputView {

    public static void printInventory(List<Product> products) {
        System.out.println(OutputMessages.GREETING);
        System.out.println(OutputMessages.INVENTORY_HEADER);
        products.forEach(product -> {
            String promotionInfo = getPromotionInfo(product);
            String stockStatus = getStockStatus(product);
            System.out.printf("- %s %,d원 %s%s\n", product.getName(), product.getPrice(), stockStatus, promotionInfo);
        });
        System.out.println();
    }

    public static void printReceipt(Receipt receipt) {
        printHeader();
        printPurchasedItems(receipt);
        printFreeItems(receipt);
        printFooter(receipt);
    }

    private static void printHeader() {
        System.out.println(OutputMessages.RECEIPT_HEADER);
        System.out.println(OutputMessages.ITEM_HEADER);
    }

    private static void printPurchasedItems(Receipt receipt) {
        receipt.getPurchasedItems().forEach((itemName, detail) -> {
            int quantity = detail.getQuantity();
            int price = detail.getPrice();
            System.out.printf("%s\t\t%d\t%,d\n", itemName, quantity, quantity * price);
        });
    }

    private static void printFreeItems(Receipt receipt) {
        System.out.println(OutputMessages.FREE_ITEM_HEADER);
        receipt.getFreeItems().forEach((item, quantity) ->
                System.out.printf("%s\t\t%d\n", item, quantity)
        );
    }

    private static void printFooter(Receipt receipt) {
        System.out.println(OutputMessages.RECEIPT_FOOTER);
        System.out.printf("%s\t\t%d\t%,d\n", OutputMessages.TOTAL_PRICE_LABEL, receipt.getTotalItemCount(),
                receipt.getTotalPrice());
        System.out.printf("%s\t\t\t-%,d\n", OutputMessages.PROMOTION_DISCOUNT_LABEL,
                receipt.getTotalPromotionDiscount());
        System.out.printf("%s\t\t\t-%,d\n", OutputMessages.MEMBERSHIP_DISCOUNT_LABEL,
                receipt.getTotalMembershipDiscount());
        System.out.printf("%s\t\t\t%,d\n", OutputMessages.FINAL_AMOUNT_LABEL, receipt.getFinalAmount());
    }

    public static void printErrorMessage(String errorMessage) {
        System.out.printf("%s %s\n", OutputMessages.ERROR_PREFIX, errorMessage);
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
        return OutputMessages.STOCK_UNAVAILABLE;
    }
}
