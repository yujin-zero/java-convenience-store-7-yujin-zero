package store.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class InputView {

    public static Map<String, Integer> getPurchaseItems() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        String input = Console.readLine();
        return parsePurchaseInput(input);
    }

    public static boolean isMembershipDiscount() {
        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
        String input = Console.readLine();
        return input.equalsIgnoreCase("Y");
    }

    public static boolean isAdditionalPurchase() {
        System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
        String input = Console.readLine();
        return input.equalsIgnoreCase("Y");
    }

    public static boolean confirmAdditionalOption(String message) {
        System.out.print(message + " (Y/N)");
        String input = Console.readLine();
        return input.equalsIgnoreCase("Y");
    }

    public static boolean confirmAddtionPromotionItem(String productName, int productCount) {
        System.out.printf("현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)\n", productName, productCount);
        String input = Console.readLine();
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
