package store.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.model.Product;
import store.model.Promotion;

class ProductLoaderTest {

    @Test
    @DisplayName("상품 정보를 파싱해서 Product 리스트로 반환하는지 테스트")
    public void testLoadProduct() throws IOException {
        Path tempFile = Files.createTempFile("testProducts", "md");
        try (FileWriter writer = new FileWriter(tempFile.toFile())) {
            writer.write("name,price,quantity,promotion\n");
            writer.write("콜라,1000,10,탄산2+1\n");
            writer.write("사이다,1200,5,null\n");
            writer.write("물,500,20,null\n");
        }

        List<Promotion> promotions = new ArrayList<>();
        promotions.add(new Promotion("탄산2+1", 3, 1, LocalDate.of(2024, 11, 1).atStartOfDay(),
                LocalDate.of(2024, 12, 1).atStartOfDay()));

        ProductLoader loader = new ProductLoader(promotions);
        List<Product> products = loader.loadProduct(tempFile.toString());

        assertNotNull(products, "Product 리스트가 null이 아니어야 합니다.");
        assertEquals(3, products.size(), "리스트의 상품 개수가 예상과 다릅니다.");

        Product product1 = products.get(0);
        assertEquals("콜라", product1.getName());
        assertEquals(1000, product1.getPrice());
//        assertEquals(10, product1.getQuantity());
        assertEquals("탄산2+1", product1.getPromotion().getName());

        Product product2 = products.get(1);
        assertEquals("사이다", product2.getName());
        assertEquals(1200, product2.getPrice());
//        assertEquals(5, product2.getQuantity());
        assertEquals(null, product2.getPromotion());

        Product product3 = products.get(2);
        assertEquals("물", product3.getName());
        assertEquals(500, product3.getPrice());
//        assertEquals(20, product3.getQuantity());
        assertEquals(null, product3.getPromotion());

        Files.delete(tempFile);
    }

}