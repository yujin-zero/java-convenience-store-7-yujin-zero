package store.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InputViewTest {
    @Test
    @DisplayName("상품명 및 수량이 올바르게 파싱되는지 확인하는 테스트")
    public void testGetPurchaseItems() {
        String simulatedInput = "[사이다-1],[감자-2]\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Map<String, Integer> result = InputView.getPurchaseItems();

        assertEquals(2, result.size(), "구매 항목 개수가 예상과 다릅니다");
        assertEquals(1, result.get("사이다"));
        assertEquals(2, result.get("감자"));

        System.setIn(System.in);
    }
}