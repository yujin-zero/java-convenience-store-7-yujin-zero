package store.service;

import java.util.StringTokenizer;
import store.model.Product;

public class ProductLoader {

    private Product parseProductLine(String line) {
        StringTokenizer tokenizer = new StringTokenizer(line, ",");

        String name = tokenizer.nextToken().trim();
        int price = Integer.parseInt(tokenizer.nextToken().trim());
        int quantity = Integer.parseInt(tokenizer.nextToken().trim());

        String promotion = null;
        if (tokenizer.hasMoreTokens()) {
            promotion = tokenizer.nextToken().trim();
        }

        return new Product(name, price, quantity, promotion);
    }
}
