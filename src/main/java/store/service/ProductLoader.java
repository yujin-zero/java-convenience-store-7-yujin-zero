package store.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import store.model.Product;
import store.model.Promotion;

public class ProductLoader {
    private List<Promotion> promotions;

    public ProductLoader(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public List<Product> loadProduct(String filePath) {
        List<Product> products = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                Product product = parseProductLine(line);
                products.add(product);
                addOrSkipGeneralProduct(br, products, product);
            }
        } catch (IOException e) {
            throw new RuntimeException("파일을 읽는 도중 오류가 발생했습니다: " + filePath, e);
        }

        return products;
    }

    private void addOrSkipGeneralProduct(BufferedReader br, List<Product> products, Product promoProduct)
            throws IOException {
        if (promoProduct.getPromotion() == null) {
            return;
        }

        br.mark(1000);
        String nextLine = br.readLine();

        if (nextLine == null || !isNextLineGeneralProduct(nextLine, promoProduct, products)) {
            products.add(new Product(promoProduct.getName(), promoProduct.getPrice(), 0, null));
            if (nextLine != null) {
                br.reset();
            }
        }
    }

    private boolean isNextLineGeneralProduct(String nextLine, Product promoProduct, List<Product> products) {
        Product nextProduct = parseProductLine(nextLine);
        if (nextProduct.getName().equals(promoProduct.getName()) && nextProduct.getPromotion() == null) {
            products.add(nextProduct);
            return true;
        }
        return false;
    }

    private Product parseProductLine(String line) {
        StringTokenizer tokenizer = new StringTokenizer(line, ",");

        String name = tokenizer.nextToken().trim();
        int price = Integer.parseInt(tokenizer.nextToken().trim());
        int quantity = Integer.parseInt(tokenizer.nextToken().trim());

        Promotion promotion = null;
        if (tokenizer.hasMoreTokens()) {
            String promotionName = tokenizer.nextToken().trim();
            if (!"null".equalsIgnoreCase(promotionName)) {
                promotion = findPromotionByName(promotionName);
            }
        }

        return new Product(name, price, quantity, promotion);
    }

    private Promotion findPromotionByName(String promotionName) {
        for (Promotion promotion : promotions) {
            if (promotion.getName().equalsIgnoreCase(promotionName)) {
                return promotion;
            }
        }
        return null;
    }
}
