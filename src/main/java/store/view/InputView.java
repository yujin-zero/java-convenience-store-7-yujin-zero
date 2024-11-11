package store.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;
import store.validator.InputValidator;

public class InputView {

    public static Map<String, Integer> getPurchaseItems() {
        while (true) {
            System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
            String input = Console.readLine();

            if (InputValidator.isValidPurchaseInput(input)) {
                return parsePurchaseInput(input);
            }

            System.out.println("잘못된 형식입니다. 올바른 형식으로 다시 입력해 주세요.");
        }
    }


    public static boolean isMembershipDiscount() {
        String input;
        while (true) {
            System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
            input = Console.readLine();
            if (InputValidator.isValidYesNoInput(input)) {
                break;
            }
            System.out.println("잘못된 입력입니다. Y 또는 N으로 다시 입력해 주세요.");
        }
        return input.equalsIgnoreCase("Y");
    }

    public static boolean isAdditionalPurchase() {
        String input;
        while (true) {
            System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
            input = Console.readLine();
            if (InputValidator.isValidYesNoInput(input)) {
                break;
            }
            System.out.println("잘못된 입력입니다. Y 또는 N으로 다시 입력해 주세요.");
        }
        return input.equalsIgnoreCase("Y");
    }

    public static boolean confirmAdditionalOption(String message) {
        String input;
        while (true) {
            System.out.print(message + " (Y/N) ");
            input = Console.readLine();
            if (InputValidator.isValidYesNoInput(input)) {
                break;
            }
            System.out.println("잘못된 입력입니다. Y 또는 N으로 다시 입력해 주세요.");
        }
        return input.equalsIgnoreCase("Y");
    }

    public static boolean confirmAddtionPromotionItem(String productName, int productCount) {
        String input;
        while (true) {
            System.out.printf("현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)\n", productName, productCount);
            input = Console.readLine();
            if (InputValidator.isValidYesNoInput(input)) {
                break;
            }
            System.out.println("잘못된 입력입니다. Y 또는 N으로 다시 입력해 주세요.");
        }
        return input.equalsIgnoreCase("Y");
    }

    public static boolean confirmStandartPriceForRemainder(String productName, int remainderQuantity) {
        String input;
        while (true) {
            System.out.printf("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)\n", productName, remainderQuantity);
            input = Console.readLine();
            if (InputValidator.isValidYesNoInput(input)) {
                break;
            }
            System.out.println("잘못된 입력입니다. Y 또는 N으로 다시 입력해 주세요.");
        }
        return input.equalsIgnoreCase("Y");
    }

    private static Map<String, Integer> parsePurchaseInput(String input) {
        Map<String, Integer> items = new LinkedHashMap<>();
        StringTokenizer pairTokenizer = new StringTokenizer(input.replaceAll("\\[|\\]", ""), ",");

        while (pairTokenizer.hasMoreTokens()) {
            String pair = pairTokenizer.nextToken().trim();
            items.put(pair.substring(0, pair.indexOf("-")).trim(),
                    Integer.parseInt(pair.substring(pair.indexOf("-") + 1).trim()));
        }

        return items;
    }

}
