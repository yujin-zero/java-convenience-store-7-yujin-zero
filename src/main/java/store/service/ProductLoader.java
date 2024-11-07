package store.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import store.model.Product;

public class ProductLoader {

    public List<Product> loadProduct(String filePath) {
        List<Product> products = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                Product product = parseProductLine(line);
                products.add(product);
            }
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽는 도중 오류가 발생했습니다: " + filePath, e);
        }

        return products;
    }

    private Product parseProductLine(String line) {
        StringTokenizer tokenizer = new StringTokenizer(line, ",");

        String name = tokenizer.nextToken().trim();
        int price = Integer.parseInt(tokenizer.nextToken().trim());
        int quantity = Integer.parseInt(tokenizer.nextToken().trim());

        String promotion = null;
        if (tokenizer.hasMoreTokens()) {
            promotion = tokenizer.nextToken().trim();
            if ("null".equalsIgnoreCase(promotion)) {
                promotion = null;
            }
        }

        return new Product(name, price, quantity, promotion);
    }
}
