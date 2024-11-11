package store.validator;

public class InputValidator {

    public static boolean isValidYesNoInput(String input) {
        return input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("N");
    }

    public static boolean isValidPurchaseInput(String input) {
        input = input.replaceAll("\\s+", "");
        String[] items = input.split(",");

        for (String item : items) {
            if (!isValidItemFormat(item) || !isValidItemContent(item)) {
                return false;
            }
        }

        return true;
    }

    private static boolean isValidItemFormat(String item) {
        return item.startsWith("[") && item.endsWith("]") && item.contains("-");
    }

    private static boolean isValidItemContent(String item) {
        String content = item.substring(1, item.length() - 1);
        String[] parts = content.split("-");
        return parts.length == 2 && isValidQuantity(parts[1].trim());
    }

    private static boolean isValidQuantity(String quantityStr) {
        try {
            int quantity = Integer.parseInt(quantityStr);
            return quantity > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
