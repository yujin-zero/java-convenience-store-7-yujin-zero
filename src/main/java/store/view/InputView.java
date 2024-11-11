package store.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;
import store.constants.ErrorMessages;
import store.constants.PromptMessages;
import store.validator.InputValidator;

public class InputView {

    public static Map<String, Integer> getPurchaseItems() {
        while (true) {
            System.out.println(PromptMessages.ENTER_PURCHASE_ITEMS);
            String input = Console.readLine();

            if (InputValidator.isValidPurchaseInput(input)) {
                return parsePurchaseInput(input);
            }

            System.out.println(ErrorMessages.INVALID_FORMAT);
        }
    }


    public static boolean isMembershipDiscount() {
        String input;
        while (true) {
            System.out.println(PromptMessages.MEMBERSHIP_DISCOUNT);
            input = Console.readLine();
            if (InputValidator.isValidYesNoInput(input)) {
                break;
            }
            System.out.println(ErrorMessages.INVALID_YES_NO);
        }
        return input.equalsIgnoreCase("Y");
    }

    public static boolean isAdditionalPurchase() {
        String input;
        while (true) {
            System.out.println(PromptMessages.ADDITIONAL_PURCHASE);
            input = Console.readLine();
            if (InputValidator.isValidYesNoInput(input)) {
                break;
            }
            System.out.println(ErrorMessages.INVALID_YES_NO);
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
            System.out.println(ErrorMessages.INVALID_YES_NO);
        }
        return input.equalsIgnoreCase("Y");
    }

    public static boolean confirmAddtionPromotionItem(String productName, int productCount) {
        String input;
        while (true) {
            System.out.printf(PromptMessages.ADD_PROMOTION_ITEM, productName, productCount);
            input = Console.readLine();
            if (InputValidator.isValidYesNoInput(input)) {
                break;
            }
            System.out.println(ErrorMessages.INVALID_YES_NO);
        }
        return input.equalsIgnoreCase("Y");
    }

    public static boolean confirmStandartPriceForRemainder(String productName, int remainderQuantity) {
        String input;
        while (true) {
            System.out.printf(PromptMessages.STANDARD_PRICE_FOR_REMAINDER, productName, remainderQuantity);
            input = Console.readLine();
            if (InputValidator.isValidYesNoInput(input)) {
                break;
            }
            System.out.println(ErrorMessages.INVALID_YES_NO);
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
