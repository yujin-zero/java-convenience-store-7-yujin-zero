package store.validator;

public class InputValidator {

    public static boolean isValidYesNoInput(String input) {
        return input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("N");
    }

    public static boolean isValidPurchaseInput(String input) {
        // 입력값에서 공백을 제거합니다.
        input = input.replaceAll("\\s+", "");

        // 입력을 콤마로 분할하여 각 아이템의 형식을 검사합니다.
        String[] items = input.split(",");
        for (String item : items) {
            // 각 아이템이 []로 둘러싸여 있어야 하고, '-'로 상품명과 수량이 구분되어야 합니다.
            if (!item.startsWith("[") || !item.endsWith("]") || !item.contains("-")) {
                return false;
            }

            // 괄호를 제거하고 상품명과 수량 부분을 분리합니다.
            String content = item.substring(1, item.length() - 1);
            String[] parts = content.split("-");

            // 상품명과 수량 부분이 정확히 존재하는지 확인
            if (parts.length != 2) {
                return false;
            }

            String productName = parts[0].trim();
            String quantityStr = parts[1].trim();

            // 수량이 숫자인지 검사합니다.
            try {
                int quantity = Integer.parseInt(quantityStr);
                if (quantity <= 0) {
                    return false; // 수량이 0 이하인 경우 유효하지 않음
                }
            } catch (NumberFormatException e) {
                return false; // 숫자가 아닌 경우 유효하지 않음
            }
        }

        return true; // 모든 검사를 통과하면 유효한 입력
    }
}
