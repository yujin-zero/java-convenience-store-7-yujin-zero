package store.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InputValidatorTest {

    @Test
    @DisplayName("Yes/No 입력 값 검증 테스트")
    public void testIsValidYesNoInput() {
        assertTrue(InputValidator.isValidYesNoInput("Y"), "'Y'는 유효한 Yes/No 입력이어야 합니다.");
        assertTrue(InputValidator.isValidYesNoInput("N"), "'N'는 유효한 Yes/No 입력이어야 합니다.");
        assertTrue(InputValidator.isValidYesNoInput("y"), "'y'는 유효한 Yes/No 입력이어야 합니다.");
        assertTrue(InputValidator.isValidYesNoInput("n"), "'n'는 유효한 Yes/No 입력이어야 합니다.");

        assertFalse(InputValidator.isValidYesNoInput("yes"), "'yes'는 유효하지 않은 Yes/No 입력이어야 합니다.");
        assertFalse(InputValidator.isValidYesNoInput("no"), "'no'는 유효하지 않은 Yes/No 입력이어야 합니다.");
        assertFalse(InputValidator.isValidYesNoInput("A"), "'A'는 유효하지 않은 Yes/No 입력이어야 합니다.");
        assertFalse(InputValidator.isValidYesNoInput(" "), "공백은 유효하지 않은 Yes/No 입력이어야 합니다.");
    }

    @Test
    @DisplayName("구매 입력 값 형식 및 내용 검증 테스트")
    public void testIsValidPurchaseInput() {
        assertTrue(InputValidator.isValidPurchaseInput("[사과-2]"), "[사과-2]는 유효한 구매 입력 형식이어야 합니다.");
        assertTrue(InputValidator.isValidPurchaseInput("[사과-2],[바나나-3]"), "[사과-2],[바나나-3]는 유효한 구매 입력 형식이어야 합니다.");
        assertTrue(InputValidator.isValidPurchaseInput("[사과-1] , [바나나-5]"), "[사과-1], [바나나-5]는 유효한 구매 입력 형식이어야 합니다.");

        assertFalse(InputValidator.isValidPurchaseInput("[사과]"), "[사과]는 수량이 없으므로 유효하지 않은 구매 입력 형식이어야 합니다.");
        assertFalse(InputValidator.isValidPurchaseInput("사과-2"), "대괄호가 없으므로 유효하지 않은 구매 입력 형식이어야 합니다.");
        assertFalse(InputValidator.isValidPurchaseInput("[사과-0]"), "수량이 0이므로 유효하지 않은 구매 입력 형식이어야 합니다.");
        assertFalse(InputValidator.isValidPurchaseInput("[사과-2], 바나나-3"), "[사과-2], 바나나-3은 유효하지 않은 구매 입력 형식이어야 합니다.");
        assertFalse(InputValidator.isValidPurchaseInput("[사과-2, 바나나-3]"), "콤마 구분이 대괄호 안에 있어 유효하지 않은 구매 입력 형식이어야 합니다.");
    }
}
