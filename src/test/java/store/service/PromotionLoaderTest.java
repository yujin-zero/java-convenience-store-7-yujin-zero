package store.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.Promotion;

class PromotionLoaderTest {

    @Test
    @DisplayName("프로모션 정보를 파일에서 로드하여 Promotion 리스트로 반환하는지 테스트")
    public void testLoadPromotions() throws IOException {
        Path tempFile = Files.createTempFile("testPromotions", "md");
        try (FileWriter writer = new FileWriter(tempFile.toFile())) {
            writer.write("name,buy,get,start_date,end_date\n");
            writer.write("탄산2+1,2,1,2024-01-01,2024-12-31\n");
            writer.write("MD추천상품,1,1,2024-01-01,2024-12-31\n");
            writer.write("반짝할인,1,1,2024-11-01,2024-11-30\n");
        }

        PromotionLoader loader = new PromotionLoader();
        List<Promotion> promotions = loader.loadPromotions(tempFile.toString());

        assertNotNull(promotions, "Promotion 리스트가 null이 아니어야 합니다.");
        assertEquals(3, promotions.size(), "리스트의 프로모션 개수가 예상과 다릅니다");

        Promotion promotion1 = promotions.get(0);
        assertEquals("탄산2+1", promotion1.getName());
        assertEquals(2, promotion1.getBuy());
        assertEquals(1, promotion1.getGet());

        Promotion promotion2 = promotions.get(1);
        assertEquals("MD추천상품", promotion2.getName());

        Promotion promotion3 = promotions.get(2);
        assertEquals("반짝할인", promotion3.getName());

        Files.delete(tempFile);
    }
}